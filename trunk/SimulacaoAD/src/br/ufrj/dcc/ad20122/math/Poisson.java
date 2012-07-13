package br.ufrj.dcc.ad20122.math;

public class Poisson {

	private double lambda;
	private Uniform uniform;

	public Poisson(double lambda, long seed) {

		this.lambda = lambda;

		if (uniform == null)
			uniform = new Uniform(seed);
	}

	/**
	 * Return an integer with a Poisson distribution with mean lambda.
	 */
	public int getPoissonNumber() {
		// using algorithm given by Knuth
		// see http://en.wikipedia.org/wiki/Poisson_distribution
		int k = 0;
		double p = 1.0;
		double L = Math.exp(this.lambda);
		do {
			k++;
			p *= this.uniform.random();
		} while (p >= L);
		return k - 1;
	}

	/**
	 * Return the mean of an exponential distribution.
	 */
	public double getMean() {
		return this.lambda;
	}

	/**
	 * Return the variance of an exponential distribution.
	 */
	public double getVariance() {
		return this.lambda;
	}
}
