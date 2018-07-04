package com.fred.sync.pull.series;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fred.sync.web.domain.request.GenericRequestREST;

public class SeriesObservationRequestPOJO extends GenericRequestREST{

	@JsonProperty(value = "series_id")
	public String seriesId;
	@JsonProperty(value = "realtime_start")
	public String realtimeStart;
	@JsonProperty(value = "realtime_end")
	public String realtimeEnd;
	@JsonProperty(value = "offset")
	public Long offset;
	@JsonProperty(value = "limit")
	public Long limit;
	@JsonProperty(value = "file_type")
	public String fileType;

	public SeriesObservationRequestPOJO(String seriesId, String realTimeStart, String realTimeEnd, Long offset,
			Long limit, String fileType) {
		this.seriesId = seriesId;
		this.realtimeStart = realTimeStart;
		this.realtimeEnd = realTimeEnd;
		this.offset = offset;
		this.limit = limit;
		this.fileType = fileType;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("SeriesObservationRequestPOJO [seriesId=");
		builder.append(seriesId);
		builder.append(", realtimeStart=");
		builder.append(realtimeStart);
		builder.append(", realtimeEnd=");
		builder.append(realtimeEnd);
		builder.append(", offset=");
		builder.append(offset);
		builder.append(", limit=");
		builder.append(limit);
		builder.append(", fileType=");
		builder.append(fileType);
		builder.append("]");
		return builder.toString();
	}
}
