package com.capstone.capstone_group.isklinika;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.ColorRes;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AdapterHealthAssessAPE extends RecyclerView.Adapter<AdapterHealthAssessAPE.HealthAssessHolder> {

    private Context tvContext;
    private ArrayList<ClassApe> tvData;
    private AdapterHealthAssessAPE.OnItemClickListener tvListener ;

    public interface OnItemClickListener{
        void onItemClick(int position) ;
    }

    public void setOnItemClickListener(AdapterHealthAssessAPE.OnItemClickListener listener){
        tvListener = listener ;
    }

    public AdapterHealthAssessAPE(Context tvContext, ArrayList<ClassApe> tvData) {
        this.tvContext = tvContext;
        this.tvData = tvData;
    }

    @NonNull
    @Override
    public AdapterHealthAssessAPE.HealthAssessHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycle_ape, parent, false) ;
        AdapterHealthAssessAPE.HealthAssessHolder myViewHolder = new AdapterHealthAssessAPE.HealthAssessHolder(v, tvListener) ;
        return myViewHolder ;
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterHealthAssessAPE.HealthAssessHolder holder, int position) {

        ClassApe ape = tvData.get(position) ;
        holder.tv_examinationSY.setText("SY: " + ape.getSchoolYear());
        holder.tv_examinationDate.setText(ape.getApeDate());
        holder.tv_examinationPhysician.setText(ape.getClinician());
        holder.tv_examinationWeight.setText(ape.getWeight());
        holder.tv_examinationHeight.setText(ape.getHeight());
        holder.tv_examinationBMI.setText(ape.getBmi());

        if(ape.getBmiStatus().equals("Normal")){
            holder.tv_examinationBMIStatus.getBackground().setTint(ContextCompat.getColor(tvContext, R.color.green));
        }else{
            holder.tv_examinationBMIStatus.getBackground().setTint(ContextCompat.getColor(tvContext, R.color.error_container));
        }

        holder.tv_examinationFindings.setText(ape.getConcern());
        holder.tv_examinationRecommendations.setText(ape.getAssess());

    }

    @Override
    public int getItemCount() {
        return tvData.size();
    }

    public static class HealthAssessHolder extends RecyclerView.ViewHolder{

        TextView tv_examinationSY, tv_examinationDate, tv_examinationPhysician, tv_examinationWeight, tv_examinationWeightStatus,
                tv_examinationHeight, tv_examinationHeightStatus, tv_examinationBMI, tv_examinationBMIStatus, tv_examinationFindings,
                tv_examinationRecommendations ;
        CheckBox check_examinationNormal;


        public HealthAssessHolder(@NonNull View itemView, AdapterHealthAssessAPE.OnItemClickListener listener){
            super(itemView);

            tv_examinationDate = itemView.findViewById(R.id.tv_examinationDate) ;
            tv_examinationSY = itemView.findViewById(R.id.tv_examinationSY) ;
            tv_examinationPhysician = itemView.findViewById(R.id.tv_examinationPhysician) ;
            tv_examinationWeight = itemView.findViewById(R.id.tv_examinationWeight) ;
            tv_examinationWeightStatus = itemView.findViewById(R.id.tv_examinationWeightStatus) ;
            tv_examinationHeight = itemView.findViewById(R.id.tv_examinationHeight) ;
            tv_examinationHeightStatus = itemView.findViewById(R.id.tv_examinationHeightStatus) ;
            tv_examinationBMI = itemView.findViewById(R.id.tv_examinationBMI) ;
            tv_examinationBMIStatus = itemView.findViewById(R.id.tv_examinationBMIStatus) ;
            tv_examinationFindings = itemView.findViewById(R.id.tv_examinationFindings) ;
            tv_examinationRecommendations = itemView.findViewById(R.id.tv_examinationRecommendations) ;
            check_examinationNormal = itemView.findViewById(R.id.check_examinationNormal) ;


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(listener != null){
                        int position = getAdapterPosition();
                        if(position != RecyclerView.NO_POSITION){
                            listener.onItemClick(position);
                        }
                    }
                }
            });
        }
    }
}
