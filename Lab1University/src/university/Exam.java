package university;

public class Exam {

	private int grade;
	
	//constructor
	public Exam(Student s, Course c, int grade) {
		
		//assign the grade
		this.grade=grade;
		
		//adding the exam to the student's array of exams
		s.addExam(this);
		
		//adding the exam to the course's array of exams
		c.addExam(this);
	}
	public int getGrade() {
		return grade;
	}
	
	public void setGrade(int grade) {
		this.grade=grade;
	}
}
