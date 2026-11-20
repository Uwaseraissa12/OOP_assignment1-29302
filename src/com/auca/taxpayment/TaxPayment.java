package com.auca.taxpayment;
import java.time.LocalDate;
import java.math.BigDecimal;
import com.auca.interfaces.*;
import com.auca.exceptions.ValidationException;

import com.auca.taxassessment.TaxAssessment;
public class TaxPayment implements Identifiable, Validatable {
 private String paymentId;
 private TaxAssessment assessment;
 private String paymentReference;
 private BigDecimal amount;
 private LocalDate paymentDate;
 private String paymentMethod;
 public TaxPayment(){}
 public String getPaymentId(){return paymentId;} public void setPaymentId(String v){paymentId=v;}
 public TaxAssessment getAssessment(){return assessment;} public void setAssessment(TaxAssessment v){assessment=v;}
 public String getPaymentReference(){return paymentReference;} public void setPaymentReference(String v){paymentReference=v;}
 public BigDecimal getAmount(){return amount;} public void setAmount(BigDecimal v){amount=v;}
 public LocalDate getPaymentDate(){return paymentDate;} public void setPaymentDate(LocalDate v){paymentDate=v;}
 public String getPaymentMethod(){return paymentMethod;} public void setPaymentMethod(String v){paymentMethod=v;}
 @Override public String getId(){return paymentId;}
 @Override public String getDisplayName(){return "Payment "+paymentId;} @Override public boolean isActive(){return true;}
 @Override public void validate() throws ValidationException {
 if(paymentId==null||paymentId.trim().isEmpty()) throw new ValidationException("Payment ID is required.");
 if(assessment==null||!"CONFIRMED".equals(assessment.getStatus())) throw new com.auca.exceptions.InvalidStatusTransitionException("Payments require a CONFIRMED assessment.");
 if(paymentReference==null||paymentReference.trim().isEmpty()) throw new ValidationException("Payment reference is required.");
 if(amount==null||amount.signum()<=0) throw new com.auca.exceptions.OverpaymentException("Payment amount must be greater than zero.");
 if(paymentDate==null||paymentDate.isAfter(java.time.LocalDate.now())) throw new ValidationException("Payment date cannot be in the future.");
 if(paymentMethod==null||paymentMethod.trim().isEmpty()) throw new ValidationException("Payment method is required.");
 }
 @Override public void printDetails(){System.out.println(getSummary());}
 @Override public String getSummary(){return getDisplayName()+" [ID="+getId()+"]";}
}

