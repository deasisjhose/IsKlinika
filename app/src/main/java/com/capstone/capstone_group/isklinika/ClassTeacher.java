package com.capstone.capstone_group.isklinika;

import android.os.Parcel;
import android.os.Parcelable;

public class ClassTeacher implements Parcelable {
    private String email;
    private String firstName;
    private String lastName;
    private String password;
    private String role;
    private String section;
    private String key ;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }


    public ClassTeacher(String email, String firstName, String lastName, String password, String role, String section) {
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
        this.role = role;
        this.section = section;
    }

    public ClassTeacher() {
    }

    protected ClassTeacher(Parcel in) {
        email = in.readString();
        firstName = in.readString();
        lastName = in.readString();
        password = in.readString();
        role = in.readString();
        section = in.readString();
        key = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(email);
        dest.writeString(firstName);
        dest.writeString(lastName);
        dest.writeString(password);
        dest.writeString(role);
        dest.writeString(section);
        dest.writeString(key);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ClassTeacher> CREATOR = new Creator<ClassTeacher>() {
        @Override
        public ClassTeacher createFromParcel(Parcel in) {
            return new ClassTeacher(in);
        }

        @Override
        public ClassTeacher[] newArray(int size) {
            return new ClassTeacher[size];
        }
    };

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getSection() {
        return section;
    }

    public void setSection(String section) {
        this.section = section;
    }
}
