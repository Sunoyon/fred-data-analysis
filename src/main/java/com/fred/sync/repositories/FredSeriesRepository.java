package com.fred.sync.repositories;

import java.sql.Date;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.fred.sync.web.domain.db.FredSeries;

@Repository
public interface FredSeriesRepository extends JpaRepository<FredSeries, Long> {

	@Modifying
	@Query(value = "DELETE FROM fred_series WHERE series_id = ?1 AND realtime_start >= ?2 AND realtime_end <= ?3", nativeQuery = true)
	public void deleteBySeriesIdAndRealtimeStartGreaterThanEqualAndRealtimeEndLesserThanEqual(String seriesId, Date realtimeStart, Date realtimeEnd);

}
