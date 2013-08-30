package mph.session;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import mph.entity.Deliverable;
import mph.entity.File;
import mph.entity.Group;
import mph.remote.SessionGroupRemote;
import mph.remote.SessionUpDownRemote;

import org.jboss.ejb3.annotation.RemoteBinding;

@Stateless
@RemoteBinding(jndiBinding = "SessionUpDownRemote")
public class SessionUpDown implements SessionUpDownRemote {

	private static final long serialVersionUID = 1L;

	@PersistenceContext(unitName = "mph")
	EntityManager em;

	@Override
	public void upload(String name, byte[] file, String type, long idGroup,
			long idDel) {
		File f = new File();
		f.setFileName(name);
		f.setCod(file);

		f.setExtension(type);
		Group g = em.find(Group.class, idGroup);
		Deliverable d = em.find(Deliverable.class, idDel);
		f.setGroup(g);
		f.setDeliverable(d);
		Calendar cal = new GregorianCalendar();
		Date today = cal.getTime();
		Date dd = d.getDeadLine();
		Calendar c1 = Calendar.getInstance();
		Calendar c2 = Calendar.getInstance();
		c1.setTime(today);
		c2.setTime(dd);

		long giorni = (c1.getTime().getTime() - c2.getTime().getTime())
				/ (24 * 3600 * 1000);
		if (giorni > 0) {
			f.setLate(giorni);
		} else
			f.setLate(0);

		em.persist(f);
	}

	@Override
	public byte[] downloadSingleFile(long idFile) {
		File f = em.find(File.class, idFile);
		return f.getCod();
	}

	@Override
	public byte[] downloadListFile(long id) {

		return null;
	}

	@Override
	public String getExtension(long idFile) {
		File f = em.find(File.class, idFile);
		return f.getExtension();
	}

	public void removeFile(long idFile) {
		Query q = em.createQuery("delete from File as f where f.idFile=?1");
		q.setParameter(1, idFile);
		q.executeUpdate();
	}
}
