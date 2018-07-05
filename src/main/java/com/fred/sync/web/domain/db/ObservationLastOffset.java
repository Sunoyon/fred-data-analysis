package com.fred.sync.web.domain.db;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "observation_last_offset")
public class ObservationLastOffset {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	public Long id;
	
	@Column(name = "series_id")
	public String seriesId;
	
	@Column(name = "realtime_start")
	public Date realtimeStart;
	
	@Column(name = "realtime_end")
	public Date realtimeEnd;
	
	@Column(name = "last_offset")
	public Long lastOffset;

	public ObservationLastOffset(String seriesId, Date realtimeStart, Date realtimeEnd, Long lastOffset) {
		this.seriesId = seriesId;
		this.realtimeStart = realtimeStart;
		this.realtimeEnd = realtimeEnd;
		this.lastOffset = lastOffset;
	}

	public ObservationLastOffset() {
	}
}
