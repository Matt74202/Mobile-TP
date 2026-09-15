-- ============================================================
-- Fichier : activites.sql
-- Catégories : Sport, Bien-être, Culture, Nature, Détente
-- ============================================================

DROP TABLE IF EXISTS activites;

CREATE TABLE activites (
    id          INTEGER PRIMARY KEY AUTOINCREMENT,
    nom         TEXT    NOT NULL,
    type        TEXT    NOT NULL,   -- type précis (Cinéma, Spa, Tennis...)
    categorie   TEXT    NOT NULL,   -- Sport | Bien-être | Culture | Nature | Détente
    lieu        TEXT    NOT NULL,
    latitude    REAL    NOT NULL,
    longitude   REAL    NOT NULL
);

INSERT INTO activites (id, nom, type, categorie, lieu, latitude, longitude) VALUES
-- ===== Tes 10 activités de test (conservées) =====
(1,  'Canal Olympia Andohatapenaka',     'Cinéma',                  'Détente',      'Andohatapenaka',     -18.898000, 47.506000),
(2,  'Padel Club Ivandry',               'Padel',                   'Sport',        'Ivandry',            -18.868000, 47.531000),
(3,  'Spa Le Louvre',                    'Spa',                     'Bien-être',    'Antaninarenina',    -18.908000, 47.524000),
(4,  'Bowling Tana Waterfront',          'Bowling',                 'Détente',      'Waterfront',         -18.891000, 47.524000),
(5,  'Tennis Club Ankorondrano',         'Tennis',                  'Sport',        'Ankorondrano',       -18.880000, 47.522000),
(6,  'Musée Andafiavaratra',             'Visite guidée',           'Culture',      'Andafiavaratra',     -18.917000, 47.531000),
(7,  'Salle de sport Fitness Park',      'Musculation',             'Sport',        'Ankorondrano',       -18.878000, 47.523000),
(8,  'Parc Tsarasaotra',                 'Observation d''oiseaux',  'Nature',       'Alarobia',           -18.870000, 47.535000),
(9,  'Escape Game Tana',                 'Escape game',             'Détente',      'Isoraka',            -18.912000, 47.518000),
(10, 'Piscine Hôtel Panorama',           'Natation',                'Sport',        'Ankorondrano',       -18.876000, 47.520000),

-- ===== Culture =====
(11, 'Rova de Manjakamiadana',           'Visite guidée',           'Culture',      'Haute-Ville',        -18.923600, 47.532000),
(12, 'Musée de la Photo',                'Musée',                   'Culture',      'Haute-Ville',        -18.919000, 47.530000),
(13, 'Musée d''Art et d''Archéologie',   'Musée',                   'Culture',      'Isoraka',            -18.913000, 47.519000),
(14, 'Musée des Pirates',                'Musée',                   'Culture',      'Isoraka',            -18.911000, 47.517000),
(15, 'Cathédrale d''Andohalo',           'Visite guidée',           'Culture',      'Andohalo',           -18.920000, 47.528000),
(16, 'Palais de la Reine (Rova)',        'Visite guidée',           'Culture',      'Haute-Ville',        -18.923000, 47.532500),

-- ===== Nature =====
(17, 'Parc Botanique et Zoologique Tsimbazaza', 'Zoo / Parc',       'Nature',       'Tsimbazaza',         -18.928000, 47.525000),
(18, 'Lemurs'' Park',                    'Parc animalier',          'Nature',       'PK22 (Ouest)',       -18.950000, 47.400000),
(19, 'Lac Anosy',                        'Promenade',               'Nature',       'Anosy',              -18.915000, 47.521000),
(20, 'Croc Farm',                        'Parc animalier',          'Nature',       'Ivato',              -18.805000, 47.478000),
(21, 'Parc de Tsarasaotra (Lac Alarobia)','Observation d''oiseaux', 'Nature',       'Alarobia',           -18.870500, 47.535500),

