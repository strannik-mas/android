package com.strannik.currencyexchange_retrofit2;

import retrofit2.Retrofit;
import retrofit2.converter.simplexml.SimpleXmlConverterFactory;

public class NetworkService {
    private static NetworkService networkServiceInstance;
    private static final String BASE_URL = "https://www.cbr-xml-daily.ru";
    private Retrofit retrofit;

    private NetworkService() {
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(SimpleXmlConverterFactory.create())
                .build();
    }

    public static NetworkService getInstance() {
        if(networkServiceInstance == null) {
            networkServiceInstance = new NetworkService();
        }
        return networkServiceInstance;
    }

    public XMLCurrencyRatesService getXMLApi() {
        return retrofit.create(XMLCurrencyRatesService.class);
    }
}
