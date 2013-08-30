package mph.session;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.ejb.Stateless;
import javax.naming.InitialContext;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.UserTransaction;

import mph.entity.Deliverable;
import mph.entity.File;
import mph.entity.Group;
import mph.entity.Invite;
import mph.entity.Mark;
import mph.entity.Professor;
import mph.entity.Project;
import mph.entity.Student;
import mph.remote.SessionGroupRemote;
import mph.remote.SessionProjectRemote;

import org.hibernate.Transaction;
import org.jboss.ejb3.annotation.RemoteBinding;

@Stateless
@RemoteBinding(jndiBinding = "SessionGroupRemote")
public class SessionGroup implements SessionGroupRemote {

	private static final long serialVersionUID = 1L;

	@PersistenceContext(unitName = "mph")
	EntityManager em;

	@Override
	public Group getGroup(long idGroup) {
		try {
			Group g = em.find(Group.class, idGroup);
			return g;
		} catch (Exception e) {
			return null;
		}
	}
	@Override
	public List<Group> getAllGroup()
	{
		try{
			Query m = em.createQuery("FROM Group");
			return  m.getResultList();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public Group getGroupByName(String groupname) {
		try {
			Group g = em.find(Group.class, groupname);
			return g;
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public List<Student> getStudentOfGroup(long idGroup) {
		try {
			Group g = em.find(Group.class, idGroup);
			Query q = em
					.createQuery("SELECT s from Group g JOIN g.students  s where g.idGroup=?1 ");
			q.setParameter(1, idGroup);
			List<Student> listStudent = q.getResultList();
			// if(q.getResultList() == null) throw new Exception();
			return q.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public boolean createVisibility(long idGroup1, long idGroup2)
	{
		if (idGroup1 == idGroup2) return false;
		else{
			Group g = em.find(Group.class, idGroup1);
			Group g1 = em.find(Group.class, idGroup2);
			List<Group> gr = g.getGroups();
			gr.add(g1);
			g.setGroups(gr);
			em.persist(g);
			return true;
		}
	}
	
	@Override
	public List<Group> getGroupVisibleBy(long idGroup)
	{
		Group g = em.find(Group.class, idGroup);
		Query q = em.createQuery("SELECT gr FROM Group g JOIN g.groups gr where g.idGroup=?1");
		q.setParameter(1, idGroup);
		return q.getResultList();
	}
	
	@Override
	public Group createGroup(String nameGroup, ArrayList<String> studSub,
			long idProj) {
		Group g = new Group();
		try {
			long idProf;
			g.setGroupName(nameGroup);
			g.setProject(em.find(Project.class, idProj));
			Project p = em.find(Project.class, idProj);
			g.setProfessor(em.find(Professor.class, p.getProfessor()
					.getIdProfessor()));
			Query q = null;
			q = em.createQuery("FROM Student as s where s=?1");
			q.setParameter(1,
					em.find(Student.class, Long.parseLong(studSub.get(0))));
			List<Student> listud = q.getResultList();
			listud.get(0).getGroup().add(g);
			em.refresh(listud.get(0));
			g.setStudents(listud);
			g.setGroupMark(-1);
			em.persist(g);
			g = em.find(Group.class, g.getIdGroup());
			if (studSub.size() == 2) {
				Student s2 = em.find(Student.class,
						Long.parseLong(studSub.get(1)));
				Invite i = new Invite();
				i.setIdGroup(g.getIdGroup());
				i.setIdStud(s2.getIdStudent());
				em.persist(i);
			} else if (studSub.size() == 3) {
				Student s2 = em.find(Student.class,
						Long.parseLong(studSub.get(1)));
				Student s3 = em.find(Student.class,
						Long.parseLong(studSub.get(2)));
				Invite i3 = new Invite();
				Invite i2 = new Invite();
				i3.setIdGroup(g.getIdGroup());
				i3.setIdStud(s3.getIdStudent());
				i2.setIdGroup(g.getIdGroup());
				i2.setIdStud(s2.getIdStudent());
				em.persist(i2);
				em.persist(i3);
			}
			return g;
		} catch (Exception e) {

			e.printStackTrace();
		}
		return g;
	}

	@Override
	public void removeGroup(long idGroup) {
		Query q = em.createQuery("delete from Group as g where g=?1");
		q.setParameter(1, idGroup);
		q.executeUpdate();
		q = em.createQuery("delete from Invite as i where i.idGroup=?1");
		q.setParameter(1, idGroup);
	}

	@Override
	public void assignVote(long idGroup) {
		Group g = em.find(Group.class, idGroup);
		Query q = em
				.createQuery("SELECT mark from Mark as m  where m.group=?1");
		q.setParameter(1, g);
		List<Integer> list = (List<Integer>) q.getResultList();
		Integer x = 0;
		for (Integer i : list) {
			x = x + i;
		}
		g.setGroupMark(x / list.size());
		em.merge(g);
	}

	@Override
	public void assignIntermediateVote(long idGroup, long idDel, int vote) {
		try{
			Group gr = em.find(Group.class, idGroup);
			Deliverable del = em.find(Deliverable.class, idDel);
			Mark m = this.getMark(gr, del);
			if(m != null){
				m.setMark(vote);
			em.merge(m);
			} else{
				Mark ma = new Mark();
				ma.setDeliverable(del);
				ma.setGroup(gr);
				ma.setMark(vote);
				em.persist(ma);
			}
		} catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public Mark getMark(Group gr, Deliverable del){
		try{
			Query q = em
					.createQuery("from Mark as m where m.group=?1 and m.deliverable=?2");
			q.setParameter(1, gr);
			q.setParameter(2, del);
			Mark m = (Mark) q.getSingleResult();
			return m;
		} catch(Exception e){
			return null;
		}
	}

	@Override
	public List<Mark> getGroupMark(long idGroup){
		Group g = em.find(Group.class, idGroup);
		Query q = em.createQuery("from Mark as m where m.group=?1");
		q.setParameter(1, g);
		List<Mark> lm = q.getResultList();
		return lm;
	}
	
	@Override
	public void enableViewGroup(String grouptarget) {

	}

	@Override
	public void accept(long idGroup, long idStud) {
		Query q = em
				.createQuery("FROM Invite as i where i.idGroup=?1 and i.idStud=?2");
		q.setParameter(1, idGroup);
		q.setParameter(2, idStud);
		Invite i = (Invite) q.getSingleResult();

		q = em.createQuery("delete FROM Invite as i where i=?1");
		q.setParameter(1, em.find(Invite.class, i.getIdInvite()));
		int x = q.executeUpdate();

		Group g = em.find(Group.class, idGroup);
		Student s = em.find(Student.class, idStud);
		List<Student> list = g.getStudents();
		list.add(s);
		em.persist(g);
	}

	public List<Group> getStudentGroup(long idStud) {
		try {
			// Query q =
			// em.createQuery("SELECT GROUPON from (STUDENT as s JOIN STUDENT_GROUPS as g) JOIN GROUPON as gr where s.STUDENT_ID = g.STUDENT_ID AND g.GROUP_ID =gr.GROUPON_ID AND s.STUDENT_ID=?1 ");
			Query q = em
					.createQuery("SELECT g from Group g JOIN g.students  s where s.idStudent=?1 ");
			q.setParameter(1, idStud);
			// List<Student> listStudent = q.getResultList();
			if (q.getResultList() == null)
				throw new Exception();
			return q.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public List<Student> getStudentInGroup(long idGroup) {
		try {
			Query q = em.createQuery("FROM Student as s where s.idStudent IN "
					+ "(FROM Group g where g.idGroup=?1)");
			q.setParameter(1, idGroup);
			List<Student> list = q.getResultList();
			return list;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public boolean control(ArrayList<String> studSub, long idProj) {

		Project p = em.find(Project.class, idProj);
		for(int i = 0; i < studSub.size(); i++){
			Student s = em.find(Student.class, Long.parseLong(studSub.get(i)));
			try{
			Query q = em.createQuery("FROM Student as s where s.idStudent=?1 and s.idStudent IN "
					+ "(FROM Group g where g.project=?2)");
			q.setParameter(1, s.getIdStudent());
			q.setParameter(2, p);
			Student stud = (Student) q.getSingleResult();
			if (stud != null) return false;
			} catch (NoResultException e){}
			
			try{
			Query q2 = em.createQuery("from Group as g where g.project=?1 and g.idGroup in "
					+ "(from Invite i where i.idStud=?2)");
			q2.setParameter(1, p);
			q2.setParameter(2, Long.parseLong(studSub.get(i)));
			Group g = (Group) q2.getSingleResult();
			if(g != null) return false;
			} catch(NoResultException en){}
		} return true;
	}
}