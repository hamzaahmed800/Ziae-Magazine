package ziaetaiba.com.zia_e_magazine.Fragments;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.MotionEventCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;

import java.io.File;
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
import ziaetaiba.com.zia_e_magazine.GestureListener.simpleOnScaleGestureListener;
import ziaetaiba.com.zia_e_magazine.Globals.ConnectionStatus;
import ziaetaiba.com.zia_e_magazine.Globals.Constants;
import ziaetaiba.com.zia_e_magazine.Globals.GlobalCalls;
import ziaetaiba.com.zia_e_magazine.Globals.ImageDownloader;
import ziaetaiba.com.zia_e_magazine.Interface.ApiInterface;
import ziaetaiba.com.zia_e_magazine.Models.HomeData_Model;
import ziaetaiba.com.zia_e_magazine.Models.Home_Model;
import ziaetaiba.com.zia_e_magazine.Models.Product_Model;
import ziaetaiba.com.zia_e_magazine.Models.ShareModel;
import ziaetaiba.com.zia_e_magazine.R;



public class PageFragment extends Fragment implements homedetailListener {
    public static final String ARG_PAGE = "ARG_PAGE";
    public static final String ARG_TAB = "ARG_TAB";
    public RecyclerView recyclerView;
    public AllContent_Adapter homeadapter;
    public static List<HomeData_Model> listItems;
    public  static List<HomeData_Model> listItem;
    public SwipeRefreshLayout mSwipeRefreshLayout;
    public ProgressBar progressBar;
    private int mPage;
    private String mTab;
    private DetailAdapter detailAdapter;
    public Context context;
    private DBHelper dbHelper;
    public static int tabPosition;
    public static String description = null, topic_name = null,Thumb = null,detailDesc = null,detailTpName = null,detailThumb = null;
    private ScaleGestureDetector scaleGestureDetector;
    public static File storageDir;


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
       // setHasOptionsMenu(true);
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

         storageDir = new File(Environment.getExternalStorageDirectory()
                + "/Ziae Magazine");

