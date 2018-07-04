SELECT EXTRACT(YEAR FROM realtime_start), AVG(value)
FROM fred_series
WHERE realtime_start >= '1980-01-01' AND realtime_end <= '2015-12-31'
AND series_id = 'UNRATE'
GROUP BY 1
ORDER BY 1;