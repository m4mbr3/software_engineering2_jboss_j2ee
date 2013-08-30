package mph.session;

import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.persistence.Query;

import mph.entity.Deliverable;
import mph.entity.File;
import mph.entity.Group;
import mph.entity.Professor;
import mph.entity.Project;
import mph.remote.SessionProjectRemote;

import org.jboss.ejb3.annotation.RemoteBinding;

@Stateless
@RemoteBinding(jndiBinding = "SessionProjectRemote")
public class SessionProject implements SessionProjectRemote {

	private static final long serialVersionUID = 1L;

	@PersistenceContext(unitName = "mph")
	EntityManager em;

	@Override
	public Project getProject(long idProj) {
		try {
			Project p = em.find(Project.class, idProj);
			return p;
		} catch (NoResultException nr) {
			return null;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	@Override
	public List<File> getFileDel(long idDel)
	{
		Query q = em.createQuery("FROM File f where f.deliverable=?1 ");
		q.setParameter(1, idDel);
		return q.getResultList();
	}
	@Override
	public Deliverable getDeliv(long idDel)
	{
		Deliverable del = em.find(Deliverable.class,idDel);
		return del;
	}

	@Override
	public List<Deliverable> getDeliverable(long idProj) {
		try {
			Query q = em
					.createQuery("FROM Deliverable as d where d.project=?1");
			q.setParameter(1, em.find(Project.class, idProj));
			List<Deliverable> listdel = (List<Deliverable>) q.getResultList();
			return listdel;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<Project> getProjectList(long idProf) {
		try {
			Query q = em.createQuery("FROM Project where PROFESSOR_ID=?1");
			q.setParameter(1, idProf);
			List<Project> listProj = (List<Project>) q.getResultList();
			return listProj;
		} catch (NoResultException nr) {
			return null;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<Project> getProjectList() {
		try {
			Query q = em.createQuery("FROM Project as p");
			List<Project> listProj = (List<Project>) q.getResultList();
			return listProj;
		} catch (Exception e) {

		}
		return null;
	}

	@Override
	public void removeProject(long idProj) {
		Query q = em.createQuery("from Group as g where g.project=?1");
		q.setParameter(1, em.find(Project.class, idProj));
		List<Group> lg = q.getResultList();
		if (lg.size() == 0) {
			q = em.createQuery("delete FROM Project as p where p=?1");
			q.setParameter(1, em.find(Project.class, idProj));
			int x = q.executeUpdate();
		}
	}

	@Override
	public Project createProject(long useridProf, String idProj,
			Date deadlineProj, String description) {
		try {
			Project p = new Project();
			p.setProfessor(em.find(Professor.class, useridProf));
			p.setProjectName(idProj);
			p.setDeadLine(deadlineProj);
			p.setDescription(description);
			em.persist(p);
			return p;
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public void createDeliverable(String nameDel, Date deadDel, long idProj) {
		try {
			Deliverable d = new Deliverable();
			d.setProject(em.find(Project.class, idProj));
			d.setDeliverableName(nameDel);
			d.setDeadLine(deadDel);
			em.persist(d);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public List<Group> getProjectGroups(long idProj) {
		try {
			Query q = em.createQuery("FROM Group as g where g.project=?1");
			q.setParameter(1, em.find(Project.class, idProj));
			List<Group> listgroup = (List<Group>) q.getResultList();
			if (listgroup == null)
				System.out.println("");
			return listgroup;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<Group> getProfessorGroups(long idProf) {
		try {
			Query q = em.createQuery("FROM Group as g where g.project IN "
					+ "(FROM Project p where p.professor=?1)");
			q.setParameter(1, em.find(Professor.class, idProf));
			List<Group> listgroup = (List<Group>) q.getResultList();
			return listgroup;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<File> getProjectFiles(long idProj) {
		try {
			Query q = em.createQuery("FROM File f where f.group IN "
					+ "(FROM Group g where g.project=?1)");
			q.setParameter(1, em.find(Project.class, idProj));
			List<File> listFile = (List<File>) q.getResultList();
			return listFile;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public long getGroupProject(long idGroup) {
		try {
			Query q = em
					.createQuery("SELECT p FROM Group g JOIN g.project p WHERE g.idGroup=?1 ");
			q.setParameter(1, idGroup);
			return ((Project) q.getSingleResult()).getIdProject();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 1;
	}

	public List<File> getGroupFile(long idGroup) {
		Query q = em.createQuery("from File as f where f.group=?1");
		q.setParameter(1, em.find(Group.class, idGroup));
		List<File> l = q.getResultList();
		return l;
	}
}
