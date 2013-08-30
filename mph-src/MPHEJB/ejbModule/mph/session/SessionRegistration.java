package mph.session;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.jboss.ejb3.annotation.RemoteBinding;

import mph.entity.Professor;
import mph.entity.Student;
import mph.remote.SessionRegistrationRemote;

/**
 * Session Bean implementation class SessionRegistration
 */
@Stateless
@RemoteBinding(jndiBinding = "SessionRegistrationRemote")
public class SessionRegistration implements SessionRegistrationRemote {

    /**
     * Default constructor. 
     */
	@PersistenceContext(unitName = "mph")
	EntityManager em;
    public SessionRegistration() {
        // TODO Auto-generated constructor stub
    }
    @Override
    public int studentRegistration(String firstName, String lastName, String username, String password)
    {
		try{
    		Student s = new Student();
    		s.setFirstName(firstName);
    		s.setLastName(lastName);
    		s.setPassword(password);
    		s.setUsername(username);
    		em.persist(s);
    		return 1;
    	}
    	catch(Exception e)
    	{
    		e.printStackTrace();
    	}
    	
    		
		return 0;
    }
    public int professorRegistration(String firstName, String lastName, String username, String password, boolean professor)
    {
    	try{
    		Professor p = new Professor();
    		p.setFirstName(firstName);
    		p.setLastName(lastName);
    		p.setPassword(password);
    		p.setUsername(username);
    		p.setProfessor(professor);
    		em.persist(p);
    		return 1;
    	}
    	catch(Exception e)
    	{
    		e.printStackTrace();
    	}
    	return 0;
    }
}
