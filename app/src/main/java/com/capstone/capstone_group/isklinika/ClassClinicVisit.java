package com.capstone.capstone_group.isklinika;

public class ClassClinicVisit {

    private String bodyTemp, diagnosis, diastolicBP, notes,  nurseName, pulseRateStatus, respRateStatus, status, systolicBP, timeIn, timeOut,
                    treatment, visitDate, visitReason ;

    public ClassClinicVisit(String bodyTemp, String diagnosis, String diastolicBP, String notes, String nurseName, String pulseRateStatus,
                            String respRateStatus, String status, String systolicBP, String timeIn, String timeOut, String treatment,
                            String visitDate, String visitReason) {
        this.bodyTemp = bodyTemp;
        this.diagnosis = diagnosis;
        this.diastolicBP = diastolicBP;
        this.notes = notes;
        this.nurseName = nurseName;
        this.pulseRateStatus = pulseRateStatus;
        this.respRateStatus = respRateStatus;
        this.status = status;
        this.systolicBP = systolicBP;
        this.timeIn = timeIn;
        this.timeOut = timeOut;
        this.treatment = treatment;
        this.visitDate = visitDate;
        this.visitReason = visitReason;
    }

    public String getBodyTem() {
        return bodyTemp;
    }

    public void setBodyTem(String bodyTemp) {
        this.bodyTemp = bodyTemp;
    }

    public String getDiagnosis() {
        return diagnosis;
    }

    public void setDiagnosis(String diagnosis) {
        this.diagnosis = diagnosis;
    }

    public String getDiastolicBP() {
        return diastolicBP;
    }

    public void setDiastolicBP(String diastolicBP) {
        this.diastolicBP = diastolicBP;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getNurseName() {
        return nurseName;
    }

    public void setNurseName(String nurseName) {
        this.nurseName = nurseName;
    }

    public String getPulseRateStatus() {
        return pulseRateStatus;
    }

    public void setPulseRateStatus(String pulseRateStatus) {
        this.pulseRateStatus = pulseRateStatus;
    }

    public String getRespRateStatus() {
        return respRateStatus;
    }

    public void setRespRateStatus(String respRateStatus) {
        this.respRateStatus = respRateStatus;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSystolicBP() {
        return systolicBP;
    }

    public void setSystolicBP(String systolicBP) {
        this.systolicBP = systolicBP;
    }

    public String getTimeIn() {
        return timeIn;
    }

    public void setTimeIn(String timeIn) {
        this.timeIn = timeIn;
    }

    public String getTimeOut() {
        return timeOut;
    }

    public void setTimeOut(String timeOut) {
        this.timeOut = timeOut;
    }

    public String getTreatment() {
        return treatment;
    }

    public void setTreatment(String treatment) {
        this.treatment = treatment;
    }

    public String getVisitDate() {
        return visitDate;
    }

    public void setVisitDate(String visitDate) {
        this.visitDate = visitDate;
    }

    public String getVisitReason() {
        return visitReason;
    }

    public void setVisitReason(String visitReason) {
        this.visitReason = visitReason;
    }
}
