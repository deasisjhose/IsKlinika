package com.capstone.capstone_group.isklinika;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.button.MaterialButtonToggleGroup;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
/*
This is the Mediical History module
 */
public class ActivityMedicalHistory extends AppCompatActivity implements View.OnClickListener{

    FirebaseDatabase db = FirebaseDatabase.getInstance();   // getting real time database
    public DatabaseReference databaseReference = db.getReference("studentInfo") ;
    public DatabaseReference databaseStudentHealthHistory = db.getReference("studentHealthHistory");

    private ArrayList<ClassStudentInfo> children ;
    private ClassStudentInfo studentInfo ;
    private Intent intent ;
    private int checkActive ;
    private String userType, studentId ;
    public String TAG="MEDICALHISTORY//";
    private ImageButton btn_back ;
    //module tab children
    private ShapeableImageView shapeImg_moduleProfileChild ;
    private MaterialButtonToggleGroup mbtg_medicalHistory ;
    private Spinner spinner_childNameModules ;
    private TextView tv_moduleFullName ;

    private FloatingActionButton fbtn_medicalHistory ;

    //past illnesses
    private LinearLayout layout_pastIllness ;
    private ExpandableListView expand_pastIllness ;
    private ExpandableListAdapter expandPastIllnessAdapter ;
    private RecyclerView recycle_past_illness ;
    private ArrayList<ClassPastIllness> illnessHistoryArrayList ;
    private TextView tv_cvFromDate, tv_cvToDate ;
    private Spinner spinner_sort ;
    private MaterialDatePicker materialDatePicker ;
    private int selectedDate ;

