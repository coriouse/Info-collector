package app.collector.launch;

import app.collector.core.Collector;

public class Launcher {
	public static void main(String[] args) {
		Collector collector = new Collector();
		collector.collect();
	}
}
