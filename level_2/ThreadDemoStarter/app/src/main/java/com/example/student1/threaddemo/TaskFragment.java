package com.example.student1.threaddemo;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

// "Безголовый" фрагмент
// используется для хранения AsyncTask
public class TaskFragment extends Fragment {

    // Ссылка на AsyncTask
    private MyTask task;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Фрагмент не пересоздается при уничтожении активности
        setRetainInstance(true);    //после создания новой активности, фрагмент к ней присоеденится
    }

    // Запуск таска из активности
    public void startProgress()
    {
        if (task == null || task.getStatus() != AsyncTask.Status.RUNNING) {
            task = new MyTask((MainActivity) getActivity());
        }
        task.execute();
    }

    // Метод жизненного цикла фрагмента
    // Активность поменялась - "старая" удалилась, "новая" создалась
    // Используется для актуализации ссылки таска на активность
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        task.newActivity((MainActivity) context);
    }

    // Метод жизненного цикла фрагмента
    @Override
    public void onDetach() {
        super.onDetach();
        // Обнулить ссылку на активити
        task.deleteActivity();
    }
}
