package com.capstone.capstone_group.isklinika;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.google.android.material.card.MaterialCardView;

/*
This fragment contains the six modules that the users can use to monitor their child's health
 */
public class FragmentModule extends Fragment implements View.OnClickListener {

    private MaterialCardView mcard_clinicVisit, mcard_healthAssess, mcard_diseaseSurveillance,
                                mcard_medicalHistory, mcard_immunization, mcard_medication ;

    private ActivityLanding activity_landing ;

    //This is a required blank constructor
    public FragmentModule() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_table_module, container, false);

        this.activity_landing = (ActivityLanding) getActivity() ;

        this. mcard_clinicVisit = view.findViewById(R.id.mcard_clinicVisit) ;
        this. mcard_healthAssess = view.findViewById(R.id.mcard_healthAssess) ;
        this. mcard_diseaseSurveillance = view.findViewById(R.id.mcard_diseaseSurveillance) ;
        this. mcard_medicalHistory = view.findViewById(R.id.mcard_medicalHistory) ;
        this. mcard_immunization = view.findViewById(R.id.mcard_immunization) ;
        this. mcard_medication = view.findViewById(R.id.mcard_medication) ;

        mcard_clinicVisit.setOnClickListener(this);
        mcard_healthAssess.setOnClickListener(this);
        mcard_diseaseSurveillance.setOnClickListener(this);
        mcard_medicalHistory.setOnClickListener(this);
        mcard_immunization.setOnClickListener(this);
        mcard_medication.setOnClickListener(this);


        return view;
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.mcard_clinicVisit){
            Intent intent = new Intent(getContext(), ActivityClinicVisit.class) ;
            intent.putExtra("userType", activity_landing.getUserType()) ;
            switch (activity_landing.getUserType()){
                case "Student":
                    intent.putExtra("studentInfo", activity_landing.getStudentInfo()) ;
                    break;
                case "Parent":
                    intent.putParcelableArrayListExtra("children", activity_landing.getChildren()) ;
                    break;
            }
            startActivity(intent);

        }else if(view.getId() == R.id.mcard_healthAssess){
            Intent intent = new Intent(getContext(), ActivityHealthAssessment.class) ;
            intent.putExtra("userType", activity_landing.getUserType()) ;
            switch (activity_landing.getUserType()){
                case "Student":
                    intent.putExtra("studentInfo", activity_landing.getStudentInfo()) ;
                    break;
                case "Parent":
                    intent.putParcelableArrayListExtra("children", activity_landing.getChildren()) ;
                    break;
            }
            startActivity(intent);

            }else if(view.getId() == R.id.mcard_diseaseSurveillance){
                    Intent intent = new Intent(getContext(), ActivityDiseaseSurveillance.class) ;
                    intent.putExtra("userType", activity_landing.getUserType()) ;
                    switch (activity_landing.getUserType()){
                        case "Student":
                            intent.putExtra("studentInfo", activity_landing.getStudentInfo()) ;
                            break;
                        case "Parent":
                            intent.putParcelableArrayListExtra("children", activity_landing.getChildren()) ;
                            break;
                    }
                    startActivity(intent);

                }else if(view.getId() == R.id.mcard_medicalHistory){
                        Intent intent = new Intent(getContext(), ActivityMedicalHistory.class) ;
                        intent.putExtra("userType", activity_landing.getUserType()) ;
                        switch (activity_landing.getUserType()){
                            case "Student":
                                intent.putExtra("studentInfo", activity_landing.getStudentInfo()) ;
                                break;
                            case "Parent":
                                intent.putParcelableArrayListExtra("children", activity_landing.getChildren()) ;
                                break;
                        }
                        startActivity(intent);

                    }else if(view.getId() == R.id.mcard_immunization){
                            Intent intent = new Intent(getContext(), ActivityImmunization.class) ;
                            intent.putExtra("userType", activity_landing.getUserType()) ;
                            switch (activity_landing.getUserType()){
                                case "Student":
                                    intent.putExtra("studentInfo", activity_landing.getStudentInfo()) ;
                                    break;
                                case "Parent":
                                    intent.putParcelableArrayListExtra("children", activity_landing.getChildren()) ;
                                    break;
                            }
                            startActivity(intent);

                        }else if(view.getId() == R.id.mcard_medication){
                                Intent intent = new Intent(getContext(), ActivityMedication.class) ;
                                intent.putExtra("userType", activity_landing.getUserType()) ;
                                switch (activity_landing.getUserType()){
                                    case "Student":
                                        intent.putExtra("studentInfo", activity_landing.getStudentInfo()) ;
                                        break;
                                    case "Parent":
                                        intent.putParcelableArrayListExtra("children", activity_landing.getChildren()) ;
                                        break;
            }
                                startActivity(intent);

                        }
    }
}
