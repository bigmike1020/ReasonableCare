import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import asg.cliche.Command;

public class StudentShell {

  final Connection connection;

  public StudentShell(Connection connection) {
    this.connection = connection;
  }

  @Command
  public Object getStudents() throws SQLException {

    // Create a statement instance that will be sending
    // your SQL statements to the DBMS
    try (Statement statement = connection.createStatement()) {

      // Get records from the Student table
      try (ResultSet result = statement
          .executeQuery("SELECT StudentID FROM Student")) {

        StringBuilder sb = new StringBuilder("List of all StudentIDs\n");

        int i = 0;
        while (result.next()) {
          String name = result.getString("StudentID");
          sb.append(i + ": " + name + "\n");
        }
        
        return sb.toString();
      }
    }
  }

}
