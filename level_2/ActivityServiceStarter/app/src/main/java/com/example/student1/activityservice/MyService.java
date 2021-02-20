package com.example.student1.activityservice;

import android.app.IntentService;
import android.content.Intent;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.NotificationCompat;
import android.util.Log;

public class MyService extends IntentService {

    public static final String DOWNLOAD_URL = "DOWNLOAD_URL";
    public static final String DOWNLOAD_PROGRESS = "DOWNLOAD_PROGRESS";
    public static final String DOWNLOAD_HANDLED = "DOWNLOAD_HANDLED";

    // Идентификатор уведомления как о прогрессе так и об
    // окончании загрузки файла
    public static final int DOWNLOAD_NOTIFICATION_ID = 555;

    public MyService() {
        super("MyService");
    }


    DownloadListener listener = new DownloadListener() {
        // Анонимная реализация загрузочного интерфейса
        @Override
        public void percentDownloaded(int percentDownloaded) {

            // Для передачи процента загрузки,
            // создадим интент с акцией, на которую подписан
            // ресивер
            Intent percentDownloadedIntent = new Intent(DOWNLOAD_PROGRESS);

            // Добавим в этот интент процент загрузки файла
            /******* нужно что-то сделать  *********/
            percentDownloadedIntent.putExtra(DOWNLOAD_PROGRESS, percentDownloaded);

            // Методика позволяет синхронно послать броадкаст
            LocalBroadcastManager.getInstance(MyService.this).sendBroadcastSync(percentDownloadedIntent);
            // И проверить, был ли он кем-либо обработан
            boolean handled = percentDownloadedIntent.getBooleanExtra(DOWNLOAD_HANDLED, false);
            // Обработан он может быть только активити
            if(!handled)
                // Поэтому, если броадкаст не обработан,
                // повесим уведомление
                showNotification(percentDownloaded);
            else
                // Если же броадкаст обработан, уберем уведомление, если оно
                // вдруг висит
                cancelNotification();
        }
    };


    @Override
    protected void onHandleIntent(Intent intent) {
        // Получим URL из Intent, с которым нас вызвали
        String url = intent.getStringExtra(DOWNLOAD_URL);

        // Получим результат
        /******* нужно что-то сделать  *********/
        Result<String> res = Utility.doDownload(url, listener);


        // Если файл успешно загружен,
        // нужно уведомить об этом активити
        if(res.result != null) {


            // Создадим интент, который будет содержать путь к загруженному файлу, с
            // акцией, на которую подписан ресивер
            Intent fileDownloadedIntent = new Intent(DOWNLOAD_URL);
            // Положим в этот интент путь к файлу
            /******* нужно что-то сделать  *********/
            fileDownloadedIntent.putExtra(DOWNLOAD_URL, res.result);

            // Есть два типа броадкастов:
            // Общесистемый - все приложения в системе увидят (при желании)
            // если зарегистрированы на соответствующий IntentFilter
            // sendBroadcast(i);

            // Броадкаст внутри приложения через LocalBroadcastManager
            LocalBroadcastManager.getInstance(this).sendBroadcastSync(fileDownloadedIntent);
            boolean handled = fileDownloadedIntent.getBooleanExtra(DOWNLOAD_HANDLED, false);
            if(!handled)
                // Если интент не был обработан ресивером,
                // покажем уведомление
                showNotification(res.result);
        }
    }


    // Уведомление, содержащее в себе прогресс
    public void showNotification(int downloadProgress)
    {
        NotificationCompat.Builder builder = Utility.getBuilder(this);
        builder.setContentTitle("Downloading file");
        builder.setContentText("Downloaded Percent");
        builder.setProgress(100, downloadProgress, false);
        NotificationManagerCompat.from(this).notify(DOWNLOAD_NOTIFICATION_ID, builder.build());
    }

    // Отмена уведомления
    public void cancelNotification()
    {
        NotificationManagerCompat.from(this).cancel(DOWNLOAD_NOTIFICATION_ID);
    }

    // Уведомление, содержащее в себе путь к загруженному файлу
    public void showNotification(String downloadFileName)
    {
        NotificationCompat.Builder builder = Utility.getBuilder(this);
        builder.setContentTitle("Download complete");
        builder.setContentText("File Downloaded");
        builder.setContentIntent(Utility.getFileDownloadedPendingIntent(this, downloadFileName));
        NotificationManagerCompat.from(this).notify(DOWNLOAD_NOTIFICATION_ID, builder.build());
    }

    // Загрузочный интерфейс - позволяет коду, осуществляющему загрузку,
    // уведомить о проценте загрузки
    public interface DownloadListener
    {
        void percentDownloaded(int i);
    }
}
