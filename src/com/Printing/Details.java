package com.Printing;

import java.io.File;
import java.util.HashMap;

public class Details extends Print {

    public static final String STUDENT_DETAILS = new File("src\\Print Details\\Student Details.jrxml").getAbsolutePath();
    public static final String STUDENT_DETAILS_XLSX = new File("src\\Print Details\\Student Details_xlsx.jrxml").getAbsolutePath();

    public Details(String jrxml, HashMap<String, Object> map, com.Printing.Extention extention) {
        super(jrxml, map, extention);
    }
}
