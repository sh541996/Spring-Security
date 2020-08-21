package security.demo.controller;

import java.util.Arrays;
import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import security.demo.student.Student;

@RequestMapping("/management/api/students")
@RestController
public class StudentManagementController {
	
	private static final List<Student> STUDENTS = Arrays.asList(
			new Student(1, "james"), 
			new Student(2, "ravi"),
			new Student(3, "rohit"));

	@GetMapping
	@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_ADMINTRAINEE')")
	public List<Student> getAllStudents() {
		
		return STUDENTS;
	}
	
	@PostMapping
	@PreAuthorize("hasAuthority('student:write')")
	public void registerNewStudent(@RequestBody Student student) {
		
		System.out.println(student);
	}
	
	@DeleteMapping("{studentId}")
	@PreAuthorize("hasAuthority('student:write')")
	public void deleteStudent(@PathVariable Integer studentId) {
		
		System.out.println(studentId);
	}
	
	@PutMapping("{studentId}")
	@PreAuthorize("hasAuthority('student:write')")
	public void updateStudentId(@PathVariable Integer studentId,@RequestBody Student student) {
		
		System.out.println(String.format("%s %s", studentId, student));
	}
}
