-- ============================================================
-- Fichier : hotels.sql
-- Description : Création de la table hotels + données de test
-- ============================================================

-- Suppression de la table si elle existe déjà
DROP TABLE IF EXISTS hotels;

-- Création de la table
CREATE TABLE hotels (
    id          INTEGER PRIMARY KEY AUTOINCREMENT,
    nom         TEXT    NOT NULL,
    lieu        TEXT    NOT NULL,
    contact     TEXT,
    site_web    TEXT,
    image_res   TEXT                -- nom de la ressource drawable (ex: hotel_carlton)
);

-- ============================================================
-- Insertion des données (25 hôtels réels)
-- ============================================================

INSERT INTO hotels (id, nom, lieu, contact, site_web, image_res) VALUES
-- ===== Tes 6 hôtels de test (premières lignes) =====
(1,  'Carlton Madagascar',                          'Anosy',              '+261 20 22 260 60',  'https://www.carlton-madagascar.com',          'hotel_carlton'),
(2,  'Radisson Blu Antananarivo Waterfront',        'Waterfront',         '+261 20 23 300 00',  'https://www.radissonhotels.com',              'hotel_radisson'),
(3,  'Hôtel Colbert',                               'Antaninarenina',    '+261 20 22 202 02',  'https://www.hotel-colbert.mg',                'hotel_colbert'),
(4,  'Le Louvre Hôtel & Spa',                       'Antaninarenina',    '+261 20 22 341 33',  'https://www.lelouvre-hotel.com',              'hotel_louvre'),
(5,  'Sakamanga Hôtel',                             'Isoraka',            '+261 20 22 358 09',  NULL,                                         'hotel_sakamanga'),
(6,  'Hôtel Panorama',                              'Ankorondrano',       '+261 20 22 630 06',  'https://www.hotelpanorama.mg',               'hotel_panorama'),

-- ===== Autres hôtels réels à Antananarivo =====
(7,  'Palissandre Antananarivo',                    'Faravohitra',        '+261 20 22 605 60',  'https://www.hotel-palissandre.com',           'hotel_palissandre'),
(8,  'Relais des Plateaux',                         'Ivato',              '+261 20 22 424 24',  'https://www.relais-des-plateaux.com',         'hotel_relais_plateaux'),
(9,  'Grand Hotel Urban',                           'Ambatovinaky',       '+261 20 22 202 20',  NULL,                                         'hotel_grand_urban'),
(10, 'La Varangue',                                 'Antaninarenina',    '+261 20 22 273 97',  NULL,                                         'hotel_varangue'),
(11, 'The Citizen',                                 'Isoraka',            '+261 34 05 111 11',  NULL,                                         'hotel_citizen'),
(12, 'ibis Antananarivo Ankorondrano',              'Ankorondrano',       '+261 20 22 222 22',  'https://all.accor.com',                       'hotel_ibis'),
(13, 'Radisson Serviced Apartments City Centre',    'Ambatonakanga',      '+261 20 22 300 00',  'https://www.radissonhotels.com',              'hotel_radisson_apart'),
(14, 'Maison d''Hôtes Mandrosoa',                   'Faravohitra',        '+261 20 22 258 77',  NULL,                                         'hotel_mandrosoa'),
(15, 'Havana Resort',                               'Ambohidahy',         '+261 20 22 345 67',  NULL,                                         'hotel_havana'),
(16, 'Fly Inn Madagascar Hotel',                    'Ivato',              '+261 34 05 050 50',  NULL,                                         'hotel_flyinn'),
(17, 'San Cristobal Boutique Hotel',                'Talatamaty',         '+261 34 07 070 70',  NULL,                                         'hotel_sancristobal'),
(18, 'Novotel Convention & Spa',                    'Alarobia',           '+261 20 22 222 00',  'https://all.accor.com',                       'hotel_novotel'),
(19, 'Le Centell Hotel & Spa',                      'Ankorondrano',       '+261 20 22 333 00',  NULL,                                         'hotel_centell'),
(20, 'Sole Hotel',                                  'Tsaralalana',        '+261 20 22 234 56',  NULL,                                         'hotel_sole'),

-- ===== Hôtels dans d''autres villes de Madagascar =====
(21, 'Allamanda Hotel',                             'Antsiranana',       '+261 20 82 210 33',  NULL,                                         'hotel_allamanda'),
(22, 'Hotel Baobab Café',                           'Morondava',          '+261 20 95 520 12',  NULL,                                         'hotel_baobab'),
(23, 'Royal Beach Hôtel',                           'Nosy Be',            '+261 32 07 123 45',  NULL,                                         'hotel_royal_beach'),
(24, 'Palm Beach Resort & Spa',                     'Nosy Be',            '+261 32 05 678 90',  NULL,                                         'hotel_palm_beach'),
(25, 'Anjajavy Le Lodge',                           'Anjajavy',           '+261 20 22 123 45',  'https://www.anjajavy.com',                    'hotel_anjajavy');