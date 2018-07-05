---- Average Unemployment rate for each year ----
-- Considering date as observation date
SELECT EXTRACT(YEAR FROM "date"), AVG(value)
FROM fred_series
WHERE "date" >= '1980-01-01' AND "date" <= '2015-12-31'
AND series_id = 'UNRATE'
GROUP BY 1
ORDER BY 1;


---- Average Unemployment rate for each year ----
-- Considering realtime_start as observation date
SELECT EXTRACT(YEAR FROM realtime_start), AVG(value)
FROM fred_series
WHERE realtime_start >= '1980-01-01' AND realtime_end <= '2015-12-31'
AND series_id = 'UNRATE'
GROUP BY 1
ORDER BY 1;
