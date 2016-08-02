package com.study.bigdata.simulation;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import kafka.producer.KeyedMessage;

import org.apache.log4j.Logger;

public abstract class AbstractProducer {
	
	static Logger logger = Logger.getLogger(AbstractProducer.class.getName());
	
	public AbstractProducer() {
	}
	
	public void send() throws Exception {
		Message msg = null;
		msg= prepareMsg();
		
		if ( null != msg ) {
			doSendMassageToKafka(msg);
		}
		
	}

	protected void doSendMassageToKafka(Message msgList) {
		KeyedMessage<String, String> keyedMsg= new KeyedMessage<String, String>(getTopic(), msgList.getKey(), msgList.getBody());
		KafkaManager.getInstance().getProducer().send(keyedMsg);   
	}
	
	protected abstract Message prepareMsg() throws Exception;
	
	protected abstract String getTopic();
	
}
