package covid.analysis.exception.daoexception;

import covid.analysis.exception.CovidDataException;

/**
 * @author Alok Kumar
 *
 * This is Application's DAO Layer Level Exception Class.
 */
public class CovidDataDaoException extends CovidDataException{

	public CovidDataDaoException() {
		super();
		// TODO Auto-generated constructor stub
	}

	public CovidDataDaoException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		// TODO Auto-generated constructor stub
	}

	public CovidDataDaoException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	public CovidDataDaoException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	public CovidDataDaoException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}

}
