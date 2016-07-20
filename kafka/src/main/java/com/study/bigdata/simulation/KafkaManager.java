package com.study.bigdata.simulation;

import java.util.Properties;

import kafka.javaapi.producer.Producer;
import kafka.producer.ProducerConfig;

public class KafkaManager {
	
	private static KafkaManager INSTANCE;
	
	private Producer<String, String> producer;
	
	private KafkaManager() {
		Properties props = new Properties();
        
        props.put("metadata.broker.list", "BTTETI01:9092,BTTETI02:9092,BTTETI03:9092");
        props.put("serializer.class", "kafka.serializer.StringEncoder");
        props.put("partitioner.class", "com.study.bigdata.simulation.DidiPartitioner");
        props.put("request.required.acks", "1");
 
        ProducerConfig config = new ProducerConfig(props);
 
        producer = new Producer<String, String>(config);
	}
	
	public static KafkaManager getInstance() {
		if (INSTANCE == null) {
			INSTANCE= new KafkaManager();
		}
		return INSTANCE;
	}
	
	public Producer<String, String> getProducer() {
		return producer;
	}

}
