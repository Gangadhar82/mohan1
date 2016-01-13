package com.mmadapps.fairpriceshop.reports;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TabWidget;
import android.widget.TextView;

import com.mmadapps.fairpriceshop.R;
import com.mmadapps.fairpriceshop.actionbars.ActionbarActivity;

/**
 * Created by Baskar on 12/11/2015.
 */
public class ReportFragmentActivity extends ActionbarActivity implements FragmentInteractor,TabHost.OnTabChangeListener, ViewPager.OnPageChangeListener {

    public static FragmentInteractor setListViewHeightBasedOnChild;
    public static LanguageInteractor languageReseter;

    private TabHost tabHost;
    private ViewPager pager;
    //TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);
        setListViewHeightBasedOnChild = this;
        initalizeMenu();
        setPageTitle(R.string.title_reports,true);

        tabHost = (TabHost) findViewById(android.R.id.tabhost);
        pager = (ViewPager) findViewById(R.id.vP_RA_viewpager);
        pager.setOffscreenPageLimit(4);
        tabHost.setup();
        //TabWidget tabwidget = tabHost.getTabWidget();

        TabHost.TabSpec spec = tabHost.newTabSpec("Operation Report");
        spec.setContent(R.id.tab1);
        spec.setIndicator("Operation Report");
        tabHost.addTab(spec);

        spec = tabHost.newTabSpec("Ration Card Report");
        spec.setContent(R.id.tab2);
        spec.setIndicator("Ration Card Report");
        tabHost.addTab(spec);

        spec = tabHost.newTabSpec("Transaction Report");
        spec.setContent(R.id.tab3);
        spec.setIndicator("Transaction Report");
        tabHost.addTab(spec);

        spec = tabHost.newTabSpec("Stock Report");
        spec.setContent(R.id.tab4);
        spec.setIndicator("Stock Report");
        tabHost.addTab(spec);

        pager.setAdapter(new TabsPagerAdapter(getSupportFragmentManager()));
        pager.setOnPageChangeListener(this);
        tabHost.setOnTabChangedListener(this);
        //tabHost.setSelectedTabIndicatorColor(Color.WHITE);
        for (int i=0;i<4;i++) {
            TabWidget tw = (TabWidget) tabHost.findViewById(android.R.id.tabs);
            View tabView = tw.getChildTabViewAt(i);
            TextView tv = (TextView) tabView.findViewById(android.R.id.title);
            tv.setTextSize(15);
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
    }

    @Override
    public void onPageSelected(int position) {
        tabHost.setCurrentTab(position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {
    }

    @Override
    public void onTabChanged(String tabId) {
        int pageNumber;

        if (tabId.equals("Operation Report")) {
            pageNumber = 0;
        } else if (tabId.equals("Ration Card Report")) {
            pageNumber = 1;
        } else if (tabId.equals("Transaction Report")) {
            pageNumber = 2;
        } else if (tabId.equals("Stock Report")) {
            pageNumber = 3;
        } else {
            pageNumber = 0;
        }
        pager.setCurrentItem(pageNumber);
    }

    public void resetListSize(ListView listView) {
        setListViewHeightBasedOnChildren(listView);
    }

    private class TabsPagerAdapter extends FragmentPagerAdapter{


        public TabsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return new OperationReportFragment();
                case 1:
                    return new RationcardReportFragment();
                case 2:
                    return new TransactionReportFragment();
                case 3:
                    return new StockReportFragment();
            }
            return null;
        }

        @Override
        public int getCount() {
            return 4;
        }
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
    }

    @Override
    protected void updateCaptions() {
        languageReseter.updateCaptions();
    }

}
