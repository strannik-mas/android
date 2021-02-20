package com.example.student.kitten;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.CursorAdapter;
import android.widget.GridView;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

// INFO https://www.flickr.com/services/api/explore/flickr.photos.search

public class MainActivity extends AppCompatActivity implements Callback<Result>, AdapterView.OnItemClickListener {

    // Константа для передачи URL в детальную Activity
    // через Intent
    public static final String IMAGE_URL = "IMAGE_URL";

    private GridView grid;
    private CursorAdapter adapter;
    private PhotosDBHelper helper;

    private Retrofit retrofit;
    private FlickrService service;

    private static final  String sql = "  insert into   " +
            PhotosTable.TABLE_PHOTOS +
            "   (   " +
            PhotosTable.COLUMN_URL +
            "   )   " +
            " values (  ?  ) ;  ";

    private SQLiteStatement statement;


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
        );

        setContentView(R.layout.activity_main);

        // Сделаем тулбар
        Toolbar bar = (Toolbar) findViewById(R.id.top_toolbar);
        setSupportActionBar(bar);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setTitle("Kitten");

        grid = (GridView) findViewById(R.id.grid);

        // Чтобы скроллинг "вверх" грида вызывал
        // исчезновение тулбара.
        grid.setNestedScrollingEnabled(true);

        adapter = new PhotoAdapter(this, null, 0);
        helper = new PhotosDBHelper(this);

        statement = helper.getWritableDatabase().compileStatement(sql);

        grid.setAdapter(adapter);

        retrofit = new Retrofit.Builder()
                .baseUrl("https://api.flickr.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        service = retrofit.create(FlickrService.class);

        grid.setOnItemClickListener(this);

        grid.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                /**
                 * SCROLL_STATE_IDLE    - состояние покоя
                 * SCROLL_STATE_TOUCH_SCROLL    - состояние скролирования, когда палец удерживается на экране
                 * SCROLL_STATE_FLING   - состояние скролирования, когда палец НЕ удерживается на экране
                 */
                if (!loading)
                {
                    if(scrollState == SCROLL_STATE_IDLE)
                    {
                        int last = grid.getLastVisiblePosition();
                        int total = grid.getCount();
                        if( (total - last) < threshold  )
                        {
                            loadMore(currentPage + 1, term);
                            Log.d("happy", "onScrollStateChanged");
                        }
                    }
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

            }
        });

        // Загрузить картинки
        startOver();
    }

    // Порог
    // Если разница между крайним видимым элементом GridView и количеством
    // элементов в GridView меньше порога, запросим 
    // еще картинки с сервера
    private static final  int threshold = 40;


    private static String createUrl(Photo p) {
        // Сервисная функция для получения URL картинки по объекту
        // Подробности https://www.flickr.com/services/api/misc.urls.html
        //Log.d("happy", url);
        return String.format(
                "https://farm%s.staticflickr.com/%s/%s_%s_q.jpg",
                p.getFarm(),
                p.getServer(),
                p.getId(),
                p.getSecret()
        );
    }

    // Начинаем с первой страницы
    private int currentPage = 1;

    // Запрос по-умолчанию
    private String term = "kitten";


    // Выполняется при старте приложения 
    // или изменении поискового запроса
    private void startOver() {

        helper.getWritableDatabase().delete(
                PhotosTable.TABLE_PHOTOS,
                null,
                null
        );

        // Начнем с первой страницы
        currentPage = 1;

        // Загрузим
        loadMore(currentPage, term);
    }

    // Исользуется для загрузки новой порции изображений из сервиса
    // Вызов Retrofit
    private void loadMore(int page, String search) {
        loading = true;

        Call<Result> call = service.search(
                "flickr.photos.search",
                "da8f3977e280b5976247b4c617622fe9",
                search,
                "json",
                1,
                page
        );

        call.enqueue(this);


    }


    // Только один процесс загрузки данных с сервера
    private boolean loading = false;


    @Override
    public void onResponse(Call<Result> call, Response<Result> response) {
        Result result = response.body();
        if(result.getStat().equals("ok"))
        {

            currentPage = result.getPhotos().getPage();

            Photos photos = result.getPhotos();

            SQLiteDatabase db = helper.getWritableDatabase();
            db.beginTransaction();
            try {
                for (Photo p : photos.getPhoto()) {
//                    Log.d("happy_name", p.getTitle());
                    String url = createUrl(p);
                    // insert into photo (url) values (?)
                    statement.bindString(1, url);
                    statement.execute();
                }
                db.setTransactionSuccessful();
            }
            catch (Exception e) { }
            finally {
                db.endTransaction();
            }
        }
        Cursor cursor = getPhotoCursor();
        adapter.swapCursor(cursor);
        loading = false;
    }

    private Cursor getPhotoCursor() {
        Cursor cursor = helper.getReadableDatabase().query(
                PhotosTable.TABLE_PHOTOS,
                null,
                null,
                null,
                null,
                null,
                null
        );
        return cursor;
    }

    @Override
    public void onFailure(Call<Result> call, Throwable t) {
        Log.d("happy", t.getMessage());
        loading = false;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(this, Detail.class);

        Cursor cursor = adapter.getCursor();

        cursor.moveToPosition(position);

        String url = cursor.getString(
                cursor.getColumnIndex(PhotosTable.COLUMN_URL)
        );

        intent.putExtra(IMAGE_URL, url);

        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.main_search:
                handleSearch(item);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void handleSearch(MenuItem item) {
        SearchView searchView = (SearchView) item.getActionView();
        item.expandActionView();
        searchView.setQuery(term, false);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if (! term.equals(query)){
                    term = query;
                    startOver();
                }
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }
}













