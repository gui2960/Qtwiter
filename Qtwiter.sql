﻿create table usuario(
nome varchar(45),
senha varchar(45),
email varchar(45), 
primary key (email)
);

create table folow(
email_segue varchar(45),
email_seguido varchar(45),
primary key (email_segue, email_seguido),
CONSTRAINT folow_fk1 FOREIGN KEY (email_segue) REFERENCES usuario(email),
CONSTRAINT folow_fk2 FOREIGN KEY (email_seguido) REFERENCES usuario(email)
);

create table postagem(
data date,
hora time,
texto text,
email_usuario varchar(45),
primary key (data, hora),
CONSTRAINT postagem_fk1 FOREIGN KEY (email_usuario) REFERENCES usuario(email)
);

--- VALIDAR O INSERT DE UM USUÁRIO

CREATE OR REPLACE FUNCTION verificarNome() RETURNS TRIGGER AS $$
BEGIN
	IF new.nome !~ '^([a-zA-Z])[a-zA-Z0-9_]*$' THEN
	RAISE EXCEPTION '%: Nome do usuário não pode ser nulo', NEW.nome;

	ELSIF new.senha !~ '^([a-zA-Z0-9])[a-zA-Z0-9_]*$' THEN
	RAISE EXCEPTION 'senha não pode ser nula';

	ELSIF new.email !~ '^[A-Za-z0-9._%-]+@[A-Za-z0-9.-]+[.][A-Za-z]+$' THEN
	RAISE EXCEPTION '%: Email inserido é inválido', NEW.email;

	ELSE RETURN NEW;
	END IF;
END;
$$
LANGUAGE 'plpgsql';

-------------------------------------------------------------------------------------
-------------------------------------- TRIGGER --------------------------------------

CREATE TRIGGER "verificarNome"
BEFORE INSERT 
ON "usuario"
FOR EACH ROW
EXECUTE PROCEDURE verificarNome();

-------------------------------------------------------------------------------------
------------------------------------ CREATE VIEW ------------------------------------

CREATE VIEW postagens_dia (nome, texto) AS
SELECT u.nome, p.texto
FROM Usuario u FULL OUTER JOIN Postagem p
ON u.email = p.email_usuario
WHERE (SELECT EXTRACT ('Day' FROM date(CURRENT_DATE))) = ALL((SELECT EXTRACT('Day' from data)
							     FROM Postagem)) ;
