package com.example.student1.tabs;

import android.graphics.Color;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    // Виджеты
    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;

    ViewPagerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        tabLayout = (TabLayout) findViewById(R.id.tabs);
        viewPager = (ViewPager) findViewById(R.id.viewpager);

        // Устанавливаем Toolbar в качестве ActionBar
        // ActionBar удаляется с помощью темы в styles.xml
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Tab activity");

        tabLayout.setupWithViewPager(viewPager);

        adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(GenericFragment.newInstance(0xaabb0000, "First"), "First");
        adapter.addFragment(GenericFragment.newInstance(0xffaacc00, "Second"), "Second");
        adapter.addFragment(GenericFragment.newInstance(0x0122ffaf, "Third"), "Third");
        adapter.addFragment(GenericFragment.newInstance(0xacff0000, "Fourth"), "Fourth");
        /*adapter.addFragment(GenericFragment.newInstance(0xacff0000, "Fifth"), "Fifth");
        adapter.addFragment(GenericFragment.newInstance(0xacff0000, "Sixth"), "Sixth");
        adapter.addFragment(GenericFragment.newInstance(0xacff0000, "Seventh"), "Seventh");*/


        viewPager.setAdapter(adapter);
    }


    // Цвета - целые числа
    // 0xaaccbbaa 0x - шестнадцатиричный ARGB Alpha Red Green Blue 00-ff
    // 0xccbbaa - без Alpha - полность непрозрачный

    static class ViewPagerAdapter extends FragmentPagerAdapter
    {
        // Контейнеры для фрагментов и заголовков табов
        private List<String> titles = new ArrayList<>();
        private List<Fragment> fragments = new ArrayList<Fragment>();


        // Конструктор
        public ViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }


        @Override
        public Fragment getItem(int position) {
            GenericFragment fragment = (GenericFragment) fragments.get(position);
            return fragment;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return  titles.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }

        // Добавление фрагментов во ViewPager
        public void addFragment(Fragment fragment, String title)
        {
            fragments.add(fragment);
            titles.add(title);
        }
    }

}
