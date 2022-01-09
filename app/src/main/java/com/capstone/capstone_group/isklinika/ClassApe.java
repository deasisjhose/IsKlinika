package com.capstone.capstone_group.isklinika;

import android.os.Parcel;
import android.os.Parcelable;

public class ClassApe implements Parcelable {

    private String age, allergies, apeDate, assess, bmi, bmiStatus, bp, clinician, concern, diastolic,
            height,heightStatus, id, medProb, name, odGlasses, odVision, osGlasses, osVision, pr,rr, schoolYear,
            sf, systolic, temp, weight, weightStatus ;

    public ClassApe() {
        this.age = "" ;
        this.allergies = "" ;
        this.apeDate = "" ;
        this.assess = "" ;
        this.bmi = "" ;
        this.bmiStatus = "" ;
        this.bp = "" ;
        this.clinician = "" ;
        this.concern = "" ;
        this.diastolic = "" ;
        this.height = "" ;
        this.heightStatus = "" ;
        this.id = "" ;
        this.medProb = "" ;
        this.name = "" ;
        this.odGlasses = "" ;
        this.odVision = "" ;
        this.osGlasses = "" ;
        this.osVision = "" ;
        this.pr = "" ;
        this.rr = "" ;
        this.schoolYear = "" ;
        this.sf = "" ;
        this.systolic = "" ;
        this.temp = "" ;
        this.weight = "" ;
        this.weightStatus = "" ;
    }

    protected ClassApe(Parcel in) {
        age = in.readString();
        allergies = in.readString();
        apeDate = in.readString();
        assess = in.readString();
        bmi = in.readString();
        bmiStatus = in.readString();
        bp = in.readString();
        clinician = in.readString();
        concern = in.readString();
        diastolic = in.readString();
        height = in.readString();
        heightStatus = in.readString();
        id = in.readString();
        medProb = in.readString();
        name = in.readString();
        odGlasses = in.readString();
        odVision = in.readString();
        osGlasses = in.readString();
        osVision = in.readString();
        pr = in.readString();
        rr = in.readString();
        schoolYear = in.readString();
        sf = in.readString();
        systolic = in.readString();
        temp = in.readString();
        weight = in.readString();
        weightStatus = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(age);
        dest.writeString(allergies);
        dest.writeString(apeDate);
        dest.writeString(assess);
        dest.writeString(bmi);
        dest.writeString(bmiStatus);
        dest.writeString(bp);
        dest.writeString(clinician);
        dest.writeString(concern);
        dest.writeString(diastolic);
        dest.writeString(height);
        dest.writeString(heightStatus);
        dest.writeString(id);
        dest.writeString(medProb);
        dest.writeString(name);
        dest.writeString(odGlasses);
        dest.writeString(odVision);
        dest.writeString(osGlasses);
        dest.writeString(osVision);
        dest.writeString(pr);
        dest.writeString(rr);
        dest.writeString(schoolYear);
        dest.writeString(sf);
        dest.writeString(systolic);
        dest.writeString(temp);
        dest.writeString(weight);
        dest.writeString(weightStatus);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ClassApe> CREATOR = new Creator<ClassApe>() {
        @Override
        public ClassApe createFromParcel(Parcel in) {
            return new ClassApe(in);
        }

        @Override
        public ClassApe[] newArray(int size) {
            return new ClassApe[size];
        }
    };

    public String getDiastolic() {
        return diastolic;
    }

    public void setDiastolic(String diastolic) {
        this.diastolic = diastolic;
    }

    public String getSystolic() {
        return systolic;
    }

    public void setSystolic(String systolic) {
        this.systolic = systolic;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getAllergies() {
        return allergies;
    }

    public void setAllergies(String allergies) {
        this.allergies = allergies;
    }

    public String getApeDate() {
        return apeDate;
    }

    public void setApeDate(String apeDate) {
        this.apeDate = apeDate;
    }

    public String getAssess() {
        return assess;
    }

    public void setAssess(String assess) {
        this.assess = assess;
    }

    public String getBmi() {
        return bmi;
    }

    public void setBmi(String bmi) {
        this.bmi = bmi;
    }

    public String getBmiStatus() {
        return bmiStatus;
    }

    public void setBmiStatus(String bmiStatus) {
        this.bmiStatus = bmiStatus;
    }

    public String getBp() {
        return bp;
    }

    public void setBp(String bp) {
        this.bp = bp;
    }

    public String getClinician() {
        return clinician;
    }

    public void setClinician(String clinician) {
        this.clinician = clinician;
    }

    public String getConcern() {
        return concern;
    }

    public void setConcern(String concern) {
        this.concern = concern;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMedProb() {
        return medProb;
    }

    public void setMedProb(String medProb) {
        this.medProb = medProb;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOdGlasses() {
        return odGlasses;
    }

    public void setOdGlasses(String odGlasses) {
        this.odGlasses = odGlasses;
    }

    public String getOdVision() {
        return odVision;
    }

    public void setOdVision(String odVision) {
        this.odVision = odVision;
    }

    public String getOsGlasses() {
        return osGlasses;
    }

    public void setOsGlasses(String osGlasses) {
        this.osGlasses = osGlasses;
    }

    public String getOsVision() {
        return osVision;
    }

    public void setOsVision(String osVision) {
        this.osVision = osVision;
    }

    public String getPr() {
        return pr;
    }

    public void setPr(String pr) {
        this.pr = pr;
    }

    public String getRr() {
        return rr;
    }

    public void setRr(String rr) {
        this.rr = rr;
    }

    public String getSchoolYear() {
        return schoolYear;
    }

    public void setSchoolYear(String schoolYear) {
        this.schoolYear = schoolYear;
    }

    public String getSf() {
        return sf;
    }

    public void setSf(String sf) {
        this.sf = sf;
    }

    public String getTemp() {
        return temp;
    }

    public void setTemp(String temp) {
        this.temp = temp;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getHeightStatus() {
        return heightStatus;
    }

    public void setHeightStatus(String heightStatus) {
        this.heightStatus = heightStatus;
    }

    public String getWeightStatus() {
        return weightStatus;
    }

    public void setWeightStatus(String weightStatus) {
        this.weightStatus = weightStatus;
    }
}
