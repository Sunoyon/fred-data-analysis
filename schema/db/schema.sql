CREATE USER fred_write WITH PASSWORD 'fred_write';
CREATE DATABASE fred_db;
GRANT ALL PRIVILEGES ON DATABASE fred_db to fred_write;

--------------------------------------------------------------------------------------------
-- Login in fred_db database as fred_write user
-- psql -d fred_db -U fred_write -W
------------------------------------------- fred_write ---------------------------------

-- Store Observations of Series
CREATE TABLE fred_series
(
	id bigserial primary key,
	series_id varchar(20) NOT NULL,
	realtime_start date NOT NULL,
	realtime_end date NOT NULL,
	"date" date NOT NULL,
	value real NOT NULL
);
CREATE INDEX fred_series_series_id_idx ON fred_series(series_id);
CREATE INDEX fred_series_date_idx ON fred_series("date");
CREATE INDEX fred_series_realtime_start_realtime_end_idx ON fred_series(realtime_start, realtime_end);

-- Store last offset for syncing
CREATE TABLE observation_last_offset
(
	id bigserial primary key,
	series_id varchar(20) NOT NULL,
	realtime_start date NOT NULL,
	realtime_end date NOT NULL,
	last_offset BIGINT NOT NULL,
	unique(series_id, realtime_start, realtime_end)
);