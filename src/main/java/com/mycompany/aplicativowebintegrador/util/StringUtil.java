package com.mycompany.aplicativowebintegrador.util;

import org.apache.commons.lang3.StringUtils;

public class StringUtil {
    public static String limpiarTexto(String texto) {
        return StringUtils.trimToEmpty(texto);
    }

    public static boolean esNumerico(String texto) {
        return StringUtils.isNumeric(texto);
    }
}
