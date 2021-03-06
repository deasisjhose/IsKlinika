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
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.button.MaterialButtonToggleGroup;
import com.google.android.material.checkbox.MaterialCheckBox;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.annotations.NotNull;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
/*
This is the Medication module
 */
public class ActivityMedication extends AppCompatActivity implements View.OnClickListener {

    FirebaseDatabase db = FirebaseDatabase.getInstance();   // getting real time database
    public DatabaseReference databaseReference = db.getReference("studentInfo") ;
    public DatabaseReference databaseStudentHealthHistory = db.getReference("studentHealthHistory");

    private ArrayList<ClassStudentInfo> children ;
    private ArrayList<ClassMedication> medHistoryList ;
    private ClassStudentInfo studentInfo ;
    private Intent intent ;
    private int checkActive ;
    private String userType, studentId ;
    public String TAG="MEDICATION//";
    private ImageButton btn_back ;
    //module tab children
    private ShapeableImageView shapeImg_moduleProfileChild ;
    private MaterialButtonToggleGroup mbtg_medication ;
    private Spinner spinner_childNameModules ;
    private TextView tv_moduleFullName ;

    //prescription tab
    private LinearLayout layout_medicationPrescription ;
    private FloatingActionButton float_addPrescription ;
    private Spinner spinner_medicationStatus ;
    private RecyclerView recycle_medicationPrescription ;
    private AdapterMedPrescriptions adapterMedPrescriptions ;
    private String prescriptionStatus ;


    //allowed medications tab
    private LinearLayout layout_medicationAllowedMedicine ;
    private TextView tv_allowedMedicineLastUpdated ;
    private ImageButton ibtn_editAM, ibtn_saveAM ;
    private MaterialCheckBox paracetamol, mefenamic, salbutamol, ibuprofen, epinephrine, clonidine,
            oralRehydration, loratidine, citirizine, neozep, decolgen, antacid ;

