package onlineTest;

import java.io.Serializable;

public class TrueFalseQuestion implements Question, Serializable {
	private static final long serialVersionUID = 1L;
	private String text;
	private boolean answer;
	private double points;
	private int questionNumber;
	
	public TrueFalseQuestion(String text, int questionNumber, double points, boolean answer) {
		this.text = text;
		this.questionNumber = questionNumber;
		this.answer = answer;
		this.points = points;
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
	
	public boolean getAnswer() {
		return answer;
	}
}
