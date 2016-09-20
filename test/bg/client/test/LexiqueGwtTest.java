package bg.client.test;

import org.junit.Test;

import bg.client.inter.cicada.model.ICicadaPropertes;
import bg.client.inter.cicada.model.Lexique;
import bg.client.inter.cicada.model.LexiqueFactory;
import bg.client.inter.cicada.model.statistic.StatistiquesLexique;
import bg.client.inter.cicada.model.statistic.StatistiquesLexiqueFactory;
import bg.client.inter.cicada.model.statistic.StatistiquesUL;
import bg.client.inter.cicada.util.ILogListener;

import com.google.gwt.core.shared.GWT;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.junit.client.GWTTestCase;

/**
 * Pour lancer les tests JUnit en "Production Mode" Essayé en simple java, ne
 * marche pas: Trop de dépendances gwt (outre GWTTestCase ).
 * 
 * @author utilisateur
 *
 */
public class LexiqueGwtTest extends GWTTestCase {
	ICicadaPropertes cicadaProperties = new ICicadaPropertes() {

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

		GWT.log("Test de Junit");
		GWTTestCase.assertTrue(true);
	}

	@Test
	public void test_1() {
		LexiqueFactory lexiqueFactory = new LexiqueFactory(cicadaProperties, logListener);
		Lexique lexique_1 = lexiqueFactory.getLexique();
		String xml_1 = lexiqueFactory.toXml(lexique_1);
		System.out.println("lexique xml : " + xml_1);
		Lexique lexique_2 = lexiqueFactory.parse(xml_1);
		GWTTestCase.assertTrue(lexique_1.equals(lexique_2));

	}

	@Test
	public void test_2() {
		StatistiquesLexique stat = StatistiquesLexiqueFactory.getInstance().createNewStatistique();
		System.out.println("stat " + stat);
		String xml = StatistiquesLexiqueFactory.getInstance().toXml(stat);
		GWTTestCase.assertNotNull(xml);
		GWTTestCase.assertTrue(xml.length() > 0);
		System.out.println("Statistique xml_1 : " + xml);

		StatistiquesLexique stat_2 = StatistiquesLexiqueFactory.getInstance().parse(xml);
		String xml_2 = StatistiquesLexiqueFactory.getInstance().toXml(stat_2);
		System.out.println("Statistique xml_2 : " + xml_2);
		GWTTestCase.assertNotNull(stat_2);
		GWTTestCase.assertEquals(xml, xml_2);
		GWTTestCase.assertEquals(stat, stat_2);
	}

	@Test
	public void test_3() {
		StatistiquesUL ul = new StatistiquesUL();
		ul.setUniteLexicaleId("myId");
		ul.add(true);
		ul.add(false);
		ul.add(false);
		JSONObject json = StatistiquesLexiqueFactory.toJSon(ul);
		String jsonStr = json.toString();
		System.out.println("ul json : " + jsonStr);
		StatistiquesUL ul2 = StatistiquesLexiqueFactory.parseJSon_(jsonStr);

		JSONObject json2 = StatistiquesLexiqueFactory.toJSon(ul2);
		String jsonStr2 = json2.toString();
		System.out.println("jsonStr2 " + jsonStr2);
		GWTTestCase.assertEquals(jsonStr, jsonStr2);
	}

	@Override
	public String getModuleName() {
		return "bg.sigale";
	}

}
