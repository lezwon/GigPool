package test.lezwon.firstapp;


import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.annotation.TargetApi;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.IdRes;
import android.support.v4.view.animation.LinearOutSlowInInterpolator;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.*;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;


import java.io.File;
import java.util.ArrayList;

import static android.os.Environment.getExternalStorageDirectory;

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

    @BindView(R.id.radioGroup_gender)
    RadioGroup radioGroup_gender;

    @BindView(R.id.radioGroup_age)
    RadioGroup radioGroup_age;

    @BindView(R.id.img_gender_line)
    ImageView img_gender_line;

    @BindView(R.id.img_age_line)
    ImageView img_age_line;

    @BindView(R.id.profile_image)
    CircleImageView profileImage;

    private int windowHeight;
    int currentPage = 0;
    private int startScroll = 0;
    private int endScroll;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);

        setContainerHeights();
        initializeListeners();

        File imageFile = new File(getExternalStorageDirectory(),"Bhatinder/profile.PNG");

        if(imageFile.exists()){
            Bitmap image = BitmapFactory.decodeFile(imageFile.getAbsolutePath());
            profileImage.setImageBitmap(image);
        }
    }

    private void initializeListeners() {
        /*Phone Textbox Listener*/
        phoneEditTextArrayList = new ArrayList<>();
        phoneEditTextArrayList.add((EditText) findViewById(R.id.text_phone_num_1));
        phoneEditTextArrayList.add((EditText) findViewById(R.id.text_phone_num_2));
        phoneEditTextArrayList.add((EditText) findViewById(R.id.text_phone_num_3));

        for (int j = 0; j< phoneEditTextArrayList.size(); j++) {
            phoneEditTextArrayList.get(j).addTextChangedListener(new PhoneNumberTextboxListener(j));
        }

        /*Radio Button Listener for gender*/
        radioGroup_gender.clearCheck();
        radioGroup_age.clearCheck();

        radioGroup_gender.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {
                RadioButton button = (RadioButton) findViewById(i);
                int index = radioGroup.indexOfChild(button);
                animateLine(img_gender_line,index);
            }
        });

        /*Radio Button Listener for age group*/
        radioGroup_age.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {
                RadioButton button = (RadioButton) findViewById(i);
                int index = radioGroup.indexOfChild(button);
                animateLine(img_age_line, index);
            }
        });
    }

    private void animateLine(ImageView line, int i) {
        int width = line.getLayoutParams().width;
        float startValue = line.getTranslationX();
        float endValue = width * (i);
        ObjectAnimator animateLine = ObjectAnimator.ofFloat(line,"translationX", startValue, endValue);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(animateLine);
        animatorSet.setDuration(400);
        animatorSet.setInterpolator(new LinearOutSlowInInterpolator());
        animatorSet.start();
    }


    private void setContainerHeights() {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

        endScroll = windowHeight = displayMetrics.heightPixels;
        LinearLayout.LayoutParams  layoutParams = (LinearLayout.LayoutParams) main_container.getChildAt(0).getLayoutParams();

        layoutParams.height = windowHeight;

        for(int i = 0; i< main_container.getChildCount(); i++){
            View child = main_container.getChildAt(i);
            child.setVisibility(View.VISIBLE);
            child.setLayoutParams(layoutParams);
        }
    }


    @OnClick(R.id.btn_verify_number)
    void submitPhoneNumber(){
        scrollDown();
    }

    @OnClick(R.id.btn_next)
    void submitUsername(){
        scrollDown();
    }

    @OnClick(R.id.btn_getStarted)
    void submitGender(){
        Intent intent = new Intent(this,HomeActivity.class);
        startActivity(intent);
    }

    private void scrollDown() {
        endScroll= windowHeight*++currentPage;
        animateScroll();
    }

    private void scrollUp(){
        endScroll= windowHeight*--currentPage;
        animateScroll();
    }

    private void animateScroll() {
        ObjectAnimator animator_form_scroll = ObjectAnimator.ofInt(main_container,"scrollY", startScroll, endScroll);
        AnimatorSet animatorSet_form = new AnimatorSet();
        animatorSet_form.playSequentially(animator_form_scroll);
        animatorSet_form.setInterpolator(new LinearOutSlowInInterpolator());
        animatorSet_form.setDuration(800);
        animatorSet_form.start();
        startScroll = endScroll;
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    @Override
    public void onBackPressed() {

        if(currentPage>0){
            scrollUp();
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
//                return;
            }

//            //todo backspace deletes from previous edittext
//            if(editable.length() == 0 && currentTextBoxPos != 0){
//                EditText editText = phoneEditTextArrayList.get(currentTextBoxPos - 1);
//                editText.requestFocus();
//                editText.dispatchKeyEvent(new KeyEvent(100,500,KeyEvent.ACTION_DOWN,KeyEvent.KEYCODE_DEL,3));
//            }

            //// TODO: 21-10-2016 verfiy number animation

        }
    }
}

