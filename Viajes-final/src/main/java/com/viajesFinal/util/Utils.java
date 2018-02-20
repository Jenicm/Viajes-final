package com.viajesFinal.util;
 
import javax.servlet.http.HttpServletRequest;

import com.viajesFinal.model.CarroInfo;
 
public class Utils {
 
    // Products in Cart, stored in Session.
    public static CarroInfo getCartInSession(HttpServletRequest request) {
 
        // Get Cart from Session.
        CarroInfo cartInfo = (CarroInfo) request.getSession().getAttribute("myCart");
        
        // If null, create it.
        if (cartInfo == null) {
            cartInfo = new CarroInfo();
            
            // And store to Session.
            request.getSession().setAttribute("myCart", cartInfo);
        }
 
        return cartInfo;
    }
 
    public static void removeCartInSession(HttpServletRequest request) {
        request.getSession().removeAttribute("myCart");
    }
 
    public static void storeLastOrderedCartInSession(HttpServletRequest request, CarroInfo cartInfo) {
        request.getSession().setAttribute("lastOrderedCart", cartInfo);
    }
    
    public static CarroInfo getLastOrderedCartInSession(HttpServletRequest request) {
        return (CarroInfo) request.getSession().getAttribute("lastOrderedCart");
    }
 
}