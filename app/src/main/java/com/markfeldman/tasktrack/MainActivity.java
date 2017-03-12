package com.markfeldman.tasktrack;

import android.content.res.Configuration;
import android.support.annotation.NonNull;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TableLayout;

import com.markfeldman.tasktrack.fragments.Calendar;
import com.markfeldman.tasktrack.fragments.Contacts;
import com.markfeldman.tasktrack.fragments.Home;
import com.markfeldman.tasktrack.fragments.Tasks;
import com.markfeldman.tasktrack.utilities.ViewPagerAdapter;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {
    private Fragment selectedFragment;
    private CollapsingToolbarLayout collapsingToolbarLayout;
    private String title;
    private final String INSTANCE_STATE_SAVE_TITLE = "save_title";
    private ArrayList<Fragment> fragments = new ArrayList<>();
    private ArrayList<String> titles = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        collapsingToolbarLayout = (CollapsingToolbarLayout)findViewById(R.id.collapsing_toolbar);
        setSupportActionBar(toolbar);

        fragments.addAll(Arrays.asList(new Tasks(),new Calendar(),new Contacts()));
        titles.addAll(Arrays.asList(getResources().getString(R.string.fragment_task_title),
                getResources().getString(R.string.fragment_cal_title),getResources().getString(R.string.fragment_contacts_title)));
        TabLayout tabLayout = (TabLayout)findViewById(R.id.tabs);
        final FloatingActionButton fab = (FloatingActionButton)findViewById(R.id.fab);
        fab.hide();
        ViewPager viewPager = (ViewPager)findViewById(R.id.container);
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(),fragments,titles);
        viewPager.setAdapter(viewPagerAdapter);
        viewPager.setCurrentItem(1);


        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position == 1) {
                    fab.hide();
                }else {
                    fab.show();
                }

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        tabLayout.setupWithViewPager(viewPager);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


        selectedFragment = new Home();
        if (savedInstanceState==null){
           /* title  = getString(R.string.fragment_home_title);
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.container,selectedFragment)
                    .addToBackStack(title)//WORKS HERE
                    .commit();*/

            if (getSupportActionBar()!=null){
                getSupportActionBar().setDisplayShowTitleEnabled(true);
            }
        }else{
            if (getSupportActionBar()!=null){
                getSupportActionBar().setDisplayShowTitleEnabled(false);
            }
        }
    }



    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(INSTANCE_STATE_SAVE_TITLE, title);
    }

}
