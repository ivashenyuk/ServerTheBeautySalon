-- phpMyAdmin SQL Dump
-- version 3.5.1
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1
-- Generation Time: May 19, 2018 at 01:52 PM
-- Server version: 5.5.25
-- PHP Version: 5.3.13

SET SQL_MODE="NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Database: `thebeautysalon`
--

-- --------------------------------------------------------

--
-- Table structure for table `orders_table`
--

CREATE TABLE IF NOT EXISTS `orders_table` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `Name` varchar(50) DEFAULT NULL,
  `Serveice` varchar(50) DEFAULT NULL,
  `Price` int(11) DEFAULT NULL,
  `Email` varchar(30) DEFAULT NULL,
  `Date` varchar(30) DEFAULT NULL,
  `Performer` varchar(50) DEFAULT NULL,
  `Discount` int(11) DEFAULT NULL,
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=8 ;

--
-- Dumping data for table `orders_table`
--

INSERT INTO `orders_table` (`Id`, `Name`, `Serveice`, `Price`, `Email`, `Date`, `Performer`, `Discount`) VALUES
(1, 'Дідух Юлія', 'Масаж', 120, 'diduh@gmail.com', '18.19.2018 12:04:00', 'Максим Борисович', 0),
(2, 'Дідух Юлія', 'Апаратна косметологія', 50, 'diduh@gmail.com', '18.50.2018 09:04:00', 'Пономарчук Людмила', 0),
(3, 'Дідух Юлія', 'Апаратна косметологія', 50, 'diduh@gmail.com', '18.51.2018 09:04:00', 'Пономарчук Людмила', 0),
(4, 'New User', 'Манікюр', 100, 'user@gmail.com', '24.57.2018 18:04:00', 'Кирил Євгенович', 0),
(5, 'New User', 'Апаратна косметологія', 50, 'user@gmail.com', '26.57.2018 18:04:00', 'Пономарчук Людмила', 0),
(6, 'New User', 'Косметологія', 30, 'user@gmail.com', '28.57.2018 18:04:00', 'Крамарчук Софія', 0),
(7, 'Дідух Юлія', 'Манікюр', 100, 'diduh@gmail.com', '17.41.2018 12:04:00', 'Кирил Євгенович', 0);

-- --------------------------------------------------------

--
-- Table structure for table `performers_table`
--

CREATE TABLE IF NOT EXISTS `performers_table` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `Name` varchar(50) DEFAULT NULL,
  `Serveice` varchar(30) DEFAULT NULL,
  `Price` varchar(30) DEFAULT NULL,
  `Img` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=12 ;

--
-- Dumping data for table `performers_table`
--

INSERT INTO `performers_table` (`id`, `Name`, `Serveice`, `Price`, `Img`) VALUES
(1, 'Кирил Євгенович', 'Манікюр', '100$', 'Іванченко.jpg'),
(2, 'Єлизавета Янович', 'Педікюр', '150$', 'Романченко.jpg'),
(3, 'Микитюк Ольга', 'Нарощування нігтів', '200$', 'Борисович.jpg'),
(4, 'Анна Анатолійович', 'Естетика обличчя', '75$', 'Лисенко.jpg'),
(5, 'Крамарчук Софія', 'Косметологія', '30$', 'Андрійович.jpg'),
(6, 'Петренко Олена', 'Депіляція', '230$', 'Сергійович.jpg'),
(7, 'Пономарчук Людмила', 'Апаратна косметологія', '50$', 'Янович.jpg'),
(8, 'Максим Борисович', 'Масаж', '120$', 'Броварчук.jpg'),
(9, 'Мирослав Євгенович', 'Послуги стиліста (перукаря)', '400$', 'Кравченко.jpg'),
(10, 'Кирил Євгенович', 'Манікюр', '100$', 'Іванченко.jpg');

-- --------------------------------------------------------

--
-- Table structure for table `profit_table`
--

CREATE TABLE IF NOT EXISTS `profit_table` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `Serveice` varchar(30) DEFAULT NULL,
  `Profit` varchar(50) DEFAULT NULL,
  `Netprofit` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=5 ;

--
-- Dumping data for table `profit_table`
--

INSERT INTO `profit_table` (`Id`, `Serveice`, `Profit`, `Netprofit`) VALUES
(1, 'Масаж', '120', '60'),
(2, 'Манікюр', '200', '120'),
(3, 'Апаратна косметологія', '150', '60'),
(4, 'Косметологія', '30', '0');

-- --------------------------------------------------------

--
-- Table structure for table `schedule_table`
--

CREATE TABLE IF NOT EXISTS `schedule_table` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `Serveice` varchar(40) DEFAULT NULL,
  `Performer` varchar(60) DEFAULT NULL,
  `TimeWork` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=10 ;

--
-- Dumping data for table `schedule_table`
--

INSERT INTO `schedule_table` (`Id`, `Serveice`, `Performer`, `TimeWork`) VALUES
(1, 'Манікюр', 'Кирил Євгенович', '08:00-17:00 Пн-Пт '),
(2, 'Педікюр', 'Єлизавета Янович', '10:00-19:00 Пн-Пт '),
(3, 'Нарощування нігтів', 'Микитюк Ольга', '09:00-18:00 Пн-Пт '),
(4, 'Естетика обличчя', 'Анна Анатолійович', '09:00-18:00 Пн-Пт '),
(5, 'Косметологія', 'Крамарчук Софія', '09:00-18:00 Пн-Пт '),
(6, 'Депіляція', 'Петренко Олена', '14:00-23:00 Пн-Пт '),
(7, 'Апаратна косметологія', 'Пономарчук Людмила', '09:00-18:00 Пн-Пт '),
(8, 'Масаж', 'Максим Борисович', '15:00-00:00 Пн-Пт '),
(9, 'Послуги стиліста (перукаря)', 'Мирослав Євгенович', '00:00-09:00 Пн-Пт ');

-- --------------------------------------------------------

--
-- Table structure for table `users_table`
--

CREATE TABLE IF NOT EXISTS `users_table` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `Name` varchar(50) DEFAULT NULL,
  `Email` varchar(30) DEFAULT NULL,
  `Password` varchar(20) DEFAULT NULL,
  `SecretCode` varchar(11) DEFAULT NULL,
  `Status` varchar(15) DEFAULT NULL,
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=3 ;

--
-- Dumping data for table `users_table`
--

INSERT INTO `users_table` (`Id`, `Name`, `Email`, `Password`, `SecretCode`, `Status`) VALUES
(1, 'Дідух Юлія', 'diduh@gmail.com', '123', 'Xf34Bbd71C', 'admin'),
(2, 'New User', 'user@gmail.com', 'changePassword', 'Gy75lyo11X', 'user');

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
