package br.ufrj.dcc.ad20122;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

import br.ufrj.dcc.ad20122.math.StatisticsSample;
import br.ufrj.dcc.ad20122.model.Sample;
import br.ufrj.dcc.ad20122.model.SampleSimulation;

public class Main {

	private static final String BOOKSTORE_XML = "./samples/samples.xml";

	public static void main(String[] args) {

		Serializer serializer = new Persister();
		File source = new File(BOOKSTORE_XML);
		SampleSimulation simulations = null;
		try {
			simulations = serializer.read(SampleSimulation.class, source);
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}

		List<StatisticsSample> statistics = new ArrayList<StatisticsSample>();

		for (Sample sample : simulations.getSamples()) {

			switch (sample.getSimulationCase()) {
			case CASE1:

				switch (sample.getSimulationType()) {
				case DETERMINISTIC:
					statistics.add(new Simulations()
							.simulateDetExpCase1(sample));
					break;

				case NORMAL:

					break;
				default:
					break;
				}

				break;

			case CASE2:
				switch (sample.getSimulationType()) {
				case DETERMINISTIC:
					statistics.add(new Simulations()
							.simulateDetExpCase2(sample));
					break;

				case NORMAL:

					break;
				default:
					break;
				}

				break;

			default:
				break;
			}
		}

		for (StatisticsSample statisticsSample : statistics) {
			System.out.println(statisticsSample);
		}

	}
}
