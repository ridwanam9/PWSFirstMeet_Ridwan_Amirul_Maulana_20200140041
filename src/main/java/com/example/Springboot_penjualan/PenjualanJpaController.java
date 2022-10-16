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
public class PenjualanJpaController implements Serializable {

    public PenjualanJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("com.example._Springboot_penjualan_jar_0.0.1-SNAPSHOTPU");

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Penjualan penjualan) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Pelanggan idPelanggan = penjualan.getIdPelanggan();
            if (idPelanggan != null) {
                idPelanggan = em.getReference(idPelanggan.getClass(), idPelanggan.getIdPelanggan());
                penjualan.setIdPelanggan(idPelanggan);
            }
            em.persist(penjualan);
            if (idPelanggan != null) {
                idPelanggan.getPenjualanCollection().add(penjualan);
                idPelanggan = em.merge(idPelanggan);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findPenjualan(penjualan.getIdPenjualan()) != null) {
                throw new PreexistingEntityException("Penjualan " + penjualan + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Penjualan penjualan) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Penjualan persistentPenjualan = em.find(Penjualan.class, penjualan.getIdPenjualan());
            Pelanggan idPelangganOld = persistentPenjualan.getIdPelanggan();
            Pelanggan idPelangganNew = penjualan.getIdPelanggan();
            if (idPelangganNew != null) {
                idPelangganNew = em.getReference(idPelangganNew.getClass(), idPelangganNew.getIdPelanggan());
                penjualan.setIdPelanggan(idPelangganNew);
            }
            penjualan = em.merge(penjualan);
            if (idPelangganOld != null && !idPelangganOld.equals(idPelangganNew)) {
                idPelangganOld.getPenjualanCollection().remove(penjualan);
                idPelangganOld = em.merge(idPelangganOld);
            }
            if (idPelangganNew != null && !idPelangganNew.equals(idPelangganOld)) {
                idPelangganNew.getPenjualanCollection().add(penjualan);
                idPelangganNew = em.merge(idPelangganNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = penjualan.getIdPenjualan();
                if (findPenjualan(id) == null) {
                    throw new NonexistentEntityException("The penjualan with id " + id + " no longer exists.");
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
            Penjualan penjualan;
            try {
                penjualan = em.getReference(Penjualan.class, id);
                penjualan.getIdPenjualan();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The penjualan with id " + id + " no longer exists.", enfe);
            }
            Pelanggan idPelanggan = penjualan.getIdPelanggan();
            if (idPelanggan != null) {
                idPelanggan.getPenjualanCollection().remove(penjualan);
                idPelanggan = em.merge(idPelanggan);
            }
            em.remove(penjualan);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Penjualan> findPenjualanEntities() {
        return findPenjualanEntities(true, -1, -1);
    }

    public List<Penjualan> findPenjualanEntities(int maxResults, int firstResult) {
        return findPenjualanEntities(false, maxResults, firstResult);
    }

    private List<Penjualan> findPenjualanEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Penjualan.class));
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

    public Penjualan findPenjualan(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Penjualan.class, id);
        } finally {
            em.close();
        }
    }

    public int getPenjualanCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Penjualan> rt = cq.from(Penjualan.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
