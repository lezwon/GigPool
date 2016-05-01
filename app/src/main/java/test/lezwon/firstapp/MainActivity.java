package test.lezwon.firstapp;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.TransitionDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.*;
import android.widget.Button;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
    }


    public  void toggleForm(View view) {
        switch (view.getId()){
            case R.id.btn_signup:{
                findViewById(R.id.signIn_form).setVisibility(View.GONE);
                findViewById(R.id.signUp_form).setVisibility(View.VISIBLE);
                break;
            }

            case R.id.btn_login:{
                findViewById(R.id.signUp_form).setVisibility(View.GONE);
                findViewById(R.id.signIn_form).setVisibility(View.VISIBLE);
                break;
            }

        }
    }


    public void switchActivity(View view) {
        Intent intent = new Intent(this,HomeActivity.class);
        startActivity(intent);

    }
}

