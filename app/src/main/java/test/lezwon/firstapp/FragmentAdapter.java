package test.lezwon.firstapp;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import java.util.ArrayList;

/**
 * Created by Lezwon on 15-05-2016.
 * This class manages and serves all the necessary fragments for the VIewPager
 * in the HomeActivity. The basci purpose of this class is to act as an interface
 * and provider between the view and the data.
 *
 * All Fragments are stored in fragmentArrayList. The fragmentArrayList is initialized
 * in the constructor. The getItem function server the requested fragment by its position.
 * The count function returns the number of fragments.
 */

class FragmentAdapter extends FragmentPagerAdapter{

    private ArrayList<Fragment> fragmentArrayList;

    FragmentAdapter(FragmentManager fm) {
        super(fm);
        fragmentArrayList = new ArrayList<>(3);
        fragmentArrayList.add(new FragmentA());
        fragmentArrayList.add(new FragmentB());
        fragmentArrayList.add(new FragmentC());
    }

    public Fragment getItem(int position) {
        return fragmentArrayList.get(position);
    }

    @Override
    public int getCount() {
        return fragmentArrayList.size();
    }

}
