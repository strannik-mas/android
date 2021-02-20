package com.nocompany.settings;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.preference.PreferenceActivity;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;


// AppCompatPreferenceActivity это абстрактный класс, включенный в этот проект
// Он позволяет использовать стили Material Design для настроечной Activity

// Preference Activity нужна чтобы не писать самостоятельно интерфейс для 
// показа, изменения и хранения настроек приложения -
// внешний вид и сохранение настроек работают автоматически при 
// использовании xml специального формата - см комментарии в preferences.xml

public class Settings /* extends  PreferenceActivity  */ extends AppCompatPreferenceActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Кнопка Home на тулбаре
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        // Используем res/xml/preferences.xml 
        // для создания внешнего вида Activity
        addPreferencesFromResource(R.xml.preferences);
    }

    // Нажатие на Home кнопку на тулбаре обрабатывается как
    // Options menu со специальным идентификатором android.R.id.home
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case android.R.id.home:
                onBackPressed();
                return  true;
        }
        return super.onOptionsItemSelected(item);
    }
}
