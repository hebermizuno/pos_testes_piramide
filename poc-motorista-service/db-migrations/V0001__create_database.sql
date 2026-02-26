CREATE TABLE `Usuario` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `cpf` varchar(255) DEFAULT NULL,
  `nome` varchar(255) DEFAULT NULL,
  `dataNascimento` date NOT NULL,
  PRIMARY KEY (`id`)
);

CREATE TABLE `Automovel` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `modelo` varchar(255) DEFAULT NULL,
  `dataModelo` date NOT NULL,
  `idUsuario` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKih6a0gitefstjru6yyv8tljar` (`idUsuario`),
  CONSTRAINT `FKih6a0gitefstjru6yyv8tljar` FOREIGN KEY (`idUsuario`) REFERENCES `Usuario` (`id`)
);


