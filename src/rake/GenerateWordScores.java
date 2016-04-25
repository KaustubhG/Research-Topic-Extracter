package rake;
import java.util.ArrayList;
import java.util.HashMap;


public class GenerateWordScores {


	ArrayList<ArrayList<String>> candKeywords;

	public GenerateWordScores(ArrayList<ArrayList<String>> candKeywords){
		this.candKeywords = candKeywords;
	}


	public HashMap<ArrayList<String>, Double> getWordScore(){

		HashMap<String, Integer> freq_map = getFreq(candKeywords);
		HashMap<String, Integer> dist_edge_map = getDistEdges(candKeywords);

		HashMap<ArrayList<String>, Double> word_score_map = new HashMap<ArrayList<String>, Double>();

		for(ArrayList<String> list : candKeywords){

			double val = 0;
			for(String a : list){
				//this is deg/freq
				val = val + (double)(freq_map.get(a) + dist_edge_map.get(a))/(freq_map.get(a));
			}
			word_score_map.put(list, val);
		}

		return word_score_map;
	}

	private HashMap<String, Integer> getFreq(ArrayList<ArrayList<String>> candKeywords){
		HashMap<String, Integer> loc_freq_map = new HashMap<String, Integer>();
		for(ArrayList<String> list : candKeywords){

			for(String a : list){

				if(loc_freq_map.containsKey(a)){
					int old_val = loc_freq_map.get(a);
					loc_freq_map.put(a, old_val+1);
				}
				else{
					loc_freq_map.put(a, 1);
				}
			}
		}
		
		return loc_freq_map;
	}

	private HashMap<String, Integer> getDistEdges(ArrayList<ArrayList<String>> candKeywords){
		HashMap<String, Integer> loc_dist_edge_map = new HashMap<String, Integer>();

		for(ArrayList<String> list : candKeywords){
			//update all words in list to old_val + list.size()-1
			for(String a : list){
				if(loc_dist_edge_map.containsKey(a)){
					int old_val = loc_dist_edge_map.get(a);
					loc_dist_edge_map.put(a, old_val+(list.size()-1));
				}
				else{
					loc_dist_edge_map.put(a, list.size()-1);
				}
			}

		}
		return loc_dist_edge_map;
	}



}
