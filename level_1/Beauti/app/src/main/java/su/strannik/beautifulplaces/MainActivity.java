package su.strannik.beautifulplaces;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;
import com.viewpagerindicator.UnderlinePageIndicator;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ViewPager mPager;

    //Контейнер для мест
    private List<Place> places = new ArrayList<>();

    private PagerAdapter mPageAdapter;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Прозрачный статус-бар
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        setContentView(R.layout.activity_main);

        //Настройка тулбара - определяется в разметке
        Toolbar toolbar = (Toolbar) findViewById(R.id.tollbar);
        if (toolbar != null) {
            toolbar.setTitle("Beautiful Places");
        }

        //Установка тулбара в качестве экшн-бара
        //при этом экшн-бар скрывается через стиль приложения
        setSupportActionBar(toolbar);

        //Находим пейджер
        mPager = (ViewPager) findViewById(R.id.pager);

        //Добавляем места в список
        places.add(new Place("Монако", "Монако – город и крошечное государство на побережье Средиземного моря, которое граничит с Францией. Оно известно своими шикарными казино, причалом, на котором швартуются дорогие яхты, и этапом престижной автогонки \"Формула-1\", который проходит на улицах Монако.", "$245", "$150", "https://minfin.com.ua/img/2020/51179377/4162540ae4301ebd2dce5f16167748cd.jpeg"));
        places.add(new Place("Прага", "Пра́га (чеш. Praha [ˈpraɦa]) — статутный город и столица Чешской Республики, административный центр Среднечешского края и двух его районов", "$1150", "$1000", "https://upload.wikimedia.org/wikipedia/commons/thumb/8/8d/TanahLot_2014.JPG/1024px-TanahLot_2014.JPG"));
        places.add(new Place("Таллин", "столица Эстонской Республики, крупный пассажирский и грузовой морской порт", "$245", "$150", "https://cdn.footballua.tv/i/image_650x360/uploads/football-media/image/5e0/8ed/deb/5e08eddebccf6_41588_bali_3.jpeg"));
        places.add(new Place("Озеро Комо", "третье по величине озеро Италии", "$845", "$799", "https://upload.wikimedia.org/wikipedia/commons/thumb/8/8d/TanahLot_2014.JPG/1024px-TanahLot_2014.JPG"));

        //Адаптер
        mPageAdapter = new MyPagerAdapter(places);

        //Устанавливаем адаптер пейджеру
        mPager.setAdapter(mPageAdapter);

        //находим индикатор
        UnderlinePageIndicator underlinePageIndicator = (UnderlinePageIndicator) findViewById(R.id.indicator);

        //связываем индикатор с пейджером
        if (underlinePageIndicator != null) {
            underlinePageIndicator.setViewPager(mPager);
        }
    }

    public void fabClicked(View view) {
        int position = mPager.getCurrentItem();
        String trip = places.get(position).getPlace();
        Snackbar.make(findViewById(R.id.coordinator), "Ваше путешествие в " + trip + " заказано!", Snackbar.LENGTH_LONG).show();
    }
}