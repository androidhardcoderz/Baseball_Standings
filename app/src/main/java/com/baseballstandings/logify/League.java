package com.baseballstandings.logify;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Scott on 1/21/2016.
 */
public class League implements Parcelable {

    private String name;
    private List<Division> division;

    public League(){
        division = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Division> getDivision() {
        return division;
    }

    public void setDivision(List<Division> division) {
        this.division = division;
    }

    protected League(Parcel in) {
        name = in.readString();
        if (in.readByte() == 0x01) {
            division = new ArrayList<Division>();
            in.readList(division, Division.class.getClassLoader());
        } else {
            division = null;
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        if (division == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(division);
        }
    }

    @SuppressWarnings("unused")
    public static final Creator<League> CREATOR = new Creator<League>() {
        @Override
        public League createFromParcel(Parcel in) {
            return new League(in);
        }

        @Override
        public League[] newArray(int size) {
            return new League[size];
        }
    };
}