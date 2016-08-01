package com.utils.activemq;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.DeliveryMode;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.log4j.Logger;

public class ActiveMQManager {
	private Logger log = Logger.getLogger(this.getClass().getSimpleName());

	private final String QUEUE_NAME = "didi-order-2";

	private static ActiveMQManager INSTANCE;

	ConnectionFactory connectionFactory;

	private ActiveMQManager() {
		// ConnectionFactory ：连接工厂，JMS 用它创建连接

		// Connection ：JMS 客户端到JMS Provider 的连接
		// Session： 一个发送或接收消息的线程
		// Session session;
		// Destination ：消息的目的地;消息发送给谁.
		// Destination destination;
		// MessageProducer：消息发送者
		// MessageProducer producer;
		// TextMessage message;
		// 构造ConnectionFactory实例对象，此处采用ActiveMq的实现jar
		connectionFactory = new ActiveMQConnectionFactory(
				ActiveMQConnection.DEFAULT_USER,
				ActiveMQConnection.DEFAULT_PASSWORD, "tcp://9.115.65.42:61616");
	}

	public static ActiveMQManager getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new ActiveMQManager();
		}
		return INSTANCE;

	}

	public Connection getConnection() throws JMSException {
		// 构造从工厂得到连接对象
		Connection connection = connectionFactory.createConnection();
		// 启动
		connection.start();

		return connection;
	}

	public Session getSession(Connection conn) throws JMSException {
		return conn.createSession(Boolean.FALSE, Session.AUTO_ACKNOWLEDGE);
	}

	public void sendMessage(String textMsg) throws JMSException {
		Connection connection = getConnection();
		Session session = getSession(connection);

		Queue destination = session.createQueue(QUEUE_NAME);
		MessageProducer producer = session.createProducer(destination);
		// 设置不持久化，此处学习，实际根据项目决定
		producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);

		TextMessage message = session.createTextMessage(textMsg);
		// 发送消息到目的地方
		producer.send(message);

		session.close();
		connection.close();
	}

	public String ReceiveMessage() throws JMSException {
		Connection connection = getConnection();
		Session session = getSession(connection);

		Queue destination = session.createQueue(QUEUE_NAME);
		MessageConsumer consumer = session.createConsumer(destination);

		Message message = consumer.receive(3000);

		if (message instanceof TextMessage) {
			TextMessage textMessage = (TextMessage) message;
			String text = textMessage.getText();
			System.out.println("Received: " + text);
			return textMessage.getText();
		}

		return null;
	}
}
