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

import com.google.android.material.button.MaterialButton;
import com.google.android.material.radiobutton.MaterialRadioButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    FirebaseDatabase db = FirebaseDatabase.getInstance();   // getting real time database
    FirebaseAuth auth = FirebaseAuth.getInstance();
    public DatabaseReference studentReference= db.getReference("studentUsers");
    public DatabaseReference parentReference= db.getReference("parentUsers");
    public DatabaseReference clinicReference= db.getReference("clinicUsers");

    private Intent intent ;
    private String key;

    private EditText edit_user, edit_password ;
    private TextView tv_username, tv_forgotPassword ;
    private RadioGroup radio_users ;
    private MaterialRadioButton radio_student, radio_parent, radio_clinician ;
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

    public void buildViews() {
        this.edit_user = findViewById(R.id.edit_username) ;
        this.edit_password = findViewById(R.id.edit_password) ;
        this.tv_username = findViewById(R.id.tv_username) ;
        this.tv_forgotPassword = findViewById(R.id.tv_forgotPassword);

        this.radio_users = findViewById(R.id.radio_users) ;
        this.radio_student = findViewById(R.id.radio_student) ;
        this.radio_parent = findViewById(R.id.radio_parent) ;
        this.radio_clinician = findViewById(R.id.radio_clinician) ;

        this.btn_login = findViewById(R.id.btn_login) ;

        btn_login.setOnClickListener(this);
        tv_forgotPassword.setOnClickListener(this);

        radio_parent.setChecked(true);

    }

    @RequiresApi(api = Build.VERSION_CODES.Q)
    public void checkRadioChecked(View v){
        int radioId = radio_users.getCheckedRadioButtonId();

        RadioButton radioButton = findViewById(radioId);

        if(radio_parent.isChecked() || radio_clinician.isChecked()){
            tv_username.setText("Email");
            edit_user.setText("");
            edit_user.setHint("email");
            edit_user.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_WEB_EMAIL_ADDRESS);
        } else{
            tv_username.setText("Id Number");
            edit_user.setText("");
            edit_user.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_CLASS_NUMBER);
            edit_user.setHint("id number");
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

                                    DatabaseReference studentInfoReference = db.getReference("studentInfo") ;

                                    studentInfoReference.child(idNum).addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                                            Class_StudentInfo studentInfoUser = new Class_StudentInfo() ;
                                            studentInfoUser= snapshot.getValue(Class_StudentInfo.class) ;

                                            Toast.makeText(MainActivity.this, "User found and login successfully", Toast.LENGTH_SHORT).show();
                                            intent = new Intent(getBaseContext(), Activity_Landing.class);
                                            intent.putExtra("userType", "Student") ;
                                            intent.putExtra("studentInfo", studentInfoUser ) ;
//                                                intent.putExtra("studentId",idNum);
                                            startActivity(intent);
                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError error) {
                                        }
                                    }) ;

                                }
                                else{
                                    Toast.makeText(MainActivity.this, "Wrong password.", Toast.LENGTH_SHORT).show();
                                }
                            }
                            else{
                                Toast.makeText(MainActivity.this, "No student with that account.", Toast.LENGTH_SHORT).show();
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
                query.addValueEventListener(new ValueEventListener() {
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

                                    DatabaseReference  parentInfoReference = db.getReference("parentInfo") ;

                                    parentInfoReference.child(key).addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                                            Class_ParentInfo parentUser = new Class_ParentInfo() ;
                                            parentUser = snapshot.getValue(Class_ParentInfo.class) ;

                                            for (DataSnapshot postSnapshot: snapshot.child("children").getChildren()){
                                                parentUser.addChild(postSnapshot.getValue().toString());
                                            }

                                            intent = new Intent(getBaseContext(), Activity_Landing.class);
                                            intent.putExtra("userType", "Parent") ;
//                                            intent.putExtra("parentKey", key) ;
                                            intent.putExtra("parentInfo", parentUser);
                                            startActivity(intent);
                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError error) {
                                        }
                                    }) ;
                                }
                                else{
                                    Toast.makeText(MainActivity.this, "Wrong password.", Toast.LENGTH_SHORT).show();
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

                intent = new Intent(getBaseContext(), Activity_Landing.class);
                intent.putExtra("username", edit_user.getText().toString()) ;
                startActivity(intent);
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out );
                Query query = clinicReference.child(idNum);
//                query.addListenerForSingleValueEvent(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot snapshot) {
//                        if(idNum.isEmpty() && !pass.isEmpty()){
//                            Toast.makeText(MainActivity.this, "Please enter email.", Toast.LENGTH_SHORT).show();
//                        }
//                        else if(!idNum.isEmpty() && pass.isEmpty()){
//                            Toast.makeText(MainActivity.this, "Please enter password.", Toast.LENGTH_SHORT).show();
//                        } else if(idNum.isEmpty() && pass.isEmpty()){
//                            Toast.makeText(MainActivity.this, "Please enter email and password.", Toast.LENGTH_SHORT).show();
//                        }
//                        else {
//                            auth.signInWithEmailAndPassword(idNum, pass)
//                                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
//                                        @Override
//                                        public void onComplete(@NonNull Task<AuthResult> task) {
//                                            if(task.isSuccessful()) {
//                                                Toast.makeText(MainActivity.this, "Login successful!", Toast.LENGTH_SHORT).show();
////                                intent = new Intent(getBaseContext(), Landing.class);
////                                intent.putExtra("id",idNum.toString());
////                                startActivity(intent);
//                                            } else {
//                                                String errorCode = ((FirebaseAuthException) task.getException()).getErrorCode();
//
//                                                if(errorCode.equalsIgnoreCase("ERROR_WRONG_PASSWORD")){
//                                                    Toast.makeText(MainActivity.this, "Wrong password!", Toast.LENGTH_SHORT).show();
//                                                } else if(errorCode.equalsIgnoreCase("ERROR_INVALID_EMAIL")){
//                                                    Toast.makeText(MainActivity.this, "Please enter a valid email!", Toast.LENGTH_SHORT).show();
//                                                } else if(errorCode.equalsIgnoreCase("ERROR_USER_NOT_FOUND")) {
//                                                    Toast.makeText(MainActivity.this, "No user with such email!", Toast.LENGTH_SHORT).show();
//                                                } else if(errorCode.equalsIgnoreCase("ERROR_USER_DISABLED")) {
//                                                    Toast.makeText(MainActivity.this, "Account disabled by Admin!.", Toast.LENGTH_SHORT).show();
//                                                } else{
//                                                    Toast.makeText(MainActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
//                                                }
//                                            }
//                                        }
//                                    });
//                        }
//                    }
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError error) {
//                        throw error.toException();
//                    }
//                });
            }
        }
    }




}