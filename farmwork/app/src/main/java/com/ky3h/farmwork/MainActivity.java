package com.ky3h.farmwork;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Build;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.Toolbar;
import android.telephony.TelephonyManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.ky3h.farmwork.adapter.MyAdapter;
import com.ky3h.farmwork.base.BaseActivity;
import com.ky3h.farmwork.bean.User;
import com.ky3h.farmwork.netrequest.UserManager;
import com.ky3h.farmwork.utils.NoHttpUtil;
import com.yolanda.nohttp.NoHttp;
import com.yolanda.nohttp.rest.CacheMode;
import com.yolanda.nohttp.rest.OnResponseListener;
import com.yolanda.nohttp.rest.Request;
import com.yolanda.nohttp.rest.RequestQueue;
import com.yolanda.nohttp.rest.Response;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends BaseActivity {
    private Toolbar toolbar;
    private ViewPager pager;
    private Fragment_home fragment_home;
    private Fragment_quality fragment_quality;
    private Fragment_type fragment_type;
    private Fragment_mine fragment_mine;
    private List<Fragment> fragmentList;
    private List<String> titles;
    private DrawerLayout drawerLayout;
    private CircleImageView user_photo;
    private TextView user_name;
    private NavigationView navigationView;

    @Override
    public void touchListener(View v) {

    }


    @Override
    public void setContentView() {
        setContentView(R.layout.activity_main);
    }

    @Override
    public void dealLogicBeforeInitView() {

    }

    @Override
    public void initView() {
        toolbar = (Toolbar) findViewById(R.id.tooolbar);
        setSupportActionBar(toolbar);

        final ActionBar ab = getSupportActionBar();
        ab.setHomeAsUpIndicator(R.mipmap.ic_menu);
        ab.setDisplayHomeAsUpEnabled(true);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        if (navigationView != null) {
            setupDrawerContent(navigationView);

        }

        titles = new ArrayList<>();
        titles.add("首页");
        titles.add("分类");
        titles.add("精品");
        titles.add("我的");
        toolbar.setTitle("我的应用");
        toolbar.setTitleTextColor(Color.parseColor("#FFFFFF"));
        pager = (ViewPager) findViewById(R.id.viewpager);
        fragment_home = new Fragment_home();
        fragment_mine = new Fragment_mine();
        fragment_quality = new Fragment_quality();
        fragment_type = new Fragment_type();
        fragmentList = new ArrayList<Fragment>();
        FloatingActionButton fb = (FloatingActionButton) findViewById(R.id.fab);
        fb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Here's a Snackbar", Snackbar.LENGTH_LONG)
                        .setAction("Action", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                            }
                        }).show();
            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.sample_actions, menu);
        return true;
    }
  OnResponseListener<Bitmap> lsitner=new OnResponseListener<Bitmap>() {


      @Override
      public void onStart(int what) {

      }

      @Override
      public void onSucceed(int what, Response<Bitmap> response) {
          Bitmap bitmap =  response.get();
          user_photo.setImageBitmap(bitmap);
      }

      @Override
      public void onFailed(int i, String s, Object o, Exception e, int i1, long l) {

      }


      @Override
      public void onFinish(int what) {

      }


  };
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                View view = navigationView.getHeaderView(0);
                user_photo = (CircleImageView) view.findViewById(R.id.drawer_userImage);
                user_name = (TextView) view.findViewById(R.id.drawer_userName);
                User user = UserManager.getLoginedUser();
                RequestQueue requestQueue = NoHttpUtil.getRequestQueue();
                Request request = NoHttp.createImageRequest(user.getMemberImage());
                request.setCacheMode(CacheMode.NONE_CACHE_REQUEST_NETWORK);
                requestQueue.add(1, request,lsitner);
                user_name.setText(user.getName());
                break;
            case R.id.menu_night_mode_system:
                setNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM);
                break;
            case R.id.menu_night_mode_day:
                setNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                break;
            case R.id.menu_night_mode_night:
                setNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                break;
            case R.id.menu_night_mode_auto:
                setNightMode(AppCompatDelegate.MODE_NIGHT_AUTO);
                break;

        }
        return super.onOptionsItemSelected(item);
    }   

    private void setupDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        menuItem.setChecked(true);
                        drawerLayout.closeDrawers();
                        return true;
                    }
                });
    }

    private void setNightMode(@AppCompatDelegate.NightMode int nightMode) {
        AppCompatDelegate.setDefaultNightMode(nightMode);

        if (Build.VERSION.SDK_INT >= 11) {
            recreate();
        }
    } 

    @Override
    public void afterInitView() {
        fragmentList.add(fragment_home);
        fragmentList.add(fragment_type);
        fragmentList.add(fragment_quality);
        fragmentList.add(fragment_mine);
        pager.setAdapter(new MyAdapter(getSupportFragmentManager(), fragmentList, titles));
        pager.setCurrentItem(0);
        TabLayout tab = (TabLayout) findViewById(R.id.tabs);
        String number = getUserPhoneNumber();
        tab.setupWithViewPager(pager);
        showShortToast(number);
    }

    private String getUserPhoneNumber() {
        TelephonyManager telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        return telephonyManager.getLine1Number();
    };
}
