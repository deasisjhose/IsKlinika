package com.capstone.capstone_group.isklinika;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class ActivityAddAllergy extends AppCompatActivity implements View.OnClickListener{

    FirebaseDatabase db = FirebaseDatabase.getInstance();   // getting real time database
    public DatabaseReference databaseReference= db.getReference();

    private Intent intent ;
    public String TAG="MEDICALHISTORY//";
    private String idNum ;
    private int selectedDate ; // selected from the activity_immune


    private Spinner spinner_addAllergyChild;
    private TextView tv_addAllergyDate, tv_addAllergyLast ;
    private EditText edit_addAllergyAllergy, edit_addAllergyType;
    private MaterialButton mbtn_addAllergyCancel, mbtn_addAllergyAdd ;
    private MaterialDatePicker materialDatePicker ;
    private ArrayList<ClassStudentInfo> children ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_allergy);

        intent = getIntent() ;
        this.children = new ArrayList<>() ;
        children = intent.getParcelableArrayListExtra("children") ;
        buildViews();

        makeSpinnerChildren();
    }

    public void buildViews(){
        this.spinner_addAllergyChild = findViewById(R.id.spinner_addAllergyChild) ;
        this.tv_addAllergyDate = findViewById(R.id.tv_addAllergyDate) ;
        this.tv_addAllergyLast = findViewById(R.id.tv_addAllergyLast) ;
        this.edit_addAllergyAllergy = findViewById(R.id.edit_addAllergyAllergy) ;
        this.edit_addAllergyType = findViewById(R.id.edit_addAllergyType) ;
        this.mbtn_addAllergyCancel = findViewById(R.id.mbtn_addAllergyCancel) ;
        this.mbtn_addAllergyAdd = findViewById(R.id.mbtn_addAllergyAdd) ;

        tv_addAllergyDate.setOnClickListener(this);
        tv_addAllergyLast.setOnClickListener(this);
        mbtn_addAllergyCancel.setOnClickListener(this);
        mbtn_addAllergyAdd.setOnClickListener(this);

        tv_addAllergyDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                materialDatePicker.show(getSupportFragmentManager(), "DATE PICKER");
            }
        });

        MaterialDatePicker.Builder builder = MaterialDatePicker.Builder.datePicker() ;
        builder.setTitleText("Select Date (MM-DD-YY)") ;
        builder.setTheme(R.style.ThemeOverlay_App_DatePicker_MedicalHistory) ;
        builder.setSelection(MaterialDatePicker.todayInUtcMilliseconds()) ;


        this.materialDatePicker = builder.build() ;
        materialDatePicker.addOnPositiveButtonClickListener(selection -> {

            ClassDateConvert dateConvert = new ClassDateConvert(materialDatePicker.getHeaderText()) ;

            switch (selectedDate){
                case 10:
                    tv_addAllergyDate.setText(dateConvert.getConverted());
                    break;
                case 20:
                    tv_addAllergyLast.setText(dateConvert.getConverted());
                    break;
            }
        }) ;
    }

    /*This function is used to create the spinner/dropdown*/
    public void makeSpinnerChildren(){
        ArrayAdapter<ClassStudentInfo> adapter = new ArrayAdapter<ClassStudentInfo>(this, R.layout.spinner_medicalhistory,children) ;
        adapter.setDropDownViewResource(R.layout.spinner_immune_down);
        spinner_addAllergyChild.setAdapter(adapter);

        spinner_addAllergyChild.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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


    public void addAllergy(){
        if(edit_addAllergyAllergy.getText().toString().equals("")){
            Toast.makeText(this, "Add an allergy on!", Toast.LENGTH_SHORT).show();
            edit_addAllergyAllergy.setBackgroundColor(Color.parseColor("#FFFD6868"));
        } else {
            String allergy, type, diagnosisDate, lastOccurrence ;
            allergy =  edit_addAllergyAllergy.getText().toString() ;
            type = edit_addAllergyType.getText().toString() ;
            diagnosisDate = tv_addAllergyDate.getText().toString() ;
            lastOccurrence = tv_addAllergyLast.getText().toString() ;
            ClassAllergy allergyObj = new ClassAllergy(allergy, type, diagnosisDate, lastOccurrence) ;

            databaseReference.child("studentHealthHistory/" +idNum + "/allergies/").push().setValue(allergyObj).addOnSuccessListener((OnSuccessListener) (aVoid) -> {
                Toast.makeText(this, "Data successfully updated!", Toast.LENGTH_SHORT).show();
                resetViews();
            }).addOnFailureListener((error) -> {
                Toast.makeText(this, "Data was not updated!", Toast.LENGTH_SHORT).show();
            }); ;

        }
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.mbtn_addAllergyCancel){
            finish();
        }else if(view.getId() == R.id.mbtn_addAllergyAdd){
            addAllergy();
        }else if(view.getId() == R.id.tv_addAllergyDate){
            materialDatePicker.show(getSupportFragmentManager(), "DATE PICKER");
            selectedDate = 10 ;

        }else if(view.getId() == R.id.tv_addAllergyLast){
            materialDatePicker.show(getSupportFragmentManager(), "DATE PICKER");
            selectedDate = 20 ;
        }
    }

    public void resetViews(){
        edit_addAllergyType.setText("");
        edit_addAllergyAllergy.setText("");
        tv_addAllergyLast.setText("");
        tv_addAllergyDate.setText("");
        edit_addAllergyAllergy.setBackgroundColor(Color.parseColor("#CCDDF6C0"));

    }
}