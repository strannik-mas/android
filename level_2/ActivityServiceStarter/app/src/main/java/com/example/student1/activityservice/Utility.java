package com.example.student1.activityservice;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Environment;
import android.support.v7.app.NotificationCompat;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

// Загрузка файла по УРЛ, возвращение либо полного пути к файлу 
// либо исключения
public class Utility {
    // Чтобы не сообщать о каждом прочитанном байте,
    // будем стараться сообщать только если количество прочитанных байт на столько
    // процентов больше того количества байт, о котором уже было сообщено
    private static final  int percentDifference = 5;

    public static final int ACTIVITY_PENDING_INTENT_ID = 444;

    // PendingIntent для запуска активити из уведомления о
    // завершении загрузки файла
    public static PendingIntent getFileDownloadedPendingIntent(Context context, String downloadedFileName)
    {
        Intent intent = new Intent(context, MainActivity.class);
        intent.putExtra(MyService.DOWNLOAD_URL, downloadedFileName);

        return PendingIntent.getActivity(context, ACTIVITY_PENDING_INTENT_ID, intent, PendingIntent.FLAG_UPDATE_CURRENT);
    }

    // Общий код для создания уведомления
    public static NotificationCompat.Builder getBuilder(Context context)
    {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
        builder.setSmallIcon(android.R.drawable.ic_media_pause);
        builder.setAutoCancel(true);
        return builder;
    }

    // Код для загрузки файла с листенером
    // загруженных процентов
    public static Result<String> doDownload(String url, MyService.DownloadListener listener)
    {
        Result<String> result = new Result<>();

        // Каталог, соответствующий external storage
        File storage = Environment.getExternalStorageDirectory();

        try {
            // Создадим файл с именем
            // abc.....def
            File output = File.createTempFile("acb", "def", storage);

            URL target = new URL(url);
            HttpURLConnection connection = (HttpURLConnection) target.openConnection();

            // Сколько всего байт в картинке
            int contentLength = connection.getContentLength();

            // Сколько байт прочитано
            int bytesWritten = 0;

            // Про сколько байт сообщено
            int bytesSignalled = 0;

            InputStream input = connection.getInputStream();
            FileOutputStream fos = new FileOutputStream(output.getCanonicalPath());

            try {
                // Счетчик сколько байт прочитали за раз
                int read = 0;

                // Буфер, куда читаем байты из InputStream
                // в 1024 нет ничего магического - просто размер буфера
                // Используют еще 8*1024, 16*1024 и т.п.
                byte [] bytes = new byte[1024];

                // Пока количество считанных байт не равно -1
                // (сигнал о том, что байты в InputBuffer закончились)
                // читаем в буфер 
                while(  (read = input.read(bytes)) != -1  )
                {
                    // Поспим немножко
                    Thread.sleep(20);

                    // и пишем в OutputStream
                    fos.write(bytes, 0, read);

                    bytesWritten += read;
                    if(listener != null && contentLength > 0) {
                        if ((bytesWritten - bytesSignalled)*100/contentLength >= percentDifference) {
                            listener.percentDownloaded(bytesWritten * 100 / contentLength);
                            bytesSignalled = bytesWritten;
                        }
                    }
                }
                // Возвращаем в результате путь к файлу
                result.result = output.getCanonicalPath();
            }
            // Обязательно закроем потоки ввода-вывода
            finally {
                input.close();
                fos.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
            // Или исключение
            result.exception = e;
        }
        return result;
    }
}
