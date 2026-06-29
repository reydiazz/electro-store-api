-- =========================================================================
-- 1. docker cp populate_db.sql electro_sqlserver:/tmp/populate_db.sql
-- 2. docker exec electro_sqlserver /opt/mssql-tools18/bin/sqlcmd -S localhost -U sa -P "DevOnly#SQL26!" -d electro_db -i /tmp/populate_db.sql -C
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

    PRINT 'Cleaning existing data...';
    
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

    PRINT 'Inserting people...';

    INSERT INTO people (code, first_name, last_name, phone, national_id) VALUES
    ('PEO260603120001PEO01', 'Carlos', 'Mendoza', '+51 987654321', '74224731'),
    ('PEO260603120002PEO02', 'Sofía', 'Castro', '+51 987654322', '74224732'),
    ('PEO260603120003PEO03', 'Diego', 'Torres', '+51 987654323', '74224733');

    INSERT INTO people (code, first_name, last_name, phone, national_id) VALUES
    ('PEO260603120004PEO04', 'Juan', 'Pérez', '+51 987654324', '74224734'),
    ('PEO260603120005PEO05', 'María', 'Rodríguez', '+51 987654325', '74224735'),
    ('PEO260603120006PEO06', 'Carlos', 'Gómez', '+51 987654326', '74224736'),
    ('PEO260603120007PEO07', 'Ana', 'Martínez', '+51 987654327', '74224737'),
    ('PEO260603120008PEO08', 'Luis', 'Flores', '+51 987654328', '74224738');

    PRINT 'Inserting employees...';
    
    INSERT INTO employees (code, person_code, position, salary) VALUES
    ('EMP260603120001EMP01', 'PEO260603120001PEO01', 'MANAGER', 5500.00),
    ('EMP260603120002EMP02', 'PEO260603120002PEO02', 'SELLER', 2800.00),
    ('EMP260603120003EMP03', 'PEO260603120003PEO03', 'STOREKEEPER', 3200.00);

    PRINT 'Inserting users...';
    
    INSERT INTO users (code, employee_code, username, password, role) VALUES
    ('USR260603120001USR01', 'EMP260603120001EMP01', 'admin', '$2a$10$yHYv0ARBbrp2fQxDP//Ckury1gFDq.UOFHDIaGj/kWCrZWL9I5/X6', 'ADMIN');

    PRINT 'Inserting customers...';
    
    INSERT INTO customers (code, person_code, tax_id) VALUES
    ('CUS260603120001CUS01', 'PEO260603120004PEO04', '10742247341'),
    ('CUS260603120002CUS02', 'PEO260603120005PEO05', '10742247352'),
    ('CUS260603120003CUS03', 'PEO260603120006PEO06', NULL),
    ('CUS260603120004CUS04', 'PEO260603120007PEO07', '10742247374'),
    ('CUS260603120005CUS05', 'PEO260603120008PEO08', NULL);

    PRINT 'Inserting suppliers...';
    
    INSERT INTO suppliers (code, tax_id, trade_name, phone, legal_name) VALUES
    ('SUP260603120001SUP01', '20600123451', 'Distribuidora Tech S.A.', '+51 911111111', 'DISTRIBUIDORA TECH SOCIEDAD ANONIMA'),
    ('SUP260603120002SUP02', '20600123452', 'Sony Perú', '+51 922222222', 'SONY PERU S.R.L.'),
    ('SUP260603120003SUP03', '20600123453', 'Logitech Wholesale', '+51 933333333', 'LOGITECH WHOLESALE PERU S.A.C.'),
    ('SUP260603120004SUP04', '20600123454', 'Apple Importaciones', '+51 944444444', 'APPLE IMPORTACIONES Y DISTRIBUCION S.A.C.'),
    ('SUP260603120005SUP05', '20600123455', 'Intel Latam', '+51 955555555', 'INTEL LATINOAMERICA S.A.');

    PRINT 'Inserting categories...';
    
    INSERT INTO product_categories (code, name) VALUES
    ('CAT260603120001CAT01', 'Laptops & Computadoras'),
    ('CAT260603120002CAT02', 'Celulares & Smartphones'),
    ('CAT260603120003CAT03', 'Televisores & Smart TVs'),
    ('CAT260603120004CAT04', 'Audio & Sonido'),
    ('CAT260603120005CAT05', 'Consolas & Videojuegos'),
    ('CAT260603120006CAT06', 'Smartwatches & Wearables'),
    ('CAT260603120007CAT07', 'Cámaras & Fotografía'),
    ('CAT260603120008CAT08', 'Accesorios & Componentes');

    PRINT 'Inserting products...';
    
    INSERT INTO products (code, category_code, name, brand, model, sale_price, stock, description, warranty_months) VALUES
    ('PRD260603120001PRD01', 'CAT260603120001CAT01', 'Laptop ASUS ROG Zephyrus G14', 'ASUS', 'GA402XV', 6599.00, 0, 'Laptop gaming de 14 pulgadas con procesador Ryzen 9 y RTX 4060.', 24),
    ('PRD260603120001PRD02', 'CAT260603120001CAT01', 'MacBook Air 13 M3', 'Apple', 'A3113', 5299.00, 0, 'Laptop ultradelgada con chip Apple M3, 8GB de RAM y 256GB SSD.', 12),
    ('PRD260603120001PRD03', 'CAT260603120001CAT01', 'Lenovo ThinkPad X1 Carbon Gen 11', 'Lenovo', '21HM002DUS', 7899.00, 0, 'Laptop corporativa ultraligera de fibra de carbono con procesador Intel Core i7.', 36),
    ('PRD260603120001PRD04', 'CAT260603120001CAT01', 'Dell XPS 15 9530', 'Dell', 'XPS15-7000', 8499.00, 0, 'Laptop premium para creadores con pantalla OLED de 15.6 pulgadas e Intel Core i9.', 12),

    ('PRD260603120002PRD05', 'CAT260603120002CAT02', 'iPhone 15 Pro Max 256GB', 'Apple', 'MU773QL/A', 5499.00, 0, 'Smartphone premium con cuerpo de titanio, chip A17 Pro y cámara teleobjetivo 5x.', 12),
    ('PRD260603120002PRD06', 'CAT260603120002CAT02', 'Samsung Galaxy S24 Ultra 512GB', 'Samsung', 'SM-S928B', 4999.00, 0, 'Smartphone con Galaxy AI, pantalla plana de 6.8 pulgadas y S Pen integrado.', 12),
    ('PRD260603120002PRD07', 'CAT260603120002CAT02', 'Xiaomi 14 Ultra 5G', 'Xiaomi', '24030PN60G', 4299.00, 0, 'Smartphone enfocado en fotografía con óptica Leica y sensor de 1 pulgada.', 12),
    ('PRD260603120002PRD08', 'CAT260603120002CAT02', 'Google Pixel 8 Pro 128GB', 'Google', 'GC3VE', 3699.00, 0, 'Smartphone con procesador Google Tensor G3 y la mejor experiencia de Android puro.', 12),

    ('PRD260603120003PRD09', 'CAT260603120003CAT03', 'Smart TV LG OLED C3 55"', 'LG', 'OLED55C3PSA', 3899.00, 0, 'Televisor inteligente OLED 4K con procesador a9 Gen6 y soporte Dolby Vision.', 24),
    ('PRD260603120003PRD10', 'CAT260603120003CAT03', 'Smart TV Samsung Neo QLED 65"', 'Samsung', 'QN65QN90CAG', 4999.00, 0, 'Televisor 4K Neo QLED con tecnología Mini LED y tasa de refresco de 120Hz.', 24),
    ('PRD260603120003PRD11', 'CAT260603120003CAT03', 'Smart TV Sony BRAVIA XR OLED 65"', 'Sony', 'XR-65A80L', 6899.00, 0, 'Televisor de gama alta OLED con procesador cognitivo XR y sonido Acoustic Surface.', 24),
    ('PRD260603120003PRD12', 'CAT260603120003CAT03', 'Smart TV Xiaomi TV A Pro 55"', 'Xiaomi', 'L55M8-A2ME', 1599.00, 0, 'Televisor LED 4K UHD con Google TV integrado y diseño sin marcos.', 12),

    ('PRD260603120004PRD13', 'CAT260603120004CAT04', 'Audífonos Sony WH-1000XM5', 'Sony', 'WH-1000XM5/B', 1299.00, 0, 'Audífonos inalámbricos con cancelación de ruido activa líder de la industria.', 12),
    ('PRD260603120004PRD14', 'CAT260603120004CAT04', 'Audífonos Bose QuietComfort Ultra', 'Bose', 'QC-ULTRA-HP', 1599.00, 0, 'Audífonos premium con cancelación de ruido y audio espacial inmersivo.', 12),
    ('PRD260603120004PRD15', 'CAT260603120004CAT04', 'Parlante JBL PartyBox 310', 'JBL', 'JBLPARTYBOX310', 2199.00, 0, 'Parlante portátil para fiestas de 240W con luces rítmicas integradas y ruedas.', 12),
    ('PRD260603120004PRD16', 'CAT260603120004CAT04', 'Parlante Inteligente Sonos Era 300', 'Sonos', 'E30G1US1BLK', 1899.00, 0, 'Parlante premium con soporte para Audio Espacial y Dolby Atmos.', 12),

    ('PRD260603120005PRD17', 'CAT260603120005CAT05', 'Consola PlayStation 5 Slim 1TB', 'Sony', 'CFI-2000A01', 2499.00, 0, 'Consola PS5 edición física con diseño más delgado y 1TB de almacenamiento SSD.', 12),
    ('PRD260603120005PRD18', 'CAT260603120005CAT05', 'Consola Xbox Series X 1TB', 'Microsoft', 'RRT-00002', 2399.00, 0, 'La consola Xbox más rápida y potente con lector de discos y resolución 4K nativa.', 12),
    ('PRD260603120005PRD19', 'CAT260603120005CAT05', 'Consola Nintendo Switch OLED Edition', 'Nintendo', 'HEG-S-KABAA', 1599.00, 0, 'Consola híbrida con pantalla OLED de 7 pulgadas y almacenamiento de 64GB.', 12),
    ('PRD260603120005PRD20', 'CAT260603120005CAT05', 'Consola Portátil Steam Deck OLED 512GB', 'Valve', 'STEAM-DECK-OLED-512', 2999.00, 0, 'Consola portátil para juegos de PC con pantalla HDR OLED y batería de larga duración.', 12),

    ('PRD260603120006PRD21', 'CAT260603120006CAT06', 'Apple Watch Series 9 GPS 45mm', 'Apple', 'MR9A3QL/A', 1899.00, 0, 'Reloj inteligente con sensor de temperatura, detección de choques y doble toque.', 12),
    ('PRD260603120006PRD22', 'CAT260603120006CAT06', 'Samsung Galaxy Watch6 Classic 47mm', 'Samsung', 'SM-R960N', 1499.00, 0, 'Reloj inteligente con bisel giratorio físico y monitoreo avanzado de salud.', 12),
    ('PRD260603120006PRD23', 'CAT260603120006CAT06', 'Garmin Fenix 7 Pro Sapphire Solar', 'Garmin', '010-02777-10', 3699.00, 0, 'Reloj deportivo premium con carga solar, mapas topográficos y linterna LED.', 24),
    ('PRD260603120006PRD24', 'CAT260603120006CAT06', 'Pulsera Deportiva Fitbit Charge 6', 'Fitbit', 'FB423BKBK', 699.00, 0, 'Monitor de actividad física con GPS integrado y herramientas de ritmo cardíaco.', 12),

    ('PRD260603120007PRD25', 'CAT260603120007CAT07', 'Cámara Mirrorless Sony Alpha 7 IV', 'Sony', 'ILCE-7M4', 9499.00, 0, 'Cámara de lentes intercambiables con sensor Full-Frame de 33MP y video 4K 60p.', 24),
    ('PRD260603120007PRD26', 'CAT260603120007CAT07', 'Cámara Mirrorless Canon EOS R6 Mark II', 'Canon', '5662C002', 9999.00, 0, 'Cámara híbrida Full-Frame con autoenfoque avanzado y ráfagas de hasta 40 fps.', 24),
    ('PRD260603120007PRD27', 'CAT260603120007CAT07', 'Cámara Mirrorless Fujifilm X-T5', 'Fujifilm', 'X-T5-18-55', 8299.00, 0, 'Cámara de diseño retro con sensor APS-C de 40MP y estabilización integrada.', 12),
    ('PRD260603120007PRD28', 'CAT260603120007CAT07', 'Cámara de Acción GoPro HERO12 Black', 'GoPro', 'CHDHX-121-RW', 1799.00, 0, 'Cámara de acción ultra resistente con estabilización HyperSmooth 6.0.', 12),

    ('PRD260603120008PRD29', 'CAT260603120008CAT08', 'Mouse Inalámbrico Logitech MX Master 3S', 'Logitech', '910-006557', 499.00, 0, 'Mouse ergonómico premium para productividad con sensor de 8000 DPI.', 12),
    ('PRD260603120008PRD30', 'CAT260603120008CAT08', 'Teclado Mecánico Corsair K70 RGB PRO', 'Corsair', 'CH-9109410-SP', 799.00, 0, 'Teclado gaming con interruptores Cherry MX Red y retroiluminación RGB por tecla.', 24),
    ('PRD260603120008PRD31', 'CAT260603120008CAT08', 'Disco Duro Externo WD My Passport 2TB', 'Western Digital', 'WDBYVG0020BBK', 349.00, 0, 'Disco duro portátil USB 3.0 con protección por contraseña y copia de seguridad.', 36),
    ('PRD260603120008PRD32', 'CAT260603120008CAT08', 'Memoria RAM Kingston FURY Beast DDR5 16GB', 'Kingston', 'KF552C40BBK2-16', 299.00, 0, 'Kit de memoria RAM de alto rendimiento optimizado para plataformas Intel y AMD.', 36);

    COMMIT TRANSACTION;
    PRINT 'Database seeded successfully!';
END TRY
BEGIN CATCH
    ROLLBACK TRANSACTION;
    PRINT 'Error occurred during database seeding. All changes rolled back.';
    THROW;
END CATCH;
