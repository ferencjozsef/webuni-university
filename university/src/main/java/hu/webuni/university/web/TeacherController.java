package hu.webuni.university.web;

import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.server.ResponseStatusException;

import hu.webuni.university.api.TeacherControllerApi;
import hu.webuni.university.api.model.TeacherDto;
import hu.webuni.university.mapper.TeacherMapper;
import hu.webuni.university.model.Teacher;
import hu.webuni.university.service.TeacherService;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class TeacherController implements TeacherControllerApi {
	
	private final NativeWebRequest nativeWebRequest;
	private final TeacherService teacherService;
	private final TeacherMapper teacherMapper;
	
	@Override
	public Optional<NativeWebRequest> getRequest() {
		return Optional.of(nativeWebRequest);
	}

	@Override
	public ResponseEntity<TeacherDto> getTeacherById(Integer id) {
		Teacher teacher = teacherService.findById(id)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

		return ResponseEntity.ok(teacherMapper.teacherToDto(teacher));
	}
	
	

}
