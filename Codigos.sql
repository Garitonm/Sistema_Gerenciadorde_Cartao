CREATE TABLE pessoa (
    id INT NOT NULL PRIMARY KEY,
    nome VARCHAR(70),
    cpf VARCHAR(11)
);
CREATE TABLE cartaocredito (
    idcartao INT AUTO_INCREMENT PRIMARY KEY,
    idpessoa INT,
    numero BIGINT,
    dataValidade VARCHAR(10),
    limite DOUBLE,
    FOREIGN KEY (idpessoa) REFERENCES pessoa(id)
);

CREATE TABLE transacao(
	idtransacao INT AUTO_INCREMENT PRIMARY KEY,
	cartao INT,
	valor DOUBLE,
	estabelecimento VARCHAR(60),
	FOREIGN KEY (cartao) REFERENCES cartaocredito(idcartao)
);

CREATE TABLE cartaoadicional(
	idcartaoadicional INT AUTO_INCREMENT PRIMARY KEY,
   idpessoa INT,
   numero BIGINT,
   dataValidadead VARCHAR(10),
   limiteprin DOUBLE,
   FOREIGN KEY (idpessoa) REFERENCES pessoa(id)
);
