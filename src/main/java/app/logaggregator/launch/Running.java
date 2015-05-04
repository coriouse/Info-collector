package app.logaggregator.launch;

import app.logaggregator.core.StatisticAggregator;

public class Running {
	public static void main(String[] args) {
		StatisticAggregator sa = new StatisticAggregator();
		sa.aggreagate();
	}
}
