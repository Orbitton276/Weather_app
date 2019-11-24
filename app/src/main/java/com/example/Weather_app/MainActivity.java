package com.example.Weather_app;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.Weather_app.Adapters.Forecast_Adapter;
import com.example.Weather_app.Models.DateAndTime;
import com.example.Weather_app.Models.Dt_weather_item;
import com.example.Weather_app.Models.OpenWeatherMap;
import com.example.Weather_app.Models.Weather5D3H;
import com.github.ybq.android.spinkit.style.Circle;


import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity implements LocationListener {
    private static final int REQUEST_LOCATION = 1;
    public static final String TAG = "OnMainActivity";
    LocationManager locationManager;
    double currentLat, currentLog;
    Retrofit retrofit;
    TextView temp, description, humidity, windSpeed, location, country;
    ImageView currentWeatherImageBackground;
    ImageView currentWeatherImage;
    ImageButton search, refresh,locationBtn;
    OpenWeatherMap openWeatherMap;
    EditText search_edit;
    ServerHandler serverHandler;
    List<String> cities;
    RecyclerView forecastRecyclerView;
    String place;
    ArrayAdapter<String> adapter;
    Forecast_Adapter forecast_adapter;
    AutoCompleteTextView autoCompleteTextView;
    private ArrayList<Dt_weather_item> dataArray;
    String provider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loading_layout);
        buildRetrofit();
        cities = new ArrayList<>();
        ProgressBar progressBar = (ProgressBar) findViewById(R.id.spin_kit);
        Circle doubleBounce = new Circle();
        progressBar.setIndeterminateDrawable(doubleBounce);
        serverHandler = new ServerHandler();
        serverHandler.SetOnCitiesFetchedListener(new ServerHandler.OnCitiesFetchedListener() {
            @Override
            public void OnCitiesFetchedSuccessfully(List<String> cityList) {
                cities = cityList;
                loadMainLayout();

            }
        });
        serverHandler.fetchCityList();


    }

    private void loadMainLayout() {
        setContentView(R.layout.main_layout);
        Log.e(TAG, "onLoadMain");
        forecastRecyclerView = findViewById(R.id.forecast_recycler);
        temp = findViewById(R.id.tvTemp);
        description = findViewById(R.id.tvDescription);
        humidity = findViewById(R.id.tvHumidity);
        windSpeed = findViewById(R.id.tvWindSpeed);
        location = findViewById(R.id.tvLocation);
        refresh = findViewById(R.id.refreshBtn);
        locationBtn = findViewById(R.id.locationBtn);
        country = findViewById(R.id.tvCountry);
        dataArray = new ArrayList<>();
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        //====================
        if (locationManager == null)
            Log.e(TAG, "LM is null");
        else
            Log.e(TAG, "LM is not null");
        //====================
        currentWeatherImageBackground = findViewById(R.id.weatherImage);
        currentWeatherImage = findViewById(R.id.currentWeatherImage);
        search = findViewById(R.id.searchButton);
        search_edit = findViewById(R.id.search_edit);
        autoCompleteTextView = findViewById(R.id.search_edit);
        setCitiesAdapter();
        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String locationToRefresh = search_edit.getText().toString();
                if (locationToRefresh.equals(""))
                    getLocation();
                else {
                    getDataByCityName(locationToRefresh);
                    get5D3HData(locationToRefresh);
                }
            }
        });
        locationBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getLocation();
            }
        });
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideKeyboard(MainActivity.this);
                place = search_edit.getText().toString();
                get5D3HData(place);
                getDataByCityName(place);
            }
        });
        checkLocationPermission();



    }

    private void initForecastRecycler() {

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        forecastRecyclerView.setLayoutManager(linearLayoutManager);
        forecast_adapter = new Forecast_Adapter(dataArray, this);
        forecastRecyclerView.setAdapter(forecast_adapter);
    }


    private void setCitiesAdapter() {
        Log.e(TAG, "onSetCitiesAdapter");
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, cities);
        autoCompleteTextView.setAdapter(adapter);
    }

    private void getLocation() {

        if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            checkLocationPermission();
            return;
        } else {
            if (isLocationEnabled()){
                try {
                    provider = locationManager.getBestProvider(new Criteria(), false);
                    Location location = locationManager.getLastKnownLocation(provider);
                    currentLat = location.getLatitude();
                    currentLog = location.getLongitude();
                    getDataByLocation(currentLat, currentLog);
                }catch (Exception e){

                }

            }
            else{
                Toast.makeText(this, "Turn on Location", Toast.LENGTH_SHORT).show();
            }

        }

    }

    private boolean isLocationEnabled() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
