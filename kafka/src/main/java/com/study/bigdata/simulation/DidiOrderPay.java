package com.study.bigdata.simulation;


import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DidiOrderPay extends AbstractProducer {
	
    public static void main(String[] args) throws InterruptedException {
      
    	/**
    	 * ===================testcase01=============================
    	 * following code for test kafka send message
    	 */
    	
    	int times = 100000;
    	DidiOrderPay pay = new DidiOrderPay();
    	try {
    		pay.initialize();
		} catch (Exception e1) {
		}
    	long l = System.currentTimeMillis();
    	int success_time = 0;
    	try {
    		for ( int i=0;i<times;i++) {
    			pay.doSend(pay.prepareMsg());
    			success_time++;
    		}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	System.out.println(String.format("time=%s, send=%s", (System.currentTimeMillis()-l)/1000, success_time));
    	/**
    	 * ===================testcase01=============================end
    	 */
    	
    }

    
    /*
    order_id 	string 	订单ID 	70fc7c2bd2caf386bb50f8fd5dfef0cf
    driver_id 	string 	司机ID 	56018323b921dd2c5444f98fb45509de
    passenger_id 	string 	用户ID 	238de35f44bbe8a67bdea86a5b0f4719
    start_district_hash 	string 	出发地区域哈希�1�7�1�7 	d4ec2125aff74eded207d2d915ef682f
    dest_district_hash 	string 	目的地区域哈希�1�7�1�7 	929ec6c160e6f52c20a4217c7978f681
    Price 	double 	价格 	37.5
    Time 	string 	订单时间戄1�7 	2016-01-15 00:35:11*/

    //97ebd0c6680f7c0535dbfdead6e51b4b	dd65fa250fca2833a3a8c16d2cf0457c	ed180d7daf639d936f1aeae4f7fb482f	4725c39a5e5f4c188d382da3910b3f3f	3e12208dd0be281c92a6ab57d9a6fb32	24	2016-01-01 13:37:23
	@Override
	protected Message prepareMsg() throws Exception {
		SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        
        StringBuilder builder= new StringBuilder();
        builder.append("97ebd0c6680f7c0535dbfdead6e51b4b").append("\t");//order_id
        builder.append("dd65fa250fca2833a3a8c16d2cf0457c").append("\t");//driver_id
        builder.append("ed180d7daf639d936f1aeae4f7fb482f").append("\t");//passenger_id
        builder.append("4725c39a5e5f4c188d382da3910b3f3f").append("\t");//start_district_hash
        builder.append("3e12208dd0be281c92a6ab57d9a6fb32").append("\t");//dest_district_hash
        
        builder.append("37.5").append("\t");
        Date date= new Date();
        builder.append(sdf.format(date));
        int ss= Calendar.getInstance().get(Calendar.SECOND);
        
        return new Message(String.valueOf(ss), builder.toString());
	}

	@Override
	protected String getTopic() {
		return "didi-order-topic";
	}

}