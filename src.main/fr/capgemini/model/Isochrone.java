package fr.capgemini.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Isochrone implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7163711927386734653L;

	private List<GeoPolygon> geoPolygones;

	public List<GeoPolygon> getGeoPolygones() {
		return geoPolygones;
	}

	public void setGeoPolygones(List<GeoPolygon> polygones) {
		this.geoPolygones = polygones;
	}

	public Isochrone() {
		this.geoPolygones = new ArrayList<>();
	}

	public Isochrone(List<GeoPolygon> polygones) {
		super();
		this.geoPolygones = polygones;
	}

}
