package com.capstone.capstone_group.isklinika;

public class ClassNotif {

    private String date, id, message, timeIn ;

    public ClassNotif(String date, String id, String message, String timeIn) {
        this.date = date;
        this.id = id;
        this.message = message;
        this.timeIn = timeIn;
    }

    public ClassNotif() {
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTimeIn() {
        return timeIn;
    }

    public void setTimeIn(String timeIn) {
        this.timeIn = timeIn;
    }
}
