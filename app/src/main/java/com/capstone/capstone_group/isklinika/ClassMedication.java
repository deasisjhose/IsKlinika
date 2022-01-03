package com.capstone.capstone_group.isklinika;

public class ClassMedication {

    private String amount, endMed, interval, medicine, purpose, startMed, status, key;

    public ClassMedication() {

    }

    public ClassMedication(String amount, String endMed, String interval, String medicine, String purpose, String startMed, String status) {
        this.amount = amount;
        this.endMed = endMed;
        this.interval = interval;
        this.medicine = medicine;
        this.purpose = purpose;
        this.startMed = startMed;
        this.status = status;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getEndMed() {
        return endMed;
    }

    public void setEndMed(String endMed) {
        this.endMed = endMed;
    }

    public String getInterval() {
        return interval;
    }

    public void setInterval(String interval) {
        this.interval = interval;
    }

    public String getMedicine() {
        return medicine;
    }

    public void setMedicine(String medicine) {
        this.medicine = medicine;
    }

    public String getPurpose() {
        return purpose;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }

    public String getStartMed() {
        return startMed;
    }

    public void setStartMed(String startMed) {
        this.startMed = startMed;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