// This is new method provided in API 28
            LocationManager lm = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
            return lm.isLocationEnabled();
        } else {
// This is Deprecated in API 28
            int mode = Settings.Secure.getInt(this.getContentResolver(), Settings.Secure.LOCATION_MODE,
                    Settings.Secure.LOCATION_MODE_OFF);
            return (mode != Settings.Secure.LOCATION_MODE_OFF);

        }


    }

    private void get5D3HData(String location) {
        Log.e("5D", "in");
        JsonPlaceHolderApi jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);
        Call<Weather5D3H> call = jsonPlaceHolderApi.get5D3HWeather("forecast?appid=445a092c11e9a29a848b2a60aaa55973&q=" + location + "&mode=json&units=metric");
        call.enqueue(new Callback<Weather5D3H>() {
            @Override
            public void onResponse(Call<Weather5D3H> call, Response<Weather5D3H> response) {

                Weather5D3H weather5D3H = response.body();

                if (weather5D3H != null) {
                    initData5D3H(weather5D3H);
                }
            }

            @Override
            public void onFailure(Call<Weather5D3H> call, Throwable t) {

            }
        });
    }

    private void initData5D3H(Weather5D3H weather5D3H) {
        DateAndTime currentDate = new DateAndTime(weather5D3H.getList().get(0).getDt_txt());
        String today = currentDate.getDay();
        dataArray.clear();
        for (OpenWeatherMap item : weather5D3H.getList()) {

            DateAndTime dateAndTime = new DateAndTime(item.getDt_txt());
            if (dateAndTime.getDay().equals(today)) {
                dateAndTime.setDate("Today");
            }

            Dt_weather_item weather_item = new Dt_weather_item(dateAndTime.getDate() + "\n" + dateAndTime.getTime(), weather5D3H.getCity().getName(),
                    weather5D3H.getCity().getCountry(), item.getWeather().get(0).getDescription(), item.getWeather().get(0).getIcon(),
                    item.getMain().getTemp(), item.getMain().getHumidity(), item.getWind().getSpeed() * 3.6);
            dataArray.add(weather_item);
        }
        initForecastRecycler();
    }

    private void getDataByCityName(String LocationName) {
        JsonPlaceHolderApi jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);
        Call<OpenWeatherMap> call = jsonPlaceHolderApi.getWeatherByCity(("weather?q=" + LocationName + "&appid=445a092c11e9a29a848b2a60aaa55973&units=metric"));
        call.enqueue(new Callback<OpenWeatherMap>() {
            @Override
            public void onResponse(Call<OpenWeatherMap> call, Response<OpenWeatherMap> response) {
                Log.e(TAG, "OnResponseAPIcallback");
                openWeatherMap = response.body();
                if (openWeatherMap != null) {
                    Log.e(TAG, "OnResponseAPIcallback");

                    initData(openWeatherMap);
                } else
                    Toast.makeText(MainActivity.this, "Location invalid", Toast.LENGTH_SHORT).show();


            }

            @Override
            public void onFailure(Call<OpenWeatherMap> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
            }
        });


    }

    private void buildRetrofit() {
        retrofit = new Retrofit.Builder()
                .baseUrl("https://api.openweathermap.org/data/2.5/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public static void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = activity.getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    private void initData(OpenWeatherMap openWeatherMap) {

        DecimalFormat df2 = new DecimalFormat("#.##");
        temp.setText(String.valueOf(openWeatherMap.getMain().getTemp()) + " °C");
        humidity.setText(String.valueOf(openWeatherMap.getMain().getHumidity()) + " %");
        windSpeed.setText(String.valueOf(df2.format(openWeatherMap.getWind().getSpeed() * 3.6)) + " km/h");
        description.setText(String.valueOf(openWeatherMap.getWeather().get(0).getDescription()));
        location.setText(String.valueOf(openWeatherMap.getName()));
        country.setText(String.valueOf(openWeatherMap.getSys().getCountry()));
        initWeatherImage(openWeatherMap.getWeather().get(0).getId());
    }

    private void initWeatherImage(int value) {

        int id = getResources().getIdentifier("a" + openWeatherMap.getWeather().get(0).getIcon(), "drawable", getPackageName());
        currentWeatherImage.setImageResource(id);
        String dayOrNight;

        if (openWeatherMap.getWeather().get(0).getIcon().contains("n"))
            dayOrNight = "n";
        else
            dayOrNight = "d";


        if (value <= 200 && value < 300)
            currentWeatherImageBackground.setImageResource(R.drawable.thunder_day);
        else if (value <= 300 && value < 600)
            if (dayOrNight.equals("n"))
                currentWeatherImageBackground.setImageResource(R.drawable.rain_night);
            else
                currentWeatherImageBackground.setImageResource(R.drawable.rain_day);
        else if (value <= 600 && value < 700)
            if (dayOrNight.equals("n"))
                currentWeatherImageBackground.setImageResource(R.drawable.snow_night);
            else
                currentWeatherImageBackground.setImageResource(R.drawable.snow_day);
        else if (value <= 700 && value < 800)
            if (dayOrNight.equals("n"))
                currentWeatherImageBackground.setImageResource(R.drawable.mist_night);
            else
                currentWeatherImageBackground.setImageResource(R.drawable.mist_day);
        else if (value == 800)
            if (dayOrNight.equals("n"))
                currentWeatherImageBackground.setImageResource(R.drawable.clear_sky_night);
            else
                currentWeatherImageBackground.setImageResource(R.drawable.clear_sky_day);
        else if (dayOrNight.equals("n"))
            currentWeatherImageBackground.setImageResource(R.drawable.clouds_night);
        else
            currentWeatherImageBackground.setImageResource(R.drawable.clouds_day);
    }

    private void getDataByLocation(double lat, double lng) {

        JsonPlaceHolderApi jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);
        Call<OpenWeatherMap> call = jsonPlaceHolderApi.getWeatherByCoordinates(("weather?lat=" + lat + "&lon=" + lng + "&appid=445a092c11e9a29a848b2a60aaa55973&units=metric"));
        call.enqueue(new Callback<OpenWeatherMap>() {
            @Override
            public void onResponse(Call<OpenWeatherMap> call, Response<OpenWeatherMap> response) {
                openWeatherMap = response.body();
                if (openWeatherMap != null) {
                    get5D3HData(openWeatherMap.getName());
                    initData(openWeatherMap);
                } else
                    Toast.makeText(MainActivity.this, "Location invalid", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<OpenWeatherMap> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
            }
        });

    }


    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;

    public boolean checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {
                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
                new AlertDialog.Builder(this)
                        .setTitle("Location services needed")
                        .setMessage("This weather application displays your current location forecast. Do you mind enable us to get that information?")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //Prompt the user once explanation has been shown
                                ActivityCompat.requestPermissions(MainActivity.this,
                                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                                        MY_PERMISSIONS_REQUEST_LOCATION);
                            }
                        })
                        .create()
                        .show();
            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);
            }
            return false;
        } else {
            Log.e(TAG, "already have permission");
            getLocation();
            return true;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        Log.e(TAG, "OnRequestPermissionResult");
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {


                    getLocation();
                    // permission was granted, yay! Do the
                    // location-related task you need to do.

                    Log.e("Location permission", "Granted");

                    if (ContextCompat.checkSelfPermission(this,
                            Manifest.permission.ACCESS_FINE_LOCATION)
                            == PackageManager.PERMISSION_GRANTED) {

                        //Request location updates:
                        locationManager.requestLocationUpdates(provider, 400, 1, this);
                    }

                } else {
                    Log.e("Location permission", "Denied");
                    getDataByLocation(51.5073, -0.1277);

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.

                }
                return;
            }

        }
    }


    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            if (locationManager != null) {
                locationManager.requestLocationUpdates(provider, 400, 1, this);
            }
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            if (this == null) {
                Log.e(TAG, "this is null");
            }
            if (locationManager != null) {
                locationManager.removeUpdates(this);

            }
        }
    }
}

