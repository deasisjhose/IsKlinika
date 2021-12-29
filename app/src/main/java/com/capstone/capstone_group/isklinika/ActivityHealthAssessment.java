package com.capstone.capstone_group.isklinika;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.button.MaterialButtonToggleGroup;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
/**To do
 * 1. Indicators line 55, 119, 192
 * 2. Reuse some of the old codes na lang from capstone mobile
 * */
public class ActivityHealthAssessment extends AppCompatActivity {

    FirebaseDatabase db = FirebaseDatabase.getInstance();   // getting real time database
    public DatabaseReference databaseApe = db.getReference("studentHealthHistory");
    public DatabaseReference databaseStudentInfo = db.getReference("studentInfo");
    public DatabaseReference databaseSClinicUser = db.getReference("clinicUsers");

    private ArrayList<ClassStudentInfo> children ;
    private ClassStudentInfo studentInfo ;
    private Intent intent ;
    private int checkActive ;
    private String userType, studentId ;
    public String TAG="HEALTHASSESSMENT//";

    //module tab children
    private ShapeableImageView shapeImg_moduleProfileChild ;
    private MaterialButtonToggleGroup mbtg_moduleChildren, mbtg_healthAssess  ;
    private TextView tv_moduleFullName ;
    private Spinner spinner_childNameModules ;

    //growthChart tab
    private LinearLayout layout_growthChart ;
    private Spinner spinner_growthOptions ;


