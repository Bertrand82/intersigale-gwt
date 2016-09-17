package bg.server.test;

import static org.junit.Assert.*;

import org.junit.Test;

import bg.client.inter.sigal.beans.UserBean;
import bg.client.ui.login.LoginService;

public class TestParseUserBean {

	public TestParseUserBean() {
		// TODO Auto-generated constructor stub
	}
	
	
	@Test
	public void test_1() {
		UserBean userBean = new UserBean("toto", "email", "blabla");
		String s = ""+userBean;
		System.out.println(" s: "+s);
		UserBean userBean2 = LoginService.parseUser(s);
		System.out.println(s);
		System.out.println(""+userBean2);
		assertEquals(s, ""+userBean2);;
		
	}
}
