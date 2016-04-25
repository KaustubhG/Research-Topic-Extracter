package ccs;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Main {

	public static void main(String[] args) {

		// identify topics of Computer Science Faculty
		String filePath = "D:\\Acads\\NLP_Proj_4-2\\proj_files\\cs_faculty.txt";
		ArrayList<String> cs_faculty = new ArrayList<>();

		// parse file to String
		String sCurrentLine;
		// this is try block with resources (Auto-closes)
		try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
			sCurrentLine = br.readLine();
			while (sCurrentLine != null) {
				cs_faculty.add(sCurrentLine.trim());
				sCurrentLine = br.readLine();
			}
		} catch (IOException e) {
			throw new RuntimeException("Error parsing filePathName containing cs_faculty");
		}

		TopicIdentifier topicIdentifier = new TopicIdentifier();
		String baseUrl = "D:\\Acads\\NLP_Proj_4-2\\proj_files\\researchPageKeywords\\";
		File ofile = new File("D:\\Acads\\NLP_Proj_4-2\\proj_files\\cs_faculty_research_topics");
		FileOutputStream fos;

		try {
			fos = new FileOutputStream(ofile, true);
			for (String prof : cs_faculty) {
				try {
					fos.write(prof.getBytes());
					fos.write(new String(":  ").getBytes());
					fos.write(topicIdentifier.getTopic(baseUrl + "\\" + prof).keySet().toString().getBytes());
					fos.write(new String("\n").getBytes());
				} catch (Exception e) {
					continue;
				}
			}
			fos.close();
		} catch (Exception e1) {
			throw new RuntimeException("couldn't open file");
		}

	}
}
