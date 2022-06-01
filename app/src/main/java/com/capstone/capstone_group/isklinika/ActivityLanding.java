package com.capstone.capstone_group.isklinika;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.content.res.ResourcesCompat;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Spinner;

import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.MaterialToolbar;
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

/*This activity is where the users land after logging in. This activity contains the different
    modules that the users can use.
 */
public class ActivityLanding extends AppCompatActivity implements View.OnClickListener{

    //Firebase
    FirebaseDatabase db = FirebaseDatabase.getInstance();   // getting real time database
    public DatabaseReference database = db.getReference();
//    public DatabaseReference databaseStudentReference= db.getReference("studentInfo");

    //Variables
    private ClassBotnav botNav ;
    private Intent intent ;
    private int checkActive, prevActive ; // 10 = home 20 = profile 30 = mail 40 = modules  //not followed
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
    private MaterialToolbar materialToolbar ;


    // children profile active check == 20
    private LinearLayout layout_pageTitle_Children ;
    private ShapeableImageView shapeImg_profileChildIdPic ;
    private MaterialTextView mtv_profileChildIdNum, mtv_profileChildFullName ;
    private MaterialButtonToggleGroup mbtg_childrenButton ;
    private Spinner spinner_childName ;
    private int lastSelected  = 0;

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


    //This method is used to build the bottom nav bar and as well as some of the other views
    public void buildBar() {
        this.botNav = new ClassBotnav(this) ;
        botNav.btn_nav_home.setOnClickListener(this);
        botNav.btn_nav_profile.setOnClickListener(this);
        botNav.btn_nav_mail.setOnClickListener(this);
        botNav.btn_nav_parent_profile.setOnClickListener(this);

        if(userType.equals("Student")){
            botNav.btn_nav_mail.setVisibility(View.GONE);

        }
        //set active btn tint
        botNav.btn_nav_home.setIconTint(getColorStateList(R.color.active_nav_btn));

        //profile fragment top
        this.shapeImg_profileChildIdPic = findViewById(R.id.shapeImg_profileChildIdPic) ;
        this.mtv_profileChildIdNum = findViewById(R.id.mtv_profileChildIdNum) ;
        this.mtv_profileChildFullName = findViewById(R.id.mtv_profileChildFullName) ;
        this.mbtg_childrenButton = findViewById(R.id.mbtg_childrenButton) ;
    }


