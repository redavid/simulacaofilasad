package br.ufrj.dcc.ad20122;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import org.apache.commons.math3.distribution.ExponentialDistribution;

import br.ufrj.dcc.ad20122.math.StatisticsSample;

public class MM1 {

	// lambda - arrival rate
	// mu - service rate
	public List<Double> run(double lambda, double mu, int clientsSize) {

		ExponentialDistribution arrivalExp = new ExponentialDistribution(lambda);
		ExponentialDistribution serviceExp = new ExponentialDistribution(
				1.0 / mu);

		List<Double> usersTime = new ArrayList<Double>();

		// arrival times of customers
		Queue<Double> queue = new LinkedList<Double>();

		// time of next arrival
		double nextArrival = arrivalExp.sample();

		// time of next departure
		double nextDeparture = Double.POSITIVE_INFINITY;

		// simulate an M/M/1 queue
		while (usersTime.size() < clientsSize) {

			// it's an arrival
			if (nextArrival <= nextDeparture) {

				if (queue.isEmpty())
					nextDeparture = nextArrival + serviceExp.sample();

				queue.add(nextArrival);
				nextArrival += arrivalExp.sample();
			}

			// it's a departure
			else {
				double arrival = queue.remove();
				double wait = nextDeparture - arrival;

				usersTime.add(wait);

				if (queue.isEmpty())
					nextDeparture = Double.POSITIVE_INFINITY;
				else
					nextDeparture += serviceExp.sample();

			}
		}

		return usersTime;

	}

	// Test MM1 Queue
	public static void main(String[] args) {
		double lambda = 1.0; // arrival rate
		double mu = 4.0; // service rate
		int clientsSize = 10;

		List<Double> usersTime = new MM1().run(lambda, mu, clientsSize);

		System.err.println(new StatisticsSample(usersTime));

		for (Double double1 : usersTime) {
			System.out.println("User time: " + double1);
		}

	}
}
