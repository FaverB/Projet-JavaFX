package com.objis.repertoire.vue;

import com.objis.repertoire.Repertoire;
import com.objis.repertoire.model.Codeur;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class EditCodeurControleur {

	@FXML
	private TextField txFnom;
	
	@FXML
	private TextField txFprenom;
	
	@FXML
	private TextField txFtel;
	
	@FXML
	private TextField txFtel2;
	
	@FXML
	private TextField txFmail;
	
	@FXML
	private TextField txFpromo;
	
	private Stage dialogStage;
	private Codeur codeur;
	private Boolean clickOk = false;
	private Repertoire repertoire;
	
	public void setRepertoire(Repertoire repertoire) {
		this.repertoire = repertoire;
	}
	
	@FXML
	private void initialize() {
		
	}
 
	/*
	 * guetter de DialogStage
	 */
	public Stage getDialogStage() {
		return dialogStage;
	}

	public void setDialogStage(Stage dialogStage) {
		this.dialogStage = dialogStage;
	}
	
	public void setCodeur(Codeur c) {
		this.codeur = c;
		
		txFnom.setText(c.getNom());
		txFprenom.setText(c.getPrenom());
		txFmail.setText(c.getTel());
		txFtel.setText("null");
		txFtel2.setText("Aucun");
		//txFpromo.setText("1");
		
	}
	
	public boolean aCliqueSurok() {
		return clickOk;
	}
	
	@FXML
	private void clickSurValider() {
		codeur.setNom(txFnom.getText());
		codeur.setPrenom(txFprenom.getText());
		codeur.setTel(txFtel.getText());
		codeur.setEmail("objis@objis.com");
		//codeur.setPromo("2");

		clickOk = true;
		dialogStage.close();
		
	}
	
	@FXML
	private void clickSurAnnuler() {
		dialogStage.close();
	}
	 
}
