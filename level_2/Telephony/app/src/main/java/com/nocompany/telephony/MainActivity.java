package com.nocompany.telephony;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.PhoneStateListener;
import android.telephony.SignalStrength;
import android.telephony.SmsManager;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    // Идентификаторы запросов на получение прав
    private static final int PERMISSIONS_TELEPHONY_CALL = 25;
    private static final int PERMISSIONS_TELEPHONY_STATS = 47;
    private static final int PERMISSIONS_TELEPHONY_INFO = 58;
    private static final int PERMISSIONS_SEND_SMS = 60;
    private static final int PERMISSIONS_CALL_PHONE = 62;

    private EditText editSms;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editSms = (EditText) findViewById(R.id.edit_sms);


        String s = "Debug-infos:";
        s += "\n OS Version: " + System.getProperty("os.version") + "(" + android.os.Build.VERSION.INCREMENTAL + ")";
        s += "\n OS API Level: " + android.os.Build.VERSION.RELEASE + "(" + android.os.Build.VERSION.SDK_INT + ")";
        s += "\n Device: " + android.os.Build.DEVICE;
        s += "\n Model (and Product): " + android.os.Build.MODEL + " (" + android.os.Build.PRODUCT + ")";

        Log.d("happy", s);
    }


    // Так можно позвонить через программу-звонилку по интенту
    public void call(View view) {
        doCall();
    }

    private void doCall() {
        // Так можно позвонить через программу-звонилку по интенту
        // если есть право CALL_PHONE
        Intent i = new Intent(
                Intent.ACTION_CALL,
                Uri.parse("tel:+123")
        );
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED && Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(
                    new String[]{
                            Manifest.permission.CALL_PHONE
                    },
                    PERMISSIONS_TELEPHONY_CALL);
        } else {
            startActivity(i);
        }
    }






    public void stats(View view) {
        doStats();
    }

    // Информация о телефонном модуле
    private void doStats() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M &&
                checkSelfPermission(Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED
                )
        {
            requestPermissions(
                    new String[]{
                            Manifest.permission.READ_PHONE_STATE
                    },
                    PERMISSIONS_TELEPHONY_STATS);
        } else {
            PackageManager pm = getPackageManager();
            boolean telephonySupported = pm.hasSystemFeature(PackageManager.FEATURE_TELEPHONY);
            boolean gsmTelephonySupported = pm.hasSystemFeature(PackageManager.FEATURE_TELEPHONY_GSM);
            boolean cdmaTelephonySupported = pm.hasSystemFeature(PackageManager.FEATURE_TELEPHONY_CDMA);

            Log.d("happy", "telephonySupportedd:" + telephonySupported);
            Log.d("happy", "gsmTelephonySupported:" + gsmTelephonySupported);
            Log.d("happy", "cdmaTelephonySupported:" + cdmaTelephonySupported);
        }
    }





    public void info(View view) {
        doInfo();
    }

    // Расширенная информация от телефонного модуля - сим карта, телефонный номер и т.п.
    private void doInfo()
    {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M &&
                checkSelfPermission(Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED
                )
        {
            requestPermissions(
                    new String[]{
                            Manifest.permission.READ_PHONE_STATE
                    },
                    PERMISSIONS_TELEPHONY_INFO);
        } else {
            TelephonyManager tm = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);

            // android.permission.READ_PHONE_STATE
            String deviceId = tm.getDeviceId();
            String phoneNumber = tm.getLine1Number();
            int phoneType = tm.getPhoneType();

            Log.d("happy", "deviceId: " + deviceId);
            Log.d("happy", "phoneNumber: " + phoneNumber);
            int simState = tm.getSimState();
            if (simState == TelephonyManager.SIM_STATE_READY) {
                String simCountry = tm.getSimCountryIso();
                String simOperatorCode = tm.getSimOperator();
                String simOperatorName = tm.getSimOperatorName();
                String simSerial = tm.getSimSerialNumber();

                Log.d("happy", "simCountry: " + simCountry);
                Log.d("happy", "simOperatorCode: " + simOperatorCode);
                Log.d("happy", "simOperatorName: " + simOperatorName);
                Log.d("happy", "simSerial: " + simSerial);
            }
        }
    }


    // Звонилка с номером телефона.
    // Только набирает номер.
    // Права не нужны.
    public void prepare(View view) {
        Intent i = new Intent(
                Intent.ACTION_DIAL,
                Uri.parse("tel:+123")
        );
        startActivity(i);
    }




    // Шлем смс через мессенджер по интенту
    // Права не нужны
    public void sendSms(View view) {
        String sms = editSms.getText().toString();
        if (!sms.equals("")) {

            Intent intent = new Intent(
                    Intent.ACTION_SENDTO,
                    Uri.parse("sms:123")
            );
            intent.putExtra("sms_body", sms);

            startActivity(intent);
        }
    }






    public void sendSmsViaManager(View view) {
        doSendSMSViaManager();
        
    }

    // Посылаем смс сами
    // Требуется разрешение SEND_SMS
    private void doSendSMSViaManager() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M &&
                checkSelfPermission(Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED
                )
        {
            requestPermissions(
                    new String[]{
                            Manifest.permission.SEND_SMS
                    },
                    PERMISSIONS_SEND_SMS);
        } else {
            String sms = editSms.getText().toString();
            if (!sms.equals("")) {
                SmsManager smsManager = SmsManager.getDefault();
                smsManager.sendTextMessage(
                        "+123",
                        null,
                        sms,
                        null,
                        null
                );
            }
        }
    }







    // Обработка запросов на получение прав от пользователя
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode)
        {
            case PERMISSIONS_TELEPHONY_STATS:
                if (    grantResults[0] == PackageManager.PERMISSION_GRANTED &&
                        permissions[0].equals(Manifest.permission.READ_PHONE_STATE))
                {
                    doStats();
                } else {
                    Toast.makeText(this, "Cannot access telephony stats without this permission", Toast.LENGTH_SHORT).show();
                }
                break;
            case PERMISSIONS_TELEPHONY_INFO:
                if (    grantResults[0] == PackageManager.PERMISSION_GRANTED &&
                        permissions[0].equals(Manifest.permission.READ_PHONE_STATE))
                {
                    doInfo();
                } else {
                    Toast.makeText(this, "Cannot access telephony info without this permission", Toast.LENGTH_SHORT).show();
                }
                break;
            case PERMISSIONS_SEND_SMS:
                if (    grantResults[0] == PackageManager.PERMISSION_GRANTED &&
                        permissions[0].equals(Manifest.permission.SEND_SMS))
                {
                    doSendSMSViaManager();
                } else {
                    Toast.makeText(this, "Cannot send sms without this permission", Toast.LENGTH_SHORT).show();
                }
                break;
            case PERMISSIONS_TELEPHONY_CALL:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED &&
                        permissions[0].equals(Manifest.permission.CALL_PHONE)) {
                    doCall();
                } else {
                    Toast.makeText(this, "Cannot call without this permission", Toast.LENGTH_SHORT).show();
                }
        }
    }


}
