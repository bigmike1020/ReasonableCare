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
    // TODO 
  }
}
