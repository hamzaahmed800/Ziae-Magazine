package ziaetaiba.com.zia_e_magazine.Activites;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Spannable;
import android.text.SpannableString;
import android.util.Log;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.astuetz.PagerSlidingTabStrip;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import ziaetaiba.com.zia_e_magazine.Adapter.MyPagerAdapter;
import ziaetaiba.com.zia_e_magazine.Connection.Connect_Server;
import ziaetaiba.com.zia_e_magazine.CustomAnimation.CubeOutRotationTransformation;
import ziaetaiba.com.zia_e_magazine.CustomFont.CustomTypefaceSpan;
import ziaetaiba.com.zia_e_magazine.Fragments.PreviousMagazineFragment;
import ziaetaiba.com.zia_e_magazine.Globals.Constants;
import ziaetaiba.com.zia_e_magazine.Interface.ApiInterface;
import ziaetaiba.com.zia_e_magazine.Models.MenuData_Model;
import ziaetaiba.com.zia_e_magazine.Models.Menu_Model;
import ziaetaiba.com.zia_e_magazine.R;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    public NavigationView navigationView;
    public Toolbar toolbar;
    public static List<MenuData_Model> listItems;
    private PagerSlidingTabStrip tabs;
    private ViewPager pager;
    private MyPagerAdapter adapter;
    private Typeface headingFont,typeFace;
    private TextView toolbarText;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = findViewById(R.id.toolbar);
        toolbarText = findViewById(R.id.toolbar_title);
        if(Constants.language.equals("ur")){
            typeFace = Typeface.createFromAsset(this.getAssets(), "Jameel_Noori_Nastaleeq.ttf");
        }else{
            typeFace = Typeface.createFromAsset(this.getAssets(), "Times New Roman.ttf");
        }
        toolbarText.setTypeface(typeFace,Typeface.BOLD);
        toolbarText.setText(Constants.toolbarTitle());
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        adapter = new MyPagerAdapter(getSupportFragmentManager());

        tabs = findViewById(R.id.tabs);
        pager = findViewById(R.id.pager);

        // ye adapter initialize hha hy adapater main fragment knsa bhej rahe ho? run karo app
        //HomeFragment main Fragment hy but is se home fragment ka object pass nahi hoga na.
        headingFont = Typeface.createFromAsset(this.getAssets(), "Aslam.ttf");
        listItems = new ArrayList<>();


        if (Constants.language.equals("ur")) {
            getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);

        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();


        navigationView = findViewById(R.id.nav_view);
        if (Constants.language.equals("ur")) {
            navigationView.getMenu().clear();
            navigationView.inflateMenu(R.menu.activity_main_drawer_ur);
            navigationView.inflateHeaderView(R.layout.nav_header_main_ur);
            Menu m = navigationView.getMenu();
            for (int i=0;i<m.size();i++) {
                MenuItem mi = m.getItem(i);
                //Method for setting Typface and size of NavigationView Items
                applyFontToMenuItem(mi);
            }

        } else {

            navigationView.getMenu().clear();
            navigationView.inflateMenu(R.menu.activity_main_drawer);
            navigationView.inflateHeaderView(R.layout.nav_header_main);
            Menu m = navigationView.getMenu();
            for (int i=0;i<m.size();i++) {
                MenuItem mi = m.getItem(i);
                //Method for setting Typface and size of NavigationView Items
                applyFontToMenuItem(mi);
            }

        }
        navigationView.setNavigationItemSelectedListener(this);
        //Fetching Menus
        getMenusData();
        tabs.setIndicatorColor(Color.WHITE);
        tabs.setTextColor(Color.WHITE);
        tabs.setTypeface(headingFont, Typeface.NORMAL);
        adapter.notifyDataSetChanged();
        pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if(position == 0){
                    pager.setAdapter(adapter);
                }
            if(position > 0){
                adapter.pageChanged(position);
            }

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        CubeOutRotationTransformation cubeOutRotationTransformation = new CubeOutRotationTransformation();
        pager.setPageTransformer(true,cubeOutRotationTransformation);



    }

    @Override
    public void onBackPressed() {

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else if (pager.getCurrentItem() > 0) {
            pager.setCurrentItem(0);
        } else if (pager.getCurrentItem() == 1) {
            pager.setCurrentItem(0);
        } else if (pager.getCurrentItem() == 0) {
            if(Constants.backStack == true){
                Constants.backStack = false;
                pager.setAdapter(adapter);
                tabs.setViewPager(pager);
            }else{
                super.onBackPressed();
                System.exit(0);
            }

        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        } else if (id == R.id.action_language) {
//            Constants.language_type = 1;
//            this.finish();
//            startActivity(getIntent());

        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        int id = item.getItemId();

        if (id == R.id.nav_home) {

            pager.setAdapter(adapter);
            tabs.setViewPager(pager);

        } else if (id == R.id.nav_latest) {

        } else if (id == R.id.nav_old) {
            pager.setCurrentItem(0);
            Constants.backStack = true;
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.add(R.id.PAGEFragment, new PreviousMagazineFragment());
            ft.addToBackStack(null);
            ft.commit();

        } else if (id == R.id.nav_offical_web) {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://magazine.ziaetaiba.com/"));
            startActivity(intent);

        } else if (id == R.id.nav_contact_us) {

        }


        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    // Fetching Menu From Server
    public void getMenusData() {
        Retrofit retrofit = Connect_Server.getApiClient();
        ApiInterface clientAPIs = retrofit.create(ApiInterface.class);
        Log.e("Month",Constants.month);
        Call<Menu_Model> call = clientAPIs.getMenu(Constants.language, Constants.year, Constants.month);
        call.enqueue(new Callback<Menu_Model>() {
            @Override
            public void onResponse(Call<Menu_Model> call, Response<Menu_Model> response) {
                if (response.isSuccessful()) {

                    Menu_Model menu_model = response.body();
                    if (menu_model != null) {
                        // Fetching data and setting in Adapter
                        listItems.add(0, new MenuData_Model("1", "ہوم"));
                        listItems.addAll(menu_model.getMenus());
                        pager.setAdapter(adapter);
                        final int pageMargin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 4, getResources()
                                .getDisplayMetrics());
                        pager.setPageMargin(pageMargin);
                        tabs.setViewPager(pager);


                    } else {
                        Toast.makeText(getApplicationContext(), Constants.null_data, Toast.LENGTH_SHORT).show();
                    }

                } else {
                    Toast.makeText(getApplicationContext(), Constants.server_error, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Menu_Model> call, Throwable t) {
                Toast.makeText(getApplicationContext(), Constants.networkerror, Toast.LENGTH_SHORT).show();
            }
        });

    }

    //Setting Menu Font TypeFace
    public void applyFontToMenuItem(MenuItem mi) {
        Typeface font = null;
        if(Constants.language.equals("ur")){
            font = Typeface.createFromAsset(getAssets(), "Jameel_Noori_Nastaleeq.ttf");
        }else{
            font = Typeface.createFromAsset(getAssets(), "Times New Roman.ttf");
        }
        SpannableString mNewTitle = new SpannableString(mi.getTitle());
        mNewTitle.setSpan(new CustomTypefaceSpan("" , font,50), 0 , mNewTitle.length(),  Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        mi.setTitle(mNewTitle);
    }


}
