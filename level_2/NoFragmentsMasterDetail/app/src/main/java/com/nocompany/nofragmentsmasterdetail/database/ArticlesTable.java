package com.nocompany.nofragmentsmasterdetail.database;

import android.database.sqlite.SQLiteDatabase;

public class ArticlesTable {


    public static final String TABLE_ARTICLES = "articles";
    public static final String COLUMN_ARTICLE_ID = "_id";
    public static final String COLUMN_ARTICLE_TITLE = "title";
    public static final String COLUMN_ARTICLE_URL = "url";
    public static final String COLUMN_ARTICLE_RATING = "rating";

    private static final String ARTICLES_CREATE = "create table "
            + TABLE_ARTICLES + "("
            + COLUMN_ARTICLE_ID
            + " integer primary key autoincrement, "
            + COLUMN_ARTICLE_TITLE
            + " text not null, "
            + COLUMN_ARTICLE_URL
            + " text not null, "
            + COLUMN_ARTICLE_RATING
            + " real not null "
            + ");";

    public static void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(ARTICLES_CREATE);
        populate(sqLiteDatabase);
    }

    private static void populate(SQLiteDatabase sqLiteDatabase)
    {

         sqLiteDatabase.execSQL("insert into articles (title, url, rating) values ("
                +"'Зюганов посоветовал Путину и Медведеву ознакомиться с опытом модернизации в СССР',"
                +"'http://www.gazeta.ru/politics/news/2016/03/05/n_8333843.shtml',"
                +" 0"
                +")");

        sqLiteDatabase.execSQL("insert into articles (title, url, rating) values ("
                +"'Парком «Царицыно» будет руководить директор «Коломенского»',"
                +"'http://www.gazeta.ru/social/news/2016/03/05/n_8333813.shtml',"
                +" 0"
                +")");

        sqLiteDatabase.execSQL("insert into articles (title, url, rating) values ("
                +"'В Воронеже мужчина кинул гранату в кабинет начальника',"
                +"'http://www.gazeta.ru/social/news/2016/03/05/n_8333777.shtml',"
                +" 0"
                +")");

        sqLiteDatabase.execSQL("insert into articles (title, url, rating) values ("
                +"'В Черновцах демонтировали слово «России» на вывеске Сбербанка',"
                +"'http://www.gazeta.ru/social/news/2016/03/05/n_8333783.shtml',"
                +" 0"
                +")");

        sqLiteDatabase.execSQL("insert into articles (title, url, rating) values ("
                +"'СК возбудил дело после гибели детей при пожаре в Ленинградской области',"
                +"'http://www.gazeta.ru/social/news/2016/03/05/n_8333771.shtml',"
                +" 0"
                +")");

        sqLiteDatabase.execSQL("insert into articles (title, url, rating) values ("
                +"'Президент Кипра призвал ЕС осознать важность хороших отношений с Россией',"
                +"'http://www.gazeta.ru/politics/news/2016/03/05/n_8333933.shtml',"
                +" 0"
                +")");

        sqLiteDatabase.execSQL("insert into articles (title, url, rating) values ("
                +"'Россия может обеспечить безопасность лидеров сирийской оппозиции',"
                +"'http://www.gazeta.ru/politics/news/2016/03/05/n_8333957.shtml',"
                +" 0"
                +")");

        sqLiteDatabase.execSQL("insert into articles (title, url, rating) values ("
                +"'Минобороны России зафиксировало 9 случаев нарушения перемирия в Сирии за сутки',"
                +"'http://www.gazeta.ru/army/news/8333969.shtml',"
                +" 0"
                +")");

        sqLiteDatabase.execSQL("insert into articles (title, url, rating) values ("
                +"'У Volkswagen могут затянутся переговоры с властями США из-за отзыва 600 тыс. авто',"
                +"'http://www.gazeta.ru/auto/news/2016/03/05/n_8333945.shtml',"
                +" 0"
                +")");

        sqLiteDatabase.execSQL("insert into articles (title, url, rating) values ("
                +"'«Салават Юлаев» выиграл у «Ак Барса» в седьмом матче серии и вышел в следующий раунд плей-офф',"
                +"'http://www.gazeta.ru/sport/news/2016/03/05/n_8333729.shtml',"
                +" 0"
                +")");

        sqLiteDatabase.execSQL("insert into articles (title, url, rating) values ("
                +"'Бывший министр юстиции Украины обжалует продление санкций Евросоюза против нее',"
                +"'http://www.gazeta.ru/social/news/2016/03/05/n_8333699.shtml',"
                +" 0"
                +")");

        sqLiteDatabase.execSQL("insert into articles (title, url, rating) values ("
                +"'Житель Белгорода вырастил на крыше дома 8 тысяч тюльпанов к 8 Марта',"
                +"'http://www.gazeta.ru/lifestyle/news/2016/03/05/n_8333567.shtml',"
                +" 0"
                +")");

        sqLiteDatabase.execSQL("insert into articles (title, url, rating) values ("
                +"'В Лондоне произошло столкновение фанатов «Тоттенхэма» и «Арсенала»',"
                +"'http://www.gazeta.ru/sport/news/2016/03/05/n_8333759.shtml',"
                +" 0"
                +")");

    }

    public static void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_ARTICLES);
        onCreate(sqLiteDatabase);
    }
}
