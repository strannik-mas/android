package com.example.camerademo;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.ParcelFileDescriptor;
import android.os.PersistableBundle;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;


public class MainActivity extends AppCompatActivity {

    private ImageView image;

    private Uri capturedUri;

    // Идентификаторы для запроса прав - чтобы знать,
    // чей запрос на получение прав обрабатывать в
    // onRequestPermissionsResult
    public static final int TAKE_PHOTO = 1;
    public static final int TAKE_VIDEO = 2;
    public static final int PICK_PHOTO = 3;
    public static final String KEY_STATE_CAPTURE="KEY_STATE_CAPTURE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        image = (ImageView) findViewById(R.id.image);
        Log.d("happy", "Pics dir: " + Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES));
    }

    // Фото через враппер
    public void takePhoto(View view) {
        takePhotoWrapper();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if(capturedUri != null)
            outState.putString(KEY_STATE_CAPTURE, capturedUri.getPath());
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        String path = savedInstanceState.getString(KEY_STATE_CAPTURE);
        if(!TextUtils.isEmpty(path))
            capturedUri = Uri.fromFile(new File(path));
    }

    private void takePhotoWrapper() {
        // Если андроид больше 5 и нет прав
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M &&
                checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                ) {
            // запросим нужные права
            requestPermissions(
                    new String[]{
                            Manifest.permission.WRITE_EXTERNAL_STORAGE
                    },
                    TAKE_PHOTO);
        } else {
            // Если права есть или андроид до версии 6
            takePhoto();
        }
    }

    // Фото через интент
    private void takePhoto() {
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());

        File dir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        if (!dir.isDirectory())
            dir.mkdirs();

        // Создадим файл
        // String filename = "test.jpg";
        // file = new File(dir, filename);
        File file = null;
        try {
            file = Utility.createFile(this, ".jpg");

            Log.d("happy", "takePhoto " + file.getAbsolutePath());

            // Получение фотографии фотоаппаратом через интент
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            // Имя для файла у нас уже есть
            capturedUri = Uri.fromFile(file);

            intent.putExtra(MediaStore.EXTRA_OUTPUT, capturedUri);
            startActivityForResult(intent,TAKE_PHOTO);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    // Выбрать картинку из галереи через враппер
    public void pickImage(View view) {
        pickImage();
    }

    private void pickImage() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M &&
                checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                ) {
            requestPermissions(
                    new String[]{
                            Manifest.permission.READ_EXTERNAL_STORAGE,
                    },
                    PICK_PHOTO);
        } else {
            Intent intent = new Intent(
                    Intent.ACTION_PICK,
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI
            );
            startActivityForResult(intent, PICK_PHOTO);
        }
    }




    // Видео через враппер
    public void takeVideo(View view) {
        takeVideoWrapper();
    }

    private void takeVideoWrapper() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M &&
                checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                ) {
            requestPermissions(
                    new String[]{
                            Manifest.permission.WRITE_EXTERNAL_STORAGE
                    },
                    TAKE_VIDEO);
        } else {
            takeVideo();
        }
    }


    private void takeVideo() {
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());

        File dir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        //File dir = new File("/opt/pictures/...");
        if (!dir.isDirectory())
            dir.mkdirs();

        // Файл куда записывать видео
        // String filename = "test.mp4";
        // file = new File(dir, filename);

        File file = null;
        try {
            file = Utility.createFile(this, ".mp4");
            // Видео через интент
            Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
            // Файл, куда нужно записать видео, уже есть
            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));

            capturedUri = Uri.fromFile(file);

            // Длиной не более 30 секунд
            intent.putExtra(MediaStore.EXTRA_DURATION_LIMIT, 5);
            startActivityForResult(intent, TAKE_VIDEO);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }









    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == TAKE_PHOTO) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                takePhoto();
            } // else I'm sorry...
        } else if (requestCode == TAKE_VIDEO) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                takeVideo();
            } // else I'm sorry...
        } else if (requestCode == PICK_PHOTO) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                pickImage();
            } // else I'm sorry...
        }
    }








    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case TAKE_PHOTO:
                    if(capturedUri != null) {

                        // scan(new File(capturedUri.getPath()));

                        Log.d("happy", "onActivityResult " + capturedUri);

                        Picasso
                                .with(this)
                                .load(capturedUri)
                                .fit()
                                .centerCrop()
                                .into(image);

                        scan(new File(capturedUri.getPath()));

                    }
                    break;
                case TAKE_VIDEO:

                    if(capturedUri != null) {

                        scan(new File(capturedUri.getPath()));

                        Intent intent = new Intent(Intent.ACTION_VIEW);
                        //intent.setData(capturedUri);
                        intent.setDataAndType(capturedUri, "video/mp4");
                        startActivity(intent);
                    }
                    break;
                case PICK_PHOTO:
                    if (data != null && data.getData() != null) {
                        Uri pictureUri = data.getData();
                        Log.d("happy", "uri " + pictureUri.toString());

                        String type = getContentResolver().getType(pictureUri);
                        Log.d("happy", "type " + type);

                        // Выбранную через галарею картинку загрузим
                        Picasso
                                .with(this)
                                .load(pictureUri)
                                .fit()
                                .centerCrop()
                                .into(image);

                    }
                default:
                    super.onActivityResult(requestCode, resultCode, data);
            }
        }
    }


    // Полезный метод для сканирования только что загруженного или созданного
    // медиа файла - картинки, музыки, видео и т.п.
    private void scan(File file) {
        if (file != null) {
            MediaScannerConnection.scanFile(this,
                    new String[]{file.getAbsolutePath()}, null,
                    new MediaScannerConnection.OnScanCompletedListener() {
                        @Override
                        public void onScanCompleted(String path, Uri uri) {
                            Log.d("happy", "Scanned path: " + path);
                            Log.d("happy", "Scanned uri: " + uri);
                        }
                    });
        }
    }



    // Вызов метода для сканирования файла
    public void scan(View view) {
        if(capturedUri != null)
            scan(new File(capturedUri.getPath()));

    }
}
