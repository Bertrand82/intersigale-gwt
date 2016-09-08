package bg.client.inter.sigale.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import bg.client.inter.sigale.model.statistic.StatistiquesLexique;
import bg.client.inter.sigale.model.statistic.StatistiquesUL;

public class Lexique implements Serializable {

	private static final long serialVersionUID = 1L;
	public static final String TAG_name = "name";
	public static String TAG_ROOT = "lexique";

	private String name = "intersigale-default";
	private long id=0;
	private StatistiquesLexique statistiquesLexique;

	private List<UniteLexicale> listUniteLexicale = new ArrayList<UniteLexicale>();

	int iCourrante = 0;

	public Lexique() {
		super();
	}

	public UniteLexicale next(int n) {
		iCourrante += n;
		if (listUniteLexicale.size() == 0) {
			return new UniteLexicale("", "");
		}
		iCourrante = iCourrante % listUniteLexicale.size();
		return getUniteLexicaleCourante();
	}

	public UniteLexicale next() {
		return next(1);
	}

	public UniteLexicale getUniteLexicaleCourante() {
		if (listUniteLexicale.size() == 0) {
			return new UniteLexicale("", "");
		}
		if (iCourrante >= listUniteLexicale.size()) {
			iCourrante = listUniteLexicale.size() - 1;
		}
		if (iCourrante < 0) {
			iCourrante = listUniteLexicale.size() - 1;
		}
		return listUniteLexicale.get(iCourrante);
	}

	public void add(UniteLexicale uniteLexicale) {
		this.listUniteLexicale.add(uniteLexicale);
	}

	public List<UniteLexicale> getListUniteLexicale() {
		return listUniteLexicale;
	}

	public void setListUniteLexicale(List<UniteLexicale> listUniteLexicale) {
		this.listUniteLexicale = listUniteLexicale;
	}

	@Override
	public String toString() {
		String s = "  size : " + listUniteLexicale.size() + "  name : " + name;
		for (Object ul : listUniteLexicale) {
			s += "\n " + ul;
		}
		return s;
	}

	public int getNbUniteLexicale(boolean b) {
		int i = 0;
		for (UniteLexicale ul : listUniteLexicale) {
			if (ul.getStatistique().isLastResult(b)) {
				i++;
			}
			;
		}
		return i;
	}

	/**
	 * 
	 * @param statistiquesLexique
	 */
	public void setStatistiquesLexique(StatistiquesLexique statistiquesLexique) {
		this.statistiquesLexique = statistiquesLexique;
		
	}

	public StatistiquesLexique getStatistiquesLexique() {
		if (statistiquesLexique == null) {
			createStatistiqueLexique();
		}
		return statistiquesLexique;
	}

	private void createStatistiqueLexique() {
		this.statistiquesLexique = new StatistiquesLexique();
		for (UniteLexicale ul : listUniteLexicale) {
			StatistiquesUL stat = ul.getStatistique();
			statistiquesLexique.getListStatistiqueUL().add(stat);
		}
	}

	

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getiCourrante() {
		return iCourrante;
	}

	public void setiCourrante(int iCourrante) {
		this.iCourrante = iCourrante;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((listUniteLexicale == null) ? 0 : listUniteLexicale.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
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
		Lexique other = (Lexique) obj;
		if (listUniteLexicale == null) {
			if (other.listUniteLexicale != null)
				return false;
		} else if (!listUniteLexicale.equals(other.listUniteLexicale))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

}
