package org.eventprocessor.storm;

import backtype.storm.Config;
import backtype.storm.StormSubmitter;
import backtype.storm.generated.AlreadyAliveException;
import backtype.storm.generated.InvalidTopologyException;
import backtype.storm.topology.TopologyBuilder;

public class LearningStormSingleNodeTopology {
	public static void main(String[] args) {
		TopologyBuilder builder = new TopologyBuilder();
		// set the spout class
		builder.setSpout("LearningStormSpout", new LearningStormSpout(), 4);
		// set the bolt class
		builder.setBolt("LearningStormBolt", new LearningStormBolt(), 2)
				.shuffleGrouping("LearningStormSpout");
		Config conf = new Config();
		conf.setNumWorkers(3);
		try {
			// This statement submit the topology on remote cluster.
			// args[0] = name of topology
			StormSubmitter.submitTopology(args[0], conf,
					builder.createTopology());
		} catch (AlreadyAliveException alreadyAliveException) {
			System.out.println(alreadyAliveException);
		} catch (InvalidTopologyException invalidTopologyException) {
			System.out.println(invalidTopologyException);
		}
	}
}
