package covid.analysis.service;

import java.time.LocalDate;
import java.util.IntSummaryStatistics;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import covid.analysis.exception.serviceexception.CovidDataServiceException;
import covid.analysis.exception.serviceexception.InvalidDateException;
import covid.analysis.exception.serviceexception.InvalidDateRangeException;
import covid.analysis.exception.serviceexception.InvalidStateCodeException;

/**
 * @author Alok Kumar
 * This interface interacts with the Service Layer of the application.
 *
 */
public interface CovidDataService {
	/**
	 * @return
	 * @throws CovidDataServiceException
	 */
	public Set<String> getStateNames() throws CovidDataServiceException;
	
	/**
	 * @param stateName
	 * @return
	 * @throws InvalidStateCodeException
	 * @throws CovidDataServiceException
	 */
	public Set<String> getDistrictNamesUnderAState(String stateName) throws InvalidStateCodeException, CovidDataServiceException;
	
	/**
	 * @param startDate
	 * @param endDate
	 * @return
	 * @throws CovidDataServiceException
	 */
	public TreeMap<LocalDate, Map<String, IntSummaryStatistics>> getDataByStatesWithInDateRange(LocalDate startDate,
			LocalDate endDate) throws CovidDataServiceException;
	
	/**
	 * @param startDate
	 * @param endDate
	 * @param fState
	 * @param sState
	 * @return
	 * @throws CovidDataServiceException
	 * @throws InvalidDateException
	 * @throws InvalidDateRangeException
	 */
	public TreeMap<LocalDate, Map<String, IntSummaryStatistics>> getConfirmedCasesByComparingTwoStatesData(
			LocalDate startDate, LocalDate endDate, String fState, String sState) throws CovidDataServiceException, InvalidDateException, InvalidDateRangeException;

}
