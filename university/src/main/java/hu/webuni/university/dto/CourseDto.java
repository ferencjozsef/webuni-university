package hu.webuni.university.dto;

import java.util.Set;

import lombok.Data;

@Data
public class CourseDto {
	
	private int id;
	private String name;
	private Set<StudentDto> students;
	private Set<TeacherDto> teachers;
}
