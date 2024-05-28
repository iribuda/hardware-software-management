USE DB_HSverwaltung;

-- creating tables
CREATE TABLE worker(
    worker_id int Primary Key IDENTITY(1, 1) NOT NULL,
    worker_name VARCHAR(50) NOT NULL,
    worker_surname VARCHAR(50) NOT NULL,
    email VARCHAR(255) UNIQUE NOT NULL
);

CREATE TABLE manufacturer (
    manufacturer_id int Primary Key IDENTITY(1, 1) NOT NULL,
    manufacturer_name VARCHAR(50) NOT NULL,
    mobile_number VARCHAR(50) NOT NULL,
    email VARCHAR(50) NOT NULL UNIQUE
);

CREATE TABLE warranty(
    warranty_id int Primary Key IDENTITY(1, 1) NOT NULL,
    warranty_status VARCHAR(50) DEFAULT 'active' NOT NULL,
    w_start_date Date NOT NULL,
    expiration_date Date NOT NULL
);

CREATE TABLE hardware (
    hardware_id int Primary Key IDENTITY(1, 1) NOT NULL,
    hardware_name VARCHAR(50) NOT NULL,
    manufacturer_id int NOT NULL FOREIGN KEY (manufacturer_id) REFERENCES manufacturer(manufacturer_id) ON DELETE CASCADE,
    warranty_id int NOT NULL FOREIGN KEY (warranty_id) REFERENCES warranty(warranty_id) ON DELETE CASCADE
);

CREATE TABLE h_order (
    order_id int Primary Key IDENTITY(1, 1) NOT NULL,
    order_date DATE NOT NULL,
    order_status VARCHAR(50) NOT NULL,
    hardware_id int NOT NULL FOREIGN KEY (hardware_id) REFERENCES hardware(hardware_id) ON DELETE CASCADE
);

CREATE TABLE failure(
    failure_id int Primary Key IDENTITY(1, 1) NOT NULL,
    failure_date Date NOT NULL,
    failure_type VARCHAR(50),
    failure_status VARCHAR(50) NOT NULL DEFAULT 'To repair',
    hardware_id int NOT NULL FOREIGN KEY(hardware_id) REFERENCES hardware(hardware_id) ON DELETE CASCADE
);

-- Creating vendor table
CREATE TABLE vendor (
    vendor_id int Primary Key IDENTITY(1, 1) NOT NULL,
    vendor_name VARCHAR(50) NOT NULL,
    email VARCHAR(50) NOT NULL UNIQUE,
    mobile_number VARCHAR(50) NOT NULL
);

-- Creating software table
CREATE TABLE software (
    software_id int Primary Key IDENTITY(1, 1) NOT NULL,
    software_name VARCHAR(50) NOT NULL,
    software_version VARCHAR(50) NOT NULL,
    vendor_id int NOT NULL FOREIGN KEY(vendor_id) REFERENCES vendor(vendor_id) ON DELETE CASCADE
);

-- Creating license table
CREATE TABLE license (
    license_id int Primary Key IDENTITY(1, 1) NOT NULL,
    license_key int NOT NULL,
    license_start_date DATETIME NOT NULL, 
    expiration_date DATETIME NOT NULL,
    purchase_date DATETIME NOT NULL,
	license_status VARCHAR(50) NOT NULL DEFAULT 'active',
	price FLOAT DEFAULT 0,
	software_id int NOT NULL FOREIGN KEY(software_id) REFERENCES software(software_id) ON DELETE CASCADE
);

CREATE TABLE worker_hardware(
    worker_id int NOT NULL,
    hardware_id int NOT NULL,
    usage_start_date Date NOT NULL,
    PRIMARY KEY(worker_id, hardware_id, usage_start_date),
    FOREIGN KEY(worker_id) REFERENCES worker(worker_id) 
    ON DELETE CASCADE
    ON UPDATE CASCADE,
    FOREIGN KEY(hardware_id) REFERENCES hardware(hardware_id)
    ON DELETE CASCADE
    ON UPDATE CASCADE
);

CREATE TABLE worker_software(
    worker_id int NOT NULL,
    software_id int NOT NULL,
    usage_start_date Date NOT NULL,
    PRIMARY KEY(worker_id, software_id, usage_start_date),
    FOREIGN KEY(worker_id) REFERENCES worker(worker_id) 
    ON DELETE CASCADE
    ON UPDATE CASCADE,
    FOREIGN KEY(software_id) REFERENCES software(software_id)
    ON DELETE CASCADE
    ON UPDATE CASCADE
);


