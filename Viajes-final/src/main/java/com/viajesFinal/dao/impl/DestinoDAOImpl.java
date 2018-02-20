package com.viajesFinal.dao.impl;
 
import java.util.Date;
 
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.viajesFinal.dao.DestinoDAO;
import com.viajesFinal.entity.Destino;
import com.viajesFinal.model.PaginationResult;
import com.viajesFinal.model.DestinoInfo;
 
// Transactional for Hibernate
@Transactional
public class DestinoDAOImpl implements DestinoDAO {
 
    @Autowired
    private SessionFactory sessionFactory;
 
    public Destino findDestino(String code) {
        Session session = sessionFactory.getCurrentSession();
        Criteria crit = session.createCriteria(Destino.class);
        crit.add(Restrictions.eq("code", code));
        return (Destino) crit.uniqueResult();
    }
 
    public DestinoInfo findDestinoInfo(String code) {
        Destino destino = this.findDestino(code);
        if (destino == null) {
            return null;
        }
        return new DestinoInfo(destino.getCode(), destino.getNombre(), destino.getPrecio());
    }
 
    public void save(DestinoInfo destinoInfo) {
        String code = destinoInfo.getCode();
 
        Destino destino = null;
 
        boolean isNew = false;
        if (code != null) {
            destino = this.findDestino(code);
        }
        if (destino == null) {
            isNew = true;
            destino = new Destino();
            destino.setCreateDate(new Date());
        }
        destino.setCode(code);
        destino.setNombre(destinoInfo.getNombre());
        destino.setPrecio(destinoInfo.getPrecio());
 
        if (destinoInfo.getFileData() != null) {
            byte[] foto = destinoInfo.getFileData().getBytes();
            if (foto != null && foto.length > 0) {
                destino.setFoto(foto);
            }
        }
        if (isNew) {
            this.sessionFactory.getCurrentSession().persist(destino);
        }
        // If error in DB, Exceptions will be thrown out immediately
        this.sessionFactory.getCurrentSession().flush();
    }
 
    public PaginationResult<DestinoInfo> queryDestinos(int page, int maxResult, int maxNavigationPage,
            String likeNombre) {
        String sql = "Select new " + DestinoInfo.class.getName() //
                + "(p.code, p.nombre, p.precio) " + " from "//
                + Destino.class.getName() + " p ";
        if (likeNombre != null && likeNombre.length() > 0) {
            sql += " Where lower(p.nombre) like :likeNombre ";
        }
        sql += " order by p.createDate desc ";
        //
        Session session = sessionFactory.getCurrentSession();
 
        Query query = session.createQuery(sql);
        if (likeNombre != null && likeNombre.length() > 0) {
            query.setParameter("likeNombre", "%" + likeNombre.toLowerCase() + "%");
        }
        return new PaginationResult<DestinoInfo>(query, page, maxResult, maxNavigationPage);
    }
 
    public PaginationResult<DestinoInfo> queryDestinos(int page, int maxResult, int maxNavigationPage) {
        return queryDestinos(page, maxResult, maxNavigationPage, null);
    }
    
}