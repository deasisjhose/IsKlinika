package com.capstone.capstone_group.isklinika;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.content.res.ResourcesCompat;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.button.MaterialButtonToggleGroup;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.textview.MaterialTextView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ActivityLanding extends AppCompatActivity implements InterfaceIsklinika, View.OnClickListener{

    //Firebase
    FirebaseDatabase db = FirebaseDatabase.getInstance();   // getting real time database
    public DatabaseReference database = db.getReference();
//    public DatabaseReference databaseStudentReference= db.getReference("studentInfo");

    //Variables
    private ClassBotnav botNav ;
    private Intent intent ;
    private int checkActive, prevActive ; // 10 = home 20 = profile 30 = mail 40 = modules
    private final String TAG ="LANDING//" ;

    //data sent from user login
    private String userType ;
    private ClassStudentInfo studentInfo ; //if user type is student
    private ArrayList<ClassStudentInfo> children ;// children of the parent user
    private ClassParentInfo parentInfo ;  //if user type is parent

    //activity views
    private CoordinatorLayout coordinatorLayout ;
    private AppBarLayout appBar ;

    private MaterialTextView mtv_pageTitle ; // collapsible title
    private FrameLayout fragment_layout ;
    private NestedScrollView nestedScrollView ;

    // children profile active check == 20
    private LinearLayout layout_pageTitle_Children ;
    private ShapeableImageView shapeImg_profileChildIdPic ;
    private MaterialTextView mtv_profileChildIdNum, mtv_profileChildFullName ;
    private MaterialButtonToggleGroup mbtg_childrenButton ;
    private RecyclerView recycle_profileChildrenName ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing);

        intent = getIntent() ;
        this.userType = intent.getStringExtra("userType") ;
        checkUser();

        checkActive = 10 ;

        buildBar();
        buildViews();

        getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.nav_default_pop_enter_anim, R.anim.nav_default_pop_exit_anim).replace(R.id.fragment_layout, new FragmentModule()).commit() ;
    }


    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "onStart: ");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume: ");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause: ");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop: ");
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void buildBar() {
        this.botNav = new ClassBotnav(this) ;
        botNav.btn_nav_home.setOnClickListener(this);
        botNav.btn_nav_profile.setOnClickListener(this);
        botNav.btn_nav_mail.setOnClickListener(this);
        botNav.btn_nav_modules.setOnClickListener(this);

        //set active btn tint
        botNav.btn_nav_home.setIconTint(getColorStateList(R.color.active_nav_btn));


        //profile fragment top
        this.shapeImg_profileChildIdPic = findViewById(R.id.shapeImg_profileChildIdPic) ;
        this.mtv_profileChildIdNum = findViewById(R.id.mtv_profileChildIdNum) ;
        this.mtv_profileChildFullName = findViewById(R.id.mtv_profileChildFullName) ;
        this.mbtg_childrenButton = findViewById(R.id.mbtg_childrenButton) ;
    }

    @Override
    public void buildViews() {
        this.coordinatorLayout = findViewById(R.id.coordinatorLayout);
        this.appBar = findViewById(R.id.appbar) ;
        this.nestedScrollView = findViewById(R.id.navScroller) ;
        this.fragment_layout = findViewById(R.id.fragment_layout) ;
        this.layout_pageTitle_Children = findViewById(R.id.layout_pageTitle_Children) ;

        this.mtv_pageTitle = findViewById(R.id.mtv_pageTitle) ;
        mtv_pageTitle.setVisibility(View.VISIBLE);

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
                retrieveDataParentUser();
                break;
        }
    }
    

    @Override
    public void retrieveDataParentUser() {
        ArrayList<ClassStudentInfo> childrenInfo = new ArrayList<>();

        database.child("studentInfo").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(childrenInfo.size() != 0){
                    childrenInfo.clear();
                }
                int i ;
                for (DataSnapshot postSnapshot: snapshot.getChildren()){

                    for(i = 0 ; i < parentInfo.getChildrenSize() ; i++){
                        if(postSnapshot.getKey().equals(parentInfo.getIdNumber(i))){
                            ClassStudentInfo child = postSnapshot.getValue(ClassStudentInfo.class) ;
                            childrenInfo.add(child) ;
//                            Log.d(TAG, "onDataChange: parentChild = " + child.toString());
                        }
                    }
                }



//                setChildren(childrenInfo);
                dataInToggleChildren(childrenInfo);

            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }



    private void dataInToggleChildren(ArrayList<ClassStudentInfo> childrenArrayList){
        this.children = new ArrayList<>() ;
        children = childrenArrayList ;

        mbtg_childrenButton.removeAllViews();

        for(int j = 0 ; j < children.size() ; j++){
            MaterialButton button = (MaterialButton) getLayoutInflater().inflate(R.layout.button_naem, null);
            button.setId(j);
            button.setText(children.get(j).getFirstName());
            mbtg_childrenButton.addView(button, -2, -1);

        }
        mbtg_childrenButton.clearChecked();

        mbtg_childrenButton.addOnButtonCheckedListener((group, checkedId, isChecked) -> {
            if(checkedId >= 0){
                if(isChecked){
                    MaterialButton button3 = findViewById(checkedId);
                    button3.setBackgroundColor(Color.WHITE);
                    button3.setTextColor(Color.BLACK);
                    studentInfo = children.get(checkedId);
                    mtv_profileChildFullName.setText(children.get(checkedId).getFullName());
                    mtv_profileChildIdNum.setText(children.get(checkedId).getIdNumGradeSection());
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_layout, new FragmentChildrenProfile()).commit() ;

                    Log.d(TAG, "onButtonChecked: checkId = " + checkedId);
                }else{
                    MaterialButton buttonCheck = findViewById(checkedId);
                    buttonCheck.setBackgroundColor(Color.BLACK);
                    buttonCheck.setTextColor(Color.WHITE);
                    buttonCheck.setClickable(true);
                }
            }
        });

    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onClick(View view) {
        Fragment selectedFragment ;

        if(view.getId() == R.id.btn_nav_home){
            mbtg_childrenButton.clearChecked();
            setBtnInactive();
            botNav.btn_nav_home.setIconTint(getColorStateList(R.color.active_nav_btn));
            checkActive = 10 ; //check ative activity
            layout_pageTitle_Children.setVisibility(View.GONE);
            mtv_pageTitle.setVisibility(View.VISIBLE);
            mtv_pageTitle.setText("Child Health");
            mtv_pageTitle.setCompoundDrawablesWithIntrinsicBounds(ResourcesCompat.getDrawable(getResources(),R.drawable.ic_home, null), null, null, null);

            selectedFragment = new FragmentModule() ;
            getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.nav_default_pop_enter_anim, R.anim.nav_default_pop_exit_anim).replace(R.id.fragment_layout, selectedFragment).commit() ;

        }else if(view.getId() == R.id.btn_nav_profile){

                setBtnInactive();
                botNav.btn_nav_profile.setIconTint(getColorStateList(R.color.active_nav_btn));
                checkActive = 20 ; //check ative activity
                nestedScrollView.setNestedScrollingEnabled(false);
                mtv_pageTitle.setVisibility(View.GONE);
                layout_pageTitle_Children.setVisibility(View.VISIBLE);

               switch (userType){
                   case "Student":
                       mtv_profileChildFullName.setText(studentInfo.getFullName());
                       mtv_profileChildIdNum.setText(studentInfo.getIdNumGradeSection());
                       selectedFragment = new FragmentChildrenProfile() ;
                       mbtg_childrenButton.setVisibility(View.GONE);

                       getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.nav_default_pop_enter_anim, R.anim.nav_default_pop_exit_anim).replace(R.id.fragment_layout, selectedFragment).commit() ;
                       break;
                   case "Parent":
//                       MaterialButton button = findViewById(0) ;
//                       button.setBackgroundColor(Color.WHITE);
//                       button.setTextColor(Color.BLACK);

                       mbtg_childrenButton.check(0);
                       studentInfo = children.get(0) ;

                       mtv_profileChildFullName.setText(children.get(0).getFullName());
                       mtv_profileChildIdNum.setText(children.get(0).getIdNumGradeSection());
                       Log.d(TAG, "dataInToggleChildren: onNAVCLICK");
//                       getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.nav_default_pop_enter_anim, R.anim.nav_default_pop_exit_anim).replace(R.id.fragment_layout, new FragmentChildrenProfile()).commit() ;

                       break;
               }
            }else if(view.getId() == R.id.btn_nav_mail){
                    mbtg_childrenButton.clearChecked();
                    setBtnInactive();
                    botNav.btn_nav_mail.setIconTint(getColorStateList(R.color.active_nav_btn));
                    checkActive = 30 ; //check ative activity
                    mtv_pageTitle.setVisibility(View.VISIBLE);
                    layout_pageTitle_Children.setVisibility(View.GONE);
                    nestedScrollView.setNestedScrollingEnabled(true);

                    selectedFragment = new FragmentNotifs() ;
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_layout, selectedFragment).commit() ;


                }
