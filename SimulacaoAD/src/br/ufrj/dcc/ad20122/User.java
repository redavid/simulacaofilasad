package br.ufrj.dcc.ad20122;

public class User {

	private double arrivalTime; // W
	private double serviceTime; // X
	private double time; // T

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

	public void setTime(double time) {
		this.time = time;
	}

	public double getTime() {
		return this.time;
	}

	@Override
	public String toString() {
		return "User with wait time: " + this.arrivalTime
				+ " and service time: " + this.serviceTime + " and Time: "
				+ this.time;
	}

}
