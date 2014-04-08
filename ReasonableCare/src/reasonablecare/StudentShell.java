package reasonablecare;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;

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
	
	//insurance instances to contact insurance and credit card companies
	private InsuranceCompanySystem insurance = new InsuranceCompanySystem();
	private CreditCardSystem creditCard = new CreditCardSystem();

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
	@Command(description="Interactive way to make an appointment.  Prompts for all information"
			+ "needed.")
	public Object makeAppointmentInteractive() throws Exception {
		
		// TODO makeAppointment		
		
		java.sql.Timestamp apptTime;
		int apptDoc=0,menuSelection=0, cost=0;
		String apptType="", apptReason="", insuranceProvider, insuranceNumber, ccNumber;
		boolean apptTypeSelected=false, hasInsurance=false, 
				creditCardAccepted=false;
		
		//Check that student has insurance information
		
		while (!hasInsurance)
		{
			if (!checkHasInsurance(id))
			{
				System.out.println("You do not have insurance.  \nEnter an option from the menu below:"
						+"\n1. Provide Insurance Information"
						+"\n2. Exit System and Log out\n");
				try {
					menuSelection=Integer.parseInt(br.readLine().trim());
					if (menuSelection<1 || menuSelection>2)
				   	  System.out.println("Invalid Selection\n");
					else apptTypeSelected=true;
				      
				} catch (NumberFormatException e) {
			      System.out.println("Invalid Selection\n");
				}		
				if (apptTypeSelected){
				switch (menuSelection) {
			    case 1:
			    	//take insurance info
			    	System.out.println("Enter the name of your insurance provider:\n");
			    	insuranceProvider = br.readLine().trim();
			    	System.out.println("Enter your insurance policy number:\n");
			    	insuranceNumber = br.readLine().trim();
			    	
			    	String updateIns = "UPDATE STUDENT SET HEALTHINSURANCEPROVIDERNAME=?, HEALTHINSURANCEPOLICYNUMBER= ? "
			    			+ "WHERE STUDENTID=?";
			    	
			    	try (PreparedStatement stm = connection.prepareStatement(updateIns)) {
					      stm.setString(1, insuranceProvider);
					      stm.setString(2, insuranceNumber);
					      stm.setInt(3, id); 
					      stm.executeUpdate();
			    	}
			    	System.out.println("Insurance Information Accepted");
			    	hasInsurance=true;
			    	break;
			    	
				default:
					System.exit(0);
				}}// end switch+if
			}
			else hasInsurance=true;
		}
		
		//prompt for appointment type and reason (if not physical/vaccination)
		do
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
				apptType="Office Visit";
				System.out.println("Enter the reason for your appointment:");
				apptReason=br.readLine().trim();
				//TODO control input >512 chars
			}}// end switch+if
		} while (!apptTypeSelected);//end while
		
		//select doctor for the appointment
		apptDoc=selectDoctor();
		
		//prompt for appointment time
		apptTime = selectDateTime(apptDoc);
		
		//fetch insurance information
		insuranceProvider = getInsuranceProvider(id);
		insuranceNumber = getInsuranceNumber(id);
		
		//TODO allow student to get a free physical each year
		
		//get cost of appointment
		cost = insurance.getCopay(apptType, apptDoc, insuranceProvider, insuranceNumber);
		
		System.out.println("The copayment for your appointment will be: "+cost);
		
		if (insurance.getDeductiblePaid(insuranceProvider, insuranceNumber))
		{
			System.out.println("Your deductible has been paid for the year.  You will not be billed.");
		}
		else
		{
			System.out.println("Your deductible has not been paid for the year.");
			do{
				System.out.println("Enter your credit card number:");
				ccNumber = br.readLine().trim();
				//TODO handle credit card expiration date
				
				if (creditCard.validateCreditCard(ccNumber))
				{
					if (creditCard.getPreapproval(ccNumber))
					{
						creditCardAccepted=true;
						System.out.println("Your credit card was pre-approved.");
					}
					else
					{
						System.out.println("Your credit card was not pre-approved.  You will need to"
								+ "use a different card.");
						//TODO handle exit option for this loop
					}
				}
				else
					System.out.println("Invalid Credit Card Number");
				
			} while (!creditCardAccepted);
		}
		
		//Make the appointment
		
		String makeAppt = "insert into appointment(reasonForVisit,type,appointmentTime,"
				+ "doctorNotes, cost) values(?,?,?,?,?)";
		
		 int apptID = 0;
		 
		try (PreparedStatement stm = connection.prepareStatement(makeAppt,
		        new String[] { "AppointmentID" })) {

		      stm.setString(1, apptReason);
		      stm.setString(2, apptType);
		      stm.setTimestamp(3, apptTime); 
		      stm.setString(4, "");
		      stm.setInt(5, cost);
		      stm.executeUpdate();
		      
		      ResultSet rs = stm.getGeneratedKeys();
		      if (rs != null && rs.next()) {
		        apptID = rs.getInt(1);
		      }
		}  
		String associateAppt = "insert into makesAppointment(studentID,doctorID,appointmentID)"
			 	+ "values (?,?,?)";
		
		try (PreparedStatement stm1 = connection.prepareStatement(associateAppt)){
			 
			 	stm1.setInt(1, id);
			 	stm1.setInt(2, apptDoc);
			 	stm1.setInt(3, apptID);
			 	stm1.executeUpdate();
			 	
			 	return "Created new Appointment with id= "+apptID+"\n"
			 			+"You were billed for $"+cost;
			} 
		
	}

	/**
	 * Show the available appointment times for a doctor on a given date.  Times associated with 
	 * scheduled appointments or times in the past are not displayed.
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
		      
		    //Timestamp representing actual current time
		      java.util.Calendar calendar = Calendar.getInstance();
		      java.sql.Timestamp now = new java.sql.Timestamp(calendar.getTime().getTime());
		           
		      //Build AppointmentTable
		      
		      java.sql.Timestamp currentTime;
		      if (dayName.equals("Saturday")) //open 10-2
		      {
		    	  for (int hour=10; hour<14; hour++)
					{
						for (int minute=0; minute<31; minute+=30)
						{
							currentTime = java.sql.Timestamp.valueOf(apptDate+" "+hour+":"+minute+":00");
							if (!scheduledAppointments.contains(currentTime) 
									&& now.before(currentTime))
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
							if (!scheduledAppointments.contains(currentTime)
									&& now.before(currentTime))
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
		java.util.Calendar calendar = Calendar.getInstance();
		java.sql.Timestamp now = new java.sql.Timestamp(calendar.getTime().getTime());
		int menuSelection=0;
		boolean dateSelected=false, runSelectionLoop=true;
		
		//loops through prompts; for use handling invalid input without exiting
		do{
			System.out.println("Enter the date for the appointment (YYYY-MM-DD): \n");

			date=(br.readLine().trim());
				
			try{
				apptDate = java.sql.Date.valueOf(date);
				
				dateSelected = true;
			}
			catch (IllegalArgumentException e){
				System.out.println("Invalid Date Format: Must be YYYY-MM-DD"); 
				}
			
			if (dateSelected)
			{
				System.out.println(showAvailableTimes(Integer.toString(doctorID),date));
				
				//Allow user to select a time
				do
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
						    
						    java.sql.Timestamp apptTimestamp = 
					    			java.sql.Timestamp.valueOf(date+" "+time);
						
						    if (apptTimes.contains(apptTimestamp))
						    {
						    	if (apptTimestamp.after(now))
						    		return apptTimestamp;
						    	else
						    	{
						    		System.out.println("Appointment time cannot be in the past");
						    		runSelectionLoop=true;
						    	}
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
				}while (runSelectionLoop);//end while selection
			}//end if date
		}while (!dateSelected);	
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
		      
		      //Timestamp representing actual current time
		      java.util.Calendar calendar = Calendar.getInstance();
		      java.sql.Timestamp now = new java.sql.Timestamp(calendar.getTime().getTime());
		      
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
							if (!scheduledAppointments.contains(currentTime) && 
									now.before(currentTime))
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
	
	/**
	 * Check if free physical has been taken in the past year for a student
	 * 
	 */
	public boolean checkFreePhysical(int studentID)
	{
		//TODO Check if user has had a free physical in the past year
		return false;
	}
	
	/**
	 * Check if a student has insurance
	 * @param studentID
	 * @return true or false that the student has insurance
	 */
	public boolean checkHasInsurance(int studentID)
	{
		String sql = "select healthinsuranceprovidername,healthinsurancepolicynumber "
		+ "from student where studentID=?";
		
		try (PreparedStatement stm = connection.prepareStatement(sql)) {

			stm.setInt(1,studentID);

			ResultSet rs = stm.executeQuery();
			
			rs.next();
			
			if (rs.getString(1)==null || rs.getString(2)==null)
				return false;
			else return true;
			}
		    
		 catch (SQLException e) {
			// TODO Auto-generated catch block
			return false;
		}
	}
	
	/**
	 * Check return insuranceProvider of student
	 * @param studentID
	 * @return
	 */
	public String getInsuranceProvider(int studentID)
	{
		String sql = "select healthinsuranceprovidername from student where studentID=?";
		
		try (PreparedStatement stm = connection.prepareStatement(sql)) {

			stm.setInt(1,studentID);

			ResultSet rs = stm.executeQuery();
			
			rs.next();
			
			return rs.getString(1);
			}
		    
		 catch (SQLException e) {
			// TODO Auto-generated catch block
			return null;
		}
	}
	
	/**
	 * Get Insurance Policy Number from Student
	 * @param studentID
	 * @return
	 */
	public String getInsuranceNumber(int studentID)
	{
		String sql = "select healthinsurancepolicynumber from student where studentID=?";
		
		try (PreparedStatement stm = connection.prepareStatement(sql)) {

			stm.setInt(1,studentID);

			ResultSet rs = stm.executeQuery();
			
			rs.next();
			
			return rs.getString(1);
			}
		    
		 catch (SQLException e) {
			// TODO Auto-generated catch block
			return null;
		}
	}
	
}
