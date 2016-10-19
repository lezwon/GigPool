package test.lezwon.firstapp;


import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.view.animation.LinearOutSlowInInterpolator;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.transition.*;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


import java.util.ArrayList;

public class RegisterActivity extends AppCompatActivity {


    private static final String IBAN_MASK = "999 999 9999";
    private ArrayList<EditText> phoneEditTextArrayList;

    @BindView(R.id.container_register_main)
    LinearLayout main_container;

    @BindView(R.id.layout_form_phone)
    RelativeLayout form_container;

    @BindView(R.id.layout_form_profile)
    RelativeLayout form_profile;

    @BindView(R.id.layout_form_gender)
    RelativeLayout form_gender;

    @BindView(R.id.btn_verify_number)
    Button btn_verify;


    private int windowHeight;
    private boolean flag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);

        setContainerHeights();
        initializePhoneTextBox();

    }

    private void initializePhoneTextBox() {
        phoneEditTextArrayList = new ArrayList<>();
        phoneEditTextArrayList.add((EditText) findViewById(R.id.text_phone_num_1));
        phoneEditTextArrayList.add((EditText) findViewById(R.id.text_phone_num_2));
        phoneEditTextArrayList.add((EditText) findViewById(R.id.text_phone_num_3));

        for (int i = 0; i< phoneEditTextArrayList.size(); i++) {
            phoneEditTextArrayList.get(i).addTextChangedListener(new PhoneNumberTextboxListener(i));
        }
    }

    private void setContainerHeights() {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

        windowHeight = displayMetrics.heightPixels;
        LinearLayout.LayoutParams  layoutParams = (LinearLayout.LayoutParams) main_container.getChildAt(0).getLayoutParams();

        layoutParams.height = windowHeight;

        for(int i = 0; i< main_container.getChildCount(); i++){
            main_container.getChildAt(i).setLayoutParams(layoutParams);
        }
    }


    void verifyNumber(){
        Intent intent = new Intent(getApplicationContext(),HomeActivity.class);
        startActivity(intent);
    }


    @TargetApi(Build.VERSION_CODES.KITKAT)
    @OnClick(R.id.btn_verify_number)
    void bottomAnimate(View view){
//        LinearLayout formContainer = (LinearLayout) findViewById(R.id.textbox_group);
//        Button verifyButton = (Button) findViewById(R.id.btn_verify_number);
//
//        Animator animationForm = AnimatorInflater.loadAnimator(this, R.animator.animation);
//        animationForm.setTarget(formContainer);
//        Animator animationButton = AnimatorInflater.loadAnimator(this, R.animator.animation);
//        animationButton.setTarget(verifyButton);
//
//        AnimatorSet set = new AnimatorSet();
//        set.playTogether(animationForm,animationButton);
//        set.setDuration(500);
//        set.setInterpolator(new LinearOutSlowInInterpolator());
//        set.start();


//        ObjectAnimator animatorForm = ObjectAnimator.ofFloat(formContainer,"rotation", 0, 300);
//        ObjectAnimator animatorBtn = ObjectAnimator.ofFloat(verifyButton,"rotation", 0, 400);

//        AnimatorSet set = new AnimatorSet();
////        set.playSequentially(animatorForm,animatorBtn);
//        set.setDuration(500);
//        set.setInterpolator(new FastOutSlowInInterpolator());
//        set.start();

//        ViewGroup transitionRoot = form_container;
//        Scene expandedScene = Scene.getSceneForLayout(transitionRoot,R.layout.activity_register_end,view.getContext());
//        Transition transition = TransitionInflater.from(this).inflateTransition(R.transition.transition_register);
//
//        expandedScene.setEnterAction(new Runnable() {
//            @Override
//            public void run() {
//                ButterKnife.bind(RegisterActivity.this);
//                flag = true;
//            }
//        });
//
//        TransitionManager.go(expandedScene, transition);


        ObjectAnimator animator_form_scroll = ObjectAnimator.ofInt(main_container,"scrollY", 0, windowHeight);
//        ObjectAnimator animator_form_visibility = ObjectAnimator.ofInt(form_container,"visibility", View.VISIBLE, View.GONE);

        AnimatorSet animatorSet_form = new AnimatorSet();
        animatorSet_form.playSequentially(animator_form_scroll);
        animatorSet_form.setInterpolator(new LinearOutSlowInInterpolator());
        animatorSet_form.setDuration(1000);
        animatorSet_form.start();

    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    @Override
    public void onBackPressed() {
        if(flag){
            ViewGroup transitionRoot = form_container;
            Scene expandedScene = Scene.getSceneForLayout(transitionRoot,R.layout.activity_register,this);
            expandedScene.setEnterAction(new Runnable() {
                @Override
                public void run() {
                    ButterKnife.bind(RegisterActivity.this);
                    flag = false;
                }
            });

            TransitionManager.go(expandedScene, new ChangeBounds());
            return;
        }

        super.onBackPressed();
    }

    private class PhoneNumberTextboxListener implements TextWatcher {
        int currentTextBoxPos;

        PhoneNumberTextboxListener(int i) {
            currentTextBoxPos = i;
        }

        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void afterTextChanged(Editable editable) {
            if(editable.length() >= 3 && currentTextBoxPos != 2){
                phoneEditTextArrayList.get(currentTextBoxPos +1).requestFocus();
                return;
            }

            //todo backspace deletes from previous edittext
            if(editable.length() == 0 && currentTextBoxPos != 0){
                EditText editText = phoneEditTextArrayList.get(currentTextBoxPos - 1);
                editText.requestFocus();
                editText.dispatchKeyEvent(new KeyEvent(100,500,KeyEvent.ACTION_DOWN,KeyEvent.KEYCODE_DEL,3));
            }

        }
    }
}

