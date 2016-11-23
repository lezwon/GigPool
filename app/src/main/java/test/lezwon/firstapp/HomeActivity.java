package test.lezwon.firstapp;

import android.animation.Animator;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.*;
import android.view.animation.LinearInterpolator;
import android.widget.RelativeLayout;
import android.widget.Toast;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.firebase.auth.FirebaseAuth;

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

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;
    private GoogleApiClient mGoogleApiClient;

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
        mAuth = FirebaseAuth.getInstance();

        GoogleAuth googleAuth = ((GoogleAuth) getApplication());
        googleAuth.build(this);
        mGoogleApiClient = googleAuth.getGoogleApiClient();

        mAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if(firebaseAuth.getCurrentUser() == null){
                    startActivity(new Intent(HomeActivity.this,SplashScreen.class));
                }
            }
        };
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        RecyclerView recyclerView = (RecyclerView) getSupportFragmentManager().findFragmentById(R.id.fragment_chat_list).getView();
        ViewGroup.LayoutParams layoutParams = recyclerView != null ? recyclerView.getLayoutParams() : null;
    }

    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthStateListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        mAuth.removeAuthStateListener(mAuthStateListener);
    }

    private void initializeDrawer(Toolbar toolbar) {
    /*enables the drawer toggle button*/
        final DrawerLayout drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        NavigationView navigationView  = (NavigationView) findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener(){

                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {

                        menuItem.setChecked(true);
                        if(menuItem.getTitle().equals("Sign Out"))
                        {
                            drawerLayout.closeDrawers();
                            mAuth.signOut();
                            Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(new ResultCallback<Status>() {
                                @Override
                                public void onResult(@NonNull Status status) {
                                    if(status.isSuccess()){
                                        Toast.makeText(HomeActivity.this,"Sign out",Toast.LENGTH_SHORT).show();
                                        startActivity(new Intent(HomeActivity.this,SplashScreen.class));
                                    }

                                }
                            });

                            return true;
                        }

                        return false;
                    }
                }
        );

        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(
                this,
                drawerLayout,
                toolbar,
                R.string.openDrawer,
                R.string.closeDrawer
        );


        /*enables toggle button*/
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.setDrawerIndicatorEnabled(true);
        initializeActionBar(actionBarDrawerToggle);
    }

    public void signOut() {
        // Firebase sign out
        mAuth.signOut();

        // Google revoke access
        Auth.GoogleSignInApi.revokeAccess(mGoogleApiClient).setResultCallback(
                new ResultCallback<Status>() {
                    @Override
                    public void onResult(@NonNull Status status) {

                    }
                });
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

        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);

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