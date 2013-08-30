/**
 * 
 */
package mph.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.persistence.*;

import org.hibernate.mapping.Collection;
/**
 * @author Andrea
 *
 */
@Entity 
@Table(name="STUDENT")
public class Student implements java.io.Serializable {

	@Id 
	@GeneratedValue
	@Column(name="STUDENT_ID")
	private long idStudent;
	
	private String firstName;
	private String lastName;
	private String username;
	private String password;
	
	
	@ManyToMany(mappedBy="students")
	private List<Group> group;
	
	public Student()
	{
		group = new ArrayList<Group>();
	}
	
	public long getIdStudent() {
		return idStudent;
	}
	public void setIdStudent(long idStudent) {
		this.idStudent = idStudent;
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
	public List<Group> getGroup() {
		return group;
	}
	public void setGroup(List<Group> group) {
		this.group = group;
	}
}
