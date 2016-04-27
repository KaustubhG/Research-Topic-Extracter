package app;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.logging.Logger;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class ResearchTextExtracter {
	private static final Logger logger = Logger.getLogger(ResearchTextExtracter.class.getName());
	ArrayList<String> usernames = new ArrayList<>();
	String baseUrl;
	public int count = 1;

	public ResearchTextExtracter(String filePathName, String baseUrl) {
		this.baseUrl = baseUrl;
		// parse file to String
		String sCurrentLine;
		// this is try block with resources (Auto-closes)
		try (BufferedReader br = new BufferedReader(new FileReader(filePathName))) {
			sCurrentLine = br.readLine();
			while (sCurrentLine != null) {
				usernames.add(sCurrentLine.trim());
				sCurrentLine = br.readLine();
			}
		} catch (IOException e) {
			throw new RuntimeException("Error parsing filePathName containing usernames");
		}
	}

	public void extractResearchInformation(String filePathName) throws Exception {

		File folder = new File(Main.localPathName);
		HashSet<String> doneProfList = new HashSet<>();
		for (File file : folder.listFiles()) {
			doneProfList.add(file.getName());
		}

		ArrayList<String> pendingProfList = new ArrayList<>(usernames);

		while (!pendingProfList.isEmpty()) {
			Document researchPage = null;
			// check if present in doneProfList
			if (doneProfList.contains(pendingProfList.get(0))) {
				pendingProfList.remove(0);
				continue;
			}
			try {
				Document profile = Jsoup.connect(baseUrl + "/" + pendingProfList.get(0) + "/profile").get();
				logger.info("profile-page for " + pendingProfList.get(0) + " extracted");
				String researchHref = getResearchLink(profile);
				if (!researchHref.isEmpty()) {
					researchPage = Jsoup.connect(baseUrl + "/" + pendingProfList.get(0) + "/" + researchHref).get();
					logger.info("research-page extracted");
				} else {
					pendingProfList.remove(0);
					continue;
				}

			} catch (IOException e) {
				pendingProfList.remove(0);
				continue;
			}
			// save researchPage text to file
			File ofile = new File(filePathName + "\\" + pendingProfList.get(0));
			FileOutputStream fos;
			fos = new FileOutputStream(ofile, true);
			fos.write(researchPage.text().getBytes());
			fos.close();
			pendingProfList.remove(0);
			System.out.println(count);
			count += 1;
		}
	}

	private String getResearchLink(Document profile) {
		Elements links = profile.select("a[href]");
		String url = "";
		for (Element link : links) {
			if (link.text().matches("((.*)[Rr]esearch(.*))")) {
				url = link.attr("href");
			}
			if (link.text().matches("((.*)[Rr]esearch(.*)[Ii]nterest(.*))")) {
				url = link.attr("href");
			}

		}
		return url;
	}

}
