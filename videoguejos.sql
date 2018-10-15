-- phpMyAdmin SQL Dump
-- version 4.8.0
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 14-10-2018 a las 19:33:33
-- Versión del servidor: 10.1.31-MariaDB
-- Versión de PHP: 7.2.4

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `videoguejos`
--
CREATE DATABASE IF NOT EXISTS `videoguejos` DEFAULT CHARACTER SET latin1 COLLATE latin1_swedish_ci;
USE `videoguejos`;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `desarrolladores`
--

DROP TABLE IF EXISTS `desarrolladores`;
CREATE TABLE `desarrolladores` (
  `id` int(50) NOT NULL,
  `Nombre` varchar(50) NOT NULL,
  `Categoria` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `desarrolladores`
--

INSERT INTO `desarrolladores` (`id`, `Nombre`, `Categoria`) VALUES
(4, 'CDred', 'AAA'),
(5, 'MeCagoEnDiosElias', 'AAA'),
(6, 'Ubi', 'AAA'),
(9, 'Indiegala', 'indie'),
(34, 'toby', 'aaa');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `videojuegos`
--

DROP TABLE IF EXISTS `videojuegos`;
CREATE TABLE `videojuegos` (
  `id` int(11) NOT NULL,
  `Titulo` varchar(50) NOT NULL,
  `Descripcion` varchar(50) NOT NULL,
  `Genero` varchar(50) NOT NULL,
  `Desarrolladora` varchar(150) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `desarrolladores`
--
ALTER TABLE `desarrolladores`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `Nombre_2` (`Nombre`),
  ADD KEY `Nombre` (`Nombre`);

--
-- Indices de la tabla `videojuegos`
--
ALTER TABLE `videojuegos`
  ADD PRIMARY KEY (`id`),
  ADD KEY `Desarrolladora` (`Desarrolladora`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `desarrolladores`
--
ALTER TABLE `desarrolladores`
  MODIFY `id` int(50) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=35;

--
-- AUTO_INCREMENT de la tabla `videojuegos`
--
ALTER TABLE `videojuegos`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `videojuegos`
--
ALTER TABLE `videojuegos`
  ADD CONSTRAINT `videojuegos_ibfk_1` FOREIGN KEY (`Desarrolladora`) REFERENCES `desarrolladores` (`Nombre`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
