package com.example.student1.tabs;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class GenericFragment extends Fragment {
    /*
        Так делать нельзя!
        Конструктор не будет вызван при пересоздании фрагмента
        при изменении конфигурации устройства
        Нужно использовать статическую инициализацию
    public GenericFragment(int color, String data)
    {

    }
    */

    private int backgroundColor;
    private String title;

    private static final String FRAGMENT_COLOR = "FRAGMENT_COLOR";
    private static final String FRAGMENT_TITLE = "FRAGMENT_TITLE";

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
        View view = inflater.inflate(R.layout.fragment_generic, container, false);

        RelativeLayout relative = (RelativeLayout) view.findViewById(R.id.relative);
        relative.setBackgroundColor(backgroundColor);

        TextView text = (TextView) view.findViewById(R.id.text);
        text.setText(title);

        return view;
    }
}
