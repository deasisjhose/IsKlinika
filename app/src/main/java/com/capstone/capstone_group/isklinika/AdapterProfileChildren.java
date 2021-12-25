package com.capstone.capstone_group.isklinika;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.card.MaterialCardView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class AdapterProfileChildren extends RecyclerView.Adapter<AdapterProfileChildren.ChildrenHolder> {

    private Context tvContext;
    private ArrayList<ClassStudentInfo> tvData;
    private AdapterProfileChildren.OnItemClickListener tvListener ;
     //instance variable
     private List<MaterialCardView> cardViewList = new ArrayList<>();

    FirebaseDatabase db = FirebaseDatabase.getInstance();   // getting real time database
    public DatabaseReference database = db.getReference();

    public interface OnItemClickListener{
        void onItemClick(int position) ;
    }

    public void setOnItemClickListener(AdapterProfileChildren.OnItemClickListener listener){
        tvListener = listener ;
    }

    public AdapterProfileChildren(Context tvContext, ArrayList<ClassStudentInfo> tvData) {
        this.tvContext = tvContext;
        this.tvData = tvData;

    }

    @NonNull
    @Override
    public AdapterProfileChildren.ChildrenHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycle_children_top, parent, false) ;
        AdapterProfileChildren.ChildrenHolder myViewHolder = new AdapterProfileChildren.ChildrenHolder(v, tvListener) ;
        return myViewHolder ;
    }


    @Override
    public void onBindViewHolder(@NonNull AdapterProfileChildren.ChildrenHolder holder, int position) {
        holder.mcv_nameButton.setText(tvData.get(position).getFirstName());


    }

    @Override
    public int getItemCount() {
        return tvData.size();
    }

    public static class ChildrenHolder extends RecyclerView.ViewHolder{

        public MaterialButton mcv_nameButton ;


        public ChildrenHolder(@NonNull View itemView, AdapterProfileChildren.OnItemClickListener listener){
            super(itemView);

            mcv_nameButton = itemView.findViewById(R.id.mcv_nameButton) ;

            itemView.setOnClickListener(v -> {
                if(listener != null){
                    int position = getAdapterPosition();
                    if(position != RecyclerView.NO_POSITION){
                        listener.onItemClick(position);
                    }
                }
            });
        }
    }

}
