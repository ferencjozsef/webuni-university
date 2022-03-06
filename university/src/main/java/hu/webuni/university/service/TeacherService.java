
package hu.webuni.university.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import hu.webuni.university.model.Teacher;
import hu.webuni.university.repository.TeacherRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class TeacherService {
	
	private final TeacherRepository teacherRepository;
	
	public Optional<Teacher> findById(int id) {
		return teacherRepository.findById(id);
	}

}
