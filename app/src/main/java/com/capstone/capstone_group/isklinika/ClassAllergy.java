package com.capstone.capstone_group.isklinika;

public class ClassAllergy {

    private String allergy, type, diagnosisDate, lastOccurrence ;
    private String key ;

    public ClassAllergy() {
    }

    public ClassAllergy(String allergy, String type, String diagnosisDate, String lastOccurrence) {
        this.allergy = allergy;
        this.type = type;
        this.diagnosisDate = diagnosisDate;
        this.lastOccurrence = lastOccurrence;
    }

    public String getAllergy() {
        return allergy;
    }

    public void setAllergy(String allergy) {
        this.allergy = allergy;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDiagnosisDate() {
        return diagnosisDate;
    }

    public void setDiagnosisDate(String diagnosisDate) {
        this.diagnosisDate = diagnosisDate;
    }

    public String getLastOccurrence() {
        return lastOccurrence;
    }

    public void setLastOccurrence(String lastOccurrence) {
        this.lastOccurrence = lastOccurrence;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
