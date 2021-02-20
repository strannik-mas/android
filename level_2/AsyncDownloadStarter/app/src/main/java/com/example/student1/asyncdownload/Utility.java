package com.example.student1.asyncdownload;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;


public class Utility {

    // Загрузка данных с сервера по URL,
    // возвращение или Bitmap или исключения
    public static Result<Bitmap> download(String url)
    {
        Result<Bitmap> res = new Result<>();
        try {
            URL u = new URL(url);
            HttpURLConnection connection = (HttpURLConnection) u.openConnection();
            InputStream inputStream = connection.getInputStream();
            // Если все ОК возвратим Bitmap
            res.result = BitmapFactory.decodeStream(inputStream);
        } catch (Exception e) {
            e.printStackTrace();
            // Если нет, исключение
            res.exception = e;
        }
        return res;
    }
}
