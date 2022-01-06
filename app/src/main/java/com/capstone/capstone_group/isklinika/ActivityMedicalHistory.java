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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.button.MaterialButtonToggleGroup;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ActivityMedicalHistory extends AppCompatActivity implements View.OnClickListener{

    FirebaseDatabase db = FirebaseDatabase.getInstance();   // getting real time database
    public DatabaseReference databaseReference = db.getReference("studentInfo") ;
    public DatabaseReference databaseStudentHealthHistory = db.getReference("studentHealthHistory");

    private ArrayList<ClassStudentInfo> children ;
    private ClassStudentInfo studentInfo ;
    private Intent intent ;
    private int checkActive ;
    private String userType, studentId ;
    public String TAG="DISEASESURVEILLANCE//";

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
    private ArrayList<ClassIllnessHistory> illnessHistoryArrayList ;

    //allergy tab
    private LinearLayout layout_allergies ;
    private RecyclerView recycle_medicalAllergy ;
    private ArrayList<ClassAllergy> allergyArrayList ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medical_history);

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
        this.fbtn_medicalHistory = findViewById(R.id.fbtn_medicalHistory) ;
        fbtn_medicalHistory.setOnClickListener(this);

        //pastIllness
        this.layout_pastIllness = findViewById(R.id.layout_pastIllness) ;

        //allergy tab
        this.layout_allergies = findViewById(R.id.layout_allergies) ;


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

                    }else if(checkedId == R.id.mBtn_allergies){
                        checkActive = 20 ;
                        layout_pastIllness.setVisibility(View.GONE);
                        layout_allergies.setVisibility(View.VISIBLE);
                        retrieveDataMAllergies(studentId);
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
                        break;
                    case 20:
                        retrieveDataMAllergies(studentId);
                        break;
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    public void retrieveDataPastIllness(){
        this.illnessHistoryArrayList = new ArrayList<>() ;

        databaseStudentHealthHistory.child(studentId).child("pastIllness").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(illnessHistoryArrayList.size() != 0){
                    illnessHistoryArrayList.clear();
                }
                for (DataSnapshot postSnapshot: snapshot.getChildren()){
                    ClassIllnessHistory illnessHistory = new ClassIllnessHistory() ;

                    illnessHistoryArrayList.add(illnessHistory) ;
                }

                if (illnessHistoryArrayList.size() != 0){
                    dataInExpandPastIllness(illnessHistoryArrayList, studentId);
                } else{
                    Toast.makeText(ActivityMedicalHistory.this, "No data in past illnesses .", Toast.LENGTH_LONG).show();
                    if(layout_allergies.isShown()){
//                        layout_prescriptionMed.setVisibility(View.VISIBLE);
                        dataInExpandPastIllness(illnessHistoryArrayList, studentId);
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        }) ;
//        Log.d(TAG, "retrieveDataMedicationsList: total list = " + medHistoryList.size());
    }

    public void dataInExpandPastIllness(ArrayList<ClassIllnessHistory> allergyArrayList, String studentId){

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
                    intent = new Intent(getBaseContext(), ActivityAddPastIllness.class) ;
                    intent.putParcelableArrayListExtra("children", children) ;
                    startActivity(intent);
                    break;
                case 20:
                    intent = new Intent(getBaseContext(), ActivityAddAllergy.class) ;
                    intent.putParcelableArrayListExtra("children", children) ;
                    startActivity(intent);
                    break;
            }
        }
    }
}