    //ape tab
    private LinearLayout layout_ape ;
    private RecyclerView recycle_ape ;
    private AdapterHealthAssessAPE adapterHealthAssessAPE ;
    private String clinicUserName ;
    private ExpandableListView expand_ape ;
    private ExpandableListAdapter expandApeAdapter ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_health_assessment);

        checkActive = 10 ;
        intent = getIntent() ;
        this.userType = intent.getStringExtra("userType") ;
        buildBar();
        buildViews();
        checkUser();
    }

    public void buildBar(){
        this.tv_moduleFullName = findViewById(R.id.tv_moduleFullName) ;

        this.mbtg_healthAssess = findViewById(R.id.mbtg_healthAssess) ;

        mbtg_healthAssess.addOnButtonCheckedListener(new MaterialButtonToggleGroup.OnButtonCheckedListener() {
            @Override
            public void onButtonChecked(MaterialButtonToggleGroup group, int checkedId, boolean isChecked) {

                if (isChecked) {
                    MaterialButton buttonCheck = findViewById(checkedId);
                    buttonCheck.setTypeface(null, Typeface.BOLD);
                    if(checkedId == R.id.mBtn_growthChart){
                        checkActive = 10 ;
                        layout_growthChart.setVisibility(View.VISIBLE);
                        layout_ape.setVisibility(View.GONE);
                    }else if(checkedId == R.id.mBtn_ape){
                        checkActive = 20 ;
                        layout_growthChart.setVisibility(View.GONE);
                        layout_ape.setVisibility(View.VISIBLE);
                        retrieveDataAPE();
                    }else if(checkedId == R.id.mBtn_dental){
                        checkActive = 30 ;
                        layout_growthChart.setVisibility(View.GONE);
                        layout_ape.setVisibility(View.GONE);
                    }

                } else {
                    MaterialButton buttonCheck = findViewById(checkedId);
                    buttonCheck.setTypeface(null, Typeface.NORMAL);
                }
            }
        });
        mbtg_healthAssess.check(0);
    }

    public void buildViews(){

        //checkActive == 10 GrowthChart
        this.layout_growthChart = findViewById(R.id.layout_growthChart) ;
        makeGrowthChartSpinner();

        this.layout_ape = findViewById(R.id.layout_ape) ;

    }

    public void checkUser() {

        switch (userType){
            case "Student":
                this.studentInfo = intent.getParcelableExtra("studentInfo") ;
                tv_moduleFullName.setText(studentInfo.getFullName());
                studentId = studentInfo.getIdNum() ;
                //retrieveDataAPE();
                makeGrowthChart(studentId);
                break;
            case "Parent":
                this.children = intent.getParcelableArrayListExtra("children") ;
                tv_moduleFullName.setText(children.get(0).getFullName());
                this.mbtg_moduleChildren = findViewById(R.id.mbtg_moduleChildren) ;
//                mbtg_moduleChildren.setVisibility(View.VISIBLE);
                makeSpinnerChildren();
//                dataInToggleChildren();
                break;
        }
    }

    //This function is used to make the toggle buttons of the children
    public void makeSpinnerChildren(){

        this.spinner_childNameModules = findViewById(R.id.spinner_childNameModules) ;
        spinner_childNameModules.setVisibility(View.VISIBLE);
        ArrayAdapter<ClassStudentInfo> adapter = new ArrayAdapter<>(this, R.layout.spinner_child_healthassess, children) ;
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
                        makeGrowthChart(studentId);
                        break;
                    case 20:
                        retrieveDataAPE();
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



    //checkActive == 10
    //functions
    public void makeGrowthChartSpinner(){

        this.spinner_growthOptions = findViewById(R.id.spinner_growthOptions) ;
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.charts,R.layout.spinner_healthassess) ;
        adapter.setDropDownViewResource(R.layout.spinner_immune_down);
        spinner_growthOptions.setAdapter(adapter);
        spinner_growthOptions.setSelection(0);
//        Log.d(TAG, "onItemSelected: selectedSort = " + spinner_growthChart.getSelectedItem());

        spinner_growthOptions.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.d(TAG, "onItemSelected: selectedSort = " + spinner_growthOptions.getSelectedItem());
                makeGrowthChart(studentId);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    public void makeChart(ArrayList<Integer> dataAge, ArrayList<Double> dataHeight, ArrayList<Double> dataWeight, ArrayList<Double> dataBMI ){
        Log.d(TAG,"age1: " + dataAge.get(0));
        LineChart lineChart = findViewById(R.id.lineChart_growth);
        ArrayList<Entry> heightSet = new ArrayList<>();
        ArrayList<Entry> weightSet = new ArrayList<>();
        ArrayList<Entry> bmiSet = new ArrayList<>();
        Description desc = new Description();

        for (int x = 0; x < dataAge.size(); x++) { //x axis
            for (int y = 0; y < dataHeight.size(); y++) { //y axis
                if (x == y) { //to match what height is for a certain age
                    heightSet.add(new Entry(dataAge.get(x).intValue(), dataHeight.get(y).floatValue()));
                }
            }
            for (int w = 0; w < dataWeight.size(); w++) { //y axis
                if (x == w) { //to match what weight is for a certain age
                    weightSet.add(new Entry(dataAge.get(x).intValue(), dataWeight.get(w).floatValue()));
                }
            }
            for (int b = 0; b < dataBMI.size(); b++) { //y axis
                if (x == b) { //to match what weight is for a certain age
                    bmiSet.add(new Entry(dataAge.get(x).intValue(), dataBMI.get(b).floatValue()));
                }
            }
        }

        String selected = spinner_growthOptions.getSelectedItem().toString();
        Log.d(TAG, selected);
        if(selected.equalsIgnoreCase("BMI")){
            Log.d(TAG,"inside BMI");
            LineDataSet bmiDataSet = new LineDataSet(bmiSet, "BMI Data");
            bmiDataSet.setColor(Color.BLUE);
            bmiDataSet.setValueTextSize(9);

            XAxis xAxis = lineChart.getXAxis();
            YAxis yAxisLeft = lineChart.getAxisLeft();
            YAxis yAxisRight = lineChart.getAxisRight();

            xAxis.setAxisMinimum(5);
            xAxis.setAxisMaximum(13);
            xAxis.setLabelCount(dataHeight.size());
            xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);


            yAxisLeft.setAxisMaximum(20);
            yAxisLeft.setAxisMinimum(5);
            yAxisLeft.setTextColor(Color.BLUE);
            yAxisRight.setAxisMaximum(20);
            yAxisRight.setAxisMinimum(5);
            yAxisRight.setTextColor(Color.BLUE);

            LineData lineData = new LineData(bmiDataSet);
            //To set which Yaxis is the line dependent on:
            //bmiDataSet.setAxisDependency(YAxis.AxisDependency.LEFT);

            desc.setText("BMI by Age");
            desc.setTextColor(Color.BLACK);
            desc.setTextSize(10);
            lineChart.setBackgroundColor(Color.parseColor("#FFF4F1"));
            lineChart.setDrawGridBackground(true);
            lineChart.setBorderColor(Color.BLACK);
            lineChart.setDescription(desc);

            lineChart.setData(lineData);
            lineChart.invalidate();
        }
        else{
            Log.d(TAG,"inside height and weight");
            LineDataSet heightDataSet = new LineDataSet(heightSet, "Height Data");
            LineDataSet weightDataSet = new LineDataSet(weightSet, "Weight Data");

            heightDataSet.setColor(Color.BLUE);
            heightDataSet.setValueTextSize(9);
            weightDataSet.setColor(Color.RED);
            weightDataSet.setValueTextSize(9);

            XAxis xAxis = lineChart.getXAxis();
            YAxis yAxisLeft = lineChart.getAxisLeft();
            YAxis yAxisRight = lineChart.getAxisRight();

            xAxis.setAxisMinimum(5);
            xAxis.setAxisMaximum(13);
            xAxis.setLabelCount(dataHeight.size());
            xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);

            //weight in kg
            yAxisLeft.setAxisMaximum(60);
            yAxisLeft.setAxisMinimum(0);
            yAxisLeft.setTextColor(Color.RED);

            //height in cm
            yAxisRight.setAxisMaximum(200);
            yAxisRight.setAxisMinimum(0);
            yAxisRight.setTextColor(Color.BLUE);

            LineData lineData = new LineData(heightDataSet,weightDataSet);
            //To set which Yaxis is the line dependent on:
            heightDataSet.setAxisDependency(YAxis.AxisDependency.RIGHT);
            weightDataSet.setAxisDependency(YAxis.AxisDependency.LEFT);

            desc.setText("Height and Weight by Age");
            desc.setTextColor(Color.BLACK);
            desc.setTextSize(10);
            lineChart.setBackgroundColor(Color.parseColor("#FFF4F1"));
            lineChart.setDrawGridBackground(true);
            lineChart.setBorderColor(Color.BLACK);
            lineChart.setDescription(desc);

            lineChart.setData(lineData);
            lineChart.invalidate();
        }

    }

    //checkActive == 20
    //This function is used to create the expandable list view
    public void dataInApeExpand(ArrayList<ClassApe> apeList){
        List<String> groupListSY = new ArrayList<>() ;
        Map<String, ClassApe> apeMap ;
        for(int i = 0 ; i < apeList.size() ; i++){
            groupListSY.add(apeList.get(i).getSchoolYear()) ;
        }
        apeMap = new HashMap<String, ClassApe>() ;
        int i  ;
        for(i = 0 ; i < groupListSY.size() ; i++){

            for(int j = 0 ; j < apeList.size() ; j++){
                if(groupListSY.get(i).equals(apeList.get(j).getSchoolYear())){
                    Log.d(TAG, "dataInApeExpand: index of group = ");
                    apeMap.put(groupListSY.get(i), apeList.get(j)) ;
                    break;
                }
            }
        }


        this.expand_ape = findViewById(R.id.expand_ape) ;
        this.expandApeAdapter = new AdapaterExpandApe(this, groupListSY, apeMap) ;
        expand_ape.setAdapter(expandApeAdapter);
        expand_ape.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            int lastExpandedPosition = -1;
            @Override
            public void onGroupExpand(int i) {
                if(lastExpandedPosition != -1 && i != lastExpandedPosition){
                    expand_ape.collapseGroup(lastExpandedPosition);
                }
                lastExpandedPosition = i;
            }
        });
        expand_ape.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView expandableListView, View view, int i, int i1, long l) {

                return true;
            }
        });
    }

    //This function is used to retrieve the data in ape
    public void retrieveDataAPE(){
        ArrayList<ClassApe> apeList = new ArrayList<>();
        Log.d(TAG, "retrieveDataAPE: studentId = " + studentId);

        databaseApe.child(studentId).child("ape").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(!apeList.isEmpty())
                    apeList.clear();

                for (DataSnapshot postSnapshot: snapshot.getChildren()){
                    ClassApe ape  = new ClassApe();
                        ape.setAge(postSnapshot.child("age").getValue().toString());
                        ape.setAllergies(postSnapshot.child("allergies").getValue().toString());
                        ape.setApeDate(postSnapshot.child("apeDate").getValue().toString());
                        ape.setAssess(postSnapshot.child("assess").getValue().toString());
                        ape.setBmi(postSnapshot.child("bmi").getValue().toString());
                        ape.setBmiStatus(postSnapshot.child("bmiStatus").getValue().toString());
                        ape.setClinician(postSnapshot.child("clinician").getValue().toString());
                        ape.setConcern(postSnapshot.child("concern").getValue().toString());
                        ape.setDiastolic(postSnapshot.child("diastolic").getValue().toString());
                        ape.setHeight(postSnapshot.child("height").getValue().toString());
                        ape.setId(postSnapshot.child("id").getValue().toString());
                        ape.setMedProb(postSnapshot.child("medProb").getValue().toString());
                        ape.setName(postSnapshot.child("name").getValue().toString());
                        ape.setOdGlasses(postSnapshot.child("odGlasses").getValue().toString());
                        ape.setOdVision(postSnapshot.child("odVision").getValue().toString());
                        ape.setOsGlasses(postSnapshot.child("osGlasses").getValue().toString());
                        ape.setOsVision(postSnapshot.child("osVision").getValue().toString());
                        ape.setPr(postSnapshot.child("pr").getValue().toString());
                        ape.setRr(postSnapshot.child("rr").getValue().toString());
                        ape.setSchoolYear(postSnapshot.child("schoolYear").getValue().toString());
                        ape.setSf(postSnapshot.child("sf").getValue().toString());
                        ape.setSystolic(postSnapshot.child("systolic").getValue().toString());
                        ape.setTemp(postSnapshot.child("temp").getValue().toString());
                        ape.setWeight(postSnapshot.child("weight").getValue().toString());

                        apeList.add(ape) ;
                }
                if(!apeList.isEmpty()){
                    Log.d(TAG, "onDataChange: apeSIZE = " + apeList.size());
                    dataInApeExpand(apeList);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        }) ;
    }

    public void makeGrowthChart(String id){
        ArrayList<Integer> dataAge = new ArrayList<>();
        ArrayList<Double> dataHeight = new ArrayList<>();
        ArrayList<Double> dataWeight = new ArrayList<>();
        ArrayList<Double> dataBMI = new ArrayList<>();

        LineChart lineChart = findViewById(R.id.lineChart_growth);
        ArrayList<Entry> heightSet = new ArrayList<>();
        ArrayList<Entry> weightSet = new ArrayList<>();
        ArrayList<Entry> bmiSet = new ArrayList<>();
        Description desc = new Description();

        Log.d(TAG,id);

        databaseApe.child(id).child("ape").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot postSnapshot : snapshot.getChildren()) { //get school year APE keys
                    //Log.d(TAG, "postSnapshot Key: "+postSnapshot.getKey());
                    //Log.d(TAG, "postSnapshot Key: "+postSnapshot.getValue());
                    Integer age = Integer.parseInt(postSnapshot.child("age").getValue().toString());
                    double height = Double.parseDouble(postSnapshot.child("height").getValue().toString());
                    double weight = Double.parseDouble(postSnapshot.child("weight").getValue().toString());
                    double bmi = Double.parseDouble(postSnapshot.child("bmi").getValue().toString());
                    dataAge.add(age);
                    dataHeight.add(height);
                    dataWeight.add(weight);
                    dataBMI.add(bmi);
                }
                Log.d(TAG,"age size: " + dataAge.size());
                if(dataAge.isEmpty() || dataHeight.isEmpty()){

                    lineChart.setNoDataText("No Data Yet");
                    lineChart.setNoDataTextColor(Color.BLACK);

                    lineChart.invalidate();
                    lineChart.clear();
                }
                else{
                    makeChart(dataAge,dataHeight,dataWeight,dataBMI);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

}