/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.Springboot_penjualan;

import java.io.Serializable;
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
@Table(name = "retur_pembelian")
@NamedQueries({
    @NamedQuery(name = "ReturPembelian.findAll", query = "SELECT r FROM ReturPembelian r"),
    @NamedQuery(name = "ReturPembelian.findByIdReturPembelian", query = "SELECT r FROM ReturPembelian r WHERE r.idReturPembelian = :idReturPembelian"),
    @NamedQuery(name = "ReturPembelian.findByTanggalRetur", query = "SELECT r FROM ReturPembelian r WHERE r.tanggalRetur = :tanggalRetur")})
public class ReturPembelian implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "Id_Retur_Pembelian")
    private String idReturPembelian;
    @Column(name = "Tanggal_Retur")
    @Temporal(TemporalType.TIMESTAMP)
    private Date tanggalRetur;
    @JoinColumn(name = "Id_Pembelian", referencedColumnName = "Id_Pembelian")
    @ManyToOne
    private Pembelian idPembelian;

    public ReturPembelian() {
    }

    public ReturPembelian(String idReturPembelian) {
        this.idReturPembelian = idReturPembelian;
    }

    public String getIdReturPembelian() {
        return idReturPembelian;
    }

    public void setIdReturPembelian(String idReturPembelian) {
        this.idReturPembelian = idReturPembelian;
    }

    public Date getTanggalRetur() {
        return tanggalRetur;
    }

    public void setTanggalRetur(Date tanggalRetur) {
        this.tanggalRetur = tanggalRetur;
    }

    public Pembelian getIdPembelian() {
        return idPembelian;
    }

    public void setIdPembelian(Pembelian idPembelian) {
        this.idPembelian = idPembelian;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idReturPembelian != null ? idReturPembelian.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ReturPembelian)) {
            return false;
        }
        ReturPembelian other = (ReturPembelian) object;
        if ((this.idReturPembelian == null && other.idReturPembelian != null) || (this.idReturPembelian != null && !this.idReturPembelian.equals(other.idReturPembelian))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.example.Springboot_penjualan.ReturPembelian[ idReturPembelian=" + idReturPembelian + " ]";
    }
    
}
