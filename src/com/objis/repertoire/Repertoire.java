package com.objis.repertoire;

import java.io.File;
import java.io.IOException;
import java.util.prefs.Preferences;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import com.objis.repertoire.model.Codeur;
import com.objis.repertoire.model.ListCodeurWrapper;
import com.objis.repertoire.vue.CodeurControleur;
import com.objis.repertoire.vue.EditCodeurControleur;
import com.objis.repertoire.vue.RepertoireControleur;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class Repertoire extends Application {

	private Stage primaryStage;

	private BorderPane vueRepertoire;
	ObservableList<Codeur> listCodeur = FXCollections.observableArrayList();

	public Repertoire() {

		listCodeur.add(new Codeur("banba", "isaa"));

		listCodeur.add(new Codeur("meite", "oumar"));
		listCodeur.add(new Codeur("mess", "beke"));
		listCodeur.add(new Codeur("paul", "greguare"));
		listCodeur.add(new Codeur("goulei", "mass"));
		listCodeur.add(new Codeur("marie", "paul"));

		listCodeur.add(new Codeur("banba", "isaa"));

		listCodeur.add(new Codeur("meite", "oumar"));
		listCodeur.add(new Codeur("mess", "beke"));
		listCodeur.add(new Codeur("paul", "greguare"));
		listCodeur.add(new Codeur("goulei", "mass"));
		listCodeur.add(new Codeur("marie", "paul"));
		
	}
	
	@Override

	public void start(Stage primaryStage) {
		this.primaryStage = primaryStage;
		// primaryStage.setTitle("Repertoire 10000 Codeurs");

		afficherVueRepertoire();
		afficherVueCodeur();
	}

	public void afficherVueRepertoire() {
		FXMLLoader loader = new FXMLLoader();
		// apel du fichier .fxml
		loader.setLocation(Repertoire.class.getResource("vue/VueRepertoire.fxml"));

		try {
			vueRepertoire = (BorderPane) loader.load();

			Scene scene = new Scene(vueRepertoire);
			primaryStage.setScene(scene);
			primaryStage.show();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	
	public void afficherVueCodeur() {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("vue/VueCodeur.fxml"));

		try {

			AnchorPane vueCodeur = (AnchorPane) loader.load();
			vueRepertoire.setCenter(vueCodeur);

			CodeurControleur controleur = loader.getController();
			controleur.setRepertoire(this);

		} catch (IOException e) { 

			e.printStackTrace();
		}
	}

	/*
	 *  Affichage de la vue edition
	 */
	public boolean afficherVueEditerCodeur(Codeur codeur) {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("vue/VueEditCodeur.fxml"));

			AnchorPane pane = (AnchorPane) loader.load();
			Stage stageEdition = new Stage();
			stageEdition.setTitle("Editer");
			stageEdition.initModality(Modality.WINDOW_MODAL);
			stageEdition.initOwner(primaryStage);

			Scene scene = new Scene(pane);
			stageEdition.setScene(scene);

			EditCodeurControleur codeurControleur = loader.getController();
			codeurControleur.setDialogStage(stageEdition);
			codeurControleur.setCodeur(codeur);
			
			
			stageEdition.showAndWait();

			return codeurControleur.aCliqueSurok();

		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}

	}

	/**
	 * getter pour la liste de codeur
	 * 
	 * @param args
	 */
	public ObservableList<Codeur> getListCodeur() {
		return listCodeur;
	}

	public Stage getPrimaryStage() {
		return primaryStage;
	}

	public static void main(String[] args) {
		launch(args);
	}

	// chemin d'acces au fichier qui contient la liste des codeurs
	public File getPathCodeur() {
		Preferences prefs = Preferences.userNodeForPackage(getClass());
		String filepath = prefs.get("filepath", null);
		if (filepath != null) {
			return new File(filepath);
		} else {
			return null;
		}
	}

	public void setPathCodeur(File file) {
		Preferences prefs = Preferences.userNodeForPackage(getClass());
		if (file != null) {
			prefs.put("filepath", file.getPath());
			primaryStage.setTitle("Repertoire Objis " + file.getName());
		} else {
			prefs.remove("filepath");

			primaryStage.setTitle("Repertoire Objis");
		}
	}

	public void chargerCodeurDepuisFichier(File file) {
		try {
			JAXBContext context = JAXBContext.newInstance(ListCodeurWrapper.class);
			Unmarshaller um = context.createUnmarshaller();
			ListCodeurWrapper codeurWrapper = (ListCodeurWrapper) um.unmarshal(file);
			listCodeur.clear();
			listCodeur.addAll(codeurWrapper.getListCodeur());

			// sauvegarder le chemin de fichier dans le registre

			setPathCodeur(file);

		} catch (JAXBException e) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Erreur");
			alert.setHeaderText("Impossible de charger les donnees");
			alert.setContentText("impossible de charger les donnees depuis le fichier:\n" + file.getPath());

			alert.showAndWait();
		}

	}

	public void enregistrerCodeurDansfichier(File file) {

		try {
			JAXBContext context = JAXBContext.newInstance(ListCodeurWrapper.class);
			Marshaller m = context.createMarshaller();
			ListCodeurWrapper codeurWrapper = new  ListCodeurWrapper();
			m.marshal(codeurWrapper, file);
			
			setPathCodeur(file);
			
		} catch (Exception e) {
			Alert alert = new Alert(AlertType.ERROR);
	        alert.setTitle("Erreur");
	        alert.setHeaderText("Impossible de sauvegarder");
	        alert.setContentText("Je n'arrive pas à enregistrer les donnees dans le fichier:\n" + file.getPath());

	        alert.showAndWait();
		}
	}
	
	/**
	 * Initialiser la vue principale et essayer de charger le dernier fichier ouvert
	 */
	public void initVueRepertoire() {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("vue/VueRepertoire.fxml"));
		
		try {
			vueRepertoire = (BorderPane)loader.load();
			Scene scene = new  Scene(vueRepertoire);
			primaryStage.setScene(scene);
			
			//permettre au controlleur d'acceder a la classe repertoire, nous faisons la liason entre le controlleur et la classe repertoire
			//pour cela on cree une instance de la classe controlleur
			RepertoireControleur controleur = loader.getController();
			controleur.setRepertoire(this);
			
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		File file = getPathCodeur();
		if(file != null) {
			chargerCodeurDepuisFichier(file);
		}
		
	}
}
