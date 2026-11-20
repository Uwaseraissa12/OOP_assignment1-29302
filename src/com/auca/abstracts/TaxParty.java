package com.auca.abstracts;
import com.auca.exceptions.ValidationException;
import com.auca.interfaces.*;
import java.time.LocalDate;
public abstract class TaxParty implements Identifiable, Validatable {
 private String partyId, firstName, lastName, email, phoneNumber;
 private LocalDate dateOfBirth;
 public TaxParty() {}
 public TaxParty(String partyId, String firstName, String lastName, String email, String phoneNumber, LocalDate dateOfBirth) {
  this.partyId=partyId; this.firstName=firstName; this.lastName=lastName; this.email=email;
  this.phoneNumber=phoneNumber; this.dateOfBirth=dateOfBirth;
 }
 public String getPartyId(){
    return partyId;
}
 public void setPartyId(String v){
    partyId=v;
}
 public String getFirstName(){
    return firstName;
}
 public void setFirstName(String v){
    firstName=v;
}
 public String getLastName(){
    return lastName;
}
 public void setLastName(String v){
    lastName=v;
}
 public String getEmail(){
    return email;
}
 public void setEmail(String v){
    email=v;
}
 public String getPhoneNumber(){
    return phoneNumber;
}
 public void setPhoneNumber(String v){
    phoneNumber=v;
}
 public LocalDate getDateOfBirth(){
    return dateOfBirth;
}
 public void setDateOfBirth(LocalDate v){
    dateOfBirth=v;
}
 public String getId(){
    return partyId;
}
 public String getDisplayName(){
    return firstName+" "+lastName;
}
 public boolean isActive(){
    return true;
}
 public void validate() throws ValidationException {
  if(blank(partyId)||blank(firstName)||firstName.trim().length()<2||blank(lastName)||lastName.trim().length()<2) throw new ValidationException("Party ID and names are required; names need at least 2 characters.");
  if(blank(email)||!email.matches("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")||email.length()>120) throw new ValidationException("Enter a valid email (maximum 120 characters).");
  if(blank(phoneNumber)||!phoneNumber.matches("\\+?[0-9]{10,15}")) throw new ValidationException("Phone must contain 10-15 digits, optionally starting with +.");
  if(dateOfBirth==null||!dateOfBirth.isBefore(LocalDate.now())) throw new ValidationException("Date of birth must be a valid past date.");
 }
 protected boolean blank(String s){
    return s==null||s.trim().isEmpty();
}
 public void printDetails(){
    System.out.println(getSummary());
}
 public String getSummary(){
    return getDisplayName()+" [ID="+partyId+"]";
}
}
