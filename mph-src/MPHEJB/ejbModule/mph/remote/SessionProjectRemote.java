package mph.remote;

import java.util.Date;
import java.util.List;

import javax.ejb.Remote;

import mph.entity.Deliverable;
import mph.entity.File;
import mph.entity.Group;
import mph.entity.Project;

@Remote
public interface SessionProjectRemote {
	
	public Project getProject(long idProj);
	public List<Deliverable> getDeliverable(long idProj);
	public List<Project> getProjectList(long idProf);
	public List<Project> getProjectList();
	public void removeProject(long idProj);
	public Project createProject(long usernameProf, String nameProj, Date deadlineProj, String description);
	public void createDeliverable(String nameDel, Date deadDel, long idProj);
	public List<Group> getProjectGroups(long idProj);
	public List<Group> getProfessorGroups(long idProf);
	public List<File> getProjectFiles(long idProj);
	long getGroupProject(long idGroup);
	public List<File> getGroupFile(long idGroup);
	Deliverable getDeliv(long idDel);
	List<File> getFileDel(long idDel);
}
