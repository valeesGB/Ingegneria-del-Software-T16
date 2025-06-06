-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Creato il: Giu 06, 2025 alle 01:52
-- Versione del server: 10.4.32-MariaDB
-- Versione PHP: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `t16`
--

-- --------------------------------------------------------

--
-- Struttura della tabella `capo_farmacia`
--

CREATE TABLE `capo_farmacia` (
  `Nome` varchar(127) NOT NULL,
  `Cognome` varchar(127) NOT NULL,
  `Email` varchar(127) NOT NULL,
  `Password` varchar(255) NOT NULL,
  `IdFarmacia` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Struttura della tabella `farmacia`
--

CREATE TABLE `farmacia` (
  `Id` int(11) NOT NULL,
  `Nome` varchar(255) NOT NULL,
  `Indirizzo` varchar(255) NOT NULL,
  `Citta` varchar(127) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Struttura della tabella `farmacista`
--

CREATE TABLE `farmacista` (
  `Nome` varchar(127) NOT NULL,
  `Cognome` varchar(127) NOT NULL,
  `Email` varchar(255) NOT NULL,
  `Password` varchar(255) NOT NULL,
  `IdFarmacia` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Struttura della tabella `prenotazione`
--

CREATE TABLE `prenotazione` (
  `Id` int(11) NOT NULL,
  `NomeC` varchar(127) NOT NULL,
  `CognomeC` varchar(127) NOT NULL,
  `Data` date NOT NULL,
  `Ora` time NOT NULL,
  `Esito` varchar(16) NOT NULL,
  `Anamnesi` text NOT NULL,
  `Vaccino` varchar(16) NOT NULL,
  `IdFarmacia` int(11) NOT NULL,
  `IdFarmacista` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Indici per le tabelle scaricate
--

--
-- Indici per le tabelle `capo_farmacia`
--
ALTER TABLE `capo_farmacia`
  ADD PRIMARY KEY (`Email`),
  ADD KEY `fk_capofarmacia_farmacia` (`IdFarmacia`) USING BTREE;

--
-- Indici per le tabelle `farmacia`
--
ALTER TABLE `farmacia`
  ADD PRIMARY KEY (`Id`);

--
-- Indici per le tabelle `farmacista`
--
ALTER TABLE `farmacista`
  ADD PRIMARY KEY (`Email`) USING BTREE,
  ADD KEY `fk_farmacista_farmacia` (`IdFarmacia`);

--
-- Indici per le tabelle `prenotazione`
--
ALTER TABLE `prenotazione`
  ADD PRIMARY KEY (`Id`),
  ADD KEY `fk_prenotazione_farmacia` (`IdFarmacia`),
  ADD KEY `fk_prenotazione_farmacista` (`IdFarmacista`);

--
-- AUTO_INCREMENT per le tabelle scaricate
--

--
-- AUTO_INCREMENT per la tabella `farmacia`
--
ALTER TABLE `farmacia`
  MODIFY `Id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT per la tabella `prenotazione`
--
ALTER TABLE `prenotazione`
  MODIFY `Id` int(11) NOT NULL AUTO_INCREMENT;

--
-- Limiti per le tabelle scaricate
--

--
-- Limiti per la tabella `capo_farmacia`
--
ALTER TABLE `capo_farmacia`
  ADD CONSTRAINT `fk_idfarmacia` FOREIGN KEY (`IdFarmacia`) REFERENCES `farmacia` (`Id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Limiti per la tabella `farmacista`
--
ALTER TABLE `farmacista`
  ADD CONSTRAINT `fk_farmacista_farmacia` FOREIGN KEY (`IdFarmacia`) REFERENCES `farmacia` (`Id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Limiti per la tabella `prenotazione`
--
ALTER TABLE `prenotazione`
  ADD CONSTRAINT `fk_prenotazione_farmacia` FOREIGN KEY (`IdFarmacia`) REFERENCES `farmacia` (`Id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `fk_prenotazione_farmacista` FOREIGN KEY (`IdFarmacista`) REFERENCES `farmacista` (`Email`) ON DELETE NO ACTION ON UPDATE NO ACTION;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
