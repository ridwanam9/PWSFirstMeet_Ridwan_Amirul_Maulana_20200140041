/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.Springboot_penjualan;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author DELL
 */
@Entity
@Table(name = "supplier")
@NamedQueries({
    @NamedQuery(name = "Supplier.findAll", query = "SELECT s FROM Supplier s"),
    @NamedQuery(name = "Supplier.findByIdsuplier", query = "SELECT s FROM Supplier s WHERE s.idsuplier = :idsuplier"),
    @NamedQuery(name = "Supplier.findByNama", query = "SELECT s FROM Supplier s WHERE s.nama = :nama"),
    @NamedQuery(name = "Supplier.findByTelp", query = "SELECT s FROM Supplier s WHERE s.telp = :telp"),
    @NamedQuery(name = "Supplier.findByAlamat", query = "SELECT s FROM Supplier s WHERE s.alamat = :alamat")})
public class Supplier implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "Id_suplier")
    private String idsuplier;
    @Column(name = "Nama")
    private String nama;
    @Column(name = "Telp")
    private String telp;
    @Column(name = "Alamat")
    private String alamat;
    @OneToMany(mappedBy = "idSuplier")
    private Collection<Pembelian> pembelianCollection;

    public Supplier() {
    }

    public Supplier(String idsuplier) {
        this.idsuplier = idsuplier;
    }

    public String getIdsuplier() {
        return idsuplier;
    }

    public void setIdsuplier(String idsuplier) {
        this.idsuplier = idsuplier;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getTelp() {
        return telp;
    }

    public void setTelp(String telp) {
        this.telp = telp;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public Collection<Pembelian> getPembelianCollection() {
        return pembelianCollection;
    }

    public void setPembelianCollection(Collection<Pembelian> pembelianCollection) {
        this.pembelianCollection = pembelianCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idsuplier != null ? idsuplier.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Supplier)) {
            return false;
        }
        Supplier other = (Supplier) object;
        if ((this.idsuplier == null && other.idsuplier != null) || (this.idsuplier != null && !this.idsuplier.equals(other.idsuplier))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.example.Springboot_penjualan.Supplier[ idsuplier=" + idsuplier + " ]";
    }
    
}
