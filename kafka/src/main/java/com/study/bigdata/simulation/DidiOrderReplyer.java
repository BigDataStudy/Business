package com.study.bigdata.simulation;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Calendar;

import com.study.bigdata.simulation.dao.SimulationDao;
import com.utils.activemq.ActiveMQManager;
import com.utils.db.DruidDataSourceManager;
import com.utils.redis.JedisUtil;

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
		Order order = insertReply(getUnRepliedOrder_redisImpl());
		return null != order ? new Message(String.valueOf(Calendar.getInstance().get(Calendar.SECOND)), order.toString()) : null;
	}
	
	private Order updateReply(Order order) throws Exception{
		SimulationDao dao = new SimulationDao();
		
		Connection conn = DruidDataSourceManager.getInstance().getConnection();
		
		try {
			order.setDriver_id(dao.getAvailableDriver(conn));
			int result = dao.updateOrder(conn, order);
			if ( 1== result) {
				return order;
			} 
			throw new Exception("failed to update order.");
		} finally {
			dao.closeConn(conn);
		}
	}
	
	private Order insertReply(Order order) throws Exception{
		SimulationDao dao = new SimulationDao();
		
		Connection conn = DruidDataSourceManager.getInstance().getConnection();
		
		try {
			order.setDriver_id(dao.getAvailableDriver(conn));
			order.setPrice(new Double(35));
			int result = dao.insertOrder(conn, order);
			if ( 1== result) {
				return order;
			} 
			throw new Exception("failed to insert order.");
		} finally {
			dao.closeConn(conn);
		}
	}
	
	private Order updateReplyWithTransaction() throws Exception{
		SimulationDao dao = new SimulationDao();
		
		int result = 0;
		
		Order order=null;
		Connection conn = DruidDataSourceManager.getInstance().getConnection();
		conn.setAutoCommit(false);
		conn.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
		
		try {
			order = dao.getUnRepliedOrder(conn);
			order.setDriver_id(dao.getAvailableDriver(conn));
			
			
			result = dao.updateOrder(conn, order);
			if ( result == 1 ) {
				conn.commit();
				return order;
			} 
			throw new Exception("failed to update order.");
		} catch (Exception e) {
			try {
				conn.rollback();
			} catch (SQLException e1) {
				throw new Exception("failed to rollback when update order", e1);
			}
			throw new Exception("failed to update order", e);
		} finally {
			dao.closeConn(conn);
		}
	}
	
	private Order getUnRepliedOrder_activeMQImpl() throws Exception {
		String message = ActiveMQManager.getInstance().ReceiveMessage();
		if ( null != message ) {
			Order order = new Order();
			order.setOrder_id(message);
			return order;
		}
		throw new Exception("activeMQ order queue is empty...");
	}
	
	private Order getUnRepliedOrder_redisImpl() throws Exception {
		String message = JedisUtil.rpop("didi-order");
		if ( null != message ) {
			Order order = new Order();
			order.buildFromString(message);
			return order;
		}
		throw new Exception("redis order queue is empty...");
	}


	@Override
	protected String getTopic() {
		return "didi-order-topic";
	}

}