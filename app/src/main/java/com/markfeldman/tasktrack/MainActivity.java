package com.markfeldman.tasktrack;

import android.content.res.Configuration;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import com.markfeldman.tasktrack.fragments.Calendar;
import com.markfeldman.tasktrack.fragments.Contacts;
import com.markfeldman.tasktrack.fragments.Home;
import com.markfeldman.tasktrack.fragments.Tasks;

public class MainActivity extends AppCompatActivity implements Home.onFragmentClicked {
    private Fragment selectedFragment;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle drawerToggle;
    private CharSequence drawerTitle;
    private CharSequence title;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        selectedFragment = new Home();
        if (savedInstanceState==null){
            title  = getString(R.string.fragment_home_title);
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.container,selectedFragment)
                    .addToBackStack(title.toString())
                    .commit();
            if (getSupportActionBar()!=null){
                getSupportActionBar().setTitle(title);
            }
        }
        title = drawerTitle = getTitle();
        String[] drawerItems = getResources().getStringArray(R.array.navigation_drawer_items);
        drawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);
        listView = (ListView)findViewById(R.id.drawer_list);
        listView.setAdapter(new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, drawerItems));
        listView.setOnItemClickListener(new DrawerItemClickListener());
        drawerToggle = new ActionBarDrawerToggle(this,drawerLayout,R.string.drawer_open,R.string.drawer_close) {

            /** Called when a drawer has settled in a completely closed state. */
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                if (getSupportActionBar()!=null){
                    getSupportActionBar().setTitle(title);
                }
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }

            /** Called when a drawer has settled in a completely open state. */
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                if (getSupportActionBar()!=null){
                    getSupportActionBar().setTitle(drawerTitle);
                }
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }
        };
        drawerLayout.addDrawerListener(drawerToggle);
        if (getSupportActionBar()!=null){
            getSupportActionBar().setLogo(R.drawable.ic_action_expand);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true);
        }
        drawerToggle.syncState();
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        drawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        drawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:{
                if (drawerLayout.isDrawerOpen(listView)){
                    drawerLayout.closeDrawer(listView);
                }else{
                    drawerLayout.openDrawer(listView);
                }
                return true;
            }
            default:return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        int count = this.getSupportFragmentManager().getBackStackEntryCount();
        if(count==0){
            this.finish();
        }else if (count>0){
            title = getSupportFragmentManager().getBackStackEntryAt(count-1).getName();
            if (getSupportActionBar()!=null){
                getSupportActionBar().setTitle(title);
            }
        }
    }

    @Override
    public void buttonClicked(View v) {
        int id = v.getId();
        switch (id){
            case (R.id.cal_button):{
                selectedFragment = new Calendar();
                title = getString(R.string.fragment_cal_title);
                break;
            }
            case (R.id.tasks_button):{
                selectedFragment = new Tasks();
                title = getString(R.string.fragment_task_title);
                break;
            }
            case (R.id.contacts_button):{
                selectedFragment = new Contacts();
                title = getString(R.string.fragment_contacts_title);
                break;
            }
        }
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.container,selectedFragment)
                .addToBackStack(title.toString())
                .commit();

        if (getSupportActionBar()!=null){
            getSupportActionBar().setTitle(title);
        }
    }
    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            selectItem(position);
        }

        private void selectItem (int position){
            switch (position){
                case (0) :{
                    selectedFragment = new Home();
                    title = getString(R.string.fragment_home_title);
                    break;
                }
                case (1) :{
                    selectedFragment = new Calendar();
                    title = getString(R.string.fragment_cal_title);
                    break;
                }
                case (2) :{
                    selectedFragment = new Contacts();
                    title = getString(R.string.fragment_contacts_title);
                    break;
                }
                case (3) :{
                    selectedFragment = new Tasks();
                    title = getString(R.string.fragment_task_title);
                    break;
                }
            }
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.container,selectedFragment)
                    .addToBackStack(title.toString())
                    .commit();

            if (getSupportActionBar()!=null){
                getSupportActionBar().setTitle(title);
            }
            drawerLayout.closeDrawer(listView);
        }
    }
}
