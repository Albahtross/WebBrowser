package edu.temple.webbrowser;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.graphics.pdf.PdfDocument;
import android.os.Bundle;
import android.webkit.URLUtil;

public class BrowserActivity extends AppCompatActivity implements PageControlFragment.clickBackInterface, PageControlFragment.visitPageInterface, PageControlFragment.clickNextInterface, PageViewerFragment.updateText, BrowserControlFragment.BrowserControlListener, PageListFragment.PageListListener, PagerFragment.PagerListener, PageControlFragment.PageControlListener, PageViewerFragment.PageViewerListener {

        PageControlFragment pageControlFrag;
        PageViewerFragment viewFrag;
        PagerFragment pagerFrag;
        PageListFragment pageListFrag;
        BrowserControlFragment browserControlFrag;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.browser_activity);

        if(pageControlFrag==null){
            pageControlFrag=new PageControlFragment();
        }
        if(viewFrag==null){
            viewFrag=new PageViewerFragment();
        }
        if (pagerFrag == null){
            pagerFrag = new PagerFragment();
        }
        if (pageListFrag == null){
            pageListFrag = new PageListFragment();
        }

        FragmentManager fragmentManager=getSupportFragmentManager();
        FragmentTransaction transaction=fragmentManager.beginTransaction();


        transaction.add(R.id.page_control,pageControlFrag);
        transaction.add(R.id.page_viewer, viewFrag);
        transaction.add(R.id.page_list, pageListFrag);
        transaction.add(R.id.browser_control, browserControlFrag);
        transaction.add(R.id.view_pager, pagerFrag);
        transaction.commit();
    }

    @Override
    public void clickBack(){
        viewFrag.clickBack();
    }
    @Override
    public void clickNext(){
        viewFrag.clickNext();
    }

    @Override
    public void visitPage(String url){
        viewFrag.visitPage(url);
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
        pageListFrag.insertURL(i, pagerFrag.currentPage().getURL());
    }

    @Override
    public void sendPageViewData(){
        pageControlFrag.setText(pagerFrag.currentPage().getURL());
        pageListFrag.insertURL(pagerFrag.getIndex(), pagerFrag.currentPage().getURL());
    }
}