package com.alquimia.koua;

import android.app.Application;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.alquimia.koua.db.DaoMaster;
import com.alquimia.koua.db.DaoSession;

import java.io.File;

/**
 * Created by Rolando on 7/24/2016.
 */
public class Koua
{
  /*private static Koua instance;
  public DaoSession daoSession;*/

  /*public static Koua getInstance()
  {
    return instance;
  }*/

/*  @Override
  public void onCreate()
  {
    super.onCreate();
    instance = this;

//    boolean dbExist = doesDatabaseExist(getApplicationContext(), "alquimia-koua-db");
    DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(getApplicationContext(), "alquimia-koua-db", null);
    SQLiteDatabase db = helper.getWritableDatabase();
    DaoMaster daoMaster = new DaoMaster(db);
    daoSession = daoMaster.newSession();

*//*    if(dbExist)
    {
      // return main activity.
    }
    else
    {
      // return Activity to ask to read previous messages.
    }*//*
  }*/

/*  private boolean doesDatabaseExist(Context context, String databaseName)
  {
    File dbFile = context.getDatabasePath(databaseName);
    return dbFile.exists();
  }*/

/*  public DaoSession getDaoSession()
  {
    return daoSession;
  }*/
}
