package br.ufrj.dcc.ad20122.math;

import java.util.Random;

public class Uniform {

	private Random random;

	public Uniform(long seed) {
		random = new Random(seed);
	}

	/**
	 * Return real number uniformly in [0, 1).
	 */
	public double random() {
		return random.nextDouble();
	}

	/**
	 * Return an integer uniformly between 0 and N-1.
	 */
	public int random(int N) {
		return random.nextInt(N);
	}

}
