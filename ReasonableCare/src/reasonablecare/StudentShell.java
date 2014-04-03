package reasonablecare;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import asg.cliche.Command;

public class StudentShell {

	/**
	 * Connection object used to create Statements. This shell doesn't own the
	 * connection; no need to close.
	 */
	final Connection connection;

	final int id;

	public StudentShell(Connection connection, int id) {
		this.connection = connection;
		this.id = id;
	}

	@Command(description = "List all doctor specializations, and doctors that have those specializations.")
	public Table getSpecializations() throws SQLException {
		try (Statement statement = connection.createStatement()) {

			// Get records from the Student table
			try (ResultSet rs = statement
					.executeQuery("SELECT specialization, doctorID, doctorName FROM Doctor ORDER BY specialization, doctorID")) {

				Table table = new Table("Specialization", "Doctor ID",
						"Doctor Name");

				while (rs.next()) {
					table.add(rs.getString(1), rs.getInt(2), rs.getString(3));
				}

				return table;
			}
		}
	}

	@Command
	public Object getDoctors(String specialization) {
		// TODO getDoctors
		return null;
	}

	@Command
	public void showAvailableTimes(String doctorId, String date) {
		// TODO getFreeTime
	}

	@Command
	public void checkVaccinations() {
		// TODO show the student mandatory vaccination info
	}

	@Command
	public void checkFutureAppointments() {
		// TODO list future appointments
	}
	
	@Command
	public void checkPastAppointments() {
		// TODO checkPastAppointments
	}

	@Command
	public void updateStudent() {
		// TODO updateStudent
	}

	@Command
	public void makeAppointment() {
		// TODO makeAppointment
		
		// Lots of prompts here
		// TODO use insurance to get copay
		// use insurance to get deductible
		// if deductible not paid,
		//     use billing to approve copay
		// if deductible paid,
		//     don't bill
		
	}

	@Command
	public void deleteAppointment() {
		// TODO deleteAppointment
	}

}
