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

import java.util.List;
import java.util.Map;

public class AdapaterExpandApe extends BaseExpandableListAdapter {

    private Context context;
    private Map<String, ClassApe> apeMap ;
    private List<String> groupList;
    public TextView item ;
    public LinearLayout layout_group ;
    private TextView tv_examinationSY, tv_examinationDate, tv_examinationPhysician, tv_examinationWeight, tv_examinationWeightStatus,
            tv_examinationHeight, tv_examinationHeightStatus, tv_examinationBMI, tv_examinationBMIStatus, tv_examinationFindings,
            tv_examinationRecommendations ;
    private MaterialButton mbtn_apeDetails ;
    private CheckBox check_examinationNormal;

    public AdapaterExpandApe(Context context, List<String> groupList,
                                   Map<String, ClassApe> apeMap){
        this.context = context;
        this.apeMap = apeMap;
        this.groupList = groupList;
    }


    @Override
    public int getGroupCount() {
        return apeMap.size();
    }

    @Override
    public int getChildrenCount(int i) {
        return 1;
    }

    @Override
    public Object getGroup(int i) {
        return groupList.get(i);
    }

    @Override
    public Object getChild(int i, int i1) {
        return apeMap.get(groupList.get(i));
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
        String schoolYear = getGroup(i).toString();
        if(view == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.expand_title_group_ape, null);
        }
        this.item = view.findViewById(R.id.tv_expandSY);
        item.setTypeface(null, Typeface.BOLD);
        item.setText("SY: " + schoolYear);
        return view;
    }

    @Override
    public View getChildView(int i, int i1, boolean b, View view, ViewGroup viewGroup) {
        if (view == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.expand_ape, null);
        }

        tv_examinationDate = view.findViewById(R.id.tv_examinationDate) ;
        tv_examinationSY = view.findViewById(R.id.tv_examinationSY) ;
        tv_examinationPhysician = view.findViewById(R.id.tv_examinationPhysician) ;
        tv_examinationWeight = view.findViewById(R.id.tv_examinationWeight) ;
        tv_examinationWeightStatus = view.findViewById(R.id.tv_examinationWeightStatus) ;
        tv_examinationHeight = view.findViewById(R.id.tv_examinationHeight) ;
        tv_examinationHeightStatus = view.findViewById(R.id.tv_examinationHeightStatus) ;
        tv_examinationBMI = view.findViewById(R.id.tv_examinationBMI) ;
        tv_examinationBMIStatus = view.findViewById(R.id.tv_examinationBMIStatus) ;
        tv_examinationFindings = view.findViewById(R.id.tv_examinationFindings) ;
        tv_examinationRecommendations = view.findViewById(R.id.tv_examinationRecommendations) ;
        check_examinationNormal = view.findViewById(R.id.check_examinationNormal) ;
        mbtn_apeDetails = view.findViewById(R.id.mbtn_apeDetails) ;

        ClassApe ape = (ClassApe) getChild(i, i1);
        tv_examinationSY.setText("SY: " + ape.getSchoolYear());
        tv_examinationDate.setText(ape.getApeDate());
        tv_examinationPhysician.setText(ape.getClinician());
        tv_examinationWeight.setText(ape.getWeight());
        tv_examinationWeightStatus.setText(ape.getWeightStatus());
        tv_examinationHeight.setText(ape.getHeight());
        tv_examinationHeightStatus.setText(ape.getHeightStatus());
        tv_examinationBMI.setText(ape.getBmi());
        tv_examinationBMIStatus.setText(ape.getBmiStatus());

        if(ape.getBmiStatus().equals("Normal")){
            tv_examinationBMIStatus.getBackground().setTint(ContextCompat.getColor(context, R.color.green));
        }else{
            tv_examinationBMIStatus.getBackground().setTint(ContextCompat.getColor(context, R.color.error_container));
        }
        if(ape.getHeightStatus().equals("Normal")){
            tv_examinationHeightStatus.getBackground().setTint(ContextCompat.getColor(context, R.color.green));
        }else{
            tv_examinationHeightStatus.getBackground().setTint(ContextCompat.getColor(context, R.color.error_container));
        }
        if(ape.getWeightStatus().equals("Normal")){
            tv_examinationWeightStatus.getBackground().setTint(ContextCompat.getColor(context, R.color.green));
        }else{
            tv_examinationWeightStatus.getBackground().setTint(ContextCompat.getColor(context, R.color.error_container));
        }


        tv_examinationFindings.setText(ape.getConcern());
        tv_examinationRecommendations.setText(ape.getAssess());

        mbtn_apeDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ClassApe ape = (ClassApe) getChild(i, i1);
                Log.d("HEALTHASSESSMENT//", "onClick: " + ape.getName());
                Intent intent = new Intent(context, ActivityPhysicalExam.class) ;
                intent.putExtra("ape", ape) ;
                context.startActivity(intent);
            }
        });


        return view ;
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return false;
    }
}
