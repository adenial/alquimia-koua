package com.alquimia.koua.service;
import com.alquimia.koua.db.KouaRecord;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.commons.lang3.StringUtils;

public class SMSParserImpl implements SMSParser
{
  public Boolean isValidMessage(String message)
  {
    if (message != null)
    {
      List<String> tokens = new ArrayList<>();
      tokens.add("Retiro/Compra");
      tokens.add("Deposito");

      String patternString = "\\b(" + StringUtils.join(tokens, "|") + ")\\b";
      Pattern pattern = Pattern.compile(patternString);
      Matcher matcher = pattern.matcher(message);

      while (matcher.find())
      {
        return true;
      }

      return false;
    }
    else
    {
      return false;
    }
  }

  public KouaRecord Process(String message)
  {
    try
    {
      String firstDatePart = null;
      KouaRecord msg = new KouaRecord();

      String[] words = message.split("\\s+");
      List<String> wordList = Arrays.asList(words);

      for (String eachString : wordList)
      {
        if (eachString.equals("Retiro/Compra") || eachString.equals("Deposito"))
        {
          msg.setOperation(eachString);

          if(eachString.equals("Retiro/Compra"))
          {
            msg.setOperationType(1);
          }
          else
          {
            msg.setOperationType(2);
          }
        }
        else
        if (eachString.startsWith("$"))
        {
          String amount;
          if(eachString.contains(","))
          {
            amount = eachString.replace("$","");
            amount = amount.replace(",","");
          }
          else
          {
            amount = eachString.substring(1);
          }

          msg.setAmount(Double.parseDouble(amount));
        }

        if (firstDatePart == null)
        {
          Boolean validDate = TryToParse(eachString, "dd/MM/yy");

          if (validDate)
          {
            firstDatePart = eachString;
          }
        }
        else
        {
          String fullDate = firstDatePart + " " + eachString;

          if (TryToParse(fullDate, "dd/MM/yy HH:mm:ss"))
          {
            msg.setDate(fullDate);
          }
        }
      }

      Pattern pattern = Pattern.compile("Auto.(.*)");
      Matcher matcher = pattern.matcher(message);
      if (matcher.find())
      {
        msg.setAuthorization(Integer.parseInt(matcher.group(1).trim()));
      }

      msg.setMessage(message);
      return msg;
    }
    catch (Exception ex)
    {
      throw ex;
    }
  }

  private Boolean TryToParse(String value, String pattern)
  {
    try
    {
      SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
      dateFormat.parse(value);
      return true;
    }
    catch (Exception ex)
    {
      return false;
    }
  }
}
