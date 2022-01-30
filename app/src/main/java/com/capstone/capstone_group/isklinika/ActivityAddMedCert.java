package com.capstone.capstone_group.isklinika;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Typeface;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.button.MaterialButtonToggleGroup;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textview.MaterialTextView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.ListResult;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageMetadata;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import static android.content.Intent.ACTION_GET_CONTENT;
import static android.content.Intent.ACTION_PICK;

public class ActivityAddMedCert extends AppCompatActivity implements View.OnClickListener{

    FirebaseDatabase db = FirebaseDatabase.getInstance();   // getting real time database
    public DatabaseReference databaseReference = db.getReference("studentHealthHistory");
    public DatabaseReference database = db.getReference();

    private FirebaseStorage storage ;
    private StorageReference storageReference ;

    private ClassStudentInfo studentInfo ;
    private String userType ;
    private Intent intent ;
    private final  String TAG = "FILES//" ;

    private MaterialButtonToggleGroup mbtg_medCertTab ;
    private LinearLayout layout_addMedCert, layout_medCerts ;

    //MedCert and Lab
    private ArrayList<ClassFile> dateList ;
    private RecyclerView recycle_files ;
    private ListView list_files ;
    private ArrayList<ClassFile> uploadedPDF ;
    private AdapterFileUploads adapterFileUploads ;
    private String url ;


    //Layout addMedCert
    private MaterialCardView mcard_uploadCamera, mcard_uploadFile ;
    private MaterialButton mbtn_addMedCertCancel, mbtn_addMedCertAdd ;
    private Spinner spinner_childMedCert ;
    private ImageView img_newUploadMC ;
    private Uri uploadedUri ;
    private ProgressBar progress_upload ;
    private Spinner spinner_upload ;
    private MaterialTextView tv_filename ;

