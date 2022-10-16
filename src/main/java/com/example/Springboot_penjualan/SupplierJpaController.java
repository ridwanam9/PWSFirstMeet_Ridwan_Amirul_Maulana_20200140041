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
public class SupplierJpaController implements Serializable {

    public SupplierJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("com.example._Springboot_penjualan_jar_0.0.1-SNAPSHOTPU");

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Supplier supplier) throws PreexistingEntityException, Exception {
        if (supplier.getPembelianCollection() == null) {
            supplier.setPembelianCollection(new ArrayList<Pembelian>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<Pembelian> attachedPembelianCollection = new ArrayList<Pembelian>();
            for (Pembelian pembelianCollectionPembelianToAttach : supplier.getPembelianCollection()) {
                pembelianCollectionPembelianToAttach = em.getReference(pembelianCollectionPembelianToAttach.getClass(), pembelianCollectionPembelianToAttach.getIdPembelian());
                attachedPembelianCollection.add(pembelianCollectionPembelianToAttach);
            }
            supplier.setPembelianCollection(attachedPembelianCollection);
            em.persist(supplier);
            for (Pembelian pembelianCollectionPembelian : supplier.getPembelianCollection()) {
                Supplier oldIdSuplierOfPembelianCollectionPembelian = pembelianCollectionPembelian.getIdSuplier();
                pembelianCollectionPembelian.setIdSuplier(supplier);
                pembelianCollectionPembelian = em.merge(pembelianCollectionPembelian);
                if (oldIdSuplierOfPembelianCollectionPembelian != null) {
                    oldIdSuplierOfPembelianCollectionPembelian.getPembelianCollection().remove(pembelianCollectionPembelian);
                    oldIdSuplierOfPembelianCollectionPembelian = em.merge(oldIdSuplierOfPembelianCollectionPembelian);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findSupplier(supplier.getIdsuplier()) != null) {
                throw new PreexistingEntityException("Supplier " + supplier + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Supplier supplier) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Supplier persistentSupplier = em.find(Supplier.class, supplier.getIdsuplier());
            Collection<Pembelian> pembelianCollectionOld = persistentSupplier.getPembelianCollection();
            Collection<Pembelian> pembelianCollectionNew = supplier.getPembelianCollection();
            Collection<Pembelian> attachedPembelianCollectionNew = new ArrayList<Pembelian>();
            for (Pembelian pembelianCollectionNewPembelianToAttach : pembelianCollectionNew) {
                pembelianCollectionNewPembelianToAttach = em.getReference(pembelianCollectionNewPembelianToAttach.getClass(), pembelianCollectionNewPembelianToAttach.getIdPembelian());
                attachedPembelianCollectionNew.add(pembelianCollectionNewPembelianToAttach);
            }
            pembelianCollectionNew = attachedPembelianCollectionNew;
            supplier.setPembelianCollection(pembelianCollectionNew);
            supplier = em.merge(supplier);
            for (Pembelian pembelianCollectionOldPembelian : pembelianCollectionOld) {
                if (!pembelianCollectionNew.contains(pembelianCollectionOldPembelian)) {
                    pembelianCollectionOldPembelian.setIdSuplier(null);
                    pembelianCollectionOldPembelian = em.merge(pembelianCollectionOldPembelian);
                }
            }
            for (Pembelian pembelianCollectionNewPembelian : pembelianCollectionNew) {
                if (!pembelianCollectionOld.contains(pembelianCollectionNewPembelian)) {
                    Supplier oldIdSuplierOfPembelianCollectionNewPembelian = pembelianCollectionNewPembelian.getIdSuplier();
                    pembelianCollectionNewPembelian.setIdSuplier(supplier);
                    pembelianCollectionNewPembelian = em.merge(pembelianCollectionNewPembelian);
                    if (oldIdSuplierOfPembelianCollectionNewPembelian != null && !oldIdSuplierOfPembelianCollectionNewPembelian.equals(supplier)) {
                        oldIdSuplierOfPembelianCollectionNewPembelian.getPembelianCollection().remove(pembelianCollectionNewPembelian);
                        oldIdSuplierOfPembelianCollectionNewPembelian = em.merge(oldIdSuplierOfPembelianCollectionNewPembelian);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = supplier.getIdsuplier();
                if (findSupplier(id) == null) {
                    throw new NonexistentEntityException("The supplier with id " + id + " no longer exists.");
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
            Supplier supplier;
            try {
                supplier = em.getReference(Supplier.class, id);
                supplier.getIdsuplier();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The supplier with id " + id + " no longer exists.", enfe);
            }
            Collection<Pembelian> pembelianCollection = supplier.getPembelianCollection();
            for (Pembelian pembelianCollectionPembelian : pembelianCollection) {
                pembelianCollectionPembelian.setIdSuplier(null);
                pembelianCollectionPembelian = em.merge(pembelianCollectionPembelian);
            }
            em.remove(supplier);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Supplier> findSupplierEntities() {
        return findSupplierEntities(true, -1, -1);
    }

    public List<Supplier> findSupplierEntities(int maxResults, int firstResult) {
        return findSupplierEntities(false, maxResults, firstResult);
    }

    private List<Supplier> findSupplierEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Supplier.class));
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

    public Supplier findSupplier(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Supplier.class, id);
        } finally {
            em.close();
        }
    }

    public int getSupplierCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Supplier> rt = cq.from(Supplier.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
