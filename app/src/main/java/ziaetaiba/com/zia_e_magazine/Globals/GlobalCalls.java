package ziaetaiba.com.zia_e_magazine.Globals;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.Calendar;
import java.util.TimeZone;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by HAMI on 31/07/2018.
 */

public class GlobalCalls {


    public static String MY_PREFS_NAME = "MONTH";
    public static String MONTH_KEY = "Month";
    public static String MONTHURDU_KEY = "MonthUrdu";
    public static String GlobalMonth;


    public static String getYear(){
        Calendar calendar = Calendar.getInstance(TimeZone.getDefault());
        String year = String.valueOf(calendar.get(Calendar.YEAR));
        return year;
    }

    public static void  getMonth(String month) {

     if(month.equals("january")){
         Constants.month_type = 1;
     }
     else if(month.equals("febuary")){
         Constants.month_type = 2;
     }
     else if(month.equals("march")){
         Constants.month_type = 3;
     }
     else if(month.equals("april")){
         Constants.month_type = 4;
     }
     else if(month.equals("may")){
         Constants.month_type = 5;
     }
     else if(month.equals("june")){
         Constants.month_type = 6;
     }
     else if(month.equals("july")){
         Constants.month_type = 7;
     }
     else if(month.equals("august")){
         Constants.month_type = 8;
     }
     else if(month.equals("september")){
         Constants.month_type = 9;
     }
     else if(month.equals("october")){
         Constants.month_type = 10;
     }
     else if(month.equals("november")){
         Constants.month_type = 11;
     }
     else if(month.equals("december")){
         Constants.month_type = 12;
     }
    }

    public static String getMonthNameUR(int month) {

        switch (month){
            case 1:
                return "جنوری"+" "+getYear();

            case 2:
                return "فروری"+" "+getYear();

            case 3:
                return "مارچ"+" "+getYear();

            case 4:
                return "اپریل"+" "+getYear();

            case 5:
                return "مئی"+" "+getYear();

            case 6:
                return "جون"+" "+getYear();

            case 7:
                return "جولائی"+" "+getYear();

            case 8:
                return "اگست"+" "+getYear();

            case 9:
                return "ستمبر"+" "+getYear();

            case 10:
                return "اکتوبر"+" "+getYear();

            case 11:
                return "نومبر"+" "+getYear();

            case 12:
                return "دسمبر"+" "+getYear();

            default:
                return null;

        }
    }


    public static void saveMonth(Context context,String month,int no_month){
        SharedPreferences.Editor editor = context.getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
        editor.putString(MONTH_KEY, month);
        editor.putString(MONTHURDU_KEY,getMonthNameUR(no_month));
        editor.apply();
    }

    public static String  retriveMonth(Context context,String key){
        SharedPreferences prefs = context.getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        String restoredText = null;
        if(prefs != null){
            restoredText = prefs.getString(key, null);
        }
        return restoredText;
    }

    public static void removeMonth(Context context){
        SharedPreferences.Editor editor = context.getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
        editor.remove(MONTH_KEY);
        editor.apply();
    }





}
