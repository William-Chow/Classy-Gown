package com.classygown.HomePage;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.classygown.BaseActivity.BaseActivityWithoutExit;
import com.classygown.R;

/**
 * Created by William Chow on 12/16/15.
 */
public class HomePageActivity extends BaseActivityWithoutExit implements View.OnClickListener {

    //    Toolbar toolbar;
    ViewPager pager;
    ViewPagerAdapter adapter;
    SlidingTabLayout tabs;
    CharSequence Titles[] = {"Home", "Shop", "Order", "More"};
    int numberOfTabds = 4;

    private TextView tvHomePageHeaderTitle;
    private ImageView ivSearch, ivShop;

    public static long lastClickTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page_classy_gown);

        init();
        setupTab();
        setOnClickButtonListener();
    }

    private void init() {
        tvHomePageHeaderTitle = (TextView) findViewById(R.id.tvHomePageHeaderTitle);
        ivSearch = (ImageView) findViewById(R.id.ivSearch);
        ivShop = (ImageView) findViewById(R.id.ivShop);
    }

    private void setupTab() {
        // Creating The ViewPagerAdapter and Passing Fragment Manager, Titles fot the Tabs and Number Of Tabs.
        adapter = new ViewPagerAdapter(getSupportFragmentManager(), Titles, numberOfTabds);

        // Assigning ViewPager View and setting the adapter
        pager = (ViewPager) findViewById(R.id.pager);
        pager.setAdapter(adapter);

        // Assiging the Sliding Tab Layout View
        tabs = (SlidingTabLayout) findViewById(R.id.tabs);
        tabs.setDistributeEvenly(true); // To make the Tabs Fixed set this true, This makes the tabs Space Evenly in Available width

        // Setting Custom Color for the Scroll bar indicator of the Tab View
        tabs.setCustomTabColorizer(new SlidingTabLayout.TabColorizer() {
            @Override
            public int getIndicatorColor(int position) {

                if (position == 0) {
                    tvHomePageHeaderTitle.setText(getResources().getText(R.string.home_page_home_title_header));
                    ivSearch.setVisibility(View.INVISIBLE);
                    ivShop.setVisibility(View.VISIBLE);
                } else if (position == 1) {
                    tvHomePageHeaderTitle.setText(getResources().getText(R.string.home_page_shop_title_header));
                    ivSearch.setVisibility(View.VISIBLE);
                    ivShop.setVisibility(View.VISIBLE);
                } else if (position == 2) {
                    tvHomePageHeaderTitle.setText(getResources().getText(R.string.home_page_order_title_header));
                    ivSearch.setVisibility(View.INVISIBLE);
                    ivShop.setVisibility(View.INVISIBLE);
                } else if (position == 3) {
                    tvHomePageHeaderTitle.setText(getResources().getText(R.string.home_page_more_title_header));
                    ivSearch.setVisibility(View.INVISIBLE);
                    ivShop.setVisibility(View.INVISIBLE);
                }

                return ContextCompat.getColor(HomePageActivity.this, R.color.tab_text_color);
            }
        });
        // Setting the ViewPager For the SlidingTabsLayout
        tabs.setViewPager(pager);
    }

    private void setOnClickButtonListener() {
        ivSearch.setOnClickListener(this);
        ivShop.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(isFastDoubleClick()){
            return;
        }
        switch (v.getId()) {
            case R.id.ivSearch:
                intentSearch();
                break;
            case R.id.ivShop:
                intentCart();
                break;
            default:
                break;
        }
    }

    public static boolean isFastDoubleClick() {
        long time = System.currentTimeMillis();
        if (Math.abs(time - lastClickTime) < 1000) {
            return true;
        }
        lastClickTime = time;
        return false;
    }

    private void intentSearch() {

    }

    private void intentCart() {
        Intent intent = new Intent(HomePageActivity.this, CartActivity.class);
        startActivity(intent);
        HomePageActivity.this.overridePendingTransition(R.anim.animation_slide_up, R.anim.animation_slide_down);
    }
}
