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
  public void checkStudentRecord() {
    // TODO
  }
  
  @Command
  public void checkAppointments() {
    // TODO list future appointments
  }

}
