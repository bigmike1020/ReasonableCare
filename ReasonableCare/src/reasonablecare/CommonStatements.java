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
import java.util.ArrayList;
import java.util.Calendar;

public class CommonStatements implements AutoCloseable {

  private static BufferedReader br = new BufferedReader(new InputStreamReader(
      System.in));

  private static Statement stm;
  
  Connection connection;

  public CommonStatements(Connection connection) throws SQLException {
    // Create a statement instance that will be sending your SQL statements
    // to the DBMS
	this.connection=connection;
    stm = connection.createStatement();
    //connection.setAutoCommit(true); //commented out due to SQL error
  }

  @Override
  public void close() throws SQLException {
    if (stm != null) {
      stm.close();
    }
  }

  public void updatenurseinformation(int z) throws IOException, SQLException {
    int userid2 = z;
    int y;
    String b;
    out.println("Enter the attribute to be updated: \n1.Name \n2. Password");
    y = Integer.parseInt(br.readLine());
    out.println("Enter the changed value");
    b = (br.readLine());
    if (b.isEmpty()) {
      out.println("invalid input");
      updatenurseinformation(userid2);
    }

    switch (y) {
    case 1:
      stm.executeUpdate("update Nurse set nursename =' " + b
          + " 'where nurseid=" + z);
      out.println("Record Updated");
      printNurseInfo(z);
      break;
    case 2:

      stm.executeUpdate("update nurse set password =' " + b
          + " 'where nurseid=" + z);
      out.println("Record Updated");
      printNurseInfo(z);

      break;
    }// end of switch
  }

  private void printNurseInfo(int nurseId) throws SQLException {
    out.println("Your updated details are as shown:");

    // get the details of the student whose record is updated
    try (ResultSet result = stm
        .executeQuery("SELECT * FROM nurse WHERE nurseid=" + nurseId + "")) {

      while (result.next()) {
        out.println("ID: " + result.getString("nurseid"));
        out.println("Name: " + result.getString("nursename"));
        out.println("Password: " + result.getString("password"));
      }
    }
  }

  public void updatedoctorinformation(int z) throws IOException, SQLException {
    int userid2 = z;
    int y;
    String b;
    out.println("Enter the attribute to be updated: \n1.Name \n2.Password\n3.Specialization\n4.Phone Number");
    y = Integer.parseInt(br.readLine());
    out.println("Enter the changed value");
    b = (br.readLine());
    if (b.isEmpty()) {
      out.println("invalid input");
      updatestudentinformation(userid2);
    }

    switch (y) {
    case 1:
      stm.executeUpdate("update doctor set doctorname ='" + b
          + "' where doctorid=" + z);
      out.println("Record Updated");
      printDoctorInfo(z);
      break;

    case 2:
      stm.executeUpdate("update doctor set PASSWORD ='" + b
          + " ' where doctorid=" + z);
      out.println("Record Updated");
      printDoctorInfo(z);
      break;
    case 3:
      stm.executeUpdate("update doctor set Specialization ='" + b
          + "' where doctorid=" + z);
      out.println("Record Updated");
      printDoctorInfo(z);
      break;
    case 4:
      stm.executeUpdate("update doctor set phonenumber ='" + b
          + "' where doctorid=" + z);
      out.println("Record Updated");
      printDoctorInfo(z);
      break;
    }// end of switch
  }

  private void printDoctorInfo(int doctorId) throws SQLException {
    out.println("Your updated details are as shown:");

    // get the details of the student whose record is updated
    try (ResultSet result = stm
        .executeQuery("SELECT * FROM doctor WHERE doctorid=" + doctorId + "")) {

      while (result.next()) {
        out.println("ID: " + result.getString("doctorid"));
        out.println("Name: " + result.getString("doctorname"));
        out.println("Password: " + result.getString("password"));
        out.println("Specialization: " + result.getString("specialization"));
        out.println("Phone Number: " + result.getString("Phonenumber"));
      }
    }
  }

  public void updatestaffinformation(int z) throws IOException, SQLException {
    int userid2 = z;
    int y;
    String b;
    out.println("Enter the attribute to be updated: \n1.Name \n2. Password");
    y = Integer.parseInt(br.readLine());
    out.println("Enter the changed value");
    b = (br.readLine());
    if (b.isEmpty()) {
      out.println("invalid input");
      updatestaffinformation(userid2);
    }

    switch (y) {
    case 1:
      stm.executeUpdate("update staff set staffname =' " + b
          + " 'where staffid=" + z);
      out.println("Record Updated");
      printStaffInfo(z);
      break;
    case 2:

      stm.executeUpdate("update staff set password =' " + b
          + " 'where staffid=" + z);
      out.println("Record Updated");
      printStaffInfo(z);

      break;
    }// end of switch
  }