    //intake history tab
    private LinearLayout layot_intakeHistory ;
    private AdapterIntakeHistory adapterIntakeHistory ;
    private RecyclerView recycle_intakeHistory ;
    private TextView tv_cvFromDate, tv_cvToDate ;
    private Spinner spinner_sort ;
    private MaterialDatePicker materialDatePicker ;
    private int selectedDate ;
    private ArrayList<ClassIntakeHistory> intakeHistoryListForSort ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medication);

        checkActive = 10 ;
        intent = getIntent() ;
        this.userType = intent.getStringExtra("userType") ;
        buildBar();
        buildView();
        checkUser();

    }

    public void buildBar(){
        this.btn_back = findViewById(R.id.btn_back);
        btn_back.setOnClickListener(view -> finish());

        this.tv_moduleFullName = findViewById(R.id.tv_moduleFullName) ;
        this.mbtg_medication = findViewById(R.id.mbtg_medication) ;

        mbtg_medication.addOnButtonCheckedListener(new MaterialButtonToggleGroup.OnButtonCheckedListener() {
            @Override
            public void onButtonChecked(MaterialButtonToggleGroup group, int checkedId, boolean isChecked) {

                if (isChecked) {
                    MaterialButton buttonCheck = findViewById(checkedId);
                    buttonCheck.setTypeface(null, Typeface.BOLD);
                    if(checkedId == R.id.mbtn_prescription){
                        checkActive = 10 ;
                        if(userType.equals("Parent"))
                            float_addPrescription.setVisibility(View.VISIBLE);
                        layout_medicationPrescription.setVisibility(View.VISIBLE);
                        layout_medicationAllowedMedicine.setVisibility(View.GONE);
                        layot_intakeHistory.setVisibility(View.GONE);
                        retrieveDataMedications(studentId) ;
                    }else if(checkedId == R.id.mbtn_allowedMedicines){
                        checkActive = 20 ;
                        if(userType.equals("Parent"))
                            float_addPrescription.setVisibility(View.GONE);
                        layout_medicationPrescription.setVisibility(View.GONE);
                        layout_medicationAllowedMedicine.setVisibility(View.VISIBLE);
                        layot_intakeHistory.setVisibility(View.GONE);
                        retrieveDataAllowedMedicines();
                    }else if(checkedId == R.id.mbtn_intakeHistory){
                        checkActive = 30 ;
                        if(userType.equals("Parent"))
                            float_addPrescription.setVisibility(View.VISIBLE);
                        layout_medicationPrescription.setVisibility(View.GONE);
                        layout_medicationAllowedMedicine.setVisibility(View.GONE);
                        layot_intakeHistory.setVisibility(View.VISIBLE);
                        retrieveIntakeHistory();
                    }

                } else {
                    MaterialButton buttonCheck = findViewById(checkedId);
                    buttonCheck.setTypeface(null, Typeface.NORMAL);
                }
            }
        });
        mbtg_medication.check(0);
    }

    public void buildView(){
        //check == 10 Prescriptions
        this.layout_medicationPrescription = findViewById(R.id.layout_medicationPrescription) ;
        this.float_addPrescription = findViewById(R.id.float_addPrescription) ;
        this.spinner_medicationStatus = findViewById(R.id.spinner_medicationStatus) ;

        float_addPrescription.setOnClickListener(this);
        makeSpinnerPrescription();


        //check == 20 AllowedMedicines
        this.layout_medicationAllowedMedicine = findViewById(R.id.layout_medicationAllowedMedicine) ;
        this.tv_allowedMedicineLastUpdated = findViewById(R.id.tv_allowedMedicineLastUpdated) ;
        this.ibtn_editAM = findViewById(R.id.ibtn_editAM) ;
        this.ibtn_saveAM = findViewById(R.id.ibtn_saveAM) ;
        this.paracetamol = findViewById(R.id.paracetamol) ;
        this.mefenamic = findViewById(R.id.mefenamic) ;
        this.salbutamol = findViewById(R.id.salbutamol) ;
        this.ibuprofen = findViewById(R.id.ibuprofen) ;
        this.epinephrine = findViewById(R.id.epinephrine) ;
        this.clonidine = findViewById(R.id.clonidine) ;
        this.oralRehydration = findViewById(R.id.oralRehydration) ;
        this.loratidine = findViewById(R.id.loratidine) ;
        this.citirizine = findViewById(R.id.cetirizine) ;
        this.neozep = findViewById(R.id.neozep) ;
        this.decolgen = findViewById(R.id.decolgen) ;
        this.antacid = findViewById(R.id.antacid) ;

        ibtn_editAM.setOnClickListener(this);
        ibtn_saveAM.setOnClickListener(this);


        //check == 30 Intake History
        this.layot_intakeHistory = findViewById(R.id.layot_intakeHistory) ;
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
        builder.setTitleText("Select Immunization Date (MM-DD-YY)") ;
        builder.setTheme(R.style.ThemeOverlay_App_DatePicker_Medication) ;
        builder.setSelection(MaterialDatePicker.todayInUtcMilliseconds()) ;


        this.materialDatePicker = builder.build() ;
        materialDatePicker.addOnPositiveButtonClickListener(selection -> {

            ClassDateConvert dateConvert = new ClassDateConvert(materialDatePicker.getHeaderText()) ;

            switch (selectedDate){
                case 10:
                    tv_cvFromDate.setText(dateConvert.getConverted());
                    if(!tv_cvToDate.getText().toString().equals("YYYY-MM-DD"))
                        retrieveIntakeHistory();
                    break;
                case 20:
                    tv_cvToDate.setText(dateConvert.getConverted());
                    if(!tv_cvFromDate.getText().toString().equals("YYYY-MM-DD"))
                        retrieveIntakeHistory();
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
                    img_logoHealthAssess.setImageResource(R.drawable.clinician_medication);
                }
                this.studentInfo = intent.getParcelableExtra("studentInfo") ;
                tv_moduleFullName.setText(studentInfo.getFullName());
                studentId = studentInfo.getIdNum() ;
                ibtn_editAM.setVisibility(View.GONE);
                break;
            case "Parent":
                this.children = intent.getParcelableArrayListExtra("children") ;
                tv_moduleFullName.setText(children.get(0).getFullName());
                this.mbtg_medication = findViewById(R.id.mbtg_moduleChildren) ;
                mbtg_medication.setVisibility(View.GONE);
                float_addPrescription.setVisibility(View.VISIBLE);
                makeSpinnerChildren();
//                dataInToggleChildren();
                break;
        }
    }

    //This function is used to make the toggle buttons of the children
    public void makeSpinnerChildren(){

        this.spinner_childNameModules = findViewById(R.id.spinner_childNameModules) ;
        spinner_childNameModules.setVisibility(View.VISIBLE);
        ArrayAdapter<ClassStudentInfo> adapter = new ArrayAdapter<>(this, R.layout.spinner_child_medication, children) ;
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
                        retrieveDataMedications(studentId) ;
                        break;
                    case 20:
                        tv_allowedMedicineLastUpdated.setText("");
                        retrieveDataAllowedMedicines();
                        break;
                    case 30:
                        retrieveIntakeHistory();
                        break;
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    //check == 10 ; From makeSpinnerPrescription() to retrieveDataMedications()
    /*This function is used to create the spinner/dropdown for the prescription sort. MEDICATIONS TAB*/
    public void makeSpinnerPrescription(){
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.medication_status,R.layout.spinner_medication) ;
        adapter.setDropDownViewResource(R.layout.spinner_immune_down);
        spinner_medicationStatus.setAdapter(adapter);
        spinner_medicationStatus.setSelection(0);
        prescriptionStatus = spinner_medicationStatus.getSelectedItem().toString() ;
        Log.d(TAG, "onItemSelected: selectedSort = " + spinner_medicationStatus.getSelectedItem());

        spinner_medicationStatus.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.d(TAG, "onItemSelected: selectedSort = " + spinner_medicationStatus.getSelectedItem());
                //sort function
                prescriptionStatus = spinner_medicationStatus.getSelectedItem().toString() ;
                retrieveDataMedications(studentId);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    //This function is used to create the recycler view of the medications of the student
    public void dataInMedHistory(ArrayList<ClassMedication> medHistoryList, String studentId){
        ArrayList<ClassMedication> medMatchSpinnerList = new ArrayList<>() ;

        for(int i = 0 ; i < medHistoryList.size() ; i++){
            if(prescriptionStatus.equals(medHistoryList.get(i).getStatus())){
                medMatchSpinnerList.add(medHistoryList.get(i)) ;
            }
        }

        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        this.adapterMedPrescriptions = new AdapterMedPrescriptions(this, medMatchSpinnerList, studentId, prescriptionStatus, userType) ;
        adapterMedPrescriptions.setUserType(userType);
        this.recycle_medicationPrescription = findViewById(R.id.recycle_medicationPrescription) ;
        recycle_medicationPrescription.setLayoutManager(layoutManager);
        recycle_medicationPrescription.setAdapter(adapterMedPrescriptions);
    }

    /*This functions is used to get the data medication data. MEDICATIONS TAB*/
    public void retrieveDataMedications(String studentId){
        this.medHistoryList = new ArrayList<>() ;

        //for medication
        databaseStudentHealthHistory.child(studentId).child("prescriptionHistory").orderByChild("startMed").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(medHistoryList.size() != 0){
                    medHistoryList.clear();
                }
                for (DataSnapshot postSnapshot: snapshot.getChildren()){
                    ClassMedication medHistory = new ClassMedication() ;
                    medHistory.setAmount(postSnapshot.child("amount").getValue().toString());
                    medHistory.setEndMed(postSnapshot.child("endMed").getValue().toString());
                    medHistory.setInterval(postSnapshot.child("interval").getValue().toString());
                    medHistory.setMedicine(postSnapshot.child("medicine").getValue().toString());
                    medHistory.setPurpose(postSnapshot.child("purpose").getValue().toString());
                    medHistory.setStartMed(postSnapshot.child("startMed").getValue().toString());
                    medHistory.setStatus(postSnapshot.child("status").getValue().toString());
                    Log.d(TAG, "onDataChange: medication = " + medHistory.toString());
                    medHistory.setKey(postSnapshot.getKey());
                    medHistoryList.add(medHistory) ;

                }


                if (medHistoryList.size() != 0){
                    Collections.reverse(medHistoryList);
                    dataInMedHistory(medHistoryList, studentId);
                } else{
                    Toast.makeText(ActivityMedication.this, "No data in medication history.", Toast.LENGTH_LONG).show();
                    if(layout_medicationPrescription.isShown()){
//                        layout_prescriptionMed.setVisibility(View.VISIBLE);
                        dataInMedHistory(medHistoryList, studentId);
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        }) ;
//        Log.d(TAG, "retrieveDataMedicationsList: total list = " + medHistoryList.size());
    }


    //checkActive == 20 ; From allowed_updateViews() to retrieveDataAllowedMedicines()
    //This function is used to update the checkboxes in allowed medicines
    public void allowed_updateViews(@NotNull ClassAllowedMed allowedMeds){
        tv_allowedMedicineLastUpdated.setText(allowedMeds.getLastUpdated());

        int i ;

        for(i = 0 ; i < allowedMeds.getMedAllowedSize() ; i++){
            switch (allowedMeds.getMedicineName(i)){
                case "antacid":
                    antacid.setChecked(allowedMeds.getIsAllowed(i));
                    break;
                case "citirizine":
                    citirizine.setChecked(allowedMeds.getIsAllowed(i));
                    break;
                case "clonidine":
                    clonidine.setChecked(allowedMeds.getIsAllowed(i));
                    break;
                case "decolgen":
                    decolgen.setChecked(allowedMeds.getIsAllowed(i));
                    break;
                case "epinephrine":
                    epinephrine.setChecked(allowedMeds.getIsAllowed(i));
                    break;
                case "ibuprofen":
                    ibuprofen.setChecked(allowedMeds.getIsAllowed(i));
                    break;
                case "loratidine":
                    loratidine.setChecked(allowedMeds.getIsAllowed(i));
                    break;
                case "mefenamic":
                    mefenamic.setChecked(allowedMeds.getIsAllowed(i));
                    break;
                case "neozep":
                    neozep.setChecked(allowedMeds.getIsAllowed(i));
                    break;
                case "oralRehydration":
                    oralRehydration.setChecked(allowedMeds.getIsAllowed(i));
                    break;
                case "paracetamol":
                    paracetamol.setChecked(allowedMeds.getIsAllowed(i));
                    break;
                case "salbutamol":
                    salbutamol.setChecked(allowedMeds.getIsAllowed(i));
                    break;
            }
        }
    }

    //This function is used to remove all checks in each checkboxes in order to make sure that the boxes are unchecked especially when the other student has no data in allowed medicines
    public void allowed_resetCheck(){
        paracetamol.setChecked(false);
        mefenamic.setChecked(false);
        salbutamol.setChecked(false);
        ibuprofen.setChecked(false);
        antacid.setChecked(false) ;
        epinephrine.setChecked(false);
        clonidine.setChecked(false);
        oralRehydration.setChecked(false);
        loratidine.setChecked(false);
        citirizine.setChecked(false);
        neozep.setChecked(false);
        decolgen.setChecked(false);
        antacid.setChecked(false);
    }

    //This function is used to make the allowedMedication views editable
    public void allowed_edit(){
        ibtn_editAM.setVisibility(View.GONE);
        ibtn_saveAM.setVisibility(View.VISIBLE);

        paracetamol.setClickable(true);
        mefenamic.setClickable(true);
        salbutamol.setClickable(true);
        ibuprofen.setClickable(true);
        epinephrine.setClickable(true);
        clonidine.setClickable(true);
        oralRehydration.setClickable(true);
        loratidine.setClickable(true);
        citirizine.setClickable(true);
        neozep.setClickable(true);
        decolgen.setClickable(true);
        antacid.setClickable(true);
    }

    //This function is used to save the changes in the allowed medications of the student/child
    public void allowed_save(){
        ibtn_editAM.setVisibility(View.VISIBLE);
        ibtn_saveAM.setVisibility(View.GONE);

        paracetamol.setClickable(false);
        mefenamic.setClickable(false);
        salbutamol.setClickable(false);
        ibuprofen.setClickable(false);
        epinephrine.setClickable(false);
        clonidine.setClickable(false);
        oralRehydration.setClickable(false);
        loratidine.setClickable(false);
        citirizine.setClickable(false);
        neozep.setClickable(false);
        decolgen.setClickable(false);
        antacid.setClickable(false);

        Date date = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String formattedDate = df.format(date);
        tv_allowedMedicineLastUpdated.setText(formattedDate);

        HashMap<String, Object> allowedMedication = new HashMap<>();
        allowedMedication.put(studentId + "/allowedMedicines" + "/paracetamol/" + "/isAllowed/", paracetamol.isChecked()) ;
        allowedMedication.put(studentId + "/allowedMedicines" + "/mefenamic/" + "/isAllowed/", mefenamic.isChecked()) ;
        allowedMedication.put(studentId + "/allowedMedicines" + "/salbutamol/" + "/isAllowed/", salbutamol.isChecked()) ;
        allowedMedication.put(studentId + "/allowedMedicines" + "/ibuprofen/" + "/isAllowed/", ibuprofen.isChecked()) ;
        allowedMedication.put(studentId + "/allowedMedicines" + "/epinephrine/" + "/isAllowed/", epinephrine.isChecked()) ;
        allowedMedication.put(studentId + "/allowedMedicines" + "/clonidine/" + "/isAllowed/", clonidine.isChecked()) ;
        allowedMedication.put(studentId + "/allowedMedicines" + "/hydrite/" + "/isAllowed/", oralRehydration.isChecked()) ;
        allowedMedication.put(studentId + "/allowedMedicines" + "/loratidine/" + "/isAllowed/", loratidine.isChecked()) ;
        allowedMedication.put(studentId + "/allowedMedicines" + "/cetirizine/" + "/isAllowed/", citirizine.isChecked()) ;
        allowedMedication.put(studentId + "/allowedMedicines" + "/neozep/" + "/isAllowed/", neozep.isChecked()) ;
        allowedMedication.put(studentId + "/allowedMedicines" + "/decolgen/" + "/isAllowed/", decolgen.isChecked()) ;
        allowedMedication.put(studentId + "/allowedMedicines" + "/antacid/" + "/isAllowed/", antacid.isChecked()) ;
        allowedMedication.put(studentId + "/allowedMedicines" + "/lastUpdated/", tv_allowedMedicineLastUpdated.getText().toString()) ;

        databaseStudentHealthHistory.updateChildren(allowedMedication).addOnSuccessListener((OnSuccessListener) (aVoid) -> {
            Toast.makeText(this, "Data successfully updated!", Toast.LENGTH_SHORT).show();
        }).addOnFailureListener((error) -> {
            Toast.makeText(this, "Data was not updated!", Toast.LENGTH_SHORT).show();
        });

    }

    //This function is used to retrieve the student data on their allowed medications in the clinic
    public void retrieveDataAllowedMedicines(){
        ArrayList<ClassMedicineAllowed> medsAllowed = new ArrayList<>() ;

        allowed_resetCheck();  //remove all the check to make sure that the fields are uncheck
        databaseStudentHealthHistory.child(studentId).child("allowedMedicines").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ClassAllowedMed allowedMed = new ClassAllowedMed() ;
                if(medsAllowed.size() > 0 ){
                    medsAllowed.clear();
                }
                for (DataSnapshot postSnapshot: snapshot.getChildren()){
                    if(!postSnapshot.getKey().equals("lastUpdated")){
                        ClassMedicineAllowed medicineAllowed = postSnapshot.getValue(ClassMedicineAllowed.class) ;
                        medicineAllowed.setMedicineName(postSnapshot.getKey());
                        medsAllowed.add(medicineAllowed) ;
                    }else
                        allowedMed.setLastUpdated(postSnapshot.getValue().toString());
                }
                if(medsAllowed.size() == 0)
                    Toast.makeText(ActivityMedication.this, "No data in allowed medications.", Toast.LENGTH_LONG).show();
                else{
                    allowedMed.setAllowedMedicines(medsAllowed);
                    allowed_updateViews(allowedMed);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        }) ;
    }


    //check == 30 ; From  to
    //this function is used to display the intake history
    public void dataInIntakeHistory(ArrayList<ClassIntakeHistory> intakeHistoryList){

        if(spinner_sort.getSelectedItem().toString().equals("Latest") && intakeHistoryList.size() > 0){
            Collections.reverse(intakeHistoryList);
            Log.d(TAG, "dataInExpandClinicVisit: size = " +intakeHistoryList.size());
        }

        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        this.adapterIntakeHistory= new AdapterIntakeHistory(getBaseContext(), intakeHistoryList, studentId) ;
        this.recycle_intakeHistory = findViewById(R.id.recycle_intakeHistory) ;
        recycle_intakeHistory.setLayoutManager(layoutManager);
        recycle_intakeHistory.setAdapter(adapterIntakeHistory);
    }

    //This function is used to retrieve the student data on their intake history
    public void retrieveIntakeHistory(){
        ArrayList<ClassIntakeHistory> intakeHistoryList = new ArrayList<>() ;


        databaseStudentHealthHistory.child(studentId).child("intakeHistory").orderByChild("dateTaken").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(!intakeHistoryList.isEmpty()){
                    intakeHistoryList.clear();
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
                    ClassIntakeHistory intakeHistory = new ClassIntakeHistory() ;

                    if(!tv_cvFromDate.getText().toString().equals("YYYY-MM-DD") && !tv_cvToDate.getText().toString().equals("YYYY-MM-DD")) {
                        vDate = postSnapshot.child("dateTaken").getValue().toString();
                        parts3 = vDate.split("-");
                        Calendar dataDate = Calendar.getInstance();
                        dataDate.set(Calendar.YEAR, Integer.parseInt(parts3[0]));
                        dataDate.set(Calendar.MONTH, Integer.parseInt(parts3[1]) - 1);
                        dataDate.set(Calendar.DAY_OF_MONTH, Integer.parseInt(parts3[2]));

                        if (((startDate.before(dataDate)) || (startDate.equals(dataDate))) && ((dataDate.before(endDate)) || (dataDate.equals(endDate)))) {
                            intakeHistory = postSnapshot.getValue(ClassIntakeHistory.class) ;
                            intakeHistoryList.add(intakeHistory) ;
                        }
                    }else{
                        intakeHistory = postSnapshot.getValue(ClassIntakeHistory.class) ;
                        intakeHistoryList.add(intakeHistory) ;
                    }

//
                }

                if(intakeHistoryList.isEmpty())
                    Toast.makeText(ActivityMedication.this, "No data in intake history.", Toast.LENGTH_LONG).show();

                dataInIntakeHistory(intakeHistoryList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        }) ;
    }
    public void makeSpinnerSort(){
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.sort_by,R.layout.spinner_medication2) ;
        adapter.setDropDownViewResource(R.layout.spinner_immune_down);
        spinner_sort.setAdapter(adapter);