    //allergy tab
    private LinearLayout layout_allergies ;
    private RecyclerView recycle_medicalAllergy ;
    private ArrayList<ClassAllergy> allergyArrayList ;
    private ImageButton ibtn_editAllergy ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medical_history);

        checkActive = 30 ;
        intent = getIntent() ;
        this.userType = intent.getStringExtra("userType") ;
        buildBar();
        buildView();
        checkUser();
    }

    public void buildBar(){
        this.tv_moduleFullName = findViewById(R.id.tv_moduleFullName) ;
        this.btn_back = findViewById(R.id.btn_back);
        btn_back.setOnClickListener(view -> finish());
    }

    public void buildView(){
        this.fbtn_medicalHistory = findViewById(R.id.fbtn_medicalHistory) ;
        fbtn_medicalHistory.setOnClickListener(this);

        //pastIllness
        this.layout_pastIllness = findViewById(R.id.layout_pastIllness) ;
        this.tv_cvFromDate = findViewById(R.id.tv_cvFromDate) ;
        this.tv_cvToDate = findViewById(R.id.tv_cvToDate) ;
        this.spinner_sort = findViewById(R.id.spinner_sort2) ;

        tv_cvFromDate.setOnClickListener(this);
        tv_cvToDate.setOnClickListener(this);

        Calendar cal = Calendar.getInstance();
        int day = cal.get(Calendar.DAY_OF_MONTH);
        int currMonth=cal.get(Calendar.MONTH);
        int currYear=cal.get(Calendar.YEAR);

        String dayy = Integer.toString(day) ;
        String month = Integer.toString(currMonth) ;
        if(dayy.length() == 1){
            dayy = "0" + day ;
        }
        if(month.length() == 1){
            month = "0" + (currMonth+1) ;
        }

        tv_cvToDate.setText(currYear + "-" + month + "-" + dayy);

        makeSpinnerSort();
        MaterialDatePicker.Builder builder = MaterialDatePicker.Builder.datePicker() ;
        builder.setTitleText("Select Past Illness Date (MM-DD-YY)") ;
        builder.setTheme(R.style.ThemeOverlay_App_DatePicker_MedicalHistory) ;
        builder.setSelection(MaterialDatePicker.todayInUtcMilliseconds()) ;


        this.materialDatePicker = builder.build() ;
        materialDatePicker.addOnPositiveButtonClickListener(selection -> {

            ClassDateConvert dateConvert = new ClassDateConvert(materialDatePicker.getHeaderText()) ;

            switch (selectedDate){
                case 10:
                    tv_cvFromDate.setText(dateConvert.getConverted());
                    if(!tv_cvToDate.getText().toString().equals("YYYY-MM-DD"))
                        retrieveDataPastIllness() ;
                    break;
                case 20:
                    tv_cvToDate.setText(dateConvert.getConverted());
                    if(!tv_cvFromDate.getText().toString().equals("YYYY-MM-DD"))
                        retrieveDataPastIllness() ;
                    break;
            }
        }) ;


        //allergy tab
        this.layout_allergies = findViewById(R.id.layout_allergies) ;
        this.ibtn_editAllergy = findViewById(R.id.ibtn_editAllergy) ;

        ibtn_editAllergy.setOnClickListener(this);

        this.mbtg_medicalHistory = findViewById(R.id.mbtg_medicalHistory) ;

        mbtg_medicalHistory.addOnButtonCheckedListener(new MaterialButtonToggleGroup.OnButtonCheckedListener() {
            @Override
            public void onButtonChecked(MaterialButtonToggleGroup group, int checkedId, boolean isChecked) {

                if (isChecked) {
                    MaterialButton buttonCheck = findViewById(checkedId);
                    buttonCheck.setTypeface(null, Typeface.BOLD);
                    if(checkedId == R.id.mBtn_pastIllness){
                        checkActive = 10 ;
                        layout_allergies.setVisibility(View.GONE);
                        layout_pastIllness.setVisibility(View.VISIBLE);
                        retrieveDataPastIllness() ;
                    }else if(checkedId == R.id.mBtn_allergies){
                        checkActive = 20 ;
                        layout_pastIllness.setVisibility(View.GONE);
                        layout_allergies.setVisibility(View.VISIBLE);
                        retrieveDataMAllergies(studentId);
                    }
                    else if(checkedId == R.id.mBtn_Illness){
                        checkActive = 30 ;
                        layout_allergies.setVisibility(View.GONE);
                        layout_pastIllness.setVisibility(View.VISIBLE);
                        retrieveDataPastIllness() ;
                    }
                } else {
                    MaterialButton buttonCheck = findViewById(checkedId);
                    buttonCheck.setTypeface(null, Typeface.NORMAL);
                }
            }
        });
        mbtg_medicalHistory.check(0);

    }

    public void checkUser() {

        switch (userType){
            case "Student":
            case "Clinician":
                if(userType.equals("Clinician")){
                    ImageView img_logoHealthAssess = findViewById(R.id.img_logoHealthAssess) ;
                    if (intent.getStringExtra("selected").equals("allergy")){
                        img_logoHealthAssess.setImageResource(R.drawable.clinician_allergy);

                    }else
                        img_logoHealthAssess.setImageResource(R.drawable.clinician_medical_history);

                }
                this.studentInfo = intent.getParcelableExtra("studentInfo") ;
                tv_moduleFullName.setText(studentInfo.getFullName());
                fbtn_medicalHistory.setVisibility(View.GONE);
                ibtn_editAllergy.setVisibility(View.GONE);
                studentId = studentInfo.getIdNum() ;
                retrieveDataPastIllness() ;
                break;
            case "Parent":
                this.children = intent.getParcelableArrayListExtra("children") ;
//                tv_moduleFullName.setText(children.get(0).getFullName());
                makeSpinnerChildren();

                break;
        }
    }

    //This function is used to make the toggle buttons of the children
    public void makeSpinnerChildren(){
        this.spinner_childNameModules = findViewById(R.id.spinner_childNameModules) ;
        spinner_childNameModules.setVisibility(View.VISIBLE);
        ArrayAdapter<ClassStudentInfo> adapter = new ArrayAdapter<>(this, R.layout.spinner_child_medicalhistory, children) ;
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
                        Log.d(TAG, "onItemSelected: retrievingPastIllness from child spinner");
                        retrieveDataPastIllness() ;
                        break;
                    case 20:
                        retrieveDataMAllergies(studentId);
                        break;
                    case 30:
                        retrieveDataPastIllness() ;
                        break;

                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    //This method is used to retrieve the past illness records
    public void retrieveDataPastIllness(){
        this.illnessHistoryArrayList = new ArrayList<>() ;
        illnessHistoryArrayList.clear();
        databaseStudentHealthHistory.child(studentId).child("pastIllness").orderByChild("startDate").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(illnessHistoryArrayList.size() != 0){
                    illnessHistoryArrayList.clear();
                }

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
                    endDate.set(Calendar.DAY_OF_MONTH, Integer.parseInt(parts2[2]) + 1);
                }

                for (DataSnapshot postSnapshot: snapshot.getChildren()){
                    ClassPastIllness illnessHistory = new ClassPastIllness() ;

                    if(!tv_cvFromDate.getText().toString().equals("YYYY-MM-DD") && !tv_cvToDate.getText().toString().equals("YYYY-MM-DD")) {
                        vDate = postSnapshot.child("endDate").getValue().toString();
                        parts3 = vDate.split("-");
                        Calendar dataDate = Calendar.getInstance();
                        dataDate.set(Calendar.YEAR, Integer.parseInt(parts3[0]));
                        dataDate.set(Calendar.MONTH, Integer.parseInt(parts3[1]) - 1);
                        dataDate.set(Calendar.DAY_OF_MONTH, Integer.parseInt(parts3[2]));

                        if (((startDate.before(dataDate)) || (startDate.equals(dataDate))) && ((dataDate.before(endDate)) || (dataDate.equals(endDate)))) {
                            illnessHistory = postSnapshot.getValue(ClassPastIllness.class) ;
                            illnessHistory.setKey(postSnapshot.getKey());
                            illnessHistoryArrayList.add(illnessHistory) ;
                        }
                    }else{
                        illnessHistory = postSnapshot.getValue(ClassPastIllness.class) ;
                        illnessHistory.setKey(postSnapshot.getKey());
//                        Log.d(TAG, "onDataChange: illnessHistory = " + illnessHistory.toString());
                        illnessHistoryArrayList.add(illnessHistory) ;
                    }
                }

                if (illnessHistoryArrayList.isEmpty())
                    Toast.makeText(ActivityMedicalHistory.this, "No data in past illnesses .", Toast.LENGTH_LONG).show();

//                Log.d(TAG, "onDataChange: checkActive sa loob ng retrieve == " + checkActive);

                    dataInPastIllness(illnessHistoryArrayList) ;

            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        }) ;
