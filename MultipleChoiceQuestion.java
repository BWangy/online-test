package onlineTest;

import java.io.Serializable;

public class MultipleChoiceQuestion implements Question, Serializable {
	private static final long serialVersionUID = 1L;
	private String text;
	private int questionNumber;
	private double points;
	private String[] answer;

	public MultipleChoiceQuestion(String text, int questionNumber, double points, String[] answer) {
		this.text = text;
		this.questionNumber = questionNumber;
		this.points = points;
		this.answer = answer;
	}
	
	@Override
	public int getQuestionNumber() {
		return questionNumber;
	}

	@Override
	public String getText() {
		return text;
	}

	@Override
	public double getPoints() {
		return points;
	}
	
	public String[] getAnswer() {
		return answer;
	}

}
