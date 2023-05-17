-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 17-05-2023 a las 03:23:11
-- Versión del servidor: 10.4.28-MariaDB
-- Versión de PHP: 8.0.28

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `crud`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `productos`
--

CREATE TABLE `productos` (
  `id` int(11) NOT NULL,
  `nombreDelProducto` varchar(145) NOT NULL,
  `precioDeCompra` decimal(20,2) NOT NULL,
  `precioDeVenta` decimal(20,2) NOT NULL,
  `cantidad` int(11) NOT NULL,
  `ganancias` decimal(20,2) NOT NULL,
  `perdidas` decimal(20,2) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `productos`
--

INSERT INTO `productos` (`id`, `nombreDelProducto`, `precioDeCompra`, `precioDeVenta`, `cantidad`, `ganancias`, `perdidas`) VALUES
(1, 'ABANICO SONY', 1000000.00, 2000000.00, 1, 14000000.00, 0.00);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `usuarios`
--

CREATE TABLE `usuarios` (
  `id` int(11) NOT NULL,
  `nombre` varchar(45) NOT NULL,
  `apellido` varchar(45) NOT NULL,
  `password` varchar(45) NOT NULL,
  `fechaDeCreacion` datetime NOT NULL,
  `ultimaEdicion` datetime NOT NULL,
  `esAdmin` tinyint(1) DEFAULT NULL,
  `esSuperAdmin` tinyint(1) DEFAULT NULL,
  `esUsuario` tinyint(1) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `usuarios`
--

INSERT INTO `usuarios` (`id`, `nombre`, `apellido`, `password`, `fechaDeCreacion`, `ultimaEdicion`, `esAdmin`, `esSuperAdmin`, `esUsuario`) VALUES
(1, 'M', 'F', '12345678', '2023-05-14 21:21:50', '2023-05-14 21:22:18', 0, 1, 0),
(2, 'c', 'd', '87654321', '2023-05-15 13:00:48', '2023-05-15 13:01:59', 0, 0, 1),
(3, 'B', 'D', 'QWERTYUI', '2023-05-15 13:25:46', '2023-05-15 17:33:40', 1, 0, 0);

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `productos`
--
ALTER TABLE `productos`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `id_UNIQUE` (`id`);

--
-- Indices de la tabla `usuarios`
--
ALTER TABLE `usuarios`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `id_UNIQUE` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
