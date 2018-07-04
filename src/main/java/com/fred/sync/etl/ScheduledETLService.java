package com.fred.sync.etl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class ScheduledETLService {

	private static final Logger log = LoggerFactory.getLogger(ScheduledETLService.class);
	private BaseETLService etl;
	private List<String> seriesIds;

	@Autowired
	public ScheduledETLService(BaseETLService etl, @Value("${series.ids}") String seriesIds) {
		this.etl = etl;
		String[] seriesArray = seriesIds.split(",");
		this.seriesIds = new ArrayList<String>(seriesArray.length);
		for (String series : seriesArray) {
			this.seriesIds.add(series);
		}
	}

	@Scheduled(cron = "${scheduled.etl.cron}")
	public void run() {
		log.info("Starting scheduling job");
		String currentDate = DateFormatUtils.formatUTC(System.currentTimeMillis(), "yyyy-MM-dd");
		for (String seriesId : seriesIds) {
			etl.sync(seriesId, currentDate, currentDate);
		}
		log.info("Scheduling job has been ended");
	}

}
