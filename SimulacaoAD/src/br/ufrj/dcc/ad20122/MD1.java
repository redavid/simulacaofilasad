package br.ufrj.dcc.ad20122;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import org.apache.commons.math3.distribution.ExponentialDistribution;

import br.ufrj.dcc.ad20122.math.StatisticsSample;

public class MD1 {

	// lambda - arrival rate
	// mu - service rate
	public List<Double> run(double lambda, double mu, int clientsSize) {

		ExponentialDistribution arrivalExp = new ExponentialDistribution(lambda);

		Queue<Double> queue = new LinkedList<Double>();
		List<Double> usersTime = new ArrayList<Double>();

		double serviceTIme = 1.0 / mu;

		// time of next arrival
		double nextArrival = arrivalExp.sample();
		// time of next completed service
		double nextService = nextArrival + serviceTIme;

		System.out.println("Simulate M/D/1 Queue.");

		// simulate the M/D/1 queue
		while (usersTime.size() < clientsSize) {

			// next event is an arrival
			if (nextArrival < nextService) {
				queue.add(nextArrival);
				nextArrival += arrivalExp.sample();
			} else {

				// next event is a service completion
				double arrival = queue.remove();
				double time = nextService - arrival;

				usersTime.add(time);

				// update the queue
				if (queue.isEmpty()) {
					nextService = nextArrival + serviceTIme;
				} else {
					nextService = nextService + serviceTIme;
				}
			}
		}

		return usersTime;

	}

	// Test MD1 Queue
	public static void main(String[] args) {
		double lambda = 1.0; // arrival rate
		double mu = 2.0; // service rate
		int sampleSize = 10;

		List<Double> usersTime = new MD1().run(lambda, mu, sampleSize);

		System.err.println(new StatisticsSample(usersTime));

		for (Double double1 : usersTime) {
			System.out.println("User time: " + double1);
		}

	}
}
