/**
 * 
 */
package mph.entity;

import java.util.List;
import java.util.Set;

import javax.persistence.*;

/**
 * @author Andrea
 *
 */
@Entity
@Table(name="PROFESSOR")
public class Professor implements java.io.Serializable {
	
	@Id
	@GeneratedValue
	@Column(name="PROFESSOR_ID")
	private long idProfessor;
	
	private String firstName;
	private String lastName;
	private String username;
	private String password;
	private boolean professor;
	

	@OneToMany(mappedBy="professor")
	private Set<Group> groups;
	
	@OneToMany(mappedBy="professor")
	private Set<Project> project;
	
	
	public long getIdProfessor() {
		return idProfessor;
	}
	public void setIdProfessor(long idProfessor) {
		this.idProfessor = idProfessor;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public boolean isProfessor() {
		return professor;
	}
	public void setProfessor(boolean professor) {
		this.professor = professor;
	}
	
}
