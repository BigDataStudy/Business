package com.study.bigdata.simulation;


import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;
import java.util.UUID;

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
		try {
			SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	        
	        StringBuilder builder= new StringBuilder();
	        builder.append(UUID.randomUUID().toString().replace("-", "")).append("\t");//order_id
	        builder.append("NULL").append("\t");//driver_id
	        
	        //connect
	        String passenger = getAvailablePassenger();
            
	        builder.append(passenger).append("\t");//passenger_id
	        builder.append(getDistrict()).append("\t");//start_district_hash
	        builder.append(getDistrict()).append("\t");//dest_district_hash
	        
	        builder.append("NULL").append("\t");
	        Date date= new Date();
	        builder.append(sdf.format(date));
	        int ss= Calendar.getInstance().get(Calendar.SECOND);
	        createOrder(builder.toString());
	        
	        return new Message(String.valueOf(ss), builder.toString());
		} catch (Exception e) {
			throw new Exception("Error occured when request. " + e);
		}
	}


	private void createOrder(String string) throws Exception {
		String[] detail= string.split("\t");
		java.sql.Connection conn= this.getConnection();
		PreparedStatement ps = null;
		
		try {
//			String sql = "INSERT INTO order_simulation(order_id,driver_id,passenger_id,start_district_hash,dest_district_hash,Price,Time) VALUES( ?, ?, ?, ?, ?, ?, ?) " ;
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
			throw new Exception("Insert Error. " + e);
			
		} finally {
			try {
				ps.close();
			} catch (Exception e) {
				
			}
		}
	}


	private String getAvailablePassenger() throws Exception {
		java.sql.Connection conn= this.getConnection();
		PreparedStatement ps = null;
		try {
			Random random = new Random();
			int pid = random.nextInt(1289406) + 1;
			ps = conn.prepareStatement("select * from passenger where id=" + pid);
			ResultSet rs = ps.executeQuery();
			
		    while (rs.next()) {
		        return rs.getString(2);
		    }
		    rs.close();
		    return getAvailablePassenger();
		} catch (SQLException e) {
			throw new Exception("Acquire passenger error. " + e);
		} finally {
			try {
				ps.close();
			} catch (Exception e) {
				
			}
		}
//		throw new Exception("No available passenger found!");
	}
	
	private String getDistrict() throws Exception {
		java.sql.Connection conn= this.getConnection();
		PreparedStatement ps = null;
		try {
			Random random = new Random();
			int did = random.nextInt(65) + 1;
			ps = conn.prepareStatement("select district_id from district where id=" + did);
			ResultSet rs = ps.executeQuery();
		    while (rs.next()) {
		        return rs.getString(1);
		    }
		    rs.close();
		    return getDistrict();
		} catch (SQLException e) {
			throw new Exception("Acquire passenger error. " + e);
		} finally {
			try {
				ps.close();
			} catch (Exception e) {
				
			}
		}
//		throw new Exception("No available passenger found!");
	}

	@Override
	protected String getTopic() {
		return "didi-order-topic";
	}
	
	public static void main(String[] args) throws InterruptedException {
		 
	      DidiOrderRequestor requester = new DidiOrderRequestor();
	      try {
	    	  requester.initialize();
	    	  requester.send();
//			System.out.println(requester.getAvailablePassenger());
		} catch (Exception e) {
			e.printStackTrace();
		}
    }

}