package hu.webuni.university.web;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import hu.webuni.university.dto.StudentDto;
import hu.webuni.university.mapper.StudentMapper;
import hu.webuni.university.model.Student;
import hu.webuni.university.service.StudentService;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/students")
public class StudentController {

	private final StudentService studentService;
	private final StudentMapper studentMapper;

	@GetMapping("/{id}")
	public StudentDto getById(@PathVariable int id) {
		Student student = studentService.findById(id)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
		
		return studentMapper.studentToDto(student);
	}
}
