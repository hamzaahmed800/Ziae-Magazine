package ziaetaiba.com.zia_e_magazine.Fragments;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import ziaetaiba.com.zia_e_magazine.Activites.MainActivity;
import ziaetaiba.com.zia_e_magazine.Adapter.AllContent_Adapter;
import ziaetaiba.com.zia_e_magazine.Adapter.DetailAdapter;
import ziaetaiba.com.zia_e_magazine.Calls.homedetailListener;
import ziaetaiba.com.zia_e_magazine.Connection.Connect_Server;
import ziaetaiba.com.zia_e_magazine.Database.DBHelper;
import ziaetaiba.com.zia_e_magazine.Globals.ConnectionStatus;
import ziaetaiba.com.zia_e_magazine.Globals.Constants;
import ziaetaiba.com.zia_e_magazine.Globals.GlobalCalls;
import ziaetaiba.com.zia_e_magazine.Globals.ImageDownloader;
import ziaetaiba.com.zia_e_magazine.Interface.ApiInterface;
import ziaetaiba.com.zia_e_magazine.Models.HomeData_Model;
import ziaetaiba.com.zia_e_magazine.Models.Home_Model;
import ziaetaiba.com.zia_e_magazine.Models.Product_Model;
import ziaetaiba.com.zia_e_magazine.R;



public class PageFragment extends Fragment implements homedetailListener {
    public static final String ARG_PAGE = "ARG_PAGE";
    public static final String ARG_TAB = "ARG_TAB";
    public RecyclerView recyclerView;
    public AllContent_Adapter homeadapter;
    public List<HomeData_Model> listItems;
    public  List<HomeData_Model> listItem;
    public SwipeRefreshLayout mSwipeRefreshLayout;
    public ProgressBar progressBar;
    private int mPage;
    private String mTab;
    private DetailAdapter detailAdapter;
    public Context context;
    private DBHelper dbHelper;
    public static boolean productData = false;

    public PageFragment(){

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
        mPage = getArguments().getInt(ARG_PAGE);
        mTab = getArguments().getString(ARG_TAB);
    }

