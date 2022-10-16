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
public class PembelianJpaController implements Serializable {

    public PembelianJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("com.example._Springboot_penjualan_jar_0.0.1-SNAPSHOTPU");

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Pembelian pembelian) throws PreexistingEntityException, Exception {
        if (pembelian.getReturPembelianCollection() == null) {
            pembelian.setReturPembelianCollection(new ArrayList<ReturPembelian>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Supplier idSuplier = pembelian.getIdSuplier();
            if (idSuplier != null) {
                idSuplier = em.getReference(idSuplier.getClass(), idSuplier.getIdsuplier());
                pembelian.setIdSuplier(idSuplier);
            }
            Collection<ReturPembelian> attachedReturPembelianCollection = new ArrayList<ReturPembelian>();
            for (ReturPembelian returPembelianCollectionReturPembelianToAttach : pembelian.getReturPembelianCollection()) {
                returPembelianCollectionReturPembelianToAttach = em.getReference(returPembelianCollectionReturPembelianToAttach.getClass(), returPembelianCollectionReturPembelianToAttach.getIdReturPembelian());
                attachedReturPembelianCollection.add(returPembelianCollectionReturPembelianToAttach);
            }
            pembelian.setReturPembelianCollection(attachedReturPembelianCollection);
            em.persist(pembelian);
            if (idSuplier != null) {
                idSuplier.getPembelianCollection().add(pembelian);
                idSuplier = em.merge(idSuplier);
            }
            for (ReturPembelian returPembelianCollectionReturPembelian : pembelian.getReturPembelianCollection()) {
                Pembelian oldIdPembelianOfReturPembelianCollectionReturPembelian = returPembelianCollectionReturPembelian.getIdPembelian();
                returPembelianCollectionReturPembelian.setIdPembelian(pembelian);
                returPembelianCollectionReturPembelian = em.merge(returPembelianCollectionReturPembelian);
                if (oldIdPembelianOfReturPembelianCollectionReturPembelian != null) {
                    oldIdPembelianOfReturPembelianCollectionReturPembelian.getReturPembelianCollection().remove(returPembelianCollectionReturPembelian);
                    oldIdPembelianOfReturPembelianCollectionReturPembelian = em.merge(oldIdPembelianOfReturPembelianCollectionReturPembelian);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findPembelian(pembelian.getIdPembelian()) != null) {
                throw new PreexistingEntityException("Pembelian " + pembelian + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Pembelian pembelian) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Pembelian persistentPembelian = em.find(Pembelian.class, pembelian.getIdPembelian());
            Supplier idSuplierOld = persistentPembelian.getIdSuplier();
            Supplier idSuplierNew = pembelian.getIdSuplier();
            Collection<ReturPembelian> returPembelianCollectionOld = persistentPembelian.getReturPembelianCollection();
            Collection<ReturPembelian> returPembelianCollectionNew = pembelian.getReturPembelianCollection();
            if (idSuplierNew != null) {
                idSuplierNew = em.getReference(idSuplierNew.getClass(), idSuplierNew.getIdsuplier());
                pembelian.setIdSuplier(idSuplierNew);
            }
            Collection<ReturPembelian> attachedReturPembelianCollectionNew = new ArrayList<ReturPembelian>();
            for (ReturPembelian returPembelianCollectionNewReturPembelianToAttach : returPembelianCollectionNew) {
                returPembelianCollectionNewReturPembelianToAttach = em.getReference(returPembelianCollectionNewReturPembelianToAttach.getClass(), returPembelianCollectionNewReturPembelianToAttach.getIdReturPembelian());
                attachedReturPembelianCollectionNew.add(returPembelianCollectionNewReturPembelianToAttach);
            }
            returPembelianCollectionNew = attachedReturPembelianCollectionNew;
            pembelian.setReturPembelianCollection(returPembelianCollectionNew);
            pembelian = em.merge(pembelian);
            if (idSuplierOld != null && !idSuplierOld.equals(idSuplierNew)) {
                idSuplierOld.getPembelianCollection().remove(pembelian);
                idSuplierOld = em.merge(idSuplierOld);
            }
            if (idSuplierNew != null && !idSuplierNew.equals(idSuplierOld)) {
                idSuplierNew.getPembelianCollection().add(pembelian);
                idSuplierNew = em.merge(idSuplierNew);
            }
            for (ReturPembelian returPembelianCollectionOldReturPembelian : returPembelianCollectionOld) {
                if (!returPembelianCollectionNew.contains(returPembelianCollectionOldReturPembelian)) {
                    returPembelianCollectionOldReturPembelian.setIdPembelian(null);
                    returPembelianCollectionOldReturPembelian = em.merge(returPembelianCollectionOldReturPembelian);
                }
            }
            for (ReturPembelian returPembelianCollectionNewReturPembelian : returPembelianCollectionNew) {
                if (!returPembelianCollectionOld.contains(returPembelianCollectionNewReturPembelian)) {
                    Pembelian oldIdPembelianOfReturPembelianCollectionNewReturPembelian = returPembelianCollectionNewReturPembelian.getIdPembelian();
                    returPembelianCollectionNewReturPembelian.setIdPembelian(pembelian);
                    returPembelianCollectionNewReturPembelian = em.merge(returPembelianCollectionNewReturPembelian);
                    if (oldIdPembelianOfReturPembelianCollectionNewReturPembelian != null && !oldIdPembelianOfReturPembelianCollectionNewReturPembelian.equals(pembelian)) {
                        oldIdPembelianOfReturPembelianCollectionNewReturPembelian.getReturPembelianCollection().remove(returPembelianCollectionNewReturPembelian);
                        oldIdPembelianOfReturPembelianCollectionNewReturPembelian = em.merge(oldIdPembelianOfReturPembelianCollectionNewReturPembelian);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = pembelian.getIdPembelian();
                if (findPembelian(id) == null) {
                    throw new NonexistentEntityException("The pembelian with id " + id + " no longer exists.");
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
            Pembelian pembelian;
            try {
                pembelian = em.getReference(Pembelian.class, id);
                pembelian.getIdPembelian();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The pembelian with id " + id + " no longer exists.", enfe);
            }
            Supplier idSuplier = pembelian.getIdSuplier();
            if (idSuplier != null) {
                idSuplier.getPembelianCollection().remove(pembelian);
                idSuplier = em.merge(idSuplier);
            }
            Collection<ReturPembelian> returPembelianCollection = pembelian.getReturPembelianCollection();
            for (ReturPembelian returPembelianCollectionReturPembelian : returPembelianCollection) {
                returPembelianCollectionReturPembelian.setIdPembelian(null);
                returPembelianCollectionReturPembelian = em.merge(returPembelianCollectionReturPembelian);
            }
            em.remove(pembelian);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Pembelian> findPembelianEntities() {
        return findPembelianEntities(true, -1, -1);
    }

    public List<Pembelian> findPembelianEntities(int maxResults, int firstResult) {
        return findPembelianEntities(false, maxResults, firstResult);
    }

    private List<Pembelian> findPembelianEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Pembelian.class));
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

    public Pembelian findPembelian(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Pembelian.class, id);
        } finally {
            em.close();
        }
    }

    public int getPembelianCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Pembelian> rt = cq.from(Pembelian.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
