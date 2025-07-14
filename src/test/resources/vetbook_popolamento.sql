-- Popolamento base
use vetbook;

INSERT INTO utenti (id, nome, cognome, email, username, password, immagine_profilo, ruolo) VALUES

(1, NULL, NULL, NULL, 'drroberto', 'vet123', NULL, 'VETERINARIO'),
(2, NULL, NULL, NULL, 'admin1', 'admin123', NULL, 'AMMINISTRATORE'),
(3, 'Mario', 'Rossi', 'mario.rossi@email.it', 'mrossi', 'proprietario123', NULL, 'PROPRIETARIO');

INSERT INTO disponibilita (id, data_visita, ora_visita, stato, prenotazioni_id) VALUES

(NULL, '2026-07-13', '10:00:00', 'PRENOTATA', NULL),
(NULL, '2026-07-14', '10:00:00', 'LIBERA', NULL),
(NULL, '2026-07-15', '10:00:00', 'LIBERA', NULL),
(NULL, '2026-07-16', '10:00:00', 'PRENOTATA', NULL),
(NULL, '2026-07-17', '10:00:00', 'LIBERA', NULL);

INSERT INTO animali (codicechip, nome, tipo, razza, colore, data_nascita, idproprietario, vaccinazione) VALUES

(1000000001, 'Fido', 'Cane', 'Labrador', 'Nero', '2019-04-10', 3, '2024-04-01'),
(1000000002, 'Micio', 'Gatto', 'Persiano', 'Bianco', '2020-06-15', 3, '2023-12-15'),
(1000000003, 'Rex', 'Cane', 'Pastore Tedesco', 'Marrone', '2018-01-25', 3, NULL);

INSERT INTO visite (id, tipo_visita, descrizione, costo, data_visita, ora_visita, id_veterinario) VALUES
(NULL, 'CONTROLLO', 'Controllo generale.', 40.00, '2025-04-13', '10:00:00', 1),
(NULL, 'VACCINAZIONE', 'Vaccino annuale.', 35.00, '2025-04-14', '10:00:00', 1),
(NULL, 'CHIRURGIA', 'Rimozione cisti.', 150.00, '2025-04-16', '10:00:00', 1);


INSERT INTO prenotazioni (id, id_proprietario, id_animale, data_prenotazione, ora_prenotazione) VALUES

(NULL, 3, 1000000001, '2025-08-13', '10:00:00'),
(NULL, 3, 1000000002, '2025-08-14', '10:00:00');

INSERT INTO farmaci (nome, produttore) VALUES

('Antibiocan', 'VetLife'),
('Cortidog', 'PetPharma'),
('Antipul', 'SalusVet'),
('Vaccidogin', 'BioVet'),
('DolorPet', 'MediPet'),
('Antiviralex', 'VetBioLab'),
('Otoclean', 'Aurora PetCare'),
('Antinfiammix', 'ZampaSoft'),
('Parassil', 'FidoMed'),
('Vitavet C', 'NaturaVet');

INSERT INTO farmaci_has_visite (farmaci_id, visite_id) VALUES

(1, 1),
(2, 2),
(3, 3);