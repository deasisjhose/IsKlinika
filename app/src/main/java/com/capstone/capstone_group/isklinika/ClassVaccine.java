package com.capstone.capstone_group.isklinika;

public class ClassVaccine {

    private String doses, expectedAge, notes, purpose, vaccineName ;

    public String getVaccineName() {
        return vaccineName;
    }

    public void setVaccineName(String vaccineName) {
        this.vaccineName = vaccineName;
    }

    public String getDoses() {
        return doses;
    }

    public void setDoses(String doses) {
        this.doses = doses;
    }

    public String getExpectedAge() {
        return expectedAge;
    }

    public void setExpectedAge(String expectedAge) {
        this.expectedAge = expectedAge;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getPurpose() {
        return purpose;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }

    @Override
    public String toString() {
        return vaccineName;
    }


}
