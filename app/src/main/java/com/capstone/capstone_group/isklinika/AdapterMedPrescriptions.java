package com.capstone.capstone_group.isklinika;

import android.content.Context;
import android.graphics.Color;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;

public class AdapterMedPrescriptions extends  RecyclerView.Adapter<AdapterMedPrescriptions.MedPrescriptionsHolder>{

    private Context tvContext;
    private ArrayList<ClassMedication> tvData;
    private AdapterMedPrescriptions.OnItemClickListener tvListener ;
    private String studentId ;
    private String userType ;
    private String prescriptionStatus ;
    public AdapterMedPrescriptions.MedPrescriptionsHolder medHistoryHolder ;
    public String TAG="MEDICATION//";

    FirebaseDatabase db = FirebaseDatabase.getInstance();   // getting real time database
    public DatabaseReference database = db.getReference();

    public interface OnItemClickListener{
        void onItemClick(int position) ;
    }

    public void setOnItemClickListener(AdapterMedPrescriptions.OnItemClickListener listener){
        tvListener = listener ;
    }

    public AdapterMedPrescriptions(Context tvContext, ArrayList<ClassMedication> tvData, String studentId, String status, String userType) {
        this.tvContext = tvContext;
        this.tvData = tvData;
        this.studentId = studentId ;
        this.prescriptionStatus = status ;
        this.userType = userType ;
    }

