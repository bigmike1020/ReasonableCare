VAR john_id NUMBER
INSERT INTO Doctor(doctorName, password, phoneNumber, specialization)
VALUES('John Terry', 'password', '919-100-2101', 'Oncology Surgeon')
RETURNING doctorId INTO :john_id;

INSERT INTO Doctor(doctorName, password, phoneNumber, specialization)
VALUES('Mary Jobs', 'password', '919-500-1212', 'Oncology Surgeon');

INSERT INTO Nurse(nurseName, password) 
VALUES('Rebecca Johnston', 'password');

INSERT INTO Nurse(nurseName, password) 
VALUES('Davy Jacobs', 'password');

INSERT INTO Staff(staffName, password)
VALUES('Michael Smith', 'password');

VAR jason_id NUMBER
INSERT INTO Student(studentName, password, healthInsuranceProviderName,
healthInsurancePolicyNumber, startingDate)
VALUES('Jason Hunter', 'password', 'Acme', 
123456, '15-MAR-2011')
RETURNING studentId INTO :jason_id;

INSERT INTO Student(studentName, password, startingDate)
VALUES('Dale Steyn', 'password', null);

VAR appt_id NUMBER
INSERT INTO Appointment(reasonForVisit, type, appointmentTime,
doctorNotes, cost) VALUES('Broken Bone', 'general', 
TO_DATE('15.03.2011 09:00:00', 'dd.mm.yyyy hh24:mi:ss'), 
'Pain killer', 30) RETURNING appointmentId INTO :appt_id;

INSERT INTO MakesAppointment(studentId, doctorId, appointmentId)
VALUES(:jason_id, :john_id, :appt_id);

INSERT INTO Appointment(reasonForVisit, type, appointmentTime,
doctorNotes, cost) VALUES('Vaccination', 'vaccination',
TO_DATE('21.04.2014 16:00:00', 'dd.mm.yyyy hh24:mi:ss'),
'', 100) RETURNING appointmentId INTO :appt_id;

INSERT INTO MakesAppointment(studentId, doctorId, appointmentId)
VALUES(:jason_id, :john_id, :appt_id);

INSERT INTO Appointment(reasonForVisit, type, appointmentTime,
doctorNotes, cost) VALUES('Vaccination', 'vaccination1',
TO_DATE('16.03.2011 09:00:00', 'dd.mm.yyyy hh24:mi:ss'),
'', 20) RETURNING appointmentId INTO :appt_id;

INSERT INTO Appointment(studentId, doctorId, appointmentId)
VALUES(:jason_id, :john_id, :appt_id);

INSERT INTO Appointment(reasonForVisit, type, appointmentTime,
doctorNotes, cost) VALUES('Vaccination', 'vaccination2',
TO_DATE('16.03.2011 09:30:00', 'dd.mm.yyyy hh24:mi:ss'),
'', 20) RETURNING appointmentId INTO :appt_id;

INSERT INTO Appointment(studentId, doctorId, appointmentId)
VALUES(:jason_id, :john_id, :appt_id);

INSERT INTO Appointment(reasonForVisit, type, appointmentTime,
doctorNotes, cost) VALUES('Vaccination', 'vaccination3',
TO_DATE('16.03.2011 10:00:00', 'dd.mm.yyyy hh24:mi:ss'),
'', 20) RETURNING appointmentId INTO :appt_id;

INSERT INTO Appointment(studentId, doctorId, appointmentId)
VALUES(:jason_id, :john_id, :appt_id);

