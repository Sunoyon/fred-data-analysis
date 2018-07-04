package com.fred.sync.repositories;

import java.sql.Date;
import java.text.ParseException;
import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;

import com.fred.sync.App;
import com.fred.sync.common.AppDateUtils;
import com.fred.sync.web.domain.db.FredSeries;

@SpringBootTest
@RunWith(SpringRunner.class)
@Import(App.class)
@Ignore
public class FredSeriesRepositoryTest {

	@Inject
	FredSeriesRepository fredSeriesRepository;
	
	@Test
	public void saveAllTest() throws ParseException {
		Date date = AppDateUtils.parseDate("2018-07-03");
		List<FredSeries> serieses = Arrays.asList(new FredSeries("GDPC1", date, date, date, 4.5), new FredSeries("GDPC1", date, date, date, 6.5));
		fredSeriesRepository.save(serieses);
	}

	@Test
	public void deleteBySeriesIdAndrealtimeStartAndrealtimeEndTest() throws ParseException {
		String seriesId = "GDPC1";
		Date realtimeStart = AppDateUtils.parseDate("2018-07-03");
		Date realtimeEnd = AppDateUtils.parseDate("2018-07-03");
		fredSeriesRepository.deleteBySeriesIdAndRealtimeStartGreaterThanEqualAndRealtimeEndLesserThanEqual(seriesId, realtimeStart, realtimeEnd);
	}
}
