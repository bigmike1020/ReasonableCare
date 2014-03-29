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

  private Shell loginShell;

  public LoginShell(Connection connection) {
    this.connection = connection;
  }
  
  /**
   * This method is mocked in unit tests.
   */
  Connection getConnection() {
    return this.connection;
  }

  /**
   * Called by Cliche so this shell can create sub-shells.
   */
  public void cliSetShell(Shell theShell) {
    this.loginShell = theShell;
  }

  @Command
  public void loginStudent(int id) throws IOException {
    StudentShell student = new StudentShell(getConnection());
    createSubshell("student", student);
  }

  @Command
  public void loginStaff() throws IOException {
    StaffShell staff = new StaffShell(getConnection());
    createSubshell("staff", staff);
  }

  public void createSubshell(String name, Object shell) throws IOException {
    ShellFactory.createSubshell(name, loginShell, APP_NAME, shell).commandLoop();
  }

}
