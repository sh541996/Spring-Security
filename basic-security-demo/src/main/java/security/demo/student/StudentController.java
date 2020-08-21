package security.demo.student;

import java.util.Arrays;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/students")
public class StudentController {
	
	private static final List<Student> STUDENTS = Arrays.asList(
			new Student(1, "james"), 
			new Student(2, "ravi"),
			new Student(3, "rohit"));
	
	@GetMapping("/{studentId}")
	public Student getStudent(@PathVariable Integer studentId) {
		
		return (Student) STUDENTS.stream()
				.filter(student -> studentId.equals(student.getStudentId()))
				.findFirst()
				.orElseThrow(() -> new IllegalStateException("student with "+ studentId + "does not exist"));
				
	}

}
