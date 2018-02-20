package com.viajesFinal.dao.impl;
 
import java.util.Date;
import java.util.List;
import java.util.UUID;
 
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.viajesFinal.dao.ReservaDAO;
import com.viajesFinal.dao.DestinoDAO;
import com.viajesFinal.entity.Reserva;
import com.viajesFinal.entity.ReservaDetail;
import com.viajesFinal.entity.Destino;
import com.viajesFinal.model.CarroInfo;
import com.viajesFinal.model.CarroLineInfo;
import com.viajesFinal.model.ClienteInfo;
import com.viajesFinal.model.ReservaDetailInfo;
import com.viajesFinal.model.ReservaInfo;
import com.viajesFinal.model.PaginationResult;
 
//Transactional for Hibernate
@Transactional
public class ReservaDAOImpl implements ReservaDAO {
 
    @Autowired
    private SessionFactory sessionFactory;
 
    @Autowired
    private DestinoDAO destinoDAO;
 
    private int getMaxOrderNum() {
        String sql = "Select max(o.orderNum) from " + Reserva.class.getName() + " o ";
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery(sql);
        Integer value = (Integer) query.uniqueResult();
        if (value == null) {
            return 0;
        }
        return value;
    }
 
    public void saveOrder(CarroInfo cartInfo) {
        Session session = sessionFactory.getCurrentSession();
 
        int orderNum = this.getMaxOrderNum() + 1;
        Reserva order = new Reserva();
 
        order.setId(UUID.randomUUID().toString());
        order.setOrderNum(orderNum);
        order.setOrderDate(new Date());
        order.setAmount(cartInfo.getAmountTotal());
 
        ClienteInfo customerInfo = cartInfo.getCustomerInfo();
        order.setCustomerNombre(customerInfo.getNombre());
        order.setCustomerEmail(customerInfo.getEmail());
        order.setCustomerPhone(customerInfo.getPhone());
        order.setCustomerAddress(customerInfo.getAddress());
 
        session.persist(order);
 
        List<CarroLineInfo> lines = cartInfo.getCartLines();
 
        for (CarroLineInfo line : lines) {
            ReservaDetail detail = new ReservaDetail();
            detail.setId(UUID.randomUUID().toString());
            detail.setOrder(order);
            detail.setAmount(line.getAmount());
            detail.setPrecio(line.getDestinoInfo().getPrecio());
            detail.setCantidad(line.getCantidad());
 
            String code = line.getDestinoInfo().getCode();
            Destino destino = this.destinoDAO.findDestino(code);
            detail.setDestino(destino);
 
            session.persist(detail);
        }
 
        // Set OrderNum for report.
        // Set OrderNum 
        cartInfo.setOrderNum(orderNum);
    }
 
    // @page = 1, 2, ...
    public PaginationResult<ReservaInfo> listOrderInfo(int page, int maxResult, int maxNavigationPage) {
        String sql = "Select new " + ReservaInfo.class.getName()//
                + "(ord.id, ord.orderDate, ord.orderNum, ord.amount, "
                + " ord.customerNombre, ord.customerAddress, ord.customerEmail, ord.customerPhone) " + " from "
                + Reserva.class.getName() + " ord "//
                + " order by ord.orderNum desc";
        Session session = this.sessionFactory.getCurrentSession();
 
        Query query = session.createQuery(sql);
 
        return new PaginationResult<ReservaInfo>(query, page, maxResult, maxNavigationPage);
    }
 
    public Reserva findOrder(String orderId) {
        Session session = sessionFactory.getCurrentSession();
        Criteria crit = session.createCriteria(Reserva.class);
        crit.add(Restrictions.eq("id", orderId));
        return (Reserva) crit.uniqueResult();
    }
 
    public ReservaInfo getOrderInfo(String orderId) {
        Reserva order = this.findOrder(orderId);
        if (order == null) {
            return null;
        }
        return new ReservaInfo(order.getId(), order.getOrderDate(), //
                order.getOrderNum(), order.getAmount(), order.getCustomerNombre(), //
                order.getCustomerAddress(), order.getCustomerEmail(), order.getCustomerPhone());
    }
 
    public List<ReservaDetailInfo> listOrderDetailInfos(String orderId) {
        String sql = "Select new " + ReservaDetailInfo.class.getName() //
                + "(d.id, d.destino.code, d.destino.nombre , d.cantidad,d.precio,d.amount) "//
                + " from " + ReservaDetail.class.getName() + " d "//
                + " where d.order.id = :orderId ";
 
        Session session = this.sessionFactory.getCurrentSession();
 
        Query query = session.createQuery(sql);
        query.setParameter("orderId", orderId);
 
        return query.list();
    }
 
}