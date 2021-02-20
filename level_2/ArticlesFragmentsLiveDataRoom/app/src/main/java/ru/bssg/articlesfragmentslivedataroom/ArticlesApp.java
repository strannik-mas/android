package ru.bssg.articlesfragmentslivedataroom;

import android.app.Application;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

import ru.bssg.articlesfragmentslivedataroom.room.Article;

public class ArticlesApp extends Application {

    public static final String INITIALIZED = "INITIALIZED";

    private static ArticlesApp sInstance;

    private static volatile Repository sRepository;

    @Override
    public void onCreate() {
        super.onCreate();

        sInstance = this;

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        if (!prefs.contains(INITIALIZED)) {
            insertArticles();
            prefs.edit().putString(INITIALIZED, INITIALIZED).apply();
        }
    }

    @NonNull
    public static ArticlesApp getAppContext() {
        return sInstance;
    }

    @NonNull
    public static Repository getRepository()
    {
        Repository result = sRepository;
        if (result == null) {
            synchronized(ArticlesApp.class) {
                result = sRepository;
                if (result == null) {
                    sRepository = result = new Repository(ArticlesApp.getAppContext());
                }
            }
        }
        return result;
    }


    public void insertArticles()
    {
        final List<Article> articles = new ArrayList<>();
        articles.add(new Article(0, "Зюганов посоветовал Путину и Медведеву ознакомиться с опытом модернизации в СССР", "http://www.gazeta.ru/politics/news/2016/03/05/n_8333843.shtml", 0));
        articles.add(new Article(1, "Парком «Царицыно» будет руководить директор «Коломенского»", "http://www.gazeta.ru/social/news/2016/03/05/n_8333813.shtml", 0));
        articles.add(new Article(2, "В Воронеже мужчина кинул гранату в кабинет начальника", "http://www.gazeta.ru/social/news/2016/03/05/n_8333777.shtml", 0));
        articles.add(new Article(3, "В Черновцах демонтировали слово «России» на вывеске Сбербанка", "http://www.gazeta.ru/social/news/2016/03/05/n_8333783.shtml", 0));
        articles.add(new Article(4, "СК возбудил дело после гибели детей при пожаре в Ленинградской области", "http://www.gazeta.ru/social/news/2016/03/05/n_8333771.shtml", 0));
        articles.add(new Article(5, "Президент Кипра призвал ЕС осознать важность хороших отношений с Россией", "http://www.gazeta.ru/politics/news/2016/03/05/n_8333933.shtml", 0));
        articles.add(new Article(6, "Россия может обеспечить безопасность лидеров сирийской оппозиции", "http://www.gazeta.ru/politics/news/2016/03/05/n_8333957.shtml", 0));
        articles.add(new Article(7, "Минобороны России зафиксировало 9 случаев нарушения перемирия в Сирии за сутки", "http://www.gazeta.ru/army/news/8333969.shtml", 0));
        articles.add(new Article(8, "У Volkswagen могут затянутся переговоры с властями США из-за отзыва 600 тыс. авто", "http://www.gazeta.ru/auto/news/2016/03/05/n_8333945.shtml", 0));
        articles.add(new Article(9, "«Салават Юлаев» выиграл у «Ак Барса» в седьмом матче серии и вышел в следующий раунд плей-офф", "http://www.gazeta.ru/sport/news/2016/03/05/n_8333729.shtml", 0));
        articles.add(new Article(10, "Бывший министр юстиции Украины обжалует продление санкций Евросоюза против нее", "http://www.gazeta.ru/social/news/2016/03/05/n_8333699.shtml", 0));
        articles.add(new Article(11, "Житель Белгорода вырастил на крыше дома 8 тысяч тюльпанов к 8 Марта", "http://www.gazeta.ru/lifestyle/news/2016/03/05/n_8333567.shtml", 0));
        articles.add(new Article(12, "В Лондоне произошло столкновение фанатов «Тоттенхэма» и «Арсенала»", "http://www.gazeta.ru/sport/news/2016/03/05/n_8333759.shtml", 0));

        new Thread() {
            @Override
            public void run() {
                getRepository().insertArticles(articles);
            }
        }.start();
    }
}
