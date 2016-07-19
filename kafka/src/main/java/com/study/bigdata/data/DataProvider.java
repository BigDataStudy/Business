package com.study.bigdata.data;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.StringTokenizer;

public class DataProvider {

	public static final String driverClassName = "com.mysql.cj.jdbc.Driver";
	public static final String url = "jdbc:mysql://9.115.65.48:3306/didi?characterEncoding=utf-8";
	private static Random ran = new Random();

	public void insertDriver2DB() {
		try {
			Class.forName(driverClassName);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		Connection conn = null;
		try {
			conn = DriverManager.getConnection(url, "root", "123456");

			conn.setAutoCommit(false);
			PreparedStatement statement = conn
					.prepareStatement("INSERT INTO driver(driver_id,driver_name,driver_age,driver_city) VALUES( ?, ?, ?, ?)");
			for (int i = 0; i < 54; i++) {
				List<String> li = getIDList("E:/work/BigData/data/driverData/part-000"
						+ (i < 10 ? "0" + i : i));
				for (int j = 0; j < li.size(); j++) {
					statement.setString(1, li.get(j));
					statement.setString(2, getName());
					statement.setInt(3, 40);
					statement.setString(4, "北京");
					statement.addBatch();
				}
				// 批量执行
				statement.executeBatch();
				// Commit
				conn.commit();
			}
			statement.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void insertPassenger2DB() {
		try {
			Class.forName(driverClassName);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		Connection conn = null;
		try {
			conn = DriverManager.getConnection(url, "root", "123456");

			conn.setAutoCommit(false);
			PreparedStatement statement = conn
					.prepareStatement("INSERT INTO passenger(passenger_id,passenger_name) VALUES( ?, ?)");
			for (int i = 0; i < 54; i++) {
				List<String> li = getIDList("E:/work/BigData/data/passengerData/part-000"
						+ (i < 10 ? "0" + i : i));
				for (int j = 0; j < li.size(); j++) {
					statement.setString(1, li.get(j));
					statement.setString(2, getName());
					statement.addBatch();
				}
				statement.executeBatch();
				// Commit
				conn.commit();
			}
			statement.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void insertDistrict2DB() {
		try {
			Class.forName(driverClassName);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		Connection conn = null;
		try {
			conn = DriverManager.getConnection(url, "root", "123456");
			conn.setAutoCommit(false);
			PreparedStatement statement = conn
					.prepareStatement("INSERT INTO district(id,district_id) VALUES(?,?)");
			List<String> li = getIDList("E:/work/BigData/data/cluster_map.map");
			for (int i = 0; i < li.size(); i++) {
				if(i%2 == 0){
					statement.setInt(1, i/2 + 1);
					statement.setString(2, li.get(i));
					statement.addBatch();
				}
			}
			statement.executeBatch();
			// Commit
			conn.commit();
			statement.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public List<String> getIDList(String path) {
		List<String> idList = new ArrayList<String>();
		String str = txt2String(new File(path));
		StringTokenizer itr = new StringTokenizer(str);
		while (itr.hasMoreTokens()) {
			String istr = itr.nextToken();
			idList.add(istr);
		}
		return idList;
	}

	public String txt2String(File file) {
		String result = "";
		try {
			BufferedReader br = new BufferedReader(new FileReader(file));// ����һ��BufferedReader������ȡ�ļ�
			String s = null;
			while ((s = br.readLine()) != null) {// ʹ��readLine������һ�ζ�һ��
				result = result + "\n" + s;
			}
			br.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	public String getName() {
		List<String> surnameList = new ArrayList<String>();
		surnameList.add(0, "赵");
		surnameList.add(1, "钱");
		surnameList.add(2, "孙");
		surnameList.add(3, "李");
		surnameList.add(4, "周");
		surnameList.add(5, "吴");
		surnameList.add(6, "郑");
		surnameList.add(7, "王");
		surnameList.add(8, "黄");
		surnameList.add(9, "宋");
		surnameList.add(10, "张");
		surnameList.add(11, "陈");
		List<String> lnameList = new ArrayList<String>();
		lnameList.add(0, "强");
		lnameList.add(1, "明");
		lnameList.add(2, "辉");
		lnameList.add(3, "鹏");
		lnameList.add(4, "军");
		lnameList.add(5, "武");
		lnameList.add(6, "忠");
		lnameList.add(7, "林");
		lnameList.add(8, "睿");
		lnameList.add(9, "洋");
		lnameList.add(10, "维");
		lnameList.add(11, "飞");
		String name;
		if (ran.nextInt(2) == 0) {
			name = surnameList.get(ran.nextInt(12)).concat(
					lnameList.get(ran.nextInt(12)));
		} else {
			name = surnameList.get(ran.nextInt(12)).concat(
					lnameList.get(ran.nextInt(12)).concat(
							lnameList.get(ran.nextInt(12))));
		}
		return name;
	}

	public static void main(String[] args) {
		 DataProvider dp = new DataProvider();
		 dp.insertDistrict2DB();
		// dp.insertPassenger2DB();
		// dp.insertDriver2DB();
		// System.out.println(ran.nextInt(100000020));

	}
}
