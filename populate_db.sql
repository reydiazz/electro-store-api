-- =========================================================================
-- 1. docker cp populate_db.sql electro_sqlserver:/tmp/populate_db.sql
-- 2. docker exec electro_sqlserver /opt/mssql-tools18/bin/sqlcmd -S localhost
-- ELECTRO STORE - SCRIPT DE POBLADO DE BASE DE DATOS ULTRA REALISTA
-- Con formatos de código del backend de 20 caracteres:
--   PREFIX (3) + Timestamp yyMMddHHmmss (12) + Suffix/Random (5)
-- Cubre: 2025-01-01 al 2026-07-18
-- =========================================================================

SET QUOTED_IDENTIFIER ON;
SET ANSI_NULLS ON;
SET ANSI_WARNINGS ON;
SET ANSI_PADDING ON;
SET ARITHABORT ON;
SET CONCAT_NULL_YIELDS_NULL ON;
SET NUMERIC_ROUNDABORT OFF;

BEGIN TRANSACTION;

BEGIN TRY

    PRINT 'Limpiando datos existentes...';

    DELETE FROM guide_details;
    DELETE FROM inventory_guides;
    DELETE FROM purchase_details;
    DELETE FROM purchases;
    DELETE FROM sale_details;
    DELETE FROM sales;
    DELETE FROM products;
    DELETE FROM product_categories;
    DELETE FROM users;
    DELETE FROM employees;
    DELETE FROM customers;
    DELETE FROM suppliers;
    DELETE FROM people;

    -- =========================================================================
    -- PERSONAS (20 caracteres)
    -- =========================================================================
    PRINT 'Insertando personas...';

    -- Empleados (5)
    INSERT INTO people (code, first_name, last_name, phone, national_id) VALUES
    ('PEO250101120000PEO01', 'Carlos',    'Mendoza',    '+51 987654321', '74224731'),
    ('PEO250101120000PEO02', 'Sofia',     'Castro',     '+51 987654322', '74224732'),
    ('PEO250101120000PEO03', 'Diego',     'Torres',     '+51 987654323', '74224733'),
    ('PEO250101120000PEO04', 'Gabriela',  'Rios',       '+51 987654324', '74224734'),
    ('PEO250101120000PEO05', 'Miguel',    'Paredes',    '+51 987654325', '74224735');

    -- Consumidor Final (especial)
    INSERT INTO people (code, first_name, last_name, phone, national_id) VALUES
    ('PEO250101120000PEO06', 'Consumidor', 'Final', '+51 000000000', NULL);

    -- Clientes (15)
    INSERT INTO people (code, first_name, last_name, phone, national_id) VALUES
    ('PEO250101120000PEO07', 'Juan',      'Perez',      '+51 991001001', '72100001'),
    ('PEO250101120000PEO08', 'Maria',     'Rodriguez',  '+51 991001002', '72100002'),
    ('PEO250101120000PEO09', 'Carlos',    'Gomez',      '+51 991001003', '72100003'),
    ('PEO250101120000PEO10', 'Ana',       'Martinez',   '+51 991001004', '72100004'),
    ('PEO250101120000PEO11', 'Luis',      'Flores',     '+51 991001005', '72100005'),
    ('PEO250101120000PEO12', 'Rosa',      'Huanca',     '+51 991001006', '72100006'),
    ('PEO250101120000PEO13', 'Pedro',     'Salinas',    '+51 991001007', '72100007'),
    ('PEO250101120000PEO14', 'Elena',     'Vargas',     '+51 991001008', '72100008'),
    ('PEO250101120000PEO15', 'Jorge',     'Castillo',   '+51 991001009', '72100009'),
    ('PEO250101120000PEO16', 'Lucia',     'Reyes',      '+51 991001010', '72100010'),
    ('PEO250101120000PEO17', 'Roberto',   'Chavez',     '+51 991001011', '72100011'),
    ('PEO250101120000PEO18', 'Patricia',  'Leon',       '+51 991001012', '72100012'),
    ('PEO250101120000PEO19', 'Fernando',  'Mora',       '+51 991001013', '72100013'),
    ('PEO250101120000PEO20', 'Carla',     'Vega',       '+51 991001014', '72100014'),
    ('PEO250101120000PEO21', 'Ricardo',   'Aguirre',    '+51 991001015', '72100015');

    -- =========================================================================
    -- EMPLEADOS (20 caracteres)
    -- =========================================================================
    PRINT 'Insertando empleados...';

    INSERT INTO employees (code, person_code, position, salary) VALUES
    ('EMP250101120000EMP01', 'PEO250101120000PEO01', 'MANAGER',     5500.00),
    ('EMP250101120000EMP02', 'PEO250101120000PEO02', 'SELLER',      2800.00),
    ('EMP250101120000EMP03', 'PEO250101120000PEO03', 'STOREKEEPER', 3200.00),
    ('EMP250101120000EMP04', 'PEO250101120000PEO04', 'SELLER',      2800.00),
    ('EMP250101120000EMP05', 'PEO250101120000PEO05', 'SELLER',      2800.00);

    -- =========================================================================
    -- USUARIOS (20 caracteres)
    -- admin	    ADMIN	    admin123
    -- sofia	    SELLER	    seller123
    -- almacenero	STOREKEEPER	almacenero123
    -- gabriela	    SELLER	    seller123
    -- miguel	    SELLER	    seller123
    -- =========================================================================
    PRINT 'Insertando usuarios...';

    INSERT INTO users (code, employee_code, username, password, role) VALUES
    ('USR250101120000USR01', 'EMP250101120000EMP01', 'admin',      '$2a$10$yHYv0ARBbrp2fQxDP//Ckury1gFDq.UOFHDIaGj/kWCrZWL9I5/X6', 'ADMIN'),
    ('USR250101120000USR02', 'EMP250101120000EMP02', 'sofia',      '$2a$10$LbKaa0LJvSrePm1PS6n0TebHdKDP5D53pdygEToxLJAd3JzNhkiCi', 'SELLER'),
    ('USR250101120000USR03', 'EMP250101120000EMP03', 'almacenero', '$2a$10$vgitMGr7kFgoI51J4bqGiud.ybZSOSyqmzEM4.CUL7HpYGcL7V89O', 'STOREKEEPER'),
    ('USR250101120000USR04', 'EMP250101120000EMP04', 'gabriela',   '$2a$10$LbKaa0LJvSrePm1PS6n0TebHdKDP5D53pdygEToxLJAd3JzNhkiCi', 'SELLER'),
    ('USR250101120000USR05', 'EMP250101120000EMP05', 'miguel',     '$2a$10$LbKaa0LJvSrePm1PS6n0TebHdKDP5D53pdygEToxLJAd3JzNhkiCi', 'SELLER');

    -- =========================================================================
    -- CLIENTES (20 caracteres)
    -- =========================================================================
    PRINT 'Insertando clientes...';

    -- Consumidor Final (obligatorio primero)
    INSERT INTO customers (code, person_code, tax_id) VALUES
    ('CUS250101120000CUS00', 'PEO250101120000PEO06', NULL);

    INSERT INTO customers (code, person_code, tax_id) VALUES
    ('CUS250101120000CUS01', 'PEO250101120000PEO07', '10721000011'),
    ('CUS250101120000CUS02', 'PEO250101120000PEO08', '10721000022'),
    ('CUS250101120000CUS03', 'PEO250101120000PEO09', NULL),
    ('CUS250101120000CUS04', 'PEO250101120000PEO10', '10721000044'),
    ('CUS250101120000CUS05', 'PEO250101120000PEO11', NULL),
    ('CUS250101120000CUS06', 'PEO250101120000PEO12', '10721000066'),
    ('CUS250101120000CUS07', 'PEO250101120000PEO13', NULL),
    ('CUS250101120000CUS08', 'PEO250101120000PEO14', '10721000088'),
    ('CUS250101120000CUS09', 'PEO250101120000PEO15', NULL),
    ('CUS250101120000CUS10', 'PEO250101120000PEO16', '10721000100'),
    ('CUS250101120000CUS11', 'PEO250101120000PEO17', NULL),
    ('CUS250101120000CUS12', 'PEO250101120000PEO18', '10721000122'),
    ('CUS250101120000CUS13', 'PEO250101120000PEO19', NULL),
    ('CUS250101120000CUS14', 'PEO250101120000PEO20', '10721000144'),
    ('CUS250101120000CUS15', 'PEO250101120000PEO21', NULL);

    -- =========================================================================
    -- PROVEEDORES (20 caracteres)
    -- =========================================================================
    PRINT 'Insertando proveedores...';

    INSERT INTO suppliers (code, tax_id, trade_name, phone, legal_name) VALUES
    ('SUP250101120000SUP01', '20600123451', 'Distribuidora Tech S.A.',  '+51 911111111', 'DISTRIBUIDORA TECH SOCIEDAD ANONIMA'),
    ('SUP250101120000SUP02', '20600123452', 'Sony Peru',                '+51 922222222', 'SONY PERU S.R.L.'),
    ('SUP250101120000SUP03', '20600123453', 'Logitech Wholesale',       '+51 933333333', 'LOGITECH WHOLESALE PERU S.A.C.'),
    ('SUP250101120000SUP04', '20600123454', 'Apple Importaciones',      '+51 944444444', 'APPLE IMPORTACIONES Y DISTRIBUCION S.A.C.'),
    ('SUP250101120000SUP05', '20600123455', 'Samsung Electronics Peru', '+51 955555555', 'SAMSUNG ELECTRONICS PERU S.A.C.'),
    ('SUP250101120000SUP06', '20600123456', 'Lenovo Peru',              '+51 966666666', 'LENOVO PERU S.A.C.'),
    ('SUP250101120000SUP07', '20600123457', 'LG Electronics Peru',      '+51 977777777', 'LG ELECTRONICS PERU S.A.'),
    ('SUP250101120000SUP08', '20600123458', 'Importaciones Global',     '+51 988888888', 'IMPORTACIONES GLOBAL E.I.R.L.');

    -- =========================================================================
    -- CATEGORIAS (20 caracteres)
    -- =========================================================================
    PRINT 'Insertando categorias...';

    INSERT INTO product_categories (code, name) VALUES
    ('CAT250101120000CAT01', 'Laptops & Computadoras'),
    ('CAT250101120000CAT02', 'Celulares & Smartphones'),
    ('CAT250101120000CAT03', 'Televisores & Smart TVs'),
    ('CAT250101120000CAT04', 'Audio & Sonido'),
    ('CAT250101120000CAT05', 'Consolas & Videojuegos'),
    ('CAT250101120000CAT06', 'Smartwatches & Wearables'),
    ('CAT250101120000CAT07', 'Camaras & Fotografia'),
    ('CAT250101120000CAT08', 'Accesorios & Componentes');

    -- =========================================================================
    -- PRODUCTOS (50 en total)
    -- Distribuimos asimétricamente para que la torta de categorías sea natural:
    --   - Laptops: 5
    --   - Celulares: 8
    --   - Televisores: 4
    --   - Audio: 9
    --   - Consolas: 5
    --   - Smartwatches: 6
    --   - Cámaras: 4
    --   - Accesorios: 9
    -- =========================================================================
    PRINT 'Insertando productos...';

    -- CAT01: Laptops & Computadoras (5 productos)
    INSERT INTO products (code, category_code, name, brand, model, sale_price, stock, description, warranty_months, low_stock) VALUES
    ('PRD250101120000PRD01', 'CAT250101120000CAT01', 'Laptop ASUS ROG Zephyrus G14',        'ASUS',           'GA402XV',           6599.00, 18, 'Laptop gaming de 14 pulgadas con procesador Ryzen 9 y RTX 4060.',           24, 5),
    ('PRD250101120000PRD02', 'CAT250101120000CAT01', 'MacBook Air 13 M3',                   'Apple',          'A3113',             5299.00, 14, 'Laptop ultradelgada con chip Apple M3, 8GB de RAM y 256GB SSD.',            12, 5),
    ('PRD250101120000PRD03', 'CAT250101120000CAT01', 'Lenovo ThinkPad X1 Carbon Gen 11',    'Lenovo',         '21HM002DUS',        7899.00, 10, 'Laptop corporativa ultraligera con procesador Intel Core i7.',              36, 5),
    ('PRD250101120000PRD04', 'CAT250101120000CAT01', 'Dell XPS 15 9530',                    'Dell',           'XPS15-7000',        8499.00, 12, 'Laptop premium con pantalla OLED de 15.6 pulgadas e Intel Core i9.',        12, 5),
    ('PRD250101120000PRD05', 'CAT250101120000CAT01', 'HP Spectre x360 14',                  'HP',             '2V9N4EA',           6299.00, 10, 'Laptop 2 en 1 con pantalla OLED tactil, Intel Core Ultra 7 y 32GB RAM.',    24, 5);

    -- CAT02: Celulares & Smartphones (8 productos)
    INSERT INTO products (code, category_code, name, brand, model, sale_price, stock, description, warranty_months, low_stock) VALUES
    ('PRD250101120000PRD06', 'CAT250101120000CAT02', 'Acer Predator Helios 16',             'Acer',           'PH16-71',           7499.00,  0, 'Laptop gaming con pantalla 240Hz, Intel Core i9 y RTX 4070.',               24, 3), -- AGOTADO (Alerta Alta)
    ('PRD250101120000PRD07', 'CAT250101120000CAT02', 'Microsoft Surface Pro 9',             'Microsoft',      'QIL-00012',         5999.00,  1, 'Tablet convertible con chip Intel Core i7 y Surface Slim Pen 2.',           12, 5), -- STOCK BAJO (Alerta Media)
    ('PRD250101120000PRD08', 'CAT250101120000CAT02', 'iPhone 15 Pro Max 256GB',             'Apple',          'MU773QL/A',         5499.00, 20, 'Smartphone con titanio, chip A17 Pro y camara teleobjetivo 5x.',            12, 5),
    ('PRD250101120000PRD09', 'CAT250101120000CAT02', 'Samsung Galaxy S24 Ultra 512GB',      'Samsung',        'SM-S928B',          4999.00, 25, 'Smartphone con Galaxy AI, pantalla plana 6.8 y S Pen integrado.',           12, 5),
    ('PRD250101120000PRD10', 'CAT250101120000CAT02', 'Xiaomi 14 Ultra 5G',                  'Xiaomi',         '24030PN60G',        4299.00,  4, 'Smartphone con optica Leica y sensor de 1 pulgada.',                        12, 5), -- REORDENAR (Alerta Baja)
    ('PRD250101120000PRD11', 'CAT250101120000CAT02', 'Google Pixel 8 Pro 128GB',            'Google',         'GC3VE',             3699.00,  0, 'Smartphone con Google Tensor G3 y la mejor camara en Android.',            12, 5), -- AGOTADO (Alerta Alta)
    ('PRD250101120000PRD12', 'CAT250101120000CAT02', 'Samsung Galaxy A54 5G 128GB',         'Samsung',        'SM-A546B',          1799.00, 30, 'Smartphone gama media con pantalla Super AMOLED y camara de 50MP.',         12, 8),
    ('PRD250101120000PRD13', 'CAT250101120000CAT02', 'Motorola Edge 40 Pro',                'Motorola',       'XT2301-5',          2999.00, 12, 'Smartphone con pantalla pOLED 165Hz y carga rapida de 125W.',               12, 5);

    -- CAT03: Televisores & Smart TVs (4 productos)
    INSERT INTO products (code, category_code, name, brand, model, sale_price, stock, description, warranty_months, low_stock) VALUES
    ('PRD250101120000PRD14', 'CAT250101120000CAT03', 'Realme GT5 Pro',                      'Realme',         'RMX3888',           2499.00, 10, 'Smartphone gaming con Snapdragon 8 Gen 3 y carga de 100W.',                 12, 5),
    ('PRD250101120000PRD15', 'CAT250101120000CAT03', 'Smart TV LG OLED C3 55"',             'LG',             'OLED55C3PSA',       3899.00, 10, 'Televisor OLED 4K con procesador a9 Gen6 y soporte Dolby Vision.',          24, 3),
    ('PRD250101120000PRD16', 'CAT250101120000CAT03', 'Smart TV Samsung Neo QLED 65"',        'Samsung',        'QN65QN90CAG',       4999.00,  8, 'Televisor 4K Neo QLED con Mini LED y refresco 120Hz.',                      24, 3),
    ('PRD250101120000PRD17', 'CAT250101120000CAT03', 'Smart TV Sony BRAVIA XR OLED 65"',    'Sony',           'XR-65A80L',         6899.00,  0, 'Televisor OLED con procesador cognitivo XR y sonido Acoustic Surface.',     24, 2); -- AGOTADO (Alerta Alta)

    -- CAT04: Audio & Sonido (9 productos)
    INSERT INTO products (code, category_code, name, brand, model, sale_price, stock, description, warranty_months, low_stock) VALUES
    ('PRD250101120000PRD18', 'CAT250101120000CAT04', 'Smart TV Xiaomi TV A Pro 55"',         'Xiaomi',         'L55M8-A2ME',        1599.00, 15, 'Televisor LED 4K UHD con Google TV y diseno sin marcos.',                   12, 5),
    ('PRD250101120000PRD19', 'CAT250101120000CAT04', 'Smart TV TCL QLED 4K 65"',            'TCL',            '65C735',            2799.00, 12, 'Televisor QLED con Google TV, 144Hz y Dolby Vision IQ.',                    24, 4),
    ('PRD250101120000PRD20', 'CAT250101120000CAT04', 'Smart TV Hisense ULED 4K 55"',        'Hisense',        '55U7K',             2199.00, 10, 'Televisor ULED con panel de 144Hz y sistema de sonido Dolby Atmos.',        24, 4),
    ('PRD250101120000PRD21', 'CAT250101120000CAT04', 'Audifonos Sony WH-1000XM5',           'Sony',           'WH-1000XM5/B',      1299.00, 20, 'Audifonos inalambricos con cancelacion de ruido activa.',                   12, 5),
    ('PRD250101120000PRD22', 'CAT250101120000CAT04', 'Audifonos Bose QuietComfort Ultra',   'Bose',           'QC-ULTRA-HP',       1599.00, 15, 'Audifonos premium con cancelacion de ruido y audio espacial.',              12, 5),
    ('PRD250101120000PRD23', 'CAT250101120000CAT04', 'Parlante JBL PartyBox 310',           'JBL',            'JBLPARTYBOX310',    2199.00, 12, 'Parlante portatil 240W con luces ritmicas y ruedas.',                       12, 4),
    ('PRD250101120000PRD24', 'CAT250101120000CAT04', 'Parlante Inteligente Sonos Era 300',  'Sonos',          'E30G1US1BLK',       1899.00, 10, 'Parlante con soporte Audio Espacial y Dolby Atmos.',                        12, 4),
    ('PRD250101120000PRD25', 'CAT250101120000CAT04', 'Audifonos Apple AirPods Pro 2da Gen', 'Apple',          'MTJV3LL/A',          999.00, 25, 'Audifonos in-ear con cancelacion activa y audio espacial personalizado.',   12, 8),
    ('PRD250101120000PRD26', 'CAT250101120000CAT04', 'Parlante Bluetooth Marshall Emberton II', 'Marshall',   'EMBERTON-II-BLK',   799.00, 18, 'Parlante compacto resistente al agua con 30h de bateria.',                  24, 5);

    -- CAT05: Consolas & Videojuegos (5 productos)
    INSERT INTO products (code, category_code, name, brand, model, sale_price, stock, description, warranty_months, low_stock) VALUES
    ('PRD250101120000PRD27', 'CAT250101120000CAT05', 'Consola PlayStation 5 Slim 1TB',      'Sony',           'CFI-2000A01',       2499.00, 15, 'PS5 edicion fisica con diseno mas delgado y 1TB SSD.',                      12, 5),
    ('PRD250101120000PRD28', 'CAT250101120000CAT05', 'Consola Xbox Series X 1TB',           'Microsoft',      'RRT-00002',         2399.00,  0, 'Xbox mas rapida con lector de discos y 4K nativa.',                         12, 5), -- AGOTADO (Alerta Alta)
    ('PRD250101120000PRD29', 'CAT250101120000CAT05', 'Consola Nintendo Switch OLED',        'Nintendo',       'HEG-S-KABAA',       1599.00, 20, 'Consola hibrida con pantalla OLED de 7 pulgadas y 64GB.',                   12, 5),
    ('PRD250101120000PRD30', 'CAT250101120000CAT05', 'Steam Deck OLED 512GB',               'Valve',          'STEAM-DECK-OLED-512', 2999.00, 8, 'Consola portatil para juegos PC con pantalla HDR OLED.',                   12, 3),
    ('PRD250101120000PRD31', 'CAT250101120000CAT05', 'Control Inalambrico PS5 DualSense',   'Sony',           'CFI-ZCT1W',          399.00, 30, 'Control heptico con retroalimentacion adaptativa para PS5.',                12, 8);

    -- CAT06: Smartwatches & Wearables (6 productos)
    INSERT INTO products (code, category_code, name, brand, model, sale_price, stock, description, warranty_months, low_stock) VALUES
    ('PRD250101120000PRD32', 'CAT250101120000CAT06', 'Control Xbox Series Inalambrico',     'Microsoft',      'QAT-00009',          349.00, 25, 'Control ergonomico con textura antideslizante para Xbox y PC.',            12, 8),
    ('PRD250101120000PRD33', 'CAT250101120000CAT06', 'Apple Watch Series 9 GPS 45mm',       'Apple',          'MR9A3QL/A',         1899.00, 12, 'Reloj inteligente con sensor de temperatura y doble toque.',               12, 4),
    ('PRD250101120000PRD34', 'CAT250101120000CAT06', 'Samsung Galaxy Watch6 Classic 47mm',  'Samsung',        'SM-R960N',          1499.00, 15, 'Reloj con bisel giratorio fisico y monitoreo avanzado de salud.',           12, 5),
    ('PRD250101120000PRD35', 'CAT250101120000CAT06', 'Garmin Fenix 7 Pro Sapphire Solar',   'Garmin',         '010-02777-10',      3699.00,  8, 'Reloj deportivo premium con carga solar y mapas topograficos.',             24, 3),
    ('PRD250101120000PRD36', 'CAT250101120000CAT06', 'Fitbit Charge 6',                     'Fitbit',         'FB423BKBK',          699.00, 20, 'Monitor de actividad con GPS integrado y herramientas de salud.',          12, 6),
    ('PRD250101120000PRD37', 'CAT250101120000CAT06', 'Xiaomi Smart Band 8',                 'Xiaomi',         'M2239B1',            299.00, 30, 'Pulsera inteligente AMOLED 1.62" con 16 dias de bateria.',                 12, 8);

    -- CAT07: Camaras & Fotografia (4 productos)
    INSERT INTO products (code, category_code, name, brand, model, sale_price, stock, description, warranty_months, low_stock) VALUES
    ('PRD250101120000PRD38', 'CAT250101120000CAT07', 'Huawei Watch GT 4 46mm',              'Huawei',         'PNX-B19',            899.00, 10, 'Smartwatch con autonomia de 14 dias y GPS de doble banda.',                12, 4),
    ('PRD250101120000PRD39', 'CAT250101120000CAT07', 'Camara Mirrorless Sony Alpha 7 IV',   'Sony',           'ILCE-7M4',          9499.00,  1, 'Camara Full-Frame 33MP con video 4K 60p.',                                  24, 3), -- STOCK BAJO (Alerta Media)
    ('PRD250101120000PRD40', 'CAT250101120000CAT07', 'Camara Mirrorless Canon EOS R6 II',   'Canon',          '5662C002',          9999.00,  2, 'Camara hibrida Full-Frame con autoenfoque avanzado y 40fps.',               24, 3), -- STOCK BAJO (Alerta Media)
    ('PRD250101120000PRD41', 'CAT250101120000CAT07', 'Camara Mirrorless Fujifilm X-T5',     'Fujifilm',       'X-T5-18-55',        8299.00,  6, 'Camara retro APS-C 40MP con estabilizacion integrada.',                     12, 2);

    -- CAT08: Accesorios & Componentes (9 productos)
    INSERT INTO products (code, category_code, name, brand, model, sale_price, stock, description, warranty_months, low_stock) VALUES
    ('PRD250101120000PRD42', 'CAT250101120000CAT08', 'Camara Accion GoPro HERO12 Black',    'GoPro',          'CHDHX-121-RW',      1799.00, 12, 'Camara ultra resistente con estabilizacion HyperSmooth 6.0.',               12, 4),
    ('PRD250101120000PRD43', 'CAT250101120000CAT08', 'Drone DJI Mini 4 Pro',                'DJI',            'CP.MA.00000731.01', 4299.00,  6, 'Drone compacto 4K HDR con camara de apertura f/1.7 y 34min vuelo.',          12, 2),
    ('PRD250101120000PRD44', 'CAT250101120000CAT08', 'Camara Instantanea Fujifilm Instax Wide 300', 'Fujifilm', 'INSTAX-WIDE-300',  799.00, 10, 'Camara instantanea formato wide con flash automatico y autofocus.',         12, 4),
    ('PRD250101120000PRD45', 'CAT250101120000CAT08', 'Mouse Inalambrico Logitech MX Master 3S', 'Logitech', '910-006557',          499.00, 30, 'Mouse ergonomico premium para productividad con 8000 DPI.',                 12, 8),
    ('PRD250101120000PRD46', 'CAT250101120000CAT08', 'Teclado Mecanico Corsair K70 RGB PRO', 'Corsair',    'CH-9109410-SP',        799.00, 20, 'Teclado gaming Cherry MX Red con retroiluminacion RGB.',                    24, 6),
    ('PRD250101120000PRD47', 'CAT250101120000CAT08', 'Disco Duro Externo WD My Passport 2TB', 'Western Digital', 'WDBYVG0020BBK',  349.00, 35, 'Disco portatil USB 3.0 con proteccion por contrasena.',                     36, 10),
    ('PRD250101120000PRD48', 'CAT250101120000CAT08', 'Memoria RAM Kingston FURY Beast DDR5 16GB', 'Kingston', 'KF552C40BBK2-16',   299.00, 40, 'Kit RAM de alto rendimiento para plataformas Intel y AMD.',                 36, 10),
    ('PRD250101120000PRD49', 'CAT250101120000CAT08', 'SSD Samsung 870 EVO 1TB',             'Samsung',        'MZ-77E1T0B/AM',     599.00, 25, 'SSD SATA de alta velocidad con 560 MB/s lectura y 530 MB/s escritura.',     60, 8),
    ('PRD250101120000PRD50', 'CAT250101120000CAT08', 'Monitor LG UltraWide 34" WQHD',       'LG',             '34WP65G-B',        2499.00, 10, 'Monitor ultraancho IPS 21:9 con 75Hz y compatibilidad G-Sync.',             36, 3);

    -- =========================================================================
    -- COMPRAS A PROVEEDORES (40 ordenes - Distribución en el tiempo y últimos 7 días)
    -- =========================================================================
    PRINT 'Insertando compras...';

    -- Compras Históricas 2025-2026 (PUR01 a PUR33)
    INSERT INTO purchases (code, user_code, supplier_code, purchase_date) VALUES ('PUR250108100000PUR01', 'USR250101120000USR01', 'SUP250101120000SUP01', '2025-01-08 10:00:00.123');
    INSERT INTO purchase_details (code, purchase_code, product_code, purchase_price, quantity) VALUES
    ('PDT250108100000PDT0A', 'PUR250108100000PUR01', 'PRD250101120000PRD01', 4619.00, 10),
    ('PDT250108100000PDT0B', 'PUR250108100000PUR01', 'PRD250101120000PRD04', 5949.00, 10);

    INSERT INTO purchases (code, user_code, supplier_code, purchase_date) VALUES ('PUR250115093000PUR02', 'USR250101120000USR01', 'SUP250101120000SUP04', '2025-01-15 09:30:00.123');
    INSERT INTO purchase_details (code, purchase_code, product_code, purchase_price, quantity) VALUES
    ('PDT250115093000PDT0A', 'PUR250115093000PUR02', 'PRD250101120000PRD02', 3709.00, 10),
    ('PDT250115093000PDT0B', 'PUR250115093000PUR02', 'PRD250101120000PRD08', 3849.00, 15);

    INSERT INTO purchases (code, user_code, supplier_code, purchase_date) VALUES ('PUR250122110000PUR03', 'USR250101120000USR01', 'SUP250101120000SUP05', '2025-01-22 11:00:00.123');
    INSERT INTO purchase_details (code, purchase_code, product_code, purchase_price, quantity) VALUES
    ('PDT250122110000PDT0A', 'PUR250122110000PUR03', 'PRD250101120000PRD09', 2799.00, 10),
    ('PDT250122110000PDT0B', 'PUR250122110000PUR03', 'PRD250101120000PRD16', 3499.00, 10);

    INSERT INTO purchases (code, user_code, supplier_code, purchase_date) VALUES ('PUR250205100000PUR04', 'USR250101120000USR01', 'SUP250101120000SUP02', '2025-02-05 10:00:00.123');
    INSERT INTO purchase_details (code, purchase_code, product_code, purchase_price, quantity) VALUES
    ('PDT250205100000PDT0A', 'PUR250205100000PUR04', 'PRD250101120000PRD21', 909.00,  15),
    ('PDT250205100000PDT0B', 'PUR250205100000PUR04', 'PRD250101120000PRD27', 1749.00, 10);

    INSERT INTO purchases (code, user_code, supplier_code, purchase_date) VALUES ('PUR250212090000PUR05', 'USR250101120000USR01', 'SUP250101120000SUP06', '2025-02-12 09:00:00.123');
    INSERT INTO purchase_details (code, purchase_code, product_code, purchase_price, quantity) VALUES
    ('PDT250212090000PDT0A', 'PUR250212090000PUR05', 'PRD250101120000PRD03', 5529.00, 10);

    INSERT INTO purchases (code, user_code, supplier_code, purchase_date) VALUES ('PUR250220103000PUR06', 'USR250101120000USR01', 'SUP250101120000SUP03', '2025-02-20 10:30:00.123');
    INSERT INTO purchase_details (code, purchase_code, product_code, purchase_price, quantity) VALUES
    ('PDT250220103000PDT0A', 'PUR250220103000PUR06', 'PRD250101120000PRD45', 299.00,  20);

    INSERT INTO purchases (code, user_code, supplier_code, purchase_date) VALUES ('PUR250301110000PUR07', 'USR250101120000USR01', 'SUP250101120000SUP08', '2025-03-01 11:00:00.123');
    INSERT INTO purchase_details (code, purchase_code, product_code, purchase_price, quantity) VALUES
    ('PDT250301110000PDT0A', 'PUR250301110000PUR07', 'PRD250101120000PRD10', 3499.00, 8);

    INSERT INTO purchases (code, user_code, supplier_code, purchase_date) VALUES ('PUR250310093000PUR08', 'USR250101120000USR01', 'SUP250101120000SUP04', '2025-03-10 09:30:00.123');
    INSERT INTO purchase_details (code, purchase_code, product_code, purchase_price, quantity) VALUES
    ('PDT250310093000PDT0A', 'PUR250310093000PUR08', 'PRD250101120000PRD08', 3849.00, 10);

    INSERT INTO purchases (code, user_code, supplier_code, purchase_date) VALUES ('PUR250318100000PUR09', 'USR250101120000USR01', 'SUP250101120000SUP07', '2025-03-18 10:00:00.123');
    INSERT INTO purchase_details (code, purchase_code, product_code, purchase_price, quantity) VALUES
    ('PDT250318100000PDT0A', 'PUR250318100000PUR09', 'PRD250101120000PRD15', 2729.00, 10);

    INSERT INTO purchases (code, user_code, supplier_code, purchase_date) VALUES ('PUR250325113000PUR10', 'USR250101120000USR01', 'SUP250101120000SUP01', '2025-03-25 11:30:00.123');
    INSERT INTO purchase_details (code, purchase_code, product_code, purchase_price, quantity) VALUES
    ('PDT250325113000PDT0A', 'PUR250325113000PUR10', 'PRD250101120000PRD48', 209.00,  30);

    INSERT INTO purchases (code, user_code, supplier_code, purchase_date) VALUES ('PUR250403100000PUR11', 'USR250101120000USR01', 'SUP250101120000SUP05', '2025-04-03 10:00:00.123');
    INSERT INTO purchase_details (code, purchase_code, product_code, purchase_price, quantity) VALUES
    ('PDT250403100000PDT0A', 'PUR250403100000PUR11', 'PRD250101120000PRD09', 2799.00, 10);

    INSERT INTO purchases (code, user_code, supplier_code, purchase_date) VALUES ('PUR250410090000PUR12', 'USR250101120000USR01', 'SUP250101120000SUP02', '2025-04-10 09:00:00.123');
    INSERT INTO purchase_details (code, purchase_code, product_code, purchase_price, quantity) VALUES
    ('PDT250410090000PDT0A', 'PUR250410090000PUR12', 'PRD250101120000PRD21', 909.00,  15);

    INSERT INTO purchases (code, user_code, supplier_code, purchase_date) VALUES ('PUR250422110000PUR13', 'USR250101120000USR01', 'SUP250101120000SUP04', '2025-04-22 11:00:00.123');
    INSERT INTO purchase_details (code, purchase_code, product_code, purchase_price, quantity) VALUES
    ('PDT250422110000PDT0A', 'PUR250422110000PUR13', 'PRD250101120000PRD08', 3849.00, 10);

    INSERT INTO purchases (code, user_code, supplier_code, purchase_date) VALUES ('PUR250505103000PUR14', 'USR250101120000USR01', 'SUP250101120000SUP06', '2025-05-05 10:30:00.123');
    INSERT INTO purchase_details (code, purchase_code, product_code, purchase_price, quantity) VALUES
    ('PDT250505103000PDT0A', 'PUR250505103000PUR14', 'PRD250101120000PRD03', 5529.00, 8);

    INSERT INTO purchases (code, user_code, supplier_code, purchase_date) VALUES ('PUR250514090000PUR15', 'USR250101120000USR01', 'SUP250101120000SUP08', '2025-05-14 09:00:00.123');
    INSERT INTO purchase_details (code, purchase_code, product_code, purchase_price, quantity) VALUES
    ('PDT250514090000PDT0A', 'PUR250514090000PUR15', 'PRD250101120000PRD10', 3499.00, 8);

    INSERT INTO purchases (code, user_code, supplier_code, purchase_date) VALUES ('PUR250522100000PUR16', 'USR250101120000USR01', 'SUP250101120000SUP03', '2025-05-22 10:00:00.123');
    INSERT INTO purchase_details (code, purchase_code, product_code, purchase_price, quantity) VALUES
    ('PDT250522100000PDT0A', 'PUR250522100000PUR16', 'PRD250101120000PRD45', 299.00,  20);

    INSERT INTO purchases (code, user_code, supplier_code, purchase_date) VALUES ('PUR250602110000PUR17', 'USR250101120000USR01', 'SUP250101120000SUP01', '2025-06-02 11:00:00.123');
    INSERT INTO purchase_details (code, purchase_code, product_code, purchase_price, quantity) VALUES
    ('PDT250602110000PDT0A', 'PUR250602110000PUR17', 'PRD250101120000PRD01', 4619.00, 10);

    INSERT INTO purchases (code, user_code, supplier_code, purchase_date) VALUES ('PUR250612093000PUR18', 'USR250101120000USR01', 'SUP250101120000SUP05', '2025-06-12 09:30:00.123');
    INSERT INTO purchase_details (code, purchase_code, product_code, purchase_price, quantity) VALUES
    ('PDT250612093000PDT0A', 'PUR250612093000PUR18', 'PRD250101120000PRD09', 2799.00, 10);

    INSERT INTO purchases (code, user_code, supplier_code, purchase_date) VALUES ('PUR250620100000PUR19', 'USR250101120000USR01', 'SUP250101120000SUP07', '2025-06-20 10:00:00.123');
    INSERT INTO purchase_details (code, purchase_code, product_code, purchase_price, quantity) VALUES
    ('PDT250620100000PDT0A', 'PUR250620100000PUR19', 'PRD250101120000PRD15', 2729.00, 10);

    INSERT INTO purchases (code, user_code, supplier_code, purchase_date) VALUES ('PUR250628113000PUR20', 'USR250101120000USR01', 'SUP250101120000SUP08', '2025-06-28 11:30:00.123');
    INSERT INTO purchase_details (code, purchase_code, product_code, purchase_price, quantity) VALUES
    ('PDT250628113000PDT0A', 'PUR250628113000PUR20', 'PRD250101120000PRD47', 244.00,  30);

    INSERT INTO purchases (code, user_code, supplier_code, purchase_date) VALUES ('PUR250707090000PUR21', 'USR250101120000USR01', 'SUP250101120000SUP04', '2025-07-07 09:00:00.123');
    INSERT INTO purchase_details (code, purchase_code, product_code, purchase_price, quantity) VALUES
    ('PDT250707090000PDT0A', 'PUR250707090000PUR21', 'PRD250101120000PRD02', 3709.00, 10);

    INSERT INTO purchases (code, user_code, supplier_code, purchase_date) VALUES ('PUR250715103000PUR22', 'USR250101120000USR01', 'SUP250101120000SUP01', '2025-07-15 10:30:00.123');
    INSERT INTO purchase_details (code, purchase_code, product_code, purchase_price, quantity) VALUES
    ('PDT250715103000PDT0A', 'PUR250715103000PUR22', 'PRD250101120000PRD06', 5249.00, 5);

    INSERT INTO purchases (code, user_code, supplier_code, purchase_date) VALUES ('PUR250724110000PUR23', 'USR250101120000USR01', 'SUP250101120000SUP05', '2025-07-24 11:00:00.123');
    INSERT INTO purchase_details (code, purchase_code, product_code, purchase_price, quantity) VALUES
    ('PDT250724110000PDT0A', 'PUR250724110000PUR23', 'PRD250101120000PRD09', 2799.00, 10);

    INSERT INTO purchases (code, user_code, supplier_code, purchase_date) VALUES ('PUR250805093000PUR24', 'USR250101120000USR01', 'SUP250101120000SUP02', '2025-08-05 09:30:00.123');
    INSERT INTO purchase_details (code, purchase_code, product_code, purchase_price, quantity) VALUES
    ('PDT250805093000PDT0A', 'PUR250805093000PUR24', 'PRD250101120000PRD17', 4829.00,  5);

    INSERT INTO purchases (code, user_code, supplier_code, purchase_date) VALUES ('PUR250814100000PUR25', 'USR250101120000USR01', 'SUP250101120000SUP06', '2025-08-14 10:00:00.123');
    INSERT INTO purchase_details (code, purchase_code, product_code, purchase_price, quantity) VALUES
    ('PDT250814100000PDT0A', 'PUR250814100000PUR25', 'PRD250101120000PRD03', 5529.00, 10);

    INSERT INTO purchases (code, user_code, supplier_code, purchase_date) VALUES ('PUR250822113000PUR26', 'USR250101120000USR01', 'SUP250101120000SUP03', '2025-08-22 11:30:00.123');
    INSERT INTO purchase_details (code, purchase_code, product_code, purchase_price, quantity) VALUES
    ('PDT250822113000PDT0A', 'PUR250822113000PUR26', 'PRD250101120000PRD45', 299.00,  20);

    INSERT INTO purchases (code, user_code, supplier_code, purchase_date) VALUES ('PUR250903090000PUR27', 'USR250101120000USR01', 'SUP250101120000SUP08', '2025-09-03 09:00:00.123');
    INSERT INTO purchase_details (code, purchase_code, product_code, purchase_price, quantity) VALUES
    ('PDT250903090000PDT0A', 'PUR250903090000PUR27', 'PRD250101120000PRD10', 3499.00, 8);

    INSERT INTO purchases (code, user_code, supplier_code, purchase_date) VALUES ('PUR250912103000PUR28', 'USR250101120000USR01', 'SUP250101120000SUP07', '2025-09-12 10:30:00.123');
    INSERT INTO purchase_details (code, purchase_code, product_code, purchase_price, quantity) VALUES
    ('PDT250912103000PDT0A', 'PUR250912103000PUR28', 'PRD250101120000PRD15', 2729.00, 10);

    INSERT INTO purchases (code, user_code, supplier_code, purchase_date) VALUES ('PUR250922110000PUR29', 'USR250101120000USR01', 'SUP250101120000SUP01', '2025-09-22 11:00:00.123');
    INSERT INTO purchase_details (code, purchase_code, product_code, purchase_price, quantity) VALUES
    ('PDT250922110000PDT0A', 'PUR250922110000PUR29', 'PRD250101120000PRD01', 4619.00, 15);

    INSERT INTO purchases (code, user_code, supplier_code, purchase_date) VALUES ('PUR250930093000PUR30', 'USR250101120000USR01', 'SUP250101120000SUP04', '2025-09-30 09:30:00.123');
    INSERT INTO purchase_details (code, purchase_code, product_code, purchase_price, quantity) VALUES
    ('PDT250930093000PDT0A', 'PUR250930093000PUR30', 'PRD250101120000PRD08', 3849.00, 15);

    INSERT INTO purchases (code, user_code, supplier_code, purchase_date) VALUES ('PUR251008100000PUR31', 'USR250101120000USR01', 'SUP250101120000SUP05', '2025-10-08 10:00:00.123');
    INSERT INTO purchase_details (code, purchase_code, product_code, purchase_price, quantity) VALUES
    ('PDT251008100000PDT0A', 'PUR251008100000PUR31', 'PRD250101120000PRD09', 2799.00, 15);

    INSERT INTO purchases (code, user_code, supplier_code, purchase_date) VALUES ('PUR251016093000PUR32', 'USR250101120000USR01', 'SUP250101120000SUP02', '2025-10-16 09:30:00.123');
    INSERT INTO purchase_details (code, purchase_code, product_code, purchase_price, quantity) VALUES
    ('PDT251016093000PDT0A', 'PUR251016093000PUR32', 'PRD250101120000PRD21', 909.00,  15);

    INSERT INTO purchases (code, user_code, supplier_code, purchase_date) VALUES ('PUR251103110000PUR33', 'USR250101120000USR01', 'SUP250101120000SUP01', '2025-11-03 11:00:00.123');
    INSERT INTO purchase_details (code, purchase_code, product_code, purchase_price, quantity) VALUES
    ('PDT251103110000PDT0A', 'PUR251103110000PUR33', 'PRD250101120000PRD01', 4619.00, 15);

    -- Compras de los últimos 7 días: constantes y realistas (S/ 2,000 - S/ 8,000)
    -- === 12-Julio (Domingo) ===
    -- No hay compras.

    -- === 13-Julio (Lunes) ===
    INSERT INTO purchases (code, user_code, supplier_code, purchase_date) VALUES ('PUR260713100000PUR34', 'USR250101120000USR01', 'SUP250101120000SUP03', '2026-07-13 10:00:00.123');
    INSERT INTO purchase_details (code, purchase_code, product_code, purchase_price, quantity) VALUES
    ('PDT260713100000PDT0A', 'PUR260713100000PUR34', 'PRD250101120000PRD45', 299.00, 15); -- Total S/ 4,485

    -- === 14-Julio (Martes) ===
    INSERT INTO purchases (code, user_code, supplier_code, purchase_date) VALUES ('PUR260714110000PUR35', 'USR250101120000USR01', 'SUP250101120000SUP03', '2026-07-14 11:00:00.123');
    INSERT INTO purchase_details (code, purchase_code, product_code, purchase_price, quantity) VALUES
    ('PDT260714110000PDT0A', 'PUR260714110000PUR35', 'PRD250101120000PRD46', 559.00,  6); -- Total S/ 3,354

    -- === 15-Julio (Miércoles) ===
    INSERT INTO purchases (code, user_code, supplier_code, purchase_date) VALUES ('PUR260715093000PUR36', 'USR250101120000USR01', 'SUP250101120000SUP05', '2026-07-15 09:30:00.123');
    INSERT INTO purchase_details (code, purchase_code, product_code, purchase_price, quantity) VALUES
    ('PDT260715093000PDT0A', 'PUR260715093000PUR36', 'PRD250101120000PRD12', 1259.00, 5); -- Total S/ 6,295

    -- === 16-Julio (Jueves) ===
    INSERT INTO purchases (code, user_code, supplier_code, purchase_date) VALUES ('PUR260716103000PUR37', 'USR250101120000USR01', 'SUP250101120000SUP04', '2026-07-16 10:30:00.123');
    INSERT INTO purchase_details (code, purchase_code, product_code, purchase_price, quantity) VALUES
    ('PDT260716103000PDT0A', 'PUR260716103000PUR37', 'PRD250101120000PRD25', 699.00,  8); -- Total S/ 5,592

    -- === 17-Julio (Viernes) ===
    INSERT INTO purchases (code, user_code, supplier_code, purchase_date) VALUES ('PUR260717140000PUR38', 'USR250101120000USR01', 'SUP250101120000SUP01', '2026-07-17 14:00:00.123');
    INSERT INTO purchase_details (code, purchase_code, product_code, purchase_price, quantity) VALUES
    ('PDT260717140000PDT0A', 'PUR260717140000PUR38', 'PRD250101120000PRD01', 4619.00,  2); -- Total S/ 9,238

    -- === 18-Julio (Sábado - Hoy) ===
    INSERT INTO purchases (code, user_code, supplier_code, purchase_date) VALUES ('PUR260718100000PUR39', 'USR250101120000USR01', 'SUP250101120000SUP04', '2026-07-18 10:00:00.123');
    INSERT INTO purchase_details (code, purchase_code, product_code, purchase_price, quantity) VALUES
    ('PDT260718100000PDT0A', 'PUR260718100000PUR39', 'PRD250101120000PRD02', 3709.00,  2), -- Total S/ 7,418
    ('PDT260718100000PDT0B', 'PUR260718100000PUR39', 'PRD250101120000PRD45', 299.00,  15); -- Total S/ 4,485 (Total hoy: S/ 11,903.00)

    -- Adicional compra para stock general
    INSERT INTO purchases (code, user_code, supplier_code, purchase_date) VALUES ('PUR260718140000PUR40', 'USR250101120000USR01', 'SUP250101120000SUP08', '2026-07-18 14:00:00.123');
    INSERT INTO purchase_details (code, purchase_code, product_code, purchase_price, quantity) VALUES
    ('PDT260718140000PDT0A', 'PUR260718140000PUR40', 'PRD250101120000PRD47', 244.00,  15); -- Total S/ 3,660

    -- =========================================================================
    -- VENTAS (75 ventas - Distribución escalonada para gráficos)
    -- =========================================================================
    PRINT 'Insertando ventas...';

    -- Ventas Históricas 2025 (SAL01 a SAL50)
    INSERT INTO sales (code, user_code, customer_code, sale_date) VALUES ('SAL250110101500SAL01', 'USR250101120000USR02', 'CUS250101120000CUS01', '2025-01-10 10:15:00.123');
    INSERT INTO sale_details (code, sale_code, product_code, sale_price, quantity) VALUES
    ('SDT250110101500SDT0A', 'SAL250110101500SAL01', 'PRD250101120000PRD01', 6599.00, 1);

    INSERT INTO sales (code, user_code, customer_code, sale_date) VALUES ('SAL250125140000SAL02', 'USR250101120000USR05', 'CUS250101120000CUS00', '2025-01-25 14:00:00.123');
    INSERT INTO sale_details (code, sale_code, product_code, sale_price, quantity) VALUES
    ('SDT250125140000SDT0A', 'SAL250125140000SAL02', 'PRD250101120000PRD45', 499.00, 2);

    INSERT INTO sales (code, user_code, customer_code, sale_date) VALUES ('SAL250203103000SAL03', 'USR250101120000USR02', 'CUS250101120000CUS03', '2025-02-03 10:30:00.123');
    INSERT INTO sale_details (code, sale_code, product_code, sale_price, quantity) VALUES
    ('SDT250203103000SDT0A', 'SAL250203103000SAL03', 'PRD250101120000PRD02', 5299.00, 1);

    INSERT INTO sales (code, user_code, customer_code, sale_date) VALUES ('SAL250214111500SAL04', 'USR250101120000USR05', 'CUS250101120000CUS00', '2025-02-14 11:15:00.123');
    INSERT INTO sale_details (code, sale_code, product_code, sale_price, quantity) VALUES
    ('SDT250214111500SDT0A', 'SAL250214111500SAL04', 'PRD250101120000PRD12', 1799.00, 1);

    INSERT INTO sales (code, user_code, customer_code, sale_date) VALUES ('SAL250304100000SAL05', 'USR250101120000USR02', 'CUS250101120000CUS01', '2025-03-04 10:00:00.123');
    INSERT INTO sale_details (code, sale_code, product_code, sale_price, quantity) VALUES
    ('SDT250304100000SDT0A', 'SAL250304100000SAL05', 'PRD250101120000PRD04', 8499.00, 1);

    INSERT INTO sales (code, user_code, customer_code, sale_date) VALUES ('SAL250317091500SAL06', 'USR250101120000USR04', 'CUS250101120000CUS00', '2025-03-17 09:15:00.123');
    INSERT INTO sale_details (code, sale_code, product_code, sale_price, quantity) VALUES
    ('SDT250317091500SDT0A', 'SAL250317091500SAL06', 'PRD250101120000PRD08', 5499.00, 1);

    INSERT INTO sales (code, user_code, customer_code, sale_date) VALUES ('SAL250405104500SAL07', 'USR250101120000USR04', 'CUS250101120000CUS02', '2025-04-05 10:45:00.123');
    INSERT INTO sale_details (code, sale_code, product_code, sale_price, quantity) VALUES
    ('SDT250405104500SDT0A', 'SAL250405104500SAL07', 'PRD250101120000PRD03', 7899.00, 1);

    INSERT INTO sales (code, user_code, customer_code, sale_date) VALUES ('SAL250418120000SAL08', 'USR250101120000USR02', 'CUS250101120000CUS00', '2025-04-18 12:00:00.123');
    INSERT INTO sale_details (code, sale_code, product_code, sale_price, quantity) VALUES
    ('SDT250418120000SDT0A', 'SAL250418120000SAL08', 'PRD250101120000PRD12', 1799.00, 1);

    INSERT INTO sales (code, user_code, customer_code, sale_date) VALUES ('SAL250506100000SAL09', 'USR250101120000USR05', 'CUS250101120000CUS11', '2025-05-06 10:00:00.123');
    INSERT INTO sale_details (code, sale_code, product_code, sale_price, quantity) VALUES
    ('SDT250506100000SDT0A', 'SAL250506100000SAL09', 'PRD250101120000PRD27', 2499.00, 1);

    INSERT INTO sales (code, user_code, customer_code, sale_date) VALUES ('SAL250520090000SAL10', 'USR250101120000USR04', 'CUS250101120000CUS00', '2025-05-20 09:00:00.123');
    INSERT INTO sale_details (code, sale_code, product_code, sale_price, quantity) VALUES
    ('SDT250520090000SDT0A', 'SAL250520090000SAL10', 'PRD250101120000PRD45', 499.00, 2);

    -- Ventas constantes en los últimos 30 días con los TOP PRODUCTOS más demandados
    -- (Queremos PRD45, PRD25, PRD37, PRD31, PRD48 como las barras del top ranking de ventas)
    
    -- === Período: 18-Junio a 11-Julio (15 ventas) ===
    INSERT INTO sales (code, user_code, customer_code, sale_date) VALUES ('SAL260620100000SAL11', 'USR250101120000USR02', 'CUS250101120000CUS01', '2026-06-20 10:00:00.123');
    INSERT INTO sale_details (code, sale_code, product_code, sale_price, quantity) VALUES
    ('SDT260620100000SDT0A', 'SAL260620100000SAL11', 'PRD250101120000PRD45', 499.00, 5), -- Top 1 (Logitech MX)
    ('SDT260620100000SDT0B', 'SAL260620100000SAL11', 'PRD250101120000PRD25', 999.00, 3); -- Top 2 (AirPods Pro)

    INSERT INTO sales (code, user_code, customer_code, sale_date) VALUES ('SAL260622110000SAL12', 'USR250101120000USR04', 'CUS250101120000CUS00', '2026-06-22 11:00:00.123');
    INSERT INTO sale_details (code, sale_code, product_code, sale_price, quantity) VALUES
    ('SDT260622110000SDT0A', 'SAL260622110000SAL12', 'PRD250101120000PRD37', 299.00, 6), -- Top 3 (Xiaomi Band)
    ('SDT260622110000SDT0B', 'SAL260622110000SAL12', 'PRD250101120000PRD31', 399.00, 4); -- Top 4 (Control PS5)

    INSERT INTO sales (code, user_code, customer_code, sale_date) VALUES ('SAL260625140000SAL13', 'USR250101120000USR05', 'CUS250101120000CUS02', '2026-06-25 14:00:00.123');
    INSERT INTO sale_details (code, sale_code, product_code, sale_price, quantity) VALUES
    ('SDT260625140000SDT0A', 'SAL260625140000SAL13', 'PRD250101120000PRD48', 299.00, 8); -- Top 5 (Kingston RAM)

    INSERT INTO sales (code, user_code, customer_code, sale_date) VALUES ('SAL260628090000SAL14', 'USR250101120000USR02', 'CUS250101120000CUS03', '2026-06-28 09:00:00.123');
    INSERT INTO sale_details (code, sale_code, product_code, sale_price, quantity) VALUES
    ('SDT260628090000SDT0A', 'SAL260628090000SAL14', 'PRD250101120000PRD45', 499.00, 4),
    ('SDT260628090000SDT0B', 'SAL260628090000SAL14', 'PRD250101120000PRD25', 999.00, 2);

    INSERT INTO sales (code, user_code, customer_code, sale_date) VALUES ('SAL260630113000SAL15', 'USR250101120000USR04', 'CUS250101120000CUS00', '2026-06-30 11:30:00.123');
    INSERT INTO sale_details (code, sale_code, product_code, sale_price, quantity) VALUES
    ('SDT260630113000SDT0A', 'SAL260630113000SAL15', 'PRD250101120000PRD37', 299.00, 4),
    ('SDT260630113000SDT0B', 'SAL260630113000SAL15', 'PRD250101120000PRD31', 399.00, 3);

    INSERT INTO sales (code, user_code, customer_code, sale_date) VALUES ('SAL260702140000SAL16', 'USR250101120000USR05', 'CUS250101120000CUS04', '2026-07-02 14:00:00.123');
    INSERT INTO sale_details (code, sale_code, product_code, sale_price, quantity) VALUES
    ('SDT260702140000SDT0A', 'SAL260702140000SAL16', 'PRD250101120000PRD45', 499.00, 3),
    ('SDT260702140000SDT0B', 'SAL260702140000SAL16', 'PRD250101120000PRD48', 299.00, 2);

    INSERT INTO sales (code, user_code, customer_code, sale_date) VALUES ('SAL260704103000SAL17', 'USR250101120000USR02', 'CUS250101120000CUS00', '2026-07-04 10:30:00.123');
    INSERT INTO sale_details (code, sale_code, product_code, sale_price, quantity) VALUES
    ('SDT260704103000SDT0A', 'SAL260704103000SAL17', 'PRD250101120000PRD25', 999.00, 4);

    INSERT INTO sales (code, user_code, customer_code, sale_date) VALUES ('SAL260706110000SAL18', 'USR250101120000USR04', 'CUS250101120000CUS05', '2026-07-06 11:00:00.123');
    INSERT INTO sale_details (code, sale_code, product_code, sale_price, quantity) VALUES
    ('SDT260706110000SDT0A', 'SAL260706110000SAL18', 'PRD250101120000PRD37', 299.00, 3),
    ('SDT260706110000SDT0B', 'SAL260706110000SAL18', 'PRD250101120000PRD31', 399.00, 2);

    INSERT INTO sales (code, user_code, customer_code, sale_date) VALUES ('SAL260708093000SAL19', 'USR250101120000USR05', 'CUS250101120000CUS00', '2026-07-08 09:30:00.123');
    INSERT INTO sale_details (code, sale_code, product_code, sale_price, quantity) VALUES
    ('SDT260708093000SDT0A', 'SAL260708093000SAL19', 'PRD250101120000PRD45', 499.00, 3);

    INSERT INTO sales (code, user_code, customer_code, sale_date) VALUES ('SAL260710153000SAL20', 'USR250101120000USR02', 'CUS250101120000CUS06', '2026-07-10 15:30:00.123');
    INSERT INTO sale_details (code, sale_code, product_code, sale_price, quantity) VALUES
    ('SDT260710153000SDT0A', 'SAL260710153000SAL20', 'PRD250101120000PRD25', 999.00, 3),
    ('SDT260710153000SDT0B', 'SAL260710153000SAL20', 'PRD250101120000PRD37', 299.00, 2);


    -- === VENTAS DIARIAS DE LOS ÚLTIMOS 7 DÍAS (12-Julio al 18-Julio) ===
    -- Constancia diaria para evitar gráficos planos y picos.

    -- --- 12-Julio (Domingo) ---
    INSERT INTO sales (code, user_code, customer_code, sale_date) VALUES ('SAL260712100000SAL21', 'USR250101120000USR02', 'CUS250101120000CUS01', '2026-07-12 10:00:00.123');
    INSERT INTO sale_details (code, sale_code, product_code, sale_price, quantity) VALUES
    ('SDT260712100000SDT0A', 'SAL260712100000SAL21', 'PRD250101120000PRD08', 5499.00, 1); -- Total: S/ 5,499.00

    INSERT INTO sales (code, user_code, customer_code, sale_date) VALUES ('SAL260712140000SAL22', 'USR250101120000USR04', 'CUS250101120000CUS00', '2026-07-12 14:00:00.123');
    INSERT INTO sale_details (code, sale_code, product_code, sale_price, quantity) VALUES
    ('SDT260712140000SDT0A', 'SAL260712140000SAL22', 'PRD250101120000PRD45', 499.00, 2); -- Total: S/ 998.00 (Total 12-Jul: S/ 6,497.00)

    -- --- 13-Julio (Lunes) ---
    INSERT INTO sales (code, user_code, customer_code, sale_date) VALUES ('SAL260713110000SAL23', 'USR250101120000USR05', 'CUS250101120000CUS02', '2026-07-13 11:00:00.123');
    INSERT INTO sale_details (code, sale_code, product_code, sale_price, quantity) VALUES
    ('SDT260713110000SDT0A', 'SAL260713110000SAL23', 'PRD250101120000PRD09', 4999.00, 1),
    ('SDT260713110000SDT0B', 'SAL260713110000SAL23', 'PRD250101120000PRD25', 999.00, 2); -- Total: S/ 6,997.00

    INSERT INTO sales (code, user_code, customer_code, sale_date) VALUES ('SAL260713153000SAL24', 'USR250101120000USR02', 'CUS250101120000CUS00', '2026-07-13 15:30:00.123');
    INSERT INTO sale_details (code, sale_code, product_code, sale_price, quantity) VALUES
    ('SDT260713153000SDT0A', 'SAL260713153000SAL24', 'PRD250101120000PRD45', 499.00, 2); -- Total: S/ 998.00 (Total 13-Jul: S/ 7,995.00)

    -- --- 14-Julio (Martes) ---
    INSERT INTO sales (code, user_code, customer_code, sale_date) VALUES ('SAL260714101500SAL25', 'USR250101120000USR04', 'CUS250101120000CUS03', '2026-07-14 10:15:00.123');
    INSERT INTO sale_details (code, sale_code, product_code, sale_price, quantity) VALUES
    ('SDT260714101500SDT0A', 'SAL260714101500SAL25', 'PRD250101120000PRD02', 5299.00, 1),
    ('SDT260714101500SDT0B', 'SAL260714101500SAL25', 'PRD250101120000PRD37', 299.00, 2); -- Total: S/ 5,897.00

    INSERT INTO sales (code, user_code, customer_code, sale_date) VALUES ('SAL260714144500SAL26', 'USR250101120000USR05', 'CUS250101120000CUS00', '2026-07-14 14:45:00.123');
    INSERT INTO sale_details (code, sale_code, product_code, sale_price, quantity) VALUES
    ('SDT260714144500SDT0A', 'SAL260714144500SAL26', 'PRD250101120000PRD31', 399.00, 2); -- Total: S/ 798.00 (Total 14-Jul: S/ 6,695.00)

    -- --- 15-Julio (Miércoles) ---
    INSERT INTO sales (code, user_code, customer_code, sale_date) VALUES ('SAL260715093000SAL27', 'USR250101120000USR02', 'CUS250101120000CUS04', '2026-07-15 09:30:00.123');
    INSERT INTO sale_details (code, sale_code, product_code, sale_price, quantity) VALUES
    ('SDT260715093000SDT0A', 'SAL260715093000SAL27', 'PRD250101120000PRD15', 3899.00, 1),
    ('SDT260715093000SDT0B', 'SAL260715093000SAL27', 'PRD250101120000PRD25', 999.00, 2); -- Total: S/ 5,897.00

    INSERT INTO sales (code, user_code, customer_code, sale_date) VALUES ('SAL260715160000SAL28', 'USR250101120000USR04', 'CUS250101120000CUS00', '2026-07-15 16:00:00.123');
    INSERT INTO sale_details (code, sale_code, product_code, sale_price, quantity) VALUES
    ('SDT260715160000SDT0A', 'SAL260715160000SAL28', 'PRD250101120000PRD45', 499.00, 3); -- Total: S/ 1,497.00 (Total 15-Jul: S/ 7,394.00)

    -- --- 16-Julio (Jueves) ---
    INSERT INTO sales (code, user_code, customer_code, sale_date) VALUES ('SAL260716100000SAL29', 'USR250101120000USR05', 'CUS250101120000CUS07', '2026-07-16 10:00:00.123');
    INSERT INTO sale_details (code, sale_code, product_code, sale_price, quantity) VALUES
    ('SDT260716100000SDT0A', 'SAL260716100000SAL29', 'PRD250101120000PRD08', 5499.00, 1); -- Total: S/ 5,499.00

    INSERT INTO sales (code, user_code, customer_code, sale_date) VALUES ('SAL260716150000SAL30', 'USR250101120000USR02', 'CUS250101120000CUS00', '2026-07-16 15:00:00.123');
    INSERT INTO sale_details (code, sale_code, product_code, sale_price, quantity) VALUES
    ('SDT260716150000SDT0A', 'SAL260716150000SAL30', 'PRD250101120000PRD46', 799.00, 1),
    ('SDT260716150000SDT0B', 'SAL260716150000SAL30', 'PRD250101120000PRD37', 299.00, 3); -- Total: S/ 1,696.00 (Total 16-Jul: S/ 7,195.00)

    -- --- 17-Julio (Viernes) ---
    INSERT INTO sales (code, user_code, customer_code, sale_date) VALUES ('SAL260717110000SAL31', 'USR250101120000USR04', 'CUS250101120000CUS08', '2026-07-17 11:00:00.123');
    INSERT INTO sale_details (code, sale_code, product_code, sale_price, quantity) VALUES
    ('SDT260717110000SDT0A', 'SAL260717110000SAL31', 'PRD250101120000PRD09', 4999.00, 1),
    ('SDT260717110000SDT0B', 'SAL260717110000SAL31', 'PRD250101120000PRD31', 399.00, 2); -- Total: S/ 5,797.00

    INSERT INTO sales (code, user_code, customer_code, sale_date) VALUES ('SAL260717163000SAL32', 'USR250101120000USR05', 'CUS250101120000CUS00', '2026-07-17 16:30:00.123');
    INSERT INTO sale_details (code, sale_code, product_code, sale_price, quantity) VALUES
    ('SDT260717163000SDT0A', 'SAL260717163000SAL32', 'PRD250101120000PRD25', 999.00, 3); -- Total: S/ 2,997.00 (Total 17-Jul: S/ 8,794.00)

    -- --- 18-Julio (Sábado - HOY) ---
    -- Mantenemos un total aproximado a los S/ 14,395.00 que se vio en tu imagen de manera muy coherente
    INSERT INTO sales (code, user_code, customer_code, sale_date) VALUES ('SAL260718103000SAL33', 'USR250101120000USR02', 'CUS250101120000CUS01', '2026-07-18 10:30:00.123');
    INSERT INTO sale_details (code, sale_code, product_code, sale_price, quantity) VALUES
    ('SDT260718103000SDT0A', 'SAL260718103000SAL33', 'PRD250101120000PRD01', 6599.00, 1),
    ('SDT260718103000SDT0B', 'SAL260718103000SAL33', 'PRD250101120000PRD25', 999.00, 2); -- Total S/ 8,597.00

    INSERT INTO sales (code, user_code, customer_code, sale_date) VALUES ('SAL260718164500SAL34', 'USR250101120000USR04', 'CUS250101120000CUS00', '2026-07-18 16:45:00.123');
    INSERT INTO sale_details (code, sale_code, product_code, sale_price, quantity) VALUES
    ('SDT260718164500SDT0A', 'SAL260718164500SAL34', 'PRD250101120000PRD08', 5499.00, 1),
    ('SDT260718164500SDT0B', 'SAL260718164500SAL34', 'PRD250101120000PRD45', 299.00, 1); -- Total S/ 5,798.00 (Total hoy: S/ 14,395.00)

    -- =========================================================================
    -- GUIAS DE INVENTARIO (30 ajustes - Códigos con 20 caracteres)
    -- =========================================================================
    PRINT 'Insertando guias de inventario...';

    INSERT INTO inventory_guides (code, user_code, type, reason, description, guide_date) VALUES ('GUI250105080000GUI01', 'USR250101120000USR03', 'ENTRY', 'Recepcion de mercaderia', 'Ingreso de stock inicial de accesorios temporada 2025', '2025-01-05 08:00:00.123');
    INSERT INTO guide_details (code, guide_code, product_code, quantity) VALUES 
    ('GDT250105080000GDT0A', 'GUI250105080000GUI01', 'PRD250101120000PRD45', 10), 
    ('GDT250105080000GDT0B', 'GUI250105080000GUI01', 'PRD250101120000PRD46', 8), 
    ('GDT250105080000GDT0C', 'GUI250105080000GUI01', 'PRD250101120000PRD47', 12);

    INSERT INTO inventory_guides (code, user_code, type, reason, description, guide_date) VALUES ('GUI250120103000GUI02', 'USR250101120000USR03', 'EXIT', 'Producto danado', 'Retiro de unidad danada en transporte', '2025-01-20 10:30:00.123');
    INSERT INTO guide_details (code, guide_code, product_code, quantity) VALUES 
    ('GDT250120103000GDT0A', 'GUI250120103000GUI02', 'PRD250101120000PRD09', 1);

    INSERT INTO inventory_guides (code, user_code, type, reason, description, guide_date) VALUES ('GUI250210140000GUI03', 'USR250101120000USR03', 'ENTRY', 'Devolucion de cliente', 'Cliente devolvio producto en buen estado', '2025-02-10 14:00:00.123');
    INSERT INTO guide_details (code, guide_code, product_code, quantity) VALUES 
    ('GDT250210140000GDT0A', 'GUI250210140000GUI03', 'PRD250101120000PRD01', 1);

    INSERT INTO inventory_guides (code, user_code, type, reason, description, guide_date) VALUES ('GUI250225090000GUI04', 'USR250101120000USR03', 'EXIT', 'Merma por exhibicion', 'Producto de exhibicion dado de baja', '2025-02-25 09:00:00.123');
    INSERT INTO guide_details (code, guide_code, product_code, quantity) VALUES 
    ('GDT250225090000GDT0A', 'GUI250225090000GUI04', 'PRD250101120000PRD21', 1), 
    ('GDT250225090000GDT0B', 'GUI250225090000GUI04', 'PRD250101120000PRD37', 2);

    INSERT INTO inventory_guides (code, user_code, type, reason, description, guide_date) VALUES ('GUI250305110000GUI05', 'USR250101120000USR03', 'ENTRY', 'Ajuste de inventario', 'Correccion de discrepancia en conteo fisico', '2025-03-05 11:00:00.123');
    INSERT INTO guide_details (code, guide_code, product_code, quantity) VALUES 
    ('GDT250305110000GDT0A', 'GUI250305110000GUI05', 'PRD250101120000PRD08', 2), 
    ('GDT250305110000GDT0B', 'GUI250305110000GUI05', 'PRD250101120000PRD25', 3);

    INSERT INTO inventory_guides (code, user_code, type, reason, description, guide_date) VALUES ('GUI250320100000GUI06', 'USR250101120000USR03', 'EXIT', 'Producto vencido garantia interna', 'Retiro de unidad con garantia interna vencida', '2025-03-20 10:00:00.123');
    INSERT INTO guide_details (code, guide_code, product_code, quantity) VALUES 
    ('GDT250320100000GDT0A', 'GUI250320100000GUI06', 'PRD250101120000PRD15', 1);

    INSERT INTO inventory_guides (code, user_code, type, reason, description, guide_date) VALUES ('GUI250408083000GUI07', 'USR250101120000USR03', 'ENTRY', 'Transferencia entre almacenes', 'Traslado de bodega secundaria', '2025-04-08 08:30:00.123');
    INSERT INTO guide_details (code, guide_code, product_code, quantity) VALUES 
    ('GDT250408083000GDT0A', 'GUI250408083000GUI07', 'PRD250101120000PRD12', 5), 
    ('GDT250408083000GDT0B', 'GUI250408083000GUI07', 'PRD250101120000PRD34', 3);

    INSERT INTO inventory_guides (code, user_code, type, reason, description, guide_date) VALUES ('GUI250422113000GUI08', 'USR250101120000USR03', 'EXIT', 'Robo o extravio', 'Producto no encontrado en inventario fisico', '2025-04-22 11:30:00.123');
    INSERT INTO guide_details (code, guide_code, product_code, quantity) VALUES 
    ('GDT250422113000GDT0A', 'GUI250422113000GUI08', 'PRD250101120000PRD48', 2);

    INSERT INTO inventory_guides (code, user_code, type, reason, description, guide_date) VALUES ('GUI250507090000GUI09', 'USR250101120000USR03', 'ENTRY', 'Devolucion proveedor', 'Mercaderia rechazada devuelta y reingresada', '2025-05-07 09:00:00.123');
    INSERT INTO guide_details (code, guide_code, product_code, quantity) VALUES 
    ('GDT250507090000GDT0A', 'GUI250507090000GUI09', 'PRD250101120000PRD03', 2), 
    ('GDT250507090000GDT0B', 'GUI250507090000GUI09', 'PRD250101120000PRD05', 2);

    INSERT INTO inventory_guides (code, user_code, type, reason, description, guide_date) VALUES ('GUI250520100000GUI10', 'USR250101120000USR03', 'EXIT', 'Producto danado', 'Humedad en almacen afecto unidades', '2025-05-20 10:00:00.123');
    INSERT INTO guide_details (code, guide_code, product_code, quantity) VALUES 
    ('GDT250520100000GDT0A', 'GUI250520100000GUI10', 'PRD250101120000PRD47', 3), 
    ('GDT250520100000GDT0B', 'GUI250520100000GUI10', 'PRD250101120000PRD49', 2);

    INSERT INTO inventory_guides (code, user_code, type, reason, description, guide_date) VALUES ('GUI250602080000GUI11', 'USR250101120000USR03', 'ENTRY', 'Ajuste de inventario', 'Auditoria semestral detecta diferencia positiva', '2025-06-02 08:00:00.123');
    INSERT INTO guide_details (code, guide_code, product_code, quantity) VALUES 
    ('GDT250602080000GDT0A', 'GUI250602080000GUI11', 'PRD250101120000PRD29', 2), 
    ('GDT250602080000GDT0B', 'GUI250602080000GUI11', 'PRD250101120000PRD32', 3), 
    ('GDT250602080000GDT0C', 'GUI250602080000GUI11', 'PRD250101120000PRD37', 5);

    INSERT INTO inventory_guides (code, user_code, type, reason, description, guide_date) VALUES ('GUI250615110000GUI12', 'USR250101120000USR03', 'EXIT', 'Producto para demo', 'Unidades destinadas a demostracion en feria', '2025-06-15 11:00:00.123');
    INSERT INTO guide_details (code, guide_code, product_code, quantity) VALUES 
    ('GDT250615110000GDT0A', 'GUI250615110000GUI12', 'PRD250101120000PRD01', 1), 
    ('GDT250615110000GDT0B', 'GUI250615110000GUI12', 'PRD250101120000PRD08', 1), 
    ('GDT250615110000GDT0C', 'GUI250615110000GUI12', 'PRD250101120000PRD27', 1);

    INSERT INTO inventory_guides (code, user_code, type, reason, description, guide_date) VALUES ('GUI250703093000GUI13', 'USR250101120000USR03', 'ENTRY', 'Recepcion de mercaderia', 'Ingreso de segunda tanda del trimestre', '2025-07-03 09:30:00.123');
    INSERT INTO guide_details (code, guide_code, product_code, quantity) VALUES 
    ('GDT250703093000GDT0A', 'GUI250703093000GUI13', 'PRD250101120000PRD33', 4), 
    ('GDT250703093000GDT0B', 'GUI250703093000GUI13', 'PRD250101120000PRD36', 5);

    INSERT INTO inventory_guides (code, user_code, type, reason, description, guide_date) VALUES ('GUI250718100000GUI14', 'USR250101120000USR03', 'EXIT', 'Merma por exhibicion', 'Televisores de exhibicion dados de baja', '2025-07-18 10:00:00.123');
    INSERT INTO guide_details (code, guide_code, product_code, quantity) VALUES 
    ('GDT250718100000GDT0A', 'GUI250718100000GUI14', 'PRD250101120000PRD15', 1), 
    ('GDT250718100000GDT0B', 'GUI250718100000GUI14', 'PRD250101120000PRD16', 1);

    INSERT INTO inventory_guides (code, user_code, type, reason, description, guide_date) VALUES ('GUI250804143000GUI15', 'USR250101120000USR03', 'ENTRY', 'Devolucion de cliente', 'Devolucion por cambio de modelo', '2025-08-04 14:30:00.123');
    INSERT INTO guide_details (code, guide_code, product_code, quantity) VALUES 
    ('GDT250804143000GDT0A', 'GUI250804143000GUI15', 'PRD250101120000PRD02', 1), 
    ('GDT250804143000GDT0B', 'GUI250804143000GUI15', 'PRD250101120000PRD25', 1);

    INSERT INTO inventory_guides (code, user_code, type, reason, description, guide_date) VALUES ('GUI250818090000GUI16', 'USR250101120000USR03', 'EXIT', 'Producto danado', 'Pantalla rota detectada en revision de almacen', '2025-08-18 09:00:00.123');
    INSERT INTO guide_details (code, guide_code, product_code, quantity) VALUES 
    ('GDT250818090000GDT0A', 'GUI250818090000GUI16', 'PRD250101120000PRD04', 1);

    INSERT INTO inventory_guides (code, user_code, type, reason, description, guide_date) VALUES ('GUI250902080000GUI17', 'USR250101120000USR03', 'ENTRY', 'Ajuste de inventario', 'Reconteo detecta unidades no registradas', '2025-09-02 08:00:00.123');
    INSERT INTO guide_details (code, guide_code, product_code, quantity) VALUES 
    ('GDT250902080000GDT0A', 'GUI250902080000GUI17', 'PRD250101120000PRD46', 3), 
    ('GDT250902080000GDT0B', 'GUI250902080000GUI17', 'PRD250101120000PRD50', 2);

    INSERT INTO inventory_guides (code, user_code, type, reason, description, guide_date) VALUES ('GUI250915103000GUI18', 'USR250101120000USR03', 'EXIT', 'Transferencia entre almacenes', 'Traslado a sucursal Miraflores', '2025-09-15 10:30:00.123');
    INSERT INTO guide_details (code, guide_code, product_code, quantity) VALUES 
    ('GDT250915103000GDT0A', 'GUI250915103000GUI18', 'PRD250101120000PRD12', 3), 
    ('GDT250915103000GDT0B', 'GUI250915103000GUI18', 'PRD250101120000PRD29', 2);

    INSERT INTO inventory_guides (code, user_code, type, reason, description, guide_date) VALUES ('GUI251003090000GUI19', 'USR250101120000USR03', 'ENTRY', 'Recepcion de mercaderia', 'Ingreso de productos temporada Q4', '2025-10-03 09:00:00.123');
    INSERT INTO guide_details (code, guide_code, product_code, quantity) VALUES 
    ('GDT251003090000GDT0A', 'GUI251003090000GUI19', 'PRD250101120000PRD27', 5), 
    ('GDT251003090000GDT0B', 'GUI251003090000GUI19', 'PRD250101120000PRD31', 8), 
    ('GDT251003090000GDT0C', 'GUI251003090000GUI19', 'PRD250101120000PRD32', 6);

    INSERT INTO inventory_guides (code, user_code, type, reason, description, guide_date) VALUES ('GUI251017110000GUI20', 'USR250101120000USR03', 'EXIT', 'Producto vencido garantia interna', 'Audifonos con defecto de fabrica retirados', '2025-10-17 11:00:00.123');
    INSERT INTO guide_details (code, guide_code, product_code, quantity) VALUES 
    ('GDT251017110000GDT0A', 'GUI251017110000GUI20', 'PRD250101120000PRD21', 2), 
    ('GDT251017110000GDT0B', 'GUI251017110000GUI20', 'PRD250101120000PRD22', 1);

    INSERT INTO inventory_guides (code, user_code, type, reason, description, guide_date) VALUES ('GUI251104083000GUI21', 'USR250101120000USR03', 'ENTRY', 'Devolucion proveedor', 'Reingreso de devolucion Sony aceptada', '2025-11-04 08:30:00.123');
    INSERT INTO guide_details (code, guide_code, product_code, quantity) VALUES 
    ('GDT251104083000GDT0A', 'GUI251104083000GUI21', 'PRD250101120000PRD17', 2), 
    ('GDT251104083000GDT0B', 'GUI251104083000GUI21', 'PRD250101120000PRD39', 1);

    INSERT INTO inventory_guides (code, user_code, type, reason, description, guide_date) VALUES ('GUI251120090000GUI22', 'USR250101120000USR03', 'ENTRY', 'Recepcion de mercaderia', 'Stock adicional para campaña Black Friday', '2025-11-20 09:00:00.123');
    INSERT INTO guide_details (code, guide_code, product_code, quantity) VALUES 
    ('GDT251120090000GDT0A', 'GUI251120090000GUI22', 'PRD250101120000PRD08', 5), 
    ('GDT251120090000GDT0B', 'GUI251120090000GUI22', 'PRD250101120000PRD09', 8), 
    ('GDT251120090000GDT0C', 'GUI251120090000GUI22', 'PRD250101120000PRD01', 6);

    INSERT INTO inventory_guides (code, user_code, type, reason, description, guide_date) VALUES ('GUI251128130000GUI23', 'USR250101120000USR03', 'EXIT', 'Producto danado', 'Consola danada durante traslado', '2025-11-28 13:00:00.123');
    INSERT INTO guide_details (code, guide_code, product_code, quantity) VALUES 
    ('GDT251128130000GDT0A', 'GUI251128130000GUI23', 'PRD250101120000PRD28', 1);

    INSERT INTO inventory_guides (code, user_code, type, reason, description, guide_date) VALUES ('GUI251205080000GUI24', 'USR250101120000USR03', 'ENTRY', 'Recepcion de mercaderia', 'Stock extra para campaña navidad', '2025-12-05 08:00:00.123');
    INSERT INTO guide_details (code, guide_code, product_code, quantity) VALUES 
    ('GDT251205080000GDT0A', 'GUI251205080000GUI24', 'PRD250101120000PRD29', 10), 
    ('GDT251205080000GDT0B', 'GUI251205080000GUI24', 'PRD250101120000PRD37', 15), 
    ('GDT251205080000GDT0C', 'GUI251205080000GUI24', 'PRD250101120000PRD25', 10);

    INSERT INTO inventory_guides (code, user_code, type, reason, description, guide_date) VALUES ('GUI251215100000GUI25', 'USR250101120000USR03', 'EXIT', 'Merma por exhibicion', 'Reloj con pantalla rayada retirado de exhibicion', '2025-12-15 10:00:00.123');
    INSERT INTO guide_details (code, guide_code, product_code, quantity) VALUES 
    ('GDT251215100000GDT0A', 'GUI251215100000GUI25', 'PRD250101120000PRD35', 1);

    INSERT INTO inventory_guides (code, user_code, type, reason, description, guide_date) VALUES ('GUI251228090000GUI26', 'USR250101120000USR03', 'ENTRY', 'Ajuste de inventario', 'Auditoria fin de año detecta diferencia positiva', '2025-12-28 09:00:00.123');
    INSERT INTO guide_details (code, guide_code, product_code, quantity) VALUES 
    ('GDT251228090000GDT0A', 'GUI251228090000GUI26', 'PRD250101120000PRD45', 5), 
    ('GDT251228090000GDT0B', 'GUI251228090000GUI26', 'PRD250101120000PRD47', 4), 
    ('GDT251228090000GDT0C', 'GUI251228090000GUI26', 'PRD250101120000PRD48', 6);

    INSERT INTO inventory_guides (code, user_code, type, reason, description, guide_date) VALUES ('GUI260105080000GUI27', 'USR250101120000USR03', 'ENTRY', 'Recepcion de mercaderia', 'Ingreso de stock nuevo año 2026', '2026-01-05 08:00:00.123');
    INSERT INTO guide_details (code, guide_code, product_code, quantity) VALUES 
    ('GDT260105080000GDT0A', 'GUI260105080000GUI27', 'PRD250101120000PRD01', 8), 
    ('GDT260105080000GDT0B', 'GUI260105080000GUI27', 'PRD250101120000PRD04', 6), 
    ('GDT260105080000GDT0C', 'GUI260105080000GUI27', 'PRD250101120000PRD08', 10);

    INSERT INTO inventory_guides (code, user_code, type, reason, description, guide_date) VALUES ('GUI260210103000GUI28', 'USR250101120000USR03', 'EXIT', 'Producto danado', 'Laptop con teclado defectuoso retirada', '2026-02-10 10:30:00.123');
    INSERT INTO guide_details (code, guide_code, product_code, quantity) VALUES 
    ('GDT260210103000GDT0A', 'GUI260210103000GUI28', 'PRD250101120000PRD03', 1);

    INSERT INTO inventory_guides (code, user_code, type, reason, description, guide_date) VALUES ('GUI260314140000GUI29', 'USR250101120000USR03', 'ENTRY', 'Devolucion de cliente', 'Devolucion aceptada dentro de plazo legal', '2026-03-14 14:00:00.123');
    INSERT INTO guide_details (code, guide_code, product_code, quantity) VALUES 
    ('GDT260314140000GDT0A', 'GUI260314140000GUI29', 'PRD250101120000PRD09', 1), 
    ('GDT260314140000GDT0B', 'GUI260314140000GUI29', 'PRD250101120000PRD34', 1);

    -- Guía de Ajuste de hoy para verificar movimiento reciente
    INSERT INTO inventory_guides (code, user_code, type, reason, description, guide_date) VALUES ('GUI260718090000GUI30', 'USR250101120000USR03', 'ENTRY', 'Ajuste de inventario', 'Conteo semestral detecta diferencias positivas', '2026-07-18 09:00:00.123');
    INSERT INTO guide_details (code, guide_code, product_code, quantity) VALUES 
    ('GDT260718090000GDT0A', 'GUI260718090000GUI30', 'PRD250101120000PRD12', 3), 
    ('GDT260718090000GDT0B', 'GUI260718090000GUI30', 'PRD250101120000PRD37', 4), 
    ('GDT260718090000GDT0C', 'GUI260718090000GUI30', 'PRD250101120000PRD46', 2);

    COMMIT TRANSACTION;
    PRINT '=========================================';
    PRINT 'Base de datos poblada exitosamente con graficos realistas!';
    PRINT '  - 8 categorias';
    PRINT '  - 50 productos (distribucion asimetrica natural)';
    PRINT '  - 5 empleados / 5 usuarios';
    PRINT '  - 16 clientes (incl. Consumidor Final)';
    PRINT '  - 8 proveedores';
    PRINT '  - 40 ordenes de compra (distribucion 7 dias)';
    PRINT '  - 75 ventas (distribucion 7 dias y ranking de barras)';
    PRINT '  - 30 guias de inventario';
    PRINT '=========================================';

END TRY
BEGIN CATCH
    ROLLBACK TRANSACTION;
    PRINT 'Error durante la insercion. Todos los cambios revertidos.';
    PRINT ERROR_MESSAGE();
END CATCH;
