package com.example.student1.fragmentdialog;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;


// Наследуемся от DialogFragment - 
// это один из предварительно настроенных классов фрагментов -
// так же в качестве базовых классов для фрагментов можно использовать
// ListFragment и MapFragment.
public class MyDialog extends DialogFragment
{

    // Интерфейс который должен имплементить тот, кто желает получать 
    // вызовы из фрагмента
    public interface MyDialogHost
    {
        void positive();
        void negative();
    }


    // Переопределить функцию интерфейса, возвращающую диалог

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Используя Builder
        AlertDialog.Builder b = new AlertDialog.Builder(getActivity());
        // Создать заголовок диалога
        b.setTitle("Удаление файла");
        b.setMessage("Файл будет удалён. Вы уверены?");
        // Создать "отрицательную" кнопку
        // Переопределить коллбэк, выполнямый при нажатии на нее
        // В конце закрывать диалог
        b.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                try {
                    MyDialogHost host = (MyDialogHost) getActivity();
                    host.negative();
                } catch (Exception e) {}
                finally {
                    dialog.cancel();
                }
            }
        });
        // Создать "положительную" кнопку
        // Переопределить коллбэк, выполнямый при нажатии на нее
        // В конце закрывать диалог
        b.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                try {
                    MyDialogHost host = (MyDialogHost) getActivity();
                    host.positive();
                } catch (Exception e) {}
                finally {
                    dialog.dismiss();
                }
            }
        });
        // Возврать диалог
        return b.create();
    }

}
