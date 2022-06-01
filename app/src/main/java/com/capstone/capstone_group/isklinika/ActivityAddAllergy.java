package com.capstone.capstone_group.isklinika;

import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
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
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.textview.MaterialTextView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;

/*
This activity allows the parent user to add an allergy in the allergies list.
 */
public class ActivityAddAllergy extends AppCompatActivity implements View.OnClickListener{

    FirebaseDatabase db = FirebaseDatabase.getInstance();   // getting real time database
    public DatabaseReference databaseReference= db.getReference();

    private Intent intent ;
    public String TAG="MEDICALHISTORY//";
    private String idNum ;
    private int selectedDate ; // selected from the activity_immune
    private int checkEditAdd ;

    private Spinner spinner_addAllergyChild, spinner_addAllergyType;
    private TextView tv_addAllergyDate, tv_addAllergyLast ;
    private EditText edit_addAllergyAllergy, edit_addAllergyType;
    private MaterialButton mbtn_addAllergyCancel, mbtn_addAllergyAdd ;
    private MaterialCardView mcard_deleteAllergy ;
    private MaterialDatePicker materialDatePicker ;
    private ArrayList<ClassStudentInfo> children ;

    private int selectedType ;
    //edit allergy
    private MaterialTextView mtv_titleAllergy ;
    private Spinner spinner_editAllergy ;
    private ArrayList<ClassAllergy> allergyArrayList ;
    private ClassAllergy selectedAllergy ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_allergy);

        intent = getIntent() ;
        this.children = new ArrayList<>() ;
        children = intent.getParcelableArrayListExtra("children") ;
        checkEditAdd = intent.getIntExtra("checkEditAdd", 0) ;
        buildViews();

        makeSpinnerChildren();
    }

    public void buildViews(){
        this.spinner_addAllergyChild = findViewById(R.id.spinner_addAllergyChild) ;
        this.spinner_addAllergyType = findViewById(R.id.spinner_addAllergyType) ;
        this.tv_addAllergyDate = findViewById(R.id.tv_addAllergyDate) ;
        this.tv_addAllergyLast = findViewById(R.id.tv_addAllergyLast) ;
        this.edit_addAllergyAllergy = findViewById(R.id.edit_addAllergyAllergy) ;
        this.edit_addAllergyType = findViewById(R.id.edit_addAllergyType) ;
        this.mbtn_addAllergyCancel = findViewById(R.id.mbtn_addAllergyCancel) ;
        this.mbtn_addAllergyAdd = findViewById(R.id.mbtn_addAllergyAdd) ;
        this.mcard_deleteAllergy = findViewById(R.id.mcard_deleteAllergy) ;

        tv_addAllergyDate.setOnClickListener(this);
        tv_addAllergyLast.setOnClickListener(this);
        mbtn_addAllergyCancel.setOnClickListener(this);
        mbtn_addAllergyAdd.setOnClickListener(this);
        mcard_deleteAllergy.setOnClickListener(this);


        makeSpinnerTypes() ;

        if(checkEditAdd == 1){
            mbtn_addAllergyAdd.setText("Update");
            this.allergyArrayList = intent.getParcelableArrayListExtra("allergyList") ;
            this.mtv_titleAllergy = findViewById(R.id.mtv_titleAllergy) ;
            this.spinner_editAllergy = findViewById(R.id.spinner_editAllergy) ;

            mtv_titleAllergy.setText("Edit Allergy Information");
            edit_addAllergyAllergy.setVisibility(View.GONE);
            spinner_editAllergy.setVisibility(View.VISIBLE);
            mcard_deleteAllergy.setVisibility(View.VISIBLE);
            makeSpinnerAllergy();

            checkSelectedType(allergyArrayList.get(0).getType()) ;

        }


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

    //This method is called when editing an existing allergy in the list.
    public void makeSpinnerAllergy(){
        ArrayAdapter<ClassAllergy> adapter = new ArrayAdapter<ClassAllergy>(this, R.layout.spinner_medicalhistory, allergyArrayList) ;
        adapter.setDropDownViewResource(R.layout.spinner_immune_down);
        spinner_editAllergy.setAdapter(adapter);
        spinner_editAllergy.setSelection(0);
        selectedAllergy = allergyArrayList.get(0) ;
        spinner_editAllergy.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

//                edit_addAllergyType.setText(allergyArrayList.get(position).getType());
                tv_addAllergyDate.setText(allergyArrayList.get(position).getDiagnosisDate());
                tv_addAllergyLast.setText(allergyArrayList.get(position).getLastOccurrence());
                selectedAllergy = allergyArrayList.get(position) ;
                checkSelectedType(allergyArrayList.get(position).getType());
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    public void makeSpinnerTypes(){
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.allergies,R.layout.spinner_medicalhistory) ;
        adapter.setDropDownViewResource(R.layout.spinner_immune_down);
        spinner_addAllergyType.setAdapter(adapter);
        spinner_addAllergyType.setSelection(selectedType);
       spinner_addAllergyType.setSelection(0);
        spinner_addAllergyType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    public void checkSelectedType(String allergyType){
        switch(allergyType){
            case "Drug":
                spinner_addAllergyType.setSelection(0);
                break;
            case "Food":
                spinner_addAllergyType.setSelection(1);
                break;
            case "Insect":
                spinner_addAllergyType.setSelection(2);
                break;
            case "Latex":
                spinner_addAllergyType.setSelection(3);
                break;
            case "Mold":
                spinner_addAllergyType.setSelection(4);
                break;
            case "Pet":
                spinner_addAllergyType.setSelection(5);
                break;
            case "Pollen":
                spinner_addAllergyType.setSelection(6);
                break;
        }
    }

    //This method is called when the add button is pressed. This allows allergies to be added on the list
    public void addAllergy(){
        if(checkEditAdd == 0){

            if(edit_addAllergyAllergy.getText().toString().equals("") && tv_addAllergyLast.getText().toString().equals("")){
                Toast.makeText(this, "Add an allergy on and last occurrence!", Toast.LENGTH_SHORT).show();
                edit_addAllergyAllergy.getBackground().setTint(Color.parseColor("#FFFD6868"));
                tv_addAllergyLast.getBackground().setTint(Color.parseColor("#FFFD6868"));
            } else if(edit_addAllergyAllergy.getText().toString().equals("") && !tv_addAllergyLast.getText().toString().equals("")){
                Toast.makeText(this, "Add an allergy on!", Toast.LENGTH_SHORT).show();
                edit_addAllergyAllergy.getBackground().setTint(Color.parseColor("#FFFD6868"));
                tv_addAllergyLast.getBackground().setTint(Color.parseColor("#CCDDF6C0"));
            }else if(!edit_addAllergyAllergy.getText().toString().equals("") && tv_addAllergyLast.getText().toString().equals("")){
                Toast.makeText(this, "Add an last occurrence!", Toast.LENGTH_SHORT).show();
                edit_addAllergyAllergy.getBackground().setTint(Color.parseColor("#CCDDF6C0"));
                tv_addAllergyLast.getBackground().setTint(Color.parseColor("#FFFD6868"));
            }else{
                String allergy, type, diagnosisDate, lastOccurrence ;
                allergy =  edit_addAllergyAllergy.getText().toString() ;
                type = spinner_addAllergyType.getSelectedItem().toString() ;
                diagnosisDate = tv_addAllergyDate.getText().toString() ;
                lastOccurrence = tv_addAllergyLast.getText().toString() ;
                ClassAllergy allergyObj = new ClassAllergy(allergy, type, diagnosisDate, lastOccurrence) ;

                databaseReference.child("studentHealthHistory/" +idNum + "/allergies/").push().setValue(allergyObj).addOnSuccessListener((OnSuccessListener) (aVoid) -> {
                    Toast.makeText(this, "Data successfully updated!", Toast.LENGTH_SHORT).show();
                    resetViews();
                }).addOnFailureListener((error) -> {
                    Toast.makeText(this, "Data was not updated!", Toast.LENGTH_SHORT).show();
                });
            }
        }else{
            HashMap<String, Object> allergyValues = new HashMap<>();
            allergyValues.put("studentHealthHistory/" +idNum + "/allergies/" + allergyArrayList.get(spinner_editAllergy.getSelectedItemPosition()).getKey() + "/allergy/", spinner_editAllergy.getSelectedItem().toString());
            allergyValues.put("studentHealthHistory/" +idNum + "/allergies/" + allergyArrayList.get(spinner_editAllergy.getSelectedItemPosition()).getKey() + "/diagnosisDate/", tv_addAllergyDate.getText().toString());
            allergyValues.put("studentHealthHistory/" +idNum + "/allergies/" + allergyArrayList.get(spinner_editAllergy.getSelectedItemPosition()).getKey() + "/lastOccurrence/", tv_addAllergyLast.getText().toString());
            allergyValues.put("studentHealthHistory/" +idNum + "/allergies/" + allergyArrayList.get(spinner_editAllergy.getSelectedItemPosition()).getKey() + "/type/", spinner_addAllergyType.getSelectedItem().toString());

            databaseReference.updateChildren(allergyValues).addOnSuccessListener((OnSuccessListener) (aVoid) -> {
                Toast.makeText(this, "Data successfully updated!", Toast.LENGTH_SHORT).show();
            }).addOnFailureListener((error) -> {
                Toast.makeText(this, "Data was not updated!", Toast.LENGTH_SHORT).show();
            });
        }
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.mbtn_addAllergyCancel){
            finish();
        }else if(view.getId() == R.id.mbtn_addAllergyAdd){
            addAllergy();
        }else if(view.getId() == R.id.tv_addAllergyDate){
            selectedDate = 10 ;
            materialDatePicker.show(getSupportFragmentManager(), "DATE PICKER");
        }else if(view.getId() == R.id.tv_addAllergyLast){
            selectedDate = 20 ;
            materialDatePicker.show(getSupportFragmentManager(), "DATE PICKER");
        }else if(view.getId() == R.id.mcard_deleteAllergy){
            new MaterialAlertDialogBuilder(view.getRootView().getContext(), R.style.ThemeOverlay_App_MaterialAlertDialog_Illness)
                    .setTitle(R.string.title)
                    .setMessage(R.string.supporting_text)
                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener(){

                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    })
                    .setPositiveButton("Remove", new DialogInterface.OnClickListener(){

                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            databaseReference.child("studentHealthHistory").child(idNum).child("allergies").child(selectedAllergy.getKey()).removeValue().addOnSuccessListener((OnSuccessListener) (aVoid) -> {
                                Toast.makeText(getBaseContext(), "Data successfully deleted!", Toast.LENGTH_SHORT).show();
                                allergyArrayList.remove(selectedAllergy) ;
                                selectedAllergy = allergyArrayList.get(0) ;
                                checkSelectedType(allergyArrayList.get(0).getType());
//                                spinner_addAllergyType.setSelection(selectedType);
                                tv_addAllergyDate.setText(selectedAllergy.getDiagnosisDate());
                                tv_addAllergyLast.setText(selectedAllergy.getLastOccurrence());
                            }).addOnFailureListener((error) -> {
                                Toast.makeText(getBaseContext(), "Data was not deleted!", Toast.LENGTH_SHORT).show();
                            });;
                        }
                    })

                    .show() ;
        }
    }

    public void resetViews(){
        edit_addAllergyType.setText("");
        edit_addAllergyAllergy.setText("");
        tv_addAllergyLast.setText("");
        tv_addAllergyDate.setText("");
        edit_addAllergyAllergy.getBackground().setTint(Color.parseColor("#CCDDF6C0"));
        tv_addAllergyLast.getBackground().setTint(Color.parseColor("#CCDDF6C0"));

    }
}