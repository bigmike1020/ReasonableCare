package reasonablecare;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
//import java.sql.Timestamp;

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
    // second argument is the generated key that is to be returned
    try (PreparedStatement stm = connection.prepareStatement(sql, new String[]
    		{"StudentID"})) {
    	
      stm.setString(1, name);
      stm.setString(2, password);
      stm.setDate(3, java.sql.Date.valueOf(startingDate)); //date must be in YYYY-MM-DD format
      stm.executeUpdate();
      
      //get the auto-generated key from the row that was added
      int id=0;
      
      ResultSet rs = stm.getGeneratedKeys();
      if (rs != null && rs.next()) {
          id = rs.getInt(1);}
      
      return "Created new student with id "+ id;
    }
  }
}