-- inserting values
INSERT INTO worker (worker_name, worker_surname, email) 
VALUES 
	('James', 'Stone', 'james.stone@example.com'),
	('John', 'Smith', 'john.smith@example.com'),
	('Alice', 'Waterson', 'alice.waterson@example.com'),
	('Emily', 'Jones', 'emily.jones@example.com'),
	('Michael', 'Brown', 'michael.brown@example.com'),
	('Sophia', 'Davis', 'sophia.davis@example.com'),
	('Matthew', 'Wilson', 'matthew.wilson@example.com'),
	('Emma', 'Martinez', 'emma.martinez@example.com'),
	('William', 'Anderson', 'william.anderson@example.com'),
	('Olivia', 'Taylor', 'olivia.taylor@example.com'),
	('James', 'Thomas', 'james.thomas@example.com'),
	('Amelia', 'Hernandez', 'amelia.hernandez@example.com'),
	('Benjamin', 'Young', 'benjamin.young@example.com');


INSERT INTO manufacturer (manufacturer_name, mobile_number, email) 
VALUES 
	('Apple', '123-456-7890', 'contact@apple.com'),
	('Samsung', '234-567-8901', 'support@samsung.com'),
	('Dell', '345-678-9012', 'info@dell.com'),
	('HP', '456-789-0123', 'help@hp.com'),
	('Lenovo', '567-890-1234', 'service@lenovo.com'),
	('Asus', '678-901-2345', 'contact@asus.com'),
	('Acer', '789-012-3456', 'support@acer.com'),
	('Microsoft', '890-123-4567', 'info@microsoft.com'),
	('Sony', '901-234-5678', 'service@sony.com'),
	('LG', '012-345-6789', 'contact@lg.com');


INSERT INTO warranty (warranty_status, expiration_date, w_start_date)
VALUES 
    ('Available', '2025-12-31', '2022-01-01'),
    ('Expired', '2020-06-30', '2018-07-01'),
    ('Active', '2024-12-31', '2023-01-01'),
    ('Active', '2026-06-30', '2025-07-01'),
    ('Expired', '2022-06-30', '2020-07-01'),
    ('Active', '2025-12-31', '2022-01-01'),
    ('Active', '2023-06-30', '2022-07-01'),
    ('Expired', '2021-12-31', '2020-01-01'),
    ('Expired', '2023-06-30', '2021-07-01'),
    ('Active', '2024-12-31', '2023-01-01'),
    ('Expired', '2021-06-30', '2019-07-01'),
    ('Active', '2025-12-31', '2022-01-01'),
    ('Expired', '2023-12-31', '2021-01-01'),
    ('Active', '2024-06-30', '2021-07-01'),
    ('Active', '2023-12-31', '2020-01-01'),
    ('Expired', '2021-06-30', '2019-07-01'),
    ('Active', '2025-12-31', '2022-01-01'),
    ('Active', '2024-06-30', '2021-07-01'),
    ('Expired', '2022-12-31', '2020-01-01'),
    ('Active', '2026-06-30', '2023-07-01'),
    ('Expired', '2023-06-30', '2021-07-01'),
    ('Active', '2025-12-31', '2022-01-01');


INSERT INTO hardware (hardware_name, manufacturer_id, warranty_id) 
VALUES 
	('MacBook Pro', 1, 1),
	('Galaxy S21', 2, 2),
	('Inspiron 15', 3, 3),
	('Pavilion x360', 4, 4),
	('ThinkPad X1', 5, 5),
	('ZenBook 14', 6, 6),
	('Aspire 5', 7, 7),
	('Surface Pro 7', 8, 8),
	('PlayStation 5', 9, 9),
	('OLED TV', 10, 10),
	('iMac', 1, 11),
	('Galaxy Tab S7', 2, 12),
	('XPS 13', 3, 13),
	('Envy 13', 4, 14),
	('Yoga 920', 5, 15),
	('ROG Strix', 6, 16),
	('Swift 3', 7, 17),
	('Surface Laptop 3', 8, 18),
	('Bravia TV', 9, 19),
	('Gram 17', 10, 20);


INSERT INTO failure (failure_date, failure_type, failure_status, hardware_id) 
VALUES 
	('2024-01-05', 'Screen failure', 'Repaired', 1),
	('2024-01-20', 'Battery failure', 'Repaired', 2),
	('2024-02-15', 'Hard drive failure', 'Replacement ordered', 3),
	('2024-02-28', 'Overheating', 'Repaired', 4),
	('2024-03-10', 'Keyboard failure', 'Repaired', 5),
	('2024-03-25', 'Screen failure', 'Not repairable', 6),
	('2024-04-05', 'Power supply failure', 'Repaired', 7),
	('2024-04-12', 'Wi-Fi failure', 'Repaired', 8),
	('2024-04-20', 'Hardware failure', 'Replacement ordered', 9),
	('2024-04-28', 'Screen failure', 'Under inspection', 10),
	('2024-05-02', 'Graphics card failure', 'Awaiting parts', 11),
	('2024-05-10', 'Battery failure', 'Not repairable', 12),
	('2024-05-12', 'Touchscreen failure', 'To repair', 13),
	('2024-05-15', 'Cooling system failure', 'Under inspection', 14),
	('2024-05-18', 'USB port failure', 'Replacement ordered', 15);


