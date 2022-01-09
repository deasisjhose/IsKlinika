package com.capstone.capstone_group.isklinika;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

public class ClassDental implements Parcelable {

    private String adeDate, age, anomaly, calculus, clinician, gingiva, id;
    private List<String> inputs ;
    private String name, pocket, schoolYear ;

    public ClassDental() {
        this.inputs = new ArrayList<>() ;
    }

    protected ClassDental(Parcel in) {
        adeDate = in.readString();
        age = in.readString();
        anomaly = in.readString();
        calculus = in.readString();
        clinician = in.readString();
        gingiva = in.readString();
        id = in.readString();
        inputs = in.createStringArrayList();
        name = in.readString();
        pocket = in.readString();
        schoolYear = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(adeDate);
        dest.writeString(age);
        dest.writeString(anomaly);
        dest.writeString(calculus);
        dest.writeString(clinician);
        dest.writeString(gingiva);
        dest.writeString(id);
        dest.writeStringList(inputs);
        dest.writeString(name);
        dest.writeString(pocket);
        dest.writeString(schoolYear);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ClassDental> CREATOR = new Creator<ClassDental>() {
        @Override
        public ClassDental createFromParcel(Parcel in) {
            return new ClassDental(in);
        }

        @Override
        public ClassDental[] newArray(int size) {
            return new ClassDental[size];
        }
    };

    public String getAdeDate() {
        return adeDate;
    }

    public void setAdeDate(String adeDate) {
        this.adeDate = adeDate;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getAnomaly() {
        return anomaly;
    }

    public void setAnomaly(String anomaly) {
        this.anomaly = anomaly;
    }

    public String getCalculus() {
        return calculus;
    }

    public void setCalculus(String calculus) {
        this.calculus = calculus;
    }

    public String getClinician() {
        return clinician;
    }

    public void setClinician(String clinician) {
        this.clinician = clinician;
    }

    public String getGingiva() {
        return gingiva;
    }

    public void setGingiva(String gingiva) {
        this.gingiva = gingiva;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<String> getInputs() {
        return inputs;
    }

    public void setInputs(List<String> inputs) {
        this.inputs = inputs;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPocket() {
        return pocket;
    }

    public void setPocket(String pocket) {
        this.pocket = pocket;
    }

    public String getSchoolYear() {
        return schoolYear;
    }

    public void setSchoolYear(String schoolYear) {
        this.schoolYear = schoolYear;
    }

    public int getInputsSize(){
        return inputs.size() ;
    }

    public String getInputAt(int i){
        return inputs.get(i) ;
    }

    @Override
    public String toString() {
        return "ClassDental{" +
                "adeDate='" + adeDate + '\'' +
                ", age='" + age + '\'' +
                ", anomaly='" + anomaly + '\'' +
                ", calculus='" + calculus + '\'' +
                ", clinician='" + clinician + '\'' +
                ", gingiva='" + gingiva + '\'' +
                ", id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", pocket='" + pocket + '\'' +
                ", schoolYear='" + schoolYear + '\'' +
                '}';
    }
}
