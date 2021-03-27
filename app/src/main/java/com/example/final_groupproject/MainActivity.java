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

            Intent goToTicketMaster = new Intent(MainActivity.this, TicketMaster_event_search.class);
            ticketMasterButton.setOnClickListener(e -> {
                startActivity(goToTicketMaster);
            });

    }
}

