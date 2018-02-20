package com.viajesFinal.model;
 
import java.util.ArrayList;
import java.util.List;
 
public class CarroInfo {
 
    private int orderNum;
 
    private ClienteInfo customerInfo;
 
    private final List<CarroLineInfo> cartLines = new ArrayList<CarroLineInfo>();
 
    public CarroInfo() {
 
    }
 
    public int getOrderNum() {
        return orderNum;
    }
 
    public void setOrderNum(int orderNum) {
        this.orderNum = orderNum;
    }
 
    public ClienteInfo getCustomerInfo() {
        return customerInfo;
    }
 
    public void setCustomerInfo(ClienteInfo customerInfo) {
        this.customerInfo = customerInfo;
    }
 
    public List<CarroLineInfo> getCartLines() {
        return this.cartLines;
    }
 
    private CarroLineInfo findLineByCode(String code) {
        for (CarroLineInfo line : this.cartLines) {
            if (line.getDestinoInfo().getCode().equals(code)) {
                return line;
            }
        }
        return null;
    }
 
    public void addDestino(DestinoInfo destinoInfo, int cantidad) {
        CarroLineInfo line = this.findLineByCode(destinoInfo.getCode());
 
        if (line == null) {
            line = new CarroLineInfo();
            line.setCantidad(0);
            line.setDestinoInfo(destinoInfo);
            this.cartLines.add(line);
        }
        int newCantidad = line.getCantidad() + cantidad;
        if (newCantidad <= 0) {
            this.cartLines.remove(line);
        } else {
            line.setCantidad(newCantidad);
        }
    }
 
    public void validate() {
 
    }
 
    public void updateDestino(String code, int cantidad) {
        CarroLineInfo line = this.findLineByCode(code);
 
        if (line != null) {
            if (cantidad <= 0) {
                this.cartLines.remove(line);
            } else {
                line.setCantidad(cantidad);
            }
        }
    }
 
    public void removeDestino(DestinoInfo destinoInfo) {
        CarroLineInfo line = this.findLineByCode(destinoInfo.getCode());
        if (line != null) {
            this.cartLines.remove(line);
        }
    }
 
    public boolean isEmpty() {
        return this.cartLines.isEmpty();
    }
 
    public boolean isValidCustomer() {
        return this.customerInfo != null && this.customerInfo.isValid();
    }
 
    public int getCantidadTotal() {
        int cantidad = 0;
        for (CarroLineInfo line : this.cartLines) {
            cantidad += line.getCantidad();
        }
        return cantidad;
    }
 
    public double getAmountTotal() {
        double total = 0;
        for (CarroLineInfo line : this.cartLines) {
            total += line.getAmount();
        }
        return total;
    }
 
    public void updateCantidad(CarroInfo cartForm) {
        if (cartForm != null) {
            List<CarroLineInfo> lines = cartForm.getCartLines();
            for (CarroLineInfo line : lines) {
                this.updateDestino(line.getDestinoInfo().getCode(), line.getCantidad());
            }
        }
 
    }
 
}