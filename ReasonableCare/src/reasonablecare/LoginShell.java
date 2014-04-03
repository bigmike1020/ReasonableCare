package reasonablecare;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import asg.cliche.Command;
import asg.cliche.Shell;
import asg.cliche.ShellDependent;

public class LoginShell implements ShellDependent {

	private static class LoginFailedException extends Exception {
		private static final long serialVersionUID = 1L;

		public LoginFailedException(String msg) {
			super(msg);
		}
	}

	/**
	 * Connection object used to create Statements. This shell doesn't own the
	 * connection; no need to close.
	 */
	Connection connection;

	private Shell loginShell;

	ShellFactory factory = new ShellFactory();

	public LoginShell(Connection connection) {
		this.connection = connection;
	}

	/**
	 * This method is called in unit tests.
	 */
	public void setFactory(ShellFactory f) {
		this.factory = f;
	}

	/**
	 * Called by Cliche so this shell can create sub-shells.
	 */
	public void cliSetShell(Shell theShell) {
		this.loginShell = theShell;
	}

	@Command
	public String login(int id, String password) throws Exception {

		try {
			if (1000 <= id && id < 2000) {
				loginStudent(id, password);
			} else if (2000 <= id && id < 3000) {
				loginDoctor(id, password);
			} else if (4000 <= id && id < 5000) {
				loginNurse(id, password);
			} else if (5000 <= id && id < 6000) {
				loginStaff(Integer.toString(id), password);
			} else {
				return "Invalid id. Not in any user class range.";
			}
		} catch (LoginFailedException e) {
			return e.getMessage();
		}

		return "Back at login shell.";
	}

	private void loginStudent(int id, String password) throws Exception {

		String sql = "select 1 from Student where StudentID=? and password=?";

		// Create a statement instance that will be sending
		// your SQL statements to the DBMS
		try (PreparedStatement stm = connection.prepareStatement(sql)) {

			stm.setInt(1, id);
			stm.setString(2, password);

			ResultSet rs = stm.executeQuery();
			if (!rs.next()) {
				throw new LoginFailedException("Invalid student ID or password");
			}

		}

		factory.createSubshell(loginShell, "student",
				new StudentShell(connection, id)).commandLoop();
	}

	private String loginStaff(String idStr, String password) throws Exception {

		int id;
		try {
			id = Integer.parseInt(idStr);
		} catch (NumberFormatException ex) {
			return "Cannot parse id. Enter a number.";
		}

		String sql = "select 1 from Staff where staffID=? and password=?";

		// Create a statement instance that will be sending
		// your SQL statements to the DBMS
		try (PreparedStatement stm = connection.prepareStatement(sql)) {

			stm.setInt(1, id);
			stm.setString(2, password);

			ResultSet rs = stm.executeQuery();
			if (!rs.next()) {
				throw new LoginFailedException("Invalid staff ID or password");
			}

		}

		factory.createSubshell(loginShell, "staff",
				new StaffShell(connection, id)).commandLoop();
		return "";
	}

	private void loginDoctor(int id, String password) throws Exception {

		String sql = "select 1 from Doctor where doctorID=? and password=?";

		// Create a statement instance that will be sending
		// your SQL statements to the DBMS
		try (PreparedStatement stm = connection.prepareStatement(sql)) {

			stm.setInt(1, id);
			stm.setString(2, password);

			ResultSet rs = stm.executeQuery();
			if (!rs.next()) {
				throw new LoginFailedException("Invalid doctor ID or password");
			}

		}

		factory.createSubshell(loginShell, "doctor",
				new DoctorShell(connection, id)).commandLoop();
	}

	private void loginNurse(int id, String password) throws Exception {

		String sql = "select 1 from Nurse where NurseID=? and password=?";

		// Create a statement instance that will be sending
		// your SQL statements to the DBMS
		try (PreparedStatement stm = connection.prepareStatement(sql)) {

			stm.setInt(1, id);
			stm.setString(2, password);

			ResultSet rs = stm.executeQuery();
			if (!rs.next()) {
				throw new LoginFailedException("Invalid nurse ID or password");
			}

		}

		factory.createSubshell(loginShell, "nurse",
				new NurseShell(connection, id)).commandLoop();
	}

}
