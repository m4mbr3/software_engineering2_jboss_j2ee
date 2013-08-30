/**
 * 
 */
package mph.entity;

import java.util.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * @author Andrea
 *
 */
@Entity
@Table(name="PROJECT")
public class Project implements java.io.Serializable{

	@Id
	@GeneratedValue
	@Column(name="PROJECT_ID")
	private long idProject;
	
	@OneToMany(mappedBy="project")
	private Set<Group> groups;
	
	@OneToMany(mappedBy="project")
	private Set<Deliverable> deliverable;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="PROFESSOR_ID")
	private Professor professor;
	
	
	
	private String projectName;
	private Date deadLine;
	private String description;
	private boolean mark;

	/**
	 * @return the idProject
	 */
	public long getIdProject() {
		return idProject;
	}
	/**
	 * @param idProject the idProject to set
	 */
	public void setIdProject(long idProject) {
		this.idProject = idProject;
	}
	/**
	 * @return the Professor
	 */
	public Professor getProfessor() {
		return professor;
	}
	/**
	 * @param Professor the Professor to set
	 */
	public void setProfessor(Professor professor) {
		this.professor = professor;
	}
	/**
	 * @return the projectName
	 */
	public String getProjectName() {
		return projectName;
	}
	/**
	 * @param projectName the projectName to set
	 */
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	/**
	 * @return the deadLine
	 */
	public Date getDeadLine() {
		return deadLine;
	}
	/**
	 * @param deadLine the deadLine to set
	 */
	public void setDeadLine(Date deadLine) {
		this.deadLine = deadLine;
	}
	/**
	 * @return the mark
	 */
	public boolean isMark() {
		return mark;
	}
	/**
	 * @param mark the mark to set
	 */
	public void setMark(boolean mark) {
		this.mark = mark;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
}
