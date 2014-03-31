package reasonablecare;

import java.io.*;
import java.sql.*;



public class HealthCentre
{
  private static final String jdbcURL = "jdbc:oracle:thin:@ora.csc.ncsu.edu:1521:orcl";

  // Put your oracle ID and password here
  private static final String user = "gshah";  
  private static final String password = "001080029";

  private static BufferedReader br = new BufferedReader (new InputStreamReader (System.in));

  static Connection connection = null;
  static Statement statement = null;
  static ResultSet result = null;
  static int userid;
  static String pass;
  static String job;

  public static void main(String[] args)
  {
    try 
    { // try 1

      // Loading the driver. This creates an instance of the driver and calls the registerDriver method to make Oracle Thin driver, at ora.csc.ncsu.edu, available to clients.
      Class.forName("oracle.jdbc.driver.OracleDriver");

      try 
      { // try 2    

        // Get a connection instance from the first driver in the DriverManager list that recognizes the URL jdbcURL
        connection = DriverManager.getConnection(jdbcURL, user, password);

        // Create a statement instance that will be sending your SQL statements to the DBMS
        statement = connection.createStatement();
        connection.setAutoCommit(true); //set autocommit on
        
        System.out.println("Are you a new user to this system?\n1:Yes \n2:No");
        int newuser=Integer.parseInt(br.readLine());
        if(newuser==1)
          register();
        
        login();              

      }
      // end of try 2

      finally 
      {
        close(result);
        close(statement);
        close(connection);
      }// end of finally for try 2

    }// end of try 1
    
    catch(Throwable oops) 
    {
      oops.printStackTrace();
    }// end of catch for try 1

  }// end of main

  private static void register() throws IOException, SQLException//function for user of the system to register for the first time in the system
  {
    String j="";    

    System.out.println ("Select which intended user are you :\ns for Student\nm for Managing Staff\nd for Doctor\nn for Nurse");
    j=br.readLine();
    j.toLowerCase();
    switch(j)
    {
    case "s":
      String sname="";
      while(sname.isEmpty())
      {
        System.out.println("Enter your Name");
        sname = (br.readLine());
      }
      String spassword="";
      while(spassword.isEmpty())
      {
        System.out.println("Choose a student health centre Password");
        spassword = (br.readLine());
      }
      String startingsemester="";
      while(startingsemester.isEmpty())
      {
        System.out.println("Enter the starting semester");
        startingsemester = (br.readLine());
      }
      System.out.println("Do you have a health insurance?\n1. Yes \n2. No");
      int c = Integer.parseInt(br.readLine());
      if(c==1)
      {
        String HEALTHINSURANCEPROVIDERNAME="";
        while(HEALTHINSURANCEPROVIDERNAME.isEmpty())
        {
          System.out.println("Enter your health insurance provider name");
           HEALTHINSURANCEPROVIDERNAME = br.readLine();
        }
        String HEALTHINSURANCEPOLICYNUMBER="";
        while(HEALTHINSURANCEPOLICYNUMBER.isEmpty())
        {
          System.out.println("Enter your health insurance provider name");
          HEALTHINSURANCEPOLICYNUMBER = br.readLine();
        }
        
        statement.executeUpdate("INSERT INTO Student(studentName, password, healthInsuranceProviderName, healthInsurancePolicynumber, startingDate) values('"+sname+"' , '"+spassword+"' , '"+HEALTHINSURANCEPROVIDERNAME+"', '"+HEALTHINSURANCEPOLICYNUMBER+"', '"+startingsemester+"')");
        result = statement.executeQuery("SELECT studentid from student");
      }//end of if
      else
      {
        statement.executeUpdate("INSERT INTO Student( studentName, password, startingDate) values('"+sname+"' , '"+spassword+"' , '"+startingsemester+"')");
        result = statement.executeQuery("SELECT studentid from student");
      }
      int ID1=0;
      while (result.next()) 
      {
        ID1=result.getInt("studentid");
      }// end of while
      System.out.println("Registration completed. Your Id is:" + ID1);
      break;       
    case "n":
      String nname="";
      while(nname.isEmpty())
      {
        System.out.println("Enter your Name");
        nname = (br.readLine());
      }
      String npassword="";
      while(npassword.isEmpty())
      {
        System.out.println("Choose a student health centre Password");
        npassword = (br.readLine());
      }
      statement.executeUpdate("INSERT INTO Nurse( nurseName, password) values ('"+nname+"','"+npassword+"')");
      result = statement.executeQuery("SELECT nurseid from nurse");
      int ID2=0;
      while (result.next()) 
      {
        ID2=result.getInt("nurseid");
      }// end of while
      System.out.println("Registration completed. Your Id is:" + ID2);
      break;
    case "d":
      String dname="";
      while(dname.isEmpty())
      {
        System.out.println("Enter your Name");
        dname = (br.readLine());
      }
      String dpassword="";
      while(dpassword.isEmpty())
      {
        System.out.println("Choose a student health centre Password");
        dpassword = (br.readLine());
      }
      String dphone="";
      while(dphone.isEmpty())
      {
        System.out.println("Enter your Phone number");
        dphone = (br.readLine());
      }
      String dspecialization="";
      while(dspecialization.isEmpty())
      {
        System.out.println("Enter your Specialization");
        dspecialization = (br.readLine());
      }
      statement.executeUpdate("INSERT INTO Doctor( doctorNAme, password, phonenumber, specialization) values ('"+dname+"','"+dpassword+"','"+dphone+"','"+dspecialization+"')");
      result = statement.executeQuery("SELECT doctorid from doctor");
      int ID3=0;
      while (result.next()) 
      {
        ID3=result.getInt("doctorid");
      }// end of while
      System.out.println("Registration completed. Your Id is:" +ID3);
      break;
    case "m":
      String mname="";
      while(mname.isEmpty())
      {
        System.out.println("Enter your Name");
        mname = (br.readLine());
      }
      String mpassword="";
      while(mpassword.isEmpty())
      {
        System.out.println("Choose a student health centre Password");
        mpassword = (br.readLine());
      }
      statement.executeUpdate("INSERT INTO Staff(staffName, password) values ('"+mname+"','"+mpassword+"')");
      result = statement.executeQuery("SELECT staffid from staff");
      int ID4=0;
      while (result.next()) 
      {
        ID4=result.getInt("staffid");
      }// end of while
      System.out.println("Registration completed. Your Id is:" + ID4);
      break;
    
              
    }//end of switch
  }

