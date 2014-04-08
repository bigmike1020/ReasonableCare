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

/**
 * Shell to provide the required tasks, operations, and views for a staff member.
 *
 */
public class StaffShell {

  /**
   * Connection object used to create Statements. This shell doesn't own the
   * connection; no need to close.
   */
  final Connection connection;
  final CommonStatements commonStatements;

  final int id;

  public StaffShell(Connection connection, int id) throws SQLException {
    this.connection = connection;
    this.id = id;
    //constructor to use shared statements
  	commonStatements = new CommonStatements(connection);
  }
  
  /**
   * Allow a staff member to add a new student to the system
   * 
   * @param name
   * @param password
   * @param startingDate
   * @return studentID of created student if successful
   * @throws SQLException
   */
  @Command(description="Add a new student to the system")
  public Object createStudent(
		  @Param(name = "name")
		  String name, 
		  @Param(name="password")
		  String password, 
		  @Param(name="startingDate", description="starting semester of student in the format"
		  		+ "YYYY-MM-DD")
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
  
  /**
   * Allow staff member to add or update insturance information for a student
   * @param studentID
   * @param insuranceProviderName
   * @param insurancePolicyNumber
   * @return confirmation
   * @throws SQLException
   */
  @Command(description="Add or Update Insurance Information for a Student")
  public Object updateInsuranceInformation(
	  @Param(name="studentID", description="Student ID Number")
	  int studentID,
	  @Param(name="insuranceProviderName")
	  String insuranceProviderName,
	  @Param(name="insurancePolicyNumber")
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
  			
/**
 * Allow staff to add a new doctor to the system.
 * 
 * @param name
 * @param password
 * @param phoneNumber
 * @param specialization
 * @return doctorID of created doctor
 * @throws SQLException
 */
  @Command(description = "Add a new doctor to the system")
  public Object createDoctor(
      @Param(name = "name") 
      String name,
      @Param(name = "password") 
      String password,
      @Param(name = "phoneNumber", description = "Should be in form ###-###-####.") 
      String phoneNumber,
      @Param(name = "specialization", description = "If doctor is not a specialist, use "
      		+ "'General Practitioner'.") 
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

  /**
   * Return a table of all doctors, grouped by specialization
   * @return table of all doctors
   * @throws SQLException
   */

  @Command(description = "Show list of all doctors and their specializations")
	public Table getDoctors() throws SQLException {
		
		Table doctorTable = commonStatements.getDoctors();

		return doctorTable;
	}
/**
 * Show list of all students
 * @return table of all students
 * @throws SQLException
 */
  @Command(description = "Return a table of all students in the system")
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
/**
 * Allow a staff member to add a new staff member to the system
 * @param name
 * @param password
 * @return staffID of the created Staff member
 * @throws SQLException
 */
  @Command(description = "Add a new staff member to the system")
	  public Object createStaff(
	      @Param(name = "name") 
	      String name,
	      @Param(name = "password") 
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

  /**
   * Allow staff member to add a new nurse to the system
   * @param name
   * @param password
   * @return nurseID of the created nurse
   * @throws SQLException
   */
  @Command(description = "Add a new nurse to the system.")
		  public Object createNurse(
		      @Param(name = "name") 
		      String name,
		      @Param(name = "password") 
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
	
  /**
   * Update information for a Nurse
   * @param nurseId
   * @throws IOException
   * @throws SQLException
   */
  @Command(description="update information for a nurse")
  public void updateNurse(@Param(name="nurseID")int nurseId) throws IOException, SQLException {
    try (CommonStatements stm = new CommonStatements(connection)) {
      stm.updatenurseinformation(nurseId);
    }
  }

  /**
   * Update information for a Doctor
   * 
   * @param id Doctor ID
   * @throws IOException
   * @throws SQLException
   */
  @Command(description="update information for a doctor")
  public void updateDoctor(@Param(name="doctorID") int id) throws IOException, SQLException {
    try (CommonStatements stm = new CommonStatements(connection)) {
      stm.updatedoctorinformation(id);
    }
  }
  
  /**
   * Update information for a Student
   * 
   * @param id Student ID
   * @throws IOException
   * @throws SQLException
   */
  @Command(description="update information for a student")
  public void updateStudent(@Param(name="studentID") int id) throws IOException, SQLException {
    try (CommonStatements stm = new CommonStatements(connection)) {
      stm.updatestudentinformation(id);
    }
  }

  /**
   * Update information for a staff member
   * @param id Staff ID
   * @throws IOException
   * @throws SQLException
   */
  @Command(description="update information for a staff member")
  public void updateStaff(@Param(name="staffID") int id) throws IOException, SQLException {
    try (CommonStatements stm = new CommonStatements(connection)) {
      stm.updatestaffinformation(id);
    }
  }

  @Command
  public void makeAppointment() {
	  // TODO makeAppointment
	  // TODO make sure date is at start of semester
  }
  
  @Command(description = "Delete an appointment, given the appointment's ID.")
  public String deleteAppointment(@Param(name="appointmentID")String appointmentId) throws SQLException {
    int id;
    try {
      id = Integer.parseInt(appointmentId);
    } catch (NumberFormatException e) {
      return "Error: AppointmentId must be a number. Appointment not deleted.";
    }

    final String studentName, doctorName;
    try {
      connection.setAutoCommit(false);

      String sql = "select studentname, doctorname from makesAppointment join student using(studentid) join doctor using(doctorid) "
          + " where appointmentid=?";
      try (PreparedStatement statement = connection.prepareStatement(sql)) {

        statement.setInt(1, id);

        // Get records from the Student table
        try (ResultSet rs = statement.executeQuery()) {

          if (!rs.next()) {
            return "Error: Could not find appointment. Appointment not deleted.";
          }

          studentName = rs.getString(1);
          doctorName = rs.getString(2);
        }
      }

      sql = "DELETE FROM Appointment WHERE appointmentId=?";
      try (PreparedStatement statement = connection.prepareStatement(sql)) {
        statement.setInt(1, id);
        statement.executeUpdate();
      }

      connection.commit();
    } catch (SQLException e) {
      connection.rollback();
      throw e;
    } finally {
      connection.setAutoCommit(true);
    }

    return "Deleted appointment between " + doctorName + " and " + studentName;
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