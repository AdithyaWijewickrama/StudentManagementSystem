package com.HealthReport;

public enum Types {
    Vaccines("Vaccines",0),
    Desseases("Desseases",0),
    LabReports("Lab Reports",1);
    
    private final String NAME;
    private final int INDEX;

    private Types(String path, int idx) {
        NAME = path;
        INDEX = idx;
    }

    public int getIndex() {
        return INDEX;
    }

    public String getName() {
        return NAME;
    }
}
