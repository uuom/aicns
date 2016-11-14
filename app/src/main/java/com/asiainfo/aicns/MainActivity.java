package com.asiainfo.aicns;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.asiainfo.aicns.bean.VersionBean;
import com.asiainfo.aicns.common.api.Api;
import com.asiainfo.aicns.common.view.NoScrollViewPager;
import com.asiainfo.aicns.customer.view.CustomerFragment;
import com.asiainfo.aicns.overview.view.OverviewFragment;
import com.asiainfo.aicns.trouble.view.TroubleFragment;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    TabLayout mTabLayout;
    Toolbar toolbar;
    NoScrollViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        App.addActivity(this);

        //check update
        try {
            PackageManager manager = this.getPackageManager();
            PackageInfo info = manager.getPackageInfo(this.getPackageName(), 0);
            int version = info.versionCode;
            checkVersion(version);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        mTabLayout = (TabLayout) findViewById(R.id.tabs);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        mViewPager = (NoScrollViewPager) findViewById(R.id.viewPager);

        setSupportActionBar(toolbar);

        String[] tabTitles = this.getResources().getStringArray(R.array.tab_title);
        Fragment[] fragments  = new Fragment[]{new OverviewFragment(),new TroubleFragment(),new CustomerFragment()};
        ViewPageAdapter adapter = new ViewPageAdapter(this.getSupportFragmentManager(), tabTitles, fragments);
        mViewPager.setAdapter(adapter);
        mViewPager.setOffscreenPageLimit(3);
        mTabLayout.setupWithViewPager(mViewPager);
        mTabLayout.setTabMode(TabLayout.MODE_FIXED);
    }

    /**
     * 版本检查
     * @param version
     */
    private void checkVersion(final int version) {
        Observable.create(new Observable.OnSubscribe<VersionBean>() {
            @Override
            public void call(final Subscriber<? super VersionBean> subscriber) {
                String url = Api.REQUEST_BASE_URL + Api.CHECK_APP_UPDATE_URL + "&versionCode="+version;
                App.getOkHttpUtil().get(url, new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {}

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        String resp = response.body().string();
                        try {
                            JSONObject jsonObject = new JSONObject(resp);
                            int code = jsonObject.getInt("code");
                            if (code == 1){
                                VersionBean vb = new VersionBean();
                                vb.setDescription(jsonObject.getString("description"));
                                vb.setGetUrl(jsonObject.getString("getUrl"));
                                vb.setVersionCode(jsonObject.getString("versionCode"));
                                subscriber.onNext(vb);
                                subscriber.onCompleted();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });

                //for test
                /*VersionBean vb = new VersionBean();
                vb.setDescription("修复BUG");
                vb.setGetUrl("http://fir.im/aicns");
                vb.setVersionCode("20161110");
                subscriber.onNext(vb);
                subscriber.onCompleted();*/
            }
        }).subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(new Action1<VersionBean>() {
                @Override
                public void call(final VersionBean versionBean) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                    builder.setTitle("版本更新");
                    builder.setMessage(versionBean.getDescription());
                    builder.setNegativeButton("下次再说", null);
                    builder.setPositiveButton("立即更新", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(versionBean.getGetUrl()));
                            MainActivity.this.startActivity(intent);
                        }
                    });
                    builder.show();
                }
            });
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
