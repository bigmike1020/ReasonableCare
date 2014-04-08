package reasonablecare;

import static java.lang.System.out;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class CommonStatements implements AutoCloseable {

  private final BufferedReader br = new BufferedReader(new InputStreamReader(
      System.in));

  private final Statement stm;
  
  Connection connection;

  public CommonStatements(Connection connection) throws SQLException {
    // Create a statement instance that will be sending your SQL statements
    // to the DBMS
	this.connection=connection;
    stm = connection.createStatement();
    //connection.setAutoCommit(true); //commented out due to SQL error
  }

  @Override
  public void close() throws SQLException {
    if (stm != null) {
      stm.close();
    }
  }

  public void updatenurseinformation(int z) throws IOException, SQLException {
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

  public void updatedoctorinformation(int z) throws IOException, SQLException {
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

  public void updatestaffinformation(int z) throws IOException, SQLException {
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

  public void updatestudentinformation(int z) throws IOException, SQLException {
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
  
  /**
   * Print Table of doctors
   * @return Table of doctors with their specializations, names, and doctorIDs
   * @throws SQLException
   */
  
  public Table getDoctors() throws SQLException {
		try (Statement statement = connection.createStatement()) {

		// Get records from the Student table
		try (ResultSet rs = statement
				.executeQuery("SELECT specialization, doctorID, doctorName "							+ "FROM Doctor ORDER BY specialization, doctorID")) {

			Table table = new Table("Specialization", "Doctor ID",
					"Doctor Name");

			while (rs.next()) {
				table.add(rs.getString(1), rs.getInt(2), rs.getString(3));
			}
			
			return table;
		}
	}
}
  
  /**
	 * Utility method to validate that a given int is a valid doctorID in the DB
	 * 
	 * @param docID
	 * @throws Exception
	 */
	public boolean validateDoctorID(String docID) throws Exception {

		int doctorID=0;
		
		//ensure the given doctor ID is an int
		try {
			doctorID=Integer.parseInt(docID);
		} catch (NumberFormatException e) {
			return false;
		}
		//check valid range
		if (doctorID<2000 || doctorID>2999)
			return false;
		
		//check if exists in DB
		String sql = "select 1 from doctor where doctorID=?";
		
		try (PreparedStatement stm = connection.prepareStatement(sql)) {

			stm.setInt(1,doctorID);

			ResultSet rs = stm.executeQuery();
			if (!rs.next()) {
				return false;
			}
			else return true;

		}
	}
	/*
	 * Create statements are only performed by Staff - Have methods in staff shell
	 * 
	 * 
	  public void createStudent() throws IOException, SQLException {
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

		  public void createNurse() throws IOException, SQLException {
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

		  public void createDoctor() throws IOException, SQLException {
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

		  public void createStaff() throws IOException, SQLException {
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
		  */

}// end of class HealthCentre

