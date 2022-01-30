package com.capstone.capstone_group.isklinika;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AdapterFileUploads extends RecyclerView.Adapter<AdapterFileUploads.UploadHolder> {

    private Context tvContext;
    private ArrayList<ClassFile> tvData;
    private OnItemClickListener tvListener ;

    public interface OnItemClickListener{
        void onItemClick(int position) ;
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        tvListener = listener ;
    }

    public AdapterFileUploads(Context tvContext, ArrayList<ClassFile> tvData) {
        this.tvContext = tvContext;
        this.tvData = tvData;
    }

    @NonNull
    @Override
    public UploadHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycle_upload_file, parent, false) ;
        UploadHolder myViewHolder = new UploadHolder(v, tvListener) ;
        return myViewHolder ;
    }

    @Override
    public void onBindViewHolder(@NonNull UploadHolder holder, int position) {
       ClassFile file = tvData.get(position) ;

       holder.tv_recycleFileDate.setText(file.getDate());

       holder.tv_recycleFileDate.setOnClickListener(view -> {
           Intent intent = new Intent(Intent.ACTION_VIEW) ;
            intent.setType("application/*") ;
            intent.setData(Uri.parse(file.getUrl())) ;
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK) ;
            tvContext.startActivity(intent);
       });

    }

    @Override
    public int getItemCount() {
        return tvData.size();
    }

    public static class UploadHolder extends RecyclerView.ViewHolder{

        TextView tv_recycleFileDate;

        public UploadHolder(@NonNull View itemView, AdapterFileUploads.OnItemClickListener listener){
            super(itemView);

            tv_recycleFileDate = itemView.findViewById(R.id.tv_recycleFileDate) ;
        }
    }

}
