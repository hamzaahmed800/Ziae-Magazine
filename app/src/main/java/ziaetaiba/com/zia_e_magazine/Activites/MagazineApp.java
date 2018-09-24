package ziaetaiba.com.zia_e_magazine.Activites;

import android.app.Application;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import ziaetaiba.com.zia_e_magazine.Connection.Connect_Server;
import ziaetaiba.com.zia_e_magazine.Database.DBHelper;
import ziaetaiba.com.zia_e_magazine.Globals.ConnectionStatus;
import ziaetaiba.com.zia_e_magazine.Globals.Constants;
import ziaetaiba.com.zia_e_magazine.Globals.GlobalCalls;
import ziaetaiba.com.zia_e_magazine.Interface.ApiInterface;
import ziaetaiba.com.zia_e_magazine.Models.Home_Model;
import ziaetaiba.com.zia_e_magazine.Models.Menu_Model;

/**
 * Created by HAMI on 27/08/2018.
 */

public class MagazineApp extends Application {

    public DBHelper dbHelper;
    public Cursor cursor;
    private Menu_Model menu_model;
    private Home_Model home_model;
    private boolean menuData = false, productData = false;
    private SQLiteDatabase db;

    @Override
    public void onCreate() {
        super.onCreate();
        new Thread(new Runnable() {
            @Override
            public void run() {

                    InsertMenusData();

            }
        }).start();

    }


    public void InsertMenusData(){
        dbHelper = new DBHelper(this);
        cursor =  dbHelper.getAllMenuData();
        if(cursor.getCount() == 0){
          Log.e("---->","1");
            try {
                if(ConnectionStatus.getInstance(getApplicationContext()).isOnline()){
                    FetchMenusData();
                }


            }catch (Exception e){e.printStackTrace();}

        }else if(cursor.getCount() > 0 ){
            Log.e("---->","2");
                updateDatabase();
        }

    }



    public void InsertProductData(){

        dbHelper = new DBHelper(this);
        cursor =  dbHelper.getAllProductData();
        if(cursor.getCount() == 0){
            try {
                FetchProductData();

            }catch (Exception e){e.printStackTrace();}

        }

    }

    //Fetcing Menus Data From Server Storing in Local DB
    public void FetchMenusData() {
        Log.e("Fetching Menu Data","Executed");
        Retrofit retrofit = Connect_Server.getApiClient();
        ApiInterface clientAPIs = retrofit.create(ApiInterface.class);
        Call<Menu_Model> call = clientAPIs.getMenu(Constants.language);
        call.enqueue(new Callback<Menu_Model>() {
            @Override
            public void onResponse(Call<Menu_Model> call, Response<Menu_Model> response) {
                if (response.isSuccessful()) {

                    menu_model = response.body();
                    GlobalCalls.GlobalMonth = menu_model.getMonth();
                    Constants.month = menu_model.getMonth();
                    Constants.year = menu_model.getYear();
                    GlobalCalls.getMonth(Constants.month);
                    if (menu_model != null && menu_model.getMenus().size()>0){
                        // Fetching data and setting in Adapter
                        dbHelper.insertMenuData("1", "ہوم",Constants.month+"/"+"ہوم");
                        for(int i=0;i<menu_model.getMenus().size();i++){
                            dbHelper.insertMenuData(menu_model.getMenus().get(i).getId(),
                                                    menu_model.getMenus().get(i).getName(),
                                                    menu_model.getMenus().get(i).getExtra());
                        }
                        InsertProductData();

                    }else {Toast.makeText(getApplicationContext(), Constants.null_data, Toast.LENGTH_SHORT).show();}
                } else {Toast.makeText(getApplicationContext(), Constants.null_data, Toast.LENGTH_SHORT).show();}
            }

            @Override
            public void onFailure(Call<Menu_Model> call, Throwable t) {
                Toast.makeText(getApplicationContext(), Constants.server_error, Toast.LENGTH_SHORT).show();
            }
        });
     //   return menuList;
    }