//        spinner_sort.setSelection(0);

        spinner_sort.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.d(TAG, "onItemSelected: selectedSort = " + spinner_sort.getSelectedItem());


                retrieveIntakeHistory();

            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }


    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.ibtn_editAM){
            allowed_edit();
        }else if (view.getId() == R.id.ibtn_saveAM){
            allowed_save();
        }else if(view.getId() == R.id.float_addPrescription){
            if(checkActive == 10){
                intent = new Intent(getBaseContext(), ActivityAddPrescription.class) ;
                intent.putParcelableArrayListExtra("children", children) ;
                intent.putExtra("currentSelect", spinner_childNameModules.getSelectedItemPosition()) ;
            }else if(checkActive == 30){
                intent = new Intent(getBaseContext(), ActivityAddIntake.class) ;
                intent.putParcelableArrayListExtra("children", children) ;
                intent.putExtra("currentSelect", spinner_childNameModules.getSelectedItemPosition()) ;
            }
            startActivity(intent);
        }else if(view.getId() == R.id.tv_cvFromDate){
            materialDatePicker.show(getSupportFragmentManager(), "DATE PICKER");
            selectedDate = 10 ;
        }else if(view.getId() == R.id.tv_cvToDate){
            materialDatePicker.show(getSupportFragmentManager(), "DATE PICKER");
            selectedDate = 20 ;
        }
    }
}