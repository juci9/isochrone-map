package fr.capgemini.webservices.navitia;

import java.time.LocalDateTime;

import fr.capgemini.model.Coordonnees;
import fr.capgemini.webservices.common.IsochronesInfosUrl;

/**
 * Classe contenant les informations nécessaires pour fabriquer une url navitia
 * 
 * @author aulecoin
 *
 */
public class NavitiaInfosUrl extends IsochronesInfosUrl {
	private LocalDateTime date;
	private String codePostal;

	public String getCodePostal() {
		return codePostal;
	}

	public void setCodePostal(String codePostal) {
		this.codePostal = codePostal;
	}

	public LocalDateTime getDate() {
		return date;
	}

	public void setDate(LocalDateTime date) {
		this.date = date;
	}

	public NavitiaInfosUrl() {
		super();
	}

	public NavitiaInfosUrl(Coordonnees coord, LocalDateTime date, int maxValue, String codeP, int intervalle) {
		this.date = date;
		this.setCoord(coord);
		this.setMaxValue(maxValue);
		this.codePostal = codeP;
		this.setIntervalle(intervalle);
	}

}
