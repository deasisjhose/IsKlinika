package com.capstone.capstone_group.isklinika;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.card.MaterialCardView;

import java.io.IOException;
import java.util.ArrayList;

import static android.content.Intent.ACTION_GET_CONTENT;

public class AdapterNotificationTeacher extends RecyclerView.Adapter<AdapterNotificationTeacher.NotificationHolder> {

    private Context tvContext;
    private ArrayList<ClassNotifTeacher> tvData;
    private OnItemClickListener tvListener ;
    private int clickedNotif ;

    public interface OnItemClickListener{
        void onItemClick(int position) ;
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        tvListener = listener ;
    }

    public AdapterNotificationTeacher(Context tvContext, ArrayList<ClassNotifTeacher> tvData, int clickedNotif) {
        this.tvContext = tvContext;
        this.tvData = tvData;
        this.clickedNotif = clickedNotif ;

        
    }

    @NonNull
    @Override
    public NotificationHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycle_notif_mail, parent, false) ;
        NotificationHolder myViewHolder = new NotificationHolder(v, tvListener) ;
        return myViewHolder ;
    }

    @Override
    public void onBindViewHolder(@NonNull NotificationHolder holder, int position) {
        ClassNotifTeacher notif = tvData.get(position) ;

        holder.tv_notifMessage.setText(notif.getMessage());
        holder.tv_notifDate.setText(notif.getDate());
        
        switch (clickedNotif){
            default:
            case 10:
                holder.mcard_notif.setClickable(true);
//                holder.layout_notifBg.getLayout
                
                holder.mcard_notif.setOnClickListener(view -> {
                    Intent intent = new Intent(Intent.ACTION_VIEW) ;
                    intent.setType("application/*") ;
                    intent.setData(Uri.parse(notif.getUrl())) ;
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK) ;
                    tvContext.startActivity(intent);
                });
                break;
            case 20:
                holder.layout_notifBg.getBackground().setTint(Color.parseColor("#FF919A"));
                holder.image_notifIcon.setImageResource(R.drawable.clinician_visit);
                holder.image_notifIcon.getDrawable().setTintList(null);
                break;
        }

        
    }

    @Override
    public int getItemCount() {
        return tvData.size();
    }

    public static class NotificationHolder extends RecyclerView.ViewHolder{

        MaterialCardView mcard_notif ;
        LinearLayout layout_notifBg ;
        ImageView image_notifIcon ;
        TextView tv_notifMessage, tv_notifDate ;

        public NotificationHolder(@NonNull View itemView, AdapterNotificationTeacher.OnItemClickListener listener){
            super(itemView);

            mcard_notif = itemView.findViewById(R.id.mcard_notif) ;
            tv_notifMessage = itemView.findViewById(R.id.tv_notifMessage) ;
            tv_notifDate = itemView.findViewById(R.id.tv_notifDate) ;
            layout_notifBg = itemView.findViewById(R.id.layout_notifBg) ;
            image_notifIcon = itemView.findViewById(R.id.image_notifIcon) ;

        }
    }

}
