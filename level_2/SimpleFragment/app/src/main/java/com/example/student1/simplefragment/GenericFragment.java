package com.example.student1.simplefragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.NotificationCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * Created by Student1 on 21.09.2016.
 */
public class GenericFragment extends Fragment {
    /*
        Так делать нельзя!
        Конструктор не будет вызван при пересоздании фрагмента
        при изменении конфигурации устройства.
        Нужно использовать статическую инициализацию
    public GenericFragment(int color, String data)
    {

    }
    */

    // Параметры view, которые будем менять -
    // инициализируются в конструкторе
    private int backgroundColor;
    private String title;

    // Ключи для сохранения настроек
    private static final String FRAGMENT_COLOR = "FRAGMENT_COLOR";
    private static final String FRAGMENT_TITLE = "FRAGMENT_TITLE";

    // Статический инициализатор
    // 1. Создает фрагмент
    // 2. Создает бандл
    // 3. Сохраняет в бандл аргументы
    // 4. Присоединяет бандл к возвращаемому фрагменту
    // Инициализация параметрами из бандла происходит в onCreate
    // Используется для создания фрагментов которым нужно передать 
    // входные параметры (возможно различные)
    public static GenericFragment newInstance(int color, String data)
    {
        GenericFragment fragment = new GenericFragment();

        Bundle bundle = new Bundle();
        bundle.putInt(FRAGMENT_COLOR, color);
        bundle.putString(FRAGMENT_TITLE, data);
        fragment.setArguments(bundle);

        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        // Инициализируем данными из бандла
        if(bundle != null)
        {
            if(bundle.containsKey(FRAGMENT_COLOR))
            {
                backgroundColor = bundle.getInt(FRAGMENT_COLOR);
            }
            if(bundle.containsKey(FRAGMENT_TITLE))
            {
                title = bundle.getString(FRAGMENT_TITLE);
            }
        }
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Создаем иерархию вью
        View view = inflater.inflate(R.layout.fragment_generic, container, false);

        // Найдем нужные вью, чтобы поменять их атрибуты
        RelativeLayout relative = (RelativeLayout) view.findViewById(R.id.relative);
        relative.setBackgroundColor(backgroundColor);

        TextView text = (TextView) view.findViewById(R.id.text);
        text.setText(title);

        return view;
    }
}
