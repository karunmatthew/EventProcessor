package org.eventprocessor.kafka;

import storm.kafka.KafkaSpout;
import storm.kafka.SpoutConfig;
import storm.kafka.StringScheme;
import storm.kafka.ZkHosts;
import backtype.storm.Config;
import backtype.storm.StormSubmitter;
import backtype.storm.generated.AlreadyAliveException;
import backtype.storm.generated.InvalidTopologyException;
import backtype.storm.spout.SchemeAsMultiScheme;
import backtype.storm.topology.TopologyBuilder;

public class KafkaSingleNodeTopology {
	public static void main(String[] args) throws AlreadyAliveException,
			InvalidTopologyException {
		ZkHosts zkHosts = new ZkHosts("localhost:2181");
		SpoutConfig kafkaConfig = new SpoutConfig(zkHosts, "words_topic", "",
				"id7");
		kafkaConfig.scheme = new SchemeAsMultiScheme(new StringScheme());
		// We want to consume all the first messages in
		// the topic every time we run the topology to
		// help in debugging. In production, this
		// property should be false
		kafkaConfig.forceFromStart = true;
		
		TopologyBuilder builder = new TopologyBuilder();
		builder.setSpout("KafkaSpout", new KafkaSpout(kafkaConfig), 2);
		builder.setBolt("SentenceBolt", new SentenceBolt(), 2).globalGrouping(
				"KafkaSpout");
		builder.setBolt("PrinterBolt", new PrinterBolt(), 2).globalGrouping(
				"SentenceBolt");
		
		Config conf = new Config();
		conf.setNumWorkers(3);
				
		StormSubmitter.submitTopology(args[0], conf,
				builder.createTopology());		
		
	}
}
