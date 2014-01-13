package de.unima.lski.core;

import java.io.File;
import java.util.ArrayList;

import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLDatatype;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyManager;



public class Ontology {
	private OWLOntologyManager manager;
	private OWLOntology onto;

	public OWLOntology getOnto() {
		return this.onto;
	}
	
	public Ontology(String ontoFile){
		this.manager = OWLManager.createOWLOntologyManager();
		try {
			this.onto = this.manager.loadOntologyFromOntologyDocument(new File(ontoFile));
		} catch (OWLOntologyCreationException e) {
			e.printStackTrace();
		}
	}
	
	public ArrayList<String> getClassesString(){
		ArrayList<String> classFragments = new ArrayList<String>();
		for(OWLClass classy:this.onto.getClassesInSignature()){
			classFragments.add(classy.getIRI().getFragment());
		}
		classFragments.remove(0);
		return classFragments;
	}
	
	public ArrayList<String> getDatatypesString(){
		ArrayList<String> datatypeFragments = new ArrayList<String>();
		for(OWLDataProperty datatype:this.onto.getDataPropertiesInSignature()){
			datatypeFragments.add(datatype.getIRI().getFragment());
		}
		return datatypeFragments;
	}
	
	public ArrayList<String> getObjectPropsString(){
		ArrayList<String> objectFragments = new ArrayList<String>();
		for(OWLObjectProperty objectProp:this.onto.getObjectPropertiesInSignature()){
			objectFragments.add(objectProp.getIRI().getFragment());
		}
		return objectFragments;
	}
}
