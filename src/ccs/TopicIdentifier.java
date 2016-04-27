package ccs;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map.Entry;

import app.Util;

public class TopicIdentifier {

	HashMap<String, HashSet<String>> concepts;
	XMLParser parser;

	public TopicIdentifier() {

		parser = new XMLParser("acmccs.xml");
		concepts = parser.parseDocument();
	}

	public HashMap<String, Integer> getTopic(String path) throws IOException {
		// parse file to String
		ArrayList<String> keywords = new ArrayList<>();
		String sCurrentLine;
		try (BufferedReader br = new BufferedReader(new FileReader(path))) {
			while ((sCurrentLine = br.readLine()) != null) {
				keywords.add(sCurrentLine);
			}
		} catch (FileNotFoundException e) {
			return null;
		}
		return getTopicFromString(keywords);
	}

	private HashMap<String, Integer> getTopicFromString(ArrayList<String> keywords) {
		// get a list of terms
		PorterStemmer stemmer = new PorterStemmer();

		HashMap<String, Integer> weights = new HashMap<String, Integer>();
		for (String keyword : keywords) {
			stemmer.add(keyword.toCharArray(), keyword.length());
			stemmer.stem();
			String stem_word = stemmer.toString();
			stemmer.reset();
			for (String concept_name : concepts.keySet()) {
				HashSet<String> labels = concepts.get(concept_name);
				if (labels.contains(stem_word)) {
					if (weights.containsKey(concept_name)) {
						int weight = weights.get(concept_name);
						weights.put(concept_name, ++weight);
					} else
						weights.put(concept_name, 1);
				}
			}

		}

		List<Entry<String, Integer>> greatest = Util.findGreatest(weights, 3);
		HashMap<String, Integer> result = new HashMap<>();
		for (Entry<String, Integer> entry : greatest) {
			result.put(entry.getKey(), entry.getValue());
		}
		return result;
	}
}
