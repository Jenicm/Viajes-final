package com.viajesFinal.entity;
 
import java.io.Serializable;
 
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
 
@Entity
@Table(name = "Order_Details")
public class ReservaDetail implements Serializable {
 
    private static final long serialVersionUID = 7550745928843183535L;
 
    private String id;
    private Reserva order;
 
    private Destino destino;
    private int cantidad;
    private double precio;
    private double amount;
 
    @Id
    @Column(name = "ID", length = 50, nullable = false)
    public String getId() {
        return id;
    }
 
    public void setId(String id) {
        this.id = id;
    }
 
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ORDER_ID", nullable = false, //
    foreignKey = @ForeignKey(name = "ORDER_DETAIL_ORD_FK") )
    public Reserva getOrder() {
        return order;
    }
 
    public void setOrder(Reserva order) {
        this.order = order;
    }
 
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "destino_ID", nullable = false, //
    foreignKey = @ForeignKey(name = "ORDER_DETAIL_PROD_FK") )
    public Destino getDestino() {
        return destino;
    }
 
    public void setDestino(Destino destino) {
        this.destino = destino;
    }
 
    @Column(name = "cantidad", nullable = false)
    public int getCantidad() {
        return cantidad;
    }
 
    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }
 
    @Column(name = "precio", nullable = false)
    public double getPrecio() {
        return precio;
    }
 
    public void setPrecio(double precio) {
        this.precio = precio;
    }
 
    @Column(name = "Amount", nullable = false)
    public double getAmount() {
        return amount;
    }
 
    public void setAmount(double amount) {
        this.amount = amount;
    }
    
}