/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.Springboot_penjualan;

import com.example.Springboot_penjualan.exceptions.NonexistentEntityException;
import com.example.Springboot_penjualan.exceptions.PreexistingEntityException;
import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.Persistence;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

/**
 *
 * @author DELL
 */
public class BarangJpaController implements Serializable {

    public BarangJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("com.example._Springboot_penjualan_jar_0.0.1-SNAPSHOTPU");

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Barang barang) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Katagori idKatagori = barang.getIdKatagori();
            if (idKatagori != null) {
                idKatagori = em.getReference(idKatagori.getClass(), idKatagori.getIdKatagori());
                barang.setIdKatagori(idKatagori);
            }
            em.persist(barang);
            if (idKatagori != null) {
                idKatagori.getBarangCollection().add(barang);
                idKatagori = em.merge(idKatagori);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findBarang(barang.getIdBarang()) != null) {
                throw new PreexistingEntityException("Barang " + barang + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Barang barang) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Barang persistentBarang = em.find(Barang.class, barang.getIdBarang());
            Katagori idKatagoriOld = persistentBarang.getIdKatagori();
            Katagori idKatagoriNew = barang.getIdKatagori();
            if (idKatagoriNew != null) {
                idKatagoriNew = em.getReference(idKatagoriNew.getClass(), idKatagoriNew.getIdKatagori());
                barang.setIdKatagori(idKatagoriNew);
            }
            barang = em.merge(barang);
            if (idKatagoriOld != null && !idKatagoriOld.equals(idKatagoriNew)) {
                idKatagoriOld.getBarangCollection().remove(barang);
                idKatagoriOld = em.merge(idKatagoriOld);
            }
            if (idKatagoriNew != null && !idKatagoriNew.equals(idKatagoriOld)) {
                idKatagoriNew.getBarangCollection().add(barang);
                idKatagoriNew = em.merge(idKatagoriNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = barang.getIdBarang();
                if (findBarang(id) == null) {
                    throw new NonexistentEntityException("The barang with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(String id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Barang barang;
            try {
                barang = em.getReference(Barang.class, id);
                barang.getIdBarang();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The barang with id " + id + " no longer exists.", enfe);
            }
            Katagori idKatagori = barang.getIdKatagori();
            if (idKatagori != null) {
                idKatagori.getBarangCollection().remove(barang);
                idKatagori = em.merge(idKatagori);
            }
            em.remove(barang);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Barang> findBarangEntities() {
        return findBarangEntities(true, -1, -1);
    }

    public List<Barang> findBarangEntities(int maxResults, int firstResult) {
        return findBarangEntities(false, maxResults, firstResult);
    }

    private List<Barang> findBarangEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Barang.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Barang findBarang(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Barang.class, id);
        } finally {
            em.close();
        }
    }

    public int getBarangCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Barang> rt = cq.from(Barang.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
