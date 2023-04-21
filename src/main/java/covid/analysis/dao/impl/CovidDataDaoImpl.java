package covid.analysis.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import covid.analysis.dao.CovidDataDao;
import covid.analysis.entity.CovidData;
import covid.analysis.exception.daoexception.CovidDataDaoException;
import covid.analysis.util.DBUtil;

public class CovidDataDaoImpl implements CovidDataDao{

	private DBUtil dbUtil = new DBUtil();
	
//Method to fetch Covid Data from Database.	
	@Override
	public List<CovidData> getCovidData() throws CovidDataDaoException {
		Connection con = null;	
		PreparedStatement statement = null;
		ResultSet result = null;
		String sql = "Select * from covid_data; ";
		List<CovidData> covidData = new ArrayList<CovidData>();
		try {
			con = dbUtil.getMyConnection();
			statement = con.prepareStatement(sql);
			result = statement.executeQuery();

			while (result.next()) {
				CovidData covid = new CovidData();
				covid.setId(result.getInt("id"));
				covid.setState(result.getString("state"));
				covid.setDistrict(result.getString("district"));
				covid.setConfirmed(result.getInt("confirmed"));
				covid.setTested(result.getInt("tested"));
				covid.setRecovered(result.getInt("recovered"));
				covid.setDate(LocalDate.parse(result.getDate("date").toString()));

				covidData.add(covid);

			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (con != null)
					dbUtil.closeResource(con);
				if (statement != null)
					statement.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return covidData;
		
	}



}
