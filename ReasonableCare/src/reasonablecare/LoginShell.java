package reasonablecare;

import java.io.IOException;
import java.sql.Connection;

import asg.cliche.Command;
import asg.cliche.Shell;
import asg.cliche.ShellDependent;

public class LoginShell implements ShellDependent {

  /**
   * Connection object used to create Statements. This shell doesn't own the
   * connection; no need to close.
   */
  Connection connection;

  private Shell loginShell;

  ShellFactory factory = new ShellFactory();

  public LoginShell(Connection connection) {
    this.connection = connection;
  }

  /**
   * This method is called in unit tests.
   */
  public void setFactory(ShellFactory f) {
    this.factory = f;
  }

  /**
   * Called by Cliche so this shell can create sub-shells.
   */
  public void cliSetShell(Shell theShell) {
    this.loginShell = theShell;
  }

  @Command
  public void loginStudent(int id, String password) throws IOException {

    // TODO verify id/password is correct

    factory.createSubshell(loginShell, "student",
        new StudentShell(connection, id)).commandLoop();
  }

  @Command
  public void loginStaff(int id) throws IOException {

    // TODO verify id/password is correct

    factory.createSubshell(loginShell, "staff", new StaffShell(connection, id))
        .commandLoop();
  }

  @Command
  public void loginDoctor(int id) throws IOException {

    // TODO verify id/password is correct

    factory.createSubshell(loginShell, "doctor",
        new DoctorShell(connection, id)).commandLoop();
  }

}
