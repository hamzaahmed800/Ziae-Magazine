package ziaetaiba.com.zia_e_magazine.Globals;


/**
 * Created by HAMI on 17/07/2018.
 */

public class Constants {

    public static int month_type;
    public static String language = "ur";
    public static boolean backStack = false;
    public static boolean checkFirst = true;
    public static boolean shareCheck = false;
    public static String month = null;
    public static String year = null;
    public static String internet_require = "Internet Require";
    public static String networkerror = "Network Error!";
    public static String server_error = "Server Error!";
    public static String null_data = "Null Data!";
    public static String header_app_name = "Magazine ZiaeTaiba";
    public static String magzine_month_year;





    //Toolbar Title
    public static String toolbarTitle(){
        String magzine_name = null;
        if(language.equals("ur")){
            magzine_name = "ماہنامہ ضیائے طیبہ  ";
         //   magzine_month = GlobalCalls.getMonthNameUR();
        }else{
            magzine_name = "Ziae Magazine  ";
          //  magzine_month = GlobalCalls.getMonth();
        }


        return magzine_name+"( "+magzine_month_year+" )";
    }
}
