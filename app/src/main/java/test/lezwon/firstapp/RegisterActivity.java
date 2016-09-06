package test.lezwon.firstapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.azimolabs.maskformatter.MaskFormatter;

import java.util.ArrayList;

public class RegisterActivity extends AppCompatActivity {


    private static final String IBAN_MASK = "999 999 9999";
    private ArrayList<EditText> phoneEditTextArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);


        phoneEditTextArrayList = new ArrayList<>();
        phoneEditTextArrayList.add((EditText) findViewById(R.id.text_phone_num_1));
        phoneEditTextArrayList.add((EditText) findViewById(R.id.text_phone_num_2));
        phoneEditTextArrayList.add((EditText) findViewById(R.id.text_phone_num_3));

        for (int i = 0; i< phoneEditTextArrayList.size(); i++) {
            phoneEditTextArrayList.get(i).addTextChangedListener(new PhoneNumberTextboxListener(i));
        }
    }




    @OnClick(R.id.btn_verify_number)
    void verifyNumber(View view){
        Intent intent = new Intent(getApplicationContext(),HomeActivity.class);
        startActivity(intent);
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

