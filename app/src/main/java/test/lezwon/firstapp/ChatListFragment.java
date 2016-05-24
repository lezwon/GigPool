package test.lezwon.firstapp;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;


/**
 * A simple {@link Fragment} subclass.
 * The chatListFragment initializes the RecyclerView and assigns a Layout manager to it.
 * Context is passed to ChatListAdapter. ItemClickListener is implemented in this view.
 */
public class ChatListFragment extends Fragment implements ChatListAdapter.ItemClickListener {


    public ChatListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_chat_list, container, false);
        RecyclerView recyclerView =  (RecyclerView) view.findViewById(R.id.list_view_chat);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        ChatListAdapter chatListAdapter = new ChatListAdapter(getActivity());
        recyclerView.setAdapter(chatListAdapter);
        chatListAdapter.setOnItemClickListener(this);
        return view;
    }

    @Override
    public void onItemClickListener(View view, int position) {
        Intent intent = new Intent(getContext(),ChatMessagesActivity.class);
        intent.putExtra("EXTRA_CHAT_NAME",((TextView)view.findViewById(R.id.chat_list_name)).getText());
        startActivity(intent);
    }

}
