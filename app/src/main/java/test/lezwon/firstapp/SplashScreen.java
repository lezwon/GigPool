package test.lezwon.firstapp;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v4.view.animation.FastOutSlowInInterpolator;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.google.firebase.iid.FirebaseInstanceId;

import static android.content.ContentValues.TAG;

public class SplashScreen extends AppCompatActivity {

    @BindView(R.id.btn_register)
    Button btn_register;

    @BindView(R.id.app_title)
    TextView appTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        ButterKnife.bind(this);
        btn_register.setTranslationY(300);

        Typeface bubblegums = Typeface.createFromAsset(getAssets(), "fonts/BUBBLEGUMS.TTF");
        appTitle.setTypeface(bubblegums);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState){
        super.onPostCreate(savedInstanceState);
        animateRegisterButton();

        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Log.d(TAG, "Refreshed token: " + refreshedToken);
    }

    private void animateRegisterButton() {
        Animator slideUp = AnimatorInflater.loadAnimator(this,R.animator.animation);
        slideUp.setTarget(btn_register);
        slideUp.setStartDelay(2000);
        slideUp.setDuration(800);
        slideUp.setInterpolator(new FastOutSlowInInterpolator());
        slideUp.start();
    }

    @OnClick(R.id.btn_register)
    void loadRegisterActivity(View view){
        Intent intent = new Intent(this,RegisterActivity.class);
        startActivity(intent);
    }
}
