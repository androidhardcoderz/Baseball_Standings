package com.baseballstandings.logify;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

/**
 * Created by Scott on 1/22/2016.
 */
public class Game implements Parcelable {

    private String id;
    private String day_night;
    private String scheduled;
    private String home_team;
    private String home_market;
    private String away_team;
    private String away_market;
    private String date;
    private String time;
    private Date game_date;

    public Game(){

    }

    public Date getGame_date() {
        return game_date;
    }

    public void setGame_date(Date game_date) {
        this.game_date = game_date;
    }

    public String getAway_market() {
        return away_market;
    }

    public void setAway_market(String away_market) {
        this.away_market = away_market;
    }

    public String getHome_market() {
        return home_market;
    }

    public void setHome_market(String home_market) {
        this.home_market = home_market;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getAway_team() {
        return away_team;
    }

    public void setAway_team(String away_team) {
        this.away_team = away_team;
    }

    public String getDay_night() {
        return day_night;
    }

    public void setDay_night(String day_night) {
        this.day_night = day_night;
    }

    public String getHome_team() {
        return home_team;
    }

    public void setHome_team(String home_team) {
        this.home_team = home_team;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getScheduled() {
        return scheduled;
    }

    public void setScheduled(String scheduled) {
        this.scheduled = scheduled;
    }

    protected Game(Parcel in) {
        id = in.readString();
        day_night = in.readString();
        scheduled = in.readString();
        home_team = in.readString();
        home_market = in.readString();
        away_team = in.readString();
        away_market = in.readString();
        date = in.readString();
        time = in.readString();
        long tmpDate = in.readLong();
        game_date = tmpDate != -1 ? new Date(tmpDate) : null;
    }

    /**
     * return date of game based on users current timezone
     *
     */
    public void setDateOfGame(){
        date = new FormatGameStartTime().getDateOfGame(getScheduled());
    }

    /**
     * return String time of game based on users current timezone
     */
    public void setTimeOfGame(){
        time = new FormatGameStartTime().getTimeOfGame(getScheduled());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(day_night);
        dest.writeString(scheduled);
        dest.writeString(home_team);
        dest.writeString(home_market);
        dest.writeString(away_team);
        dest.writeString(away_market);
        dest.writeString(date);
        dest.writeString(time);
        dest.writeLong(game_date != null ? game_date.getTime() : -1L);
    }

    @SuppressWarnings("unused")
    public static final Creator<Game> CREATOR = new Creator<Game>() {
        @Override
        public Game createFromParcel(Parcel in) {
            return new Game(in);
        }

        @Override
        public Game[] newArray(int size) {
            return new Game[size];
        }
    };
}
