package br.ufrj.dcc.ad20122;


public class Simulations {

	public void simulateCase1(double lambda, double mu1, double mu2,
			int clientsSize) {

		// Queue<Double> queue1 = new LinkedList<Double>();
		// Stack<Double> queue2 = new Stack<Double>();
		//
		// List<Double> usersTime = new ArrayList<Double>();
		//
		// double serviceTIme1 = 1.0 / mu1;
		// double serviceTIme2 = Double.POSITIVE_INFINITY;
		//
		// ExponentialDistribution arrivalExp = new
		// ExponentialDistribution(lambda);
		// ExponentialDistribution service2Exp = new ExponentialDistribution(
		// 1.0 / mu2);
		//
		// // time of next arrival
		// double nextArrival = arrivalExp.sample();
		//
		// // time of next completed service
		// double nextService = nextArrival + serviceTIme1;

		// System.out.println("Simulate 2 Queues.");

		// simulate the M/D/1 queue
		while (clientsSize-- > 0) {

			// while(nextArrival < serviceTIme1 && nextArrival < serviceTIme2){
			// User newUser = new User(waitTime, serviceTime)
			// }

			// // next event is an arrival
			// while (nextArrival < nextService) {
			// queue.add(nextArrival);
			// nextArrival += arrivalExp.sample();
			// }
			//
			// // next event is a service completion
			// double arrival = queue.remove();
			// double time = nextService - arrival;
			//
			// usersTime.add(time);
			//
			// // update the queue
			// if (queue.isEmpty()) {
			// nextService = nextArrival + serviceTIme;
			// } else {
			// nextService = nextService + serviceTIme;
			// }
		}

	}
}
