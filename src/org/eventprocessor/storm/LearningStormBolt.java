package org.eventprocessor.storm;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import backtype.storm.topology.BasicOutputCollector;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseBasicBolt;
import backtype.storm.tuple.Tuple;

public class LearningStormBolt extends BaseBasicBolt {

	private static final long serialVersionUID = 1L;

	public void execute(Tuple input, BasicOutputCollector collector) {
		// fetched the field "site" from input tuple.
		String test = input.getStringByField("site");
		// print the value of field "site" on console.
		System.out.println("Name of input site is : " + test);

		File file = new File("/root/Desktop/A");
		createFile(file);

		try (FileWriter fw = new FileWriter(file.getAbsoluteFile(), true);
				BufferedWriter bw = new BufferedWriter(fw);) {
			bw.write(test);
			bw.write("\n");
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	private void createFile(File file) {
		if (!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public void declareOutputFields(OutputFieldsDeclarer declarer) {
	}
}
