package com.capstone.capstone_group.isklinika;

import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;

/*
This function contains the user profile. The parent user can edit their information here as well
 */
public class FragmentUserProfile extends Fragment {

    FirebaseDatabase db = FirebaseDatabase.getInstance();   // getting real time database
    public DatabaseReference database = db.getReference();
    public DatabaseReference databaseReference= db.getReference("studentInfo");
    public DatabaseReference databaseParentInfo= db.getReference("parentUsers");

    private MaterialCardView mcard_editProfile, mcard_saveProfile, mbtn_updatePassword  ;
    private ActivityLanding activity_landing ;
    private LinearLayout layout_parentProfile ;
    private TextInputEditText tv_parentName, tv_parentEmail, tv_parentContact, mtv_currentPassword, mtv_newPassword, mtv_confirmPassword;
    private TextView tv_characters, tv_uppercase, tv_specialChar, tv_numeric ;

    private ClassParentInfo parentInfo ;
    private ArrayList<ClassStudentInfo> children ;
    private ClassStudentInfo studentInfo ;

    String password ;
    int passTest ;
    int checkParent = 0  ; // 0 == mother 1 == father
    String parentNameBeforeEdit = null;
    public FragmentUserProfile() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_parent_profile, container, false);

        this.mcard_editProfile = view.findViewById(R.id.mcard_editProfile) ;
        this.mcard_saveProfile = view.findViewById(R.id.mcard_saveProfile) ;
        this.activity_landing = (ActivityLanding) getActivity() ;

        this.tv_parentName = view.findViewById(R.id.tv_parentName) ;
        this.tv_parentEmail = view.findViewById(R.id.tv_parentEmail) ;
        this.tv_parentContact = view.findViewById(R.id.tv_parentContact) ;

        this.mbtn_updatePassword = view.findViewById(R.id.mtn_updatePassword) ;
        this.mtv_currentPassword = view.findViewById(R.id.mtv_currentPassword) ;
        this.mtv_newPassword = view.findViewById(R.id.mtv_newPassword) ;
        this.mtv_confirmPassword = view.findViewById(R.id.mtv_confirmPassword) ;

        this.layout_parentProfile = view.findViewById(R.id.layout_parentProfile) ;

        this.tv_characters = view.findViewById(R.id.tv_characters) ;
        this.tv_uppercase = view.findViewById(R.id.tv_uppercase) ;
        this.tv_specialChar = view.findViewById(R.id.tv_specialChar) ;
        this.tv_numeric = view.findViewById(R.id.tv_numeric) ;

        return view ;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        switch (activity_landing.getUserType()){
            case "Student":
                layout_parentProfile.setVisibility(View.GONE);
                mcard_editProfile.setVisibility(View.GONE);
                studentInfo = activity_landing.getStudentInfo() ;
                password = studentInfo.getPassword() ;
                break;
            case "Parent":
                parentInfo = activity_landing.getParentInfo() ;
                children = activity_landing.getChildren();
                password = parentInfo.getPassword() ;
                for(int i = 0 ; i < children.size() ; i++){
                    if(parentInfo.getEmail().equals(children.get(i).getMotherEmail())){
                        checkParent = 0 ; //mother
                    }else {
                        checkParent = 1 ; //father
                    }
                }

                switch (checkParent){
                    case 0:
                        tv_parentName.setText(children.get(0).getMotherName());
                        parentNameBeforeEdit = children.get(0).getMotherName() ;
                        tv_parentEmail.setText(children.get(0).getMotherEmail());
                        tv_parentContact.setText(children.get(0).getMotherContact());
                        break;
                    case 1:
                        tv_parentName.setText(children.get(0).getFatherName());
                        parentNameBeforeEdit = children.get(0).getFatherName() ;
                        tv_parentEmail.setText(children.get(0).getFatherEmail());
                        tv_parentContact.setText(children.get(0).getFatherContact());
                        break;
                }
                break;
        }

        mbtn_updatePassword.setClickable(true);
        mtv_currentPassword.setEnabled(true);
        mtv_currentPassword.setClickable(true);
        mtv_newPassword.setEnabled(true);
        mtv_newPassword.setClickable(true);
        mtv_confirmPassword.setEnabled(true);
        mtv_confirmPassword.setClickable(true);

        mcard_editProfile.setOnClickListener(view1 -> {
            mcard_saveProfile.setVisibility(View.VISIBLE);
            mcard_editProfile.setVisibility(View.GONE);

            tv_parentName.setEnabled(true);
            tv_parentName.setClickable(true);
            tv_parentEmail.setEnabled(true);
            tv_parentEmail.setClickable(true);
            tv_parentContact.setEnabled(true);
            tv_parentContact.setClickable(true);
        });

