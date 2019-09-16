package com.yjm.passengerapp;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.yjm.passengerapp.Helper.ScheduleAdapter;
import com.yjm.passengerapp.models.PassengerShift;
import com.yjm.passengerapp.models.Schedule;
import com.yjm.passengerapp.util.Constants;

import java.lang.reflect.Array;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class scheduleActivity extends AppCompatActivity {

    String items[] = new String [] {"Monday", "Tuesday", "Wednessday", "Thursday", "Friday", "Saturday", "Sunday"};
    ListView listView_dialog = null;
    ApiInterface apiInterface;
    private static String shiftID;
    private static String time;
    private static String isFrom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule);







        ListView listView = (ListView) findViewById(R.id.list_view);
        listView.setAdapter(new ScheduleAdapter(this, Constants.ARRAY_SCHEDULE));
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                 Gson gson = new GsonBuilder()
                    .setLenient()
                    .create();

                 Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl("http://23.239.215.199:2345/")
                        .addConverterFactory(GsonConverterFactory.create(gson))
                        .build();
                apiInterface = retrofit.create(ApiInterface.class);
                Call<JsonObject> data_getShiftofDate = apiInterface.getShiftOfDate();
                data_getShiftofDate.enqueue(new Callback<JsonObject>() {
                    @Override
                    public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                        if (response.isSuccessful()){

                            JsonArray schedule_data_each_day = response.body().getAsJsonArray("ShiftOfDay");
                            ArrayList<String[]> schedule_of_day = new ArrayList();
                            ArrayList<String> times_list = new ArrayList<String>();



                            for (int i = 0, count = schedule_data_each_day.size(); i < count; i++) {


                                try {
                                    JsonObject jsonObject = (JsonObject) schedule_data_each_day.get(i);

                                    shiftID = jsonObject.get("ShiftId").toString();
                                    time = jsonObject.get("time").toString();
                                    isFrom = jsonObject.get("IsFrom").toString();

                                    String item_schedule_of_day[] = new String[]{shiftID, time, isFrom};

                                    schedule_of_day.add(item_schedule_of_day);
                                    times_list.add(time);

                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                            int size = schedule_of_day.size();
                            String item_time[] = new String[size];
                            item_time = times_list.toArray(item_time);
                            Constants.ARRAY_SCHEDULE_DAY = schedule_of_day;
                            Constants.ITEM_TIME = item_time;
                            showDialogListView();


                        }
                    }

                    @Override
                    public void onFailure(Call<JsonObject> call, Throwable t) {
                        Log.d("faild", "reason");
                    }
                });

            }

        });
    }


    public void showDialogListView(){


        AlertDialog.Builder builder = new AlertDialog.Builder(scheduleActivity.this);
        builder.setCancelable(true);

        listView_dialog = new ListView(this);

        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, Constants.ITEM_TIME );
        listView_dialog.setAdapter(adapter1);
        listView_dialog.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {




            }
        });

        builder.setView(listView_dialog);
        AlertDialog dialog = builder.create();
        dialog.show();

    }
}
