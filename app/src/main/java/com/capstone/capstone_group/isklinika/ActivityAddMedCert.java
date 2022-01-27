package com.capstone.capstone_group.isklinika;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Typeface;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.button.MaterialButtonToggleGroup;
import com.google.android.material.card.MaterialCardView;

import java.io.IOException;
import java.util.ArrayList;

import static android.content.Intent.ACTION_PICK;

public class ActivityAddMedCert extends AppCompatActivity implements View.OnClickListener{

    private ClassStudentInfo studentInfo ;
    private String userType ;
    private Intent intent ;

    private MaterialButtonToggleGroup mbtg_medCertTab ;
    private LinearLayout layout_addMedCert, layout_medCerts ;

    //Layout addMedCert
    private MaterialCardView mcard_uploadCamera, mcard_uploadFile ;
    private MaterialButton mbtn_addMedCertCancel, mbtn_addMedCertAdd ;
    private Spinner spinner_childMedCert ;
    private ImageView img_newUploadMC ;

    private ActivityResultLauncher<Intent> resultLauncher ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_med_cert);

        intent = getIntent() ;
        this.studentInfo = intent.getParcelableExtra("studentChild") ;
        this.userType = intent.getStringExtra("userType") ;
        buildViews();

        resultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {
                Intent intent = result.getData() ;

                //check condition
                if(intent != null){
                    try {
                        Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), intent.getData()) ;
                        //set bitmap on imgview
                        img_newUploadMC.setVisibility(View.VISIBLE);
                        mbtn_addMedCertAdd.setVisibility(View.VISIBLE);
                        mbtn_addMedCertCancel.setVisibility(View.VISIBLE);
                        img_newUploadMC.setImageBitmap(bitmap );
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

        this.mcard_uploadFile = findViewById(R.id.mcard_uploadFile) ;
        this.mbtn_addMedCertCancel = findViewById(R.id.mbtn_addMedCertCancel) ;
        this.mbtn_addMedCertAdd = findViewById(R.id.mbtn_addMedCertAdd) ;

        this.img_newUploadMC = findViewById(R.id.img_newUploadMC) ;

        mcard_uploadFile.setOnClickListener(this);
        mbtn_addMedCertCancel.setOnClickListener(this);
        mbtn_addMedCertAdd.setOnClickListener(this);

        if(!userType.equals("Parent")){
            MaterialButton mbtn_addMedCert = findViewById(R.id.mbtn_addMedCert);
            mbtn_addMedCert.setVisibility(View.GONE);
        }

        mbtg_medCertTab.addOnButtonCheckedListener(new MaterialButtonToggleGroup.OnButtonCheckedListener() {
            @Override
            public void onButtonChecked(MaterialButtonToggleGroup group, int checkedId, boolean isChecked) {
                if(isChecked){
                    if(checkedId == R.id.mbtn_medCerts){
                        MaterialButton mbtn_medCerts = findViewById(R.id.mbtn_medCerts);
                        mbtn_medCerts.setTextColor(Color.parseColor("#FFFFFFFF"));
                        layout_medCerts.setVisibility(View.VISIBLE);
                        layout_addMedCert.setVisibility(View.GONE);
                    }else {

                        MaterialButton mbtn_addMedCert = findViewById(R.id.mbtn_addMedCert);
                        mbtn_addMedCert.setTextColor(Color.parseColor("#FFFFFFFF"));
                        layout_medCerts.setVisibility(View.GONE);
                        layout_addMedCert.setVisibility(View.VISIBLE);

                    }
                }else{
                    MaterialButton buttonCheck = findViewById(checkedId);
                    buttonCheck.setTextColor(Color.parseColor("#252525"));
                }
            }
        });
    }


    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.mcard_uploadFile){
            Intent intent = new Intent(ACTION_PICK) ;
            intent.setType("image/*") ;
//            intent.setAction()
            resultLauncher.launch(intent);
        }else if(view.getId() == R.id.mbtn_addMedCertCancel){
            img_newUploadMC.setVisibility(View.GONE);
            mbtn_addMedCertAdd.setVisibility(View.GONE);
            mbtn_addMedCertCancel.setVisibility(View.GONE);
        }
    }
}