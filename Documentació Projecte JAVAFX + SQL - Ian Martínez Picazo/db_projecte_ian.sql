-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Temps de generació: 10-03-2025 a les 11:42:34
-- Versió del servidor: 10.4.32-MariaDB
-- Versió de PHP: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de dades: `db_projecte_ian`
--

-- --------------------------------------------------------

--
-- Estructura de la taula `client`
--

CREATE TABLE `client` (
  `id_client` int(11) NOT NULL,
  `data_registre` date NOT NULL,
  `tipus_client` varchar(50) NOT NULL,
  `targeta_credit` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Bolcament de dades per a la taula `client`
--

INSERT INTO `client` (`id_client`, `data_registre`, `tipus_client`, `targeta_credit`) VALUES
(1, '2025-02-25', 'NORMAL', 'ugyogyigyugy'),
(2, '2025-02-25', 'VIP', 'y'),
(3, '2025-02-25', 'VIP', 'ds'),
(4, '2025-03-02', 'VIP', 'sff'),
(8, '2025-03-02', 'NORMAL', 'xsa'),
(9, '2025-03-02', 'NORMAL', 'ñjo'),
(11, '2025-03-07', 'NORMAL', 'No');

-- --------------------------------------------------------

--
-- Estructura de la taula `empleat`
--

CREATE TABLE `empleat` (
  `id_empleat` int(11) NOT NULL,
  `lloc_feina` varchar(255) NOT NULL,
  `data_contractacio` date NOT NULL,
  `salari_brut` double NOT NULL,
  `estat_laboral` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Bolcament de dades per a la taula `empleat`
--

INSERT INTO `empleat` (`id_empleat`, `lloc_feina`, `data_contractacio`, `salari_brut`, `estat_laboral`) VALUES
(2, 'kj', '2025-02-27', 546, 'ACTIU'),
(4, '', '2025-02-27', 1, 'BAIXA'),
(5, 'yuhjklñ', '2025-02-27', 455, 'ACTIU'),
(8, 'df', '2025-03-02', 23, 'ACTIU'),
(9, 'fdfs', '2025-03-02', 3434, 'ACTIU'),
(10, 'dewre', '2025-03-04', 4567, 'ACTIU'),
(11, 'Tot', '2025-03-07', 99999999, 'ACTIU');

-- --------------------------------------------------------

--
-- Estructura de la taula `factura`
--

CREATE TABLE `factura` (
  `id_factura` int(11) NOT NULL,
  `data_emissio` date NOT NULL,
  `metode_pagament` varchar(50) NOT NULL,
  `base_imposable` double NOT NULL,
  `tipus_iva` varchar(16) NOT NULL,
  `total` double NOT NULL,
  `id_reserva` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Bolcament de dades per a la taula `factura`
--

INSERT INTO `factura` (`id_factura`, `data_emissio`, `metode_pagament`, `base_imposable`, `tipus_iva`, `total`, `id_reserva`) VALUES
(4, '2025-02-26', 'EFECTIU', 3456, '_20_PERCENT', 4147.2, 2),
(5, '2025-02-26', 'TARGETA', 57.76, '_20_PERCENT', 69.312, 1);

-- --------------------------------------------------------

--
-- Estructura de la taula `habitacio`
--

CREATE TABLE `habitacio` (
  `id_habitacio` int(11) NOT NULL,
  `numero_habitacio` int(11) NOT NULL,
  `tipus` varchar(50) NOT NULL,
  `capacitat` int(11) NOT NULL,
  `preu_nit_AD` double NOT NULL,
  `preu_nit_MP` double NOT NULL,
  `estat` varchar(50) NOT NULL,
  `descripcio` text DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Bolcament de dades per a la taula `habitacio`
--

INSERT INTO `habitacio` (`id_habitacio`, `numero_habitacio`, `tipus`, `capacitat`, `preu_nit_AD`, `preu_nit_MP`, `estat`, `descripcio`) VALUES
(1, 101, 'AD', 2, 100.5, 130.75, 'DISPONIBLE', 'Vista al mar'),
(2, 102, 'MP', 3, 120, 150.25, 'OCUPADA', 'Balcó privat'),
(3, 103, 'AD', 1, 80, 110, 'NETEJA', 'Interior'),
(4, 104, 'MP', 4, 200, 250, 'DISPONIBLE', 'Suite de luxe'),
(5, 105, 'AD', 2, 90, 120.5, 'OCUPADA', 'Econòmica'),
(6, 106, 'MP', 3, 140.75, 180, 'NETEJA', 'Amb terrassa'),
(7, 107, 'AD', 2, 95, 125, 'DISPONIBLE', 'Standard'),
(8, 108, 'MP', 1, 70.5, 100, 'OCUPADA', 'Habitació petita'),
(9, 109, 'AD', 4, 160, 210, 'NETEJA', 'Familiar'),
(10, 110, 'MP', 3, 130, 170, 'DISPONIBLE', 'Amb vistes al jardí');

-- --------------------------------------------------------

--
-- Estructura de la taula `persona`
--

CREATE TABLE `persona` (
  `id_persona` int(11) NOT NULL,
  `nom` varchar(255) NOT NULL,
  `cognom` varchar(255) NOT NULL,
  `adreca` varchar(255) NOT NULL,
  `dni` varchar(20) NOT NULL,
  `data_naixement` date NOT NULL,
  `telefon` varchar(20) NOT NULL,
  `email` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Bolcament de dades per a la taula `persona`
--

INSERT INTO `persona` (`id_persona`, `nom`, `cognom`, `adreca`, `dni`, `data_naixement`, `telefon`, `email`) VALUES
(1, 'yucc', 'uycucy', 'uycuyc', 'uycuctuct', '2025-02-14', '9086686', 'dfcgvhbjnkml,'),
(2, 'iygiy', 'iuuig', 'trxrt', 'gñougo', '2025-02-25', 'iullkjbkbjkbjkbjkbj', 'lkbmhjh'),
(3, 'wqqrewtrytykul', 'loikjuhgfd', 'sdfghj', 'iuytrd', '2025-02-24', 'wtrjykl', 'iohjgfv'),
(4, 'poyih', 'pojh', 'dxfgbmlp', 'o', '2025-03-08', '7909783', 'xdfcgvhbjn'),
(5, 'oligholg', 'ezxdcg', 'phu', 'qrew', '2025-01-29', '09876tr', 'rdyugy'),
(6, 'jnkm', 'jnkm,', 'hbjnkm,', 'hbjn m,', '2025-03-09', 'dfghjklñ', 'gjyjgjgy'),
(8, 'iuhjk', 'wefr', 'efdsx', 'defrgr4edrfg', '2025-03-15', 'ijfpji', 'wdcd'),
(9, 'fdgdd', 'fdg', 'egfdsa', 'dfgfh', '2025-02-24', 'fdg', 'fwsddf'),
(10, 'sd', 'asasdsa', 'sdad', 'edfgjyi', '2025-03-13', 'rytyu', 'gthryukiljk'),
(11, 'Ian', 'Martínez', 'Picazo', '12345678X', '2006-01-21', '324789432', 'iama249@vidalibarraquer.net');

-- --------------------------------------------------------

--
-- Estructura de la taula `realitzar`
--

CREATE TABLE `realitzar` (
  `id_empleat` int(11) NOT NULL,
  `id_tasca` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Bolcament de dades per a la taula `realitzar`
--

INSERT INTO `realitzar` (`id_empleat`, `id_tasca`) VALUES
(2, 1),
(2, 2),
(2, 4),
(2, 5),
(2, 6),
(2, 8),
(4, 1),
(4, 9),
(5, 1),
(5, 3),
(5, 7),
(5, 12),
(8, 6),
(8, 9),
(8, 10),
(11, 9),
(11, 11),
(11, 12);

-- --------------------------------------------------------

--
-- Estructura de la taula `reserva`
--

CREATE TABLE `reserva` (
  `id_reserva` int(11) NOT NULL,
  `data_reserva` date NOT NULL,
  `data_inici` date NOT NULL,
  `data_fi` date NOT NULL,
  `tipus_reserva` varchar(50) NOT NULL,
  `tipus_iva` varchar(15) NOT NULL,
  `preu_total_reserva` double NOT NULL,
  `id_client` int(11) NOT NULL,
  `id_habitacio` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Bolcament de dades per a la taula `reserva`
--

INSERT INTO `reserva` (`id_reserva`, `data_reserva`, `data_inici`, `data_fi`, `tipus_reserva`, `tipus_iva`, `preu_total_reserva`, `id_client`, `id_habitacio`) VALUES
(1, '2025-02-25', '2025-02-14', '2025-02-17', 'MP', '_20_PERCENT', 435, 2, 10),
(2, '2025-02-26', '2025-02-01', '2025-02-03', 'AD', '_20_PERCENT', 456, 2, 4),
(3, '2025-02-26', '2025-02-27', '2025-02-28', 'AD', '_19_PERCENT', 6123, 2, 7),
(4, '2025-03-02', '2025-03-14', '2025-03-21', 'AD', '_16_PERCENT', 2434, 2, 4),
(5, '2025-03-07', '2025-03-28', '2025-03-31', 'AD', '_19_PERCENT', 300, 11, 7),
(6, '2025-03-07', '2025-04-02', '2025-04-05', 'MP', '_20_PERCENT', 12, 11, 7),
(7, '2025-02-02', '2025-02-03', '2025-02-11', 'AD', '_16_PERCENT', 300, 11, 10);

-- --------------------------------------------------------

--
-- Estructura de la taula `tasca`
--

CREATE TABLE `tasca` (
  `id_tasca` int(11) NOT NULL,
  `data_execucio` date NOT NULL,
  `data_creacio` date NOT NULL,
  `descripcio` text NOT NULL,
  `estat` varchar(16) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Bolcament de dades per a la taula `tasca`
--

INSERT INTO `tasca` (`id_tasca`, `data_execucio`, `data_creacio`, `descripcio`, `estat`) VALUES
(1, '2025-02-07', '2025-02-27', 'oiyoi', 'COMPLETADA'),
(2, '2025-02-20', '2025-02-27', 'qwer', 'COMPLETADA'),
(3, '2025-02-27', '2025-02-27', 'srty', 'COMPLETADA'),
(4, '2025-02-27', '2025-02-27', 'ghjklñ', 'COMPLETADA'),
(5, '2025-03-03', '2025-03-03', 'lihh', 'COMPLETADA'),
(6, '2025-03-14', '2025-03-03', 'ojk', 'PENDENT'),
(7, '2025-02-26', '2025-03-03', 'hbkj', 'PENDENT'),
(8, '2025-03-04', '2025-03-03', 'fcghj', 'COMPLETADA'),
(9, '2025-03-19', '2025-03-03', 'vhikjn', 'PENDENT'),
(10, '2025-03-04', '2025-03-04', 'ñ', 'COMPLETADA'),
(11, '2025-03-28', '2025-03-07', 'Prova', 'PENDENT'),
(12, '2025-04-06', '2025-03-07', 'Prova 2', 'PENDENT');

--
-- Índexs per a les taules bolcades
--

--
-- Índexs per a la taula `client`
--
ALTER TABLE `client`
  ADD PRIMARY KEY (`id_client`);

--
-- Índexs per a la taula `empleat`
--
ALTER TABLE `empleat`
  ADD PRIMARY KEY (`id_empleat`);

--
-- Índexs per a la taula `factura`
--
ALTER TABLE `factura`
  ADD PRIMARY KEY (`id_factura`),
  ADD KEY `id_reserva` (`id_reserva`);

--
-- Índexs per a la taula `habitacio`
--
ALTER TABLE `habitacio`
  ADD PRIMARY KEY (`id_habitacio`),
  ADD UNIQUE KEY `numero_habitacio` (`numero_habitacio`);

--
-- Índexs per a la taula `persona`
--
ALTER TABLE `persona`
  ADD PRIMARY KEY (`id_persona`),
  ADD UNIQUE KEY `dni` (`dni`),
  ADD UNIQUE KEY `email` (`email`);

--
-- Índexs per a la taula `realitzar`
--
ALTER TABLE `realitzar`
  ADD PRIMARY KEY (`id_empleat`,`id_tasca`),
  ADD KEY `id_tasca` (`id_tasca`);

--
-- Índexs per a la taula `reserva`
--
ALTER TABLE `reserva`
  ADD PRIMARY KEY (`id_reserva`),
  ADD KEY `id_client` (`id_client`),
  ADD KEY `id_habitacio` (`id_habitacio`);

--
-- Índexs per a la taula `tasca`
--
ALTER TABLE `tasca`
  ADD PRIMARY KEY (`id_tasca`),
  ADD UNIQUE KEY `descripcio` (`descripcio`) USING HASH;

--
-- AUTO_INCREMENT per les taules bolcades
--

--
-- AUTO_INCREMENT per la taula `factura`
--
ALTER TABLE `factura`
  MODIFY `id_factura` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT per la taula `habitacio`
--
ALTER TABLE `habitacio`
  MODIFY `id_habitacio` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;

--
-- AUTO_INCREMENT per la taula `persona`
--
ALTER TABLE `persona`
  MODIFY `id_persona` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=12;

--
-- AUTO_INCREMENT per la taula `reserva`
--
ALTER TABLE `reserva`
  MODIFY `id_reserva` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;

--
-- AUTO_INCREMENT per la taula `tasca`
--
ALTER TABLE `tasca`
  MODIFY `id_tasca` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=13;

--
-- Restriccions per a les taules bolcades
--

--
-- Restriccions per a la taula `client`
--
ALTER TABLE `client`
  ADD CONSTRAINT `client_ibfk_1` FOREIGN KEY (`id_client`) REFERENCES `persona` (`id_persona`);

--
-- Restriccions per a la taula `empleat`
--
ALTER TABLE `empleat`
  ADD CONSTRAINT `empleat_ibfk_1` FOREIGN KEY (`id_empleat`) REFERENCES `persona` (`id_persona`);

--
-- Restriccions per a la taula `factura`
--
ALTER TABLE `factura`
  ADD CONSTRAINT `factura_ibfk_1` FOREIGN KEY (`id_reserva`) REFERENCES `reserva` (`id_reserva`);

--
-- Restriccions per a la taula `realitzar`
--
ALTER TABLE `realitzar`
  ADD CONSTRAINT `realitzar_ibfk_1` FOREIGN KEY (`id_empleat`) REFERENCES `empleat` (`id_empleat`) ON DELETE CASCADE,
  ADD CONSTRAINT `realitzar_ibfk_2` FOREIGN KEY (`id_tasca`) REFERENCES `tasca` (`id_tasca`) ON DELETE CASCADE;

--
-- Restriccions per a la taula `reserva`
--
ALTER TABLE `reserva`
  ADD CONSTRAINT `reserva_ibfk_1` FOREIGN KEY (`id_client`) REFERENCES `client` (`id_client`),
  ADD CONSTRAINT `reserva_ibfk_2` FOREIGN KEY (`id_habitacio`) REFERENCES `habitacio` (`id_habitacio`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
