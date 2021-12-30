package com.capstone.capstone_group.isklinika;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class ActivityClinicianLanding extends AppCompatActivity implements View.OnClickListener{

    private Intent intent ;
    private LinearLayout personalInfoModule ;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.clinician_landing);



        this.personalInfoModule = findViewById(R.id.personalInfoModule) ;

        personalInfoModule.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {


        if(view.getId() == R.id.personalInfoModule) {
            intent = new Intent(getBaseContext(), ActivityClinicianPersonalInfoModule.class) ;
            startActivity(intent);
        }
    }
}
