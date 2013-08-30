package mph.test;

import static org.junit.Assert.*;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Properties;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.rmi.PortableRemoteObject;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import mph.entity.Group;
import mph.entity.Professor;
import mph.entity.Project;
import mph.remote.SessionGroupRemote;
import mph.remote.SessionPersonRemote;
import mph.remote.SessionProjectRemote;
import mph.remote.SessionRegistrationRemote;
import mph.remote.UtilDeleteDBRemote;


public class TestGroup extends TestCase{
	
	public SessionRegistrationRemote sr = null;
	public SessionProjectRemote sp = null;
	public UtilDeleteDBRemote ud = null;
	public SessionPersonRemote per = null;
	public SessionGroupRemote sg = null;
    InitialContext ctx=null;
    private String server="localhost:1099";    
    private String principal="E001";
    private String credentials="Avi";

    public TestGroup(String name)
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
		Object ref5 = ctx.lookup("SessionGroupRemote");
		sg = (SessionGroupRemote) PortableRemoteObject.narrow(ref5, SessionGroupRemote.class);
    }
    
    protected void tearDown() throws Exception {
        super.tearDown();
    }
    
    public static Test suite()
    {   
    	return new TestSuite(TestProject.class);
    }
    
    public void testcreateGroup(){
    	
    	ud.deleteAll();
    	
    	int x = sr.professorRegistration("Andrea", "Gandini", "mirty", "mirtillo89", true);
    	Professor p = per.loginProfessor("mirty", "mirtillo89");
    	Date deadlineProject = null;
		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
		try{
		deadlineProject = format.parse("12/12/2012");
		}catch(Exception e){}
    	Project pr = sp.createProject(p.getIdProfessor(), "project name", deadlineProject, "project description");
    	
    	int y = sr.studentRegistration("Andrea", "Mambretti", "mabro", "mambro007");
    	ArrayList<String> ls = new ArrayList<String>();
    	ls.add("1");
    	Group g = sg.createGroup("cippo", ls, pr.getIdProject());
    	assertEquals(g.getProject().getIdProject(), pr.getIdProject());
    }
}
