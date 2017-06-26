package com.lx.testandroid.common;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created on 24/03/2017.
 *
 * @author lx
 */

public class Student implements Parcelable {

    private String name;
    private int age;
    private String email;
    private String address;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeInt(this.age);
        dest.writeString(this.email);
    }

    public Student() {
    }

    protected Student(Parcel in) {
        this.name = in.readString();
        this.age = in.readInt();
        this.email = in.readString();
    }

    public static final Parcelable.Creator<Student> CREATOR = new Parcelable.Creator<Student>() {
        @Override
        public Student createFromParcel(Parcel source) {
            return new Student(source);
        }

        @Override
        public Student[] newArray(int size) {
            return new Student[size];
        }
    };
}
