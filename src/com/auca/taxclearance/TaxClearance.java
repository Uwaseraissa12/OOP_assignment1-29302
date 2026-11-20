package com.auca.taxclearance;
import java.time.LocalDate;
import java.math.BigDecimal;
import com.auca.interfaces.*;
import com.auca.exceptions.ValidationException;

import com.auca.abstracts.RegisteredTaxpayer;
import com.auca.revenueofficer.RevenueOfficer;
public class TaxClearance implements Identifiable, Validatable {
 private String clearanceId;
 private RegisteredTaxpayer taxpayer;
 private LocalDate requestDate;
 private LocalDate expiryDate;
 private String status;
 private RevenueOfficer approvedBy;
 public TaxClearance(){}
 public String getClearanceId(){return clearanceId;} public void setClearanceId(String v){clearanceId=v;}
 public RegisteredTaxpayer getTaxpayer(){return taxpayer;} public void setTaxpayer(RegisteredTaxpayer v){taxpayer=v;}
 public LocalDate getRequestDate(){return requestDate;} public void setRequestDate(LocalDate v){requestDate=v;}
 public LocalDate getExpiryDate(){return expiryDate;} public void setExpiryDate(LocalDate v){expiryDate=v;}
 public String getStatus(){return status;} public void setStatus(String v){status=v;}
 public RevenueOfficer getApprovedBy(){return approvedBy;} public void setApprovedBy(RevenueOfficer v){approvedBy=v;}
 @Override public String getId(){return clearanceId;}
 @Override public String getDisplayName(){return "Clearance "+clearanceId;} @Override public boolean isActive(){return "APPROVED".equals(status);}
 @Override public void validate() throws ValidationException {
 if(clearanceId==null||clearanceId.trim().isEmpty()) throw new ValidationException("Clearance ID is required.");
 if(taxpayer==null) throw new ValidationException("Taxpayer reference is required.");
 if(requestDate==null||expiryDate==null||!expiryDate.isAfter(requestDate)) throw new ValidationException("Expiry date must be after request date.");
 if(!"APPROVED".equals(status)&&!"REJECTED".equals(status)&&!"PENDING".equals(status)) throw new ValidationException("Invalid clearance status.");
 if("APPROVED".equals(status)&&(approvedBy==null||!approvedBy.isActive())) throw new com.auca.exceptions.UnauthorizedApprovalException("Approved clearance requires an active RevenueOfficer.");
 }
 @Override public void printDetails(){System.out.println(getSummary());}
 @Override public String getSummary(){return getDisplayName()+" [ID="+getId()+"]";}
}

