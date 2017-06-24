package com.example.androiddevelopment.zavrsnitest.db.model;

import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;


@DatabaseTable(tableName = Nekretnine.TABLE_NAME_USERS)
public class Nekretnine {


    public static final String TABLE_NAME_USERS = "nekretnine";

    public static final String FIELD_NAME_ID     = "id";
    public static final String FIELD_NAME_NAME   = "name";
    public static final String FIELD_NAME_OPIS   = "opis";
    public static final String FIELD_NAME_SLIKA = "slika";
    public static final String FIELD_NAME_ADRESA = "adresa";
    public static final String FIELD_NAME_TELEFON = "telefon";
    public static final String FIELD_NAME_KVARATURA = "kvadratura";
    public static final String FIELD_NAME_SOBE = "sobe";
    public static final String FIELD_NAME_CENA = "cena";

    public static final String FIELD_NAME_STAN = "stan";

    @DatabaseField(columnName = FIELD_NAME_ID, generatedId = true)
    private int mId;

    @DatabaseField(columnName = FIELD_NAME_NAME)
    private String mIme;

    @DatabaseField(columnName = FIELD_NAME_OPIS)
    private String myOpis;

    @DatabaseField(columnName = FIELD_NAME_SLIKA)
    private String mSlika;

    @DatabaseField(columnName = FIELD_NAME_ADRESA)
    private String mAdresa;

    @DatabaseField(columnName = FIELD_NAME_TELEFON)
    private String mTelefon;

    @DatabaseField(columnName = FIELD_NAME_KVARATURA)
    private int mKvadratura;

    @DatabaseField(columnName = FIELD_NAME_SOBE)
    private int mSobe;

    @DatabaseField(columnName = FIELD_NAME_CENA)
    private int mCena;

    @ForeignCollectionField(columnName = FIELD_NAME_STAN, eager = true)
    private ForeignCollection<Stan> stan;


    public Nekretnine(){}

    public Nekretnine(int mId, String mIme, String myOpis, String mSlika, String mAdresa, String mTelefon, int mKvadratura, int mSobe, int mCena, ForeignCollection<Stan> stan) {
        this.mId = mId;
        this.mIme = mIme;
        this.myOpis = myOpis;
        this.mSlika = mSlika;
        this.mAdresa = mAdresa;
        this.mTelefon = mTelefon;
        this.mKvadratura = mKvadratura;
        this.mSobe = mSobe;
        this.mCena = mCena;
        this.stan = stan;
    }

    public int getmId() {
        return mId;
    }

    public void setmId(int mId) {
        this.mId = mId;
    }

    public String getmIme() {
        return mIme;
    }

    public void setmIme(String mIme) {
        this.mIme = mIme;
    }

    public String getMyOpis() {
        return myOpis;
    }

    public void setMyOpis(String myOpis) {
        this.myOpis = myOpis;
    }

    public String getmSlika() {
        return mSlika;
    }

    public void setmSlika(String mSlika) {
        this.mSlika = mSlika;
    }

    public String getmAdresa() {
        return mAdresa;
    }

    public void setmAdresa(String mAdresa) {
        this.mAdresa = mAdresa;
    }

    public String getmTelefon() {
        return mTelefon;
    }

    public void setmTelefon(String mTelefon) {
        this.mTelefon = mTelefon;
    }

    public int getmKvadratura() {
        return mKvadratura;
    }

    public void setmKvadratura(int mKvadratura) {
        this.mKvadratura = mKvadratura;
    }

    public int getmSobe() {
        return mSobe;
    }

    public void setmSobe(int mSobe) {
        this.mSobe = mSobe;
    }

    public int getmCena() {
        return mCena;
    }

    public void setmCena(int mCena) {
        this.mCena = mCena;
    }

    public ForeignCollection<Stan> getStan() {
        return stan;
    }

    public void setStan(ForeignCollection<Stan> stan) {
        this.stan = stan;
    }

    @Override
    public String toString() {
        return mIme;
    }
}



