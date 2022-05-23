package com.capstone.capstone_group.isklinika;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;

import com.google.android.material.button.MaterialButton;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class ActivityFirstTime extends AppCompatActivity implements View.OnClickListener {

    //Firebase
    FirebaseDatabase db = FirebaseDatabase.getInstance();   // getting real time database
    public DatabaseReference database = db.getReference();

    //Variables
    private ClassBotnav botNav ;
    private Intent intent ;
    private final String TAG ="FIRSTTIME//" ;

    //data sent from user login
    private String userType ;
    private ClassStudentInfo studentInfo ; //if user type is student
    private ArrayList<ClassStudentInfo> children ;// children of the parent user
    private ClassParentInfo parentInfo ;  //if user type is parent

    private ConstraintLayout constraint_welcome, constraint_terms ;
    private ImageView img_welcome, img_welcome1, img_welcome2, img_welcome3, img_welcome4, img_welcome5, img_welcome6,img_welcome7, img_welcome8, img_welcome9, img_welcome10 ;
    private MaterialButton mbtn_getStart, mbtn_startNext, mbtn_startPrev, button_termsContinue ;
    private CheckBox checkBox_terms ;


    //next checker
    private int checkWelcome ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_time);

        intent = getIntent() ;
        buildViews() ;
        this.checkWelcome = 0 ;

        this.userType = intent.getStringExtra("userType") ;
        Log.d(TAG, "onCreate: USERTYPE = " + userType);
        checkUser();

    }

    public void buildViews(){

        this.constraint_welcome = findViewById(R.id.constraint_welcome) ;
        this.img_welcome = findViewById(R.id.img_welcome) ;
        this.img_welcome1 = findViewById(R.id.img_welcome1) ;
        this.img_welcome2 = findViewById(R.id.img_welcome2) ;
        this.img_welcome3 = findViewById(R.id.img_welcome3) ;
        this.img_welcome4 = findViewById(R.id.img_welcome4) ;
        this.img_welcome5 = findViewById(R.id.img_welcome5) ;
        this.img_welcome6 = findViewById(R.id.img_welcome6) ;
        this.img_welcome7 = findViewById(R.id.img_welcome7) ;
        this.img_welcome8 = findViewById(R.id.img_welcome8) ;
        this.img_welcome9 = findViewById(R.id.img_welcome9) ;
        this.img_welcome10 = findViewById(R.id.img_welcome10) ;
        this.mbtn_getStart = findViewById(R.id.mbtn_getStart) ;
        this.mbtn_startNext = findViewById(R.id.mbtn_startNext) ;
        this.mbtn_startPrev = findViewById(R.id.mbtn_startPrev) ;
        this.constraint_terms = findViewById(R.id.constraint_terms) ;
        this.checkBox_terms = findViewById(R.id.checkBox_terms) ;
        this.button_termsContinue = findViewById(R.id.btn_termsContinue) ;

        mbtn_getStart.setOnClickListener(this);
        mbtn_startNext.setOnClickListener(this);
        mbtn_startPrev.setOnClickListener(this);
        button_termsContinue.setOnClickListener(this);
        
    }

    //This method is used to check the user that logged in
    public void checkUser() {
        switch (userType){
            case "Student":
                studentInfo = intent.getParcelableExtra("studentInfo") ;
                break;
            case "Parent":
                this.parentInfo = intent.getParcelableExtra("parentInfo") ;
                this.children = intent.getParcelableArrayListExtra("children") ;
                break;
        }
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.mbtn_getStart){
            img_welcome.setVisibility(View.GONE);
            mbtn_getStart.setVisibility(View.GONE);

            img_welcome1.setVisibility(View.VISIBLE);
            mbtn_startNext.setVisibility(View.VISIBLE);
            mbtn_startPrev.setVisibility(View.VISIBLE);

            checkWelcome += 1 ;
        } else if(view.getId() == R.id.mbtn_startNext){
                checkWelcome += 1 ;
                    switch (checkWelcome){
                        case  0:
                            break;
                        case 1:

                            break;
                        case 2:
                            img_welcome1.setVisibility(View.GONE);
                            img_welcome2.setVisibility(View.VISIBLE);
                            break;
                        case 3:
                            img_welcome2.setVisibility(View.GONE);
                            img_welcome3.setVisibility(View.VISIBLE);
                            break;
                        case 4:
                            img_welcome3.setVisibility(View.GONE);
                            img_welcome4.setVisibility(View.VISIBLE);
                            break;
                        case 5:
                            img_welcome4.setVisibility(View.GONE);
                            constraint_welcome.setBackgroundColor(Color.parseColor("#FF919A"));
                            img_welcome5.setVisibility(View.VISIBLE);
                            break;
                        case 6:
                            img_welcome5.setVisibility(View.GONE);
                            constraint_welcome.setBackgroundColor(Color.parseColor("#FFB198"));
                            img_welcome6.setVisibility(View.VISIBLE);
                            break;
                        case 7:
                            img_welcome6.setVisibility(View.GONE);
                            constraint_welcome.setBackgroundColor(Color.parseColor("#FFDE7D"));
                            img_welcome7.setVisibility(View.VISIBLE);
                            break;
                        case 8:
                            img_welcome7.setVisibility(View.GONE);
                            constraint_welcome.setBackgroundColor(Color.parseColor("#B3E678"));
                            img_welcome8.setVisibility(View.VISIBLE);
                            break;
                        case 9:
                            img_welcome8.setVisibility(View.GONE);
                            constraint_welcome.setBackgroundColor(Color.parseColor("#74D4FF"));
                            img_welcome9.setVisibility(View.VISIBLE);
                            break;
                        case 10:
                            img_welcome9.setVisibility(View.GONE);
                            constraint_welcome.setBackgroundColor(Color.parseColor("#BAA0FF"));
                            img_welcome10.setVisibility(View.VISIBLE);
                            break;
                        case 11:
                            constraint_welcome.setVisibility(View.GONE);
                            constraint_terms.setVisibility(View.VISIBLE);
                            break;
                    }
        } else if (view.getId() == R.id.mbtn_startPrev){
                checkWelcome -= 1 ;
                switch (checkWelcome){
                    case 0:
                        img_welcome1.setVisibility(View.GONE);
                        mbtn_startNext.setVisibility(View.GONE);
                        mbtn_startPrev.setVisibility(View.GONE);

                        img_welcome.setVisibility(View.VISIBLE);
                        mbtn_getStart.setVisibility(View.VISIBLE);
                        break;
                    case 1:
                        img_welcome2.setVisibility(View.GONE);
                        img_welcome1.setVisibility(View.VISIBLE);

                        break;
                    case 2:
                        img_welcome3.setVisibility(View.GONE);
                        img_welcome2.setVisibility(View.VISIBLE);
                        break;
                    case 3:
                        img_welcome4.setVisibility(View.GONE);
                        img_welcome3.setVisibility(View.VISIBLE);
                        break;
                    case 4:
                        img_welcome5.setVisibility(View.GONE);
                        constraint_welcome.setBackgroundColor(Color.parseColor("#4e73df"));
                        img_welcome4.setVisibility(View.VISIBLE);
                        break;
                    case 5:
                        img_welcome6.setVisibility(View.GONE);
                        constraint_welcome.setBackgroundColor(Color.parseColor("#FF919A"));
                        img_welcome5.setVisibility(View.VISIBLE);
                        break;
                    case 6:
                        img_welcome7.setVisibility(View.GONE);
                        constraint_welcome.setBackgroundColor(Color.parseColor("#FFB198"));
                        img_welcome6.setVisibility(View.VISIBLE);
                        break;
                    case 7:
                        img_welcome8.setVisibility(View.GONE);
                        constraint_welcome.setBackgroundColor(Color.parseColor("#FFDE7D"));
                        img_welcome7.setVisibility(View.VISIBLE);
                        break;
                    case 8:
                        img_welcome9.setVisibility(View.GONE);
                        constraint_welcome.setBackgroundColor(Color.parseColor("#B3E678"));
                        img_welcome8.setVisibility(View.VISIBLE);
                        break;
                    case 9:
                        img_welcome10.setVisibility(View.GONE);
                        constraint_welcome.setBackgroundColor(Color.parseColor("#74D4FF"));
                        img_welcome9.setVisibility(View.VISIBLE);
                        break;
                    case 10:
//
                        constraint_welcome.setBackgroundColor(Color.parseColor("#BAA0FF"));
                        img_welcome10.setVisibility(View.VISIBLE);
                        break;
                    case 11:

                        break;
            }
        } else if(view.getId() == R.id.btn_termsContinue){
            if(checkBox_terms.isChecked()){
                intent = new Intent(getBaseContext(), ActivityLanding.class);
                intent.putExtra("userType", userType) ;
                switch (userType){
                    case "Student":
                        intent.putExtra("studentInfo", studentInfo) ;
                        break;
                    case "Parent":
                        intent.putParcelableArrayListExtra("children", children) ;
                        intent.putExtra("parentInfo", parentInfo);
                        break;
                }

                startActivity(intent);
            }
        }
    }
}