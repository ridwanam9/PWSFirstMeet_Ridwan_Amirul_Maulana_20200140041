/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.Springboot_penjualan;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author DELL
 */
@Entity
@Table(name = "penjualan")
@NamedQueries({
    @NamedQuery(name = "Penjualan.findAll", query = "SELECT p FROM Penjualan p"),
    @NamedQuery(name = "Penjualan.findByIdPenjualan", query = "SELECT p FROM Penjualan p WHERE p.idPenjualan = :idPenjualan"),
    @NamedQuery(name = "Penjualan.findByTanggal", query = "SELECT p FROM Penjualan p WHERE p.tanggal = :tanggal"),
    @NamedQuery(name = "Penjualan.findByTotal", query = "SELECT p FROM Penjualan p WHERE p.total = :total"),
    @NamedQuery(name = "Penjualan.findByDibayar", query = "SELECT p FROM Penjualan p WHERE p.dibayar = :dibayar"),
    @NamedQuery(name = "Penjualan.findByKembali", query = "SELECT p FROM Penjualan p WHERE p.kembali = :kembali")})
public class Penjualan implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "Id_Penjualan")
    private String idPenjualan;
    @Column(name = "Tanggal")
    @Temporal(TemporalType.TIMESTAMP)
    private Date tanggal;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "Total")
    private BigDecimal total;
    @Column(name = "Dibayar")
    private BigDecimal dibayar;
    @Column(name = "Kembali")
    private BigDecimal kembali;
    @JoinColumn(name = "Id_Pelanggan", referencedColumnName = "Id_Pelanggan")
    @ManyToOne
    private Pelanggan idPelanggan;

    public Penjualan() {
    }

    public Penjualan(String idPenjualan) {
        this.idPenjualan = idPenjualan;
    }

    public String getIdPenjualan() {
        return idPenjualan;
    }

    public void setIdPenjualan(String idPenjualan) {
        this.idPenjualan = idPenjualan;
    }

    public Date getTanggal() {
        return tanggal;
    }

    public void setTanggal(Date tanggal) {
        this.tanggal = tanggal;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public BigDecimal getDibayar() {
        return dibayar;
    }

    public void setDibayar(BigDecimal dibayar) {
        this.dibayar = dibayar;
    }

    public BigDecimal getKembali() {
        return kembali;
    }

    public void setKembali(BigDecimal kembali) {
        this.kembali = kembali;
    }

    public Pelanggan getIdPelanggan() {
        return idPelanggan;
    }

    public void setIdPelanggan(Pelanggan idPelanggan) {
        this.idPelanggan = idPelanggan;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idPenjualan != null ? idPenjualan.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Penjualan)) {
            return false;
        }
        Penjualan other = (Penjualan) object;
        if ((this.idPenjualan == null && other.idPenjualan != null) || (this.idPenjualan != null && !this.idPenjualan.equals(other.idPenjualan))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.example.Springboot_penjualan.Penjualan[ idPenjualan=" + idPenjualan + " ]";
    }
    
}
