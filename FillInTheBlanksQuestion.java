package onlineTest;

import java.io.Serializable;

public class FillInTheBlanksQuestion implements Question, Serializable {
	private static final long serialVersionUID = 1L;
	private int questionNumber;
	private String text;
	private double points;
	private String[] answer;
	
	public FillInTheBlanksQuestion(String text, int questionNumber, double points, String[] answer) {
		this.questionNumber = questionNumber;
		this.text = text;
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
