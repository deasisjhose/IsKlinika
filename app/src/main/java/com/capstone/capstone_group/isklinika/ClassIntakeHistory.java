package com.capstone.capstone_group.isklinika;

public class ClassIntakeHistory {

    private String amount ;
    private String date ;
    private String medicineName ;
    private String time ;

    public String getMedicineName() {
        return medicineName;
    }

    public void setMedicineName(String medicineName) {
        this.medicineName = medicineName;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "Class_IntakeHistory{" +
                "amount='" + amount + '\'' +
                ", date='" + date + '\'' +
                ", isClinicVisit=" +
                ", medicineName='" + medicineName + '\'' +
                ", time='" + time + '\'' +
                '}';
    }
}
