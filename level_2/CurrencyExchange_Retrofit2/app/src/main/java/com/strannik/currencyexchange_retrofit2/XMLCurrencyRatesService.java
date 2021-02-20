package com.strannik.currencyexchange_retrofit2;

import retrofit2.Call;
import retrofit2.http.GET;

public interface XMLCurrencyRatesService {
    @GET("/daily_utf8.xml")
    Call<Result> getRatesForCurrentDay();
}
