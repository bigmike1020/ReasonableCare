package reasonablecare;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

import asg.cliche.Command;
import asg.cliche.Param;

public class NurseShell {

  Connection connection;
  int id;
  
  public NurseShell(Connection connection, int id) {
    this.connection = connection;
    this.id = id;
  }
  
  @Command
  public String addConsultation(String strDateTime, String nurseNotes, int studentId, int nurseId) {
    // INSERT INTO Consultation(timeofConsultation, nurseNotes)
	// VALUES (to_date('16.03.2014 01:22:43', 'dd.mm.yyyy hh24:mi:ss'), 'Notes go here')
	// INSERT INTO MakesConsultation
	  
	  Date parsedDate = new Date();
	  
	  PreparedStatement addConsultation = null;
	  PreparedStatement addMakesConsultation = null;
	  
	  String addConsultationString = "INSERT INTO Consultation(timeofConsultation, nurseNotes) VALUES(?, ?)";
	  String addMakesConsultationString = "INSERT INTO MakesConsultation(studentId, nurseId, consultationID) " +
			  "VALUES(?, ?, ?)";
	  
	  try{
		  SimpleDateFormat format = new SimpleDateFormat();
		  
		  
		  
		  parsedDate = format.parse(strDateTime);
		  Timestamp timestamp = new Timestamp(parsedDate.getTime());
		  
		  int consultationId;
		  
		  addConsultation = connection.prepareStatement(addConsultationString, Statement.RETURN_GENERATED_KEYS);
		  addConsultation.setTimestamp(1, timestamp);
		  addConsultation.setString(2,  nurseNotes);
		  
		  addConsultation.executeUpdate();
		  
		  ResultSet generatedKeys = addConsultation.getGeneratedKeys();
		  consultationId = generatedKeys.getInt(1);
		  
		  addMakesConsultation = connection.prepareStatement(addMakesConsultationString);
		  
		  addMakesConsultation.setInt(1, studentId);
		  addMakesConsultation.setInt(2, nurseId);
		  addMakesConsultation.setInt(3, consultationId);
		  
		  int rowsUpdated = addMakesConsultation.executeUpdate();
		  
		  return "Updated " + rowsUpdated + " row(s)";
	  }
	  catch(Exception e){
		  return "Error adding consultation: " + e;
	  }
	  
  }
  
  @Command
  public void updateConsultation() {
	  
  }
  
  
  @Command(description="Update nurse information, including name and password. Usage:")
  public String updateNurse(
		  @Param(name="password", description = "New password") String password,
		  @Param(name="nurseName", description = "New name") String nurseName, 
		  @Param(name="nurseId", description = "Nurse ID") int nurseId) throws SQLException{
	  
	  PreparedStatement updateNurse = null;
	  
	  String updateString = "UPDATE Nurse SET password = ?, nurseName = ? WHERE NurseId = ?";
	  
	  try {
		  updateNurse = connection.prepareStatement(updateString);
		  updateNurse.setString(1, password);
		  updateNurse.setString(2,  nurseName);
		  updateNurse.setInt(3,  nurseId);
		  
		  int numRowsUpdated = updateNurse.executeUpdate();
		  return "Updated " + numRowsUpdated + " row(s)";
	  }
	  catch(SQLException e){
		  return "Error updating information for nurse with ID: " + nurseId + ". " + e;
	  }
	  finally {
		  if(updateNurse != null){
			  updateNurse.close();
		  }
	  }
  }
  
  @Command
  public void viewAppointments(String studentId) {
	  // TODO viewAppointments
  }
  
  @Command
  public void checkStudentRecord(String studentId) {
    // TODO show past appointments and reasons for appointment
	  // TODO show consultations and notes
  }
  
  @Command
  public void showStudentsDoctors(String studentId) {
	  // TODO show list of doctors this student has seen
	  // the doctors specialization, and phone numbers
  }

}
