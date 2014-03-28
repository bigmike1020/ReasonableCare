import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
//import java.sql.Timestamp;
//import java.sql.Date;

import asg.cliche.Command;

public class StaffShell {

  /**
   * Connection object used to create Statements. This shell doesn't own the
   * connection; no need to close.
   */
  final Connection connection;

  public StaffShell(Connection connection) {
    this.connection = connection;
  }

  @Command
  public Object createStudent(String name, String password,
      String startingDate) throws SQLException {

    String sql = "insert into student(studentName,password,startingDate) values(?,?,?)";

    // Create a statement instance that will be sending
    // your SQL statements to the DBMS
    try (PreparedStatement stm = connection.prepareStatement(sql)) {
    	
      stm.setString(1, name);
      stm.setString(2, password);
      stm.setDate(3, java.sql.Date.valueOf(startingDate));
      int id = stm.executeUpdate();
      
      return "Created new student with id " + id;
    }
  }
}
