package com.capstone.capstone_group.isklinika;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.android.material.card.MaterialCardView;

/*
This activity is used to display the student information when the user is the clinician.
 */
public class ActivityClinicianPersonalInfoModule extends AppCompatActivity {

    private Intent intent ;
    private ImageButton btn_back ;
    private ClassStudentInfo studentInfo ;
    private TextView tv_clinicStudentName, tv_clinicStudentID, tv_clinicGradeSection, tv_clinicianFullName, tv_clinicianIdNum, tv_clinicianGradenSection, tv_clinicianSex, tv_clinicianStudentType,
            tv_clinicianBirthday, tv_clinicianAge, tv_clinicianReligion, tv_clinicianNationality, tv_clinicianAddress, tv_clinicianBMI, tv_clinicianWeight, tv_clinicianHeight, tv_clinicianFather,
            tv_clinicianFatherEmail, tv_ClinicianFatherContact, tv_clinicianMother, tv_clinicianMotherEmail, tv_ClinicianMotherContact, tv_clinicianGuardian, tv_clinicianGuardianEmail,
            tv_ClinicianGuardianContact, tv_clinicianPediatrician, tv_clinicianPediaEmail, tv_ClinicianPediaContact, tv_clinicianDentist, tv_clinicianDentalEmail, tv_clinicianDentalContact,
            tv_clinicianPreferredHospital, tv_clinicianHospitalAddress ;
    private MaterialCardView mcard_uploadMedcert ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clinician_personal_info_module);

        intent = getIntent() ;
        studentInfo = intent.getParcelableExtra("student") ;
        buildViews() ;
    }

    public void buildViews(){
        this.btn_back = findViewById(R.id.btn_back);
        btn_back.setOnClickListener(view -> finish());;

        this.mcard_uploadMedcert = findViewById(R.id.mcard_uploadMedcert) ;

        mcard_uploadMedcert.setOnClickListener(view -> {
            intent = new Intent(getBaseContext(), ActivityAddMedCert.class) ;
            intent.putExtra("studentChild", studentInfo) ;
            intent.putExtra("userType", "Clinician") ;
            startActivity(intent);
        });

        this.tv_clinicStudentName = findViewById(R.id.tv_clinicStudentName) ;
        this.tv_clinicStudentID = findViewById(R.id.tv_clinicStudentID) ;
        this.tv_clinicGradeSection = findViewById(R.id.tv_clinicGradeSection) ;
        this.tv_clinicianFullName = findViewById(R.id.tv_clinicianFullName) ;
        this.tv_clinicianIdNum = findViewById(R.id.tv_clinicianIdNum) ;
        this.tv_clinicianGradenSection = findViewById(R.id.tv_clinicianGradenSection) ;
        this.tv_clinicianSex = findViewById(R.id.tv_clinicianSex) ;
        this.tv_clinicianStudentType = findViewById(R.id.tv_clinicianStudentType) ;
        this.tv_clinicianBirthday = findViewById(R.id.tv_clinicianBirthday) ;
        this.tv_clinicianAge = findViewById(R.id.tv_clinicianAge) ;
        this.tv_clinicianReligion = findViewById(R.id.tv_clinicianReligion) ;
        this.tv_clinicianNationality = findViewById(R.id.tv_clinicianNationality) ;
        this.tv_clinicianAddress = findViewById(R.id.tv_clinicianAddress) ;
        this.tv_clinicianBMI = findViewById(R.id.tv_clinicianBMI) ;
        this.tv_clinicianWeight = findViewById(R.id.tv_clinicianWeight) ;
        this.tv_clinicianHeight = findViewById(R.id.tv_clinicianHeight) ;
        this.tv_clinicianFather = findViewById(R.id.tv_clinicianFather) ;
        this.tv_clinicianFatherEmail = findViewById(R.id.tv_clinicianFatherEmail) ;
        this.tv_ClinicianFatherContact = findViewById(R.id.tv_ClinicianFatherContact) ;
        this.tv_clinicianMother = findViewById(R.id.tv_clinicianMother) ;
        this.tv_clinicianMotherEmail = findViewById(R.id.tv_clinicianMotherEmail) ;
        this.tv_ClinicianMotherContact = findViewById(R.id.tv_ClinicianMotherContact) ;
        this.tv_clinicianGuardian = findViewById(R.id.tv_clinicianGuardian) ;
        this.tv_clinicianGuardianEmail = findViewById(R.id.tv_clinicianGuardianEmail) ;
        this.tv_ClinicianGuardianContact = findViewById(R.id.tv_ClinicianGuardianContact) ;
        this.tv_clinicianPediatrician = findViewById(R.id.tv_clinicianPediatrician) ;
        this.tv_clinicianPediaEmail = findViewById(R.id.tv_clinicianPediaEmail) ;
        this.tv_ClinicianPediaContact = findViewById(R.id.tv_ClinicianPediaContact) ;
        this.tv_clinicianDentist = findViewById(R.id.tv_clinicianDentist) ;
        this.tv_clinicianDentalEmail = findViewById(R.id.tv_clinicianDentalEmail) ;
        this.tv_clinicianDentalContact = findViewById(R.id.tv_clinicianDentalContact) ;
        this.tv_clinicianPreferredHospital = findViewById(R.id.tv_clinicianPreferredHospital) ;
        this.tv_clinicianHospitalAddress = findViewById(R.id.tv_clinicianHospitalAddress) ;

        tv_clinicStudentName.setText(studentInfo.getFullName());
        tv_clinicStudentID.setText(studentInfo.getIdNum());
        tv_clinicGradeSection.setText(studentInfo.getGrade()+"-"+studentInfo.getSection());
        tv_clinicianFullName.setText(studentInfo.getFullName());
        tv_clinicianIdNum.setText(studentInfo.getIdNum());
        tv_clinicianGradenSection.setText(studentInfo.getGrade()+"-"+studentInfo.getSection());
        tv_clinicianSex.setText(studentInfo.getSex());
        tv_clinicianStudentType.setText(studentInfo.getStudentType());
        tv_clinicianBirthday.setText(studentInfo.getBirthday());
        tv_clinicianAge.setText(studentInfo.getAge());
        tv_clinicianReligion.setText(studentInfo.getReligion());
        tv_clinicianNationality.setText(studentInfo.getNationality());
        tv_clinicianAddress.setText(studentInfo.getAddress());
        tv_clinicianBMI.setText(studentInfo.getBmi());
        tv_clinicianWeight.setText(studentInfo.getWeight());
        tv_clinicianHeight.setText(studentInfo.getHeight());
        tv_clinicianFather.setText(studentInfo.getFatherName());
        tv_clinicianFatherEmail.setText(studentInfo.getFatherEmail());
        if(!studentInfo.getFatherContact().equals("0"))
            tv_ClinicianFatherContact.setText(studentInfo.getFatherContact());
        tv_clinicianMother.setText(studentInfo.getMotherName());
        tv_clinicianMotherEmail.setText(studentInfo.getMotherEmail());
        if(!studentInfo.getMotherContact().equals("0"))
            tv_ClinicianMotherContact.setText(studentInfo.getMotherContact());
        tv_clinicianGuardian.setText(studentInfo.getGuardianName());
        tv_clinicianGuardianEmail.setText(studentInfo.getGuardianEmail());
        if(!studentInfo.getGuardianContact().equals("0"))
            tv_ClinicianGuardianContact.setText(studentInfo.getGuardianContact());
        tv_clinicianPediatrician.setText(studentInfo.getPediaName());
        tv_clinicianPediaEmail.setText(studentInfo.getPediaEmail());
        if(!studentInfo.getPediaContact().equals("0"))
            tv_ClinicianPediaContact.setText(studentInfo.getPediaContact());
        tv_clinicianDentist.setText(studentInfo.getDentistName());
        tv_clinicianDentalEmail.setText(studentInfo.getDentistEmail());
        if(!studentInfo.getDentistContact().equals("0"))
            tv_clinicianDentalContact.setText(studentInfo.getDentistContact());
        tv_clinicianPreferredHospital.setText(studentInfo.getPreferredHospital());
        tv_clinicianHospitalAddress.setText(studentInfo.getHospitalAddress());

    }
}