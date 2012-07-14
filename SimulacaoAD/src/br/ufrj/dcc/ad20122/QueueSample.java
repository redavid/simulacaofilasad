package br.ufrj.dcc.ad20122;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
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

		int numUsuarios = 10;
		Queue<Double> queue = new LinkedList<Double>();
		List<Usuario> listUsers = new ArrayList<Usuario>();

		// time of next arrival
		double nextArrival = arrivalDistribution.sample();
		// X = poisson() + 1/mu
		double nextService = nextArrival + ServiceDistribution.getMean();
		// 1 / mu - time of next completed service

		System.out.println("Simulate M/D/1 Queue.");

		// simulate the M/D/1 queue
		while (numUsuarios-- > 0) {

			// next event is an arrival
			while (nextArrival < nextService) {
				queue.add(nextArrival);
				// lamda = lamda + poisson();
				nextArrival += arrivalDistribution.sample();
			}

			// next event is a service completion
			double arrival = queue.remove();
			// W = 1/mu - lambada
			double wait = nextService - arrival;

			listUsers.add(new Usuario(wait, nextService));

			// update the console
			// System.out.println(" >>>> tempo de espera do usuário : " + wait);

			// update the queue
			if (queue.isEmpty())
				// X = poisson() + 1/mu
				nextService = nextArrival + ServiceDistribution.getMean();
			else
				// x = x + 1/mu
				nextService = nextService + ServiceDistribution.getMean();
		}

		System.out.println("Lista de usuários");
		for (Usuario usuario : listUsers) {
			System.out.println(">> Usuários: " + usuario);
		}

	}
}
