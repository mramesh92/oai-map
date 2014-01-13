package de.unima.lski.util;

import java.io.File;
import java.util.Properties;

import org.semanticweb.owl.align.Alignment;

import fr.inrialpes.exmo.align.impl.eval.PRecEvaluator;
import fr.inrialpes.exmo.align.parser.AlignmentParser;


public class Eval {
	
	private static String pathReference;
	private static String pathAlignment;
	
	
	//Prints Precision, Recall and FMeasure of two given alignments
	public static void main(String[] args){
		Eval eval = new Eval();
		eval.eval(pathAlignment, pathReference);
	}
	
	public void eval(String pathToAlignement, String pathToReference){
		try{
			AlignmentParser aparser = new AlignmentParser(0);
			Alignment alig = aparser.parse(new File(pathToAlignement).toURI());
			Alignment reference = aparser.parse( new File(pathToReference).toURI() );
			
			PRecEvaluator evaluator = new PRecEvaluator( reference, alig ); 
			evaluator.eval(new Properties());
			System.out.println("Precision: " + evaluator.getPrecision());
			System.out.println("Recall: " + evaluator.getRecall());
			System.out.println("FMeasure: " + evaluator.getFmeasure());
		} catch(Exception e){
			e.printStackTrace();
		}
	}
}
