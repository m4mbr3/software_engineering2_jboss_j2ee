/**
 * 
 */
package mph.entity;

import javax.persistence.*;

/**
 * @author Andrea
 *
 */
@Entity
@Table(name="MARK")
public class Mark {
	
	public Mark()
	{
		
	}
	
	@Id
	@GeneratedValue
	@Column(name="MARK_ID")
	private long idMark;
	
	private int mark;
	
	@OneToOne
	@JoinColumn(name="GROUP_ID")
	private Group group;
	
	@OneToOne
	@JoinColumn(name="DELIVERABLE_ID")
	private Deliverable deliverable;

	public int getMark() {
		return mark;
	}

	public void setMark(int mark) {
		this.mark = mark;
	}
	public Group getGroup() {
		return group;
	}

	public void setGroup(Group group) {
		this.group = group;
	}

	public Deliverable getDeliverable() {
		return deliverable;
	}

	public void setDeliverable(Deliverable deliverable) {
		this.deliverable = deliverable;
	}
}