-- Insert data into the h_order table
INSERT INTO h_order (order_date, order_status, hardware_id) 
VALUES 
	('2023-12-20', 'Delivered', 1),
	('2023-12-25', 'Delivered', 2),
	('2023-12-30', 'Delivered', 3),
	('2024-01-05', 'Delivered', 4),
	('2024-01-10', 'Delivered', 5),
	('2024-01-15', 'Delivered', 6),
	('2024-01-20', 'Delivered', 7),
	('2024-01-25', 'Delivered', 8),
	('2024-02-01', 'Delivered', 9),
	('2024-02-05', 'Delivered', 10),
	('2024-02-10', 'Pending Purchase', 11),
	('2024-02-15', 'Canceled', 12),
	('2024-02-20', 'Purchased', 13),
	('2024-02-25', 'Delivered', 14),
	('2024-03-01', 'Pending Purchase', 15),
	('2024-03-05', 'Canceled', 16),
	('2024-03-10', 'Purchased', 17),
	('2024-03-15', 'Delivered', 18),
	('2024-03-20', 'Pending Purchase', 19),
	('2024-03-25', 'Pending Purchase', 20);


-- Inserting into vendor table
INSERT INTO vendor (vendor_name, email, mobile_number) 
VALUES
	('Microsoft', 'contact@microsoft.com', '1234567890'),
	('Adobe', 'contact@adobe.com', '0987654321'),
	('Apple', 'contact@apple.com', '1112223333'),
	('Google', 'contact@google.com', '4445556666'),
	('Amazon', 'contact@amazon.com', '7778889999'),
	('Oracle', 'contact@oracle.com', '6667778888'),
	('IBM', 'contact@ibm.com', '9998887776'),
	('Salesforce', 'contact@salesforce.com', '8889996665'),
	('Cisco', 'contact@cisco.com', '5554443332'),
	('Intel', 'contact@intel.com', '3332221110'),
	('VMware', 'contact@vmware.com', '2221110009'),
	('HP', 'contact@hp.com', '0009998888'),
	('Dell', 'contact@dell.com', '1112223334'),
	('SAP', 'contact@sap.com', '4445556667'),
	('Lenovo', 'contact@lenovo.com', '7778889990'),
	('Nvidia', 'contact@nvidia.com', '6667778889'),
	('Qualcomm', 'contact@qualcomm.com', '9998887775'),
	('Sony', 'contact@sony.com', '8889996664'),
	('ASUS', 'contact@asus.com', '3332221111');


-- Inserting values into the software table
INSERT INTO software (software_name, software_version, vendor_id) 
VALUES
    ('Windows', '10.0', 1),      -- Microsoft
    ('Photoshop', '2023', 2),    -- Adobe
    ('iOS', '16.0', 3),          -- Apple
    ('Android', '13.0', 4),      -- Google
    ('AWS', '2023', 5),          -- Amazon
    ('Oracle DB', '19c', 6),     -- Oracle
    ('WebSphere', '9.0', 7),     -- IBM
    ('Salesforce CRM', '2023', 8), -- Salesforce
    ('IOS XR', '7.5', 9),        -- Cisco
    ('Intel OneAPI', '2023', 10), -- Intel
    ('vSphere', '7.0', 11),      -- VMware
    ('HP UFT', '15.0', 12),      -- HP
    ('Dell BIOS', '1.2.3', 13),  -- Dell
    ('SAP HANA', '2.0', 14),     -- SAP
    ('Lenovo Vantage', '10.0', 15), -- Lenovo
    ('CUDA', '11.6', 16),        -- Nvidia
    ('Snapdragon', '865', 17),   -- Qualcomm
    ('PlayStation OS', '5.0', 18), -- Sony
    ('ASUS Aura', '3.0', 19),    -- ASUS
    ('Office 365', '2023', 1);   -- Microsoft


