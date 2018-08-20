package ziaetaiba.com.zia_e_magazine.Adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.SparseArray;

import ziaetaiba.com.zia_e_magazine.Activites.MainActivity;
import ziaetaiba.com.zia_e_magazine.Fragments.PageFragment;


public class MyPagerAdapter extends FragmentStatePagerAdapter {

    SparseArray<PageFragment> registeredFragments = new SparseArray<>();

    public MyPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public String getPageTitle(int position) {
        return MainActivity.listItems.get(position).getName();
    }

    @Override
    public int getCount() {
        if (MainActivity.listItems.size() > 0) {
            return MainActivity.listItems.size();
        }
        return 0;
    }


    @Override
    public Fragment getItem(int position) {

        if (position == 0) {
            return PageFragment.newInstance(position);
        } else {
            String[] name = MainActivity.listItems.get(position).getExtra().split("/");
            if(name != null){
                return PageFragment.newInstance(position,name[2]);
            }else{
                return null;
            }
        }

    }

    public PageFragment pageChanged(int position) {
        String[] name = MainActivity.listItems.get(position).getExtra().split("/");
        return PageFragment.newInstance(position,name[2]);
    }



}
