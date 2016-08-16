package com.ky3h.farmwork;


import android.content.Context;
import android.graphics.Color;
import android.hardware.fingerprint.FingerprintManager;
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
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.ky3h.farmwork.adapter.MyAdapter;
import com.ky3h.farmwork.adapter.MyRecyclearAdapter;
import com.ky3h.farmwork.base.BaseActivity;
import com.ky3h.farmwork.utils.SystemBarTintManager;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

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

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
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
        tab.setupWithViewPager(pager);
    }

    PopupWindow popupWindow;

    public void ShowPop(List list) {
        if (popupWindow == null) {
            popupWindow = new PopupWindow();
            View view = LayoutInflater.from(this).inflate(R.layout.fragment_home, null);
            RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recyclearView);
            recyclerView.setAdapter(new MyRecyclearAdapter(this, list));
            popupWindow.setContentView(view);

        } else {
            View view = LayoutInflater.from(this).inflate(R.layout.fragment_home, null);
            RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recyclearView);
            recyclerView.setAdapter(new MyRecyclearAdapter(this, list));
            popupWindow.setContentView(view);

        }

    }
}
