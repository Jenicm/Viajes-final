package com.viajesFinal.model;
 
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.viajesFinal.entity.Destino;
 
public class DestinoInfo {
    private String code;
    private String nombre;
    private double precio;
 
    private boolean newDestino=false;
 
    // Upload file.
    private CommonsMultipartFile fileData;
 
    public DestinoInfo() {
    }
 
    public DestinoInfo(Destino destino) {
        this.code = destino.getCode();
        this.nombre = destino.getNombre();
        this.precio = destino.getPrecio();
    }
 

    public DestinoInfo(String code, String nombre, double precio) {
        this.code = code;
        this.nombre = nombre;
        this.precio = precio;
    }
 
    public String getCode() {
        return code;
    }
 
    public void setCode(String code) {
        this.code = code;
    }
 
    public String getNombre() {
        return nombre;
    }
 
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
 
    public double getPrecio() {
        return precio;
    }
 
    public void setPrecio(double precio) {
        this.precio = precio;
    }
 
    public CommonsMultipartFile getFileData() {
        return fileData;
    }
 
    public void setFileData(CommonsMultipartFile fileData) {
        this.fileData = fileData;
    }
 
    public boolean isNewDestino() {
        return newDestino;
    }
 
    public void setNewDestino(boolean newDestino) {
        this.newDestino = newDestino;
    }
 
}