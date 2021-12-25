package com.capstone.capstone_group.isklinika;

import android.app.Activity;
import android.view.View;

import com.google.android.material.button.MaterialButton;

public class ClassBotnav {

    public MaterialButton btn_nav_home, btn_nav_profile, btn_nav_mail, btn_nav_modules;

    public ClassBotnav(Activity activity){
        this.btn_nav_home = activity.findViewById(R.id.btn_nav_home) ;
        this.btn_nav_profile = activity.findViewById(R.id.btn_nav_profile) ;
        this.btn_nav_mail = activity.findViewById(R.id.btn_nav_mail) ;
        this.btn_nav_modules = activity.findViewById(R.id.btn_nav_modules) ;

    }
}
