package test.lezwon.firstapp;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.*;
import android.widget.Button;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.rockerhieu.emojicon.EmojiconEditText;
import com.rockerhieu.emojicon.EmojiconGridFragment;
import com.rockerhieu.emojicon.EmojiconsFragment;
import com.rockerhieu.emojicon.emoji.Emojicon;

//todo attachment button
//todo emoji/keyboard toggle button

public class ChatMessagesActivity extends AppCompatActivity implements Toolbar.OnMenuItemClickListener,
        EmojiconGridFragment.OnEmojiconClickedListener,
        EmojiconsFragment.OnEmojiconBackspaceClickedListener  {

    private boolean emojiKeyboardOpen = false;
    private EmojiconEditText emojiconEditText;
    private FragmentManager supportFragmentManager = getSupportFragmentManager();
    private ViewGroup fragmentContainer;
    private ViewGroup.LayoutParams layoutParams;
    private EmojiconsFragment emojiconsFragment = new EmojiconsFragment();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        String name;

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_messages);
        ButterKnife.bind(this);

        emojiconEditText = (EmojiconEditText) findViewById(R.id.chat_textbox);
        fragmentContainer = (ViewGroup) findViewById(R.id.emojicons_container);
        layoutParams = fragmentContainer.getLayoutParams();

        supportFragmentManager.beginTransaction().add(R.id.emojicons_container, emojiconsFragment).commit();



        if(savedInstanceState == null){
            Bundle extras = getIntent().getExtras();
            name = extras.getString("EXTRA_CHAT_NAME");
        }
        else {
            name = (String) savedInstanceState.getSerializable("EXTRA_CHAT_NAME");
        }


        /*material design toolbar*/
        Toolbar toolbar = (Toolbar) findViewById(R.id.app_bar);
        toolbar.setTitle(name);
        setSupportActionBar(toolbar);

        /*Get chat toolbar layout*/
        LayoutInflater layoutInflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        layoutInflater.inflate(R.layout.chat_toolbar_details,toolbar);

        /*Initializes action bar abd back button*/
        ActionBar actionBar = getSupportActionBar();

        assert actionBar != null;

        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

        toolbar.setOnMenuItemClickListener(this);

        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }

    //TODO go to precious activity by default


    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                getFragmentManager().popBackStack();
                return true;

            default:
                return false;
        }
    }

    @Override
    public void onBackPressed(){
        if(emojiKeyboardOpen){
            layoutParams.height = 0;
            fragmentContainer.setLayoutParams(layoutParams);
            emojiKeyboardOpen = false;
            return;
        }

        super.onBackPressed();
    }

    @Override
    public void onEmojiconClicked(Emojicon emojicon) {
        EmojiconsFragment.input(emojiconEditText,emojicon);
    }

    @Override
    public void onEmojiconBackspaceClicked(View v) {
        EmojiconsFragment.backspace(emojiconEditText);
    }

    //todo go button string


    @OnClick(R.id.btn_insert_emoji)
    public void openEmoji(View view){
        layoutParams = fragmentContainer.getLayoutParams();

        if(emojiKeyboardOpen){
            layoutParams.height = 0;
            emojiKeyboardOpen = false;
        }
        else{
            layoutParams.height = 350;
            emojiKeyboardOpen = true;
        }

        fragmentContainer.setLayoutParams(layoutParams);

    }
}
