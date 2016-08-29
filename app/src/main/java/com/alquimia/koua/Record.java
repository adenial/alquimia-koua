package com.alquimia.koua;

/**
 * Created by Rolando on 8/3/2016.
 */
public class Record
{
  private String title;

  private String smsMessage;

  private Double amount;

  private Integer operationType;

  public String getDate()
  {
    return date;
  }

  public void setDate(String date)
  {
    this.date = date;
  }

  private String date;

  public void setSmsMessage(String smsMessage)
  {
    this.smsMessage = smsMessage;
  }

  public Long getRecordId()
  {
    return recordId;
  }

  public void setRecordId(Long recordId)
  {
    this.recordId = recordId;
  }

  private Long recordId;

  public Integer getOperationType()
  {
    return operationType;
  }

  public void setOperationType(Integer operationType)
  {
    this.operationType = operationType;
  }

  public String getTitle()
  {
    return title;
  }

  public void setTitle(String title)
  {
    this.title = title;
  }

  public Double getAmount()
  {
    return amount;
  }

  public String getSmsMessage()
  {
    return smsMessage;
  }

  public void setAmount(Double amount)
  {
    this.amount = amount;
  }

  public Record()
  {
  }

  public Record(String title, Double amount, Integer operationType, String smsMessage, Long recordId, String date)
  {
    this.title = title;
    this.amount = amount;
    this.operationType = operationType;
    this.smsMessage = smsMessage;
    this.recordId = recordId;
    this.date = date;
  }
}
