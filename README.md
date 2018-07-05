# Fred Data Sync

This tool will fetch and store data from [Federal Reserve Bank Data](https://research.stlouisfed.org/docs/api/fred/) to postgres DB. 

It works in two separate data pipelines.

1.  Batch Sync
2.  Real time Sync

**Batch Sync:** Batch sync service will sync data with specified date range and series id. It cleans up if any data is persisted within the query range. After that it will fetch data and store in postgresql. Thus this operation supports both fresh load and incremental load.

**Real time Sync:** The real time service will fetch data (with current date) in every two minutes for realtime sync. 

## Sink Database Configuration

The sink database should be postgresql. Database schema can be found in the following path:

	schema/db/schema.sql

## Build the application

To build the application without testing
	
	$ mvn clean package -DskipTests
	
The application has been written in TDD approach. Unit tests have been written using `junit`. To run unit tests [prerequisite: the database should be configured to run the test]

	$ mvn test

## Property Configuration

Properties can be configured in the following file. Configuration details are mentioned in the property file.

	overrides.properties

## Run

The following command is to run the application.

	$ java -jar target/fred-data-sync.jar --spring.config.location=overrides.properties
	
The application will automatically fetch data of current date in every two minutes (which can be configured as well). For batch sync purpose, we have to run following command

	$ curl -X PUT --header "Content-Type: application/json" --header "Accept: application/json" "http://localhost:8002/fred_data/api/sync/v1/{seriesId}/{realtime_start with yyyy-MM-dd format}/{realtime_end with yyyy-MM-dd format}/{batch etl mode}" 
	
Value of `batch etl mode` will be `daily` or `accumulated`. For `daily` mode, the service will fetch and store data in daily (iteratively) fashion. e.g, For start date 2010-01-01 to end date 2010-01-31, at first it will fetch data of 2010-01-01, then it will fetch data of 2010-01-02 and so on. 

For `accumulated` mode, the service will fetch data accumulately. e.g, For start date 2010-01-01 to end date 2010-01-31, the service will call fred API with start date 2010-01-01 and end date 2010-01-31 and fetch the accumulated records.

`mode` parameter is optional. Default value is `accumulated`.


**An example**, if we want to sync US Civilian Unemployment Rate (`UNRATE`) data from `realtime_start` Jan 01, 2000 to `realtime_end` Dec 31, 2007 we should run the following command:

	$ curl -X PUT --header "Content-Type: application/json" --header "Accept: application/json" "http://localhost:8002/fred_data/api/sync/v1/UNRATE/2000-01-01/2017-12-31/accumulated"

## Log

Application log can be found in the following path:

	logs/fred-data.log

## Swagger URL

The API is documented by swagger. After running the service, `swagger url` can be found as follows:

	http://localhost:8002/fred_data/api/swagger-ui.html

## Note 	
	
The sync process is transactional. There data will be available after any sync process is completed.
