
package hu.webuni.university.service;

import java.util.List;
import java.util.Optional;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import hu.webuni.university.model.Student;
import hu.webuni.university.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Service
@Slf4j
public class StudentService {
	
	private final StudentRepository studentRepository;
	private final CentralEducationService centralEducationService;
	
	public Optional<Student> findById(int id) {
		return studentRepository.findById(id);
	}
	
	@Scheduled (cron = "${university.freeSemesterUpdater.cron}")
	public void updateFreeSemesters() {
		List<Student> students = studentRepository.findAll();
		
		students.forEach(student -> {
			System.out.format("Get number of free semesters of student %s%n", student.getName());
			
			try {
				Integer eduId = student.getEduId();
				if (eduId != null) {
					int numberFreeSemesters = centralEducationService.getFreeSemestersForStudent(eduId);
					student.setFreeSemesters(numberFreeSemesters);
					studentRepository.save(student);
				}
				
			} catch (Exception e) {
				System.out.println("Error calling central education service");
				log.error("Error calling central education service.", e);
			}
		});
	}

}
