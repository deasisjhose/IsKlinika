package com.capstone.capstone_group.isklinika;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
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
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.button.MaterialButtonToggleGroup;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ActivityImmunization extends AppCompatActivity implements InterfaceIsklinika{

    FirebaseDatabase db = FirebaseDatabase.getInstance();   // getting real time database
    public DatabaseReference databaseImmunization = db.getReference("studentHealthHistory");
    public DatabaseReference databaseReference= db.getReference("studentInfo");

    private Intent intent ;
    private ArrayList<ClassStudentInfo> children ;
    private ArrayList<ClassImmuneRecord> immunizationArrayList ;
    private ArrayList<ClassVaccine> vaccineArrayList ;
    private ClassStudentInfo studentInfo ;
    private int checkActive ;
    private String userType, studentId ;

    private TextView tv_immuneFullName ;
    private MaterialButtonToggleGroup mbtg_immuneChildren, mbtg_immuneTab ;
    private Spinner spinner_childNameModules ;
    private LinearLayout layout_immune_history, layout_vaccineInformation ;
    private FloatingActionButton float_addImmune ;
    private AdapterImmunization adapterImmunization;
    private RecyclerView recycler_immuneDates;
    private AdapterImmunizationHistory adapterImmunizationHistory;
    private RecyclerView recycler_immunizationHistory;
    private TextView tv_viVaxName, tv_viPurpose, tv_viDoses, tv_viAgeRange, tv_viNotes ;
    private Spinner spinner_vaccineList ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_immunization);

        checkActive = 10 ;
        intent = getIntent() ;
        this.userType = intent.getStringExtra("userType") ;
        buildBar();
        buildViews();
        checkUser();

    }


    @Override
    public void buildBar() {

    }

    @Override
    public void buildViews() {
        this.tv_immuneFullName = findViewById(R.id.tv_moduleFullName) ;
        this.layout_immune_history = findViewById(R.id.layout_immune_history) ;
        this.float_addImmune = findViewById(R.id.float_addImmune) ;
        this.mbtg_immuneTab = findViewById(R.id.mbtg_immuneTab) ;

        float_addImmune.setOnClickListener(view -> {
            intent = new Intent(getBaseContext(), ActivityAddImmune.class) ;
            intent.putParcelableArrayListExtra("children", children) ;
            intent.putExtra("currentSelect", spinner_vaccineList.getSelectedItemPosition()) ;
            intent.putParcelableArrayListExtra("children", children) ;
            intent.putParcelableArrayListExtra("vaccineArrayList", vaccineArrayList) ;
            startActivity(intent);
        });

        mbtg_immuneTab.addOnButtonCheckedListener(new MaterialButtonToggleGroup.OnButtonCheckedListener() {
            @Override
            public void onButtonChecked(MaterialButtonToggleGroup group, int checkedId, boolean isChecked) {
                if(isChecked){
                    if(checkedId == R.id.mbtn_immuneStatus){
                        checkActive = 10 ;
                        MaterialButton mbtn_immuneStatus = findViewById(R.id.mbtn_immuneStatus);
                        mbtn_immuneStatus.setTypeface(null, Typeface.BOLD);
                        if(userType.equals("Parent"))
                            float_addImmune.setVisibility(View.GONE);
                        layout_immune_history.setVisibility(View.VISIBLE);
                        layout_vaccineInformation.setVisibility(View.GONE);
                        retrieveDataVaxList();
                    }else {
                        checkActive = 20 ;
                        MaterialButton mbtn_immuneInformation = findViewById(R.id.mbtn_immuneInformation);
                        mbtn_immuneInformation.setTypeface(null, Typeface.BOLD);
                        if(userType.equals("Parent"))
                            float_addImmune.setVisibility(View.VISIBLE);
                        layout_immune_history.setVisibility(View.GONE);
                        layout_vaccineInformation.setVisibility(View.VISIBLE);
                        retrieveDataVaxList();
                        retrieveImmunizationHistory();

                    }
                }else{
                    MaterialButton buttonCheck = findViewById(checkedId);
                    buttonCheck.setTypeface(null, Typeface.NORMAL);
                }
            }
        });

        //check = 20 Vaccine Information
        this.layout_vaccineInformation = findViewById(R.id.layout_vaccineInformation) ;
        this.tv_viVaxName = findViewById(R.id.tv_viVaxName) ;
        this.tv_viPurpose = findViewById(R.id.tv_viPurpose) ;
        this.tv_viDoses = findViewById(R.id.tv_viDoses) ;
        this.tv_viAgeRange = findViewById(R.id.tv_viAgeRange) ;
        this.tv_viNotes = findViewById(R.id.tv_viNotes) ;
        this.spinner_vaccineList = findViewById(R.id.spinner_vaccineList) ;

    }

    @Override
    public void checkUser() {

        switch (userType){
            case "Student":
                this.studentInfo = intent.getParcelableExtra("studentInfo") ;
                tv_immuneFullName.setText(studentInfo.getFullName());
                float_addImmune.setVisibility(View.GONE);
                studentId = studentInfo.getIdNum() ;
                retrieveDataVaxList();
                break;
            case "Parent":
                this.children = intent.getParcelableArrayListExtra("children") ;
                tv_immuneFullName.setText(children.get(0).getFullName());
                this.mbtg_immuneChildren = findViewById(R.id.mbtg_moduleChildren) ;
                mbtg_immuneChildren.setVisibility(View.GONE);
                makeSpinnerChildren();
                retrieveDataVaxList();
                break;
        }
    }

    @Override
    public void retrieveDataParentUser() {

    }

    //This function is used to build the recycler of the dates for the vaccine information. checkActive = 20
    public void dataInImmunizationDate(ArrayList<ClassImmuneRecord> immunizationList){
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        this.adapterImmunization = new AdapterImmunization(getBaseContext(), immunizationList) ;
        this.recycler_immuneDates = findViewById(R.id.recycler_immuneDates) ;
        recycler_immuneDates.setLayoutManager(layoutManager);
        recycler_immuneDates.setAdapter(adapterImmunization);
    }


    //This function is used to build the recycler of the dates for the immunization record. checkActive = 10
    public void dataInImmunizationHistory(){
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        this.adapterImmunizationHistory = new AdapterImmunizationHistory(getBaseContext(), vaccineArrayList, immunizationArrayList, studentInfo) ;
        this.recycler_immunizationHistory = findViewById(R.id.recycle_immune_status) ;
        recycler_immunizationHistory.setLayoutManager(layoutManager);
        recycler_immunizationHistory.setAdapter(adapterImmunizationHistory);
    }

    //This function is used to create the spinner/dropdown of the vaccines.
    public void makeSpinnerVaccine(ArrayList<ClassVaccine> vaccines){

        ArrayAdapter<ClassVaccine> adapter = new ArrayAdapter<>(this, R.layout.spinner_immune, vaccines) ;
        adapter.setDropDownViewResource(R.layout.spinner_immune_down);
        spinner_vaccineList.setAdapter(adapter);

        spinner_vaccineList.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//               String vaccineSelected = spinner_vaccineList.getSelectedItem().toString() ;
                updateVaccineInfo(vaccines.get(position));
                retrieveImmunizationHistory();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    public void makeSpinnerChildren(){
        this.spinner_childNameModules = findViewById(R.id.spinner_childNameModules) ;
        spinner_childNameModules.setVisibility(View.VISIBLE);
        ArrayAdapter<ClassStudentInfo> adapter = new ArrayAdapter<>(this, R.layout.spinner_child_immune, children) ;
        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spinner_childNameModules.setAdapter(adapter);
        spinner_childNameModules.setSelection(0);

        tv_immuneFullName.setText(children.get(0).getFullName());
        studentId = children.get(0).getIdNum() ;

        spinner_childNameModules.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                studentInfo = children.get(position);
                tv_immuneFullName.setText(studentInfo.getFullName());
                studentId = studentInfo.getIdNum() ;
                retrieveDataVaxList();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    //This function is used to make the toggle buttons of the children
    private void dataInToggleChildren(){
        mbtg_immuneChildren.removeAllViews();

        for(int j = 0 ; j < children.size() ; j++){
            MaterialButton button = (MaterialButton) getLayoutInflater().inflate(R.layout.button_naem, null);
            button.setId(j);
            button.setText(children.get(j).getFirstName());
            button.setBackgroundColor(Color.parseColor("#4e73df"));
            button.setTextColor(Color.WHITE);
            mbtg_immuneChildren.addView(button, -2, -1);

        }

        tv_immuneFullName.setText(children.get(0).getFullName());
        studentId = children.get(0).getIdNum() ;

        mbtg_immuneChildren.addOnButtonCheckedListener((group, checkedId, isChecked) -> {
            if(isChecked){
                MaterialButton button3 = findViewById(checkedId);
                button3.setBackgroundColor(Color.WHITE);
                button3.setTextColor(Color.BLACK);
                studentInfo = children.get(checkedId);
                tv_immuneFullName.setText(studentInfo.getFullName());
                studentId = studentInfo.getIdNum() ;
                retrieveDataVaxList();

            }else{
                MaterialButton buttonCheck = findViewById(checkedId);
                buttonCheck.setBackgroundColor(Color.parseColor("#4e73df"));
                buttonCheck.setTextColor(Color.WHITE);
            }

        });
        mbtg_immuneChildren.check(0);

    }

    //This function is used to retrieve the list of vaccine in the database
    public void retrieveDataVaxList(){
        this.vaccineArrayList = new ArrayList<>() ;

        db.getReference("vaccineList").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(!vaccineArrayList.isEmpty())
                    vaccineArrayList.clear();

                for (DataSnapshot postSnapshot: snapshot.getChildren()){
                    ClassVaccine getVaxxed  = new ClassVaccine();
                    getVaxxed = postSnapshot.getValue(ClassVaccine.class);
                    getVaxxed.setVaccineName(postSnapshot.getKey());
                    vaccineArrayList.add(getVaxxed) ;
                }
                switch (checkActive){
                    case 10:
                        setVaccineArrayList(vaccineArrayList);
                        retrieveImmunizationHistory();
                        break;
                    case 20:
                        makeSpinnerVaccine(vaccineArrayList);
                        break;
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        }) ;
    }

    //This function is used to retrieve the immunization history of the selected child.
    public void retrieveImmunizationHistory(){

        //db immune then set the views eme
        ArrayList<ClassImmuneRecord> immunizationList = new ArrayList<>() ;

        databaseImmunization.child(studentId).child("immuneHistory").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(!immunizationList.isEmpty())
                    immunizationList.clear();
                for (DataSnapshot postSnapshot: snapshot.getChildren()){
                    ClassImmuneRecord immunization = postSnapshot.getValue(ClassImmuneRecord.class) ;

                    switch (checkActive){
                        case 10:
                            assert immunization != null;
                            immunizationList.add(immunization) ;
                            break;
                        case 20:
                            if(postSnapshot.child("purpose").getValue().toString().equals(spinner_vaccineList.getSelectedItem().toString())){
//                                Log.d(TAG, "onDataChange: filteredValue = " + postSnapshot.getValue());
                                assert immunization != null;
                                immunizationList.add(immunization) ;
                            }
                            break;
                    }

                }
                switch (checkActive){
                    case 10:
                        setImmunizationList(immunizationList);
                        dataInImmunizationHistory();
                        break;
                    case 20:
                        if(immunizationList.isEmpty()){
                            Toast.makeText(getApplicationContext(), "No data in immunization", Toast.LENGTH_SHORT).show();
                            dataInImmunizationDate(immunizationList);
                        }
                        dataInImmunizationDate(immunizationList);
                        break;
                }

            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        }) ;
    }

    //This function is used to update the information about the selected vaccine. vaccineInformation = tab is blue
    public void updateVaccineInfo(ClassVaccine getVaxxed){
        tv_viVaxName.setText(getVaxxed.getVaccineName());
        tv_viDoses.setText(getVaxxed.getDoses());
        tv_viPurpose.setText(getVaxxed.getPurpose());
        tv_viAgeRange.setText(getVaxxed.getExpectedAge());
        tv_viNotes.setText(getVaxxed.getNotes());
    }

    public void setImmunizationList(ArrayList<ClassImmuneRecord> immunizationList){
        this.immunizationArrayList = new ArrayList<>() ;
        immunizationArrayList = immunizationList ;

    }

    public void setVaccineArrayList(ArrayList<ClassVaccine> vaccines){
        this.vaccineArrayList = new ArrayList<>() ;
        vaccineArrayList = vaccines ;
    }
}