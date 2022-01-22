package com.capstone.capstone_group.isklinika;

public class ClassPastIllness {

    private String disease, endDate, notes, startDate, status, treatment, key ;

    public ClassPastIllness(String disease, String endDate, String notes, String startDate, String status, String treatment) {
        this.disease = disease;
        this.endDate = endDate;
        this.notes = notes;
        this.startDate = startDate;
        this.status = status;
        this.treatment = treatment;
    }

    public ClassPastIllness() {
    }

    public String getDisease() {
        return disease;
    }

    public void setDisease(String disease) {
        this.disease = disease;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTreatment() {
        return treatment;
    }

    public void setTreatment(String treatment) {
        this.treatment = treatment;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    @Override
    public String toString() {
        return "ClassPastIllness{" +
                "disease='" + disease + '\'' +
                ", endDate='" + endDate + '\'' +
                ", notes='" + notes + '\'' +
                ", startDate='" + startDate + '\'' +
                ", status='" + status + '\'' +
                ", treatment='" + treatment + '\'' +
                '}';
    }
}
