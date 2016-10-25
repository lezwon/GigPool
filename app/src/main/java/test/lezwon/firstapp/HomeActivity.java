package test.lezwon.firstapp;

import android.animation.Animator;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.*;
import android.view.animation.LinearInterpolator;
import android.widget.RelativeLayout;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * The Home Activity class displays the activity_home layout. The material
 * design toolbar is implemented through the setSupportActionBar function.
 * This class also implements the Drawer Navigation and Tabs Layout.
 *
 */

public class HomeActivity extends AppCompatActivity {

    @BindView(R.id.container_menu_bottom)
    RelativeLayout container_menu_bottom;

    private boolean bottom_menu_is_active = false;
    private float startValue = 150;
    private float endValue = 0;
    private RecyclerView recyclerView;
    private ViewGroup.LayoutParams layoutParams;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);

        /*material design toolbar*/
        Toolbar toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);

        /*Hide Bottom Menu*/
//        container_menu_bottom.setVisibility(View.INVISIBLE);
        container_menu_bottom.setTranslationY(300);
        initializeDrawer(toolbar);
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        recyclerView = (RecyclerView) getSupportFragmentManager().findFragmentById(R.id.fragment_chat_list).getView();
        layoutParams = recyclerView != null ? recyclerView.getLayoutParams() : null;
    }

    private void initializeDrawer(Toolbar toolbar) {
    /*enables the drawer toggle button*/
        DrawerLayout drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(
                this,
                drawerLayout,
                toolbar,
                R.string.openDrawer,
                R.string.closeDrawer
        );


        /*enables toggle button*/
        drawerLayout.setDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.setDrawerIndicatorEnabled(true);
        initializeActionBar(actionBarDrawerToggle);
    }

    private void initializeActionBar(ActionBarDrawerToggle actionBarDrawerToggle) {
    /*Initializes action bar abd back button*/
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);
        actionBarDrawerToggle.syncState();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.app_compat_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.opt_addTask:
                toggleBottomMenu();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {
        if(bottom_menu_is_active){
            toggleBottomMenu();
            return;
        }

        super.onBackPressed();
    }

    private void toggleBottomMenu() {

        ViewPropertyAnimator animator = container_menu_bottom.animate();
        animator.setListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {
                float temp = startValue;
                startValue = endValue;
                endValue = temp;
                bottom_menu_is_active = !bottom_menu_is_active;

//                if(bottom_menu_is_active)
//                    layoutParams.height+=150;
//                else
//                    layoutParams.height-=150;
//
//                recyclerView.setLayoutParams(layoutParams);
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });
        animator.translationY(endValue).setDuration(300).setInterpolator(new LinearInterpolator());
    }

}