package covid.analysis.service.impl;

import java.time.LocalDate;
import java.util.IntSummaryStatistics;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import covid.analysis.dao.CovidDataDao;
import covid.analysis.dao.impl.CovidDataDaoImpl;
import covid.analysis.entity.CovidData;
import covid.analysis.exception.daoexception.CovidDataDaoException;
import covid.analysis.exception.daoexception.NoDataFoundException;
import covid.analysis.exception.serviceexception.CovidDataServiceException;
import covid.analysis.exception.serviceexception.InvalidDateException;
import covid.analysis.exception.serviceexception.InvalidDateRangeException;
import covid.analysis.exception.serviceexception.InvalidStateCodeException;
import covid.analysis.service.CovidDataService;


/**
 * @author Alok Kumar
 * 
 * This is the implementation class of Service Layer of the Application.
 */
public class CovidDataServiceImpl implements CovidDataService{
	
	private static CovidDataDao covidDataDao = new CovidDataDaoImpl();
	private List<CovidData> covidData;

	public Set<String> getStateNames() throws CovidDataServiceException {
		Map<String, List<CovidData>> filteredList = null;

//		retrieving all states from covidData List then apply grouping to get distinct states
		try {
			covidData = covidDataDao.getCovidData();
			filteredList = covidData.stream().collect(Collectors.groupingBy(CovidData::getState));
		} catch (CovidDataDaoException e) {
			throw new CovidDataServiceException("Something is wrong in DB!",e);
		}
		
		return filteredList.keySet();
	}

	@Override
	public Set<String> getDistrictNamesUnderAState(String stateName) throws CovidDataServiceException{
		Predicate<CovidData> predicate = cd -> cd.getState().equals(stateName);

//		checking whether the given state code exist in out database or not
		try {
			covidData = covidDataDao.getCovidData();
		} catch (CovidDataDaoException e) {
			throw new CovidDataServiceException("Something is wrong in DB!",e);
		}
	boolean anyMatch = covidData.stream().anyMatch(predicate);

	Set<String> collect = null;
	if (!anyMatch) {
		throw new InvalidStateCodeException("Invalid State Code Please Check Your input!!");
	}
//			retrieving district data which is equal to given state 
	collect = this.covidData.stream().filter(predicate).map(cv -> cv.getDistrict()).collect(Collectors.toSet());

	return collect;
	
	}

	@Override
	public TreeMap<LocalDate, Map<String, IntSummaryStatistics>> getDataByStatesWithInDateRange(LocalDate startDate,
			LocalDate endDate) throws CovidDataServiceException,InvalidDateException, InvalidDateRangeException,NoDataFoundException {
		
		try {
			covidData = covidDataDao.getCovidData();
		} catch (CovidDataDaoException e) {
			throw new CovidDataServiceException("Something is wrong in DB!",e);
		}		
		Map<LocalDate, Map<String, IntSummaryStatistics>> collectedData = null;
		
//		condition to check end date is not before start date
		if (!startDate.isBefore(endDate)) {
			throw new InvalidDateRangeException("Invalid Date Range, check Your Input.");
		}			//		condition's to check whether given date is valid or not
		else if(!this.covidData.stream().anyMatch(cd -> cd.getDate().isEqual(startDate))) {
			throw new InvalidDateException("Invalid Start Date Please Check your input.");
		}
		else if (!this.covidData.stream().anyMatch(cd -> cd.getDate().isEqual(endDate))) {
			throw new InvalidDateException("Invalid End Date Please Check your input.");
		}
		else {
//			Predicate to filter Covid data between given date range
		Predicate<CovidData> predicate = cd -> cd.getDate().isAfter(startDate) && cd.getDate().isBefore(endDate);
		
		List<CovidData> collect = this.covidData.stream().filter(predicate).collect(Collectors.toList());

//			condition to check if list is empty then throw "No data Present Exception"
		if (collect.isEmpty()) {
			throw new NoDataFoundException("No Data Present.");
		} else {
//			grouping the list of elements on the basis of date and state then summing the total number of confirmed cases
			collectedData = collect.stream().collect(Collectors.groupingBy(CovidData::getDate, Collectors
					.groupingBy(CovidData::getState, Collectors.summarizingInt(CovidData::getConfirmed))));
		}

	}
//	storing HashMap elements into TreeMap to get the sorted Map
	return new TreeMap<LocalDate, Map<String, IntSummaryStatistics>>(collectedData);
	}

	@Override
	public TreeMap<LocalDate, Map<String, IntSummaryStatistics>> getConfirmedCasesByComparingTwoStatesData(
			LocalDate startDate, LocalDate endDate, String fState, String sState) throws CovidDataServiceException {
		
		Map<LocalDate, Map<String, IntSummaryStatistics>> collectedData = null;
		try {
			covidData = covidDataDao.getCovidData();
		} catch (CovidDataDaoException e) {
			throw new CovidDataServiceException("Something is wrong in DB!",e);
		}
		
//		condition's to check whether given date is valid or not
		if (!this.covidData.stream().anyMatch(cd -> cd.getDate().isEqual(startDate))) {

			throw new InvalidDateException("Invalid Start Date Please Check your input.");
		}

		else if (!this.covidData.stream().anyMatch(cd -> cd.getDate().isEqual(endDate))) {
			throw new InvalidDateException("Invalid End Date Please Check your input.");
		}
//		condition to check end date is not before start date
		else if (!startDate.isBefore(endDate)) {
			throw new InvalidDateRangeException("Invalid Date Range, check Your Input.");
		}

		else {
//				condition to check state equals or not
			Predicate<CovidData> predicate = cd -> cd.getState().equals(fState);
			Predicate<CovidData> predicate1 = cd -> cd.getState().equals(sState);
//							checking whether the given state code exist in out database or not
			boolean fStateMatch = this.covidData.stream().anyMatch(predicate);
			boolean sStateMatch = this.covidData.stream().anyMatch(predicate1);

			if (!fStateMatch) {
				throw new InvalidStateCodeException("Invalid State code, please check your input");
			} else if (!sStateMatch) {
				throw new InvalidStateCodeException("Invalid State code, please check your input");
			}
//						predicate condition to filter data between given date range
			Predicate<CovidData> datePredicate = cd -> cd.getDate().isAfter(startDate)
					&& cd.getDate().isBefore(endDate);

//						predicate condition to filter data which matches given two state's
			Predicate<CovidData> statePredicate = cd -> cd.getState().equals(fState) || cd.getState().equals(sState);

//						applying filters on list of Covid data to retrieve a collection of data between given date range and given state
			List<CovidData> collect = this.covidData.stream().filter(datePredicate.and(statePredicate))
					.collect(Collectors.toList());

//						condition to check if list is empty then throw "No data Present Exception"
			if (collect.isEmpty()) {
				throw new NoDataFoundException("No Data Present.");
			} else {
//						grouping the list of elements on the basis of date and state then summing the total number of confirmed cases
				collectedData = collect.stream().collect(Collectors.groupingBy(CovidData::getDate, Collectors
						.groupingBy(CovidData::getState, Collectors.summarizingInt(CovidData::getConfirmed))));

			}

		}
//			Storing HashMap elements into TreeMap to get the sorted Map
		return new TreeMap<LocalDate, Map<String, IntSummaryStatistics>>(collectedData);
	}

}
