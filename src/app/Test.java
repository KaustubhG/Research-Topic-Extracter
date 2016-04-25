package app;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Test {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub

		String baseUrl = "http://www.bits-pilani.ac.in/Goa";
		Document profile = Jsoup.connect(baseUrl+"/tsrkp/profile").get();
		
		Elements links = profile.select("a[href]");

		for (Element link : links) {	
			System.out.println("-------------");
			System.out.println(link.text());
			System.out.println(link.attr("href"));
			System.out.println("-------------");
			
		}
	}

}
