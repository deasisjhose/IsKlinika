package com.capstone.capstone_group.isklinika;

import android.os.Parcel;
import android.os.Parcelable;

public class ClassStudentInfo implements Parcelable {

    private Long  age,grade, dentistContact, fatherContact, guardianContact, motherContact, pediaContact;
    private String address, birthday, bmi, bmiStatus, dentistEmail, dentistName, fatherEmail,fatherName, firstName,
            guardianEmail, guardianName, hasSpecialNeeds, height, heightStatus, hospitalAddress, idNum, lastCheckedWandH, lastName, middleName,  motherEmail, motherName, nationality,
            pediaEmail, pediaName, preferredHospital, religion, section, sex, studentType, weight, weightStatus;
    private String password ;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public ClassStudentInfo(){
        this.address = "" ;
        this.birthday = "" ;
        this. bmi = "" ;
        this.bmiStatus = "" ;
        this.dentistEmail = "" ;
        this.dentistName = "" ;
        this.fatherEmail = "" ;
        this.fatherName = "" ;
        this.firstName = "" ;
        this.guardianEmail = "" ;
        this.guardianName = "" ;
        this.hasSpecialNeeds = "" ;
        this.height = "" ;
        this.heightStatus = "" ;
        this.hospitalAddress = "" ;
        this.idNum = "" ;
        this.lastCheckedWandH = "" ;
        this.lastName = "" ;
        this.middleName = "" ;
        this.motherEmail = "" ;
        this.motherName = "" ;
        this.nationality = "" ;
        this.pediaEmail = "" ;
        this.pediaName = "" ;
        this.preferredHospital = "" ;
        this.religion = "" ;
        this.section = "" ;
        this.sex = "" ;
        this.studentType = "" ;
        this.weight = "" ;
        this.weightStatus = "" ;

        this.age = 0L ;
        this.grade = 0L ;
        this.dentistContact = 0L ;
        this.fatherContact = 0L ;
        this.guardianContact = 0L ;
        this.motherContact = 0L ;
        this.pediaContact = 0L ;

    }


