package com.viajesFinal.model;
 
public class ReservaDetailInfo {
    private String id;
 
    private String destinoCode;
    private String destinoNombre;
 
    private int cantidad;
    private double precio;
    private double amount;
 
    public ReservaDetailInfo() {
 
    }
 
    // Using for Hibernate Query.
    // Hibernate Query.
    public ReservaDetailInfo(String id, String destinoCode, //
            String destinoNombre, int cantidad, double precio, double amount) {
        this.id = id;
        this.destinoCode = destinoCode;
        this.destinoNombre = destinoNombre;
        this.cantidad = cantidad;
        this.precio = precio;
        this.amount = amount;
    }
 
    public String getId() {
        return id;
    }
 
    public void setId(String id) {
        this.id = id;
    }
 
    public String getDestinoCode() {
        return destinoCode;
    }
 
    public void setDestinoCode(String destinoCode) {
        this.destinoCode = destinoCode;
    }
 
    public String getDestinoNombre() {
        return destinoNombre;
    }
 
    public void setProductNombre(String destinoNombre) {
        this.destinoNombre = destinoNombre;
    }
 
    public int getCantidad() {
        return cantidad;
    }
 
    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }
 
    public double getPrecio() {
        return precio;
    }
 
    public void setPrecio(double precio) {
        this.precio = precio;
    }
 
    public double getAmount() {
        return amount;
    }
 
    public void setAmount(double amount) {
        this.amount = amount;
    }
}