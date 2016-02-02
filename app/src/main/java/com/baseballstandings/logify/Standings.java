package com.baseballstandings.logify;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Scott on 1/22/2016.
 */
public class Standings implements Parcelable {

    private List<League> leagues;

    public Standings(){
        leagues = new ArrayList<>();
    }

    public List<League> getLeagues() {
        return leagues;
    }

    public void setLeagues(List<League> leagues) {
        this.leagues = leagues;
    }



    protected Standings(Parcel in) {
        if (in.readByte() == 0x01) {
            leagues = new ArrayList<League>();
            in.readList(leagues, League.class.getClassLoader());
        } else {
            leagues = null;
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (leagues == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(leagues);
        }
    }

    @SuppressWarnings("unused")
    public static final Creator<Standings> CREATOR = new Creator<Standings>() {
        @Override
        public Standings createFromParcel(Parcel in) {
            return new Standings(in);
        }

        @Override
        public Standings[] newArray(int size) {
            return new Standings[size];
        }
    };
}
