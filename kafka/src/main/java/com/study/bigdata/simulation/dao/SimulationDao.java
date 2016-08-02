package com.study.bigdata.simulation.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Random;

import net.sf.ehcache.Cache;
import net.sf.ehcache.Element;

import com.study.bigdata.simulation.Order;
import com.utils.db.EHCacheManager;

public class SimulationDao {
	
	public int updateOrder(Connection conn, Order order)  throws Exception {
		PreparedStatement ps = null;
		String des_table = "order_simulation";
		//des_table = TablePartitionUtil.tableRouter("order_simulation", order.getOrder_id());
		String sql = "update " +des_table+  " set driver_id='" + order.getDriver_id() + "' where order_id='" + order.getOrder_id() +"'";
		
		try {
			ps = conn.prepareStatement(sql);
			return  ps.executeUpdate();
		} catch (SQLException e) {
			throw new Exception("update order Error. " + e);
		}finally {
			closePreparedStatement(ps);
		}
	}
	
	public int insertOrder(Connection conn, Order order) throws Exception {
		PreparedStatement ps = null;

		try {
			String des_table = "order_simulation";
			// des_table = TablePartitionUtil.tableRouter("order_simulation",detail[0]);
			String sql = "INSERT INTO " + des_table
					+ " VALUES( ?, ?, ?, ?, ?, ?, ?) ";
			ps = conn.prepareStatement(sql);
			ps.setString(1, order.getOrder_id());
			ps.setString(2, order.getDriver_id());
			ps.setString(3, order.getPassenger_id());
			ps.setString(4, order.getStart_district_hash());
			ps.setString(5, order.getDest_district_hash());
			ps.setString(6, order.getPrice().toString());
			ps.setString(7, order.getTime());
			return ps.executeUpdate();

		} catch (SQLException e) {
			throw new Exception("create order Error. " + e);
		} finally {
			closePreparedStatement(ps);
		}
	}
	
	public Order getUnRepliedOrder(Connection conn ) throws Exception {
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = conn.prepareStatement("select order_id, driver_id, passenger_id, start_district_hash, dest_district_hash, Time from order_simulation where driver_id = 'NULL' limit 1 for update");
			rs = ps.executeQuery();
			if (rs.next()) {
				Order order = new Order();
				order.setOrder_id(rs.getString("order_id"));
				order.setPassenger_id(rs.getString("passenger_id"));
				order.setStart_district_hash(rs.getString("start_district_hash"));
				order.setDest_district_hash(rs.getString("dest_district_hash"));
				order.setTime(rs.getString("Time"));
				return order;
			}
			return getUnRepliedOrder(conn);
		} finally {
			closeResultSet(rs);
			closePreparedStatement(ps);
		}
	}
	
	public String getAvailablePassenger(Connection conn) throws Exception {
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			Random random = new Random();
			int pid = random.nextInt(1289406) + 1;
			Cache cache = EHCacheManager.getInstance().getCache(
					"passenger_cache");
			Element ele = cache.get(pid);
			if (null == ele) {
				ps = conn.prepareStatement("select * from passenger where id="
						+ pid);
				rs = ps.executeQuery();

				while (rs.next()) {
					ele = new Element(pid, rs.getString(2));
					cache.put(ele);
					return rs.getString(2);
				}
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

	public String getDistrict(Connection conn) throws Exception {
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			Random random = new Random();
			int did = random.nextInt(65) + 1;
			Cache cache = EHCacheManager.getInstance().getCache(
					"district_cache");
			Element ele = cache.get(did);
			if (null == ele) {
				ps = conn
						.prepareStatement("select district_id from district where id="
								+ did);
				rs = ps.executeQuery();
				while (rs.next()) {
					ele = new Element(did, rs.getString(1));
					cache.put(ele);
					return rs.getString(1);
				}
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
	
	public String getAvailableDriver(Connection conn) throws Exception {
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			Random random = new Random();
			int pid = random.nextInt(91390) + 7422;
			Cache cache= EHCacheManager.getInstance().getCache("driver_cache");
			Element ele = cache.get(pid);
			if ( null == ele ) {
				ps = conn
						.prepareStatement("select * from driver where id = " + pid);
				rs = ps.executeQuery();
				if (rs.next()) {
					ele = new Element(pid, rs.getString(2));
			    	cache.put(ele);
					return rs.getString(2);
				}
			}else {
				return ele.getObjectValue().toString();
			}
			
			return getAvailableDriver(conn);
		}catch (SQLException e) {
			throw new Exception("Acquire Available Driver error. " + e);
		}  finally {
			closeResultSet(rs);
			closePreparedStatement(ps);
		}
	}
	
	
	public void  closePreparedStatement(PreparedStatement ps) {
		if (null != ps) {
			try {
				ps.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void  closeConn(Connection conn) {
		if (null != conn) {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void  closeResultSet(ResultSet rs) {
		if (null != rs) {
			try {
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}
