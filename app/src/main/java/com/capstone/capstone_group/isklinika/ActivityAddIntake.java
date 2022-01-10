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
import com.google.android.material.timepicker.MaterialTimePicker;
import com.google.android.material.timepicker.TimeFormat;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class ActivityAddIntake extends AppCompatActivity implements View.OnClickListener{

    FirebaseDatabase db = FirebaseDatabase.getInstance();   // getting real time database
    public DatabaseReference databaseReference= db.getReference();

    private Intent intent ;
    public String TAG="MEDICATION//";
    private String idNum ;
    private int selectedDate ; // selected from the activity_immune

    private Spinner spinner_addIntakeChild ;
    private ArrayList<ClassStudentInfo> children ;
    private EditText  edit_addIntakeMedicine, edit_addIntakeAmount ;
    private TextView tv_addIntakeStart, tv_addIntakeEnd ;
    private MaterialButton mbtn_addIntakeCancel, mbtn_addIntakeAdd ;
    private MaterialDatePicker materialDatePicker ;
    private MaterialTimePicker materialTimePicker ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_intake);

        intent = getIntent() ;
        this.children = new ArrayList<>() ;
        children = intent.getParcelableArrayListExtra("children") ;
        buildViews();

        makeSpinnerChildren();
    }

    public void buildViews(){
        this.edit_addIntakeMedicine = findViewById(R.id.edit_addIntakeMedicine) ;
        this.edit_addIntakeAmount = findViewById(R.id.edit_addIntakeAmount) ;
        this.tv_addIntakeStart = findViewById(R.id.tv_addIntakeStart) ;
        this.tv_addIntakeEnd = findViewById(R.id.tv_addIntakeEnd) ;
        this.mbtn_addIntakeCancel = findViewById(R.id.mbtn_addIntakeCancel) ;
        this.mbtn_addIntakeAdd = findViewById(R.id.mbtn_addIntakeAdd) ;
        this.spinner_addIntakeChild = findViewById(R.id.spinner_addIntakeChild) ;

        tv_addIntakeStart.setOnClickListener(this);
        tv_addIntakeEnd.setOnClickListener(this);
        mbtn_addIntakeAdd.setOnClickListener(this);
        mbtn_addIntakeCancel.setOnClickListener(this);

        MaterialDatePicker.Builder builder = MaterialDatePicker.Builder.datePicker() ;
        builder.setTitleText("Select Immunization Date (MM-DD-YY)") ;
        builder.setTheme(R.style.ThemeOverlay_App_DatePicker_Medication) ;
        builder.setSelection(MaterialDatePicker.todayInUtcMilliseconds()) ;


        this.materialDatePicker = builder.build() ;
        materialDatePicker.addOnPositiveButtonClickListener(selection -> {

            ClassDateConvert dateConvert = new ClassDateConvert(materialDatePicker.getHeaderText()) ;

            tv_addIntakeStart.setText(dateConvert.getConverted());

        }) ;

//        MaterialTimePicker.Builder builder1 = new MaterialTimePicker.Builder();
//        builder1.setTimeFormat(TimeFormat.CLOCK_24H).build();
        materialTimePicker = new MaterialTimePicker.Builder()
                .setTimeFormat(TimeFormat.CLOCK_24H).setInputMode(MaterialTimePicker.INPUT_MODE_KEYBOARD).setTitleText("Set intake time 00:00-23:59").build();

        materialTimePicker.addOnPositiveButtonClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String hour = Integer.toString(materialTimePicker.getHour()) ;
                String minute = Integer.toString(materialTimePicker.getMinute()) ;
                if(materialTimePicker.getHour() < 10){
                    hour = "0" + materialTimePicker.getHour() ;
                }
                if(materialTimePicker.getMinute() < 10)
                {
                    minute = "0" + materialTimePicker.getMinute() ;
                }
                tv_addIntakeEnd.setText(hour +":"+minute);
            }
        }) ;

    }

    /*This function is used to create the spinner/dropdown*/
    public void makeSpinnerChildren(){
        ArrayAdapter<ClassStudentInfo> adapter = new ArrayAdapter<ClassStudentInfo>(this, R.layout.spinner_medication,children) ;
        adapter.setDropDownViewResource(R.layout.spinner_immune_down);
        spinner_addIntakeChild.setAdapter(adapter);

        spinner_addIntakeChild.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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


    public void addIntake(){
        if(tv_addIntakeStart.getText().toString().equals("")){
            Toast.makeText(this, "Add a date!", Toast.LENGTH_SHORT).show();
            tv_addIntakeStart.getBackground().setTint(Color.parseColor("#FFFD6868"));
        } else {
            String dateTaken, specificAmount, specificMedicine, time;
            dateTaken =  tv_addIntakeStart.getText().toString() ;
            specificAmount = edit_addIntakeAmount.getText().toString() ;
            specificMedicine = edit_addIntakeMedicine.getText().toString() ;
            time = tv_addIntakeEnd.getText().toString() ;

            ClassIntakeHistory intakeHistory = new ClassIntakeHistory(dateTaken, specificAmount, specificMedicine, time) ;

            databaseReference.child("studentHealthHistory/" +idNum + "/intakeHistory/").push().setValue(intakeHistory).addOnSuccessListener((OnSuccessListener) (aVoid) -> {
                Toast.makeText(this, "Data successfully updated!", Toast.LENGTH_SHORT).show();
                resetViews();
            }).addOnFailureListener((error) -> {
                Toast.makeText(this, "Data was not updated!", Toast.LENGTH_SHORT).show();
            }); ;

        }
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.mbtn_addIntakeCancel){
            finish();
        }else if(view.getId() == R.id.mbtn_addIntakeAdd){
            addIntake();
        }else if(view.getId() == R.id.tv_addIntakeStart){
            materialDatePicker.show(getSupportFragmentManager(), "DATE PICKER");


        }else if(view.getId() == R.id.tv_addIntakeEnd){
            materialTimePicker.show(getSupportFragmentManager(), "TIME PICKER");
        }
    }

    public void resetViews(){
        edit_addIntakeMedicine.setText("");
        edit_addIntakeAmount.setText("");
        tv_addIntakeEnd.setText("");
        tv_addIntakeStart.setText("");
        tv_addIntakeStart.getBackground().setTint(Color.parseColor("#E1D6FF"));
    }
}