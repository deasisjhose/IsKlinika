package com.capstone.capstone_group.isklinika;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.material.button.MaterialButtonToggleGroup;
import com.google.android.material.imageview.ShapeableImageView;

import java.util.ArrayList;

public class ActivityDiseaseSurveillance extends AppCompatActivity {

    private ArrayList<ClassStudentInfo> children ;
    private ClassStudentInfo studentInfo ;
    private Intent intent ;
    private int checkActive ;
    private String userType, studentId ;
    public String TAG="DISEASESURVEILLANCE//";

    //module tab children
    private ShapeableImageView shapeImg_moduleProfileChild ;
    private MaterialButtonToggleGroup mbtg_medication ;
    private Spinner spinner_childNameModules ;
    private TextView tv_moduleFullName ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_disease_surveillance);

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

    }

    public void checkUser() {

        switch (userType){
            case "Student":
                this.studentInfo = intent.getParcelableExtra("studentInfo") ;
                tv_moduleFullName.setText(studentInfo.getFullName());
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
        ArrayAdapter<ClassStudentInfo> adapter = new ArrayAdapter<>(this, R.layout.spinner_child_diseasesurveillance, children) ;
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
                        break;
                    case 30:
                        break;
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

}