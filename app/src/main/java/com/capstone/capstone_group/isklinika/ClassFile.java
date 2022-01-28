package com.capstone.capstone_group.isklinika;

public class ClassFile {
    String name ;
    String url;
    String date ;

    public ClassFile(String name, String url, String date) {
        this.name = name;
        this.url = url;
        this.date = date ;
    }

    public ClassFile() {
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "ClassFile{" +
                "name='" + name + '\'' +
                ", url='" + url + '\'' +
                ", date='" + date + '\'' +
                '}';
    }
}
