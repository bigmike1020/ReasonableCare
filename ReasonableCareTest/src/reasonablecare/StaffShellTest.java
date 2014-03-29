package reasonablecare;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

import java.sql.SQLException;

import org.dbunit.DBTestCase;
import org.dbunit.dataset.IDataSet;
import org.junit.*;

public class StaffShellTest extends DBTestCase {

  StaffShell shell;
  
  @Before
  protected void setUp() throws Exception {
    super.setUp();
    
    shell = null;
  }

  @After
  protected void tearDown() throws Exception {

    shell = null;
    
    super.tearDown();
  }

  @Override
  protected IDataSet getDataSet() throws Exception {
    // TODO Auto-generated method stub
    return null;
  }
  
  @Test
  public void testGetDoctors() throws SQLException {
    Object result = shell.getDoctors();
    Object expected = "List of all DoctorIDs\n0: 4000\n";
    
    assertThat(result, equalTo(expected));
  }


}
