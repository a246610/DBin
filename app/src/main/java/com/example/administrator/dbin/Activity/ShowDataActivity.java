package com.example.administrator.dbin.Activity;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.example.administrator.dbin.Adapter.TabPagerAdapter;
import com.example.administrator.dbin.ClassLibrary.SlidingTabLayout;
import com.example.administrator.dbin.Fragment.AlertInternetFragment;
import com.example.administrator.dbin.ClassLibrary.Http;
import com.example.administrator.dbin.R;

public class ShowDataActivity extends AppCompatActivity {

    private TabPagerAdapter tabAdapter;
    private ViewPager pager;
    private SlidingTabLayout tab;

    public static final int CODE_DATA = 0;
    public static final int CODE_ROUTE = 1;

    public static final String PASS_OBJECT_KEY = "pass_object_key";

    private CharSequence[] charSequences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_data);

        charSequences = getResources().getStringArray(R.array.string_array_name);
        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setLogo(R.mipmap.ic_launcher);
        setSupportActionBar(toolbar);

        Http http = new Http(getBaseContext());

        if (!http.isNetworkAvailable()){
            AlertInternetFragment alertInternetFragment = new AlertInternetFragment();
            alertInternetFragment.show(getSupportFragmentManager(),"");
        }else{

            tabAdapter = new TabPagerAdapter(getSupportFragmentManager(), charSequences);

            pager = (ViewPager) findViewById(R.id.pager);
            pager.setAdapter(tabAdapter);

            tab = (SlidingTabLayout) findViewById(R.id.tabs);
            //make the Tabs Fixed set this true, This makes the tabs Space Evenly in Available width
            tab.setDistributeEvenly(true);

            tab.setCustomTabColorizer(new SlidingTabLayout.TabColorizer() {
                @Override
                public int getIndicatorColor(int position) {
                    return getResources().getColor(R.color.ColorTabScroll);
                }
            });

            tab.setViewPager(pager);
        }
    }
}
