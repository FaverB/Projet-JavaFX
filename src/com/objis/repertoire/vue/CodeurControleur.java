package com.objis.repertoire.vue;

import com.objis.repertoire.Repertoire;
import com.objis.repertoire.model.Codeur;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class CodeurControleur {

	@FXML
	private TableView<Codeur> tableCodeur;
	@FXML
	private TableColumn<Codeur, String> colNom;
	@FXML
	private TableColumn<Codeur, String> colPrenom;
	@FXML
	private Label lblNom;
	@FXML
	private Label lblPrenom;
	@FXML
	private Label lblTel;
	@FXML
	private Label lblPromo;
	@FXML
	private Label lblEmail;
	
	@FXML
	private TextField filtre;

	private Repertoire repertoire;

	
	
	// setter de repertoire
	public void setRepertoire(Repertoire repertoire) {
		this.repertoire = repertoire;
		tableCodeur.setItems(repertoire.getListCodeur()); 
	}

	// Constructeur par default
	public CodeurControleur() { 

	 
	}

	/**
	 * methode d'initialisation du controleur attention ne pas changer le nom de
	 * cette methode
	 */
	@FXML
	private void initialize() {
		colNom.setCellValueFactory(valeurCell -> valeurCell.getValue().nomProperty());
		colPrenom.setCellValueFactory(valeurCell -> valeurCell.getValue().prenomProperty());

		afficherDetailsCodeur(null);
		
		tableCodeur.getSelectionModel().selectedItemProperty()
				.addListener((observable, oldValue, newValue) -> afficherDetailsCodeur(newValue));

		//gestion de la recherche d'un nom ou prenom ou numero de telephone
		
		ObservableList<Codeur> listeCodeurs = FXCollections.observableArrayList();
		listeCodeurs.addAll(tableCodeur.getItems());
		
		FilteredList<Codeur> listeFiltree = new FilteredList<>(listeCodeurs, p -> true);
		filtre.textProperty().addListener((observable, oldValue,newValue) ->{
			listeFiltree.setPredicate(codeur ->{
				if (newValue == null || newValue.isEmpty()) {
					return true;
				}
				
				String textEnMinuscule = newValue.toLowerCase();
				if(codeur.getNom().toLowerCase().contains(textEnMinuscule)) {
					return true; //le champ contient une partie du nom
				}
				else if(codeur.getPrenom().toLowerCase().contains(textEnMinuscule)) {
					return true; //le champ contient une partie du prenom
				}
				return false;
			});
		}
		);
		
		//
		SortedList<Codeur> listeTriee = new SortedList<>(listeFiltree);
		listeTriee.comparatorProperty().bind(tableCodeur.comparatorProperty());
		tableCodeur.setItems(listeTriee);
		
	}

	// Afficher details lors du clic avec la souris
	private void afficherDetailsCodeur(Codeur codeur) {

		if (codeur != null) {

			lblNom.setText(codeur.getNom());
			lblPrenom.setText(codeur.getPrenom());
			lblTel.setText(codeur.getTel());
			// lblPromo.setText(codeur.getPromo());
			// lblEmail.setText(codeur.getEmail());

		} else {

			lblNom.setText("");
			lblPrenom.setText("");  
			// lblTel.setText("");
			// lblPromo.setText("");
			// lblEmail.setText("");
		}
	}

	//  bouton pour supprimer la ligne selectionnée
	@FXML
	private void clickSurSupprimer() {
		int index = tableCodeur.getSelectionModel().getSelectedIndex();

		if (index >= 0) {
			tableCodeur.getItems().remove(index);

		} else {
			// si on click sur supprimer sans aucune selection
			Alert alert = new Alert(AlertType.WARNING);
			alert.initOwner(repertoire.getPrimaryStage());
			alert.setTitle("Aucune selection");
			alert.setHeaderText("Attention veuillez selection une ligne");
			alert.setContentText("Veuillez selectionner une ligne du tableau et réeessayer");
			alert.showAndWait();
		}
	}

	@FXML
	private void clickSurNouveau() {
		Codeur tempCodeur = new Codeur();
		boolean clickOk = repertoire.afficherVueEditerCodeur(tempCodeur);
		if (clickOk) {
			repertoire.getListCodeur().add(tempCodeur);
		}
	}

	@FXML
	private void clickSurModifier() {
		Codeur codeur = tableCodeur.getSelectionModel().getSelectedItem();
		if (codeur != null) {
			boolean clickOk = repertoire.afficherVueEditerCodeur(codeur);
			if (clickOk) {
				afficherDetailsCodeur(codeur);
			}
			
			// si aucune selection
		} else {
			Alert alert = new Alert(AlertType.WARNING);
			alert.initOwner(repertoire.getPrimaryStage());
			
			alert.setTitle("Pas de selection");
			alert.setHeaderText("Aucun contact n'a ete selectionne");
			alert.setContentText("veuillez selectionner un contact dans la table svp");

			alert.showAndWait();

		}
	}

}
