package com.baseballstandings.logify;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Scott on 1/21/2016.
 */
public class Division implements Parcelable {

    private String name;
    private List<Team> teams;

    public Division(){
        teams = new ArrayList<>();
    }

    public List<Team> getTeams() {
        return teams;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    protected Division(Parcel in) {
        name = in.readString();
        if (in.readByte() == 0x01) {
            teams = new ArrayList<Team>();
            in.readList(teams, Team.class.getClassLoader());
        } else {
            teams = null;
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        if (teams == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(teams);
        }
    }

    @SuppressWarnings("unused")
    public static final Creator<Division> CREATOR = new Creator<Division>() {
        @Override
        public Division createFromParcel(Parcel in) {
            return new Division(in);
        }

        @Override
        public Division[] newArray(int size) {
            return new Division[size];
        }
    };
}