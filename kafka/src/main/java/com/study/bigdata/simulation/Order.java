package com.study.bigdata.simulation;

public class Order {
	
	 /*
    order_id 	string 	订单ID 	70fc7c2bd2caf386bb50f8fd5dfef0cf
    driver_id 	string 	司机ID 	56018323b921dd2c5444f98fb45509de
    passenger_id 	string 	用户ID 	238de35f44bbe8a67bdea86a5b0f4719
    start_district_hash 	string 	出发地区域哈希值 	d4ec2125aff74eded207d2d915ef682f
    dest_district_hash 	string 	目的地区域哈希值 	929ec6c160e6f52c20a4217c7978f681
    Price 	double 	价格 	37.5
    Time 	string 	订单时间戳 	2016-01-15 00:35:11*/
	
	private String order_id;
	private String driver_id;
	private String passenger_id;
	private String start_district_hash;
	private String dest_district_hash;
	private Double price;
	private String time;
	
	
	public String getOrder_id() {
		return order_id;
	}
	public void setOrder_id(String order_id) {
		this.order_id = order_id;
	}
	public String getDriver_id() {
		return driver_id;
	}
	public void setDriver_id(String driver_id) {
		this.driver_id = driver_id;
	}
	public String getPassenger_id() {
		return passenger_id;
	}
	public void setPassenger_id(String passenger_id) {
		this.passenger_id = passenger_id;
	}
	public String getStart_district_hash() {
		return start_district_hash;
	}
	public void setStart_district_hash(String start_district_hash) {
		this.start_district_hash = start_district_hash;
	}
	public String getDest_district_hash() {
		return dest_district_hash;
	}
	public void setDest_district_hash(String dest_district_hash) {
		this.dest_district_hash = dest_district_hash;
	}
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}

}
