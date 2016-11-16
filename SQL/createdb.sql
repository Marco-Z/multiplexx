DROP TABLE IF EXISTS PRENOTAZIONE;
DROP TABLE IF EXISTS SPETTACOLO;
DROP TABLE IF EXISTS POSTO;
DROP TABLE IF EXISTS UTENTE;
DROP TABLE IF EXISTS SALA;
DROP TABLE IF EXISTS FILM;
DROP TABLE IF EXISTS PREZZI;

CREATE TABLE UTENTE (
	id_utente 		bigserial primary key not null,
	email 			varchar(1000) not null unique,
	nome			varchar(1000) not null,
	cognome			varchar(1000) not null,
	password 		varchar(1000) not null,
	credito 		numeric(2, 15) DEFAULT 0,
	tipo 			varchar(50) DEFAULT 'NORMALE'
);

CREATE TABLE SALA (
	id_sala 		bigserial primary key not null,
	nome_sala 		varchar(1000) not null unique,
	descrizione 	varchar(10000),
	larghezza		int,
	lunghezza		int
);

CREATE TABLE FILM (
	id_film 		bigserial primary key not null,
	titolo 			varchar(1000) not null unique,
	locandina 		varchar(10000) not null,
	descrizione 	text not null,
	genere 			varchar(10000) not null,
	link_trailer 	varchar(10000) not null,
	durata 			int not null,
	regia			varchar(10000),
	nazionalita		varchar(10000),
	tipo			varchar(10000),
	anno			int
);

CREATE TABLE POSTO (
	id_posto 		bigserial primary key not null,
	id_sala 		int not null references SALA(id_sala),
	riga 			int not null,
	colonna 		int not null
);

CREATE TABLE SPETTACOLO (
	id_spettacolo 	bigserial primary key not null,
	id_film 		int not null references FILM(id_film),
	id_sala 		int not null references SALA(id_sala),
	data_ora 		timestamp not null
);

CREATE TABLE PRENOTAZIONE ( --in dbManager.griglia è chiamato prenotazione_bis
	id_prenotazione 		bigserial primary key not null,
	id_utente 				int not null references UTENTE(id_utente),
	id_spettacolo 			int not null references SPETTACOLO(id_spettacolo),
	id_sala 				int not null references SALA(id_sala),
	id_posto 				int not null references posto(id_posto),
	id_riga 				int not null,
	id_colonna				int not null,
	tipo_biglietto			varchar(10000),
	data_ora_operazione 	timestamp not null,
	unique(id_spettacolo, id_riga, id_colonna),
	check (data_ora_operazione < SPETTACOLO(data_ora))
); 

CREATE TABLE PREZZI (
	tipo_film		varchar(10000),
	tipo_biglietto	varchar(10000),  -- 'INTERO', 'RIDOTTO'
	prezzo			int not null
);