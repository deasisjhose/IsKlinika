package com.capstone.capstone_group.isklinika;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.TextView;

/*
This method is used to display the result of the dental examination
 */
public class ActivityDentalExam extends AppCompatActivity {

    private TextView tv_dentalDate, tv_dentalDr, tv_dentalName, tv_dentalSY ;
    private TextView tv_calculusYes, tv_calculusNo, tv_gingivaYes, tv_gingivaNo, tv_pocketYes, tv_pocketNo, tv_facialYes, tv_facialNo ;
    private TextView tv0,tv1,tv2,tv3,tv4,tv5,tv6,tv7,tv8,tv9,tv10,tv11,tv12,tv13,tv14,tv15,tv16,tv17,tv18,tv19,tv20;
    private TextView tv21,tv22,tv23,tv24,tv25,tv26,tv27,tv28,tv29,tv30,tv31,tv32,tv33,tv34,tv35,tv36,tv37,tv38,tv39,tv40;
    private TextView tv41,tv42,tv43,tv44,tv45,tv46,tv47,tv48,tv49,tv50,tv51,tv52,tv53,tv54,tv55,tv56,tv57,tv58,tv59,tv60;
    private TextView tv61,tv62,tv63,tv64,tv65,tv66,tv67,tv68,tv69,tv70,tv71,tv72,tv73,tv74,tv75,tv76,tv77,tv78,tv79,tv80;
    private TextView tv81,tv82,tv83,tv84,tv85,tv86,tv87,tv88,tv89,tv90,tv91,tv92,tv93,tv94,tv95,tv96,tv97,tv98,tv99,tv100;
    private TextView tv101,tv102,tv103,tv104,tv105,tv106,tv107,tv108,tv109,tv110,tv111,tv112,tv113,tv114,tv115,tv116,tv117,tv118,tv119,tv120;
    private TextView tv121,tv122,tv123,tv124,tv125,tv126,tv127,tv128,tv129,tv130,tv131,tv132,tv133,tv134,tv135,tv136,tv137,tv138,tv139,tv140;
    private TextView tv141,tv142,tv143,tv144,tv145,tv146,tv147,tv148,tv149,tv150,tv151,tv152,tv153,tv154,tv155,tv156,tv157,tv158,tv159,tv160;
    private TextView tv161,tv162,tv163,tv164,tv165,tv166,tv167,tv168,tv169,tv170,tv171,tv172,tv173;
    private Intent intent ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dental_exam);

        ImageButton btn_back ;
        btn_back = findViewById(R.id.btn_back) ;
        btn_back.setOnClickListener(view -> finish());

        intent = getIntent() ;
        ClassDental dental = intent.getParcelableExtra("dental");

        this.tv_dentalDate = findViewById(R.id.tv_dentalDate) ;
        this.tv_dentalDr = findViewById(R.id.tv_dentalDr) ;
        this.tv_dentalName = findViewById(R.id.tv_dentalName) ;
        this.tv_dentalSY = findViewById(R.id.tv_dentalSY) ;
        this.tv_calculusYes = findViewById(R.id.tv_calculusYes) ;
        this.tv_calculusNo = findViewById(R.id.tv_calculusNo) ;
        this.tv_gingivaYes = findViewById(R.id.tv_gingivaYes) ;
        this.tv_gingivaNo = findViewById(R.id.tv_gingivaNo) ;
        this.tv_pocketYes = findViewById(R.id.tv_pocketYes) ;
        this.tv_pocketNo = findViewById(R.id.tv_pocketNo) ;
        this.tv_facialYes = findViewById(R.id.tv_facialYes) ;
        this.tv_facialNo = findViewById(R.id.tv_facialNo) ;

        tv_dentalDate.setText(dental.getAdeDate());
        tv_dentalDr.setText(dental.getClinician());
        tv_dentalName.setText(dental.getName());
        tv_dentalSY.setText("SY: " + dental.getSchoolYear());



        if(dental.getCalculus().equals("true")){
            tv_calculusYes.setTextColor(Color.parseColor("#ff781f"));
        }else{
            tv_calculusNo.setTextColor(Color.parseColor("#ff781f"));
        }

        if(dental.getGingiva().equals("true")){
            tv_gingivaYes.setTextColor(Color.parseColor("#ff781f"));
        }else{
            tv_gingivaNo.setTextColor(Color.parseColor("#ff781f"));
        }

        if(dental.getPocket().equals("true")){
            tv_pocketYes.setTextColor(Color.parseColor("#ff781f"));
        }else{
            tv_pocketNo.setTextColor(Color.parseColor("#ff781f"));
        }

        if(dental.getAnomaly().equals("true")){
            tv_facialYes.setTextColor(Color.parseColor("#ff781f"));
        }else{
            tv_facialNo.setTextColor(Color.parseColor("#ff781f"));
        }

        this.tv0 = findViewById(R.id.tv0) ;
        this.tv1 = findViewById(R.id.tv1) ;
        this.tv2 = findViewById(R.id.tv2) ;
        this.tv3 = findViewById(R.id.tv3) ;
        this.tv4 = findViewById(R.id.tv4) ;
        this.tv5 = findViewById(R.id.tv5) ;
        this.tv6 = findViewById(R.id.tv6) ;
        this.tv7 = findViewById(R.id.tv7) ;
        this.tv8 = findViewById(R.id.tv8) ;
        this.tv9 = findViewById(R.id.tv9) ;

        this.tv10 = findViewById(R.id.tv10) ;
        this.tv11 = findViewById(R.id.tv11) ;
        this.tv12 = findViewById(R.id.tv12) ;
        this.tv13 = findViewById(R.id.tv13) ;
        this.tv14 = findViewById(R.id.tv14) ;
        this.tv15 = findViewById(R.id.tv15) ;
        this.tv16 = findViewById(R.id.tv16) ;
        this.tv17 = findViewById(R.id.tv17) ;
        this.tv18 = findViewById(R.id.tv18) ;
        this.tv19 = findViewById(R.id.tv19) ;

        this.tv20 = findViewById(R.id.tv20) ;
        this.tv21 = findViewById(R.id.tv21) ;
        this.tv22 = findViewById(R.id.tv22) ;
        this.tv23 = findViewById(R.id.tv23) ;
        this.tv24 = findViewById(R.id.tv24) ;
        this.tv25 = findViewById(R.id.tv25) ;
        this.tv26 = findViewById(R.id.tv26) ;
        this.tv27 = findViewById(R.id.tv27) ;
        this.tv28 = findViewById(R.id.tv28) ;
        this.tv29 = findViewById(R.id.tv29) ;

        this.tv30 = findViewById(R.id.tv30) ;
        this.tv31 = findViewById(R.id.tv31) ;
        this.tv32 = findViewById(R.id.tv32) ;
        this.tv33 = findViewById(R.id.tv33) ;
        this.tv34 = findViewById(R.id.tv34) ;
        this.tv35 = findViewById(R.id.tv35) ;
        this.tv36 = findViewById(R.id.tv36) ;
        this.tv37 = findViewById(R.id.tv37) ;
        this.tv38 = findViewById(R.id.tv38) ;
        this.tv39 = findViewById(R.id.tv39) ;

        this.tv40 = findViewById(R.id.tv40) ;
        this.tv41 = findViewById(R.id.tv41) ;
        this.tv42 = findViewById(R.id.tv42) ;
        this.tv43 = findViewById(R.id.tv43) ;
        this.tv44 = findViewById(R.id.tv44) ;
        this.tv45 = findViewById(R.id.tv45) ;
        this.tv46 = findViewById(R.id.tv46) ;
        this.tv47 = findViewById(R.id.tv47) ;
        this.tv48 = findViewById(R.id.tv48) ;
        this.tv49 = findViewById(R.id.tv49) ;

        this.tv50 = findViewById(R.id.tv50) ;
        this.tv51 = findViewById(R.id.tv51) ;
        this.tv52 = findViewById(R.id.tv52) ;
        this.tv53 = findViewById(R.id.tv53) ;
        this.tv54 = findViewById(R.id.tv54) ;
        this.tv55 = findViewById(R.id.tv55) ;
        this.tv56 = findViewById(R.id.tv56) ;
        this.tv57 = findViewById(R.id.tv57) ;
        this.tv58 = findViewById(R.id.tv58) ;
        this.tv59 = findViewById(R.id.tv59) ;

        this.tv60 = findViewById(R.id.tv60) ;
        this.tv61 = findViewById(R.id.tv61) ;
        this.tv62 = findViewById(R.id.tv62) ;
        this.tv63 = findViewById(R.id.tv63) ;
        this.tv64 = findViewById(R.id.tv64) ;
        this.tv65 = findViewById(R.id.tv65) ;
        this.tv66 = findViewById(R.id.tv66) ;
        this.tv67 = findViewById(R.id.tv67) ;
        this.tv68 = findViewById(R.id.tv68) ;
        this.tv69 = findViewById(R.id.tv69) ;

        this.tv70 = findViewById(R.id.tv70) ;
        this.tv71 = findViewById(R.id.tv71) ;
        this.tv72 = findViewById(R.id.tv72) ;
        this.tv73 = findViewById(R.id.tv73) ;
        this.tv74 = findViewById(R.id.tv74) ;
        this.tv75 = findViewById(R.id.tv75) ;
        this.tv76 = findViewById(R.id.tv76) ;
        this.tv77 = findViewById(R.id.tv77) ;
        this.tv78 = findViewById(R.id.tv78) ;
        this.tv79 = findViewById(R.id.tv79) ;

        this.tv80 = findViewById(R.id.tv80) ;
        this.tv81 = findViewById(R.id.tv81) ;
        this.tv82 = findViewById(R.id.tv82) ;
        this.tv83 = findViewById(R.id.tv83) ;
        this.tv84 = findViewById(R.id.tv84) ;
        this.tv85 = findViewById(R.id.tv85) ;
        this.tv86 = findViewById(R.id.tv86) ;
        this.tv87 = findViewById(R.id.tv87) ;
        this.tv88 = findViewById(R.id.tv88) ;
        this.tv89 = findViewById(R.id.tv89) ;

        this.tv90 = findViewById(R.id.tv90) ;
        this.tv91 = findViewById(R.id.tv91) ;
        this.tv92 = findViewById(R.id.tv92) ;
        this.tv93 = findViewById(R.id.tv93) ;
        this.tv94 = findViewById(R.id.tv94) ;
        this.tv95 = findViewById(R.id.tv95) ;
        this.tv96 = findViewById(R.id.tv96) ;
        this.tv97 = findViewById(R.id.tv97) ;
        this.tv98 = findViewById(R.id.tv98) ;
        this.tv99 = findViewById(R.id.tv99) ;
        this.tv100 = findViewById(R.id.tv100) ;
        tv101 = findViewById(R.id.tv101);
        tv102 = findViewById(R.id.tv102);
        tv103 = findViewById(R.id.tv103);
        tv104 = findViewById(R.id.tv104);
        tv105 = findViewById(R.id.tv105);
        tv106 = findViewById(R.id.tv106);
        tv107 = findViewById(R.id.tv107);
        tv108 = findViewById(R.id.tv108);
        tv109 = findViewById(R.id.tv109);
        tv110 = findViewById(R.id.tv110);
        tv111 = findViewById(R.id.tv111);
        tv112 = findViewById(R.id.tv112);
        tv113 = findViewById(R.id.tv113);
        tv114 = findViewById(R.id.tv114);
        tv115 = findViewById(R.id.tv115);
        tv116 = findViewById(R.id.tv116);
        tv117 = findViewById(R.id.tv117);
        tv118 = findViewById(R.id.tv118);
        tv119 = findViewById(R.id.tv119);
        tv120 = findViewById(R.id.tv120);
        tv121 = findViewById(R.id.tv121);
        tv122 = findViewById(R.id.tv122);
        tv123 = findViewById(R.id.tv123);
        tv124 = findViewById(R.id.tv124);
        tv125 = findViewById(R.id.tv125);
        tv126 = findViewById(R.id.tv126);
        tv127 = findViewById(R.id.tv127);
        tv128 = findViewById(R.id.tv128);
        tv129 = findViewById(R.id.tv129);
        tv130 = findViewById(R.id.tv130);
        tv131 = findViewById(R.id.tv131);
        tv132 = findViewById(R.id.tv132);
        tv133 = findViewById(R.id.tv133);
        tv134 = findViewById(R.id.tv134);
        tv135 = findViewById(R.id.tv135);
        tv136 = findViewById(R.id.tv136);
        tv137 = findViewById(R.id.tv137);
        tv138 = findViewById(R.id.tv138);
        tv139 = findViewById(R.id.tv139);
        tv140 = findViewById(R.id.tv140);
        tv141 = findViewById(R.id.tv141);
        tv142 = findViewById(R.id.tv142);
        tv143 = findViewById(R.id.tv143);
        tv144 = findViewById(R.id.tv144);
        tv145 = findViewById(R.id.tv145);
        tv146 = findViewById(R.id.tv146);
        tv147 = findViewById(R.id.tv147);
        tv148 = findViewById(R.id.tv148);
        tv149 = findViewById(R.id.tv149);
        tv150 = findViewById(R.id.tv150);
        tv151 = findViewById(R.id.tv151);
        tv152 = findViewById(R.id.tv152);
        tv153 = findViewById(R.id.tv153);
        tv154 = findViewById(R.id.tv154);
        tv155 = findViewById(R.id.tv155);
        tv156 = findViewById(R.id.tv156);
        tv157 = findViewById(R.id.tv157);
        tv158 = findViewById(R.id.tv158);
        tv159 = findViewById(R.id.tv159);
        tv160 = findViewById(R.id.tv160);
        tv161 = findViewById(R.id.tv161);
        tv162 = findViewById(R.id.tv162);
        tv163 = findViewById(R.id.tv163);
        tv164 = findViewById(R.id.tv164);
        tv165 = findViewById(R.id.tv165);
        tv166 = findViewById(R.id.tv166);
        tv167 = findViewById(R.id.tv167);
        tv168 = findViewById(R.id.tv168);
        tv169 = findViewById(R.id.tv169);
        tv170 = findViewById(R.id.tv170);
        tv171 = findViewById(R.id.tv171);
        tv172 = findViewById(R.id.tv172);
        tv173 = findViewById(R.id.tv173);

        tv46.setText(dental.getInputAt(46));
        tv47.setText(dental.getInputAt(47));
        tv48.setText(dental.getInputAt(48));
        tv49.setText(dental.getInputAt(49));
        tv50.setText(dental.getInputAt(50));
        tv51.setText(dental.getInputAt(51));
        tv52.setText(dental.getInputAt(52));
        tv53.setText(dental.getInputAt(53));

        tv54.setText(dental.getInputAt(54));
        tv55.setText(dental.getInputAt(55));
        tv56.setText(dental.getInputAt(56));
        tv57.setText(dental.getInputAt(57));
        tv58.setText(dental.getInputAt(58));
        tv59.setText(dental.getInputAt(59));
        tv60.setText(dental.getInputAt(60));
        tv61.setText(dental.getInputAt(61));

        tv94.setText(dental.getInputAt(94));
        tv95.setText(dental.getInputAt(95));
        tv96.setText(dental.getInputAt(96));
        tv97.setText(dental.getInputAt(97));
        tv98.setText(dental.getInputAt(98));
        tv99.setText(dental.getInputAt(99));
        tv100.setText(dental.getInputAt(100));
        tv101.setText(dental.getInputAt(101));

        tv102.setText(dental.getInputAt(102));
        tv103.setText(dental.getInputAt(103));
        tv104.setText(dental.getInputAt(104));
        tv105.setText(dental.getInputAt(105));
        tv106.setText(dental.getInputAt(106));
        tv107.setText(dental.getInputAt(107));
        tv108.setText(dental.getInputAt(108));
        tv109.setText(dental.getInputAt(109));

        tv156.setText(dental.getInputAt(156));
        tv158.setText(dental.getInputAt(158));
        tv160.setText(dental.getInputAt(160));
        tv162.setText(dental.getInputAt(162));
        tv164.setText(dental.getInputAt(164));
        tv168.setText(dental.getInputAt(168));
        tv170.setText(dental.getInputAt(170));
        tv172.setText(dental.getInputAt(172));

    }
}