package com.fred.sync.web.service;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;

import org.apache.http.client.utils.URIBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import com.fred.sync.exceptions.InternalIOException;
import com.fred.sync.exceptions.Non200Exception;
import com.fred.sync.web.domain.request.GenericRequestREST;
import com.fred.sync.web.domain.response.GenericResponseREST;

@Service("RESTProvider")
public class RESTProviderService {

	private static final Logger log = LoggerFactory.getLogger(RESTProviderService.class);

	public static URI getFullURI(String url) {
		try {
			URIBuilder builder = new URIBuilder(url);
			return builder.build().toURL().toURI();
		} catch (URISyntaxException e) {
			log.error("URISyntaxException", e);
		} catch (MalformedURLException e) {
			log.error("MalformedURLException", e);
		}
		throw new InternalIOException();
	}

	public static GenericResponseREST doRest(HttpMethod httpMethod, URI url, GenericRequestREST request,
			Class<? extends GenericResponseREST> responseType) {
		return doRest(httpMethod, url, request, responseType, new HttpHeaders());
	}

	public static GenericResponseREST doRest(HttpMethod httpMethod, URI url, GenericRequestREST request, Class<? extends GenericResponseREST> responseType,
			HttpHeaders headers) {

		log.debug("Remote " + httpMethod.name() + " : " + url);
		HttpEntity<GenericRequestREST> requestHttpEntity = new HttpEntity<>(request, headers);

		if (responseType != null) {
			try {
				RestTemplate restTemplate = new RestTemplate();
				ResponseEntity<? extends GenericResponseREST> responseREST = restTemplate.exchange(url, httpMethod,
						requestHttpEntity, responseType);
				log.debug("Got " + responseREST.getStatusCode());
				return responseREST.getBody();

			} catch (HttpStatusCodeException e) {
				if (e.getStatusCode().is5xxServerError()) {
					log.error("Remote internal error", e);
					throw new InternalIOException();
				} else
					log.debug("Got " + e.getStatusCode());

				Non200Exception exception = new Non200Exception(e.getMessage(), e.getResponseBodyAsString());
				exception.statusCode = e.getStatusCode().value();
				throw exception;
			}
		}
		return null;
	}
}
