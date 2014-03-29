package reasonablecare;

import java.io.IOException;
import java.sql.Connection;


import asg.cliche.Command;
import asg.cliche.Shell;
import asg.cliche.ShellDependent;
import asg.cliche.ShellFactory;

public class LoginShell implements ShellDependent {

  private static final String APP_NAME = ReasonableCare.APP_NAME;

  /**
   * Connection object used to create Statements. This shell doesn't own the
   * connection; no need to close.
   */
  final Connection connection;

  Object student, staff;

  private Shell theShell;

  public LoginShell(Connection connection) {
    this.connection = connection;
  }

  /**
   * Called by Cliche so this shell can create sub-shells.
   */
  public void cliSetShell(Shell theShell) {
    this.theShell = theShell;
  }

  @Command
  public void loginStudent() throws IOException {
    if (student == null) {
      student = new StudentShell(connection);
    }
    
    ShellFactory.createSubshell("student", theShell, APP_NAME, student).commandLoop();
  }
  
  @Command
  public void loginStaff() throws IOException {
    if (staff == null) {
      staff = new StaffShell(connection);
    }
    
    ShellFactory.createSubshell("staff", theShell, APP_NAME, staff).commandLoop();
  }


}
