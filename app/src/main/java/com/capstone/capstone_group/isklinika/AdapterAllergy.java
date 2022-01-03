package com.capstone.capstone_group.isklinika;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class AdapterAllergy extends RecyclerView.Adapter<AdapterAllergy.AllergiesHolder>{

    private Context tvContext;
    private ArrayList<ClassAllergy> tvData;
    private AdapterAllergy.OnItemClickListener tvListener ;
    private String studentId ;
    private String userType ;
    public AdapterAllergy.AllergiesHolder allergiesHolder ;
    public String TAG="MEDICALHISTORY//";

    FirebaseDatabase db = FirebaseDatabase.getInstance();   // getting real time database
    public DatabaseReference database = db.getReference();

    public interface OnItemClickListener{
        void onItemClick(int position) ;
    }

    public void setOnItemClickListener(AdapterAllergy.OnItemClickListener listener){
        tvListener = listener ;
    }

    public AdapterAllergy(Context tvContext, ArrayList<ClassAllergy> tvData, String studentId) {
        this.tvContext = tvContext;
        this.tvData = tvData;
        this.studentId = studentId ;
    }

    @NonNull
    @Override
    public AdapterAllergy.AllergiesHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType ) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycle_allergy, parent, false) ;
        AdapterAllergy.AllergiesHolder myViewHolder = new AdapterAllergy.AllergiesHolder(v, tvListener) ;
        this.allergiesHolder = myViewHolder ;
        return myViewHolder ;
    }

    @Override
    public void onBindViewHolder(AllergiesHolder holder, int position){
        ClassAllergy allergy = tvData.get(position) ;

        if(position % 2 == 0)
            holder.tr_recycleAllergy.setBackgroundColor(Color.parseColor("#CCDDF6C0"));

        holder.tv_recycleAllergy.setText(allergy.getAllergy());
        holder.tv_recycleAllergyType.setText(allergy.getType());
        holder.tv_recycleAllergyDate.setText(allergy.getDiagnosisDate());
        holder.tv_recycleAllergyLastOccur.setText(allergy.getLastOccurrence());


    }

    public void setUserType(String userType){
        this.userType = userType ;
    }

    @Override
    public int getItemCount() {
        return tvData.size();
    }

    public static class AllergiesHolder extends RecyclerView.ViewHolder{

         TableRow tr_recycleAllergy ;
         TextView tv_recycleAllergy, tv_recycleAllergyType, tv_recycleAllergyDate, tv_recycleAllergyLastOccur ;

        public AllergiesHolder(@NonNull View itemView, AdapterAllergy.OnItemClickListener listener){
            super(itemView);

            tr_recycleAllergy = itemView.findViewById(R.id.tr_recycleAllergy) ;
            tv_recycleAllergy = itemView.findViewById(R.id.tv_recycleAllergy) ;
            tv_recycleAllergyType = itemView.findViewById(R.id.tv_recycleAllergyType) ;
            tv_recycleAllergyDate = itemView.findViewById(R.id.tv_recycleAllergyDate) ;
            tv_recycleAllergyLastOccur = itemView.findViewById(R.id.tv_recycleAllergyLastOccur) ;

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
