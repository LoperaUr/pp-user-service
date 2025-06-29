package com.pragma.userservice.infraestructure.util;

public class UserValidator {

    public static boolean validateCellphone(String cellphone) {
        // Verifica que el celular no sea nulo, tenga al menos 3 caracteres y solo contenga dígitos o el símbolo +
        return cellphone != null && cellphone.matches("^\\+?[0-9]{2,}$");
    }

    public static boolean validateDocument(String document) {
        // Verifica que el documento no sea nulo, tenga al menos 3 caracteres y solo contenga dígitos
        return document != null && document.matches("^[0-9]{3,}$");
    }
}
