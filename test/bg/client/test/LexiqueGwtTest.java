package bg.client.test;


import org.junit.Test;

import bg.client.inter.sigale.model.ISigalePropertes;
import bg.client.inter.sigale.model.Lexique;
import bg.client.inter.sigale.model.LexiqueFactory;
import bg.client.inter.sigale.model.statistic.StatistiquesLexique;
import bg.client.inter.sigale.model.statistic.StatistiquesLexiqueFactory;
import bg.client.inter.sigale.util.ILogListener;

import com.google.gwt.core.shared.GWT;
import com.google.gwt.junit.client.GWTTestCase;



/**
 * Pour lancer les tests JUnit en "Production Mode"
 * Essayé en simple java, ne marche pas: Trop de dépendances gwt (outre GWTTestCase ).
 * 
 * @author utilisateur
 *
 */
public class LexiqueGwtTest extends GWTTestCase {
	ISigalePropertes sigaleProperties = new ISigalePropertes() {
		
		@Override
		public void setNameLexique(String nameLexique) {
			
		}
		
		@Override
		public String getNameLexique() {
			
			return null;
		}
	};
	ILogListener logListener = new ILogListener() {
		
		@Override
		public void logTitle(String s) {
			System.out.println(s);
		}
		
		@Override
		public void logText(String message) {
			System.out.println(message);
			
		}
		
		@Override
		public void log(String message, Throwable t) {
			System.out.println(message);
			t.printStackTrace();
		}
		
		@Override
		public void log(String message) {
			System.out.println(message);
		}
	};
	
	
	@Test
	public void test_0() {
		
		GWT.log("TEst de Junit");
		GWTTestCase.assertTrue(true);
	}
		
	@Test
   public void test_1() {
		
		LexiqueFactory lexiqueFactory = new LexiqueFactory(sigaleProperties,logListener);
		Lexique lexique_1 = lexiqueFactory.getLexique();
		String xml_1 = lexiqueFactory.toXml(lexique_1);
		System.out.println("lexique xml : "+xml_1);
		Lexique lexique_2 = lexiqueFactory.parse(xml_1);
		GWTTestCase.assertTrue(lexique_1.equals(lexique_2));
		
	}
	
	@Test
	public void test_2() {
		StatistiquesLexique stat  = StatistiquesLexiqueFactory.getInstance().createNewStatistique();
		String xml = StatistiquesLexiqueFactory.getInstance().toXml(stat);
		System.out.println("Statis xml : ");
		StatistiquesLexique stat_2  = StatistiquesLexiqueFactory.getInstance().parse(xml);
		GWTTestCase.assertNotNull(stat_2);
	}

	

	@Override
	public String getModuleName() {
		return "bg.sigale";
	}

}
