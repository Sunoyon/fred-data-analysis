package com.fred.sync.web.controller;

import java.net.HttpURLConnection;

import javax.inject.Inject;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;

import com.fred.sync.web.domain.response.GenericResponseREST;
import com.fred.sync.web.handler.FredAnalyticsHandler;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/")
@Api(value = "Fred Data Sync")
public class FredAnalyticsController {
	
	@Inject
	private FredAnalyticsHandler handler;
	
	@ApiResponses(value = {
			@ApiResponse(code = HttpURLConnection.HTTP_OK, message = "Success", response = GenericResponseREST.class) })
	@RequestMapping(value = {"/sync/v1/{seriesId}/{realtimeStart}/{realtimeEnd}/{mode}" }, 
			method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public DeferredResult<ResponseEntity<?>> refreshRules(
			@PathVariable(name = "seriesId", required = true) String seriesId, 
			@PathVariable(name = "realtimeStart", required = true) String from,
			@PathVariable(name = "realtimeEnd", required = true) String to,
			@ApiParam(required = false) @PathVariable(name = "mode", required = false) String mode) {
		DeferredResult<ResponseEntity<?>> result = new DeferredResult<>();
		handler.etl(result, seriesId, from, to, mode);
		return result;
	}

}
