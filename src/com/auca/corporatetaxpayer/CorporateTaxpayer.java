package com.auca.corporatetaxpayer;
import java.time.LocalDate;
import java.math.BigDecimal;
import com.auca.interfaces.*;
import com.auca.exceptions.ValidationException;
import com.auca.abstracts.RegisteredTaxpayer;

public class CorporateTaxpayer extends RegisteredTaxpayer  {
 private String companyRegistrationNo;
 private String legalName;
 private LocalDate incorporationDate;
 private String businessSector;
 private String authorizedRepresentative;
 private BigDecimal annualTurnover;
 public CorporateTaxpayer(){}
 public CorporateTaxpayer(String partyId, String firstName, String lastName, String email, String phoneNumber, LocalDate dateOfBirth,
                          String tin, LocalDate registrationDate, String taxpayerStatus, String district, String sector, String taxOffice,
                          String companyRegistrationNo, String legalName, LocalDate incorporationDate, String businessSector,
                          String authorizedRepresentative, BigDecimal annualTurnover) {
  super(partyId, firstName, lastName, email, phoneNumber, dateOfBirth, tin, registrationDate, taxpayerStatus, district, sector, taxOffice);
  this.companyRegistrationNo=companyRegistrationNo; this.legalName=legalName; this.incorporationDate=incorporationDate;
  this.businessSector=businessSector; this.authorizedRepresentative=authorizedRepresentative; this.annualTurnover=annualTurnover;
 }
 public String getCompanyRegistrationNo(){return companyRegistrationNo;} public void setCompanyRegistrationNo(String v){companyRegistrationNo=v;}
 public String getLegalName(){return legalName;} public void setLegalName(String v){legalName=v;}
 public LocalDate getIncorporationDate(){return incorporationDate;} public void setIncorporationDate(LocalDate v){incorporationDate=v;}
 public String getBusinessSector(){return businessSector;} public void setBusinessSector(String v){businessSector=v;}
 public String getAuthorizedRepresentative(){return authorizedRepresentative;} public void setAuthorizedRepresentative(String v){authorizedRepresentative=v;}
 public BigDecimal getAnnualTurnover(){return annualTurnover;} public void setAnnualTurnover(BigDecimal v){annualTurnover=v;}
 @Override public String getId(){return getPartyId();}
 @Override public String getDisplayName(){return super.getDisplayName();}
 @Override public void validate() throws ValidationException {
 super.validate(); if(companyRegistrationNo==null||!companyRegistrationNo.matches("[A-Z0-9-]{5,30}")) throw new ValidationException("Company number must be 5-30 uppercase letters, digits or hyphens."); if(legalName==null||legalName.trim().length()<3||legalName.length()>120) throw new ValidationException("Legal name must be 3-120 characters."); if(annualTurnover==null||annualTurnover.signum()<0) throw new ValidationException("Turnover cannot be negative."); if(incorporationDate==null||!incorporationDate.isBefore(getRegistrationDate())) throw new ValidationException("Incorporation must precede registration.");
 }
 @Override public void printDetails(){System.out.println(getSummary());}
 @Override public String getSummary(){return getDisplayName()+" [ID="+getId()+"]";}
}

