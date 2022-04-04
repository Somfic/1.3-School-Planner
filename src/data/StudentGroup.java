package data;
import java.util.ArrayList;
import java.util.List;

public class StudentGroup {
	private List<Student> students;
	private String name;

	public StudentGroup(String name) {
        this.name = name;
		this.students = new ArrayList<>();

		for (int i = 0; i < Math.random() * 6 + 6; i++) {
			this.students.add(new Student(Gender.MALE, "Hello", (int) (Math.random() * 99999)));
		}

        this.name = name;
	}

	public void addStudent(Student student) {
		this.students.add(student);
	}

	public void addStudents(List<Student> students) {
		this.students.addAll(students);
	}

	public List<Student> getStudents() {
		return this.students;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
