package br.ufrj.dcc.ad.model;

public class User {

	private double arrivalTime; // W
	private double serviceTime; // X

	public User() {
	}

	public User(double arrivalTime, double serviceTime) {
		super();
		this.arrivalTime = arrivalTime;
		this.serviceTime = serviceTime;
	}

	public double getArrivalTime() {
		return arrivalTime;
	}

	public void setArrivalTime(double arrivalTime) {
		this.arrivalTime = arrivalTime;
	}

	public double getServiceTime() {
		return serviceTime;
	}

	public void setServiceTime(double serviceTime) {
		this.serviceTime = serviceTime;
	}

	@Override
	public String toString() {
		return "User with arrival time: " + this.arrivalTime
				+ " and service time: " + this.serviceTime;
	}

}
