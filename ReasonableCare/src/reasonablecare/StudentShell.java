package reasonablecare;

import static java.lang.System.out;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import reasonablecare.LoginShell.LoginFailedException;
import asg.cliche.Command;

public class StudentShell {

	/**
	 * Connection object used to create Statements. This shell doesn't own the
	 * connection; no need to close.
	 */
	final Connection connection;

	final int id;
	
	final BufferedReader br = new BufferedReader(new InputStreamReader(
		      System.in));

	public StudentShell(Connection connection, int id) {
		this.connection = connection;
		this.id = id;
	}

	@Command(description = "List all doctor specializations, and doctors that have "
			+ "those specializations.")
	public Table getSpecializations() throws SQLException {
		try (Statement statement = connection.createStatement()) {

			// Get records from the Student table
			try (ResultSet rs = statement
					.executeQuery("SELECT specialization, doctorID, doctorName "
							+ "FROM Doctor ORDER BY specialization, doctorID")) {

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
	public Object getDoctorsBySpecialization(String specialization) {
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
	public void makeAppointment() throws Exception {
		
		// TODO makeAppointment
		
		
		String reasonForVisit="",apptTime="", apptDate="", apptType="";
		int apptDoc=0;
		
		//prompt for appointment information
		
		//TODO get Doctor for appointment
		
		apptDoc=selectDoctor();
		
		
		/*
		String makeAppt = "insert into appointment(reasonForVisit,type,appointmentTime,"
				+ "doctorNotes, cost) values(?,?,?,?,?)";
		
		try (PreparedStatement stm = connection.prepareStatement(makeAppt,
		        new String[] { "AppointmentID" })) {

		      stm.setString(1, reasonForVisit);
		      stm.setString(2, apptType);
		      stm.setString(3, apptTime);
		      stm.setString(4, "");
		      stm.setString(5, null);
		      stm.executeUpdate();

		      //get the auto-generated key from the row that was added
		      int apptID = 0;

		      ResultSet rs = stm.getGeneratedKeys();
		      if (rs != null && rs.next()) {
		        apptID = rs.getInt(1);
		      }

		      return "Created new Appointment";*/
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
	
	/**
	 * Prompt user to select a doctor.  Allow to enter doctorID or choose from list.
	 * @return selected DoctorID
	 * @throws Exception 
	 */
	public int selectDoctor() throws Exception
	{
		int menuSelection=0, doctorID=0;
		
		while (true)
		{
			System.out.println("Select a doctor: \n1. Enter Doctor ID"
				+ "\n2. View List of Doctors"					+ "\n3. Exit Program\n");
			try {
				menuSelection=Integer.parseInt(br.readLine().trim());
				if (menuSelection<1 || menuSelection>3)
			   	  System.out.println("\nInvalid Selection\n");
			      
			} catch (NumberFormatException e) {
		      System.out.println("\nInvalid Selection\n");
			}	
		
		switch (menuSelection) {
		    case 1:
		    	
		    	System.out.println("Enter Doctor ID: ");
		    	
		    	try {
				      doctorID=Integer.parseInt(br.readLine().trim());
				      if (doctorID<2000 || doctorID>2999 || !validateDoctorID(doctorID))
				      {
				    	  System.out.println("Invalid Doctor ID\n");
				    	  break;
				      }
				      
				} catch (NumberFormatException e) {
				    	  System.out.println("Invalid Doctor ID\n");
				    	  break;
				}
		    	
		    	return doctorID;
		      
		     case 2:
		    	
		    	 System.out.println(getSpecializations());
		    	 
		    	 System.out.println("Enter DoctorID from List: ");
		    	 
		    	 try {
				      doctorID=Integer.parseInt(br.readLine().trim());
				      if (doctorID<2000 || doctorID>2999 || !validateDoctorID(doctorID))
				      {
				    	  System.out.println("Invalid Doctor ID\n");
				    	  break;
				      }
				      
				} catch (NumberFormatException e) {
					 System.out.println("Invalid Doctor ID\n");
			    	  break;
				}
		    	
		    	return doctorID;
		    	
			default:
				
				System.exit(0);
		    
		    }// end of switch
		}//end while
	}
	
	/**
	 * Check if a doctor exists in the DB with a given doctorID
	 * @param docID the doctorID to be checked
	 * @throws Exception if there is no doctor
	 */
	private boolean validateDoctorID(int docID) throws Exception {

		String sql = "select 1 from doctor where doctorID=?";

		try (PreparedStatement stm = connection.prepareStatement(sql)) {

			stm.setInt(1,docID);

			ResultSet rs = stm.executeQuery();
			if (!rs.next()) {
				return false;
			}
			else return true;

		}
	}
}
