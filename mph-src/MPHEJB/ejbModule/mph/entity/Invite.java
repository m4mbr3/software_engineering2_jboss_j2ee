package mph.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="INVITE")
public class Invite implements java.io.Serializable {
	
	@Id
	@GeneratedValue
	@Column(name="INVITE_ID")
	private long idInvite;
	
	@Column(name="GROUP_ID")
	private long idGroup;
	
	@Column(name="STUDENT_ID")
	private long idStud;

	public long getIdInvite() {
		return idInvite;
	}

	public void setIdInvite(long idInvite) {
		this.idInvite = idInvite;
	}

	public long getIdGroup() {
		return idGroup;
	}

	public void setIdGroup(long idGroup) {
		this.idGroup = idGroup;
	}

	public long getIdStud() {
		return idStud;
	}

	public void setIdStud(long idStud) {
		this.idStud = idStud;
	}
}
