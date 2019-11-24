package com.example.Weather_app.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.Weather_app.Models.Dt_weather_item;
import com.example.Weather_app.R;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class Forecast_Adapter extends RecyclerView.Adapter<Forecast_Adapter.ViewHolder> {


    private ArrayList<Dt_weather_item> dataArray;
    private Context context;

    public Forecast_Adapter(ArrayList<Dt_weather_item> dataArray, Context context) {
        this.dataArray = dataArray;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.dt_weather_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        DecimalFormat df2 = new DecimalFormat("#.##");
        int id = context.getResources().getIdentifier("a" + dataArray.get(position).getIconUrl(), "drawable", context.getPackageName());
        holder.tvTimeStamp.setText(dataArray.get(position).getTime());
        holder.tvTemp.setText((dataArray.get(position).getTemp())+" °C");
        holder.tvHumidity.setText((dataArray.get(position).getHumidity())+ " %");
        holder.tvDesctiption.setText(dataArray.get(position).getDescription());
        holder.tvWind.setText((df2.format(dataArray.get(position).getWindSpeed()))+ " km/h");
        holder.tvCity.setText(dataArray.get(position).getLocation());
        holder.tvCountry.setText(dataArray.get(position).getState());
        holder.forecastIcon.setImageResource(id);
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //do something when clicked
            }
        });
    }
    @Override
    public int getItemCount() {
        return dataArray.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvTimeStamp, tvTemp, tvHumidity, tvDesctiption, tvWind, tvCity, tvCountry;
        ImageView forecastIcon;
        CardView cardView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTimeStamp = itemView.findViewById(R.id.tvTimeStamp);
            tvTemp = itemView.findViewById(R.id.tvTemp);
            tvHumidity = itemView.findViewById(R.id.tvHumidity);
            tvDesctiption = itemView.findViewById(R.id.tvDescription);
            tvWind = itemView.findViewById(R.id.tvWindSpeed);
            tvCity = itemView.findViewById(R.id.tvLocation);
            tvCountry = itemView.findViewById(R.id.tvLocationState);
            forecastIcon = itemView.findViewById(R.id.item_image);
            cardView = itemView.findViewById(R.id.forecastCardView);
        }
    }
}
