package com.fred.sync.etl;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.fred.sync.common.AppDateUtils;
import com.fred.sync.pull.series.SeriesObservationPullService;
import com.fred.sync.pull.series.SeriesObservationResponsePOJO;
import com.fred.sync.repositories.ObservationLastOffsetRepository;
import com.fred.sync.store.SeriesObservationStoreService;
import com.fred.sync.web.domain.db.ObservationLastOffset;

@Service
public class BaseETLService {

	private static final Logger log = LoggerFactory.getLogger(BaseETLService.class);
	private static final String FILE_TYPE = "json";

	private SeriesObservationPullService pullService;
	private SeriesObservationStoreService storeService;
	private ObservationLastOffsetRepository lastOffsetRepository;
	private Long batchSize;

	@Autowired
	public BaseETLService(SeriesObservationPullService pullService, SeriesObservationStoreService storeService,
			ObservationLastOffsetRepository lastOffsetRepository, @Value("${etl.batch.size}") Long batchSize) {
		this.pullService = pullService;
		this.storeService = storeService;
		this.batchSize = batchSize;
		this.lastOffsetRepository = lastOffsetRepository;
	}

	public Long etl(String seriesId, String realtimeStart, String realtimeEnd) {
		try {
			Long offset = getLastOffset(seriesId, realtimeStart, realtimeEnd);
			log.info("ETL Job is starting for series {}, realtime_start {}, realtime_end {}, offset {}, limit {}",
					seriesId, realtimeStart, realtimeEnd, offset, batchSize);
			SeriesObservationResponsePOJO series = pullService.getSeries(seriesId, realtimeStart, realtimeEnd, offset,
					batchSize, FILE_TYPE);
			if (series == null || series.observations == null) {
				log.info("No observation is found for series {}, realtime_start {}, realtime_end {}", seriesId,
						realtimeStart, realtimeEnd);
				return 0l;
			}

			if (series.observations.isEmpty()) {
				log.info("No more observation is found for series {}, realtime_start {}, realtime_end {}", seriesId,
						realtimeStart, realtimeEnd);
				return 0l;
			}
			log.info("Fetched record count {}", series.observations.size());

			storeService.storeObservation(seriesId, series.observations);
			log.info("Stored {} records of series {}", series.observations.size(), seriesId);
			updateLastOffset(seriesId, realtimeStart, realtimeEnd, (long) series.observations.size());
			return (long) series.observations.size();
		} catch (Exception e) {
			log.error("Exception in etl: series {}, realtime_start {}, realtime_end {}", seriesId, realtimeStart,
					realtimeEnd, e);
		}
		return 0l;
	}

	public Long sync(String seriesId, String realtimeStart, String realtimeEnd) {
		try {
			Long observationSize = -1l;
			Long recordCount = 0l;
			while (observationSize != 0) {
				observationSize = etl(seriesId, realtimeStart, realtimeEnd);
				recordCount += observationSize;
			}
			log.info("Sync process has been completed");
			return recordCount;
		} catch (Exception e) {
			log.error("Exception in sync: series {}, realtime_start {}, realtime_end {}", seriesId, realtimeStart,
					realtimeEnd, e);
		}
		return 0l;
	}

	@Transactional
	public Long dateRangeSync(String seriesId, String realtimeStart, String realtimeEnd) {
		try {
			cleanup(seriesId, realtimeStart, realtimeEnd);
			LocalDate start = AppDateUtils.getLocalDate(realtimeStart);
			LocalDate end = AppDateUtils.getLocalDate(realtimeEnd);

			Long recordCount = 0l;
			for (LocalDate date = start; date.isBefore(end) || date.isEqual(end); date = date.plusDays(1)) {
				String dateString = date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
				recordCount += sync(seriesId, dateString, dateString);
			}
			return recordCount;
		} catch (Exception e) {
			log.error("Exception in dateRangeETL: series {}, realtime_start {}, realtime_end {}", seriesId,
					realtimeStart, realtimeEnd, e);
		}
		return 0l;
	}
	
	public void cleanup(String seriesId, String realtimeStart, String realtimeEnd) throws ParseException {
		log.info("Removing records of series {}, realtime_start {}, realtime_end {}", seriesId, realtimeStart,
				realtimeEnd);
		storeService.removeObservation(seriesId, realtimeStart, realtimeEnd);
		lastOffsetRepository.deleteBySeriesIdAndRealtimeStartAndRealtimeEnd(seriesId, realtimeStart, realtimeEnd);
		log.info("Records have been removed");
	}

	public Long getLastOffset(String seriesId, String realtimeStart, String realtimeEnd) {
		Long lastOffset = 0l;
		ObservationLastOffset lastOffsetObservation = lastOffsetRepository
				.findBySeriesIdAndRealtimeStartAndRealtimeEnd(seriesId, realtimeStart, realtimeEnd);
		if (lastOffsetObservation != null) {
			return lastOffsetObservation.lastOffset;
		}
		return lastOffset;
	}

	public void updateLastOffset(String seriesId, String realtimeStart, String realtimeEnd, Long count) {
		ObservationLastOffset lastOffsetObservation = lastOffsetRepository
				.findBySeriesIdAndRealtimeStartAndRealtimeEnd(seriesId, realtimeStart, realtimeEnd);
		if (lastOffsetObservation != null) {
			lastOffsetObservation.lastOffset += count;
			lastOffsetRepository.save(lastOffsetObservation);
		} else
			lastOffsetRepository.save(new ObservationLastOffset(seriesId, realtimeStart, realtimeEnd, count));
	}
}
