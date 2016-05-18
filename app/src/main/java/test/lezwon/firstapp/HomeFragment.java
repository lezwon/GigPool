package test.lezwon.firstapp;


import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {

    private ViewPager viewPager; //slideshow manager
    private TabLayout tabLayout; //tabs title container


    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View layout = inflater.inflate(R.layout.fragment_home, container, false);

        /*TABS INITIATION*/
        tabLayout = (TabLayout) layout.findViewById(R.id.tabLayout);
        tabLayout.addTab(tabLayout.newTab().setText(getString(R.string.tab_1_now)));
        tabLayout.addTab(tabLayout.newTab().setText(getString(R.string.tab_2_assigned)));
        tabLayout.addTab(tabLayout.newTab().setText(getString(R.string.tab_3_applied)));
        tabLayout.setOnTabSelectedListener(new HomeFragment.TabListener());

        /*Connects view pager and adapter*/
        FragmentAdapter fragmentAdapter = new FragmentAdapter(getFragmentManager());
        viewPager = (ViewPager) layout.findViewById(R.id.viewpager);
        viewPager.setAdapter(fragmentAdapter);
        viewPager.addOnPageChangeListener(new HomeFragment.ViewPagerListener());


        return layout;
    }

    private class TabListener implements TabLayout.OnTabSelectedListener{
        @Override
        public void onTabSelected(TabLayout.Tab tab) {
            viewPager.setCurrentItem(tab.getPosition());
        }

        @Override
        public void onTabUnselected(TabLayout.Tab tab) {}

        @Override
        public void onTabReselected(TabLayout.Tab tab) {}
    }

    private class ViewPagerListener implements ViewPager.OnPageChangeListener {

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {}

        @Override
        public void onPageSelected(int position) {
            tabLayout.getTabAt(position).select();
        }

        @Override
        public void onPageScrollStateChanged(int state) {}
    }

}
