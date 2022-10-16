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
public class ReturPembelianJpaController implements Serializable {

    public ReturPembelianJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("com.example._Springboot_penjualan_jar_0.0.1-SNAPSHOTPU");

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(ReturPembelian returPembelian) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Pembelian idPembelian = returPembelian.getIdPembelian();
            if (idPembelian != null) {
                idPembelian = em.getReference(idPembelian.getClass(), idPembelian.getIdPembelian());
                returPembelian.setIdPembelian(idPembelian);
            }
            em.persist(returPembelian);
            if (idPembelian != null) {
                idPembelian.getReturPembelianCollection().add(returPembelian);
                idPembelian = em.merge(idPembelian);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findReturPembelian(returPembelian.getIdReturPembelian()) != null) {
                throw new PreexistingEntityException("ReturPembelian " + returPembelian + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(ReturPembelian returPembelian) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            ReturPembelian persistentReturPembelian = em.find(ReturPembelian.class, returPembelian.getIdReturPembelian());
            Pembelian idPembelianOld = persistentReturPembelian.getIdPembelian();
            Pembelian idPembelianNew = returPembelian.getIdPembelian();
            if (idPembelianNew != null) {
                idPembelianNew = em.getReference(idPembelianNew.getClass(), idPembelianNew.getIdPembelian());
                returPembelian.setIdPembelian(idPembelianNew);
            }
            returPembelian = em.merge(returPembelian);
            if (idPembelianOld != null && !idPembelianOld.equals(idPembelianNew)) {
                idPembelianOld.getReturPembelianCollection().remove(returPembelian);
                idPembelianOld = em.merge(idPembelianOld);
            }
            if (idPembelianNew != null && !idPembelianNew.equals(idPembelianOld)) {
                idPembelianNew.getReturPembelianCollection().add(returPembelian);
                idPembelianNew = em.merge(idPembelianNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = returPembelian.getIdReturPembelian();
                if (findReturPembelian(id) == null) {
                    throw new NonexistentEntityException("The returPembelian with id " + id + " no longer exists.");
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
            ReturPembelian returPembelian;
            try {
                returPembelian = em.getReference(ReturPembelian.class, id);
                returPembelian.getIdReturPembelian();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The returPembelian with id " + id + " no longer exists.", enfe);
            }
            Pembelian idPembelian = returPembelian.getIdPembelian();
            if (idPembelian != null) {
                idPembelian.getReturPembelianCollection().remove(returPembelian);
                idPembelian = em.merge(idPembelian);
            }
            em.remove(returPembelian);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<ReturPembelian> findReturPembelianEntities() {
        return findReturPembelianEntities(true, -1, -1);
    }

    public List<ReturPembelian> findReturPembelianEntities(int maxResults, int firstResult) {
        return findReturPembelianEntities(false, maxResults, firstResult);
    }

    private List<ReturPembelian> findReturPembelianEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(ReturPembelian.class));
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

    public ReturPembelian findReturPembelian(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(ReturPembelian.class, id);
        } finally {
            em.close();
        }
    }

    public int getReturPembelianCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<ReturPembelian> rt = cq.from(ReturPembelian.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
