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

public class AdapaterExpandDental extends BaseExpandableListAdapter {

    private Context context;
    private Map<String, ClassDental> dentalMap ;
    private List<String> groupList;
    public TextView item ;
    public LinearLayout layout_group ;
    private TextView tv_ur1, tv_ur2, tv_ur3, tv_ur4, tv_ur5, tv_ur6, tv_ur7, tv_ur8 ;
    private TextView tv_ul1, tv_ul2, tv_ul3, tv_ul4, tv_ul5, tv_ul6, tv_ul7, tv_ul8 ;
    private TextView tv_lr1, tv_lr2, tv_lr3, tv_lr4, tv_lr5, tv_lr6, tv_lr7, tv_lr8 ;
    private TextView tv_ll1, tv_ll2, tv_ll3, tv_ll4, tv_ll5, tv_ll6, tv_ll7, tv_ll8 ;
    private MaterialButton mbtn_dentalDetails ;

    public AdapaterExpandDental(Context context, List<String> groupList,
                                Map<String, ClassDental> dentalMap){
        this.context = context;
        this.dentalMap = dentalMap;
        this.groupList = groupList;
    }


    @Override
    public int getGroupCount() {
        return dentalMap.size();
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
        return dentalMap.get(groupList.get(i));
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
            view = inflater.inflate(R.layout.expand_dental, null);
        }

        this.mbtn_dentalDetails = view.findViewById(R.id.mbtn_dentalDetails) ;

        this.tv_ur1 = view.findViewById(R.id.tv_ur1) ;
        this.tv_ur2 = view.findViewById(R.id.tv_ur2) ;
        this.tv_ur3 = view.findViewById(R.id.tv_ur3) ;
        this.tv_ur4 = view.findViewById(R.id.tv_ur4) ;
        this.tv_ur5 = view.findViewById(R.id.tv_ur5) ;
        this.tv_ur6 = view.findViewById(R.id.tv_ur6) ;
        this.tv_ur7 = view.findViewById(R.id.tv_ur7) ;
        this.tv_ur8 = view.findViewById(R.id.tv_ur8) ;

        this.tv_ul1 = view.findViewById(R.id.tv_ul1) ;
        this.tv_ul2 = view.findViewById(R.id.tv_ul2) ;
        this.tv_ul3 = view.findViewById(R.id.tv_ul3) ;
        this.tv_ul4 = view.findViewById(R.id.tv_ul4) ;
        this.tv_ul5 = view.findViewById(R.id.tv_ul5) ;
        this.tv_ul6 = view.findViewById(R.id.tv_ul6) ;
        this.tv_ul7 = view.findViewById(R.id.tv_ul7) ;
        this.tv_ul8 = view.findViewById(R.id.tv_ul8) ;

        this.tv_lr1 = view.findViewById(R.id.tv_lr1) ;
        this.tv_lr2 = view.findViewById(R.id.tv_lr2) ;
        this.tv_lr3 = view.findViewById(R.id.tv_lr3) ;
        this.tv_lr4 = view.findViewById(R.id.tv_lr4) ;
        this.tv_lr5 = view.findViewById(R.id.tv_lr5) ;
        this.tv_lr6 = view.findViewById(R.id.tv_lr6) ;
        this.tv_lr7 = view.findViewById(R.id.tv_lr7) ;
        this.tv_lr8 = view.findViewById(R.id.tv_lr8) ;

        this.tv_ll1 = view.findViewById(R.id.tv_ll1) ;
        this.tv_ll2 = view.findViewById(R.id.tv_ll2) ;
        this.tv_ll3 = view.findViewById(R.id.tv_ll3) ;
        this.tv_ll4 = view.findViewById(R.id.tv_ll4) ;
        this.tv_ll5 = view.findViewById(R.id.tv_ll5) ;
        this.tv_ll6 = view.findViewById(R.id.tv_ll6) ;
        this.tv_ll7 = view.findViewById(R.id.tv_ll7) ;
        this.tv_ll8 = view.findViewById(R.id.tv_ll8) ;

        ClassDental dental = (ClassDental) getChild(i, i1);

        tv_ur1.setText(dental.getInputAt(53));
        tv_ur2.setText(dental.getInputAt(52));
        tv_ur3.setText(dental.getInputAt(51));
        tv_ur4.setText(dental.getInputAt(50));
        tv_ur5.setText(dental.getInputAt(49));
        tv_ur6.setText(dental.getInputAt(48));
        tv_ur7.setText(dental.getInputAt(47));
        tv_ur8.setText(dental.getInputAt(46));

        tv_ul1.setText(dental.getInputAt(54));
        tv_ul2.setText(dental.getInputAt(55));
        tv_ul3.setText(dental.getInputAt(56));
        tv_ul4.setText(dental.getInputAt(57));
        tv_ul5.setText(dental.getInputAt(58));
        tv_ul6.setText(dental.getInputAt(59));
        tv_ul7.setText(dental.getInputAt(60));
        tv_ul8.setText(dental.getInputAt(61));

        tv_lr1.setText(dental.getInputAt(101));
        tv_lr2.setText(dental.getInputAt(100));
        tv_lr3.setText(dental.getInputAt(99));
        tv_lr4.setText(dental.getInputAt(98));
        tv_lr5.setText(dental.getInputAt(97));
        tv_lr6.setText(dental.getInputAt(96));
        tv_lr7.setText(dental.getInputAt(95));
        tv_lr8.setText(dental.getInputAt(94));

        tv_ll1.setText(dental.getInputAt(102));
        tv_ll2.setText(dental.getInputAt(103));
        tv_ll3.setText(dental.getInputAt(104));
        tv_ll4.setText(dental.getInputAt(105));
        tv_ll5.setText(dental.getInputAt(106));
        tv_ll6.setText(dental.getInputAt(107));
        tv_ll7.setText(dental.getInputAt(108));
        tv_ll8.setText(dental.getInputAt(109));

        mbtn_dentalDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ClassDental dental = (ClassDental) getChild(i, i1);
                Intent intent = new Intent(context, ActivityDentalExam.class) ;
                intent.putExtra("dental", dental) ;
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
