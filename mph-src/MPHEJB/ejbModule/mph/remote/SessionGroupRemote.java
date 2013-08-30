package mph.remote;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Remote;

import mph.entity.Group;
import mph.entity.Mark;
import mph.entity.Student;

@Remote
public interface SessionGroupRemote {

	public Group getGroupByName(String groupname);
	public Group getGroup(long idGroup);
	public Group createGroup(String nameGroup,ArrayList<String> studSub,long idProj);
	public void removeGroup(long idGroup);
	public void assignVote(long idGroup);
	public List<Student> getStudentOfGroup(long idGroup);
	public void assignIntermediateVote(long idGroup, long idDel, int vote);
	public void enableViewGroup(String grouptarget);
	public void accept(long idGroup, long idStud);
	public List<Group> getStudentGroup(long idStud);
	public List<Student> getStudentInGroup(long idGroup);
	public boolean control(ArrayList<String> studSub, long idProj);
	List<Group> getAllGroup();
	boolean createVisibility(long idGroup1, long idGroup2);
	List<Group> getGroupVisibleBy(long idGroup);
	public List<Mark> getGroupMark(long idGroup);
}
