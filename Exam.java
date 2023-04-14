package onlineTest;

import java.io.Serializable;
import java.util.ArrayList;

public class Exam implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private int id;
	private String title;
	private ArrayList<Question> questions;
	
	
	public Exam(int examId, String title) {
		this.id = examId;
		this.title = title;
		this.questions = new ArrayList<>();
	}
	
	public void addTrueFalseQuestion(String text, int questionNumber, double points, boolean answer) {
		for(Question question : questions) {
			if(question.getQuestionNumber() == questionNumber) {
				question = new TrueFalseQuestion(text, questionNumber, points, answer);
				return;
			}
		}
		TrueFalseQuestion question = new TrueFalseQuestion(text, questionNumber, points, answer);
		questions.add(question);
	}
	
	public void addMultipleChoiceQuestion(String text, int questionNumber, double points, String[] answer) {
		for(Question question : questions) {
			if(question.getQuestionNumber() == questionNumber) {
				question = new MultipleChoiceQuestion(text, questionNumber, points, answer);
				return;
			}
		}
		MultipleChoiceQuestion question = new MultipleChoiceQuestion(text, questionNumber, points, answer);
		questions.add(question);
	}
	
	public void addFillInTheBlanksQuestion(String text, int questionNumber, double points, String[] answer) {
		for(Question question : questions) {
			if(question.getQuestionNumber() == questionNumber) {
				question = new FillInTheBlanksQuestion(text, questionNumber, points, answer);
				return;
			}
		}
		FillInTheBlanksQuestion question = new FillInTheBlanksQuestion(text, questionNumber, points, answer);
		questions.add(question);
	}
	
	public int getId() {
		return id;
	}
	
	public ArrayList<Question> getQuestions() {
		ArrayList<Question> copy = new ArrayList<>();
		for(Question question : questions) {
			copy.add(question);
		}
		return copy;
	}
	
	public double getTotalPoints() {
		double totalPoints = 0.0;
		for(Question question : questions) {
			totalPoints += question.getPoints();	
		}
		
		return totalPoints;
	}
}