  static void close(Connection connection) 
  {
    if(connection != null) 
    {
      try
      { 
        connection.close(); 
      } 
      catch(Throwable whatever) 
      {}
    } // end of if
  } // end of close for connection

  static void close(Statement statement)
  {
    if(statement != null)
    {
      try 
      { 
        statement.close(); 
      } 
      catch(Throwable whatever) 
      {}
    } // end of if
  } // end of close for statement

  static void close(ResultSet result)
  {
    if(result != null) 
    {
      try
      { 
        result.close();
      } 
      catch(Throwable whatever)
      {}
    } // end of if
  } // end of close for result set
  static void login() throws IOException, SQLException//function for user of the system to login
  {
    String log = new String();
    do
    {
      System.out.println("Please Login to the system:");

      System.out.println("Enter Userid");
      userid=Integer.parseInt(br.readLine());

      System.out.println("Enter Password");
      pass=br.readLine();

      String j="";    
      while(j.isEmpty())
      {
        System.out.println ("Select which intended user are you :\ns for Student\nm for Managing Staff\nd for Doctor\nn for Nurse");
        j=br.readLine();
        j.toLowerCase();
        switch(j)
        {
        case "s":
          result = statement.executeQuery("SELECT * FROM student WHERE studentid="+userid+" and password='"+pass+"'");  //fetch the query
          if(!result.next())
            {
              System.out.println("invalid login");
              job="none";
            }
          else
            job="student";
          break;
        case "n":
          result = statement.executeQuery("SELECT * FROM nurse WHERE nurseid="+userid+" and password='"+pass+"'");  //fetch the query
          if(!result.next())
          {
            System.out.println("invalid login");
            job="none";
          }
          else
            job="nurse";
          break;
        case "d":
          result = statement.executeQuery("SELECT * FROM doctor WHERE doctorid="+userid+" and password='"+pass+"'");  //fetch the query
          if(!result.next())
          {
            System.out.println("invalid login");
            job="none";
          }
          else
          job="doctor";
          break;
        case "m":
          result = statement.executeQuery("SELECT * FROM staff WHERE staffid="+userid+" and password='"+pass+"'");  //fetch the query
          if(!result.next())
            {
              System.out.println("invalid login");
              job="none";
            }
          else
          job="managing staff";
          break;
        
                  
        default:
          j="";
        }//end of switch
      }//end of while
                    
          switch(job)     //call the appropriate function for the corresponding staff
          {
            case "student":
            {
              int at=0;
              result = statement.executeQuery("SELECT studentname FROM student WHERE studentid="+userid+"");  //get the name of the student
              if(result.next())
              {
                do
                {
                  System.out.println("Hi "+result.getString("studentname")+". Welcome to Student Health Centre.");
                }while (result.next());
              }
              do{
              int schoice;
              System.out.println("What would you like to do:\n1.Update your information\n2. Make an Appointment \n Make an Appointment\n3.Check your mandatory vaccinations \n4. See future appointments"); 
              schoice=Integer.parseInt(br.readLine());
              switch(schoice)
              {
              case 1:
                updatestudentinformation(userid);
                break;
              case 2:
                makeAppointment();
                break;
              case 3:
                checkVaccinations();
                break;
              case 4:
                checkAppointments();
                break;
                default:
                  System.out.println("Wrong choice:(");
              }//end of switch
                  System.out.println("Do you want to carry out another task?\n 1.Yes\n2.No");
                  at=Integer.parseInt(br.readLine());
              
              }while(at==1);//end of do
              if(at!=1)
                System.out.println("Thank you for using this system. Do visit us again");
              break;
            }
            case "managing staff":
            {
              int at=0;
              result = statement.executeQuery("SELECT staffname FROM staff WHERE staffid="+userid+"");  //get the name of the staff member
              if(result.next())
              {
                do
                {
                  System.out.println("Hi "+result.getString("staffname")+". Welcome to Student Health Centre.");
                }while (result.next());
              }
              do{
                int mchoice;
                System.out.println("What would you like to do:\n1.Update your information\n2.Make an Appointment");//n3.Check your mandatory vaccinations \n4. See future appointments"); 
                mchoice=Integer.parseInt(br.readLine());
                switch(mchoice)
                {
                case 1:
                  updatestaffinformation(userid);
                  break;
                case 2:
                  makeAppointment();
                  break;
//                case 3:
//                  checkVaccinations();
//                  break;
//                case 4:
//                  checkAppointments();
//                  break;
                  default:
                    System.out.println("Wrong choice:(");
                }//end of switch
                    System.out.println("Do you want to carry out another task?\n 1.Yes\n2.No");
                    at=Integer.parseInt(br.readLine());
                
                }while(at==1);//end of do
              if(at!=1)
                System.out.println("Thank you for using this system. Do visit us again");
              break;
            }
            case "doctor":
            {
              int at=0;
              result = statement.executeQuery("SELECT doctorname FROM doctor WHERE doctorid="+userid+""); //get the name of the doctor
              if(result.next())
              {
                do
                {
                  System.out.println("Hi "+result.getString("doctorname")+". Welcome to Student Health Centre.");
                }while (result.next());
              }
              do{
                int dchoice;
                System.out.println("What would you like to do:\n1.Update your information\n2.See future appointments\n3.See a particular student record"); 
                dchoice=Integer.parseInt(br.readLine());
                switch(dchoice)
                {
                case 1:
                  updatedoctorinformation(userid);
                  break;
                case 2:
                  checkAppointments();
                  break;
                case 3:
                  checkStudentRecord();
                  break;
//                case 4:
//                  checkAppointments();
//                  break;
                  default:
                    System.out.println("Wrong choice:(");
                }//end of switch
                    System.out.println("Do you want to carry out another task?\n 1.Yes\n2.No");
                    at=Integer.parseInt(br.readLine());
                
                }while(at==1);//end of do
              if(at!=1)
                System.out.println("Thank you for using this system. Do visit us again");
              break;
            }
            case "nurse":
            {
              int at=0;
              result = statement.executeQuery("SELECT nursename FROM nurse WHERE nurseid="+userid+"");  //get the name of the nurse
              if(result.next())
              {
                do
                {
                  System.out.println("Hi "+result.getString("nursename")+". Welcome to Student Health Centre.");
                }while (result.next());
              }
              do{
                int nchoice;
                System.out.println("What would you like to do:\n1.Update your information\n2.Add a new consultation record"); 
                nchoice=Integer.parseInt(br.readLine());
                switch(nchoice)
                {
                case 1:
                  updatenurseinformation(userid);
                  break;
                case 2:
                  addConsultations();
                  break;
              
                  default:
                    System.out.println("Wrong choice:(");
                }//end of switch
                    System.out.println("Do you want to carry out another task?\n 1.Yes\n2.No");
                    at=Integer.parseInt(br.readLine());
                
                }while(at==1);//end of do
              if(at!=1)
                System.out.println("Thank you for using this system. Do visit us again");
              break;
            }
            case "none":
              System.out.println("Enter y to go to try logging in again");
              log = br.readLine();
              log.toLowerCase();
          }//end of switch
    }while(log.equals("y"));
  }//end login function 

