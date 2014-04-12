ReasonableCare
==============

The Reasonable Care management system for CSC 540

Reasonable Care provides a means for students, doctors, and nurses to schedule doctor and receive medical care. 
It provides an interface for students to schedule appointments, search for doctors, and update their personal
information. Doctors may view their upcoming and past appointments, and nurses may help provide on-the-phone
consultations for students, as well as schedule appointments if necessary.


#Usage
##Managing User Accounts
###Register a new student with basic information
Staff users may register new students with basic information. This essentially creates a new student account, and they student may then update their own information or staff can do this for them.

    
    ReasonableCare/staff> create-student <name> <password> <starting date>
    
###Take health insurance information and update student information
Staff may update a student's information, or a student may do this himself. Upon issuing the update command, users are prompted for which criteria they wish to update, i.e. health insurance information.

####Staff

    ReasonableCare/staff> update-student <student ID>
    Enter the attribute to be updated: 
    1.Name 
    2.HealthInsurance Provider Name
    3.HealthInsurance Provider Number 
    4.Password
    5.Starting semester
    
####Student

    ReasonableCare/student> update-student
    Enter the attribute to be updated: 
    1.Name 
    2.HealthInsurance Provider Name
    3.HealthInsurance Provider Number 
    4.Password
    5.Starting semester

##Making an Appointment with a Doctor

Students can make appointments through the Student shell. Upon issuing the make appointment command, students are prompted for the relevant information for the appointment, including appointment type.

    ReasonableCare/sudent> make-appointment-interactive
    Select Appointment Type
    1. Vaccination
    2. Physical
    3. Office Visit
    
###Scheduling an office visit

To schedule an office visit, select the appopriate prompt response. You will then be shown doctors who specialize in that field and a list of availble times for a date that you choose. After successfully choosing a doctor and appointment time, enter relevant payment information if you have not met your deductible.

    Enter the reason of your visit 
    1.Diabetes 
    2.FluShots 
    3.Mental Health 
    4.Orthopedics 
    5.Physical Therapy 
    6.Women's Health
    7.Urinary, Genital Problems 
    8.HIV Testing 
    9.Ear, Nose, Throat Problems 
    10.Heart related Problems 
    3
    2004          Dr. Rob
    2108          Dr. Harry
    Select the id of the doctor you want to book appointment with
    2004
    Enter the date for the appointment (YYYY-MM-DD):
    2014-04-12
    +-------------------------------------------+
    | Available Appointment Times on 2014-04-12 |
    +-------------------------------------------+
    | 2014-04-12 13:30:00.0                     |
    +-------------------------------------------+
    
    Select an option: 
    1. Enter an available time from the list (HH:MM:SS)
    2. Enter a different date
    3. Exit system and log out
    1
    Enter an available time from the list (HH:MM:SS):
    13:30:00
    The copayment for your appointment will be: 20
    Your deductible has not been paid for the year.
    Enter your credit card number:
    12345
    Enter your the expiration month:
    8
    Enter your the expiration year:
    2014
    Your credit card was pre-approved.
    Created new Appointment with id = 3220
    
##Doctors' Schedules

Doctors are able to view inforamtion about their appointments from the Doctor Shell.

    ReasonableCare/doctor> check-future-appointments
    +---------+------------------+-----------------------+--------------+---------------+-------+
    | Appt ID | Student          | Date and Time         | Appt Type    | Appt Reason   | Notes |
    +---------+------------------+-----------------------+--------------+---------------+-------+
    | 3220    | Mitchell Neville | 2014-04-12 13:30:00.0 | Office Visit | Mental Health | null  |
    +---------+------------------+-----------------------+--------------+---------------+-------+

##Consultations

