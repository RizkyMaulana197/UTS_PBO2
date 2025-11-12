-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Nov 10, 2025 at 06:51 PM
-- Server version: 10.4.32-MariaDB
-- PHP Version: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `db_tambang`
--

-- --------------------------------------------------------

--
-- Table structure for table `cadangan`
--

CREATE TABLE `cadangan` (
  `kd_cad` varchar(20) NOT NULL,
  `nm_kpl` varchar(100) DEFAULT NULL,
  `bulan` varchar(20) DEFAULT NULL,
  `tahun` varchar(10) DEFAULT NULL,
  `no_iup` varchar(50) DEFAULT NULL,
  `laut` varchar(100) DEFAULT NULL,
  `luas` varchar(100) DEFAULT NULL,
  `ddh` varchar(50) DEFAULT NULL,
  `idh` varchar(50) DEFAULT NULL,
  `tdh` varchar(50) DEFAULT NULL,
  `pdh` varchar(50) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `iup`
--

CREATE TABLE `iup` (
  `no_iup` varchar(50) NOT NULL,
  `lokasi` varchar(255) DEFAULT NULL,
  `no_sk` varchar(100) DEFAULT NULL,
  `tgl_berlaku` varchar(50) DEFAULT NULL,
  `no_sert` varchar(100) DEFAULT NULL,
  `ket` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `kapal`
--

CREATE TABLE `kapal` (
  `nm_kpl` varchar(100) NOT NULL,
  `instansi` varchar(100) DEFAULT NULL,
  `dlm_gali` varchar(100) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `realisasi`
--

CREATE TABLE `realisasi` (
  `kd_real` varchar(20) NOT NULL,
  `kd_cad` varchar(20) DEFAULT NULL,
  `dsb` varchar(50) DEFAULT NULL,
  `isb` varchar(50) DEFAULT NULL,
  `tsb` varchar(50) DEFAULT NULL,
  `psb` varchar(50) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Indexes for dumped tables
--

--
-- Indexes for table `cadangan`
--
ALTER TABLE `cadangan`
  ADD PRIMARY KEY (`kd_cad`);

--
-- Indexes for table `iup`
--
ALTER TABLE `iup`
  ADD PRIMARY KEY (`no_iup`);

--
-- Indexes for table `kapal`
--
ALTER TABLE `kapal`
  ADD PRIMARY KEY (`nm_kpl`);

--
-- Indexes for table `realisasi`
--
ALTER TABLE `realisasi`
  ADD PRIMARY KEY (`kd_real`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
