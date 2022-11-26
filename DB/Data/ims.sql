-- phpMyAdmin SQL Dump
-- version 5.2.0
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Nov 27, 2022 at 04:34 PM
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
-- Dumping data for table `attendance`
--

INSERT INTO `attendance` (`id`, `regClassId`, `date`) VALUES
(2, 1, '2022-11-26');

--
-- Dumping data for table `classes`
--

INSERT INTO `classes` (`id`, `subjectId`, `teacherId`, `payment`, `day`, `telegramId`) VALUES
(1, 1, 1, 1000, 6, '-1001698896292'),
(2, 2, 1, 500, 5, '4654654');

--
-- Dumping data for table `login`
--

INSERT INTO `login` (`id`, `user`, `pass`, `type`, `lastLogin`) VALUES
(1, 'admin', 'admin', 'Administrator', '2022-11-27 16:10:28'),
(2, 'user', 'user', 'Moderator', '2022-11-27 19:50:47');

--
-- Dumping data for table `payments`
--

INSERT INTO `payments` (`id`, `studentId`, `classId`, `year`, `month`, `status`) VALUES
(1, 1, 1, '2022', '11', 1),
(2, 5, 1, '2022', '11', 1),
(3, 2, 1, '2022', '11', 1),
(4, 4, 2, '2022', '11', 1);

--
-- Dumping data for table `regclass`
--

INSERT INTO `regclass` (`id`, `studentId`, `classId`) VALUES
(1, 1, 1),
(9, 2, 1),
(10, 3, 1),
(14, 6, 1),
(12, 7, 1);

--
-- Dumping data for table `students`
--

INSERT INTO `students` (`id`, `firstName`, `lastName`, `guardianName`, `guardianPhone`, `address`, `grade`, `telegramId`, `status`) VALUES
(1, 'Naveen', 'Balasooriya', 'Anupama Balasooriya', '0711716616', 'No.109, Diddeliya, Meetiyagoda', 10, '1241006555', 0),
(2, 'Nirmana', 'Balasooriya', 'Naveen Balasooriya', '0711716616', 'No. 109, Diddeliya, Meetiyagoda', 9, '1241006555', 0),
(3, 'Kamal', 'Saman', 'Kamal Saha Bandu', '0711234567', 'Galle, Sri Lanka', 8, '1241006555', 0),
(4, 'Saman', 'Nimal', 'Saman Kamal', '0771234567', 'Galle, Sri Lanka', 9, '1241006555', 0),
(5, 'Amal', 'Kamal', 'Nimal Sunil', '0123456789', 'jsdbjc, bs', 6, '1241006555', 0),
(6, 'Kaajuh', 'ihgih', 'bhgb', '0123456789', 'uyfu', 8, '1241006555', 0),
(7, 'fgd', 'fgd', 'fgd', '0123456789', 'dgdfg', 6, '1241006555', 0);

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
