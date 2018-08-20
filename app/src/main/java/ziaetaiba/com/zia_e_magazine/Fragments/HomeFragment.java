package ziaetaiba.com.zia_e_magazine.Fragments;


import android.annotation.SuppressLint;
import android.support.v4.app.Fragment;

/**
 * A simple {@link Fragment} subclass.
 */
@SuppressLint("ValidFragment")
public class HomeFragment extends Fragment {

//    public RecyclerView recyclerView;
//    public AllContent_Adapter adapter;
//    public List<HomeData_Model> listItems;
//    public int position;
//    public SwipeRefreshLayout mSwipeRefreshLayout;
//    public ProgressBar progressBar;
//    public View v;
//    public HomeFragment(){
//
//    }
//
//    @SuppressLint("ValidFragment")
//    public HomeFragment(int position){
//        this.position = position;
//    }
//
//    @SuppressLint("ResourceAsColor")
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        if(container != null){
//            container.removeAllViews();
//        }
//        v = inflater.inflate(R.layout.fragment_content, container, false);
//        mSwipeRefreshLayout = v.findViewById(R.id.activity_main_swipe_refresh_layout);
//        mSwipeRefreshLayout.setColorSchemeColors(R.color.colorPrimary);
//        progressBar = v.findViewById(R.id.contentprogressBar);
//        listItems = new ArrayList<>();
//
//
//        if (ConnectionStatus.getInstance(getContext()).isOnline()) {
//            //Fetcing data
//            //setting data into recyclerview
//
//            if(this.position == 0){
//                Log.e("HomeFragment", String.valueOf(position));
//                getHomeData();
//            }else if(this.position > 0){
//                Log.e("HomeFragment", String.valueOf(position));
//            }
//
//
//            mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
//                @Override
//                public void onRefresh() {
//                   refreshContent();
//                }
//            });
//
//        }
//        else{
//            Toast.makeText(getContext(), Constants.networkerror,Toast.LENGTH_SHORT).show();
//        }
//
//        recyclerView =  v.findViewById(R.id.fragment_Recyclerview);
//        recyclerView.setHasFixedSize(true);
//        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
//
//            return v;
//        }
//
//    private void getHomeData() {
//
//        Retrofit retrofit = Connect_Server.getApiClient();
//        ApiInterface clientAPIs = retrofit.create(ApiInterface.class);
//
//        Call<Home_Model> call = clientAPIs.getHomeData(Constants.language,Constants.year,Constants.month);
//        call.enqueue(new Callback<Home_Model>() {
//            @Override
//            public void onResponse(Call<Home_Model> call, Response<Home_Model> response) {
//                if(response.isSuccessful()){
//                    progressBar.setVisibility(View.GONE);
//                    Home_Model home_model = response.body();
//                    if(home_model != null){
//                        listItems.addAll(home_model.getProductsDetails());
//                        adapter =  new AllContent_Adapter(getContext(),listItems);
//                        adapter.setItemClickListener(HomeFragment.this);
//                        adapter.notifyDataSetChanged();
//                        recyclerView.setAdapter(adapter);
//                    }else{
//                        Toast.makeText(getContext(),Constants.null_data,Toast.LENGTH_SHORT).show();
//                    }
//                }else{
//                    Toast.makeText(getContext(),Constants.server_error,Toast.LENGTH_SHORT).show();
//                }
//            }
//
//            @Override
//            public void onFailure(Call<Home_Model> call, Throwable t) {
//                Toast.makeText(getContext(),Constants.networkerror,Toast.LENGTH_SHORT).show();
//            }
//        });
//    }
//
//    private void refreshContent() {
//        if(ConnectionStatus.getInstance(getContext()).isOnline()) {
//            //Fetcing data
//               getHomeData();
//
//        }
//        //setting Adapter
//        adapter =  new AllContent_Adapter(getContext(),listItems);
//        recyclerView.setAdapter(adapter);
//        adapter.setItemClickListener(HomeFragment.this);
//        adapter.notifyDataSetChanged();
//        mSwipeRefreshLayout.setRefreshing(false);
//    }
//
//    @Override
//    public void getHomeDetails(View view, HomeData_Model homeModel) {
////
////        FragmentTransaction ft =  getActivity().getSupportFragmentManager().beginTransaction();
////        Bundle bundle = new Bundle();
////        DetailFragment detailFragment = new DetailFragment(0);
////        bundle.putSerializable("HOME_MODEL", homeModel);
////        detailFragment.setArguments(bundle);
////        ft.add(R.id.contentFragment,detailFragment).detach(detailFragment).attach(detailFragment);
////        ft.commit();
//
//    }





}
