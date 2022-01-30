package com.capstone.capstone_group.isklinika;

public class ClassNotifTeacher {

    private String date, id, message, url, key ;

    public ClassNotifTeacher() {
    }

    public ClassNotifTeacher(String date, String id, String message, String url) {
        this.date = date;
        this.id = id;
        this.message = message;
        this.url = url;
    }

    public ClassNotifTeacher(String date, String id, String message) {
        this.date = date;
        this.id = id;
        this.message = message;
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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
