package hu.webuni.university.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;

import hu.webuni.university.model.Course;
import hu.webuni.university.model.QCourse;
import hu.webuni.university.repository.CourseRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class CourseService {
	
	private final CourseRepository courseRepository;
	
	@Transactional
	public List<Course> searchWithRelationships(Predicate predicate, Pageable pageable) {
//		List<Course> courses = courseRepository.findAll(predicate, "Course.students");
//		courses = courseRepository.findAll(QCourse.course.in(courses), "Course.teachers");
		List<Course> courses = courseRepository.findAll(predicate, pageable).getContent();
		BooleanExpression inByCourseId = QCourse.course.in(courses);
		courses = courseRepository.findAll(inByCourseId, "Course.teachers", Sort.unsorted());
		courses = courseRepository.findAll(inByCourseId, "Course.students", pageable.getSort());
		return courses;
	}

	public Optional<Course> findById(int id) {
		return courseRepository.findById(id);
	}

}
