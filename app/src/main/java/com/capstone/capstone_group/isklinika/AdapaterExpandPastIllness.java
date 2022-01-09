package com.capstone.capstone_group.isklinika;

import android.content.Context;
import android.media.Image;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputEditText;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Map;

public class AdapaterExpandPastIllness extends BaseExpandableListAdapter {

    private Context context;
    private Map<ClassPastIllness, ClassPastIllness> pastIllnessMap  ;
    private ArrayList<ClassPastIllness> pastIllnessesInDate ;
    public TextView item ;
    public LinearLayout layout_group ;
    private TextView tv_expandDiagnosis, tv_expandVisitDate ;
    private TextInputEditText  tv_pastDisease, tv_pastStatus, tv_pastStart, tv_pastEnd, tv_pastTreatment, tv_pastNotes ;
    private ImageButton ibtn_editPast, ibtn_savePast ;


    public AdapaterExpandPastIllness(Context context, ArrayList<ClassPastIllness> pastIllnessesInDate,
                                     Map<ClassPastIllness, ClassPastIllness> pastIllnessMap ){
        this.context = context;
        this.pastIllnessesInDate = pastIllnessesInDate;
        this.pastIllnessMap = pastIllnessMap;
    }


    @Override
    public int getGroupCount() {
        return pastIllnessMap.size();
    }

    @Override
    public int getChildrenCount(int i) {
        return 1;
    }

    @Override
    public Object getGroup(int i) {
        return pastIllnessesInDate.get(i);
    }

    @Override
    public Object getChild(int i, int i1) {
        return pastIllnessMap.get(pastIllnessesInDate.get(i));
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
        ClassPastIllness pastIllness = (ClassPastIllness) getGroup(i);
        if(view == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.expand_title_past_illness, null);
        }
        this.tv_expandDiagnosis = view.findViewById(R.id.tv_expandDiagnosis);
        this.tv_expandVisitDate = view.findViewById(R.id.tv_expandVisitDate);

        tv_expandDiagnosis.setText(pastIllness.getDisease());
        tv_expandVisitDate.setText(pastIllness.getStartDate());
        return view;
    }

    @Override
    public View getChildView(int i, int i1, boolean b, View view, ViewGroup viewGroup) {
        if (view == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.expand_past_illness, null);
        }

        this.tv_pastDisease = view.findViewById(R.id.tv_pastDisease) ;
        this.tv_pastStatus = view.findViewById(R.id.tv_pastStatus) ;
        this.tv_pastStart = view.findViewById(R.id.tv_pastStart) ;
        this.tv_pastEnd = view.findViewById(R.id.tv_pastEnd) ;
        this.tv_pastTreatment = view.findViewById(R.id.tv_pastTreatment) ;
        this.tv_pastNotes = view.findViewById(R.id.tv_pastNotes) ;

        this.ibtn_editPast = view.findViewById(R.id.ibtn_editPast) ;
        this.ibtn_savePast = view.findViewById(R.id.ibtn_savePast) ;

        ClassPastIllness pastIllness = (ClassPastIllness) getChild(i, i1);

        tv_pastDisease.setText(pastIllness.getDisease());
        tv_pastStatus.setText(pastIllness.getStatus());
        tv_pastStart.setText(pastIllness.getStartDate());
        tv_pastEnd.setText(pastIllness.getEndDate());
        tv_pastTreatment.setText(pastIllness.getTreatment());
        tv_pastNotes.setText(pastIllness.getNotes());

        ibtn_editPast.setOnClickListener(view1 -> {
            ibtn_editPast.setVisibility(View.GONE);
            ibtn_savePast.setVisibility(View.VISIBLE);

            tv_pastDisease.setClickable(true);
            tv_pastDisease.setEnabled(true);
            tv_pastStatus.setClickable(true);
            tv_pastStatus.setEnabled(true);
            tv_pastStart.setClickable(true);
            tv_pastStart.setEnabled(true);
            tv_pastEnd.setClickable(true);
            tv_pastEnd.setEnabled(true);
            tv_pastTreatment.setClickable(true);
            tv_pastTreatment.setEnabled(true);
            tv_pastNotes.setClickable(true);
            tv_pastNotes.setEnabled(true);

        });

        ibtn_savePast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("MEDICALHISTORY//", "onClick: SAVE");
                ibtn_savePast.setVisibility(View.GONE);
                ibtn_editPast.setVisibility(View.VISIBLE);


                tv_pastDisease.setClickable(false);
                tv_pastDisease.setEnabled(false);
                tv_pastStatus.setClickable(false);
                tv_pastStatus.setEnabled(false);
                tv_pastStart.setClickable(false);
                tv_pastStart.setEnabled(false);
                tv_pastEnd.setClickable(false);
                tv_pastEnd.setEnabled(false);
                tv_pastTreatment.setClickable(false);
                tv_pastTreatment.setEnabled(false);
                tv_pastNotes.setClickable(false);
                tv_pastNotes.setEnabled(false);
            }
        }) ;



        return view ;
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return false;
    }
}