    public static PageFragment newInstance(int page) {
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, page);
        PageFragment fragment = new PageFragment();
        fragment.setArguments(args);
        return fragment;
    }
    public static PageFragment newInstance(int page,String mTab) {
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, page);
        args.putString(ARG_TAB,mTab);
        PageFragment fragment = new PageFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPage = getArguments().getInt(ARG_PAGE);
        mTab = getArguments().getString(ARG_TAB);
    }

    // Inflate the fragment layout we defined above for this fragment
    // Set the associated text for the title
    @SuppressLint("ResourceAsColor")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_page, container, false);

        mSwipeRefreshLayout = v.findViewById(R.id.activity_main_swipe_refresh_layout);
        progressBar = v.findViewById(R.id.contentprogressBar);
        recyclerView =  v.findViewById(R.id.fragment_Recyclerview);

        listItem = new ArrayList<>();
        listItems = new ArrayList<>();

        requestStoragePermission();

        if(ConnectionStatus.getInstance(context).isOnline()){

            if(this.mPage == 0){
                getHomeData();
             ///   Log.e("PageFragment", String.valueOf("IF--"+mPage));

                mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {
                        refreshContent();
                    }
                });

            }else {
              //  Log.e("PageFragment", String.valueOf("ELSE--"+mPage));
                getProductDetails();
            }



        } else{
           // Toast.makeText(context, Constants.networkerror,Toast.LENGTH_SHORT).show();
            if(mPage == 0){

                getHomeDataLocalDb();

            }else{
                Log.e("Else","POSITION CALLED");
                getProductDetailLocalDb(MainActivity.listItems.get(mPage).getName());

            }

        }


        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));



        return v;
    }

    private void refreshContent() {

        if(ConnectionStatus.getInstance(context).isOnline()) {
            //Fetcing data
            getHomeData();

        }else{
            getHomeDataLocalDb();
        }
        homeadapter.notifyDataSetChanged();
        mSwipeRefreshLayout.setRefreshing(false);
    }

    //Fetching Home Complete data From Server
    private void getHomeData() {

        Retrofit retrofit = Connect_Server.getApiClient();
        ApiInterface clientAPIs = retrofit.create(ApiInterface.class);
        Call<Home_Model> call = clientAPIs.getHomeData(Constants.language,Constants.year,Constants.month);
        call.enqueue(new Callback<Home_Model>() {
            @Override
            public void onResponse(Call<Home_Model> call, Response<Home_Model> response) {
                if(response.isSuccessful()){
                    progressBar.setVisibility(View.GONE);
                    final Home_Model home_model = response.body();
                    if(home_model != null && home_model.getProductsDetails().size() > 0){
                        listItem.clear();
                        listItem.addAll(home_model.getProductsDetails());
                        homeadapter =  new AllContent_Adapter(context,listItem);
                        recyclerView.setAdapter(homeadapter);
                        homeadapter.setItemClickListener(PageFragment.this);
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            if (ContextCompat.checkSelfPermission(context,
                                    Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {

                                if(Constants.checkFirst == true){
                                    Log.e("CheckFirst","TRUE");
                                    saveImage(home_model);
                                    Constants.checkFirst = false;
                                }  else if(GlobalCalls.retriveMonth(context,GlobalCalls.MONTH_KEY) != GlobalCalls.GlobalMonth){
                                    saveImage(home_model);
                                }else{}

                            } else {
                                // requestStoragePermission(context,resource,image_name);
                            }
                        } else {

//                            if (ContextCompat.checkSelfPermission(context,
//                                    Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {

                                if(Constants.checkFirst == true){
                                    Log.e("CheckFirst","TRUE");
                                    saveImage(home_model);
                                    Constants.checkFirst = false;
                                }
                                else if(GlobalCalls.retriveMonth(context,GlobalCalls.MONTH_KEY) != GlobalCalls.GlobalMonth){
                                    saveImage(home_model);
                                }else{}

                          //  }

                        }


                    }else{//  getHomeDataLocalDb();//Toast.makeText(context,Constants.null_data,Toast.LENGTH_SHORT).show();
                         }
                }else{
                    Toast.makeText(context,Constants.server_error,Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Home_Model> call, Throwable t) {
                Toast.makeText(context,Constants.networkerror+"Page Fragment Menu",Toast.LENGTH_SHORT).show();
            }
        });
    }
    //Fetching Tab Details From Server
    private void getProductDetails() {
        Retrofit retrofit = Connect_Server.getApiClient();
        ApiInterface clientAPIs = retrofit.create(ApiInterface.class);
        Call<Product_Model> call = clientAPIs.getDetails(Constants.language, Constants.year, Constants.month, mTab);
        call.enqueue(new Callback<Product_Model>() {
            @Override
            public void onResponse(Call<Product_Model> call, Response<Product_Model> response) {
                if (response.isSuccessful()) {
                    progressBar.setVisibility(View.GONE);
                    Product_Model product_model = response.body();
                    if (product_model != null && product_model.getDetails().size()>0) {
                        if (product_model != null) {
                           listItems.addAll(product_model.getDetails());
                           detailAdapter = new DetailAdapter(context,listItems);
                           recyclerView.setAdapter(detailAdapter);
                        } else {
                            Toast.makeText(context, Constants.null_data, Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        getProductDetailLocalDb(MainActivity.listItems.get(mPage).getName());
                    }
                }

            }

            @Override
            public void onFailure(Call<Product_Model> call, Throwable t) {
                Toast.makeText(context, Constants.networkerror+"Page Fragment Product", Toast.LENGTH_SHORT).show();
            }
        });

    }
    //Home Adapter Listener
    @Override
    public void getHomeDetails(View view, HomeData_Model homeModel) {
        listItems.clear();
        listItems.add(homeModel);
        detailAdapter = new DetailAdapter(context,listItems);
        recyclerView.setAdapter(detailAdapter);
    }
    //Fetching Data From LocalDB
    private void getHomeDataLocalDb(){

        if(listItems == null || listItems.size() == 0){

            Cursor cursor;
            dbHelper = new DBHelper(context);

            try {

                cursor = dbHelper.getAllProductData();

                if(cursor != null){

                    cursor.moveToFirst();

                    if(cursor.getCount() > 0){
                        progressBar.setVisibility(View.GONE);
                        do{
                            listItems.add(new HomeData_Model(cursor.getString(0),cursor.getString(1),
                                    ImageDownloader.retriveImagePath(cursor.getString(0)),cursor.getString(3)));


                        }while(cursor.moveToNext());
                        homeadapter =  new AllContent_Adapter(context,listItems);
                        recyclerView.setAdapter(homeadapter);
                        homeadapter.setItemClickListener(PageFragment.this);

                    }

                } else{

                }


            }catch (Exception e){e.printStackTrace();}



        }

    }

    private void getProductDetailLocalDb(String name){

                Cursor cursor;
                listItem = new ArrayList<>();
                dbHelper = new DBHelper(context);

                try {

                    cursor = dbHelper.getAllProductData();

                    if(cursor != null){

                        cursor.moveToFirst();

                        if(cursor.getCount() > 0){
                            progressBar.setVisibility(View.GONE);
                            do{

//                                Log.e("TAB--NAME--->",name);
//                                Log.e("DB_TNAME--->",cursor.getString(1));
                                String tab = cursor.getString(1);
                                if(name.replace(" ","").equalsIgnoreCase(tab.replace(" ",""))){
//                                    Log.e("ID",cursor.getString(0));
//                                    Log.e("NAME",cursor.getString(1));
//                                    Log.e("THUMB",cursor.getString(2));
//                                    Log.e("DESC",cursor.getString(3));
//                                    Log.e("path from folder", ImageDownloader.retriveImagePath(cursor.getString(1)));

                                    listItem.add(new HomeData_Model(cursor.getString(0),cursor.getString(1),
                                            ImageDownloader.retriveImagePath(cursor.getString(0)),cursor.getString(3)));
                                    detailAdapter = new DetailAdapter(context,listItem);
                                    recyclerView.setAdapter(detailAdapter);
                                    break;
                                }


                            }while(cursor.moveToNext());


                        }

                    } else{

                    }


                }catch (Exception e){e.printStackTrace();}

            }

    private void saveImage(Home_Model home_model){
     //   Log.e("MonthChange","ImagesSaved");
        for (int i =0;i<home_model.getProductsDetails().size();i++){
            saveImageInternalStorage(home_model.getProductsDetails().get(i).getThumbnailPath(),
                    home_model.getProductsDetails().get(i).getId());
        }
    }

    public void saveImageInternalStorage(String url,final String image_name){
        Glide.with(context)
                .load(url)
                .asBitmap()
                .into(new SimpleTarget<Bitmap>(100,100) {
                    @Override
                    public void onResourceReady(Bitmap resource, GlideAnimation glideAnimation)  {
                                ImageDownloader.saveImage(context,resource,image_name);

                    }
                });


    }

    public void requestStoragePermission() {
        ActivityCompat.requestPermissions(getActivity(),
                new String[] {Manifest.permission.READ_EXTERNAL_STORAGE},1);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 1)  {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Log.e("Permission","GRANTED");
            } else {
                Log.e( "Permission","Denied");
            }
        }
    }

    }


