package com.study.bigdata.db;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

import javax.sql.DataSource;

import org.apache.log4j.Logger;

import com.alibaba.druid.pool.DruidDataSourceFactory;

public class DruidDataSourceManager {
	private Logger log = Logger.getLogger(this.getClass().getSimpleName());

	private static DruidDataSourceManager INSTANCE;

	private DataSource datasource;

	private DruidDataSourceManager() {
		Properties dbProperties = new Properties();
		try {
			dbProperties.load(DruidDataSourceManager.class.getClassLoader()
					.getResourceAsStream("ds.properties"));
			datasource = DruidDataSourceFactory.createDataSource(dbProperties);
		} catch (Exception e) {
			e.printStackTrace();
			log.error("initialize druid data source error", e);
		}

	}

	public static DruidDataSourceManager getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new DruidDataSourceManager();
		}
		return INSTANCE;

	}

	public Connection getConnection() throws SQLException {
		return datasource.getConnection();
	}

	
}
