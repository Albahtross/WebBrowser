package edu.temple.webbrowser;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PageListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PageListFragment extends Fragment {

    ListView list;

    private Context context;
    private ArrayList<String> urls;
    private ArrayAdapter<String> adapter;
    private PageListListener listener;

    public PageListFragment() {
        // Required empty public constructor
    }


    public static PageListFragment newInstance() {
        return new PageListFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        urls = new ArrayList<>();
        adapter = new ArrayAdapter<>(context, android.R.layout.simple_list_item_1, urls);

        if(savedInstanceState!=null){
            urls.addAll(savedInstanceState.getStringArrayList("urls"));
        }
        else {
            urls.add("");
        }
    }


    @Override
    public void onAttach(Context context){
        super.onAttach(context);
        this.context = context;
        if(context instanceof PageListListener){
            listener = (PageListListener) context;
        }
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onSaveInstanceState(Bundle outState){
        super.onSaveInstanceState(outState);
        outState.putStringArrayList("urls", urls);
    }

    public void addURL(String url){
        urls.add(url);
        adapter.notifyDataSetChanged();
    }
    public void replaceURL(int i, String url){
        urls.set(i, url);
        adapter.notifyDataSetChanged();
    }

    public interface PageListListener{
        void sendPageListData(int i);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View v = inflater.inflate(R.layout.fragment_page_list, container, false);
        list = v.findViewById(R.id.url_list);
        list.setAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> adapterView, View v, int i, long l){
                listener.sendPageListData(i);
            }
        });
        adapter.notifyDataSetChanged();
        return v;
    }
}