-- ===== Sport =====
(22, 'Golf du Rova',                     'Golf',                    'Sport',        'Ambohimanga',        -18.760000, 47.560000),
(23, 'Fitness Center Ankorondrano',      'Musculation',             'Sport',        'Ankorondrano',       -18.879000, 47.522500),
(24, 'Piscine Carlton Madagascar',       'Natation',                'Sport',        'Anosy',              -18.912000, 47.520000),
(25, 'Stade Municipal de Mahamasina',    'Football',                'Sport',        'Mahamasina',         -18.918000, 47.525000),
(26, 'Tennis Club Ivandry',              'Tennis',                  'Sport',        'Ivandry',            -18.869000, 47.530000),

-- ===== Bien-être =====
(27, 'Spa Radisson Blu Waterfront',      'Spa',                     'Bien-être',    'Waterfront',         -18.890000, 47.523500),
(28, 'Spa Carlton Madagascar',           'Spa',                     'Bien-être',    'Anosy',              -18.912500, 47.520500),
(29, 'Spa Hôtel Colbert',                'Spa',                     'Bien-être',    'Antaninarenina',    -18.909000, 47.525000),
(30, 'Centre de Yoga Isoraka',           'Yoga',                    'Bien-être',    'Isoraka',            -18.912500, 47.518500),

-- ===== Détente =====
(31, 'Canal Olympia Andraharo',          'Cinéma',                  'Détente',      'Andraharo',          -18.885000, 47.510000),
(32, 'Tana Waterfront (loisirs)',        'Shopping / Loisirs',      'Détente',      'Waterfront',         -18.891500, 47.524500),
(33, 'Akoor Digue',                      'Shopping / Loisirs',      'Détente',      'Andohatapenaka',     -18.897000, 47.505000),
(34, 'Bowling / Loisirs Ankorondrano',   'Bowling',                 'Détente',      'Ankorondrano',       -18.881000, 47.521000),
(35, 'Escape Game Antananarivo',         'Escape game',             'Détente',      'Isoraka',            -18.911500, 47.517500),

-- ===== Karting & sports mécaniques =====
(36, 'Serana Racing Kart (SRK)',          'Karting',                 'Sport',        'Imerintsiatosika',   -18.970000, 47.420000),
(37, 'Havoana Land Karting',              'Karting',                 'Sport',        'Havoana Land',       -18.920000, 47.480000),

-- ===== Paintball / Aventure =====
(38, 'Marlix Adventure Club',             'Paintball / Accrobranche','Détente',      'Andakana (RN4)',     -18.850000, 47.450000),
(39, 'Top Paintball Ilafy',               'Paintball',               'Détente',      'Ilafy',              -18.860000, 47.560000),
(40, 'Marlix Mini-Golf',                  'Mini-golf',               'Détente',      'Andakana (RN4)',     -18.850500, 47.450500),

-- ===== Quad / Outdoor =====
(41, 'Gasy Quad Ivato',                   'Quad',                    'Sport',        'Ivato',              -18.800000, 47.480000),
(42, 'Parc Sam Run',                      'Parc d''attractions',      'Détente',      'Ouest Tana',         -18.910000, 47.470000),

-- ===== Autres sports / loisirs =====
(43, 'Golf du Rova',                      'Golf',                    'Sport',        'Ambohimanga',        -18.760000, 47.560000),
(44, 'Malaza Golf',                       'Golf',                    'Sport',        'Ambodiafotsy',       -18.930000, 47.400000),
(45, 'Galaxy Golf Andraharo',             'Golf (practice)',         'Sport',        'Andraharo',          -18.885000, 47.508000),
(46, 'Centre équestre Marlix',            'Équitation',              'Sport',        'Andakana (RN4)',     -18.851000, 47.451000),
(47, 'Club du Car',                       'Multisports',             'Sport',        'Route aéroport',     -18.820000, 47.490000);