package com.strannik.beautifulplacesrecycler;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

import com.google.android.material.snackbar.Snackbar;
import com.viewpagerindicator.UnderlinePageIndicator;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    //Контейнер для мест
    private List<Place> places = new ArrayList<>();
    private RecyclerView recycler;
    private RecyclerAdapter recyclerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Прозрачный статус-бар
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        setContentView(R.layout.activity_main);

        // Настрока тулбара - определяется в разметке
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        if(toolbar != null)
            toolbar.setTitle(R.string.app_name);

        // Установка тулбара в качестве экшн-бара,
        // при этом экшн-бар скрывается через стиль приложения
        setSupportActionBar(toolbar);

        recycler = (RecyclerView) findViewById(R.id.pagerRecycler);
        recycler.setHasFixedSize(true);

        // Добавляем места в список
        places.add(new Place("Монако", "В Столице суверенного княжества Монако живет больше миллионеров, чем настройщиков роялей", "$1180", "$999.95", "http://media.globalchampionstour.com/cache/750x429/assets/monaco_2016.jpg"));
        places.add(new Place("Прага", "Культурная столица восточной европы - город, который хорош в любое время года", "$180", "$80", "http://www.pragueczechtravel.com/images/prague_banner.jpg"));
        places.add(new Place("Таллинн", "Столица прибалтийской жемчужины Эстонии", "$245", "$15", "http://cbpspb.ru/assets/images/bbb/tallinn-1.jpg"));
        places.add(new Place("Озеро Комо", "Живописное озеро в северной Италии", "$845", "$799", "https://www.travcoa.com/sites/default/files/styles/flexslider_full/public/tours/images/veniceandlakecomo-hero-italy-lake-como-menaggio-41965520.jpg?itok=fROUMZe2"));

        recyclerAdapter = new RecyclerAdapter(places);

        recycler.setAdapter(recyclerAdapter);
        recycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        /*SnapHelper snapHelper = new LinearSnapHelper();
        snapHelper.attachToRecyclerView(recycler);

        // Находим индикатор
        UnderlinePageIndicator underlinePageIndicator = (UnderlinePageIndicator)findViewById(R.id.indicator);

        // Связываем индикатор с пейджером
        if(underlinePageIndicator != null) {

        }*/
    }

    public void fabClicked(View view) {
        Snackbar.make(findViewById(R.id.coordinator), "Ваше путешествие заказано", Snackbar.LENGTH_SHORT).show();
    }
}