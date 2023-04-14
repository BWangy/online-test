package onlineTest;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map.Entry;

public class SystemManager implements Manager, Serializable {
	
	private static final long serialVersionUID = 1L;
	private ArrayList<Exam> exams;
	private ArrayList<Student> students;
	private HashMap<String, Double> gradeCutoffs;

	public SystemManager() {
		exams = new ArrayList<>();
		students = new ArrayList<>();
		gradeCutoffs = new HashMap<>();
	}
	
	@Override
	public boolean addExam(int examId, String title) {
		Exam newExam = new Exam(examId, title);
		boolean examExists = false;
		for(Exam exam : exams) {
			if(exam == newExam) {
				examExists = true;
			}
		}
		
		if(!examExists) {
			exams.add(newExam);
			return true;
		} else {
			return false;	
		}
	}

	@Override
	public void addTrueFalseQuestion(int examId, int questionNumber, String text, double points, boolean answer) {
		for(Exam exam : exams) {
			if(exam.getId() == examId) {
				exam.addTrueFalseQuestion(text, questionNumber, points, answer);
			}
		}

	}

	@Override
	public void addMultipleChoiceQuestion(int examId, int questionNumber, String text, double points, String[] answer) {
		for(Exam exam : exams) {
			if(exam.getId() == examId) {
				exam.addMultipleChoiceQuestion(text, questionNumber, points, answer);
			}
		}

	}

	@Override
	public void addFillInTheBlanksQuestion(int examId, int questionNumber, String text, double points, String[] answer) {
		for(Exam exam : exams) {
			if(exam.getId() == examId) {
				exam.addFillInTheBlanksQuestion(text, questionNumber, points, answer);
			}
		}
	}

	@Override
	public String getKey(int examId) {
		String key = "";
		for(Exam exam : exams) {
			if(exam.getId() == examId) {
				for(Question question : exam.getQuestions()) {
					key += "Question Text: " + question.getText() + "\n";
					key += "Points: " + question.getPoints() + "\n";
					key += "Correct Answer: ";
					if(question instanceof TrueFalseQuestion) {
						TrueFalseQuestion cast = (TrueFalseQuestion) question;
						if(cast.getAnswer() == true) {
							key += "True";
						} else {
							key += "False";
						}
					} else if(question instanceof MultipleChoiceQuestion) {
						MultipleChoiceQuestion cast = (MultipleChoiceQuestion) question;
						String[] copyOfAnswers = new String[cast.getAnswer().length];
						for(int i = 0; i < cast.getAnswer().length; i++) {
							copyOfAnswers[i] = cast.getAnswer()[i];
						}
						
						Arrays.sort(copyOfAnswers);
						key += Arrays.toString(copyOfAnswers);
					} else if(question instanceof FillInTheBlanksQuestion) {
						FillInTheBlanksQuestion cast = (FillInTheBlanksQuestion) question;
						String[] copyOfAnswers = new String[cast.getAnswer().length];
						for(int i = 0; i < cast.getAnswer().length; i++) {
							copyOfAnswers[i] = cast.getAnswer()[i];
						}
						
						Arrays.sort(copyOfAnswers);
						key += Arrays.toString(copyOfAnswers);
					}
					key += "\n";
				}
			} else {
				key += "Exam not found";
			}
		}
		return key;
	}

	@Override
	public boolean addStudent(String name) {
		for(Student student : students) {
			if(student.getName().equals(name)) {
				return false;
			}
		}
		Student newStudent = new Student(name);
		students.add(newStudent);
		return true;
	}

	@Override
	public void answerTrueFalseQuestion(String studentName, int examId, int questionNumber, boolean answer) {
		boolean examExists = false;
		for(Student student : students) {
			if(student.getName().equals(studentName)) {
				for(Integer id : student.getStudentAnswers().keySet()) {
					if(id == examId) {
						examExists = true;
						student.getStudentAnswers().get(id).getTrueFalseAnswers().put(questionNumber, answer);
					} 
				}
				
				if(!examExists) {
					for(Exam exam : exams) {
						if(exam.getId() == examId) {
							student.addExam(exam);
						}
					}
					
					Answer answers = new Answer();
					student.getStudentAnswers().put(examId, answers);
					answers.getTrueFalseAnswers().put(questionNumber, answer);
				}
			}
		}

	}

	@Override
	public void answerMultipleChoiceQuestion(String studentName, int examId, int questionNumber, String[] answer) {
		boolean examExists = false;
		for(Student student : students) {
			if(student.getName().equals(studentName)) {
				for(Integer id : student.getStudentAnswers().keySet()) {
					if(id == examId) {
						examExists = true;
						student.getStudentAnswers().get(id).getMultipleChoiceAnswers().put(questionNumber, answer);
					} 
				}
				
				if(!examExists) {
					for(Exam exam : exams) {
						if(exam.getId() == examId) {
							student.addExam(exam);
						}
					}
					
					Answer answers = new Answer();
					student.getStudentAnswers().put(examId, answers);
					answers.getMultipleChoiceAnswers().put(questionNumber, answer);
				}
			}
		}

	}

