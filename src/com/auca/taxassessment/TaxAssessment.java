package com.auca.taxassessment;
import java.time.LocalDate;
import java.math.BigDecimal;
import com.auca.interfaces.*;
import com.auca.exceptions.ValidationException;

import com.auca.taxdeclaration.TaxDeclaration;
public class TaxAssessment implements Identifiable, Validatable {
 private String assessmentId;
 private TaxDeclaration declaration;
 private BigDecimal assessedTax;
 private BigDecimal adjustmentAmount;
 private LocalDate assessmentDate;
 private String status;
 public TaxAssessment(){}
 public String getAssessmentId(){return assessmentId;} public void setAssessmentId(String v){assessmentId=v;}
 public TaxDeclaration getDeclaration(){return declaration;} public void setDeclaration(TaxDeclaration v){declaration=v;}
 public BigDecimal getAssessedTax(){return assessedTax;} public void setAssessedTax(BigDecimal v){assessedTax=v;}
 public BigDecimal getAdjustmentAmount(){return adjustmentAmount;} public void setAdjustmentAmount(BigDecimal v){adjustmentAmount=v;}
 public LocalDate getAssessmentDate(){return assessmentDate;} public void setAssessmentDate(LocalDate v){assessmentDate=v;}
 public String getStatus(){return status;} public void setStatus(String v){status=v;}
 @Override public String getId(){return assessmentId;}
 @Override public String getDisplayName(){return "Assessment "+assessmentId;} @Override public boolean isActive(){return !"CANCELLED".equals(status);}
 @Override public void validate() throws ValidationException {
 if(assessmentId==null||assessmentId.trim().isEmpty()) throw new ValidationException("Assessment ID is required.");
 if(declaration==null||"REJECTED".equals(declaration.getStatus())) throw new ValidationException("A non-rejected declaration is required.");
 if(assessedTax==null||adjustmentAmount==null||assessedTax.signum()<0||adjustmentAmount.signum()<0) throw new ValidationException("Assessment amounts cannot be negative.");
 if(assessedTax.add(adjustmentAmount).signum()<0) throw new ValidationException("Assessment total cannot be negative.");
 if(assessmentDate==null||assessmentDate.isAfter(java.time.LocalDate.now())) throw new ValidationException("Assessment date cannot be in the future.");
 if(!"DRAFT".equals(status)&&!"CONFIRMED".equals(status)&&!"CANCELLED".equals(status)) throw new ValidationException("Assessment status must be DRAFT, CONFIRMED or CANCELLED.");
 }
 @Override public void printDetails(){System.out.println(getSummary());}
 @Override public String getSummary(){return getDisplayName()+" [ID="+getId()+"]";}
}

