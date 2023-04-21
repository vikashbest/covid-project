package covid.analysis.sevicetest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDate;
import java.time.Month;
import java.util.Arrays;
import java.util.IntSummaryStatistics;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

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
import covid.analysis.service.impl.CovidDataServiceImpl;

public class ServiceClassTest {
	
	private static CovidDataService covidDataService = new CovidDataServiceImpl();
	private static CovidDataDao covidDataDao = new CovidDataDaoImpl();
	private static List<CovidData> covidData = null;
	
	@BeforeAll
	static void setUp() throws CovidDataDaoException {
		covidDataService = new CovidDataServiceImpl();
		covidDataDao = new CovidDataDaoImpl();
		covidData = covidDataDao.getCovidData();
	}
	
	@AfterAll
	static void tearDown() {
		covidData = null;
		covidDataDao = null;
		covidDataService = null;
	}

	@Test
	@DisplayName("Get State Names")
	void stateNames() throws CovidDataServiceException {
		Set<String> statesName = covidDataService.getStateNames();
		assertEquals(statesName.size(), 35);
		assertTrue(statesName.containsAll(Arrays.asList("JK", "HP", "DL", "PY", "HR", "DN", "WB", "BR", "KA", "SK",
				"GA", "UP", "MH", "UT", "ML", "MN", "KL", "OR", "MP", "GJ", "CH", "MZ", "AN", "AP", "CT", "AR", "PB",
				"AS", "TG", "LA", "RJ", "TN", "TR", "NL", "JH")));
	}
	
	@Test
	@DisplayName("Get District Names By State Name")
	void districtsByStateName() throws CovidDataServiceException,InvalidStateCodeException {

		Set<String> districtNamesUnderAState = covidDataService.getDistrictNamesUnderAState("BR");
		assertTrue(districtNamesUnderAState.size() > 0);
		assertTrue(districtNamesUnderAState
				.containsAll(Arrays.asList("Patna", "Purnia", "Rohtas", "Madhubani")));

	}
	
	@Test()
	@DisplayName("If state Code is invalid throw InvalidStateCodeException")
	void districtsByStateNameException() {

		InvalidStateCodeException assertThrows2 = assertThrows(InvalidStateCodeException.class, () -> covidDataService.getDistrictNamesUnderAState("P"));

		assertEquals("Invalid State Code Please Check Your input!!", assertThrows2.getMessage());
	}
	
	@Test
	@DisplayName("Get Confirmed Cases between Given Date Range")
	void confirmedCases() throws CovidDataServiceException {

		TreeMap<LocalDate, Map<String, IntSummaryStatistics>> dataByStatesWithInDateRange = covidDataService.getDataByStatesWithInDateRange(LocalDate.of(2020, Month.JUNE, 1), LocalDate.of(2020, Month.AUGUST, 1));

		assertTrue(dataByStatesWithInDateRange.size() > 0);

		assertEquals(dataByStatesWithInDateRange.get(LocalDate.of(2020, 7, 7)).get("UP").getSum(), 1332);
	}
	
	@Test
	@DisplayName("If given date is invalid throw InvalidDateException")
	void confirmedCasesInvalidDateException()
			throws NoDataFoundException, InvalidDateException, InvalidDateRangeException {

		assertThrows(InvalidDateException.class, () -> {
			covidDataService.getDataByStatesWithInDateRange(LocalDate.of(2019, Month.AUGUST, 1),
					LocalDate.of(2022, Month.JUNE, 1));
		});
	}
	
	@Test
	@DisplayName("If given date range is invalid throw InvalidDateRangeException")
	void confirmedCasesInvalidDateRangeException()
			throws NoDataFoundException, InvalidDateException, InvalidDateRangeException {

		assertThrows(InvalidDateRangeException.class, () -> {
			covidDataService.getDataByStatesWithInDateRange(LocalDate.of(2020, Month.AUGUST, 1),
					LocalDate.of(2020, Month.JUNE, 1));
		});
	}
	
	@Test
	@DisplayName("If No data is avilable between given date range throw NoDataFoundException")
	void confirmedCasesNoDataFoundException()
			throws NoDataFoundException, InvalidDateException, InvalidDateRangeException {

		assertThrows(InvalidDateRangeException.class, () -> {
			covidDataService.getDataByStatesWithInDateRange(LocalDate.of(2020, Month.AUGUST, 1),
					LocalDate.of(2020, Month.AUGUST, 1));
		});
	}
	
	@Test
	@DisplayName("Get Confirmed Cases between Given Date Range and between two states")
	void confirmedCasesbetweenTwoStates() throws CovidDataServiceException {

		TreeMap<LocalDate, Map<String, IntSummaryStatistics>> confirmedCasesByComparingTwoStatesData = covidDataService
				.getConfirmedCasesByComparingTwoStatesData(LocalDate.of(2020, 8, 4), LocalDate.of(2020, 8, 7), "PB",
						"CH");
		assertTrue(confirmedCasesByComparingTwoStatesData.size() > 0);
		assertEquals(confirmedCasesByComparingTwoStatesData.get(LocalDate.of(2020, 8, 6)).get("PB").getSum(), 1035);
		assertEquals(confirmedCasesByComparingTwoStatesData.get(LocalDate.of(2020, 8, 6)).get("CH").getSum(), 57);
	}
	
	@Test
	@DisplayName("If given date is invalid for confirmedCasesbetweenTwoStates throw InvalidDateException")
	void confirmedCasesbetweenTwoStatesInvalidDateException() throws InvalidDateException {

		assertThrows(InvalidDateException.class, () -> {
			covidDataService.getConfirmedCasesByComparingTwoStatesData(LocalDate.of(2019, 8, 4),
					LocalDate.of(2020, 8, 7), "PB", "CH");
		});
	}
	
	@Test()
	@DisplayName("If state Code is invalid for confirmedCasesbetweenTwoStates throw InvalidStateCodeException")
	void confirmedCasesbetweenTwoStatesInvalidStateCodeException() {

		InvalidStateCodeException assertThrows2 = assertThrows(InvalidStateCodeException.class, () -> {
			covidDataService.getConfirmedCasesByComparingTwoStatesData(LocalDate.of(2020, 8, 4),
					LocalDate.of(2020, 8, 7), "P", "C");
		});

		assertEquals("Invalid State code, please check your input", assertThrows2.getMessage());
	}

	@Test
	@DisplayName("If given date range is invalid for confirmedCasesbetweenTwoStates throw InvalidDateRangeException")
	void confirmedCasesbetweenTwoStatesInvalidDateRangeException() throws InvalidDateRangeException {

		assertThrows(InvalidDateRangeException.class, () -> {
			covidDataService.getConfirmedCasesByComparingTwoStatesData(LocalDate.of(2020, 8, 8),
					LocalDate.of(2020, 8, 4), "PB", "CH");
		});
	}
	
}
