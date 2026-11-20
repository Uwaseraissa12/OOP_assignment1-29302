package com.auca.taxrefund;
import java.time.LocalDate;
import java.math.BigDecimal;
import com.auca.interfaces.*;
import com.auca.exceptions.ValidationException;

import com.auca.abstracts.RegisteredTaxpayer;
import com.auca.taxassessment.TaxAssessment;
public class TaxRefund implements Identifiable, Validatable {
 private String refundId;
 private RegisteredTaxpayer taxpayer;
 private TaxAssessment assessment;
 private BigDecimal requestedAmount;
 private LocalDate requestDate;
 private String status;
 public TaxRefund(){}
 public String getRefundId(){return refundId;} public void setRefundId(String v){refundId=v;}
 public RegisteredTaxpayer getTaxpayer(){return taxpayer;} public void setTaxpayer(RegisteredTaxpayer v){taxpayer=v;}
 public TaxAssessment getAssessment(){return assessment;} public void setAssessment(TaxAssessment v){assessment=v;}
 public BigDecimal getRequestedAmount(){return requestedAmount;} public void setRequestedAmount(BigDecimal v){requestedAmount=v;}
 public LocalDate getRequestDate(){return requestDate;} public void setRequestDate(LocalDate v){requestDate=v;}
 public String getStatus(){return status;} public void setStatus(String v){status=v;}
 @Override public String getId(){return refundId;}
 @Override public String getDisplayName(){return "Refund "+refundId;} @Override public boolean isActive(){return "PENDING".equals(status);}
 @Override public void validate() throws ValidationException {
 if(refundId==null||refundId.trim().isEmpty()) throw new ValidationException("Refund ID is required.");
 if(taxpayer==null||assessment==null) throw new ValidationException("Refund taxpayer and assessment references are required.");
 if(requestedAmount==null||requestedAmount.signum()<=0) throw new com.auca.exceptions.OverpaymentException("Refund amount must be greater than zero.");
 if(requestDate==null||requestDate.isAfter(java.time.LocalDate.now())) throw new ValidationException("Refund request date cannot be in the future.");
 if(!"PENDING".equals(status)&&!"APPROVED".equals(status)&&!"REJECTED".equals(status)&&!"CANCELLED".equals(status)) throw new ValidationException("Invalid refund status.");
 }
 @Override public void printDetails(){System.out.println(getSummary());}
 @Override public String getSummary(){return getDisplayName()+" [ID="+getId()+"]";}
}

