package com.study.bigdata.simulation;

import java.util.Properties;

import kafka.javaapi.producer.Producer;
import kafka.producer.ProducerConfig;

public class KafkaManager {

	private static KafkaManager INSTANCE;

	private Producer<String, String> producer;

	private KafkaManager() {
		Properties producer_props = new Properties();

		producer_props.put("metadata.broker.list",
				"9.125.73.223:9092,BTTETI01:9092,BTTETI02:9092,BTTETI03:9092");
		producer_props
				.put("serializer.class", "kafka.serializer.StringEncoder");
		producer_props.put("partitioner.class",
				"com.study.bigdata.simulation.DidiPartitioner");
		producer_props.put("request.required.acks", "1");

		ProducerConfig producer_config = new ProducerConfig(producer_props);

		producer = new Producer<String, String>(producer_config);
	}

	public static KafkaManager getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new KafkaManager();
		}
		return INSTANCE;
	}

	public Producer<String, String> getProducer() {
		return producer;
	}

	

}
