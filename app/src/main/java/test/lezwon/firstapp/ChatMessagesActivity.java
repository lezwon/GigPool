package test.lezwon.firstapp;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.*;
import android.widget.*;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.rockerhieu.emojicon.EmojiconEditText;
import com.rockerhieu.emojicon.EmojiconGridFragment;
import com.rockerhieu.emojicon.EmojiconsFragment;
import com.rockerhieu.emojicon.emoji.Emojicon;

//todo attachment button
//todo emoji/keyboard toggle button
//todo fix fragemnt change emoji
//todo popup

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
        setSupportActionBar(toolbar);

        /*Get chat toolbar layout*/
        LayoutInflater layoutInflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        View toolbarLayout =  layoutInflater.inflate(R.layout.chat_toolbar_details,toolbar);
        ((TextView)toolbarLayout.findViewById(R.id.chat_name)).setText(name);

        /*Initialize Chat List View*/
        ListView chatMessagesListView = (ListView) findViewById(R.id.list_chat_messages);
//        ArrayAdapter<String> chatMessagesArrayAdapter = new ArrayAdapter<>(this,R.layout.textbox_chat_outgoing,getResources().getStringArray(R.array.dummy_data));

        ChatMessagesAdapter chatMessagesAdapter = new ChatMessagesAdapter(this);
        chatMessagesListView.setAdapter(chatMessagesAdapter);


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

            case R.id.opt_attachFile:
                toggleAttachmentMenu();
                return true;

            default:
                return false;
        }
    }

    //// TODO: 06-09-2016  install circular reveal library 
    
    private void toggleAttachmentMenu() {
        final FrameLayout menuAttachmentContainer = (FrameLayout) findViewById(R.id.menu_attachmentOptions);
        Animator anim;

        /*Get Width and Height*/
        int x = menuAttachmentContainer.getWidth();
        int y = menuAttachmentContainer.getHeight();

        /*Set Final Radius*/
        float finalRadius = (float) Math.hypot(x,y);

        /*Store Start Coordinates*/
        x/=2;

        switch(menuAttachmentContainer.getVisibility()){
            case View.GONE:

                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                    /*Create Animation*/
                    anim = ViewAnimationUtils.createCircularReveal(menuAttachmentContainer,x,y,0,finalRadius);

                    /*Set Visibility*/
                    menuAttachmentContainer.setVisibility(View.VISIBLE);

                    /*Start Animation*/
                    anim.start();
                    return;
                }

                menuAttachmentContainer.setVisibility(View.VISIBLE);
                break;

            case View.VISIBLE:
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                    /*Create Animation*/
                    anim = ViewAnimationUtils.createCircularReveal(menuAttachmentContainer,x,y,finalRadius,0);

                    /*Set Visibility*/
                    menuAttachmentContainer.setVisibility(View.VISIBLE);

                    /*Set Animation Listener*/
                    anim.addListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            super.onAnimationEnd(animation);
                            menuAttachmentContainer.setVisibility(View.GONE);
                        }
                    });

                    /*Start Animation*/
                    anim.start();
                    return;
                }

                menuAttachmentContainer.setVisibility(View.GONE);
                break;
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
    void openEmoji(View view){
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

    //todo drawer icons

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_chat_messages,menu);
        return super.onCreateOptionsMenu(menu);
    }
}