  private static void updatenurseinformation(int z)throws IOException, SQLException
  {
    int userid2=z;
    int y;
    String b;
    System.out.println ("Enter the attribute to be updated: \n1.Name \n2. Password");
    y=Integer.parseInt(br.readLine());
    System.out.println ("Enter the changed value");
    b=(br.readLine());
    if(b.isEmpty())
    {
      System.out.println ("invalid input");
      updatenurseinformation(userid2);
    }
    
    switch(y)
    {
    case 1:
      statement.executeUpdate("update Nurse set nursename =' " + b + " 'where nurseid=" + z);
      System.out.println("Record Updated");
      System.out.println("Your updated details are as shown:");
      result = statement.executeQuery("SELECT * FROM nurse WHERE nurseid="+z+""); //get the details of the student whose record is updated
      if(result.next())
      {
        do
        {
          System.out.println("ID: "+result.getString("nurseid"));
          System.out.println("Name: "+result.getString("nursename"));
          System.out.println("Password: "+result.getString("password"));
        }while (result.next());
      }
    break;
    case 2:

        statement.executeUpdate("update nurse set password =' " + b + " 'where nurseid=" + z);
        System.out.println("Record Updated");
        System.out.println("Your updated details are as shown:");
        result = statement.executeQuery("SELECT * FROM nurse WHERE nurseid="+z+""); //get the details of the student whose record is updated
        if(result.next())
        {
          do
          {
            System.out.println("ID: "+result.getString("nurseid"));
            System.out.println("Name: "+result.getString("nursename"));
            System.out.println("Password: "+result.getString("password"));
          }while (result.next());
        }

    break;
    }//end of switch    
  }

