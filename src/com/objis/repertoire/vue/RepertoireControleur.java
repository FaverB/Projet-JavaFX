package com.objis.repertoire.vue;

import java.io.File;

import com.objis.repertoire.Repertoire;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;

/*
 * COntroleur de la vue Repertoire
 */
public class RepertoireControleur {

	private Repertoire repertoire;

	public void setRepertoire(Repertoire repertoire) {
		this.repertoire = repertoire;
	}

	/**
	 * Appele lorsque l'utilisateur clique sur le menu nouveau
	 */
	@FXML
	private void clickNouveau() {
		//repertoire.getListCodeur().clear();
		repertoire.setPathCodeur(null);

	}

	@FXML
	private void clickOuvrir() {
		FileChooser chooser = new FileChooser();

		// definir un filtre d'extension
		ExtensionFilter xmlFilter = new ExtensionFilter("Fichier xml (*.xml)", "*.xml");
		chooser.getExtensionFilters().add(xmlFilter);

		File file = chooser.showOpenDialog(repertoire.getPrimaryStage());
		if (file != null) {
			repertoire.chargerCodeurDepuisFichier(file);
		}

	}

	@FXML
	private void clickEnregistrer() {
		File codeurFile = repertoire.getPathCodeur();
		if (codeurFile != null) {
		} else {
			clickEnregistrerSous();
		}
	}

	@FXML
	private void clickEnregistrerSous() {
		FileChooser chooser = new FileChooser();
		ExtensionFilter xmlFilter = new ExtensionFilter("Fichier xml (*.xml)", "*.xml");
		chooser.getExtensionFilters().add(xmlFilter);

		// afficher la boite de dialogue enregistrer

		File file = chooser.showSaveDialog(repertoire.getPrimaryStage());
		// si le fichier existe
		if (file != null) {
			// verifier qu'il possede la bonne extension, c'est .xml
			// si le fichier ne se termine pas par .xml

			if (!file.getPath().endsWith(".xml")) {
				// creer lel fichier avec l'extension .xml
				file = new File(file.getPath() + ".xml");
			}
		}
	}
	
	@FXML
	private void clickApropos() {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Repertoire 10000 Codeur");
		alert.setHeaderText("A propos");
		alert.setContentText("Auteur: Gueu Ghislain OUEI \n ghislainouei@gmail.com \n Tel: +225 09 309 699");
		alert.showAndWait();			
		
	}
	
	@FXML
	private void clickQuitter() {
		System.exit(0);
	}

} // fin de la classe