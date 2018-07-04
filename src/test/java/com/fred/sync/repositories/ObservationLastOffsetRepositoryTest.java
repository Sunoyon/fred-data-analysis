package com.fred.sync.repositories;

import javax.inject.Inject;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;

import com.fred.sync.App;
import com.fred.sync.web.domain.db.ObservationLastOffset;

@SpringBootTest
@RunWith(SpringRunner.class)
@Import(App.class)
public class ObservationLastOffsetRepositoryTest {
	
	private static final Logger log = LoggerFactory.getLogger(ObservationLastOffsetRepositoryTest.class);
	
	@Inject
	ObservationLastOffsetRepository repository;

	@Test
	public void findBySeriesIdAndRealtimeStartAndRealtimeEndTest() {
		String seriesId = "GDPC1";
		String realtimeStart = "2018-05-10";
		String realtimeEnd = "2018-05-10";
		ObservationLastOffset lastOffsetObject = repository.findBySeriesIdAndRealtimeStartAndRealtimeEnd(seriesId, realtimeStart, realtimeEnd);
		log.info("", lastOffsetObject);
	}
	
}
