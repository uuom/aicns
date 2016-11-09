package com.asiainfo.aicns;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.asiainfo.aicns.common.view.NoScrollViewPager;
import com.asiainfo.aicns.customer.view.CustomerFragment;
import com.asiainfo.aicns.overview.view.OverviewFragment;
import com.asiainfo.aicns.trouble.view.TroubleFragment;

public class MainActivity extends AppCompatActivity {

    TabLayout mTabLayout;
    Toolbar toolbar;
    NoScrollViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        App.addActivity(this);

        mTabLayout = (TabLayout) findViewById(R.id.tabs);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        mViewPager = (NoScrollViewPager) findViewById(R.id.viewPager);

        setSupportActionBar(toolbar);

        String[] tabTitles = this.getResources().getStringArray(R.array.tab_title);
        Fragment[] fragments  = new Fragment[]{new TroubleFragment()
                , new OverviewFragment(),new CustomerFragment()};
        ViewPageAdapter adapter = new ViewPageAdapter(this.getSupportFragmentManager(), tabTitles, fragments);
        mViewPager.setAdapter(adapter);
        mViewPager.setOffscreenPageLimit(3);
        mTabLayout.setupWithViewPager(mViewPager);
        mTabLayout.setTabMode(TabLayout.MODE_FIXED);
    }


    class ViewPageAdapter extends FragmentPagerAdapter {

        private String[] tabTitles;
        private Fragment[] fragments;

        public ViewPageAdapter(FragmentManager fm, String[] tabTitles, Fragment[] fragments) {
            super(fm);
            this.tabTitles = tabTitles;
            this.fragments = fragments;
        }

        @Override
        public Fragment getItem(int position) {
            return fragments[position];
        }

        @Override
        public int getCount() {
            return tabTitles.length;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return tabTitles[position];
        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.action_logout:
                App.clear();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    long currentTime = 0;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK) {

            if (currentTime != 0){
                long waitTime = System.currentTimeMillis() - currentTime;
                if (waitTime < 2000){
                    App.clear();
                }else {
                    currentTime = 0;
                    Toast.makeText(this, "再按一次退出", Toast.LENGTH_SHORT).show();
                    currentTime = System.currentTimeMillis();
                }
            }else{
                Toast.makeText(this, "再按一次退出", Toast.LENGTH_SHORT).show();
                currentTime = System.currentTimeMillis();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
