package com.capstone.capstone_group.isklinika;

import android.os.Parcel;
import android.os.Parcelable;

public class ClassAllergy implements Parcelable {

    private String allergy, type, diagnosisDate, lastOccurrence ;
    private String key ;

    public ClassAllergy() {
    }

    public ClassAllergy(String allergy, String type, String diagnosisDate, String lastOccurrence) {
        this.allergy = allergy;
        this.type = type;
        this.diagnosisDate = diagnosisDate;
        this.lastOccurrence = lastOccurrence;
    }

    protected ClassAllergy(Parcel in) {
        allergy = in.readString();
        type = in.readString();
        diagnosisDate = in.readString();
        lastOccurrence = in.readString();
        key = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(allergy);
        dest.writeString(type);
        dest.writeString(diagnosisDate);
        dest.writeString(lastOccurrence);
        dest.writeString(key);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ClassAllergy> CREATOR = new Creator<ClassAllergy>() {
        @Override
        public ClassAllergy createFromParcel(Parcel in) {
            return new ClassAllergy(in);
        }

        @Override
        public ClassAllergy[] newArray(int size) {
            return new ClassAllergy[size];
        }
    };

    public String getAllergy() {
        return allergy;
    }

    public void setAllergy(String allergy) {
        this.allergy = allergy;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDiagnosisDate() {
        return diagnosisDate;
    }

    public void setDiagnosisDate(String diagnosisDate) {
        this.diagnosisDate = diagnosisDate;
    }

    public String getLastOccurrence() {
        return lastOccurrence;
    }

    public void setLastOccurrence(String lastOccurrence) {
        this.lastOccurrence = lastOccurrence;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    @Override
    public String toString() {
        return allergy ;
    }
}
