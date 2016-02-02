package com.baseballstandings.logify;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class FormatGameStartTime {

	private String year, day, month;

	// 2015-04-17T02:15:00+00:00
	SimpleDateFormat parseFormat = new SimpleDateFormat(
			"yyyy-MM-dd'T'HH:mm:ssZ");
	SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy hh:mm a");
	SimpleDateFormat timeFormatAP = new SimpleDateFormat("hh:mm a z");
	SimpleDateFormat dateFormatAP = new SimpleDateFormat("MM/dd/yyyy");


	public String getDateOfGame(String s) {
		try {
			Date date = parseFormat.parse(s);

			return dateFormatAP.format(date);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return s;
	}

	public String convertCaldroidDateToDate(Date date){

		SimpleDateFormat parseCaldroid = new SimpleDateFormat("EE MMM dd hh:mm:ss z yyyy");
		try {
			Date d = parseCaldroid.parse(date +"");
			return dateFormatAP.format(d);
		} catch (ParseException e) {
			e.printStackTrace();
		}

		return null;
	}

	public String getTodaysDate(){

		return dateFormatAP.format(new Date());
	}

    /**
     * UNIT TESTING METHOD
     * pass in date string to test specific days games
     * @param dateString custom user specific date in the form MM/DD/YYYY
     * @return the parsed date
     */
    public String getCustomDate(String dateString){
        try {
            Date date = dateFormatAP.parse(dateString);
            return dateFormatAP.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return "";

    }

    /**
     * @param day the day send in
     * @return the day with the proper display suffix
     */
    public String getDateSuffix(int day) {
        switch (day) {
            case 1: case 21: case 31:
                return ("st");
            case 2: case 22:
                return ("nd");
            case 3: case 23:
                return ("rd");
            default:
                return ("th");
        }
    }

	/**
	 * @param scheduled Game Date As String yyyy-MM-dd'T'HH:mm:ssZ
	 * @return Date Object From String Game Date
	 */
	public Date formatGameDateToDateObject(String scheduled) {
		try {
			return parseFormat.parse(scheduled);
		} catch (ParseException e) {
			e.printStackTrace();
		}

		return null;
	}

    /**
     * @return string with today's date
     * in format Monday, March 3rd 2015
     */
	public String getTodaysDateLongString(){

		SimpleDateFormat dateFormatAP = new SimpleDateFormat("EEEE, MMMM dd, yyyy");
		return dateFormatAP.format(new Date());
	}

	public String getDateLongString(String date){
		SimpleDateFormat dateFormatAP = new SimpleDateFormat("EEEE, MMMM dd, yyyy");
		Date d = null;
		try {
			d = parseFormat.parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return dateFormatAP.format(d);
	}

	public String getTimeOfGame(String dateString) {
		try {
			Date date = parseFormat.parse(dateString);

			return timeFormatAP.format(date);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return dateString;

	}

	//Convert Date to Calendar
	public Calendar dateToCalendar(Date date) {

		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar;

	}

	//Convert Calendar to Date
	public Date calendarToDate(Calendar calendar) {
		return calendar.getTime();
	}

	public String formatYYYYMMDDDateToString(Date date){
		SimpleDateFormat dFormat = new SimpleDateFormat("yyyy-MM-dd");

		return dFormat.format(date);
	}


}
