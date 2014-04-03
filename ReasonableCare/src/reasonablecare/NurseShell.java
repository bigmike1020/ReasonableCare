package reasonablecare;

import java.sql.Connection;

import asg.cliche.Command;

public class NurseShell {

  Connection connection;
  int id;

  public NurseShell(Connection connection, int id) {
    this.connection = connection;
    this.id = id;
  }
  
  @Command
  public void addConsultation() {
    // TODO add consultation
  }
  
  @Command
  public void updateConsultation() {
	  // TODO updateConsultation
  }
  
  @Command
  public void updateNurse() {
	  // TODO updateNurse
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
