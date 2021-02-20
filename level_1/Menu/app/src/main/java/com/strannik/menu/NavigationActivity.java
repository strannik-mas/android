package com.strannik.menu;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

public class NavigationActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    private TextView action;
    private BottomNavigationView bottom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);

        action = findViewById(R.id.action);
        bottom = findViewById(R.id.navigation);
        bottom.setOnNavigationItemSelectedListener(this);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        if (menuItem.getItemId() == R.id.bottom_git){
            action.setText("GIT");
            return true;
        } else if (menuItem.getItemId() == R.id.bottom_home){
            action.setText("HOME");
            return true;
        } else if (menuItem.getItemId() == R.id.bottom_svn){
            action.setText("SVN");
            return true;
        }
        return false;
    }
}