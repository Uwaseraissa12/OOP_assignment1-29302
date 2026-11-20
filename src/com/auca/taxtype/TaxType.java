package com.auca.taxtype;
import java.time.LocalDate;
import java.math.BigDecimal;
import com.auca.interfaces.*;
import com.auca.exceptions.ValidationException;

public class TaxType implements Identifiable, Validatable {
 private String taxTypeId;
 private String taxCode;
 private String taxName;
 private BigDecimal rate;
 private String filingFrequency;
 private boolean active;
 public TaxType(){}
 public String getTaxTypeId(){return taxTypeId;} public void setTaxTypeId(String v){taxTypeId=v;}
 public String getTaxCode(){return taxCode;} public void setTaxCode(String v){taxCode=v;}
 public String getTaxName(){return taxName;} public void setTaxName(String v){taxName=v;}
 public BigDecimal getRate(){return rate;} public void setRate(BigDecimal v){rate=v;}
 public String getFilingFrequency(){return filingFrequency;} public void setFilingFrequency(String v){filingFrequency=v;}
 public boolean getActive(){return active;} public void setActive(boolean v){active=v;}
 @Override public String getId(){return taxTypeId;}
 @Override public String getDisplayName(){return taxName;} @Override public boolean isActive(){return active;}
 @Override public void validate() throws ValidationException {
 if(blank(taxTypeId)||blank(taxCode)||blank(taxName)||rate==null||rate.signum()<0||rate.compareTo(new BigDecimal("100"))>0)
  throw new ValidationException("Tax type details invalid; ID, code and name are required and rate must be 0-100.");
 if(!"MONTHLY".equals(filingFrequency)&&!"QUARTERLY".equals(filingFrequency)&&!"ANNUAL".equals(filingFrequency))
  throw new ValidationException("Frequency must be MONTHLY, QUARTERLY or ANNUAL.");
 }
 private boolean blank(String s){return s==null||s.trim().isEmpty();}
 @Override public void printDetails(){System.out.println(getSummary());}
 @Override public String getSummary(){return getDisplayName()+" [ID="+getId()+"]";}
}

