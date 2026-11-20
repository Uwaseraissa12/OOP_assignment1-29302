package com.auca.abstracts;
import com.auca.exceptions.ValidationException;
import java.time.LocalDate;
public abstract class RegisteredTaxpayer extends TaxParty {
 private String tin, taxpayerStatus, district, sector, taxOffice;
 private LocalDate registrationDate;
 public RegisteredTaxpayer(){}
 public RegisteredTaxpayer(String partyId, String firstName, String lastName, String email, String phoneNumber, LocalDate dateOfBirth,
                           String tin, LocalDate registrationDate, String taxpayerStatus, String district, String sector, String taxOffice) {
  super(partyId, firstName, lastName, email, phoneNumber, dateOfBirth);
  this.tin=tin; this.registrationDate=registrationDate; this.taxpayerStatus=taxpayerStatus;
  this.district=district; this.sector=sector; this.taxOffice=taxOffice;
 }
 public String getTin(){
    return tin;
} 
 public void setTin(String v){
    tin=v;
}
 public String getTaxpayerStatus(){
    return taxpayerStatus;
}
 public void setTaxpayerStatus(String v){
    taxpayerStatus=v;
}
 public String getDistrict(){
    return district;
}
  public void setDistrict(String v){
    district=v;
}
 public String getSector(){
    return sector;
}
 public void setSector(String v){
    sector=v;
}
 public String getTaxOffice(){
    return taxOffice;
}
 public void setTaxOffice(String v){
    taxOffice=v;
}
 public LocalDate getRegistrationDate(){
    return registrationDate;
}
 public void setRegistrationDate(LocalDate v){
    registrationDate=v;
}
 @Override public boolean isActive(){
    return "ACTIVE".equalsIgnoreCase(taxpayerStatus);
}
 @Override public void validate() throws ValidationException {
  super.validate();
  if(tin==null||!tin.matches("[0-9]{9}")) throw new ValidationException("TIN must be exactly 9 digits.");
  if(registrationDate==null||registrationDate.isAfter(LocalDate.now())||!getDateOfBirth().isBefore(registrationDate)) throw new ValidationException("Registration date must be valid and after birth date.");
  if(!isActive()&&!"INACTIVE".equalsIgnoreCase(taxpayerStatus)) throw new ValidationException("Status must be ACTIVE or INACTIVE.");
  if(district==null||district.trim().isEmpty()||sector==null||sector.trim().isEmpty()||taxOffice==null||taxOffice.trim().isEmpty()) throw new ValidationException("District, sector and tax office are required.");
 }
 @Override public String getSummary(){
    return super.getSummary()+" | TIN="+tin+" | "+taxpayerStatus;
}
}
