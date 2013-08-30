/**
 * 
 */
package mph.entity;

import java.util.List;

import javax.persistence.*;

import java.util.*;

/**
 * @author Andrea
 *
 */
@Entity
@Table(name="GROUPON")
public class Group implements java.io.Serializable{

	@Id
	@GeneratedValue
	@Column(name="GROUPON_ID")
	private long idGroup;
	
	@ManyToMany
	@JoinTable(
			name="STUDENT_GROUPS",
			joinColumns={@JoinColumn(name="GROUPON_ID")},
			inverseJoinColumns={@JoinColumn(name="STUDENT_ID")}
			)
	private List<Student> students;
	
	@ManyToMany
	@JoinTable(
			name="GROUP_GROUPS",
			joinColumns={@JoinColumn(name="GROUPON_ID2")},
			inverseJoinColumns={@JoinColumn(name="GROUPON_ID")}
			)
	private List<Group> groups;
	
	/**
	 * @return the groups
	 */
	public List<Group> getGroups() {
		return groups;
	}

	/**
	 * @param groups the groups to set
	 */
	public void setGroups(List<Group> groups) {
		this.groups = groups;
	}

	@ManyToOne
	@JoinColumn(name="PROJECT_ID")
	private Project project;
	
	@OneToMany(mappedBy="group")
	private Set<File> files;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="PROFESSOR_ID")
	private Professor professor;
	
	@Column(name="GROUP_MARK")
	private int groupMark;
	
	private String groupName;

	/**
	 * @return the idGroup
	 */
	public long getIdGroup() {
		return idGroup;
	}

	/**
	 * @param idGroup the idGroup to set
	 */
	public void setIdGroup(long idGroup) {
		this.idGroup = idGroup;
	}

	/**
	 * @return the groupName
	 */
	public String getGroupName() {
		return groupName;
	}

	/**
	 * @param groupName the groupName to set
	 */
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}

	public List<Student> getStudents() {
		return students;
	}

	public void setStudents(List<Student> students) {
		this.students = students;
	}

	public Professor getProfessor() {
		return professor;
	}

	public void setProfessor(Professor professor) {
		this.professor = professor;
	}

	public int getGroupMark() {
		return groupMark;
	}

	public void setGroupMark(int groupMark) {
		this.groupMark = groupMark;
	}
}
