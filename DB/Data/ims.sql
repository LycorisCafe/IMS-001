-- phpMyAdmin SQL Dump
-- version 5.2.0
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Dec 01, 2022 at 11:27 AM
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

INSERT INTO `classes` (`id`, `subjectId`, `teacherId`, `payment`, `day`, `telegramId`) VALUES
(1, 1, 1, 1000, 6, '-1001698896292'),
(2, 2, 1, 500, 5, '4654654');

--
-- Dumping data for table `payments`
--

INSERT INTO `payments` (`id`, `studentId`, `classId`, `year`, `month`, `status`) VALUES
(7, 0, 1, '2022', '11', 1),
(8, 1, 1, '2022', '11', 1),
(9, 2, 1, '2022', '11', 1),
(10, 0, 1, '2022', '12', 1),
(11, 0, 1, '2023', '1', 1),
(12, 0, 1, '2023', '2', 1),
(13, 0, 1, '2023', '3', 1),
(14, 0, 1, '2023', '4', 1),
(15, 0, 1, '2023', '5', 1),
(16, 0, 1, '2023', '6', 1),
(17, 0, 1, '2023', '7', 1),
(18, 0, 1, '2023', '8', 1),
(19, 0, 1, '2023', '9', 1),
(20, 0, 1, '2023', '10', 1),
(21, 0, 1, '2023', '10', 1),
(22, 0, 1, '2023', '10', 1),
(23, 0, 1, '2023', '10', 1),
(24, 0, 1, '2023', '10', 1),
(25, 0, 1, '2023', '10', 1),
(26, 0, 1, '2023', '10', 1),
(27, 0, 1, '2023', '10', 1),
(28, 0, 1, '2023', '10', 1),
(29, 0, 1, '2023', '10', 1),
(30, 0, 1, '2023', '10', 1),
(31, 0, 1, '2023', '10', 1),
(32, 0, 1, '2023', '10', 1),
(33, 0, 1, '2023', '10', 1),
(34, 0, 1, '2023', '10', 1),
(35, 0, 1, '2023', '10', 1),
(36, 0, 1, '2023', '10', 1),
(37, 0, 1, '2023', '10', 1),
(38, 0, 1, '2023', '10', 1),
(39, 0, 1, '2023', '10', 1),
(40, 0, 1, '2023', '10', 1),
(41, 0, 1, '2023', '10', 1),
(42, 0, 1, '2023', '10', 1);

--
-- Dumping data for table `regclass`
--

INSERT INTO `regclass` (`id`, `studentId`, `classId`) VALUES
(17, 0, 1),
(18, 1, 1),
(19, 2, 1);

--
-- Dumping data for table `students`
--

INSERT INTO `students` (`id`, `firstName`, `lastName`, `guardianName`, `guardianPhone`, `address`, `grade`, `telegramId`, `status`) VALUES
(0, 'Naveen', 'Nirmana', 'Balasooriya', '0711716161', 'fdgdfgdf', 8, '1241006555', 0),
(1, 'fgfdgdf', 'fgdfg', 'dfgdfgf', '1234567890', 'fgfg', 7, '12410065557', 0),
(2, 'sxvf', 'dfgdf', 'dfgdf', '0147852369', 'dfgdfg', 7, '1241006555', 0);

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