    private ActivityResultLauncher<Intent> resultLauncher ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_med_cert);

        storage = FirebaseStorage.getInstance() ;
        storageReference = storage.getReference() ;

        intent = getIntent() ;
        this.studentInfo = intent.getParcelableExtra("studentChild") ;
        this.userType = intent.getStringExtra("userType") ;
        buildViews();

        //launcher
        resultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {
                Intent intent = result.getData() ;

                //check condition
                if(intent != null){
                    try {
                        Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), intent.getData()) ;
                        uploadedUri = intent.getData() ;
                        //set bitmap on imgview
                        mbtn_addMedCertAdd.setVisibility(View.VISIBLE);
                        mbtn_addMedCertCancel.setVisibility(View.VISIBLE);
                        tv_filename.setVisibility(View.VISIBLE);

//                        img_newUploadMC.setVisibility(View.VISIBLE);
//                        img_newUploadMC.setImageBitmap(bitmap );
//                        String fileName = new File(uploadedUri.getPath()).getName() ;
                        String fileName = uploadedUri.getPath().substring(uploadedUri.getPath().lastIndexOf("/") + 1);
                        tv_filename.setText(fileName);

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }) ;
    }


    public void buildViews(){
        ImageButton btn_back = findViewById(R.id.btn_back);
        btn_back.setOnClickListener(view -> finish());

        this.mbtg_medCertTab = findViewById(R.id.mbtg_medCertTab) ;;
        this.layout_addMedCert = findViewById(R.id.layout_addMedCert) ;
        this.layout_medCerts = findViewById(R.id.layout_medCerts) ;

        //MEDCERT&LABS
        this.recycle_files = findViewById(R.id.recycle_files) ;
        this.list_files = findViewById(R.id.list_files) ;

        this.progress_upload = findViewById(R.id.progress_upload) ;
        this.mcard_uploadFile = findViewById(R.id.mcard_uploadFile) ;
        this.mbtn_addMedCertCancel = findViewById(R.id.mbtn_addMedCertCancel) ;
        this.mbtn_addMedCertAdd = findViewById(R.id.mbtn_addMedCertAdd) ;

        this.tv_filename = findViewById(R.id.tv_filename) ;
        this.img_newUploadMC = findViewById(R.id.img_newUploadMC) ;

        mcard_uploadFile.setOnClickListener(this);
        mbtn_addMedCertCancel.setOnClickListener(this);
        mbtn_addMedCertAdd.setOnClickListener(this);

        makeSpinnerUpload();



        mbtg_medCertTab.addOnButtonCheckedListener(new MaterialButtonToggleGroup.OnButtonCheckedListener() {
            @Override
            public void onButtonChecked(MaterialButtonToggleGroup group, int checkedId, boolean isChecked) {
                if(isChecked){
                    if(checkedId == R.id.mbtn_medCerts){
                        MaterialButton mbtn_medCerts = findViewById(R.id.mbtn_medCerts);
                        mbtn_medCerts.setTextColor(Color.parseColor("#FFFFFFFF"));
                        layout_medCerts.setVisibility(View.VISIBLE);
                        layout_addMedCert.setVisibility(View.GONE);
                        retrieveMedCert("MedCert");
                    }else if(checkedId == R.id.mbtn_labResult){
                        MaterialButton mbtn_medCerts = findViewById(R.id.mbtn_labResult);
                        mbtn_medCerts.setTextColor(Color.parseColor("#FFFFFFFF"));
                        layout_medCerts.setVisibility(View.VISIBLE);
                        layout_addMedCert.setVisibility(View.GONE);
                        retrieveMedCert("LabResult");

                    }else if(checkedId == R.id.mbtn_addMedCert){

                        MaterialButton mbtn_addMedCert = findViewById(R.id.mbtn_addMedCert);
                        mbtn_addMedCert.setTextColor(Color.parseColor("#FFFFFFFF"));
                        layout_medCerts.setVisibility(View.GONE);
                        layout_addMedCert.setVisibility(View.VISIBLE);
                        retrieveMedCert("LabResult");
                    }
                }else{
                    MaterialButton buttonCheck = findViewById(checkedId);
                    buttonCheck.setTextColor(Color.parseColor("#252525"));
                }
            }
        });

        if(!userType.equals("Parent")){
            layout_addMedCert.setVisibility(View.GONE);
            layout_medCerts.setVisibility(View.VISIBLE);
            MaterialButton mbtn_medCerts = findViewById(R.id.mbtn_medCerts);
            mbtn_medCerts.setChecked(true);
            MaterialButton mbtn_addMedCert = findViewById(R.id.mbtn_addMedCert);
            mbtn_addMedCert.setVisibility(View.GONE);
        }

        list_files.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Log.d(TAG, "onItemClick: itemclickeed");
//                ClassFile file = uploadedPDF.get(i) ;
//
//                Intent intent = new Intent(Intent.ACTION_VIEW) ;
//                intent.setType("application/*") ;
//                intent.setData(Uri.parse(file.getUrl())) ;
//                startActivity(intent);
            }
        });
    }

    public void retrieveMedCert(String tab){
        this.dateList = new ArrayList<>() ;
        dateList.clear();

        this.uploadedPDF = new ArrayList<>() ;
        uploadedPDF.clear();

        String reference ;
        if(tab.equals("MedCert")){
            reference = "/" +studentInfo.getIdNum() + "/uploads/" + "medCert/" ;
        }else{
            reference = "/" +studentInfo.getIdNum() + "/uploads/" + "labResult/";
        }

        databaseReference.child(reference).orderByChild("date").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for(DataSnapshot postSnapshot : snapshot.getChildren()){
                    ClassFile file = postSnapshot.getValue(ClassFile.class) ;

                    uploadedPDF.add(file) ;
//                    dateList.add(file) ;
                }
                Collections.reverse(uploadedPDF);
                dataInFiles(uploadedPDF);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        }) ;

    }

    public void dataInFiles(ArrayList<ClassFile> files){
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        this.adapterFileUploads = new AdapterFileUploads(getBaseContext(), files) ;
        recycle_files.setLayoutManager(layoutManager);
        recycle_files.setAdapter(adapterFileUploads);
    }
    public void makeSpinnerUpload(){
        this.spinner_upload = findViewById(R.id.spinner_upload) ;
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.upload,R.layout.spinner_child_immune) ;
        adapter.setDropDownViewResource(R.layout.spinner_immune_down);
        spinner_upload.setAdapter(adapter);
        spinner_upload.setSelection(0);


        spinner_upload.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }


    public void sendNotifToTeacher(String url, String date){

        //get teacher
        database.child("teacherUsers").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String teacherKey ;
                String reference = null;
                ClassNotifTeacher notifTeacher = new ClassNotifTeacher();
                for (DataSnapshot postSnapshot : snapshot.getChildren()){
                    if (postSnapshot.child("section").getValue().toString().equals(studentInfo.getSection())){
                        teacherKey = postSnapshot.getKey() ;
                        reference = "notifications/" + teacherKey + "/medCerts/" ;
                        String message = studentInfo.getFullName() + ", with ID number " + studentInfo.getIdNum() + " uploaded a medical certificate." ;
                        notifTeacher = new ClassNotifTeacher(date, studentInfo.getIdNum(),  message, url) ;
                    }
                }
                
                if(!reference.equals(null)){
                    database.child(reference).push().setValue(notifTeacher) ;
                    Toast.makeText(getApplicationContext(), "Class adviser notified", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        }) ;
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.mcard_uploadFile){
            Intent intent = new Intent(ACTION_GET_CONTENT) ;
            intent.setType("*/*") ;
//            intent.setAction()
            resultLauncher.launch(intent);
        }else if(view.getId() == R.id.mbtn_addMedCertCancel){
            img_newUploadMC.setVisibility(View.GONE);
            mbtn_addMedCertAdd.setVisibility(View.GONE);
            mbtn_addMedCertCancel.setVisibility(View.GONE);
            tv_filename.setVisibility(View.GONE);
        }else if(view.getId() == R.id.mbtn_addMedCertAdd){
            mcard_uploadFile.setVisibility(View.GONE);

            final  String randomKey = UUID.randomUUID().toString() ;
            StorageReference medCertReference ;

            if(spinner_upload.getSelectedItem().toString().equals("Medical Certificate")){
                medCertReference = storageReference.child("uploads/" + studentInfo.getIdNum() + "/medCertificate/" + randomKey) ;
            }else{
                medCertReference = storageReference.child("uploads/" + studentInfo.getIdNum() + "/labResults/" + randomKey) ;
            }

            progress_upload.setVisibility(View.VISIBLE);
            medCertReference.putFile(uploadedUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    progress_upload.setProgress(0);
                    mcard_uploadFile.setVisibility(View.VISIBLE);
                    progress_upload.setVisibility(View.GONE);
                    mbtn_addMedCertAdd.setVisibility(View.GONE);
                    mbtn_addMedCertCancel.setVisibility(View.GONE);
                    tv_filename.setVisibility(View.GONE);
                    Toast.makeText(getApplicationContext(),"File uploaded", Toast.LENGTH_SHORT).show();
                }
            }).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                    Log.d(TAG, "onComplete: ");
                    Calendar cal = Calendar.getInstance();
                    int day = cal.get(Calendar.DAY_OF_MONTH);
                    int currMonth=cal.get(Calendar.MONTH);
                    int currYear=cal.get(Calendar.YEAR);

                    String dayy = Integer.toString(day) ;
                    String month = Integer.toString(currMonth) ;
                    if(dayy.length() == 1){
                        dayy = "0" + day ;
                    }
                    if(month.length() == 1){
                        month = "0" + (currMonth+1) ;
                    }

                    String date = currYear + "-" + month + "-" + dayy ;

                    medCertReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            url = uri.toString() ;
                            ClassFile file = new ClassFile( randomKey, url, date) ;
                            String reference ;
                            if(spinner_upload.getSelectedItem().toString().equals("Medical Certificate")){
                                reference = "/" +studentInfo.getIdNum() + "/uploads/" + "medCert/" ;
                            }else{
                                reference = "/" +studentInfo.getIdNum() + "/uploads/" + "labResult/";
                            }

                            databaseReference.child(reference).push().setValue(file).addOnSuccessListener((OnSuccessListener) (aVoid) -> {
                                Toast.makeText(getApplicationContext(), "Data successfully added to database!", Toast.LENGTH_SHORT).show();
                            }).addOnFailureListener((error) -> {
                                Toast.makeText(getApplicationContext(), "Data was not added to database!", Toast.LENGTH_SHORT).show();
                            });
                            if(spinner_upload.getSelectedItem().toString().equals("Medical Certificate")){
                                sendNotifToTeacher(url, date) ;
                            }


                        }
                    });


                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    mcard_uploadFile.setVisibility(View.VISIBLE);
                    progress_upload.setVisibility(View.GONE);
                    Toast.makeText(getApplicationContext(),"Failed to upload", Toast.LENGTH_SHORT).show(); ;
                }
            }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                    double progress = (100*snapshot.getBytesTransferred() / snapshot.getTotalByteCount()) ;
                    progress_upload.setProgress((int) progress);
                }
            }) ;
        }
    }

}