---- Average Unemployment rate for each year ----
-- Considering date as observation date
SELECT EXTRACT(YEAR FROM "date") as year, AVG(value) as avg_rate
FROM fred_series
WHERE "date" >= '1980-01-01' AND "date" <= '2015-12-31'
AND series_id = 'UNRATE'
GROUP BY 1
ORDER BY 1;
