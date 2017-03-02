package com.markfeldman.tasktrack;

import android.support.v4.app.Fragment;

import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.markfeldman.tasktrack.fragments.Calendar;
import com.markfeldman.tasktrack.fragments.Contacts;
import com.markfeldman.tasktrack.fragments.Home;
import com.markfeldman.tasktrack.fragments.Tasks;

public class MainActivity extends AppCompatActivity implements Home.onFragmentClicked {
    private Fragment selectedFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        selectedFragment = new Home();
        if (savedInstanceState==null){
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.container,selectedFragment)
                    .commit();
        }
    }

    @Override
    public void buttonClicked(View v) {
        int id = v.getId();
        switch (id){
            case (R.id.cal_button):{
                selectedFragment = new Calendar();
                break;
            }
            case (R.id.tasks_button):{
                selectedFragment = new Tasks();
                break;
            }
            case (R.id.contacts_button):{
                selectedFragment = new Contacts();
                break;
            }
        }
        FragmentTransaction fragmentTransaction;
        fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.container,selectedFragment)
                .addToBackStack(null)
                .commit();
    }
}
