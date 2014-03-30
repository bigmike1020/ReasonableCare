package reasonablecare;

import java.sql.Connection;

public class NurseShell {

  Connection connection;
  int id;

  public NurseShell(Connection connection, int id) {
    this.connection = connection;
    this.id = id;
  }
}
