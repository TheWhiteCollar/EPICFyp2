-- phpMyAdmin SQL Dump
-- version 4.7.9
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1:3306
-- Generation Time: Nov 18, 2018 at 05:22 PM
-- Server version: 5.7.21
-- PHP Version: 5.6.35

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `epicdb`
--

-- --------------------------------------------------------

--
-- Table structure for table `admin`
--

DROP TABLE IF EXISTS `admin`;
CREATE TABLE IF NOT EXISTS `admin` (
  `adminName` varchar(15) NOT NULL,
  `adminPassword` varchar(30) NOT NULL,
  `adminLevel` varchar(30) NOT NULL,
  PRIMARY KEY (`adminName`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `admin`
--

INSERT INTO `admin` (`adminName`, `adminPassword`, `adminLevel`) VALUES
('admin1', 'admin123', 'superadmin'),
('admin2', 'admin123', 'admin');

-- --------------------------------------------------------

--
-- Table structure for table `company`
--

DROP TABLE IF EXISTS `company`;
CREATE TABLE IF NOT EXISTS `company` (
  `companyID` int(11) NOT NULL AUTO_INCREMENT,
  `companyEmail` varchar(50) NOT NULL,
  `companyTermsAndConditions` int(1) NOT NULL,
  `companyName` varchar(100) NOT NULL,
  `companyContact` int(15) NOT NULL,
  `companyContinent` varchar(100) NOT NULL,
  `companyCountry` varchar(100) NOT NULL,
  `companyState` varchar(100) NOT NULL,
  `companyDescription` varchar(500) NOT NULL,
  `companyPassword` varchar(50) NOT NULL,
  `companyLogo` mediumblob,
  PRIMARY KEY (`companyID`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

--
-- Dumping data for table `company`
--

INSERT INTO `company` (`companyID`, `companyEmail`, `companyTermsAndConditions`, `companyName`, `companyContact`, `companyContinent`, `companyCountry`, `companyState`, `companyDescription`, `companyPassword`, `companyLogo`) VALUES
(0, 'testing@gmail.com', 0, 'TESTING Pte Ltd', 11111111, 'Australia', 'Australia', 'Gold Coast', 'not assigned', 'test', ''),
(1, 'universtar.bt21sg@gmail.com', 0, 'BT21 Pte Ltd', 61234567, 'Asia', 'South Korea', 'Seoul', 'Here are AA Pte Ltd, we want to acheive greatness and quality at the best given possible time.', 'test', ''),
(2, 'rachaellow9011@gmail.com', 0, 'Rach Pte Ltd', 62211334, 'Asia', 'Singapore', 'Singapore', 'Here are BB Pte Ltd, we hand pick the best materials t bring to you the best BB product ever.', 'test', ''),
(3, 'rachael9011@gmail.com', 0, 'BHE Pte Ltd', 61122335, 'Europe', 'France', 'Paris', 'Here are CC Pte Ltd, we believe that only the best should be given to our customers.', 'test', ''),
(4, 'rjseoul2018@gmail.com', 0, 'BTS Pte Ltd', 91234567, 'America', 'USA', 'Newark', 'Here are DD Pte Ltd, we give the best for everything we do.', 'test', ''),
(5, '123.com', 0, 'Company123', 92348343, 'Asia', 'China', 'Shanghai', 'We sell the 123s of life, and we are glad to see you join us on this journey', '123', '');

-- --------------------------------------------------------

--
-- Table structure for table `countryinternship`
--

DROP TABLE IF EXISTS `countryinternship`;
CREATE TABLE IF NOT EXISTS `countryinternship` (
  `countryName` varchar(100) NOT NULL,
  `countryContinent` varchar(100) NOT NULL,
  PRIMARY KEY (`countryName`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `countryinternship`
--

INSERT INTO `countryinternship` (`countryName`, `countryContinent`) VALUES
('Canada', 'America'),
('China', 'Asia'),
('Czech Republic', 'Europe'),
('Malaysia', 'Asia'),
('Myanmar', 'Asia'),
('New Zealand', 'Australia'),
('Singapore', 'Asia'),
('Thailand', 'Asia');

-- --------------------------------------------------------

--
-- Table structure for table `countrytrip`
--

DROP TABLE IF EXISTS `countrytrip`;
CREATE TABLE IF NOT EXISTS `countrytrip` (
  `countryTripName` varchar(100) NOT NULL,
  PRIMARY KEY (`countryTripName`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `countrytrip`
--

INSERT INTO `countrytrip` (`countryTripName`) VALUES
('Malaysia'),
('Myanmar'),
('Singapore');

-- --------------------------------------------------------

--
-- Table structure for table `fieldofstudy`
--

DROP TABLE IF EXISTS `fieldofstudy`;
CREATE TABLE IF NOT EXISTS `fieldofstudy` (
  `fieldOfStudyName` varchar(40) NOT NULL,
  PRIMARY KEY (`fieldOfStudyName`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `fieldofstudy`
--

INSERT INTO `fieldofstudy` (`fieldOfStudyName`) VALUES
('Accountancy'),
('Arts & Social Sciences'),
('Business'),
('Computing'),
('Dentistry'),
('Design & Environment'),
('Engineering'),
('History'),
('Hospitality & Tourism'),
('Law'),
('Medicine'),
('Music'),
('Others'),
('Science');

-- --------------------------------------------------------

--
-- Table structure for table `interest`
--

DROP TABLE IF EXISTS `interest`;
CREATE TABLE IF NOT EXISTS `interest` (
  `interestName` varchar(40) NOT NULL,
  PRIMARY KEY (`interestName`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `interest`
--

INSERT INTO `interest` (`interestName`) VALUES
('Academic & Business'),
('Nature & Culture'),
('Service & Social Innovation');

-- --------------------------------------------------------

--
-- Table structure for table `internship`
--

DROP TABLE IF EXISTS `internship`;
CREATE TABLE IF NOT EXISTS `internship` (
  `internshipID` int(11) NOT NULL AUTO_INCREMENT,
  `internshipName` varchar(100) NOT NULL,
  `internshipFieldOfStudy` varchar(500) NOT NULL,
  `internshipDescription` varchar(1000) NOT NULL,
  `internshipStart` date NOT NULL,
  `internshipEnd` date NOT NULL,
  `internshipPay` decimal(13,2) NOT NULL,
  `internshipSupervisor` varchar(100) NOT NULL DEFAULT '',
  `internshipSupervisorEmail` varchar(100) NOT NULL DEFAULT '',
  `internshipVacancy` int(100) NOT NULL DEFAULT '0',
  `internshipPartnerID` int(11) NOT NULL DEFAULT '1',
  `internshipDatetime` datetime,
  PRIMARY KEY (`internshipID`),
  KEY `internshippartnerid` (`internshipPartnerID`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8;

--
-- Dumping data for table `internship`
--

INSERT INTO `internship` (`internshipID`, `internshipName`, `internshipFieldOfStudy`, `internshipDescription`, `internshipStart`, `internshipEnd`, `internshipPay`, `internshipSupervisor`, `internshipSupervisorEmail`, `internshipVacancy`, `internshipPartnerID`, `internshipDatetime`) VALUES
(1, 'Data Analytics Intern', 'Business, Accountancy', 'This intern position is within the Business Intelligence Department and will support the team with data analysis, model development, data visualizations and decision support for various departments.', '2018-11-11', '2019-11-11', '1000.00', 'Tommy Lau', 'tommy.lau@xwy.com', 3, 1, '2018-08-08 12:40:30'),
(2, 'Business and Science Intern', 'Business, Science', 'This intern position is within the Business Science Department. Interns will have to conduct science experiments with a business angle, and acheive suitable actionable results for the company to take.', '2018-11-11', '2019-11-11', '1000.00', 'Tommy Lau', 'tommy.lau@xwy.com', 4, 1, '2018-08-08 12:40:30'),
(3, 'Music Assistant', 'Music', 'Intern is required to have strong foundational knowledge in music. At least of Grade 5. Strong sight-reading skills is required.', '2018-11-11', '2019-11-11', '1000.00', 'Tommy Lau', 'tommy.lau@xwy.com', 2, 1, '2018-08-08 12:40:30'),
(4, 'Tour Guide Intern', 'History, Hospitality & Tourism', 'Interns are expected to have strong command of knowledge with regards to important historical sites. Interns should be able to vocalise and impart knowledge effectively and clearly.', '2018-11-11', '2019-11-11', '1000.00', 'Tommy Lau', 'tommy.lau@xwy.com', 2, 1, '2018-08-08 12:40:30'),
(5, 'Robotics Discovery Intern', 'Engineering', 'Intern is expected to be self-directed and self-motivated. Intern is expected to explore the different uses of robotics, and  how it can be applied into the daily life.', '2018-11-11', '2019-11-11', '1000.00', 'Bob Ma', 'bob.ma@xwy.com', 1, 2, '2018-08-08 12:40:30'),
(6, 'Archives Intern', 'Law', 'Intern will be in charge of managing the Law archives. These includes retrieving appropriate texts, and opportunities to closely collaborate with full time lawyers building their cases.', '2018-11-11', '2019-11-11', '1000.00', 'Bob Ma', 'tommy.ma@xwy.com', 1, 2, '2018-08-08 12:40:30'),
(7, 'Nurse Intern', 'Medicine', 'Possess strong emotional strength. It is preferrable if intern is able to speak in dialect.', '2018-11-11', '2019-11-11', '1000.00', 'Mary Tan', 'mary.tan@xwy.com', 1, 3, '2018-08-08 12:40:30'),
(8, 'Machine Learning Intern', 'Computing', 'Intern is expected to be self-directed and motivated. Project requires strong foundational knowledge of Machine Learning concepts. ', '2018-11-11', '2019-11-11', '1000.00', 'Mary Tan', 'mary.tan@xwy.com', 2, 3, '2018-08-08 12:40:30'),
(9, 'English Researcher Intern', 'History', 'Interns will be researching into the history of words, and curating how the language has evolved overtime. If intern has the language capabilities, he/she might have the opportunity to collaborate in a research paper.', '2018-11-11', '2019-11-11', '1000.00', 'Sally Pi', 'sally.pi@xwy.com', 3, 4, '2018-08-08 12:40:30');

-- --------------------------------------------------------

--
-- Table structure for table `internshipstudent`
--

DROP TABLE IF EXISTS `internshipstudent`;
CREATE TABLE IF NOT EXISTS `internshipstudent` (
  `internshipStudentID` int(11) NOT NULL AUTO_INCREMENT,
  `internshipUserEmail` varchar(50) NOT NULL, 
  `internshipStudentContinent` varchar(15) NOT NULL,
  `internshipStudentStatus` varchar(90) NOT NULL,
  `internshipStudentDatetime` datetime NOT NULL,
  `internshipStudentStatusAction` int(1) NOT NULL,
  PRIMARY KEY (`internshipStudentID`),
  KEY `internshipUserEmail` (`internshipUserEmail`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8;

--
-- Dumping data for table `internshipstudent`
--

INSERT INTO `internshipstudent` (`internshipStudentID`, `internshipUserEmail`, `internshipStudentContinent`, `internshipStudentStatus`, `internshipStudentDatetime`,  `internshipStudentStatusAction`) VALUES
(1, 'mediani.2015@sis.smu.edu.sg', 'Asia', 'User submitted application - Admin to review application', '2018-04-05 12:32:21', 1),
(2, 'mediani.2015@sis.smu.edu.sg', 'Asia', 'Application fails application review - No further actions', '2018-10-02 12:32:21', 0),
(3, 'mediani.2015@sis.smu.edu.sg', 'Europe', 'User submitted application - Admin to review application', '2018-11-22 14:46:00', 1),
(4, 'mediani.2015@sis.smu.edu.sg', 'Europe', 'Admin approves application - Send email with internship details for interest confirmation', '2018-11-22 14:50:00', 1),
(5, 'mediani.2015@sis.smu.edu.sg', 'Europe', 'Sent interest email - Waiting for user reply', '2018-11-22 15:13:00', 2),
(6, 'mediani.2015@sis.smu.edu.sg', 'Europe', 'User accepts - Send email to schedule interview', '2018-11-22 15:15:00', 1),
(7, 'mediani.2015@sis.smu.edu.sg', 'Europe', 'Sent interview schedule email - Waiting for user reply', '2018-11-22 15:20:00', 2),
(8, 'mediani.2015@sis.smu.edu.sg', 'Europe', 'Interview scheduled - Pending interview', '2018-11-22 15:25:00', 2),
(9, 'mediani.2015@sis.smu.edu.sg', 'Europe', 'Interview completed - Review interview', '2018-11-22 15:30:00', 1),
(10, 'mediani.2015@sis.smu.edu.sg', 'Europe', 'Internship offered - Pending user internship acceptance', '2018-11-22 15:35:00', 2),
(11, 'mediani.2015@sis.smu.edu.sg', 'Europe', 'User accepted internship offer - No further action', '2018-11-22 15:40:00', 3),
(12, 'mediani.2015@sis.smu.edu.sg', 'Europe', 'Internship Cancelled - No further action', '2018-11-22 16:00:00', 4);

-- --------------------------------------------------------

--
-- Table structure for table `internshipassigned`
--

DROP TABLE IF EXISTS `internshipassigned`;
CREATE TABLE IF NOT EXISTS `internshipassigned` (
  `internshipAssignedID` int(11) NOT NULL,
  `internshipID` int(11) NOT NULL,
  `internshipStudentID` int(11) NOT NULL,
  PRIMARY KEY (`internshipAssignedID`),
  KEY `internshipID` (`internshipID`),
  KEY `internshipStudentID` (`internshipStudentID`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

--
-- Dumping data for table `internshipassigned`
--

INSERT INTO `internshipassigned` (`internshipAssignedID`, `internshipID`, `internshipStudentID`) VALUES
(1, 5, 11);

-- --------------------------------------------------------

--
-- Table structure for table `payment`
--

DROP TABLE IF EXISTS `payment`;
CREATE TABLE IF NOT EXISTS `payment` (
  `paymentID` int(11) NOT NULL AUTO_INCREMENT,
  `tripStudentID` int(11) NOT NULL,
  `paymentMode` varchar(15) NOT NULL,
  `paymentTransaction` varchar(100) NOT NULL,
  `paymentAmount` double NOT NULL,
  PRIMARY KEY (`paymentID`),
  KEY `tripStudentID` (`tripStudentID`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

--
-- Dumping data for table `payment`
--

INSERT INTO `payment` (`paymentID`, `tripStudentID`, `paymentMode`, `paymentTransaction`, `paymentAmount`) VALUES
(1, 3, 'PayNow', 'Sh-123-123', 400);

-- --------------------------------------------------------

--
-- Table structure for table `trip`
--

DROP TABLE IF EXISTS `trip`;
CREATE TABLE IF NOT EXISTS `trip` (
  `tripID` int(11) NOT NULL AUTO_INCREMENT,
  `tripTitle` varchar(100) NOT NULL,
  `tripPrice` double NOT NULL DEFAULT '0',
  `tripItinerary` mediumblob,
  `tripDescription` varchar(1000) NOT NULL,
  `tripCountry` varchar(100) NOT NULL,
  `tripState` varchar(100) NOT NULL,
  `tripStart` date DEFAULT NULL,
  `tripEnd` date DEFAULT NULL,
  `tripDuration` int(11) NOT NULL DEFAULT '0',
  `tripActivation` int(3) NOT NULL DEFAULT '0',
  `tripInterest` varchar(500) NOT NULL,
  `tripTotalSignUp` int(11) DEFAULT '0',
  `tripPicture` mediumblob,
  PRIMARY KEY (`tripID`),
  KEY `tripCountry` (`tripCountry`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

--
-- Dumping data for table `trip`
--

INSERT INTO `trip` (`tripID`, `tripTitle`, `tripPrice`, `tripItinerary`, `tripDescription`, `tripCountry`, `tripState`, `tripStart`, `tripEnd`, `tripDuration`, `tripActivation`, `tripInterest`, `tripTotalSignUp`,`tripPicture`) VALUES
(1, 'Trip to Myanmar Oceans', 500, NULL, 'The beautiful Lake Inle will wait for no one. Enjoy the beauty before global warming mars it\'s beauty forever.', 'Myanmar', 'Shan', '2018-08-20', '2018-09-20', 30, 3, 'Nature and Culture, Academic and Business', 1, NULL),
(2, ' Grad Trip', 300, NULL, 'Come join us at the amazing grad trip around Malaysia!', 'Malaysia', 'Johor', '2018-08-20', '2018-09-20', 30, 4, 'Academic and Business', 0, NULL),
(3, ' Winter Study Trip Customised', 100, NULL, 'This Winter Study Trip in Singapore will just be amazing! Imagine WINTER in SINGAPORE. You must not miss this once in a life time opportunity!', 'Singapore', 'Singapore', '2018-08-20', '2018-09-20', 30, 4, 'Nature and Culture', 0, NULL);

-- --------------------------------------------------------

--
-- Table structure for table `tripstudent`
--

DROP TABLE IF EXISTS `tripstudent`;
CREATE TABLE IF NOT EXISTS `tripstudent` (
  `tripStudentID` int(11) NOT NULL AUTO_INCREMENT,
  `tripUserEmail` varchar(50) NOT NULL,
  `tripID` int(11) NOT NULL,
  `tripStudentStatus` varchar(25) DEFAULT '',
  `tripStudentTimestamp` datetime NOT NULL,
  PRIMARY KEY (`tripStudentID`),
  KEY `tripUserEmail` (`tripUserEmail`),
  KEY `tripID` (`tripID`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

--
-- Dumping data for table `tripstudent`
--

INSERT INTO `tripstudent` (`tripStudentID`, `tripUserEmail`, `tripID`, `tripStudentStatus`, `tripStudentTimestamp`) VALUES
(1, 'mediani.2015@sis.smu.edu.sg', 1, 'Applied interest', '2018-11-19 02:21:59'),
(2, 'mediani.2015@sis.smu.edu.sg', 1, 'Pending Deposit', '2018-11-19 02:22:01'),
(3, 'mediani.2015@sis.smu.edu.sg', 1, 'Deposit Made', '2018-11-19 02:22:07');

-- --------------------------------------------------------

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
CREATE TABLE IF NOT EXISTS `user` (
  `userEmail` varchar(50) NOT NULL,
  `userFirstName` varchar(50) NOT NULL,
  `userLastName` varchar(50) NOT NULL,
  `userPhone` int(15) NOT NULL,
  `userGender` varchar(1) NOT NULL,
  `userCitizenship` varchar(50) NOT NULL,
  `userDOB` year(4) NOT NULL,
  `userInterest` varchar(1000) DEFAULT NULL,
  `userPassword` varchar(50) NOT NULL,
  `userOccupation` varchar(40) NOT NULL,
  `userResume` mediumblob,
  `userIsEmailConfirm` varchar(10) NOT NULL DEFAULT 'pending',
  `userHighestEducation` varchar(40) NOT NULL,
  `userFieldOfStudy` varchar(40) DEFAULT NULL,
  `userDescription` varchar(500) DEFAULT NULL,
  `userSchool` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`userEmail`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `user`
--

INSERT INTO `user` (`userEmail`, `userFirstName`, `userLastName`, `userPhone`, `userGender`, `userCitizenship`, `userDOB`, `userInterest`, `userPassword`, `userOccupation`, `userResume`, `userIsEmailConfirm`, `userHighestEducation`, `userFieldOfStudy`, `userDescription`, `userSchool`) VALUES
('mediani.2015@sis.smu.edu.sg', 'Mediani', 'Law', 83036983, 'F', 'Singapore PR', 1996, 'Service & Social Innovation, Academic & Business', '1234', 'Student', NULL, 'pending', 'Bachelor Degree', 'Law', 'I love the exercise! It is my favourite thing. It makes me happy and beautiful.', 'SMU'),
('rachael.low.2015@sis.smu.edu.sg', 'Rachael', 'Low', 91234567, 'M', 'Algerian', 1995, 'Academic & Business', 'fyp1234', 'Student', NULL, 'confirmed', 'ITE', 'History', 'Weeee. The life of greatness and happiness is amazinggggg.', 'NTU'),
('suhailahs.2015@smu.edu.sg', 'Nurul', 'Suhailah', 81290722, 'F', 'Singaporean', 1993, 'Academic & Business, Nature & Culture', 'Ashrvf14', 'Student', NULL, 'pending', 'bachelor', 'Business', 'I love travelling.', 'Singapore Management University'),
('xiuwen.yeo@gmail.com', 'Xiu Wen', 'Yeo', 98769876, 'F', 'Singaporean', 1994, 'Nature & Culture', '1234', 'Student', NULL, 'pending', 'Postgraduate Diploma', 'Computing', 'I am really into helping others. When others feel happy, I feel happy too.', 'SMU'),
('yijing.oon.2015@smu.edu.sg', 'Yi Jing', 'Oon', 98766789, 'F', 'Singapore PR', 1993, 'Nature & Culture, Service & Social Innovation', 'oonyijing', 'Student', NULL, 'confirmed', 'Masters/Doctorate', 'Computing', 'Greatness is in the eye of the beholder. I believe with great power comes great responsibility.', 'NTU');

--
-- Constraints for dumped tables
--

--
-- Constraints for table `internship`
--
ALTER TABLE `internship`
  ADD CONSTRAINT `internshippartner_fk` FOREIGN KEY (`internshipPartnerID`) REFERENCES `company` (`companyID`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `internshipstudent`
--
ALTER TABLE `internshipstudent`
  ADD CONSTRAINT `internshipstudent_fk1` FOREIGN KEY (`internshipUserEmail`) REFERENCES `user` (`userEmail`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `internshipstudent`
--
ALTER TABLE `internshipassigned`
  ADD CONSTRAINT `internshipassigned_fk1` FOREIGN KEY (`internshipID`) REFERENCES `internship` (`internshipID`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `internshipassigned_fk2` FOREIGN KEY (`internshipStudentID`) REFERENCES `internshipStudent` (`internshipStudentID`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `payment`
--
ALTER TABLE `payment`
  ADD CONSTRAINT `payment_fk1` FOREIGN KEY (`tripStudentID`) REFERENCES `tripstudent` (`tripStudentID`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `trip`
--
ALTER TABLE `trip`
  ADD CONSTRAINT `tripcountry_fk1` FOREIGN KEY (`tripCountry`) REFERENCES `countrytrip` (`countryTripName`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `tripstudent`
--
ALTER TABLE `tripstudent`
  ADD CONSTRAINT `tripstudent_fk1` FOREIGN KEY (`tripUserEmail`) REFERENCES `user` (`userEmail`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `tripstudent_fk2` FOREIGN KEY (`tripID`) REFERENCES `trip` (`tripID`) ON DELETE CASCADE ON UPDATE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
