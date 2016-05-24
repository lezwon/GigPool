package test.lezwon.firstapp;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import com.rockerhieu.emojicon.EmojiconGridFragment;
import com.rockerhieu.emojicon.EmojiconsFragment;
import com.rockerhieu.emojicon.emoji.Emojicon;

public class ChatMessagesActivity extends AppCompatActivity implements Toolbar.OnMenuItemClickListener,
        EmojiconGridFragment.OnEmojiconClickedListener,
        EmojiconsFragment.OnEmojiconBackspaceClickedListener  {

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
        setSupportActionBar(toolbar);

        /*Initializes action bar abd back button*/
        ActionBar actionBar = getSupportActionBar();

        assert actionBar != null;
        actionBar.setTitle(name);

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

    }

    @Override
    public void onEmojiconBackspaceClicked(View v) {

    }

    //todo go button string

    public void openEmoji(View view){

        View fragmentContainer = findViewById(R.id.emojicons);
        ViewGroup.LayoutParams layoutParams = fragmentContainer.getLayoutParams();
        layoutParams.height = 250;
        getSupportFragmentManager().beginTransaction().add(R.id.emojicons,new EmojiconGridFragment()).commit();
    }


}
