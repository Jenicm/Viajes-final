package com.viajesFinal.validator;
 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.viajesFinal.dao.DestinoDAO;
import com.viajesFinal.entity.Destino;
import com.viajesFinal.model.DestinoInfo;
 
// @Component: As a Bean.
@Component
public class DestinoInfoValidator implements Validator {
 
    @Autowired
    private DestinoDAO destinoDAO;
 
    // This Validator support ProductInfo class.
    public boolean supports(Class<?> clazz) {
        return clazz == DestinoInfo.class;
    }
 
    public void validate(Object target, Errors errors) {
        DestinoInfo destinoInfo = (DestinoInfo) target;
 
        // Check the fields of ProductInfo class.
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "code", "NotEmpty.destinoForm.code");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "nombre", "NotEmpty.destinoForm.nombre");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "precio", "NotEmpty.destinoForm.precio");
 
        String code = destinoInfo.getCode();
        if (code != null && code.length() > 0) {
            if (code.matches("\\s+")) {
                errors.rejectValue("code", "Pattern.destinoForm.code");
            } else if(destinoInfo.isNewDestino()) {
                Destino destino = destinoDAO.findDestino(code);
                if (destino != null) {
                    errors.rejectValue("code", "Duplicate.destinoForm.code");
                }
            }
        }
    }
 
}