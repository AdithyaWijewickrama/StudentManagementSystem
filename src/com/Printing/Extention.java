package com.Printing;

public enum Extention {
    PDF("pdf"),
    XLSX("xlsx"),
    HTML("html"),
    XML("xml"),
    DOCX("docx"),
    PPTX("pptx"),
    RTF("rtf"),
    TXT("txt");
    private final String EXT;

    private Extention(String ext) {
        EXT = ext;
    }

    public String getEXT() {
        return EXT;
    }

}
