package com.auca.revenueofficer;
import java.time.LocalDate;
import java.math.BigDecimal;
import com.auca.interfaces.*;
import com.auca.exceptions.ValidationException;
import com.auca.abstracts.TaxParty;

public class RevenueOfficer extends TaxParty  {
 private String staffNumber;
 private String department;
 private String role;
 private LocalDate hireDate;
 private BigDecimal approvalLimit;
 private boolean active;
 public RevenueOfficer(){}
 public RevenueOfficer(String partyId, String firstName, String lastName, String email, String phoneNumber, LocalDate dateOfBirth,
                       String staffNumber, String department, String role, LocalDate hireDate, BigDecimal approvalLimit, boolean active) {
  super(partyId, firstName, lastName, email, phoneNumber, dateOfBirth);
  this.staffNumber=staffNumber; this.department=department; this.role=role; this.hireDate=hireDate;
  this.approvalLimit=approvalLimit; this.active=active;
 }
 public String getStaffNumber(){return staffNumber;} public void setStaffNumber(String v){staffNumber=v;}
 public String getDepartment(){return department;} public void setDepartment(String v){department=v;}
 public String getRole(){return role;} public void setRole(String v){role=v;}
 public LocalDate getHireDate(){return hireDate;} public void setHireDate(LocalDate v){hireDate=v;}
 public BigDecimal getApprovalLimit(){return approvalLimit;} public void setApprovalLimit(BigDecimal v){approvalLimit=v;}
 public boolean getActive(){return active;} public void setActive(boolean v){active=v;}
 @Override public String getId(){return getPartyId();}
 @Override public String getDisplayName(){return getFirstName()+" "+getLastName()+" (Officer)";} @Override public boolean isActive(){return active;}
 @Override public void validate() throws ValidationException {
 super.validate(); if(staffNumber==null||staffNumber.trim().isEmpty()||department==null||role==null||hireDate==null||approvalLimit==null||approvalLimit.signum()<0) throw new ValidationException("Complete officer details and non-negative approval limit.");
 }
 @Override public void printDetails(){System.out.println(getSummary());}
 @Override public String getSummary(){return getDisplayName()+" [ID="+getId()+"]";}
}

