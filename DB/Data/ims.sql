-- phpMyAdmin SQL Dump
-- version 5.2.0
-- https://www.phpmyadmin.net/
--
-- Host: localhost
-- Generation Time: Dec 08, 2022 at 12:47 PM
-- Server version: 10.4.25-MariaDB
-- PHP Version: 8.1.10

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `ims`
--

--
-- Dumping data for table `classes`
--

INSERT INTO `classes` (`id`, `subjectId`, `teacherId`, `payment`, `day`, `time`, `duration`, `telegramId`) VALUES
(1, 1, 1, 1000, 6, '0', 0, '-1001698896292');

--
-- Dumping data for table `exams`
--

INSERT INTO `exams` (`id`, `name`, `classId`, `date`, `time`, `duration`) VALUES
(13, 'jhfgjhffgf', 1, '2022-11-02', '0', 0),
(14, 'හෙහ් හේ', 1, '2022-12-12', '0', 0);

--
-- Dumping data for table `payments`
--

INSERT INTO `payments` (`id`, `studentId`, `classId`, `year`, `month`, `status`, `paymentDate`) VALUES
(44, 4, 1, '2022', '12', 1, '2022-11-12');

--
-- Dumping data for table `regclass`
--

INSERT INTO `regclass` (`id`, `studentId`, `classId`) VALUES
(17, 0, 1),
(18, 1, 1),
(19, 2, 1),
(20, 3, 1),
(21, 4, 1);

--
-- Dumping data for table `students`
--

INSERT INTO `students` (`id`, `firstName`, `lastName`, `guardianName`, `guardianPhone`, `address`, `grade`, `telegramId`, `status`) VALUES
(0, 'Naveen', 'Nirmana', 'Balasooriya', '0711716161', 'fdgdfgdf', 8, '1241006555', 0),
(1, 'fgfdgdf', 'fgdfg', 'dfgdfgf', '1234567890', 'fgfg', 7, '12410065557', 0),
(2, 'sxvf', 'dfgdf', 'dfgdf', '0147852369', 'dfgdfg', 7, '1241006555', 0),
(3, 'Naveen', 'Balasooriya', 'Anupama Balasooriya', '0711716616', 'No. 109, Diddeliya, Meetiyagoda', 8, '1241006555', 0),
(4, 'jdfgf', 'gfjfg', 'gjgjfg', '0123456789', 'fgfgh', 7, '1241006555', 0);

--
-- Dumping data for table `subjects`
--

INSERT INTO `subjects` (`id`, `grade`, `subject`) VALUES
(1, 10, 'Maths'),
(2, 10, 'Science');

--
-- Dumping data for table `teachers`
--

INSERT INTO `teachers` (`id`, `name`, `nic`, `address`, `telegramId`, `contact`, `status`) VALUES
(1, 'Nirmana Balasooriya', '200416903113', 'No.109, Diddeliya, Meetiyagoda', '1241006555', '0711716616', 0);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
