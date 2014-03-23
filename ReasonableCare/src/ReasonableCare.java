import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Entrance program for the Reasonable Care system
 */
public class ReasonableCare {

  private static final String jdbcURL = "jdbc:oracle:thin:@ora.csc.ncsu.edu:1521:orcl";

  // Put your oracle ID and password here
  private static final String user = "gshah@orcl";
  private static final String password = "001080029";

  /**
   * @param args
   *          Not used.
   */
  public static void main(String[] args) {

    try {
      // Loading the driver. This creates an instance of the driver
      // and calls the registerDriver method to make Oracle Thin
      // driver, at ora.csc.ncsu.edu, available to clients.
      Class.forName("oracle.jdbc.driver.OracleDriver");

      // Get a connection instance from the first driver in the
      // DriverManager list that recognizes the URL jdbcURL
      try (Connection connection = DriverManager.getConnection(jdbcURL, user,
          password)) {

        // Create a statement instance that will be sending
        // your SQL statements to the DBMS
        try (Statement statement = connection.createStatement()) {

        }
      }

    } catch (SQLException | ClassNotFoundException oops) {
      System.out.println("Exception running program.");
      oops.printStackTrace();

    }

  }

}
