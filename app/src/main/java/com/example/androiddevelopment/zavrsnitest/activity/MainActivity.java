package com.example.androiddevelopment.zavrsnitest.activity;

import android.app.Dialog;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.NotificationCompat;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.androiddevelopment.zavrsnitest.R;
import com.example.androiddevelopment.zavrsnitest.db.DatabaseHelper;
import com.example.androiddevelopment.zavrsnitest.db.model.Nekretnine;
import com.j256.ormlite.android.apptools.OpenHelperManager;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    boolean showTost =false;
    boolean showNotif = false;
    boolean gearPress = false;
    private DatabaseHelper databaseHelper;
    ArrayAdapter<Nekretnine> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

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

    private void addItem() throws SQLException {
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.nekretnine_add_dijalog);

        final EditText naziv = (EditText) dialog.findViewById(R.id.naziv);
        final EditText opis = (EditText) dialog.findViewById(R.id.opis);
        final EditText slika = (EditText) dialog.findViewById(R.id.slika);
        final EditText adresa = (EditText) dialog.findViewById(R.id.adresa);
        final EditText telefon = (EditText) dialog.findViewById(R.id.telefon);
        final EditText kvadratura = (EditText) dialog.findViewById(R.id.kvadratura);
        final EditText brSoba = (EditText) dialog.findViewById(R.id.br_soba);
        final EditText cena = (EditText) dialog.findViewById(R.id.cena);

        Button ok = (Button) dialog.findViewById(R.id.submit_btn);
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nNaziv = naziv.getText().toString();
                String nOpis = opis.getText().toString();
                String nSlika = slika.getText().toString();
                String nAdresa = adresa.getText().toString();
                String nTelefon = telefon.getText().toString();
                String nKvadratura = kvadratura.getText().toString();
                String nSobe = brSoba.getText().toString();
                String nCena = cena.getText().toString();

                Nekretnine nekretnine = new Nekretnine();
                nekretnine.setmIme(nNaziv);
                nekretnine.setMyOpis(nOpis);
                nekretnine.setmSlika(nSlika);
                nekretnine.setmAdresa(nAdresa);
                nekretnine.setmTelefon(nTelefon);
                //nekretnine.setmKvadratura(Integer.parseInt(nKvadratura));
                //nekretnine.setmSobe(Integer.parseInt(nSobe));
                //nekretnine.setmCena(Integer.parseInt(nCena));


                try {

                    getDatabaseHelper().getNekretnineDao().create(nekretnine);
                    refresh();


                } catch (SQLException e) {
                    e.printStackTrace();
                }
                dialog.dismiss();

                if (showNotif){
                    showNotifi("Nekretnine ");
                }
            }
        });

        Button cancel = (Button) dialog.findViewById(R.id.reset_btn);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    // refresh() prikazuje novi sadrzaj.Povucemo nov sadrzaj iz baze i popunimo listu
    private void refresh() {

        final ListView listview = (ListView) findViewById(R.id.nekretnine);

        if (listview != null){
            //ArrayAdapter<Actors> adapter = (ArrayAdapter<Actors>) listview.getAdapter();

            Toast.makeText(this, "radi", Toast.LENGTH_SHORT).show();

            ArrayList<Nekretnine> nekretnine = new ArrayList<>();
            adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, nekretnine);

            listview.setAdapter(adapter);

            listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    Nekretnine n = (Nekretnine) listview.getItemAtPosition(position);
                    int setPosition = n.getmId();

                    Intent intent = new Intent(MainActivity.this, SecondActivity.class);
                    intent.putExtra("list_position", setPosition);
                    intent.putExtra("db_position", position);
                    startActivity(intent);
                }
            });

            if(adapter != null)
            {
                try {

                    adapter.clear();
                    List<Nekretnine> list = getDatabaseHelper().getNekretnineDao().queryForAll();
                    adapter.addAll(list);
                    adapter.notifyDataSetChanged();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    //Metoda koja komunicira sa bazom podataka
    public DatabaseHelper getDatabaseHelper() {
        if (databaseHelper == null) {
            databaseHelper = OpenHelperManager.getHelper(this, DatabaseHelper.class);
        }
        return databaseHelper;
    }

    //Notification

    public void showNotifi(String info){
        NotificationCompat.Builder mBilder= (NotificationCompat.Builder) new NotificationCompat.Builder(this).
                setSmallIcon(R.drawable.ic_notification_info).
                setContentTitle("Info").
                setContentText(info + " is added.");

        NotificationManager mNotificationMenager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationMenager.notify(1, mBilder.build());
    }


    @Override
    protected void onStart() {
        super.onStart();
        refresh();
    }

    @Override
    protected void onResume() {
        super.onResume();
        refresh();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        gearPress = true;
        refresh();
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        refresh();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        // nakon rada sa bazo podataka potrebno je obavezno
        //osloboditi resurse!
        if (databaseHelper != null) {
            OpenHelperManager.releaseHelper();
            databaseHelper = null;
        }
    }
}