//                    else if(view.getId() == R.id.btn_nav_modules){
//                        mbtg_childrenButton.clearChecked();
//                        setBtnInactive();
//                        botNav.btn_nav_modules.setIconTint(getColorStateList(R.color.active_nav_btn));
//                        checkActive = 40 ; //check ative activity
//                        layout_pageTitle_Children.setVisibility(View.GONE);
//                        mtv_pageTitle.setVisibility(View.VISIBLE);
//                        mtv_pageTitle.setText("Child Health");
//                        mtv_pageTitle.setCompoundDrawablesWithIntrinsicBounds(ResourcesCompat.getDrawable(getResources(),R.drawable.ic_view_module, null), null, null, null);
//
//                        selectedFragment = new FragmentModule() ;
//                        getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.nav_default_pop_enter_anim, R.anim.nav_default_pop_exit_anim).replace(R.id.fragment_layout, selectedFragment).commit() ;
//                    }
    }

    public void setBtnInactive(){
        botNav.btn_nav_home.setIconTint(getColorStateList(R.color.inactive_nav_btn));
        botNav.btn_nav_profile.setIconTint(getColorStateList(R.color.inactive_nav_btn));
        botNav.btn_nav_mail.setIconTint(getColorStateList(R.color.inactive_nav_btn));
        botNav.btn_nav_modules.setIconTint(getColorStateList(R.color.inactive_nav_btn));
    }

    private void setChildren(ArrayList<ClassStudentInfo> childrenArrayList){
        this.children = new ArrayList<>() ;
        children = childrenArrayList ;
    }

    public ClassStudentInfo getStudentInfo(){
        return studentInfo ;
    }
    public ArrayList<ClassStudentInfo> getChildren() {
        return children;
    }
    public String getUserType() {
        return userType;
    }
}