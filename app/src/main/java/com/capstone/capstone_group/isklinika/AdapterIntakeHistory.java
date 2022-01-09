package com.capstone.capstone_group.isklinika;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class AdapterIntakeHistory extends RecyclerView.Adapter<AdapterIntakeHistory.IntakeHistoryHolder>{

    private Context tvContext;
    private ArrayList<ClassIntakeHistory> tvData;
    private AdapterIntakeHistory.OnItemClickListener tvListener ;
    private String studentId ;
    public String TAG="MEDICATION//";

    FirebaseDatabase db = FirebaseDatabase.getInstance();   // getting real time database
    public DatabaseReference database = db.getReference();

    public interface OnItemClickListener{
        void onItemClick(int position) ;
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        tvListener = listener ;
    }

    public AdapterIntakeHistory(Context tvContext, ArrayList<ClassIntakeHistory> tvData, String studentId) {
        this.tvContext = tvContext;
        this.tvData = tvData;
        this.studentId = studentId;
    }


    @NonNull
    @Override
    public AdapterIntakeHistory.IntakeHistoryHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycle_intake_history, parent, false) ;
        AdapterIntakeHistory.IntakeHistoryHolder myViewHolder = new AdapterIntakeHistory.IntakeHistoryHolder(v, tvListener) ;
        return myViewHolder ;
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterIntakeHistory.IntakeHistoryHolder holder, int position) {
        ClassIntakeHistory intakeHistory = tvData.get(position) ;

        if(position % 2 == 0)
            holder.tr_intake.setBackgroundColor(Color.parseColor("#E1D6FF"));
        holder.tv_intakeMed.setText(intakeHistory.getSpecificMedicine());
        holder.tv_intakeAmt.setText(intakeHistory.getSpecificAmount());
        holder.tv_intakeDate.setText(intakeHistory.getDateTaken());
        holder.tv_intakeTime.setText(intakeHistory.getTime());

    }

    @Override
    public int getItemCount() {
        return tvData.size();
    }

    public static class IntakeHistoryHolder extends RecyclerView.ViewHolder{

        TableRow tr_intake ;
        TextView tv_intakeMed, tv_intakeAmt,  tv_intakeDate, tv_intakeTime ;

        public IntakeHistoryHolder(@NonNull View itemView, OnItemClickListener listener){
            super(itemView);

            tv_intakeMed = itemView.findViewById(R.id.tv_intakeMed) ;
            tv_intakeAmt = itemView.findViewById(R.id.tv_intakeAmt) ;
            tv_intakeDate = itemView.findViewById(R.id.tv_intakeDate) ;
            tv_intakeTime = itemView.findViewById(R.id.tv_intakeTime) ;
            tr_intake = itemView.findViewById(R.id.tr_intake) ;

        }
    }
}
