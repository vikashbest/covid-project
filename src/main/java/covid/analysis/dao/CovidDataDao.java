package covid.analysis.dao;

import java.util.List;


import covid.analysis.entity.CovidData;
import covid.analysis.exception.daoexception.CovidDataDaoException;

/**
 * @author Alok Kumar
 * 
 * This Layer interacts with the DAO Layer of the Application.
 *
 */
public interface CovidDataDao {

	/**
	 * @return
	 * @throws CovidDataDaoException
	 */
	List<CovidData> getCovidData() throws CovidDataDaoException;

}
