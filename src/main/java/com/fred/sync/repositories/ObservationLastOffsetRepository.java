package com.fred.sync.repositories;

import java.sql.Date;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.fred.sync.web.domain.db.ObservationLastOffset;

public interface ObservationLastOffsetRepository extends JpaRepository<ObservationLastOffset, Long> {

	public ObservationLastOffset findBySeriesIdAndRealtimeStartAndRealtimeEnd(String seriesId, Date realtimeStart, Date realtimeEnd);
	@Modifying
	@Query(value = "DELETE FROM observation_last_offset WHERE series_id = ?1 AND realtime_start >= ?2 AND realtime_end <= ?3", nativeQuery = true)
	public void deleteBySeriesIdAndRealtimeStartAndRealtimeEnd(String seriesId, Date realtimeStart, Date realtimeEnd);
}
