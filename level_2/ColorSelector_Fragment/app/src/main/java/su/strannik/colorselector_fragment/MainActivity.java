package su.strannik.colorselector_fragment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ListView list;
    private static final String TAG = "MainActivity";

    private List<ColorItem> colors = new ArrayList<ColorItem>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        list = findViewById(R.id.list);

        colors.add(new ColorItem("Green", Color.GREEN));
        colors.add(new ColorItem("Lite Gray", Color.LTGRAY));
        colors.add(new ColorItem("Black", Color.BLACK));
        colors.add(new ColorItem("Red", Color.RED));
        colors.add(new ColorItem("Yellow", Color.YELLOW));
        colors.add(new ColorItem("Blue", Color.BLUE));
        colors.add(new ColorItem("Magenta", Color.MAGENTA));
        colors.add(new ColorItem("Cyan", Color.CYAN));
        colors.add(new ColorItem("Gray", Color.GRAY));
        colors.add(new ColorItem("Dark gray", Color.DKGRAY));

        ColorAdapter adapter = new ColorAdapter(this, R.layout.item, colors);
        list.setAdapter(adapter);
    }

    public void colorClick(View view) {
        TextView text = view.findViewById(R.id.colorSelector);
        String title = (String) text.getText();

        ColorDrawable colorDrawable = (ColorDrawable) view.getBackground();
        int colorId = colorDrawable.getColor();

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.host, GenericColorFragment.newInstance(colorId, title));
        transaction.addToBackStack(null);
        transaction.commit();
    }
}