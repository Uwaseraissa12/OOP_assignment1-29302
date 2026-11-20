package com.auca.main;

import java.util.Scanner;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import com.auca.abstracts.*;
import com.auca.individualtaxpayer.IndividualTaxpayer;
import com.auca.corporatetaxpayer.CorporateTaxpayer;
import com.auca.revenueofficer.RevenueOfficer;
import com.auca.taxtype.TaxType;
import com.auca.taxobligation.TaxObligation;
import com.auca.taxdeclaration.TaxDeclaration;
import com.auca.taxassessment.TaxAssessment;
import com.auca.taxpayment.TaxPayment;
import com.auca.penalty.Penalty;
import com.auca.taxrefund.TaxRefund;
import com.auca.taxclearance.TaxClearance;
import com.auca.interfaces.*;
import com.auca.exceptions.*;

public class Main {
 private static final Scanner sc = new Scanner(System.in);
 private static final IndividualTaxpayer[] individuals = new IndividualTaxpayer[100]; private static int ni;
 private static final CorporateTaxpayer[] companies = new CorporateTaxpayer[100]; private static int nc;
 private static final RevenueOfficer[] officers = new RevenueOfficer[50]; private static int no;
 private static final TaxType[] types = new TaxType[50]; private static int nt;
 private static final TaxObligation[] obligations = new TaxObligation[300]; private static int nob;
 private static final TaxDeclaration[] declarations = new TaxDeclaration[300]; private static int nd;
 private static final TaxAssessment[] assessments = new TaxAssessment[300]; private static int na;
 private static final TaxPayment[] payments = new TaxPayment[500]; private static int np;
 private static final Penalty[] penalties = new Penalty[300]; private static int npen;
 private static final TaxRefund[] refunds = new TaxRefund[100]; private static int nr;
 private static final TaxClearance[] clearances = new TaxClearance[100]; private static int ncl;

 public static void main(String[] args) {
  System.out.println("================================================");
  System.out.println("       TAX ADMINISTRATION MANAGEMENT SYSTEM");
  System.out.println("  Educational simulation - fictional tax rules");
  System.out.println("================================================");
  while(true) {
   menu(); int c=integer("Select option: ");
   try {
    switch(c) {
     case 1: registerIndividual(); break; case 2: registerCorporate(); break; case 3: registerOfficer(); break;
     case 4: registerTaxType(); break; case 5: createObligation(); break; case 6: fileDeclaration(); break;
     case 7: assessDeclaration(); break; case 8: confirmAssessment(); break; case 9: viewAll(); break;
     case 10: historyDeclarations(); break; case 11: historyAssessments(); break; case 12: recordPayment(); break;
     case 13: recordPenalty(); break; case 14: refundWorkflow(); break; case 15: clearanceWorkflow(); break;
     case 16: search(); break; case 17: updateStatus(); break; case 18: taxpayerStatement(); break;
     case 19: balances(); break; case 20: reports(); break; case 21: polymorphism(); break;
     case 0: System.out.println("Thank you. Goodbye."); return;
     default: System.out.println("Invalid menu option.");
    }
   } catch(Exception ex) { System.out.println("Operation not completed: "+ex.getMessage()); }
   pause();
  }
 }
 private static void menu() {
  System.out.println("\n================ TAX ADMINISTRATION ================");
  System.out.println(" 1 Register Individual Taxpayer   12 Record Tax Payment");
  System.out.println(" 2 Register Corporate Taxpayer    13 Calculate / Record Penalty");
  System.out.println(" 3 Register Revenue Officer       14 Request / Approve Refund");
  System.out.println(" 4 Register Tax Type              15 Request / Issue Tax Clearance");
  System.out.println(" 5 Create Tax Obligation          16 Search Record by ID / TIN");
  System.out.println(" 6 File Tax Declaration           17 Update Eligible Record");
  System.out.println(" 7 Assess Declaration             18 View Taxpayer Statement");
  System.out.println(" 8 Confirm Assessment             19 View Outstanding Balances");
  System.out.println(" 9 View All Records               20 View Tax Reports");
  System.out.println("10 View Declaration History       21 Demonstrate Polymorphism");
  System.out.println("11 View Assessment History         0 Exit");
 }
 private static String line(String p){System.out.print(p);return sc.nextLine().trim();}
 private static int integer(String p){while(true){try{return Integer.parseInt(line(p));}catch(NumberFormatException e){System.out.println("Enter a whole number.");}}}
 private static BigDecimal money(String p){while(true){try{BigDecimal b=new BigDecimal(line(p)); if(b.scale()>2||b.signum()<0||b.compareTo(new BigDecimal("999999999999.99"))>0) throw new NumberFormatException(); return b.setScale(2,RoundingMode.HALF_UP);}catch(Exception e){System.out.println("Enter a non-negative amount with at most 2 decimal places.");}}}
 private static LocalDate date(String p){while(true){try{LocalDate d=LocalDate.parse(line(p));return d;}catch(DateTimeParseException e){System.out.println("Use a real date in YYYY-MM-DD format.");}}}
 private static boolean yes(String p){return line(p+" (YES/NO): ").equalsIgnoreCase("YES");}
 private static void pause(){System.out.println();}

