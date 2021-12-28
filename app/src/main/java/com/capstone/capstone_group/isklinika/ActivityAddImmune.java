package com.capstone.capstone_group.isklinika;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textview.MaterialTextView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.annotations.NotNull;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.TimeZone;

public class ActivityAddImmune extends AppCompatActivity implements View.OnClickListener{

    FirebaseDatabase db = FirebaseDatabase.getInstance();   // getting real time database
    public DatabaseReference databaseReference= db.getReference();

    private Intent intent ;
    public String TAG="IMMUNIZATION//";
    private String idNum ;
    private int currentSelected ; // selected from the activity_immune
    private String vaccineSelected ;

    private Spinner spinner_addImmuneChild, spinner_addImmuneVaccine ;
    private MaterialTextView mtv_immuneDate ;
    private TextInputEditText mEdit_immuneBrand;
    private MaterialButton mbtn_addImmuneCancel, mbtn_addImmuneAdd ;
    private MaterialDatePicker materialDatePicker ;
    private ArrayList<ClassStudentInfo> children ;
    private ArrayList<ClassVaccine> vaccines ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_immune);

        intent = getIntent() ;
        this.children = new ArrayList<>() ;
        children = intent.getParcelableArrayListExtra("children") ;
        currentSelected = intent.getIntExtra("currentSelect", 0) ;
        vaccines = intent.getParcelableArrayListExtra("vaccineArrayList") ;
        buildViews();

        makeSpinnerChildren();
        makeSpinnerVaccine(vaccines);
    }

    //This function is used to add to a new immunization record
    public void addImmunization() {

        if(mtv_immuneDate.getText().toString().equals("") || mtv_immuneDate.getText().toString().equals("MM/DD/YYYY")){
            Toast.makeText(this, "Add a date!", Toast.LENGTH_SHORT).show();
            mtv_immuneDate.setBackgroundColor(Color.parseColor("#FFFD6868"));
        } else {
            ClassRandomString randomString = new ClassRandomString() ;
            mtv_immuneDate.setBackgroundResource(R.drawable.border_bottom);
            String key = randomString.createRandom(9) ;

            //updating studentHealthHistory information
            HashMap immunization = new HashMap();
            immunization.put("/studentHealthHistory/" + idNum + "/immuneHistory/" +  key + "/dateGiven/" , mtv_immuneDate.getText().toString());
            immunization.put("/studentHealthHistory/" + idNum + "/immuneHistory/" + key + "/name/", mEdit_immuneBrand.getText().toString());
            immunization.put("/studentHealthHistory/" + idNum + "/immuneHistory/" + key + "/purpose/", vaccineSelected);

            databaseReference.updateChildren(immunization).addOnSuccessListener((OnSuccessListener) (aVoid) -> {
                Toast.makeText(this, "Data successfully updated!", Toast.LENGTH_SHORT).show();
                resetViews();
            }).addOnFailureListener((error) -> {
                Toast.makeText(this, "Data was not updated!", Toast.LENGTH_SHORT).show();
            });
        }
    }

    //This function is used to build and initialize the views inside
    public void buildViews(){
        this.spinner_addImmuneChild = findViewById(R.id.spinner_addImmuneChild) ;
        this.spinner_addImmuneVaccine = findViewById(R.id.spinner_addImmuneVax) ;
        this.mEdit_immuneBrand = findViewById(R.id.mEdit_immuneBrand) ;
        this.mtv_immuneDate = findViewById(R.id.mtv_immuneDate) ;

        this.mbtn_addImmuneAdd = findViewById(R.id.mbtn_addImmuneAdd) ;
        this.mbtn_addImmuneCancel = findViewById(R.id.mbtn_addImmuneCancel) ;

        mbtn_addImmuneAdd.setOnClickListener(this);
        mbtn_addImmuneCancel.setOnClickListener(this);
        mtv_immuneDate.setOnClickListener(this);

        MaterialDatePicker.Builder builder = MaterialDatePicker.Builder.datePicker() ;
        builder.setTitleText("Select Immunization Date") ;
        builder.setSelection(MaterialDatePicker.todayInUtcMilliseconds()) ;


        this.materialDatePicker = builder.build() ;
        materialDatePicker.addOnPositiveButtonClickListener(selection -> {

            ClassDateConvert dateConvert = new ClassDateConvert(materialDatePicker.getHeaderText()) ;

            mtv_immuneDate.setText(dateConvert.getConverted());
        }) ;
    }

    /*This function is used to create the spinner/dropdown*/
    public void makeSpinnerChildren(){
        ArrayAdapter<ClassStudentInfo> adapter = new ArrayAdapter<ClassStudentInfo>(this, R.layout.spinner_immune,children) ;
        adapter.setDropDownViewResource(R.layout.spinner_immune_down);
        spinner_addImmuneChild.setAdapter(adapter);

        spinner_addImmuneChild.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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

    /*This function is used to create the spinner/dropdown*/
    public void makeSpinnerVaccine(ArrayList<ClassVaccine> vaccines){
        ArrayAdapter<ClassVaccine> adapter = new ArrayAdapter<>(this, R.layout.spinner_immune, vaccines) ;
        adapter.setDropDownViewResource(R.layout.spinner_immune_down);
//        if(adapter.)
        spinner_addImmuneVaccine.setAdapter(adapter);

        spinner_addImmuneVaccine.setSelection(currentSelected);

        spinner_addImmuneVaccine.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                vaccineSelected = spinner_addImmuneVaccine.getSelectedItem().toString() ;
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    /* Override of the onClick function */
    @Override
    public void onClick(@NotNull View v) {
        if(v.getId() == R.id.mbtn_addImmuneCancel){
            finish();
        }else
        if(v.getId() == R.id.mbtn_addImmuneAdd){
            addImmunization();
        }else if(v.getId() == R.id.mtv_immuneDate){
                materialDatePicker.show(getSupportFragmentManager(), "DATE PICKER");

        }
    }

    public void resetViews(){
        mEdit_immuneBrand.setText("");
        mtv_immuneDate.setText("");
        mtv_immuneDate.setText("MM/DD/YYYY");
    }
}