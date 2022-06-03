package com.capstone.capstone_group.isklinika;

public class ClassClinicVisit {

    private String bodyTemp, bodyTempStatus, communicable, diagnosis, diagnosisAssigned, diagnosisSentence,
            diastolicBP, diastolicStatus, grade, height, heightStatus, id, impression, injury, notes, nurseKey,
            nurseName, prescribedBy, pulseRateStatus, respRateStatus, section, status, studentName,
            systolicBP, systolicStatus, timeIn, timeOut, treatment, visitDate, visitReason, visitType, weight, weightStatus ;

    public ClassClinicVisit() {
    }

    public ClassClinicVisit(String bodyTemp, String bodyTempStatus, String communicable, String diagnosis, String diagnosisAssigned, String diagnosisSentence,
                            String diastolicBP, String diastolicStatus, String grade, String height, String heightStatus, String id, String impression, String injury,
                            String notes, String nurseKey, String nurseName, String prescribedBy, String pulseRateStatus, String respRateStatus,
                            String section, String status, String studentName, String systolicBP, String systolicStatus, String timeIn, String timeOut,
                            String treatment, String visitDate, String visitReason, String visitType, String weight, String weightStatus) {
        this.bodyTemp = bodyTemp;
        this.bodyTempStatus = bodyTempStatus;
        this.communicable = communicable;
        this.diagnosis = diagnosis;
        this.diagnosisAssigned = diagnosisAssigned;
        this.diagnosisSentence = diagnosisSentence;
        this.diastolicBP = diastolicBP;
        this.diastolicStatus = diastolicStatus;
        this.grade = grade;
        this.height = height;
        this.heightStatus = heightStatus;
        this.id = id;
        this.impression = impression;
        this.injury = injury;
        this.notes = notes;
        this.nurseKey = nurseKey;
        this.nurseName = nurseName;
        this.prescribedBy = prescribedBy;
        this.pulseRateStatus = pulseRateStatus;
        this.respRateStatus = respRateStatus;
        this.section = section;
        this.status = status;
        this.studentName = studentName;
        this.systolicBP = systolicBP;
        this.systolicStatus = systolicStatus;
        this.timeIn = timeIn;
        this.timeOut = timeOut;
        this.treatment = treatment;
        this.visitDate = visitDate;
        this.visitReason = visitReason;
        this.visitType = visitType;
        this.weight = weight;
        this.weightStatus = weightStatus;
    }

    public ClassClinicVisit(String bodyTemp, String diagnosis, String diastolicBP, String notes, String nurseName, String pulseRateStatus,
                            String respRateStatus, String status, String systolicBP, String timeIn, String timeOut, String treatment, String visitDate, String visitReason) {
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

    public String getBodyTemp() {
        return bodyTemp;
    }

    public void setBodyTemp(String bodyTemp) {
        this.bodyTemp = bodyTemp;
    }

    public String getBodyTempStatus() {
        return bodyTempStatus;
    }

    public void setBodyTempStatus(String bodyTempStatus) {
        this.bodyTempStatus = bodyTempStatus;
    }

    public String getCommunicable() {
        return communicable;
    }

    public void setCommunicable(String communicable) {
        this.communicable = communicable;
    }

    public String getDiagnosis() {
        return diagnosis;
    }

    public void setDiagnosis(String diagnosis) {
        this.diagnosis = diagnosis;
    }

    public String getDiagnosisAssigned() {
        return diagnosisAssigned;
    }

    public void setDiagnosisAssigned(String diagnosisAssigned) {
        this.diagnosisAssigned = diagnosisAssigned;
    }

    public String getDiagnosisSentence() {
        return diagnosisSentence;
    }

    public void setDiagnosisSentence(String diagnosisSentence) {
        this.diagnosisSentence = diagnosisSentence;
    }

    public String getDiastolicBP() {
        return diastolicBP;
    }

    public void setDiastolicBP(String diastolicBP) {
        this.diastolicBP = diastolicBP;
    }

    public String getDiastolicStatus() {
        return diastolicStatus;
    }

    public void setDiastolicStatus(String diastolicStatus) {
        this.diastolicStatus = diastolicStatus;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getHeightStatus() {
        return heightStatus;
    }

    public void setHeightStatus(String heightStatus) {
        this.heightStatus = heightStatus;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImpression() {
        return impression;
    }

    public void setImpression(String impression) {
        this.impression = impression;
    }

    public String getInjury() {
        return injury;
    }

    public void setInjury(String injury) {
        this.injury = injury;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getNurseKey() {
        return nurseKey;
    }

    public void setNurseKey(String nurseKey) {
        this.nurseKey = nurseKey;
    }

    public String getNurseName() {
        return nurseName;
    }

    public void setNurseName(String nurseName) {
        this.nurseName = nurseName;
    }

    public String getPrescribedBy() {
        return prescribedBy;
    }

    public void setPrescribedBy(String prescribedBy) {
        this.prescribedBy = prescribedBy;
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

    public String getSection() {
        return section;
    }

    public void setSection(String section) {
        this.section = section;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getSystolicBP() {
        return systolicBP;
    }

    public void setSystolicBP(String systolicBP) {
        this.systolicBP = systolicBP;
    }

    public String getSystolicStatus() {
        return systolicStatus;
    }

    public void setSystolicStatus(String systolicStatus) {
        this.systolicStatus = systolicStatus;
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

    public String getVisitType() {
        return visitType;
    }

    public void setVisitType(String visitType) {
        this.visitType = visitType;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getWeightStatus() {
        return weightStatus;
    }

    public void setWeightStatus(String weightStatus) {
        this.weightStatus = weightStatus;
    }
}
