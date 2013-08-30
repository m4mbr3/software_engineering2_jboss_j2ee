/**
 * 
 */
package mph.entity;



import java.util.Date;

import javax.persistence.*;

/**
 * @author Andrea
 *
 */
@Entity
@Table(name="FILE")
public class File implements java.io.Serializable {
	
	@Id
	@GeneratedValue
	private long idFile;
	
	private Date date;
	long late;
	private String fileName;
	private String extension;
	@Column(name="cod", length=100000000)
	private byte[] cod;
	
	@ManyToOne//(fetch=FetchType.LAZY)
	@JoinColumn(name="GROUPON_ID")
	private Group group;
	
	@ManyToOne//(fetch=FetchType.LAZY)
	@JoinColumn(name="DELIVERABLE_ID")
	private Deliverable deliverable;
	/**
	 * @return the idFile
	 */
	public long getIdFile() {
		return idFile;
	}
	/**
	 * @param idFile the idFile to set
	 */
	public void setIdFile(long idFile) {
		this.idFile = idFile;
	}
	/**
	 * @return the fileName
	 */
	public String getFileName() {
		return fileName;
	}
	/**
	 * @param fileName the fileName to set
	 */
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	/**
	 * @return the extension
	 */
	public String getExtension() {
		return extension;
	}
	/**
	 * @param extension the extension to set
	 */
	public void setExtension(String extension) {
		this.extension = extension;
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
	public byte[] getCod() {
		return cod;
	}
	public void setCod(byte[] cod) {
		this.cod = cod;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public long isLate() {
		return late;
	}
	public void setLate(long late) {
		this.late = late;
	}	
}
