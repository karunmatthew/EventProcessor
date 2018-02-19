package org.eventprocessor.kafka;

import java.util.Properties;

import kafka.javaapi.producer.Producer;
import kafka.producer.KeyedMessage;
import kafka.producer.ProducerConfig;

public class StreamDataProducer {

	public static void main(String[] args) {

		Properties props = new Properties();
		props.put("metadata.broker.list", "localhost:9092");
		props.put("serializer.class", "kafka.serializer.StringEncoder");
		props.put("request.required.acks", "1");

		ProducerConfig config = new ProducerConfig(props);
		Producer<String, String> producer = new Producer<String, String>(config);

		KeyedMessage<String, String> data = new KeyedMessage<String, String>(
				"words_topic", "");
		producer.send(data);

		System.out.println("Produced data");
		producer.close();
	}

}
