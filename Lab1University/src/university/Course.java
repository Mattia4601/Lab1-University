package university;


public class Course {
	//max number of students for each course is 100
		public static final int MAXN_STUD = 100;
	
		private int courseCode;
		private String title;
		private String teacher;
		
		/* each course can be enrolled by a maximum of 100 students */
		private Student[] enrolledStudents;
		public static final int MAXSTUDENTS_ENROLLED = 100;
		
		//array of exams for a course
		Exam[] exams;
		
		//constructor
		public Course(String title, String teacher, int code) {
			this.title=title;
			this.teacher=teacher;
			this.courseCode=code;
			
			//creation of the array enrolledStudents
			enrolledStudents = new Student[MAXSTUDENTS_ENROLLED];
			
			//creation of the array exams
			exams = new Exam[MAXSTUDENTS_ENROLLED];
			
		}

		
		/* method to register a student into the array of student of a course
		 * it needs the student item, that we have to register, as argument
		 */
		public void enroll(Student s) {
			int i;
			
			for (i=0; i<MAXSTUDENTS_ENROLLED; i++) {
				if (enrolledStudents[i]==null) {
					//add the student to the course
					enrolledStudents[i]=s;
					return;
				}
				
			}
			
			//if the course has more than 100 students enrolled
			System.out.println("The course cannot have more than 100 students enrolled!");
		}
		
		
		//getter title
		public String getTitle() {
			return this.title;
		}
		
		//getter teacher
		public String getTeacher() {
			return this.teacher;
		}

		public String getListAttendees() {
			String out="", nameS,surnameS;
			int i,idS;
			
			for (i=0; i<MAXSTUDENTS_ENROLLED; i++) {
				
				if (this.enrolledStudents[i]==null)
					break;
				
				idS=this.enrolledStudents[i].getId();
				nameS=this.enrolledStudents[i].getName();
				surnameS=this.enrolledStudents[i].getSurname();
				
				out=out+idS+" "+nameS+" "+surnameS+"\n";
			}
			return out;
		}


		public int getCourseCode() {
			return courseCode;
		}


		public void setCourseCode(int courseCode) {
			this.courseCode = courseCode;
		}
		
		//method to add an exam into the course array of exams
		public void addExam(Exam e) {
			int i;
			
			//scrolling the array till you find a free cell
			for (i=0; i<MAXSTUDENTS_ENROLLED; i++) {
				if (exams[i]==null) {
					exams[i]=e;
					return;
				}
			}
		}
		
		//method used to get the average of the exams done for a corresponding course
		public float getAvgExams(){
			float sum=0;
			int i, count=0;
			
			for (i=0; i<MAXSTUDENTS_ENROLLED && exams[i]!=null; i++) {
				
				if (exams[i]!=null) {
					sum+=(float)exams[i].getGrade();
					count++;
				}
			}
			
			if (count==0) {
				//no exams done for this course yet
				return 0;
			}
			return (sum/count);
		}
}
