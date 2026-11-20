package com.auca.taxdeclaration;
import java.time.LocalDate;
import java.math.BigDecimal;
import com.auca.interfaces.*;
import com.auca.exceptions.ValidationException;

import com.auca.taxobligation.TaxObligation;
public class TaxDeclaration implements Identifiable, Validatable {
 private String declarationId;
 private TaxObligation obligation;
 private LocalDate filingDate;
 private BigDecimal taxableAmount;
 private BigDecimal declaredTax;
 private String status;
 public TaxDeclaration(){}
 public String getDeclarationId(){return declarationId;} public void setDeclarationId(String v){declarationId=v;}
 public TaxObligation getObligation(){return obligation;} public void setObligation(TaxObligation v){obligation=v;}
 public LocalDate getFilingDate(){return filingDate;} public void setFilingDate(LocalDate v){filingDate=v;}
 public BigDecimal getTaxableAmount(){return taxableAmount;} public void setTaxableAmount(BigDecimal v){taxableAmount=v;}
 public BigDecimal getDeclaredTax(){return declaredTax;} public void setDeclaredTax(BigDecimal v){declaredTax=v;}
 public String getStatus(){return status;} public void setStatus(String v){status=v;}
 @Override public String getId(){return declarationId;}
 @Override public String getDisplayName(){return "Declaration "+declarationId;} @Override public boolean isActive(){return !"REJECTED".equals(status);}
 @Override public void validate() throws ValidationException {
 if(declarationId==null||declarationId.trim().isEmpty()) throw new ValidationException("Declaration ID is required.");
 if(obligation==null) throw new ValidationException("Obligation reference is required.");
 if(filingDate==null||filingDate.isAfter(java.time.LocalDate.now())) throw new ValidationException("Filing date must be a real date not in the future.");
 if(taxableAmount==null||taxableAmount.signum()<0||declaredTax==null||declaredTax.signum()<0) throw new ValidationException("Taxable amount and declared tax cannot be negative.");
 if(!"FILED".equals(status)&&!"LATE".equals(status)&&!"ASSESSED".equals(status)&&!"REJECTED".equals(status)) throw new ValidationException("Invalid declaration status.");
 }
 @Override public void printDetails(){System.out.println(getSummary());}
 @Override public String getSummary(){return getDisplayName()+" [ID="+getId()+"]";}
}

