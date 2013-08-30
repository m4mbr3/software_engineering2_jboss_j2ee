package mph.remote;

import java.util.List;

import javax.ejb.Remote;

import mph.entity.Professor;
import mph.entity.Student;

@Remote
public interface SessionPersonRemote {

	public void register();
	public Student loginStudent(String username, String password);
	public Professor loginProfessor(String username, String password);
	public void logout();
	public void enableProf();
	public void getPerson(String name);
	public List<Student> getAllStudents();
	public List<Student> getAllStudents(long idStud);
} 
