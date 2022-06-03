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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/*
This activity allow the parent user to add some of the past illnesses that their child experienced to allow the school physician to use
these information.
 */
public class ActivityAddPastIllness extends AppCompatActivity implements View.OnClickListener{

    FirebaseDatabase db = FirebaseDatabase.getInstance();   // getting real time database
    public DatabaseReference databaseReference= db.getReference();

    private Intent intent ;
    private int activeTab ;
    public String TAG="MEDICALHISTORY//";
    private String idNum ;
    private int selectedDate ; // selected from the activity_immune
    private ArrayList<ClassStudentInfo> children ;

    private Spinner spinner_addPastChild ;
    private EditText edit_addPastIllness, edit_addPastTreatment, edit_addPastNote;
    private Spinner edit_addPastStatus ;
    private TextView tv_addPastStart, tv_addPastEnd, tv_enddate ;
    private MaterialButton mbtn_addPastCancel, mbtn_addPastAdd ;
    private MaterialDatePicker materialDatePicker ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_past_illness);

        intent = getIntent() ;
        activeTab = intent.getIntExtra("activeTab", 30) ;
        this.children = new ArrayList<>() ;
        children = intent.getParcelableArrayListExtra("children") ;
        buildViews();

        makeSpinnerChildren();
    }

    //This method is used to initialize and build the views in the activity
    public void buildViews(){
        this.spinner_addPastChild = findViewById(R.id.spinner_addPastChild) ;
        this.edit_addPastIllness = findViewById(R.id.edit_addPastIllness) ;
        this.edit_addPastTreatment = findViewById(R.id.edit_addPastTreatment) ;
        this.edit_addPastNote = findViewById(R.id.edit_addPastNote) ;
        this.edit_addPastStatus = findViewById(R.id.edit_addPastStatus) ;
        this.tv_addPastStart = findViewById(R.id.tv_addPastStart) ;
        this.tv_addPastEnd = findViewById(R.id.tv_addPastEnd) ;
        this.mbtn_addPastAdd = findViewById(R.id.mbtn_addPastAdd) ;
        this.mbtn_addPastCancel = findViewById(R.id.mbtn_addPastCancel) ;
        this.tv_enddate = findViewById(R.id.tv_enddate) ;


        tv_addPastStart.setOnClickListener(this);
        tv_addPastEnd.setOnClickListener(this);
        mbtn_addPastAdd.setOnClickListener(this);
        mbtn_addPastCancel.setOnClickListener(this);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getBaseContext(), R.array.illness,R.layout.spinner_medicalhistory) ;
        adapter.setDropDownViewResource(R.layout.spinner_immune_down);
        edit_addPastStatus.setAdapter(adapter);

        if (activeTab == 30)
            edit_addPastStatus.setSelection(0);
        else
            edit_addPastStatus.setSelection(1);


        edit_addPastStatus.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //sort function
                if(position == 1){
                    tv_enddate.setText("End date *");
                }else{
                    tv_enddate.setText("End date");
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        MaterialDatePicker.Builder builder = MaterialDatePicker.Builder.datePicker() ;
        builder.setTitleText("Select Immunization Date (MM-DD-YY)") ;
        builder.setTheme(R.style.ThemeOverlay_App_DatePicker_MedicalHistory) ;
        builder.setSelection(MaterialDatePicker.todayInUtcMilliseconds()) ;


        this.materialDatePicker = builder.build() ;
        materialDatePicker.addOnPositiveButtonClickListener(selection -> {

            ClassDateConvert dateConvert = new ClassDateConvert(materialDatePicker.getHeaderText()) ;

            switch (selectedDate){
                case 10:
                    tv_addPastStart.setText(dateConvert.getConverted());
                    break;
                case 20:
                    tv_addPastEnd.setText(dateConvert.getConverted());
                    break;
            }
        }) ;
    }

    /*This function is used to create the spinner/dropdown*/
    public void makeSpinnerChildren(){
        ArrayAdapter<ClassStudentInfo> adapter = new ArrayAdapter<ClassStudentInfo>(this, R.layout.spinner_medicalhistory,children) ;
        adapter.setDropDownViewResource(R.layout.spinner_immune_down);
        spinner_addPastChild.setAdapter(adapter);

        spinner_addPastChild.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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


    //This method is called when the add button is clicked.
    public void addPastIllness(){

        int allClear = 0 ;

        if(edit_addPastIllness.getText().toString().equals("")){
            edit_addPastIllness.getBackground().setTint(Color.parseColor("#FFFD6868"));
        }else{
            edit_addPastIllness.getBackground().setTint(Color.parseColor("#CCDDF6C0"));
            allClear += 1 ;
        }
        if(edit_addPastTreatment.getText().toString().equals("")){
            edit_addPastTreatment.getBackground().setTint(Color.parseColor("#FFFD6868"));
        }else{
            edit_addPastTreatment.getBackground().setTint(Color.parseColor("#CCDDF6C0"));
            allClear += 1 ;
        }
        if(tv_addPastStart.getText().toString().equals("")){
            tv_addPastStart.getBackground().setTint(Color.parseColor("#FFFD6868"));
        }else{
            tv_addPastStart.getBackground().setTint(Color.parseColor("#CCDDF6C0"));
            allClear += 1 ;
        }

        if(edit_addPastStatus.getSelectedItem().toString().equals("Recovered")){
            if(tv_addPastEnd.getText().toString().equals("")){
                tv_addPastEnd.getBackground().setTint(Color.parseColor("#FFFD6868"));
            }else{
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

                Date date1 = null;
                Date date2 = null;
                try {
                    date1 = sdf.parse(tv_addPastStart.getText().toString()) ;
                    date2 = sdf.parse(tv_addPastEnd.getText().toString()) ;
                }catch (Exception e){

                }

                if(date2.before(date1)){
                    tv_addPastEnd.setBackgroundColor(Color.parseColor("#FFFD6868"));
                }else{
                    allClear += 1 ; 
                    tv_addPastEnd.setBackgroundColor(Color.parseColor("#e4e4e4"));
                }



                tv_addPastEnd.getBackground().setTint(Color.parseColor("#CCDDF6C0"));
                allClear += 1 ;
            }
        }

        if(allClear >= 3){
                ClassPastIllness pastIllness = new ClassPastIllness(edit_addPastIllness.getText().toString(), tv_addPastEnd.getText().toString(), edit_addPastNote.getText().toString(),
                        tv_addPastStart.getText().toString(), edit_addPastStatus.getSelectedItem().toString(), edit_addPastTreatment.getText().toString()) ;

                databaseReference.child("studentHealthHistory/" +idNum + "/pastIllness/").push().setValue(pastIllness).addOnSuccessListener((OnSuccessListener) (aVoid) -> {
                    Toast.makeText(this, "Data successfully updated!", Toast.LENGTH_SHORT).show();
                    resetViews();
                }).addOnFailureListener((error) -> {
                    Toast.makeText(this, "Data was not updated!", Toast.LENGTH_SHORT).show();
                });

        }else{
            Toast.makeText(this, "Complete required fields!", Toast.LENGTH_SHORT).show();
        }
    }

    public void resetViews(){
        edit_addPastIllness.setText("");
        edit_addPastNote.setText("");
        edit_addPastTreatment.setText("");
        edit_addPastIllness.getBackground().setTint(Color.parseColor("#CCDDF6C0"));
        edit_addPastTreatment.getBackground().setTint(Color.parseColor("#CCDDF6C0"));
        tv_addPastStart.getBackground().setTint(Color.parseColor("#CCDDF6C0"));
        tv_addPastStart.setText("");
        tv_addPastEnd.setText("");

    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.mbtn_addPastCancel){
            finish();
        }else if(view.getId() == R.id.mbtn_addPastAdd){
            addPastIllness();
        }else if(view.getId() == R.id.tv_addPastStart){
            materialDatePicker.show(getSupportFragmentManager(), "DATE PICKER");
            selectedDate = 10 ;

        }else if(view.getId() == R.id.tv_addPastEnd){
            materialDatePicker.show(getSupportFragmentManager(), "DATE PICKER");
            selectedDate = 20 ;
        }
    }

}