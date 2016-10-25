package test.lezwon.firstapp;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
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
                slideInBottomMenu();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void slideInBottomMenu() {
//        container_menu_bottom.setVisibility(View.VISIBLE);
        Animator menu_slideIn = AnimatorInflater.loadAnimator(this, R.animator.animation);
        menu_slideIn.setTarget(container_menu_bottom);;
        menu_slideIn.setInterpolator(new LinearInterpolator());
        menu_slideIn.start();
    }

}