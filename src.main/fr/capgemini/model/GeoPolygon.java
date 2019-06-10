package fr.capgemini.model;

import java.io.Serializable;

public class GeoPolygon implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3610101891683648804L;
	private String sommets;

	public String getSommets() {
		return sommets;
	}

	public void setSommets(String sommets) {
		this.sommets = sommets;
	}

	public GeoPolygon() {
		
	}

	public GeoPolygon(String sommets) {
		super();
		this.sommets = sommets;
	}

}
