package com.example.Weather_app;
import com.example.Weather_app.Models.OpenWeatherMap;
import com.example.Weather_app.Models.Weather5D3H;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;


public interface JsonPlaceHolderApi{

    @GET
    Call<OpenWeatherMap> getWeatherByCity(@Url String url);
    @GET
    Call<Weather5D3H> get5D3HWeather(@Url String url);
    @GET
    Call<OpenWeatherMap> getWeatherByCoordinates(@Url String coordinates);

}


