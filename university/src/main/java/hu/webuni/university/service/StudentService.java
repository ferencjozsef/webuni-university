
package hu.webuni.university.service;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Optional;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

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
	
	@Value("${university.content.profilePics}")
	private String profilePicsFolder;
	
	@PostConstruct
	public void init() {
		try {
			Files.createDirectories(Path.of(profilePicsFolder));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
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

	public Resource getProfilePicture(Integer id) {
		
		FileSystemResource fileSystemResource = new FileSystemResource(getProfilePicturePath(id));
		
		if(!fileSystemResource.exists()) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}
		return fileSystemResource;
	}

	private Path getProfilePicturePath(Integer id) {
		return Paths.get(profilePicsFolder, id.toString() + ".jpg");
	}

	public void saveProfilePicture(Integer id, InputStream is) {
		try {
			Files.copy(is, getProfilePicturePath(id), StandardCopyOption.REPLACE_EXISTING);
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
	
	public void deleteProfilePicturePath(Integer id) {
		try {
			Files.delete(getProfilePicturePath(id));
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
	
	@Transactional
	public void updateBalance(int studentId, int amount) {
		studentRepository.findById(studentId).ifPresent(s -> s.setBalance(s.getBalance() + amount));
	}
}
