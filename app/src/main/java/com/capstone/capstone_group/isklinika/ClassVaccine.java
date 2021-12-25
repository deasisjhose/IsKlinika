package com.capstone.capstone_group.isklinika;

import android.os.Parcel;
import android.os.Parcelable;

public class ClassVaccine implements Parcelable {

    private String doses, expectedAge, notes, purpose, vaccineName ;

    protected ClassVaccine(Parcel in) {
        doses = in.readString();
        expectedAge = in.readString();
        notes = in.readString();
        purpose = in.readString();
        vaccineName = in.readString();
    }

    public ClassVaccine() {

    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(doses);
        dest.writeString(expectedAge);
        dest.writeString(notes);
        dest.writeString(purpose);
        dest.writeString(vaccineName);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ClassVaccine> CREATOR = new Creator<ClassVaccine>() {
        @Override
        public ClassVaccine createFromParcel(Parcel in) {
            return new ClassVaccine(in);
        }

        @Override
        public ClassVaccine[] newArray(int size) {
            return new ClassVaccine[size];
        }
    };

    public String getVaccineName() {
        return vaccineName;
    }

    public void setVaccineName(String vaccineName) {
        this.vaccineName = vaccineName;
    }

    public String getDoses() {
        return doses;
    }

    public void setDoses(String doses) {
        this.doses = doses;
    }

    public String getExpectedAge() {
        return expectedAge;
    }

    public void setExpectedAge(String expectedAge) {
        this.expectedAge = expectedAge;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getPurpose() {
        return purpose;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }

    @Override
    public String toString() {
        return vaccineName;
    }


}
