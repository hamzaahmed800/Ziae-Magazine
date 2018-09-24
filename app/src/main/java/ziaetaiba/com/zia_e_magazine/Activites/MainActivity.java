package ziaetaiba.com.zia_e_magazine.Activites;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
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
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import ziaetaiba.com.zia_e_magazine.Database.DBHelper;
import ziaetaiba.com.zia_e_magazine.Fragments.PageFragment;
import ziaetaiba.com.zia_e_magazine.Fragments.PreviousMagazineFragment;
import ziaetaiba.com.zia_e_magazine.Fragments.SearchFragment;
import ziaetaiba.com.zia_e_magazine.Globals.ConnectionStatus;
import ziaetaiba.com.zia_e_magazine.Globals.Constants;
import ziaetaiba.com.zia_e_magazine.Globals.GlobalCalls;
import ziaetaiba.com.zia_e_magazine.Interface.ApiInterface;
import ziaetaiba.com.zia_e_magazine.Models.MenuData_Model;
import ziaetaiba.com.zia_e_magazine.Models.Menu_Model;
import ziaetaiba.com.zia_e_magazine.Models.ShareModel;
import ziaetaiba.com.zia_e_magazine.R;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    public static boolean checkFirst = false;
    public NavigationView navigationView;
    public static Toolbar toolbar;
    public static List<MenuData_Model> listItems;
    private PagerSlidingTabStrip tabs;
    private ViewPager pager;
    private MyPagerAdapter adapter;
    private Typeface headingFont,typeFace;
    private TextView toolbarText,t_textView;
    private DBHelper dbHelper;
    private SharedPreferences sharedPreferences;
    private Typeface title_header;
    private Menu_Model menu_model;
    public static List<ShareModel> sharelist;
    private ImageView imageViewheader;


    @SuppressLint("ResourceAsColor")
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (Constants.language.equals("ur")) {
            getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
        }



        sharedPreferences = getSharedPreferences(GlobalCalls.MY_PREFS_NAME, MODE_PRIVATE);

        toolbar = findViewById(R.id.toolbar);
        toolbarText = findViewById(R.id.toolbar_title);


        title_header = Typeface.createFromAsset(this.getAssets(), "Times New Roman.ttf");
        if(Constants.language.equals("ur")){
            typeFace = Typeface.createFromAsset(this.getAssets(), "Mehr Nastaliq.ttf");
            headingFont = Typeface.createFromAsset(this.getAssets(), "Mehr Nastaliq.ttf");
        }else{
            typeFace = Typeface.createFromAsset(this.getAssets(), "Times New Roman.ttf");
            headingFont = Typeface.createFromAsset(this.getAssets(), "Times New Roman.ttf");
        }
        toolbarText.setTypeface(typeFace,Typeface.BOLD);
