package br.ufrj.dcc.ad20122;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import org.apache.commons.math3.distribution.ExponentialDistribution;

import br.ufrj.dcc.ad20122.math.UtilMath;

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
		// X = exp() + 1/mu
		double nextService = nextArrival + serviceTIme;
		// 1 / mu - time of next completed service

		System.out.println("Simulate M/D/1 Queue.");

		// simulate the M/D/1 queue
		while (clientsSize-- > 0) {

			// next event is an arrival
			while (nextArrival < nextService) {
				queue.add(nextArrival);
				// lamda = lamda + exp();
				nextArrival += arrivalExp.sample();
			}

			// next event is a service completion
			double arrival = queue.remove();
			// W = 1/mu - exp
			double time = nextService - arrival;

			usersTime.add(time);

			// update the console
			// System.out.println(" >>>> tempo de espera do usuário : " + wait);

			// update the queue
			if (queue.isEmpty()) {
				// X = exp() + 1/mu
				nextService = nextArrival + serviceTIme;
			} else {
				// x = x + 1/mu
				nextService = nextService + serviceTIme;
			}
		}

		return usersTime;

	}

	public static void main(String[] args) {
		double lambda = 1.0; // arrival rate
		double mu = 2.0; // service rate
		int clientsSize = 10;

		List<Double> usersTime = new MD1().run(lambda, mu, clientsSize);

		System.out.println("Queue CDF with mean : "
				+ UtilMath.getMean(usersTime) + " with StandardDeviation: "
				+ UtilMath.getStandardDeviation(usersTime));
		for (Double double1 : usersTime) {
			System.out.println("User time: " + double1);
		}

	}
}
