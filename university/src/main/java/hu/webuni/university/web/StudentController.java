package hu.webuni.university.web;

import java.io.IOException;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import hu.webuni.university.api.StudentControllerApi;
import hu.webuni.university.api.model.StudentDto;
import hu.webuni.university.mapper.StudentMapper;
import hu.webuni.university.model.Student;
import hu.webuni.university.service.StudentService;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class StudentController implements StudentControllerApi {
	
	private final NativeWebRequest nativeWebRequest;
	private final StudentService studentService;
	private final StudentMapper studentMapper;
	
	@Override
	public Optional<NativeWebRequest> getRequest() {
		return Optional.of(nativeWebRequest);
	}

	@Override
	public ResponseEntity<StudentDto> getStudentById(Integer id) {
		Student student = studentService.findById(id)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
	
		return ResponseEntity.ok(studentMapper.studentToDto(student));
	}

	@Override
	public ResponseEntity<Resource> getProfilePicture(Integer id) {

		return ResponseEntity.ok(studentService.getProfilePicture(id));
	}

	@Override
	public ResponseEntity<Void> uploadProfilePicture(Integer id, @Valid MultipartFile content) {
		try {
			studentService.saveProfilePicture(id, content.getInputStream());
		} catch (IOException e) {
			e.printStackTrace();
			return ResponseEntity.internalServerError().build();
		}
		return ResponseEntity.ok().build();
	}

	@Override
	public ResponseEntity<Void> deleteProfilePicture(Integer id) {
		studentService.deleteProfilePicturePath(id);
		
		return ResponseEntity.ok().build();

	}
	
	
}
