package test.lezwon.firstapp;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.ArrayList;


/**
 * Created by Lezwon on 18-05-2016.
 */
public class ChatListAdapter extends BaseAdapter {


    private ArrayList<ChatRow> chatRowArrayList;

    private Context context;

    ChatListAdapter(Context context){
        this.context = context;
        chatRowArrayList = new ArrayList<>();
        Resources resources = context.getResources();
        String[] names = resources.getStringArray(R.array.names);
        String[] descriptions = resources.getStringArray(R.array.descriptions);

        TypedArray colors = resources.obtainTypedArray(R.array.profileColors);

        for (int i = 0; i < names.length; i++) {
            chatRowArrayList.add(new ChatRow(names[i],descriptions[i],colors.getResourceId(i,1)));
        }

        colors.recycle();
    }


    @Override
    public int getCount() {
        return chatRowArrayList.size();
    }

    @Override
    public Object getItem(int i) {
        return chatRowArrayList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View newView = inflater.inflate(R.layout.layout_chat_row,viewGroup, false);

        ImageView chatImageView = (ImageView) newView.findViewById(R.id.chat_list_image);
        TextView chatNameView = (TextView) newView.findViewById(R.id.chat_list_name);
        TextView chatDescView = (TextView) newView.findViewById(R.id.chat_list_desc);

        ChatRow chatRow = chatRowArrayList.get(i);

        chatImageView.setImageResource(chatRow.image);
        chatNameView.setText(chatRow.name);
        chatDescView.setText(chatRow.description);

        return newView;
    }



    private class ChatRow {
        String name;
        String description;
        int image;

        ChatRow(String name, String description, int image){
            this.name = name;
            this.description = description;
            this.image = image;
        }
    }
}
