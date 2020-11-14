package edu.temple.webbrowser;

import android.content.Context;
import android.graphics.Bitmap;
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
    PageViewerListener listener;

    public PageViewerFragment() {
        // Required empty public constructor
    }


    public static PageViewerFragment newInstance() {
        PageViewerFragment fragment = new PageViewerFragment();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onAttach(Context context){
        super.onAttach(context);
        if(context instanceof PageViewerListener){
            listener = (PageViewerListener) context;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View v = inflater.inflate(R.layout.fragment_page_viewer, container, false);

        WebBrowser = v.findViewById(R.id.WebBrowser);

        WebBrowser.setWebViewClient(new WebViewClient(){
            public void onReceivedError(WebView view, int errorCode, String description){

            }
            @Override
            public void onPageFinished(WebView view, String url){
                ((PageViewerFragment.updateText) getActivity()).updateText();
            }
            @Override
            public void onPageStarted(WebView view, String url, Bitmap bm){
                super.onPageStarted(view,url, bm);
                if (listener != null){
                    listener.sendPageViewData();
                }
            }
        });

        return v;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);
        if(savedInstanceState != null){
            WebBrowser.restoreState(savedInstanceState);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState){
        super.onSaveInstanceState(outState);
        WebBrowser.saveState(outState);
        outState.putString("url", WebBrowser.getUrl());
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

    public interface PageViewerListener{
        void sendPageViewData();
    }

    public interface updateText{
        void updateText();
    }
}