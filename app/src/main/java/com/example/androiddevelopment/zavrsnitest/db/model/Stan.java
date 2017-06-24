package com.example.androiddevelopment.zavrsnitest.db.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;


@DatabaseTable(tableName = Stan.TABLE_NAME_USER)
public class Stan {


    public static final String TABLE_NAME_USER = "stan";

    public static final String FIELD_NAME_ID     = "id";
    public static final String FIELD_NAME_STAN = "stanovi";
    public static final String FIELD_NAME_NEKRETNINE = "nekretnine";

    @DatabaseField(columnName = FIELD_NAME_ID, generatedId = true)
    private int mID;

    @DatabaseField(columnName = FIELD_NAME_STAN)
    private String mStan;

    @DatabaseField(columnName = FIELD_NAME_NEKRETNINE, foreign = true, foreignAutoRefresh = true)
    private Nekretnine nekretnine;

    public Stan(){}

    public Stan(int mID, String mStan, Nekretnine nekretnine) {
        this.mID = mID;
        this.mStan = mStan;
        this.nekretnine = nekretnine;
    }

    public int getmID() {
        return mID;
    }

    public void setmID(int mID) {
        this.mID = mID;
    }

    public String getmStan() {
        return mStan;
    }

    public void setmStan(String mStan) {
        this.mStan = mStan;
    }

    public Nekretnine getNekretnine() {
        return nekretnine;
    }

    public void setNekretnine(Nekretnine nekretnine) {
        this.nekretnine = nekretnine;
    }

    @Override
    public String toString() {
        return mStan;
    }
}
