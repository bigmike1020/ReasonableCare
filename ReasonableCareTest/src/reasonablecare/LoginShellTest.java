package reasonablecare;

import static org.mockito.Mockito.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class LoginShellTest extends BaseShellTest {

  protected LoginShell mockedShell;

  @Before
  public void setUpShell() throws Exception {
    mockedShell = mock(LoginShell.class);
    
    // Return our connection. Couldn't be passed in through constructor.
    doReturn(connection.getConnection()).when(mockedShell).getConnection();
    
    // Don't create subshells. Those would run forever
    doNothing().when(mockedShell).createSubshell(anyString(),anyObject());
  }

  @After
  public void tearDownShell() throws Exception {
    mockedShell = null;
  }

  @Test
  public void testLoginStudent() throws Exception {
    mockedShell.loginStudent(1004);
    verify(mockedShell).createSubshell(eq("student"), any(StudentShell.class));
  }
}
