package com.viajesFinal.model;
 
public class CarroLineInfo {
 
    private DestinoInfo destinoInfo;
    private int cantidad;
 
    public CarroLineInfo() {
        this.cantidad = 0;
    }
 
    public DestinoInfo getDestinoInfo() {
        return destinoInfo;
    }
 
    public void setDestinoInfo(DestinoInfo destinoInfo) {
        this.destinoInfo = destinoInfo;
    }
 
    public int getCantidad() {
        return cantidad;
    }
 
    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }
 
    public double getAmount() {
        return this.destinoInfo.getPrecio() * this.cantidad;
    }
    
}