  private void printStaffInfo(int staffId) throws SQLException {
    out.println("Your updated details are as shown:");

    // get the details of the student whose record is updated
    try (ResultSet result = stm
        .executeQuery("SELECT * FROM staff WHERE staffid=" + staffId + "")) {

      while (result.next()) {
        out.println("ID: " + result.getString("staffid"));
        out.println("Name: " + result.getString("staffname"));
        out.println("Password: " + result.getString("password"));
      }
    }
  }

  public void updatestudentinformation(int z) throws IOException, SQLException {
    int userid2 = z;
    int y;
    String b;
    out.println("Enter the attribute to be updated: \n1.Name \n2.HealthInsurance Provider Name\n3.HealthInsurance Provider Number \n4.Password\n5.Starting semester");
    y = Integer.parseInt(br.readLine());
    out.println("Enter the changed value");
    b = (br.readLine());
    if (b.isEmpty()) {
      out.println("invalid input");
      updatestudentinformation(userid2);
    }

    switch (y) {
    case 1:
      stm.executeUpdate("update Student set studentname =' " + b
          + " 'where studentid=" + z);
      out.println("Record Updated");
      printStudentInfo(z);
      break;
    case 2:

      stm.executeUpdate("update Student set HEALTHINSURANCEPROVIDERNAME =' "
          + b + " 'where studentid=" + z);
      do {
        out.println("Enter the HEALTHINSURANCE POLICY NUMBER");
        b = (br.readLine());
      } while (b.isEmpty());
      stm.executeUpdate("update Student set HEALTHINSURANCEPOLICYNUMBER =' "
          + b + " 'where studentid=" + z);
      out.println("Record Updated");
      printStudentInfo(z);

      break;
    case 3:
      stm.executeUpdate("update Student set HEALTHINSURANCEPOLICYNUMBER =' "
          + b + " 'where studentid=" + z);
      out.println("Record Updated");
      printStudentInfo(z);
      break;
    case 4:
      stm.executeUpdate("update Student set PASSWORD =' " + b
          + " 'where studentid=" + z);
      out.println("Record Updated");
      printStudentInfo(z);
      break;
    case 5:
      stm.executeUpdate("update Student set startingdate =' " + b
          + " 'where studentid=" + z);
      out.println("Record Updated");
      printStudentInfo(z);
      break;
    }// end of switch
  }// end of updatestudentinformation

  private void printStudentInfo(int studentId) throws SQLException {
    out.println("Your updated details are as shown:");

    // get the details of the student whose record is updated
    try (ResultSet result = stm
        .executeQuery("SELECT * FROM student WHERE studentid=" + studentId + "")) {

      while (result.next()) {
        out.println("ID: " + result.getString("studentID"));
        out.println("Name: " + result.getString("studentname"));
        out.println("Password: " + result.getString("password"));
        out.println("HealthInsurance Provider Name: "
            + result.getString("HEALTHINSURANCEPROVIDERNAME"));
        out.println("HealthInsurance Provider Number: "
            + result.getString("HEALTHINSURANCEPOLICYNUMBER"));
        out.println("Starting semester: " + result.getString("startingdate"));
      }
    }
  }
  
  /**
   * Print Table of doctors
   * @return Table of doctors with their specializations, names, and doctorIDs
   * @throws SQLException
   */
  
  public Table getDoctors() throws SQLException {
		try (Statement statement = connection.createStatement()) {

		// Get records from the Student table
		try (ResultSet rs = statement
				.executeQuery("SELECT specialization, doctorID, doctorName "							+ "FROM Doctor ORDER BY specialization, doctorID")) {

			Table table = new Table("Specialization", "Doctor ID",
					"Doctor Name");

			while (rs.next()) {
				table.add(rs.getString(1), rs.getInt(2), rs.getString(3));
			}
			
			return table;
		}
	}
}
  
