package ru.bssg.articlesfragmentslivedataroom;

import android.arch.lifecycle.LifecycleFragment;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;


import java.util.List;

import ru.bssg.articlesfragmentslivedataroom.adapter.ArticlesAdapter;
import ru.bssg.articlesfragmentslivedataroom.room.Article;

// ListFragment уже содержит в себе ListView
public class ArticlesListFragment extends LifecycleFragment {

    private ListView list;
    private ArticlesViewModel model;

    private boolean doubleSided = false;

    // Активность, на которой будет размещен фрагмент, должна имплментить
    // этот интерфейс, чтобы фрагмент мог передавать ей данные
    public interface ArticleListFragmentHost {
        public void articleSelected(Article article);
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        model = ViewModelProviders.of(getActivity()).get(ArticlesViewModel.class);

        model.getDoubleSided().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable Boolean twoSided) {
                if (twoSided != null) {
                    doubleSided = twoSided;
                }
            }
        });

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        View view = inflater.inflate(R.layout.fragment_article_list, container, false);
        list = (ListView) view.findViewById(R.id.list);


        list.setAdapter(new ArticlesAdapter(getContext(), R.layout.articles_list_item));
        model.getArticles().observe(this, new Observer<List<Article>>() {
            @Override
            public void onChanged(@Nullable List<Article> articles) {
                ((ArticlesAdapter) list.getAdapter()).updateArticles(articles);
            }
        });


        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Article a = (Article) list.getAdapter().getItem(position);
                model.articleSelected(a);

                if (!doubleSided) {
                    FragmentManager manager = getActivity().getSupportFragmentManager();
                    Fragment fragment = manager.findFragmentByTag("added");
                    if(fragment == null)
                        fragment = new ArticleFragment();

                    manager.beginTransaction().replace(R.id.list_layout, fragment, "added").addToBackStack(null).commit();
                }

            }
        });

        return view;

    }
}
