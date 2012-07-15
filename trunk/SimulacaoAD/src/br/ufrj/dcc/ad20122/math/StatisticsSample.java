package br.ufrj.dcc.ad20122.math;

import java.util.List;

import org.apache.commons.math3.distribution.TDistribution;
import org.apache.commons.math3.stat.descriptive.SummaryStatistics;

import br.ufrj.dcc.ad20122.model.Sample;

public class StatisticsSample {

	SummaryStatistics summaryStatistics;
	Sample sample;

	public StatisticsSample(List<Double> data) {

		this.summaryStatistics = new SummaryStatistics();
		for (Double double1 : data) {
			summaryStatistics.addValue(double1);
		}
	}

	public StatisticsSample(List<Double> data, Sample sample) {

		this.sample = sample;
		this.summaryStatistics = new SummaryStatistics();
		for (Double double1 : data) {
			summaryStatistics.addValue(double1);
		}
	}

	public double getMean() {
		return summaryStatistics.getMean();
	}

	public double getVariance() {
		return this.summaryStatistics.getVariance();
	}

	public double getStandardDeviation() {
		return this.summaryStatistics.getStandardDeviation();
	}

	private ConfidenceInterval getConfidenceInterval95FromMean() {
		double significance = 0.95;

		TDistribution tDist = new TDistribution(summaryStatistics.getN() - 1);
		double a = tDist.inverseCumulativeProbability(1.0 - significance / 2);
		double confidence = a * summaryStatistics.getStandardDeviation()
				/ Math.sqrt(summaryStatistics.getN());

		return new ConfidenceInterval(this.getMean() - confidence,
				this.getMean() + confidence);

	}

	@Override
	public String toString() {
		return (sample != null ? this.toStringWithSample() : this
				.toStringWithoutSample());
	}

	private String toStringWithSample() {
		return "Statistics - " + this.sample + " Mean:"
				+ this.getMean() + " Variance: " + this.getVariance()
				+ " ConfidenceInterval 95%: "
				+ this.getConfidenceInterval95FromMean();
	}

	private String toStringWithoutSample() {
		return "Statistics - Mean:" + this.getMean() + " Variance: "
				+ this.getVariance() + " StandardDeviation: "
				+ this.getStandardDeviation() + " ConfidenceInterval 95%: "
				+ this.getConfidenceInterval95FromMean();

	}

	public class ConfidenceInterval {
		private double lowerEndpoint;
		private double upperEndpoint;

		public ConfidenceInterval() {
		}

		public ConfidenceInterval(double lowerEndpoint, double upperEndpoint) {
			super();
			this.lowerEndpoint = lowerEndpoint;
			this.upperEndpoint = upperEndpoint;
		}

		public double getLowerEndpoint() {
			return lowerEndpoint;
		}

		public void setLowerEndpoint(double lowerEndpoint) {
			this.lowerEndpoint = lowerEndpoint;
		}

		public double getUpperEndpoint() {
			return upperEndpoint;
		}

		public void setUpperEndpoint(double upperEndpoint) {
			this.upperEndpoint = upperEndpoint;
		}

		@Override
		public String toString() {
			return "ConfidenceInterval: (" + this.lowerEndpoint + ", "
					+ this.upperEndpoint + ")";
		}
	}
}
