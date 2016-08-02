package com.study.bigdata.simulation;

import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

import com.study.bigdata.simulation.dao.SimulationDao;
import com.utils.db.DruidDataSourceManager;
import com.utils.redis.JedisUtil;

public class DidiOrderRequestor extends AbstractProducer {

	/*
	 * order_id string 订单ID 70fc7c2bd2caf386bb50f8fd5dfef0cf driver_id string
	 * 司机ID 56018323b921dd2c5444f98fb45509de passenger_id string 用户ID
	 * 238de35f44bbe8a67bdea86a5b0f4719 start_district_hash string
	 * 出发地区域哈希�1�7�1�7 d4ec2125aff74eded207d2d915ef682f dest_district_hash
	 * string 目的地区域哈希�1�7�1�7 929ec6c160e6f52c20a4217c7978f681 Price double 价格
	 * 37.5 Time string 订单时间 2016-01-15 00:35:11
	 */

	// 97ebd0c6680f7c0535dbfdead6e51b4b dd65fa250fca2833a3a8c16d2cf0457c
	// ed180d7daf639d936f1aeae4f7fb482f 4725c39a5e5f4c188d382da3910b3f3f
	// 3e12208dd0be281c92a6ab57d9a6fb32 24 2016-01-01 13:37:23
	@Override
	protected Message prepareMsg() throws Exception {
		Connection conn = null;
		SimulationDao dao = new SimulationDao();
		try {
			conn = DruidDataSourceManager.getInstance().getConnection();
			String passenger = dao.getAvailablePassenger(conn);

			Order order = new Order();
			order.setOrder_id(UUID.randomUUID().toString().replace("-", ""));
			order.setDriver_id("NULL");
			order.setPassenger_id(passenger);
			order.setDest_district_hash(dao.getDistrict(conn));
			order.setStart_district_hash(dao.getDistrict(conn));
			order.setTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
					.format(new Date()));
			order.setPrice(new Double(45));
			
			// insert order into queue instead of insert order into database
			//dao.insertOrder(conn, order);

			// ActiveMQManager.getInstance().sendMessage(order_id);
			JedisUtil.lpush("didi-order", order.toString());

			return new Message(String.valueOf(Calendar.getInstance().get(
					Calendar.SECOND)), order.toString());
		} finally {
			dao.closeConn(conn);
		}
	}


	@Override
	protected String getTopic() {
		return "didi-order-topic";
	}

}