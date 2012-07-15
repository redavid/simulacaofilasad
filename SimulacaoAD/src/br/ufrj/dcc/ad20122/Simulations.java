package br.ufrj.dcc.ad20122;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Stack;

import org.apache.commons.math3.distribution.ExponentialDistribution;

import br.ufrj.dcc.ad20122.math.StatisticsSample;

public class Simulations {

	// Case Deterministic - Exponencial
	public StatisticsSample simulateCase1(double lambda, double mu1,
			double mu2, int clientsSize) {

		Queue<User> queue1 = new LinkedList<User>();
		Stack<User> queue2 = new Stack<User>();

		List<Double> listTime1 = new ArrayList<Double>();
		List<Double> listTime2 = new ArrayList<Double>();

		double serviceTime = 1.0 / mu1;

		ExponentialDistribution arrivalExp = new ExponentialDistribution(lambda);
		ExponentialDistribution service2Exp = new ExponentialDistribution(
				1.0 / mu2);

		double nextArrival = arrivalExp.sample();

		double nextService1 = nextArrival + serviceTime;
		double nextService2 = Double.POSITIVE_INFINITY;

		boolean stopCondition = true;

		while (stopCondition) {

			if (nextArrival <= nextService1 && nextArrival <= nextService2) {

				User newUser = new User(nextArrival, serviceTime);

				queue1.add(newUser);
				nextArrival += arrivalExp.sample();

				if (queue1.isEmpty() && queue2.isEmpty()) {
					nextService1 = nextArrival + serviceTime;
				} else if (queue1.isEmpty() && !queue2.isEmpty()) {
					User interrupedUser = queue2.peek();
					interrupedUser.setServiceTime(nextService2 - nextArrival);
					nextService2 = Double.POSITIVE_INFINITY;
					nextService1 = nextArrival + serviceTime;
				}

			} else if (nextService1 <= nextService2) {

				User actualUserQueue1 = queue1.remove();
				double tempo1 = nextService1
						- actualUserQueue1.getArrivalTime();

				if (listTime1.size() < clientsSize) {
					listTime1.add(tempo1 + actualUserQueue1.getServiceTime());
				}

				User newUser2 = new User(nextService1, service2Exp.sample());
				nextService2 = nextService1 + newUser2.getServiceTime();
				queue2.push(newUser2);

				if (queue1.isEmpty()) {
					nextService1 = nextArrival + serviceTime;
				} else {
					double nextServiceTime = queue1.peek().getServiceTime();
					nextService1 += nextServiceTime;

					if (!queue2.isEmpty()) {
						nextService2 += nextServiceTime;
					}

				}

			} else {
				User actualUserQueue2 = queue2.pop();
				double tempo2 = nextService2
						- actualUserQueue2.getArrivalTime();

				listTime2.add(tempo2 + actualUserQueue2.getServiceTime());

				// update the queue
				if (queue2.isEmpty()) {
					nextService2 = Double.POSITIVE_INFINITY;
				} else {
					nextService2 += actualUserQueue2.getServiceTime();
				}
			}

			stopCondition = listTime2.size() < clientsSize;

		}

		// System.out.println("QueueSize 1: " + listTime1.size());
		// for (Double double1 : listTime1) {
		// System.out.println(" Queue1 Time: " + double1);
		// }
		//
		// System.out.println("QueueSize 2: " + listTime2.size());
		// for (Double double1 : listTime2) {
		// System.out.println(" Queue2 Time: " + double1);
		// }

		List<Double> resultList = new ArrayList<Double>();
		for (int i = 0; i < clientsSize; i++) {
			double time1 = listTime1.get(i);
			double time2 = listTime2.get(i);

			resultList.add(time1 + time2);
		}

		// for (Double double1 : resultList) {
		// System.out.println("QueueUniom Time: " + double1);
		// }

		return new StatisticsSample(resultList);
	}

	public static void main(String[] args) {
		double lambda = 1.0; // arrival rate
		double mu1 = 2.0, mu2 = 4.0; // service rate
		int clientsSize = 10000;

		System.err.println(new Simulations().simulateCase1(lambda, mu1, mu2,
				clientsSize));

		// for (Double double1 : usersTime) {
		// System.out.println("User time: " + double1);
		// }
	}
}
