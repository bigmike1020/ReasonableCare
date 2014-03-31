package reasonablecare;

import static java.lang.System.out;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class HealthCentre {

  private final BufferedReader br = new BufferedReader(new InputStreamReader(
      System.in));

  /** This HealthCentre does not own the connection, no need to close. */
  private final Connection connection;

  private Statement statement;
  private ResultSet result;
  private final int userid;
  private String job;

  public HealthCentre(Connection connection, int userid) {
    this.connection = connection;
    this.userid = userid;
  }

  public void main(Connection connection) throws Exception {

    // Create a statement instance that will be sending your SQL statements
    // to the DBMS
    statement = connection.createStatement();
    connection.setAutoCommit(true); // set autocommit on

    out.println("Are you a new user to this system?\n1:Yes \n2:No");
    int newuser = Integer.parseInt(br.readLine());
    if (newuser == 1)
      register();

  }// end of main

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
      int c = Integer.parseInt(br.readLine());
      if (c == 1) {
        String HEALTHINSURANCEPROVIDERNAME = "";
        while (HEALTHINSURANCEPROVIDERNAME.isEmpty()) {
          out.println("Enter your health insurance provider name");
          HEALTHINSURANCEPROVIDERNAME = br.readLine();
        }
        String HEALTHINSURANCEPOLICYNUMBER = "";
        while (HEALTHINSURANCEPOLICYNUMBER.isEmpty()) {
          out.println("Enter your health insurance provider name");
          HEALTHINSURANCEPOLICYNUMBER = br.readLine();
        }

        statement
            .executeUpdate("INSERT INTO Student(studentName, password, healthInsuranceProviderName, healthInsurancePolicynumber, startingDate) values('"
                + sname
                + "' , '"
                + spassword
                + "' , '"
                + HEALTHINSURANCEPROVIDERNAME
                + "', '"
                + HEALTHINSURANCEPOLICYNUMBER
                + "', '"
                + startingsemester
                + "')");
        result = statement.executeQuery("SELECT studentid from student");
      }// end of if
      else {
        statement
            .executeUpdate("INSERT INTO Student( studentName, password, startingDate) values('"
                + sname
                + "' , '"
                + spassword
                + "' , '"
                + startingsemester
                + "')");
        result = statement.executeQuery("SELECT studentid from student");
      }
      int ID1 = 0;
      while (result.next()) {
        ID1 = result.getInt("studentid");
      }// end of while
      out.println("Registration completed. Your Id is:" + ID1);
      break;
    case "n":
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
      statement
          .executeUpdate("INSERT INTO Nurse( nurseName, password) values ('"
              + nname + "','" + npassword + "')");
      result = statement.executeQuery("SELECT nurseid from nurse");
      int ID2 = 0;
      while (result.next()) {
        ID2 = result.getInt("nurseid");
      }// end of while
      out.println("Registration completed. Your Id is:" + ID2);
      break;
    case "d":
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
      statement
          .executeUpdate("INSERT INTO Doctor( doctorNAme, password, phonenumber, specialization) values ('"
              + dname
              + "','"
              + dpassword
              + "','"
              + dphone
              + "','"
              + dspecialization + "')");
      result = statement.executeQuery("SELECT doctorid from doctor");
      int ID3 = 0;
      while (result.next()) {
        ID3 = result.getInt("doctorid");
      }// end of while
      out.println("Registration completed. Your Id is:" + ID3);
      break;
    case "m":
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
      statement
          .executeUpdate("INSERT INTO Staff(staffName, password) values ('"
              + mname + "','" + mpassword + "')");
      result = statement.executeQuery("SELECT staffid from staff");
      int ID4 = 0;
      while (result.next()) {
        ID4 = result.getInt("staffid");
      }// end of while
      out.println("Registration completed. Your Id is:" + ID4);
      break;

    }// end of switch
  }

  public void manageStudent() throws SQLException, IOException {
    int at = 0;
    result = statement
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
      out.println("What would you like to do:\n1.Update your information\n2. Make an Appointment \n Make an Appointment\n3.Check your mandatory vaccinations \n4. See future appointments");
      schoice = Integer.parseInt(br.readLine());
      switch (schoice) {
      case 1:
        updatestudentinformation(userid);
        break;
      case 2:
        makeAppointment();
        break;
      case 3:
        checkVaccinations();
        break;
      case 4:
        checkAppointments();
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

  public void manageStaff() throws SQLException, IOException {
    int at = 0;
    result = statement
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
      out.println("What would you like to do:\n1.Update your information\n2.Make an Appointment");
      // n3.Check your mandatory vaccinations \n4. See future appointments");
      mchoice = Integer.parseInt(br.readLine());
      switch (mchoice) {
      case 1:
        updatestaffinformation(userid);
        break;
      case 2:
        makeAppointment();
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

  public void manageDoctor() throws SQLException, IOException {
    int at = 0;
    result = statement
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
      out.println("What would you like to do:\n1.Update your information\n2.See future appointments\n3.See a particular student record");
      dchoice = Integer.parseInt(br.readLine());
      switch (dchoice) {
      case 1:
        updatedoctorinformation(userid);
        break;
      case 2:
        checkAppointments();
        break;
      case 3:
        checkStudentRecord();
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

  public void manageNurse() throws SQLException, IOException {
    int at = 0;
    result = statement
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
      out.println("What would you like to do:\n1.Update your information\n2.Add a new consultation record");
      nchoice = Integer.parseInt(br.readLine());
      switch (nchoice) {
      case 1:
        updatenurseinformation(userid);
        break;
      case 2:
        addConsultations();
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
      statement.executeUpdate("update Nurse set nursename =' " + b
          + " 'where nurseid=" + z);
      out.println("Record Updated");
      out.println("Your updated details are as shown:");
      result = statement.executeQuery("SELECT * FROM nurse WHERE nurseid=" + z
          + ""); // get the details of the student whose record is updated
      if (result.next()) {
        do {
          out.println("ID: " + result.getString("nurseid"));
          out.println("Name: " + result.getString("nursename"));
          out.println("Password: " + result.getString("password"));
        } while (result.next());
      }
      break;
    case 2:

      statement.executeUpdate("update nurse set password =' " + b
          + " 'where nurseid=" + z);
      out.println("Record Updated");
      out.println("Your updated details are as shown:");
      result = statement.executeQuery("SELECT * FROM nurse WHERE nurseid=" + z
          + ""); // get the details of the student whose record is updated
      if (result.next()) {
        do {
          out.println("ID: " + result.getString("nurseid"));
          out.println("Name: " + result.getString("nursename"));
          out.println("Password: " + result.getString("password"));
        } while (result.next());
      }

      break;
    }// end of switch
  }

  private void addConsultations() throws IOException, SQLException {

  }

  private void checkStudentRecord() throws IOException, SQLException {

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
      statement.executeUpdate("update doctor set doctorname =' " + b
          + " 'where doctorid=" + z);
      out.println("Record Updated");
      out.println("Your updated details are as shown:");
      result = statement.executeQuery("SELECT * FROM doctor WHERE doctorid="
          + z + ""); // get the details of the student whose record is updated
      if (result.next()) {
        do {
          out.println("ID: " + result.getString("doctorid"));
          out.println("Name: " + result.getString("doctorname"));
          out.println("Password: " + result.getString("password"));
          out.println("Specialization: " + result.getString("specialization"));
          out.println("Phone Number: " + result.getString("Phonenumber"));
        } while (result.next());
      }
      break;

    case 2:
      statement.executeUpdate("update doctor set PASSWORD =' " + b
          + " 'where doctorid=" + z);
      out.println("Record Updated");
      out.println("Your updated details are as shown:");
      result = statement.executeQuery("SELECT * FROM doctor WHERE doctorid="
          + z + ""); // get the details of the student whose record is updated
      if (result.next()) {
        do {
          out.println("ID: " + result.getString("doctorid"));
          out.println("Name: " + result.getString("doctorname"));
          out.println("Password: " + result.getString("password"));
          out.println("Specialization: " + result.getString("specialization"));
          out.println("Phone Number: " + result.getString("Phonenumber"));
        } while (result.next());
      }
      break;
    case 3:
      statement.executeUpdate("update doctor set Specialization =' " + b
          + " 'where doctorid=" + z);
      out.println("Record Updated");
      out.println("Your updated details are as shown:");
      result = statement.executeQuery("SELECT * FROM doctor WHERE doctorid="
          + z + ""); // get the details of the student whose record is updated
      if (result.next()) {
        do {
          out.println("ID: " + result.getString("doctorid"));
          out.println("Name: " + result.getString("doctorname"));
          out.println("Password: " + result.getString("password"));
          out.println("Specialization: " + result.getString("specialization"));
          out.println("Phone Number: " + result.getString("Phonenumber"));
        } while (result.next());
      }
      break;
    case 4:
      statement.executeUpdate("update doctor set phonenumber =' " + b
          + " 'where doctorid=" + z);
      out.println("Record Updated");
      out.println("Your updated details are as shown:");
      result = statement.executeQuery("SELECT * FROM doctor WHERE doctorid="
          + z + ""); // get the details of the student whose record is updated
      if (result.next()) {
        do {
          out.println("ID: " + result.getString("doctorid"));
          out.println("Name: " + result.getString("doctorname"));
          out.println("Password: " + result.getString("password"));
          out.println("Specialization: " + result.getString("specialization"));
          out.println("Phone Number: " + result.getString("Phonenumber"));
        } while (result.next());
      }
      break;
    }// end of switch
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
      updatenurseinformation(userid2);
    }

    switch (y) {
    case 1:
      statement.executeUpdate("update staff set staffname =' " + b
          + " 'where staffid=" + z);
      out.println("Record Updated");
      out.println("Your updated details are as shown:");
      result = statement.executeQuery("SELECT * FROM staff WHERE staffid=" + z
          + ""); // get the details of the student whose record is updated
      if (result.next()) {
        do {
          out.println("ID: " + result.getString("staffid"));
          out.println("Name: " + result.getString("staffname"));
          out.println("Password: " + result.getString("password"));
        } while (result.next());
      }
      break;
    case 2:

      statement.executeUpdate("update staff set password =' " + b
          + " 'where staffid=" + z);
      out.println("Record Updated");
      out.println("Your updated details are as shown:");
      result = statement.executeQuery("SELECT * FROM staff WHERE staffid=" + z
          + ""); // get the details of the student whose record is updated
      if (result.next()) {
        do {
          out.println("ID: " + result.getString("staffid"));
          out.println("Name: " + result.getString("staffname"));
          out.println("Password: " + result.getString("password"));
        } while (result.next());
      }

      break;
    }// end of switch
  }

  private void checkAppointments() throws IOException, SQLException {
    // out.println("You are in: checkAppointments");
  }

  private void checkVaccinations() throws IOException, SQLException {
    // out.println("You are in: checkVaccinations");

  }

  private void makeAppointment() throws IOException, SQLException {
    // out.println("You are in: makeAppointment");

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
      statement.executeUpdate("update Student set studentname =' " + b
          + " 'where studentid=" + z);
      out.println("Record Updated");
      printStudentInfo(z);
      break;
    case 2:

      statement
          .executeUpdate("update Student set HEALTHINSURANCEPROVIDERNAME =' "
              + b + " 'where studentid=" + z);
      do {
        out.println("Enter the HEALTHINSURANCE POLICY NUMBER");
        b = (br.readLine());
      } while (b.isEmpty());
      statement
          .executeUpdate("update Student set HEALTHINSURANCEPOLICYNUMBER =' "
              + b + " 'where studentid=" + z);
      out.println("Record Updated");
      printStudentInfo(z);

      break;
    case 3:
      statement
          .executeUpdate("update Student set HEALTHINSURANCEPOLICYNUMBER =' "
              + b + " 'where studentid=" + z);
      out.println("Record Updated");
      printStudentInfo(z);
      break;
    case 4:
      statement.executeUpdate("update Student set PASSWORD =' " + b
          + " 'where studentid=" + z);
      out.println("Record Updated");
      printStudentInfo(z);
      break;
    case 5:
      statement.executeUpdate("update Student set startingdate =' " + b
          + " 'where studentid=" + z);
      out.println("Record Updated");
      printStudentInfo(z);
      break;
    }// end of switch
  }// end of updatestudentinformation

  private void printStudentInfo(int studentId) throws SQLException {
    out.println("Your updated details are as shown:");

    // get the details of the student whose record is updated
    try (ResultSet result = statement
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

