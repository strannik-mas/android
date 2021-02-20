package com.example.moneytracker;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.ActionMode;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;

public class MainActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener {
    private static final String TAG = "MainActivity";

    private ViewPager viewPager;
    private TabLayout tabLayout;
    public FloatingActionButton fab;
    private ActionMode actionMode;
    private boolean initialized = false;

    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle(R.string.app_name);

        Log.i(TAG, "onCreate: ");

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
//        getSupportActionBar().setTitle("New");

        viewPager = findViewById(R.id.viewPager);
        tabLayout = findViewById(R.id.tabLayout);
        tabLayout.setTabTextColors(
                this.getResources().getColor(R.color.tab_text_color_default),
                this.getResources().getColor(R.color.tab_text_color_active)
        );
        tabLayout.setSelectedTabIndicatorColor(
                this.getResources().getColor(R.color.tab_indicator_color)
        );

        viewPager.addOnPageChangeListener(this);

        tabLayout.setupWithViewPager(viewPager);

        fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int currentPage = viewPager.getCurrentItem();
                String type = null;
                if (currentPage == MainPagesAdapter.PAGE_INCOMES) {
                    type = Item.TYPE_INCOMES;
                } else if (currentPage == MainPagesAdapter.PAGE_EXPENSES) {
                    type = Item.TYPE_EXPENSES;
                } else {
                    type = Item.TYPE_UNCNOWN;
                }

                Log.i(TAG, "onClick: ");
                Intent intent = new Intent(MainActivity.this, AddItemActivity.class);
                intent.putExtra(AddItemActivity.TYPE_KEY, type);
                startActivityForResult(intent, ItemsFragment.ADD_ITEM_REQUEST_CODE);
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();

        if (((App) getApplication()).isAuthorized()) {
            if (! initialized) {
                initTabs();
            }
        } else {
            Intent intent = new Intent(this, AuthActivity.class);
            startActivity(intent);
        }
    }

    private void initTabs() {
        MainPagesAdapter adapter = new MainPagesAdapter(getSupportFragmentManager(), this);
        viewPager.setAdapter(adapter);
        initialized = true;
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        switch(position) {
            case MainPagesAdapter.PAGE_INCOMES:
            case MainPagesAdapter.PAGE_EXPENSES:
                fab.show();
                break;
            case MainPagesAdapter.PAGE_BALANCE:
                fab.hide();
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {
        switch (state) {
            case ViewPager.SCROLL_STATE_IDLE:
                fab.setEnabled(true);
                break;
            case ViewPager.SCROLL_STATE_DRAGGING:
            case ViewPager.SCROLL_STATE_SETTLING:
                fab.setEnabled(false);
                if (actionMode != null) {
                    actionMode.finish();
                }
                break;

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        for (Fragment fragment : getSupportFragmentManager().getFragments()) {
            fragment.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    public void onSupportActionModeStarted(@NonNull ActionMode mode) {
        super.onSupportActionModeStarted(mode);
        fab.hide();
        actionMode = mode;
    }

    @Override
    public void onSupportActionModeFinished(@NonNull ActionMode mode) {
        super.onSupportActionModeFinished(mode);
        fab.show();
        actionMode = null;
    }
}
