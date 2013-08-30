package mph.entity;

import java.util.Date;
import java.util.Set;

import javax.persistence.*;

/**
 * @author Andrea
 *
 */

@Entity
@Table(name="DELIVERABLE")
public class Deliverable implements java.io.Serializable {
	
	@Id
	@GeneratedValue
	@Column(name="DELIVERABLE_ID")
	private long idDeliverable;
	
	private String deliverableName;
	
	private Date deadLine;
	private boolean intermediateMark;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="PROJECT_ID")
	private Project project;
	
	@OneToMany(mappedBy="deliverable")
	private Set<File> file;
	
	public long getIdDeliverable() {
		return idDeliverable;
	}
	public void setIdDeliverable(long idDeliverable) {
		this.idDeliverable = idDeliverable;
	}
	public String getDeliverableName() {
		return deliverableName;
	}
	public void setDeliverableName(String deliverableName) {
		this.deliverableName = deliverableName;
	}
	public Date getDeadLine() {
		return deadLine;
	}
	public void setDeadLine(Date deadLine) {
		this.deadLine = deadLine;
	}
	public boolean isIntermediateMark() {
		return intermediateMark;
	}
	public void setIntermediateMark(boolean intermediateMark) {
		this.intermediateMark = intermediateMark;
	}
	public Project getProject() {
		return project;
	}
	public void setProject(Project project) {
		this.project = project;
	}
}
