package ccs;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class XMLParser {
	
	PorterStemmer stemmer ;
	String filePath = "";
	
	public XMLParser(String filePath) {
		this.filePath = filePath;
		stemmer = new PorterStemmer();
	}
	
	public HashMap<String, HashSet<String>> parseDocument(){
		SAXParserFactory spf = SAXParserFactory.newInstance();
		try {

			//get a new instance of parser
			SAXParser sp = spf.newSAXParser();
			XMLHandler xmHandler = new XMLHandler();

			//parse the file and also register this class for call backs
			sp.parse(filePath, xmHandler);
			HashMap<String, HashSet<String>> topics = new HashMap<String, HashSet<String>>();
			for(Concept concept: xmHandler.concepts){
				HashSet<String> topicLabels = new HashSet<String>();
				for(String conceptLabels: concept.getConceptNames()){
					String[] labelTokensArr = conceptLabels.split(" ");
					for(String labeltokens: labelTokensArr){
						stemmer.add(labeltokens.toCharArray(), labeltokens.length());
						stemmer.stem();
						String stemmedtoken = stemmer.toString();
						topicLabels.add(stemmedtoken);
						stemmer.reset();
						//topicLabels.add(labeltokens);
					}
					topics.put(concept.getConceptName(), topicLabels);
				}
			}
			return topics;
		}catch(SAXException se) {
			se.printStackTrace();
		}catch(ParserConfigurationException pce) {
			pce.printStackTrace();
		}catch (IOException ie) {
			ie.printStackTrace();
		}
		return null;
	}
	
}

class XMLHandler extends DefaultHandler{

	List<Concept> concepts = new ArrayList<Concept>();
	Concept concept ; 
	String tempVal ; 
	@Override
	public void startElement(String uri, String localName, String qName,
			Attributes attributes) throws SAXException {
		
		if(qName.equalsIgnoreCase("skos:Concept")){
			concept = new Concept();
		}
	}
	
	@Override
	public void endElement(String uri, String localName, String qName)
			throws SAXException {
		if(qName.equalsIgnoreCase("skos:Concept")){
			concepts.add(concept);
		}
		else if(qName.equalsIgnoreCase("skos:prefLabel")){
			concept.setConceptName(tempVal);
		}
		else if(qName.equalsIgnoreCase("skos:altLabel")){
			concept.addStringstoConcept(tempVal);
		}
	}
	
	public void characters(char[] ch, int start, int length) throws SAXException {
		tempVal = new String(ch,start,length);
	}
}