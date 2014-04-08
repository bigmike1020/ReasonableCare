package reasonablecare;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import asg.cliche.Command;

public class DoctorShell {

	private final Connection connection;

	private final int id;

	public DoctorShell(Connection connection, int id) {
		this.connection = connection;
		this.id = id;
	}

	@Command
	public void checkStudentRecord(String studentId) {
		// TODO show past appointments and reasons for appointment
		// TODO show consultations and notes
	}

	@Command
	public Table checkPastAppointments() throws SQLException {
		String sql = "select appointmentId, studentName, appointmenttime, type, reasonforvisit, doctornotes "
				+ "FROM Appointment join makesappointment using(appointmentid) join student using(studentid) "
				+ "WHERE doctorid=? AND (appointmenttime < CURRENT_TIMESTAMP)";

		// Create a statement to enter staff into DB and return ID
		try (PreparedStatement stm = connection.prepareStatement(sql)) {
			stm.setInt(1, id);

			Table table = new Table("Appt ID", "Student", "Date and Time",
					"Appt Type", "Appt Reason", "Notes");
			ResultSet rs = stm.executeQuery();
			while (rs.next()) {
				table.add(rs.getInt(1), rs.getString(2), rs.getTimestamp(3),
						rs.getString(4), rs.getString(5), rs.getString(6));
			}

			return table;
		}
	}

	@Command
	public void checkFutureAppointments() {
		// TODO list future appointments
	}

	@Command
	public void updateNotes(String appointmentId) {
		// TODO updateNotes
	}

	@Command
	public void updateDoctor() {
		// TODO updateDoctor
	}
}
