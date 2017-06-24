package com.example.androiddevelopment.zavrsnitest.activity;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.androiddevelopment.zavrsnitest.R;

import java.sql.SQLException;

public class MainActivity extends AppCompatActivity {

    boolean showTost, showNotif;

    //Dodajemo toolbar u naredne dve metode
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_nekretnine, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(this);
        showTost = settings.getBoolean("cb_show_tost", true);
        showNotif = settings.getBoolean("cb_show_notification", false);

        switch (item.getItemId()) {
            case R.id.action_add:
                try {
                    addItem();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                if (showTost) {
                    Toast.makeText(this, "Add actors", Toast.LENGTH_SHORT).show();
                }
                break;
        }

            return super.onOptionsItemSelected(item);
    }

    private void addItem() throws SQLException{


    }
}
