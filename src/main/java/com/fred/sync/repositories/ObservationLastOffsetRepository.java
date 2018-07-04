package com.fred.sync.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fred.sync.web.domain.db.ObservationLastOffset;

public interface ObservationLastOffsetRepository extends JpaRepository<ObservationLastOffset, Long> {

	public ObservationLastOffset findBySeriesIdAndRealtimeStartAndRealtimeEnd(String seriesId, String realtimeStart, String realtimeEnd);
	public void deleteBySeriesIdAndRealtimeStartAndRealtimeEnd(String seriesId, String realtimeStart, String realtimeEnd);
}
