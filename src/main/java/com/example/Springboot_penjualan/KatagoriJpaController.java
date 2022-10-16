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
public class KatagoriJpaController implements Serializable {

    public KatagoriJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("com.example._Springboot_penjualan_jar_0.0.1-SNAPSHOTPU");

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Katagori katagori) throws PreexistingEntityException, Exception {
        if (katagori.getBarangCollection() == null) {
            katagori.setBarangCollection(new ArrayList<Barang>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<Barang> attachedBarangCollection = new ArrayList<Barang>();
            for (Barang barangCollectionBarangToAttach : katagori.getBarangCollection()) {
                barangCollectionBarangToAttach = em.getReference(barangCollectionBarangToAttach.getClass(), barangCollectionBarangToAttach.getIdBarang());
                attachedBarangCollection.add(barangCollectionBarangToAttach);
            }
            katagori.setBarangCollection(attachedBarangCollection);
            em.persist(katagori);
            for (Barang barangCollectionBarang : katagori.getBarangCollection()) {
                Katagori oldIdKatagoriOfBarangCollectionBarang = barangCollectionBarang.getIdKatagori();
                barangCollectionBarang.setIdKatagori(katagori);
                barangCollectionBarang = em.merge(barangCollectionBarang);
                if (oldIdKatagoriOfBarangCollectionBarang != null) {
                    oldIdKatagoriOfBarangCollectionBarang.getBarangCollection().remove(barangCollectionBarang);
                    oldIdKatagoriOfBarangCollectionBarang = em.merge(oldIdKatagoriOfBarangCollectionBarang);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findKatagori(katagori.getIdKatagori()) != null) {
                throw new PreexistingEntityException("Katagori " + katagori + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Katagori katagori) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Katagori persistentKatagori = em.find(Katagori.class, katagori.getIdKatagori());
            Collection<Barang> barangCollectionOld = persistentKatagori.getBarangCollection();
            Collection<Barang> barangCollectionNew = katagori.getBarangCollection();
            Collection<Barang> attachedBarangCollectionNew = new ArrayList<Barang>();
            for (Barang barangCollectionNewBarangToAttach : barangCollectionNew) {
                barangCollectionNewBarangToAttach = em.getReference(barangCollectionNewBarangToAttach.getClass(), barangCollectionNewBarangToAttach.getIdBarang());
                attachedBarangCollectionNew.add(barangCollectionNewBarangToAttach);
            }
            barangCollectionNew = attachedBarangCollectionNew;
            katagori.setBarangCollection(barangCollectionNew);
            katagori = em.merge(katagori);
            for (Barang barangCollectionOldBarang : barangCollectionOld) {
                if (!barangCollectionNew.contains(barangCollectionOldBarang)) {
                    barangCollectionOldBarang.setIdKatagori(null);
                    barangCollectionOldBarang = em.merge(barangCollectionOldBarang);
                }
            }
            for (Barang barangCollectionNewBarang : barangCollectionNew) {
                if (!barangCollectionOld.contains(barangCollectionNewBarang)) {
                    Katagori oldIdKatagoriOfBarangCollectionNewBarang = barangCollectionNewBarang.getIdKatagori();
                    barangCollectionNewBarang.setIdKatagori(katagori);
                    barangCollectionNewBarang = em.merge(barangCollectionNewBarang);
                    if (oldIdKatagoriOfBarangCollectionNewBarang != null && !oldIdKatagoriOfBarangCollectionNewBarang.equals(katagori)) {
                        oldIdKatagoriOfBarangCollectionNewBarang.getBarangCollection().remove(barangCollectionNewBarang);
                        oldIdKatagoriOfBarangCollectionNewBarang = em.merge(oldIdKatagoriOfBarangCollectionNewBarang);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = katagori.getIdKatagori();
                if (findKatagori(id) == null) {
                    throw new NonexistentEntityException("The katagori with id " + id + " no longer exists.");
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
            Katagori katagori;
            try {
                katagori = em.getReference(Katagori.class, id);
                katagori.getIdKatagori();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The katagori with id " + id + " no longer exists.", enfe);
            }
            Collection<Barang> barangCollection = katagori.getBarangCollection();
            for (Barang barangCollectionBarang : barangCollection) {
                barangCollectionBarang.setIdKatagori(null);
                barangCollectionBarang = em.merge(barangCollectionBarang);
            }
            em.remove(katagori);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Katagori> findKatagoriEntities() {
        return findKatagoriEntities(true, -1, -1);
    }

    public List<Katagori> findKatagoriEntities(int maxResults, int firstResult) {
        return findKatagoriEntities(false, maxResults, firstResult);
    }

    private List<Katagori> findKatagoriEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Katagori.class));
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

    public Katagori findKatagori(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Katagori.class, id);
        } finally {
            em.close();
        }
    }

    public int getKatagoriCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Katagori> rt = cq.from(Katagori.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
