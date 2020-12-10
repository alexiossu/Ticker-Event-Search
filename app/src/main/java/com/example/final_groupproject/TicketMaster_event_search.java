package com.example.final_groupproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.AlertDialog;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.material.snackbar.Snackbar;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * @author Aleksei Surkov
 * @version 1.0
 */

public class TicketMaster_event_search extends AppCompatActivity {


    EditText city, radius;
    String cit;
    String rad;
    SharedPreferences.Editor edit;
    SharedPreferences pref;
    ImageView event_image;
    private ListView listView;
    private ImageButton searchButton;
    private Toolbar toolbar;
    private ProgressBar progressBar;
    private ArrayList<CurrentEvent> eventsList = new ArrayList<>();
    MyListAdapter myAdapter = new MyListAdapter();
    private  String apiKey = "FHc2BjBpvDVQFLEMXqq8gkloZ2rauJVJ";

    /**
     * Inflate the menu items for use in the action bar
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.ticket_menu, menu);

        return true;
    }
    /**
     * Option of what to do if one of the options is picked
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch(item.getItemId())
        {
            case R.id.help_item:
                helpDialog();
                break;
            case R.id.favorite_item:
                // showFavorites();
                break;
            default:
                Toast.makeText(this, "You clicked on the overflow menu", Toast.LENGTH_LONG).show();
                break;
        }
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticket_master_event_search);

        listView = findViewById(R.id.eventList);
        searchButton = findViewById(R.id.search);
        progressBar = findViewById(R.id.progressbar);
        city = findViewById(R.id.inputCity);
        radius = findViewById(R.id.inputRadius);
        toolbar = findViewById(R.id.ticket_toolbar);
        pref = getSharedPreferences("city", MODE_PRIVATE);
        pref = getSharedPreferences("radius", MODE_PRIVATE);
        event_image = findViewById(R.id.event_image);

        city.setText((pref.getString("city", "")));
        radius.setText((pref.getString("radius", "")));

        Toast.makeText(getApplicationContext(), "Search for events in the city and radius of the search", Toast.LENGTH_LONG).show();


        listView.setOnItemClickListener((parent, view, pos, id) -> {

        //    View aNewRow = getLayoutInflater().inflate(R.layout.even_image, null, false);
        //    ImageView evImg = aNewRow.findViewById(R.id.event_image);

            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
            alertDialogBuilder.setTitle(eventsList.get(pos).getName())


                    .setPositiveButton("DELETE", (click, arg) -> {
                        eventsList.remove(pos);
                        myAdapter.notifyDataSetChanged();
                    })

                    .setNegativeButton("Quit", (click, arg) -> {

                    })

                    .setMessage(
                                    "           " +
                            "URL:" + eventsList.get(pos).getURL() + "\n \n" +
                             "Event time " + eventsList.get(pos).getEventDate() + "\n \n" +
                                            "Event date " + eventsList.get(pos).getEventTime() + "\n \n" +
                            "Price range: " + eventsList.get(pos).getPriceMin() + "   ~   " + eventsList.get(pos).getPriceMax() + " CAD")
                    .create().show();

            myAdapter.notifyDataSetChanged();

        });

        searchButton.setOnClickListener( click -> {
            if ( city.getText().equals("")||city.getText() == null || radius.getText().toString().equals("")||radius.getText().toString() == null) {
                Toast.makeText(getApplicationContext(),
                        "PLEASE ENTER CITY and RADIUS",
                        Toast.LENGTH_LONG).show();

            } else {
                cit = city.getText().toString();
                rad = radius.getText().toString();
                progressBar.setVisibility(View.VISIBLE);
                String search_url = "https://app.ticketmaster.com/discovery/v2/events.json?apikey="+apiKey+"&city="+cit+"&radius="+rad;
                new TicketQuery().execute(search_url);
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();

        edit = pref.edit();
        edit.putString("city", city.getText().toString());
        edit.putString("radius", radius.getText().toString());
        edit.commit();
    }

    /**
     *Async class to is parsing JSON data and adding it to the CurrentEvent list
     */

