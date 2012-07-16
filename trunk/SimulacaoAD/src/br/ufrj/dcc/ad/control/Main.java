package br.ufrj.dcc.ad.control;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

import br.ufrj.dcc.ad.math.StatisticsSample;
import br.ufrj.dcc.ad.model.Sample;
import br.ufrj.dcc.ad.model.SampleSimulation;

public class Main {

	private static final String SAMPLE_XML = "./samples/samples.xml";
	private static final String CHART = "./samples/chart.jpg";

	public static void main(String[] args) {

		Serializer serializer = new Persister();
		File source = new File(SAMPLE_XML);
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

		int i = 0;
		for (StatisticsSample statisticsSample : statistics) {
			String statisticsString = statisticsSample.toString();
			System.out.println(statisticsString);

			switch (statisticsSample.getSample().getSimulationCase()) {

			case CASE1:
				try {
					statisticsSample.generateChart(CHART.replace(".jpg", i
							+ ".jpg"));
				} catch (IOException e) {
					e.printStackTrace();
				}
				break;

			case CASE2:
				try {
					statisticsSample.generateChart(CHART.replace(".jpg", i
							+ ".jpg"));
				} catch (IOException e) {
					e.printStackTrace();
				}

				try {
					statisticsSample.generateChartJoinedStatistics(
							CHART.replace(".jpg", (i - 3) + "_" + i + ".jpg"),
							statistics.get((i - 3)));
				} catch (IOException e) {
					e.printStackTrace();
				}

				break;

			default:
				break;
			}
			i++;
		}

	}
}