	@Override
	public void answerFillInTheBlanksQuestion(String studentName, int examId, int questionNumber, String[] answer) {
		boolean examExists = false;
		for(Student student : students) {
			if(student.getName().equals(studentName)) {
				for(Integer id : student.getStudentAnswers().keySet()) {
					if(id == examId) {
						examExists = true;
						student.getStudentAnswers().get(id).getFillInTheBlankAnswers().put(questionNumber, answer);
					} 
				}
				
				if(!examExists) {
					for(Exam exam : exams) {
						if(exam.getId() == examId) {
							student.addExam(exam);
						}
					}
					
					Answer answers = new Answer();
					student.getStudentAnswers().put(examId, answers);
					answers.getFillInTheBlankAnswers().put(questionNumber, answer);
				}
			}
		}

	}

	@Override
	public double getExamScore(String studentName, int examId) {
		double score = 0.0;
		Answer examAnswers = null;
		for(Student student : students) {
			if(student.getName().equals(studentName)) {
				for(Integer id : student.getStudentAnswers().keySet()) {
					if(id == examId) {
						examAnswers = student.getStudentAnswers().get(id);
					}
				}
				
				for(Exam exam : exams) {
					if(exam.getId() == examId) {
						for(Question question : exam.getQuestions()) {
							if(question instanceof TrueFalseQuestion) {
								TrueFalseQuestion tfq = (TrueFalseQuestion) question;
								if(examAnswers.getTrueFalseAnswers().get(tfq.getQuestionNumber()) == tfq.getAnswer()) {
									score += tfq.getPoints();
								}
							} else if(question instanceof MultipleChoiceQuestion) {
								MultipleChoiceQuestion mcq = (MultipleChoiceQuestion) question;
								boolean correct = true;
								String[] copyOfAnswers = new String[mcq.getAnswer().length];
								for(int i = 0; i < mcq.getAnswer().length; i++) {
									copyOfAnswers[i] = mcq.getAnswer()[i];
								}
								
								Arrays.sort(copyOfAnswers);
								String[] studentAnswers = examAnswers.getMultipleChoiceAnswers().get(mcq.getQuestionNumber());
								Arrays.sort(studentAnswers);
								
								if(studentAnswers.length != copyOfAnswers.length) {
									correct = false;
								} else {
									for(int i = 0; i < copyOfAnswers.length; i++) {
										if(!(studentAnswers[i].equals(copyOfAnswers[i]))) {
											correct = false;
										}
									}
									
								}
								
								if(correct) {
									score += mcq.getPoints();
								}
								
							} else if(question instanceof FillInTheBlanksQuestion) {
								FillInTheBlanksQuestion fibq = (FillInTheBlanksQuestion) question;
								String[] copyOfAnswers = new String[fibq.getAnswer().length];
								for(int i = 0; i < fibq.getAnswer().length; i++) {
									copyOfAnswers[i] = fibq.getAnswer()[i];
								}
								
								Arrays.sort(copyOfAnswers);
								String[] studentAnswers = examAnswers.getFillInTheBlankAnswers().get(fibq.getQuestionNumber());
								Arrays.sort(studentAnswers);
								
								
								for(int i = 0; i < studentAnswers.length; i++) {
									for(String answer : copyOfAnswers) {
										if(answer.equals(studentAnswers[i])) {
											score += fibq.getPoints() / copyOfAnswers.length;
										}
									}
								}
							}
						}
					}
				}
			}
		}
		return score;
	}

