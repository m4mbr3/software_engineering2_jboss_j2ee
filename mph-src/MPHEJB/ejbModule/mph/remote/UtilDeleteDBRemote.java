package mph.remote;

import javax.ejb.Remote;

@Remote
public interface UtilDeleteDBRemote {

	public void deleteAll();
}
