package app;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import rake.GenerateCandidateKeywords;
import rake.GenerateWordScores;

public class KeywordUtil {

	// generate List of (Map of document term freq)
	public static ArrayList<HashMap<String, Double>> generateTermFreq(String localPathName) {
		ArrayList<String> list = new ArrayList<String>();
		ArrayList<HashMap<String, Double>> documentTerms_mapList;
		File localFolder = new File(localPathName);
		for (File fileEntry : localFolder.listFiles()) {
			list.add(fileEntry.getAbsolutePath());
		}
		// generate term-freq for each document
		documentTerms_mapList = new ArrayList<>();
		for (String doc : list) {
			documentTerms_mapList.add(genDocTermFreq(doc));
		}

		return documentTerms_mapList;
	}

	// calculate term freqs for a document
	public static HashMap<String, Double> genDocTermFreq(String filename) {

		HashMap<String, Double> term_freq_map = new HashMap<>();

		// parse file to String
		StringBuilder sb = new StringBuilder();
		String sCurrentLine;
		try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
			while ((sCurrentLine = br.readLine()) != null) {
				sb.append(sCurrentLine).append(" ");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		// sb now has the parsed contents of filename

		// remove punctuations
		ArrayList<String> sent_list = new ArrayList<String>();
		String delimiters = "\\.\\s*|\\?\\s*|\\s*,\\s*|\\s*!\\s*";
		for (String a : sb.toString().split(delimiters)) {
			sent_list.add(a);
		}

		// remove spaces to generate words
		HashSet<String> word_set = new HashSet<>();
		for (String sent : sent_list) {

			for (String single_word : sent.split("\\s+|\\s*,\\s*")) {
				single_word = single_word.toLowerCase();
				// Removing numbers from generated keyword list
				if (single_word.matches("[0-9]+") || single_word.matches("\\(.*\\)")) {
					continue;
				}
				// calc term-freq
				if (word_set.contains(single_word)) {
					term_freq_map.put(single_word, term_freq_map.get(single_word) + 1);
				} else {
					term_freq_map.put(single_word, 1.0);
					word_set.add(single_word);
				}
			}
		}
		// normalize
		double total_val = 0;
		for (String term : term_freq_map.keySet()) {
			total_val += Math.pow(term_freq_map.get(term), 2);
		}
		total_val = Math.sqrt(total_val);

		for (String term : term_freq_map.keySet()) {
			term_freq_map.put(term, term_freq_map.get(term) / total_val);
		}
		return term_freq_map;
	}

	// calculate idf value for each word
	public static double getIDF(String word, ArrayList<HashMap<String, Double>> documentTerms_mapList) {
		int total_docs = documentTerms_mapList.size();
		int docs_containing_word = 0;
		for (HashMap<String, Double> map : documentTerms_mapList) {
			if (map.containsKey(word)) {
				docs_containing_word += 1;
			}
		}
		// calculate idf for word
		double idf_value = Math.log((double) total_docs / (docs_containing_word));
		return idf_value;
	}

	// get RAKE keywords from file
	public static HashMap<ArrayList<String>, Double> getRAKEKeywords(String file) {
		// parse file to String
		StringBuilder sb = new StringBuilder();
		String sCurrentLine;
		try (BufferedReader br = new BufferedReader(new FileReader(file))) {
			while ((sCurrentLine = br.readLine()) != null) {
				sb.append(sCurrentLine).append("\n");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		// keyword extraction algorithm
		GenerateCandidateKeywords genKey = new GenerateCandidateKeywords(sb.toString());
		ArrayList<ArrayList<String>> candKeyWords = genKey.generate();
		GenerateWordScores wordScores = new GenerateWordScores(candKeyWords);
		return wordScores.getWordScore();
	}

}
