package com.capstone.capstone_group.isklinika;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AdapterImmunizationHistory extends RecyclerView.Adapter<AdapterImmunizationHistory.ImmunizationHistoryHolder>{
    private Context tvContext;
    private final ArrayList<ClassVaccine> tvData;
    private final ArrayList<ClassImmuneRecord> immuneStatus ;
    private ClassStudentInfo studentInfo ;
    private AdapterImmunizationHistory.OnItemClickListener tvListener ;

    public interface OnItemClickListener{
        void onItemClick(int position) ;
    }

    public void setOnItemClickListener(AdapterImmunizationHistory.OnItemClickListener listener){
        tvListener = listener ;
    }

    public AdapterImmunizationHistory(Context tvContext, ArrayList<ClassVaccine> tvData, ArrayList<ClassImmuneRecord> immuneStatus, ClassStudentInfo studentInfo) {
        this.tvContext = tvContext;
        this.tvData = tvData;
        this.immuneStatus = immuneStatus ;
        this.studentInfo = studentInfo ;
    }

    @NonNull
    @Override
    public AdapterImmunizationHistory.ImmunizationHistoryHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycle_immunization_status, parent, false) ;
        AdapterImmunizationHistory.ImmunizationHistoryHolder myViewHolder = new AdapterImmunizationHistory.ImmunizationHistoryHolder(v, tvListener) ;
        return myViewHolder ;
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterImmunizationHistory.ImmunizationHistoryHolder holder, int position) {
        ClassVaccine vaccine = tvData.get(position) ;

        holder.tv_recycleVaccineName.setText(vaccine.getVaccineName());
        holder.tv_recycleVaccineExpectedAge.setText(vaccine.getExpectedAge());
        holder.tv_recycleVaccineFrequency.setText(vaccine.getDoses());

        if(position % 2 == 0){
            holder.tr_status.setBackgroundColor(Color.parseColor("#99B3E8FF"));
        }

        int j ;
        int matchCounter  = 0;
        boolean hasMatch = false ;

        for(j = 0 ; j < immuneStatus.size() ; j++){
            if(immuneStatus.get(j).getPurpose().equals(vaccine.getVaccineName())){
                hasMatch = true ;
                matchCounter += 1 ;
                Log.d("IMMUNIZATION//", "onBindViewHolder: matchcount = " + matchCounter);
            }
        }
        if(hasMatch){
            String dose = vaccine.getDoses() ;
            if(dose.length() == 1){
                if(matchCounter >= Integer.parseInt(dose)){
                    int i = Integer.parseInt(dose) ;
                    Log.d("IMMUNIZATION//", "onBindViewHolder: int = " + i);
                    holder.tv_recycleVaccineStatus.setText("Complete");
                    holder.tv_recycleVaccineStatus.setBackgroundResource(R.drawable.border_circle_green);
                } else{
                    holder.tv_recycleVaccineStatus.setText("Incomplete");
                    holder.tv_recycleVaccineStatus.setBackgroundResource(R.drawable.border_circle_yellow);
                }
            }else{
                if(matchCounter == Integer.parseInt(String.valueOf(dose.charAt(0))) || matchCounter == Integer.parseInt(String.valueOf(dose.charAt(2)))){
                    holder.tv_recycleVaccineStatus.setText("Complete");
                    holder.tv_recycleVaccineStatus.setBackgroundResource(R.drawable.border_circle_green);
                } else if(matchCounter < Integer.parseInt(String.valueOf(dose.charAt(0)))){
                    holder.tv_recycleVaccineStatus.setText("Incomplete");
                    holder.tv_recycleVaccineStatus.setBackgroundResource(R.drawable.border_circle_yellow);
                }
            }
        }else{
            holder.tv_recycleVaccineStatus.setText("No data");
            holder.tv_recycleVaccineStatus.setBackgroundResource(R.drawable.border_circle_red);
        }
    }

    @Override
    public int getItemCount() {
        return tvData.size();
    }


    public static class ImmunizationHistoryHolder extends RecyclerView.ViewHolder{

        TextView tv_recycleVaccineName, tv_recycleVaccineExpectedAge, tv_recycleVaccineFrequency, tv_recycleVaccineStatus ;
        TableRow tr_status ;

        public ImmunizationHistoryHolder(@NonNull View itemView, AdapterImmunizationHistory.OnItemClickListener listener){
            super(itemView);

            tv_recycleVaccineName = itemView.findViewById(R.id.tv_recycleVaccineName) ;
            tv_recycleVaccineExpectedAge = itemView.findViewById(R.id.tv_recycleVaccineExpectedAge) ;
            tv_recycleVaccineFrequency = itemView.findViewById(R.id.tv_recycleVaccineDose) ;
            tv_recycleVaccineStatus = itemView.findViewById(R.id.tv_recycleVaccineStatus) ;
            tr_status = itemView.findViewById(R.id.tr_status) ;

        }
    }

}
