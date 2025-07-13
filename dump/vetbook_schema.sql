CREATE DATABASE IF NOT EXISTS vetbook;
USE vetbook;

-- Tabella utenti
CREATE TABLE utenti (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(50),
    cognome VARCHAR(50),
    email VARCHAR(100),
    username VARCHAR(50) UNIQUE,
    password VARCHAR(255),
    immagine_profilo MEDIUMBLOB,
    ruolo ENUM('PROPRIETARIO', 'VETERINARIO', 'AMMINISTRATORE') NOT NULL
);

-- Tabella animali
CREATE TABLE animali (
    codicechip BIGINT PRIMARY KEY,
    nome VARCHAR(45),
    tipo VARCHAR(45),
    razza VARCHAR(45),
    colore VARCHAR(45),
    data_nascita DATE,
    idproprietario INT,
    vaccinazione DATE,
    FOREIGN KEY (idproprietario) REFERENCES utenti(id)
);

-- Tabella disponibilità
CREATE TABLE disponibilita (
    id INT AUTO_INCREMENT PRIMARY KEY,
    data_visita DATE,
    ora_visita TIME,
    stato ENUM('LIBERA', 'PRENOTATA'),
    prenotazioni_id INT
);

-- Tabella visite
CREATE TABLE visite (
    id INT AUTO_INCREMENT PRIMARY KEY,
    tipo_visita ENUM('CONTROLLO', 'VACCINAZIONE', 'CHIRURGIA'),
    descrizione TEXT,
    costo DECIMAL(10,2),
    data_visita DATE,
    ora_visita TIME,
    id_veterinario INT,
    FOREIGN KEY (id_veterinario) REFERENCES utenti(id)
);

-- Tabella prenotazioni
CREATE TABLE prenotazioni (
    id INT AUTO_INCREMENT PRIMARY KEY,
    id_proprietario INT,
    id_animale BIGINT,
    data_prenotazione DATE,
    ora_prenotazione TIME,
    FOREIGN KEY (id_proprietario) REFERENCES utenti(id),
    FOREIGN KEY (id_animale) REFERENCES animali(codicechip)
);

-- Tabella farmaci
CREATE TABLE farmaci (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(100),
    produttore VARCHAR(100)
);

-- Tabella farmaci_has_visite (molti-a-molti)
CREATE TABLE farmaci_has_visite (
    farmaci_id INT,
    visite_id INT,
    PRIMARY KEY (farmaci_id, visite_id),
    FOREIGN KEY (farmaci_id) REFERENCES farmaci(id),
    FOREIGN KEY (visite_id) REFERENCES visite(id)
);
