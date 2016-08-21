package bg.client.test;

import org.junit.Test;

import bg.client.inter.sigale.model.Lexique;
import bg.client.inter.sigale.model.LexiqueFactory;

import com.google.gwt.junit.client.GWTTestCase;

public class LexiqueGwtTest extends GWTTestCase {

	@Test
	public void test() {
		Lexique lexique = LexiqueFactory.getInstance().getLexique();
		String xml = LexiqueFactory.getInstance().toXml(lexique);
		System.out.println("xml "+xml);
		
	}

	@Override
	public String getModuleName() {
		return "bg.sigale";
	}

}
