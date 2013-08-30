package mph.session;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.jboss.ejb3.annotation.RemoteBinding;

import mph.remote.UtilDeleteDBRemote;

@Stateless
@RemoteBinding(jndiBinding = "UtilDeleteDBRemote")
public class UtilDeleteDB implements UtilDeleteDBRemote {

	private static final long serialVersionUID = 1L;
	
	@PersistenceContext(unitName = "mph")
	private EntityManager em;
	
	public void deleteAll(){
		em.createQuery("delete from Deliverable").executeUpdate();
		em.createQuery("delete from File").executeUpdate();
		em.createQuery("delete from Group").executeUpdate();
		em.createQuery("delete from Invite").executeUpdate();
		em.createQuery("delete from Mark").executeUpdate();
		em.createQuery("delete from Professor").executeUpdate();
		em.createQuery("delete from Project").executeUpdate();
		em.createQuery("delete from Student").executeUpdate();
	}
}
