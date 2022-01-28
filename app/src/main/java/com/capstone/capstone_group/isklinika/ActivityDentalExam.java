package com.capstone.capstone_group.isklinika;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.TextView;

/*
This method is used to display the result of the dental examination
 */
public class ActivityDentalExam extends AppCompatActivity {

    private TextView tv0,tv1,tv2,tv3,tv4,tv5,tv6,tv7,tv8,tv9,tv10,tv11,tv12,tv13,tv14,tv15,tv16,tv17,tv18,tv19,tv20;
    private TextView tv21,tv22,tv23,tv24,tv25,tv26,tv27,tv28,tv29,tv30,tv31,tv32,tv33,tv34,tv35,tv36,tv37,tv38,tv39,tv40;
    private TextView tv41,tv42,tv43,tv44,tv45,tv46,tv47,tv48,tv49,tv50,tv51,tv52,tv53,tv54,tv55,tv56,tv57,tv58,tv59,tv60;
    private TextView tv61,tv62,tv63,tv64,tv65,tv66,tv67,tv68,tv69,tv70,tv71,tv72,tv73,tv74,tv75,tv76,tv77,tv78,tv79,tv80;
    private TextView tv81,tv82,tv83,tv84,tv85,tv86,tv87,tv88,tv89,tv90,tv91,tv92,tv93,tv94,tv95,tv96,tv97,tv98,tv99,tv100;
    private TextView tv101,tv102,tv103,tv104,tv105,tv106,tv107,tv108,tv109,tv110,tv111,tv112,tv113,tv114,tv115,tv116,tv117,tv118,tv119,tv120;
    private TextView tv121,tv122,tv123,tv124,tv125,tv126,tv127,tv128,tv129,tv130,tv131,tv132,tv133,tv134,tv135,tv136,tv137,tv138,tv139,tv140;
    private TextView tv141,tv142,tv143,tv144,tv145,tv146,tv147,tv148,tv149,tv150,tv151,tv152,tv153,tv154,tv155,tv156,tv157,tv158,tv159,tv160;
    private TextView tv161,tv162,tv163,tv164,tv165,tv166,tv167,tv168,tv169,tv170,tv171,tv172,tv173;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dental_exam);

        ImageButton btn_back ;
        btn_back = findViewById(R.id.btn_back) ;
        btn_back.setOnClickListener(view -> finish());
    }
}