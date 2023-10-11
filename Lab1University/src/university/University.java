package university;
import java.util.logging.Logger;

/**
 * This class represents a university education system.
 * 
 * It manages students and courses.
 *
 */
public class University {
	public static final String SEPARATOR=",";
	public static final int MAXCOURSES = 50;
	public static final int NMAXSTUDENT = 100;
	public static final int MIN_ID = 10000;
	public static final int MIN_CODE = 10;
	
	private Course[] courses;
	private int nextCode=MIN_CODE;
	
	private Student[] students;
	private int nextID=MIN_ID;
	
	private String universityName;
	private String rectorName;
	
	
// R1
	/**
	 * Constructor
	 * @param name name of the university
	 */
	public University(String name){
		this.universityName=name;
		
		//initialize the array of object student to keep infos about our students 
		students = new Student[NMAXSTUDENT];  //max 100 students
		
		//initialize the array of object course to keep infos about our courses
		courses=new Course[MAXCOURSES]; //max 50 courses
			
	}
	
	/**
	 * Getter for the name of the university
	 * 
	 * @return name of university
	 */
	public String getName(){
		
		return this.universityName;
	}
	
	/**
	 * Defines the rector for the university
	 * 
	 * @param first first name of the rector
	 * @param last	last name of the rector
	 */
	public void setRector(String first, String last){
		this.rectorName=first+" "+last;
	}
	
	/**
	 * Retrieves the rector of the university
	 * 
	 * @return name of the rector
	 */
	public String getRector(){
		
		return rectorName;
	}
	
// R2
	/**
	 * Enrol a student in the university
	 * 
	 * @param first first name of the student
	 * @param last last name of the student
	 * 
	 * @return unique ID of the newly enrolled student
	 */
	public int enroll(String first, String last){
		Student s;
		
		if (nextID<=10100) {
			//setting name and surname to the new student
			s=new Student(first, last);
			//giving the id to the new student
			s.setId(nextID);
			
			//adding the new student in the array of object student
			students[nextID-MIN_ID]=s;
			
			logger.info("New student enrolled: "+nextID+" "+first+" "+last);
			
			//updating the value of nextID and returning the ID given to the new student
			return nextID++; 
			
		}
		
		else {
			//we reached the max number of students, we cannot accept new students!
			System.out.println("Max number of students reached!");
			return -1;
		}
	}
	
	/**
	 * Retrieves the information for a given student
	 * 
	 * @param id the ID of the student
	 * 
	 * @return information about the student
	 */
	public String student(int id){
		int index;
		
		if (id<MIN_ID || id>=nextID) {
			// id not in our university range of ids
			logger.info("Error Student "+id+" is not enrolled in university "+universityName);
			return null;
		}
		index=id-MIN_ID;
		
		String first, last;
		
		first=students[index].getName();
		last=students[index].getSurname();
		
		String output=id+" "+first+" "+last;
		
		return output;
	}
	
// R3
	/**
	 * Activates a new course with the given teacher
	 * 
	 * @param title title of the course
	 * @param teacher name of the teacher
	 * 
	 * @return the unique code assigned to the course
	 */
	public int activate(String title, String teacher){
		
		if (nextCode<60) {
			//we still have less than 50 courses activated so we can activate a new one
			courses[nextCode-MIN_CODE]=new Course(title,teacher,nextCode);
			logger.info("New course activated: "+nextCode+", "+title+" "+teacher);
			return nextCode++;
		}
		else {
			//we already have 50 courses activated! No more can be activated!
			System.out.println("we already have 50 courses activated! No more can be activated!");
			return -1;
		}
	}
	
