package com.example.student1.threaddemo;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    // Тэг фрагмента
    private static final String FRAGMENT = "FRAGMENT";

    // Виджеты
    private Button changeText;
    private ProgressBar progress;
    private MyTask task;

    /**
     * выполняется когда закрывается активность
     * @return MyTask
     */
    /*@Override
    public Object onRetainCustomNonConfigurationInstance() {
        return task;
    }*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        changeText = (Button) findViewById(R.id.change_text);
        progress = (ProgressBar) findViewById(R.id.progress);

        /*task = (MyTask) getLastNonConfigurationInstance();
        if (task != null) {
            task.newActivity(this);
        }*/
    }

    // Попытка изменить виджет из фонового треда
    public void changeText(View view) {
        Thread t1 = new Thread() {
            @Override
            public void run() {
                try {
                    sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                changeText.setText("Changed!");     //не работает  - нельзя менять UI основного потока - интерфейс андроида однопоточный
            }
        };
        t1.start();

    }

    // Для изменения виджета из фонового потока можно использовать
    // View.post(Runnable) или runOnUiThread(Runnable)
    // Передаваемый Runnable выполняется в основном потоке
    public void changeTextSuccess(final View view) {
        new Thread() {
            @Override
            public void run() {
                try {
                    sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                /*view.post(new Runnable() {
                    @Override
                    public void run() {
                        changeText.setText("Changed by view.post!");
                    }
                });*/
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        //тоже плохо - имеем ссылку на активность, т.е. нельзя ее закрыть
                        changeText.setText("Changed!");
                    }
                });

            }
        }.start();

        /*new MyThread().start(); //тоже нужна ссылка на активность*/
    }

    class MyThread extends Thread {
        @Override
        public void run() {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    //тоже плохо - имеем ссылку на активность, т.е. нельзя ее закрыть
                    changeText.setText("Changed2!");
                }
            });
        }
    }

    // Нельзя останавливать или загружать
    // основной тред
    public void pause(View view) {
        try {
            Thread.sleep(10000); //нельзя ничего делать в основном потоке
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    // Неправильный вариант обновления прогресс бара
    public void progressBad(View view) {
        MyTask task = new MyTask(this);
        task.execute();
    }

    // Правильный вариант обновления прогресс бара
    public void progressGood(View view) {
        // Найдем фргмент по тагу
        FragmentManager manager = getSupportFragmentManager();
        TaskFragment fragment = (TaskFragment) manager.findFragmentByTag(FRAGMENT);
            // Если его нет, создадим
        if (fragment == null) {
            fragment = new TaskFragment();
            // и добавим
            manager.beginTransaction().add(fragment, FRAGMENT).commit();
        }

        // Запустим прогресс
        fragment.startProgress();
    }

    // Разделяемый ресурс
    final int[] array = {0};

    private /*synchronized */void add()
    {
            synchronized (array) {
                array[0]++;
            }
    }

    // Совместный доступ к разделяемому ресурсу
    public void jointAccess(View view) {
        array[0] = 0;


        Runnable r = new Runnable() {
            @Override
            public void run() {
                for(int i = 0; i < 1_000_000; i++)
                {
                    add();
                }
            }
        };

        // Создаем два треда
        Thread t1 = new Thread(r);
        Thread t2 = new Thread(r);

        // Запускаеи
        t1.start();
        t2.start();

        // Ждем в главном треде окончания
        // запущенных тредов
        try {

            t1.join();
            t2.join();


        } catch (Exception e) {
            e.printStackTrace();
        }

        // Выводим результат
        Toast.makeText(this, "Value is: " + array[0], Toast.LENGTH_SHORT).show();
    }

    public void progressUpdate(Integer value) {
        progress.setProgress(value);
    }
}