 private static RegisteredTaxpayer findTaxpayer(String key) {
  for(int i=0;i<ni;i++) if(individuals[i].getTin().equals(key)||individuals[i].getPartyId().equals(key)) return individuals[i];
  for(int i=0;i<nc;i++) if(companies[i].getTin().equals(key)||companies[i].getPartyId().equals(key)) return companies[i];
  return null;
 }
 private static boolean tinUsed(String tin){return findTaxpayer(tin)!=null;}
 private static void registerIndividual() throws Exception {
  if(ni==individuals.length) throw new ValidationException("Individual taxpayer capacity reached.");
  IndividualTaxpayer t=new IndividualTaxpayer();
  for(int step=1;step<=4;) {
   if(step==1) {
    System.out.println("\n+---------------- REGISTER INDIVIDUAL TAXPAYER ----------------+");
    System.out.println("| STEP 1 OF 4 - PERSONAL DETAILS");
    System.out.println("| 01 Party ID [String, unique, 4-30]");
    t.setPartyId(wizardString("01","Party ID",t.getPartyId(),"String, unique, 4-30",4,30)); 
    t.setFirstName(wizardString("02","First name",t.getFirstName(),"String, 2-60",2,60));
    t.setLastName(wizardString("03","Last name",t.getLastName(),"String, 2-60",2,60));
    t.setEmail(wizardString("04","Email",t.getEmail(),"valid email, <=120",1,120));
    t.setPhoneNumber(wizardString("05","Phone",t.getPhoneNumber(),"10-15 digits, optional +",10,16));
    t.setDateOfBirth(wizardDate("06","Date of birth",t.getDateOfBirth(),"YYYY-MM-DD"));
   } else if(step==2) {
    System.out.println("\n| STEP 2 OF 4 - REGISTRATION DETAILS");
    t.setTin(wizardTin("07","TIN",t.getTin(),"exactly 9 digits"));
    t.setRegistrationDate(wizardDate("08","Registration date",t.getRegistrationDate(),"YYYY-MM-DD"));
    t.setTaxpayerStatus("ACTIVE");
    t.setDistrict(wizardString("09","District",t.getDistrict(),"String, required",1,60));
    t.setSector(wizardString("10","Sector",t.getSector(),"String, required",1,60));
    t.setTaxOffice(wizardString("11","Tax office",t.getTaxOffice(),"String, required",1,60));
   } else if(step==3) {
    System.out.println("\n| STEP 3 OF 4 - INDIVIDUAL DETAILS");
    t.setNationalId(wizardNationalId("12","National ID",t.getNationalId(),"exactly 16 digits"));
    t.setOccupation(wizardString("13","Occupation",t.getOccupation(),"String, 2-60",2,60));
    t.setBusinessName(wizardString("14","Business name",t.getBusinessName(),"String, optional",0,120));
    t.setBusinessStartDate(wizardDate("15","Business start date",t.getBusinessStartDate(),"YYYY-MM-DD"));
    t.setAnnualTurnover(wizardMoney("16","Annual turnover",t.getAnnualTurnover(),"BigDecimal, 0-999999999999.99"));
    t.setResident(wizardYes("17","Resident",t.getResident()));
   } else {
    System.out.println("\n| STEP 4 OF 4 - REVIEW AND SAVE");
    printIndividualReview(t);
    System.out.print("1 SAVE | 2 EDIT FIELD | 0 CANCEL: ");
    String choice=sc.nextLine().trim();
    if(choice.equals("1")) {
     try { t.validate(); individuals[ni++]=t; System.out.println("Individual taxpayer saved: "+t.getSummary()); return; }
     catch(Exception ex){System.out.println("Cannot save: "+ex.getMessage());continue;}
    }
    if(choice.equals("2")) {step=wizardEditStep();continue;}
    if(choice.equals("0")) {System.out.println("Cancelled. No record saved.");return;}
    System.out.println("Choose 1, 2 or 0.");
    continue;
   }
   if(step<4) {System.out.print("NEXT / BACK / CANCEL: ");String nav=sc.nextLine().trim().toUpperCase();
    if(nav.equals("CANCEL")){System.out.println("Cancelled. No record saved.");return;}
    if(nav.equals("BACK")){step=Math.max(1,step-1);}else{step++;}}
  }
 }
 private static void registerCorporate() throws Exception {
  if(nc==companies.length) throw new ValidationException("Corporate taxpayer capacity reached.");
  CorporateTaxpayer t=new CorporateTaxpayer();
  for(int step=1;step<=4;) {
   if(step==1){
    System.out.println("\n+---------------- REGISTER CORPORATE TAXPAYER ----------------+");
    System.out.println("| STEP 1 OF 4 - PERSONAL DETAILS");
    t.setPartyId(wizardString("01","Party ID",t.getPartyId(),"String, unique, 4-30",4,30));t.setFirstName(wizardString("02","Contact first name",t.getFirstName(),"String, 2-60",2,60));
    t.setLastName(wizardString("03","Contact last name",t.getLastName(),"String, 2-60",2,60));t.setEmail(wizardString("04","Email",t.getEmail(),"valid email, <=120",1,120));
    t.setPhoneNumber(wizardString("05","Phone",t.getPhoneNumber(),"10-15 digits, optional +",10,16));t.setDateOfBirth(wizardDate("06","Representative date of birth",t.getDateOfBirth(),"YYYY-MM-DD"));
   } else if(step==2){
    System.out.println("| STEP 2 OF 4 - REGISTRATION DETAILS");
    t.setTin(wizardTin("07","TIN",t.getTin(),"exactly 9 digits"));t.setRegistrationDate(wizardDate("08","Registration date",t.getRegistrationDate(),"YYYY-MM-DD"));t.setTaxpayerStatus("ACTIVE");
    t.setDistrict(wizardString("09","District",t.getDistrict(),"String, required",1,60));t.setSector(wizardString("10","Sector",t.getSector(),"String, required",1,60));t.setTaxOffice(wizardString("11","Tax office",t.getTaxOffice(),"String, required",1,60));
   } else if(step==3){
    System.out.println("| STEP 3 OF 4 - CORPORATE DETAILS");
    t.setCompanyRegistrationNo(wizardCompanyNo("12","Company registration number",t.getCompanyRegistrationNo()));t.setLegalName(wizardString("13","Legal name",t.getLegalName(),"String, 3-120",3,120));
    t.setIncorporationDate(wizardDate("14","Incorporation date",t.getIncorporationDate(),"YYYY-MM-DD"));t.setBusinessSector(wizardString("15","Business sector",t.getBusinessSector(),"String, required",1,60));
    t.setAuthorizedRepresentative(wizardString("16","Authorized representative",t.getAuthorizedRepresentative(),"String, required",2,60));t.setAnnualTurnover(wizardMoney("17","Annual turnover",t.getAnnualTurnover(),"BigDecimal, 0-999999999999.99"));
   } else {
    System.out.println("| STEP 4 OF 4 - REVIEW AND SAVE");
    printCorporateReview(t);System.out.print("1 SAVE | 2 EDIT FIELD | 0 CANCEL: ");String choice=sc.nextLine().trim();
    if(choice.equals("1")){t.validate();companies[nc++]=t;System.out.println("Corporate taxpayer saved: "+t.getSummary());return;}
    if(choice.equals("2")){step=wizardEditCorporate();continue;}if(choice.equals("0")){System.out.println("Cancelled. No record saved.");return;}continue;
   }
   if(step<4){System.out.print("NEXT / BACK / CANCEL: ");String nav=sc.nextLine().trim().toUpperCase();if(nav.equals("CANCEL")){System.out.println("Cancelled.");return;}if(nav.equals("BACK"))step=Math.max(1,step-1);else step++;}
  }
 }
 private static String required(String p){while(true){String s=line(p+": ");if(s.equals("0"))throw new RuntimeException("Cancelled by user; no record saved.");if(!s.isEmpty())return s;System.out.println("This field is required.");}}
 private static String wizardString(String no,String name,String old,String format,int min,int max){
  while(true){System.out.print("| "+no+" "+name+" ["+format+"] ["+(old==null?"":old)+"]: ");
   String s=sc.nextLine().trim();if(s.equalsIgnoreCase("BACK")||s.equalsIgnoreCase("CANCEL"))throw new RuntimeException("Cancelled by user; no record saved.");
   if(s.isEmpty()&&old!=null&&!old.isEmpty())return old;if(s.isEmpty()&&min==0)return "";
   if(s.length()<min||s.length()>max){System.out.println("Enter 0 to cancel or a value with length "+min+"-"+max+".");continue;}return s;}
 }
 private static LocalDate wizardDate(String no,String name,LocalDate old,String format){
  while(true){System.out.print("| "+no+" "+name+" ["+format+"] ["+(old==null?"":old)+"]: ");
   String s=sc.nextLine().trim();if(s.equalsIgnoreCase("BACK")||s.equalsIgnoreCase("CANCEL"))throw new RuntimeException("Cancelled by user; no record saved.");
   if(s.isEmpty()&&old!=null)return old;if(s.equals("0"))throw new RuntimeException("Cancelled by user; no record saved.");
   try{return LocalDate.parse(s);}catch(DateTimeParseException ex){System.out.println("Use a real YYYY-MM-DD date.");}}
 }
 private static BigDecimal wizardMoney(String no,String name,BigDecimal old,String format){
  while(true){System.out.print("| "+no+" "+name+" ["+format+"] ["+(old==null?"":old)+"]: ");
   String s=sc.nextLine().trim();if(s.equalsIgnoreCase("BACK")||s.equalsIgnoreCase("CANCEL"))throw new RuntimeException("Cancelled by user; no record saved.");
   if(s.isEmpty()&&old!=null)return old;
   try{BigDecimal b=new BigDecimal(s);if(b.scale()>2||b.signum()<0||b.compareTo(new BigDecimal("999999999999.99"))>0)throw new NumberFormatException();return b.setScale(2,RoundingMode.HALF_UP);}catch(NumberFormatException ex){System.out.println("Enter a non-negative amount with at most 2 decimal places.");}}
 }
 private static boolean wizardYes(String no,String name,boolean old){System.out.print("| "+no+" "+name+" [boolean YES/NO] ["+(old?"YES":"NO")+"]: ");String s=sc.nextLine().trim();if(s.isEmpty())return old;return s.equalsIgnoreCase("YES");}
 private static String wizardTin(String no,String name,String old,String format){
  while(true){String s=wizardString(no,name,old,format,9,9);if(!s.matches("[0-9]{9}")){System.out.println("TIN must be exactly 9 digits.");continue;}if(tinUsed(s)&&!s.equals(old)){System.out.println("TIN already exists.");continue;}return s;}
 }
 private static String wizardNationalId(String no,String name,String old,String format){
  while(true){String s=wizardString(no,name,old,format,16,16);if(!s.matches("[0-9]{16}")){System.out.println("National ID must be exactly 16 digits.");continue;}boolean used=false;for(int i=0;i<ni;i++)if(individuals[i].getNationalId()!=null&&individuals[i].getNationalId().equals(s)&&individuals[i]!=null&&!s.equals(old))used=true;if(used){System.out.println("National ID already exists.");continue;}return s;}
 }
 private static int wizardEditStep(){System.out.print("Enter field number 1-18 to edit: ");String s=sc.nextLine().trim();try{int n=Integer.parseInt(s);if(n<=6)return 1;if(n<=11)return 2;return 3;}catch(NumberFormatException ex){return 4;}}
 private static String wizardCompanyNo(String no,String name,String old){
  while(true){String s=wizardString(no,name,old,"uppercase letters/digits/hyphens, 5-30",5,30);if(!s.matches("[A-Z0-9-]{5,30}")){System.out.println("Use 5-30 uppercase letters, digits or hyphens.");continue;}
   boolean used=false;for(int i=0;i<nc;i++)if(companies[i].getCompanyRegistrationNo()!=null&&companies[i].getCompanyRegistrationNo().equals(s))used=true;if(used){System.out.println("Company registration number already exists.");continue;}return s;}
 }
 private static int wizardEditCorporate(){System.out.print("Enter field number 1-18 to edit: ");try{int n=Integer.parseInt(sc.nextLine().trim());if(n<=6)return 1;if(n<=12)return 2;return 3;}catch(NumberFormatException ex){return 4;}}
 private static void printCorporateReview(CorporateTaxpayer t){
  System.out.println("01 Party ID: "+t.getPartyId());System.out.println("02 Contact first name: "+t.getFirstName());System.out.println("03 Contact last name: "+t.getLastName());
  System.out.println("04 Email: "+t.getEmail());System.out.println("05 Phone: "+t.getPhoneNumber());System.out.println("06 Representative date of birth: "+t.getDateOfBirth());
  System.out.println("07 TIN: "+t.getTin());System.out.println("08 Registration date: "+t.getRegistrationDate());System.out.println("09 Taxpayer status: "+t.getTaxpayerStatus());
  System.out.println("10 District: "+t.getDistrict());System.out.println("11 Sector: "+t.getSector());System.out.println("12 Tax office: "+t.getTaxOffice());
  System.out.println("13 Company registration number: "+t.getCompanyRegistrationNo());System.out.println("14 Legal name: "+t.getLegalName());
  System.out.println("15 Incorporation date: "+t.getIncorporationDate());System.out.println("16 Business sector: "+t.getBusinessSector());
  System.out.println("17 Authorized representative: "+t.getAuthorizedRepresentative());System.out.println("18 Annual turnover: "+t.getAnnualTurnover());
 }
 private static void printIndividualReview(IndividualTaxpayer t){
  System.out.println("01 Party ID: "+t.getPartyId());System.out.println("02 First name: "+t.getFirstName());System.out.println("03 Last name: "+t.getLastName());
  System.out.println("04 Email: "+t.getEmail());System.out.println("05 Phone: "+t.getPhoneNumber());System.out.println("06 Date of birth: "+t.getDateOfBirth());
  System.out.println("07 TIN: "+t.getTin());System.out.println("08 Registration date: "+t.getRegistrationDate());System.out.println("09 Taxpayer status: "+t.getTaxpayerStatus());
  System.out.println("10 District: "+t.getDistrict());System.out.println("11 Sector: "+t.getSector());System.out.println("12 Tax office: "+t.getTaxOffice());
  System.out.println("13 National ID: "+t.getNationalId());System.out.println("14 Occupation: "+t.getOccupation());System.out.println("15 Business name: "+t.getBusinessName());
  System.out.println("16 Business start date: "+t.getBusinessStartDate());System.out.println("17 Annual turnover: "+t.getAnnualTurnover());System.out.println("18 Resident: "+(t.getResident()?"YES":"NO"));
 }
 private static String uniqueTin(){while(true){String s=required("TIN (exactly 9 digits)");if(!s.matches("[0-9]{9}")){System.out.println("TIN must be 9 digits.");continue;}if(tinUsed(s)){System.out.println("TIN already exists.");continue;}return s;}}
 private static String uniqueNationalId(){while(true){String s=required("National ID (16 digits)");if(!s.matches("[0-9]{16}")){System.out.println("National ID must be 16 digits.");continue;}boolean used=false;for(int i=0;i<ni;i++)used|=individuals[i].getNationalId().equals(s);if(!used)return s;System.out.println("National ID already exists.");}}
 private static String wizardStaffNumber(String fieldNo,String name,String old){
  while(true){String s=wizardString(fieldNo,name,old,"String, unique, required",1,30);boolean used=false;for(int i=0;i<no;i++)if(officers[i].getStaffNumber()!=null&&officers[i].getStaffNumber().equals(s))used=true;if(used){System.out.println("Staff number already exists.");continue;}return s;}
 }
 private static void printOfficerReview(RevenueOfficer o){
  System.out.println("01 Party ID: "+o.getPartyId());System.out.println("02 First name: "+o.getFirstName());System.out.println("03 Last name: "+o.getLastName());
  System.out.println("04 Email: "+o.getEmail());System.out.println("05 Phone: "+o.getPhoneNumber());System.out.println("06 Date of birth: "+o.getDateOfBirth());
  System.out.println("07 Staff number: "+o.getStaffNumber());System.out.println("08 Department: "+o.getDepartment());System.out.println("09 Role: "+o.getRole());
  System.out.println("10 Hire date: "+o.getHireDate());System.out.println("11 Approval limit: "+o.getApprovalLimit());System.out.println("12 Active: "+(o.isActive()?"YES":"NO"));
 }
 private static void registerOfficer() throws Exception {
  if(no==officers.length)throw new ValidationException("Officer capacity reached.");
  RevenueOfficer o=new RevenueOfficer();
  for(int step=1;step<=4;) {
   if(step==1){System.out.println("\n+---------------- REGISTER REVENUE OFFICER ----------------+");System.out.println("| STEP 1 OF 4 - PERSONAL DETAILS");
    o.setPartyId(wizardString("01","Party ID",o.getPartyId(),"String, unique, 4-30",4,30));o.setFirstName(wizardString("02","First name",o.getFirstName(),"String, 2-60",2,60));
    o.setLastName(wizardString("03","Last name",o.getLastName(),"String, 2-60",2,60));o.setEmail(wizardString("04","Email",o.getEmail(),"valid email, <=120",1,120));
    o.setPhoneNumber(wizardString("05","Phone",o.getPhoneNumber(),"10-15 digits, optional +",10,16));o.setDateOfBirth(wizardDate("06","Date of birth",o.getDateOfBirth(),"YYYY-MM-DD"));
   } else if(step==2){System.out.println("| STEP 2 OF 4 - OFFICER DETAILS");
    o.setStaffNumber(wizardStaffNumber("07","Staff number",o.getStaffNumber()));o.setDepartment(wizardString("08","Department",o.getDepartment(),"String, required",1,60));
    o.setRole(wizardString("09","Role",o.getRole(),"String, required",1,60));o.setHireDate(wizardDate("10","Hire date",o.getHireDate(),"YYYY-MM-DD"));
    o.setApprovalLimit(wizardMoney("11","Approval limit",o.getApprovalLimit(),"BigDecimal, 0-999999999999.99"));o.setActive(wizardYes("12","Active",o.isActive()));
   } else if(step==3){System.out.println("| STEP 3 OF 4 - VALIDATION");o.validate();}
   else {System.out.println("| STEP 4 OF 4 - REVIEW AND SAVE");printOfficerReview(o);System.out.print("1 SAVE | 2 EDIT FIELD | 0 CANCEL: ");String c=sc.nextLine().trim();
    if(c.equals("1")){o.validate();officers[no++]=o;System.out.println("Officer saved: "+o.getDisplayName());return;}if(c.equals("2")){step=1;continue;}if(c.equals("0")){System.out.println("Cancelled.");return;}continue;}
   System.out.print("NEXT / BACK / CANCEL: ");String nav=sc.nextLine().trim().toUpperCase();if(nav.equals("CANCEL")){System.out.println("Cancelled.");return;}if(nav.equals("BACK"))step=Math.max(1,step-1);else step++;
  }
 }
 private static void registerTaxType() throws Exception {
  if(nt==types.length)throw new ValidationException("Tax type capacity reached.");
  TaxType t=new TaxType();t.setTaxTypeId(required("Tax type ID"));for(int i=0;i<nt;i++)if(types[i].getTaxTypeId().equals(t.getTaxTypeId()))throw new DuplicateRecordException("Tax type ID already exists.");
  t.setTaxCode(required("Tax code"));for(int i=0;i<nt;i++)if(types[i].getTaxCode().equalsIgnoreCase(t.getTaxCode()))throw new DuplicateRecordException("Tax code already exists.");
  t.setTaxName(required("Tax name"));t.setRate(money("Fictional rate percentage (0-100): "));
  t.setFilingFrequency(required("Frequency MONTHLY / QUARTERLY / ANNUAL").toUpperCase());t.setActive(true);t.validate();types[nt++]=t;System.out.println("Tax type saved.");
 }
 private static void createObligation() throws Exception {
  if(nob==obligations.length)throw new ValidationException("Obligation capacity reached.");
  RegisteredTaxpayer p=chooseTaxpayer();if(!p.isActive())throw new ValidationException("Taxpayer is inactive.");
  TaxType type=chooseType();if(!type.isActive())throw new ValidationException("Tax type inactive.");
  LocalDate start=date("Period start: "),end=date("Period end: "),due=date("Due date: ");
  if(start.isAfter(end)||due.isBefore(end))throw new InvalidTaxPeriodException("Period start must be <= end and due date cannot precede period end.");
  for(int i=0;i<nob;i++)if(obligations[i].getTaxpayer()==p&&obligations[i].getTaxType()==type&&overlap(start,end,obligations[i].getPeriodStart(),obligations[i].getPeriodEnd()))throw new DuplicateRecordException("Overlapping obligation exists.");
  TaxObligation o=new TaxObligation();o.setObligationId(required("Obligation ID"));for(int i=0;i<nob;i++)if(obligations[i].getObligationId().equals(o.getObligationId()))throw new DuplicateRecordException("Obligation ID already exists.");
  o.setTaxpayer(p);o.setTaxType(type);o.setPeriodStart(start);o.setPeriodEnd(end);o.setDueDate(due);o.validate();obligations[nob++]=o;System.out.println("Obligation saved.");
 }
 private static boolean overlap(LocalDate a,LocalDate b,LocalDate c,LocalDate d){return !a.isAfter(d)&&!c.isAfter(b);}
 private static RegisteredTaxpayer chooseTaxpayer(){String s=required("Enter taxpayer TIN or Party ID");RegisteredTaxpayer p=findTaxpayer(s);if(p==null)throw new RuntimeException("Taxpayer not found. Register taxpayer first.");return p;}
 private static TaxType chooseType(){String s=required("Enter tax type ID");for(int i=0;i<nt;i++)if(types[i].getTaxTypeId().equals(s))return types[i];throw new RuntimeException("Tax type not found.");}
 private static TaxObligation chooseObligation(){String s=required("Enter obligation ID");for(int i=0;i<nob;i++)if(obligations[i].getObligationId().equals(s))return obligations[i];throw new RuntimeException("Obligation not found.");}
 private static void fileDeclaration() throws Exception {
  TaxObligation o=chooseObligation();for(int i=0;i<nd;i++)if(declarations[i].getObligation()==o&&!"REJECTED".equals(declarations[i].getStatus()))throw new DuplicateRecordException("An active declaration already exists for this obligation.");
  TaxDeclaration d=new TaxDeclaration();d.setDeclarationId(required("Declaration ID"));for(int i=0;i<nd;i++)if(declarations[i].getDeclarationId().equals(d.getDeclarationId()))throw new DuplicateRecordException("Declaration ID already exists.");
  d.setObligation(o);d.setFilingDate(date("Filing date: "));
  if(d.getFilingDate().isAfter(LocalDate.now()))throw new ValidationException("Filing date cannot be in the future.");
  d.setTaxableAmount(money("Taxable amount: "));BigDecimal expected=d.getTaxableAmount().multiply(o.getTaxType().getRate()).divide(new BigDecimal("100"),2,RoundingMode.HALF_UP);
  d.setDeclaredTax(money("Declared tax (expected "+expected+"): "));
  if(d.getDeclaredTax().subtract(expected).abs().compareTo(new BigDecimal("0.01"))>0)throw new InvalidTaxCalculationException("Declared tax does not match calculated tax "+expected+".");
  d.setStatus(d.getFilingDate().isAfter(o.getDueDate())?"LATE":"FILED");d.validate();declarations[nd++]=d;System.out.println("Declaration saved with status "+d.getStatus()+".");
 }
 private static TaxDeclaration chooseDeclaration(){String s=required("Enter declaration ID");for(int i=0;i<nd;i++)if(declarations[i].getDeclarationId().equals(s))return declarations[i];throw new RuntimeException("Declaration not found.");}
 private static void assessDeclaration() throws Exception {
  TaxDeclaration d=chooseDeclaration();if("REJECTED".equals(d.getStatus()))throw new InvalidStatusTransitionException("Rejected declaration cannot be assessed.");
  TaxAssessment a=new TaxAssessment();a.setAssessmentId(required("Assessment ID"));for(int i=0;i<na;i++)if(assessments[i].getAssessmentId().equals(a.getAssessmentId()))throw new DuplicateRecordException("Assessment ID exists.");
  a.setDeclaration(d);a.setAssessedTax(d.getDeclaredTax());a.setAdjustmentAmount(money("Adjustment amount (0 if none): "));if(a.getAssessedTax().add(a.getAdjustmentAmount()).signum()<0)throw new ValidationException("Assessment total cannot be negative.");
  a.setAssessmentDate(LocalDate.now());a.setStatus("DRAFT");a.validate();assessments[na++]=a;d.setStatus("ASSESSED");System.out.println("Draft assessment created.");
 }
 private static TaxAssessment chooseAssessment(){String s=required("Enter assessment ID");for(int i=0;i<na;i++)if(assessments[i].getAssessmentId().equals(s))return assessments[i];throw new RuntimeException("Assessment not found.");}
 private static void confirmAssessment() throws Exception {TaxAssessment a=chooseAssessment();if(!"DRAFT".equals(a.getStatus()))throw new InvalidStatusTransitionException("Only DRAFT assessments can be confirmed.");a.setStatus("CONFIRMED");System.out.println("Assessment confirmed. Total: "+total(a));}
 private static BigDecimal total(TaxAssessment a){return a.getAssessedTax().add(a.getAdjustmentAmount()).setScale(2,RoundingMode.HALF_UP);}
 private static BigDecimal paid(TaxAssessment a){BigDecimal x=BigDecimal.ZERO;for(int i=0;i<np;i++)if(payments[i].getAssessment()==a)x=x.add(payments[i].getAmount());return x;}
 private static BigDecimal penaltiesFor(TaxAssessment a){BigDecimal x=BigDecimal.ZERO;for(int i=0;i<npen;i++)if(a.getDeclaration().getObligation()==penalties[i].getObligation())x=x.add(penalties[i].getPenaltyAmount());return x;}
 private static BigDecimal balance(TaxAssessment a){return total(a).add(penaltiesFor(a)).subtract(paid(a));}
 private static void recordPayment() throws Exception {
  TaxAssessment a=chooseAssessment();if(!"CONFIRMED".equals(a.getStatus()))throw new InvalidStatusTransitionException("Payments require a CONFIRMED assessment.");
  String ref=required("Payment reference (unique): ");for(int i=0;i<np;i++)if(payments[i].getPaymentReference().equals(ref))throw new DuplicateRecordException("Payment reference already exists.");
  BigDecimal amount=money("Payment amount: ");if(amount.signum()==0||amount.compareTo(balance(a))>0)throw new OverpaymentException("Payment must be > 0 and <= outstanding balance "+balance(a));
  LocalDate d=date("Payment date: ");if(d.isAfter(LocalDate.now()))throw new ValidationException("Payment date cannot be in the future.");
  TaxPayment p=new TaxPayment();p.setPaymentId(required("Payment ID"));for(int i=0;i<np;i++)if(payments[i].getPaymentId().equals(p.getPaymentId()))throw new DuplicateRecordException("Payment ID already exists.");
  p.setAssessment(a);p.setPaymentReference(ref);p.setAmount(amount);p.setPaymentDate(d);p.setPaymentMethod(required("Payment method"));p.validate();payments[np++]=p;System.out.println("Payment saved. Remaining balance: "+balance(a));
 }
 private static void recordPenalty() throws Exception {
  TaxObligation o=chooseObligation();boolean late=false;for(int i=0;i<nd;i++)if(declarations[i].getObligation()==o&&"LATE".equals(declarations[i].getStatus()))late=true;
  if(!late)throw new PrerequisiteMissingException("No late declaration found for this obligation.");
  for(int i=0;i<npen;i++)if(penalties[i].getObligation()==o&&"LATE_FILING".equals(penalties[i].getPenaltyType()))throw new DuplicateRecordException("Late filing penalty already exists.");
  BigDecimal base=money("Penalty base amount: ");BigDecimal rate=new BigDecimal("5.00");BigDecimal amount=base.multiply(rate).divide(new BigDecimal("100"),2,RoundingMode.HALF_UP);
  Penalty p=new Penalty();p.setPenaltyId(required("Penalty ID"));for(int i=0;i<npen;i++)if(penalties[i].getPenaltyId().equals(p.getPenaltyId()))throw new DuplicateRecordException("Penalty ID already exists.");
  p.setObligation(o);p.setPenaltyType("LATE_FILING");p.setBaseAmount(base);p.setPenaltyRate(rate);p.setPenaltyAmount(amount);p.validate();penalties[npen++]=p;System.out.println("Illustrative 5% penalty recorded: "+amount);
 }
 private static void refundWorkflow() throws Exception {
  TaxAssessment a=chooseAssessment();BigDecimal over=paid(a).subtract(total(a).add(penaltiesFor(a)));if(over.signum()<=0)throw new OverpaymentException("No verified overpayment exists.");
  for(int i=0;i<nr;i++)if(refunds[i].getAssessment()==a&&"PENDING".equals(refunds[i].getStatus()))throw new DuplicateRecordException("Pending refund already exists.");
  TaxRefund r=new TaxRefund();r.setRefundId(required("Refund ID"));for(int i=0;i<nr;i++)if(refunds[i].getRefundId().equals(r.getRefundId()))throw new DuplicateRecordException("Refund ID already exists.");
  r.setTaxpayer(a.getDeclaration().getObligation().getTaxpayer());r.setAssessment(a);r.setRequestedAmount(money("Refund amount (maximum "+over+"): "));
  if(r.getRequestedAmount().signum()==0||r.getRequestedAmount().compareTo(over)>0)throw new OverpaymentException("Refund exceeds verified overpayment.");
  r.setRequestDate(LocalDate.now());
  if(yes("Approve this refund now")){
   RevenueOfficer o=chooseOfficer();if(!o.isActive()||r.getRequestedAmount().compareTo(o.getApprovalLimit())>0)throw new UnauthorizedApprovalException("Officer inactive or approval limit insufficient.");r.setStatus("APPROVED");
  }else r.setStatus("PENDING");
  r.validate();refunds[nr++]=r;System.out.println("Refund "+("APPROVED".equals(r.getStatus())?"approved":"saved as PENDING")+": "+r.getRefundId());
 }
 private static RevenueOfficer chooseOfficer(){String s=required("Enter officer staff number");for(int i=0;i<no;i++)if(officers[i].getStaffNumber().equals(s))return officers[i];throw new RuntimeException("Officer not found.");}
 private static void clearanceWorkflow() throws Exception {
  RegisteredTaxpayer p=chooseTaxpayer();boolean overdue=false,unresolved=false;
  for(int i=0;i<nob;i++)if(obligations[i].getTaxpayer()==p&&obligations[i].getDueDate().isBefore(LocalDate.now())){boolean settled=false;for(int j=0;j<na;j++)if(assessments[j].getDeclaration().getObligation()==obligations[i]&&balance(assessments[j]).signum()<=0)settled=true;if(!settled)overdue=true;}
  for(int i=0;i<nd;i++)if(declarations[i].getObligation().getTaxpayer()==p&&!"ASSESSED".equals(declarations[i].getStatus())&&!"REJECTED".equals(declarations[i].getStatus()))unresolved=true;
  LocalDate req=LocalDate.now(),exp=date("Clearance expiry date: ");if(!exp.isAfter(req))throw new ValidationException("Expiry date must be after request date.");
  TaxClearance c=new TaxClearance();c.setClearanceId(required("Clearance ID"));for(int i=0;i<ncl;i++)if(clearances[i].getClearanceId().equals(c.getClearanceId()))throw new DuplicateRecordException("Clearance ID already exists.");
  c.setTaxpayer(p);c.setRequestDate(req);c.setExpiryDate(exp);
  if(!p.isActive()||overdue||unresolved){c.setStatus("REJECTED");c.setApprovedBy(null);clearances[ncl++]=c;System.out.println("Clearance rejected: taxpayer inactive, overdue unpaid obligation or unresolved declaration.");}
  else {RevenueOfficer o=chooseOfficer();if(!o.isActive())throw new UnauthorizedApprovalException("Approving officer must be active.");c.setStatus("APPROVED");c.setApprovedBy(o);clearances[ncl++]=c;System.out.println("Tax clearance APPROVED.");}
 }
 private static void viewAll() {
  System.out.println("\n--- INDIVIDUAL TAXPAYERS ---");for(int i=0;i<ni;i++)individuals[i].printDetails();
  System.out.println("--- CORPORATE TAXPAYERS ---");for(int i=0;i<nc;i++)companies[i].printDetails();
  System.out.println("--- OFFICERS ---");for(int i=0;i<no;i++)officers[i].printDetails();
  System.out.println("--- TAX TYPES ---");for(int i=0;i<nt;i++)types[i].printDetails();
  System.out.println("--- OBLIGATIONS ---");for(int i=0;i<nob;i++)obligations[i].printDetails();
  System.out.println("--- DECLARATIONS ---");for(int i=0;i<nd;i++)declarations[i].printDetails();
  System.out.println("--- ASSESSMENTS ---");for(int i=0;i<na;i++)assessments[i].printDetails();
  System.out.println("--- PAYMENTS ---");for(int i=0;i<np;i++)payments[i].printDetails();
  System.out.println("--- PENALTIES ---");for(int i=0;i<npen;i++)penalties[i].printDetails();
  System.out.println("--- REFUNDS ---");for(int i=0;i<nr;i++)refunds[i].printDetails();
  System.out.println("--- CLEARANCES ---");for(int i=0;i<ncl;i++)clearances[i].printDetails();
 }
 private static void historyDeclarations(){for(int i=0;i<nd;i++)System.out.println(declarations[i].getSummary()+" | "+declarations[i].getStatus()+" | filed "+declarations[i].getFilingDate());}
 private static void historyAssessments(){for(int i=0;i<na;i++)System.out.println(assessments[i].getSummary()+" | "+assessments[i].getStatus()+" | total "+total(assessments[i]));}
 private static void search(){
  String id=required("Enter ID or TIN: ");RegisteredTaxpayer p=findTaxpayer(id);if(p!=null){p.printDetails();return;}
  for(int i=0;i<nt;i++)if(types[i].getTaxTypeId().equals(id)){types[i].printDetails();return;}
  for(int i=0;i<nob;i++)if(obligations[i].getObligationId().equals(id)){obligations[i].printDetails();return;}
  for(int i=0;i<nd;i++)if(declarations[i].getDeclarationId().equals(id)){declarations[i].printDetails();return;}
  for(int i=0;i<na;i++)if(assessments[i].getAssessmentId().equals(id)){assessments[i].printDetails();return;}
  System.out.println("No matching record.");
 }
 private static void updateStatus(){
  String id=required("Taxpayer TIN: ");RegisteredTaxpayer p=findTaxpayer(id);if(p==null){System.out.println("Taxpayer not found.");return;}
  String s=required("New status ACTIVE or INACTIVE: ").toUpperCase();if(!s.equals("ACTIVE")&&!s.equals("INACTIVE")){System.out.println("Invalid status.");return;}p.setTaxpayerStatus(s);System.out.println("Taxpayer status updated.");
 }
 private static void taxpayerStatement(){
  RegisteredTaxpayer p=chooseTaxpayer();System.out.println("\nSTATEMENT FOR "+p.getDisplayName()+" | TIN "+p.getTin());
  for(int i=0;i<na;i++)if(assessments[i].getDeclaration().getObligation().getTaxpayer()==p)System.out.println(assessments[i].getAssessmentId()+" total="+total(assessments[i])+" penalties="+penaltiesFor(assessments[i])+" paid="+paid(assessments[i])+" balance="+balance(assessments[i]));
 }
 private static void balances(){for(int i=0;i<na;i++)System.out.println(assessments[i].getAssessmentId()+" | "+assessments[i].getDeclaration().getObligation().getTaxpayer().getDisplayName()+" | "+balance(assessments[i]));}
 private static void reports(){
  BigDecimal assessed=BigDecimal.ZERO,received=BigDecimal.ZERO,penalty=BigDecimal.ZERO;
  for(int i=0;i<na;i++)assessed=assessed.add(total(assessments[i]));for(int i=0;i<np;i++)received=received.add(payments[i].getAmount());for(int i=0;i<npen;i++)penalty=penalty.add(penalties[i].getPenaltyAmount());
  System.out.println("TAX REPORT");System.out.println("Individuals: "+ni+" | Corporates: "+nc+" | Officers: "+no);System.out.println("Tax types: "+nt+" | Obligations: "+nob+" | Declarations: "+nd);System.out.println("Assessment total: "+assessed+" | Payments: "+received+" | Penalties: "+penalty);
 }
 private static void polymorphism(){
  TaxParty[] parties=new TaxParty[ni+nc+no];int x=0;for(int i=0;i<ni;i++)parties[x++]=individuals[i];for(int i=0;i<nc;i++)parties[x++]=companies[i];for(int i=0;i<no;i++)parties[x++]=officers[i];
  Identifiable[] ids=new Identifiable[parties.length];for(int i=0;i<parties.length;i++)ids[i]=parties[i];
  System.out.println("TaxParty[] runtime polymorphism:");for(TaxParty p:parties)System.out.println(p.getClass().getSimpleName()+" -> "+p.getDisplayName()+" | "+p.getSummary());
  System.out.println("Identifiable[] interface polymorphism:");for(Identifiable i:ids)System.out.println(i.getId()+" | "+i.getDisplayName()+" | active="+i.isActive());
 }
}