  /**
   * Creates an appointment in the db given all of the information
   * 
   * @throws SQLException 
   * 
   */
  public Object makeAppointment(int studentID, int doctorID, String type, String reason,
		  java.sql.Timestamp time, int cost) throws SQLException
  {
	  String makeAppt = "insert into appointment(reasonForVisit,type,appointmentTime,"
				+ "doctorNotes, cost) values(?,?,?,?,?)";
	  
	  PreparedStatement prepStmt = null;
	  ResultSet rs = null;
	  
		 int apptID = 0;
		 
		try {
			
			prepStmt = connection.prepareStatement(makeAppt,
			        new String[] { "AppointmentID" });

		      prepStmt.setString(1, reason);
		      prepStmt.setString(2, type);
		      prepStmt.setTimestamp(3, time); 
		      prepStmt.setString(4, "");
		      prepStmt.setInt(5, 0);
		      prepStmt.executeUpdate();
		      
		      rs = prepStmt.getGeneratedKeys();
		      if (rs != null && rs.next()) {
		        apptID = rs.getInt(1);
		      }
		}
		finally{
			close(prepStmt);
			close(rs);
		}
		
		String associateAppt = "insert into makesAppointment(studentID,doctorID,appointmentID)"
			 	+ "values (?,?,?)";
		
		try {
			prepStmt = connection.prepareStatement(associateAppt);
			 
			prepStmt.setInt(1, studentID);
			prepStmt.setInt(2, doctorID);
			prepStmt.setInt(3, apptID);
			prepStmt.executeUpdate();
			 	
			return "Created new Appointment with id = "+apptID+"\n";
		}
		finally{
			close(prepStmt);
		}
  }
  
