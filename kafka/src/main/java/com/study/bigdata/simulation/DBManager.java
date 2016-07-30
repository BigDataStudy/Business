/*package com.study.bigdata.simulation;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSourceFactory;

*//**
 * 
 * 
 *//*
public class DBManager {
	private static final String configFile = "dbcp.properties";
	private static DataSource dataSource;
	
	static {
		Properties dbProperties = new Properties();
		try {
			dbProperties.load(DBManager.class.getClassLoader()
					.getResourceAsStream(configFile));
			dataSource = BasicDataSourceFactory.createDataSource(dbProperties);

		} catch (Exception e) {
			e.printStackTrace();

		}
	}

	public DBManager() {
		System.out.println("Data source connection initiated!");
	}

	*//**
	 * 
	 * @see {@link DBManager#closeConn(Connection)}
	 * @return
	 * @throws SQLException 
	 *//*
	public static final Connection getConn() throws SQLException {
		return dataSource.getConnection();
	}

	*//**
	 * 
	 * @param conn
	 * @throws SQLException 
	 *//*
	public static void closeConn(Connection conn) throws SQLException {
		if (conn != null && !conn.isClosed()) {
			conn.setAutoCommit(true);
			conn.close();
		}
	}
}*/