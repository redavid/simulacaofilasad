package br.ufrj.dcc.ad20122.math;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.math3.distribution.TDistribution;
import org.apache.commons.math3.stat.descriptive.SummaryStatistics;
import org.apache.commons.math3.stat.descriptive.moment.Mean;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import br.ufrj.dcc.ad20122.model.Sample;

public class StatisticsSample {

	List<Double> dataW;
	List<Double> dataT;

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

		this.dataW = dataW;
		this.dataT = dataT;

		this.sample = sample;
		this.summaryStatisticsW = new SummaryStatistics();
		for (Double double1 : dataW) {
			summaryStatisticsW.addValue(double1);
		}

		this.summaryStatisticsT = new SummaryStatistics();
		this.summaryStatisticsT.setMeanImpl(new Mean());

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

	// private ConfidenceInterval getWConfidenceInterval95(double value) {
	// double significance = 0.95;
	//
	// TDistribution tDist = new TDistribution(summaryStatisticsW.getN() - 1);
	// double a = tDist.inverseCumulativeProbability(1.0 - significance / 2);
	// double confidence = a * summaryStatisticsW.getStandardDeviation()
	// / Math.sqrt(summaryStatisticsW.getN());
	//
	// return new ConfidenceInterval(value - confidence, value + confidence);
	//
	// }

	private ConfidenceInterval getTConfidenceInterval95(double value) {
		double significance = 0.95;

		TDistribution tDist = new TDistribution(summaryStatisticsT.getN() - 1);
		double a = tDist.inverseCumulativeProbability(1.0 - significance / 2);
		double confidence = a * summaryStatisticsT.getStandardDeviation()
				/ Math.sqrt(summaryStatisticsT.getN());

		return new ConfidenceInterval(value - confidence, value + confidence);

	}

	private List<Double> getDataTMean() {
		List<Double> dataTMean = new ArrayList<Double>();
		Mean mean = new Mean();

		double[] dataTArray = ArrayUtils.toPrimitive(this.dataT
				.toArray(new Double[0]));
		dataTMean.add(dataTArray[0]);
		for (int i = 1; i < this.dataT.size(); i++) {

			// E[T]
			dataTMean.add(mean.evaluate(dataTArray, 0, i));

		}
		return dataTMean;
	}

	public void generateChart(String path) throws IOException {

		XYSeries series = new XYSeries("N por E[T]");

		List<Double> means = this.getDataTMean();
		for (int i = 0; i < means.size(); i++) {
			series.add(i, means.get(i));

		}
		XYSeriesCollection dataset = new XYSeriesCollection();
		dataset.addSeries(series);

		// Generate the graph
		JFreeChart chart = ChartFactory.createXYLineChart("N X E[T]", // Title
				"N", // x-axis Label
				"E[T]", // y-axis Label
				dataset, // Dataset
				PlotOrientation.VERTICAL, // Plot Orientation
				true, // Show Legend
				true, // Use tooltips
				false // Configure chart to generate URLs?
				);

		ChartUtilities.saveChartAsJPEG(new File(path), chart, 500, 300);

	}

	public String toStringTIndividual() {

		StringBuilder builder = new StringBuilder();
		builder.append("T invidual: \n");
		for (double double1 : this.getDataTMean()) {
			builder.append(("" + double1).replace(".", ","));
			builder.append("\n");
		}

		return builder.toString();
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
