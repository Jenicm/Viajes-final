package com.viajesFinal.views;

import java.text.DateFormat;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.itextpdf.text.Document;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.viajesFinal.model.CarroInfo;
import com.viajesFinal.model.CarroLineInfo;
import com.viajesFinal.util.Utils;

public class ItextPdfView extends AbstractITextPdfView {

    private static final DateFormat DATE_FORMAT = DateFormat.getDateInstance(DateFormat.SHORT);

    @Override
    protected void buildPdfDocument(Map<String, Object> model,
                                    Document document, PdfWriter writer, HttpServletRequest request,
                                    HttpServletResponse response) throws Exception {

        @SuppressWarnings("unchecked")
//        List<CartInfo> cartinfos = (List<CartInfo>) model.get("cartinfos");
        CarroInfo cartinfo = Utils.getCartInSession(request);
        PdfPTable table = new PdfPTable(4);
        table.setWidths(new int[]{10, 40, 40, 10});

        table.addCell("Code");
        table.addCell("Nombre de usuario");
        table.addCell("Nombre de destinoo");
        table.addCell("Precio");
        
        for (CarroLineInfo cInfo : cartinfo.getCartLines()){
        	table.addCell(String.valueOf(cInfo.getDestinoInfo().getCode()));
    		table.addCell(String.valueOf(cartinfo.getCustomerInfo().getNombre()));
    		table.addCell(String.valueOf(cInfo.getDestinoInfo().getNombre()));
    		table.addCell(String.valueOf(cInfo.getDestinoInfo().getPrecio()));
        }

        document.add(table);
    }

}