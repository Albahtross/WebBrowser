package edu.temple.webbrowser;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PagerFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PagerFragment extends Fragment {

    ViewPager pager;
    ArrayList<PageViewerFragment> pageViews;
    FragmentStatePagerAdapter fspAdapter;
    PagerListener listener;


    public PagerFragment() {
        // Required empty public constructor
    }


    public static PagerFragment newInstance() {
        return new PagerFragment();
    }

    @Override
    public void onSaveInstanceState(Bundle outState){
        super.onSaveInstanceState(outState);
        outState.putSerializable("fragments", pageViews);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pageViews = new ArrayList<>();
        if (savedInstanceState != null) {
            pageViews = (ArrayList<PageViewerFragment>) savedInstanceState.getSerializable("fragments");
        } else{
            addPage();
        }

        fspAdapter = new FragmentStatePagerAdapter(getFragmentManager()){
            @Override
            public void destroyItem(ViewGroup container, int position, Object object){

            }
            @Override
            public int getItemPosition(Object object){
                if(pageViews.contains(object)){
                    return pageViews.indexOf(object);
                } else{
                    return POSITION_NONE;
                }
            }
            @Override
            public Fragment getItem(int position){
                return pageViews.get(position);
            }
            @Override
            public int getCount(){
                return pageViews.size();
            }
        };

    }

    public void notifyDataSetChanged(){
        fspAdapter.notifyDataSetChanged();
    }

    public void addPage(){
        PageViewerFragment newFrag = PageViewerFragment.newInstance();
        pageViews.add(newFrag);
    }

    public PageViewerFragment currentPage(){
        return (PageViewerFragment)fspAdapter.getItem(pager.getCurrentItem());
    }

    public ArrayList<PageViewerFragment> getAllPageViews(){
        return pageViews;
    }

    public void setPages(ArrayList<PageViewerFragment> p){
        pageViews = new ArrayList<>();
        pageViews.addAll(p);
    }

    public int getIndex(){
        return pager.getCurrentItem();
    }

    public int getSize(){
        return pager.getChildCount();
    }

    public void switchPages(int i){
        pager.setCurrentItem(i, true);
    }

    public interface PagerListener{
        void sendPagerData(int i);
    }

    @Override
    public void onAttach(Context context){
        super.onAttach(context);
        if (context instanceof PagerListener) {
            listener = (PagerListener) context;
        }
    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_pager,container, false);
        pager = v.findViewById(R.id.view_pager);
        pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                listener.sendPagerData(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        pager.setAdapter(fspAdapter);
        return v;
    }
}