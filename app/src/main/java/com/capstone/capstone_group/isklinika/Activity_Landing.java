package com.capstone.capstone_group.isklinika;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.textview.MaterialTextView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Activity_Landing extends AppCompatActivity implements Interface_Isklinika, View.OnClickListener{

    //Firebase
    FirebaseDatabase db = FirebaseDatabase.getInstance();   // getting real time database
    public DatabaseReference database = db.getReference();
//    public DatabaseReference databaseStudentReference= db.getReference("studentInfo");

    //Variables
    private Class_Botnav botNav ;
    private Intent intent ;
    private int checkActive, prevActive ; // 10 = home 20 = profile 30 = mail 40 = modules
    private final String TAG ="LANDING//" ;

    //data sent from user login
    private String userType ;
    private Class_StudentInfo studentInfo ; //if user type is student
    private Class_ParentInfo parentInfo ;  //if user type is parent

    //activity views
    private CollapsingToolbarLayout collapsingToolbarLayout ;
    private MaterialTextView mtv_pageTitle ;
    private View view ;
    private NestedScrollView nestedScrollView ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing);

        intent = getIntent() ;
        this.userType = intent.getStringExtra("userType") ;
        Log.d(TAG, "onCreate: userType = " + userType);
        checkUser();


//        nestedScrollView = findViewById(R.id.navScroller) ;
//        nestedScrollView.setNestedScrollingEnabled(false);


        buildBar();
        buildViews();

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_layout, new Fragment_Home()).commit() ;
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void buildBar() {
        this.botNav = new Class_Botnav(this) ;
        botNav.btn_nav_home.setOnClickListener(this);
        botNav.btn_nav_profile.setOnClickListener(this);
        botNav.btn_nav_mail.setOnClickListener(this);
        botNav.btn_nav_modules.setOnClickListener(this);

        this.collapsingToolbarLayout = findViewById(R.id.collapsingToolbarLayout) ;
        this.mtv_pageTitle = findViewById(R.id.mtv_pageTitle) ;

//        Typeface collapsed = ResourcesCompat.getFont(this, R.font.helveticacompressed);
//        collapsingToolbarLayout.setCollapsedTitleTypeface(collapsed);
//
//
//
//        Typeface expanded = ResourcesCompat.getFont(this, R.font.);
//        collapsingToolbarLayout.setExpandedTitleTypeface(expanded);
//        collapsingToolbarLayout.setExpandedTitleColor(ResourcesCompat.getColor(getResources(), R.color.light, null)) ;
//        collapsingToolbarLayout.setExpandedTitleMarginStart(40);




        //set active btn tint
        botNav.btn_nav_modules.setIconTint(getColorStateList(R.color.active_nav_btn));
        setBtnInactive(prevActive);
    }

    @Override
    public void buildViews() {


    }

    @Override
    public void checkUser() {
        Log.d(TAG, "checkUser: ");
        switch (userType){
            case "Student":
                studentInfo = intent.getParcelableExtra("studentInfo") ;
                break;
            case "Parent":
                this.parentInfo = intent.getParcelableExtra("parentInfo") ;
                Log.d(TAG, "checkUser: parentChildrenSize = " + parentInfo.getChildrenSize());
                retrieveDataParentUser();
                break;
        }
    }

    @Override
    public void dataInChildren(ArrayList<Class_StudentInfo> childrenInfo) {

    }

    @Override
    public void retrieveDataParentUser() {
        ArrayList<Class_StudentInfo> childrenInfo = new ArrayList<>();

        database.child("studentInfo").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(childrenInfo.size() != 0){
                    childrenInfo.clear(); ;
                }
                int i ;
                for (DataSnapshot postSnapshot: snapshot.getChildren()){

                    for(i = 0 ; i < parentInfo.getChildrenSize() ; i++){
                        if(postSnapshot.getKey().equals(parentInfo.getIdNumber(i))){
                            Class_StudentInfo child = postSnapshot.getValue(Class_StudentInfo.class) ;
                            childrenInfo.add(child) ;
                            Log.d(TAG, "onDataChange: parentChild = " + child.toString());
                        }
                    }
                }

//                dataInChildren(childrenInfo);

            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onClick(View view) {
        Fragment selectedFragment ;

        if(view.getId() == R.id.btn_nav_home){

            botNav.btn_nav_home.setIconTint(getColorStateList(R.color.active_nav_btn));
            checkActive = 10 ; //check ative activity
            selectedFragment = new Fragment_Home() ;
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_layout, selectedFragment).commit() ;
        }else if(view.getId() == R.id.btn_nav_profile){

                botNav.btn_nav_profile.setIconTint(getColorStateList(R.color.active_nav_btn));
                checkActive = 20 ; //check ative activity
//                mtv_pageTitle.setText("Children Profile");
//                mtv_pageTitle.setCompoundDrawablesWithIntrinsicBounds(ResourcesCompat.getDrawable(getResources(),R.drawable.ic_baseline_child_friendly_24, null), null, null, null);
                selectedFragment = new Fragment_Children_Profile() ;
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_layout, selectedFragment).commit() ;
            }else if(view.getId() == R.id.btn_nav_mail){

                    botNav.btn_nav_mail.setIconTint(getColorStateList(R.color.active_nav_btn));
                    selectedFragment = new Fragment_Notifs() ;
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_layout, selectedFragment).commit() ;
                    checkActive = 30 ; //check ative activity

                }else if(view.getId() == R.id.btn_nav_modules){

                        botNav.btn_nav_modules.setIconTint(getColorStateList(R.color.active_nav_btn));
                        selectedFragment = new Fragment_Module() ;
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_layout, selectedFragment).commit() ;
                        checkActive = 40 ; //check ative activity
                }
    }

    public void setBtnInactive(int check){
        switch (check){
            case 10:
                botNav.btn_nav_home.setIconTint(getColorStateList(R.color.inactive_nav_btn));
                break;
            case 20:
                botNav.btn_nav_profile.setIconTint(getColorStateList(R.color.inactive_nav_btn));
                break;
            case 30:
                botNav.btn_nav_mail.setIconTint(getColorStateList(R.color.inactive_nav_btn));
                break;
            case 40:
                botNav.btn_nav_modules.setIconTint(getColorStateList(R.color.inactive_nav_btn));
                break;
        }
    }




}