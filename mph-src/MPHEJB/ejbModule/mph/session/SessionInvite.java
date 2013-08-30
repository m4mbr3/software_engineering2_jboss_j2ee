package mph.session;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.jboss.ejb3.annotation.RemoteBinding;

import mph.entity.Group;
import mph.entity.Invite;
import mph.entity.Student;
import mph.remote.SessionInviteRemote;

/**
 * Session Bean implementation class SessionInvite
 */
@Stateless
@RemoteBinding(jndiBinding = "SessionInviteRemote")
public class SessionInvite implements SessionInviteRemote {

	/**
	 * Default constructor.
	 */
	@PersistenceContext(unitName = "mph")
	EntityManager em;

	public SessionInvite() {

	}

	@Override
	public List<Group> getGroupInvite(long idStud) {
		try {
			Query q = em.createQuery("FROM Group g where g.idGroup IN "
					+ "(FROM Invite i where i.idStud=?1)");
			q.setParameter(1, idStud);
			return q.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public boolean sendInvite(long idStud, long idGroup) {
		Query q1 = em
				.createQuery("SELECT s FROM Group g JOIN g.students s WHERE g.idGroup =?1");
		q1.setParameter(1, idGroup);
		Query q2 = em.createQuery("FROM INVITE i WHERE i.idGroup =?1");
		q2.setParameter(1, idGroup);
		List<Student> studList = q1.getResultList();
		List<Invite> inviteList = q2.getResultList();
		if (((studList.size() + inviteList.size()) < 3)) {

			Invite i = new Invite();
			i.setIdGroup(idGroup);
			i.setIdStud(idStud);
			em.persist(i);
			return true;

		} else
			return false;
	}
}
