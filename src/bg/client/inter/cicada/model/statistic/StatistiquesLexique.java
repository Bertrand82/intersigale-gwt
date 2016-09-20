package bg.client.inter.cicada.model.statistic;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import bg.client.inter.cicada.model.Lexique;
import bg.client.inter.cicada.model.LexiqueFactory;
import bg.client.inter.cicada.model.UniteLexicale;
import bg.client.inter.cicada.model.statistic.curve.StatistiquesSynthese;

public class StatistiquesLexique {

	public static final String TAG_ROOT = "LexiqueStat";

	private List<StatistiquesUL> listStatistiqueUL = new ArrayList<StatistiquesUL>();

	public List<StatistiquesUL> getListStatistiqueUL() {
		return listStatistiqueUL;
	}

	public void setListStatistiqueUL(List<StatistiquesUL> listStatistiqueUL) {
		this.listStatistiqueUL = listStatistiqueUL;
	}

	@Override
	public String toString() {
		return "StatistiquesLexique [listStatistiqueUL.size =" + listStatistiqueUL.size() + "  " + listStatistiqueUL + "]";
	}

	public StatistiquesUL getStatistiqueULById(String id) {
		if (id == null) {
			id = "";
		}
		for (StatistiquesUL sul : listStatistiqueUL) {
			if (id.equals(sul.getUniteLexicaleId())) {
				return sul;
			}
		}
		return new StatistiquesUL();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((listStatistiqueUL == null) ? 0 : listStatistiqueUL.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		StatistiquesLexique other = (StatistiquesLexique) obj;
		if (listStatistiqueUL == null) {
			if (other.listStatistiqueUL != null)
				return false;
		} else if (!listStatistiqueUL.equals(other.listStatistiqueUL))
			return false;
		return true;
	}

	public StatistiquesSynthese getStatistiquesSynthese(long dureeTotale, long dureeUnitaire) {
		Date date_1 = new Date();
		Date date_0 = new Date(date_1.getTime()-dureeTotale);
		
		int  nbUnits = (int) (dureeTotale/dureeUnitaire);
		StatistiquesSynthese statSyntb =  new StatistiquesSynthese((int) nbUnits, dureeUnitaire);
		Lexique lexique = LexiqueFactory.getInstance().getLexique();
		String lexiqueName= lexique.getName();
		for(UniteLexicale ul   : lexique.getListUniteLexicale()){
			StatistiquesUL statistiqueUL = ul.getStatistique();
			for (StatistiquesItem statItem : statistiqueUL.getList()){
				Date d = statItem.getDate();
				if (d.before(date_0)){
					// Trop tot
				}else if (d.after(date_1)){
					//trop tard
				} else {
					statSyntb.process(statItem);
				}
			}
		}
		
		//statSyntb.initTest();
		return statSyntb;
	}

}
