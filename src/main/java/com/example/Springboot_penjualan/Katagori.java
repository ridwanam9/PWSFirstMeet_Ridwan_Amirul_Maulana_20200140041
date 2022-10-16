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
@Table(name = "katagori")
@NamedQueries({
    @NamedQuery(name = "Katagori.findAll", query = "SELECT k FROM Katagori k"),
    @NamedQuery(name = "Katagori.findByIdKatagori", query = "SELECT k FROM Katagori k WHERE k.idKatagori = :idKatagori"),
    @NamedQuery(name = "Katagori.findByNama", query = "SELECT k FROM Katagori k WHERE k.nama = :nama")})
public class Katagori implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "Id_Katagori")
    private String idKatagori;
    @Column(name = "Nama")
    private String nama;
    @OneToMany(mappedBy = "idKatagori")
    private Collection<Barang> barangCollection;

    public Katagori() {
    }

    public Katagori(String idKatagori) {
        this.idKatagori = idKatagori;
    }

    public String getIdKatagori() {
        return idKatagori;
    }

    public void setIdKatagori(String idKatagori) {
        this.idKatagori = idKatagori;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public Collection<Barang> getBarangCollection() {
        return barangCollection;
    }

    public void setBarangCollection(Collection<Barang> barangCollection) {
        this.barangCollection = barangCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idKatagori != null ? idKatagori.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Katagori)) {
            return false;
        }
        Katagori other = (Katagori) object;
        if ((this.idKatagori == null && other.idKatagori != null) || (this.idKatagori != null && !this.idKatagori.equals(other.idKatagori))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.example.Springboot_penjualan.Katagori[ idKatagori=" + idKatagori + " ]";
    }
    
}
