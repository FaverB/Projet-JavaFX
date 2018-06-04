package com.objis.repertoire.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * 
 * @author ANAGKAZO
 *
 */
public class Codeur {

	private StringProperty nom;
	private StringProperty prenom;
	private StringProperty tel;
	private StringProperty tel2;

	private StringProperty promo;
	private StringProperty mail;

	public String getNom() {
		return nom.get();

	}

	public void setNom(String nom) {
		this.nom.set(nom);
	}

	public StringProperty nomProperty() {
		return nom;

	}

	public String getPrenom() {
		return prenom.get();
	}

	public void setPrenom(String prenom) {
		this.prenom.set(prenom);
	}

	public StringProperty prenomProperty() {
		return prenom;
	}

	public String getTel() {
		return tel.get();

	}

	public void setTel(String tel) {
		this.tel.set(tel);

	}

	public StringProperty telProperty() {
		return tel;

	}

	public String getTel2() {
		return tel2.get();
	}

	public void setTel2(String tel2) {
		this.tel2.set(tel2);
	}
	
	public StringProperty tel2Property() {
		return tel;

	}

	public String getPromo() {
		return promo.get();
	}

	public void setPromo(String promo) {
		this.promo.set(promo);
	}

	public String getEmail() {
		return mail.get();
	}

	public void setEmail(String mail) {
		this.mail.set(mail);
	}

	// Constructeurs
	public Codeur() {
		this(null, null);
	}

	public Codeur(String nom, String prenom) {
		this.nom = new SimpleStringProperty(nom);
		this.prenom = new SimpleStringProperty(prenom);
		this.tel = new SimpleStringProperty("225 00 00 00 00");
		this.mail = new SimpleStringProperty("objis@10000.com");

	}

	public Codeur(String nom, String prenom, String tel, String promo, String email) {
		super();
		this.nom = new SimpleStringProperty(nom);
		this.prenom = new SimpleStringProperty(prenom);
		this.tel = new SimpleStringProperty(tel);
		this.promo = new SimpleStringProperty(promo);
		this.mail = new SimpleStringProperty(email);
	}

}
