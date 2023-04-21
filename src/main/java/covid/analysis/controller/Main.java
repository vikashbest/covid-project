package covid.analysis.controller;

import java.time.LocalDate;
import java.util.IntSummaryStatistics;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeMap;

import covid.analysis.exception.CovidDataException;
import covid.analysis.exception.serviceexception.CovidDataServiceException;
import covid.analysis.service.CovidDataService;
import covid.analysis.service.impl.CovidDataServiceImpl;


/**
 * @author Alok Kumar
 * This is the Main Driver Class of the Covid Analysis System Application.
 *
 */
public class Main {

	private static final Scanner scanner = new Scanner(System.in);
	private static final CovidDataService service = new CovidDataServiceImpl();
	
	public static void main(String[] args) {
		boolean isRunning = true;
		do 
		{
			displayChoice();
			int choice = validateChoice();
			
			switch (choice) {
			case 1:
//				Calling GetStateMethod to Display all States
				GetStateNames();
				break;
			case 2:
				System.out.print("Please enter state code : ");
				String state = scanner.next().toUpperCase();
//				Calling getDistrictName method to display districts for a given specific State.
				GetDistrictName(state);
				break;
			case 3:
				System.out.print("Please enter start date (yyyy-MM-dd) : ");
				LocalDate startDate = LocalDate.parse(scanner.next());
				System.out.print("Please enter end date (yyyy-MM-dd) : ");
				LocalDate endDate = LocalDate.parse(scanner.next());
//				Calling displyDataByStateWithInDateRange to display total Confirmed cases of any all states comes
//				under given date range.
				displyDataByStateWithInDateRange(startDate, endDate);
				break;
			case 4:
				System.out.print("Please enter start date (yyyy-MM-dd) : ");
				LocalDate sDate = LocalDate.parse(scanner.next());
				System.out.print("Please enter end date (yyyy-MM-dd) : ");
				LocalDate eDate = LocalDate.parse(scanner.next());
				System.out.print("Please Enter First State code : ");
				String firstSc = scanner.next().toUpperCase();
				System.out.print("Please Enter Second State code : ");
				String secondSc = scanner.next().toUpperCase();
//				Calling confirmedCasesByComparingTwoStates to display total number of Confirmed cases between two states within date range.
				confirmedCasesByComparingTwoStates(sDate, eDate, firstSc, secondSc);
				break;
			case 5:
				isRunning = false;
				System.out.println("Exit");
				break;
			default:
				System.out.println("Please choose appropriate option given between 1 to 5.");

			}
			
		}while(isRunning);

	}

	

	/**
	 * @param startDate
	 * @param endDate
	 * @param fState
	 * @param sState
	 */
	public static void confirmedCasesByComparingTwoStates(LocalDate startDate, LocalDate endDate, String fState,
			String sState){
		TreeMap<LocalDate, Map<String, IntSummaryStatistics>> comparingTwoStatesData = null;
		try {
			comparingTwoStatesData = service.getConfirmedCasesByComparingTwoStatesData(startDate, endDate, fState, sState);
			System.out.println(
					"DATE       |      FIRST STATE     |     FIRST STATE CONFIRMED TOTAL     |     SECOND STATE     |    SECOND STATE CONFIRMED TOTAL	");
			for (Entry<LocalDate, Map<String, IntSummaryStatistics>> entry : comparingTwoStatesData.entrySet()) {

				System.out.println(entry.getKey() + " |          " + fState + "          |                 "
						+ entry.getValue().get(fState).getSum() + "                 |          " + sState
						+ "          |               " + entry.getValue().get(sState).getSum());
			}
		} catch (CovidDataServiceException e) {
			System.out.println(e.getLocalizedMessage());
		}catch(CovidDataException ex) {
			System.out.println(ex.getLocalizedMessage());
		}catch(Exception exc) {
			System.out.println(exc.getLocalizedMessage());
		}
		
	}



	/**
	 * @param startDate
	 * @param endDate
	 */
	private static void displyDataByStateWithInDateRange(LocalDate startDate, LocalDate endDate) {
		TreeMap<LocalDate, Map<String, IntSummaryStatistics>> withInDateRange = null;
		
		try {
			withInDateRange = service
					.getDataByStatesWithInDateRange(startDate, endDate);
			System.out.println("      Date| State| Confirmed total");
			for (Entry<LocalDate, Map<String, IntSummaryStatistics>> entry : withInDateRange.entrySet()) {
				entry.getValue().forEach((k, v) -> {
					System.out.println(entry.getKey() + "|    " + k + "|       " + v.getSum());
				});
			}
		} catch (CovidDataServiceException e) {
			System.out.println(e.getLocalizedMessage());
		}catch(CovidDataException ex) {
			System.out.println(ex.getLocalizedMessage());
		}catch(Exception exc) {
			System.out.println(exc.getLocalizedMessage());
		}
		
		
	}

	/**
	 * @param state
	 */
	private static void GetDistrictName(String state) {
		Set<String> districtNamesUnderAState = null;
		try {
			districtNamesUnderAState = service.getDistrictNamesUnderAState(state);
			districtNamesUnderAState.stream().sorted((s1, s2) -> s1.compareTo(s2)).forEach(System.out::println);
		} catch (CovidDataServiceException e) {
			System.out.println(e.getLocalizedMessage());
		}catch(CovidDataException ex) {
			System.out.println(ex.getLocalizedMessage());
		}catch(Exception exc) {
			System.out.println(exc.getLocalizedMessage());
		}
		
		
	}


	private static void GetStateNames() {
		Set<String> stateNames = null;
		try {
			stateNames = service.getStateNames();
			stateNames.forEach(System.out::println);
		} catch (CovidDataServiceException e) {
			System.out.println(e.getLocalizedMessage());
		}
		
	}


	private static void displayChoice() {
		System.out.println("*******************************");
		System.out.println("1. Get State Names");
		System.out.println("2. Get District for given state");
		System.out.println("3. Display Data by State within Date Range");
		System.out.println("4. Display Confirmed Cases by Comparing two States for a Given Date Range");
		System.out.println("5. Exit");
		System.out.print("Please select option : ");
	}

	private static int validateChoice() {
		boolean isInvalidChoice = true;
		int choice=0;
		while(isInvalidChoice)
		{
			if(scanner.hasNextInt())
			{
				choice = scanner.nextInt();
				isInvalidChoice = false;

			}
			else
			{
				System.out.print("Choice must be only digit! Enter choice again: ");
				scanner.next();
				isInvalidChoice = true;
			}

		}
		return choice;
	}

}
