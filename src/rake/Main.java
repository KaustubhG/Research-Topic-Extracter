package rake;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

import app.Util;


public class Main {

	public static void main(String[] args) {

		final File folder = new File("D:\\Acads\\NLP_Proj_4-2\\proj_files\\researchPageText");
		StringBuilder stringBuilder = new StringBuilder();
		for(String abs_filepath :Util.listFilesForFolder(folder) ){
			// parse file to String
			StringBuilder sb = new StringBuilder();
			String sCurrentLine;
			try (BufferedReader br = new BufferedReader(new FileReader(abs_filepath))) {
				while ((sCurrentLine = br.readLine()) != null) {
					sb.append(sCurrentLine).append("\n");
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			
			//keyword extraction algo
			GenerateCandidateKeywords genKey = new GenerateCandidateKeywords(sb.toString());
			ArrayList<ArrayList<String>> candKeyWords = genKey.generate();
			GenerateWordScores wordScores = new GenerateWordScores(candKeyWords);

			//print top n keyWords
			int n = 10;
			stringBuilder.append("-----------------" + "file : " + abs_filepath + "-------------------").append("\n");
			
			List<Entry<ArrayList<String>, Double>> greatest = Util.findGreatest(wordScores.getWordScore(), n);
			for (Entry<ArrayList<String>, Double> entry : greatest){
				stringBuilder.append(entry).append("\n");
			}
			
			stringBuilder.append("---------------------------------------").append("\n");
		}
		
		long end = System.currentTimeMillis();
		System.out.println(stringBuilder.toString());
	}


}