    //This method is used to initialize the used views in the activity
    public void buildViews() {
        this.coordinatorLayout = findViewById(R.id.coordinatorLayout);
        this.appBar = findViewById(R.id.appbar);
        this.nestedScrollView = findViewById(R.id.navScroller);
        this.fragment_layout = findViewById(R.id.fragment_layout);
        this.layout_pageTitle_Children = findViewById(R.id.layout_pageTitle_Children);

        this.mtv_pageTitle = findViewById(R.id.mtv_pageTitle);
        mtv_pageTitle.setVisibility(View.VISIBLE);

        this.materialToolbar = findViewById(R.id.toolbar) ;
        materialToolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if (item.getItemId() == R.id.menu_logout){
                    intent = new Intent(getBaseContext(), MainActivity.class) ;
                    startActivity(intent);
                    finish();
                } if (item.getItemId() == R.id.menu_terms){
                    intent = new Intent(getBaseContext(), ActivityFirstTime.class) ;
                    intent.putExtra("userType", "About") ;
                    startActivity(intent);
                }

                return false;
            }
        });

    }

    //This method is used to check the user that logged in
    public void checkUser() {
        Log.d(TAG, "checkUser: ");
        switch (userType){
            case "Student":
                studentInfo = intent.getParcelableExtra("studentInfo") ;
                break;
            case "Parent":
                this.parentInfo = intent.getParcelableExtra("parentInfo") ;
                makeSpinnerChildren(intent.getParcelableArrayListExtra("children"));
//                retrieveDataParentUser();
                break;
        }
    }


    // This method creates the dropdown option in the children information tab.
    public void makeSpinnerChildren(ArrayList<ClassStudentInfo> childrenArrayList){
        this.children = new ArrayList<ClassStudentInfo>() ;
        children = childrenArrayList ;
        this.spinner_childName = findViewById(R.id.spinner_childName) ;
        ArrayAdapter<ClassStudentInfo> adapter = new ArrayAdapter<>(this, R.layout.spinner_child_profile, childrenArrayList) ;
        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spinner_childName.setVisibility(View.VISIBLE);
        spinner_childName.setAdapter(adapter);
        spinner_childName.setSelection(0);

        spinner_childName.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.d(TAG, "onItemSelected: selectedSort = " + spinner_childName.getSelectedItem());
                studentInfo = children.get(position);
                mtv_profileChildFullName.setText(children.get(position).getFullName());
                mtv_profileChildIdNum.setText(children.get(position).getIdNumGradeSection());
                lastSelected = position;
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_layout, new FragmentChildrenProfile()).commit() ;
                checkActive = 25 ;
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
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
//            checkActive = 10 ; //check ative activity
            layout_pageTitle_Children.setVisibility(View.GONE);
            mtv_pageTitle.setVisibility(View.VISIBLE);
            mtv_pageTitle.setText("Welcome");
            mtv_pageTitle.setCompoundDrawablesWithIntrinsicBounds(ResourcesCompat.getDrawable(getResources(),R.drawable.ic_home, null), null, null, null);

            selectedFragment = new FragmentModule() ;
            getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.nav_default_pop_enter_anim, R.anim.nav_default_pop_exit_anim).replace(R.id.fragment_layout, selectedFragment).commit() ;

        }else if(view.getId() == R.id.btn_nav_profile){

                setBtnInactive();
                botNav.btn_nav_profile.setIconTint(getColorStateList(R.color.active_nav_btn));
                nestedScrollView.setNestedScrollingEnabled(false);
                mtv_pageTitle.setVisibility(View.GONE);
                layout_pageTitle_Children.setVisibility(View.VISIBLE);

               switch (userType){
                   case "Student":
                       mtv_profileChildFullName.setText(studentInfo.getFullName());
                       mtv_profileChildIdNum.setText(studentInfo.getIdNumGradeSection());
                       selectedFragment = new FragmentChildrenProfile() ;
//                       spinner_childName.setVisibility(View.GONE);
                       getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.nav_default_pop_enter_anim, R.anim.nav_default_pop_exit_anim).replace(R.id.fragment_layout, selectedFragment).commit() ;
                       break;
                   case "Parent":
                       mbtg_childrenButton.check(lastSelected);
                       studentInfo = children.get(lastSelected) ;

                       if(checkActive == 25)
                           getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.nav_default_pop_enter_anim, R.anim.nav_default_pop_exit_anim).replace(R.id.fragment_layout, new FragmentChildrenProfile()).commit() ;

                       break;
               }
            }else if(view.getId() == R.id.btn_nav_mail){
                    mbtg_childrenButton.clearChecked();
                    setBtnInactive();
                    botNav.btn_nav_mail.setIconTint(getColorStateList(R.color.active_nav_btn));
//                    checkActive = 30 ; //check ative activity
                    mtv_pageTitle.setVisibility(View.VISIBLE);
                    mtv_pageTitle.setText("Notifications");
                    layout_pageTitle_Children.setVisibility(View.GONE);
                    nestedScrollView.setNestedScrollingEnabled(true);

                    selectedFragment = new FragmentNotifs() ;
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_layout, selectedFragment).commit() ;


                } else if(view.getId() == R.id.btn_nav_parent_profile){
                        mbtg_childrenButton.clearChecked();
                        setBtnInactive();
                        botNav.btn_nav_parent_profile.setIconTint(getColorStateList(R.color.active_nav_btn));
//                        checkActive = 40 ; //check ative activity
                        layout_pageTitle_Children.setVisibility(View.GONE);
                        mtv_pageTitle.setVisibility(View.VISIBLE);
                        mtv_pageTitle.setText("User Profile");
                        mtv_pageTitle.setCompoundDrawablesWithIntrinsicBounds(ResourcesCompat.getDrawable(getResources(),R.drawable.ic_people, null), null, null, null);

                        selectedFragment = new FragmentUserProfile() ;
                        getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.nav_default_pop_enter_anim, R.anim.nav_default_pop_exit_anim).replace(R.id.fragment_layout, selectedFragment).commit() ;
                    }
    }


    //This method is used to set the colors of the inactive tabs on the bottom navigation bottom bar
    public void setBtnInactive(){
        botNav.btn_nav_home.setIconTint(getColorStateList(R.color.inactive_nav_btn));
        botNav.btn_nav_profile.setIconTint(getColorStateList(R.color.inactive_nav_btn));
        botNav.btn_nav_mail.setIconTint(getColorStateList(R.color.inactive_nav_btn));
        botNav.btn_nav_parent_profile.setIconTint(getColorStateList(R.color.inactive_nav_btn));
    }


    //This method returns a ClassStudentInfo
    public ClassStudentInfo getStudentInfo(){
        return studentInfo ;
    }

    //This method returns an ArrayList<ClassStudentInfo>
    public ArrayList<ClassStudentInfo> getChildren() {
        return children;
    }


    //This method is used to set the child info
    public void setNewChildInfo(String parent, String name, String email, Long contact){
        if(parent.equals("mother")){
            children.get(0).setMotherName(name);
            children.get(0).setMotherEmail(email);
            children.get(0).setMotherContact(contact);
        }else{
            children.get(0).setFatherName(name);
            children.get(0).setFatherEmail(email);
            children.get(0).setFatherContact(contact);
        }
    }

    //This method returns the userType
    public String getUserType() {
        return userType;
    }

    //This method is used to return the parent information
    public ClassParentInfo getParentInfo(){
        return parentInfo ;
    }
}