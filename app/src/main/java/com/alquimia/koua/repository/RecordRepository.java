package com.alquimia.koua.repository;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.alquimia.koua.Record;
import com.alquimia.koua.db.DaoMaster;
import com.alquimia.koua.db.DaoSession;
import com.alquimia.koua.db.KouaRecord;
import com.alquimia.koua.db.KouaRecordDao;

import java.util.List;

public class RecordRepository
{
  private SQLiteDatabase db;
  private DaoMaster.DevOpenHelper helper;
  private DaoMaster daoMaster;

  public List<KouaRecord> getAllRecords(Context context)
  {
    return getKouaRecordDao(context).loadAll();
  }

  public Record getRecordById(Context context, long recordId)
  {
    KouaRecord record = getKouaRecordDao(context).load(recordId);
    Record recordView = new Record();
    recordView.setTitle(record.getOperation());
    recordView.setSmsMessage(record.getMessage());
    recordView.setAmount(record.getAmount());
    recordView.setDate(record.getDate());
    return recordView;
  }

  private KouaRecordDao getKouaRecordDao(Context context)
  {
    return getDaoSession(context).getKouaRecordDao();
  }

  private DaoSession getDaoSession(Context context)
  {
    helper = new DaoMaster.DevOpenHelper(context, "alquimia-koua-db", null);
    db = helper.getWritableDatabase();
    daoMaster = new DaoMaster(db);
    return daoMaster.newSession();
  }
}
