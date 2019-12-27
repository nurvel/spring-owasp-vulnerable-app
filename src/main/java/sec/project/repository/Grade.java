package sec.project.repository;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

import org.springframework.data.jpa.domain.AbstractPersistable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import sec.project.domain.Account;
import sec.project.domain.Course;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Grade extends AbstractPersistable<Long> {

	@ManyToOne
	private Course course; 

	@ManyToOne
	private Account account;

	private int grade;

}
