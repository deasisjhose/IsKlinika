package com.capstone.capstone_group.isklinika;

import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.util.ArrayList;

public class AdapterImmunization extends RecyclerView.Adapter<AdapterImmunization.ImmunizationHolder> {

    private Context tvContext;
    private ArrayList<ClassImmuneRecord> tvData;
    private OnItemClickListener tvListener ;

    public interface OnItemClickListener{
        void onItemClick(int position) ;
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        tvListener = listener ;
    }

    public AdapterImmunization(Context tvContext, ArrayList<ClassImmuneRecord> tvData) {
        this.tvContext = tvContext;
        this.tvData = tvData;
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