    //Fetcing Products Data From Server Storing in Local DB
    public void FetchProductData() {
        Log.e("Fetching Product Data","Executed");
        Retrofit retrofit = Connect_Server.getApiClient();
        ApiInterface clientAPIs = retrofit.create(ApiInterface.class);
        Call<Home_Model> call = clientAPIs.getHomeData(Constants.language,Constants.year,Constants.month);
        call.enqueue(new Callback<Home_Model>() {
            @Override
            public void onResponse(Call<Home_Model> call, Response<Home_Model> response) {
                if(response.isSuccessful()){
                    home_model = response.body();
                    if(home_model != null && home_model.getProductsDetails().size() > 0){
                        for(int i=0;i<home_model.getProductsDetails().size();i++){
                            if(!home_model.getProductsDetails().get(i).getDescription().equals("")){

                                dbHelper.insertProductData(home_model.getProductsDetails().get(i).getId(),
                                        home_model.getProductsDetails().get(i).getName(),
                                        home_model.getProductsDetails().get(i).getThumbnailPath(),
                                        home_model.getProductsDetails().get(i).getDescription());


                            }else{

                            }

                        }

                    }else{
                        Toast.makeText(getApplicationContext(),Constants.null_data,Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(getApplicationContext(),Constants.server_error,Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Home_Model> call, Throwable t) {
                Toast.makeText(getApplicationContext(),Constants.networkerror,Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void updateDatabase() {
        Log.e("Update Function","Executed Called");
        Retrofit retrofit = Connect_Server.getApiClient();
        ApiInterface clientAPIs = retrofit.create(ApiInterface.class);
        Call<Menu_Model> callMenu = clientAPIs.getMenu(Constants.language);
        callMenu.enqueue(new Callback<Menu_Model>() {
            @Override
            public void onResponse(Call<Menu_Model> call, Response<Menu_Model> response) {
                if (response.isSuccessful()) {
                    menu_model = response.body();
                    GlobalCalls.GlobalMonth = menu_model.getMonth();
                    Constants.month = menu_model.getMonth();
                    Constants.year = menu_model.getYear();
                    GlobalCalls.getMonth(Constants.month);
                    NewProduct();
                  //  else {Toast.makeText(getApplicationContext(), Constants.null_data, Toast.LENGTH_SHORT).show();}
                } else {Toast.makeText(getApplicationContext(), Constants.server_error, Toast.LENGTH_SHORT).show();}}
            @Override
            public void onFailure(Call<Menu_Model> call, Throwable t) {//        Toast.makeText(getApplicationContext(), Constants.server_error, Toast.LENGTH_SHORT).show()
                 }});
      //      Log.e("Saved ", ""+GlobalCalls.retriveMonth(getApplicationContext(),GlobalCalls.MONTH_KEY));

    }

    private void NewProduct(){
        if (GlobalCalls.GlobalMonth != GlobalCalls.retriveMonth(getApplicationContext(),GlobalCalls.MONTH_KEY)){
            ///Product Data Call
            Log.e("RetriveMonth",GlobalCalls.retriveMonth(getApplicationContext(),GlobalCalls.MONTH_KEY));
            Log.e("Month",Constants.month);
            Log.e("Year",Constants.year);
            Retrofit retrofit = Connect_Server.getApiClient();
            ApiInterface clientAPIs = retrofit.create(ApiInterface.class);
            Call<Home_Model> callProduct = clientAPIs.getHomeData(Constants.language,Constants.year,Constants.month);
            callProduct.enqueue(new Callback<Home_Model>() {
                @Override
                public void onResponse(Call<Home_Model> call, Response<Home_Model> response) {
                    if(response.isSuccessful()){
                        Log.e("Response","sucessful");
                        home_model = response.body();
                        if(home_model != null && home_model.getProductsDetails().size() > 0){
                         //   Log.e("NewData","Insertion");
                            GlobalCalls.removeMonth(getApplicationContext());
                            GlobalCalls.saveMonth(getApplicationContext(),Constants.month,Constants.month_type);
                            dbHelper.updateTables();
                            dbHelper.insertMenuData("1", "ہوم",Constants.month+"/"+"ہوم");
                            for(int i=0;i<menu_model.getMenus().size();i++){
                                dbHelper.insertMenuData(menu_model.getMenus().get(i).getId(),
                                        menu_model.getMenus().get(i).getName(),
                                        menu_model.getMenus().get(i).getExtra());

                            }
                            for(int i=0;i<home_model.getProductsDetails().size();i++) {
                                productData = true;
                                if (!home_model.getProductsDetails().get(i).getDescription().equals("")) {
                                    Log.e("NewData","Insertion");
                                    dbHelper.insertProductData(home_model.getProductsDetails().get(i).getId(),
                                            home_model.getProductsDetails().get(i).getName(),
                                            home_model.getProductsDetails().get(i).getThumbnailPath(),
                                            home_model.getProductsDetails().get(i).getDescription());
//                                        new ImageDownloader(getApplicationContext(), home_model.getProductsDetails().get(i).getThumbnailPath(),
//                                                home_model.getProductsDetails().get(i).getName());
                                } else {}
                                //    }

                            }

                        }else{
                            Toast.makeText(getApplicationContext(),Constants.null_data,Toast.LENGTH_SHORT).show();}
                    } else{
                        Toast.makeText(getApplicationContext(),Constants.server_error,Toast.LENGTH_SHORT).show();}}
                @Override
                public void onFailure(Call<Home_Model> call, Throwable t) {
                    //     Toast.makeText(getApplicationContext(),Constants.networkerror,Toast.LENGTH_SHORT).show();
                }
            });

            ///Product Data Call End Here
            menuData = true;
        }else{}
    }



}
