package com.capstone.capstone_group.isklinika;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

public class ActivityPhysicalExam extends AppCompatActivity implements View.OnClickListener {

    private Intent intent ;
    private ClassApe ape ;

    private TextView tv_apeName, tv_apeSY, tv_apeDate, tv_apeDr, tv_apeWeight, tv_apeWeightStatus, tv_apeHeight, tv_apeHeightStatus,
            tv_apeBMI, tv_apeBMIStatus, tv_apeVisionL, tv_apevisionR, tv_apevisionLGlass, tv_apeVisionRGlass,
            tv_apeTemperature, tv_apeBP, tv_apePR, tv_apeRR, tv_apeRecommendation, tv_apeFindings, tv_apeSF,
            tv_apeMP, tv_apeAllergies  ;
    private CheckBox check_apeYes ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_physical_exam);

        intent = getIntent() ;
        ape = intent.getParcelableExtra("ape") ;
        Log.d("HEALTHASSESSMENT//", "onCreate: " + ape.getName());
        buildViews();

    }

    /* Override of the onClick function */
    @Override
    public void onClick(View v) {


    }

    //this function is used to build the views
    private void buildViews(){

        this.tv_apeName = findViewById(R.id.tv_apeName) ;
        this.tv_apeSY = findViewById(R.id.tv_apeSY) ;
        this.tv_apeDate = findViewById(R.id.tv_apeDate) ;
        this.tv_apeDr = findViewById(R.id.tv_apeDr) ;
        this.tv_apeWeight = findViewById(R.id.tv_apeWeight) ;
        this.tv_apeWeightStatus = findViewById(R.id.tv_apeWeightStatus) ;
        this.tv_apeHeight = findViewById(R.id.tv_apeHeight) ;
        this.tv_apeHeightStatus = findViewById(R.id.tv_apeHeightStatus) ;
        this.tv_apeBMI = findViewById(R.id.tv_apeBMI) ;
        this.tv_apeBMIStatus = findViewById(R.id.tv_apeBMIStatus) ;
        this.tv_apeVisionL = findViewById(R.id.tv_apeVisionL) ;
        this.tv_apevisionR = findViewById(R.id.tv_apeVisionR) ;
        this.tv_apevisionLGlass = findViewById(R.id.tv_apeVisionLGlass) ;
        this.tv_apeVisionRGlass = findViewById(R.id.tv_apeVisionRGlass) ;
        this.tv_apeTemperature = findViewById(R.id.tv_apeTemperature) ;
        this.tv_apeBP = findViewById(R.id.tv_apeBP) ;
        this.tv_apePR = findViewById(R.id.tv_apePR) ;
        this.tv_apeRR = findViewById(R.id.tv_apeRR) ;
        this.tv_apeRecommendation = findViewById(R.id.tv_apeRecommendation) ;
        this.tv_apeFindings = findViewById(R.id.tv_apeFindings) ;
        this.tv_apeSF = findViewById(R.id.tv_apeSF) ;
        this.tv_apeMP = findViewById(R.id.tv_apeMP) ;
        this.tv_apeAllergies = findViewById(R.id.tv_apeAllergies) ;
        this.check_apeYes = findViewById(R.id.check_apeYes) ;


        tv_apeName.setText(ape.getName());
        tv_apeSY.setText("SY: " + ape.getSchoolYear());
        tv_apeDate.setText(ape.getApeDate());
        tv_apeDr.setText(ape.getClinician());

        tv_apeWeight.setText(ape.getWeight());
        tv_apeWeightStatus.setText(ape.getWeightStatus());
        if(!ape.getBmiStatus().equals("Normal")){
            tv_apeWeightStatus.getBackground().setTint(ContextCompat.getColor(this, R.color.error_container));
        }else
            tv_apeWeightStatus.getBackground().setTint(ContextCompat.getColor(this, R.color.green));

        tv_apeHeight.setText(ape.getHeight());
        tv_apeHeightStatus.setText(ape.getHeightStatus());
        if(!ape.getBmiStatus().equals("Normal")){
            tv_apeWeightStatus.getBackground().setTint(ContextCompat.getColor(this, R.color.error_container));
        }else
            tv_apeWeightStatus.getBackground().setTint(ContextCompat.getColor(this, R.color.green));

        tv_apeBMI.setText(ape.getBmi());
        tv_apeBMIStatus.setText(ape.getBmiStatus());
            if(!ape.getBmiStatus().equals("Normal")){
                tv_apeBMIStatus.getBackground().setTint(ContextCompat.getColor(this, R.color.error_container));
            }else
                tv_apeBMIStatus.getBackground().setTint(ContextCompat.getColor(this, R.color.green));



        tv_apeVisionL.setText(ape.getOsVision());
        tv_apevisionR.setText(ape.getOdVision());
        tv_apevisionLGlass.setText(ape.getOsGlasses());
        tv_apeVisionRGlass.setText(ape.getOdGlasses());
        tv_apeTemperature.setText( ape.getTemp()+ "°C");
        tv_apeBP.setText(ape.getSystolic() + "/" + ape.getDiastolic() + " mmHg");
        tv_apePR.setText( ape.getPr() + " beats/min");
        tv_apeRR.setText( ape.getRr() + " breaths/min");
        tv_apeRecommendation.setText(ape.getAssess());
        tv_apeFindings.setText(ape.getConcern());
        tv_apeSF.setText(ape.getSf());
        tv_apeMP.setText(ape.getMedProb());
//        tv_apeAllergies.setText("");
//        check_apeYes.setChecked();

    }
}