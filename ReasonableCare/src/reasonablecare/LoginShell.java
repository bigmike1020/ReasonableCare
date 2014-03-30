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
  public String login(int id, String password) throws IOException {
    if (1000 <= id && id < 2000) {
      loginStudent(id, password);
    } else if (2000 <= id && id < 3000) {
      loginDoctor(id, password);
    } else if (4000 <= id && id < 5000) {
      loginNurse(id, password);
    } else if (5000 <= id && id < 6000) {
      loginStaff(id, password);
    } else {
      return "Invalid id. Not in any user class range.";
    }
    
    return "Back at login shell.";
  }

  private void loginStudent(int id, String password) throws IOException {

    // TODO verify id/password is correct

    factory.createSubshell(loginShell, "student",
        new StudentShell(connection, id)).commandLoop();
  }

  private void loginStaff(int id, String password) throws IOException {

    // TODO verify id/password is correct

    factory.createSubshell(loginShell, "staff", new StaffShell(connection, id))
        .commandLoop();
  }

  private void loginDoctor(int id, String password) throws IOException {

    // TODO verify id/password is correct

    factory.createSubshell(loginShell, "doctor",
        new DoctorShell(connection, id)).commandLoop();
  }

  private void loginNurse(int id, String password) throws IOException {

    // TODO verify id/password is correct

    factory.createSubshell(loginShell, "nurse", new NurseShell(connection, id))
        .commandLoop();
  }

}
