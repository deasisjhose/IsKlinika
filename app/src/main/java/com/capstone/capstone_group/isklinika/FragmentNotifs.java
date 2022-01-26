package com.capstone.capstone_group.isklinika;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class FragmentNotifs extends Fragment implements View.OnClickListener {

    FirebaseDatabase db = FirebaseDatabase.getInstance();   // getting real time database
    public DatabaseReference database = db.getReference();
    public DatabaseReference databaseReference= db.getReference("notifications");

    private LinearLayout layout_notifAll, layout_notifCV, layout_notifMedIntake, layout_notifReferral ;
    private RecyclerView recycler_notif ;
    private ClassParentInfo parentInfo ;
    private ActivityLanding activity_landing ;
    private int clickedNotif = 10 ; //10 == announcements ; 20 == clinic visits ; 30 == med intakes ; 40 == referrals

    public FragmentNotifs() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_notifs, container, false);
        this.activity_landing = (ActivityLanding) getActivity() ;

        this.layout_notifAll = view.findViewById(R.id.layout_notifAll) ;
        this.layout_notifCV = view.findViewById(R.id.layout_notifCV) ;
        this.layout_notifMedIntake = view.findViewById(R.id.layout_notifMedIntake) ;
        this.layout_notifReferral = view.findViewById(R.id.layout_notifReferral) ;
        this.recycler_notif = view.findViewById(R.id.recycler_notif) ;

        layout_notifAll.setOnClickListener(this);
        layout_notifCV.setOnClickListener(this);
        layout_notifMedIntake.setOnClickListener(this);
        layout_notifReferral.setOnClickListener(this);


        return view ;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);

        parentInfo = activity_landing.getParentInfo() ;

    }

    @Override
    public void onClick(View view) {

        if(view.getId() == R.id.layout_notifAll){
            clickedNotif = 10 ;
            retrieveNotif(clickedNotif) ;
        }else if(view.getId() == R.id.layout_notifCV){
            clickedNotif = 20 ;
            retrieveNotif(clickedNotif) ;
        }else if(view.getId() == R.id.layout_notifMedIntake){
            clickedNotif = 30 ;
            retrieveNotif(clickedNotif) ;
        }else if(view.getId() == R.id.layout_notifReferral){
            clickedNotif = 40 ;
            retrieveNotif(clickedNotif) ;
        }
    }

    public void retrieveNotif(int clicked){
        ArrayList<ClassNotif> notifArrayList = new ArrayList<>() ;

        notifArrayList.clear();
        String clickedPath;
        switch (clicked) {
            default:
            case 10:
                clickedPath = "announcements" ;
                break;
            case 20:
                clickedPath = "visits" ;
                break;
            case 30:
                clickedPath = "intake" ;
                break;
            case 40:
                clickedPath = "referrals" ;
                break;

        }
        database.child("notifications").child(parentInfo.getKey()).child(clickedPath).orderByChild("date").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ClassNotif notif = new ClassNotif();

                    for (DataSnapshot postSnapshot: snapshot.getChildren()){
                        notif = postSnapshot.getValue(ClassNotif.class) ;
                        notifArrayList.add(notif) ;
                    }

                dataInNotif(notifArrayList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        }) ;
    }

    public void dataInNotif(ArrayList<ClassNotif> notifArrayList){
        LinearLayoutManager layoutManager = new LinearLayoutManager(getView().getContext(), LinearLayoutManager.VERTICAL, false);
        AdapterNotification adapterNotification = new AdapterNotification(getView().getContext(), notifArrayList, clickedNotif) ;
        recycler_notif.setLayoutManager(layoutManager);
        recycler_notif.setAdapter(adapterNotification);
    }
}