    protected ClassStudentInfo(Parcel in) {
        address = in.readString();
        age = in.readLong();
        birthday = in.readString();
        bmi = in.readString();
        bmiStatus = in.readString();
        dentistContact = in.readLong();
        dentistEmail = in.readString();
        dentistName = in.readString();
        fatherContact = in.readLong();
        fatherEmail = in.readString();
        fatherName = in.readString();
        firstName = in.readString();
        grade = in.readLong();
        guardianContact = in.readLong();
        guardianEmail = in.readString();
        guardianName = in.readString();
        hasSpecialNeeds = in.readString();
        height = in.readString();
        heightStatus = in.readString();
        hospitalAddress = in.readString();
        idNum = in.readString();
        lastCheckedWandH = in.readString();
        lastName = in.readString();
        middleName = in.readString();
        motherContact = in.readLong();
        motherEmail = in.readString();
        motherName = in.readString();
        nationality = in.readString();
        pediaContact = in.readLong();
        pediaEmail = in.readString();
        pediaName = in.readString();
        preferredHospital = in.readString();
        religion = in.readString();
        section = in.readString();
        sex = in.readString();
        studentType = in.readString();
        weight = in.readString();
        weightStatus = in.readString();
        password = in.readString() ;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(address);
        dest.writeLong(age);
        dest.writeString(birthday);
        dest.writeString(bmi);
        dest.writeString(bmiStatus);
        dest.writeLong(dentistContact);
        dest.writeString(dentistEmail);
        dest.writeString(dentistName);
        dest.writeLong(fatherContact);
        dest.writeString(fatherEmail);
        dest.writeString(fatherName);
        dest.writeString(firstName);
        dest.writeLong(grade);
        dest.writeLong(guardianContact);
        dest.writeString(guardianEmail);
        dest.writeString(guardianName);
        dest.writeString(hasSpecialNeeds);
        dest.writeString(height);
        dest.writeString(heightStatus);
        dest.writeString(hospitalAddress);
        dest.writeString(idNum);
        dest.writeString(lastCheckedWandH);
        dest.writeString(lastName);
        dest.writeString(middleName);
        dest.writeLong(motherContact);
        dest.writeString(motherEmail);
        dest.writeString(motherName);
        dest.writeString(nationality);
        dest.writeLong(pediaContact);
        dest.writeString(pediaEmail);
        dest.writeString(pediaName);
        dest.writeString(preferredHospital);
        dest.writeString(religion);
        dest.writeString(section);
        dest.writeString(sex);
        dest.writeString(studentType);
        dest.writeString(weight);
        dest.writeString(weightStatus);
        dest.writeString(password);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ClassStudentInfo> CREATOR = new Creator<ClassStudentInfo>() {
        @Override
        public ClassStudentInfo createFromParcel(Parcel in) {
            return new ClassStudentInfo(in);
        }

        @Override
        public ClassStudentInfo[] newArray(int size) {
            return new ClassStudentInfo[size];
        }
    };


    public void checkNull(){
        if(address.equals(null)){
            address = "" ;
        }
        if(birthday.equals(null)){
            birthday = "" ;
        }
        if(bmi.equals(null)){
            bmi = "" ;
        }
        if(bmiStatus.equals(null)){
            bmiStatus = "" ;
        }

        if(dentistEmail.equals(null)){
            dentistEmail = "" ;
        }
        if(dentistName.equals(null)){
            dentistName = "" ;
        }

        if(fatherEmail.equals(null)){
            fatherEmail = "" ;
        }
        if(fatherName.equals(null)){
            fatherName = "" ;
        }
        if(firstName.equals(null)){
            firstName = "" ;
        }


        if(guardianEmail.equals(null)){
            guardianEmail = "" ;
        }
        if(guardianName.equals(null)){
            guardianName = "" ;
        }
        if(hasSpecialNeeds.equals(null)){
            hasSpecialNeeds = "" ;
        }
        if(height.equals(null)){
            height = "" ;
        }
        if(heightStatus.equals(null)){
            heightStatus = "" ;
        }
        if(hospitalAddress.equals(null)){
            hospitalAddress = "" ;
        }
        if(idNum.equals(null)){
            idNum = "" ;
        }
        if(lastCheckedWandH.equals(null)){
            lastCheckedWandH = "" ;
        }
        if(lastName.equals(null)){
            lastName = "" ;
        }
        if(middleName.equals(null)){
            middleName = "" ;
        }

        if(motherEmail.equals(null)){
            motherEmail = "" ;
        }
        if(motherName.equals(null)){
            motherName = "" ;
        }
        if(nationality.equals(null)){
            nationality = "" ;
        }


        if(pediaEmail.equals(null)){
            pediaEmail = "" ;
        }
        if(pediaName.equals(null)){
            pediaName = "" ;
        }
        if(preferredHospital.equals(null)){
            preferredHospital = "" ;
        }
        if(religion.equals(null)){
            religion = "" ;
        }
        if(section.equals(null)){
            section = "" ;
        }
        if(sex.equals(null)){
            sex = "" ;
        }
        if(studentType.equals(null)){
            studentType = "" ;
        }
        if(weight.equals(null)){
            weight = "" ;
        }
        if(weightStatus.equals(null)){
            weightStatus = "" ;
        }
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAge() {
        return age.toString();
    }

    public void setAge(Long age) {
        this.age = age;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getDentistContact() {
        return dentistContact.toString();
    }

    public void setDentistContact(Long dentistContact) {
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
        return fatherContact.toString();
    }

    public void setFatherContact(Long fatherContact) {
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
        return grade.toString();
    }

    public void setGrade(Long grade) {
        this.grade = grade;
    }

    public String getGuardianContact() {
        return guardianContact.toString();
    }

    public void setGuardianContact(Long guardianContact) {
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
        return motherContact.toString();
    }

    public void setMotherContact(Long motherContact) {
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
        return pediaContact.toString();
    }

    public void setPediaContact(long pediaContact) {
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

    public String getIdNumGradeSection() {
        return idNum + " / " + "Grade " + grade + " - " + section ;
    }

    @Override
    public String toString() {
        return firstName ;
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

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getLastCheckedWandH() {
        return lastCheckedWandH;
    }

    public void setLastCheckedWandH(String lastCheckedWandH) {
        this.lastCheckedWandH = lastCheckedWandH;
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
