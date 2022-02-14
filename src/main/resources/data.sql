INSERT INTO CUSTOMER (SocialSecurityNumber, FirstName, LastName, Email, PhoneNumber)
VALUES(9001112500, 'Ulf', 'Line','ulf@hotmail.com', '08-54398723'),
(8801128752, 'Sandra', 'Eriksson','Sandra@hotmail.com', '08-54357466'),
(6601112500, 'Hanna', 'Larsson','Eriksson@gmail.com', '070-5556842'),
(9001112500, 'Gustav', 'Pettersson','Petterson@gmail.com', '08-54395543');


INSERT INTO Employee (UserName, FirstName, LastName, Email, PhoneNumber)
VALUES('Caed02', 'Calle', 'Edqvist','calle@handelsbanken.se', '08-54398663'),
('Lwgh01', 'Lwam', 'Ghebresus','lwam@handelsbanken.se', '08-54586994'),
('Adbe05', 'Adam', 'Bengtsson','adam@handelsbanken.se', '08-54575212'),
('Situ01', 'Simon', 'Tunstig','Simon@handelbanken.se', '08-54398663');

INSERT INTO Timeslot (EmployeeId, BookingDate, StartTime, EndTime)
VALUES
(1, '2018-03-08', '12:00', '13:00'),
(1, '2018-03-08', '13:15', '14:00'),
(3, '2018-03-10', '13:15', '14:00'),
(3, '2018-03-10', '14:15', '14:00'),
(3, '2018-03-10', '14:15', '14:00'),
(3, '2018-03-10', '16:15', '14:00'),
(4, '2018-03-10', '13:15', '14:00'),
(2, '2018-03-10', '15:15', '16:00'),
(2, '2022-02-18', '08:00', '08:45'),
(2, '2022-02-18', '09:00', '09:45'),
(2, '2022-02-18', '09:00', '09:45'),
(2, '2022-02-18', '10:00', '10:45'),
(2, '2022-02-18', '11:00', '11:45'),
(2, '2022-02-18', '14:00', '14:45'),
(2, '2022-02-18', '15:00', '15:45'),
(2, '2022-02-18', '16:00', '16:45'),
(2, '2022-02-18', '17:00', '17:45'),
(2, '2022-02-18', '14:00', '09:45'),
(2, '2022-02-18', '14:00', '09:45'),

(2, '2022-02-18', '15:15', '16:00'),
(2, '2022-02-18', '15:15', '16:00'),
(2, '2018-03-10', '15:15', '16:00'),
(2, '2018-03-10', '15:15', '16:00'),
(2, '2018-03-10', '15:15', '16:00'),
(2, '2018-03-10', '15:15', '16:00'),
(2, '2018-08-10', '15:15', '16:00');

INSERT INTO Booking (CustomerId, TimeslotId,textmessage)
VALUES (1, 2,'jag vill se över min ränta'),
(2, 3,null),
(3, 4, 'Vill ha hjälp med BankID'),
(4, 1,null);


INSERT INTO Subject (Subject)
 VALUES ('PensionSavings'),
 ('CapitalSavings'),
  ('Mortgages'),
   ('Insurance'),
    ('ChildSavings');

    INSERT INTO BOOKINGREQUEST (CustomerId)
       VALUES (1),
      (2);

    INSERT INTO Content(BookingrequestID,SUBJECTSID)
     VALUES (1,5),
     (1,3),
      (2,4),
       (2,1),
        (2,1);


--INSERT INTO Content (Subjects)
-- VALUES ('PensionSavings'),
-- ('CapitalSavings'),
--  ('Mortgages'),
--   ('Insurance'),
--    ('ChildSavings');

--
--INSERT INTO Subjects (BookingId ,ContentId)
--VALUES (1,5),
--(1, 3),
--(3, 4),
--(4, 1);