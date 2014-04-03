package reasonablecare;

import java.sql.Connection;

import asg.cliche.Command;

public class DoctorShell {

  private final Connection connection;
  
  private final int id;
  
  public DoctorShell(Connection connection, int id) {
    this.connection = connection;
    this.id = id;
  }
  
  @Command
  public void checkStudentRecord(String studentId) {
    // TODO show past appointments and reasons for appointment
	  // TODO show consultations and notes
  }
  
  @Command
  public void checkPastAppointments() {
    // TODO list past appointments
  }
  
  @Command
  public void checkFutureAppointments() {
	  // TODO list future appointments
  }
  
  @Command
  public void updateNotes(String appointmentId) {
	  // TODO updateNotes
  }

  @Command
  public void updateDoctor() {
	  // TODO updateDoctor
  }
}