  private static void addConsultations()throws IOException, SQLException {
    
  }

  private static void checkStudentRecord()throws IOException, SQLException {
    
  }

  private static void updatedoctorinformation(int z)throws IOException, SQLException {
    int userid2=z;
    int y;
    String b;
    System.out.println ("Enter the attribute to be updated: \n1.Name \n2.Password\n3.Specialization\n4.Phone Number");
    y=Integer.parseInt(br.readLine());
    System.out.println ("Enter the changed value");
    b=(br.readLine());
    if(b.isEmpty())
    {
      System.out.println ("invalid input");
      updatestudentinformation(userid2);
    }
    
    switch(y)
    {
    case 1:
      statement.executeUpdate("update doctor set doctorname =' " + b + " 'where doctorid=" + z);
      System.out.println("Record Updated");
      System.out.println("Your updated details are as shown:");
      result = statement.executeQuery("SELECT * FROM doctor WHERE doctorid="+z+""); //get the details of the student whose record is updated
      if(result.next())
      {
        do
        {
          System.out.println("ID: "+result.getString("doctorid"));
          System.out.println("Name: "+result.getString("doctorname"));
          System.out.println("Password: "+result.getString("password"));
          System.out.println("Specialization: "+result.getString("specialization"));
          System.out.println("Phone Number: "+result.getString("Phonenumber"));
        }while (result.next());
      }
    break;
    
    case 2:
      statement.executeUpdate("update doctor set PASSWORD =' " + b + " 'where doctorid=" + z);
      System.out.println("Record Updated");
      System.out.println("Your updated details are as shown:");
      result = statement.executeQuery("SELECT * FROM doctor WHERE doctorid="+z+""); //get the details of the student whose record is updated
      if(result.next())
      {
        do
        {
          System.out.println("ID: "+result.getString("doctorid"));
          System.out.println("Name: "+result.getString("doctorname"));
          System.out.println("Password: "+result.getString("password"));
          System.out.println("Specialization: "+result.getString("specialization"));
          System.out.println("Phone Number: "+result.getString("Phonenumber"));
        }while (result.next());
      }
    break;
    case 3:
      statement.executeUpdate("update doctor set Specialization =' " + b + " 'where doctorid=" + z);
      System.out.println("Record Updated");
      System.out.println("Your updated details are as shown:");
      result = statement.executeQuery("SELECT * FROM doctor WHERE doctorid="+z+""); //get the details of the student whose record is updated
      if(result.next())
      {
        do
        {
          System.out.println("ID: "+result.getString("doctorid"));
          System.out.println("Name: "+result.getString("doctorname"));
          System.out.println("Password: "+result.getString("password"));
          System.out.println("Specialization: "+result.getString("specialization"));
          System.out.println("Phone Number: "+result.getString("Phonenumber"));
        }while (result.next());
      }
    break;
    case 4:
      statement.executeUpdate("update doctor set phonenumber =' " + b + " 'where doctorid=" + z);
      System.out.println("Record Updated");
      System.out.println("Your updated details are as shown:");
      result = statement.executeQuery("SELECT * FROM doctor WHERE doctorid="+z+""); //get the details of the student whose record is updated
      if(result.next())
      {
        do
        {
          System.out.println("ID: "+result.getString("doctorid"));
          System.out.println("Name: "+result.getString("doctorname"));
          System.out.println("Password: "+result.getString("password"));
          System.out.println("Specialization: "+result.getString("specialization"));
          System.out.println("Phone Number: "+result.getString("Phonenumber"));
        }while (result.next());
      }
    break;
    }//end of switch    
  }

