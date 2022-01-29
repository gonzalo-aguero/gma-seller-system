-- phpMyAdmin SQL Dump
-- version 5.1.1
-- https://www.phpmyadmin.net/
--
-- Host: localhost
-- Generation Time: Jan 29, 2022 at 03:57 AM
-- Server version: 10.4.20-MariaDB
-- PHP Version: 7.4.21

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `gma_sellersystem`
--

-- --------------------------------------------------------

--
-- Table structure for table `products`
--

CREATE TABLE `products` (
  `id` int(10) NOT NULL,
  `name` varchar(100) COLLATE utf8_bin NOT NULL,
  `price` varchar(100) COLLATE utf8_bin NOT NULL,
  `description` varchar(500) COLLATE utf8_bin NOT NULL,
  `image` mediumblob DEFAULT NULL,
  `stock` varchar(10) COLLATE utf8_bin NOT NULL,
  `salesCount` varchar(10) COLLATE utf8_bin NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

--
-- Dumping data for table `products`
--

INSERT INTO `products` (`id`, `name`, `price`, `description`, `image`, `stock`, `salesCount`) VALUES
(1, 'Servilletas', '45.0', 'Sin descripción', NULL, '10', '3'),
(2, 'Jugo Tang', '0.75', 'Sin descripción', NULL, '52', '5'),
(3, 'Papel Higienico', '30.0', 'Sin descripción', NULL, '10', '1'),
(4, 'Smart TV 52\"', '1000.0', 'Sin descripción', NULL, '15', '0'),
(5, 'Soporte Smart TV 52\"', '500.0', 'Sin descripción', NULL, '0', '0'),
(29, 'Nuevo producto', '0.0', 'Sin descripción', NULL, '0', '0'),
(31, 'Nuevo producto', '0.0', 'Sin descripción', NULL, '0', '0'),
(32, 'Nuevo producto', '0.0', 'Sin descripción', NULL, '0', '0'),
(33, 'Nuevo producto', '0.0', 'Sin descripción', NULL, '0', '0');

-- --------------------------------------------------------

--
-- Table structure for table `sales`
--

CREATE TABLE `sales` (
  `id` int(11) NOT NULL,
  `totalAmount` varchar(50) COLLATE utf8_bin NOT NULL,
  `totalProductCount` int(11) NOT NULL,
  `date` datetime DEFAULT current_timestamp(),
  `productList` varchar(150) COLLATE utf8_bin NOT NULL,
  `productUnits` varchar(150) COLLATE utf8_bin NOT NULL,
  `user` varchar(100) COLLATE utf8_bin NOT NULL COMMENT 'User who registered the sale'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

--
-- Dumping data for table `sales`
--

INSERT INTO `sales` (`id`, `totalAmount`, `totalProductCount`, `date`, `productList`, `productUnits`, `user`) VALUES
(1, '105', 3, '2022-01-27 20:02:52', '1,3', '1,2', 'admin'),
(2, '3500', 4, '2022-01-28 19:32:15', '4,5', '3,1', 'admin'),
(3, '3500', 4, '2022-01-28 19:37:55', '4,5', '3,1', 'admin'),
(4, '3500', 4, '2022-01-28 23:05:13', '4,5', '3,1', 'admin');

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

CREATE TABLE `users` (
  `id` int(11) NOT NULL,
  `username` varchar(50) COLLATE utf8_bin NOT NULL,
  `password` varchar(500) COLLATE utf8_bin NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`id`, `username`, `password`) VALUES
(1, 'admin', '123'),
(4, 'Jessica', 'juLM90??');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `products`
--
ALTER TABLE `products`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `sales`
--
ALTER TABLE `sales`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `products`
--
ALTER TABLE `products`
  MODIFY `id` int(10) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=34;

--
-- AUTO_INCREMENT for table `sales`
--
ALTER TABLE `sales`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT for table `users`
--
ALTER TABLE `users`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
