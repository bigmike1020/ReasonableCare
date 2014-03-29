package reasonablecare;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import java.io.FileInputStream;
import java.sql.SQLException;

import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.operation.DatabaseOperation;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class StaffShellTest {

  static IDatabaseConnection connection;
  StaffShell shell;

  public StaffShellTest() {
    super();
  }
  
  @BeforeClass
  public static void connect() throws Exception {
    connection = new DatabaseConnection(Constants.makeConnection());
    
  }
  
  @AfterClass
  public static void disconnect() throws Exception {
    connection.close();
    connection = null;
  }

  @Before
  public void setUp() throws Exception {
    shell = new StaffShell(connection.getConnection());

    IDataSet dataSet = getDataSet();
    DatabaseOperation.CLEAN_INSERT.execute(connection, dataSet);
  }

  @After
  public void tearDown() throws Exception {
    shell = null;
  }

  private IDataSet getDataSet() throws Exception {
    return new FlatXmlDataSetBuilder().build(new FileInputStream(
        "db/shell1.xml"));
  }

  @Test
  public void testGetDoctors() throws SQLException {
    Object result = shell.getDoctors();
    Object expected = "Doctor ID|Doctor Name\n2000     |Dr. Miller \n2001     |Dr. Singh  \n2002     |Dr. Healey \n2003     |Dr. William\n2004     |Dr. Rob    \n";

    String res = result.toString();
    assertThat(res, equalTo(expected));
  }

  @Test
  public void testGetStudents() throws SQLException {
    Table expected = new Table("Student ID", "Student Name");
    expected.add(1000, "Gati");
    expected.add(1001, "GDavid");
    expected.add(1002, "James");
    expected.add(1003, "Maria");
    expected.add(1004, "Mary");
    expected.add(1005, "MDaniel");
    expected.add(1020, "Randy");
    expected.add(1040, "donald");
    expected.add(1041, "PeeDee");
    expected.add(1043, "Bob");
    expected.add(1044, "Bob");
    expected.add(1045, "Bob");
    expected.add(1046, "walter");
    expected.add(1047, "Jane");
    expected.add(1048, "Parker");
    expected.add(1049, "evan");
    expected.add(1050, "Jim");
    expected.add(1051, "Jim");
    expected.add(1052, "Jim");
    expected.add(1053, "Jim");
    expected.add(1054, "Jim");
    expected.add(1055, "jim");
    expected.add(1056, "jim");
    expected.add(1057, "jim");

    Table result = (Table) shell.getStudents();

    assertThat(result, equalTo(expected));
  }

}
