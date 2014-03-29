package reasonablecare;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import java.io.FileInputStream;
import java.sql.SQLException;

import org.dbunit.DBTestCase;
import org.dbunit.PropertiesBasedJdbcDatabaseTester;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class StaffShellTest extends DBTestCase {

  StaffShell shell;

  public StaffShellTest(String name) {
    super(name);
    System.setProperty(PropertiesBasedJdbcDatabaseTester.DBUNIT_DRIVER_CLASS,
        Constants.driverClass);
    System.setProperty(PropertiesBasedJdbcDatabaseTester.DBUNIT_CONNECTION_URL,
        Constants.jdbcURL);
    System.setProperty(PropertiesBasedJdbcDatabaseTester.DBUNIT_USERNAME,
        Constants.user);
    System.setProperty(PropertiesBasedJdbcDatabaseTester.DBUNIT_PASSWORD,
        Constants.password);
    // System.setProperty( PropertiesBasedJdbcDatabaseTester.DBUNIT_SCHEMA, ""
    // );
  }

  @Before
  protected void setUp() throws Exception {
    super.setUp();
    shell = new StaffShell(getConnection().getConnection());
  }

  @After
  protected void tearDown() throws Exception {
    shell = null;
    super.tearDown();
  }

  @Override
  protected IDataSet getDataSet() throws Exception {
    return new FlatXmlDataSetBuilder().build(new FileInputStream(
        "db/shell1.xml"));
  }

  @Test
  public void testGetDoctors() throws SQLException {
    Object result = shell.getDoctors();
    Object expected = "List of all DoctorIDs\n0: 4000\n";

    assertThat(result, equalTo(expected));
  }

}
