package su.strannik.metro;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ListView list;

    private List<Station> stations = new ArrayList<Station>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        list = findViewById(R.id.list);

        stations.add(new Station("Пролетарская", Color.GREEN));
        stations.add(new Station("Китай-город", Color.LTGRAY));
        stations.add(new Station("Кузнецкий мост", Color.GREEN));
        stations.add(new Station("Смоленская", Color.RED));
        stations.add(new Station("Киевская", Color.GREEN));
        stations.add(new Station("Таганская", Color.LTGRAY));
        stations.add(new Station("Студенческая", Color.RED));
        stations.add(new Station("Римская", Color.GREEN));
        stations.add(new Station("Комсомольская", Color.LTGRAY));
        stations.add(new Station("Исторический музей", Color.RED));
        stations.add(new Station("Рижская", Color.GREEN));
        stations.add(new Station("Советская", Color.LTGRAY));

        MetroAdapter adapter = new MetroAdapter(this, R.layout.item, stations);
        list.setAdapter(adapter);
    }
}