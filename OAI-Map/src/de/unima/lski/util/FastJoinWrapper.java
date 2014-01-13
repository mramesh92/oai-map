package de.unima.lski.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import de.unima.lski.core.Settings;

public class FastJoinWrapper {
	
	ArrayList<String> scoreEntity1Entity2;
	
	public static void main(String[] args){
		FastJoinWrapper fjw = new FastJoinWrapper();
		fjw.run();
	}
	
	public ArrayList<String> getResults(){
		this.run();
		return this.scoreEntity1Entity2;
	}
	
	private void run(){
		Process p;
		try {
			scoreEntity1Entity2 = new ArrayList<String>();
			p = Runtime.getRuntime().exec(Settings.FASTJOIN_EXE_WIN + " " + Settings.FASTJOIN_MEASURE + " " + Settings.FASTJOIN_DELTA + " " + Settings.FASTJOIN_TAU + " " + Settings.ONTO1 + " " + Settings.ONTO2);
			BufferedReader bri = new BufferedReader(new InputStreamReader(p.getInputStream()));
			
			String line;
			double confidence = 0.0;
			String entity1;
			String entity2;
			
			while ((line = bri.readLine()) != null) {
				confidence = Double.parseDouble(line.split(" ")[0]);
				entity1 = bri.readLine();
				entity2 = bri.readLine();
				bri.readLine();
				scoreEntity1Entity2.add(confidence + " " + entity1 + " " + entity2);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
}
