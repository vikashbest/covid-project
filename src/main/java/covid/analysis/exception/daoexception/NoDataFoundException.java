package covid.analysis.exception.daoexception;

import covid.analysis.exception.serviceexception.CovidDataServiceException;

public class NoDataFoundException extends CovidDataServiceException{

	public NoDataFoundException() {
		super();
		// TODO Auto-generated constructor stub
	}

	public NoDataFoundException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		// TODO Auto-generated constructor stub
	}

	public NoDataFoundException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	public NoDataFoundException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	public NoDataFoundException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}

}
