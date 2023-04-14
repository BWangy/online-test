package onlineTest;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

public class Student implements Comparable<Student>, Serializable {
	private static final long serialVersionUID = 1L;
	private String name;
	private ArrayList<Exam> exams;
	private HashMap<Integer, Answer> studentAnswers; 
	
	public Student(String name) {
		this.name = name;
		exams = new ArrayList<>();
		studentAnswers = new HashMap<>();
	}
	
	public String getName() {
		return name;
	}
	
	public ArrayList<Exam> getExams() {
		return exams;
	}
	
	public HashMap<Integer, Answer> getStudentAnswers() {
		return studentAnswers;
	}
	
	public void addExam(Exam exam) {
		exams.add(exam);
	}

	@Override
	public int compareTo(Student student) {
		return name.compareTo(student.name);
	}
		
}
 