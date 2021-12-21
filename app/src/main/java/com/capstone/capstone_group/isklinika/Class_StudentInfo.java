package com.capstone.capstone_group.isklinika;

import android.os.Parcel;
import android.os.Parcelable;

public class Class_StudentInfo implements Parcelable {

    private String address, age, birthday, dentistContact, dentistEmail, dentistName, fatherContact, fatherEmail,fatherName, firstName, grade, guardianContact,
            guardianEmail, guardianName, hasSpecialNeeds, hospitalAddress, idNum, lastName, middleName, motherContact, motherEmail, motherName, nationality,
            pediaContact, pediaEmail, pediaName, preferredHospital, religion, section, sex, studentType ;


    public Class_StudentInfo(){

    }

    protected Class_StudentInfo(Parcel in) {
        address = in.readString();
        age = in.readString();
        birthday = in.readString();
        dentistContact = in.readString();
        dentistEmail = in.readString();
        dentistName = in.readString();
        fatherContact = in.readString();
        fatherEmail = in.readString();
        fatherName = in.readString();
        firstName = in.readString();
        grade = in.readString();
        guardianContact = in.readString();
        guardianEmail = in.readString();
        guardianName = in.readString();
        hasSpecialNeeds = in.readString();
        hospitalAddress = in.readString();
        idNum = in.readString();
        lastName = in.readString();
        middleName = in.readString();
        motherContact = in.readString();
        motherEmail = in.readString();
        motherName = in.readString();
        nationality = in.readString();
        pediaContact = in.readString();
        pediaEmail = in.readString();
        pediaName = in.readString();
        preferredHospital = in.readString();
        religion = in.readString();
        section = in.readString();
        sex = in.readString();
        studentType = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(address);
        dest.writeString(age);
        dest.writeString(birthday);
        dest.writeString(dentistContact);
        dest.writeString(dentistEmail);
        dest.writeString(dentistName);
        dest.writeString(fatherContact);
        dest.writeString(fatherEmail);
        dest.writeString(fatherName);
        dest.writeString(firstName);
        dest.writeString(grade);
        dest.writeString(guardianContact);
        dest.writeString(guardianEmail);
        dest.writeString(guardianName);
        dest.writeString(hasSpecialNeeds);
        dest.writeString(hospitalAddress);
        dest.writeString(idNum);
        dest.writeString(lastName);
        dest.writeString(middleName);
        dest.writeString(motherContact);
        dest.writeString(motherEmail);
        dest.writeString(motherName);
        dest.writeString(nationality);
        dest.writeString(pediaContact);
        dest.writeString(pediaEmail);
        dest.writeString(pediaName);
        dest.writeString(preferredHospital);
        dest.writeString(religion);
        dest.writeString(section);
        dest.writeString(sex);
        dest.writeString(studentType);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Class_StudentInfo> CREATOR = new Creator<Class_StudentInfo>() {
        @Override
        public Class_StudentInfo createFromParcel(Parcel in) {
            return new Class_StudentInfo(in);
        }

        @Override
        public Class_StudentInfo[] newArray(int size) {
            return new Class_StudentInfo[size];
        }
    };

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getDentistContact() {
        return dentistContact;
    }

    public void setDentistContact(String dentistContact) {
        this.dentistContact = dentistContact;
    }

    public String getDentistEmail() {
        return dentistEmail;
    }

    public void setDentistEmail(String dentistEmail) {
        this.dentistEmail = dentistEmail;
    }

    public String getDentistName() {
        return dentistName;
    }

    public void setDentistName(String dentistName) {
        this.dentistName = dentistName;
    }

    public String getFatherContact() {
        return fatherContact;
    }

    public void setFatherContact(String fatherContact) {
        this.fatherContact = fatherContact;
    }

    public String getFatherEmail() {
        return fatherEmail;
    }

    public void setFatherEmail(String fatherEmail) {
        this.fatherEmail = fatherEmail;
    }

    public String getFatherName() {
        return fatherName;
    }

    public void setFatherName(String fatherName) {
        this.fatherName = fatherName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getGuardianContact() {
        return guardianContact;
    }

    public void setGuardianContact(String guardianContact) {
        this.guardianContact = guardianContact;
    }

    public String getGuardianEmail() {
        return guardianEmail;
    }

    public void setGuardianEmail(String guardianEmail) {
        this.guardianEmail = guardianEmail;
    }

    public String getGuardianName() {
        return guardianName;
    }

    public void setGuardianName(String guardianName) {
        this.guardianName = guardianName;
    }

    public String getHasSpecialNeeds() {
        return hasSpecialNeeds;
    }

    public void setHasSpecialNeeds(String hasSpecialNeeds) {
        this.hasSpecialNeeds = hasSpecialNeeds;
    }

    public String getHospitalAddress() {
        return hospitalAddress;
    }

    public void setHospitalAddress(String hospitalAddress) {
        this.hospitalAddress = hospitalAddress;
    }

    public String getIdNum() {
        return idNum;
    }

    public void setIdNum(String idNum) {
        this.idNum = idNum;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getMotherContact() {
        return motherContact;
    }

    public void setMotherContact(String motherContact) {
        this.motherContact = motherContact;
    }

    public String getMotherEmail() {
        return motherEmail;
    }

    public void setMotherEmail(String motherEmail) {
        this.motherEmail = motherEmail;
    }

    public String getMotherName() {
        return motherName;
    }

    public void setMotherName(String motherName) {
        this.motherName = motherName;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public String getPediaContact() {
        return pediaContact;
    }

    public void setPediaContact(String pediaContact) {
        this.pediaContact = pediaContact;
    }

    public String getPediaEmail() {
        return pediaEmail;
    }

    public void setPediaEmail(String pediaEmail) {
        this.pediaEmail = pediaEmail;
    }

    public String getPediaName() {
        return pediaName;
    }

    public void setPediaName(String pediaName) {
        this.pediaName = pediaName;
    }

    public String getPreferredHospital() {
        return preferredHospital;
    }

    public void setPreferredHospital(String preferredHospital) {
        this.preferredHospital = preferredHospital;
    }

    public String getReligion() {
        return religion;
    }

    public void setReligion(String religion) {
        this.religion = religion;
    }

    public String getSection() {
        return section;
    }

    public void setSection(String section) {
        this.section = section;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getStudentType() {
        return studentType;
    }

    public void setStudentType(String studentType) {
        this.studentType = studentType;
    }

    public String getFullName(){
        return firstName + " " + middleName + " " + lastName ;
    }

    @Override
    public String toString() {
        return firstName + " " + middleName + " " + lastName + " / " + "Grade " + grade + " - " + section;
    }
}
