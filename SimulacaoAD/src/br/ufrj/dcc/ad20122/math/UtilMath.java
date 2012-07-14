package br.ufrj.dcc.ad20122.math;

import java.util.List;

public class UtilMath {

	// E[x]
	public static double getMean(List<Double> list) {
		double mean = 0.0;

		for (Double double1 : list) {
			mean += double1;
		}

		mean /= list.size();

		return mean;
	}

	// Var[X] = E[(X - mi)^2] = E[X^2] - (E[X})^2
	public static double getVariance(List<Double> list) {
		double variance = 0.0;
		double mean = UtilMath.getMean(list);

		for (Double double1 : list) {
			variance += double1 * double1 - mean * mean;
		}

		variance /= list.size();

		return variance;
	}

	// rho =  sqrt(Var[X])
	public static double getStandardDeviation(List<Double> list) {
		return Math.sqrt(UtilMath.getVariance(list));
	}
}
