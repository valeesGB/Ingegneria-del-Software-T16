-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Creato il: Giu 10, 2025 alle 19:12
-- Versione del server: 10.4.32-MariaDB
-- Versione PHP: 8.0.30

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

--
-- Dump dei dati per la tabella `capo_farmacia`
--

INSERT INTO `capo_farmacia` (`Nome`, `Cognome`, `Email`, `Password`, `IdFarmacia`) VALUES
('Cap1', 'Cap', 'Cap1@hotmail.it', 'cap1', 2),
('Mario', 'Rossi', 'capo', 'capo', 1),
('Giorgio', 'CappelloDiPaglia', 'valerio@hotmail.it', 'vale', 3);

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

--
-- Dump dei dati per la tabella `farmacia`
--

INSERT INTO `farmacia` (`Id`, `Nome`, `Indirizzo`, `Citta`) VALUES
(1, 'Farmacia Centrale', 'Via Roma 10', 'Milano'),
(2, 'Farmacia di Periferia', 'Via Napoli 20', 'Firenze'),
(3, 'Farmacia Bella', 'Via Gastani Frinzi 3', 'Londra');

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

--
-- Dump dei dati per la tabella `farmacista`
--

INSERT INTO `farmacista` (`Nome`, `Cognome`, `Email`, `Password`, `IdFarmacia`) VALUES
('Luca', 'Bianchi', 'farmacista@farmacia.it', 'pass1234', 1),
('Gigi', 'Sdegmo', 'gigiSdegmo@hotmail.it', '1', 2),
('Cecco', 'jomid', 'jomid74465@pngzero.com', '111', 1),
('Lucio', 'Corsi', 'luciocorsi@hotmail.it', 'admin', 3),
('luigi', 'ruso', 'luigirusso2809@gmail.com', 'luigi', 1);

-- --------------------------------------------------------

--
-- Struttura della tabella `farmacista_liquidato`
--

CREATE TABLE `farmacista_liquidato` (
  `id` int(11) NOT NULL,
  `nome` varchar(127) NOT NULL,
  `cognome` varchar(127) NOT NULL,
  `email` varchar(255) NOT NULL,
  `data_liquidazione` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dump dei dati per la tabella `farmacista_liquidato`
--

INSERT INTO `farmacista_liquidato` (`id`, `nome`, `cognome`, `email`, `data_liquidazione`) VALUES
(1, 'ciao', 'ciao', 'ciao@ciao.it', '2025-06-08'),
(2, 'cecco', 'cecco', 'cecco@cecco.it', '2025-06-09');

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
  `IdFarmacia` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dump dei dati per la tabella `prenotazione`
--

INSERT INTO `prenotazione` (`Id`, `NomeC`, `CognomeC`, `Data`, `Ora`, `Esito`, `Anamnesi`, `Vaccino`, `IdFarmacia`) VALUES
(1, 'Cecco', 'Russo', '2000-10-10', '08:00:00', 'Annullata', 'Il cliente soffre tanto', 'Moderna', 2),
(2, 'Cecco', 'Ciao', '2000-10-10', '08:00:00', 'Annullata', 'boh', 'Pfizer', 3),
(3, 'gigi', 'sorrentino', '2000-10-10', '08:15:00', 'Effettuata', 'Ha tanti brufoli', 'Moderna', 3),
(6, 'cecco', 'sorre', '2025-10-10', '08:00:00', 'Non effettuata', 'boh', 'Pfizer', 3),
(7, 'Maurs', 'Serros', '2000-10-10', '08:30:00', 'Non effettuata', 'Progetti', 'Johnson', 3),
(8, 'Andreas', 'Passas', '2000-10-10', '08:45:00', 'Non effettuata', 'Exams', 'Moderna', 3),
(9, 'Mario', 'Rossi', '2021-12-25', '08:00:00', 'Non effettuata', '', 'Moderna', 1),
(10, 'Carmine', 'Selaponte', '2000-10-10', '09:00:00', 'Non effettuata', 'Parlare', 'Johnson', 3),
(11, 'Gino', 'Paoli', '2000-10-10', '09:15:00', 'Non effettuata', '', 'Pfizer', 3),
(13, 'luigi', 'russo', '2025-06-09', '08:00:00', 'Non effettuata', 'Tante cose', 'Pfizer', 2),
(15, 'francesco', 'sorrentino', '2025-09-10', '08:00:00', 'ANNULLATA', 'Tutte', 'Moderna', 3),
(16, 'Carmine', 'Sorrentino', '2026-10-10', '08:00:00', 'Annullata', 'Boh', 'Moderna', 2);

-- --------------------------------------------------------

--
-- Struttura della tabella `turno`
--

CREATE TABLE `turno` (
  `id` int(11) NOT NULL,
  `giorno` date NOT NULL,
  `tipo_turno` int(11) NOT NULL,
  `mail_farmacista` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dump dei dati per la tabella `turno`
--

INSERT INTO `turno` (`id`, `giorno`, `tipo_turno`, `mail_farmacista`) VALUES
(1, '2025-10-10', 1, 'farmacista@farmacia.it');

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
-- Indici per le tabelle `farmacista_liquidato`
--
ALTER TABLE `farmacista_liquidato`
  ADD PRIMARY KEY (`id`);

--
-- Indici per le tabelle `prenotazione`
--
ALTER TABLE `prenotazione`
  ADD PRIMARY KEY (`Id`),
  ADD KEY `fk_prenotazione_farmacia` (`IdFarmacia`);

--
-- Indici per le tabelle `turno`
--
ALTER TABLE `turno`
  ADD PRIMARY KEY (`id`),
  ADD KEY `mail_farmacista` (`mail_farmacista`);

--
-- AUTO_INCREMENT per le tabelle scaricate
--

--
-- AUTO_INCREMENT per la tabella `farmacia`
--
ALTER TABLE `farmacia`
  MODIFY `Id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT per la tabella `farmacista_liquidato`
--
ALTER TABLE `farmacista_liquidato`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT per la tabella `prenotazione`
--
ALTER TABLE `prenotazione`
  MODIFY `Id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=17;

--
-- AUTO_INCREMENT per la tabella `turno`
--
ALTER TABLE `turno`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

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
  ADD CONSTRAINT `fk_prenotazione_farmacia` FOREIGN KEY (`IdFarmacia`) REFERENCES `farmacia` (`Id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Limiti per la tabella `turno`
--
ALTER TABLE `turno`
  ADD CONSTRAINT `turno_ibfk_1` FOREIGN KEY (`mail_farmacista`) REFERENCES `farmacista` (`Email`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
