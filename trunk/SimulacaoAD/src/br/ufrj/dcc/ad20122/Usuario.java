package br.ufrj.dcc.ad20122;

public class Usuario {

	private double waitTime; // W
	private double serviceTime; // X

	public Usuario() {
	}

	public Usuario(double waitTime, double serviceTime) {
		super();
		this.waitTime = waitTime;
		this.serviceTime = serviceTime;
	}

	public double getWaitTime() {
		return waitTime;
	}

	public void setWaitTime(double waitTime) {
		this.waitTime = waitTime;
	}

	public double getServiceTime() {
		return serviceTime;
	}

	public void setServiceTime(double serviceTime) {
		this.serviceTime = serviceTime;
	}

	// T
	public double getTotalTime() {
		return this.waitTime + this.serviceTime;
	}

	@Override
	public String toString() {
		return "User with wait time: " + this.waitTime + " and service time: "
				+ this.serviceTime;
	}

}
