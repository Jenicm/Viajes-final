package com.viajesFinal.dao;
 
import java.util.List;

import com.viajesFinal.model.CarroInfo;
import com.viajesFinal.model.ReservaDetailInfo;
import com.viajesFinal.model.ReservaInfo;
import com.viajesFinal.model.PaginationResult;
 
public interface ReservaDAO {
 
    public void saveOrder(CarroInfo cartInfo);
 
    public PaginationResult<ReservaInfo> listOrderInfo(int page,
            int maxResult, int maxNavigationPage);
    
    public ReservaInfo getOrderInfo(String orderId);
    
    public List<ReservaDetailInfo> listOrderDetailInfos(String orderId);
 
}