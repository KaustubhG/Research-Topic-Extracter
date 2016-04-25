package app;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import ccs.TopicIdentifier;

public class Main {

	// each entry is a Map of <term, freq in the document>
	public static ArrayList<HashMap<String, Double>> documentTerms_mapList;
	public static final int N = 10;
	public static String filePathName = "D:\\Acads\\NLP_Proj_4-2\\proj_files\\usernames";
	public static String baseUrl = "http://www.bits-pilani.ac.in/Goa";
	public static String localPathName = "D:\\Acads\\NLP_Proj_4-2\\proj_files\\researchPageText";

	public static void main(String[] args) throws Exception {

		// Extracts ResearchPageText of profs and puts it in a file under the localPathName
		// ResearchTextExtracter extracter = new ResearchTextExtracter(filePathName, baseUrl);
		// extracter.extractResearchInformation(localPathName);

		documentTerms_mapList = KeywordUtil.generateTermFreq(localPathName);

		File folder = new File(localPathName);
		for (File file : folder.listFiles()) {
			HashMap<ArrayList<String>, Double> keyword_scores = KeywordUtil.getRAKEKeywords(file.getAbsolutePath());
			List<Entry<ArrayList<String>, Double>> greatest = Util.findGreatest(keyword_scores, N);

			HashMap<String, Double> idf_values = new HashMap<>();
			for (Entry<ArrayList<String>, Double> entry : greatest) {
				for (String keyword : entry.getKey()) {
					double val = KeywordUtil.getIDF(keyword, documentTerms_mapList);
					if (val != 0.0)
						idf_values.put(keyword, val);
				}
			}
			// System.out.println(file.getName());
			if (file.getName().equals("biju"))
				System.out.println(idf_values);
			List<Entry<String, Double>> filter = Util.findGreatest(idf_values, 100);
			// save researchTopic keywords to file
			String path = "D:\\Acads\\NLP_Proj_4-2\\proj_files\\researchPageKeywords";
			File ofile = new File(path + "\\" + file.getName());
			FileOutputStream fos;
			fos = new FileOutputStream(ofile, true);
			for (Entry<String, Double> entry : filter) {
				fos.write(entry.getKey().getBytes());
				fos.write(new String("\n").getBytes());
			}
			fos.close();
		}

	}
}
