/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.Springboot_penjualan;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author DELL
 */
@Entity
@Table(name = "pembelian")
@NamedQueries({
    @NamedQuery(name = "Pembelian.findAll", query = "SELECT p FROM Pembelian p"),
    @NamedQuery(name = "Pembelian.findByIdPembelian", query = "SELECT p FROM Pembelian p WHERE p.idPembelian = :idPembelian"),
    @NamedQuery(name = "Pembelian.findByTanggal", query = "SELECT p FROM Pembelian p WHERE p.tanggal = :tanggal"),
    @NamedQuery(name = "Pembelian.findByTotal", query = "SELECT p FROM Pembelian p WHERE p.total = :total")})
public class Pembelian implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "Id_Pembelian")
    private String idPembelian;
    @Column(name = "Tanggal")
    @Temporal(TemporalType.TIMESTAMP)
    private Date tanggal;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "Total")
    private BigDecimal total;
    @OneToMany(mappedBy = "idPembelian")
    private Collection<ReturPembelian> returPembelianCollection;
    @JoinColumn(name = "Id_Suplier", referencedColumnName = "Id_suplier")
    @ManyToOne
    private Supplier idSuplier;

    public Pembelian() {
    }

    public Pembelian(String idPembelian) {
        this.idPembelian = idPembelian;
    }

    public String getIdPembelian() {
        return idPembelian;
    }

    public void setIdPembelian(String idPembelian) {
        this.idPembelian = idPembelian;
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

    public Collection<ReturPembelian> getReturPembelianCollection() {
        return returPembelianCollection;
    }

    public void setReturPembelianCollection(Collection<ReturPembelian> returPembelianCollection) {
        this.returPembelianCollection = returPembelianCollection;
    }

    public Supplier getIdSuplier() {
        return idSuplier;
    }

    public void setIdSuplier(Supplier idSuplier) {
        this.idSuplier = idSuplier;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idPembelian != null ? idPembelian.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Pembelian)) {
            return false;
        }
        Pembelian other = (Pembelian) object;
        if ((this.idPembelian == null && other.idPembelian != null) || (this.idPembelian != null && !this.idPembelian.equals(other.idPembelian))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.example.Springboot_penjualan.Pembelian[ idPembelian=" + idPembelian + " ]";
    }
    
}
