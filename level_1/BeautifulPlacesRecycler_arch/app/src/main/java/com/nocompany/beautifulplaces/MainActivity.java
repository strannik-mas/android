package com.nocompany.beautifulplaces;

import android.support.design.widget.Snackbar;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SnapHelper;
import android.support.v7.widget.Toolbar;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import com.viewpagerindicator.UnderlinePageIndicator;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    // private ViewPager mPager;
    private RecyclerView recycler;

    // Контейнер для мест
    private List<Place> places = new ArrayList<Place>();

    // private PagerAdapter mPagerAdapter;
    private RecyclerPagerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Прозрачный статус-бар
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        setContentView(R.layout.activity_recycler);

        // Настрока тулбара - определяется в разметке
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        if(toolbar != null)
            toolbar.setTitle(R.string.app_name);

        // Установка тулбара в качестве экшн-бара,
        // при этом экшн-бар скрывается через стиль приложения
        setSupportActionBar(toolbar);

        // Находим пейджер
        // mPager = (ViewPager) findViewById(R.id.pager);
        recycler = (RecyclerView) findViewById(R.id.pager);
        recycler.setHasFixedSize(true);


        // Добавляем места в список
        places.add(new Place("Монако", "В Столице суверенного княжества Монако живет больше миллионеров, чем настройщиков роялей", "$1180", "$999.95", "http://media.globalchampionstour.com/cache/750x429/assets/monaco_2016.jpg"));
        places.add(new Place("Прага", "Культурная столица восточной европы - город, который хорош в любое время года", "$180", "$80", "http://www.pragueczechtravel.com/images/prague_banner.jpg"));
        places.add(new Place("Таллинн", "Столица прибалтийской жемчужины Эстонии", "$245", "$15", "http://cbpspb.ru/assets/images/bbb/tallinn-1.jpg"));
        places.add(new Place("Озеро Комо", "Живописное озеро в северной Италии", "$845", "$799", "https://www.travcoa.com/sites/default/files/styles/flexslider_full/public/tours/images/veniceandlakecomo-hero-italy-lake-como-menaggio-41965520.jpg?itok=fROUMZe2"));

        // Адаптер
        // mPagerAdapter = new MyPagerAdapter(places);

        adapter = new RecyclerPagerAdapter(places);

        // Устанавливаем адаптер пейджеру
        // mPager.setAdapter(mPagerAdapter);
        recycler.setAdapter(adapter);
        recycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        SnapHelper snapHelper = new LinearSnapHelper();
        snapHelper.attachToRecyclerView(recycler);

        // Находим индикатор
        UnderlinePageIndicator underlinePageIndicator = (UnderlinePageIndicator)findViewById(R.id.indicator);

        // Связываем индикатор с пейджером
        if(underlinePageIndicator != null) {
            // underlinePageIndicator.setViewPager(mPager);
        }
    }

    public void fabClicked(View view) {
        Snackbar.make(findViewById(R.id.coordinator), "Ваше путешествие заказано", Snackbar.LENGTH_SHORT).show();
    }
}
