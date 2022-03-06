
package hu.webuni.university.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import hu.webuni.university.model.Student;
import hu.webuni.university.repository.StudentRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class StudentService {
	
	private final StudentRepository studentRepository;
	
	public Optional<Student> findById(int id) {
		return studentRepository.findById(id);
	}

}
