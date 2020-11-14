package edu.temple.webbrowser;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link BrowserControlFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BrowserControlFragment extends Fragment {

    private BrowserControlListener listener;

    public BrowserControlFragment() {
        // Required empty public constructor
    }

    public static BrowserControlFragment newInstance() {
        return new BrowserControlFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public void onSaveInstanceState(Bundle outState){
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onAttach(Context context){
        super.onAttach(context);
        if (context instanceof BrowserControlFragment.BrowserControlListener){
            listener = (BrowserControlFragment.BrowserControlListener) context;
        }
    }

    public interface BrowserControlListener{
        void sendBrowserControlData();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_browser_control, container, false);
        ImageButton createPageViewerButton = v.findViewById(R.id.createPageViewButton);
        TextView textView = v.findViewById(R.id.textView);
        createPageViewerButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                listener.sendBrowserControlData();
            }
        });
        return v;
    }
}