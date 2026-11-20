package com.auca.individualtaxpayer;
import java.time.LocalDate;
import java.math.BigDecimal;
import com.auca.interfaces.*;
import com.auca.exceptions.ValidationException;
import com.auca.abstracts.RegisteredTaxpayer;

public class IndividualTaxpayer extends RegisteredTaxpayer  {
 private String nationalId;
 private String occupation;
 private String businessName;
 private LocalDate businessStartDate;
 private BigDecimal annualTurnover;
 private boolean resident;
 public IndividualTaxpayer(){}
 public IndividualTaxpayer(String partyId, String firstName, String lastName, String email, String phoneNumber, LocalDate dateOfBirth,
                           String tin, LocalDate registrationDate, String taxpayerStatus, String district, String sector, String taxOffice,
                           String nationalId, String occupation, String businessName, LocalDate businessStartDate, BigDecimal annualTurnover, boolean resident) {
  super(partyId, firstName, lastName, email, phoneNumber, dateOfBirth, tin, registrationDate, taxpayerStatus, district, sector, taxOffice);
  this.nationalId=nationalId; this.occupation=occupation; this.businessName=businessName;
  this.businessStartDate=businessStartDate; this.annualTurnover=annualTurnover; this.resident=resident;
 }
 public String getNationalId(){return nationalId;} public void setNationalId(String v){nationalId=v;}
 public String getOccupation(){return occupation;} public void setOccupation(String v){occupation=v;}
 public String getBusinessName(){return businessName;} public void setBusinessName(String v){businessName=v;}
 public LocalDate getBusinessStartDate(){return businessStartDate;} public void setBusinessStartDate(LocalDate v){businessStartDate=v;}
 public BigDecimal getAnnualTurnover(){return annualTurnover;} public void setAnnualTurnover(BigDecimal v){annualTurnover=v;}
 public boolean getResident(){return resident;} public void setResident(boolean v){resident=v;}
 @Override public String getId(){return getPartyId();}
 @Override public String getDisplayName(){return super.getDisplayName();}
 @Override public void validate() throws ValidationException {
 super.validate(); if(nationalId==null||!nationalId.matches("[0-9]{16}")) throw new ValidationException("National ID must be exactly 16 digits."); if(annualTurnover==null||annualTurnover.signum()<0) throw new ValidationException("Turnover cannot be negative."); if(businessStartDate==null||!businessStartDate.isBefore(getRegistrationDate())) throw new ValidationException("Business start date must precede registration.");
 }
 @Override public void printDetails(){System.out.println(getSummary());}
 @Override public String getSummary(){return getDisplayName()+" [ID="+getId()+"]";}
}

