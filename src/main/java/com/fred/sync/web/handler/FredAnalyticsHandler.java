package com.fred.sync.web.handler;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.async.DeferredResult;

import com.fred.sync.etl.BaseETLService;
import com.fred.sync.web.domain.response.GenericResponseREST;


@Component
public class FredAnalyticsHandler {
	
	private static final Logger log = LoggerFactory.getLogger(FredAnalyticsHandler.class);
	
	@Inject
	private BaseETLService etl;
	
	
	@Async
	public void etl(DeferredResult<ResponseEntity<?>> result, String seriesId, String from, String to) {
		try {
			log.info("Series {},From {}, To {}", seriesId, from, to);
			result.setResult(ResponseEntity.ok().body(new GenericResponseREST("Request for ETL Job has been accepted")));
			etl.dateRangeSync(seriesId, from, to);
		} catch (Exception e) {
			log.error("Error in importing new report {}", e);
			result.setResult(ResponseEntity.badRequest().body(new GenericResponseREST("Request cannot be processed")));
		}
	}

}
