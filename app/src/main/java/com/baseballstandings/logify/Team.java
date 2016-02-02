package com.baseballstandings.logify;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Scott on 1/21/2016.
 */
public class Team implements Parcelable {

    private String name;
    private String wins;
    private String losses;
    private String win_pct;
    private String game_back;
    private String rank;

    public Team(){

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    protected Team(Parcel in) {
        name = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
    }

    @SuppressWarnings("unused")
    public static final Creator<Team> CREATOR = new Creator<Team>() {
        @Override
        public Team createFromParcel(Parcel in) {
            return new Team(in);
        }

        @Override
        public Team[] newArray(int size) {
            return new Team[size];
        }
    };

    public String getWins() {
        return wins;
    }

    public void setWins(String wins) {
        this.wins = wins;
    }

    public String getGame_back() {
        return game_back;
    }

    public void setGame_back(String game_back) {
        this.game_back = game_back;
    }

    public String getLosses() {
        return losses;
    }

    public void setLosses(String losses) {
        this.losses = losses;
    }

    public String getWin_pct() {
        return win_pct;
    }

    public void setWin_pct(String win_pct) {
        this.win_pct = win_pct;
    }

    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }
}