package rake;
import java.util.ArrayList;
import java.util.HashSet;


public class GenerateCandidateKeywords {

	String text ;
	private HashSet<String> disWords = new HashSet<String>(); 

	public HashSet<String> getdistinctWords()
	{
		if(disWords.isEmpty())
		{
			throw new IllegalStateException("Call generate before calling this method");
		}
		else
			return disWords; 
	}


	public GenerateCandidateKeywords(String text) {
		// TODO Auto-generated constructor stub
		this.text = text ; 
	}

	//Gives a list of list of candidate key-words
	public ArrayList<ArrayList<String>> generate()
	{

		ArrayList<ArrayList<String>> candidate = new ArrayList<ArrayList<String>>();
		HashSet<String> stopWords = getStopWordsfromFile("SmartStoplist.txt");

		ArrayList<String> sentences = genSent();
		ArrayList<String> temp = new ArrayList<String>(); //temp list for storing candkey words
		for(String sent : sentences){

			for(String single_word: sent.split("\\s+|\\s*,\\s*"))
			{	
				single_word = single_word.toLowerCase();

				//Removing numbers from generated keyword list
				if(single_word.matches("[0-9]+") || single_word.matches("\\(.*\\)"))
				{
					//System.out.println(single_word);
					continue ; 
				}
				//System.out.println(single_word);
				if(stopWords.contains(single_word)){

					if(temp.isEmpty()) continue;
					else{

						//Check number of words in the arraylist

						if(temp.size() <= 3){
							//System.out.println(temp);
							candidate.add(new ArrayList<String>(temp));
						}

						temp.clear();
					}
				}
				else{
					//temp.add(checkLastChar(single_word));
					temp.add(single_word);
				}
			}
			//for the last words in temp
			if(!temp.isEmpty()){
				if(temp.size() <= 3){
					candidate.add(new ArrayList<String>(temp));
				}
			}
			temp.clear();

		}
		return candidate ;
	}

	//generates sentences from given text
	public ArrayList<String> genSent(){

		ArrayList<String> sent_list = new ArrayList<String>();
		String delimiters = "\\.\\s*|\\?\\s*|\\s*,\\s*|\\s*!\\s*";

		for(String a : this.text.split(delimiters)){

			sent_list.add(a);
		}

		return sent_list;


	}

	public HashSet<String> getStopWordsfromFile(String filename)
	{

		return StopWords.getStopWordsFromFile(filename); 
	}

	public static String checkLastChar(String word)
	{
		HashSet<String> trimmer = new HashSet<String>();
		trimmer.add("s");trimmer.add("?");trimmer.add(".");
		//add while loop for multiple last char checking
		//see how substirng ke index works
		if(trimmer.contains(word.substring(word.length()-1, word.length())))
		{
			return word.substring(0, word.length() - 1);
		}
		else
			return word ; 
	}
}
