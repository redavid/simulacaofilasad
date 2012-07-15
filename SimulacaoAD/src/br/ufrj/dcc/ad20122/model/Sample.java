package br.ufrj.dcc.ad20122.model;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root(name = "sample")
public class Sample {

	@Attribute(name = "case")
	private SimulationCase simulationCase;

	@Element(name = "size")
	private int sampleSize;
	@Element
	private double lambda;
	@Element
	private double mu1;
	@Element
	private double mu2;

	public Sample() {
	}

	public Sample(SimulationCase simulationCase, int sampleSize, double lambda,
			double mu1, double mu2) {
		super();
		this.simulationCase = simulationCase;
		this.sampleSize = sampleSize;
		this.lambda = lambda;
		this.mu1 = mu1;
		this.mu2 = mu2;
	}

	public SimulationCase getSimulationCase() {
		return simulationCase;
	}

	public void setSimulationCase(SimulationCase simulationCase) {
		this.simulationCase = simulationCase;
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

	public double getMu2() {
		return mu2;
	}

	public void setMu2(double mu2) {
		this.mu2 = mu2;
	}

	@Override
	public String toString() {
		return "Sample [simulationCase=" + simulationCase + ", sampleSize="
				+ sampleSize + ", lambda=" + lambda + ", mu1=" + mu1 + ", mu2="
				+ mu2 + "]";
	}

}
