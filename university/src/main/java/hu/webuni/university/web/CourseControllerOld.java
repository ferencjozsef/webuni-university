package hu.webuni.university.web;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.data.domain.Pageable;
import org.springframework.data.querydsl.binding.QuerydslPredicate;
import org.springframework.data.web.SortDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;

import com.querydsl.core.types.Predicate;

import hu.webuni.university.api.model.CourseDto;
import hu.webuni.university.mapper.CourseMapper;
import hu.webuni.university.model.Course;
import hu.webuni.university.model.HistoryData;
import hu.webuni.university.repository.CourseRepository;
import hu.webuni.university.service.CourseService;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
//@RestController
@RequestMapping("/api/courses")
public class CourseControllerOld {

	private final CourseService courseService;
	private final CourseRepository courseRepository;

	private final CourseMapper courseMapper;

	@PostMapping
	public CourseDto createCourse(@RequestBody @Valid CourseDto courseDto) {
		Course course = courseRepository.save(courseMapper.dtoToCourse(courseDto));
		return courseMapper.courseToDto(course);
	}

	@GetMapping("/search")
	public List<CourseDto> search(@QuerydslPredicate(root = Course.class) Predicate predicate,
			@RequestParam Optional<Boolean> full,
			@SortDefault("id") Pageable pageable) {
		
		Boolean isFull = full.orElse(false);
		if(isFull) {
//			Iterable<Course> courses = courseRepository.findAllWithRelationships(predicate);
			Iterable<Course> courses = courseService.searchWithRelationships(predicate, pageable);
			return courseMapper.coursesToDtos(courses);
			
		} else {
			Iterable<Course> courses = courseRepository.findAll(predicate, pageable);
			return courseMapper.courseSummariesToDtos(courses);
		}
	}
	
	@GetMapping("/{id}")
	public CourseDto getById(@PathVariable int id) {
		Course course = courseService.findById(id)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
		
		return courseMapper.courseSummaryToDto(course);
	}
	
	@GetMapping("/{id}/history")
	public List<HistoryData<CourseDto>> getHistory(@PathVariable Integer id) {
		List<HistoryData<Course>> courses = courseService.getHistoryById(id);
		return courseMapper.coursesHistoryToDtos(courses);
	}
	
	@GetMapping("/{id}/versions")
	public CourseDto getVersionAt(@PathVariable Integer id, @RequestParam @NotNull @Valid OffsetDateTime at) {
		return courseMapper.courseToDto(courseService.getVersionAt(id, at));
	}

}
