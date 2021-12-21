package com.capstone.capstone_group.isklinika;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.google.android.material.card.MaterialCardView;

import java.util.ArrayList;

public class Fragment_Module extends Fragment implements View.OnClickListener {

    private MaterialCardView mcard_clinicVisit, mcard_healthAssess, mcard_diseaseSurveillance,
                                mcard_medicalHistory, mcard_immunization, mcard_medication ;

    public Fragment_Module() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_table_module, container, false);

        Activity_Landing activity_landing = (Activity_Landing) getActivity() ;

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


        }else if(view.getId() == R.id.mcard_healthAssess){


            }else if(view.getId() == R.id.mcard_diseaseSurveillance){


                }else if(view.getId() == R.id.mcard_medicalHistory){


                    }else if(view.getId() == R.id.mcard_immunization){


                        }else if(view.getId() == R.id.mcard_medication){


                        }
    }
}
