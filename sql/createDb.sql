DROP DATABASE DB_HSverwaltung;
CREATE DATABASE DB_HSverwaltung
ON PRIMARY (
	NAME = 'HSverwaltung',
	FILENAME = 'C:\Program Files\Microsoft SQL Server\MSSQL15.MSSQLSERVER\MSSQL\DATA\HSverwaltung_data.mdf',
	SIZE = 10MB,
	MAXSIZE = 100MB,
	FILEGROWTH = 20%
)