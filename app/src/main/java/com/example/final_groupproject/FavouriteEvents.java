package com.example.final_groupproject;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;

public class FavouriteEvents extends AppCompatActivity {

    private int id, name, url, max, min, date, time;
    private ArrayList<CurrentEvent> favEvents = new ArrayList<>();
    private ListView listView;
    MyListAdapter myAdapter = new MyListAdapter();
    String deleteQuery;
    SQLiteDatabase db;

    /**
     * Inflate the menu items for use in the action bar
     */

    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.ticket_menu, menu);

        return true;
    }

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

    /**
     * Help menu provides information about this app
     */
    private void helpDialog() {

        View middle = getLayoutInflater().inflate(R.layout.activity_ticket_help_alert, null);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("General Information").setView(middle);

        builder.create().show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved_events);

        listView = findViewById(R.id.saved_events);
        loadDataFromDatabase();
        listView.setAdapter(myAdapter);


        /**
         * An alert window, gives an option to run a query and delete data from db and the current list.
         * Second option is just a Toast
         */
        listView.setOnItemClickListener((parent, view, pos, id) -> {

            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
            alertDialogBuilder.setTitle("Do you want to delete the following event "+ favEvents.get(pos).getName())

                    .setNeutralButton("Quit", (click, arg) -> {
                        Toast.makeText(getApplicationContext(),
                                "Enjoy!",
                                Toast.LENGTH_LONG).show();
                        myAdapter.notifyDataSetChanged();
                    })

                    .setPositiveButton("Delete", (click, arg) -> {
                        deleteQuery = "DELETE FROM " + MyDbHelper.TABLE_NAME + " WHERE " + MyDbHelper.COL_ID + " = '" + id + "'";
                        db.execSQL(deleteQuery);
                        favEvents.remove(pos);
                        myAdapter.notifyDataSetChanged();
                    })

                    .create().show();

            myAdapter.notifyDataSetChanged();

        });
    }

    /**
     * loadDataFromDatabase() provides writable connection to Db and unload data from the db to the favourite object
     */
    private void loadDataFromDatabase() {

        MyDbHelper dbOpener = new MyDbHelper(this);
        db = dbOpener.getWritableDatabase();

        String [] columns = {MyDbHelper.COL_ID,MyDbHelper.COL_NAME, MyDbHelper.COL_URL,MyDbHelper.COL_DATE, MyDbHelper.COL_TIME, MyDbHelper.COL_MAX, MyDbHelper.COL_MIN};
        Cursor cursor = db.query(false, MyDbHelper.TABLE_NAME, columns, null, null, null, null, null, null);

         id = cursor.getColumnIndex(MyDbHelper.COL_ID);
         name = cursor.getColumnIndex(MyDbHelper.COL_NAME);
         url = cursor.getColumnIndex(MyDbHelper.COL_URL);
         date = cursor.getColumnIndex(MyDbHelper.COL_DATE);
         time = cursor.getColumnIndex(MyDbHelper.COL_TIME);
         max = cursor.getColumnIndex(MyDbHelper.COL_MAX);
         min = cursor.getColumnIndex(MyDbHelper.COL_MIN);

        //iterates putting data into an object
        while(cursor.moveToNext()) {
            favEvents.add(new CurrentEvent(cursor.getString(name), cursor.getString(url), cursor.getString(date),
                    cursor.getString(time), cursor.getInt(min),
                    cursor.getInt(max), cursor.getLong(id)));
        }
    }

    private class MyListAdapter extends BaseAdapter {

        public int getCount() {
            return favEvents.size();       // Function tells how many objects to show
        }

        public Object getItem(int position) {

            return favEvents.get(position);   // Returns the string at position p
        }

        public long getItemId(int p) {

            return favEvents.get(p).getId();      // Returns the database id of the item at position p
        }


        public View getView(int p, View recycled, ViewGroup parent) {
            View thisRow = recycled;

            thisRow = getLayoutInflater().inflate(R.layout.activity_favourite_events, parent, false);


            ((TextView) thisRow.findViewById(R.id.savedeventName)).setText(getItem(p).toString());
            ((TextView) thisRow.findViewById(R.id.savedeventurl)).setText(((CurrentEvent)getItem(p)).getURL());
            ((TextView) thisRow.findViewById(R.id.savedeventDate)).setText(((CurrentEvent)getItem(p)).getEventDate());

            return thisRow;
        }

    }
}