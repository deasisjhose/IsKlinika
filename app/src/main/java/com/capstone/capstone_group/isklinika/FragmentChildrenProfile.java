package com.capstone.capstone_group.isklinika;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.datepicker.CalendarConstraints;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;

public class FragmentChildrenProfile extends Fragment {

    FirebaseDatabase db = FirebaseDatabase.getInstance();   // getting real time database
    public DatabaseReference database = db.getReference();
    public DatabaseReference databaseReference= db.getReference("studentInfo");

    private TextView tv_pBirthday, tv_pAge, tv_pSex, tv_pStudentType ;
    private TextInputEditText tv_pAddress, tv_pReligion, tv_pNationality, tv_pGuardian, tv_pGuardianEmail,  tv_pGuardianContact, tv_pPedia, tv_pPediaEmail, tv_pPediaContact,
            tv_pDentist, tv_pDentistEmail, tv_pDentistContact, tv_pHospital, tv_pHospitalAddress;
    private MaterialCardView mcard_editProfile, mcard_saveProfile ;
    private ActivityLanding activity_landing ;
    private MaterialDatePicker materialDatePicker ;


    public FragmentChildrenProfile() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_children_profile, container, false);
        this.mcard_editProfile = view.findViewById(R.id.mcard_editProfile) ;
        this.mcard_saveProfile = view.findViewById(R.id.mcard_saveProfile) ;
        this.activity_landing = (ActivityLanding) getActivity() ;

        this.tv_pBirthday = view.findViewById(R.id.tv_pBirthday) ;
        this.tv_pAge = view.findViewById(R.id.tv_pAge) ;
        this.tv_pAddress = view.findViewById(R.id.tv_pAddress) ;
        this.tv_pSex = view.findViewById(R.id.tv_pSex) ;
        this.tv_pStudentType = view.findViewById(R.id.tv_pStudentType) ;
        this.tv_pReligion = view.findViewById(R.id.tv_pReligion) ;
        this.tv_pNationality = view.findViewById(R.id.tv_pNationality) ;

        this.tv_pGuardian = view.findViewById(R.id.tv_pGuardian) ;
        this.tv_pGuardianEmail = view.findViewById(R.id.tv_pGuardianEmail) ;
        this.tv_pGuardianContact = view.findViewById(R.id.tv_pGuardianContact) ;

        this.tv_pPedia = view.findViewById(R.id.tv_pPedia) ;
        this.tv_pPediaContact = view.findViewById(R.id.tv_pPediaContact) ;
        this.tv_pPediaEmail = view.findViewById(R.id.tv_pPediaEmail) ;
        this.tv_pDentist = view.findViewById(R.id.tv_pDentist) ;
        this.tv_pDentistEmail = view.findViewById(R.id.tv_pDentistEmail) ;
        this.tv_pDentistContact = view.findViewById(R.id.tv_pDentistContact) ;
        this.tv_pHospital = view.findViewById(R.id.tv_pHospital) ;
        this.tv_pHospitalAddress = view.findViewById(R.id.tv_pHospitalAddress) ;

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ClassStudentInfo studentInfo = activity_landing.getStudentInfo() ;
        Log.d("LANDING//", "FRAGMENT_PROFILEonViewCreated: childSelected = " + studentInfo.getFullName());
        tv_pBirthday.setText(studentInfo.getBirthday());
        tv_pAge.setText(studentInfo.getAge());
        tv_pAddress.setText(studentInfo.getAddress());
        tv_pSex.setText(studentInfo.getSex());
        tv_pStudentType.setText(studentInfo.getStudentType());
        tv_pReligion.setText(studentInfo.getReligion());
        tv_pNationality.setText(studentInfo.getNationality());

        tv_pGuardian.setText(studentInfo.getGuardianName());
        tv_pGuardianEmail.setText(studentInfo.getGuardianEmail());
        tv_pGuardianContact.setText(studentInfo.getGuardianContact());

        tv_pPedia.setText(studentInfo.getPediaName());
        tv_pPediaContact.setText(studentInfo.getPediaContact());
        tv_pPediaEmail.setText(studentInfo.getPediaEmail());
        tv_pDentist.setText(studentInfo.getDentistName());
        tv_pDentistEmail.setText(studentInfo.getDentistEmail());
        tv_pDentistContact.setText(studentInfo.getDentistContact());
        tv_pHospital.setText(studentInfo.getPreferredHospital());
        tv_pHospitalAddress.setText(studentInfo.getHospitalAddress());

        mcard_editProfile.setOnClickListener(view1 -> {
            mcard_saveProfile.setVisibility(View.VISIBLE);
            mcard_editProfile.setVisibility(View.GONE);

            tv_pBirthday.setEnabled(true);
            tv_pBirthday.setClickable(true);
            tv_pAddress.setEnabled(true);
            tv_pAddress.setClickable(true);
            tv_pReligion.setEnabled(true);
            tv_pReligion.setClickable(true);
            tv_pNationality.setEnabled(true);
            tv_pNationality.setClickable(true);

            tv_pGuardian.setEnabled(true);
            tv_pGuardian.setClickable(true);
            tv_pGuardianEmail.setEnabled(true);
            tv_pGuardianEmail.setClickable(true);
            tv_pGuardianContact.setEnabled(true);
            tv_pGuardianContact.setClickable(true);

            tv_pPedia.setEnabled(true);
            tv_pPedia.setClickable(true);
            tv_pPediaContact.setEnabled(true);
            tv_pPediaContact.setClickable(true);
            tv_pPediaEmail.setEnabled(true);
            tv_pPediaEmail.setClickable(true);
            tv_pDentist.setEnabled(true);
            tv_pDentist.setClickable(true);
            tv_pDentistEmail.setEnabled(true);
            tv_pDentistEmail.setClickable(true);
            tv_pDentistContact.setEnabled(true);
            tv_pDentistContact.setClickable(true);
            tv_pHospital.setEnabled(true);
            tv_pHospital.setClickable(true);
            tv_pHospitalAddress.setEnabled(true);
            tv_pHospitalAddress.setClickable(true);
        });

        mcard_saveProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mcard_saveProfile.setVisibility(View.GONE);
                mcard_editProfile.setVisibility(View.VISIBLE);

                tv_pBirthday.setEnabled(false);
                tv_pBirthday.setClickable(false);
                tv_pAddress.setEnabled(false);
                tv_pAddress.setClickable(false);
                tv_pReligion.setEnabled(false);
                tv_pReligion.setClickable(false);
                tv_pNationality.setEnabled(false);
                tv_pNationality.setClickable(false);

                tv_pGuardian.setEnabled(false);
                tv_pGuardian.setClickable(false);
                tv_pGuardianEmail.setEnabled(false);
                tv_pGuardianEmail.setClickable(false);
                tv_pGuardianContact.setEnabled(false);
                tv_pGuardianContact.setClickable(false);

                tv_pPedia.setEnabled(false);
                tv_pPedia.setClickable(false);
                tv_pPediaContact.setEnabled(false);
                tv_pPediaContact.setClickable(false);
                tv_pPediaEmail.setEnabled(false);
                tv_pPediaEmail.setClickable(false);
                tv_pDentist.setEnabled(false);
                tv_pDentist.setClickable(false);
                tv_pDentistEmail.setEnabled(false);
                tv_pDentistEmail.setClickable(false);
                tv_pDentistContact.setEnabled(false);
                tv_pDentistContact.setClickable(false);
                tv_pHospital.setEnabled(false);
                tv_pHospital.setClickable(false);
                tv_pHospitalAddress.setEnabled(false);
                tv_pHospitalAddress.setClickable(false);

                HashMap<String, Object> personalValues = new HashMap<>();
                personalValues.put("/studentInfo/" + studentInfo.getIdNum() + "/birthday", tv_pBirthday.getText().toString());
                personalValues.put("/studentInfo/" + studentInfo.getIdNum() + "/age", tv_pAge.getText().toString());
                personalValues.put("/studentInfo/" + studentInfo.getIdNum() + "/address", tv_pAddress.getText().toString());
                personalValues.put("/studentInfo/" + studentInfo.getIdNum() + "/nationality", tv_pNationality.getText().toString());
                personalValues.put("/studentInfo/" + studentInfo.getIdNum() + "/religion", tv_pReligion.getText().toString());
                personalValues.put("/studentInfo/" + studentInfo.getIdNum() + "/guardianName", tv_pGuardian.getText().toString()) ;
                personalValues.put("/studentInfo/" + studentInfo.getIdNum() + "/guardianContact", tv_pGuardianContact.getText().toString()) ;
                personalValues.put("/studentInfo/" + studentInfo.getIdNum() + "/guardianEmail", tv_pGuardianEmail.getText().toString()) ;
                personalValues.put("/studentInfo/" + studentInfo.getIdNum() + "/pediaName", tv_pPedia.getText().toString());
                personalValues.put("/studentInfo/" + studentInfo.getIdNum() + "/pediaEmail", tv_pPediaEmail.getText().toString());
                personalValues.put("/studentInfo/" + studentInfo.getIdNum() + "/pediaContact", tv_pPediaContact.getText().toString());
                personalValues.put("/studentInfo/" + studentInfo.getIdNum() + "/dentistName", tv_pDentist.getText().toString());
                personalValues.put("/studentInfo/" + studentInfo.getIdNum() + "/dentistEmail", tv_pDentistEmail.getText().toString());
                personalValues.put("/studentInfo/" + studentInfo.getIdNum() + "/dentistContact", tv_pDentistContact.getText().toString());
                personalValues.put("/studentInfo/" + studentInfo.getIdNum() + "/preferredHospital", tv_pHospital.getText().toString());
                personalValues.put("/studentInfo/" + studentInfo.getIdNum() + "/hospitalAddress", tv_pHospitalAddress.getText().toString());


                database.updateChildren(personalValues).addOnSuccessListener((OnSuccessListener) (aVoid) -> {
                    Toast.makeText(view.getContext(), "Data successfully updated!", Toast.LENGTH_SHORT).show();
                }).addOnFailureListener((error) -> {
                    Toast.makeText(view.getContext(), "Data was not updated!", Toast.LENGTH_SHORT).show();
                });

            }
        });


        MaterialDatePicker.Builder builder = MaterialDatePicker.Builder.datePicker() ;
        builder.setTitleText("Select Immunization Date") ;
        builder.setTheme(R.style.ThemeOverlay_App_DatePicker_Profile) ;
        builder.setSelection(MaterialDatePicker.todayInUtcMilliseconds()) ;
        builder.setInputMode(MaterialDatePicker.INPUT_MODE_TEXT) ;


        this.materialDatePicker = builder.build() ;
        materialDatePicker.addOnPositiveButtonClickListener(selection -> {

            ClassDateConvert dateConvert = new ClassDateConvert(materialDatePicker.getHeaderText()) ;

            tv_pBirthday.setText(dateConvert.getConverted());

            // Calculating age
            String birthdayInput = tv_pBirthday.getText().toString();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-dd-yyyy");
            LocalDate birthDate = LocalDate.parse(birthdayInput, formatter);
            Period period = Period.between(birthDate, LocalDate.now());
            String age = String.valueOf(period.getYears());

            tv_pAge.setText(age);
        }) ;

        tv_pBirthday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                materialDatePicker.show(getChildFragmentManager(), "DATE PICKER");

            }
        });

    }
}
