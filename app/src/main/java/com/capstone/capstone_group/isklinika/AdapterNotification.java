package com.capstone.capstone_group.isklinika;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AdapterNotification extends RecyclerView.Adapter<AdapterNotification.NotificationHolder> {

    private Context tvContext;
    private ArrayList<ClassNotif> tvData;
    private OnItemClickListener tvListener ;
    private int clickedNotif ;

    public interface OnItemClickListener{
        void onItemClick(int position) ;
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        tvListener = listener ;
    }

    public AdapterNotification(Context tvContext, ArrayList<ClassNotif> tvData, int clickedNotif) {
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
        ClassNotif notif = tvData.get(position) ;

        switch (clickedNotif){
            default:
            case 10:
//                holder.layout_notifBg.getLayout
                break;
            case 20:
                holder.layout_notifBg.getBackground().setTint(Color.parseColor("#FF919A"));
                holder.image_notifIcon.setImageResource(R.drawable.first_aid_kit__1_);
                holder.image_notifIcon.getDrawable().setTintList(null);
                break;
            case 30:
                holder.layout_notifBg.getBackground().setTint(Color.parseColor("#BAA0FF"));
                holder.image_notifIcon.setImageResource(R.drawable._2pxmedication);
                holder.image_notifIcon.getDrawable().setTintList(null);
                break;
            case 40:
                holder.layout_notifBg.getBackground().setTint(Color.parseColor("#FDD980"));
                holder.image_notifIcon.setImageResource(R.drawable.bell);
                holder.image_notifIcon.getDrawable().setTintList(null);
                break;
        }

        holder.tv_notifMessage.setText(notif.getMessage());
        holder.tv_notifDate.setText(notif.getDate() + " " + notif.getTimeIn());
    }

    @Override
    public int getItemCount() {
        return tvData.size();
    }

    public static class NotificationHolder extends RecyclerView.ViewHolder{

        LinearLayout layout_notifBg ;
        ImageView image_notifIcon ;
        TextView tv_notifMessage, tv_notifDate ;

        public NotificationHolder(@NonNull View itemView, AdapterNotification.OnItemClickListener listener){
            super(itemView);

            tv_notifMessage = itemView.findViewById(R.id.tv_notifMessage) ;
            tv_notifDate = itemView.findViewById(R.id.tv_notifDate) ;
            layout_notifBg = itemView.findViewById(R.id.layout_notifBg) ;
            image_notifIcon = itemView.findViewById(R.id.image_notifIcon) ;
        }
    }

}
