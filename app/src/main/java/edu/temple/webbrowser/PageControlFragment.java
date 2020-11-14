package edu.temple.webbrowser;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PageControlFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PageControlFragment extends Fragment {

    EditText URLbar;
    ImageButton goButton;
    ImageButton nextButton;
    ImageButton backButton;

    private PageControlListener listener;

    public PageControlFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     */

    public static PageControlFragment newInstance() {
        PageControlFragment fragment = new PageControlFragment();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onSaveInstanceState(Bundle outState){
        outState.putString("url", URLbar.getText().getClass().toString());
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onAttach(Context context){
        super.onAttach(context);
        if(context instanceof PageControlListener){
            listener = (PageControlListener) context;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View v = inflater.inflate(R.layout.fragment_page_control, container, false);

        URLbar = v.findViewById(R.id.URLbar);
        goButton = v.findViewById(R.id.buttonGo);
        nextButton = v.findViewById(R.id.buttonNext);
        backButton = v.findViewById(R.id.buttonBack);


        goButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                listener.sendPageControlData("visit", null);
            }
        });

        nextButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                listener.sendPageControlData("next", null);
            }
        });

        backButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                listener.sendPageControlData("back", null);
            }
        });

        if(savedInstanceState!=null){
            URLbar.setText(savedInstanceState.getString("url"));
        }

        return v;
    }


    public void setText(String text){
        URLbar.setText(text);
    }

    public interface PageControlListener{
        void sendPageControlData(String str, String url);
    }

    public interface visitPageInterface{
        void visitPage(String URL);
    }

    public interface clickNextInterface{
        void clickNext();
    }

    public interface clickBackInterface{
        void clickBack();
    }
}