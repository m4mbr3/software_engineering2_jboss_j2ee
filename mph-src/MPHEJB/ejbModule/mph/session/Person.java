package mph.session;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.persistence.Query;
import org.jboss.ejb3.annotation.RemoteBinding;

import mph.entity.Professor;
import mph.entity.Student;
import mph.remote.SessionPersonRemote;

/**
 * Session Bean implementation class Person
 */
@Stateless
@RemoteBinding(jndiBinding = "SessionPersonRemote")
public class Person implements SessionPersonRemote {

	/**
	 * Default constructor.
	 */
	@PersistenceContext(unitName = "mph")
	EntityManager em;

	public Person() {
		// TODO Auto-generated constructor stub

	}

	@Override
	public void register() {
		// TODO Auto-generated method stub

	}

	@Override
	public List<Student> getAllStudents() {
		try {
			Query q = em.createQuery("FROM Student ");
			return (List<Student>) q.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<Student> getAllStudents(long idStud) {
		try {
			Query q = em.createQuery("FROM Student s WHERE s.idStudent <>?1");
			q.setParameter(1, idStud);
			return q.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public Student loginStudent(String username, String password) {
		// TODO Auto-generated method stub
		/*
		 * if(username.matches(
		 * "((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%]).{6,20})") &&
		 * password.matches
		 * ("((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%]).{6,20})")) {
		 */
		Query q = em.createQuery("FROM Student s WHERE s.username='" + username
				+ "' AND s.password='" + password + "'");
		try {
			Student s = (Student) q.getSingleResult();
			return s;
		} catch (NoResultException e) {
			return null;
		} catch (NonUniqueResultException e) {
			List<Student> s = q.getResultList();
			return s.get(0);
		}

		/*
		 * } else {
		 * System.out.println("Username and Password not correct user"); return
		 * false; }
		 */
	}

	@Override
	public Professor loginProfessor(String username, String password) {
		// TODO Auto-generated method stub
		/*
		 * if(username.matches(
		 * "((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%]).{6,20})") &&
		 * password.matches
		 * ("((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%]).{6,20})")) {
		 */

		Query q = em.createQuery("FROM Professor s WHERE s.username='"
				+ username + "' AND s.password='" + password + "'");
		try {
			Professor p = (Professor) q.getSingleResult();
			return p;
		} catch (NoResultException e) {
			return null;
		} catch (NonUniqueResultException e) {
			List<Professor> p = q.getResultList();
			return p.get(0);
		}

		/*
		 * } else { System.out.println("Username and Password not correct");
		 * return false; }
		 */
	}

	@Override
	public void logout() {
		// TODO Auto-generated method stub

	}

	@Override
	public void enableProf() {
		// TODO Auto-generated method stub

	}

	@Override
	public void getPerson(String name) {
		// TODO Auto-generated method stub

	}

}
