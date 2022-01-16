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
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TableRow;
import android.widget.TextView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
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
//disease
public class ActivityDiseaseSurveillance extends AppCompatActivity implements View.OnClickListener{

    private ArrayList<ClassStudentInfo> children ;
    private ClassStudentInfo studentInfo ;
    private Intent intent ;
    private int checkActive ;
    private String userType, studentId ;
    public String TAG="DISEASESURVEILLANCE//";
    private ImageButton btn_back ;

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
    private Spinner spinnerDisease, spinnerComplaints;
    private TextView topDiseaseList, topComplaintList;
    private TableRow tr_disease1, tr_disease2, tr_disease3, tr_disease4, tr_disease5, tr_complaint1, tr_complaint2, tr_complaint3, tr_complaint4, tr_complaint5 ;
    private TextView tv_disease1, tv_disease2,  tv_disease3, tv_disease4, tv_disease5, tv_complaint1, tv_complaint2, tv_complaint3, tv_complaint4, tv_complaint5 ;
    private TextView tv_diseaseCount1, tv_diseaseCount2, tv_diseaseCount3, tv_diseaseCount4, tv_diseaseCount5,
            tv_complaintCount1,  tv_complaintCount2,  tv_complaintCount3,  tv_complaintCount4,  tv_complaintCount5 ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_disease_surveillance);

        checkActive = 10 ;
        intent = getIntent() ;
        this.userType = intent.getStringExtra("userType") ;
        buildBar();
        buildView();
        buildTop5Builds();
        checkUser();

    }

    public void buildBar(){
        this.tv_moduleFullName = findViewById(R.id.tv_moduleFullName) ;
        this.btn_back = findViewById(R.id.btn_back);
        btn_back.setOnClickListener(view -> finish());

    }

    public void buildView(){
        this.input_startDate = findViewById(R.id.input_startDate);
        this.input_endDate = findViewById(R.id.input_endDate);
        this.topDiseaseList = findViewById(R.id.topDiseaseList);
        this.topComplaintList=findViewById(R.id.topComplaintsList);

        input_startDate.setOnClickListener(this);
        input_endDate.setOnClickListener(this);
        if(input_startDate.getText().toString().equals("") && input_endDate.getText().toString().equals("")){
            Calendar cal = Calendar.getInstance();
            int lastDay = cal.getActualMaximum(Calendar.DATE);
            int firstDay = cal.getActualMinimum(Calendar.DATE);
            int currMonth=cal.get(Calendar.MONTH);
            int currYear=cal.get(Calendar.YEAR);
            if((currMonth+1)<10){
                input_startDate.setText(currYear + "-0" + (currMonth+1) + "-0" + firstDay);
                input_endDate.setText(currYear + "-0" + (currMonth+1) + "-" + lastDay);
                //getTop5(input_startDate.getText().toString(), input_endDate.getText().toString());
            }
            else{
                input_startDate.setText(currYear + "-" + (currMonth+1) + "-0" + firstDay);
                input_endDate.setText(currYear + "-" + (currMonth+1) + "-" + lastDay);
                //getTop5(input_startDate.getText().toString(), input_endDate.getText().toString());
            }

        }


        MaterialDatePicker.Builder builder = MaterialDatePicker.Builder.datePicker() ;
        builder.setTitleText("Select Date (MM-DD-YY)") ;
        builder.setTheme(R.style.ThemeOverlay_App_DatePicker_DiseaseSurveillance) ;
        builder.setSelection(MaterialDatePicker.todayInUtcMilliseconds()) ;


        this.materialDatePicker = builder.build() ;
        materialDatePicker.addOnPositiveButtonClickListener(selection -> {

            ClassDateConvert dateConvert = new ClassDateConvert(materialDatePicker.getHeaderText()) ;

            switch (selectedDate){
                case 10:
                    input_startDate.setText(dateConvert.getConverted());
                    if(!input_endDate.getText().toString().equals("")){
                        if(input_startDate.getText().toString().compareTo(input_endDate.getText().toString())==0){
                            getTop5(input_startDate.getText().toString(), input_endDate.getText().toString());
                        }
                        else if(input_startDate.getText().toString().compareTo(input_endDate.getText().toString())<0){
                            getTop5(input_startDate.getText().toString(), input_endDate.getText().toString());
                        }
                        else{
                            input_startDate.setText(input_endDate.getText().toString());
                            getTop5(input_startDate.getText().toString(), input_endDate.getText().toString());
                        }
                    }

                    break;

                case 20:
                    input_endDate.setText(dateConvert.getConverted());
                    if(!input_startDate.getText().toString().equals("")){
                        if(input_startDate.getText().toString().compareTo(input_endDate.getText().toString())==0){
                            getTop5(input_startDate.getText().toString(), input_endDate.getText().toString());
                        }
                        else if(input_endDate.getText().toString().compareTo(input_startDate.getText().toString())<0){
                            input_endDate.setText(input_startDate.getText().toString());
                            getTop5(input_startDate.getText().toString(), input_endDate.getText().toString());
                        }
                        else{
                            getTop5(input_startDate.getText().toString(), input_endDate.getText().toString());
                        }
                    }

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
                if(!input_startDate.getText().toString().equals("") && !input_endDate.getText().toString().equals("")){
                    getTop5(input_startDate.getText().toString(), input_endDate.getText().toString());
                }

            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    //This is a class
    private static class nameCount{
        public String name;
        public int count;

        public nameCount(String name, int count){
            this.name = name;
            this.count = count;
        }

        @Override
        public String toString() {
            return name;
        }

        public String getCount() {
            return Integer.toString(count);
        }
    }

    //
    public void buildTop5Builds(){
        
        this.tr_disease1 = findViewById(R.id.tr_disease1) ;
        this.tr_disease2 = findViewById(R.id.tr_disease2) ;
        this.tr_disease3 = findViewById(R.id.tr_disease3) ;
        this.tr_disease4 = findViewById(R.id.tr_disease4) ;
        this.tr_disease5 = findViewById(R.id.tr_disease5) ;
        this.tr_complaint1 = findViewById(R.id.tr_complaint1) ;
        this.tr_complaint2 = findViewById(R.id.tr_complaint2) ;
        this.tr_complaint3 = findViewById(R.id.tr_complaint3) ;
        this.tr_complaint4 = findViewById(R.id.tr_complaint4) ;
        this.tr_complaint5 = findViewById(R.id.tr_complaint5) ;

        this.tv_disease1 = findViewById(R.id.tv_disease1) ;
        this.tv_disease2 = findViewById(R.id.tv_disease2) ;
        this.tv_disease3 = findViewById(R.id.tv_disease3) ;
        this.tv_disease4 = findViewById(R.id.tv_disease4) ;
        this.tv_disease5 = findViewById(R.id.tv_disease5) ;
        this.tv_complaint1 = findViewById(R.id.tv_complaint1) ;
        this.tv_complaint2 = findViewById(R.id.tv_complaint2) ;
        this.tv_complaint3 = findViewById(R.id.tv_complaint3) ;
        this.tv_complaint4 = findViewById(R.id.tv_complaint4) ;
        this.tv_complaint5 = findViewById(R.id.tv_complaint5) ;

        this.tv_diseaseCount1 = findViewById(R.id.tv_diseaseCount1) ;
        this.tv_diseaseCount2 = findViewById(R.id.tv_diseaseCount2) ;
        this.tv_diseaseCount3 = findViewById(R.id.tv_diseaseCount3) ;
        this.tv_diseaseCount4 = findViewById(R.id.tv_diseaseCount4) ;
        this.tv_diseaseCount5 = findViewById(R.id.tv_diseaseCount5) ;
        this.tv_complaintCount1 = findViewById(R.id.tv_complaintCount1) ;
        this.tv_complaintCount2 = findViewById(R.id.tv_complaintCount2) ;
        this.tv_complaintCount3 = findViewById(R.id.tv_complaintCount3) ;
        this.tv_complaintCount4 = findViewById(R.id.tv_complaintCount4) ;
        this.tv_complaintCount5 = findViewById(R.id.tv_complaintCount5) ;
    }

    //This function is used to get and show the top 5 disease and visitReasons
    public void getTop5(String start, String end){

        tr_disease1.setVisibility(View.GONE);
        tr_disease2.setVisibility(View.GONE);
        tr_disease3.setVisibility(View.GONE);
        tr_disease4.setVisibility(View.GONE);
        tr_disease5.setVisibility(View.GONE);

        tr_complaint1.setVisibility(View.GONE);
        tr_complaint2.setVisibility(View.GONE);
        tr_complaint3.setVisibility(View.GONE);
        tr_complaint4.setVisibility(View.GONE);
        tr_complaint5.setVisibility(View.GONE);

        databaseClinicVisit.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                String stringStart=start;
                String stringEnd=end;
                String vDate;
                String[] parts1;
                String[] parts2;
                String[] parts3;
                String[] checkDiagnosisSplit;
                String[] checkVisitReasonSplit;

                String topDiseaseString="", topComplaintString="";

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
                Integer checker,checker2,i,splitN;
                nameCount object1;

                for (DataSnapshot postSnapshot : snapshot.getChildren()) { //get school year APE keys
                    checker=0;
                    checker2=0;

                    vDate= postSnapshot.child("visitDate").getValue().toString();
                    parts3=vDate.split("-");
                    Calendar dataDate = Calendar.getInstance();
                    dataDate.set(Calendar.YEAR, Integer.parseInt(parts3[0]));
                    dataDate.set(Calendar.MONTH, Integer.parseInt(parts3[1])-1);
                    dataDate.set(Calendar.DAY_OF_MONTH, Integer.parseInt(parts3[2]));

                    //Used to filter based on chosen date range and group according to the diagnosis/visitReason
                    if( ((startDate.before(dataDate)) ||(startDate.equals(dataDate))) && ((dataDate.before(endDate)) ||(dataDate.equals(endDate)))){
                        //DIAGNOSIS
                        if(!postSnapshot.child("diagnosis").getValue().toString().equals("")){
                            Log.d(TAG,postSnapshot.child("diagnosis").getValue().toString());
                        }

                        if(top5DiseaseTemp.size()==0){
                            if(!postSnapshot.child("diagnosis").getValue().toString().equals("")){
                                checkDiagnosisSplit= (postSnapshot.child("diagnosis").getValue().toString()).split(", ");
                                for(splitN=0;splitN<checkDiagnosisSplit.length;splitN++){
                                    object1= new nameCount(checkDiagnosisSplit[splitN],1);
                                    top5DiseaseTemp.add(object1);
                                }

                            }

                        }
                        else{
                            if(!postSnapshot.child("diagnosis").getValue().toString().equals("")){
                                checkDiagnosisSplit= (postSnapshot.child("diagnosis").getValue().toString()).split(", ");
                                for(splitN=0;splitN<checkDiagnosisSplit.length;splitN++){
                                    checker=0;
                                    for(i=0; i<top5DiseaseTemp.size();i++){
                                        if( (checkDiagnosisSplit[splitN]).equalsIgnoreCase(top5DiseaseTemp.get(i).name)){
                                            top5DiseaseTemp.get(i).count=top5DiseaseTemp.get(i).count+1;
                                            checker=1;
                                        }
                                    }
                                    if(checker==0){
                                        object1= new nameCount(checkDiagnosisSplit[splitN],1);
                                        top5DiseaseTemp.add(object1);
                                    }
                                }
                            }
                        }
                        Log.d(TAG,"top 5 Disease Temp"+top5DiseaseTemp);


                        //COMPLAINTS
                        if(top5ComplaintsTemp.size()==0){
                            if(!postSnapshot.child("visitReason").getValue().toString().equals("")){
                                checkVisitReasonSplit= (postSnapshot.child("visitReason").getValue().toString()).split(", ");
                                for(splitN=0;splitN<checkVisitReasonSplit.length;splitN++){
                                    object1= new nameCount(checkVisitReasonSplit[splitN],1);
                                    top5ComplaintsTemp.add(object1);
                                }

                            }
                        }
                        else{
                            if(!postSnapshot.child("visitReason").getValue().toString().equals("")){
                                checkVisitReasonSplit= (postSnapshot.child("visitReason").getValue().toString()).split(", ");
                                for(splitN=0;splitN<checkVisitReasonSplit.length;splitN++){
                                    checker2=0;
                                    for(i=0; i<top5ComplaintsTemp.size();i++){
                                        if( (checkVisitReasonSplit[splitN]).equalsIgnoreCase(top5ComplaintsTemp.get(i).name)){
                                            top5ComplaintsTemp.get(i).count=top5ComplaintsTemp.get(i).count+1;
                                            checker2=1;
                                        }
                                    }
                                    if(checker2==0){
                                        object1= new nameCount(checkVisitReasonSplit[splitN],1);
                                        top5ComplaintsTemp.add(object1);
                                    }
                                }
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

                //DISPLAY TO FRONT END
                if (!top5DiseaseFinal.isEmpty()){
                    for(i=0;i<top5DiseaseFinal.size();i++){

                        switch (i){
                            case 0:
                                tr_disease1.setVisibility(View.VISIBLE);
                                tv_disease1.setText("1. " + top5DiseaseFinal.get(i).name);
                                tv_diseaseCount1.setText(top5DiseaseFinal.get(i).getCount());
                                break;
                            case 1:
                                tr_disease2.setVisibility(View.VISIBLE);
                                tv_disease2.setText("2. " + top5DiseaseFinal.get(i).name);
                                tv_diseaseCount2.setText(top5DiseaseFinal.get(i).getCount());
                                break;
                            case 2:
                                tr_disease3.setVisibility(View.VISIBLE);
                                tv_disease3.setText("3. " + top5DiseaseFinal.get(i).name);
                                tv_diseaseCount3.setText(top5DiseaseFinal.get(i).getCount());
                                break;
                            case 3:
                                tr_disease4.setVisibility(View.VISIBLE);
                                tv_disease4.setText("4. " + top5DiseaseFinal.get(i).name);
                                tv_diseaseCount4.setText(top5DiseaseFinal.get(i).getCount());
                                break;
                            case 4:
                                tr_disease5.setVisibility(View.VISIBLE);
                                tv_disease5.setText("5. " + top5DiseaseFinal.get(i).name);
                                tv_diseaseCount5.setText(top5DiseaseFinal.get(i).getCount());
                                break;
                        }

//                    topDiseaseString=topDiseaseString+top5DiseaseFinal.get(i).name + "(Count: " + top5DiseaseFinal.get(i).count + ")" + "\n";
                    }
                }

                //DISPLAY TO FRONTEND
                if(!top5ComplaintsFinal.isEmpty()){
                    for(i=0;i<top5ComplaintsFinal.size();i++){

                        switch (i){
                            case 0:
                                tr_complaint1.setVisibility(View.VISIBLE);
                                tv_complaint1.setText("1. " + top5ComplaintsFinal.get(i).name);
                                tv_complaintCount1.setText(top5ComplaintsFinal.get(i).getCount());
                                break;
                            case 1:
                                tr_complaint2.setVisibility(View.VISIBLE);
                                tv_complaint2.setText("2. " + top5ComplaintsFinal.get(i).name);
                                tv_complaintCount2.setText(top5ComplaintsFinal.get(i).getCount());
                                break;
                            case 2:
                                tr_complaint3.setVisibility(View.VISIBLE);
                                tv_complaint3.setText("3. " + top5ComplaintsFinal.get(i).name);
                                tv_complaintCount3.setText(top5ComplaintsFinal.get(i).getCount());
                                break;
                            case 3:
                                tr_complaint4.setVisibility(View.VISIBLE);
                                tv_complaint4.setText("4. " + top5ComplaintsFinal.get(i).name);
                                tv_complaintCount4.setText(top5ComplaintsFinal.get(i).getCount());
                                break;
                            case 4:
                                tr_complaint5.setVisibility(View.VISIBLE);
                                tv_complaint5.setText("5. " + top5ComplaintsFinal.get(i).name);
                                tv_complaintCount5.setText(top5ComplaintsFinal.get(i).getCount());
                                break;
                        }

//                    topComplaintString=topComplaintString+ top5ComplaintsFinal.get(i).name + "(Count: " + top5ComplaintsFinal.get(i).count + ")" + "\n";
                    }
                }



//                topDiseaseList.setText(topDiseaseString);
//                topComplaintList.setText(topComplaintString);

                makeSpinnerDisease(top5DiseaseFinal);
                makeSpinnerComplaints(top5ComplaintsFinal);
                //ADD CODE TO SHOW IN FRONTEND
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    public void makeSpinnerDisease(ArrayList<nameCount> top5DiseaseFinal){
        this.spinnerDisease = findViewById(R.id.spinner_disease) ;
        ArrayAdapter<nameCount> adapter = new ArrayAdapter<nameCount>(this, R.layout.spinner_child_diseasesurveillance, top5DiseaseFinal) ;
        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spinnerDisease.setAdapter(adapter);
        spinnerDisease.setSelection(0);

        spinnerDisease.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
               String selectedDisease = top5DiseaseFinal.get(position).name;
               getCountBySectionDisease(selectedDisease);
                getCountBySectionComplaint(selectedDisease);

            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    public void makeSpinnerComplaints(ArrayList<nameCount> top5ComplaintsFinal){
        this.spinnerComplaints = findViewById(R.id.spinner_complaints) ;
        ArrayAdapter<nameCount> adapter = new ArrayAdapter<nameCount>(this, R.layout.spinner_child_diseasesurveillance, top5ComplaintsFinal) ;
        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spinnerComplaints.setAdapter(adapter);
        spinnerComplaints.setSelection(0);

        spinnerComplaints.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedComplaint = top5ComplaintsFinal.get(position).name;
                getCountBySectionComplaint(selectedComplaint);

            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    public void getCountBySectionDisease(String selected){

        String stringStart= input_startDate.getText().toString();
        String stringEnd=input_endDate.getText().toString();
        String grade = studentInfo.getGrade() ;
        String section = studentInfo.getSection() ;

        databaseClinicVisit.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                String vDate;
                String[] parts1;
                String[] parts2;
                String[] parts3;

                ArrayList<nameCount> sectionCount = new ArrayList<nameCount>();

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
                Integer checker,checker2,i,splitN;
                nameCount object1,sec1,sec2,sec3,sec4,sec5,sec6;
                String[] checkDiagnosisSplit;

                switch(grade){
                    case "1":
                        sec1 = new nameCount("Truthfulness",0);
                        sec2 = new nameCount("Sincerity",0);
                        sec3 = new nameCount("Honesty",0);
                        sec4 = new nameCount("Faithfulness",0);
                        sec5 = new nameCount("Humility",0);
                        sec6 = new nameCount("Politeness",0);
                        sectionCount.add(sec1);
                        sectionCount.add(sec2);
                        sectionCount.add(sec3);
                        sectionCount.add(sec4);
                        sectionCount.add(sec5);
                        sectionCount.add(sec6);
                        break;
                    case "2":
                        sec1 = new nameCount("Simplicity",0);
                        sec2 = new nameCount("Charity",0);
                        sec3 = new nameCount("Helpfulness",0);
                        sec4 = new nameCount("Gratefulness",0);
                        sec5 = new nameCount("Gratitude",0);
                        sec6 = new nameCount("Meekness",0);
                        sectionCount.add(sec1);
                        sectionCount.add(sec2);
                        sectionCount.add(sec3);
                        sectionCount.add(sec4);
                        sectionCount.add(sec5);
                        sectionCount.add(sec6);
                        break;
                    case "3" :
                        sec1 = new nameCount("Respect",0);
                        sec2 = new nameCount("Courtesy",0);
                        sec3 = new nameCount("Trust",0);
                        sec4 = new nameCount("Kindness",0);
                        sec5 = new nameCount("Piety",0);
                        sec6 = new nameCount("Prayerfulness",0);
                        sectionCount.add(sec1);
                        sectionCount.add(sec2);
                        sectionCount.add(sec3);
                        sectionCount.add(sec4);
                        sectionCount.add(sec5);
                        sectionCount.add(sec6);
                        break;
                    case "4":
                        sec1 = new nameCount("Unity",0);
                        sec2 = new nameCount("Purity",0);
                        sec3 = new nameCount("Fidelity",0);
                        sec4 = new nameCount("Equality",0);
                        sec5 = new nameCount("Harmony",0);
                        sec6 = new nameCount("Solidarity",0);
                        sectionCount.add(sec1);
                        sectionCount.add(sec2);
                        sectionCount.add(sec3);
                        sectionCount.add(sec4);
                        sectionCount.add(sec5);
                        sectionCount.add(sec6);
                        break;
                    case "5":
                        sec1 = new nameCount("Trustworthiness",0);
                        sec2 = new nameCount("Reliability",0);
                        sec3 = new nameCount("Dependability",0);
                        sec4 = new nameCount("Responsibility",0);
                        sec5 = new nameCount("Serenity",0);
                        sec6 = new nameCount("Flexibility",0);
                        sectionCount.add(sec1);
                        sectionCount.add(sec2);
                        sectionCount.add(sec3);
                        sectionCount.add(sec4);
                        sectionCount.add(sec5);
                        sectionCount.add(sec6);
                        break;
                    case "6":
                        sec1 = new nameCount("Self-Discipline",0);
                        sec2 = new nameCount("Abnegation",0);
                        sec3 = new nameCount("Self-Giving",0);
                        sec4 = new nameCount("Integrity",0);
                        sec5 = new nameCount("Perseverance",0);
                        sec6 = new nameCount("Patience",0);
                        sectionCount.add(sec1);
                        sectionCount.add(sec2);
                        sectionCount.add(sec3);
                        sectionCount.add(sec4);
                        sectionCount.add(sec5);
                        sectionCount.add(sec6);
                        break;

                }


                for (DataSnapshot postSnapshot : snapshot.getChildren()) { //get school year APE keys
                    checker=0;
                    checker2=0;

                    vDate= postSnapshot.child("visitDate").getValue().toString();
                    parts3=vDate.split("-");
                    Calendar dataDate = Calendar.getInstance();
                    dataDate.set(Calendar.YEAR, Integer.parseInt(parts3[0]));
                    dataDate.set(Calendar.MONTH, Integer.parseInt(parts3[1])-1);
                    dataDate.set(Calendar.DAY_OF_MONTH, Integer.parseInt(parts3[2]));

                    //Used to filter based on chosen date range and group according to the diagnosis/visitReason
                    if( ((startDate.before(dataDate)) ||(startDate.equals(dataDate))) && ((dataDate.before(endDate)) ||(dataDate.equals(endDate)))){
                        checkDiagnosisSplit=postSnapshot.child("diagnosis").getValue().toString().split(", ");
                        for(splitN=0;splitN<checkDiagnosisSplit.length;splitN++){
                            if(selected.equalsIgnoreCase(checkDiagnosisSplit[splitN])){
                                for(i=0;i<6;i++){
                                    if(sectionCount.get(i).name.equalsIgnoreCase(postSnapshot.child("section").getValue().toString())){
                                        sectionCount.get(i).count=sectionCount.get(i).count+1;
                                        break;
                                    }
                                }
                            }
                        }
                    }
                }
                makeDiseaseCountChart(sectionCount,selected);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void getCountBySectionComplaint(String selected){

        String stringStart= input_startDate.getText().toString();
        String stringEnd=input_endDate.getText().toString();
        String grade = studentInfo.getGrade() ;
        String section = studentInfo.getSection() ;

        databaseClinicVisit.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                String vDate;
                String[] parts1,parts2,parts3;
                String[] checkVisitReasonSplit;

                ArrayList<nameCount> sectionCount = new ArrayList<nameCount>();

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
                Integer checker,checker2,i,splitN;
                nameCount object1,sec1,sec2,sec3,sec4,sec5,sec6;

                switch(grade){
                    case "1":
                        sec1 = new nameCount("Truthfulness",0);
                        sec2 = new nameCount("Sincerity",0);
                        sec3 = new nameCount("Honesty",0);
                        sec4 = new nameCount("Faithfulness",0);
                        sec5 = new nameCount("Humility",0);
                        sec6 = new nameCount("Politeness",0);
                        sectionCount.add(sec1);
                        sectionCount.add(sec2);
                        sectionCount.add(sec3);
                        sectionCount.add(sec4);
                        sectionCount.add(sec5);
                        sectionCount.add(sec6);
                        break;
                    case "2":
                        sec1 = new nameCount("Simplicity",0);
                        sec2 = new nameCount("Charity",0);
                        sec3 = new nameCount("Helpfulness",0);
                        sec4 = new nameCount("Gratefulness",0);
                        sec5 = new nameCount("Gratitude",0);
                        sec6 = new nameCount("Meekness",0);
                        sectionCount.add(sec1);
                        sectionCount.add(sec2);
                        sectionCount.add(sec3);
                        sectionCount.add(sec4);
                        sectionCount.add(sec5);
                        sectionCount.add(sec6);
                        break;
                    case "3" :
                        sec1 = new nameCount("Respect",0);
                        sec2 = new nameCount("Courtesy",0);
                        sec3 = new nameCount("Trust",0);
                        sec4 = new nameCount("Kindness",0);
                        sec5 = new nameCount("Piety",0);
                        sec6 = new nameCount("Prayerfulness",0);
                        sectionCount.add(sec1);
                        sectionCount.add(sec2);
                        sectionCount.add(sec3);
                        sectionCount.add(sec4);
                        sectionCount.add(sec5);
                        sectionCount.add(sec6);
                        break;
                    case "4":
                        sec1 = new nameCount("Unity",0);
                        sec2 = new nameCount("Purity",0);
                        sec3 = new nameCount("Fidelity",0);
                        sec4 = new nameCount("Equality",0);
                        sec5 = new nameCount("Harmony",0);
                        sec6 = new nameCount("Solidarity",0);
                        sectionCount.add(sec1);
                        sectionCount.add(sec2);
                        sectionCount.add(sec3);
                        sectionCount.add(sec4);
                        sectionCount.add(sec5);
                        sectionCount.add(sec6);
                        break;
                    case "5":
                        sec1 = new nameCount("Trustworthiness",0);
                        sec2 = new nameCount("Reliability",0);
                        sec3 = new nameCount("Dependability",0);
                        sec4 = new nameCount("Responsibility",0);
                        sec5 = new nameCount("Serenity",0);
                        sec6 = new nameCount("Flexibility",0);
                        sectionCount.add(sec1);
                        sectionCount.add(sec2);
                        sectionCount.add(sec3);
                        sectionCount.add(sec4);
                        sectionCount.add(sec5);
                        sectionCount.add(sec6);
                        break;
                    case "6":
                        sec1 = new nameCount("Self-Discipline",0);
                        sec2 = new nameCount("Abnegation",0);
                        sec3 = new nameCount("Self-Giving",0);
                        sec4 = new nameCount("Integrity",0);
                        sec5 = new nameCount("Perseverance",0);
                        sec6 = new nameCount("Patience",0);
                        sectionCount.add(sec1);
                        sectionCount.add(sec2);
                        sectionCount.add(sec3);
                        sectionCount.add(sec4);
                        sectionCount.add(sec5);
                        sectionCount.add(sec6);
                        break;

                }


                for (DataSnapshot postSnapshot : snapshot.getChildren()) { //get school year APE keys
                    checker=0;
                    checker2=0;

                    vDate= postSnapshot.child("visitDate").getValue().toString();
                    parts3=vDate.split("-");
                    Calendar dataDate = Calendar.getInstance();
                    dataDate.set(Calendar.YEAR, Integer.parseInt(parts3[0]));
                    dataDate.set(Calendar.MONTH, Integer.parseInt(parts3[1])-1);
                    dataDate.set(Calendar.DAY_OF_MONTH, Integer.parseInt(parts3[2]));

                    //Used to filter based on chosen date range and group according to the diagnosis/visitReason
                    if( ((startDate.before(dataDate)) ||(startDate.equals(dataDate))) && ((dataDate.before(endDate)) ||(dataDate.equals(endDate)))){
                        checkVisitReasonSplit=postSnapshot.child("visitReason").getValue().toString().split(", ");
                        for(splitN=0;splitN<checkVisitReasonSplit.length;splitN++){
                            if(selected.equalsIgnoreCase(checkVisitReasonSplit[splitN])){
                                for(i=0;i<6;i++){
                                    if(sectionCount.get(i).name.equalsIgnoreCase(postSnapshot.child("section").getValue().toString())){
                                        sectionCount.get(i).count=sectionCount.get(i).count+1;
                                    }
                                }
                            }
                        }
                    }
                }
                makeComplaintCountChart(sectionCount,selected);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void makeDiseaseCountChart(ArrayList<nameCount> sectionCount, String selected){
        TextView tv_chartDiseaseTitle ;
        TextView tv_chartDiseaseTitleDate ;
        tv_chartDiseaseTitle = findViewById(R.id.tv_chartDiseaseTitle) ;
        tv_chartDiseaseTitle.setVisibility(View.VISIBLE);
        tv_chartDiseaseTitleDate = findViewById(R.id.tv_chartDiseaseTitleDate) ;
        tv_chartDiseaseTitleDate.setVisibility(View.VISIBLE);

        ClassDateConvert fromDate, toDate ;
        fromDate = new ClassDateConvert(0, input_startDate.getText().toString()) ;
        toDate = new ClassDateConvert(0, input_endDate.getText().toString()) ;
        tv_chartDiseaseTitle.setText( spinnerDisease.getSelectedItem().toString() + " Count In Grade " + studentInfo.getGrade());
        tv_chartDiseaseTitleDate.setText(fromDate.getReversed() + " - " + toDate.getReversed());

        String grade = studentInfo.getGrade() ;
        int i;
        ArrayList<BarEntry> diseaseCountData = new ArrayList<BarEntry>();
        ArrayList<String> diseaseNames = new ArrayList<String>();
        ArrayList<BarEntry> chosenDiseaseCountData = new ArrayList<BarEntry>();
        ArrayList<String> chosenDiseaseNames = new ArrayList<String>();
        ArrayList<String> finalNames = new ArrayList<String>();

        BarChart diseaseCountBarChart = findViewById(R.id.barChart_diseaseCount);
        Description desc = new Description();
        desc.setText(selected+ " in Grade " + grade);

        for(i=0;i<sectionCount.size();i++){
            if(sectionCount.get(i).name.equals(studentInfo.getSection())){
                Log.d(TAG,"sectionCount section:"+ sectionCount.get(i).name);
                Log.d(TAG,"child section:"+ studentInfo.getSection());

                chosenDiseaseCountData.add(new BarEntry(i,sectionCount.get(i).count));
            }
            else{
                diseaseCountData.add(new BarEntry(i,sectionCount.get(i).count));
            }
            diseaseNames.add(sectionCount.get(i).name);
        }

        //finalNames.add(chosenDiseaseNames.get(0));
        for(i=0;i<diseaseNames.size();i++){
           finalNames.add(diseaseNames.get(i));
        }

        BarDataSet diseaseCount = new BarDataSet(diseaseCountData,"Disease count in other sections Grade " + grade);
        diseaseCount.setColor(Color.parseColor("#F4C22F"));
        BarDataSet chosenDiseaseCount = new BarDataSet(chosenDiseaseCountData,"Disease count in child's section");
        chosenDiseaseCount.setColor(Color.parseColor("#f282a7"));
        BarData barData = new BarData();
        barData.addDataSet(chosenDiseaseCount);
        barData.addDataSet(diseaseCount);

        XAxis xAxis = diseaseCountBarChart.getXAxis();
        xAxis.setValueFormatter(new IndexAxisValueFormatter(diseaseNames));
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);

        diseaseCountBarChart.setDescription(desc);
        diseaseCountBarChart.setData(barData);
        diseaseCountBarChart.invalidate();


    }

    public void makeComplaintCountChart(ArrayList<nameCount> sectionCount,String selected){
        TextView tv_chartComplaintTitle ;
        TextView tv_chartComplaintTitleDate ;
        tv_chartComplaintTitle = findViewById(R.id.tv_chartComplaintTitle) ;
        tv_chartComplaintTitle.setVisibility(View.VISIBLE);
        tv_chartComplaintTitleDate = findViewById(R.id.tv_chartComplaintTitleDate) ;
        tv_chartComplaintTitleDate.setVisibility(View.VISIBLE);
        ClassDateConvert fromDate, toDate ;
        fromDate = new ClassDateConvert(0, input_startDate.getText().toString()) ;
        toDate = new ClassDateConvert(0, input_endDate.getText().toString()) ;
        tv_chartComplaintTitle.setText( spinnerComplaints.getSelectedItem().toString() + " Complaints In Grade " + studentInfo.getGrade());
        tv_chartComplaintTitleDate.setText( fromDate.getReversed() + " - " + toDate.getReversed());

        String grade = studentInfo.getGrade() ;
        int i,j;
        ArrayList<BarEntry> complaintCountData = new ArrayList<BarEntry>();
        ArrayList<String> complaintNames = new ArrayList<String>();
        ArrayList<BarEntry> chosenComplaintCountData = new ArrayList<BarEntry>();
        ArrayList<String> chosenComplaintNames = new ArrayList<String>();
        ArrayList<String> finalNames = new ArrayList<String>();


        BarChart complaintCountBarChart = findViewById(R.id.barChart_complaintCount);
        Description desc = new Description();
        desc.setText(selected + " in Grade " + grade);

        for(i=0;i<sectionCount.size();i++){
            if(sectionCount.get(i).name.equals(studentInfo.getSection())){
                chosenComplaintCountData.add(new BarEntry(i,sectionCount.get(i).count));
            }
            else{
                complaintCountData.add(new BarEntry(i,sectionCount.get(i).count));
            }
            complaintNames.add(sectionCount.get(i).name);
        }

        BarDataSet complaintCount = new BarDataSet(complaintCountData,"Complaint count in other sections Grade " + grade);
        complaintCount.setColor(Color.parseColor("#F4C22F"));
        BarDataSet chosenComplaintCount = new BarDataSet(chosenComplaintCountData,"Complaint count in child's section");
        chosenComplaintCount.setColor(Color.parseColor("#f282a7"));
        BarData barData = new BarData();
        barData.addDataSet(chosenComplaintCount);
        barData.addDataSet(complaintCount);

        XAxis xAxis = complaintCountBarChart.getXAxis();
        xAxis.setValueFormatter(new IndexAxisValueFormatter(complaintNames));
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);

        complaintCountBarChart.setDescription(desc);
        complaintCountBarChart.setData(barData);
        complaintCountBarChart.invalidate();


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

}