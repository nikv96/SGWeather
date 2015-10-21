package com.example.android.neaapitest;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by user on 18-10-2015.
 */
public class RecentAlertsScreen extends ListActivity implements android.view.View.OnClickListener{

    private int _student_id = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recent_alerts_screen);
        RecentAlertsDB alertsDB = new RecentAlertsDB(this);

        //Generating Random Data to insert to database
        RecentAlerts recentAlerts = new RecentAlerts();
            recentAlerts.description = "PSI +1";
            recentAlerts.student_ID = 0;
            if (_student_id == 0) {
                _student_id = alertsDB.insert(recentAlerts);
            } else {
                alertsDB.update(recentAlerts);
            }
        recentAlerts.description = "PSI +2";
        recentAlerts.student_ID = 1;
        if (_student_id == 0) {
            _student_id = alertsDB.insert(recentAlerts);
        } else {
            alertsDB.update(recentAlerts);
        }

        //This section does not work
        ArrayList<HashMap<String, String>> alertsList =  alertsDB.getAlertsList();
        int size = alertsList.size();
        while(size>0) {
            ListView lv = getListView();
            ListAdapter adapter = new SimpleAdapter( this,alertsList, R.layout.list_view_element, new String[] { "id","description"}, new int[] {R.id.student_Id, R.id.student_name});
            setListAdapter(adapter);
            size--;
        }
        if(alertsList.size() == 0){
            Toast.makeText(this, "No student!", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle app bar item clicks here. The app bar
        // automatically handles clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void closeRecent(View v){
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        return;
    }
}
