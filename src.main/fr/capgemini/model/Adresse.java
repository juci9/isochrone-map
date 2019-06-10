package fr.capgemini.model;

import java.io.Serializable;

public class Adresse implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8029668550240646675L;

	private int numero;
	private TypeVoie voie;
	private String nomVoie;
	private String codePostal;
	private String ville;

	public int getNumero() {
		return numero;
	}

	public void setNumero(int numero) {
		this.numero = numero;
	}

	public TypeVoie getVoie() {
		return voie;
	}

	public void setVoie(TypeVoie voie) {
		this.voie = voie;
	}

	public String getNomVoie() {
		return nomVoie;
	}

	public void setNomVoie(String nomVoie) {
		this.nomVoie = nomVoie;
	}

	public String getCodePostal() {
		return codePostal;
	}

	public void setCodePostal(String codePostal) {
		this.codePostal = codePostal;
	}

	public String getVille() {
		return ville;
	}

	public void setVille(String ville) {
		this.ville = ville;
	}

	public Adresse() {

	}

	public Adresse(int numero, TypeVoie voie, String nomVoie, String codePostal, String ville) {
		super();
		this.numero = numero;
		this.voie = voie;
		this.nomVoie = nomVoie;
		this.codePostal = codePostal;
		this.ville = ville;
	}

}
