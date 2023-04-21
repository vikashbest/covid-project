package covid.analysis.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import covid.analysis.exception.daoexception.DatabaseConnectionFailedException;

/**
 * @author Alok Kumar
 * 
 * This is the Utility Class of Application which helps to manage connection between this 
 * Java Application and MySQLServer. 
 *
 */
public class DBUtil {
	private static final String URL = "jdbc:mysql://localhost:3306/covid_analysis";
	private static final String USER_NAME = "root";
	private static final String PASSWORD = "root";

	//Method to create connection between Java Application and MySQLServer.
	/**
	 * @return
	 * @throws DatabaseConnectionFailedException
	 */
	public Connection getMyConnection() throws DatabaseConnectionFailedException {
		Connection con = null;
		try {
			con = DriverManager.getConnection(URL, USER_NAME, PASSWORD);
		} catch (SQLException e) {
			throw new DatabaseConnectionFailedException("Database could not be connected",e);
		}
		return con;
	}

	/**
	 * @param con
	 */
	public void closeResource(Connection con) {
		if (con != null) {
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * @param st
	 */
	public void closeResource(Statement st) {
		if (st != null) {
			try {
				st.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * @param st
	 */
	public void closeResource(PreparedStatement st) {
		if (st != null) {
			try {
				st.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * @param st
	 */
	public void closeResource(ResultSet st) {
		if (st != null) {
			try {
				st.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}
