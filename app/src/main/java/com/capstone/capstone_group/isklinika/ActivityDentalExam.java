package com.capstone.capstone_group.isklinika;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageButton;

/*
This method is used to display the result of the dental examination
 */
public class ActivityDentalExam extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dental_exam);

        ImageButton btn_back ;
        btn_back = findViewById(R.id.btn_back) ;
        btn_back.setOnClickListener(view -> finish());
    }
}