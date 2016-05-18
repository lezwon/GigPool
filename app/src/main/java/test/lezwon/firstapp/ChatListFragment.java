package test.lezwon.firstapp;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


/**
 * A simple {@link Fragment} subclass.
 */
public class ChatListFragment extends Fragment {


    public ChatListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_chat_list, container, false);
        ListView listView =  (ListView) view.findViewById(R.id.list_view_chat);
        listView.setAdapter(new ChatListAdapter(getActivity()));
        listView.setOnItemClickListener(new ChatListFragment.ChatListListener());
        return view;
    }

    private class ChatListListener implements android.widget.AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            TextView textView = (TextView) view.findViewById(R.id.chat_list_name);
            Toast.makeText(getActivity(), textView.getText(), Toast.LENGTH_SHORT).show();
        }
    }
}
