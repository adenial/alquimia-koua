package com.alquimia.koua.service;

import com.alquimia.koua.db.KouaRecord;

public interface SMSParser
{
  Boolean isValidMessage(String message);
  KouaRecord Process(String message);
}
