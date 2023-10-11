package university;


public class Student {
	
	public static final String SEPARATOR=",";
	
	private String name;
	private String surname;
	private int id;
	
	/* each student can enroll in a maximum of 25 courses */
	private Course[] appliedCourses;
	public static final int MAX_ALLOWED_COURSES = 25;
	
	//array of exams where I take notes of grades
	private Exam[] exams;
	
	
	public Student(String name, String surname) {
		this.name = name;
		this.surname = surname;
		
		//creation of the array appliedCourses
		appliedCourses = new Course[MAX_ALLOWED_COURSES];
		
		//creation of the array exams
		exams = new Exam[MAX_ALLOWED_COURSES];
	}
	
	//method to register a course into the array of courses applied by the student
	//It needs the course to be registered as argument
	public void enroll(Course c) {
		int i;
		
		
		for (i=0; i<MAX_ALLOWED_COURSES; i++) {
			//we scroll the array till we find a free cell
			if (this.appliedCourses[i]==null) {
				//once the free cell has been found we save the course into it
				this.appliedCourses[i]=c;
				return;
			}
			
		}
		
		//if we exit the for cycle without finding a free cell this means that the student cannot apply to another course
		System.out.println("The student cannot enroll to more than 25 courses!");
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getSurname() {
		return surname;
	}
	
	public void setSurname(String surname) {
		this.surname = surname;
	}
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	//returns a string which contains the list of courses attended by a student
	// code,title,prof name
	public String getAttendedCourses(){
		String out="";
		int i, codeC;
		String titleC, prof_name;
		for(i=0; i<MAX_ALLOWED_COURSES && appliedCourses[i]!=null;i++) {
			codeC=appliedCourses[i].getCourseCode();
			titleC=appliedCourses[i].getTitle();
			prof_name=appliedCourses[i].getTeacher();
			
			out=out+codeC+SEPARATOR+titleC+SEPARATOR+prof_name+"\n";
		}
		
		return out;
	}
	
	//add an exam to the student's array of exams
	public void addExam(Exam e) {
		int i;
		
		//scroll the exams array till I find a free cell
		for (i=0; i<MAX_ALLOWED_COURSES; i++) {
			if (exams[i]==null) {
				//free cell!
				exams[i]=e;
				return;
			}
		}
	}
	
	
	//method to get the average of exams taken by a student
	public float getAvg() {
		int i, count=0;
		float sum=0;
		for (i=0; i<MAX_ALLOWED_COURSES && exams[i]!=null; i++) {
			sum+=exams[i].getGrade();
			count++;
		}
		
		if(count==0) {
			return 0;
		}
		
		return sum/count;
	}
	
	
	//method which gives the number of exams taken by a student
	public int getNumberExamsTaken() {
		
		int i;
		
		if (exams[0]==null) {
			return 0;
		}
		
		for (i=0; i<MAX_ALLOWED_COURSES && exams[i]!=null; i++);
		
		return i;
	}
	
	//method used to get the number of courses in which the student is enrolled
	public int getCoursesNumber() {
		
		int i;
		
		if (appliedCourses[0]==null) { //the student hasn't been enrolled in any course!!!
			return 0;
		}
		
		for (i=0; i<MAX_ALLOWED_COURSES && appliedCourses[i]!=null; i++);
		
		return i;
	}
	
	//method used to get the bonus for the student
	public float getBonus() {
		
		int nExamsTaken, nCoursesEnrolled;
		float bonus;
		
		
		nExamsTaken=this.getNumberExamsTaken();
		
		if (nExamsTaken==0) {
			return 0;//the student didn't take any exam so he will have bonus equal to 0
		}
		
		nCoursesEnrolled=this.getCoursesNumber();
		
		bonus=(float) 10*nExamsTaken/nCoursesEnrolled;
		
		return bonus;
	}
}
