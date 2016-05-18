package test.lezwon.firstapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.*;
import android.widget.Button;


/**
 * Created by Lezwon on 15-05-2016.
 * This is the MainActivity class and handles the display of the main activity
 * within this application. The onCreate method creates the vies and assigns an event
 * listener to the login button. The toggleForm function is used to toggle between the
 * two forms. Sign up and Login.
 */

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        Button logInSubmitButton = (Button) findViewById(R.id.signIn_submit);

        logInSubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),HomeActivity.class);
                startActivity(intent);
            }
        });
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

}

