package com.capstone.capstone_group.isklinika;

public class ClassIntakeHistory {

    private String dateTaken ;
    private String specificAmount ;
    private String specificMedicine ;
    private String time ;

    public ClassIntakeHistory() {
    }

    public ClassIntakeHistory(String dateTaken, String specificAmount, String specificMedicine, String time) {
        this.dateTaken = dateTaken;
        this.specificAmount = specificAmount;
        this.specificMedicine = specificMedicine;
        this.time = time;
    }

    public String getDateTaken() {
        return dateTaken;
    }

    public void setDateTaken(String dateTaken) {
        this.dateTaken = dateTaken;
    }

    public String getSpecificAmount() {
        return specificAmount;
    }

    public void setSpecificAmount(String specificAmount) {
        this.specificAmount = specificAmount;
    }

    public String getSpecificMedicine() {
        return specificMedicine;
    }

    public void setSpecificMedicine(String specificMedicine) {
        this.specificMedicine = specificMedicine;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }


}
