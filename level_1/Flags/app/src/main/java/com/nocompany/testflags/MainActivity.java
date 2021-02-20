package com.nocompany.testflags;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

/*
 * Вешний вид активити меняется при повороте экрана:
 * в портретном режиме флаги отображаются один над другим,
 * в альбомном - слева и справа друг от друга.
 *
 * Каждый флаг размечен в отдельном xml-файле.
 *
 * Layout-файл для портретного вида размещен в каталоге res/layout,
 * для альбомного вида в каталоге res/layout-land.
 *
 * Активити ничего не делает - за счет загрузки layout-файлов из
 * каталогов с нужными квалификаторами, внешний вид активити
 * меняется автоматически.
 *
 * Дополнительная информация по ресурсам:
 * https://developer.android.com/guide/topics/resources/more-resources.html?hl=ru
 *
 * Дополнительная информация по квалификаторам:
 * https://developer.android.com/guide/topics/resources/providing-resources.html?hl=ru
 */

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
