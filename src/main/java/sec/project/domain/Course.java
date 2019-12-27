package sec.project.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

import org.springframework.data.jpa.domain.AbstractPersistable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Course extends AbstractPersistable<Long> {

	private String name;

	@ManyToMany
	private List<Account> accounts = new ArrayList<>();

	@OneToMany(mappedBy = "course")
	private List<CourseSubmission> grades = new ArrayList<>();

	@ElementCollection
	private List<String> feedBack = new ArrayList<>();

}
