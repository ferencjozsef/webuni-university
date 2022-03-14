package hu.webuni.university.model;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;

import org.hibernate.envers.Audited;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Entity
@Audited
@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = true)
@ToString(onlyExplicitlyIncluded = true)
public class Student extends UniversityUser{

	private int semester;
	
	@ManyToMany(mappedBy = "students")
	private Set<Course> courses;
	
	private Integer eduId;
	
	private Integer freeSemesters;
	
	private int balance;

	@Override
	public UserType getUserType() {
		return UserType.STUDENT;
	}
}
