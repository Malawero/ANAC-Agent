package group13;
import negotiator.Bid;
import negotiator.issue.ISSUETYPE;
import negotiator.issue.Issue;
import negotiator.issue.IssueDiscrete;
import negotiator.issue.IssueInteger;
import negotiator.issue.IssueReal;
import negotiator.issue.Value;
import negotiator.issue.ValueDiscrete;
import negotiator.issue.ValueReal;
import negotiator.issue.ValueInteger;
import negotiator.utility.AdditiveUtilitySpace;
import negotiator.utility.EvaluatorDiscrete;
import java.util.Comparator;
import java.util.List;

import org.apache.commons.math.util.MathUtils;

import java.util.ArrayList;

public class GirIssue {
	protected int number;
	protected double weight;
	protected ISSUETYPE type;
	protected List<GirValue> girValues;
	protected String name;
	
	public GirIssue(Issue issue, AdditiveUtilitySpace uSpace, double w0){
		this.number = issue.getNumber();
		this.weight = w0;
		this.type   = issue.getType();
		this.name   = issue.getName();
		
		this.girValues = new ArrayList<GirValue>();
		
		switch (this.type) {
		    case REAL:
		        
		    case INTEGER:	
	
		    case DISCRETE:
		        IssueDiscrete issueDiscrete = (IssueDiscrete) issue;
		        List<ValueDiscrete> allValues = issueDiscrete.getValues();
		        
		        for(ValueDiscrete valueDiscrete : allValues) {
		        	girValues.add(new GirValue(valueDiscrete.getValue()));
		        }
		        girValues.sort(GirValue.discreteComparator);
		    default:
		}
	}
	
	public void addValue(Value value, int action) {
		switch (this.type) {
		    case REAL:
		        
		    case INTEGER:
	
		    case DISCRETE:
		        ValueDiscrete valueDiscrete = (ValueDiscrete)value;
		        String valueString = valueDiscrete.getValue();
		        
		        for(GirValue girValue : this.girValues) {
		        	if(girValue.valueDiscrete.equals(valueString)) {
		        		girValue.addValue(action);
		        	}
		        }
		default:
			break;
		}
	}
	
	protected double[] getFreqs() {
		double[] freqs = new double[this.girValues.size()];
		for(int i = 0; i < this.girValues.size(); i++) {
        	freqs[i] = girValues.get(i).freq;
        }
		return freqs;
	}
	
	protected void normaliseValues() {
		double[] freqs = this.getFreqs();
		
		freqs = MathUtils.normalizeArray(freqs,(double)1);
		
		for(int i = 0; i < this.girValues.size(); i++) {
			girValues.get(i).freq = freqs[i];
        }
	}
	
	protected static void normaliseWeights(List<GirIssue> issues) {
		double[] weights = new double[issues.size()];
		
		for(int i = 0; i < issues.size(); i++) {
			weights[i] = issues.get(i).weight;
        }
		
		weights = MathUtils.normalizeArray(weights,(double)1);
		
		for(int i = 0; i < issues.size(); i++) {
			issues.get(i).weight = weights[i];
        }
	}
	
	protected double calcUtility(GirIssue opIssue) {
		double utility = 0;
		double freq;
		double vi;
		
		for(int i = 0; i < this.girValues.size(); i++) {
			freq = this.girValues.get(i).freq;
			vi   = opIssue.girValues.get(i).vi;
			utility = utility + ( freq * vi );
        }
		return utility;
	}
	
//	public void sortRates(){
//		this.girValues.sort(GirValue.rateComparator);
//	}
	
	public static Comparator<GirIssue> weightComparator = new Comparator<GirIssue>() {
		public int compare(GirIssue i1, GirIssue i2) {
		   return Double.compare(i2.weight, i1.weight);
	   }
	};
	
	public static Comparator<GirIssue> numberComparator = new Comparator<GirIssue>() {
		public int compare(GirIssue i1, GirIssue i2) {
		   return Integer.compare(i1.number, i2.number);
	   }
	};

	@Override
	public String toString() {
		return "GirIssue [name=" + name + ", number=" + number + ", weight=" + weight + "]";
	}
}
