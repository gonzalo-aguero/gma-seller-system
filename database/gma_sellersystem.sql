-- phpMyAdmin SQL Dump
-- version 5.1.1
-- https://www.phpmyadmin.net/
--
-- Host: localhost
-- Generation Time: Feb 22, 2022 at 11:41 PM
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
  `stock` varchar(10) COLLATE utf8_bin NOT NULL,
  `salesCount` varchar(10) COLLATE utf8_bin NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

--
-- Dumping data for table `products`
--

INSERT INTO `products` (`id`, `name`, `price`, `description`, `stock`, `salesCount`) VALUES
(1, 'Servilletas', '2.0', 'Sin descripción', '10', '0'),
(2, 'Jugo Tang', '1.2', 'Sin descripción 21', '10', '0'),
(3, 'Smart TV 52\"', '2.0', 'Sin descripción', '10', '0'),
(4, 'Soporte Smart TV 52\"', '12.2', '-', '10', '0'),
(5, 'Papel Higienico', '2.3', '-', '10', '0'),
(6, 'Nuevo producto', '3.0', 'Sin descripción 22asdaasdasdasasdasdasdasdasdasdakjo12', '10', '0'),
(7, 'Nuevo producto', '0.0', 'Sin descripción12', '0', '0'),
(8, 'Nuevo producto', '0.0', 'Sin descripción12', '0', '0'),
(9, 'Producto', '100.0', 'Sin descripción', '10', '2'),
(10, 'Producto', '100.0', 'Sin descripción', '10', '2'),
(11, 'Producto', '100.0', 'Sin descripción', '10', '2'),
(12, 'Producto', '100.0', 'Sin descripción', '10', '2'),
(13, 'Producto', '100.0', 'Sin descripción2 ', '10', '2'),
(14, 'Producto', '100.0', 'Sin descripción', '10', '2'),
(15, 'Producto', '100.0', 'Sin descripción', '10', '2'),
(16, 'Producto', '100.0', 'Sin descripción', '10', '2'),
(17, 'Producto', '100.0', 'Sin descripción', '10', '2'),
(18, 'Producto', '100.0', 'Sin descripción', '10', '2'),
(19, 'Producto', '100.0', 'Sin descripción', '10', '2'),
(20, 'Producto', '100.0', 'Sin descripción', '10', '2'),
(21, 'Producto', '100.0', 'Sin descripción', '10', '2'),
(22, 'Producto', '100.0', 'Sin descripción', '10', '2'),
(23, 'Producto', '100.0', 'Sin descripción', '10', '2'),
(24, 'Producto', '100.0', 'Sin descripción', '10', '2'),
(25, 'Producto', '100.0', 'Sin descripción', '10', '2'),
(26, 'Producto', '100.0', 'Sin descripción', '10', '2'),
(27, 'Producto', '100.0', 'Sin descripción', '10', '2'),
(28, 'Producto', '100.0', 'Sin descripción', '10', '2'),
(29, 'Producto', '100.0', 'Sin descripción', '10', '2'),
(30, 'Producto', '100.0', 'Sin descripción', '10', '2'),
(31, 'Producto', '100.0', 'Sin descripción', '10', '2'),
(32, 'Producto', '100.0', 'Sin descripción', '10', '2'),
(33, 'Producto', '100.0', 'Sin descripción', '10', '2'),
(34, 'Producto', '100.0', 'Sin descripción', '10', '2'),
(35, 'Producto', '100.0', 'Sin descripción', '10', '2'),
(36, 'Producto', '100.0', 'Sin descripción', '10', '2'),
(37, 'Producto', '100.0', 'Sin descripción', '10', '2'),
(38, 'Producto', '100.0', 'Sin descripción', '10', '2'),
(39, 'Producto', '100.0', 'Sin descripción', '10', '2'),
(40, 'Producto', '100.0', 'Sin descripción', '10', '2'),
(41, 'Producto', '100.0', 'Sin descripción', '10', '2'),
(42, 'Producto', '100.0', 'Sin descripción', '10', '2'),
(43, 'Producto 2', '100.0', 'Sin descripción', '10', '2'),
(44, 'Producto 2', '100.0', 'Sin descripción', '10', '2'),
(45, 'Producto 2', '100.0', 'Sin descripción', '10', '2'),
(46, 'Producto 2', '100.0', 'Sin descripción', '10', '2'),
(47, 'Producto 2', '100.0', 'Sin descripción', '10', '2'),
(48, 'Producto 2', '100.0', 'Sin descripción', '10', '2'),
(49, 'Producto 2', '100.0', 'Sin descripción', '10', '2'),
(50, 'Producto 2', '100.0', 'Sin descripción', '10', '2'),
(51, 'Producto 2', '100.0', 'Sin descripción', '10', '2'),
(52, 'Producto 2', '100.0', 'Sin descripción', '10', '2'),
(53, 'Producto 2', '100.0', 'Sin descripción', '10', '2'),
(54, 'Producto 2', '100.0', 'Sin descripción', '10', '2'),
(55, 'Producto 2', '100.0', 'Sin descripción', '10', '2'),
(56, 'Producto 2', '100.0', 'Sin descripción', '10', '2'),
(57, 'Producto 2', '100.0', 'Sin descripción', '10', '2'),
(58, 'Producto 2', '100.0', 'Sin descripción', '10', '2'),
(59, 'Producto 2', '100.0', 'Sin descripción', '10', '2'),
(60, 'Producto 2', '100.0', 'Sin descripción', '10', '2'),
(61, 'Producto 2', '100.0', 'Sin descripción', '10', '2'),
(62, 'Producto 2', '100.0', 'Sin descripción', '10', '2'),
(63, 'Producto 2', '100.0', 'Sin descripción', '10', '2'),
(64, 'Producto 2', '100.0', 'Sin descripción', '10', '2'),
(65, 'Producto 2', '100.0', 'Sin descripción', '10', '2'),
(66, 'Producto 2', '100.0', 'Sin descripción', '10', '2'),
(67, 'Producto 2', '100.0', 'Sin descripción', '10', '2'),
(68, 'Producto 2', '100.0', 'Sin descripción', '10', '2'),
(69, 'Producto 2', '100.0', 'Sin descripción', '10', '2'),
(70, 'Producto 2', '100.0', 'Sin descripción', '10', '2'),
(71, 'Producto 2', '100.0', 'Sin descripción', '10', '2'),
(72, 'Producto 2', '100.0', 'Sin descripción', '10', '2'),
(73, 'Producto 2', '100.0', 'Sin descripción', '10', '2');

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

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

CREATE TABLE `users` (
  `id` int(11) NOT NULL,
  `username` varchar(50) COLLATE utf8_bin NOT NULL,
  `password` varchar(500) COLLATE utf8_bin NOT NULL,
  `permissionLevel` int(5) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`id`, `username`, `password`, `permissionLevel`) VALUES
(1, 'Root', '123', 0),
(2, 'Joel', '123', 1),
(4, 'Jessica', '123', 2),
(5, 'Luana', '123', 3);

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
  MODIFY `id` int(10) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=1218;

--
-- AUTO_INCREMENT for table `sales`
--
ALTER TABLE `sales`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=40;

--
-- AUTO_INCREMENT for table `users`
--
ALTER TABLE `users`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
