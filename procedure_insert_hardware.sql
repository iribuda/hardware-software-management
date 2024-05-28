CREATE PROCEDURE sp_insert_hardware(
	@hardware_name varchar(50),
	@warranty_start_date date,
	@warranty_exp_date date,
	@manufacturer_name varchar(50)
)
AS
BEGIN
	DECLARE @manufacturer_id int = (SELECT manufacturer_id FROM manufacturer WHERE manufacturer_name = @manufacturer_name);
	INSERT INTO warranty (warranty_status, w_start_date, expiration_date) VALUES ('Active', @warranty_start_date, @warranty_exp_date);
	INSERT INTO hardware (hardware_name, manufacturer_id, warranty_id) VALUES (@hardware_name, @manufacturer_id, IDENT_CURRENT('warranty')); 
END;
