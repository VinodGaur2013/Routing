package com.eskar.routing.Utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CommonMethod {


    public final static long ONE_SECOND = 1000;


    // Login & signup   Validation
    public static boolean isEmailValid(String email) {
        boolean isValid = false;
        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        CharSequence inputStr = email;

        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(inputStr);
        if (matcher.matches()) {
            isValid = true;
        }
        return isValid;
    }

    public final static boolean isValidEmail(CharSequence target) {
        if (target == null) {
            return false;
        } else {
            return android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
        }
    }

    public final static boolean isValidPass(CharSequence target) {

        if (TextUtils.isEmpty(target)) {
            return false;
        } else if (target.length() < 4) {
            return false;
        } else {
            return true;
        }
    }

    public final static boolean isValidTelephone(CharSequence target) {

        if (TextUtils.isEmpty(target)) {
            return false;
        } else if (target.length() < 10) {
            return false;
        } else
            return true;

    }

    public static boolean validateName(String name) {
        if (TextUtils.isEmpty(name)) {
            return false;
        } else if (!name.matches("[A-Z][a-zA-Z]*"))
            return false;
        else return true;
    }


    //***********************************************************************************************


    //Utilities ^^^^^^^^^^
    public static void hideSoftKeyboard(Activity activity) {
        try {
            InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
        } catch (Exception e) {
        }
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

    public static void closeKeyboard(final Context context, final View view) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    public static void showAlert(String message, Activity context) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage(message).setCancelable(false).setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {

            }
        });
        try {
            builder.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static void showDialog(String title,String msg, final Context context){
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);

        alertDialog.setTitle(title);
        alertDialog.setMessage(msg);
        //alertDialog.setIcon(R.drawable.delete);

        alertDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog,int which) {

                Toast.makeText(context, "You clicked on YES", Toast.LENGTH_SHORT).show();
            }
        });

        // Setting Negative "NO" Button
        alertDialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(context, "You clicked on NO", Toast.LENGTH_SHORT).show();
                dialog.cancel();
            }
        });

        // Showing Alert Message
        alertDialog.show();
}
    //**********************************************************************************************


    //Fonts^^^^^^^^^^^^^
    public static void setRobotoBold(TextView text, Activity activity) {
        String fontRobotoBold = "fonts/roboto_bold.ttf";
        Typeface faceRobotoBold = Typeface.createFromAsset(activity.getAssets(), fontRobotoBold);
        text.setTypeface(faceRobotoBold);
    }

    public static void setRobotoRegular(TextView text, Activity activity) {
        String fontRoboto = "fonts/roboto_thin.ttf";
        Typeface faceRoboto = Typeface.createFromAsset(activity.getAssets(), fontRoboto);
        text.setTypeface(faceRoboto);
    }


   /* public static void hideSoftKeyboard(Activity activity) {
        try {
            InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
        } catch (Exception e) {

        }
    }*/



    public static void saveLoginPreferencesUserId(Context context, String value) {

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("USERID", value);
        editor.commit();

    }


    public static String getLoginSavedPreferencesUSerID(Context context) {

        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(context);

        String login_status = sharedPreferences.getString("USERID", "");

        return login_status;
    }


    public static void saveLoginPreferencesUseType(Context context, String value) {

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("USERTYPE", value);
        editor.commit();

    }


    public static String getLoginSavedPreferencesUSerType(Context context) {

        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(context);

        String login_status = sharedPreferences.getString("USERTYPE", "");

        return login_status;
    }






}