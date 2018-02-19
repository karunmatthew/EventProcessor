package org.eventprocessor.storm;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import backtype.storm.spout.SpoutOutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseRichSpout;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Values;

public class LearningStormSpout extends BaseRichSpout {

	private static final long serialVersionUID = 1L;
	private SpoutOutputCollector spoutOutputCollector;
	private static Socket clientSocket;
	private static ServerSocket serverSocket;
	private static int port = 7070;

	public void open(Map conf, TopologyContext context,
			SpoutOutputCollector spoutOutputCollector) {
		this.spoutOutputCollector = spoutOutputCollector;
		try {
			serverSocket = new ServerSocket(port);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void nextTuple() {
		try {
			clientSocket = serverSocket.accept();
			new SpoutThread(clientSocket,spoutOutputCollector).start();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void declareOutputFields(OutputFieldsDeclarer declarer) {
		// emit the tuple with field "site"
		declarer.declare(new Fields("site"));
	}
}
