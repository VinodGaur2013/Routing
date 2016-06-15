package com.eskar.routing.Utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.inputmethod.InputMethodManager;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;
import java.util.regex.Pattern;


@SuppressLint("InflateParams")
public class CommonUtils {
	
	public static final String BLANK_TEXT_VALIDATOR = "^\\s*$";
	public static final float ALPHA_LIGHT = 0.45f;
	public static final float ALPHA_DARK = 255f;
	
	
	public final static long ONE_SECOND = 1000;
    public final static long SECONDS = 60;

    public final static long ONE_MINUTE = ONE_SECOND * 60;
    public final static long MINUTES = 60;

    public final static long ONE_HOUR = ONE_MINUTE * 60;
    public final static long HOURS = 24;

    public final static long ONE_DAY = ONE_HOUR * 24;
	
	
	
	

	public static void hideSoftKeyboard(Activity activity) {
		try {
			InputMethodManager inputMethodManager = (InputMethodManager) activity
					.getSystemService(Activity.INPUT_METHOD_SERVICE);
			inputMethodManager.hideSoftInputFromWindow(activity
					.getCurrentFocus().getWindowToken(), 0);
		} catch (Exception e) {

		}
	}
	
	
	
	
	public static String getConvert24to12(String time)
	{
	    String convertedTime ="";
	    try {
	        SimpleDateFormat displayFormat = new SimpleDateFormat("hh:mm a");
	        SimpleDateFormat parseFormat = new SimpleDateFormat("HH:mm:ss");
	        Date date = parseFormat.parse(time);        
	        convertedTime=displayFormat.format(date);
	        System.out.println("convertedTime : "+convertedTime);
	    } catch (final ParseException e) {
	        e.printStackTrace();
	    }
	    return convertedTime;
	    //Output will be 10:23 PM
	}

	
	
	
	
	
	
	
	
	
	
	
	public static String getTimeStamp(String date_time, String format) {

		DateFormat formatter = new SimpleDateFormat(format);
		formatter.setTimeZone(TimeZone.getDefault());
		Date datee;
		try {
			datee = formatter.parse(date_time);
			Log.e("", "Today is  : " + datee.getTime());
			String timestamp = "" + datee.getTime();
			if (timestamp.length() > 10) {
				timestamp = "" + Long.parseLong(timestamp) / 1000L;
			}
			return timestamp;
		} catch (ParseException pe) {
			pe.printStackTrace();
			return "";
		}

	}

	
	public static long getMillesecondsStamp(String date) {
		long oldMillis = 0l;
		try {
			SimpleDateFormat formatter = new SimpleDateFormat(
					"MM-dd-yyyy hh:mm aaa");
			Date oldDate = formatter.parse(date);
			oldMillis = oldDate.getTime();
		} catch (ParseException e) {

			e.printStackTrace();
		}
		return oldMillis;

	}
	
	
	

	@SuppressLint("SimpleDateFormat")
	public static String getDateAndTimeFromTimeStamp(String timeStamp) {
		if (timeStamp.length() > 0) {
			return new SimpleDateFormat("MMM dd, yyyy  hh:mm aaa")
					.format(new Date((Long.valueOf(timeStamp) * 1000)));
		} else {

			return "";
		}
	}
	
	
	
	
	
	
	//Time ago format
	
	
	public static String getmillisToLongDHMS(long duration) {
	      StringBuffer res = new StringBuffer();
	      long temp = 0;
	      if (duration >= ONE_SECOND) {
	        temp = duration / ONE_DAY;
	        if (temp > 0) {
	          duration -= temp * ONE_DAY;
	          res.append(temp).append(" day").append(temp > 1 ? "s" : "")
	             .append(duration >= ONE_MINUTE ? ", " : "");
	        }

	        temp = duration / ONE_HOUR;
	        if (temp > 0) {
	          duration -= temp * ONE_HOUR;
	          res.append(temp).append(" hour").append(temp > 1 ? "s" : "")
	             .append(duration >= ONE_MINUTE ? ", " : "");
	        }

	        temp = duration / ONE_MINUTE;
	        if (temp > 0) {
	          duration -= temp * ONE_MINUTE;
	          res.append(temp).append(" minute").append(temp > 1 ? "s" : "");
	        }

	        if (!res.toString().equals("") && duration >= ONE_SECOND) {
	          res.append(" and ");
	        }

	        temp = duration / ONE_SECOND;
	        if (temp > 0) {
	          res.append(temp).append(" second").append(temp > 1 ? "s" : "");
	        }
	        return res.toString();
	      } else {
	        return "0 second";
	      }
	    }
	
	
	
	
	
	
	
	
	