//        Log.d(TAG, "retrieveDataMedicationsList: total list = " + medHistoryList.size());
    }



    //This method is used to display the retrieved past illness records to the recyclerview
    public void dataInPastIllness(ArrayList<ClassPastIllness> allergyArrayList){
        if(spinner_sort.getSelectedItem().toString().equals("Latest")){
            Collections.reverse(allergyArrayList);
//            Log.d(TAG, "dataInExpandPastIllness: size = " +allergyArrayList.size());
        }

        ArrayList<ClassPastIllness> illnessesArrayList  = new ArrayList<>();
        illnessesArrayList.clear();
        for(int i = 0 ; i < allergyArrayList.size() ; i++){
            if(allergyArrayList.get(i).getStatus().equalsIgnoreCase("Recovered") && checkActive == 10)
                illnessesArrayList.add(allergyArrayList.get(i)) ;
            else if ((allergyArrayList.get(i).getStatus().equalsIgnoreCase("Ongoing") || allergyArrayList.get(i).getStatus().equalsIgnoreCase("Chronic")) && checkActive == 30)
                illnessesArrayList.add(allergyArrayList.get(i)) ;
        }


        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        AdapterPastIllness adapterPastIllness = new AdapterPastIllness(this, illnessesArrayList, studentId) ;
        adapterPastIllness.setUserType(userType);
        this.recycle_past_illness = findViewById(R.id.recycle_past_illness) ;
        recycle_past_illness.setLayoutManager(layoutManager);
        recycle_past_illness.setAdapter(adapterPastIllness);
    }

    public void makeSpinnerSort(){
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.sort_by,R.layout.spinner_medicalhistory2) ;
        adapter.setDropDownViewResource(R.layout.spinner_immune_down);
        spinner_sort.setAdapter(adapter);
        spinner_sort.setSelection(0);
