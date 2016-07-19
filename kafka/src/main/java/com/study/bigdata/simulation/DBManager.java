package com.study.bigdata.simulation;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;
import javax.sql.DataSource;
import org.apache.tomcat.dbcp.dbcp.BasicDataSourceFactory;
/**
 * 
 * 
 */
public class DBManager {
 private static final String configFile = "dbcp.properties";
 private static DataSource dataSource;
 static {
  Properties dbProperties = new Properties();
  try {
   dbProperties.load(DBManager.class.getClassLoader()
     .getResourceAsStream(configFile));
   dataSource = BasicDataSourceFactory.createDataSource(dbProperties);
   //Connection conn = getConn();
   //DatabaseMetaData mdm = conn.getMetaData();
  
  	} catch (Exception e) {
  		
  	}
 }
 public DBManager() {
	 
	 
 }

 /**
  * 
  * @see {@link DBManager#closeConn(Connection)}
  * @return
  */
 public static final Connection getConn() {
  Connection conn = null;
  try {
   conn = dataSource.getConnection();
  } catch (SQLException e) {
  }
  return conn;
 }
 /**
  * 
  * @param conn
  */
 public static void closeConn(Connection conn) {
  try {
   if (conn != null && !conn.isClosed()) {
    conn.setAutoCommit(true);
    conn.close();
   }
  } catch (SQLException e) {
  }
 }
}