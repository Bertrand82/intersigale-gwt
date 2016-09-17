package bg.server.test;

import static org.junit.Assert.*;

import org.junit.Assert;
import org.junit.Test;

import bg.server.util.UtilCopyBean;

public class TestGenerique {

	@Test
	public void test_0() {
		assertTrue(true);
	}
	@Test
	public void test_1() throws Exception{
		BeanA beanA = new BeanA("a", "b", "c", "d", true, 5);
		Object o =UtilCopyBean.copyTo( BeanA.class,beanA);
		assertEquals(beanA, o);
	}
	

}


