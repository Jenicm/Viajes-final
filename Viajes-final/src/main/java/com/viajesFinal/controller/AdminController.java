package com.viajesFinal.controller;
 
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.support.ByteArrayMultipartFileEditor;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.viajesFinal.dao.ReservaDAO;
import com.viajesFinal.dao.DestinoDAO;
import com.viajesFinal.model.ReservaDetailInfo;
import com.viajesFinal.model.ReservaInfo;
import com.viajesFinal.model.PaginationResult;
import com.viajesFinal.model.DestinoInfo;
import com.viajesFinal.validator.DestinoInfoValidator;
 
@Controller
// Enable Hibernate Transaction.
@Transactional
// Need to use RedirectAttributes
@EnableWebMvc
public class AdminController {
 
    @Autowired
    private ReservaDAO orderDAO;
 
    @Autowired
    private DestinoDAO destinoDAO;
 
    @Autowired
    private DestinoInfoValidator destinoInfoValidator;
 
    // Configurated In ApplicationContextConfig.
    @Autowired
    private ResourceBundleMessageSource messageSource;
 
    @InitBinder
    public void myInitBinder(WebDataBinder dataBinder) {
        Object target = dataBinder.getTarget();
        if (target == null) {
            return;
        }
        System.out.println("Target=" + target);
 
        if (target.getClass() == DestinoInfo.class) {
            dataBinder.setValidator(destinoInfoValidator);
            // For upload Image.
            dataBinder.registerCustomEditor(byte[].class, new ByteArrayMultipartFileEditor());
        }
    }
 
    // GET: Show Login Page
    @RequestMapping(value = { "/login" }, method = RequestMethod.GET)
    public String login(Model model) {
 
        return "login";
    }
 
    @RequestMapping(value = { "/accountInfo" }, method = RequestMethod.GET)
    public String accountInfo(Model model) {
 
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        System.out.println(userDetails.getPassword());
        System.out.println(userDetails.getUsername());
        System.out.println(userDetails.isEnabled());
 
        model.addAttribute("userDetails", userDetails);
        return "accountInfo";
    }
 
    @RequestMapping(value = { "/orderList" }, method = RequestMethod.GET)
    public String orderList(Model model, //
            @RequestParam(value = "page", defaultValue = "1") String pageStr) {
        int page = 1;
        try {
            page = Integer.parseInt(pageStr);
        } catch (Exception e) {
        }
        final int MAX_RESULT = 5;
        final int MAX_NAVIGATION_PAGE = 10;
 
        PaginationResult<ReservaInfo> paginationResult //
        = orderDAO.listOrderInfo(page, MAX_RESULT, MAX_NAVIGATION_PAGE);
 
        model.addAttribute("paginationResult", paginationResult);
        return "orderList";
    }
 
    // GET: Show product.
    @RequestMapping(value = { "/destino" }, method = RequestMethod.GET)
    public String destino(Model model, @RequestParam(value = "code", defaultValue = "") String code) {
        DestinoInfo destinoInfo = null;
 
        if (code != null && code.length() > 0) {
            destinoInfo = destinoDAO.findDestinoInfo(code);
        }
        if (destinoInfo == null) {
            destinoInfo = new DestinoInfo();
            destinoInfo.setNewDestino(true);
        }
        model.addAttribute("destinoForm", destinoInfo);
        return "destino";
    }
 
    // POST: Save destino
    @RequestMapping(value = { "/destino" }, method = RequestMethod.POST)
    // Avoid UnexpectedRollbackException (See more explanations)
    @Transactional(propagation = Propagation.NEVER)
    public String destinoSave(Model model, //
            @ModelAttribute("destinoForm") @Validated DestinoInfo destinoInfo, //
            BindingResult result, //
            final RedirectAttributes redirectAttributes) {
 
        if (result.hasErrors()) {
            return "destino";
        }
        try {
            destinoDAO.save(destinoInfo);
        } catch (Exception e) {
            // Need: Propagation.NEVER?
            String message = e.getMessage();
            model.addAttribute("message", message);
            // Show product form.
            return "destino";
 
        }
        return "redirect:/destinoList";
    }
 
    @RequestMapping(value = { "/order" }, method = RequestMethod.GET)
    public String orderView(Model model, @RequestParam("orderId") String orderId) {
        ReservaInfo orderInfo = null;
        if (orderId != null) {
            orderInfo = this.orderDAO.getOrderInfo(orderId);
        }
        if (orderInfo == null) {
            return "redirect:/orderList";
        }
        List<ReservaDetailInfo> details = this.orderDAO.listOrderDetailInfos(orderId);
        orderInfo.setDetails(details);
 
        model.addAttribute("orderInfo", orderInfo);
 
        return "order";
    }
    
}