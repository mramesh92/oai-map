package de.unima.lski.core;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;

import de.unima.lski.util.FastJoinWrapper;


public class Matcher {
	
	FastJoinWrapper fjw = new FastJoinWrapper();
	Ontology onto1;
	Ontology onto2;
	
	public Matcher(String onto1Path, String onto2Path){
		this.init(onto1Path, onto2Path);
	}
	
	private void init(String onto1Path, String onto2Path){
		onto1 = new Ontology(onto1Path);
		onto2 = new Ontology(onto2Path);
	}
	
	public ArrayList<String> matchClasses(){
		return match("getClassesString");
	}
	
	public ArrayList<String> matchDatatype(){
		return match("getDatatypesString");
	}
	
	public ArrayList<String> matchObject(){
		return match("getObjectPropsString");
	}
	
	private ArrayList<String> match(String type){
		ArrayList<String> result = new ArrayList<String>();
		this.clear();
		boolean check;
		check = writeFile(Settings.ONTO1, onto1, type);
		check = check && writeFile(Settings.ONTO2, onto2, type);
		if(!check){
			return result;
		}
		for(String s:fjw.getResults()){
			result.add(s);
		}
		return result;
	}
	
	private void clear(){
		try {
			File f1 = new File(Settings.ONTO1);
			File f2 = new File(Settings.ONTO2);
			f1.delete();
			f2.delete();
		} catch(Exception e){
			e.printStackTrace();
			System.exit(1);
		}
	}
	
	private boolean writeFile(String filePath, Ontology onto, String name){
		try {
			java.lang.reflect.Method method = onto.getClass().getMethod(name);
			FileWriter fw = new FileWriter(filePath, true);
			ArrayList<String> temp = (ArrayList<String>) method.invoke(onto, null);
			if(temp.size() == 0 || temp == null){
				return false;
			}
			fw.write(temp.get(0));
			for(int i = 1; i < temp.size(); i++){
				fw.write("\n"+temp.get(i));
			}
			fw.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return true;
	}
	
}
