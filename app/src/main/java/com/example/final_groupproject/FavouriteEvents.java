package com.example.final_groupproject;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class FavouriteEvents extends AppCompatActivity {

    private ArrayList<CurrentEvent> savedEvents = new ArrayList<>();
    private ListView listView;
    private Cursor cursor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourite_events);

        listView = findViewById(R.id.favourite);
        loadDataFromDatabase();
        listView.setAdapter(new SavedListAdapter());

    }

    private void loadDataFromDatabase() {

        MyDbHelper dbOpener = new MyDbHelper(this);
        SQLiteDatabase db = dbOpener.getWritableDatabase();

        String [] columns = {MyDbHelper.COL_ID,MyDbHelper.COL_NAME, MyDbHelper.COL_URL,MyDbHelper.COL_DATE, MyDbHelper.COL_TIME, MyDbHelper.COL_MAX, MyDbHelper.COL_MIN};
        Cursor cursor = db.query(false, MyDbHelper.TABLE_NAME, columns, null, null, null, null, null, null);

        int idColIndex = cursor.getColumnIndex(MyDbHelper.COL_ID);
        int nameColIndex = cursor.getColumnIndex(MyDbHelper.COL_NAME);
        int urlColIndex = cursor.getColumnIndex(MyDbHelper.COL_URL);
        int dateIndex = cursor.getColumnIndex(MyDbHelper.COL_DATE);
        int timeIndex = cursor.getColumnIndex(MyDbHelper.COL_TIME);
        int maxColIndex = cursor.getColumnIndex(MyDbHelper.COL_MAX);
        int minColIndex= cursor.getColumnIndex(MyDbHelper.COL_MIN);

        while(cursor.moveToNext()) {

            long id = cursor.getLong(idColIndex);
            String dbName = cursor.getString(nameColIndex);
            String dbURL = cursor.getString(urlColIndex);
            String dbDate = cursor.getString(dateIndex);
            String dbTime = cursor.getString(timeIndex);
            int bdMax = cursor.getInt(maxColIndex);
            int bdMin = cursor.getInt(minColIndex);

            savedEvents.add(new CurrentEvent(dbName, dbURL, dbDate, dbTime, bdMin, bdMax, id,"","SAVED"));
           // myAdapter.notifyDataSetChanged();
        }

    }


    private class SavedListAdapter extends BaseAdapter {

        public int getCount() {
            return savedEvents.size();      // Function tells how many objects to show
        }

        public CurrentEvent getItem(int position) {

            return savedEvents.get(position);  // Returns the string at position p
        }

        public long getItemId(int p) {
            return ( long)p;          // Returns the database id of the item at position p
        }

        public View getView(int p, View recycled, ViewGroup parent) {
            View thisRow = recycled;

            if(recycled == null)
                thisRow = getLayoutInflater().inflate(R.layout.activity_favourite_events, null);
            try {
                ((TextView) thisRow.findViewById(R.id.saved_events)).setText("Name: " + getItem(p).toString());

            }
            catch (NullPointerException e) {

            }
            return thisRow;
        }
    }
}