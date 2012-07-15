package br.ufrj.dcc.ad20122.model;

import java.util.List;

import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

@Root(name = "simulations")
public class SampleSimulation {

	@ElementList()
	private List<Sample> samples;

	public List<Sample> getSamples() {
		return samples;
	}

	public void setSamples(List<Sample> samples) {
		this.samples = samples;
	}

}
