package test.lezwon.firstapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

/**
 * Created by Lezwon on 06-06-2016.
 */
class ChatMessagesAdapter extends BaseAdapter{

    private Context context;
    private Message[] messages = {
      new Message("This city will fall"),
      new Message("The place will crush under your feet"),
      new Message("ANd your joy will turn to ashes in your mouth"),
      new Message("This city deserves a better class of villians"),
      new Message("The BAt Begins"),
    };

    ChatMessagesAdapter(Context context) {
        this.context = context;
    }


    @Override
    public int getCount() {
        return messages.length;
    }

    @Override
    public Object getItem(int i) {
        return messages[i];
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        View messageView = null;

        if(view == null){
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            messageView = layoutInflater.inflate(R.layout.textbox_chat_outgoing,viewGroup,false);
        }
        else{
            messageView = view;
        }

        TextView messageTextView = (TextView) messageView.findViewById(R.id.text_message);
        TextView timeTextView = (TextView) messageView.findViewById(R.id.text_time);

        messageTextView.setText(messages[i].getMessage());
        timeTextView.setText("22:57");

        return messageView;
    }
}
