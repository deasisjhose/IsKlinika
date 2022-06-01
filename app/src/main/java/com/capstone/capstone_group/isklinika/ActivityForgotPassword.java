package com.capstone.capstone_group.isklinika;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.radiobutton.MaterialRadioButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textview.MaterialTextView;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class ActivityForgotPassword extends AppCompatActivity {

    FirebaseDatabase db = FirebaseDatabase.getInstance();   // getting real time database
    FirebaseAuth auth = FirebaseAuth.getInstance();
    public DatabaseReference database = db.getReference();
    public DatabaseReference studentInfoReference = db.getReference("studentInfo") ;
    public DatabaseReference studentReference= db.getReference("studentUsers");
    public DatabaseReference parentReference= db.getReference("parentUsers");
    public DatabaseReference clinicReference= db.getReference("clinicUsers");
    public DatabaseReference teacherReference= db.getReference("teacherUsers");

    private EditText edit_username, edit_code ;
    private TextView tv_message ;
    private MaterialTextView tv_username, tv_code ;
    private RadioGroup radio_users ;
    private MaterialRadioButton radio_student, radio_parent, radio_clinician, radio_teacher;
    private MaterialButton mbtn_verifyEmail, mbtn_confirmCode, mbtn_backtologin;
    private MaterialCardView mtn_updatePassword ;
    private LinearLayout linear_emailCode, linear_updatePass ;

    private TextView tv_characters, tv_uppercase, tv_specialChar, tv_numeric ;
    private TextInputEditText edit_newPass, edit_confirmPass ;

    private String key ;
    private String code ;
    int passTest ;
    private String userType ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        buildViews();
    }


    public void buildViews(){

        this.edit_username = findViewById(R.id.edit_username) ;
        this.edit_code = findViewById(R.id.edit_code) ;
        this.tv_username = findViewById(R.id.tv_username) ;
        this.tv_code = findViewById(R.id.tv_code) ;

        this.tv_message = findViewById(R.id.tv_message) ;

        this.radio_users = findViewById(R.id.radio_users) ;
        this.radio_student = findViewById(R.id.radio_student) ;
        this.radio_parent = findViewById(R.id.radio_parent) ;
        this.radio_clinician = findViewById(R.id.radio_clinician) ;
        this.radio_teacher = findViewById(R.id.radio_teacher) ;

        this.mbtn_verifyEmail = findViewById(R.id.mbtn_verifyEmail) ;
        this.mbtn_confirmCode = findViewById(R.id.mbtn_confirmCode) ;
        this.mbtn_backtologin = findViewById(R.id.mbtn_backtologin) ;
        this.mtn_updatePassword = findViewById(R.id.mtn_updatePassword) ;

        this.linear_emailCode = findViewById(R.id.linear_emailCode) ;
        this.linear_updatePass = findViewById(R.id.linear_updatePass) ;

        this.edit_newPass = findViewById(R.id.edit_newPass) ;
        this.edit_confirmPass = findViewById(R.id.edit_confirmPass) ;

        this.tv_characters = findViewById(R.id.tv_characters) ;
        this.tv_uppercase = findViewById(R.id.tv_uppercase) ;
        this.tv_specialChar = findViewById(R.id.tv_specialChar) ;
        this.tv_numeric = findViewById(R.id.tv_numeric) ;

        onClicks();
    }

    public void onClicks(){

        mbtn_verifyEmail.setOnClickListener(view -> {
            String emailID= edit_username.getText().toString();

            if(radio_student.isChecked()){  // If user is a student
                Query query = studentReference.child(emailID);
                query.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(emailID.isEmpty()){
                            Toast.makeText(ActivityForgotPassword.this, "Please enter id number.", Toast.LENGTH_SHORT).show();
                        } else {
                            if(snapshot.exists()){  // if the id number exists in the database
                                tv_code.setVisibility(View.VISIBLE);
                                edit_code.setVisibility(View.VISIBLE);
                                mbtn_confirmCode.setVisibility(View.VISIBLE);

                                tv_message.setVisibility(View.VISIBLE);
                                tv_message.setText(R.string.emailSent);


                            }
                            else{
                                Toast.makeText(ActivityForgotPassword.this, "No student with that account.", Toast.LENGTH_SHORT).show();

                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        throw error.toException();
                    }
                });
            } else if(radio_parent.isChecked()) {    // User is parent
                Query query = parentReference.orderByChild("email").equalTo(emailID);
                query.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(emailID.isEmpty()){
                            Toast.makeText(ActivityForgotPassword.this, "Please enter email.", Toast.LENGTH_SHORT).show();
                        } else if(!snapshot.exists()){
                            Toast.makeText(ActivityForgotPassword.this, "No parent with that account.", Toast.LENGTH_SHORT).show();
                        } else {
                            for(DataSnapshot childSnapshot : snapshot.getChildren()) {
                                key = childSnapshot.getKey();

                                tv_code.setVisibility(View.VISIBLE);
                                edit_code.setVisibility(View.VISIBLE);
                                mbtn_confirmCode.setVisibility(View.VISIBLE);

                                tv_message.setVisibility(View.VISIBLE);
                                tv_message.setText(R.string.emailSent);
                                tv_message.setTextColor(Color.parseColor("#4e73df"));

                                String email = childSnapshot.child("email").getValue().toString() ;
                                sendEmail(email);
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        throw error.toException();
                    }
                });
            } else if(radio_clinician.isChecked()){
                Query query = clinicReference;
                query.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(emailID.isEmpty()){
                            Toast.makeText(ActivityForgotPassword.this, "Please enter email.", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            Query query = clinicReference.orderByChild("email").equalTo(emailID);
                            query.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    if(emailID.isEmpty()){
                                        Toast.makeText(ActivityForgotPassword.this, "Please enter email.", Toast.LENGTH_SHORT).show();
                                    } else if(!snapshot.exists()){
                                        Toast.makeText(ActivityForgotPassword.this, "No teacher with that account.", Toast.LENGTH_SHORT).show();
                                    } else {
                                        for(DataSnapshot childSnapshot : snapshot.getChildren()) {
                                            key = childSnapshot.getKey();

                                            tv_code.setVisibility(View.VISIBLE);
                                            edit_code.setVisibility(View.VISIBLE);
                                            mbtn_confirmCode.setVisibility(View.VISIBLE);

                                            tv_message.setVisibility(View.VISIBLE);
                                            tv_message.setText(R.string.emailSent);
                                            tv_message.setTextColor(Color.parseColor("#4e73df"));

                                            String email = childSnapshot.child("email").getValue().toString() ;
                                            sendEmail("kathrina.ramos.25@gmail.com");
                                        }
                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {
                                    throw error.toException();
                                }
                            });
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        throw error.toException();
                    }
                });
            }
            else if(radio_teacher.isChecked()){
                Query query = teacherReference.orderByChild("email").equalTo(emailID);
                query.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(emailID.isEmpty()){
                            Toast.makeText(ActivityForgotPassword.this, "Please enter email.", Toast.LENGTH_SHORT).show();
                        } else if(!snapshot.exists()){
                            Toast.makeText(ActivityForgotPassword.this, "No teacher with that account.", Toast.LENGTH_SHORT).show();
                        } else {
                            for(DataSnapshot childSnapshot : snapshot.getChildren()) {
                                key = childSnapshot.getKey();

                                tv_code.setVisibility(View.VISIBLE);
                                edit_code.setVisibility(View.VISIBLE);
                                mbtn_confirmCode.setVisibility(View.VISIBLE);

                                tv_message.setVisibility(View.VISIBLE);
                                tv_message.setText(R.string.emailSent);
                                tv_message.setTextColor(Color.parseColor("#4e73df"));

                                String email = childSnapshot.child("email").getValue().toString() ;
                                sendEmail("kathrina.ramos.25@gmail.com");
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        throw error.toException();
                    }
                });
            }
        });


        mbtn_confirmCode.setOnClickListener(view -> {
            Log.d("CODERANDOM", "onClicks: CODE = " + code);
            if(edit_code.getText().toString().equals(code)){
                linear_emailCode.setVisibility(View.GONE);
                linear_updatePass.setVisibility(View.VISIBLE);
                tv_message.setVisibility(View.GONE);
            } else {
                tv_message.setText("Wrong code entered");
                tv_message.setTextColor(Color.parseColor("#e74a3b")); //red message
            }
        });

        mtn_updatePassword.setOnClickListener(view -> {
            Log.d("UpdatePass", "onClicks: KEY = " + key);
            Log.d("UpdatePass", "onClicks: User = " + userType);
            if ((edit_newPass.getText().toString().equals(edit_confirmPass.getText().toString())) && (!edit_newPass.getText().toString().equals("") && !edit_confirmPass.getText().toString().equals("")))
            {

                passTest = 0;

                if (edit_newPass.getText().toString().length() >= 8) {
                    passTest += 1;
                    tv_characters.setTextColor(Color.parseColor("#252525"));
                } else {
                    passTest = 0;
                    tv_characters.setTextColor(Color.parseColor("#e74a3b"));
                }

                int specialCount = 0;
                int upperCount = 0;
                int numeric = 0;

                for (int i = 0; i < edit_newPass.getText().toString().length(); i++) {
                    char c = edit_newPass.getText().toString().charAt(i);
                    if (Character.isLetter(c) && Character.isUpperCase(c)) {
                        upperCount += 1;
                    }
                    if (!Character.isLetter(c) && !Character.isDigit(c)) {
                        specialCount += 1;
                    }
                    if (Character.isDigit(c)) {
                        numeric += 1;
                    }
                }

                if (specialCount >= 1) {
                    passTest += 1;
                    tv_specialChar.setTextColor(Color.parseColor("#252525"));
                } else {
                    passTest = 0;
                    tv_specialChar.setTextColor(Color.parseColor("#e74a3b"));
                }


                if (upperCount >= 1) {
                    passTest += 1;
                    tv_uppercase.setTextColor(Color.parseColor("#252525"));
                } else {
                    passTest = 0;
                    tv_uppercase.setTextColor(Color.parseColor("#e74a3b"));
                }

                if (numeric >= 1) {
                    passTest += 1;
                    tv_numeric.setTextColor(Color.parseColor("#252525"));
                } else {
                    passTest = 0;
                    tv_numeric.setTextColor(Color.parseColor("#e74a3b"));
                }

                if (passTest == 4) {
                    new MaterialAlertDialogBuilder(view.getRootView().getContext(), R.style.ThemeOverlay_App_MaterialAlertDialog_Immune)
                            .setTitle(R.string.title_password)
                            .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {

                                }
                            })
                            .setPositiveButton("Update", new DialogInterface.OnClickListener() {

                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {

                                    HashMap<String, Object> passwordHash = new HashMap<String, Object>();

                                    switch (userType) {
                                        case "Guardian":
                                            passwordHash.put("/parentUsers/" + key + "/password", edit_confirmPass.getText().toString());
                                            break;
                                        case "Teacher":
                                            passwordHash.put("/teacherUsers/" + key + "/password", edit_confirmPass.getText().toString());
                                            break;
                                    }

                                    database.updateChildren(passwordHash).addOnSuccessListener((OnSuccessListener) (aVoid) -> {
                                        Toast.makeText(view.getContext(), "Password successfully changed!", Toast.LENGTH_SHORT).show();
                                        edit_newPass.setText("");
                                        edit_confirmPass.setText("");
                                        tv_message.setVisibility(View.VISIBLE);
                                        tv_message.setText("Password successfully changed");
                                    }).addOnFailureListener((error) -> {
                                        Toast.makeText(view.getContext(), "Password was not updated!", Toast.LENGTH_SHORT).show();
                                    });
                                }
                            })
                            .show();
                } else {
                    if (!edit_newPass.getText().toString().equals(edit_confirmPass.getText().toString())) {
                        tv_message.setText("Passwords do not match");
                        tv_message.setTextColor(Color.parseColor("#e74a3b")); //red message
                        Toast.makeText(view.getContext(), "Passwords do not match", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        mbtn_backtologin.setOnClickListener(view -> {
           Intent intent = new Intent(getBaseContext(), MainActivity.class);
            startActivity(intent);
            finish();
            });

    }

    //This method is checks the selected radio button
    public void checkRadioChecked(View v){
        int radioId = radio_users.getCheckedRadioButtonId();

        RadioButton radioButton = findViewById(radioId);

        if(radio_parent.isChecked()){
                userType = radioButton.getText().toString() ;
        } else if(radio_clinician.isChecked()){
            userType = radioButton.getText().toString() ;
        } else if(radio_teacher.isChecked()){
            userType = radioButton.getText().toString() ;
        }

        Toast.makeText(this, radioButton.getText() + " Selected" ,
                Toast.LENGTH_SHORT).show();
    }

    public void sendEmail(String email){

        ClassRandomString randomString = new ClassRandomString();
        String TO = email ;
        String subject = "Reset Password CODE" ;

        this.code = randomString.createRandom(5) ;
        Log.d("EMAIL", "sendEmail: TO = " + TO);
        Log.d("EMAIL", "sendEmail: CODE = " + code);

        new Thread(new Runnable() {

            @Override
            public void run() {
                try {
                    GMailSender sender = new GMailSender("capstonegroup97@gmail.com", "CapstoneUnderOli12#");
                    sender.sendMail(subject,
                            "Your password reset code is " + code,
                            "capstonegroup97@gmail.com",
                            TO);
                    Log.d("EMAIL", "sendEmail: EMAILSENT");
                } catch (Exception e) {
                    Log.e("SendMail", e.getMessage(), e);
                }
            }
        }).start();


    }
}