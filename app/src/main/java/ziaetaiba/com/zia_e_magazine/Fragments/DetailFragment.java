package ziaetaiba.com.zia_e_magazine.Fragments;


import android.support.v4.app.Fragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class DetailFragment extends Fragment {

//    private String mTab;
//    private int position;
//    private TextView headText, descriptionText;
//    private ImageView headingImage, imageView1, imageView2, imageView3, imageView4, imageView5;
//    private Typeface headingFont = null;
//    private Typeface DescriptionFont = null;
//    private HomeData_Model homeDataModel;
//    private boolean isFragmentLoaded = false;
//    private List<ProductData_Model> listItems;
//    private RecyclerView recyclerView;
//    private DetailAdapter adapter;
//    public static final String ARG_POSITION = "ARG_POSITION";
//    public static final String ARG_TAB = "ARG_TAB";
//    public  View view;
//    private ProgressBar progressBar;
//
//    public DetailFragment() {
//        // Required empty public constructor
//    }
//
//    @SuppressLint("ValidFragment")
//    public DetailFragment(int position, String mTab) {
//        this.mTab = mTab;
//        this.position = position;
//    }
//
//    public static DetailFragment DetailNewInstance(int position, String mTab) {
//        Bundle args = new Bundle();
//        args.putInt(ARG_POSITION, position);
//        args.putString(ARG_TAB, mTab);
//        DetailFragment fragment = new DetailFragment();
//        fragment.setArguments(args);
//        return fragment;
//
//    }
//
//    @Override
//    public void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        position = getArguments().getInt(ARG_POSITION);
//        mTab = getArguments().getString(ARG_TAB);
//
//    }
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//
//        if (container != null) {
//            container.removeAllViews();
//        }
//
//        view = inflater.inflate(R.layout.fragment_detail, container, false);
//        listItems = new ArrayList<>();
//        progressBar = view.findViewById(R.id.detailprogressBar);
//        headText = view.findViewById(R.id.detailheadingText);
//        headingImage = view.findViewById(R.id.headImage);
//
//        descriptionText = view.findViewById(R.id.descriptionText);
////        imageView1 = view.findViewById(R.id.articleImage1);
////        imageView2 = view.findViewById(R.id.articleImage2);
////        imageView3 = view.findViewById(R.id.articleImage3);
////        imageView4 = view.findViewById(R.id.articleImage4);
////        imageView5 = view.findViewById(R.id.articleImage5);
//
//        // For Heading
//        if (Constants.language.equals("ur")) {
//
//            headingFont = Typeface.createFromAsset(getActivity().getAssets(), "Aslam.ttf");
//            headText.setTypeface(headingFont);
//        } else {
//            headingFont = Typeface.createFromAsset(getActivity().getAssets(), "Arial.ttf");
//            headText.setTypeface(headingFont);
//        }
//
//        //For Description
//        if (Constants.language.equals("ur")) {
//            DescriptionFont = Typeface.createFromAsset(getActivity().getAssets(), "Jameel_Noori_Nastaleeq.ttf");
//            descriptionText.setTypeface(DescriptionFont);
//        } else {
//            DescriptionFont = Typeface.createFromAsset(getActivity().getAssets(), "Times New Roman.ttf");
//            descriptionText.setTypeface(DescriptionFont);
//        }
//
//        if (position == 0) {
//            Bundle bundle = getArguments();
//            homeDataModel = (HomeData_Model) bundle.getSerializable("HOME_MODEL");
//
//            if (homeDataModel != null) {
//                headText.setText(homeDataModel.getName());
//                Glide.with(getContext()).load(homeDataModel.getThumbnailPath()).listener(new RequestListener<String, GlideDrawable>() {
//                    @Override
//                    public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
//               //         progressBar.setVisibility(View.VISIBLE);
//                        return false;
//                    }
//
//                    @Override
//                    public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
//                 //       progressBar.setVisibility(View.GONE);
//                        return false;
//                    }
//                }).into(headingImage);
//                descriptionText.setText(homeDataModel.getDescription());
//            }
//
//        }else if(position > 0){
//            getProductDetails();
//        }
//        return view;
//    }
//
//    @Override
//    public void setUserVisibleHint(boolean isVisibleToUser) {
//        super.setUserVisibleHint(isVisibleToUser);
//        if(isVisibleToUser){
//            getProductDetails();
//        }
//    }
//
//    // GET PRODUCT DETAILS METHOD
//    public void getProductDetails() {
//        Retrofit retrofit = Connect_Server.getApiClient();
//        ApiInterface clientAPIs = retrofit.create(ApiInterface.class);
//        Call<Product_Model> call = clientAPIs.getDetails(Constants.language, Constants.year, Constants.month, mTab);
//        call.enqueue(new Callback<Product_Model>() {
//            @Override
//            public void onResponse(Call<Product_Model> call, Response<Product_Model> response) {
//                if (response.isSuccessful()) {
//                    Product_Model product_model = response.body();
//                    if (product_model != null) {
//                        if (product_model != null) {
//                            headText.setText(product_model.getDetails().get(0).getName());
//                            Glide.with(getContext()).load(product_model.getDetails().get(0).getThumbnailPath()).listener(new RequestListener<String, GlideDrawable>() {
//                                @Override
//                                public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
//                                    progressBar.setVisibility(View.VISIBLE);
//                                    return false;
//                                }
//
//                                @Override
//                                public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
//                                    progressBar.setVisibility(View.GONE);
//                                    return false;
//                                }
//                            }).into(headingImage);
//                            descriptionText.setText(product_model.getDetails().get(0).getDescription());
//                        } else {
//                            Toast.makeText(getActivity(), Constants.null_data, Toast.LENGTH_SHORT).show();
//                        }
//                    } else {
//                        Toast.makeText(getActivity(), Constants.server_error, Toast.LENGTH_SHORT).show();
//                    }
//                }
//
//            }
//
//            @Override
//            public void onFailure(Call<Product_Model> call, Throwable t) {
//                Toast.makeText(getContext(), Constants.networkerror, Toast.LENGTH_SHORT).show();
//            }
//        });
//
//    }
}


