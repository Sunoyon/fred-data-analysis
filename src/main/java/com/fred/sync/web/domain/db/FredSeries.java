package com.fred.sync.web.domain.db;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "fred_series")
public class FredSeries {

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

	@Column(name = "date")
	public Date date;

	@Column(name = "value")
	public Double value;

	public FredSeries(String seriesId, Date realtimeStart, Date realtimeEnd, Date date, Double value) {
		this.seriesId = seriesId;
		this.realtimeStart = realtimeStart;
		this.realtimeEnd = realtimeEnd;
		this.date = date;
		this.value = value;
	}

	public FredSeries() {
	}
}
