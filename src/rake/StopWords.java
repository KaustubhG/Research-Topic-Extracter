package rake;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;


public class StopWords {

	public static HashSet<String> getStopWordsFromFile(String filename)
	{
		
		HashSet<String> list_stop_words = new HashSet<String>();
		try (BufferedReader br = new BufferedReader(new FileReader(filename)))
		{
 
			String sCurrentLine;
 
			while ((sCurrentLine = br.readLine()) != null) {
				list_stop_words.add(sCurrentLine);
			}
 
		} catch (IOException e) {
			e.printStackTrace();
		} 
		
		return list_stop_words; 
	}
}
