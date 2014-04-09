package reasonablecare;

import static java.lang.System.out;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import asg.cliche.Command;

public class DoctorShell {

	private final BufferedReader br = new BufferedReader(new InputStreamReader(
			System.in));

	private final Connection connection;

	private final int id;

	public DoctorShell(Connection connection, int id) {
		this.connection = connection;
		this.id = id;
	}

	@Command
	public String checkStudentRecord(String studId) throws Exception {
		int studentId;
		try {
			studentId = Integer.parseInt(studId);
		} catch (NumberFormatException e) {
			return "Student ID must be an integer.";
		}

		String sql = "select appointmenttime, type, reasonforvisit, doctorname, doctorNotes from appointment "
				+ "join makesappointment using(appointmentid) join doctor using(doctorid) "
				+ "where studentid=? and (appointmenttime < CURRENT_TIMESTAMP)";
		try (PreparedStatement stm = connection.prepareStatement(sql)) {
			stm.setInt(1, studentId);

			Table table = new Table("Date and Time", "Type", "Reason",
					"Doctor", "Notes");
			ResultSet rs = stm.executeQuery();
			while (rs.next()) {
				table.add(rs.getTimestamp(1), rs.getString(2), rs.getString(3),
						rs.getString(4), rs.getString(5));
			}

			out.println("Student's Past Appointments:");
			out.println(table);
		}

		sql = "select timeOfConsultation, nurseName, nurseNotes from Consultation "
				+ "join makesConsultation using(consultationId) join nurse using(nurseid) "
				+ "where studentid=? and (timeofconsultation < CURRENT_TIMESTAMP)";
		try (PreparedStatement stm = connection.prepareStatement(sql)) {
			stm.setInt(1, studentId);

			Table table = new Table("Date and Time", "Nurse", "Notes");
			ResultSet rs = stm.executeQuery();
			while (rs.next()) {
				table.add(rs.getTimestamp(1), rs.getString(2), rs.getString(3));
			}

			out.println("Student's Consultations:");
			out.println(table);
		}

		return "";
	}

	@Command
	public Table checkPastAppointments() throws SQLException {
		String sql = "select appointmentId, studentName, appointmenttime, type, reasonforvisit, doctornotes "
				+ "FROM Appointment join makesappointment using(appointmentid) join student using(studentid) "
				+ "WHERE doctorid=? AND (appointmenttime < CURRENT_TIMESTAMP)";

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
	public Table checkFutureAppointments() throws SQLException {
		String sql = "select appointmentId, studentName, appointmenttime, type, reasonforvisit, doctornotes "
				+ "FROM Appointment join makesappointment using(appointmentid) join student using(studentid) "
				+ "WHERE doctorid=? AND (appointmenttime > CURRENT_TIMESTAMP)";

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
	public String updateNotes(String appointmentId) throws Exception {
		int apptId;
		try {
			apptId = Integer.parseInt(appointmentId);
		} catch (NumberFormatException e) {
			return "Appointment ID must be an integer.";
		}

		String sql = "select doctornotes, doctorid from makesappointment natural join appointment where appointmentid=?";
		String currentNotes;
		try (PreparedStatement stm = connection.prepareStatement(sql)) {
			stm.setInt(1, apptId);

			ResultSet rs = stm.executeQuery();
			if (!rs.next()) {
				return "Appointment not found. Cannot update notes.";
			}

			if (rs.getInt(2) != id) {
				return "Appointment found, but for a different doctor. Cannot update notes.";
			}

			currentNotes = rs.getString(1);
		}

		out.println("Current notes: " + currentNotes);

		String newNotes = "";
		while (newNotes.isEmpty()) {
			out.println("Enter the updated notes:");
			newNotes = br.readLine().trim();
		}

		sql = "update appointment set doctornotes=? where appointmentid=?";
		try (PreparedStatement stm = connection.prepareStatement(sql)) {
			stm.setString(1, newNotes);
			stm.setInt(2, apptId);

			stm.executeUpdate();
		} catch (SQLException e) {
			return "Error updating notes: " + e.getMessage();
		}

		return "Appointment updated.";
	}

	@Command
	public void updateDoctor() throws SQLException, IOException {
		try (CommonStatements common = new CommonStatements(connection)) {
			common.updatedoctorinformation(id);
		}
	}
}
