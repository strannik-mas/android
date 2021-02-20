package com.example.student1.notification;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v4.app.RemoteInput;
import android.support.v4.app.TaskStackBuilder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
// import android.support.v7.app.NotificationCompat;
import android.support.v4.app.NotificationCompat;
import android.view.View;
import android.widget.RemoteViews;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private NotificationManagerCompat manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        manager = NotificationManagerCompat.from(this);

    }

    // Самое простое уведомление:
    // Маленькая иконка для статус бара,
    // заголовок и содержание
    public void simpleNotification(View view) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
        builder.setSmallIcon(android.R.drawable.sym_def_app_icon);
        builder.setContentTitle("Что-то случилось");
        builder.setContentText("Произошло что-то важное!");
        builder.setLargeIcon(
                BitmapFactory.decodeResource(getResources(), R.drawable.beachpicture)
        );

        manager.notify(R.id.SIMPLE_NOTIFICATION_ID, builder.build());
    }

    // Удаление уведомление - 
    // требуется идентификатор, с которым оно
    // было запущено
    public void simpleCancel(View view) {
        manager.cancel(R.id.SIMPLE_NOTIFICATION_ID);
    }


    // Запуск броузера через уведомление
    public void browserNotification(View view) {
        // Создание PendingIntent - "консерва" которую можно передать кому-то
        // чтобы кто-то другой (не наше приложение) выполнил этот интент
        Intent a2 = new Intent(this, A2.class);
        PendingIntent pA2 = PendingIntent.getActivity(
            this,
            333,
            a2,
            PendingIntent.FLAG_UPDATE_CURRENT
        );

        // Что будет выполнено при щелчке на
        // уведомление
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
        builder.setSmallIcon(android.R.drawable.sym_def_app_icon);
        builder.setContentTitle("Что-то случилось A2");
        builder.setContentText("Launch A2");
        builder.setContentIntent(pA2);
                // После выбора уведомления оно будет убрано
                // из статус-бара
        builder.setAutoCancel(true);

        manager.notify(444, builder.build());
    }

    // Сложное уведомление - содержит дополнительные кнопки
    // по щелчку на которые запустяться другие 
    // PendingIntent
    public void complexNotification(View view) {

        // PendingIntent для запуска броузера
        PendingIntent pBrowser = Utility.getUriPendingIntent(
                this,
                R.id.BROWSER_PENDING_ID,
                "http://www.louvre.fr"
        );

        // PendingIntent для запуска карты
        PendingIntent pMap = Utility.getUriPendingIntent(
                this,
                R.id.MAP_PENDING_ID,
                "geo:48.85,2.34"
        );
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
        builder.setContentTitle("Экскурсия по Лувру");
        builder.setContentText("Begin after 15 minutes");
        builder.setSmallIcon(android.R.drawable.btn_plus);
        // Акция - кнопка с иконкой, текстом и
        builder.addAction(
                new NotificationCompat.Action(
                        android.R.drawable.btn_star,
                        "in browser",
                        pBrowser
                )
        );
        builder.addAction(
                new NotificationCompat.Action(
                        android.R.drawable.btn_minus,
                        "in map",
                        pMap
                )
        );
        // PendingIntent
        manager.notify(R.id.LOUVRE_NOTIFICATION_ID, builder.build());
    }


    private int numberOfMessages = 12;

    // Новый вид уведомлений - "Большая картинка"
    // Будет полноэкранным (приоритет + звук)
    // Со стэком активностей
    public void bigPicture(View view) {

        TaskStackBuilder task = TaskStackBuilder.create(this);
        task.addNextIntent(new Intent(this, MainActivity.class));
        task.addNextIntent(new Intent(this, A2.class));
        task.addNextIntent(new Intent(this, A3.class));

        PendingIntent pTask = task.getPendingIntent(
                R.id.TASK_PENDING_ID,
                PendingIntent.FLAG_UPDATE_CURRENT
        );

        // Вначале создадим стиль и определим его
        NotificationCompat.BigPictureStyle style = new NotificationCompat.BigPictureStyle();
        style.setBigContentTitle("New messages");
        style.setSummaryText(
                String.format("У вас %d новых сообщений", numberOfMessages++)
        );

        // Картинка из ресурсов
        style.bigPicture(
            BitmapFactory.decodeResource(getResources(), R.drawable.lenna)
        );

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
        builder.setSmallIcon(android.R.drawable.btn_star_big_on);
        builder.setStyle(style);
        builder.setContentIntent(pTask);

        // Чтобы сделать уведомление полноэкранным
        // нужно установить высокий приоритет +
        // должна быть или вибрация или звук
        builder.setPriority(Notification.PRIORITY_MAX);
        builder.setSound(
                Uri.parse(
                        "android.resource://" + getPackageName() + "/" + R.raw.bikeringbell
                )
        );
        builder.setVibrate(new long[] {100, 500, 1000, 500, 1000});
        builder.setLights(Color.GREEN, 1000, 500);

        manager.notify(R.id.BIG_PICTURE_NOTIFICATION_ID, builder.build());
    }

    // Собственный вид уведомления
    public void custom(View view) {
        // Есть еще виды стилей
        // MediaStyle - для проигрывания звука или видео
        // InboxStyle - 6 или 7 строк текста
        /*NotificationCompat.InboxStyle style = new NotificationCompat.InboxStyle();
        style.setSummaryText("my notifications");
        style.setBigContentTitle("Message 1");
        style.addLine("Line 1");
        style.addLine("Line 2");
        style.addLine("Line 3");
        style.setSummaryText("+5 more...");
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
        builder.setSmallIcon(android.R.drawable.btn_star_big_on);
        builder.setStyle(style);

        manager.notify(R.id.CUSTOM_NOTIFICATION_ID, builder.build());*/

        // PendingIntent на запуск броузера
        PendingIntent pIntent = Utility.getUriPendingIntent(
                this,
                R.id.BROWSER_PENDING_ID,
                "http://google.com"
        );

        // Так как иерархия View не принадлежит
        // приложению, нужно использовать RemoteViews
        RemoteViews remote = new RemoteViews(
                getPackageName(),
                R.layout.custom
        );

        // Так устанавливаются значения виджетов внутри RemoteViews
        remote.setImageViewResource(R.id.picture, R.mipmap.ic_launcher);
        remote.setTextViewText(R.id.text, "Текст с картинкой");
        remote.setOnClickPendingIntent(R.id.button, pIntent);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
        builder.setSmallIcon(android.R.drawable.btn_star_big_on);
        builder.setContent(remote);

        manager.notify(R.id.CUSTOM_NOTIFICATION_ID, builder.build());

    }


    // Inline reply уведомление -
    // можно ввести текст
    public void inlineReply(View view) {
        final String replyLabel = "Ответ";

        RemoteInput remoteInput = new RemoteInput.Builder(getResources().getString(R.string.KEY_TEXT_REPLY))
                .setLabel(replyLabel)
                .build();

        Intent intent = new Intent(this, ReplyReceiver.class);

        PendingIntent replyPendingIntent = PendingIntent.getBroadcast(
                this,
                R.id.DIRECT_REPLY_PENDING_ID,
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT
        );

        NotificationCompat.Action replyAction = new NotificationCompat.Action.Builder(
                android.R.drawable.btn_plus,
                "Ответить",
                replyPendingIntent
        )
                .addRemoteInput(remoteInput)
                .build();

        Notification newMessageNotification = new NotificationCompat.Builder(this)
                .setSmallIcon(android.R.drawable.btn_minus)
                .setContentTitle("Как насчет в кино?")
                .setContentText("У меня появилось свободное время")
                .addAction(replyAction)
                .build();

        NotificationManagerCompat.from(this).notify(
                R.id.DIRECT_REPLY_NOTIFICATION_ID,
                newMessageNotification
        );

    }

    public void progress(View view) {
        Intent s = new Intent(this, ProgressService.class);
        startService(s);
    }
}















