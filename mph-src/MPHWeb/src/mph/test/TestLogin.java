package mph.test;

import static org.junit.Assert.*;

import java.util.Properties;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.rmi.PortableRemoteObject;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import mph.entity.Professor;
import mph.remote.SessionPersonRemote;
import mph.remote.SessionRegistrationRemote;
import mph.remote.UtilDeleteDBRemote;

public class TestLogin extends TestCase {

	public SessionRegistrationRemote sr = null;
	public UtilDeleteDBRemote ud = null;
	public SessionPersonRemote per = null;
    InitialContext ctx=null;
    private String server="localhost:1099";    
    private String principal="E001";
    private String credentials="Avi";

    public TestLogin(String name)
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
    	return new TestSuite(TestLogin.class);
    }    
    
    public void testcreateProfessor(){
    	ud.deleteAll();
    	
    	int x = sr.professorRegistration("Andrea", "Gandini", "mirty", "mirtillo89", false);
    	Professor p = per.loginProfessor("mirty", "mirtillo89");
		assertEquals(x, 1);
		assertEquals(p.getFirstName(), "Andrea");
		assertEquals(p.getLastName(), "Gandini");
		assertEquals(p.getUsername(), "mirty");
    }
    
    public void testcreateStudent(){
    	int x = sr.studentRegistration("Andrea", "Mambretti", "mabro", "mambro007");
		assertEquals(x, 1);
    }
}