  private static void updatestaffinformation(int z) throws IOException, SQLException{
      int userid2=z;
      int y;
      String b;
      System.out.println ("Enter the attribute to be updated: \n1.Name \n2. Password");
      y=Integer.parseInt(br.readLine());
      System.out.println ("Enter the changed value");
      b=(br.readLine());
      if(b.isEmpty())
      {
        System.out.println ("invalid input");
        updatenurseinformation(userid2);
      }
      
      switch(y)
      {
      case 1:
        statement.executeUpdate("update staff set staffname =' " + b + " 'where staffid=" + z);
        System.out.println("Record Updated");
        System.out.println("Your updated details are as shown:");
        result = statement.executeQuery("SELECT * FROM staff WHERE staffid="+z+""); //get the details of the student whose record is updated
        if(result.next())
        {
          do
          {
            System.out.println("ID: "+result.getString("staffid"));
            System.out.println("Name: "+result.getString("staffname"));
            System.out.println("Password: "+result.getString("password"));
          }while (result.next());
        }
      break;
      case 2:

          statement.executeUpdate("update staff set password =' " + b + " 'where staffid=" + z);
          System.out.println("Record Updated");
          System.out.println("Your updated details are as shown:");
          result = statement.executeQuery("SELECT * FROM staff WHERE staffid="+z+""); //get the details of the student whose record is updated
          if(result.next())
          {
            do
            {
              System.out.println("ID: "+result.getString("staffid"));
              System.out.println("Name: "+result.getString("staffname"));
              System.out.println("Password: "+result.getString("password"));
            }while (result.next());
          }

      break;
      }//end of switch    
  }

  private static void checkAppointments() throws IOException, SQLException{
//System.out.println("You are in: checkAppointments");
  }

  private static void checkVaccinations()throws IOException, SQLException {
  //  System.out.println("You are in: checkVaccinations");    
    
  }

  private static void makeAppointment()throws IOException, SQLException {
    //System.out.println("You are in: makeAppointment");  
    
  }

