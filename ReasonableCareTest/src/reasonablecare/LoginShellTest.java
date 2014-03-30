package reasonablecare;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import asg.cliche.Shell;

public class LoginShellTest extends BaseShellTest {

  private LoginShell shell;

  private ShellFactory mockedFactory;
  private Shell mockedSubshell;

  @Before
  public void setUpShell() throws Exception {
    shell = new LoginShell(getConnection());

    // Create a mock subshell for the loginshell to create
    mockedSubshell = mock(Shell.class);
    doNothing().when(mockedSubshell).commandLoop();

    // Create a mock ShellFactory that doesn't create subshells.
    mockedFactory = mock(ShellFactory.class);
    doReturn(mockedSubshell).when(mockedFactory).createSubshell(
        any(Shell.class), anyString(), any());

    shell.setFactory(mockedFactory);
  }

  @After
  public void tearDownShell() throws Exception {
    mockedFactory = null;
    mockedSubshell = null;
    shell = null;
  }

  @Test
  public void testLoginStudent() throws Exception {
    shell.loginStudent(1004, "Triangle");
    
    verify(mockedFactory).createSubshell(any(Shell.class), eq("student"), any(StudentShell.class));
    verify(mockedSubshell).commandLoop();
  }
}
