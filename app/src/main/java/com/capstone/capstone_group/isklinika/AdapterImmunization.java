package com.capstone.capstone_group.isklinika;

import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class AdapterImmunization extends RecyclerView.Adapter<AdapterImmunization.ImmunizationHolder> {

    private Context tvContext;
    private ArrayList<ClassImmuneRecord> tvData;
    private OnItemClickListener tvListener ;
    private ClassStudentInfo studentInfo ;
    private String userType ;

    FirebaseDatabase db = FirebaseDatabase.getInstance();   // getting real time database
    public DatabaseReference database = db.getReference();

    public interface OnItemClickListener{
        void onItemClick(int position) ;
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        tvListener = listener ;
    }

    public AdapterImmunization(Context tvContext, ArrayList<ClassImmuneRecord> tvData, ClassStudentInfo studentInfo, String userType) {
        this.tvContext = tvContext;
        this.tvData = tvData;
        this.studentInfo = studentInfo ;
        this.userType = userType ;
    }

    @NonNull
    @Override
    public ImmunizationHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycle_immune_date, parent, false) ;
        ImmunizationHolder myViewHolder = new ImmunizationHolder(v, tvListener) ;
        return myViewHolder ;
    }

    @Override
    public void onBindViewHolder(@NonNull ImmunizationHolder holder, int position) {
        ClassImmuneRecord immunization = tvData.get(position) ;

        holder.tv_recycleImmuneDate.setText(immunization.getDateGiven());
        if(!immunization.getName().equals(""))
            holder.tv_recycleBrand.setText(immunization.getName());

        if(userType.equals("Student") || userType.equals("Clinician"))
            holder.ibtn_deleteImmune.setVisibility(View.GONE);

        holder.ibtn_deleteImmune.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new MaterialAlertDialogBuilder(view.getRootView().getContext(), R.style.ThemeOverlay_App_MaterialAlertDialog_Immune)
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
                                database.child("studentHealthHistory").child(studentInfo.getIdNum()).child("immuneHistory").child(immunization.getKey()).removeValue().addOnSuccessListener((OnSuccessListener) (aVoid) -> {
                                    Toast.makeText(tvContext, "Data successfully deleted!", Toast.LENGTH_SHORT).show();
                                }).addOnFailureListener((error) -> {
                                    Toast.makeText(tvContext, "Data was not deleted!", Toast.LENGTH_SHORT).show();
                                });
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

    public static class ImmunizationHolder extends RecyclerView.ViewHolder{

        TextView tv_recycleImmuneDate, tv_recycleBrand ;
        ImageButton ibtn_deleteImmune ;

        public ImmunizationHolder(@NonNull View itemView, AdapterImmunization.OnItemClickListener listener){
            super(itemView);

            tv_recycleImmuneDate = itemView.findViewById(R.id.tv_recycleImmuneDate) ;
            tv_recycleBrand = itemView.findViewById(R.id.tv_recycleBrand) ;
            ibtn_deleteImmune = itemView.findViewById(R.id.ibtn_deleteImmune) ;
        }
    }

}
