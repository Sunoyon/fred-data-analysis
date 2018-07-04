package com.fred.sync.pull.series;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fred.sync.web.domain.response.GenericResponseREST;

public class SeriesObservationResponsePOJO extends GenericResponseREST {

	private static final long serialVersionUID = -2976907122902420516L;
	@JsonProperty(value = "realtime_start")
	public String realtimeStart;
	@JsonProperty(value = "realtime_end")
	public String realtimeEnd;
	@JsonProperty(value = "observation_start")
	public String observationStart;
	@JsonProperty(value = "observation_end")
	public String observationEnd;
	@JsonProperty(value = "units")
	public String units;
	@JsonProperty(value = "output_type")
	public Integer outputType;
	@JsonProperty(value = "file_type")
	public String fileType;
	@JsonProperty(value = "order_by")
	public String orderBy;
	@JsonProperty(value = "sorted_order")
	public String sortedOrder;
	@JsonProperty(value = "count")
	public Long count;
	@JsonProperty(value = "offset")
	public Long offset;
	@JsonProperty(value = "limit")
	public Long limit;
	@JsonProperty(value = "observations")
	public List<SeriesObservationPOJO> observations;
}
