package edu.temple.webbrowser;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.graphics.pdf.PdfDocument;
import android.os.Bundle;
import android.webkit.URLUtil;

public class BrowserActivity extends AppCompatActivity implements PageControlFragment.clickBackInterface,
        PageControlFragment.visitPageInterface,
        PageControlFragment.clickNextInterface,
        PageViewerFragment.updateText,
        BrowserControlFragment.BrowserControlListener,
        PageListFragment.PageListListener,
        PagerFragment.PagerListener,
        PageControlFragment.PageControlListener,
        PageViewerFragment.PageViewerListener {

        PageControlFragment pageControlFrag;
        PagerFragment pagerFrag;
        PageListFragment pageListFrag;
        BrowserControlFragment browserControlFrag;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.browser_activity);

        if (savedInstanceState!=null){
            browserControlFrag = (BrowserControlFragment) getSupportFragmentManager().getFragment(savedInstanceState,"browserControlFrag");
            pageControlFrag = (PageControlFragment) getSupportFragmentManager().getFragment(savedInstanceState,"pageControlFrag");
            pagerFrag = (PagerFragment)getSupportFragmentManager().getFragment(savedInstanceState,"pagerFrag");
            pageListFrag = (PageListFragment)getSupportFragmentManager().getFragment(savedInstanceState, "pageListFrag");
        } else{
            pageControlFrag = PageControlFragment.newInstance();
            browserControlFrag = BrowserControlFragment.newInstance();
            pagerFrag = PagerFragment.newInstance();
            pageListFrag = PageListFragment.newInstance();

            FragmentManager fragmentManager=getSupportFragmentManager();
            FragmentTransaction transaction=fragmentManager.beginTransaction();

            transaction.add(R.id.page_control,pageControlFrag);
            transaction.add(R.id.page_list, pageListFrag);
            transaction.add(R.id.browser_control, browserControlFrag);
            transaction.add(R.id.page_viewer, pagerFrag);

            transaction.commit();
        }
        /*if(pageControlFrag==null){
            pageControlFrag=new PageControlFragment();
        }
        if(browserControlFrag==null){
            browserControlFrag = new BrowserControlFragment();
        }
        if (pagerFrag == null){
            pagerFrag = new PagerFragment();
        }
        if (pageListFrag == null){
            pageListFrag = new PageListFragment();
        }*/
    }

    @Override
    public void clickBack(){
        pagerFrag.currentPage().clickBack();
    }
    @Override
    public void clickNext(){
        pagerFrag.currentPage().clickNext();
    }

    @Override
    public void visitPage(String url){
        pagerFrag.currentPage().visitPage(url);
    }

    @Override
    public void updateText(){
        pageControlFrag.setText(pagerFrag.currentPage().getURL());
    }

    @Override
    public void sendBrowserControlData(){
        pagerFrag.addPage();
        pagerFrag.notifyDataSetChanged();

        pageControlFrag.setText("https://google.com");
        pageListFrag.addURL("https://google.com");

        pagerFrag.switchPages(pagerFrag.getSize() - 1);
    }
//asdfasdf
    @Override
    public void sendPageControlData(String str, String url){
        if (str.equals("visit")){
            pagerFrag.currentPage().visitPage(url);
        }
        else if (str.equals("next")){
            pagerFrag.currentPage().clickNext();
        }
        else if (str.equals("back")){
            pagerFrag.currentPage().clickBack();
        }
    }


    @Override
    public void sendPageListData(int i){
        pagerFrag.switchPages(i);
        pageControlFrag.setText(pagerFrag.currentPage().getURL());

    }

    @Override
    public void sendPagerData(int i){
        pagerFrag.switchPages(i);
        pageControlFrag.setText(pagerFrag.currentPage().getURL());
        pageListFrag.replaceURL(i, pagerFrag.currentPage().getURL());
    }

    @Override
    public void sendPageViewData(){
        pageControlFrag.setText(pagerFrag.currentPage().getURL());
        pageListFrag.replaceURL(pagerFrag.getIndex(), pagerFrag.currentPage().getURL());
    }
}