package sec.project.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.springframework.data.jpa.domain.AbstractPersistable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Grade extends AbstractPersistable<Long> {

	@ManyToOne 
	private Course course;  

//	@OneToMany (mappedBy ="grade")
//	private List<Course> courses = new ArrayList<>();
	
	@ManyToOne
	private Account account;

	private int grade;

}
