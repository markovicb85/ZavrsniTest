package com.example.androiddevelopment.zavrsnitest.activity;

import android.app.Dialog;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.NotificationCompat;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.androiddevelopment.zavrsnitest.R;
import com.example.androiddevelopment.zavrsnitest.db.DatabaseHelper;
import com.example.androiddevelopment.zavrsnitest.db.model.Nekretnine;
import com.example.androiddevelopment.zavrsnitest.db.model.Stan;
import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.stmt.Where;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SecondActivity extends AppCompatActivity {

    private DatabaseHelper databaseHelper;
    private int actorPosition, actorDbPosition;
    private TextView actor_name, actor_biography;
    private Nekretnine nekretnine;
    private Stan stan;
    private boolean showNotif, showTost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        Intent intent = getIntent();
        actorPosition = intent.getIntExtra("list_position", 1);
        actorDbPosition = intent.getIntExtra("db_position", 0);
        Toast.makeText(this, "Actor" + actorDbPosition, Toast.LENGTH_SHORT).show();
        showActor();
        showMovie();
    }

    //Dodajemo toolbar u naredne dve metode
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_stan, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(this);
        showTost = settings.getBoolean("cb_show_tost", true);
        showNotif = settings.getBoolean("cb_show_notification", false);

        switch (item.getItemId()) {
            case R.id.action_edit:
                editActor();
                //String text = ReviewerTools.readFromFile(this, "myfile.txt");
                Toast.makeText(this, "Edit movie", Toast.LENGTH_SHORT).show();
                break;
            case R.id.action_delete:
                deleteActor();
                //String text = ReviewerTools.readFromFile(this, "myfile.txt");
                Toast.makeText(this, "Delete actor", Toast.LENGTH_SHORT).show();
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


    private void editActor() {
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.actor_add_dialog);

        final EditText actorName = (EditText) dialog.findViewById(R.id.actor_name);
        final EditText actorBiography = (EditText) dialog.findViewById(R.id.actor_biography);
        final EditText actorDate = (EditText) dialog.findViewById(R.id.actor_date);

        Button ok = (Button) dialog.findViewById(R.id.add);
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = actorName.getText().toString();
                String biography = actorBiography.getText().toString();
                String date = actorDate.getText().toString();

                actor = new Actors();
                actor.setMyName(name);
                actor.setMyBiography(biography);
                actor.setMyDate(date);
                actor.setMyId(actorPosition); //Da bi se poklapao novi objekat sa onim u db koji menjamo mora da ima isti id

                try {
                    if (!name.matches("")) {
                        getDatabaseHelper().getActorstDao().update(actor);
                        showActor();
                    }else{
                        //Poruka popuniti sva polja
                    }

                } catch (SQLException e) {
                    e.printStackTrace();
                }
                dialog.dismiss();
            }
        });
        dialog.show();
        if(showNotif){
            showNotifi("Movie ");
        }
    }


    private void deleteActor() {
        try {
            getDatabaseHelper().getNekretnineDao().deleteById(actorPosition);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        startActivity(new Intent(SecondActivity.this, MainActivity.class));

    }

    private void addMovie() {
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.movie_add_dialog);

        final EditText movieName = (EditText) dialog.findViewById(R.id.movi_name);
        final EditText movieGenre = (EditText) dialog.findViewById(R.id.genre);
        final EditText movieYear = (EditText) dialog.findViewById(R.id.relese_date);

        Button ok = (Button) dialog.findViewById(R.id.submit_btn);
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = movieName.getText().toString();
                String genre = movieGenre.getText().toString();
                String year = movieYear.getText().toString();

                movie = new Movie();
                movie.setName(name);
                movie.setGenre(genre);
                movie.setYear(year);
                movie.setActor(actor);

                try {
                    if (!name.matches("")) {
                        getDatabaseHelper().getMovieDao().create(movie);
                        actor = getDatabaseHelper().getActorstDao().queryForId(actorPosition);
                    }else{
                        //Poruka da se unesu sva polja
                    }

                } catch (SQLException e) {
                    e.printStackTrace();
                }

                dialog.dismiss();
                showMovie();

                if (showNotif){
                    showNotifi("Movie ");
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

    private void showMovie() {

        final ListView listView = (ListView) findViewById(R.id.stan);
        List<Stan> movies = new ArrayList<>();

        try {
            QueryBuilder<Stan, Integer> qb = getDatabaseHelper().getStanDao().queryBuilder();
            Where where = qb.where();
            movies = where.eq(Stan.FIELD_NAME_NEKRETNINE, actor.getMyId()).query();
        }catch (SQLException e) {
            e.printStackTrace();
        }

        listView.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, movies));

    }

    private void showActor(){
        try {
            actor = getDatabaseHelper().getActorstDao().queryForId(actorPosition);
        }catch (SQLException e) {
            e.printStackTrace();
        }

        actor_name = (TextView) findViewById(R.id.actor_name);
        actor_biography = (TextView) findViewById(R.id.actor_biography);

        actor_name.setText(actor.getMyName());
        actor_biography.setText(actor.getMyBiography());
    }

    //Metoda koja komunicira sa bazom podataka
    public DatabaseHelper getDatabaseHelper() {
        if (databaseHelper == null) {
            databaseHelper = OpenHelperManager.getHelper(this, DatabaseHelper.class);
        }
        return databaseHelper;
    }

    public void showNotifi(String info){
        NotificationCompat.Builder mBilder= (NotificationCompat.Builder) new NotificationCompat.Builder(this).
                setSmallIcon(R.drawable.ic_notification_info).
                setContentTitle("Info").
                setContentText(info + " is added.");

        NotificationManager mNotificationMenager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationMenager.notify(2, mBilder.build());
    }

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
