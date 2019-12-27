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
public class Account extends AbstractPersistable<Long> {

	private String name;
	private String password;

	@ElementCollection(fetch = FetchType.EAGER)
	private List<String> authorities;

	@ManyToMany(mappedBy = "accounts")
	private List<Course> courses = new ArrayList<>();
	
	@OneToMany(mappedBy = "account")
	private List<Grade> grades = new ArrayList<>();
	
	
}
