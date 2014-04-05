package reasonablecare;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import asg.cliche.Command;
import asg.cliche.Param;

//import java.sql.Timestamp;

public class StaffShell {

  /**
   * Connection object used to create Statements. This shell doesn't own the
   * connection; no need to close.
   */
  final Connection connection;

  final int id;

  public StaffShell(Connection connection, int id) {
    this.connection = connection;
    this.id = id;
  }

  @Command
  public String createStudent(Object... args) {
	  return "Unrecognized command.";
  }
  
  @Command(description="Add a new student to the system.  Returns UniqueID of student.  Usage:"
  		+ "create-student 'name' 'password' startingDate (YYYY-MM-DD)")
  public Object createStudent(
		  @Param(name = "name", description="Name of student.  For student names with multiple words,"
		  		+ "surround with single quotes.")
		  String name, 
		  @Param(name="password", description="password for student to login.  For passwords with"
		  		+ "spaces, surround with single quotes.")
		  String password, 
		  @Param(name="startingDate", description="starting semester of student in the format"
		  		+ "YYYY-MM-DD.  Enter without quotes.")
		  String startingDate)
      throws SQLException {

    String sql = "insert into student(studentName,password,startingDate) values(?,?,?)";

    // Create a statement instance that will be sending
    // your SQL statements to the DBMS
    // second argument is the generated key that is to be returned
    try (PreparedStatement stm = connection.prepareStatement(sql,
        new String[] { "StudentID" })) {

      stm.setString(1, name);
      stm.setString(2, password);
      stm.setDate(3, java.sql.Date.valueOf(startingDate)); //date must be in YYYY-MM-DD format
      stm.executeUpdate();

      //get the auto-generated key from the row that was added
      int id = 0;

      ResultSet rs = stm.getGeneratedKeys();
      if (rs != null && rs.next()) {
        id = rs.getInt(1);
      }

      return "Created new student with id " + id;
    } catch(IllegalArgumentException ex) {
    	return "Error parsing date argument. Enter in yyyy-mm-dd format. Student not created.";
    }

  }
  
  @Command(description="Add or Update Insurance Informatin for a Student.  Usage:")
  public Object updateStudentInsuranceInformation(
	  @Param(name="studentID", description="Student ID Number")
	  int studentID,
	  @Param(name="insuranceProviderName", description="Health Insurance Provider Name")
	  String insuranceProviderName,
	  @Param(name="insurancePolicyNumber", description="Health Insurance Policy Number")
	  String insurancePolicyNumber)
  	throws SQLException{
	  
	  String sql = "update student set HEALTHINSURANCEPROVIDERNAME = ?, "
	  		+ "HEALTHINSURANCEPOLICYNUMBER = ? where studentID = ?";

	    // Create a statement instance that will be sending
	    try (PreparedStatement stm = connection.prepareStatement(sql)) {

	      stm.setString(1, insuranceProviderName);
	      stm.setString(2, insurancePolicyNumber);
	      stm.setInt(3, studentID);
	      stm.executeUpdate();
	      }

	      return "Updated Insurance Information";
  }
  			

  @Command(description = "Add a new doctor to the system.  Usage: create-doctor name password "
  		+ "phoneNumber specialization. Returns uniqueID of doctor.")
  public Object createDoctor(
      @Param(name = "name", description = "Name of Doctor.  For names containing spaces/multiple "
      		+ "words, surround with single quotes ('').") 
      String name,
      @Param(name = "password", description = "Password.  For passwords with spaces, surround with "
      		+ "single quotes ('').") 
      String password,
      @Param(name = "phoneNumber", description = "Phone number.  Should be in form ###-###-####.") 
      String phoneNumber,
      @Param(name = "specialization", description = "Specialization.  For specializations with spaces,"
      		+ " surround with single quotes ('')."
          + "If doctor is not a specialist, use 'General Practitioner'.") 
      String specialization)
      throws SQLException {

    String sql = "insert into doctor(doctorName,password,phoneNumber,specialization) values(?,?,?,?)";

    // Create a statement instance that will be sending
    // your SQL statements to the DBMS
    // second argument is the generated key that is to be returned
    try (PreparedStatement stm = connection.prepareStatement(sql,
        new String[] { "DoctorID" })) {

      stm.setString(1, name);
      stm.setString(2, password);
      stm.setString(3, phoneNumber);
      stm.setString(4, specialization);
      stm.executeUpdate();

      //get the auto-generated key from the row that was added
      int id = 0;

      ResultSet rs = stm.getGeneratedKeys();
      if (rs != null && rs.next()) {
        id = rs.getInt(1);
      }

      return "Created new doctor with id " + id;
    }
  }

  @Command
  public Object getDoctors() throws SQLException {

    try (Statement statement = connection.createStatement()) {

      // Get records from the Doctor table
      try (ResultSet result = statement
          .executeQuery("SELECT doctorID, doctorName FROM Doctor ORDER BY doctorID")) {

        Table res = new Table("Doctor ID", "Doctor Name");

        while (result.next()) {
          int id = result.getInt("doctorID");
          String name = result.getString("doctorName");
          res.add(id, name);
        }

        return res;
      }
    }
  }