-- Inserting into license table
INSERT INTO license (license_key, license_start_date, expiration_date, purchase_date, license_status, price, software_id) 
VALUES
	(123456, '2023-01-01 00:00:00', '2024-01-01 00:00:00', '2022-12-01 00:00:00', 'expired', 1000.00, 1),
	(654321, '2022-12-01 00:00:00', '2023-12-01 00:00:00', '2022-11-01 00:00:00', 'expired', 500.00, 2),
	(987654, '2023-01-15 00:00:00', '2024-01-15 00:00:00', '2022-12-15 00:00:00', 'expired', 1200.00, 3),
	(123987, '2022-11-15 00:00:00', '2023-11-15 00:00:00', '2022-10-15 00:00:00', 'inactive', 800.00, 4),
	(456321, '2023-02-01 00:00:00', '2024-02-01 00:00:00', '2022-12-01 00:00:00', 'inactive', 1500.00, 5),
	(789654, '2022-12-20 00:00:00', '2023-12-20 00:00:00', '2022-11-20 00:00:00', 'expired', 900.00, 6),
	(321987, '2023-01-10 00:00:00', '2024-01-10 00:00:00', '2022-12-10 00:00:00', 'expired', 2000.00, 7),
	(654987, '2022-11-10 00:00:00', '2023-11-10 00:00:00', '2022-10-10 00:00:00', 'active', 700.00, 8),
	(987321, '2023-02-15 00:00:00', '2024-02-15 00:00:00', '2022-12-15 00:00:00', 'active', 1800.00, 9),
	(159357, '2022-12-01 00:00:00', '2023-12-01 00:00:00', '2022-11-01 00:00:00', 'active', 1000.00, 10),
	(357159, '2023-01-20 00:00:00', '2024-01-20 00:00:00', '2022-12-20 00:00:00', 'active', 1200.00, 11),
	(753951, '2022-11-20 00:00:00', '2023-11-20 00:00:00', '2022-10-20 00:00:00', 'inactive', 1400.00, 12),
	(147258, '2023-02-05 00:00:00', '2024-02-05 00:00:00', '2022-12-05 00:00:00', 'inactive', 1600.00, 13),
	(258369, '2022-12-05 00:00:00', '2023-12-05 00:00:00', '2022-11-05 00:00:00', 'expired', 2000.00, 14),
	(369258, '2023-01-25 00:00:00', '2024-01-25 00:00:00', '2022-12-25 00:00:00', 'expired', 1500.00, 15),
	(456123, '2024-03-25 00:00:00', '2025-11-25 00:00:00', '2022-10-25 00:00:00', 'active', 1700.00, 16),
	(789123, '2024-02-10 00:00:00', '2026-02-10 00:00:00', '2022-12-10 00:00:00', 'active', 1900.00, 17),
	(123789, '2024-01-05 00:00:00', '2027-01-05 00:00:00', '2022-12-05 00:00:00', 'inactive', 2100.00, 18),
	(456789, '2024-02-15 00:00:00', '2028-12-15 00:00:00', '2022-11-15 00:00:00', 'inactive', 2300.00, 19),
	(987654, '2024-01-30 00:00:00', '2025-01-30 00:00:00', '2022-12-30 00:00:00', 'expired', 2500.00, 20);


-- Sample data for worker_hardware table
INSERT INTO worker_hardware (worker_id, hardware_id, usage_start_date) 
VALUES
	(1, 1, '2023-01-01'),
	(2, 2, '2023-02-15'),
	(3, 3, '2023-03-10'),
	(4, 4, '2023-04-20'),
	(5, 5, '2023-05-25'),
	(6, 6, '2023-06-30'),
	(7, 7, '2023-07-12'),
	(8, 8, '2023-08-18'),
	(9, 9, '2023-09-22'),
	(10, 10, '2023-10-05');


-- Sample data for worker_software table
INSERT INTO worker_software (worker_id, software_id, usage_start_date) 
VALUES
	(1, 1, '2023-01-01'),
	(2, 2, '2023-02-15'),
	(3, 3, '2023-03-10'),
	(4, 4, '2023-04-20'),
	(5, 5, '2023-05-25'),
	(6, 6, '2023-06-30'),
	(7, 7, '2023-07-12'),
	(8, 8, '2023-08-18'),
	(9, 9, '2023-09-22'),
	(10, 10, '2023-10-05'),
	(3, 18, '2024-01-06'),
	(1, 20, '2024-02-05'),
	(2, 19, '2024-02-16');

GO
CREATE VIEW vw_order_details 
AS
SELECT o.*, h.hardware_name, m.manufacturer_id, m.manufacturer_name
FROM h_order o
JOIN hardware h ON h.hardware_id = o.hardware_id
JOIN manufacturer m ON m.manufacturer_id = h.manufacturer_id;
GO