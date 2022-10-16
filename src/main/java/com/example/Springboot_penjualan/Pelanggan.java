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
@Table(name = "pelanggan")
@NamedQueries({
    @NamedQuery(name = "Pelanggan.findAll", query = "SELECT p FROM Pelanggan p"),
    @NamedQuery(name = "Pelanggan.findByIdPelanggan", query = "SELECT p FROM Pelanggan p WHERE p.idPelanggan = :idPelanggan"),
    @NamedQuery(name = "Pelanggan.findByNama", query = "SELECT p FROM Pelanggan p WHERE p.nama = :nama"),
    @NamedQuery(name = "Pelanggan.findByTelp", query = "SELECT p FROM Pelanggan p WHERE p.telp = :telp"),
    @NamedQuery(name = "Pelanggan.findByAlamat", query = "SELECT p FROM Pelanggan p WHERE p.alamat = :alamat")})
public class Pelanggan implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "Id_Pelanggan")
    private String idPelanggan;
    @Column(name = "Nama")
    private String nama;
    @Column(name = "Telp")
    private String telp;
    @Column(name = "Alamat")
    private String alamat;
    @OneToMany(mappedBy = "idPelanggan")
    private Collection<Penjualan> penjualanCollection;

    public Pelanggan() {
    }

    public Pelanggan(String idPelanggan) {
        this.idPelanggan = idPelanggan;
    }

    public String getIdPelanggan() {
        return idPelanggan;
    }

    public void setIdPelanggan(String idPelanggan) {
        this.idPelanggan = idPelanggan;
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

    public Collection<Penjualan> getPenjualanCollection() {
        return penjualanCollection;
    }

    public void setPenjualanCollection(Collection<Penjualan> penjualanCollection) {
        this.penjualanCollection = penjualanCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idPelanggan != null ? idPelanggan.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Pelanggan)) {
            return false;
        }
        Pelanggan other = (Pelanggan) object;
        if ((this.idPelanggan == null && other.idPelanggan != null) || (this.idPelanggan != null && !this.idPelanggan.equals(other.idPelanggan))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.example.Springboot_penjualan.Pelanggan[ idPelanggan=" + idPelanggan + " ]";
    }
    
}
