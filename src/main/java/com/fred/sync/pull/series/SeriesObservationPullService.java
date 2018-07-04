package com.fred.sync.pull.series;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;

import com.fred.sync.exceptions.Non200Exception;
import com.fred.sync.web.service.RESTProviderService;


@Service
public class SeriesObservationPullService {
	private static final Logger log = LoggerFactory.getLogger(SeriesObservationPullService.class);
	private static final String SERIES_OBSRV_API_PATH = "fred/series/observations?";
	private String fredUrl;
	private String apiKey;
	
	@Autowired
	public SeriesObservationPullService(@Value("${fred.api.url}") String fredUrl, @Value("${fred.api.key}") String fredApiKey) {
		log.info("Initializing service with fred url: {}", fredUrl);
		this.fredUrl = fredUrl;
		this.apiKey = fredApiKey;
	}
	
	public SeriesObservationResponsePOJO getSeries(String seriesId, String realtimeStart, String realtimeEnd, Long offset, Long limit, String fileType) {
		SeriesObservationRequestPOJO requestPOJO = new SeriesObservationRequestPOJO(seriesId, realtimeStart, realtimeEnd, offset, limit, fileType);
		log.info("Requesting fred with body: {}", requestPOJO.toString());
		
		SeriesObservationResponsePOJO responsePOJO = new SeriesObservationResponsePOJO();
		
		try {
			String parameter = getPathParameter(seriesId, realtimeStart, realtimeEnd, offset, limit, fileType);
            responsePOJO = (SeriesObservationResponsePOJO) RESTProviderService.doRest(HttpMethod.GET,
                    RESTProviderService.getFullURI(fredUrl + SERIES_OBSRV_API_PATH + parameter), null,
                    SeriesObservationResponsePOJO.class);
            log.info("Observation count: {}", responsePOJO.observations.size());
        } catch (Non200Exception ex) {
            log.error("Get observation series got failed from payment; statusCode {}, reason {}", ex.statusCode,
                    ex.exceptionReason);
            responsePOJO.message = ex.exceptionReason;
        } catch (Exception ex) {
            log.error("EXCEPTION {}", ex);
            responsePOJO.message = ex.getMessage();
        }
        return responsePOJO;
		
	}
	
	private String getPathParameter(String seriesId, String realtimeStart, String realtimeEnd, Long offset, Long limit, String fileType) {
		return String.format("api_key=%s&series_id=%s&realtime_start=%s&realtime_end=%s&offset=%d&limit=%d&file_type=%s", apiKey, seriesId, realtimeStart, realtimeEnd, offset, limit, fileType);
	}
}
