package com.capstone.capstone_group.isklinika;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.data.Entry;
import com.google.android.material.button.MaterialButtonToggleGroup;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;

public class ActivityDiseaseSurveillance extends AppCompatActivity implements View.OnClickListener{

    private ArrayList<ClassStudentInfo> children ;
    private ClassStudentInfo studentInfo ;
    private Intent intent ;
    private int checkActive ;
    private String userType, studentId ;
    public String TAG="DISEASESURVEILLANCE//";

    FirebaseDatabase db = FirebaseDatabase.getInstance();   // getting real time database
    public DatabaseReference databaseClinicVisit = db.getReference("clinicVisit");

    //module tab children
    private ShapeableImageView shapeImg_moduleProfileChild ;
    private MaterialButtonToggleGroup mbtg_medication ;
    private Spinner spinner_childNameModules ;
    private TextView tv_moduleFullName ;

    private TextView input_startDate, input_endDate ;
    private int selectedDate;
    private MaterialDatePicker materialDatePicker ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_disease_surveillance);

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
        this.input_startDate = findViewById(R.id.input_startDate);
        this.input_endDate = findViewById(R.id.input_endDate);

        input_startDate.setOnClickListener(this);
        input_endDate.setOnClickListener(this);


        MaterialDatePicker.Builder builder = MaterialDatePicker.Builder.datePicker() ;
        builder.setTitleText("Select Date (MM-DD-YY)") ;
        builder.setTheme(R.style.ThemeOverlay_App_DatePicker_MedicalHistory) ;
        builder.setSelection(MaterialDatePicker.todayInUtcMilliseconds()) ;


        this.materialDatePicker = builder.build() ;
        materialDatePicker.addOnPositiveButtonClickListener(selection -> {

            ClassDateConvert dateConvert = new ClassDateConvert(materialDatePicker.getHeaderText()) ;

            switch (selectedDate){
                case 10:
                    input_startDate.setText(dateConvert.getConverted());
                    break;
                case 20:
                    input_endDate.setText(dateConvert.getConverted());
                    getTop5(input_startDate.getText().toString(), input_endDate.getText().toString());
                    break;
            }
        }) ;


    }

    public void checkUser() {

        switch (userType){
            case "Student":
                this.studentInfo = intent.getParcelableExtra("studentInfo") ;
                tv_moduleFullName.setText(studentInfo.getFullName());
                studentId = studentInfo.getIdNum() ;

                break;
            case "Parent":
                this.children = intent.getParcelableArrayListExtra("children") ;
                tv_moduleFullName.setText(children.get(0).getFullName());
                makeSpinnerChildren();
                break;
        }
    }

    //This function is used to make the toggle buttons of the children
    public void makeSpinnerChildren(){

        this.spinner_childNameModules = findViewById(R.id.spinner_childNameModules) ;
        spinner_childNameModules.setVisibility(View.VISIBLE);
        ArrayAdapter<ClassStudentInfo> adapter = new ArrayAdapter<>(this, R.layout.spinner_child_diseasesurveillance, children) ;
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
                switch (checkActive){
                    case 10:
                        break;
                    case 20:
                        break;
                    case 30:
                        break;
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.input_startDate){
            materialDatePicker.show(getSupportFragmentManager(), "DATE PICKER");
            selectedDate = 10 ;

        }else if(view.getId() == R.id.input_endDate){
            materialDatePicker.show(getSupportFragmentManager(), "DATE PICKER");
            selectedDate = 20 ;
        }
    }

    private static class nameCount{
        public String name;
        public int count;

        public nameCount(String name, Integer count){
            this.name = name;
            this.count = count;
        }
    }

    //This function is used to get and show the top 5 disease and visitReasons
    public void getTop5(String start, String end){

        databaseClinicVisit.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                String stringStart=start;
                String stringEnd=end;
                String vDate;
                String[] parts1;
                String[] parts2;
                String[] parts3;

                ArrayList<nameCount> top5DiseaseTemp = new ArrayList<nameCount>();
                ArrayList<nameCount> top5ComplaintsTemp = new ArrayList<nameCount>();
                ArrayList<nameCount> top5DiseaseFinal = new ArrayList<nameCount>();
                ArrayList<nameCount> top5ComplaintsFinal = new ArrayList<nameCount>();

                parts1=stringStart.split("-");
                parts2=stringEnd.split("-");
                Calendar startDate = Calendar.getInstance();
                startDate.set(Calendar.YEAR, Integer.parseInt(parts1[0]));
                startDate.set(Calendar.MONTH, Integer.parseInt(parts1[1])-1);
                startDate.set(Calendar.DAY_OF_MONTH, Integer.parseInt(parts1[2]));
                Calendar endDate = Calendar.getInstance();
                endDate.set(Calendar.YEAR, Integer.parseInt(parts2[0]));
                endDate.set(Calendar.MONTH, Integer.parseInt(parts2[1])-1);
                endDate.set(Calendar.DAY_OF_MONTH, Integer.parseInt(parts2[2]));
                Integer checker,checker2,i;
                nameCount object1;

                for (DataSnapshot postSnapshot : snapshot.getChildren()) { //get school year APE keys
                    checker=0;
                    checker2=0;
//                    Log.d(TAG, "postSnapshot Key: "+postSnapshot.getKey());
//                    //Log.d(TAG,"postSnapshot Value:"+ postSnapshot.getValue());
//                    Log.d(TAG,"visitDate:"+ postSnapshot.child("visitDate").getValue());
                    vDate= postSnapshot.child("visitDate").getValue().toString();
                    parts3=vDate.split("-");
                    Calendar dataDate = Calendar.getInstance();
                    dataDate.set(Calendar.YEAR, Integer.parseInt(parts3[0]));
                    dataDate.set(Calendar.MONTH, Integer.parseInt(parts3[1])-1);
                    dataDate.set(Calendar.DAY_OF_MONTH, Integer.parseInt(parts3[2]));
//                    Log.d(TAG,"Start Date: " + startDate);
//                    Log.d(TAG,"End Date: " + endDate);
//                    Log.d(TAG,"Data Date: " + dataDate);
                    //Used to filter based on chosen date range and group according to the diagnosis/visitReason
                    if( ((startDate.before(dataDate)) ||(startDate.equals(dataDate))) && ((dataDate.before(endDate)) ||(dataDate.equals(endDate)))){
                        if(top5DiseaseTemp.size()==0){
                            object1= new nameCount(postSnapshot.child("diagnosis").getValue().toString(),1);
                            top5DiseaseTemp.add(object1);
                        }
                        else{
                            for(i=0; i<top5DiseaseTemp.size();i++){
                                if(postSnapshot.child("diagnosis").getValue().equals(top5DiseaseTemp.get(i).name)){
                                    top5DiseaseTemp.get(i).count=top5DiseaseTemp.get(i).count+1;
                                    checker=1;
                                }
                            }
                            if(checker==0){
                                object1= new nameCount(postSnapshot.child("diagnosis").getValue().toString(),1);
                                top5DiseaseTemp.add(object1);
                            }
                        }

                        if(top5ComplaintsTemp.size()==0){
                            object1= new nameCount(postSnapshot.child("visitReason").getValue().toString(),1);
                            top5ComplaintsTemp.add(object1);
                        }
                        else{
                            for(i=0; i<top5ComplaintsTemp.size();i++){
                                if(postSnapshot.child("visitReason").getValue().equals(top5ComplaintsTemp.get(i).name)){
                                    top5ComplaintsTemp.get(i).count=top5ComplaintsTemp.get(i).count+1;
                                    checker2=1;
                                }
                            }
                            if(checker2==0){
                                object1= new nameCount(postSnapshot.child("visitReason").getValue().toString(),1);
                                top5ComplaintsTemp.add(object1);
                            }
                        }
                    }
                }

                //Used to sort the arraylist in descending order based on the count
                Collections.sort(top5DiseaseTemp, new Comparator<nameCount>() {
                    @Override
                    public int compare(nameCount object1, nameCount object2) {
                        return Integer.valueOf(object2.count).compareTo(object1.count);
                    }
                });
                Collections.sort(top5ComplaintsTemp, new Comparator<nameCount>() {
                    @Override
                    public int compare(nameCount object1, nameCount object2) {
                        return Integer.valueOf(object2.count).compareTo(object1.count);
                    }
                });

                if(top5DiseaseTemp.size()<=5){
                    for(i=0;i<top5DiseaseTemp.size();i++){
                        object1=new nameCount(top5DiseaseTemp.get(i).name,top5DiseaseTemp.get(i).count);
                        top5DiseaseFinal.add(object1);
                    }
                }
                else{
                    for(i=0;i<5;i++){
                        object1=new nameCount(top5DiseaseTemp.get(i).name,top5DiseaseTemp.get(i).count);
                        top5DiseaseFinal.add(object1);
                    }
                }

                if(top5ComplaintsTemp.size()<=5){
                    for(i=0;i<top5ComplaintsTemp.size();i++){
                        object1=new nameCount(top5ComplaintsTemp.get(i).name,top5ComplaintsTemp.get(i).count);
                        top5ComplaintsFinal.add(object1);
                    }
                }
                else{
                    for(i=0;i<5;i++){
                        object1=new nameCount(top5ComplaintsTemp.get(i).name,top5ComplaintsTemp.get(i).count);
                        top5ComplaintsFinal.add(object1);
                    }
                }

                //ADD CODE TO SHOW IN FRONTEND
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }





}