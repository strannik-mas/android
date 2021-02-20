package com.nocompany.camerapreview;


import android.content.Context;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;

import java.io.File;

public class Utility {
    // Полезный метод для сканирования только что загруженного или созданного
    // медиа файла - картинки, музыки, видео и т.п.
    public static void scan(Context context, String filename) {
        if (!TextUtils.isEmpty(filename)) {
            MediaScannerConnection.scanFile(
                    context,
                    new String[]{ filename },
                    null,
                    new MediaScannerConnection.OnScanCompletedListener() {
                        @Override
                        public void onScanCompleted(String path, Uri uri) {
                            Log.d("happy", "Scanned path: " + path);
                            Log.d("happy", "Scanned uri: " + uri);
                        }
                    }
            );
        }
    }
}
