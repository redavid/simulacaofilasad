package br.ufrj.dcc.ad.math;

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

import br.ufrj.dcc.ad.model.Sample;

public class StatisticsSample {

	List<Double> dataW;
	List<Double> dataT;
	List<Double> meansT;

	SummaryStatistics summaryStatisticsW;
	SummaryStatistics summaryStatisticsT;
	SummaryStatistics summaryStatisticsMeansT;
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
		this.meansT = this.generateDataTMean();
		this.sample = sample;

		this.summaryStatisticsW = new SummaryStatistics();
		for (Double double1 : dataW) {
			summaryStatisticsW.addValue(double1);
		}

		this.summaryStatisticsT = new SummaryStatistics();
		for (Double double1 : dataT) {
			summaryStatisticsT.addValue(double1);
		}

		this.summaryStatisticsMeansT = new SummaryStatistics();
		for (Double double1 : meansT) {
			this.summaryStatisticsMeansT.addValue(double1);
		}

	}

	public List<Double> getMeansT() {
		return meansT;
	}

	public void setMeansT(List<Double> meansT) {
		this.meansT = meansT;
	}

	public Sample getSample() {
		return sample;
	}

	public void setSample(Sample sample) {
		this.sample = sample;
	}

	public double getWMean() {
		return summaryStatisticsW.getMean();
	}

	public double getTMean() {
		return summaryStatisticsT.getMean();
	}

	public double getMeansTMean() {
		return summaryStatisticsMeansT.getMean();
	}

	public double getWVariance() {
		return this.summaryStatisticsW.getVariance();
	}

	public double getTVariance() {
		return this.summaryStatisticsT.getVariance();
	}

	public double getMeansTVariance() {
		return this.summaryStatisticsMeansT.getVariance();
	}

	public double getWStandardDeviation() {
		return this.summaryStatisticsW.getStandardDeviation();
	}

	public double getTStandardDeviation() {
		return this.summaryStatisticsT.getStandardDeviation();
	}

	public double getMeansTStandardDeviation() {
		return this.summaryStatisticsMeansT.getStandardDeviation();
	}

	private ConfidenceInterval getMeansTConfidenceInterval95(double value) {
		double significance = 0.95;

		TDistribution tDist = new TDistribution(
				summaryStatisticsMeansT.getN() - 1);
		double a = tDist.inverseCumulativeProbability(1.0 - significance / 2);
		double confidence = a * summaryStatisticsMeansT.getStandardDeviation()
				/ Math.sqrt(summaryStatisticsMeansT.getN());

		return new ConfidenceInterval(value - confidence, value + confidence);

	}

	private List<Double> generateDataTMean() {
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

		XYSeries series = new XYSeries(sample.getSimulationCase().name());

		List<Double> means = this.meansT;
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

	public void generateChartJoinedStatistics(String path,
			StatisticsSample statistics2) throws IOException {

		XYSeries series1 = new XYSeries(sample.getSimulationCase().name());

		List<Double> means = this.meansT;
		for (int i = 0; i < means.size(); i++) {
			series1.add(i, means.get(i));
		}

		XYSeries series2 = new XYSeries(statistics2.getSample()
				.getSimulationCase().name());

		List<Double> means2 = statistics2.getMeansT();
		for (int i = 0; i < means.size(); i++) {
			series2.add(i, means2.get(i));
		}

		XYSeriesCollection dataset = new XYSeriesCollection();
		dataset.addSeries(series1);
		dataset.addSeries(series2);

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
		for (double double1 : this.meansT) {
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
		return "" + this.sample + " E[T]:" + this.getTMean() + ", Var[T]: "
				+ this.getTVariance() + ", E[T] ConfidenceInterval: "
				+ this.getMeansTConfidenceInterval95(this.getTMean())
				+ ", Var[T] ConfidenceInterval: "
				+ this.getMeansTConfidenceInterval95(this.getTVariance());
	}

	private String toStringWithoutSample() {
		return "E[T]:" + this.getTMean() + ", Var[T]: " + this.getTVariance()
				+ ", E[T] ConfidenceInterval: "
				+ this.getMeansTConfidenceInterval95(this.getTMean())
				+ ", Var[T] ConfidenceInterval: "
				+ this.getMeansTConfidenceInterval95(this.getTVariance());

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
