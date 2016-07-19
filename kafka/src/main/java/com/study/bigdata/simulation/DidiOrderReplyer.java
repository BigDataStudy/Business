package com.study.bigdata.simulation;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Random;

public class DidiOrderReplyer extends AbstractProducer {

	/*
	 * order_id string 订单ID 70fc7c2bd2caf386bb50f8fd5dfef0cf driver_id string
	 * 司机ID 56018323b921dd2c5444f98fb45509de passenger_id string 用户ID
	 * 238de35f44bbe8a67bdea86a5b0f4719 start_district_hash string 出发地区域哈希值
	 * d4ec2125aff74eded207d2d915ef682f dest_district_hash string 目的地区域哈希值
	 * 929ec6c160e6f52c20a4217c7978f681 Price double 价格 37.5 Time string 订单时间戳
	 * 2016-01-15 00:35:11
	 */

	// 97ebd0c6680f7c0535dbfdead6e51b4b dd65fa250fca2833a3a8c16d2cf0457c
	// ed180d7daf639d936f1aeae4f7fb482f 4725c39a5e5f4c188d382da3910b3f3f
	// 3e12208dd0be281c92a6ab57d9a6fb32 24 2016-01-01 13:37:23
	@Override
	protected Message prepareMsg() throws Exception {
		Connection conn = this.getConnection();
		try {
			conn.setAutoCommit(false);
			conn.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);

			Order order = getUnRepliedOrder(conn);
			order.setDriver_id(getAvailableDriver());

			StringBuilder builder = new StringBuilder();
			builder.append(order.getOrder_id()).append("\t");// order_id
			builder.append(order.getDriver_id()).append("\t");// driver_id
			builder.append(order.getPassenger_id()).append("\t");// passenger_id
			builder.append(order.getStart_district_hash()).append("\t");// start_district_hash
			builder.append(order.getDest_district_hash()).append("\t");// dest_district_hash

			builder.append("37.5").append("\t");
			builder.append(order.getTime());
			int ss = Calendar.getInstance().get(Calendar.SECOND);

			updateOrder(order);

			conn.commit();

			return new Message(String.valueOf(ss), builder.toString());
		} catch (Exception e) {
			try {
				conn.rollback();
			} catch (SQLException e1) {
				//
			}
			throw new Exception("Error occured when reply. " + e);
		}
	}

	private void updateOrder(Order order) throws Exception {
		java.sql.Connection conn= this.getConnection();
		PreparedStatement ps = null;
		try {
			String sql = "update order_simulation set driver_id='" + getAvailableDriver() + "' where order_id='" + order.getOrder_id() +"'";
            ps = conn.prepareStatement(sql);
            ps.executeUpdate();
          
		} catch (SQLException e) {
			throw new Exception("Update Error. " + e);
			
		} finally {
			try {
				ps.close();
			} catch (Exception e) {
				
			}
		}

	}

	private Order getUnRepliedOrder(Connection conn) throws Exception {
		PreparedStatement ps = null;
		try {
			Order order = new Order();
			ps = conn.prepareStatement("select order_id, driver_id, passenger_id, start_district_hash, dest_district_hash, Time from order_simulation where driver_id = 'NULL' limit 1 for update");
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				order.setOrder_id(rs.getString("order_id"));
				order.setPassenger_id(rs.getString("passenger_id"));
				order.setStart_district_hash(rs.getString("start_district_hash"));
				order.setDest_district_hash(rs.getString("dest_district_hash"));
				order.setTime(rs.getString("Time"));
				return order;
			}
			rs.close();
			return getUnRepliedOrder(conn);
		} catch (SQLException e) {
			throw new Exception("Acquire passenger error. " + e);
		} finally {
			try {
				ps.close();
			} catch (Exception e) {

			}
		}
	}

	private String getAvailableDriver() throws Exception {
		java.sql.Connection conn = this.getConnection();
		PreparedStatement ps = null;
		try {
			Random random = new Random();
			int pid = random.nextInt(91390) + 7422;
			ps = conn
					.prepareStatement("select * from driver where id = " + pid);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
//				System.out.println("id : " + rs.getInt(1) + " driver id : "
//								+ rs.getString(2) + " driver name : "
//								+ rs.getString(3));
				return rs.getString(2);
			}
			rs.close();
			return getAvailableDriver();
		} catch (SQLException e) {
			throw new Exception("Acquire passenger error. " + e);
		} finally {
			try {
				ps.close();
			} catch (Exception e) {

			}
		}
	}

	@Override
	protected String getTopic() {
		return "didi-order-topic";
	}

	public static void main(String[] args) throws InterruptedException {
		DidiOrderReplyer replyer = new DidiOrderReplyer();
		try {
			replyer.initialize();
			replyer.send();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}