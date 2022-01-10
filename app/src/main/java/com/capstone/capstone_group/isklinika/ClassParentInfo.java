package com.capstone.capstone_group.isklinika;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

public class ClassParentInfo implements Parcelable {

    private List<String> childrenIds;
    private String email;
    private String key;

    public ClassParentInfo(){
        this.childrenIds  = new ArrayList<>() ;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    protected ClassParentInfo(Parcel in) {
        childrenIds = in.createStringArrayList();
        email = in.readString() ;
        key = in.readString() ;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeStringList(childrenIds);
        dest.writeString(email);
        dest.writeString(key);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ClassParentInfo> CREATOR = new Creator<ClassParentInfo>() {
        @Override
        public ClassParentInfo createFromParcel(Parcel in) {
            return new ClassParentInfo(in);
        }

        @Override
        public ClassParentInfo[] newArray(int size) {
            return new ClassParentInfo[size];
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

    @Override
    public String toString() {
        return "ClassParentInfo{" +
                "email='" + email + '\'' + "childrenSize='" + getChildrenSize() + '\'' +
                '}';
    }
}
