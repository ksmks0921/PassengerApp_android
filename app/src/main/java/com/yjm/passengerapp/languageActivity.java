package com.yjm.passengerapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class languageActivity extends AppCompatActivity {


    private Button English;
    private  Button Russian;
    public String language;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_language);

        English = (Button)findViewById(R.id.btnEnglish);
        Russian = (Button)findViewById(R.id.btnRussian);

        English.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                language = "English";
                goToNext();


            }
        });


        Russian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                language = "Russian";
                goToNext();
            }
        });



    }

    private void goToNext(){
        Intent intent = new Intent(languageActivity.this, MapsActivity.class);
        startActivity(intent);
    }

}