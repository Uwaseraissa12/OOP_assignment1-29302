package com.auca.taxobligation;
import java.time.LocalDate;
import java.math.BigDecimal;
import com.auca.interfaces.*;
import com.auca.exceptions.ValidationException;

import com.auca.abstracts.RegisteredTaxpayer;
import com.auca.taxtype.TaxType;
public class TaxObligation implements Identifiable, Validatable {
 private String obligationId;
 private RegisteredTaxpayer taxpayer;
 private TaxType taxType;
 private LocalDate periodStart;
 private LocalDate periodEnd;
 private LocalDate dueDate;
 public TaxObligation(){}
 public String getObligationId(){return obligationId;} public void setObligationId(String v){obligationId=v;}
 public RegisteredTaxpayer getTaxpayer(){return taxpayer;} public void setTaxpayer(RegisteredTaxpayer v){taxpayer=v;}
 public TaxType getTaxType(){return taxType;} public void setTaxType(TaxType v){taxType=v;}
 public LocalDate getPeriodStart(){return periodStart;} public void setPeriodStart(LocalDate v){periodStart=v;}
 public LocalDate getPeriodEnd(){return periodEnd;} public void setPeriodEnd(LocalDate v){periodEnd=v;}
 public LocalDate getDueDate(){return dueDate;} public void setDueDate(LocalDate v){dueDate=v;}
 @Override public String getId(){return obligationId;}
 @Override public String getDisplayName(){return "Obligation "+obligationId;} @Override public boolean isActive(){return true;}
 @Override public void validate() throws ValidationException {
 if(obligationId==null||obligationId.trim().isEmpty()) throw new ValidationException("Obligation ID is required.");
 if(taxpayer==null||taxType==null) throw new ValidationException("Taxpayer and tax type references are required.");
 if(!taxpayer.isActive()||!taxType.isActive()) throw new ValidationException("Taxpayer and tax type must be active.");
 if(periodStart==null||periodEnd==null||dueDate==null) throw new ValidationException("All obligation dates are required.");
 if(periodStart.isAfter(periodEnd)||dueDate.isBefore(periodEnd)) throw new com.auca.exceptions.InvalidTaxPeriodException("Period start must be <= period end and due date cannot precede period end.");
 }
 @Override public void printDetails(){System.out.println(getSummary());}
 @Override public String getSummary(){return getDisplayName()+" [ID="+getId()+"]";}
}

