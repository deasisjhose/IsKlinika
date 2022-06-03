package com.capstone.capstone_group.isklinika;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ActivityLandingTeacher extends AppCompatActivity implements View.OnClickListener{

    FirebaseDatabase db = FirebaseDatabase.getInstance();   // getting real time database
    public DatabaseReference database = db.getReference();
    public DatabaseReference databaseReference= db.getReference("notifications");
    private final String TAG = "TEACHER//" ;
    private Intent intent ;

    private LinearLayout layout_notifAll, layout_notifCV, layout_notifReferral ;
    private ClassTeacher teacher ;
    private RecyclerView recycler_teacherNotif ;
    private int clickedNotif = 10 ;
    private MaterialToolbar materialToolbar ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing_teacher);

        intent = getIntent() ;
        teacher = intent.getParcelableExtra("teacherUser") ;
        Log.d(TAG, "onCreate: " + teacher.getKey());
        buildViews() ;
        retrieveNotif();
    }

    public void buildViews(){

        this.layout_notifAll = findViewById(R.id.layout_notifAll) ;
        this.layout_notifCV = findViewById(R.id.layout_notifCV) ;
        this.layout_notifReferral = findViewById(R.id.layout_notifReferral) ;
        this.recycler_teacherNotif = findViewById(R.id.recycler_teacherNotif) ;

        layout_notifAll.setOnClickListener(this);
        layout_notifCV.setOnClickListener(this);
        layout_notifReferral.setOnClickListener(this);

        this.materialToolbar = findViewById(R.id.toolbar2) ;
        materialToolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if (item.getItemId() == R.id.menu_logout)
                    finish();

                return false;
            }
        });
    }

    public void retrieveNotif(){
        ArrayList<ClassNotifTeacher> classNotifTeacherArrayList = new ArrayList<>() ;
        classNotifTeacherArrayList.clear();
        String notifChild = "medCerts" ;

        if(clickedNotif == 20){
            notifChild = "visits" ;
        }
        databaseReference.child(teacher.getKey()).child(notifChild).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Log.d(TAG, "onDataChange: notifsize = " + snapshot.getChildrenCount());
                for (DataSnapshot postSnapshot : snapshot.getChildren()){
                    ClassNotifTeacher notifTeacher = postSnapshot.getValue(ClassNotifTeacher.class) ;

                    classNotifTeacherArrayList.add(notifTeacher) ;
                }
                Log.d(TAG, "onDataChange: notifsize = " + classNotifTeacherArrayList.size());
                dataInNotifTeacher(classNotifTeacherArrayList) ;

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        }) ;
    }

    public void dataInNotifTeacher(ArrayList<ClassNotifTeacher> classNotifTeacherArrayList){
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        AdapterNotificationTeacher adapterNotification = new AdapterNotificationTeacher(getApplicationContext(), classNotifTeacherArrayList, clickedNotif) ;
        recycler_teacherNotif.setLayoutManager(layoutManager);
        recycler_teacherNotif.setAdapter(adapterNotification);
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.layout_notifAll){
            clickedNotif = 10 ;
            retrieveNotif();
        }else if(view.getId() == R.id.layout_notifCV){
            clickedNotif = 20 ;
            retrieveNotif();
        }
    }
}