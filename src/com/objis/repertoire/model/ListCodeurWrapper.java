package com.objis.repertoire.model;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "listeCodeurs")
public class ListCodeurWrapper {

	private List<Codeur> listeCodeur;

	@XmlElement(name = "codeur")
	public List<Codeur> getListCodeur() {
		return listeCodeur;
	}

	public void setListCodeur(List<Codeur> listCodeur) {
		this.listeCodeur = listCodeur;
	}
	
	
}
