package com.capstone.capstone_group.isklinika;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

public class Class_ParentInfo implements Parcelable {

    private List<String> childrenIds  = new ArrayList<>() ;
    private String email;

    public Class_ParentInfo(){

    }

    protected Class_ParentInfo(Parcel in) {
        childrenIds = in.createStringArrayList();
        email = in.readString() ;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeStringList(childrenIds);
        dest.writeString(email);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Class_ParentInfo> CREATOR = new Creator<Class_ParentInfo>() {
        @Override
        public Class_ParentInfo createFromParcel(Parcel in) {
            return new Class_ParentInfo(in);
        }

        @Override
        public Class_ParentInfo[] newArray(int size) {
            return new Class_ParentInfo[size];
        }
    };

    public List<String> getChildrenIds() {
        return childrenIds;
    }

    public Integer getChildrenSize(){
        return childrenIds.size() ;
    }

    public String getIdNumber(int i){
        return childrenIds.get(i) ;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setChildrenIds(List<String> childrenIds) {
        this.childrenIds = childrenIds;
    }

    public  void addChild(String childId){
        childrenIds.add(childId) ;
    }
}
