package com.fred.sync.pull.series;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fred.sync.web.domain.response.GenericResponseREST;

public class SeriesObservationPOJO extends GenericResponseREST {

	private static final long serialVersionUID = 3121644922173212469L;
	@JsonProperty(value = "realtime_start")
	public String realtimeStart;
	@JsonProperty(value = "realtime_end")
	public String realtimeEnd;
	@JsonProperty(value = "date")
	public String date;
	@JsonProperty(value = "value")
	public String value;

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("SeriesObservationPOJO [realtimeStart=");
		builder.append(realtimeStart);
		builder.append(", realtimeEnd=");
		builder.append(realtimeEnd);
		builder.append(", date=");
		builder.append(date);
		builder.append(", value=");
		builder.append(value);
		builder.append("]");
		return builder.toString();
	}

}
