-- ============================================================
-- Fichier : banques.sql
-- Description : Création de la table banques + 35 agences
-- ============================================================

DROP TABLE IF EXISTS banques;

CREATE TABLE banques (
    id          INTEGER PRIMARY KEY AUTOINCREMENT,
    nom         TEXT    NOT NULL,
    reseau      TEXT    NOT NULL,
    lieu        TEXT    NOT NULL
);

INSERT INTO banques (id, nom, reseau, lieu) VALUES
-- ===== Tes 8 banques de test =====
(1,  'BNI Analakely',                    'BNI',           'Analakely'),
(2,  'BNI Ankorondrano',                 'BNI',           'Ankorondrano'),
(3,  'BOA Antaninarenina',               'BOA',           'Antaninarenina'),
(4,  'BOA Ankorondrano',                 'BOA',           'Ankorondrano'),
(5,  'BFV-SG Analakely',                 'BFV-SG',        'Analakely'),
(6,  'BMOI Haute-Ville',                 'BMOI',          'Haute-Ville'),
(7,  'Access Bank Ambatonakanga',        'Access Bank',   'Ambatonakanga'),
(8,  'AFG Bank Tanjombato',              'AFG Bank',      'Tanjombato'),

-- ===== BNI =====
(9,  'BNI Andraharo',                    'BNI',           'Andraharo'),
(10, 'BNI Ampefiloha',                   'BNI',           'Ampefiloha'),
(11, 'BNI Ivandry',                      'BNI',           'Ivandry'),
(12, 'BNI Tanjombato',                   'BNI',           'Tanjombato'),
(13, 'BNI Antsahavola',                  'BNI',           'Antsahavola'),
(14, 'BNI Ambohibao',                    'BNI',           'Ambohibao'),
(15, 'BNI Behoririka',                   'BNI',           'Behoririka'),

-- ===== BOA =====
(16, 'BOA Analakely',                    'BOA',           'Analakely'),
(17, 'BOA Andraharo',                    'BOA',           'Andraharo'),
(18, 'BOA Tanjombato',                   'BOA',           'Tanjombato'),
(19, 'BOA Ampefiloha',                   'BOA',           'Ampefiloha'),
(20, 'BOA Ivandry',                      'BOA',           'Ivandry'),
(21, 'BOA 67 Ha',                        'BOA',           '67 Ha'),

-- ===== BMOI =====
(22, 'BMOI Antaninarenina',              'BMOI',          'Antaninarenina'),
(23, 'BMOI Ankorondrano',                'BMOI',          'Ankorondrano'),
(24, 'BMOI Tanjombato',                  'BMOI',          'Tanjombato'),
(25, 'BMOI Galaxy Andraharo',            'BMOI',          'Andraharo'),
(26, 'BMOI Talatamaty',                  'BMOI',          'Talatamaty'),
(27, 'BMOI Analamahitsy',                'BMOI',          'Analamahitsy'),

-- ===== BFV-SG / BRED =====
(28, 'BFV-SG Ankorondrano',              'BFV-SG',        'Ankorondrano'),
(29, 'BFV-SG Andraharo',                 'BFV-SG',        'Andraharo'),
(30, 'BFV-SG Antaninarenina',            'BFV-SG',        'Antaninarenina'),
(31, 'BRED Antaninarenina',              'BRED',          'Antaninarenina');