package com.fred.sync.store;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fred.sync.common.AppDateUtils;
import com.fred.sync.pull.series.SeriesObservationPOJO;
import com.fred.sync.repositories.FredSeriesRepository;
import com.fred.sync.web.domain.db.FredSeries;

@Service
public class SeriesObservationStoreService {

	private FredSeriesRepository observationRepository;

	@Autowired
	public SeriesObservationStoreService(FredSeriesRepository repository) {
		observationRepository = repository;
	}

	public void storeObservation(String seriesId, List<SeriesObservationPOJO> observations) throws ParseException {
		List<FredSeries> seriesObservations = new ArrayList<FredSeries>();
		for (SeriesObservationPOJO observation : observations) {
			Double value = 0.0;
			if (!".".equals(observation.value)) value = Double.parseDouble(observation.value);
			seriesObservations.add(new FredSeries(seriesId, AppDateUtils.parseDate(observation.realtimeStart), AppDateUtils.parseDate(observation.realtimeEnd), AppDateUtils.parseDate(observation.date), value));
		}
		observationRepository.save(seriesObservations);
	}
	
	public void removeObservation(String seriesId, String realtimeStart, String realtimeEnd) throws ParseException {
		observationRepository.deleteBySeriesIdAndRealtimeStartGreaterThanEqualAndRealtimeEndLesserThanEqual(seriesId, AppDateUtils.parseDate(realtimeStart), AppDateUtils.parseDate(realtimeEnd));
	}
}
