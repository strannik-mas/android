package com.loftschool.moneytrackermarch18;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;

import com.loftschool.moneytrackermarch18.api.Item;

public class MainPagesAdapter extends FragmentPagerAdapter {

    public static final int PAGE_INCOMES = 0;
    public static final int PAGE_EXPENSES = 1;
    public static final int PAGE_BALANCE = 2;

    private String[] titles;

    public MainPagesAdapter(FragmentManager fm, Context context) {
        super(fm);

        titles = context.getResources().getStringArray(R.array.tab_title);
    }

    @Override
    public Fragment getItem(int position) {
        Log.i("MainPagesAdapter", "getItem position = " + position);

        switch (position) {
            case PAGE_INCOMES:
                return ItemsFragment.createItemsFragment(Item.TYPE_INCOMES);


            case PAGE_EXPENSES:
                return ItemsFragment.createItemsFragment(Item.TYPE_EXPENSES);

            case PAGE_BALANCE:
                return new BalanceFragment();

            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles[position];
    }
}
