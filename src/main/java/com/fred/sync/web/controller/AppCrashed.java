package com.fred.sync.web.controller;

import java.lang.reflect.Method;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.async.DeferredResult;

import com.fred.sync.exceptions.BaseException;
import com.fred.sync.exceptions.GenericAppException;
import com.fred.sync.web.domain.response.GenericResponseREST;

public class AppCrashed implements AsyncUncaughtExceptionHandler {

	private Logger log = LoggerFactory.getLogger(this.getClass());

	@SuppressWarnings("unchecked")
	@Override
	public void handleUncaughtException(Throwable exception, Method method, Object... params) {

		log.error("App has exception: " + exception.getMessage());

		if (params[0] instanceof DeferredResult) {
			DeferredResult<ResponseEntity<GenericResponseREST>> errorResult = (DeferredResult<ResponseEntity<GenericResponseREST>>) params[0];

			if (exception instanceof GenericAppException) {
				processOrganizationException((GenericAppException) exception, errorResult);
			}
			else if (exception instanceof BaseException) {
				processOrganizationException((BaseException) exception, errorResult);
			}
			else {
				log.error("Exception: ", exception);
				processLampInHandException((Exception) exception, errorResult);
			}
		} else
			log.error("Async Exception in " + method.getName(), exception);
	}

	private void processLampInHandException(Exception exception,
			DeferredResult<ResponseEntity<GenericResponseREST>> errorResult) {
		GenericResponseREST errorResponseRest = new GenericResponseREST();
		errorResponseRest.message = exception.getMessage();
		ResponseEntity<GenericResponseREST> entity = new ResponseEntity<>(errorResponseRest,
				HttpStatus.INTERNAL_SERVER_ERROR);
		errorResult.setResult(entity);
	}

	private void processOrganizationException(BaseException baseException,
			DeferredResult<ResponseEntity<GenericResponseREST>> errorResult) {
		GenericResponseREST errorResponseRest = new GenericResponseREST();
		errorResponseRest.message = baseException.getMessage();
		ResponseEntity<GenericResponseREST> entity = new ResponseEntity<>(errorResponseRest,
				HttpStatus.valueOf(baseException.statusCode));
		errorResult.setResult(entity);
	}

	private void processOrganizationException(GenericAppException genericOfferException,
											  DeferredResult<ResponseEntity<GenericResponseREST>> errorResult) {
		GenericResponseREST errorResponseRest = new GenericResponseREST();
		errorResponseRest.message = genericOfferException.getMessage();
		ResponseEntity<GenericResponseREST> entity = new ResponseEntity<>(errorResponseRest,
				HttpStatus.valueOf(genericOfferException.statusCode));
		errorResult.setResult(entity);
	}

}