    public class TicketQuery extends AsyncTask<String, Integer, String> {

        @Override
        protected String doInBackground(String... args) {

            String result; //The results returnd from JSON query
            int searchUpdate;// used to populate the progress bar
            String info;
            String imageUrl = "no imageUrl";
            int minPrice = 0;
            int maxPrice = 0;
            String eventDate = "";
            String eventTime = "";
            String eventName;
            String eventUrl;

            try {
                URL url = new URL(args[0]);
                HttpURLConnection jsonUrlConnection = (HttpURLConnection) url.openConnection();
                InputStream response = jsonUrlConnection.getInputStream();

                BufferedReader reader = new BufferedReader(new InputStreamReader(response, "UTF-8"), 8);
                StringBuilder sb = new StringBuilder();

                eventsList.clear();        // Clear the previous search

                String line = null;
                while ((line = reader.readLine()) != null) {
                    sb.append(line + "\n");

                    result = sb.toString();
                    JSONObject parsedObj = new JSONObject(result);


                    JSONObject embeddedObj = parsedObj.getJSONObject("_embedded");
                    JSONArray eventsArray = embeddedObj.getJSONArray("events");

                    for (int i = 0; i < eventsArray.length(); i++) {

                        JSONObject anObject = eventsArray.getJSONObject(i);

                        eventName = anObject.getString("name");
                        eventUrl = anObject.getString("url");

                     if (anObject.has("images")) {
                         JSONArray imageArray = anObject.getJSONArray("images");
                         imageUrl = imageArray.getJSONObject(0).getString("url");
                     }

                     if (anObject.has("dates")) {
                         JSONObject dates = anObject.getJSONObject("dates");
                         JSONObject start = dates.getJSONObject("start");
                         eventDate = start.getString("localDate");
                         eventTime = start.getString("localTime");
                     }
                        if (anObject.has("priceRanges")) {

                            JSONArray priceRangesArray = anObject.getJSONArray("priceRanges");
                            minPrice = priceRangesArray.getJSONObject(0).getInt("min");
                            maxPrice = priceRangesArray.getJSONObject(0).getInt("max");
                        }
                        eventsList.add(new CurrentEvent(eventName, eventUrl, eventDate, eventTime, minPrice, maxPrice, 0, imageUrl, ""));// adds each event to the list


                    }
                }
            }
            catch(Exception e){
            }
                return null;
        }


        /**
         * In this method we sat snackbar and visability of the prograssBar
         */
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            progressBar.setVisibility(View.INVISIBLE);
            listView.setAdapter(new MyListAdapter());
            Snackbar.make(listView,"These are events in "+cit+" area within "+rad+" km", Snackbar.LENGTH_LONG).show();
        }
    }

    private class MyListAdapter extends BaseAdapter {

        public int getCount() {
            return eventsList.size();      // Function tells how many objects to show
        }

        public CurrentEvent getItem(int position) {

            return eventsList.get(position);  // Returns the string at position p
        }

        public long getItemId(int p) {
            return ( long)p;          // Returns the database id of the item at position p
        }

        public View getView(int p, View recycled, ViewGroup parent) {
            View thisRow = recycled;

            if(recycled == null)
                thisRow = getLayoutInflater().inflate(R.layout.events_list, null);
            try {
                ((TextView) thisRow.findViewById(R.id.title)).setText("Name: " + getItem(p).toString());

            }
            catch (NullPointerException e) {

            }
            return thisRow;
        }
    }

    /**
     * Help menu provides information about this app
     */
    private void helpDialog() {

        View middle = getLayoutInflater().inflate(R.layout.activity_ticket_help_alert, null);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("General Information").setView(middle);

        builder.create().show();
    }

}