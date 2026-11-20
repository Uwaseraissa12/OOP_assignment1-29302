package com.auca.penalty;
import java.time.LocalDate;
import java.math.BigDecimal;
import com.auca.interfaces.*;
import com.auca.exceptions.ValidationException;

import com.auca.taxobligation.TaxObligation;
public class Penalty implements Identifiable, Validatable {
 private String penaltyId;
 private TaxObligation obligation;
 private String penaltyType;
 private BigDecimal baseAmount;
 private BigDecimal penaltyRate;
 private BigDecimal penaltyAmount;
 public Penalty(){}
 public String getPenaltyId(){return penaltyId;} public void setPenaltyId(String v){penaltyId=v;}
 public TaxObligation getObligation(){return obligation;} public void setObligation(TaxObligation v){obligation=v;}
 public String getPenaltyType(){return penaltyType;} public void setPenaltyType(String v){penaltyType=v;}
 public BigDecimal getBaseAmount(){return baseAmount;} public void setBaseAmount(BigDecimal v){baseAmount=v;}
 public BigDecimal getPenaltyRate(){return penaltyRate;} public void setPenaltyRate(BigDecimal v){penaltyRate=v;}
 public BigDecimal getPenaltyAmount(){return penaltyAmount;} public void setPenaltyAmount(BigDecimal v){penaltyAmount=v;}
 @Override public String getId(){return penaltyId;}
 @Override public String getDisplayName(){return "Penalty "+penaltyId;} @Override public boolean isActive(){return true;}
 @Override public void validate() throws ValidationException {
 if(penaltyId==null||penaltyId.trim().isEmpty()) throw new ValidationException("Penalty ID is required.");
 if(obligation==null||penaltyType==null||penaltyType.trim().isEmpty()) throw new ValidationException("Penalty obligation and type are required.");
 if(baseAmount==null||penaltyRate==null||penaltyAmount==null||baseAmount.signum()<0||penaltyRate.signum()<0||penaltyAmount.signum()<0) throw new ValidationException("Penalty amounts and rate cannot be negative.");
 }
 @Override public void printDetails(){System.out.println(getSummary());}
 @Override public String getSummary(){return getDisplayName()+" [ID="+getId()+"]";}
}