	@Override
	public String getGradingReport(String studentName, int examId) {
		Answer examAnswers = null;
		String report = "";
		double score = 0.0;
		double total = 0.0;
		for(Student student : students) {
			if(student.getName().equals(studentName)) {
				for(Integer id : student.getStudentAnswers().keySet()) {
					if(id == examId) {
						examAnswers = student.getStudentAnswers().get(id);
					}
				}
				
				for(Exam exam : exams) {
					if(exam.getId() == examId) {
						for(Question question : exam.getQuestions()) {
							report += "Question #" + question.getQuestionNumber() + " ";
							if(question instanceof TrueFalseQuestion) {
								TrueFalseQuestion tfq = (TrueFalseQuestion) question;
								if(examAnswers.getTrueFalseAnswers().get(tfq.getQuestionNumber()) == tfq.getAnswer()) {
									report += tfq.getPoints() + " points out of " + tfq.getPoints() + "\n";
									score += tfq.getPoints();
									total += tfq.getPoints();
								} else {
									report += 0.0 + " points out of " + tfq.getPoints() + "\n";
									total += tfq.getPoints();
								}
							} else if(question instanceof MultipleChoiceQuestion) {
								MultipleChoiceQuestion mcq = (MultipleChoiceQuestion) question;
								boolean correct = true;
								String[] copyOfAnswers = new String[mcq.getAnswer().length];
								for(int i = 0; i < mcq.getAnswer().length; i++) {
									copyOfAnswers[i] = mcq.getAnswer()[i];
								}
								
								Arrays.sort(copyOfAnswers);
								String[] studentAnswers = examAnswers.getMultipleChoiceAnswers().get(mcq.getQuestionNumber());
								Arrays.sort(studentAnswers);
								
								if(studentAnswers.length != copyOfAnswers.length) {
									correct = false;
								} else {
									for(int i = 0; i < copyOfAnswers.length; i++) {
										if(!(studentAnswers[i].equals(copyOfAnswers[i]))) {
											correct = false;
										}
									}
								}
								
								if(correct) {
									report += mcq.getPoints() + " points out of " + mcq.getPoints() + "\n";
									score += mcq.getPoints();
									total += mcq.getPoints();
								} else {
									report += 0.0 + " points out of " + mcq.getPoints() + "\n";
									total += mcq.getPoints();
								}
							} else if(question instanceof FillInTheBlanksQuestion) {
								FillInTheBlanksQuestion fibq = (FillInTheBlanksQuestion) question;
								String[] copyOfAnswers = new String[fibq.getAnswer().length];
								for(int i = 0; i < fibq.getAnswer().length; i++) {
									copyOfAnswers[i] = fibq.getAnswer()[i];
								}
								
								Arrays.sort(copyOfAnswers);
								String[] studentAnswers = examAnswers.getFillInTheBlankAnswers().get(fibq.getQuestionNumber());
								Arrays.sort(studentAnswers);
								double questionPoints = 0.0;
								
								for(int i = 0; i < studentAnswers.length; i++) {
									for(String answer : copyOfAnswers) {
										if(answer.equals(studentAnswers[i])) {
											questionPoints += fibq.getPoints() / copyOfAnswers.length;
										}
									}
								}
								
								report += questionPoints + " points out of " + fibq.getPoints() + "\n";
								score += questionPoints;
								total += fibq.getPoints();
							}
						}
					}
				}
			}
		}
		report += "Final Score: " + score + " out of " + total;
		return report;
	}

	@Override
	public void setLetterGradesCutoffs(String[] letterGrades, double[] cutoffs) {
		for(int i = 0; i < letterGrades.length; i++) {
			gradeCutoffs.put(letterGrades[i], cutoffs[i]);
		}

	}

	@Override
	public double getCourseNumericGrade(String studentName) {
		double total = 0.0;
		double grade = 0.0;
		for(Student student : students) {
			if(student.getName().equals(studentName)) {
				for(Exam exam : student.getExams()) {
					total += getExamScore(studentName, exam.getId()) / exam.getTotalPoints();
				}
				grade = (total / student.getExams().size()) * 100;
			}
		}
		return grade;
	}

	@Override
	public String getCourseLetterGrade(String studentName) {
		double numericGrade = getCourseNumericGrade(studentName);
		String letterGrade = "";
		double floor = 101.0;
		for(Entry<String, Double> entry : gradeCutoffs.entrySet()) {
			if(numericGrade >= entry.getValue() && numericGrade < floor) {
				letterGrade = entry.getKey();
				floor = entry.getValue();
			}
		}
		
		return letterGrade;
	}

	@Override
	public String getCourseGrades() {
		String report = "";
		Collections.sort(students);
		
		for(Student student : students) {
			report += student.getName() + " " + getCourseNumericGrade(student.getName()) + " ";
			report += getCourseLetterGrade(student.getName()) + "\n";
		}
		
		return report;
	}

	@Override
	public double getMaxScore(int examId) {
		double max = 0.0;
		for(Student student : students) {
			double studentScore = getExamScore(student.getName(), examId);
			if(studentScore > max) {
				max = studentScore;
			}
		}
		
		return max;
	}

	@Override
	public double getMinScore(int examId) {
		double minimum = 100.0;
		for(Student student : students) {
			double studentScore = getExamScore(student.getName(), examId);
			if(studentScore < minimum) {
				minimum = studentScore;
			}
		}
		
		return minimum;
	}

	@Override
	public double getAverageScore(int examId) {
		double total = 0.0;
		int count = 0;
		
		for(Student student : students) {
			double studentScore = getExamScore(student.getName(), examId);
			total += studentScore;
			count++;
		}
		
		double avg = (total / count);
		
		return Double.parseDouble(String.format("%.2f", avg));
	}

	@Override
	public void saveManager(Manager manager, String fileName) {
		try {
			File file = new File(fileName);
			ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream(file));
			output.writeObject(manager);
			output.close();
		} catch (FileNotFoundException e) {
			System.err.println();
		} catch (IOException e) {
			System.err.println(e.getMessage());
		}

	}

	@Override
	public Manager restoreManager(String fileName) {
		try {
			FileInputStream fileIn = new FileInputStream(fileName);
			ObjectInputStream objectIn = new ObjectInputStream(fileIn);
			Manager manager = (Manager) objectIn.readObject();
			objectIn.close();
			fileIn.close();
			
			return manager;
		} catch(IOException e) {
			System.err.println();
			return null;
		} catch(ClassNotFoundException e) {
			System.err.println();
			return null;
		}
	}
}