//        prescriptionStatus = spinner_medicationStatus.getSelectedItem().toString() ;
//        Log.d(TAG, "onItemSelected: selectedSort = " + spinner_medicationStatus.getSelectedItem());

        spinner_sort.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.d(TAG, "onItemSelected: selectedSort = " + spinner_sort.getSelectedItem());
                retrieveDataPastIllness();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }


    //checkActive == 20 Allergies tab
    /*This functions is used to get the data medication data. Allergy TAB*/
    public void retrieveDataMAllergies(String studentId){
        this.allergyArrayList = new ArrayList<>() ;

        //for medication
        databaseStudentHealthHistory.child(studentId).child("allergies").orderByChild("allergy").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(allergyArrayList.size() != 0){
                    allergyArrayList.clear();
                }
                for (DataSnapshot postSnapshot: snapshot.getChildren()){
                    ClassAllergy allergy = new ClassAllergy() ;
                    allergy.setAllergy(postSnapshot.child("allergy").getValue().toString());
                    allergy.setDiagnosisDate(postSnapshot.child("diagnosisDate").getValue().toString());
                    allergy.setLastOccurrence(postSnapshot.child("lastOccurrence").getValue().toString());
                    allergy.setType(postSnapshot.child("type").getValue().toString());
                    allergy.setKey(postSnapshot.getKey());
                    allergyArrayList.add(allergy) ;
                }

                if (allergyArrayList.size() != 0){
                    dataInAllergyList(allergyArrayList, studentId);
                } else{
                    Toast.makeText(ActivityMedicalHistory.this, "No data in allergies.", Toast.LENGTH_LONG).show();
                    if(layout_allergies.isShown()){
//                        layout_prescriptionMed.setVisibility(View.VISIBLE);
                        dataInAllergyList(allergyArrayList, studentId);
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        }) ;
    }

    //This function is used to create the recycler view of the medications of the student
    public void dataInAllergyList(ArrayList<ClassAllergy> allergies, String studentId){
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        AdapterAllergy adapterAllergy = new AdapterAllergy(getBaseContext(), allergies, studentId) ;
        adapterAllergy.setUserType(userType);
        this.recycle_medicalAllergy = findViewById(R.id.recycle_medicalAllergy) ;
        recycle_medicalAllergy.setLayoutManager(layoutManager);
        recycle_medicalAllergy.setAdapter(adapterAllergy);
    }


    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.fbtn_medicalHistory){
            switch (checkActive){
                case 10:
                case 30:
                    intent = new Intent(getBaseContext(), ActivityAddPastIllness.class) ;
                    intent.putParcelableArrayListExtra("children", children) ;
                    intent.putExtra("activeTab", checkActive) ;
                    startActivity(intent);
                    break;
                case 20:
                    intent = new Intent(getBaseContext(), ActivityAddAllergy.class) ;
                    intent.putParcelableArrayListExtra("children", children) ;
                    startActivity(intent);
                    break;
            }
        }else if(view.getId() == R.id.tv_cvFromDate){
            materialDatePicker.show(getSupportFragmentManager(), "DATE PICKER");
            selectedDate = 10 ;
        }else if(view.getId() == R.id.tv_cvToDate){
            materialDatePicker.show(getSupportFragmentManager(), "DATE PICKER");
            selectedDate = 20 ;
        }else if(view.getId() == R.id.ibtn_editAllergy){
            intent = new Intent(getBaseContext(), ActivityAddAllergy.class) ;
            intent.putParcelableArrayListExtra("children", children) ;
            intent.putParcelableArrayListExtra("allergyList", allergyArrayList) ;
            intent.putExtra("checkEditAdd", 1) ;
            startActivity(intent);
        }
    }
}