	public static String getPreferences(Context context, String key) {

		SharedPreferences sharedPreferences = PreferenceManager
				.getDefaultSharedPreferences(context);
		String value = sharedPreferences.getString(key, "");

		return value;
	}
	
	
	
	
	
	
	public static String getTimefromTimestamp(String timestamp)
	{
		
		
		Calendar c = Calendar.getInstance();
	    c.setTimeInMillis(Long.parseLong(timestamp));
	    Date d = c.getTime();
	    SimpleDateFormat sdf = new SimpleDateFormat("MMM dd, yyyy HH:mm");
	    return sdf.format(d);
	
	}
	
	
	
	
	
	
	
	
	@SuppressLint("SimpleDateFormat")
	public static String getDateFormatted(String inputDateStr) throws ParseException{
		
		DateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		DateFormat outputFormat = new SimpleDateFormat("MMM dd, yyyy");
		
		
		Date date = inputFormat.parse(inputDateStr);
		String outputDateStr = outputFormat.format(date);
		System.out.println(outputDateStr);
		return outputDateStr;
		
	}
	
	
	

	public static String getCalenderViewFormatted(String inputDateStr) throws ParseException{
		
	
	

		
		
		DateFormat inputFormat = new SimpleDateFormat("MMdd");
		DateFormat outputFormat = new SimpleDateFormat("MMM\ndd");
		
		
		Date date = inputFormat.parse(inputDateStr);
		String outputDateStr = outputFormat.format(date);
		System.out.println(outputDateStr);
		return outputDateStr;
		
	}
	
	public static String getSavedPreferences(Context context,String key) {

		SharedPreferences sharedPreferences = PreferenceManager
				.getDefaultSharedPreferences(context);

		String login_status = sharedPreferences.getString(key, "");
		

		return login_status.trim();
	}
	public static void savePreferences(Activity activity, String key,
			String value) {

		Editor editor = PreferenceManager.getDefaultSharedPreferences(activity)
				.edit();
		editor.putString(key, value);
		editor.commit();
	}
	
	



	public final static boolean isValidEmail(CharSequence target) {
		if (target == null) {
			return false;
		} else {
			return android.util.Patterns.EMAIL_ADDRESS.matcher(target)
					.matches();
		}
	}

	public static boolean validateName(String name) {
		return name.matches("[A-Z][a-zA-Z]*");
	}

	

	/**
	 * Raman Sharma
	 * */
	public static boolean isTextBlank(String text) {

		if (Pattern.matches(BLANK_TEXT_VALIDATOR, text)) {
			return true;
		}
		return false;
	}
	
	/** Raman Sharma
	 * This method check for null value of object. If object is null then it
	 * returns a default value
	 * 
	 * @param objectToCheck
	 * @param defaultValue
	 * @return Object
	 */
	public static <T> T replaceIfNull(T objectToCheck, T defaultValue) {
		return objectToCheck == null ? defaultValue : objectToCheck;
	}

	/**
	 * Raman Sharma
	 * This method can be used to check null value for any object
	 * 
	 * @param objectToCheck
	 * @return boolean
	 */
	public static <T> boolean CheckIfNull(T objectToCheck) {
		return objectToCheck == null ? true : false;
	}




	/** Called for Showing Alert in Application */
	public static void showAlert(String message, Activity context) {

		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		builder.setMessage(message).setCancelable(false)
				.setPositiveButton("OK", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int userId) {

					}
				});
		try {
			builder.show();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}


	public static boolean isOnline(Context context) {
		if (!CheckIfNull(context)) {
			ConnectivityManager conMgr = (ConnectivityManager) context
					.getSystemService(Context.CONNECTIVITY_SERVICE);
			NetworkInfo netInfo = conMgr.getActiveNetworkInfo();

			if (netInfo == null || !netInfo.isConnected()
					|| !netInfo.isAvailable()) {

				return false;
			}
		}

		return true;
	}
	




}