	/**
	 * Retrieve the information for a given course.
	 * 
	 * The course information is formatted as a string containing 
	 * code, title, and teacher separated by commas, 
	 * e.g., {@code "10,Object Oriented Programming,James Gosling"}.
	 * 
	 * @param code unique code of the course
	 * 
	 * @return information about the course
	 */
	public String course(int code){
		int index;
		String nameC, teacherName;
		if (code>=nextCode) {
			logger.info("ERROR: course "+code+" isn't activated in university "+universityName);
			return "";
		}
		index = code - MIN_CODE;
		
		nameC=courses[index].getTitle();
		teacherName=courses[index].getTeacher();
		
		return code+SEPARATOR+nameC+SEPARATOR+teacherName;
	}
	
// R4
	/**
	 * Register a student to attend a course
	 * @param studentID id of the student
	 * @param courseCode id of the course
	 */
	public void register(int studentID, int courseCode){
		//looking for the student and the course
		Student s=students[studentID-MIN_ID];
		Course c=courses[courseCode-MIN_CODE];
		//if the student or the course doesn't exist 
		if(s==null || c==null) {
			System.out.println("Student or course id given as argument doesn't exist in the university");
			return;
		}
		
		//otherwise we can register the student to the course
		c.enroll(s);
		//and register the course to the list of courses enrolled by the student s
		s.enroll(c);
		
		logger.info("Student " + studentID + " signed up for course " + courseCode);
		
	}
	
	/**
	 * Retrieve a list of attendees
	 * 
	 * @param courseCode unique id of the course
	 * @return list of attendees separated by "\n"
	 */
	public String listAttendees(int courseCode){
		String output;
		Course c;
				
		if (courseCode < MIN_CODE || courseCode > (MIN_CODE+MAXCOURSES)) {
			System.out.println("invalid course code");
			return "";
		}
			
		//get the corresponding course
		c=courses[courseCode-MIN_CODE];
		
		output=c.getListAttendees();
		return output;
	}

	/**
	 * Retrieves the study plan for a student.
	 * 
	 * The study plan is reported as a string having
	 * one course per line (i.e. separated by '\n').
	 * The courses are formatted as describe in method {@link #course}
	 * 
	 * @param studentID id of the student
	 * 
	 * @return the list of courses the student is registered for
	 */
	public String studyPlan(int studentID){
		String output;
		
		if (studentID < MIN_ID || studentID > (MIN_ID+NMAXSTUDENT)) {
			System.out.println("invalid student id");
			return "";
		}
		
		//get the corresponding student
		Student s;
		
		s=students[studentID-MIN_ID];
		
		output=s.getAttendedCourses();
		
		return output;
	}

// R5
	/**
	 * records the grade (integer 0-30) for an exam can 
	 * 
	 * @param studentId the ID of the student
	 * @param courseID	course code 
	 * @param grade		grade ( 0-30)
	 */
	public void exam(int studentId, int courseID, int grade) {
		Exam e;
		
		if (studentId < MIN_ID || studentId > (nextID)) {
			System.out.println("Invalid student id");
			return;
		}
		
		if (courseID < MIN_CODE || courseID > (nextCode)) {
			System.out.println("Invalid course id");
			return;
		}
		
		e=new Exam(students[studentId-MIN_ID],courses[courseID-MIN_CODE],grade);
		
		logger.info("Student "+studentId+" took an exam in course "+courseID+" with grade "+grade);
		
		
	}

	/**
	 * Computes the average grade for a student and formats it as a string
	 * using the following format 
	 * 
	 * {@code "Student STUDENT_ID : AVG_GRADE"}. 
	 * 
	 * If the student has no exam recorded the method
	 * returns {@code "Student STUDENT_ID hasn't taken any exams"}.
	 * 
	 * @param studentId the ID of the student
	 * @return the average grade formatted as a string.
	 */
	public String studentAvg(int studentId) {
		float average;
		Student s;
		String avg;
		
		s=students[studentId-MIN_ID];
		average=s.getAvg();
		
		if (average==0) {
			return "Student "+studentId+" hasn't taken any exams";
		} 
		else {
			avg=String.valueOf(average);
			return "Student "+studentId+" : "+avg;
		}
	}
	
