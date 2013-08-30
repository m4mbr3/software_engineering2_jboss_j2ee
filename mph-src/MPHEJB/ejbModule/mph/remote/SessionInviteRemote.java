package mph.remote;
import java.util.List;

import javax.ejb.Remote;

import mph.entity.Group;

@Remote
public interface SessionInviteRemote {
	public List<Group> getGroupInvite(long idStud);

	boolean sendInvite(long idStud, long idGroup);
}

