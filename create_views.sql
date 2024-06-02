--DROP VIEW vw_failure_details;
CREATE VIEW vw_failure_details
WITH SCHEMABINDING
AS 
        (SELECT f.failure_id, f.failure_type, f.failure_status, f.failure_date, f.hardware_id, h.hardware_name, w.worker_name, w.worker_surname
        FROM dbo.failure f
                 JOIN dbo.hardware h ON f.hardware_id = h.hardware_id
                 JOIN dbo.worker_hardware wh ON h.hardware_id = wh.hardware_id
                 JOIN dbo.worker w ON w.worker_id = wh.hardware_id)