package com.example.android.neaapitest;

import android.app.ListActivity;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;


public class MainActivity extends AppCompatActivity {

    public String[] temperatureString = new String[] {
            "Now", "1PM", "2PM", "3PM", "32\u00b0C",
            "32°C", "32°C", "32°C"};

    public static String[] psiString = new String[] {
            "Now", "1PM", "2PM", "3PM", "108",
            "108", "108", "108"};

    public static String[] getPSIString(){
        return psiString;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //setting list view to recent alerts
        ListView listView = (ListView) findViewById(R.id.RecentAlerts);
        RecentAlertsDB alertsDB = new RecentAlertsDB(this);
        //Generating Random Data to insert to database
        RecentAlerts recentAlerts = new RecentAlerts();
        recentAlerts.description = "PSI +1";
        recentAlerts.alert_ID = 0;

        alertsDB.insert(recentAlerts);

        ArrayList<HashMap<String, String>> alertsList =  alertsDB.getAlertsList(6);
        ListAdapter adapter = new SimpleAdapter( this, alertsList, R.layout.list_view_element, new String[] {"id","description"}, new int[] {R.id.alert_Id, R.id.alert_name});
        listView.setAdapter(adapter);
        justifyListViewHeightBasedOnChildren(listView);

        //setting scrollview background to gif
        ScrollView img = (ScrollView)findViewById(R.id.MainScrollView);
        img.setBackgroundResource(R.drawable.progress_animation);
        AnimationDrawable frameAnimation = (AnimationDrawable) img.getBackground();
        frameAnimation.start();


        //Setting temperature grid view
        //uncomment the block below and edit
        /*temperatureString[0] = "";
        temperatureString[1] = "";
        temperatureString[2] = "";
        temperatureString[3] = "";
        temperatureString[4] = "";
        temperatureString[5] = "";
        temperatureString[6] = "";
        temperatureString[7] = ""; */

        GridView temperatureGridView = (GridView) findViewById(R.id.TemperatureTimes);
        ArrayAdapter<String> temperatureAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, Arrays.copyOfRange(temperatureString,0,4));
        temperatureGridView.setAdapter(temperatureAdapter);

        GridView temperatureValueGridView = (GridView) findViewById(R.id.TemperatureValues);
        ArrayAdapter<String> temperatureValueAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, Arrays.copyOfRange(temperatureString,4,8));
        temperatureValueGridView.setAdapter(temperatureValueAdapter);

        //Setting PSI level grid view
        //uncomment the block below to edit
        /*psiString[0] = "";
        psiString[1] = "";
        psiString[2] = "";
        psiString[3] = "";
        psiString[4] = "";
        psiString[5] = "";
        psiString[6] = "";
        psiString[7] = ""; */

        GridView psiGridView = (GridView) findViewById(R.id.PSITimes);
        ArrayAdapter<String> psiAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, Arrays.copyOfRange(psiString,0,4));
        psiGridView.setAdapter(psiAdapter);

        GridView psiValuesGridView = (GridView) findViewById(R.id.PSIValues);
        ArrayAdapter<String> psiValuesAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, Arrays.copyOfRange(psiString,4,8));
        psiValuesGridView.setAdapter(psiValuesAdapter);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    public void displayPSILevel(View v){
        Intent intent = new Intent(this, PSIScreenActivity.class);
        startActivity(intent);
    }

    public void displayTemp( View v){
        Intent intent = new Intent(this, TemperatureForecastScreenActivity.class);
        startActivity(intent);
    }
    public void displayRecent( View v){
        Intent intent = new Intent(this, RecentAlertsScreen.class);
        startActivity(intent);
    }

    public void justifyListViewHeightBasedOnChildren (ListView listView) {

        ListAdapter adapter = listView.getAdapter();

        if (adapter == null) {
            return;
        }
        ViewGroup vg = listView;
        int totalHeight = 0;
        for (int i = 0; i < adapter.getCount(); i++) {
            View listItem = adapter.getView(i, null, vg);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams par = listView.getLayoutParams();
        par.height = totalHeight + (listView.getDividerHeight() * (adapter.getCount() - 1));
        listView.setLayoutParams(par);
        listView.requestLayout();
    }


}
