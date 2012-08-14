package br.ufrj.dcc.ad.control;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import org.apache.commons.math3.distribution.ExponentialDistribution;

import br.ufrj.dcc.ad.math.StatisticsSample;

public class MD1 {

	// lambda - arrival rate
	// mu - service rate
	public StatisticsSample run(double lambda, double mu, int clientsSize) {

		ExponentialDistribution arrivalExp = new ExponentialDistribution(
				1.0 / lambda);

		Queue<Double> queue = new LinkedList<Double>();

		// W
		List<Double> timeW1 = new ArrayList<Double>();

		// T time
		List<Double> timeT1 = new ArrayList<Double>();

		double serviceTIme = 1.0 / mu;

		// time of next arrival
		double nextArrival = arrivalExp.sample();// exp(lambda);
		// time of next completed service
		double nextService = nextArrival + serviceTIme;

		System.out.println("Simulate M/D/1 Queue.");

		// simulate the M/D/1 queue
		while (timeW1.size() <= clientsSize) {

			// next event is an arrival
			if (nextArrival < nextService) {
				queue.add(nextArrival);
				nextArrival += arrivalExp.sample();// exp(lambda);
			} else {

				// next event is a service completion
				double arrival = queue.remove();
				double time = nextService - arrival;

				timeW1.add(time);
				timeT1.add(time + serviceTIme);

				// update the queue
				if (queue.isEmpty()) {
					nextService = nextArrival + serviceTIme;
				} else {
					nextService = nextService + serviceTIme;
				}
			}
		}

		return new StatisticsSample(timeW1, timeT1, null);

	}

	// Test MD1 Queue
	public static void main(String[] args) {
		double lambda = 1.0; // arrival rate
		double mu = 2.0; // service rate
		int sampleSize = 1000;

		StatisticsSample statisticsSample = new MD1().run(lambda, mu,
				sampleSize);

		StringBuilder builder = new StringBuilder();

		String statisticsString = statisticsSample.toString();
		builder.append(statisticsString);
		System.out.println(statisticsString);
		builder.append(statisticsSample.toStringTIndividual());

		// try {
		// FileUtils.writeStringToFile(
		// new File("./samples/statistics_MD1.txt"),
		// builder.toString());
		// } catch (IOException e) {
		// e.printStackTrace();
		// }
		//
		// try {
		// statisticsSample.generateChart("./samples/chart_MD1.jpg");
		// } catch (IOException e) {
		// e.printStackTrace();
		// }

	}
}
