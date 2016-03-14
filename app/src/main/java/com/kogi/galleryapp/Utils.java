package com.kogi.galleryapp;

import java.io.PrintWriter;
import java.io.StringWriter;

public class Utils {

    public static String getStackTrace(Exception e) {
        StringWriter errors = new StringWriter();
        PrintWriter writer = new PrintWriter(errors);
        e.printStackTrace(writer);
        return errors.toString().replaceAll("\t", "").replaceAll("\n", " | ").replaceAll("\r", "").trim();
    }
}
