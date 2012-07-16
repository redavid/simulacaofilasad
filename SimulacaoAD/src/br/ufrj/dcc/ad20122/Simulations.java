package br.ufrj.dcc.ad20122;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

import org.apache.commons.math3.distribution.ExponentialDistribution;
import org.apache.commons.math3.distribution.NormalDistribution;

import br.ufrj.dcc.ad20122.math.StatisticsSample;
import br.ufrj.dcc.ad20122.model.Sample;
import br.ufrj.dcc.ad20122.model.SimulationCase;
import br.ufrj.dcc.ad20122.model.SimulationType;

public class Simulations {

	// Case Deterministic - Exponencial
	public StatisticsSample simulateDetExpCase1(Sample sample) {

		Deque<User> queue1 = new ArrayDeque<User>();
		Deque<User> queue2 = new ArrayDeque<User>();

		// W time
		List<Double> timeW1 = new ArrayList<Double>();
		List<Double> timeW2 = new ArrayList<Double>();

		// T time
		List<Double> timeT1 = new ArrayList<Double>();
		List<Double> timeT2 = new ArrayList<Double>();

		double serviceTime = 1.0 / sample.getMu1();

		ExponentialDistribution arrivalExp = new ExponentialDistribution(
				sample.getLambda());
		ExponentialDistribution service2Exp = new ExponentialDistribution(
				1.0 / sample.getMu2());

		double nextArrival = arrivalExp.sample();

		double nextService1 = nextArrival + serviceTime;
		double nextService2 = Double.POSITIVE_INFINITY;

		boolean stopCondition = true;

		while (stopCondition) {

			if (nextArrival <= nextService1 && nextArrival <= nextService2) {

				User newUser = new User(nextArrival, serviceTime);

				if (queue1.isEmpty() && queue2.isEmpty()) {
					nextService1 = nextArrival + serviceTime;
				} else if (queue1.isEmpty() && !queue2.isEmpty()) {
					User interrupedUser = queue2.peekFirst();
					interrupedUser.setServiceTime(nextService2 - nextArrival);
					nextService2 = Double.POSITIVE_INFINITY;
					nextService1 = nextArrival + serviceTime;
				}

				queue1.add(newUser);
				nextArrival += arrivalExp.sample();

			} else if (nextService1 <= nextService2) {

				User actualUserQueue1 = queue1.removeFirst();
				double tempo1 = nextService1
						- actualUserQueue1.getArrivalTime();

				if (timeW1.size() < sample.getSampleSize()) {
					timeW1.add(tempo1);
					timeT1.add(tempo1 + actualUserQueue1.getServiceTime());
				}

				User newUser2 = new User(nextService1, service2Exp.sample());
				nextService2 = nextService1;// + newUser2.getServiceTime();
				queue2.addFirst(newUser2);

				if (queue1.isEmpty()) {
					nextService1 = nextArrival + serviceTime;
				} else {
					double nextServiceTime = queue1.peekFirst()
							.getServiceTime();
					nextService1 += nextServiceTime;

					if (!queue2.isEmpty()) {
						nextService2 += nextServiceTime;
					}

				}

			} else {
				User actualUserQueue2 = queue2.removeFirst();
				double tempo2 = nextService2
						- actualUserQueue2.getArrivalTime();

				timeW2.add(tempo2);// + actualUserQueue2.getServiceTime());
				timeT2.add(tempo2 + actualUserQueue2.getServiceTime());

				// update the queue
				if (queue2.isEmpty()) {
					nextService2 = Double.POSITIVE_INFINITY;
				} else {
					nextService2 += queue2.peekFirst().getServiceTime();
				}
			}

			stopCondition = timeW2.size() < sample.getSampleSize();

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

		List<Double> timesW = new ArrayList<Double>();
		for (int i = 0; i < sample.getSampleSize(); i++) {
			double time1 = timeW1.get(i);
			double time2 = timeW2.get(i);

			timesW.add(time1 + time2);
		}

		List<Double> timesT = new ArrayList<Double>();
		for (int i = 0; i < sample.getSampleSize(); i++) {
			double time1 = timeT1.get(i);
			double time2 = timeT2.get(i);

			timesT.add(time1 + time2);
		}

		// for (Double double1 : resultList) {
		// System.out.println("QueueUniom Time: " + double1);
		// }

		return new StatisticsSample(timesW, timesT, sample);
	}

	// Case Deterministic - Exponencial
	public StatisticsSample simulateDetExpCase2(Sample sample) {

		Deque<User> queue1 = new ArrayDeque<User>();
		Deque<User> queue2 = new ArrayDeque<User>();

		// W time
		List<Double> timeW1 = new ArrayList<Double>();
		List<Double> timeW2 = new ArrayList<Double>();

		// T time
		List<Double> timeT1 = new ArrayList<Double>();
		List<Double> timeT2 = new ArrayList<Double>();

		double serviceTime = 1.0 / sample.getMu1();

		ExponentialDistribution arrivalExp = new ExponentialDistribution(
				sample.getLambda());
		ExponentialDistribution service2Exp = new ExponentialDistribution(
				1.0 / sample.getMu2());

		double nextArrival = arrivalExp.sample();

		double nextService1 = nextArrival + serviceTime;
		double nextService2 = Double.POSITIVE_INFINITY;

		boolean stopCondition = true;

		while (stopCondition) {

			if (nextArrival <= nextService1 && nextArrival <= nextService2) {

				User newUser = new User(nextArrival, serviceTime);

				if (queue1.isEmpty() && queue2.isEmpty()) {
					nextService1 = nextArrival + newUser.getServiceTime();
				} else if (queue1.isEmpty() && !queue2.isEmpty()) {
					User interrupedUser = queue2.peekFirst();
					interrupedUser.setServiceTime(nextService2 - nextArrival);
					nextService2 = Double.POSITIVE_INFINITY;
					nextService1 = nextArrival + newUser.getServiceTime();
				}

				queue1.add(newUser);
				nextArrival += arrivalExp.sample();

			} else if (nextService1 <= nextService2) {

				User actualUserQueue1 = queue1.removeFirst();
				double tempo1 = nextService1
						- actualUserQueue1.getArrivalTime();

				if (timeW1.size() < sample.getSampleSize()) {
					timeW1.add(tempo1);
					timeT1.add(tempo1 + actualUserQueue1.getServiceTime());
				}

				User newUser2 = new User(nextService1, service2Exp.sample());
				nextService2 = nextService1;// + newUser2.getServiceTime();
				queue2.addLast(newUser2);

				if (queue1.isEmpty()) {
					nextService1 = nextArrival + newUser2.getServiceTime();
				} else {
					double nextServiceTime = queue1.peekFirst()
							.getServiceTime();
					nextService1 += nextServiceTime;

					if (!queue2.isEmpty()) {
						nextService2 += nextServiceTime;
					}

				}

			} else {
				User actualUserQueue2 = queue2.removeFirst();
				double tempo2 = nextService2
						- actualUserQueue2.getArrivalTime();

				timeW2.add(tempo2);// + actualUserQueue2.getServiceTime());
				timeT2.add(tempo2 + actualUserQueue2.getServiceTime());

				// update the queue
				if (queue2.isEmpty()) {
					nextService2 = Double.POSITIVE_INFINITY;
				} else {
					nextService2 += queue2.peekFirst().getServiceTime();
				}
			}

			stopCondition = timeW2.size() < sample.getSampleSize();

		}

		List<Double> resultList = new ArrayList<Double>();
		for (int i = 0; i < sample.getSampleSize(); i++) {
			double time1 = timeW1.get(i);
			double time2 = timeW2.get(i);

			resultList.add(time1 + time2);
		}

		List<Double> timesT = new ArrayList<Double>();
		for (int i = 0; i < sample.getSampleSize(); i++) {
			double time1 = timeT1.get(i);
			double time2 = timeT2.get(i);

			timesT.add(time1 + time2);
		}

		return new StatisticsSample(resultList, timesT, sample);
	}

	// Case Normal - Exponencial
	public StatisticsSample simulateNormalExpCase1(Sample sample) {

		Deque<User> queue1 = new ArrayDeque<User>();
		Deque<User> queue2 = new ArrayDeque<User>();

		// W time
		List<Double> timeW1 = new ArrayList<Double>();
		List<Double> timeW2 = new ArrayList<Double>();

		// T time
		List<Double> timeT1 = new ArrayList<Double>();
		List<Double> timeT2 = new ArrayList<Double>();

		ExponentialDistribution arrivalExp = new ExponentialDistribution(
				sample.getLambda());

		NormalDistribution service1Normal = new NormalDistribution(
				1.0 / sample.getMean(), sample.getStandardDeviation());

		ExponentialDistribution service2Exp = new ExponentialDistribution(
				1.0 / sample.getMu2());

		double nextArrival = arrivalExp.sample();

		double nextService1 = Double.POSITIVE_INFINITY;
		double nextService2 = Double.POSITIVE_INFINITY;

		boolean stopCondition = true;

		while (stopCondition) {

			if (nextArrival <= nextService1 && nextArrival <= nextService2) {

				User newUser = new User(nextArrival,
						this.getSampleTruncatedNormal(service1Normal));

				if (queue1.isEmpty() && queue2.isEmpty()) {
					nextService1 = nextArrival + newUser.getServiceTime();
				} else if (queue1.isEmpty() && !queue2.isEmpty()) {
					User interrupedUser = queue2.peekFirst();
					interrupedUser.setServiceTime(nextService2 - nextArrival);
					nextService2 = Double.POSITIVE_INFINITY;
					nextService1 = nextArrival + newUser.getServiceTime();
				}

				queue1.add(newUser);
				nextArrival += arrivalExp.sample();

			} else if (nextService1 <= nextService2) {

				User actualUserQueue1 = queue1.removeFirst();
				double tempo1 = nextService1
						- actualUserQueue1.getArrivalTime();

				if (timeW1.size() < sample.getSampleSize()) {
					timeW1.add(tempo1);
					timeT1.add(tempo1 + actualUserQueue1.getServiceTime());

				}

				User newUser2 = new User(nextService1, service2Exp.sample());
				nextService2 = nextService1 + newUser2.getServiceTime();
				queue2.addFirst(newUser2);

				if (queue1.isEmpty()) {
					nextService1 = Double.POSITIVE_INFINITY;
				} else {
					double nextServiceTime = queue1.peekFirst()
							.getServiceTime();
					nextService1 += nextServiceTime;

					if (!queue2.isEmpty()) {
						nextService2 += nextServiceTime;
					}

				}

			} else {
				User actualUserQueue2 = queue2.removeFirst();
				double temp2 = nextService2 - actualUserQueue2.getArrivalTime();

				timeW2.add(temp2);
				timeT2.add(temp2 + actualUserQueue2.getServiceTime());

				// update the queue
				if (queue2.isEmpty()) {
					nextService2 = Double.POSITIVE_INFINITY;
				} else {
					nextService2 += queue2.peekFirst().getServiceTime();
				}
			}

			stopCondition = timeW2.size() < sample.getSampleSize();

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
		for (int i = 0; i < sample.getSampleSize(); i++) {
			double time1 = timeW1.get(i);
			double time2 = timeW2.get(i);

			resultList.add(time1 + time2);
		}

		// for (Double double1 : resultList) {
		// System.out.println("QueueUniom Time: " + double1);
		// }

		List<Double> timesT = new ArrayList<Double>();
		for (int i = 0; i < sample.getSampleSize(); i++) {
			double time1 = timeT1.get(i);
			double time2 = timeT2.get(i);

			timesT.add(time1 + time2);
		}

		return new StatisticsSample(resultList, timesT, sample);
	}

	private double getSampleTruncatedNormal(
			NormalDistribution normalDistribution) {
		double truncatedValue = Double.NEGATIVE_INFINITY;

		while ((truncatedValue = normalDistribution.sample()) <= 0) {
		}

		return truncatedValue;
	}

	public static void main(String[] args) {

		// arrival rate
		// double lambda1 = 1.0;
		// service rate
		// double mu1 = 2.0
		double mu2 = 4.0;
		int sampleSize = 2;

		double lambda2 = 0.5;
		double mean = 10.0;
		double standardDeviation = 1;

		Sample sample;
		// sample = new Sample(SimulationCase.CASE1,
		// SimulationType.DETERMINISTIC, sampleSize, lambda1, mu1, mu2);
		//
		// System.out.println(new Simulations().simulateDetExpCase1(sample));

		sample = new Sample(SimulationCase.CASE1, SimulationType.NORMAL,
				sampleSize, lambda2, mean, standardDeviation, mu2);

		System.out.println(new Simulations().simulateNormalExpCase1(sample));

		// for (Double double1 : usersTime) {
		// System.out.println("User time: " + double1);
		// }
	}
}
