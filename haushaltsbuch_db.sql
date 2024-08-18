-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Erstellungszeit: 01. Feb 2024 um 14:34
-- Server-Version: 10.4.32-MariaDB
-- PHP-Version: 8.0.30

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Datenbank: `haushaltsbuch_db`
--
CREATE DATABASE IF NOT EXISTS `haushaltsbuch_db` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;
USE `haushaltsbuch_db`;

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `datensatz`
--

CREATE TABLE `datensatz` (
  `id` int(11) NOT NULL,
  `datum` date DEFAULT NULL,
  `kategorie` varchar(255) DEFAULT NULL,
  `konto` varchar(255) DEFAULT NULL,
  `beschreibung` varchar(255) DEFAULT NULL,
  `einnahme` double DEFAULT NULL,
  `ausgabe` double DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Daten für Tabelle `datensatz`
--

INSERT INTO `datensatz` (`id`, `datum`, `kategorie`, `konto`, `beschreibung`, `einnahme`, `ausgabe`) VALUES
(1, '2023-12-01', 'Einkommen', 'Konten', 'Arbeisgehalt', 2000, NULL),
(2, '2023-12-04', 'Sonstige', 'Konten', 'Miete', NULL, 600),
(3, '2023-12-07', 'Sonstige', 'Konten', 'Strom und Wasser', NULL, 237.68),
(4, '2023-12-05', 'Lebensmittel', 'Bargeld', 'Supermarket Aldi', NULL, 96.99),
(5, '2023-12-02', 'Auto-/Fahrkosten', 'Karte', 'Deutschland-Ticket', NULL, 49),
(6, '2024-01-20', 'Mode/Schönheitspflege', 'Karte', 'Dior und H&M', NULL, 300),
(7, '2023-12-26', 'Sonstige', 'Konten', 'Handy und festnets tarief', NULL, 97.36),
(8, '2024-01-01', 'Einkommen', 'Konten', 'Arbeitsgehalt', 2000, NULL),
(9, '2024-01-06', 'Lebensmittel', 'Bargeld', 'Turkische Market', NULL, 150.99),
(10, '2024-01-10', 'Auto-/Fahrkosten', 'Karte', 'Auto Tanken', NULL, 50),
(11, '2024-01-08', 'Sonstige', 'Konten', 'Strom6 Wasser', NULL, 236.65),
(12, '2024-01-01', 'Sonstige', 'Konten', 'Miete', NULL, 600),
(13, '2024-01-15', 'Gesundheit', 'Karte', 'Medikment', NULL, 65),
(14, '2024-01-22', 'Kindergeld', 'Konten', 'kindergeld', 250, NULL),
(15, '2024-01-27', 'Kulturleben', 'Bargeld', 'Kino mit Familien', NULL, 75),
(16, '2024-01-26', 'Haushaltswaren', 'Karte', 'Labtop Lenova', NULL, 755),
(17, '2023-12-30', 'Sonstige', 'Konten', 'Weihnacht geld', 1750, NULL);

--
-- Indizes der exportierten Tabellen
--

--
-- Indizes für die Tabelle `datensatz`
--
ALTER TABLE `datensatz`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT für exportierte Tabellen
--

--
-- AUTO_INCREMENT für Tabelle `datensatz`
--
ALTER TABLE `datensatz`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=19;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
