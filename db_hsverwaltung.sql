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
    manufacturer_id int NOT NULL FOREIGN KEY (manufacturer_id) REFERENCES manufacturer(manufacturer_id),
    warranty_id int NOT NULL FOREIGN KEY (warranty_id) REFERENCES warranty(warranty_id)
);

CREATE TABLE h_order (
    order_id int Primary Key IDENTITY(1, 1) NOT NULL,
    order_date DATE NOT NULL,
    order_status VARCHAR(50) NOT NULL,
    hardware_id int NOT NULL FOREIGN KEY (hardware_id) REFERENCES hardware(hardware_id)
);

CREATE TABLE failure(
    failure_id int Primary Key IDENTITY(1, 1) NOT NULL,
    failure_date Date NOT NULL,
    failure_type VARCHAR(50),
    failure_status VARCHAR(50) NOT NULL DEFAULT 'To repair', 
    hardware_id int NOT NULL FOREIGN KEY(hardware_id) REFERENCES hardware(hardware_id)
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
    vendor_id int NOT NULL FOREIGN KEY(vendor_id) REFERENCES vendor(vendor_id)
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
	software_id int NOT NULL FOREIGN KEY(software_id) REFERENCES software(software_id)
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
('Emily', 'Jones', 'emily.jones@example.com'),
('Michael', 'Brown', 'michael.brown@example.com'),
('Sophia', 'Davis', 'sophia.davis@example.com'),
('Matthew', 'Wilson', 'matthew.wilson@example.com'),
('Emma', 'Martinez', 'emma.martinez@example.com'),
('William', 'Anderson', 'william.anderson@example.com'),
('Olivia', 'Taylor', 'olivia.taylor@example.com'),
('James', 'Thomas', 'james.thomas@example.com'),
('Amelia', 'Hernandez', 'amelia.hernandez@example.com'),
('Benjamin', 'Young', 'benjamin.young@example.com'),
('Ava', 'King', 'ava.king@example.com'),
('Ethan', 'Wright', 'ethan.wright@example.com'),
('Mia', 'Lopez', 'mia.lopez@example.com'),
('Alexander', 'Hill', 'alexander.hill@example.com'),
('Charlotte', 'Scott', 'charlotte.scott@example.com'),
('Daniel', 'Green', 'daniel.green@example.com'),
('Harper', 'Adams', 'harper.adams@example.com'),
('Liam', 'Baker', 'liam.baker@example.com'),
('Evelyn', 'Gonzalez', 'evelyn.gonzalez@example.com'),
('Logan', 'Nelson', 'logan.nelson@example.com');

INSERT INTO Manufacturer (manufacturer_name, mobile_number, email)
VALUES 
    ('Manufacturer A', '123456789', 'manufacturerA@example.com'),
    ('Manufacturer B', '987654321', 'manufacturerB@example.com'),
    ('Manufacturer C', '111222333', 'manufacturerC@example.com'),
    ('Manufacturer D', '444555666', 'manufacturerD@example.com'),
    ('Manufacturer E', '777888999', 'manufacturerE@example.com'),
    ('Manufacturer F', '123123123', 'manufacturerF@example.com'),
    ('Manufacturer G', '456456456', 'manufacturerG@example.com'),
    ('Manufacturer H', '789789789', 'manufacturerH@example.com'),
    ('Manufacturer I', '987654321', 'manufacturerI@example.com'),
    ('Manufacturer J', '123987654', 'manufacturerJ@example.com'),
    ('Manufacturer K', '555444333', 'manufacturerK@example.com'),
    ('Manufacturer L', '111000999', 'manufacturerL@example.com'),
    ('Manufacturer M', '222333444', 'manufacturerM@example.com'),
    ('Manufacturer N', '333444555', 'manufacturerN@example.com'),
    ('Manufacturer O', '444555666', 'manufacturerO@example.com'),
    ('Manufacturer P', '777777777', 'manufacturerP@example.com'),
    ('Manufacturer Q', '888888888', 'manufacturerQ@example.com'),
    ('Manufacturer R', '999999999', 'manufacturerR@example.com'),
    ('Manufacturer S', '101010101', 'manufacturerS@example.com'),
    ('Manufacturer T', '202020202', 'manufacturerT@example.com'),
    ('Manufacturer U', '303030303', 'manufacturerU@example.com'),
    ('Manufacturer V', '404040404', 'manufacturerV@example.com');


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
    ('Laptop', 1, 1),
    ('Printer', 2, 2), 
    ('Hardware 3', 3, 3),
    ('Hardware 4', 4, 4),
    ('Hardware 5', 5, 5),
    ('Hardware 6', 6, 6),
    ('Hardware 7', 7, 7),
    ('Hardware 8', 8, 8),
    ('Hardware 9', 9, 9),
    ('Hardware 10', 10, 10),
    ('Hardware 11', 11, 11),
    ('Hardware 12', 12, 12),
    ('Hardware 13', 13, 13),
    ('Hardware 14', 14, 14),
    ('Hardware 15', 15, 15),
    ('Hardware 16', 16, 16),
    ('Hardware 17', 17, 17),
    ('Hardware 18', 18, 18),
    ('Hardware 19', 19, 19),
    ('Hardware 20', 20, 20),
    ('Hardware 21', 21, 21),
    ('Hardware 22', 22, 22);


INSERT INTO failure(hardware_id, failure_date, failure_type, failure_status)
VALUES 
(5, '2024-03-17', 'Screen malfunction', 'To repair'),
(8, '2024-03-19', 'Keyboard failure', 'To repair'),
(10, '2024-03-20', 'Printer jam', 'To repair'),
(12, '2024-03-15', 'Software crash', 'To repair'),
(15, '2024-03-18', 'Battery drain', 'To repair'),
(18, '2024-03-20', 'Motherboard failure', 'To repair'),
(3, '2024-03-19', 'Disk read error', 'To repair'),
(1, '2024-03-17', 'Network card failure', 'To repair'),
(6, '2024-03-16', 'RAM corruption', 'To repair'),
(13, '2024-03-15', 'Graphics card failure', 'To repair'),
(9, '2024-03-18', 'Mouse malfunction', 'To repair'),
(7, '2024-03-20', 'Operating system crash', 'To repair'),
(4, '2024-03-16', 'USB port malfunction', 'To repair'),
(11, '2024-03-19', 'Scanner malfunction', 'To repair'),
(14, '2024-03-17', 'Audio output issue', 'To repair'),
(16, '2024-03-15', 'Touchpad failure', 'To repair'),
(2, '2024-03-20', 'CD/DVD drive failure', 'To repair'),
(17, '2024-03-18', 'Internet connectivity issue', 'To repair'),
(19, '2024-03-16', 'Graphics driver crash', 'To repair'),
(20, '2024-03-19', 'Power button malfunction', 'To repair');

    -- Insert data into the h_order table
INSERT INTO h_order (order_date, order_status, hardware_id)
VALUES 
    ('2024-03-01', 'Pending', 1),
    ('2024-03-05', 'Shipped', 2),
    ('2024-03-10', 'Delivered', 3),
    ('2024-03-15', 'Cancelled', 4),
    ('2024-03-20', 'Pending', 5),
    ('2024-03-25', 'Shipped', 6),
    ('2024-03-30', 'Delivered', 7),
    ('2024-04-01', 'Pending', 8),
    ('2024-04-05', 'Shipped', 9),
    ('2024-04-10', 'Delivered', 10),
    ('2024-04-15', 'Cancelled', 11),
    ('2024-04-20', 'Pending', 12),
    ('2024-04-25', 'Shipped', 13),
    ('2024-04-30', 'Delivered', 14),
    ('2024-05-01', 'Pending', 15),
    ('2024-05-05', 'Shipped', 16),
    ('2024-05-10', 'Delivered', 17),
    ('2024-05-15', 'Cancelled', 18),
    ('2024-05-20', 'Pending', 19),
    ('2024-05-25', 'Shipped', 20);

-- Inserting into vendor table
INSERT INTO vendor (vendor_name, email, mobile_number) VALUES
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

-- Inserting into software table
INSERT INTO software (software_name, software_version, vendor_id) VALUES
('Microsoft Office', '365', 1),
('Photoshop', 'CC 2023', 2),
('Adobe Acrobat', 'Pro DC', 2),
('Adobe Premiere Pro', '2023', 2),
('iTunes', '12.12', 3),
('Google Chrome', '99', 4),
('Android', '12', 4),
('Amazon Web Services', 'S3', 5),
('Oracle Database', '19c', 6),
('IBM Watson', '2.0', 7),
('Salesforce CRM', 'Winter 22', 8),
('Cisco Webex', '5.0', 9),
('VMware vSphere', '7.0', 10),
('HP LaserJet', 'MFP M234dwe', 11),
('Dell Precision', '3561', 12),
('SAP ERP', 'S/4HANA', 13),
('Lenovo ThinkPad', 'X1 Carbon', 14),
('Nvidia GeForce', 'RTX 4090', 15),
('Qualcomm Snapdragon', '888', 16),
('PlayStation', 'PS5', 17);

-- Inserting into license table
INSERT INTO license (license_key, license_start_date, expiration_date, purchase_date, license_status, price, software_id) VALUES
(123456, '2023-01-01 00:00:00', '2024-01-01 00:00:00', '2022-12-01 00:00:00', 'active', 1000.00, 1),
(654321, '2022-12-01 00:00:00', '2023-12-01 00:00:00', '2022-11-01 00:00:00', 'active', 500.00, 2),
(987654, '2023-01-15 00:00:00', '2024-01-15 00:00:00', '2022-12-15 00:00:00', 'active', 1200.00, 3),
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
(456123, '2022-11-25 00:00:00', '2023-11-25 00:00:00', '2022-10-25 00:00:00', 'active', 1700.00, 16),
(789123, '2023-02-10 00:00:00', '2024-02-10 00:00:00', '2022-12-10 00:00:00', 'active', 1900.00, 17),
(123789, '2023-01-05 00:00:00', '2024-01-05 00:00:00', '2022-12-05 00:00:00', 'inactive', 2100.00, 18),
(456789, '2022-12-15 00:00:00', '2023-12-15 00:00:00', '2022-11-15 00:00:00', 'inactive', 2300.00, 19),
(987654, '2023-01-30 00:00:00', '2024-01-30 00:00:00', '2022-12-30 00:00:00', 'expired', 2500.00, 20);


-- Sample data for worker_hardware table
INSERT INTO worker_hardware (worker_id, hardware_id, usage_start_date) VALUES
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
(11, 11, '2023-11-10'),
(12, 12, '2023-12-15'),
(13, 13, '2024-01-20'),
(14, 14, '2024-02-25'),
(15, 15, '2024-03-01'),
(16, 16, '2024-03-05'),
(17, 17, '2024-03-10'),
(18, 18, '2024-03-15'),
(19, 19, '2024-03-20'),
(20, 20, '2024-03-25');

-- Sample data for worker_software table
INSERT INTO worker_software (worker_id, software_id, usage_start_date) VALUES
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
(11, 11, '2023-11-10'),
(12, 12, '2023-12-15'),
(13, 13, '2024-01-20'),
(14, 14, '2024-02-25'),
(15, 15, '2024-03-01'),
(16, 16, '2024-03-05'),
(17, 17, '2024-03-10'),
(18, 18, '2024-03-15'),
(19, 19, '2024-03-20'),
(20, 20, '2024-03-25');
