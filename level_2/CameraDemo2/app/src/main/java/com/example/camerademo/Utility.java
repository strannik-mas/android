package com.example.camerademo;

import android.content.Context;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.util.Log;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class Utility {
    public static File createFile(Context context,  @NonNull String suffix) throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES);

        if(storageDir.exists() == false)
        {
            storageDir.mkdirs();
        }

        File file = File.createTempFile(
                imageFileName,  /* prefix */
                suffix,         /* suffix */
                storageDir      /* directory */
        );


        Log.d("happy", "createFile " + file.getAbsolutePath());

        // Save a file: path for use with ACTION_VIEW intents
        return file;
    }
}