  /**
   * Show the available appointment times for a given doctor on a given date
   * @param doctorID
   * @param date YYYY-MM-DD
   * @return appointment Table
   * @throws Exception
   */
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
  
  
  private void close(ResultSet rs) {
	  if(rs != null) {
          try { 
          rs.close(); 
          } catch(Throwable whatever) {}
      }
	
}

private void close(PreparedStatement prepStmt) {
	  if(prepStmt != null) {
          try { 
          prepStmt.close(); 
          } catch(Throwable whatever) {}
      }
	
}


/**
 * Determines if student is eligible for a free physical
 * Students get one free physical year year.
 * 
 * @param studentID
 * @param date proposed date of appointment
 */
public boolean freePhysicalEligibility(int studentID, String date)
{
	return true;
}

public boolean validateStudentID(String stID) throws SQLException
{
	int studentID=0;
	
	//ensure the given studentID
	try {
		studentID=Integer.parseInt(stID);
	} catch (NumberFormatException e) {
		return false;
	}
	//check valid range
	if (studentID<1000 || studentID>1999)
		return false;
	
	//check if exists in DB
	String sql = "select 1 from student where studentID=?";
	
	try (PreparedStatement stm = connection.prepareStatement(sql)) {

		stm.setInt(1,studentID);

		ResultSet rs = stm.executeQuery();
		if (!rs.next()) {
			return false;
		}
		else return true;

	}
}

/**
	 * Utility method to validate that a given int is a valid doctorID in the DB
	 * 
	 * @param docID
	 * @throws Exception
	 */
	public boolean validateDoctorID(String docID) throws Exception {

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
	/*	
	
	public boolean checkFreePhysical(String studentID, String date) throws SQLException
	{
		if (!validateStudentID(studentID))
			return false;
		else
		{
			try (PreparedStatement stm = connection.prepareStatement(sql,
			        new String[] { "AppointmentTime" })) {

			      stm.setInt(1, doctorID);
			      stm.setString(2, date);
			      ResultSet rs = stm.executeQuery();
			      
			      while (rs.next()) 
			      {
			    	  scheduledAppointments.add(rs.getTimestamp(1));
			      }
			}
		}
		return true;
	}*/
	
	
	private static void FindSpecialist() throws IOException, SQLException {
		 ResultSet result = null;
		//static Statement statement = null;

		String specialization="";
			int aname1=0;
			do{
					System.out.println("Enter the reason of your visit \n1.Diabetes \n2.FluShots \n3.General Medical Problems \n4.Mental Health \n5.Orthopedics \n6.Physical Therapy \n7.Women's Health\n8.Urinary, Genital Problems \n9.HIV Testing \10.Ear, Nose, Throat Problems \n11.Heart related Problems \n12.Vaccination");
					aname1 = Integer.parseInt(br.readLine());
				}while(aname1>=13);
			
			
			switch(aname1)
			{
			case 1:
				specialization="Endocrinologist";
				break;
			case 2:
				specialization="General Physician";
				break;
			case 3:
				specialization="General Physician";
				break;
			case 4:
				specialization="Psychiatrist";
				break;
			case 5:
				specialization="Orthopedic Surgeon";
				break;
			case 6:
				specialization="Physical Therapist";
				break;
			case 7:
				specialization="Gynaceologist";
				break;
			case 8:
				specialization="Nephrologist";
				break;
			case 9:
				specialization="General Physician";
				break;
			case 10:
				specialization="ENT specialist";
				break;
			case 11:
			
				specialization="Cardiologist";
				break;
			case 12:
				specialization="General Physician";
				break;
				}
			System.out.println(specialization+":");
			result = stm.executeQuery("SELECT doctorname,doctorid FROM doctor WHERE SPECIALIZATION='"+specialization+"'");	
			if(result.next())
			{
				do
				{
					System.out.println(result.getInt("doctorid")+"          "+result.getString("doctorname"));
				}while (result.next());
			}		
		}

	/*
	 * Create statements are only performed by Staff - Have methods in staff shell
	 * 
	 * 
	  public void createStudent() throws IOException, SQLException {
		    String sname = "";
		    while (sname.isEmpty()) {
		      out.println("Enter your Name");
		      sname = (br.readLine());
		    }
		    String spassword = "";
		    while (spassword.isEmpty()) {
		      out.println("Choose a student health centre Password");
		      spassword = (br.readLine());
		    }
		    String startingsemester = "";
		    while (startingsemester.isEmpty()) {
		      out.println("Enter the starting semester");
		      startingsemester = (br.readLine());
		    }
		    out.println("Do you have a health insurance?\n1. Yes \n2. No");

		    ResultSet result;
		    int c = Integer.parseInt(br.readLine());
		    if (c == 1) {
		      String providerName = "";
		      while (providerName.isEmpty()) {
		        out.println("Enter your health insurance provider name");
		        providerName = br.readLine();
		      }
		      String policyNumber = "";
		      while (policyNumber.isEmpty()) {
		        out.println("Enter your health insurance provider name");
		        policyNumber = br.readLine();
		      }

		      stm.executeUpdate("INSERT INTO Student(studentName, password, healthInsuranceProviderName, healthInsurancePolicynumber, startingDate) values('"
		          + sname
		          + "' , '"
		          + spassword
		          + "' , '"
		          + providerName
		          + "', '"
		          + policyNumber + "', '" + startingsemester + "')");
		      result = stm.executeQuery("SELECT studentid from student");
		    }// end of if
		    else {
		      stm.executeUpdate("INSERT INTO Student( studentName, password, startingDate) values('"
		          + sname + "' , '" + spassword + "' , '" + startingsemester + "')");
		      result = stm.executeQuery("SELECT studentid from student");
		    }
		    int ID1 = 0;
		    while (result.next()) {
		      ID1 = result.getInt("studentid");
		    }// end of while
		    out.println("Registration completed. Your Id is:" + ID1);
		  }

		  public void createNurse() throws IOException, SQLException {
		    String nname = "";
		    while (nname.isEmpty()) {
		      out.println("Enter your Name");
		      nname = (br.readLine());
		    }
		    String npassword = "";
		    while (npassword.isEmpty()) {
		      out.println("Choose a student health centre Password");
		      npassword = (br.readLine());
		    }
		    stm.executeUpdate("INSERT INTO Nurse( nurseName, password) values ('"
		        + nname + "','" + npassword + "')");
		    ResultSet result = stm.executeQuery("SELECT nurseid from nurse");
		    int ID2 = 0;
		    while (result.next()) {
		      ID2 = result.getInt("nurseid");
		    }// end of while
		    out.println("Registration completed. Your Id is:" + ID2);
		  }

		  public void createDoctor() throws IOException, SQLException {
		    String dname = "";
		    while (dname.isEmpty()) {
		      out.println("Enter your Name");
		      dname = (br.readLine());
		    }
		    String dpassword = "";
		    while (dpassword.isEmpty()) {
		      out.println("Choose a student health centre Password");
		      dpassword = (br.readLine());
		    }
		    String dphone = "";
		    while (dphone.isEmpty()) {
		      out.println("Enter your Phone number");
		      dphone = (br.readLine());
		    }
		    String dspecialization = "";
		    while (dspecialization.isEmpty()) {
		      out.println("Enter your Specialization");
		      dspecialization = (br.readLine());
		    }
		    stm.executeUpdate("INSERT INTO Doctor( doctorNAme, password, phonenumber, specialization) values ('"
		        + dname
		        + "','"
		        + dpassword
		        + "','"
		        + dphone
		        + "','"
		        + dspecialization
		        + "')");
		    ResultSet result = stm.executeQuery("SELECT doctorid from doctor");
		    int ID3 = 0;
		    while (result.next()) {
		      ID3 = result.getInt("doctorid");
		    }// end of while
		    out.println("Registration completed. Your Id is:" + ID3);
		  }

		  public void createStaff() throws IOException, SQLException {
		    String mname = "";
		    while (mname.isEmpty()) {
		      out.println("Enter your Name");
		      mname = (br.readLine());
		    }
		    String mpassword = "";
		    while (mpassword.isEmpty()) {
		      out.println("Choose a student health centre Password");
		      mpassword = (br.readLine());
		    }
		    stm.executeUpdate("INSERT INTO Staff(staffName, password) values ('"
		        + mname + "','" + mpassword + "')");
		    ResultSet result = stm.executeQuery("SELECT staffid from staff");
		    int ID4 = 0;
		    while (result.next()) {
		      ID4 = result.getInt("staffid");
		    }// end of while
		    out.println("Registration completed. Your Id is:" + ID4);
		  }
		  */
	private static void makeAppointment1(int z,String job)throws IOException, SQLException 
	{ ResultSet result = null;
		int studentid=0;
		//System.out.println(job);
		if(job.equalsIgnoreCase("Student"))
		{
			studentid=z;
		}
		else
		{
			System.out.println("Enter the student id");
			studentid=Integer.parseInt(br.readLine());
		}
		
		int atype1=0;
	do
	{
		System.out.println("Choose the type of your visit \n1.Physical \n2.Vaccination");
		atype1 = Integer.parseInt(br.readLine());
	}while(atype1>2);
	String atype="";
	if (atype1==2)
		atype="Vaccination";
	else
		atype="Physical";
	int aname1=0;
String aname="";
String specialization="";
	if (atype1==2)
		{
		aname="Vaccination";
		specialization="General Physician";
		}
	else
	{	
	do
		{
			System.out.println("Enter the reason of your visit \n1.Diabetes \n2.FluShots \n3.General Medical Problems \n4.Mental Health \n5.Orthopedics \n6.Physical Therapy \n7.Women's Health\n8.Urinary, Genital Problems \n9.HIV Testing \10.Ear, Nose, Throat Problems \n11.Heart related Problems ");
			aname1 = Integer.parseInt(br.readLine());
		}while(aname1>=12);}
	switch(aname1)
		{
	case 1:
		aname="Diabetes";
		specialization="Endocrinologist";
		break;
	case 2:
		aname="FluShots";
		specialization="General Physician";
		break;
	case 3:
		aname="General Medical Problems";
		specialization="General Physician";
		break;
	case 4:
		aname="Mental Health";
		specialization="Psychiatrist";
		break;
	case 5:
		aname="Orthopedics";
		specialization="Orthopedic Surgeon";
		break;
	case 6:
		aname="Physical Therapy";
		specialization="Physical Therapist";
		break;
	case 7:
		aname="Women's Health";
		specialization="Gynaceologist";
		break;
	case 8:
		aname="Urinary, Genital Problems";
		specialization="Nephrologist";
		break;
	case 9:
		aname="HIV Testing";
		specialization="General Physician";
		break;
	case 10:
		aname="Ear, Nose, Throat Problems";
		specialization="ENT specialist";
		break;
	case 11:
		aname="Heart related Problems";
		specialization="Cardiologist";
		break;
		}
	
	result = stm.executeQuery("SELECT doctorname,doctorid FROM doctor WHERE SPECIALIZATION='"+specialization+"'");	
	if(result.next())
	{
		do
		{
			System.out.println(result.getInt("doctorid")+"          "+result.getString("doctorname"));
		}while (result.next());
	}
	int ID=0;
	int flag=0;
	int doc=0;
	do{
	System.out.println("Select the id of the doctor you want to book appointment with"); 
	doc=Integer.parseInt(br.readLine());
	result = stm.executeQuery("SELECT doctorname,doctorid FROM doctor WHERE SPECIALIZATION='"+specialization+"'");	
	while (result.next()) 
	{
		ID=result.getInt("doctorid");
		if(doc==ID)
			{
			flag=1;
			break;
			}
	}
	if(flag==0)
	System.out.println("Please choose a valid doctor id");
	}while(flag!=1);
	}
}
// end of class HealthCentre