  private static void updatestudentinformation(int z) throws IOException, SQLException
  {
    int userid2=z;
    int y;
    String b;
    System.out.println ("Enter the attribute to be updated: \n1.Name \n2.HealthInsurance Provider Name\n3.HealthInsurance Provider Number \n4.Password\n5.Starting semester");
    y=Integer.parseInt(br.readLine());
    System.out.println ("Enter the changed value");
    b=(br.readLine());
    if(b.isEmpty())
    {
      System.out.println ("invalid input");
      updatestudentinformation(userid2);
    }
    
    switch(y)
    {
    case 1:
      statement.executeUpdate("update Student set studentname =' " + b + " 'where studentid=" + z);
      System.out.println("Record Updated");
      System.out.println("Your updated details are as shown:");
      result = statement.executeQuery("SELECT * FROM student WHERE studentid="+z+""); //get the details of the student whose record is updated
      if(result.next())
      {
        do
        {
          System.out.println("ID: "+result.getString("studentID"));
          System.out.println("Name: "+result.getString("studentname"));
          System.out.println("Password: "+result.getString("password"));
          System.out.println("HealthInsurance Provider Name: "+result.getString("HEALTHINSURANCEPROVIDERNAME"));
          System.out.println("HealthInsurance Provider Number: "+result.getString("HEALTHINSURANCEPOLICYNUMBER"));
          System.out.println("Starting semester: "+result.getString("startingdate"));
        }while (result.next());
      }
    break;
    case 2:

        statement.executeUpdate("update Student set HEALTHINSURANCEPROVIDERNAME =' " + b + " 'where studentid=" + z);
        do
        {
          System.out.println ("Enter the HEALTHINSURANCE POLICY NUMBER");
          b=(br.readLine());
        }while(b.isEmpty());
        statement.executeUpdate("update Student set HEALTHINSURANCEPOLICYNUMBER =' " + b + " 'where studentid=" + z);
        System.out.println("Record Updated");
        System.out.println("Your updated details are as shown:");
        result = statement.executeQuery("SELECT * FROM student WHERE studentid="+z+""); //get the details of the student whose record is updated
        if(result.next())
        {
          do
          {
            System.out.println("ID: "+result.getString("studentID"));
            System.out.println("Name: "+result.getString("studentname"));
            System.out.println("Password: "+result.getString("password"));
            System.out.println("HealthInsurance Provider Name: "+result.getString("HEALTHINSURANCEPROVIDERNAME"));
            System.out.println("HealthInsurance Provider Number: "+result.getString("HEALTHINSURANCEPOLICYNUMBER"));
            System.out.println("Starting semester: "+result.getString("startingdate"));
          }while (result.next());
        }

    break;
    case 3:
      statement.executeUpdate("update Student set HEALTHINSURANCEPOLICYNUMBER =' " + b + " 'where studentid=" + z);
      System.out.println("Record Updated");
      System.out.println("Your updated details are as shown:");
      result = statement.executeQuery("SELECT * FROM student WHERE studentid="+z+""); //get the details of the student whose record is updated
      if(result.next())
      {
        do
        {
          System.out.println("ID: "+result.getString("studentID"));
          System.out.println("Name: "+result.getString("studentname"));
          System.out.println("Password: "+result.getString("password"));
          System.out.println("HealthInsurance Provider Name: "+result.getString("HEALTHINSURANCEPROVIDERNAME"));
          System.out.println("HealthInsurance Provider Number: "+result.getString("HEALTHINSURANCEPOLICYNUMBER"));
          System.out.println("Starting semester: "+result.getString("startingdate"));
        }while (result.next());
      }
    break;
    case 4:
      statement.executeUpdate("update Student set PASSWORD =' " + b + " 'where studentid=" + z);
      System.out.println("Record Updated");
      System.out.println("Your updated details are as shown:");
      result = statement.executeQuery("SELECT * FROM student WHERE studentid="+z+""); //get the details of the student whose record is updated
      if(result.next())
      {
        do
        {
          System.out.println("ID: "+result.getString("studentID"));
          System.out.println("Name: "+result.getString("studentname"));
          System.out.println("Password: "+result.getString("password"));
          System.out.println("HealthInsurance Provider Name: "+result.getString("HEALTHINSURANCEPROVIDERNAME"));
          System.out.println("HealthInsurance Provider Number: "+result.getString("HEALTHINSURANCEPOLICYNUMBER"));
          System.out.println("Starting semester: "+result.getString("startingdate"));
        }while (result.next());
      }
    break;
    case 5:
      statement.executeUpdate("update Student set startingdate =' " + b + " 'where studentid=" + z);
      System.out.println("Record Updated");
      System.out.println("Your updated details are as shown:");
      result = statement.executeQuery("SELECT * FROM student WHERE studentid="+z+""); //get the details of the student whose record is updated
      if(result.next())
      {
        do
        {
          System.out.println("ID: "+result.getString("studentID"));
          System.out.println("Name: "+result.getString("studentname"));
          System.out.println("Password: "+result.getString("password"));
          System.out.println("HealthInsurance Provider Name: "+result.getString("HEALTHINSURANCEPROVIDERNAME"));
          System.out.println("HealthInsurance Provider Number: "+result.getString("HEALTHINSURANCEPOLICYNUMBER"));
          System.out.println("Starting semester: "+result.getString("startingdate"));
        }while (result.next());
      }
    break;
    }//end of switch
  }//end of updatestudentinformation

}//end of class HealthCentre

