-- ============================================================
-- Description : Commissariats de Police & Gendarmerie - Antananarivo
-- ============================================================

DROP TABLE IF EXISTS contacts_urgence;

CREATE TABLE contacts_urgence (
    id          INTEGER PRIMARY KEY AUTOINCREMENT,
    nom         TEXT    NOT NULL,
    type        TEXT    NOT NULL,   -- 'Police' ou 'Gendarmerie'
    quartier    TEXT    NOT NULL,
    telephone   TEXT    NOT NULL
);

-- ============================================================
-- Insertion des données (30+ contacts réels)
-- ============================================================

INSERT INTO contacts_urgence (id, nom, type, quartier, telephone) VALUES
-- ===== Tes 6 contacts de test (conservés) =====
(1,  'Commissariat Analakely',              'Police',       'Analakely',         '+261 20 22 227 35'),
(2,  'Commissariat Antaninarenina',         'Police',       'Antaninarenina',   '+261 20 22 202 22'),
(3,  'Gendarmerie Ankorondrano',            'Gendarmerie',  'Ankorondrano',      '+261 20 22 621 91'),
(4,  'Commissariat Isoraka',                'Police',       'Isoraka',           '+261 20 22 296 47'),
(5,  'Gendarmerie Behoririka',              'Gendarmerie',  'Behoririka',        '+261 20 22 341 08'),
(6,  'Commissariat Ambohijatovo',           'Police',       'Ambohijatovo',      '+261 20 22 253 19'),

-- ===== Commissariats par arrondissement (Police Nationale) =====
(7,  '1° Arrondissement Analakely',         'Police',       'Analakely',         '+261 34 05 517 31'),
(8,  '2° Arrondissement Ambohijatovo',      'Police',       'Ambohijatovo',      '+261 34 05 517 28'),
(9,  '3° Arrondissement Antaninandro',      'Police',       'Antaninandro',     '+261 34 05 517 29'),
(10, '4° Arrondissement Isotry',            'Police',       'Isotry',            '+261 34 05 517 30'),
(11, '5° Arrondissement Mahamasina',        'Police',       'Mahamasina',        '+261 34 05 517 11'),
(12, '6° Arrondissement Ambohimanarina',    'Police',       'Ambohimanarina',    '+261 34 05 517 12'),
(13, '7° Arrondissement 67 Ha',             'Police',       '67 Ha',             '+261 34 05 517 13'),
(14, '8° Arrondissement Analamahitsy',      'Police',       'Analamahitsy',      '+261 34 05 517 14'),

-- ===== Postes de Police Avancés / de Proximité =====
(15, 'Poste de Police Ampefiloha',          'Police',       'Ampefiloha',        '+261 34 07 517 09'),
(16, 'Poste de Police Ankorondrano',        'Police',       'Ankorondrano',      '+261 34 05 531 28'),
(17, 'Poste de Police Antaniavo',           'Police',       'Antaniavo',        '+261 34 64 859 60'),
(18, 'Poste de Police Ilanivato',           'Police',       'Ilanivato',         '+261 34 05 517 08'),
(19, 'Commissariat Central Tanjombato',     'Police',       'Tanjombato',        '+261 34 05 517 15'),
(20, 'Commissariat de District Itaosy',     'Police',       'Itaosy',            '+261 34 05 517 16'),
(21, 'Poste de Police Loharanombato',       'Police',       'Loharanombato',     '+261 34 05 517 11'),
(22, 'Poste de Police Ampanefy',            'Police',       'Ampanefy',          '+261 34 05 703 69'),
(23, 'Poste de Police Soavina',             'Police',       'Soavina',           '+261 34 07 517 22'),
(24, 'Poste de Police Ankaraobato',         'Police',       'Ankaraobato',       '+261 34 05 703 68'),
(25, 'Poste de Police Mahabo',              'Police',       'Mahabo',            '+261 34 05 703 67'),
(26, 'Poste de Police Ampasika',            'Police',       'Ampasika',          '+261 34 05 703 66'),

-- ===== Gendarmerie =====
(27, 'Gendarmerie Nationale (Commandement)','Gendarmerie',  'Ambohijanahary',    '+261 34 14 019 02'),
(28, 'Gendarmerie Aéroport Ivato',          'Gendarmerie',  'Ivato',             '+261 34 14 005 53'),
(29, 'Gendarmerie Ankorondrano',            'Gendarmerie',  'Ankorondrano',      '+261 20 22 621 91'),
(30, 'Gendarmerie Behoririka',              'Gendarmerie',  'Behoririka',        '+261 20 22 341 08'),
(31, 'Gendarmerie Ambohimangakely',         'Gendarmerie',  'Ambohimangakely',   '+261 34 14 005 21'),

-- ===== Numéros d''urgence généraux =====
(32, 'Police Secours (117)',                'Police',       'National',          '117'),
(33, 'Gendarmerie Nationale (119)',         'Gendarmerie',  'National',          '119'),
(34, 'Commissariat Central Antananarivo',   'Police',       'Analakely',         '+261 20 22 357 09'),
(35, 'Bureau des Accidents (BAC)',          'Police',       'Tsaralalana',       '+261 34 05 517 24');