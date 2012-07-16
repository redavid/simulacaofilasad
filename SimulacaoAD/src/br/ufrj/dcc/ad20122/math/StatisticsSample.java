package br.ufrj.dcc.ad20122.math;

import java.util.List;

import org.apache.commons.math3.distribution.TDistribution;
import org.apache.commons.math3.stat.descriptive.SummaryStatistics;

import br.ufrj.dcc.ad20122.model.Sample;

public class StatisticsSample {

	SummaryStatistics summaryStatisticsW;
	SummaryStatistics summaryStatisticsT;
	Sample sample;

	public StatisticsSample(List<Double> data) {

		this.summaryStatisticsW = new SummaryStatistics();
		for (Double double1 : data) {
			summaryStatisticsW.addValue(double1);
		}
	}

	public StatisticsSample(List<Double> dataW, List<Double> dataT,
			Sample sample) {

		this.sample = sample;
		this.summaryStatisticsW = new SummaryStatistics();
		for (Double double1 : dataW) {
			summaryStatisticsW.addValue(double1);
		}

		this.summaryStatisticsT = new SummaryStatistics();
		for (Double double1 : dataT) {
			summaryStatisticsT.addValue(double1);
		}
	}

	public double getWMean() {
		return summaryStatisticsW.getMean();
	}

	public double getTMean() {
		return summaryStatisticsT.getMean();
	}

	public double getWVariance() {
		return this.summaryStatisticsW.getVariance();
	}

	public double getTVariance() {
		return this.summaryStatisticsT.getVariance();
	}

	public double getWStandardDeviation() {
		return this.summaryStatisticsW.getStandardDeviation();
	}

	public double getTStandardDeviation() {
		return this.summaryStatisticsT.getStandardDeviation();
	}

	private ConfidenceInterval getWConfidenceInterval95(double value) {
		double significance = 0.95;

		TDistribution tDist = new TDistribution(summaryStatisticsW.getN() - 1);
		double a = tDist.inverseCumulativeProbability(1.0 - significance / 2);
		double confidence = a * summaryStatisticsW.getStandardDeviation()
				/ Math.sqrt(summaryStatisticsW.getN());

		return new ConfidenceInterval(value - confidence, value + confidence);

	}

	private ConfidenceInterval getTConfidenceInterval95(double value) {
		double significance = 0.95;

		TDistribution tDist = new TDistribution(summaryStatisticsT.getN() - 1);
		double a = tDist.inverseCumulativeProbability(1.0 - significance / 2);
		double confidence = a * summaryStatisticsT.getStandardDeviation()
				/ Math.sqrt(summaryStatisticsT.getN());

		return new ConfidenceInterval(value - confidence, value + confidence);

	}

	@Override
	public String toString() {
		return (sample != null ? this.toStringWithSample() : this
				.toStringWithoutSample());
	}

	private String toStringWithSample() {
		return "" + this.sample + " E[W]:" + this.getWMean() + " E[T]:"
				+ this.getTMean() + ", Var[T]: " + this.getTVariance()
				+ ", E[T] ConfidenceInterval: "
				+ this.getTConfidenceInterval95(this.getTMean())
				+ ", Var[T] ConfidenceInterval: "
				+ this.getTConfidenceInterval95(this.getTVariance());
	}

	private String toStringWithoutSample() {
		return " E[W]:" + this.getWMean() + " E[T]:" + this.getTMean()
				+ ", Var[T]: " + this.getTVariance()
				+ ", E[T] ConfidenceInterval: "
				+ this.getTConfidenceInterval95(this.getTMean())
				+ ", Var[T] ConfidenceInterval: "
				+ this.getTConfidenceInterval95(this.getTVariance());

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
			return "(" + this.lowerEndpoint + ", " + this.upperEndpoint + ")";
		}
	}
}
