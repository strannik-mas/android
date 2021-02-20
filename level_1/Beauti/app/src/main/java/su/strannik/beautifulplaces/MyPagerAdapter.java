package su.strannik.beautifulplaces;

import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.squareup.picasso.Picasso;

import java.util.List;

public class MyPagerAdapter extends PagerAdapter {

    private List<Place> places;

    public MyPagerAdapter(List<Place> places) {
        this.places = places;
    }

    @Override
    public int getCount() {
        return places.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view.equals(object);
    }

    /**
     * Вызывается пейджером если есть необходимость уничтожить view
     * @param container
     * @param position
     * @param object
     */
    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }

    /**
     * Создание view адаптером для пейджера из элемента контейнера
     * @param container
     * @param position
     * @return
     */
    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        Context context = container.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        ViewGroup layout = (ViewGroup) inflater.inflate(R.layout.page, container, false);
        Place place = places.get(position);

        String pictureUrl = place.getPicture();

        ImageView picture = (ImageView) layout.findViewById(R.id.picture);

        Picasso
                .get()                  //Используя контекст - уже не работает with(context)
                .load(pictureUrl)       //Загружаем картинку по URL
                .fit()                  //Автоматически определяем размеры ImageView
                .centerCrop()           //Масштабируем картинку
                .into(picture);         //в ImageView

        TextView placeName = (TextView) layout.findViewById(R.id.place);
        placeName.setText(place.getPlace());
        TextView placeDesc = (TextView) layout.findViewById(R.id.description);
        placeDesc.setText(place.getDescription());
        TextView oldPrice = (TextView) layout.findViewById(R.id.priceold);
        oldPrice.setText(place.getOldPrice());
        TextView newPrice = (TextView) layout.findViewById(R.id.pricenew);
        newPrice.setText(place.getNewPrice());

        //перечёркивание старой цены
        oldPrice.setPaintFlags(
                oldPrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG
        );


        //Возвращаем "надутый" и настроенный view
        container.addView(layout);
        return layout;
    }
}
