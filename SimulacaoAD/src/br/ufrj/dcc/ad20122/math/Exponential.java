package br.ufrj.dcc.ad20122.math;

public class Exponential {

	private double lambda;
	private Uniform uniform;

	public Exponential(double lambda, long seed) {

		this.lambda = lambda;

		if (uniform == null)
			uniform = new Uniform(seed);

	}

	/**
	 * Return a real number from an exponential distribution with mean lambda.
	 * 
	 * @PDF - probability density function
	 */
	public double expPDF() {
		return -this.getMean() * Math.log(uniform.random());
	}

	/**
	 * Return a real number from an exponential distribution with mean lambda.
	 * 
	 * @CDF - cumulative distribution function
	 */
	public double expCDF() {
		return -this.getMean() * Math.log(1 - uniform.random());
	}

	/**
	 * Return the mean of an exponential distribution.
	 */
	public double getMean() {
		return 1.0 / this.lambda;
	}

	/**
	 * Return the variance of an exponential distribution.
	 */
	public double getVariance() {
		return 1.0 / (this.lambda * this.lambda);
	}

}