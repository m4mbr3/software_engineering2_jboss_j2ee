package mph.remote;

import javax.ejb.Remote;

@Remote
public interface SessionUpDownRemote {

	public void upload(String name, byte[] file, String type, long idGroup, long idDel);
	public byte[] downloadSingleFile(long idFile);
	public byte[] downloadListFile(long id);
	public String getExtension(long idFile);
	public void removeFile(long idFile);
}
