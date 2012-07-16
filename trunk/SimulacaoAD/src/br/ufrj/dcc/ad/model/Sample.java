package br.ufrj.dcc.ad.model;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root(name = "sample")
public class Sample {

	@Attribute(name = "case")
	private SimulationCase simulationCase;

	@Attribute(name = "type")
	private SimulationType simulationType;

	@Element(name = "size")
	private int sampleSize;

	// lambda
	@Element
	private double lambda;

	// Service1 - Derterministic
	@Element(required = false)
	private double mu1;

	// Service1 - Normal
	@Element(required = false)
	private double mean;

	@Element(required = false)
	private double standardDeviation;

	// Service2
	@Element
	private double mu2;

	public Sample() {
	}

	public Sample(SimulationCase simulationCase, SimulationType simulationType,
			int sampleSize, double lambda, double mean,
			double standardDeviation, double mu2) {
		super();
		this.simulationCase = simulationCase;
		this.simulationType = simulationType;
		this.sampleSize = sampleSize;
		this.lambda = lambda;
		this.mean = mean;
		this.standardDeviation = standardDeviation;
		this.mu2 = mu2;
	}

	public Sample(SimulationCase simulationCase, SimulationType simulationType,
			int sampleSize, double lambda, double mu1, double mu2) {
		super();
		this.simulationCase = simulationCase;
		this.simulationType = simulationType;
		this.sampleSize = sampleSize;
		this.lambda = lambda;
		this.mu1 = mu1;
		this.mu2 = mu2;
	}

	public Sample(SimulationCase simulationCase, SimulationType simulationType,
			int sampleSize, double lambda, double mu1, double mean,
			double standardDeviation, double mu2) {
		super();
		this.simulationCase = simulationCase;
		this.simulationType = simulationType;
		this.sampleSize = sampleSize;
		this.lambda = lambda;
		this.mu1 = mu1;
		this.mean = mean;
		this.standardDeviation = standardDeviation;
		this.mu2 = mu2;
	}

	public SimulationCase getSimulationCase() {
		return simulationCase;
	}

	public void setSimulationCase(SimulationCase simulationCase) {
		this.simulationCase = simulationCase;
	}

	public SimulationType getSimulationType() {
		return simulationType;
	}

	public void setSimulationType(SimulationType simulationType) {
		this.simulationType = simulationType;
	}

	public int getSampleSize() {
		return sampleSize;
	}

	public void setSampleSize(int sampleSize) {
		this.sampleSize = sampleSize;
	}

	public double getLambda() {
		return lambda;
	}

	public void setLambda(double lambda) {
		this.lambda = lambda;
	}

	public double getMu1() {
		return mu1;
	}

	public void setMu1(double mu1) {
		this.mu1 = mu1;
	}

	public double getMean() {
		return mean;
	}

	public void setMean(double mean) {
		this.mean = mean;
	}

	public double getStandardDeviation() {
		return standardDeviation;
	}

	public void setStandardDeviation(double standardDeviation) {
		this.standardDeviation = standardDeviation;
	}

	public double getMu2() {
		return mu2;
	}

	public void setMu2(double mu2) {
		this.mu2 = mu2;
	}

	@Override
	public String toString() {
		return (this.simulationType == SimulationType.DETERMINISTIC ? this
				.toStringDeterministic() : this.toStringNormal());
	}

	private String toStringDeterministic() {
		return "Sample [simulationCase=" + simulationCase + ", sampleSize="
				+ sampleSize + ", lambda=" + lambda + ", mu1=" + mu1 + ", mu2="
				+ mu2 + "]";

	}

	private String toStringNormal() {
		return "Sample [simulationCase=" + simulationCase + ", sampleSize="
				+ sampleSize + ", lambda=" + lambda + ", mean=" + mean
				+ ", standardDeviation=" + standardDeviation + ", mu2=" + mu2
				+ "]";
	}

}
