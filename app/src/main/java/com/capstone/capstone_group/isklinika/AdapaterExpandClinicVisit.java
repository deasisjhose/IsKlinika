package com.capstone.capstone_group.isklinika;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class AdapaterExpandClinicVisit extends BaseExpandableListAdapter {

    private Context context;
    private Map<ClassClinicVisit, ClassClinicVisit> clinicVisitMap  ;
    private ArrayList<ClassClinicVisit> clinicVisitsDiagnosisDate ;
    public TextView item ;
    public LinearLayout layout_group ;
    private TextView tv_expandDiagnosis, tv_expandVisitDate, tv_cvNurse, tv_cvVisitDate, tv_cvTimeIn, tv_cvTimeOut,
                        tv_cvTemp, tv_cvBP, tv_cvPR, tv_cvRR, tv_cvComplaint, tv_cvTreatment, tv_cvDisposition, tv_cvDiagnosis,
                            tv_cvNotes;


    public AdapaterExpandClinicVisit(Context context, ArrayList<ClassClinicVisit> clinicVisitsDiagnosisDate,
                                     Map<ClassClinicVisit, ClassClinicVisit> clinicVisitMap ){
        this.context = context;
        this.clinicVisitsDiagnosisDate = clinicVisitsDiagnosisDate;
        this.clinicVisitMap = clinicVisitMap;
    }


    @Override
    public int getGroupCount() {
        return clinicVisitMap.size();
    }

    @Override
    public int getChildrenCount(int i) {
        return 1;
    }

    @Override
    public Object getGroup(int i) {
        return clinicVisitsDiagnosisDate.get(i);
    }

    @Override
    public Object getChild(int i, int i1) {
        return clinicVisitMap.get(clinicVisitsDiagnosisDate.get(i));
    }

    @Override
    public long getGroupId(int i) {
        return i;
    }

    @Override
    public long getChildId(int i, int i1) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getGroupView(int i, boolean b, View view, ViewGroup viewGroup) {
        ClassClinicVisit visit = (ClassClinicVisit) getGroup(i);
        if(view == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.expand_title_clinic_visit, null);
        }
        this.tv_expandDiagnosis = view.findViewById(R.id.tv_expandDiagnosis);
        this.tv_expandVisitDate = view.findViewById(R.id.tv_expandVisitDate);

        if(visit.getDiagnosis().equals("")){
            tv_expandDiagnosis.setText(visit.getVisitReason());
        }else
            tv_expandDiagnosis.setText(visit.getDiagnosis());
        tv_expandVisitDate.setText(visit.getVisitDate());
        return view;
    }

    @Override
    public View getChildView(int i, int i1, boolean b, View view, ViewGroup viewGroup) {
        if (view == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.expand_clinic_visit, null);
        }

        tv_cvNurse = view.findViewById(R.id.tv_cvNurse) ;
        tv_cvVisitDate = view.findViewById(R.id.tv_cvVisitDate) ;
        tv_cvTimeIn = view.findViewById(R.id.tv_cvTimeIn) ;
        tv_cvTimeOut = view.findViewById(R.id.tv_cvTimeOut) ;
        tv_cvTemp = view.findViewById(R.id.tv_cvTemp) ;
        tv_cvBP = view.findViewById(R.id.tv_cvBP) ;
        tv_cvPR = view.findViewById(R.id.tv_cvPR) ;
        tv_cvRR = view.findViewById(R.id.tv_cvRR) ;
        tv_cvComplaint = view.findViewById(R.id.tv_cvComplaint) ;
        tv_cvTreatment = view.findViewById(R.id.tv_cvTreatment) ;
        tv_cvDisposition = view.findViewById(R.id.tv_cvDisposition) ;
        tv_cvDiagnosis = view.findViewById(R.id.tv_cvDiagnosis) ;
        tv_cvNotes = view.findViewById(R.id.tv_cvNotes) ;

        ClassClinicVisit clinicVisit = (ClassClinicVisit) getChild(i, i1);
        tv_cvNurse.setText(clinicVisit.getNurseName());
        tv_cvVisitDate.setText(clinicVisit.getVisitDate());
        tv_cvTimeIn.setText(clinicVisit.getTimeIn());
        tv_cvTimeOut.setText(clinicVisit.getTimeOut());
        tv_cvTemp.setText(clinicVisit.getBodyTem());
        tv_cvBP.setText(clinicVisit.getSystolicBP() + "/" + clinicVisit.getDiastolicBP());
        tv_cvPR.setText(clinicVisit.getPulseRateStatus());
        tv_cvRR.setText(clinicVisit.getRespRateStatus());
        tv_cvComplaint.setText(clinicVisit.getVisitReason());
        tv_cvTreatment.setText(clinicVisit.getTreatment());
        tv_cvDisposition.setText(clinicVisit.getStatus());
        tv_cvDiagnosis.setText(clinicVisit.getDiagnosis());
        tv_cvNotes.setText(clinicVisit.getNotes());
//        mbtn_apeDetails.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                ClassApe ape = (ClassApe) getChild(i, i1);
//                Log.d("HEALTHASSESSMENT//", "onClick: " + ape.getName());
//                Intent intent = new Intent(context, ActivityPhysicalExam.class) ;
//                intent.putExtra("ape", ape) ;
//                context.startActivity(intent);
//            }
//        });


        return view ;
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return false;
    }
}
