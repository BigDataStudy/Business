package com.study.bigdata.simulation;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;
import java.util.UUID;

import net.sf.ehcache.Cache;
import net.sf.ehcache.Element;

import com.utils.db.DruidDataSourceManager;
import com.utils.db.EHCacheManager;
import com.utils.redis.JedisUtil;

public class DidiOrderRequestor extends AbstractProducer {
	
    

    
    /*
    order_id 	string 	订单ID 	70fc7c2bd2caf386bb50f8fd5dfef0cf
    driver_id 	string 	司机ID 	56018323b921dd2c5444f98fb45509de
    passenger_id 	string 	用户ID 	238de35f44bbe8a67bdea86a5b0f4719
    start_district_hash 	string 	出发地区域哈希�1�7�1�7 	d4ec2125aff74eded207d2d915ef682f
    dest_district_hash 	string 	目的地区域哈希�1�7�1�7 	929ec6c160e6f52c20a4217c7978f681
    Price 	double 	价格 	37.5
    Time 	string 	订单时间 	2016-01-15 00:35:11*/

    //97ebd0c6680f7c0535dbfdead6e51b4b	dd65fa250fca2833a3a8c16d2cf0457c	ed180d7daf639d936f1aeae4f7fb482f	4725c39a5e5f4c188d382da3910b3f3f	3e12208dd0be281c92a6ab57d9a6fb32	24	2016-01-01 13:37:23
	@Override
	protected Message prepareMsg() throws Exception {
		Connection conn = null;
		try {
			conn = DruidDataSourceManager.getInstance().getConnection();
			SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	        
	        StringBuilder builder= new StringBuilder();
	        String order_id = UUID.randomUUID().toString().replace("-", "");
	        builder.append(order_id).append("\t");//order_id
	        builder.append("NULL").append("\t");//driver_id
	        
	        //connect
	        String passenger = getAvailablePassenger(conn);
            
	        builder.append(passenger).append("\t");//passenger_id
	        builder.append(getDistrict(conn)).append("\t");//start_district_hash
	        builder.append(getDistrict(conn)).append("\t");//dest_district_hash
	        
	        builder.append("NULL").append("\t");
	        Date date= new Date();
	        builder.append(sdf.format(date));
	        int ss= Calendar.getInstance().get(Calendar.SECOND);
	        createOrder(conn, builder.toString());
	        
	        //ActiveMQManager.getInstance().sendMessage(order_id);
	        JedisUtil.lpush("didi-order", order_id);
	        
	        return new Message(String.valueOf(ss), builder.toString());
		} finally {
			closeConn(conn);
		}
	}


	private void createOrder(Connection conn, String string) throws Exception {
		String[] detail= string.split("\t");
		PreparedStatement ps = null;
		
		try {
			String sql = "INSERT INTO order_simulation VALUES( ?, ?, ?, ?, ?, ?, ?) " ;
            ps = conn.prepareStatement(sql);
            ps.setString(1, detail[0]);
            ps.setString(2, detail[1]);
            ps.setString(3, detail[2]);
            ps.setString(4, detail[3]);
            ps.setString(5, detail[4]);
            ps.setString(6, detail[5]);
            ps.setString(7, detail[6]);
            ps.executeUpdate();
          
		} catch (SQLException e) {
			throw new Exception("create order Error. " + e);
			
		} finally {
			closePreparedStatement(ps);
		}
	}


	private String getAvailablePassenger(Connection conn) throws Exception {
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			Random random = new Random();
			int pid = random.nextInt(1289406) + 1;
			Cache cache= EHCacheManager.getInstance().getCache("passenger_cache");
			Element ele = cache.get(pid);
			if ( null == ele ) {
				ps = conn.prepareStatement("select * from passenger where id=" + pid);
				rs = ps.executeQuery();
				
			    while (rs.next()) {
			    	ele = new Element(pid, rs.getString(2));
			    	cache.put(ele);
			        return rs.getString(2);
			    }
			    rs.close();
			} else {
				return ele.getObjectValue().toString();
			}
			
		    return getAvailablePassenger(conn);
		} catch (SQLException e) {
			throw new Exception("Acquire passenger error. " + e);
		} finally {
			closeResultSet(rs);
			closePreparedStatement(ps);
		}
	}
	
	private String getDistrict(Connection conn) throws Exception {
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			Random random = new Random();
			int did = random.nextInt(65) + 1;
			Cache cache= EHCacheManager.getInstance().getCache("district_cache");
			Element ele = cache.get(did);
			if ( null == ele ) {
				ps = conn.prepareStatement("select district_id from district where id=" + did);
				rs = ps.executeQuery();
			    while (rs.next()) {
			    	ele = new Element(did, rs.getString(1));
			    	cache.put(ele);
			        return rs.getString(1);
			    }
			    rs.close();
			} else {
				return ele.getObjectValue().toString();
			}
			
		    return getDistrict(conn);
		} catch (SQLException e) {
			throw new Exception("Acquire district error. " + e);
		} finally {
			closeResultSet(rs);
			closePreparedStatement(ps);
		}
	}

	@Override
	protected String getTopic() {
		return "didi-order-topic";
	}
	
}