    @NonNull
    @Override
    public MedPrescriptionsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType ) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycle_prescriptions, parent, false) ;
        MedPrescriptionsHolder myViewHolder = new MedPrescriptionsHolder(v, tvListener) ;
        this.medHistoryHolder = myViewHolder ;
        return myViewHolder ;
    }

    @Override
    public void onBindViewHolder(@NonNull MedPrescriptionsHolder holder, int position) {
        ClassMedication medHistory = tvData.get(position) ;

            holder.txt_medName.setText(medHistory.getMedicine());
            holder.txt_medPurpose.setText(medHistory.getPurpose());
            holder.txt_medDosage.setText(medHistory.getAmount());
            holder.txt_medInterval.setText(medHistory.getInterval());
            holder.txt_medStart.setText(medHistory.getStartMed());
            holder.txt_medEnd.setText(medHistory.getEndMed());

            ArrayAdapter<CharSequence> spinAdapter = ArrayAdapter.createFromResource(tvContext, R.array.medication_status, R.layout.spinner_med_selected) ;
            spinAdapter.setDropDownViewResource(R.layout.spinner_med_down);
            holder.spinner_medStatus.setAdapter(spinAdapter);
            holder.spinner_medStatus.setSelection(getStatus(holder.spinner_medStatus, medHistory.getStatus()));
            holder.spinner_medStatus.setEnabled(false);

            if(medHistory.getStatus().equals("From clinic")){
                holder.img_editMH.setVisibility(View.GONE);
                holder.img_saveMH.setVisibility(View.GONE);

            }
            if(userType.equals("Student") || userType.equals("Clinician") ){
                holder.img_editMH.setVisibility(View.INVISIBLE);
                holder.txt_medStart.setClickable(false);
                holder.txt_medEnd.setClickable(false);
            }
//        else
//            dateDatePicker(holder);

            holder.img_editMH.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    editMedication(holder);
                }
            });

            holder.img_saveMH.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    saveMedication(holder, medHistory);
                }
            });

    }


    // This function is used in setting the status of the medication
    public int getStatus(@NotNull Spinner spinner, String status){
        for (int i=0;i<spinner.getCount();i++){
            if (spinner.getItemAtPosition(i).toString().equalsIgnoreCase(status)){
                return i;
            }
        }
        return 0;
    }

    //This function is used to activate the edittable fields of the medication history
    public void editMedication(AdapterMedPrescriptions.MedPrescriptionsHolder holder){
        holder.img_editMH.setVisibility(View.GONE);
        holder.img_saveMH.setVisibility(View.VISIBLE);

        holder.txt_medName.setClickable(true) ;
        holder.txt_medName.setInputType(InputType.TYPE_CLASS_TEXT);
        holder.txt_medName.setBackgroundColor(Color.parseColor("#e4e4e4"));

        holder.txt_medPurpose.setClickable(true) ;
        holder.txt_medPurpose.setInputType(InputType.TYPE_CLASS_TEXT);
        holder.txt_medPurpose.setBackgroundColor(Color.parseColor("#e4e4e4"));

        holder.txt_medDosage.setClickable(true) ;
        holder.txt_medDosage.setInputType(InputType.TYPE_CLASS_TEXT);
        holder.txt_medDosage.setBackgroundColor(Color.parseColor("#e4e4e4"));

        holder.txt_medInterval.setClickable(true) ;
        holder.txt_medInterval.setInputType(InputType.TYPE_CLASS_TEXT);
        holder.txt_medInterval.setBackgroundColor(Color.parseColor("#e4e4e4"));

        holder.txt_medStart.setClickable(true) ;
        holder.txt_medStart.setInputType(InputType.TYPE_CLASS_TEXT);
        holder.txt_medStart.setBackgroundColor(Color.parseColor("#e4e4e4"));

        holder.txt_medEnd.setClickable(true) ;
        holder.txt_medEnd.setInputType(InputType.TYPE_CLASS_TEXT);
        holder.txt_medEnd.setBackgroundColor(Color.parseColor("#e4e4e4"));

        holder.spinner_medStatus.setEnabled(true);
    }

    //This function is used to activate the edittable fields of the medication history
    public void saveMedication(AdapterMedPrescriptions.MedPrescriptionsHolder holder, ClassMedication medHistory){
        holder.img_editMH.setVisibility(View.VISIBLE);
        holder.img_saveMH.setVisibility(View.GONE);

        holder.txt_medName.setClickable(false) ;
        holder.txt_medName.setInputType(InputType.TYPE_NULL);
        holder.txt_medName.setBackgroundColor(Color.parseColor("#F2EFFD"));

        holder.txt_medPurpose.setClickable(false) ;
        holder.txt_medPurpose.setInputType(InputType.TYPE_NULL);
        holder.txt_medPurpose.setBackgroundColor(Color.parseColor("#F2EFFD"));

        holder.txt_medDosage.setClickable(false) ;
        holder.txt_medDosage.setInputType(InputType.TYPE_NULL);
        holder.txt_medDosage.setBackgroundColor(Color.parseColor("#F2EFFD"));

        holder.txt_medInterval.setClickable(false) ;
        holder.txt_medInterval.setInputType(InputType.TYPE_NULL);
        holder.txt_medInterval.setBackgroundColor(Color.parseColor("#F2EFFD"));

        holder.txt_medStart.setClickable(false) ;
        holder.txt_medStart.setInputType(InputType.TYPE_NULL);
        holder.txt_medStart.setBackgroundColor(Color.parseColor("#F2EFFD"));

        holder.txt_medEnd.setClickable(true) ;
        holder.txt_medEnd.setInputType(InputType.TYPE_NULL);
        holder.txt_medEnd.setBackgroundColor(Color.parseColor("#F2EFFD"));

        holder.spinner_medStatus.setEnabled(false);

        //Saving to firebase **refer to AdapterStudentInfo line 199 for reference
        //change 121206 to idNum
        HashMap medHistoryValues = new HashMap();
        //dosageAmount, interval, name, purpose, status
        medHistoryValues.put("/studentHealthHistory/" + studentId + "/prescriptionHistory/" + medHistory.getKey() + "/amount", holder.txt_medDosage.getText().toString());
        medHistoryValues.put("/studentHealthHistory/" + studentId + "/prescriptionHistory/" + medHistory.getKey() + "/endMed", holder.txt_medEnd.getText().toString());
        medHistoryValues.put("/studentHealthHistory/" + studentId + "/prescriptionHistory/" + medHistory.getKey() + "/interval", holder.txt_medInterval.getText().toString());
        medHistoryValues.put("/studentHealthHistory/" + studentId + "/prescriptionHistory/" + medHistory.getKey() + "/medicine", holder.txt_medName.getText().toString());
        medHistoryValues.put("/studentHealthHistory/" + studentId + "/prescriptionHistory/" + medHistory.getKey() + "/purpose", holder.txt_medPurpose.getText().toString());
        medHistoryValues.put("/studentHealthHistory/" + studentId + "/prescriptionHistory/" + medHistory.getKey() + "/startMed", holder.txt_medStart.getText().toString());
        medHistoryValues.put("/studentHealthHistory/" + studentId + "/prescriptionHistory/" + medHistory.getKey() + "/status", holder.spinner_medStatus.getSelectedItem().toString());

        database.updateChildren(medHistoryValues).addOnSuccessListener((OnSuccessListener) (aVoid) -> {
            Toast.makeText(tvContext, "Data successfully updated!", Toast.LENGTH_SHORT).show();
        }).addOnFailureListener((error) -> {
            Toast.makeText(tvContext, "Data was not updated!", Toast.LENGTH_SHORT).show();
        });

    }

    public void setUserType(String userType){
        this.userType = userType ;
    }


    @Override
    public int getItemCount() {
        return tvData.size();
    }

    public static class MedPrescriptionsHolder extends RecyclerView.ViewHolder{

        EditText txt_medName, txt_medPurpose, txt_medDosage, txt_medInterval;
        TextView txt_medStart, txt_medEnd ;
        Spinner spinner_medStatus ;
        ImageView img_editMH, img_saveMH;

        public MedPrescriptionsHolder(@NonNull View itemView, AdapterMedPrescriptions.OnItemClickListener listener){
            super(itemView);

            txt_medName = itemView.findViewById(R.id.txt_medName) ;
            txt_medPurpose = itemView.findViewById(R.id.txt_medPurpose) ;
            txt_medDosage = itemView.findViewById(R.id.txt_medDosage) ;
            txt_medInterval = itemView.findViewById(R.id.txt_medInterval) ;
            txt_medStart = itemView.findViewById(R.id.txt_medStart) ;
            txt_medEnd = itemView.findViewById(R.id.txt_medEnd) ;
            spinner_medStatus = itemView.findViewById(R.id.spinner_medStatus) ;
            img_editMH = itemView.findViewById(R.id.img_editMH) ;
            img_saveMH = itemView.findViewById(R.id.img_saveMH) ;

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
