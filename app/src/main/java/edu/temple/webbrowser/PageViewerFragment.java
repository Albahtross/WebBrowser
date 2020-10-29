package edu.temple.webbrowser;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PageViewerFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PageViewerFragment extends Fragment{

    WebView WebBrowser;

    public PageViewerFragment() {
        // Required empty public constructor
    }


    public static PageViewerFragment newInstance(String param1, String param2) {
        PageViewerFragment fragment = new PageViewerFragment();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_page_viewer, container, false);

        WebBrowser = v.findViewById(R.id.WebBrowser);

        WebBrowser.setWebViewClient(new WebViewClient(){
            public void onReceivedError(WebView view, int errorCode, String description){

            }
            @Override
            public void onPageFinished(WebView view, String url){
                ((PageViewerFragment.updateText) getActivity()).updateText();
            }

        });

        return v;
    }

    public void clickNext(){
        if(WebBrowser.canGoForward()){
            WebBrowser.goForward();
        }
    }

    public void clickBack(){
        if(WebBrowser.canGoBack()){
            WebBrowser.goBack();
        }
    }

    public String getURL(){
        return WebBrowser.getUrl();
    }

    public void visitPage(String url){
        if(!url.startsWith("https://")){
            url = "https://" + url;
        }
        WebBrowser.loadUrl(url);
    }

    public interface updateText{
        void updateText();
    }
}