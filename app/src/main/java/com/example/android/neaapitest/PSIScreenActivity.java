package com.example.android.neaapitest;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.Arrays;

/**
 * Created by user on 18-10-2015.
 */
public class PSIScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.psi_screen);

        //Setting the PSI Grid Values
        GridView psiGridView = (GridView) findViewById(R.id.PSIGridView1);
        ArrayAdapter<String> psiAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, Arrays.copyOfRange(MainActivity.getPSIString(), 0, 4));
        psiGridView.setAdapter(psiAdapter);

        GridView psiValuesGridView = (GridView) findViewById(R.id.PSIValueGridView1);
        ArrayAdapter<String> psiValuesAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, Arrays.copyOfRange(MainActivity.getPSIString(),4,8));
        psiValuesGridView.setAdapter(psiValuesAdapter);
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
    public void closePSI(View v){
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    public void setMapLabel (String text) {
        TextView textView = (TextView) findViewById(R.id.marker_text);
        textView.setText(text);
    }
}