//        Constants.magzine_month_year = GlobalCalls.getMonthNameUR(Constants.month_type);
//        toolbarText.setText(Constants.toolbarTitle());
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        adapter = new MyPagerAdapter(getSupportFragmentManager());

        tabs = findViewById(R.id.tabs);
        pager = findViewById(R.id.pager);

        listItems = new ArrayList<>();
        sharelist = new ArrayList<>();


        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();


        navigationView = findViewById(R.id.nav_view);
        View headerView =  navigationView.getHeaderView(0);
        Log.e("HeaderCount", String.valueOf(navigationView.getHeaderCount()));




    //    navigationView.setItemIconTintList(null);
        if (Constants.language.equals("ur")) {
            navigationView.getMenu().clear();
            navigationView.inflateMenu(R.menu.activity_main_drawer_ur);
            navigationView.inflateHeaderView(R.layout.nav_header_main_ur);
//            t_textView = headerView.findViewById(R.id.t_textview_ur);
//            t_textView.setTypeface(title_header);
//            t_textView.setText("Magazine ZiaeTaiba");
//            imageViewheader = headerView.findViewById(R.id.imageView_ur);
//            imageViewheader.setImageResource(R.drawable.magazine_ziaetaiba);

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
//            t_textView = headerView.findViewById(R.id.t_textview);
//            t_textView.setTypeface(title_header);
//            t_textView.setText("Magazine ZiaeTaiba");
//            imageViewheader = headerView.findViewById(R.id.imageView);
//            imageViewheader.setImageResource(R.drawable.magazine_ziaetaiba);

            Menu m = navigationView.getMenu();
            for (int i=0;i<m.size();i++) {
                MenuItem mi = m.getItem(i);
                //Method for setting Typface and size of NavigationView Items
                applyFontToMenuItem(mi);
            }

        }
        navigationView.setNavigationItemSelectedListener(this);


        tabs.setDividerColor(Color.WHITE);
        tabs.setDividerPadding(20);
        tabs.setIndicatorColor(R.color.colorPrimaryDark);//getResources().getColor(R.color.colorPrimaryDark)
        tabs.setIndicatorHeight(15);
        tabs.setTextSize(55);
        tabs.setTextColor(Color.WHITE);
        tabs.setTypeface(headingFont, Typeface.BOLD);
        //Fetching Menus
        if(ConnectionStatus.getInstance(this).isOnline()){
              getMenusServerData();
        }else{
            getMenusLocalData();
        }


        //Pager Listener
        pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @SuppressLint("ResourceAsColor")
            @Override
            public void onPageSelected(int position) {
            if(position == 0){
                checkFirst = true;
                pager.setAdapter(adapter);
            }
            PageFragment.tabPosition = position;
            if(position > 0){
                Constants.shareCheck = true;
                adapter.pageChanged(position);

            }
            //Tab TextColor
            LinearLayout linearLayout = (LinearLayout) tabs.getChildAt(0);
            for(int i = 0 ;i < listItems.size();i++){
                if(position == i){
                    TextView textView = (TextView) linearLayout.getChildAt(i);
                    textView.setTextColor(R.color.colorPrimaryDark);

                }else{
                    TextView textView = (TextView) linearLayout.getChildAt(i);
                    textView.setTextColor(Color.WHITE);}
            }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        //Animation between Tabs
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
            Constants.shareCheck = false;
            Null();
        } else if (pager.getCurrentItem() == 1) {
            Null();
            Constants.shareCheck = false;
            pager.setCurrentItem(0);
        } else if (pager.getCurrentItem() == 0) {
            if(Constants.backStack == true ){
                    Null();
                    Constants.shareCheck = false;
                    SearchFragment.searchIsCheck = false;
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
        if(Constants.shareCheck == false){
            getMenuInflater().inflate(R.menu.search,menu);
        }else if(Constants.shareCheck == false){
            toolbar.getMenu().clear();
            getMenuInflater().inflate(R.menu.search,menu);
        }

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

        }else if(id == R.id.share_setting){

            if((PageFragment.description != null && PageFragment.topic_name!= null) || sharelist != null
                    && Constants.shareCheck == true){
                Intent i = new Intent(Intent.ACTION_SEND);
                i.setType("text/plain");
                if(PageFragment.tabPosition == 0){
                    i.putExtra(Intent.EXTRA_SUBJECT, PageFragment.topic_name);
                    i.putExtra(Intent.EXTRA_TEXT,PageFragment.description);
                }else{
                    Log.e("ListSize", String.valueOf(PageFragment.listItem.size()));
                   // Log.e("TabPosition", String.valueOf(PageFragment.tabPosition-1));
                    i.putExtra(Intent.EXTRA_SUBJECT, sharelist.get(PageFragment.tabPosition-1).getName());
                    i.putExtra(Intent.EXTRA_TEXT,String.valueOf(sharelist.get(PageFragment.tabPosition-1).getDescription()));
                }

                //Constants.shareCheck = false;
                startActivity(Intent.createChooser(i, "Share Using"));

            }else if(PageFragment.detailDesc != null && PageFragment.detailTpName != null  && Constants.shareCheck == true){
                Intent i = new Intent(Intent.ACTION_SEND);
                i.setType("text/plain");
                i.putExtra(Intent.EXTRA_SUBJECT, PageFragment.detailTpName);
                i.putExtra(Intent.EXTRA_TEXT, PageFragment.detailDesc);
              //  Constants.shareCheck = false;
                startActivity(Intent.createChooser(i, "Share Using"));

            }else{
               // Toast.makeText(this,"Open Detail",Toast.LENGTH_SHORT).show();
            }
            return true;
        }else if(id == R.id.search_item){
            pager.setCurrentItem(0);
            Constants.backStack = true;
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.add(R.id.PAGEFragment, new SearchFragment()).commit();
            return true;
        }else{

        }

        return super.onOptionsItemSelected(item);
    }

    //Navigation drawer Items
    @SuppressLint("ResourceType")
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        if (id == R.id.nav_home){

            pager.setCurrentItem(0);
            pager.setAdapter(adapter);
            tabs.setViewPager(pager);
            return true;

        }else if (id == R.id.nav_latest){
            Constants.backStack = false;
            Log.e("Month",GlobalCalls.retriveMonth(getApplicationContext(),GlobalCalls.MONTH_KEY));
            Constants.month = GlobalCalls.retriveMonth(getApplicationContext(),GlobalCalls.MONTH_KEY);
            toolbar(Constants.month);
            if(ConnectionStatus.getInstance(this).isOnline()){
                getMenusServerData();
            }else{
                listItems.clear();
                getMenusLocalData();
            }
            return true;

        }else if (id == R.id.nav_old){
            pager.setCurrentItem(0);
            if(ConnectionStatus.getInstance(this).isOnline()){
                Constants.backStack = true;
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                ft.add(R.id.PAGEFragment, new PreviousMagazineFragment()).commit();
            }else {
                Toast.makeText(this,Constants.internet_require,Toast.LENGTH_SHORT).show();
            }
            return true;

        }else if (id == R.id.nav_offical_web){
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.ziaetaiba.com/"));
                overridePendingTransition(0, 0);
                startActivity(intent);
            return true;

        } else if (id == R.id.nav_contact_us){
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.ziaetaiba.com/ur/contact-us"));
            overridePendingTransition(0, 0);
            startActivity(intent);
            return true;
        }

        return false;
    }


    // Fetching Menu From Server
    public void getMenusServerData() {
        Retrofit retrofit = Connect_Server.getApiClient();
        ApiInterface clientAPIs = retrofit.create(ApiInterface.class);
        Call<Menu_Model> call = clientAPIs.getMenu(Constants.language,Constants.year,Constants.month);
        call.enqueue(new Callback<Menu_Model>() {
            @Override
            public void onResponse(Call<Menu_Model> call, Response<Menu_Model> response) {
                if (response.isSuccessful()) {

                    menu_model = response.body();
                    Constants.month = menu_model.getMonth();
                    Constants.year = menu_model.getYear();
                    toolbar(Constants.month);

                    if(sharedPreferences.getString(GlobalCalls.MONTH_KEY,null) == null) {
                     //  Log.e("SharepreferenceMonth",Constants.month);
                        GlobalCalls.saveMonth(getApplicationContext(), Constants.month, Constants.month_type);
                    }

                    if (menu_model != null && menu_model.getMenus().size()>0) {
                        Log.e("SERVER----->","CALLED");
                        listItems.clear();
                        // Fetching data and setting in Adapter
                        listItems.add(new MenuData_Model("1", "ہوم"));
                        listItems.addAll(menu_model.getMenus());
                        pager.setAdapter(adapter);
                        final int pageMargin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 10, getResources()
                                .getDisplayMetrics());
                        pager.setPageMargin(pageMargin);
                        tabs.setViewPager(pager);


                    } else {
//                        Constants.magzine_month_year = GlobalCalls.getMonthNameUR(Constants.month_type);
//                        toolbarText.setText(Constants.toolbarTitle());
//                        getMenusLocalData();
                    }

                } else {
                    Toast.makeText(getApplicationContext(), Constants.server_error, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Menu_Model> call, Throwable t) {
                Toast.makeText(getApplicationContext(), Constants.server_error, Toast.LENGTH_SHORT).show();
                getMenusLocalData();
            }
        });

    }

    public void getMenusLocalData(){
        toolbar(GlobalCalls.retriveMonth(getApplicationContext(),GlobalCalls.MONTH_KEY));
        if(listItems == null || listItems.size() == 0){

            Cursor cursor;
            listItems = new ArrayList<>();
            dbHelper = new DBHelper(this);

            try {

                cursor = dbHelper.getAllMenuData();

                if(cursor != null){

                    cursor.moveToFirst();

                    if(cursor.getCount() > 0){
                        Log.e("Local DB---->","CALLED");
                        // listItems.add(new MenuData_Model("1","ہوم"));
                        do{
//                            Log.e("ID---->",cursor.getString(0));
//                            Log.e("NAME---->",cursor.getString(1));
//                            Log.e("EXTRA---->",cursor.getString(2));
                            listItems.add(new MenuData_Model(cursor.getString(0),
                                    cursor.getString(1),
                                    cursor.getString(2)));


                        }while(cursor.moveToNext());

                        pager.setAdapter(adapter);
                        final int pageMargin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 4, getResources()
                                .getDisplayMetrics());
                        pager.setPageMargin(pageMargin);
                        tabs.setViewPager(pager);
                    }

                } else{
                   // Toast.makeText(this,Constants.internet_require,Toast.LENGTH_SHORT).show();
                }


            }catch (Exception e){e.printStackTrace();}



        }else{
         //   Toast.makeText(getApplicationContext(), Constants.internet_require, Toast.LENGTH_SHORT).show();
        }
    }

    //Setting Menu Font TypeFace
    private void applyFontToMenuItem(MenuItem mi) {
        Typeface font = null;
        if(Constants.language.equals("ur")){
            font = Typeface.createFromAsset(getAssets(), "Mehr Nastaliq.ttf");
        }else{
            font = Typeface.createFromAsset(getAssets(), "Times New Roman.ttf");
        }
        SpannableString mNewTitle = new SpannableString(mi.getTitle());
        mNewTitle.setSpan(new CustomTypefaceSpan("" , font,52), 0 , mNewTitle.length(),  Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        mi.setTitle(mNewTitle);
    }

    public void toolbar(String month ){
        GlobalCalls.getMonth(month);
        Constants.magzine_month_year = GlobalCalls.getMonthNameUR(Constants.month_type);
        toolbarText.setText(Constants.toolbarTitle());

    }

    private void Null(){
        PageFragment.topic_name = null; PageFragment.detailTpName = null;
        PageFragment.description = null; PageFragment.detailDesc = null;
        PageFragment.Thumb = null; PageFragment.detailThumb = null;
    }


}
