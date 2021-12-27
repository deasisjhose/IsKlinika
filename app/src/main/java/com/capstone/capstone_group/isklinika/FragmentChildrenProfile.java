package com.capstone.capstone_group.isklinika;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class FragmentChildrenProfile extends Fragment {

    private TextView tv_pBirthday, tv_pAge, tv_pAddress, tv_pSex, tv_pStudentType, tv_pReligion, tv_pNationality,
            tv_pGuardian, tv_pGuardianEmail,  tv_pGuardianContact, tv_pPedia, tv_pPediaEmail, tv_pPediaContact,
            tv_pDentist, tv_pDentistEmail, tv_pDentistContact, tv_pHospital, tv_pHospitalAddress ;

    private ActivityLanding activity_landing ;
    public FragmentChildrenProfile() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_children_profile, container, false);

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

    }
}