        if(ConnectionStatus.getInstance(context).isOnline()){

            if(this.mPage == 0){
                setHasOptionsMenu(true);
                getHomeData();

                mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {
                        refreshContent();
                    }
                });

            }else {

                setHasOptionsMenu(true);
                getProductDetails();


            }


        } else{
           // Toast.makeText(context, Constants.networkerror,Toast.LENGTH_SHORT).show();
            if(mPage == 0){
                getHomeDataLocalDb();
            }else{
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
                        for(int i=0;i<home_model.getProductsDetails().size();i++){
                            Log.e("Name,Des",home_model.getProductsDetails().get(i).getId());
                            MainActivity.sharelist.add(new ShareModel(home_model.getProductsDetails().get(i).getName(),
                                    String.valueOf(Html.fromHtml(home_model.getProductsDetails().get(i).getDescription()))));
                        }

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
                                }  else if(GlobalCalls.GlobalMonth != GlobalCalls.retriveMonth(context,GlobalCalls.MONTH_KEY)) {
                                    saveImage(home_model);
                                }else{}

                            } else {}
                        } else {
                                if(Constants.checkFirst == true){
                                    Log.e("CheckFirst","TRUE");
                                    saveImage(home_model);
                                    Constants.checkFirst = false;
                                }
                                else if(GlobalCalls.GlobalMonth != GlobalCalls.retriveMonth(context,GlobalCalls.MONTH_KEY)){
                                    saveImage(home_model);
                                }else{}
                        }
                    }else{}
                }else{
                    Toast.makeText(context,Constants.server_error,Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Home_Model> call, Throwable t) {
                Toast.makeText(context,Constants.server_error,Toast.LENGTH_SHORT).show();
                getHomeDataLocalDb();
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
                           listItems.clear();
                           listItems.addAll(product_model.getDetails());
                           detailAdapter = new DetailAdapter(context,listItems);
                           recyclerView.setAdapter(detailAdapter);
                           GestureListener();
//                           sharelist.add(new ShareModel(product_model.getDetails().get(0).getName(),
//                                                        String.valueOf(Html.fromHtml(product_model.getDetails().get(0).getDescription()))));

                        } else {
                            Toast.makeText(context, Constants.null_data, Toast.LENGTH_SHORT).show();
                        }
                    } else {
                      //  getProductDetailLocalDb(MainActivity.listItems.get(mPage).getName());
                    }
                }

            }

            @Override
            public void onFailure(Call<Product_Model> call, Throwable t) {
                Toast.makeText(context, Constants.server_error, Toast.LENGTH_SHORT).show();
                getProductDetailLocalDb(MainActivity.listItems.get(mPage).getName());
            }
        });

    }
    //Menu Items Override
    @SuppressLint("LongLogTag")
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        if(tabPosition == 0 && MainActivity.checkFirst == true){
                MainActivity.toolbar.getMenu().clear();
                inflater.inflate(R.menu.search,menu);
                Constants.shareCheck = true;
                MainActivity.checkFirst = false;
        }
        else if(Constants.shareCheck == true){
            MainActivity.toolbar.getMenu().clear();
            inflater.inflate(R.menu.share,menu);
        }
        super.onCreateOptionsMenu(menu,inflater);
    }
    //Home Adapter Listener
    @Override
    public void getHomeDetails(View view, HomeData_Model homeModel) {
        Log.e("HomeDetailListener","true");
        setHasOptionsMenu(false);
        Constants.shareCheck = true;
        setHasOptionsMenu(true);
        listItems.clear();
        topic_name = homeModel.getName();
        description = String.valueOf(Html.fromHtml(homeModel.getDescription()));
        listItems.add(homeModel);
        detailAdapter = new DetailAdapter(context,listItems);
        recyclerView.setAdapter(detailAdapter);
        GestureListener();

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
                            listItem.add(new HomeData_Model(cursor.getString(0),cursor.getString(1),
                                    ImageDownloader.retriveImagePath(cursor.getString(0)),cursor.getString(3)));
                            MainActivity.sharelist.add(new ShareModel(cursor.getString(1),
                                    String.valueOf(Html.fromHtml(cursor.getString(3)))));


                        }while(cursor.moveToNext());
                        homeadapter =  new AllContent_Adapter(context,listItem);
                        recyclerView.setAdapter(homeadapter);
                        homeadapter.setItemClickListener(PageFragment.this);
                    }
                } else{}
            }catch (Exception e){e.printStackTrace();}
        }
    }

    private void getProductDetailLocalDb(String name){
                Cursor cursor;
                listItems = new ArrayList<>();
                dbHelper = new DBHelper(context);

                try {

                    cursor = dbHelper.getAllProductData();

                    if(cursor != null){

                        cursor.moveToFirst();

                        if(cursor.getCount() > 0){
                            progressBar.setVisibility(View.GONE);
                            do{
                                String tab = cursor.getString(1);
                                if(name.replace(" ","").equalsIgnoreCase(tab.replace(" ",""))){
                                    listItems.add(new HomeData_Model(cursor.getString(0),cursor.getString(1),
                                            ImageDownloader.retriveImagePath(cursor.getString(0)),cursor.getString(3)));
                                    detailAdapter = new DetailAdapter(context,listItems);
                                    recyclerView.setAdapter(detailAdapter);
                                    GestureListener();
                                    break;
                                }
                            }while(cursor.moveToNext());
                        }
                    } else{}
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


    public void GestureListener(){

        scaleGestureDetector = new ScaleGestureDetector(context,
                new simpleOnScaleGestureListener(detailAdapter,recyclerView));

        recyclerView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                int pointers = MotionEventCompat.getPointerCount(event);
                //  Log.e("TOUCH", "UPPER ON TOUCH CALLED:::" + pointers);
                if (pointers > 0) {
                    scaleGestureDetector.onTouchEvent(event);
                }
                return false;
            }
        });
    }




}


