package onlineTest;

import java.io.Serializable;
import java.util.HashMap;

public class Answer implements Serializable {
	private static final long serialVersionUID = 1L;
	private HashMap<Integer, Boolean> trueFalseAnswers;
	private HashMap<Integer, String[]> multipleChoiceAnswers;
	private HashMap<Integer, String[]> fillInTheBlankAnswers;
	
	public Answer() {
		trueFalseAnswers = new HashMap<>();
		multipleChoiceAnswers = new HashMap<>();
		fillInTheBlankAnswers = new HashMap<>();
	}
	
	public void addTrueFalseAnswer(int examId, boolean answer) {
		trueFalseAnswers.put(examId, answer);
	}
	
	public HashMap<Integer, Boolean> getTrueFalseAnswers() {
		return trueFalseAnswers;
	}
	
	public HashMap<Integer, String[]> getMultipleChoiceAnswers() {
		return multipleChoiceAnswers;
	}
	
	public HashMap<Integer, String[]> getFillInTheBlankAnswers() {
		return fillInTheBlankAnswers;
	}
}
