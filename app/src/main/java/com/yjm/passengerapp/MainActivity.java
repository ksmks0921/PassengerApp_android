package com.yjm.passengerapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.yjm.passengerapp.util.Constants;

import org.json.JSONException;
import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity  extends AppCompatActivity {

    private EditText Name;
    private  EditText Password;
    private TextView Info;
    private Button Login;
    private int counter = 5;
    private boolean flag = true;

    ApiInterface apiInterface;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        apiInterface = retrofit.create(ApiInterface.class);

        Name = (EditText)findViewById(R.id.etName);
        Password = (EditText)findViewById(R.id.etPassword);
        Login = (Button)findViewById(R.id.btnLogin);
        Info = (TextView)findViewById(R.id.txtCounter);
        Info.setText("No of attempts remaining: 5");


        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (flag){

                    validate(Name.getText().toString(), Password.getText().toString());



                }
                else {

                    verify(Name.getText().toString(), Password.getText().toString());

                }




//                        String userName = Name.getText().toString();
//                        String userPassword = Password.getText().toString();
//
//                        if((userName.equals("Admin")) && (userPassword.equals("1234"))){
//                            Intent intent = new Intent(MainActivity.this, languageActivity.class);
//                            startActivity(intent);
//                        }
//                        else {
//                            counter--;
//                            Info.setText("No of attempts remaining:" + String.valueOf(counter));
//
//                            if(counter == 0){
//                                Login.setEnabled(false);
//                            }
//                        }


            }
        });







    }

    private void verify(String userName,String code){

//                    verify phone number
        Call<JsonObject> response_of_verify = apiInterface.verify(userName, code, "password");

        response_of_verify.enqueue(new Callback<JsonObject>() {

            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if(response.isSuccessful()){

//                    String token = response.getString('access_token');
                    try {
                        JSONObject subObj = new JSONObject(String.valueOf(response));
                        JSONObject json_data = subObj.getJSONObject("body");
                        String token = json_data.getString("access_token");
                        Constants.ACCESS_TOKEN = token;

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    Intent intent = new Intent(MainActivity.this, languageActivity.class);

                    startActivity(intent);

                }
                else {

                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {

                counter--;
                Info.setText("No of attempts remaining:" + String.valueOf(counter));

                if(counter == 0){
                    Login.setEnabled(false);
                }

            }
        });


    }


    private void validate(String userName, String userPassword){



        Call<JsonObject> response_of_login = apiInterface.login(userName, userPassword, "password");
        response_of_login.enqueue(new Callback<JsonObject>() {

            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if(response.isSuccessful()){

//                    Password.setVisibility(View.GONE);
                    Password.setInputType(InputType.TYPE_CLASS_TEXT);
//                    Password.setText("Enter verify code");
                    flag = false;


                }
                else {

                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {

                            counter--;
                            Info.setText("No of attempts remaining:" + String.valueOf(counter));

                            if(counter == 0){
                                Login.setEnabled(false);
                            }

            }
        });

//
//
//
//        if((userName.equals("Admin")) && (userPassword.equals("1234"))){
//            Intent intent = new Intent(MainActivity.this, languageActivity.class);
//            startActivity(intent);
//        }
//        else {
//            counter--;
//            Info.setText("No of attempts remaining:" + String.valueOf(counter));
//
//            if(counter == 0){
//                Login.setEnabled(false);
//            }
//        }
    }
}
