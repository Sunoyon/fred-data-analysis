package com.fred.sync.etl;

import java.text.ParseException;

import javax.inject.Inject;

import org.junit.Ignore;
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
public class BaseETLServiceTest {

	private static final Logger log = LoggerFactory.getLogger(BaseETLServiceTest.class);
	@Inject
	BaseETLService etl;
	
	@Test
	public void getLastOffset() throws ParseException {
		String seriesId = "GDPC1";
		String realtimeStart = "2018-07-03";
		String realtimeEnd = "2018-07-03";
		
		Long lastOffset = etl.getLastOffset(seriesId, realtimeStart, realtimeEnd);
		log.info("Last offset: {}", lastOffset);
		
	}
	
	@Test
	@Ignore
	public void dateRangeETLTest() {
		String seriesId = "UMCSENT";
		String realtimeStart = "2018-06-01";
		String realtimeEnd = "2018-06-03";
		Long dateRangeETL = etl.dateRangeSyncInDailyMode(seriesId, realtimeStart, realtimeEnd);
		log.info("{} records have been stored", dateRangeETL);
	}
}
