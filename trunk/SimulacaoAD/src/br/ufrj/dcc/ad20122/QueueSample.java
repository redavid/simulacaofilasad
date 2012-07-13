package br.ufrj.dcc.ad20122;

import java.util.LinkedList;
import java.util.Queue;

import org.apache.commons.math3.distribution.ExponentialDistribution;
import org.apache.commons.math3.distribution.PoissonDistribution;

public class QueueSample {

	public static void main(String[] args) {

		double lambda = 1.0; // arrival rate
		double mu = 2.0; // service rate

		PoissonDistribution arrivalDistribution = new PoissonDistribution(
				lambda);

		ExponentialDistribution ServiceDistribution = new ExponentialDistribution(
				1 / mu);

		Queue<Double> queue = new LinkedList<Double>();

		double nextArrival = arrivalDistribution.sample(); // time of next
															// arrival
		double nextService = nextArrival + 1 / mu; // time of next completed
													// service

		System.out.println("Simulate M/D/1 Queue.");
		
		// simulate the M/D/1 queue
		while (true) {

			// next event is an arrival
			while (nextArrival < nextService) {
				queue.add(nextArrival);
				nextArrival += ServiceDistribution.sample() ;
			}

			// next event is a service completion
			double arrival = queue.remove();
			double wait = nextService - arrival;

			// update the console
			System.out.println(" >>>> tempo de espera do usuário : " + wait);

			// update the queue
			if (queue.isEmpty())
				nextService = nextArrival + 1 / mu;
			else
				nextService = nextService + 1 / mu;
		}
	}
}
