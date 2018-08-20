package ziaetaiba.com.zia_e_magazine.Fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import ziaetaiba.com.zia_e_magazine.Adapter.AllContent_Adapter;
import ziaetaiba.com.zia_e_magazine.Adapter.DetailAdapter;
import ziaetaiba.com.zia_e_magazine.Calls.homedetailListener;
import ziaetaiba.com.zia_e_magazine.Connection.Connect_Server;
import ziaetaiba.com.zia_e_magazine.Globals.ConnectionStatus;
import ziaetaiba.com.zia_e_magazine.Globals.Constants;
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
    private Context context;

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
            Toast.makeText(context, Constants.networkerror,Toast.LENGTH_SHORT).show();
        }


        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));



        return v;
    }

    private void refreshContent() {
        if(ConnectionStatus.getInstance(context).isOnline()) {
            //Fetcing data
            getHomeData();

        }
        //setting Adapter
        homeadapter =  new AllContent_Adapter(context,listItem);
        recyclerView.setAdapter(homeadapter);
        homeadapter.setItemClickListener(PageFragment.this);
        homeadapter.notifyDataSetChanged();
        mSwipeRefreshLayout.setRefreshing(false);
    }

    //Fetching Home Complete data
    private void getHomeData() {

        Retrofit retrofit = Connect_Server.getApiClient();
        ApiInterface clientAPIs = retrofit.create(ApiInterface.class);
        Call<Home_Model> call = clientAPIs.getHomeData(Constants.language,Constants.year,Constants.month);
        call.enqueue(new Callback<Home_Model>() {
            @Override
            public void onResponse(Call<Home_Model> call, Response<Home_Model> response) {
                if(response.isSuccessful()){
                    progressBar.setVisibility(View.GONE);
                    Home_Model home_model = response.body();
                    if(home_model != null){
                        listItem.addAll(home_model.getProductsDetails());
                        homeadapter =  new AllContent_Adapter(context,listItem);
                        recyclerView.setAdapter(homeadapter);
                        homeadapter.setItemClickListener(PageFragment.this);
                    }else{
                        Toast.makeText(context,Constants.null_data,Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(context,Constants.server_error,Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Home_Model> call, Throwable t) {
                Toast.makeText(context,Constants.networkerror,Toast.LENGTH_SHORT).show();
            }
        });
    }
    //Fetching Tab Details
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
                    if (product_model != null) {
                        if (product_model != null) {
                           listItems.addAll(product_model.getDetails());
                           detailAdapter = new DetailAdapter(context,listItems);
                           recyclerView.setAdapter(detailAdapter);
                        } else {
                            Toast.makeText(context, Constants.null_data, Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(context, Constants.server_error, Toast.LENGTH_SHORT).show();
                    }
                }

            }

            @Override
            public void onFailure(Call<Product_Model> call, Throwable t) {
                Toast.makeText(context, Constants.networkerror, Toast.LENGTH_SHORT).show();
            }
        });

    }


    @Override
    public void getHomeDetails(View view, HomeData_Model homeModel) {
        listItem.clear();
        listItems.add(homeModel);
        detailAdapter = new DetailAdapter(context,listItems);
        recyclerView.setAdapter(detailAdapter);
    }
}
