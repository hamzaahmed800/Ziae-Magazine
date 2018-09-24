package ziaetaiba.com.zia_e_magazine.Fragments;


import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.MotionEventCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import ziaetaiba.com.zia_e_magazine.Activites.MainActivity;
import ziaetaiba.com.zia_e_magazine.Adapter.DetailAdapter;
import ziaetaiba.com.zia_e_magazine.Adapter.SearchAdapter;
import ziaetaiba.com.zia_e_magazine.Calls.homedetailListener;
import ziaetaiba.com.zia_e_magazine.GestureListener.simpleOnScaleGestureListener;
import ziaetaiba.com.zia_e_magazine.Globals.Constants;
import ziaetaiba.com.zia_e_magazine.Models.HomeData_Model;
import ziaetaiba.com.zia_e_magazine.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class SearchFragment extends Fragment implements homedetailListener {

    public static final String TAG = "SEARCH_FRAGMENT";
    private EditText searchText;
    private Button searchButton;
    private ImageView search_imgview;
    private Typeface typeFace;
    private LinearLayout linear;
    private List<HomeData_Model> listItems, searchList;
    private RecyclerView recyclerView;
    private SearchAdapter searchAdapter;
    private DetailAdapter detailAdapter;
    public static String searchString = null;
    public static boolean searchIsCheck = false;
    private ScaleGestureDetector scaleGestureDetector;

    public SearchFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_search, container, false);

        searchText = v.findViewById(R.id.searchText);
        searchButton = v.findViewById(R.id.searchButton);
        linear = v.findViewById(R.id.searchlinearlayout);

        searchList = new ArrayList<>();
        listItems = new ArrayList<>();
        listItems.addAll(PageFragment.listItem);

        recyclerView = v.findViewById(R.id.search_recyclerView);

        if (Constants.language.equals("ur")) {
            typeFace = Typeface.createFromAsset(getActivity().getAssets(), "Mehr Nastaliq.ttf");
            searchButton.setTypeface(typeFace, Typeface.BOLD);
            searchButton.setText(" تلاش کریں");

        } else {
            typeFace = Typeface.createFromAsset(getActivity().getAssets(), "Times New Roman.ttf");
            searchButton.setTypeface(typeFace, Typeface.BOLD);
            searchButton.setText("Search");
        }

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                searchString = searchText.getText().toString();
                if (!TextUtils.isEmpty(searchString)) {
                    Constants.shareCheck = true;
                    searchText();
                } else {
                    Toast.makeText(getContext(), "تلاش کرنے کے لئے کچھ لکھیں", Toast.LENGTH_SHORT).show();
                }

            }
        });


        return v;
    }

    //Searching function
    private void searchText() {
        searchList.clear();
        for (int i = 0; i < listItems.size(); i++) {
            String articleDes = String.valueOf(Html.fromHtml(listItems.get(i).getDescription()));
            if (articleDes.contains(searchString)) {
                searchList.add(listItems.get(i));
            }
        }
        if (searchList.size() == 0) {
            Toast.makeText(getContext(), "تلاش کا نتیجہ نہیں ملا", Toast.LENGTH_SHORT).show();
//            searchList.add(new HomeData_Model("","","",));
        }
        searchAdapter = new SearchAdapter(getContext(), searchList);
        recyclerView.setAdapter(searchAdapter);
        searchAdapter.setItemClickListener(SearchFragment.this);

    }

    @Override
    public void getHomeDetails(View view, HomeData_Model homeModel) {
        setHasOptionsMenu(true);
        linear.setVisibility(View.GONE);
        listItems.clear();
        listItems.add(homeModel);
        searchIsCheck = true;
        PageFragment.topic_name = homeModel.getName();
        PageFragment.description = String.valueOf(Html.fromHtml(homeModel.getDescription()));
        detailAdapter = new DetailAdapter(getContext(), listItems);
        recyclerView.setAdapter(detailAdapter);
        scaleGestureDetector = new ScaleGestureDetector(getContext(),
                new simpleOnScaleGestureListener(detailAdapter, recyclerView));
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

    //Menu Items Override
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        if (Constants.shareCheck == true) {
            MainActivity.toolbar.getMenu().clear();
            inflater.inflate(R.menu.share, menu);
        }
        super.onCreateOptionsMenu(menu, inflater);


    }
}
