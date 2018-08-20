package ziaetaiba.com.zia_e_magazine.Globals;

import java.util.Calendar;
import java.util.TimeZone;

/**
 * Created by HAMI on 31/07/2018.
 */

public class GlobalCalls {

//    public static String getCurrentDate(){
//
//        Calendar calendar = Calendar.getInstance(TimeZone.getDefault());
//        String year = String.valueOf(calendar.get(Calendar.YEAR));
//        String month = getMonth(calendar.get(Calendar.MONTH));
//
//        return year+","+month;
//
//    }

    public static String getYear(){
        Calendar calendar = Calendar.getInstance(TimeZone.getDefault());
        String year = String.valueOf(calendar.get(Calendar.YEAR));
        return year;
    }

    public static String getMonth() {

        Calendar calendar = Calendar.getInstance(TimeZone.getDefault());
        int month = (calendar.get(Calendar.MONTH)+1);


        switch (month){
            case 1:
                return "january";

            case 2:
                return "febuary";

            case 3:
                return "march";

            case 4:
                return "april";

            case 5:
                return "may";

            case 6:
                return "june";

            case 7:
                return "july";

            case 8:
                return "august";

            case 9:
                return "september";

            case 10:
                return "october";

            case 11:
                return "november";

            case 12:
                return "december";

            default:
                return null;

        }
    }

    public static String getMonthNameUR() {

        Calendar calendar = Calendar.getInstance(TimeZone.getDefault());
        int month = (calendar.get(Calendar.MONTH)+1);


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
}
