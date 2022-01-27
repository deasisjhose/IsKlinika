package com.capstone.capstone_group.isklinika;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.textview.MaterialTextView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
/*
This activity the landing page of the clinician user.
 */
public class ActivityClinicianLanding extends AppCompatActivity implements View.OnClickListener{

    FirebaseDatabase db = FirebaseDatabase.getInstance();   // getting real time database
    FirebaseAuth auth = FirebaseAuth.getInstance();
    public DatabaseReference studentReference= db.getReference("studentInfo");

    private Intent intent ;
    private ClassStudentInfo searchedStudent ;
    private String userType ;
    private EditText searchStudentEt ;
    private ShapeableImageView searchList ;
    private ImageButton ibtn_closeSearch ;
    private MaterialTextView tv_clinicLandWelcome ;
    private LinearLayout layout_clinicStudentInfo, layout_clinicStudentModule ;
    private TextView studentNameTv, idNumTv, gradeSectionTv, studentTypeTv ;
    private LinearLayout personalInfoModule, growthMonitoringModule, clinicVisitModule, immunizationModule, medicalHistoryModule, allergyModule, medicationModule;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clinician_landing);

        intent = getIntent() ;
        userType = intent.getStringExtra("userType") ;

        this.tv_clinicLandWelcome = findViewById(R.id.tv_clinicLandWelcome) ;

        this.searchStudentEt = findViewById(R.id.searchStudentEt) ;
        this.searchList = findViewById(R.id.searchList) ;

        searchList.setOnClickListener(this);
        searchStudentEt.setImeOptions(EditorInfo.IME_ACTION_DONE);

        searchStudentEt.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if (i == EditorInfo.IME_ACTION_DONE) {
                    retrieveStudent();
                    return true;
                }
                return false;
            }
        });

        this.layout_clinicStudentInfo = findViewById(R.id.layout_clinicStudentInfo) ;
        this.layout_clinicStudentModule = findViewById(R.id.layout_clinicStudentModule) ;

        this.ibtn_closeSearch = findViewById(R.id.ibtn_closeSearch) ;
        this.studentNameTv = findViewById(R.id.studentNameTv) ;
        this.idNumTv = findViewById(R.id.idNumTv) ;
        this.gradeSectionTv = findViewById(R.id.gradeSectionTv) ;
        this.studentTypeTv = findViewById(R.id.studentTypeTv) ;

        ibtn_closeSearch.setOnClickListener(this);

        this.personalInfoModule = findViewById(R.id.personalInfoModule) ;
        this.growthMonitoringModule = findViewById(R.id.growthMonitoringModule) ;
        this.clinicVisitModule = findViewById(R.id.clinicVisitModule) ;
        this.immunizationModule = findViewById(R.id.immunizationModule) ;
        this.medicalHistoryModule = findViewById(R.id.medicalHistoryModule) ;
        this.allergyModule = findViewById(R.id.allergyModule) ;
        this.medicationModule = findViewById(R.id.medicationModule) ;

        personalInfoModule.setOnClickListener(this);
        growthMonitoringModule.setOnClickListener(this);
        clinicVisitModule.setOnClickListener(this);
        immunizationModule.setOnClickListener(this);
        medicalHistoryModule.setOnClickListener(this);
        allergyModule.setOnClickListener(this);
        medicationModule.setOnClickListener(this);

    }

    //This method is called to retrieve the data of the student being searched.
    public void retrieveStudent(){

        studentReference.child(searchStudentEt.getText().toString()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ClassStudentInfo studentInfoUser = new ClassStudentInfo() ;
                studentInfoUser= snapshot.getValue(ClassStudentInfo.class) ;

                if(studentInfoUser != null){
                    studentInfoUser.setIdNum(snapshot.getKey());
                    displayStudent(studentInfoUser);
                }
                else
                    Toast.makeText(getBaseContext(), "StudentID does not exist!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        }) ;

    }

    //This method is used t o display th information of the student after retrieving the data.
    public void displayStudent(ClassStudentInfo studentInfoUser){
        searchStudentEt.setText("");
        searchedStudent = studentInfoUser ;
        tv_clinicLandWelcome.setVisibility(View.GONE);
        layout_clinicStudentInfo.setVisibility(View.VISIBLE);
        layout_clinicStudentModule.setVisibility(View.VISIBLE);

        studentNameTv.setText(studentInfoUser.getFullName()); ;
        idNumTv .setText(studentInfoUser.getIdNum()); ;
        gradeSectionTv.setText(studentInfoUser.getGrade() + "-" + studentInfoUser.getSection()); ;
        studentTypeTv.setText(studentInfoUser.getStudentType()); ;
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.ibtn_closeSearch){
            tv_clinicLandWelcome.setVisibility(View.VISIBLE);
            layout_clinicStudentInfo.setVisibility(View.GONE);
            layout_clinicStudentModule.setVisibility(View.GONE);
        }else if(view.getId() == R.id.searchList) {
            if(searchStudentEt.length() > 0){
                tv_clinicLandWelcome.setVisibility(View.GONE);
                retrieveStudent();
            }
        } else if(view.getId() == R.id.personalInfoModule) {
            intent = new Intent(this, ActivityClinicianPersonalInfoModule.class) ;
            intent.putExtra("student", searchedStudent);
            startActivity(intent);
        } else if(view.getId() == R.id.growthMonitoringModule) {
            intent = new Intent(this, ActivityHealthAssessment.class) ;
            intent.putExtra("studentInfo", searchedStudent);
            intent.putExtra("userType", userType) ;
            startActivity(intent);
        } else if(view.getId() == R.id.clinicVisitModule) {
            intent = new Intent(this, ActivityClinicVisit.class) ;
            intent.putExtra("studentInfo", searchedStudent);
            intent.putExtra("userType", userType) ;
            startActivity(intent);
        } else if(view.getId() == R.id.immunizationModule) {
            intent = new Intent(this, ActivityImmunization.class) ;
            intent.putExtra("studentInfo", searchedStudent);
            intent.putExtra("userType", userType) ;
            startActivity(intent);

        } else if(view.getId() == R.id.allergyModule || view.getId() == R.id.medicalHistoryModule) {
            intent = new Intent(this, ActivityMedicalHistory.class) ;
            if (view.getId() == R.id.allergyModule)
                intent.putExtra("selected", "allergy") ;
            else
                intent.putExtra("selected", "medHistory") ;
            intent.putExtra("studentInfo", searchedStudent);
            intent.putExtra("userType", userType) ;
            startActivity(intent);
        } else if(view.getId() == R.id.medicationModule) {
            intent = new Intent(this, ActivityMedication.class) ;
            intent.putExtra("studentInfo", searchedStudent);
            intent.putExtra("userType", userType) ;
            startActivity(intent);
        }
    }
}
