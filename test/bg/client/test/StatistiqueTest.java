package bg.client.test;

import org.junit.Test;

import bg.client.inter.sigale.model.statistic.curve.PointsCourbe;
import bg.client.inter.sigale.model.statistic.curve.StatistiquesSynthese;

import com.google.gwt.core.shared.GWT;
import com.google.gwt.junit.client.GWTTestCase;

public class StatistiqueTest extends GWTTestCase {

	public StatistiqueTest(){		
	}
	
	@Test
	public void test_0() {
		GWT.log("Test de Junit");
		GWTTestCase.assertTrue(true);
	}
	
	
	@Test
	public void test_1() {
		GWT.log("Test de StatistiquesSynthese");
		StatistiquesSynthese ss = new StatistiquesSynthese(24,StatistiquesSynthese.DUREE_HOUR);
		for (int i=0; i<ss.pointsCurve.length;i++){
			PointsCourbe  pc = ss.pointsCurve[i];
			GWT.log("PointsCourbe "+i+"  : "+pc);
		}
		GWT.log(" getNbMax "+ss.getNbMax());
		GWT.log(" getNbTotalFailures "+ss.getNbTotalFailures());
		GWT.log(" getNbTotalSucces "+ss.getNbTotalSucces());
		GWT.log(" getNbTotalTentatives "+ss.getNbTotalTentatives());
		assertTrue(ss.getNbTotalTentatives()>0);
		assertTrue(ss.getNbMax()>0);
	}
	
	@Override
	public String getModuleName() {
		return "bg.sigale";
	}

}
