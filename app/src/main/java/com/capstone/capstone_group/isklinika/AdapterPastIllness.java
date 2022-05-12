package com.capstone.capstone_group.isklinika;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;

public class AdapterPastIllness extends  RecyclerView.Adapter<AdapterPastIllness.PastIllnessHolder> {


    private Context tvContext;
    private ArrayList<ClassPastIllness> tvData;
    private AdapterPastIllness.OnItemClickListener tvListener ;
    private String studentId ;
    private String userType ;
    private String prescriptionStatus ;
    public AdapterPastIllness.PastIllnessHolder pastIllnessHolder ;
    public String TAG="MEDICATION//";


    FirebaseDatabase db = FirebaseDatabase.getInstance();   // getting real time database
    public DatabaseReference database = db.getReference();

    public interface OnItemClickListener{
        void onItemClick(int position) ;
    }

    public void setOnItemClickListener(AdapterPastIllness.OnItemClickListener listener){
        tvListener = listener ;
    }

    public void setUserType(String userType){
        this.userType = userType ;
    }


    public AdapterPastIllness(Context tvContext, ArrayList<ClassPastIllness> tvData, String studentId) {
        this.tvContext = tvContext;
        this.tvData = tvData;
        this.studentId = studentId ;
    }

    @NonNull
    @Override
    public AdapterPastIllness.PastIllnessHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType ) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.expand_past_illness, parent, false) ;
        AdapterPastIllness.PastIllnessHolder myViewHolder = new AdapterPastIllness.PastIllnessHolder(v, tvListener) ;
        this.pastIllnessHolder = myViewHolder ;
        return myViewHolder ;
    }

    @Override
    public void onBindViewHolder(@NonNull PastIllnessHolder holder, int position) {
        ClassPastIllness pastIllness = tvData.get(position) ;


        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(tvContext, R.array.illness,R.layout.spinner_child_illness_status) ;
        adapter.setDropDownViewResource(R.layout.spinner_immune_down);
        holder.tv_pastStatus.setAdapter(adapter);
        holder.tv_pastStatus.setEnabled(false);

        if(pastIllness.getStatus().equals("Ongoing"))
            holder.tv_pastStatus.setSelection(0);
        else if(pastIllness.getStatus().equalsIgnoreCase("Recovered"))
            holder.tv_pastStatus.setSelection(1);
        else if (pastIllness.getStatus().equals("Chronic"))
            holder.tv_pastStatus.setSelection(2);
//        for (int i = 0; i < ; i++) {
//
//        }

        holder.tv_pastStatus.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //sort function
                prescriptionStatus = holder.tv_pastStatus.getSelectedItem().toString() ;
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        holder.tv_pastDisease.setText(pastIllness.getDisease());
        holder.tv_pastStart.setText(pastIllness.getStartDate());
        holder.tv_pastEnd.setText(pastIllness.getEndDate());
        holder.tv_pastTreatment.setText(pastIllness.getTreatment());
        holder.tv_pastNotes.setText(pastIllness.getNotes());

        if(userType.equals("Parent")){
            holder.ibtn_editPast.setVisibility(View.VISIBLE);

        }

        holder.ibtn_editPast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.ibtn_editPast.setVisibility(View.GONE);
                holder.ibtn_savePast.setVisibility(View.VISIBLE);
                holder.mbtn_deleteIllness.setVisibility(View.VISIBLE);


                holder.tv_pastStatus.setClickable(true);
                holder.tv_pastStatus.setEnabled(true);
                holder.tv_pastStart.setClickable(true);
                holder.tv_pastStart.setEnabled(true);
                holder.tv_pastEnd.setClickable(true);
                holder.tv_pastEnd.setEnabled(true);
                holder.tv_pastTreatment.setClickable(true);
                holder.tv_pastTreatment.setEnabled(true);
                holder.tv_pastNotes.setClickable(true);
                holder.tv_pastNotes.setEnabled(true);
            }
        }) ;


        holder.ibtn_savePast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.ibtn_editPast.setVisibility(View.VISIBLE);
                holder.ibtn_savePast.setVisibility(View.GONE);
                holder.mbtn_deleteIllness.setVisibility(View.GONE);

                holder.tv_pastStatus.setClickable(false);
                holder.tv_pastStatus.setEnabled(false);
                holder.tv_pastStart.setClickable(false);
                holder.tv_pastStart.setEnabled(false);
                holder.tv_pastEnd.setClickable(false);
                holder.tv_pastEnd.setEnabled(false);
                holder.tv_pastTreatment.setClickable(false);
                holder.tv_pastTreatment.setEnabled(false);
                holder.tv_pastNotes.setClickable(false);
                holder.tv_pastNotes.setEnabled(false);


                HashMap pastIllnessValue = new HashMap();
                //dosageAmount, interval, name, purpose, status
                pastIllnessValue.put("/studentHealthHistory/" + studentId + "/pastIllness/" + pastIllness.getKey() + "/disease", holder.tv_pastDisease.getText().toString());
                pastIllnessValue.put("/studentHealthHistory/" + studentId + "/pastIllness/" + pastIllness.getKey() + "/endDate", holder.tv_pastEnd.getText().toString());
                pastIllnessValue.put("/studentHealthHistory/" + studentId + "/pastIllness/" + pastIllness.getKey() + "/notes", holder.tv_pastNotes.getText().toString());
                pastIllnessValue.put("/studentHealthHistory/" + studentId + "/pastIllness/" + pastIllness.getKey() + "/startDate", holder.tv_pastStart.getText().toString());
                pastIllnessValue.put("/studentHealthHistory/" + studentId + "/pastIllness/" + pastIllness.getKey() + "/status", holder.tv_pastStatus.getSelectedItem().toString());
                pastIllnessValue.put("/studentHealthHistory/" + studentId + "/pastIllness/" + pastIllness.getKey() + "/treatment", holder.tv_pastTreatment.getText().toString());