	/**
	 * Computes the average grades of all students that took the exam for a given course.
	 * 
	 * The format is the following: 
	 * {@code "The average for the course COURSE_TITLE is: COURSE_AVG"}.
	 * 
	 * If no student took the exam for that course it returns {@code "No student has taken the exam in COURSE_TITLE"}.
	 * 
	 * @param courseId	course code 
	 * @return the course average formatted as a string
	 */
	public String courseAvg(int courseId) {
		Course c;
		Float average;
		String avg;
		
		c=courses[courseId-MIN_CODE];
		average=c.getAvgExams();
		
		if (average == 0) {
			return "No student has taken the exam in "+c.getTitle();
		}
		
		avg = String.valueOf(average);
		
		return "The average for the course "+c.getTitle()+" is: "+avg;
		
	}
	

// R6
		
	/**
	 * Retrieve information for the best students to award a price.
	 * 
	 * The students' score is evaluated as the average grade of the exams they've taken. 
	 * To take into account the number of exams taken and not only the grades, 
	 * a special bonus is assigned on top of the average grade: 
	 * the number of taken exams divided by the number of courses the student is enrolled to, multiplied by 10.
	 * The bonus is added to the exam average to compute the student score.
	 * 
	 * The method returns a string with the information about the three students with the highest score. 
	 * The students appear one per row (rows are terminated by a new-line character {@code '\n'}) 
	 * and each one of them is formatted as: {@code "STUDENT_FIRSTNAME STUDENT_LASTNAME : SCORE"}.
	 * 
	 * @return info on the best three students.
	 */
	public String topThreeStudents() {
		float tempAvg, tempBonus, tempScore,temp1,temp2;
		int i,j;
		float[] topThreeScores=new float[3];
		Student[] top3Students=new Student[3];
		Student tempS1,tempS2;
		
		//for each student...
		for (i=0; i<NMAXSTUDENT && students[i]!=null; i++) {
			
			tempAvg=students[i].getAvg();
			tempBonus=students[i].getBonus();
			tempScore=tempAvg+tempBonus;
			
			//scroll the topThree array...
			for (j=0; j<3; j++) {
				if (topThreeScores[j]<tempScore) { // we replace the j position
					
					temp1=topThreeScores[j];
					topThreeScores[j]=tempScore;
					
					tempS1=top3Students[j];
					
					//proceeding to replace the lower positions
					if (j==0) { //we added a the new first student
						
						//updating the first position, shifting the next positions
						
						top3Students[j]=students[i];
						
						tempS2=top3Students[1];
						top3Students[1]=tempS1;
						top3Students[2]=tempS2;
						
						//replacing second and third
						temp2=topThreeScores[1];
						topThreeScores[1]=temp1;
						topThreeScores[2]=temp2;
					}
					else if (j==1) { //we added the new second student
						
						top3Students[j]=students[i];
						
						//replacing the third position
						top3Students[2]=tempS1;
						topThreeScores[2]=temp1;
					}
					else { //we added the new third student
						
						top3Students[j]=students[i];
					}
					break;
				}
			}
			
			
		}
		//when the for cycle ends, I'll have in the topThreeScores the scores of the 3 best students
		//and in top3Students I'll have all the infos about the best three students
		
		String output = "",first,last,score;
		
		//scrolling top3Students and topThreeScores arrays
		for (i=0; i<3 && top3Students[i]!=null; i++) {
			first=top3Students[i].getName();
			last=top3Students[i].getSurname();
			score=String.valueOf(topThreeScores[i]);
			output+=first+" "+last+" : "+score+"\n";
		}
		
		return output;
	}

// R7
    /**
     * This field points to the logger for the class that can be used
     * throughout the methods to log the activities.
     */
    private final static Logger logger = Logger.getLogger("University");
    //all requests done
}
