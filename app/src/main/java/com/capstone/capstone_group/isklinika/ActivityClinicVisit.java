package com.capstone.capstone_group.isklinika;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.button.MaterialButtonToggleGroup;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.sql.Array;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ActivityClinicVisit extends AppCompatActivity implements View.OnClickListener {

    FirebaseDatabase db = FirebaseDatabase.getInstance();   // getting real time database
    public DatabaseReference databaseClinicVisit = db.getReference("clinicVisit");

    private ArrayList<ClassStudentInfo> children ;
    private ClassStudentInfo studentInfo ;
    private Intent intent ;
    private int checkActive ;
    private String userType, studentId ;
    public String TAG="CLINICVISIT//";

    //module tab children
    private ShapeableImageView shapeImg_moduleProfileChild ;
    private MaterialButtonToggleGroup mbtg_medication ;
    private Spinner spinner_childNameModules ;
    private TextView tv_moduleFullName ;

    private ArrayList<ClassClinicVisit>classClinicVisitArrayList ;
    private ExpandableListView expand_clinicVisit ;
    private ExpandableListAdapter expandableListAdapter ;

    private TextView tv_cvFromDate, tv_cvToDate ;
    private Spinner spinner_sort ;
    private MaterialDatePicker materialDatePicker ;
    private int selectedDate ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clinic_visit);

        checkActive = 10 ;
        intent = getIntent() ;
        this.userType = intent.getStringExtra("userType") ;
        buildBar();
        buildView();
        checkUser();
    }

    public void buildBar(){
        this.tv_moduleFullName = findViewById(R.id.tv_moduleFullName) ;

    }

    public void buildView(){
        this.tv_cvFromDate = findViewById(R.id.tv_cvFromDate) ;
        this.tv_cvToDate = findViewById(R.id.tv_cvToDate) ;
        this.spinner_sort = findViewById(R.id.spinner_sort) ;

        tv_cvFromDate.setOnClickListener(this);
        tv_cvToDate.setOnClickListener(this);

        makeSpinnerSort();
        MaterialDatePicker.Builder builder = MaterialDatePicker.Builder.datePicker() ;
        builder.setTitleText("Select Immunization Date (MM-DD-YY)") ;
        builder.setTheme(R.style.ThemeOverlay_App_DatePicker_ClinicVisit) ;
        builder.setSelection(MaterialDatePicker.todayInUtcMilliseconds()) ;


        this.materialDatePicker = builder.build() ;
        materialDatePicker.addOnPositiveButtonClickListener(selection -> {

            ClassDateConvert dateConvert = new ClassDateConvert(materialDatePicker.getHeaderText()) ;

            switch (selectedDate){
                case 10:
                    tv_cvFromDate.setText(dateConvert.getConverted());
                    break;
                case 20:
                    tv_cvToDate.setText(dateConvert.getConverted());
                    retrieveClinicVisit();
                    break;
            }
        }) ;

    }

    public void checkUser() {

        switch (userType){
            case "Student":
            case "Clinician":
                if(userType.equals("Clinician")){
                    ImageView img_logoHealthAssess = findViewById(R.id.img_logoHealthAssess) ;
                    img_logoHealthAssess.setImageResource(R.drawable.clinician_visit);
                }
                this.studentInfo = intent.getParcelableExtra("studentInfo") ;
                tv_moduleFullName.setText(studentInfo.getFullName());
                studentId = studentInfo.getIdNum() ;
                retrieveClinicVisit();
                break;
            case "Parent":
                this.children = intent.getParcelableArrayListExtra("children") ;
                tv_moduleFullName.setText(children.get(0).getFullName());
                makeSpinnerChildren();
//                retrieveClinicVisit();
                break;
        }
    }

    //This function is used to make the toggle buttons of the children
    public void makeSpinnerChildren(){

        this.spinner_childNameModules = findViewById(R.id.spinner_childNameModules) ;
        spinner_childNameModules.setVisibility(View.VISIBLE);
        ArrayAdapter<ClassStudentInfo> adapter = new ArrayAdapter<>(this, R.layout.spinner_child_clinicvisit, children) ;
        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spinner_childNameModules.setAdapter(adapter);
        spinner_childNameModules.setSelection(0);

        tv_moduleFullName.setText(children.get(0).getFullName());
        studentId = children.get(0).getIdNum() ;

        spinner_childNameModules.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                studentInfo = children.get(position);
                tv_moduleFullName.setText(studentInfo.getFullName());
                studentId = studentInfo.getIdNum() ;

                retrieveClinicVisit();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    public void retrieveClinicVisit(){
        this.classClinicVisitArrayList = new ArrayList<>() ;

        Log.d(TAG, "retrieveDataAPE: studentId = " + studentId);

        databaseClinicVisit.orderByChild("visitDate").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(!classClinicVisitArrayList.isEmpty())
                    classClinicVisitArrayList.clear();

                String stringStart=tv_cvFromDate.getText().toString();
                String stringEnd=tv_cvToDate.getText().toString();
                String vDate;
                String[] parts1;
                String[] parts2;
                String[] parts3;
                Calendar startDate = Calendar.getInstance();
                Calendar endDate = Calendar.getInstance();
                if(!tv_cvFromDate.getText().toString().equals("YYYY-MM-DD") && !tv_cvToDate.getText().toString().equals("YYYY-MM-DD")) {
                    parts1 = stringStart.split("-");
                    parts2 = stringEnd.split("-");

                    startDate.set(Calendar.YEAR, Integer.parseInt(parts1[0]));
                    startDate.set(Calendar.MONTH, Integer.parseInt(parts1[1]) - 1);
                    startDate.set(Calendar.DAY_OF_MONTH, Integer.parseInt(parts1[2]));

                    endDate.set(Calendar.YEAR, Integer.parseInt(parts2[0]));
                    endDate.set(Calendar.MONTH, Integer.parseInt(parts2[1]) - 1);
                    endDate.set(Calendar.DAY_OF_MONTH, Integer.parseInt(parts2[2]));
                }




                for (DataSnapshot postSnapshot: snapshot.getChildren()){
                    if(postSnapshot.child("id").getValue().toString().equals(studentId)){

                        if(!tv_cvFromDate.getText().toString().equals("YYYY-MM-DD") && !tv_cvToDate.getText().toString().equals("YYYY-MM-DD")) {
                            vDate= postSnapshot.child("visitDate").getValue().toString();
                            parts3=vDate.split("-");
                            Calendar dataDate = Calendar.getInstance();
                            dataDate.set(Calendar.YEAR, Integer.parseInt(parts3[0]));
                            dataDate.set(Calendar.MONTH, Integer.parseInt(parts3[1])-1);
                            dataDate.set(Calendar.DAY_OF_MONTH, Integer.parseInt(parts3[2]));

                            if( ((startDate.before(dataDate)) ||(startDate.equals(dataDate))) && ((dataDate.before(endDate)) ||(dataDate.equals(endDate)))){
                                String bodyTemp, diagnosis, diastolicBP, notes,  nurseName, pulseRateStatus, respRateStatus, status, systolicBP, timeIn, timeOut,
                                        treatment, visitDate, visitReason ;
                                bodyTemp = postSnapshot.child("bodyTemp").getValue().toString() ;
                                diagnosis = postSnapshot.child("diagnosis").getValue().toString() ;
                                diastolicBP = postSnapshot.child("diastolicBP").getValue().toString() ;
                                notes = postSnapshot.child("notes").getValue().toString() ;
                                nurseName = postSnapshot.child("nurseName").getValue().toString() ;
                                pulseRateStatus = postSnapshot.child("pulseRateStatus").getValue().toString() ;
                                respRateStatus = postSnapshot.child("respRateStatus").getValue().toString() ;
                                status = postSnapshot.child("status").getValue().toString() ;
                                systolicBP = postSnapshot.child("systolicBP").getValue().toString() ;
                                timeIn = postSnapshot.child("timeIn").getValue().toString() ;
                                timeOut = postSnapshot.child("timeOut").getValue().toString() ;
                                treatment = postSnapshot.child("treatment").getValue().toString() ;
                                visitDate = postSnapshot.child("visitDate").getValue().toString() ;
                                visitReason = postSnapshot.child("visitReason").getValue().toString() ;

                                ClassClinicVisit clinicVisit = new ClassClinicVisit(bodyTemp, diagnosis, diastolicBP, notes,  nurseName, pulseRateStatus, respRateStatus, status, systolicBP, timeIn, timeOut,
                                        treatment, visitDate, visitReason) ;

                                classClinicVisitArrayList.add(clinicVisit) ;
                            }
                        }else{
                            String bodyTemp, diagnosis, diastolicBP, notes,  nurseName, pulseRateStatus, respRateStatus, status, systolicBP, timeIn, timeOut,
                                    treatment, visitDate, visitReason ;
                            bodyTemp = postSnapshot.child("bodyTemp").getValue().toString() ;
                            diagnosis = postSnapshot.child("diagnosis").getValue().toString() ;
                            diastolicBP = postSnapshot.child("diastolicBP").getValue().toString() ;
                            notes = postSnapshot.child("notes").getValue().toString() ;
                            nurseName = postSnapshot.child("nurseName").getValue().toString() ;
                            pulseRateStatus = postSnapshot.child("pulseRateStatus").getValue().toString() ;
                            respRateStatus = postSnapshot.child("respRateStatus").getValue().toString() ;
                            status = postSnapshot.child("status").getValue().toString() ;
                            systolicBP = postSnapshot.child("systolicBP").getValue().toString() ;
                            timeIn = postSnapshot.child("timeIn").getValue().toString() ;
                            timeOut = postSnapshot.child("timeOut").getValue().toString() ;
                            treatment = postSnapshot.child("treatment").getValue().toString() ;
                            visitDate = postSnapshot.child("visitDate").getValue().toString() ;
                            visitReason = postSnapshot.child("visitReason").getValue().toString() ;

                            ClassClinicVisit clinicVisit = new ClassClinicVisit(bodyTemp, diagnosis, diastolicBP, notes,  nurseName, pulseRateStatus, respRateStatus, status, systolicBP, timeIn, timeOut,
                                    treatment, visitDate, visitReason) ;

                            classClinicVisitArrayList.add(clinicVisit) ;
                        }


                    }
                }
                if(!classClinicVisitArrayList.isEmpty()){
                    Log.d(TAG, "onDataChange: apeSIZE = " + classClinicVisitArrayList.size());

                    dataInExpandClinicVisit(classClinicVisitArrayList) ;
                }else{
                    Toast.makeText(getApplicationContext(), "No data in clinic visit", Toast.LENGTH_SHORT).show();
                    dataInExpandClinicVisit(classClinicVisitArrayList) ;
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        }) ;
    }

    public void dataInExpandClinicVisit(ArrayList<ClassClinicVisit> clinicVisitArrayList){
        if(spinner_sort.getSelectedItem().toString().equals("Latest")){
//            Collections.reverse(clinicVisitsDiagnosisDate);

            Collections.reverse(clinicVisitArrayList);
            Log.d(TAG, "dataInExpandClinicVisit: size = " +clinicVisitArrayList.size());
        }
        ArrayList<ClassClinicVisit> clinicVisitsDiagnosisDate = clinicVisitArrayList;


        Map<ClassClinicVisit, ClassClinicVisit> clinicVisitMap ;


        clinicVisitMap = new HashMap<ClassClinicVisit, ClassClinicVisit>() ;
        int i  ;
        for(i = 0 ; i <clinicVisitArrayList.size() ; i++){
            clinicVisitMap.put(clinicVisitsDiagnosisDate.get(i), clinicVisitArrayList.get(i)) ;
        }


        this.expand_clinicVisit = findViewById(R.id.expand_clinicVisit) ;
        this.expandableListAdapter = new AdapaterExpandClinicVisit(this, clinicVisitsDiagnosisDate, clinicVisitMap) ;
        expand_clinicVisit.setAdapter(expandableListAdapter);
        expand_clinicVisit.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            int lastExpandedPosition = -1;
            @Override
            public void onGroupExpand(int i) {
                if(lastExpandedPosition != -1 && i != lastExpandedPosition){
                    expand_clinicVisit.collapseGroup(lastExpandedPosition);
                }
                lastExpandedPosition = i;
            }
        });
        expand_clinicVisit.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView expandableListView, View view, int i, int i1, long l) {

                return true;
            }
        });

    }

    public void makeSpinnerSort(){
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.sort_by,R.layout.spinner_clinicvisit) ;
        adapter.setDropDownViewResource(R.layout.spinner_immune_down);
        spinner_sort.setAdapter(adapter);
        spinner_sort.setSelection(0);
//        prescriptionStatus = spinner_medicationStatus.getSelectedItem().toString() ;
//        Log.d(TAG, "onItemSelected: selectedSort = " + spinner_medicationStatus.getSelectedItem());

        spinner_sort.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.d(TAG, "onItemSelected: selectedSort = " + spinner_sort.getSelectedItem());
                //sort function
//                prescriptionStatus = spinner_medicationStatus.getSelectedItem().toString() ;
                retrieveClinicVisit();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }


    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.tv_cvFromDate){
            materialDatePicker.show(getSupportFragmentManager(), "DATE PICKER");
            selectedDate = 10 ;
        }else if(view.getId() == R.id.tv_cvToDate){
            materialDatePicker.show(getSupportFragmentManager(), "DATE PICKER");
            selectedDate = 20 ;
        }
    }
}