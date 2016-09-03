package bg.client.test;

import org.junit.Test;

import bg.client.inter.sigale.model.Lexique;
import bg.client.inter.sigale.model.LexiqueFactory;
import bg.client.inter.sigale.model.statistic.StatistiquesLexique;
import bg.client.inter.sigale.model.statistic.StatistiquesLexiqueFactory;

import com.google.gwt.junit.client.GWTTestCase;

public class LexiqueGwtTest extends GWTTestCase {

	@Test
	public void test_0_LexiqueFactory() {
		
		Lexique lexique = LexiqueFactory.getInstance().getLexique();
		String xml = LexiqueFactory.getInstance().toXml(lexique);
		System.out.println("lexique xml : "+xml);
		Lexique lexique2 = LexiqueFactory.getInstance().parse(xml);
		GWTTestCase.assertEquals(lexique.getName(), lexique2.getName());
		GWTTestCase.assertEquals(lexique.getListUniteLexicale().size(), lexique2.getListUniteLexicale().size());
		GWTTestCase.assertEquals(lexique, lexique2);
		
	}
	
	
	@Test
	public void test_1_StatistiqueFactory() {
		
		StatistiquesLexique stat = StatistiquesLexiqueFactory.getInstance().getStatistiquesLexique();
		GWTTestCase.assertNotNull(stat);
		String xml = StatistiquesLexiqueFactory.getInstance().toXML(stat);
		System.out.println("stat.xml : "+xml);
		StatistiquesLexique stat2 = StatistiquesLexiqueFactory.getInstance().parse(xml);
	}


	@Override
	public String getModuleName() {
		return "bg.sigale";
	}

}
