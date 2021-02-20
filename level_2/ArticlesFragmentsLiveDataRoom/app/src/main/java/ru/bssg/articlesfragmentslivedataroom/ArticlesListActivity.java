package ru.bssg.articlesfragmentslivedataroom;

import android.arch.lifecycle.LifecycleActivity;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.util.List;

import ru.bssg.articlesfragmentslivedataroom.room.Article;

// https://developer.android.com/topic/libraries/architecture/viewmodel.html
// https://developer.android.com/topic/libraries/architecture/livedata.html


/*
    Дополнительная информация
    https://developer.android.com/guide/components/fragments.html
    https://developer.android.com/training/basics/fragments/index.html
    https://developer.android.com/reference/android/app/Fragment.html

    https://www.youtube.com/watch?v=3VXPsCUYioM&index=13&list=PLQC2_0cDcSKBNCR8UWeElzCUuFkXASduz
    https://www.youtube.com/watch?v=gD4E_TLbKeU&index=14&list=PLQC2_0cDcSKBNCR8UWeElzCUuFkXASduz

*/


public class ArticlesListActivity extends BaseLifecycleActivity
{

    ArticlesViewModel model;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.articles_list);

        model = ViewModelProviders.of(this).get(ArticlesViewModel.class);

        View view = findViewById(R.id.article_layout);

        boolean twoSided = view != null;

        model.doubleSided(twoSided);

        FragmentManager manager = getSupportFragmentManager();
        Fragment articleFragment = manager.findFragmentByTag("two");
        Fragment listFragment = manager.findFragmentByTag("one");
        Fragment addedFragment = manager.findFragmentByTag("added");

        FragmentTransaction transaction = null;
        if(listFragment == null)
        {
            if(transaction == null)
                transaction = manager.beginTransaction();

            transaction.add(R.id.list_layout, new ArticlesListFragment(), "one");


        }
        if(twoSided && articleFragment == null)
        {
            if(transaction == null)
                transaction = manager.beginTransaction();

            transaction.add(R.id.article_layout, new ArticleFragment(), "two");


        }
        if(twoSided && addedFragment != null && addedFragment.isAdded())
        {
            onBackPressed();
        }
        if(transaction != null)
            transaction.commit();

    }
}
