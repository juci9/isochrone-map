package fr.capgemini.model;

import java.io.Serializable;

public class Coordonnees implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3765277091680218669L;

	private double longitude;
	private double latitude;

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public Coordonnees() {

	}

	public Coordonnees(double longitude, double latitude) {
		super();
		this.longitude = longitude;
		this.latitude = latitude;
	}

	@Override
	public String toString() {
		return "Coordonnees [longitude=" + longitude + ", latitude=" + latitude + "]";
	}

}
