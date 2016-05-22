package test.lezwon.firstapp;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;


/**
 * Created by Lezwon on 18-05-2016.
 * ChatListAdapter is the adapter for the Recycler view for the Chat List. Its job
 * is to act as an interface between the chat list data and the chat list view.
 * It contains the following subclasses:
 *
 * ChatRowData: Acts as a container for each data row.
 *
 * ChatRowViewHolder: Extends RecyclerView.ViewHolder and contains the layout details
 * for each item in the chat list.
 *
 * ItemClickListener: Interface implemented in the ChatListAdapter to provide click functionality
 * for every item in the ChatListFragment.
 *
 * The data for the chat is populated in an array stored in the array xml file.
 * The data is loaded in String array and TypedArray and loaded into a ChatRowDataList.
 * onCreateViewHolder initialises the ViewHolder with the layout.
 * onBindViewHolder passes data from the array list to the viewHolder depending on its position.
 * setOnItemClickListener is used to assign the class which implements the itemClickListener  to a local
 * itemClickListener variable.
 *
 * ChatRowViewHolder implements View.onClickListener. It assigns the data to the respective views.
 * It also calls the onItemClickListener on the View passed to it, when an item is clicked.
 *
 */
class ChatListAdapter extends RecyclerView.Adapter<ChatListAdapter.ChatRowViewHolder>{


    private final LayoutInflater inflater;
    private ArrayList<ChatRowData> chatRowDataArrayList;
    private ItemClickListener itemClickListener;

    ChatListAdapter(Context context){
        inflater = LayoutInflater.from(context);
        chatRowDataArrayList = new ArrayList<>();

        Resources resources = context.getResources();
        String[] names = resources.getStringArray(R.array.names);
        String[] descriptions = resources.getStringArray(R.array.descriptions);
        TypedArray colors = resources.obtainTypedArray(R.array.profileColors);

        for (int i = 0; i < names.length; i++) {
            chatRowDataArrayList.add(new ChatRowData(names[i],descriptions[i],colors.getResourceId(i,1)));
        }

        colors.recycle();
    }

    @Override
    public ChatRowViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.layout_chat_row,parent,false);
        return new ChatRowViewHolder(view);
    }


    @Override
    public void onBindViewHolder(ChatRowViewHolder holder, int position) {
        ChatRowData chatRowData = chatRowDataArrayList.get(position);
        holder.updateItems(chatRowData.name,chatRowData.description,chatRowData.image);
    }

    @Override
    public int getItemCount() {
        return chatRowDataArrayList.size();
    }

    void setOnItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }


    private class ChatRowData {
        String name;
        String description;
        int image;

        ChatRowData(String name, String description, int image){
            this.name = name;
            this.description = description;
            this.image = image;
        }
    }


    class ChatRowViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView chatName;
        private ImageView chatImage;
        private  TextView chatDesc;


        ChatRowViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            chatName = (TextView) itemView.findViewById(R.id.chat_list_name);
            chatImage = (ImageView) itemView.findViewById(R.id.chat_list_image);
            chatDesc = (TextView) itemView.findViewById(R.id.chat_list_desc);
        }

        void updateItems(String name, String desc, int image){
            chatName.setText(name);
            chatDesc.setText(desc);
            chatImage.setImageResource(image);
        }

        @Override
        public void onClick(View view) {
            itemClickListener.onItemClickListener(view,getAdapterPosition());
        }

    }

    interface ItemClickListener{
        void onItemClickListener(View view, int position);
    }
}
