package mph.test;

import static org.junit.Assert.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.rmi.PortableRemoteObject;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import mph.entity.Deliverable;
import mph.entity.Professor;
import mph.entity.Project;
import mph.remote.SessionPersonRemote;
import mph.remote.SessionProjectRemote;
import mph.remote.SessionRegistrationRemote;
import mph.remote.UtilDeleteDBRemote;

public class TestProject extends TestCase {

	public SessionRegistrationRemote sr = null;
	public SessionProjectRemote sp = null;
	public UtilDeleteDBRemote ud = null;
	public SessionPersonRemote per = null;
    InitialContext ctx=null;
    private String server="localhost:1099";    
    private String principal="E001";
    private String credentials="Avi";

    public TestProject(String name)
    {    
    	super(name);
    }
    public static void main(String args[])
    {
    	//TestRunner will run the tests found in the suite
    	junit.textui.TestRunner.run(suite());
    }
    
    protected void setUp() throws Exception
    {
        super.setUp();
        Properties props = new Properties();
        props.put(Context.INITIAL_CONTEXT_FACTORY, "org.jnp.interfaces.NamingContextFactory");
        props.put(Context.SECURITY_AUTHENTICATION, "simple");
        props.put(Context.SECURITY_PRINCIPAL, principal);
        props.put(Context.SECURITY_CREDENTIALS, credentials);
        props.put(Context.PROVIDER_URL, server);
        props.put("java.naming.factory.url.pkgs", "org.jnp.interfaces");
        ctx = new InitialContext(props);
        Object ref1 = ctx.lookup("SessionRegistrationRemote");
		sr = (SessionRegistrationRemote) PortableRemoteObject.narrow(ref1, SessionRegistrationRemote.class);
		Object ref3 = ctx.lookup("SessionProjectRemote");
		sp = (SessionProjectRemote) PortableRemoteObject.narrow(ref3, SessionProjectRemote.class);
		Object ref2 = ctx.lookup("UtilDeleteDBRemote");
		ud = (UtilDeleteDBRemote) PortableRemoteObject.narrow(ref2, UtilDeleteDBRemote.class);
		Object ref4 = ctx.lookup("SessionPersonRemote");
		per = (SessionPersonRemote) PortableRemoteObject.narrow(ref4, SessionPersonRemote.class);
    }
    
    protected void tearDown() throws Exception {
        super.tearDown();
    }
    
    public static Test suite()
    {   
    	return new TestSuite(TestProject.class);
    }    
    
    public void testcreateProject(){
    	
    	ud.deleteAll();
    	
    	int x = sr.professorRegistration("Andrea", "Gandini", "mirty", "mirtillo89", true);
    	Professor p = per.loginProfessor("mirty", "mirtillo89");
    	Date deadlineProject = null;
		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
		try{
		deadlineProject = format.parse("12/12/2012");
		}catch(Exception e){}
    	Project pr = sp.createProject(p.getIdProfessor(), "project name", deadlineProject, "project description");
    	assertEquals(pr.getDescription(), "project description");
    	assertEquals(pr.getProfessor().getIdProfessor(), p.getIdProfessor());
    }
    
    public void testcreateDel(){
    	
    	ud.deleteAll();
    	
    	int x = sr.professorRegistration("Andrea", "Gandini", "mirty", "mirtillo89", true);
    	Professor p = per.loginProfessor("mirty", "mirtillo89");
    	Date deadlineProject = null;
		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
		try{
		deadlineProject = format.parse("12/12/2012");
		}catch(Exception e){}
    	Project pr = sp.createProject(p.getIdProfessor(), "project name", deadlineProject, "project description");
    	sp.createDeliverable("name del", deadlineProject, pr.getIdProject());
    	List<Deliverable> ld = sp.getDeliverable(pr.getIdProject());
    	for(Deliverable d : ld){
    		assertEquals(d.getProject().getIdProject(), pr.getIdProject());
    	}
    }
}