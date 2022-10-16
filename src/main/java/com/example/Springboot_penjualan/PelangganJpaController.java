/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.Springboot_penjualan;

import com.example.Springboot_penjualan.exceptions.NonexistentEntityException;
import com.example.Springboot_penjualan.exceptions.PreexistingEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author DELL
 */
public class PelangganJpaController implements Serializable {

    public PelangganJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("com.example._Springboot_penjualan_jar_0.0.1-SNAPSHOTPU");

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Pelanggan pelanggan) throws PreexistingEntityException, Exception {
        if (pelanggan.getPenjualanCollection() == null) {
            pelanggan.setPenjualanCollection(new ArrayList<Penjualan>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<Penjualan> attachedPenjualanCollection = new ArrayList<Penjualan>();
            for (Penjualan penjualanCollectionPenjualanToAttach : pelanggan.getPenjualanCollection()) {
                penjualanCollectionPenjualanToAttach = em.getReference(penjualanCollectionPenjualanToAttach.getClass(), penjualanCollectionPenjualanToAttach.getIdPenjualan());
                attachedPenjualanCollection.add(penjualanCollectionPenjualanToAttach);
            }
            pelanggan.setPenjualanCollection(attachedPenjualanCollection);
            em.persist(pelanggan);
            for (Penjualan penjualanCollectionPenjualan : pelanggan.getPenjualanCollection()) {
                Pelanggan oldIdPelangganOfPenjualanCollectionPenjualan = penjualanCollectionPenjualan.getIdPelanggan();
                penjualanCollectionPenjualan.setIdPelanggan(pelanggan);
                penjualanCollectionPenjualan = em.merge(penjualanCollectionPenjualan);
                if (oldIdPelangganOfPenjualanCollectionPenjualan != null) {
                    oldIdPelangganOfPenjualanCollectionPenjualan.getPenjualanCollection().remove(penjualanCollectionPenjualan);
                    oldIdPelangganOfPenjualanCollectionPenjualan = em.merge(oldIdPelangganOfPenjualanCollectionPenjualan);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findPelanggan(pelanggan.getIdPelanggan()) != null) {
                throw new PreexistingEntityException("Pelanggan " + pelanggan + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Pelanggan pelanggan) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Pelanggan persistentPelanggan = em.find(Pelanggan.class, pelanggan.getIdPelanggan());
            Collection<Penjualan> penjualanCollectionOld = persistentPelanggan.getPenjualanCollection();
            Collection<Penjualan> penjualanCollectionNew = pelanggan.getPenjualanCollection();
            Collection<Penjualan> attachedPenjualanCollectionNew = new ArrayList<Penjualan>();
            for (Penjualan penjualanCollectionNewPenjualanToAttach : penjualanCollectionNew) {
                penjualanCollectionNewPenjualanToAttach = em.getReference(penjualanCollectionNewPenjualanToAttach.getClass(), penjualanCollectionNewPenjualanToAttach.getIdPenjualan());
                attachedPenjualanCollectionNew.add(penjualanCollectionNewPenjualanToAttach);
            }
            penjualanCollectionNew = attachedPenjualanCollectionNew;
            pelanggan.setPenjualanCollection(penjualanCollectionNew);
            pelanggan = em.merge(pelanggan);
            for (Penjualan penjualanCollectionOldPenjualan : penjualanCollectionOld) {
                if (!penjualanCollectionNew.contains(penjualanCollectionOldPenjualan)) {
                    penjualanCollectionOldPenjualan.setIdPelanggan(null);
                    penjualanCollectionOldPenjualan = em.merge(penjualanCollectionOldPenjualan);
                }
            }
            for (Penjualan penjualanCollectionNewPenjualan : penjualanCollectionNew) {
                if (!penjualanCollectionOld.contains(penjualanCollectionNewPenjualan)) {
                    Pelanggan oldIdPelangganOfPenjualanCollectionNewPenjualan = penjualanCollectionNewPenjualan.getIdPelanggan();
                    penjualanCollectionNewPenjualan.setIdPelanggan(pelanggan);
                    penjualanCollectionNewPenjualan = em.merge(penjualanCollectionNewPenjualan);
                    if (oldIdPelangganOfPenjualanCollectionNewPenjualan != null && !oldIdPelangganOfPenjualanCollectionNewPenjualan.equals(pelanggan)) {
                        oldIdPelangganOfPenjualanCollectionNewPenjualan.getPenjualanCollection().remove(penjualanCollectionNewPenjualan);
                        oldIdPelangganOfPenjualanCollectionNewPenjualan = em.merge(oldIdPelangganOfPenjualanCollectionNewPenjualan);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = pelanggan.getIdPelanggan();
                if (findPelanggan(id) == null) {
                    throw new NonexistentEntityException("The pelanggan with id " + id + " no longer exists.");
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
            Pelanggan pelanggan;
            try {
                pelanggan = em.getReference(Pelanggan.class, id);
                pelanggan.getIdPelanggan();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The pelanggan with id " + id + " no longer exists.", enfe);
            }
            Collection<Penjualan> penjualanCollection = pelanggan.getPenjualanCollection();
            for (Penjualan penjualanCollectionPenjualan : penjualanCollection) {
                penjualanCollectionPenjualan.setIdPelanggan(null);
                penjualanCollectionPenjualan = em.merge(penjualanCollectionPenjualan);
            }
            em.remove(pelanggan);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Pelanggan> findPelangganEntities() {
        return findPelangganEntities(true, -1, -1);
    }

    public List<Pelanggan> findPelangganEntities(int maxResults, int firstResult) {
        return findPelangganEntities(false, maxResults, firstResult);
    }

    private List<Pelanggan> findPelangganEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Pelanggan.class));
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

    public Pelanggan findPelanggan(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Pelanggan.class, id);
        } finally {
            em.close();
        }
    }

    public int getPelangganCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Pelanggan> rt = cq.from(Pelanggan.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
