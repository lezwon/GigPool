package test.lezwon.firstapp;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import com.rockerhieu.emojicon.EmojiconEditText;
import com.rockerhieu.emojicon.EmojiconGridFragment;
import com.rockerhieu.emojicon.EmojiconsFragment;
import com.rockerhieu.emojicon.emoji.Emojicon;
import de.hdodenhof.circleimageview.CircleImageView;

public class ChatMessagesActivity extends AppCompatActivity implements Toolbar.OnMenuItemClickListener,
        EmojiconGridFragment.OnEmojiconClickedListener,
        EmojiconsFragment.OnEmojiconBackspaceClickedListener  {

    private boolean emojiKeyboardOpen = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        String name;

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_messages);


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
    public void onEmojiconClicked(Emojicon emojicon) {
        EmojiconEditText chatBox = (EmojiconEditText) findViewById(R.id.chat_textbox);
        chatBox.append(emojicon.getEmoji());
    }

    @Override
    public void onEmojiconBackspaceClicked(View v) {

    }

    //todo go button string

    public void openEmoji(View view){

        ViewGroup fragmentContainer = (ViewGroup) findViewById(R.id.emojicons);
        ViewGroup.LayoutParams layoutParams = fragmentContainer.getLayoutParams();

        if(emojiKeyboardOpen){
            layoutParams.height = 0;
            fragmentContainer.removeAllViews();
            emojiKeyboardOpen = false;
        }
        else{
            layoutParams.height = 250;
            getSupportFragmentManager().beginTransaction().add(R.id.emojicons,new EmojiconGridFragment()).commit();
            emojiKeyboardOpen = true;
        }

    }


}
