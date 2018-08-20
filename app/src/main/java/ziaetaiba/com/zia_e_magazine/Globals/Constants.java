package ziaetaiba.com.zia_e_magazine.Globals;

/**
 * Created by HAMI on 17/07/2018.
 */

public class Constants {

    public static int language_type = 1;
    public static String language = "ur";
    public static boolean backStack = false;
    public static String month = GlobalCalls.getMonth();
    public static String year = GlobalCalls.getYear();
    public static String networkerror = "Network Error!";
    public static String server_error = "Server Error!";
    public static String null_data = "Null Data!";
    public static String magzine_month_year = GlobalCalls.getMonthNameUR();





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
