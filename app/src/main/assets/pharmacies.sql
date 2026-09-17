-- ============================================================
-- Fichier : pharmacies.sql
-- Description : Tables pharmacies + semaine de garde
-- ============================================================

DROP TABLE IF EXISTS pharmacies_de_garde;
DROP TABLE IF EXISTS semaines_de_garde;
DROP TABLE IF EXISTS pharmacies;

-- ------------------------------------------------------------
-- Table des pharmacies
-- ------------------------------------------------------------
CREATE TABLE pharmacies (
    id      INTEGER PRIMARY KEY AUTOINCREMENT,
    nom     TEXT    NOT NULL,
    lieu    TEXT    NOT NULL
);

-- ------------------------------------------------------------
-- Table des semaines de garde
-- ------------------------------------------------------------
CREATE TABLE semaines_de_garde (
    id          INTEGER PRIMARY KEY AUTOINCREMENT,
    date_debut  TEXT    NOT NULL,   -- format JJ/MM/AAAA
    date_fin    TEXT    NOT NULL
);

-- ------------------------------------------------------------
-- Table de liaison (pharmacies de garde pour une semaine)
-- ------------------------------------------------------------
CREATE TABLE pharmacies_de_garde (
    id              INTEGER PRIMARY KEY AUTOINCREMENT,
    semaine_id      INTEGER NOT NULL,
    nom             TEXT    NOT NULL,
    lieu            TEXT    NOT NULL,
    FOREIGN KEY (semaine_id) REFERENCES semaines_de_garde(id)
);

-- ============================================================
-- Données : 30+ pharmacies
-- ============================================================

INSERT INTO pharmacies (id, nom, lieu) VALUES
-- ===== Tes 6 pharmacies de test =====
(1,  'Pharmacie Analakely',              'Analakely'),
(2,  'Pharmacie de Ambohijatovo',        'Ambohijatovo'),
(3,  'Pharmacie Antaninarenina',         'Antaninarenina'),
(4,  'Pharmacie Mahamasina',             'Mahamasina'),
(5,  'Pharmacie Isoraka',                'Isoraka'),
(6,  'Pharmacie CAPITALE',               'Ankazomanga'),

-- ===== Autres pharmacies réelles =====
(7,  'Pharmacie Métropole',              'Antaninarenina'),
(8,  'Pharmacie d''Ampandrana',          'Ampandrana'),
(9,  'Pharmacie de la Croix du Sud',     'Antanimena'),
(10, 'Pharmacie Principale',             'Ivandry'),
(11, 'Pharmacie d''Ivandry',             'Ivandry'),
(12, 'Pharmacie Horizon',                'Ivandry'),
(13, 'Pharmacie Hary Soa',               'Ankorondrano'),
(14, 'Pharmacie Hasimbola',              'Ankorondrano'),
(15, 'Pharmacie du Roi',                 'Ambodivona'),
(16, 'Pharmacie de la Digue',            'Akoor Digue'),
(17, 'Pharmacie Gràce',                  'Andranomena'),
(18, 'Pharmacie Jade',                   'Ivato'),
(19, 'Pharmacie Mandrosoa',              'Talatamaty'),
(20, 'Pharmacie d''Ivato',               'Ivato'),
(21, 'Pharmacie d''Ampefiloha',          'Ampefiloha'),
(22, 'Pharmacie 67 Ha',                  '67 Ha'),
(23, 'Pharmacie Nanisana',               'Nanisana'),
(24, 'Pharmacie de Tanjombato',          'Tanjombato'),
(25, 'Pharmacie Hanitra',                'Ankorahotra'),
(26, 'Pharmacie de l''Avenir',           'Amparibe'),
(27, 'Pharmacie d''Avaradrano',          'Analamahitsy'),
(28, 'Pharmacie d''Amboditsiry',         'Amboditsiry'),
(29, 'Pharmacie d''Ambohibao',           'Ambohibao'),
(30, 'Pharmacie Namontana',              'Namontana'),
(31, 'Pharmacie de Tana',                'Antsahavola'),
(32, 'Pharmacie Havana',                 '67 Ha'),
(33, 'Pharmacie Santilo',                'Anosizato'),
(34, 'Pharmacie d''Andoharanofotsy',     'Andoharanofotsy'),
(35, 'Pharmacie Grazia',                 'Itaosy');

-- ============================================================
-- Semaine de garde (tes dates + pharmacies de garde)
-- ============================================================

INSERT INTO semaines_de_garde (id, date_debut, date_fin) VALUES
(1, '12/09/2026', '19/09/2026');

INSERT INTO pharmacies_de_garde (semaine_id, nom, lieu) VALUES
-- Tes 3 pharmacies de garde (conservées)
(1, 'Pharmacie CAPITALE',    'Ankazomanga'),
(1, 'Pharmacie Isoraka',     'Isoraka'),
(1, 'Pharmacie Mahamasina',  'Mahamasina'),

-- Pharmacies de garde supplémentaires (réalistes)
(1, 'Pharmacie d''Ampandrana',   'Ampandrana'),
(1, 'Pharmacie d''Ivandry',      'Ivandry'),
(1, 'Pharmacie Ambodivona',      'Ambodivona'),
(1, 'Pharmacie de la Croix du Sud', 'Antanimena');