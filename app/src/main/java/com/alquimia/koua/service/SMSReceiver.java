package com.alquimia.koua.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.widget.Toast;

import com.alquimia.koua.db.DaoMaster;
import com.alquimia.koua.db.DaoSession;
import com.alquimia.koua.db.KouaRecord;
import com.alquimia.koua.db.KouaRecordDao;

import java.util.List;

import de.greenrobot.dao.query.QueryBuilder;

public class SMSReceiver extends BroadcastReceiver
{
  private SQLiteDatabase db;
  private DaoMaster.DevOpenHelper helper;
  private DaoSession daoSession;
  private DaoMaster daoMaster;
  private KouaRecordDao kouaMessageDao;

  private final String TAG = this.getClass().getSimpleName();
  final SmsManager sms = SmsManager.getDefault();

  public void onReceive(Context context, Intent intent)
  {
    helper = new DaoMaster.DevOpenHelper(context, "alquimia-koua-db", null);
    db = helper.getWritableDatabase();
    daoMaster = new DaoMaster(db);
    daoSession = daoMaster.newSession();
    kouaMessageDao = daoSession.getKouaRecordDao();

    String smsData;

    // Get the intent extras with the PDU information
    Bundle pdusBundle = intent.getExtras();

    if (pdusBundle != null)
    {
      // Get the message in PDU format
      Object[] pdus = (Object[]) pdusBundle.get("pdus");

      // Create a SMS message from the PDU info
      SmsMessage message = SmsMessage.createFromPdu((byte[]) pdus[0]);

      // Extract the message body
      smsData = message.getMessageBody();

      SMSParserImpl parser = new SMSParserImpl();
      Boolean valid = parser.isValidMessage(smsData);

      if (valid)
      {
        KouaRecord kouamsg;
        kouamsg = parser.Process(smsData);
        if (kouamsg != null)
        {
/*          if(canInsert(kouamsg))
          {*/
            kouaMessageDao.insert(kouamsg);
            Toast.makeText(context,  "New Message" , Toast.LENGTH_LONG).show();
/*          }
          else
          {
            Toast.makeText(context,  "Duplicated Message" , Toast.LENGTH_LONG).show();
          }*/
        }
      }
    }
  }


  private boolean canInsert(KouaRecord kouamsg)
  {
    QueryBuilder query = kouaMessageDao.queryBuilder();
    query.and(KouaRecordDao.Properties.Amount.eq(kouamsg.getAmount()),
        KouaRecordDao.Properties.Authorization.eq(kouamsg.getAuthorization()));

    List messageList = query.list();

    if(!messageList.equals(null) && messageList.size()> 0 )
    {
      return false;
    }
    else
    {
      return true;
    }
  }
}
