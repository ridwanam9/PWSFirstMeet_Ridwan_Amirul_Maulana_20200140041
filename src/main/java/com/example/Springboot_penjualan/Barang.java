/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.Springboot_penjualan;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author DELL
 */
@Entity
@Table(name = "barang")
@NamedQueries({
    @NamedQuery(name = "Barang.findAll", query = "SELECT b FROM Barang b"),
    @NamedQuery(name = "Barang.findByIdBarang", query = "SELECT b FROM Barang b WHERE b.idBarang = :idBarang"),
    @NamedQuery(name = "Barang.findByNama", query = "SELECT b FROM Barang b WHERE b.nama = :nama"),
    @NamedQuery(name = "Barang.findBySatuan", query = "SELECT b FROM Barang b WHERE b.satuan = :satuan"),
    @NamedQuery(name = "Barang.findByHargabeli", query = "SELECT b FROM Barang b WHERE b.hargabeli = :hargabeli"),
    @NamedQuery(name = "Barang.findByHargajual", query = "SELECT b FROM Barang b WHERE b.hargajual = :hargajual"),
    @NamedQuery(name = "Barang.findByStock", query = "SELECT b FROM Barang b WHERE b.stock = :stock")})
public class Barang implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "Id_Barang")
    private String idBarang;
    @Column(name = "Nama")
    private String nama;
    @Column(name = "Satuan")
    private String satuan;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "Harga_beli")
    private BigDecimal hargabeli;
    @Column(name = "Harga_jual")
    private BigDecimal hargajual;
    @Column(name = "Stock")
    private Integer stock;
    @JoinColumn(name = "Id_Katagori", referencedColumnName = "Id_Katagori")
    @ManyToOne
    private Katagori idKatagori;

    public Barang() {
    }

    public Barang(String idBarang) {
        this.idBarang = idBarang;
    }

    public String getIdBarang() {
        return idBarang;
    }

    public void setIdBarang(String idBarang) {
        this.idBarang = idBarang;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getSatuan() {
        return satuan;
    }

    public void setSatuan(String satuan) {
        this.satuan = satuan;
    }

    public BigDecimal getHargabeli() {
        return hargabeli;
    }

    public void setHargabeli(BigDecimal hargabeli) {
        this.hargabeli = hargabeli;
    }

    public BigDecimal getHargajual() {
        return hargajual;
    }

    public void setHargajual(BigDecimal hargajual) {
        this.hargajual = hargajual;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public Katagori getIdKatagori() {
        return idKatagori;
    }

    public void setIdKatagori(Katagori idKatagori) {
        this.idKatagori = idKatagori;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idBarang != null ? idBarang.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Barang)) {
            return false;
        }
        Barang other = (Barang) object;
        if ((this.idBarang == null && other.idBarang != null) || (this.idBarang != null && !this.idBarang.equals(other.idBarang))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.example.Springboot_penjualan.Barang[ idBarang=" + idBarang + " ]";
    }
    
}
