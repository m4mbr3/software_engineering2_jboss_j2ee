package mph.remote;
import javax.ejb.Remote;

@Remote
public interface SessionRegistrationRemote {
		
		public int studentRegistration(String firstName, String lastName, String username, String password);
		public int professorRegistration(String firstName, String lastName, String username, String password, boolean professor);
}