//        int finalCheckParent = checkParent;
//        String finalParentNameBeforeEdit = parentNameBeforeEdit;
        mcard_saveProfile.setOnClickListener(view1 -> {
            mcard_saveProfile.setVisibility(View.GONE);
            mcard_editProfile.setVisibility(View.VISIBLE);

            tv_parentName.setEnabled(false);
            tv_parentName.setClickable(false);
            tv_parentEmail.setEnabled(false);
            tv_parentEmail.setClickable(false);
            tv_parentContact.setEnabled(false);
            tv_parentContact.setClickable(false);

                switch (checkParent){
                    case 0:
                        activity_landing.setNewChildInfo("mother", tv_parentName.getText().toString(), tv_parentEmail.getText().toString(), Long.parseLong(tv_parentContact.getText().toString()));
                        break;
                    case 1:
                        activity_landing.setNewChildInfo("father", tv_parentName.getText().toString(), tv_parentEmail.getText().toString(), Long.parseLong(tv_parentContact.getText().toString()));
                        break;
                }

                //updating parent information
                HashMap<String, Object> parentValues = new HashMap<String, Object>() ;
                int i ;
                for (i = 0 ; i < parentInfo.getChildrenSize() ; i++){

                    if(checkParent == 1){
                        parentValues.put("/studentInfo/" + children.get(i).getIdNum() + "/fatherContact", Long.parseLong(tv_parentContact.getText().toString())) ;
                        parentValues.put("/studentInfo/" + children.get(i).getIdNum() + "/fatherEmail", tv_parentEmail.getText().toString()) ;
                        parentValues.put("/studentInfo/" + children.get(i).getIdNum() + "/fatherName", tv_parentName.getText().toString()) ;
                    }else{
                        parentValues.put("/studentInfo/" + children.get(i).getIdNum() + "/motherContact", Long.parseLong(tv_parentContact.getText().toString())) ;
                        parentValues.put("/studentInfo/" + children.get(i).getIdNum() + "/motherEmail", tv_parentEmail.getText().toString()) ;
                        parentValues.put("/studentInfo/" + children.get(i).getIdNum() + "/motherName", tv_parentName.getText().toString()) ;
                    }

                    parentValues.put("/parentUsers/" + parentInfo.getKey() + "/email",tv_parentEmail.getText().toString()) ;
                    parentValues.put("/parentInfo/" + parentInfo.getKey() + "/email",tv_parentEmail.getText().toString()) ;

                    parentNameBeforeEdit = tv_parentName.getText().toString() ;

                    activity_landing.getParentInfo().setEmail(tv_parentEmail.getText().toString());

                    database.updateChildren(parentValues).addOnSuccessListener((OnSuccessListener) (aVoid) -> {
                        Log.d("LANDING//", "onViewCreated: checkparent = " + checkParent);
                        Toast.makeText(view.getContext(), "Data successfully updated!", Toast.LENGTH_SHORT).show();
                    }).addOnFailureListener((error) -> {
                        Toast.makeText(view.getContext(), "Data was not updated!", Toast.LENGTH_SHORT).show();
                    });
                }



        });

        mbtn_updatePassword.setOnClickListener(view1 -> {

            if(mtv_currentPassword.getText().toString().equals(password)){
                mtv_currentPassword.getBackground().setTint(Color.parseColor("#ADC2FF"));
                if(mtv_newPassword.getText().toString().equals(mtv_confirmPassword.getText().toString())
                        && !mtv_newPassword.getText().toString().equals("") && !mtv_confirmPassword.getText().toString().equals("")) {

                    mtv_newPassword.getBackground().setTint(Color.parseColor("#ADC2FF"));
                    mtv_confirmPassword.getBackground().setTint(Color.parseColor("#ADC2FF"));

                    passTest = 0 ;

                    if(mtv_newPassword.getText().toString().length() >= 8){
                        passTest += 1 ;
                        tv_characters.setTextColor(Color.parseColor("#252525")) ;
                    }else {
                        passTest = 0 ;
                        tv_characters.setTextColor(Color.parseColor("#e74a3b"));
                    }

                    int specialCount = 0 ;
                    int upperCount = 0 ;
                    int numeric = 0 ;

                    for(int i = 0 ; i < mtv_newPassword.getText().toString().length() ; i++){
                        char c = mtv_newPassword.getText().toString().charAt(i) ;
                        if(Character.isLetter(c) && Character.isUpperCase(c)){
                            upperCount += 1 ;
                        }
                        if(!Character.isLetter(c) && !Character.isDigit(c)){
                            specialCount += 1 ;
                        }
                        if(Character.isDigit(c)){
                            numeric += 1 ;
                        }
                    }

                    if(specialCount >= 1){
                        passTest += 1 ;
                        tv_specialChar.setTextColor(Color.parseColor("#252525")) ;
                    }else {
                        passTest = 0 ;
                        tv_specialChar.setTextColor(Color.parseColor("#e74a3b"));
                    }


                    if(upperCount >= 1){
                        passTest += 1 ;
                        tv_uppercase.setTextColor(Color.parseColor("#252525")) ;
                    }else {
                        passTest = 0 ;
                        tv_uppercase.setTextColor(Color.parseColor("#e74a3b"));
                    }

                    if(numeric >= 1){
                        passTest += 1 ;
                        tv_numeric.setTextColor(Color.parseColor("#252525")) ;
                    }else {
                        passTest = 0 ;
                        tv_numeric.setTextColor(Color.parseColor("#e74a3b"));
                    }

                    if(passTest == 4){
                        new MaterialAlertDialogBuilder(view.getRootView().getContext(), R.style.ThemeOverlay_App_MaterialAlertDialog_Immune)
                                .setTitle(R.string.title_password)
                                .setNegativeButton("Cancel", new DialogInterface.OnClickListener(){

                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {

                                    }
                                })
                                .setPositiveButton("Update", new DialogInterface.OnClickListener(){

                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {

                                        HashMap<String, Object> passwordHash = new HashMap<String, Object>() ;

                                        switch (activity_landing.getUserType()){
                                            case "Student":
                                                passwordHash.put("/studentUsers/" + studentInfo.getIdNum() + "/password", mtv_confirmPassword.getText().toString()) ;
                                                break;
                                            case "Parent":
                                                passwordHash.put("/parentUsers/" + parentInfo.getKey() + "/password", mtv_confirmPassword.getText().toString()) ;
                                                break;
                                        }

                                        database.updateChildren(passwordHash).addOnSuccessListener((OnSuccessListener) (aVoid) -> {
                                            Toast.makeText(view.getContext(), "Password successfully updated!", Toast.LENGTH_SHORT).show();
                                            password = mtv_confirmPassword.getText().toString() ;
                                            activity_landing.getParentInfo().setPassword(mtv_confirmPassword.getText().toString());
                                            mtv_confirmPassword.setText("");
                                            mtv_currentPassword.setText("");
                                            mtv_newPassword.setText("");

                                        }).addOnFailureListener((error) -> {
                                            Toast.makeText(view.getContext(), "Password was not updated!", Toast.LENGTH_SHORT).show();
                                        });
                                    }
                                })
                                .show() ;
                    }
                } else {
                    if(!mtv_newPassword.getText().toString().equals(mtv_confirmPassword.getText().toString())){
                        mtv_newPassword.getBackground().setTint(Color.parseColor("#FFFD6868"));
                        mtv_confirmPassword.getBackground().setTint(Color.parseColor("#FFFD6868"));
                        Toast.makeText(view.getContext(), "Passwords do not match", Toast.LENGTH_SHORT).show();
                    }
                }


            }else{
                if(!mtv_currentPassword.getText().toString().equals("")){
                    mtv_currentPassword.getBackground().setTint(Color.parseColor("#FFFD6868"));
                    Toast.makeText(view.getContext(), "Password does not much with current password", Toast.LENGTH_SHORT).show();
                    Log.d("USER//", "onViewCreated: Current Password = " + parentInfo.getPassword());
                }
            }
        });
    }
}