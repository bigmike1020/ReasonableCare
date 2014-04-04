package reasonablecare;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import asg.cliche.Command;

/**
 * Shell containing methods for the student user class
 *
 */
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
	public Table getDoctors() throws SQLException {
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
	public void checkVaccinations() {
		// TODO show the student mandatory vaccination info
	}

	@Command
	public void manageAppointments(){
		
		/*
		 * 
		 * Merging these into a manageAppointments (or view/manage) suite
		 * 
		@Command
		public void checkFutureAppointments() {
			// TODO list future appointments
		}
		
		@Command
		public void checkPastAppointments() {
			// TODO checkPastAppointments
		}
		
		@Command
		public void deleteAppointment() {
		// TODO deleteAppointment
		}
	
		*/
		
	}

	@Command
	public void updateStudent() {
		// TODO updateStudent
	}
/**
 * Interactive method to allow a student to make appointment by being prompted for 
 * the values needed.  Invalid input is mostly handled
 * 
 * @throws Exception
 */
	@Command
	public void makeAppointment() throws Exception {
		
		// TODO makeAppointment		
		
		java.sql.Timestamp apptTime;
		int apptDoc=0,menuSelection=0;
		String apptType, apptReason;
		boolean apptTypeSelected=false, validCreditCardEntered=false;
		
		//TODO check that student has insurance information
		
		//prompt for appointment type and reason (if not physical/vaccination)
		while (!apptTypeSelected)
		{
			System.out.println("Select Appointment Type"
					+"\n1. Vaccination"
					+"\n2. Physical"
					+"\n3. Office Visit\n");
			try {
				menuSelection=Integer.parseInt(br.readLine().trim());
				if (menuSelection<1 || menuSelection>3)
			   	  System.out.println("Invalid Selection\n");
				else apptTypeSelected=true;
			      
			} catch (NumberFormatException e) {
		      System.out.println("Invalid Selection\n");
			}		
			if (apptTypeSelected){
			switch (menuSelection) {
		    case 1:
		    	apptType=apptReason="Vaccination"; break;
		    case 2:
		    	apptType=apptReason="Physical"; break;
			default:
				apptReason="Office Visit";
				System.out.println("Enter the reason for your appointment:");
				apptReason=br.readLine();
				//TODO control input >512 chars
			}}// end switch+if
		}//end while
		
		//select doctor for the appointment
		apptDoc=selectDoctor();
		
		//prompt for appointment time
		apptTime = selectDateTime(apptDoc);
		
		// Lots of prompts here
		// TODO use insurance to get copay
		// use insurance to get deductible
		// if deductible not paid,
		//     use billing to approve copay
		// if deductible paid,
		//     don't bill
		
		
		/* saving later for the statement
		String makeAppt = "insert into appointment(reasonForVisit,type,appointmentTime,"
				+ "doctorNotes, cost) values(?,?,?,?,?)";
		
		try (PreparedStatement stm = connection.prepareStatement(makeAppt,
		        new String[] { "AppointmentID" })) {

		      stm.setString(1, reasonForVisit);
		      stm.setString(2, apptType);
		      //TODO send as timestamp
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
		      
		 String associateAppt = "insert into makesAppointment(studentID,doctorID,apptID)"
		 	+ "values (?,?,?)";
		 	
		      
		 try (PreparedStatement stm1 = connection.prepareStatement(associateAppt){
		 
		 	stm1.setString(1, studentID)
		 	stm1.setString(2, doctorID)
		 	stm1.setString(3, appointmentID)
		 	
		 	...
		 	
		      return "Created new Appointment";*/
		
	}

	/**
	 * Show the available appointment times for a doctor on a given date
	 * @param doctorID
	 * @param date
	 * @return appointmentTable with Available Times
	 * @throws Exception
	 */
	@Command
	public Object showAvailableTimes(String doctorID, String date) throws Exception {
		
		java.sql.Date apptDate;
		int docID;
		
		//validate input and ensure valid doctor and date
		if (!validateDoctorID(doctorID))
			return "Invalid Doctor ID";
		else 
			docID=Integer.parseInt(doctorID);
		
		try{
			apptDate = java.sql.Date.valueOf(date);
		}
		catch (IllegalArgumentException e){
			return "Invalid Date Format: Must be YYYY-MM-DD";
		}
		
		//set a string equal to the day of week for the date
	      String dayName = String.format("%tA", apptDate);
	      
	    //system is closed on Sundays.
	    if (dayName.equals("Sunday"))
	    	return "The Health Center is Closed on Sundays";
		
		//Build Table of available Times 
		Table appointmentTable = new Table ("Available Appointment Times on "+ apptDate);
		
		//return appointments for a specific doctor on a specific date
		String sql="select a.appointmenttime from (appointment a natural join makesappointment ma) "
				+ "where ma.doctorID= ? and to_char(a.appointmenttime, 'YYYY-MM-DD') = ?";
		
		try (PreparedStatement stm = connection.prepareStatement(sql,
		        new String[] { "AppointmentTime" })) {

		      stm.setInt(1, docID);
		      stm.setString(2, date);
		      ResultSet rs = stm.executeQuery();
		      
		      //import result set into an ArrayList
		      ArrayList<java.sql.Timestamp> scheduledAppointments = 
		    		  new ArrayList<java.sql.Timestamp>();
		      while (rs.next()) 
		      {
		    	  scheduledAppointments.add(rs.getTimestamp(1));
		      }
		           
		      //Build AppointmentTable
		      
		      java.sql.Timestamp currentTime;
		      if (dayName.equals("Saturday")) //open 10-2
		      {
		    	  for (int hour=10; hour<14; hour++)
					{
						for (int minute=0; minute<31; minute+=30)
						{
							currentTime = java.sql.Timestamp.valueOf(apptDate+" "+hour+":"+minute+":00");
							if (!scheduledAppointments.contains(currentTime))
								appointmentTable.add(currentTime);
							else scheduledAppointments.remove(currentTime);
						}
					}
		      }
		      else //weekday - open 8-5
		      {
		    	  for (int hour=8; hour<17; hour++)
					{
						for (int minute=0; minute<31; minute+=30)
						{
							currentTime = java.sql.Timestamp.valueOf(apptDate+" "+hour+":"+minute+":00");
							if (!scheduledAppointments.contains(currentTime))
								appointmentTable.add(currentTime);
							else scheduledAppointments.remove(currentTime);
						}
					}
		      }
		}
		
		return appointmentTable;
	}
	
	/**
	 * Interactive system of prompts to allow a user to select a doctor from the DB.  
	 * 
	 * Gives the option to show a list of available doctors
	 * 
	 * @return selected DoctorID
	 * @throws Exception 
	 */
	public int selectDoctor() throws Exception
	{
		String doctorID;
		int menuSelection=0;
		
		//loops through prompts; for use handling invalid input without exiting
		while (true)
		{
			System.out.println("Select a doctor: "
				+ "\n1. Enter Doctor ID"
				+ "\n2. View List of Doctors"					
				+ "\n3. Exit Program\n");
			
			try {
				menuSelection=Integer.parseInt(br.readLine().trim());
				if (menuSelection<1 || menuSelection>3)
			   	  System.out.println("Invalid Selection\n");
			      
			} catch (NumberFormatException e) {
		      System.out.println("Invalid Selection\n");
			}	
		
		switch (menuSelection) {
		//Allow user to enter a doctorID
		    case 1:
		    	System.out.println("Enter Doctor ID: ");
		    	
		    	doctorID=br.readLine().trim();
		    	
		    	if (!validateDoctorID(doctorID))
		    	{
		    		System.out.println("Invalid Doctor ID\n");
		    		break;
		    	}
		    	else return Integer.parseInt(doctorID);
		    	
		    //Allow user to view a list of doctors and then enter a doctorID
		     case 2:
		    	 System.out.println(getDoctors());
		    	 System.out.println("Enter DoctorID from List: ");
		    	 
		    	 doctorID=br.readLine().trim();
		    	 
		    	 if (!validateDoctorID(doctorID))
			    	{
			    		System.out.println("Invalid Doctor ID\n");
			    		break;
			    	}
			    	else return Integer.parseInt(doctorID);
		    	
			default:
				System.exit(0);
		    
		    }// end of switch
		}//end while
	}

	/**
	 * Interactive system of prompts to allow a student to select a valid
	 * time and date for an appointment with a given doctor.
	 * 
	 * @param doctorID
	 * @return timestamp showing the date and time of the appointment
	 * @throws Exception 
	 */
	public java.sql.Timestamp selectDateTime(int doctorID) throws Exception
	{
		String date="";
		java.sql.Date apptDate;
		java.sql.Time apptTime;
		int menuSelection=0;
		boolean dateSelected=false, runSelectionLoop=true;
		
		//loops through prompts; for use handling invalid input without exiting
		while (!dateSelected)
		{
			System.out.println("Enter the date for the appointment (YYYY-MM-DD): \n");

			date=(br.readLine().trim());
					
			try{
				apptDate = java.sql.Date.valueOf(date);
				dateSelected=true;
			}
			catch (IllegalArgumentException e){
				System.out.println("Invalid Date Format: Must be YYYY-MM-DD"); 
				}
			
			if (dateSelected)
			{
				System.out.println(showAvailableTimes(Integer.toString(doctorID),date));
				
				//Allow user to select a time
				while (runSelectionLoop)
				{
					System.out.println("Select an option: "
							+"\n1. Enter an available time from the list (HH:MM:SS)"
							+"\n2. Enter a different date"
							+"\n3. Exit system and log out\n");
					
					try {
						menuSelection=Integer.parseInt(br.readLine().trim());
						if (menuSelection<1 || menuSelection>3)
					   	  System.out.println("Invalid Selection\n");
						else runSelectionLoop=false;
					      
					} catch (NumberFormatException e) {
				      System.out.println("Invalid Selection\n");
					}
					
					if (!runSelectionLoop)
					{
						switch (menuSelection) {
						//Allow user to enter a time and validate it
						    case 1:
						    System.out.println("Enter an available time from the list (HH:MM:SS):");
						    
						    String time = br.readLine().trim();
						    
						    try{
						    	apptTime = java.sql.Time.valueOf(time);
						    }
						    catch (IllegalArgumentException e){
								System.out.println("Invalid Time Format: Must be HH:MM:SS"); 
								}
						    //get available times
						    ArrayList<java.sql.Timestamp> apptTimes = 
						    		getAvailableTimes(doctorID,date);
						    
						    if (apptTimes.contains(java.sql.Timestamp.valueOf
						    		(date+" "+time)))
						    {
						    	return java.sql.Timestamp.valueOf(date+" "+time);
						    }
						    else 
						    {
						    	System.out.println("Time not available for appointments");
						    	runSelectionLoop=true;
						   	}
						    	
						    break;
						    
						    //Go back through the previous menus
						     case 2:
						    	runSelectionLoop=false;
						    	dateSelected=false;
						    	break;
						    	
							default:
								System.exit(0);
						}
					}//end if
				}//end while selection
			}//end if date
		}//end while date	
		return null; //if error
	}
	
	/**
	 * Utility method to validate that a given int is a valid doctorID in the DB
	 * 
	 * @param docID
	 * @throws Exception
	 */
	private boolean validateDoctorID(String docID) throws Exception {

		int doctorID=0;
		
		//ensure the given doctor ID is an int
		try {
			doctorID=Integer.parseInt(docID);
		} catch (NumberFormatException e) {
			return false;
		}
		//check valid range
		if (doctorID<2000 || doctorID>2999)
			return false;
		
		//check if exists in DB
		String sql = "select 1 from doctor where doctorID=?";
		
		try (PreparedStatement stm = connection.prepareStatement(sql)) {

			stm.setInt(1,doctorID);

			ResultSet rs = stm.executeQuery();
			if (!rs.next()) {
				return false;
			}
			else return true;

		}
	}
	
	/**
	 * Method to return the avaialable appointment times for a given doctor 
	 * on a given date as an ArrayList
	 * 
	 * @param doctorID
	 * @param date
	 * @return ArrayList<java.sql.Timestamp> of available times for a doctor
	 * @throws Exception
	 */
	public ArrayList<java.sql.Timestamp> getAvailableTimes(int doctorID, String date) 
			throws Exception {
		
		java.sql.Date apptDate = java.sql.Date.valueOf(date);
		
		//set a string equal to the day of week for the date
	      String dayName = String.format("%tA", apptDate);
	      
	      //import result set into an ArrayList
	      ArrayList<java.sql.Timestamp> scheduledAppointments = 
	    		  new ArrayList<java.sql.Timestamp>();
	      ArrayList<java.sql.Timestamp> availableAppointments =
	    		  new ArrayList<java.sql.Timestamp>();
		
		//return appointments for a specific doctor on a specific date
		String sql="select a.appointmenttime from (appointment a natural join makesappointment ma) "
				+ "where ma.doctorID= ? and to_char(a.appointmenttime, 'YYYY-MM-DD') = ?";
		
		try (PreparedStatement stm = connection.prepareStatement(sql,
		        new String[] { "AppointmentTime" })) {

		      stm.setInt(1, doctorID);
		      stm.setString(2, date);
		      ResultSet rs = stm.executeQuery();
		      
		      while (rs.next()) 
		      {
		    	  scheduledAppointments.add(rs.getTimestamp(1));
		      }
		           
		      //Build AppointmentTable  
		      java.sql.Timestamp currentTime;
			  if (dayName.equals("Sunday")); //system is closed on Sundays.
		      else if (dayName.equals("Saturday")) //open 10-2
		      {
		    	  for (int hour=10; hour<14; hour++)
					{
						for (int minute=0; minute<31; minute+=30)
						{
							currentTime = java.sql.Timestamp.valueOf(date+" "+hour+":"+minute+":00");
							if (!scheduledAppointments.contains(currentTime))
								availableAppointments.add(currentTime);
						}
					}
		      }
		      else //weekday - open 8-5
		      {
		    	  for (int hour=8; hour<17; hour++)
					{
						for (int minute=0; minute<31; minute+=30)
						{
							currentTime = java.sql.Timestamp.valueOf(date+" "+hour+":"+minute+":00");
							if (!scheduledAppointments.contains(currentTime))
								availableAppointments.add(currentTime);
						}
					}
		      }
		}
		
		return availableAppointments;
	}
}
