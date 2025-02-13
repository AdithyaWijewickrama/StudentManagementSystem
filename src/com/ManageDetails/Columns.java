package com.ManageDetails;

public enum Columns {
    ID("Admission No.", 0),
    NAME("Name", 1),
    GRADE("Grade", 2),
    SUBGRADE("Sub grade", 3),
    MEDIUM("Medium", 4),
    DATEOFBIRTH("Date of birth", 5),
    GENDER("Gender", 6),
    TELEPHONE("Telephone", 7),
    ADDRESS("Home address", 8),
    GUARDIAN("Guardian", 9),
    EMAIL("Email", 10);
    private final String COLNAME;
    private final int INDEX;

    private Columns(String name, int index) {
        COLNAME = name;
        INDEX = index;
    }

    public String getName() {
        return COLNAME;
    }

    public int getIndex() {
        return INDEX;
    }
    
    

}
