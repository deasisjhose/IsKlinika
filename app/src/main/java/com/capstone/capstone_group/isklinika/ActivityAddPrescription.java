package com.capstone.capstone_group.isklinika;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textview.MaterialTextView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class ActivityAddPrescription extends AppCompatActivity implements View.OnClickListener{

    FirebaseDatabase db = FirebaseDatabase.getInstance();   // getting real time database
    public DatabaseReference databaseReference= db.getReference();

    private Intent intent ;
    public String TAG="MEDICATION//";
    private String idNum ;
    private int selectedDate ; // selected from the activity_immune

    private Spinner spinner_addPrescriptionChild, spinner_addPrescriptionStatus ;
    private TextView tv_addPrescriptionStart, tv_addPrescriptionEnd ;
    private EditText edit_addPrescriptionMedicine, edit_addPrescriptionPurpose, edit_addPrescriptionAmount, edit_addPrescriptionInterval ;
    private MaterialButton mbtn_addPrescriptionCancel, mbtn_addIPrescriptionAdd ;
    private MaterialDatePicker materialDatePicker ;
    private ArrayList<ClassStudentInfo> children ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_prescription);

        intent = getIntent() ;
        this.children = new ArrayList<>() ;
        children = intent.getParcelableArrayListExtra("children") ;
        buildViews();

        makeSpinnerChildren();
        makeSpinnerPrescription();
    }

    public void buildViews(){
        this.spinner_addPrescriptionChild = findViewById(R.id.spinner_addPrescriptionChild) ;
        this.spinner_addPrescriptionStatus = findViewById(R.id.spinner_addPrescriptionStatus) ;
        this.tv_addPrescriptionStart = findViewById(R.id.tv_addPrescriptionStart) ;
        this.tv_addPrescriptionEnd = findViewById(R.id.tv_addPrescriptionEnd) ;
        this.edit_addPrescriptionMedicine = findViewById(R.id.edit_addPrescriptionMedicine) ;
        this.edit_addPrescriptionPurpose = findViewById(R.id.edit_addPrescriptionPurpose) ;
        this.edit_addPrescriptionAmount = findViewById(R.id.edit_addPrescriptionAmount) ;
        this.edit_addPrescriptionInterval = findViewById(R.id.edit_addPrescriptionInterval) ;
        this.mbtn_addPrescriptionCancel = findViewById(R.id.mbtn_addPrescriptionCancel) ;
        this.mbtn_addIPrescriptionAdd = findViewById(R.id.mbtn_addIPrescriptionAdd) ;

        tv_addPrescriptionStart.setOnClickListener(this);
        tv_addPrescriptionEnd.setOnClickListener(this);
        mbtn_addPrescriptionCancel.setOnClickListener(this);
        mbtn_addIPrescriptionAdd.setOnClickListener(this);

        MaterialDatePicker.Builder builder = MaterialDatePicker.Builder.datePicker() ;
        builder.setTitleText("Select Immunization Date (MM-DD-YY)") ;
        builder.setTheme(R.style.ThemeOverlay_App_DatePicker_Medication) ;
        builder.setSelection(MaterialDatePicker.todayInUtcMilliseconds()) ;


        this.materialDatePicker = builder.build() ;
        materialDatePicker.addOnPositiveButtonClickListener(selection -> {

            ClassDateConvert dateConvert = new ClassDateConvert(materialDatePicker.getHeaderText()) ;

            switch (selectedDate){
                case 10:
                    tv_addPrescriptionStart.setText(dateConvert.getConverted());
                    break;
                case 20:
                    tv_addPrescriptionEnd.setText(dateConvert.getConverted());
                    break;
            }
        }) ;

    }

    /*This function is used to create the spinner/dropdown*/
    public void makeSpinnerChildren(){
        ArrayAdapter<ClassStudentInfo> adapter = new ArrayAdapter<ClassStudentInfo>(this, R.layout.spinner_medication,children) ;
        adapter.setDropDownViewResource(R.layout.spinner_immune_down);
        spinner_addPrescriptionChild.setAdapter(adapter);

        spinner_addPrescriptionChild.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ClassStudentInfo child = (ClassStudentInfo) parent.getSelectedItem();
                idNum = child.getIdNum() ;
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    /*This function is used to create the spinner/dropdown for the prescription sort. MEDICATIONS TAB*/
    public void makeSpinnerPrescription(){
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.add_medication_status,R.layout.spinner_medication) ;
        adapter.setDropDownViewResource(R.layout.spinner_immune_down);
        spinner_addPrescriptionStatus.setAdapter(adapter);
        spinner_addPrescriptionStatus.setSelection(0);
    }

    public void addPrescription(){
        if(tv_addPrescriptionStart.getText().toString().equals("") || tv_addPrescriptionStart.getText().toString().equals("YYYY-MM-DD")){
            Toast.makeText(this, "Add a date!", Toast.LENGTH_SHORT).show();
            tv_addPrescriptionStart.setBackgroundColor(Color.parseColor("#FFFD6868"));
        } else {
            String amount, endMed, interval, medicine, purpose, startMed,status ;
            amount =  edit_addPrescriptionAmount.getText().toString() ;
            endMed = tv_addPrescriptionEnd.getText().toString() ;
            interval = edit_addPrescriptionInterval.getText().toString() ;
            medicine = edit_addPrescriptionMedicine.getText().toString() ;
            purpose = edit_addPrescriptionPurpose.getText().toString() ;
            startMed = tv_addPrescriptionStart.getText().toString() ;
            status = spinner_addPrescriptionStatus.getSelectedItem().toString() ;
            ClassMedication prescription = new ClassMedication(amount, endMed, interval, medicine, purpose, startMed,status) ;

            databaseReference.child("studentHealthHistory/" +idNum + "/prescriptionHistory/").push().setValue(prescription).addOnSuccessListener((OnSuccessListener) (aVoid) -> {
                Toast.makeText(this, "Data successfully updated!", Toast.LENGTH_SHORT).show();
                resetViews();
            }).addOnFailureListener((error) -> {
                Toast.makeText(this, "Data was not updated!", Toast.LENGTH_SHORT).show();
            }); ;

        }
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.mbtn_addPrescriptionCancel){
            finish();
        }else if(view.getId() == R.id.mbtn_addIPrescriptionAdd){
                    addPrescription();
        }else if(view.getId() == R.id.tv_addPrescriptionStart){
                    materialDatePicker.show(getSupportFragmentManager(), "DATE PICKER");
                    selectedDate = 10 ;

        }else if(view.getId() == R.id.tv_addPrescriptionEnd){
                    materialDatePicker.show(getSupportFragmentManager(), "DATE PICKER");
                    selectedDate = 20 ;
        }
    }

    public void resetViews(){
        edit_addPrescriptionAmount.setText("");
        edit_addPrescriptionPurpose.setText("");
        edit_addPrescriptionInterval.setText("");
        edit_addPrescriptionMedicine.setText("");
        tv_addPrescriptionStart.setText("");
        tv_addPrescriptionEnd.setText("");

    }
}