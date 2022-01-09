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
