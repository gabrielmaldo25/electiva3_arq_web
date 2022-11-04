package com.proyecto.electiva3.backend.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class GeneralUtils {
    public final static String DATE_FORMAT = "yyyy-MM-dd";

    /* Convertir string a fecha */
    public static String getStringToDate(LocalDate date) {
        try {
            return date.format(DateTimeFormatter.ofPattern(DATE_FORMAT));
        } catch (Exception e) {
            System.out.println("No se pudo convertir " + date + " a String: ");
            return "";
        }
    }

    /* Convertir fecha a string */
    public static LocalDate getDateToString(String date) {
        try {
            return LocalDate.parse(date, DateTimeFormatter.ofPattern(DATE_FORMAT));
        } catch (Exception e) {
            return null;
        }
    }
}