//                pastIllnessValue.put("/studentHealthHistory/" + studentId + "/prescriptionHistory/" + pastIllness.getKey() + "/status", holder.spinner_medStatus.getSelectedItem().toString());

                database.updateChildren(pastIllnessValue).addOnSuccessListener((OnSuccessListener) (aVoid) -> {
                    Toast.makeText(tvContext, "Data successfully updated!", Toast.LENGTH_SHORT).show();
                }).addOnFailureListener((error) -> {
                    Toast.makeText(tvContext, "Data was not updated!", Toast.LENGTH_SHORT).show();
                });
            }
        }) ;

        holder.mbtn_deleteIllness.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                new MaterialAlertDialogBuilder(view.getRootView().getContext(), R.style.ThemeOverlay_App_MaterialAlertDialog_Illness)
                        .setTitle(R.string.title)
                        .setMessage(R.string.supporting_text)
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener(){

                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        })
                        .setPositiveButton("Remove", new DialogInterface.OnClickListener(){

                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        })

                .show() ;


            }
        });
    }



    @Override
    public int getItemCount() {
        return tvData.size();
    }

    public static class PastIllnessHolder extends RecyclerView.ViewHolder{

        TextView tv_expandDiagnosis, tv_expandVisitDate ;
        Spinner tv_pastStatus ;
        TextInputEditText tv_pastDisease, tv_pastStart, tv_pastEnd, tv_pastTreatment, tv_pastNotes ;
        ImageButton ibtn_editPast, ibtn_savePast ;
        MaterialCardView mbtn_deleteIllness ;

        public PastIllnessHolder(@NonNull View itemView, AdapterPastIllness.OnItemClickListener listener){
            super(itemView);

            this.tv_pastDisease = itemView.findViewById(R.id.tv_pastDisease) ;
            this.tv_pastStatus = itemView.findViewById(R.id.tv_pastStatus) ;
            this.tv_pastStart = itemView.findViewById(R.id.tv_pastStart) ;
            this.tv_pastEnd = itemView.findViewById(R.id.tv_pastEnd) ;
            this.tv_pastTreatment = itemView.findViewById(R.id.tv_pastTreatment) ;
            this.tv_pastNotes = itemView.findViewById(R.id.tv_pastNotes) ;

            this.ibtn_editPast = itemView.findViewById(R.id.ibtn_editPast) ;
            this.ibtn_savePast = itemView.findViewById(R.id.ibtn_savePast) ;
            this.mbtn_deleteIllness = itemView.findViewById(R.id.mbtn_deleteIllness) ;
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
