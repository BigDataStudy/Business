package com.study.bigdata.simulation;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.Random;

import kafka.javaapi.producer.Producer;
import kafka.producer.KeyedMessage;
import kafka.producer.ProducerConfig;

public abstract class AbstractProducer {
	private Producer<String, String> producer;
	
	private Connection conn;
	
	public AbstractProducer() {
		 
	}
	
	public void send() {
		try {
			initialize();
		} catch(Exception e) {
			System.out.println("Send...error in initialize db and kafka " + e);
		}
		
		try {
			Message msgList= prepareMsg();
			doSend(msgList);
//			Thread.sleep(new Random().nextInt(100));
		} catch (Exception e) {
			System.out.println("Send...error " + e);
		}
		
		postSend();
	}

	protected void doSend(Message msgList) {
		KeyedMessage<String, String> keyedMsg= 
				new KeyedMessage<String, String>(getTopic(), msgList.getKey(), msgList.getBody());
        producer.send(keyedMsg);   
	}
	
	protected abstract Message prepareMsg() throws Exception;
	
	protected abstract String getTopic();
	
	protected void initialize() throws Exception {
		Properties props = new Properties();
        
        props.put("metadata.broker.list", "BTTETI01:9092,BTTETI02:9092,BTTETI03:9092");
        props.put("serializer.class", "kafka.serializer.StringEncoder");
        props.put("partitioner.class", "com.study.bigdata.simulation.DidiPartitioner");
        props.put("request.required.acks", "1");
 
        ProducerConfig config = new ProducerConfig(props);
 
        producer = new Producer<String, String>(config);
       
        
        try {
            conn=DBManager.getConn();
        } catch(Exception e) {
        	throw new Exception("Initiate Connection failed. " + e);
        }
	}
	
	protected Connection getConnection() {
		return this.conn;
	}
	
	protected void postSend() {
		producer.close();
		
		if (conn != null) {
            try {
            	 DBManager.closeConn(conn);
            	
            	//conn.close();
            } catch (Exception e) {
                //do nothing
            }
        }
	}
	
}
