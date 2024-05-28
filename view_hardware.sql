GO
CREATE VIEW vw_hardware_details
WITH SCHEMABINDING
AS 
SELECT h.hardware_id, h.hardware_name, h.manufacturer_id, h.warranty_id, m.manufacturer_name, m.mobile_number, m.email, w.warranty_status, w.w_start_date, w.expiration_date
FROM dbo.hardware h
JOIN dbo.manufacturer m ON h.manufacturer_id=m.manufacturer_id
JOIN dbo.warranty w ON w.warranty_id = h.warranty_id
GO