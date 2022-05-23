package com.capstone.capstone_group.isklinika;

public class ClassImmuneRecord {
    private String dateGiven, name, purpose, key ;

    public String getDateGiven() {
        return dateGiven;
    }

    public void setDateGiven(String dateGiven) {
        this.dateGiven = dateGiven;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPurpose() {
        return purpose;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }

    public ClassImmuneRecord(String dateGiven, String name, String purpose) {
        this.dateGiven = dateGiven;
        this.name = name;
        this.purpose = purpose;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public ClassImmuneRecord() {
    }
}
