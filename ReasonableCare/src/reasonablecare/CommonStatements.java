package reasonablecare;

import static java.lang.System.out;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class CommonStatements implements AutoCloseable {

  private final BufferedReader br = new BufferedReader(new InputStreamReader(
      System.in));

  private final Statement stm;

  public CommonStatements(Connection connection) throws SQLException {
    // Create a statement instance that will be sending your SQL statements
    // to the DBMS
    stm = connection.createStatement();
    connection.setAutoCommit(true); // set autocommit on
  }

  @Override
  public void close() throws SQLException {
    if (stm != null) {
      stm.close();
    }
  }

  /**
   * function for user of the system to register for the first time in the
   * system
   */
  public void register() throws IOException, SQLException {
    String j = "";

    out.println("Select which intended user are you :\ns for Student\nm for Managing Staff\nd for Doctor\nn for Nurse");
    j = br.readLine();
    j.toLowerCase();
    switch (j) {
    case "s":
      createStudent();
      break;
    case "n":
      createNurse();
      break;
    case "d":
      createDoctor();
      break;
    case "m":
      createStaff();
      break;

    }// end of switch
  }

  private void createStudent() throws IOException, SQLException {
    String sname = "";
    while (sname.isEmpty()) {
      out.println("Enter your Name");
      sname = (br.readLine());
    }
    String spassword = "";
    while (spassword.isEmpty()) {
      out.println("Choose a student health centre Password");
      spassword = (br.readLine());
    }
    String startingsemester = "";
    while (startingsemester.isEmpty()) {
      out.println("Enter the starting semester");
      startingsemester = (br.readLine());
    }
    out.println("Do you have a health insurance?\n1. Yes \n2. No");

    ResultSet result;
    int c = Integer.parseInt(br.readLine());
    if (c == 1) {
      String providerName = "";
      while (providerName.isEmpty()) {
        out.println("Enter your health insurance provider name");
        providerName = br.readLine();
      }
      String policyNumber = "";
      while (policyNumber.isEmpty()) {
        out.println("Enter your health insurance provider name");
        policyNumber = br.readLine();
      }

      stm.executeUpdate("INSERT INTO Student(studentName, password, healthInsuranceProviderName, healthInsurancePolicynumber, startingDate) values('"
          + sname
          + "' , '"
          + spassword
          + "' , '"
          + providerName
          + "', '"
          + policyNumber + "', '" + startingsemester + "')");
      result = stm.executeQuery("SELECT studentid from student");
    }// end of if
    else {
      stm.executeUpdate("INSERT INTO Student( studentName, password, startingDate) values('"
          + sname + "' , '" + spassword + "' , '" + startingsemester + "')");
      result = stm.executeQuery("SELECT studentid from student");
    }
    int ID1 = 0;
    while (result.next()) {
      ID1 = result.getInt("studentid");
    }// end of while
    out.println("Registration completed. Your Id is:" + ID1);
  }

  private void createNurse() throws IOException, SQLException {
    String nname = "";
    while (nname.isEmpty()) {
      out.println("Enter your Name");
      nname = (br.readLine());
    }
    String npassword = "";
    while (npassword.isEmpty()) {
      out.println("Choose a student health centre Password");
      npassword = (br.readLine());
    }
    stm.executeUpdate("INSERT INTO Nurse( nurseName, password) values ('"
        + nname + "','" + npassword + "')");
    ResultSet result = stm.executeQuery("SELECT nurseid from nurse");
    int ID2 = 0;
    while (result.next()) {
      ID2 = result.getInt("nurseid");
    }// end of while
    out.println("Registration completed. Your Id is:" + ID2);
  }

  private void createDoctor() throws IOException, SQLException {
    String dname = "";
    while (dname.isEmpty()) {
      out.println("Enter your Name");
      dname = (br.readLine());
    }
    String dpassword = "";
    while (dpassword.isEmpty()) {
      out.println("Choose a student health centre Password");
      dpassword = (br.readLine());
    }
    String dphone = "";
    while (dphone.isEmpty()) {
      out.println("Enter your Phone number");
      dphone = (br.readLine());
    }
    String dspecialization = "";
    while (dspecialization.isEmpty()) {
      out.println("Enter your Specialization");
      dspecialization = (br.readLine());
    }
    stm.executeUpdate("INSERT INTO Doctor( doctorNAme, password, phonenumber, specialization) values ('"
        + dname
        + "','"
        + dpassword
        + "','"
        + dphone
        + "','"
        + dspecialization
        + "')");
    ResultSet result = stm.executeQuery("SELECT doctorid from doctor");
    int ID3 = 0;
    while (result.next()) {
      ID3 = result.getInt("doctorid");
    }// end of while
    out.println("Registration completed. Your Id is:" + ID3);
  }

  private void createStaff() throws IOException, SQLException {
    String mname = "";
    while (mname.isEmpty()) {
      out.println("Enter your Name");
      mname = (br.readLine());
    }
    String mpassword = "";
    while (mpassword.isEmpty()) {
      out.println("Choose a student health centre Password");
      mpassword = (br.readLine());
    }
    stm.executeUpdate("INSERT INTO Staff(staffName, password) values ('"
        + mname + "','" + mpassword + "')");
    ResultSet result = stm.executeQuery("SELECT staffid from staff");
    int ID4 = 0;
    while (result.next()) {
      ID4 = result.getInt("staffid");
    }// end of while
    out.println("Registration completed. Your Id is:" + ID4);
  }

  public void manageStudent(int userid) throws SQLException, IOException {
    int at = 0;
    ResultSet result = stm
        .executeQuery("SELECT studentname FROM student WHERE studentid="
            + userid + ""); // get the name of the student
    if (result.next()) {
      do {
        out.println("Hi " + result.getString("studentname")
            + ". Welcome to Student Health Centre.");
      } while (result.next());
    }
    do {
      int schoice;
      out.println("What would you like to do:\n1.Update your information\n");
      schoice = Integer.parseInt(br.readLine());
      switch (schoice) {
      case 1:
        updatestudentinformation(userid);
        break;
      default:
        out.println("Wrong choice:(");
      }// end of switch
      out.println("Do you want to carry out another task?\n 1.Yes\n2.No");
      at = Integer.parseInt(br.readLine());

    } while (at == 1);// end of do
    if (at != 1)
      out.println("Thank you for using this system. Do visit us again");
  }

  public void manageStaff(int userid) throws SQLException, IOException {
    int at = 0;
    ResultSet result = stm
        .executeQuery("SELECT staffname FROM staff WHERE staffid=" + userid
            + ""); // get the name of the staff member
    if (result.next()) {
      do {
        out.println("Hi " + result.getString("staffname")
            + ". Welcome to Student Health Centre.");
      } while (result.next());
    }
    do {
      int mchoice;
      out.println("What would you like to do:\n1.Update your information\n");
      // n3.Check your mandatory vaccinations \n4. See future appointments");
      mchoice = Integer.parseInt(br.readLine());
      switch (mchoice) {
      case 1:
        updatestaffinformation(userid);
        break;
      // case 3:
      // checkVaccinations();
      // break;
      // case 4:
      // checkAppointments();
      // break;
      default:
        out.println("Wrong choice:(");
      }// end of switch
      out.println("Do you want to carry out another task?\n 1.Yes\n2.No");
      at = Integer.parseInt(br.readLine());

    } while (at == 1);// end of do
    if (at != 1)
      out.println("Thank you for using this system. Do visit us again");
  }

  public void manageDoctor(int userid) throws SQLException, IOException {
    int at = 0;
    ResultSet result = stm
        .executeQuery("SELECT doctorname FROM doctor WHERE doctorid=" + userid
            + ""); // get the name of the doctor
    if (result.next()) {
      do {
        out.println("Hi " + result.getString("doctorname")
            + ". Welcome to Student Health Centre.");
      } while (result.next());
    }
    do {
      int dchoice;
      out.println("What would you like to do:\n1.Update your information\n");
      dchoice = Integer.parseInt(br.readLine());
      switch (dchoice) {
      case 1:
        updatedoctorinformation(userid);
        break;
      // case 4:
      // checkAppointments();
      // break;
      default:
        out.println("Wrong choice:(");
      }// end of switch
      out.println("Do you want to carry out another task?\n 1.Yes\n2.No");
      at = Integer.parseInt(br.readLine());

    } while (at == 1);// end of do
    if (at != 1)
      out.println("Thank you for using this system. Do visit us again");
  }

  public void manageNurse(int userid) throws SQLException, IOException {
    int at = 0;
    ResultSet result = stm
        .executeQuery("SELECT nursename FROM nurse WHERE nurseid=" + userid
            + ""); // get the name of the nurse
    if (result.next()) {
      do {
        out.println("Hi " + result.getString("nursename")
            + ". Welcome to Student Health Centre.");
      } while (result.next());
    }
    do {
      int nchoice;
      out.println("What would you like to do:\n1.Update your information");
      nchoice = Integer.parseInt(br.readLine());
      switch (nchoice) {
      case 1:
        updatenurseinformation(userid);
        break;
      default:
        out.println("Wrong choice:(");
      }// end of switch
      out.println("Do you want to carry out another task?\n 1.Yes\n2.No");
      at = Integer.parseInt(br.readLine());

    } while (at == 1);// end of do
    if (at != 1)
      out.println("Thank you for using this system. Do visit us again");
  }

  private void updatenurseinformation(int z) throws IOException, SQLException {
    int userid2 = z;
    int y;
    String b;
    out.println("Enter the attribute to be updated: \n1.Name \n2. Password");
    y = Integer.parseInt(br.readLine());
    out.println("Enter the changed value");
    b = (br.readLine());
    if (b.isEmpty()) {
      out.println("invalid input");
      updatenurseinformation(userid2);
    }

    switch (y) {
    case 1:
      stm.executeUpdate("update Nurse set nursename =' " + b
          + " 'where nurseid=" + z);
      out.println("Record Updated");
      printNurseInfo(z);
      break;
    case 2:

      stm.executeUpdate("update nurse set password =' " + b
          + " 'where nurseid=" + z);
      out.println("Record Updated");
      printNurseInfo(z);

      break;
    }// end of switch
  }

  private void printNurseInfo(int nurseId) throws SQLException {
    out.println("Your updated details are as shown:");

    // get the details of the student whose record is updated
    try (ResultSet result = stm
        .executeQuery("SELECT * FROM nurse WHERE nurseid=" + nurseId + "")) {

      while (result.next()) {
        out.println("ID: " + result.getString("nurseid"));
        out.println("Name: " + result.getString("nursename"));
        out.println("Password: " + result.getString("password"));
      }
    }
  }

  private void updatedoctorinformation(int z) throws IOException, SQLException {
    int userid2 = z;
    int y;
    String b;
    out.println("Enter the attribute to be updated: \n1.Name \n2.Password\n3.Specialization\n4.Phone Number");
    y = Integer.parseInt(br.readLine());
    out.println("Enter the changed value");
    b = (br.readLine());
    if (b.isEmpty()) {
      out.println("invalid input");
      updatestudentinformation(userid2);
    }

    switch (y) {
    case 1:
      stm.executeUpdate("update doctor set doctorname =' " + b
          + " 'where doctorid=" + z);
      out.println("Record Updated");
      printDoctorInfo(z);
      break;

    case 2:
      stm.executeUpdate("update doctor set PASSWORD =' " + b
          + " 'where doctorid=" + z);
      out.println("Record Updated");
      printDoctorInfo(z);
      break;
    case 3:
      stm.executeUpdate("update doctor set Specialization =' " + b
          + " 'where doctorid=" + z);
      out.println("Record Updated");
      printDoctorInfo(z);
      break;
    case 4:
      stm.executeUpdate("update doctor set phonenumber =' " + b
          + " 'where doctorid=" + z);
      out.println("Record Updated");
      printDoctorInfo(z);
      break;
    }// end of switch
  }

  private void printDoctorInfo(int doctorId) throws SQLException {
    out.println("Your updated details are as shown:");

    // get the details of the student whose record is updated
    try (ResultSet result = stm
        .executeQuery("SELECT * FROM doctor WHERE doctorid=" + doctorId + "")) {

      while (result.next()) {
        out.println("ID: " + result.getString("doctorid"));
        out.println("Name: " + result.getString("doctorname"));
        out.println("Password: " + result.getString("password"));
        out.println("Specialization: " + result.getString("specialization"));
        out.println("Phone Number: " + result.getString("Phonenumber"));
      }
    }
  }

  private void updatestaffinformation(int z) throws IOException, SQLException {
    int userid2 = z;
    int y;
    String b;
    out.println("Enter the attribute to be updated: \n1.Name \n2. Password");
    y = Integer.parseInt(br.readLine());
    out.println("Enter the changed value");
    b = (br.readLine());
    if (b.isEmpty()) {
      out.println("invalid input");
      updatestaffinformation(userid2);
    }

    switch (y) {
    case 1:
      stm.executeUpdate("update staff set staffname =' " + b
          + " 'where staffid=" + z);
      out.println("Record Updated");
      printStaffInfo(z);
      break;
    case 2:

      stm.executeUpdate("update staff set password =' " + b
          + " 'where staffid=" + z);
      out.println("Record Updated");
      printStaffInfo(z);

      break;
    }// end of switch
  }

  private void printStaffInfo(int staffId) throws SQLException {
    out.println("Your updated details are as shown:");

    // get the details of the student whose record is updated
    try (ResultSet result = stm
        .executeQuery("SELECT * FROM staff WHERE staffid=" + staffId + "")) {

      while (result.next()) {
        out.println("ID: " + result.getString("staffid"));
        out.println("Name: " + result.getString("staffname"));
        out.println("Password: " + result.getString("password"));
      }
    }
  }

  private void updatestudentinformation(int z) throws IOException, SQLException {
    int userid2 = z;
    int y;
    String b;
    out.println("Enter the attribute to be updated: \n1.Name \n2.HealthInsurance Provider Name\n3.HealthInsurance Provider Number \n4.Password\n5.Starting semester");
    y = Integer.parseInt(br.readLine());
    out.println("Enter the changed value");
    b = (br.readLine());
    if (b.isEmpty()) {
      out.println("invalid input");
      updatestudentinformation(userid2);
    }

    switch (y) {
    case 1:
      stm.executeUpdate("update Student set studentname =' " + b
          + " 'where studentid=" + z);
      out.println("Record Updated");
      printStudentInfo(z);
      break;
    case 2:

      stm.executeUpdate("update Student set HEALTHINSURANCEPROVIDERNAME =' "
          + b + " 'where studentid=" + z);
      do {
        out.println("Enter the HEALTHINSURANCE POLICY NUMBER");
        b = (br.readLine());
      } while (b.isEmpty());
      stm.executeUpdate("update Student set HEALTHINSURANCEPOLICYNUMBER =' "
          + b + " 'where studentid=" + z);
      out.println("Record Updated");
      printStudentInfo(z);

      break;
    case 3:
      stm.executeUpdate("update Student set HEALTHINSURANCEPOLICYNUMBER =' "
          + b + " 'where studentid=" + z);
      out.println("Record Updated");
      printStudentInfo(z);
      break;
    case 4:
      stm.executeUpdate("update Student set PASSWORD =' " + b
          + " 'where studentid=" + z);
      out.println("Record Updated");
      printStudentInfo(z);
      break;
    case 5:
      stm.executeUpdate("update Student set startingdate =' " + b
          + " 'where studentid=" + z);
      out.println("Record Updated");
      printStudentInfo(z);
      break;
    }// end of switch
  }// end of updatestudentinformation

  private void printStudentInfo(int studentId) throws SQLException {
    out.println("Your updated details are as shown:");

    // get the details of the student whose record is updated
    try (ResultSet result = stm
        .executeQuery("SELECT * FROM student WHERE studentid=" + studentId + "")) {

      while (result.next()) {
        out.println("ID: " + result.getString("studentID"));
        out.println("Name: " + result.getString("studentname"));
        out.println("Password: " + result.getString("password"));
        out.println("HealthInsurance Provider Name: "
            + result.getString("HEALTHINSURANCEPROVIDERNAME"));
        out.println("HealthInsurance Provider Number: "
            + result.getString("HEALTHINSURANCEPOLICYNUMBER"));
        out.println("Starting semester: " + result.getString("startingdate"));
      }
    }
  }

}// end of class HealthCentre

