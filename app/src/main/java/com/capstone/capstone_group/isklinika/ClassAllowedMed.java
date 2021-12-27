package com.capstone.capstone_group.isklinika;

import java.util.ArrayList;

public class ClassAllowedMed {

    private ArrayList<ClassMedicineAllowed> allowedMedicines ;
    private String lastUpdated ;

    public ClassAllowedMed() {
        this.allowedMedicines = new ArrayList<>();
    }

    public void setAllowedMedicines(ArrayList<ClassMedicineAllowed> allowedMedicines) {
        this.allowedMedicines = allowedMedicines;
    }

    public boolean getIsAllowed(int i) {
        return allowedMedicines.get(i).getIsAllowed();
    }

    public String getMedicineName(int i) {
        return allowedMedicines.get(i).getMedicineName();
    }

    public String getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(String lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public int getMedAllowedSize(){
        return allowedMedicines.size() ;
    }
}
