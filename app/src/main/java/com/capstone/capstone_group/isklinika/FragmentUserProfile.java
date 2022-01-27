package com.capstone.capstone_group.isklinika;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.card.MaterialCardView;
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

    private MaterialCardView mcard_editProfile, mcard_saveProfile ;
    private ActivityLanding activity_landing ;
    private TextInputEditText tv_parentName, tv_parentEmail, tv_parentContact ;

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

        return view ;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ClassParentInfo parentInfo = activity_landing.getParentInfo() ;
        ArrayList<ClassStudentInfo> children = activity_landing.getChildren();


        for(int i = 0 ; i < children.size() ; i++){

            if(parentInfo.getEmail().equals(children.get(i).getMotherEmail())){
                //mother
                checkParent = 0 ;
            }else {
                //father
                checkParent = 1 ;
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
                if(parentNameBeforeEdit.equals(children.get(i).getGuardianName())){
                    parentValues.put("/studentInfo/" + children.get(i).getIdNum() + "/guardianContact", Long.parseLong(tv_parentContact.getText().toString())) ;
                    parentValues.put("/studentInfo/" + children.get(i).getIdNum() + "/guardianEmail", tv_parentEmail.getText().toString()) ;
                    parentValues.put("/studentInfo/" + children.get(i).getIdNum() + "/guardianName", tv_parentName.getText().toString()) ;
                }
                if(checkParent == 1){
                    parentValues.put("/studentInfo/" + children.get(i).getIdNum() + "/fatherContact", Long.parseLong(tv_parentContact.getText().toString())) ;
                    parentValues.put("/studentInfo/" + children.get(i).getIdNum() + "/fatherEmail", tv_parentEmail.getText().toString()) ;
                    parentValues.put("/studentInfo/" + children.get(i).getIdNum() + "/fatherName", tv_parentName.getText().toString()) ;
                }else{
                    parentValues.put("/studentInfo/" + children.get(i).getIdNum() + "/motherContact", Long.parseLong(tv_parentContact.getText().toString())) ;
                    parentValues.put("/studentInfo/" + children.get(i).getIdNum() + "/motherEmail", tv_parentEmail.getText().toString()) ;
                    parentValues.put("/studentInfo/" + children.get(i).getIdNum() + "/motherName", tv_parentName.getText().toString()) ;
                }
                parentNameBeforeEdit = tv_parentName.getText().toString() ;


                database.updateChildren(parentValues).addOnSuccessListener((OnSuccessListener) (aVoid) -> {
                    Toast.makeText(view.getContext(), "Data successfully updated!", Toast.LENGTH_SHORT).show();
                }).addOnFailureListener((error) -> {
                    Toast.makeText(view.getContext(), "Data was not updated!", Toast.LENGTH_SHORT).show();
                });
            }
        });
    }
}