package fr.capgemini.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.annotations.Type;
import org.postgis.Geometry;
 
@Entity
public class Entreprise implements Serializable {
 
	private static final long serialVersionUID = 1L;
	
	
	@Column(name = "adressePostale")
	private String adressePostale;
	@Column(name = "codepostal")
	private String codepostal;
	//@Type(type = "org.hibernate.spatial.GeometryType")
	//@Column(columnDefinition = "Geometry(Point,4326)")
	
	@Column(name = "geom",  columnDefinition = "Geometry") 
	@Type(type = "org.hibernate.spatial.GeometryType")
	private Geometry geom;
	@Column(name = "nom")
	private String nom;
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private int id;

 
	/*public int getId() {
		return id;
	}*/
 
/*	public void setId(int id) {
		this.id = id;
	}
 */
	public String getNom() {
		return nom;
	}
 
	
	public void setNom(String nom) {
		this.nom = nom;
	}
	
	public String getAdressePostale() {
		return adressePostale;
	}

	public void setAdressePostale(String adressePostale) {
		this.adressePostale = adressePostale;
	}

	public String getCodepostal() {
		return codepostal;
	}

	public void setCodepostal(String codepostal) {
		this.codepostal = codepostal;
	}

	public Geometry getGeom() {
		return geom;
	}

	public void setGeom(Geometry geom) {
		this.geom = geom;
	}
	
	
	
	
	
	public Entreprise() {
		 
	}
	
	public Entreprise(String nom) {
		super(); 
		this.nom = nom;
	}

	public Entreprise(String nom, String adressePostale, String codepostal, Geometry geom) {
		super();
		//this.id = count++;
		this.nom = nom;
		this.adressePostale = adressePostale;
		this.codepostal = codepostal;
		this.geom = geom;
		
	}
 
	
}