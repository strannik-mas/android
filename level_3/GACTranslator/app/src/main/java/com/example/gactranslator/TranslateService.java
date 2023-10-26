package com.example.gactranslator;

import android.app.IntentService;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;

import androidx.annotation.Nullable;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class TranslateService extends IntentService {

    public static final String LANG = "PARAMETER_LANG";
    public static final String SENTENCE = "PARAMETER_SENTENCE";

    public static final String ENDPOINT = "https://api.mymemory.translated.net";
    private static final String TAG = "TranslateService";

    private static MyMemoryTranslate translate = new Retrofit.Builder()
            .baseUrl(ENDPOINT)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(MyMemoryTranslate.class);

    /**
     * @deprecated
     */
    public TranslateService() {
        super("TranslateService");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        TranslateResultDao dao = Repository.getDb().translateDao();

        TranslateResult result = new TranslateResult();
        String sentence = null;
        String lang = null;
        if (intent != null) {
            sentence = intent.getStringExtra(SENTENCE);
            lang = intent.getStringExtra(LANG);
        }

        if(!TextUtils.isEmpty(sentence) && !TextUtils.isEmpty(lang)){

            result.setLang(lang);
            result.setSentence(sentence);

            Call<Translate> call = translate.translate(lang, sentence);
            try {
                Response<Translate> response = call.execute();
                Translate body = response.body();
                ResponseData responseData = null;
                if (body != null) {
                    responseData = body.getResponseData();
                    String translateResult = responseData.getTranslatedText();
                    result.setResult(translateResult);
                    Log.d(TAG, "onHandleIntent: result: " + translateResult);
                }
            } catch (Exception e) {
                result.setException(e.getMessage());
                result.setStatus("ERROR");
                Log.d(TAG, "onHandleIntent: exception: " + e.getMessage());
            }

            dao.deleteAll();
            dao.insertAll(result);
        }
    }
}
