package com.viajesFinal.dao;
 
import com.viajesFinal.entity.Destino;
import com.viajesFinal.model.PaginationResult;
import com.viajesFinal.model.DestinoInfo;
 
public interface DestinoDAO {
 
    
    
    public Destino findDestino(String code);
    
    public DestinoInfo findDestinoInfo(String code) ;
  
    
    public PaginationResult<DestinoInfo> queryDestinos(int page,
                       int maxResult, int maxNavigationPage  );
    
    public PaginationResult<DestinoInfo> queryDestinos(int page, int maxResult,
                       int maxNavigationPage, String likeNombre);
 
    public void save(DestinoInfo DestinoInfo);
    
}