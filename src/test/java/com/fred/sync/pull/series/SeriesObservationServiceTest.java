package com.fred.sync.pull.series;

import javax.inject.Inject;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;

import com.fred.sync.App;

@SpringBootTest
@RunWith(SpringRunner.class)
@Import(App.class)
public class SeriesObservationServiceTest {

	private static final Logger log = LoggerFactory.getLogger(SeriesObservationServiceTest.class);
	@Inject
	SeriesObservationPullService service;

	@Test
	public void getSeriesTest() {
		String seriesId = "GDPC1";
		String realtimeStart = "2018-07-01";
		String realtimeEnd = "2018-07-02";
		Long offset = 0l;
		Long limit = 10l;
		String fileType = "json";
		
		SeriesObservationResponsePOJO series = service.getSeries(seriesId, realtimeStart, realtimeEnd, offset, limit, fileType);
		
		for (SeriesObservationPOJO observation : series.observations) {
			log.info("Observation: {}", observation.toString());
		}
		log.info("Observation count {}", series.observations.size());
	}
}