  @Command
  public Object getStudents() throws SQLException {

    try (Statement statement = connection.createStatement()) {

      // Get records from the Student table
      try (ResultSet result = statement
          .executeQuery("SELECT studentID, studentName FROM Student ORDER BY studentID")) {

        Table res = new Table("Student ID", "Student Name");

        while (result.next()) {
          int id = result.getInt("studentID");
          String name = result.getString("studentName");
          res.add(id, name);
        }

        return res;
      }
    }
  }

  @Command(description = "Add a new staff member to the system.  Usage: create-staff name "
  		+ "password.  Returns uniqueID of staff member.")
	  public Object createStaff(
	      @Param(name = "name", description = "Name of staff member.  For names containing spaces/"
	      		+ "multiple words, surround with single quotes ('').") 
	      String name,
	      @Param(name = "password", description = "Password.  For passwords with spaces, surround "
	      		+ "with single quotes ('').") 
	      String password)
	      throws SQLException {

	    String sql = "insert into staff(staffName,password) values(?,?)";

	    // Create a statement to enter staff into DB and return ID
	    try (PreparedStatement stm = connection.prepareStatement(sql,
	        new String[] { "StaffID" })) {

	      stm.setString(1, name);
	      stm.setString(2, password);
	      stm.executeUpdate();

	      //get the auto-generated key from the row that was added
	      int id = 0;

	      ResultSet rs = stm.getGeneratedKeys();
	      if (rs != null && rs.next()) {
	        id = rs.getInt(1);
	      }

	      return "Created new staff member with id " + id;
	    }
	  }

  @Command(description = "Add a new nurse to the system.  Usage: create-nurse name "
	  		+ "password.  Returns uniqueID of nurse.")
		  public Object createNurse(
		      @Param(name = "name", description = "Name of nurse.  For names containing spaces/"
		      		+ "multiple words, surround with single quotes ('').") 
		      String name,
		      @Param(name = "password", description = "Password.  For passwords with spaces, surround "
		      		+ "with single quotes ('').") 
		      String password)
		      throws SQLException {

		    String sql = "insert into nurse(nurseName,password) values(?,?)";

		    // Create a statement to enter staff into DB and return ID
		    try (PreparedStatement stm = connection.prepareStatement(sql,
		        new String[] { "NurseID" })) {

		      stm.setString(1, name);
		      stm.setString(2, password);
		      stm.executeUpdate();

		      //get the auto-generated key from the row that was added
		      int id = 0;

		      ResultSet rs = stm.getGeneratedKeys();
		      if (rs != null && rs.next()) {
		        id = rs.getInt(1);
		      }

		      return "Created new nurse with id " + id;
		    }
		   }
	
  
  @Command
  public void updateNurse(int nurseId) throws IOException, SQLException {
    try (CommonStatements stm = new CommonStatements(connection)) {
      stm.updatenurseinformation(nurseId);
    }
  }

  @Command
  public void updateDoctor(int id) throws IOException, SQLException {
    try (CommonStatements stm = new CommonStatements(connection)) {
      stm.updatedoctorinformation(id);
    }
  }

  @Command
  public void updateStudent(int id) throws IOException, SQLException {
    try (CommonStatements stm = new CommonStatements(connection)) {
      stm.updatestudentinformation(id);
    }
  }

  @Command
  public void updateStaff(int id) throws IOException, SQLException {
    try (CommonStatements stm = new CommonStatements(connection)) {
      stm.updatestaffinformation(id);
    }
  }

  @Command
  public void makeAppointment() {
	  // TODO makeAppointment
	  // TODO make sure date is at start of semester
  }
  
  @Command
  public void deleteAppointment() {
	  // TODO deleteAppointment
  }
  
  @Command(description = "List students that have been attending for 6 months and have not scheduled 3 vaccinations.")
  public Table checkVaccinations() throws SQLException {

    try (Statement statement = connection.createStatement()) {

      String sql = "SELECT StudentID, studentName, startingDate, nvl(V.Vacc, 0) "
          +"FROM Student LEFT JOIN ( "
          +" SELECT StudentID, count(*) AS Vacc "
          +" FROM makesAppointment NATURAL JOIN Appointment "
          +" WHERE Appointment.type='Vaccination' and studentid=1004 "
          +" GROUP BY StudentID "
          +") V USING (StudentID) "
          +"WHERE MONTHS_BETWEEN(CURRENT_TIMESTAMP, startingDate) >= 6 "
          +" AND nvl(V.Vacc,0) < 3";


      // Get records from the Student table
      try (ResultSet rs = statement.executeQuery(sql)) {

        Table table = new Table("Student ID", "Student Name",
            "Starting Semester", "Number of Vaccinations");

        while (rs.next()) {
          table.add(rs.getInt(1), rs.getString(2), rs.getString(3),
              rs.getInt(4));
        }

        return table;
      }
    }

  }

}