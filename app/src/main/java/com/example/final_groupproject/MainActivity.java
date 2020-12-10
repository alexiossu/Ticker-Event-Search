package com.example.final_groupproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button ticketMasterButton = findViewById(R.id.ticketMasterButton);
        /*Button recipeSearchButton = findViewById(R.id.recipeButton);
        Button covid19Buttom = findViewById(R.id.covid19Button);
        Button audioDbButton = findViewById(R.id.audioDbButton);*/


            Intent goToTicketMaster = new Intent(MainActivity.this, TicketMaster_event_search.class);
            ticketMasterButton.setOnClickListener(e -> {
                startActivity(goToTicketMaster);
            });


        /*Intent goToRecipeSearch = new Intent(MainActivity.this, Recipe_Search_page.class);
        ticketMasterButton.setOnClickListener( e -> {
            startActivity(goToRecipeSearch);
        });

        Intent goToCovid19Buttom = new Intent(MainActivity.this, Covid19_case_data.class);
        ticketMasterButton.setOnClickListener( e -> {
            startActivity(goToCovid19Buttom);
        });

        Intent goToAudioDbButton = new Intent(MainActivity.this, The_Audio_Database_api.class);
        ticketMasterButton.setOnClickListener( e -> {
            startActivity(goToAudioDbButton);
        });
    }*/
    }
}

