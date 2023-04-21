package covid.analysis.exception.serviceexception;

import covid.analysis.exception.CovidDataException;

/**
 * @author Alok Kumar
 * 
 * This is Application's Service Layer Level Exception Class. 
 */
public class CovidDataServiceException extends CovidDataException{

	public CovidDataServiceException() {
		super();
		// TODO Auto-generated constructor stub
	}

	public CovidDataServiceException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		// TODO Auto-generated constructor stub
	}

	public CovidDataServiceException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	public CovidDataServiceException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	public CovidDataServiceException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}

}
