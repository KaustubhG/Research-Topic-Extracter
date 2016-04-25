package ccs;

public class Test {

	
	public static void main(String[] args){
		String keyword = "databases";
		PorterStemmer stemmer = new PorterStemmer();
		stemmer.add(keyword.toCharArray(), keyword.length());
		stemmer.stem();
		System.out.println(stemmer.toString());
		keyword = "computer";
		stemmer.reset();
		stemmer.add(keyword.toCharArray(), keyword.length());
		stemmer.stem();
		System.out.println(stemmer.toString());
	}
}
