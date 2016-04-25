package ccs;
import java.util.ArrayList;
import java.util.List;

public class Concept {
	String conceptName ; 
	List<String> conceptlabels ;
	
	public Concept(){
		this.conceptlabels = new ArrayList<String>();
	}
	
	public Concept(String conceptName) {
		this.conceptName = conceptName ; 
		this.conceptlabels = new ArrayList<String>();
	}
		
	public String getConceptName() {return conceptName;}

	public void setConceptName(String conceptName) {this.conceptName = conceptName;}

	public List<String> getConceptNames() {return conceptlabels;}

	public void setConceptNames(List<String> conceptlabels) {this.conceptlabels = conceptlabels;}

	public void addStringstoConcept(String name){this.conceptlabels.add(name);}

}
