package de.unima.lski.core;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.util.ArrayList;
import java.util.Properties;

import org.semanticweb.owl.align.Alignment;
import org.semanticweb.owl.align.AlignmentException;
import org.semanticweb.owl.align.AlignmentProcess;
import org.semanticweb.owl.align.AlignmentVisitor;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLDatatype;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLOntology;

import fr.inrialpes.exmo.align.impl.URIAlignment;
import fr.inrialpes.exmo.align.impl.renderer.RDFRendererVisitor;

public class FJAlignment extends URIAlignment implements AlignmentProcess {
	
	private static String pathOnto1;
	private static String pathOnto2;
	
	//aligns two ontologies and prints to console 
	public static void main(String[] args) throws FileNotFoundException, AlignmentException, UnsupportedEncodingException{
		FJAlignment alig = new FJAlignment(pathOnto1, pathOnto2);
		alig.align((Alignment)null, new Properties());
		PrintWriter writer = new PrintWriter ( new BufferedWriter( new OutputStreamWriter( System.out, "UTF-8" )), true); 
		AlignmentVisitor renderer = new RDFRendererVisitor(writer); 
		alig.render(renderer); 
		writer.flush(); 
		writer.close();
	}
	
	
	private Matcher m;
	
	public FJAlignment(String path1, String path2){
		this.init(path1, path2);
	}
	
	@Override
	public void align(Alignment arg0, Properties arg1) throws AlignmentException {
		ArrayList<String> res = m.matchClasses();
		for(String s:res){
			addClassToCell(s.split(" ")[1], s.split(" ")[2], Double.parseDouble(s.split(" ")[0]));
		}
		res.clear();
		res = m.matchObject();
		for(String s:res){
			addObjectPropToCell(s.split(" ")[1], s.split(" ")[2], Double.parseDouble(s.split(" ")[0]));
		}
		res = m.matchDatatype();
		for(String s:res){
			addDatatypeToCell(s.split(" ")[1], s.split(" ")[2], Double.parseDouble(s.split(" ")[0]));
		}
	}
	
	private void init(String path1, String path2){
		m = new Matcher(path1, path2);
		try {
			this.setOntology1(m.onto1.getOnto().getOntologyID().getOntologyIRI().toURI());
			this.setOntology2(m.onto2.getOnto().getOntologyID().getOntologyIRI().toURI());
		} catch (AlignmentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
	private void addClassToCell(String entity1, String entity2, double score){
		try {
			addAlignCell(recreateClass(entity1, m.onto1.getOnto()), recreateClass(entity2, m.onto2.getOnto()), "=", score);
		} catch (AlignmentException e) {
			e.printStackTrace();
		}
	}
	
	private URI recreateClass(String entity, OWLOntology onto){
		for(OWLClass classy:onto.getClassesInSignature()){
			if(classy.getIRI().getFragment().equals(entity)){
				return classy.getIRI().toURI();
			}
		}
		return null;
	}
	
	private void addObjectPropToCell(String entity1, String entity2, double score){
		try {
//			System.out.println(entity1 + "    " + entity2 + "    " + score);
			addAlignCell(recreateObjectProp(entity1, m.onto1.getOnto()), recreateObjectProp(entity2, m.onto2.getOnto()), "=", score);
		} catch (AlignmentException e) {
			e.printStackTrace();
		}
	}
	
	private URI recreateObjectProp(String entity, OWLOntology onto){
		for(OWLObjectProperty prop:onto.getObjectPropertiesInSignature()){
//			System.out.println(prop.getIRI().getFragment()+ "    "  + entity);
			if(prop.getIRI().getFragment().equals(entity)){
				return prop.getIRI().toURI();
			}
		}
		return null;
	}
	private void addDatatypeToCell(String entity1, String entity2, double score){
		try {
			addAlignCell(recreateDatatype(entity1, m.onto1.getOnto()), recreateDatatype(entity2, m.onto2.getOnto()), "=", score);
		} catch (AlignmentException e) {
			e.printStackTrace();
		}
	}
	private URI recreateDatatype(String entity, OWLOntology onto){
		for(OWLDatatype type:onto.getDatatypesInSignature()){
			if(type.getIRI().getFragment().equals(entity)){
				return type.getIRI().toURI();
			}
		}
		return null;
	}
	

}
