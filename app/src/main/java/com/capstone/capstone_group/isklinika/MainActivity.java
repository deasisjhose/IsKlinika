package com.capstone.capstone_group.isklinika;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.radiobutton.MaterialRadioButton;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    FirebaseDatabase db = FirebaseDatabase.getInstance();   // getting real time database
    FirebaseAuth auth = FirebaseAuth.getInstance();
    public  DatabaseReference studentInfoReference = db.getReference("studentInfo") ;
    public DatabaseReference studentReference= db.getReference("studentUsers");
    public DatabaseReference parentReference= db.getReference("parentUsers");
    public DatabaseReference clinicReference= db.getReference("clinicUsers");
    public DatabaseReference teacherReference= db.getReference("teacherUsers");

    private Intent intent ;
    private String key;

    private EditText edit_user, edit_password ;
    private TextView tv_username, tv_forgotPassword ;
    private RadioGroup radio_users ;
    private MaterialRadioButton radio_student, radio_parent, radio_clinician, radio_teacher;
    private MaterialButton btn_login ;

    private static final String TAG="MAIN//" ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        Window window = this.getWindow() ;
//        window.setStatusBarColor(this.getResources().getColor(R.color.app_background));
        buildViews();
    }

    //This method is used to initialize the views
    public void buildViews() {
        this.edit_user = findViewById(R.id.edit_username) ;
        this.edit_password = findViewById(R.id.edit_password) ;
        this.tv_username = findViewById(R.id.tv_username) ;
        this.tv_forgotPassword = findViewById(R.id.tv_forgotPassword);

        this.radio_users = findViewById(R.id.radio_users) ;
        this.radio_student = findViewById(R.id.radio_student) ;
        this.radio_parent = findViewById(R.id.radio_parent) ;
        this.radio_clinician = findViewById(R.id.radio_clinician) ;
        this.radio_teacher = findViewById(R.id.radio_teacher) ;

        this.btn_login = findViewById(R.id.btn_login) ;

        btn_login.setOnClickListener(this);
        tv_forgotPassword.setOnClickListener(this);

        radio_parent.setChecked(true);

    }

    //This method is checks the selected radio button
    public void checkRadioChecked(View v){
        int radioId = radio_users.getCheckedRadioButtonId();

        RadioButton radioButton = findViewById(radioId);

        if(radio_parent.isChecked() || radio_clinician.isChecked() || radio_teacher.isChecked()){
            tv_username.setText("Email");
            edit_user.setText("");
            edit_user.setHint("email");
            edit_user.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_WEB_EMAIL_ADDRESS);
            edit_password.setText("") ;
        } else{
            tv_username.setText("Id Number");
            edit_user.setText("");
            edit_user.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_CLASS_NUMBER);
            edit_user.setHint("id number");
            edit_password.setText("") ;
        }

        Toast.makeText(this, radioButton.getText() + " Selected" ,
                Toast.LENGTH_SHORT).show();
    }


    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.btn_login){
            String idNum= edit_user.getText().toString();
            String pass= edit_password.getText().toString();

            if(radio_student.isChecked()){  // If user is a student
                Query query = studentReference.child(idNum);
                query.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(idNum.isEmpty() && !pass.isEmpty()){
                            Toast.makeText(MainActivity.this, "Please enter id number.", Toast.LENGTH_SHORT).show();
                        }
                        else if(!idNum.isEmpty() && pass.isEmpty()){
                            Toast.makeText(MainActivity.this, "Please enter password.", Toast.LENGTH_SHORT).show();
                        } else if(idNum.isEmpty() && pass.isEmpty()){
                            Toast.makeText(MainActivity.this, "Please enter id number and password.", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            if(snapshot.exists()){  // if the id number exists in the database
                                if(snapshot.child("password").getValue().equals(pass)){ // matches password in the database
                                    String password = snapshot.child("password").getValue().toString() ;
                                    studentInfoReference.child(idNum).addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                                            ClassStudentInfo studentInfoUser = new ClassStudentInfo() ;
                                            studentInfoUser= snapshot.getValue(ClassStudentInfo.class) ;
                                            studentInfoUser.setIdNum(snapshot.getKey());
                                            studentInfoUser.setPassword(password);
                                            Toast.makeText(MainActivity.this, "User found and login successfully", Toast.LENGTH_SHORT).show();
                                            intent = new Intent(getBaseContext(), ActivityFirstTime.class);
                                            intent.putExtra("userType", "Student") ;
                                            intent.putExtra("studentInfo", studentInfoUser ) ;
                                            startActivity(intent);
                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError error) {
                                        }
                                    }) ;

                                }
                                else{
                                    Toast.makeText(MainActivity.this, "Wrong password.", Toast.LENGTH_SHORT).show();
                                    edit_password.setText("");
                                }
                            }
                            else{
                                Toast.makeText(MainActivity.this, "No student with that account.", Toast.LENGTH_SHORT).show();
                                edit_password.setText("");
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Log.d(TAG, "Failed") ;
                        throw error.toException();
                    }
                });
            } else if(radio_parent.isChecked()) {    // User is parent
                Query query = parentReference.orderByChild("email").equalTo(idNum);
                query.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(idNum.isEmpty() && !pass.isEmpty()){
                            Toast.makeText(MainActivity.this, "Please enter email.", Toast.LENGTH_SHORT).show();
                        }
                        else if(!idNum.isEmpty() && pass.isEmpty()){
                            Toast.makeText(MainActivity.this, "Please enter password.", Toast.LENGTH_SHORT).show();
                        }
                        else if(idNum.isEmpty() && pass.isEmpty()){
                            Toast.makeText(MainActivity.this, "Please enter email and password.", Toast.LENGTH_SHORT).show();
                        }
                        else if(!snapshot.exists()){
                            Toast.makeText(MainActivity.this, "No parent with that account.", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            for(DataSnapshot childSnapshot : snapshot.getChildren()) {
                                key = childSnapshot.getKey();

                                if(childSnapshot.child("password").getValue().equals(pass)){
                                    Toast.makeText(MainActivity.this, "User found and login successfully", Toast.LENGTH_SHORT).show();
                                    String email = childSnapshot.child("email").getValue().toString() ;
                                    String password = childSnapshot.child("password").getValue().toString();

                                    DatabaseReference  parentInfoReference = db.getReference("parentInfo") ;

                                    parentInfoReference.child(key).addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                                            ClassParentInfo parentUser = new ClassParentInfo() ;
                                            parentUser = snapshot.getValue(ClassParentInfo.class) ;

                                            for (DataSnapshot postSnapshot: snapshot.child("children").getChildren()){
                                                parentUser.addChild(postSnapshot.getValue().toString());
                                            }
                                            parentUser.setKey(snapshot.getKey());
                                            parentUser.setPassword(password);
                                            retrieveDataParentUser(parentUser) ;

                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError error) {
                                        }
                                    }) ;
                                    break;
                                }
                                else{
                                    Toast.makeText(MainActivity.this, "Wrong password.", Toast.LENGTH_SHORT).show();
                                    Log.d(TAG, "onDataChange: MAIN WRONG PASSWORD");
                                    edit_password.setText("");
                                }
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        throw error.toException();
                    }
                });
            } else if(radio_clinician.isChecked()){ // if user is clinician

                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out );

                Query query = clinicReference;
                query.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(idNum.isEmpty() && !pass.isEmpty()){
                            Toast.makeText(MainActivity.this, "Please enter email.", Toast.LENGTH_SHORT).show();
                        }
                        else if(!idNum.isEmpty() && pass.isEmpty()){
                            Toast.makeText(MainActivity.this, "Please enter password.", Toast.LENGTH_SHORT).show();
                        } else if(idNum.isEmpty() && pass.isEmpty()){
                            Toast.makeText(MainActivity.this, "Please enter email and password.", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            auth.signInWithEmailAndPassword(idNum, pass)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if(task.isSuccessful()) {
                                        Toast.makeText(MainActivity.this, "Login successful!", Toast.LENGTH_SHORT).show();
                                        intent = new Intent(getBaseContext(), ActivityClinicianLanding.class);
                                        intent.putExtra("userType", "Clinician") ;
                                        startActivity(intent);
                                    } else {
                                        String errorCode = ((FirebaseAuthException) task.getException()).getErrorCode();

                                        if(errorCode.equalsIgnoreCase("ERROR_WRONG_PASSWORD")){
                                            Toast.makeText(MainActivity.this, "Wrong password!", Toast.LENGTH_SHORT).show();
                                            edit_password.setText("");
                                        } else if(errorCode.equalsIgnoreCase("ERROR_INVALID_EMAIL")){
                                            Toast.makeText(MainActivity.this, "Please enter a valid email!", Toast.LENGTH_SHORT).show();
                                        } else if(errorCode.equalsIgnoreCase("ERROR_USER_NOT_FOUND")) {
                                            Toast.makeText(MainActivity.this, "No user with such email!", Toast.LENGTH_SHORT).show();
                                        } else if(errorCode.equalsIgnoreCase("ERROR_USER_DISABLED")) {
                                            Toast.makeText(MainActivity.this, "Account disabled by Admin!.", Toast.LENGTH_SHORT).show();
                                        } else{
                                            Toast.makeText(MainActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                }
                            });
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        throw error.toException();
                    }
                });
            } else if(radio_teacher.isChecked()){ // if user is clinician

                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out );

                Query query = teacherReference.orderByChild("email").equalTo(idNum);
                query.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(idNum.isEmpty() && !pass.isEmpty()){
                            Toast.makeText(MainActivity.this, "Please enter email.", Toast.LENGTH_SHORT).show();
                        }
                        else if(!idNum.isEmpty() && pass.isEmpty()){
                            Toast.makeText(MainActivity.this, "Please enter password.", Toast.LENGTH_SHORT).show();
                        } else if(idNum.isEmpty() && pass.isEmpty()){
                            Toast.makeText(MainActivity.this, "Please enter email and password.", Toast.LENGTH_SHORT).show();
                        }
                        else if(snapshot.exists()){  // if the id number exists in the database
//                                if(snapshot.child("password").getValue().equals(pass)){ // matches password in the database
                            ClassTeacher teacher = null ;
                            for(DataSnapshot childSnapshot : snapshot.getChildren()){

                                if(childSnapshot.child("email").getValue().toString().equals(idNum)){
                                        teacher= childSnapshot.getValue(ClassTeacher.class) ;
                                        teacher.setKey(childSnapshot.getKey());
                                        Toast.makeText(MainActivity.this, "User found and login successfully", Toast.LENGTH_SHORT).show();
                                        intent = new Intent(getBaseContext(), ActivityLandingTeacher.class);
                                        intent.putExtra("teacherUser", teacher ) ;
                                        startActivity(intent);
                                        break;
                                }
                            }
//                                }
//                                else{
//                                    Toast.makeText(MainActivity.this, "Wrong password.", Toast.LENGTH_SHORT).show();
                            } else{
                                Toast.makeText(MainActivity.this, "No teacher with that account.", Toast.LENGTH_SHORT).show();
                            }
                        }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        throw error.toException();
                    }
                });


            }
        }
        else if(view.getId() == R.id.tv_forgotPassword){
            intent = new Intent(getBaseContext(), ActivityForgotPassword.class);
            startActivity(intent);
        }

    }

    //This method retrieves the parent's child/children and retrieve their information in the studentInfo table.
    public void retrieveDataParentUser(ClassParentInfo parentUser) {
        ArrayList<ClassStudentInfo> childrenInfo = new ArrayList<>();

        ValueEventListener studentValueListener ;

        studentInfoReference.addListenerForSingleValueEvent(new ValueEventListener(){
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(childrenInfo.size() != 0){
                    childrenInfo.clear();
                }
                int i ;
                for (DataSnapshot postSnapshot: snapshot.getChildren()){

                    for(i = 0 ; i < parentUser.getChildrenSize() ; i++){
                        if(postSnapshot.getKey().equals(parentUser.getIdNumber(i))){
                            ClassStudentInfo child = postSnapshot.getValue(ClassStudentInfo.class) ;
                            child.setIdNum(postSnapshot.getKey());
                            childrenInfo.add(child) ;
                        }
                    }
                }

                intent = new Intent(getBaseContext(), ActivityFirstTime.class);
                intent.putExtra("userType", "Parent") ;
                intent.putParcelableArrayListExtra("children", childrenInfo) ;
                intent.putExtra("parentInfo", parentUser);
                Log.d(TAG, "onDataChange: "+ parentUser.toString());
                startActivity(intent);


            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }
}