package com.example.Weather_app;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ServerHandler {

    List<String> list;
    DatabaseReference databaseReference;
    OnCitiesFetchedListener onCitiesFetchedListener;
    public interface OnCitiesFetchedListener{
        void OnCitiesFetchedSuccessfully(List<String> cityList);
    }

    public ServerHandler() {
        list = new ArrayList<>() ;
        databaseReference = FirebaseDatabase.getInstance().getReference();
    }
    public void SetOnCitiesFetchedListener(OnCitiesFetchedListener onCitiesFetchedListener){
        this.onCitiesFetchedListener = onCitiesFetchedListener;
    }

    public void fetchCityList()
    {
        databaseReference.child("Cities").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Log.e("ServerHandler","start");

                list.clear();
                for (DataSnapshot snap: dataSnapshot.getChildren())
                {
                    String cityItem = snap.child("name").getValue(String.class);
                    list.add(cityItem);
                }
                Log.e("ServerHandler","done");
                databaseReference.setValue(list);
                if (onCitiesFetchedListener!=null)
                {
                    onCitiesFetchedListener.OnCitiesFetchedSuccessfully(list);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

}
