package edu.temple.webbrowser;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.graphics.pdf.PdfDocument;
import android.os.Bundle;
import android.webkit.URLUtil;

public class BrowserActivity extends AppCompatActivity implements PageControlFragment.clickBackInterface, PageControlFragment.visitPageInterface, PageControlFragment.clickNextInterface{

        PageControlFragment controlFrag;
        PageViewerFragment viewFrag;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.browser_activity);

        if(controlFrag==null){
            controlFrag=new PageControlFragment();
        }
        if(viewFrag==null){
            viewFrag=new PageViewerFragment();
        }
        FragmentManager fragmentManager=getSupportFragmentManager();
        FragmentTransaction transaction=fragmentManager.beginTransaction();
        transaction.add(R.id.page_control,controlFrag);
        transaction.add(R.id.page_viewer, viewFrag);
        transaction.commit();
    }

    @Override
    public void clickBack(){
        viewFrag.clickBack();
        controlFrag.updateText(viewFrag.getURL());
    }
    @Override
    public void clickNext(){
        viewFrag.clickNext();
        controlFrag.updateText(viewFrag.getURL());
    }

    @Override
    public void visitPage(String url){
        viewFrag.visitPage(url);
        controlFrag.updateText(viewFrag.getURL());
    }


}