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
import java.util.HashMap;


public class MainActivity extends AppCompatActivity {

    private int _student_id = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //setting list view to recent alerts
        ListView listView = (ListView) findViewById(R.id.recent_alerts_list);


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

        ScrollView img = (ScrollView)findViewById(R.id.scroll1);
        img.setBackgroundResource(R.drawable.progress_animation);
        AnimationDrawable frameAnimation = (AnimationDrawable) img.getBackground();

        frameAnimation.start();
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
