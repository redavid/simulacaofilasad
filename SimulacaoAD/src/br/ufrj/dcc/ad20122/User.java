package br.ufrj.dcc.ad20122;

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

	public double getWaitTime() {
		return arrivalTime;
	}

	public void setWaitTime(double waitTime) {
		this.arrivalTime = waitTime;
	}

	public double getServiceTime() {
		return serviceTime;
	}

	public void setServiceTime(double serviceTime) {
		this.serviceTime = serviceTime;
	}

	// T
	public double getTotalTime() {
		return this.arrivalTime + this.serviceTime;
	}

	@Override
	public String toString() {
		return "User with wait time: " + this.arrivalTime + " and service time: "
				+ this.serviceTime;
	}

}
