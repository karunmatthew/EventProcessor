package org.eventprocessor.kafka;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.storm.guava.collect.ImmutableList;

import backtype.storm.topology.BasicOutputCollector;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseBasicBolt;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Tuple;

public class SentenceBolt extends BaseBasicBolt {
	
	private static final long serialVersionUID = 1L;
	private static File dataFile = new File("/root/Desktop/DataFile");
	// list used for aggregating the words
	private List<String> words = new ArrayList<String>();

	public void execute(Tuple input, BasicOutputCollector collector) {
		// Get the word from the tuple
		String word = input.getString(0);
		if (StringUtils.isBlank(word)) {
			// ignore blank lines
			return;
		}
		System.out.println("Received Word:" + word);
		// add word to current list of words
		words.add(word);
		if (word.endsWith(".")) {
			// word ends with '.' which means this is the end
			// the SentenceBolt publishes a sentence tuple
			collector.emit(ImmutableList.of((Object) StringUtils.join(words,
					' ')));
			// and reset the words list.
			
			if(!dataFile.exists())
			{
				try {
					dataFile.createNewFile();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			
			try (FileWriter fw = new FileWriter(dataFile.getAbsoluteFile(), true);
					BufferedWriter bw = new BufferedWriter(fw);) {
				bw.write(words.toString());
				bw.write("\n");
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			words.clear();
		}
	}

	public void declareOutputFields(OutputFieldsDeclarer declarer) {
		// here we declare we will be emitting tuples with
		// a single field called "sentence"
		declarer.declare(new Fields("sentence"));
	}
}
