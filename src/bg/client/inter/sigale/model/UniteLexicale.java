package bg.client.inter.sigale.model;

import java.util.ArrayList;
import java.util.List;

import bg.client.inter.sigale.model.statistic.StatistiquesUL;
import bg.client.inter.sigale.util.UtilDivers;

public class UniteLexicale {

	/*
	 * ListUnion({ List(entry="p2", inline=true, type=Phrase.class) })
	 */

	public static final String TAG_ROOT = "UL";

	private Phrase phrase_0;

	private Phrase phrase_1;


	private StatistiquesUL statistique = new StatistiquesUL();

	public UniteLexicale() {
		super();
	}

	public UniteLexicale(Phrase phrase_0_, Phrase phrase_1_) {
		super();
		phrase_0 = phrase_0_;
		phrase_1 = phrase_1_;
	}

	public UniteLexicale(String text1, String text2) {
	   this(new Phrase(text1), new Phrase(text2));
	}

	public boolean resultProcess(String text) {
		String text2 = UtilDivers.removeAccents(text);
		String text2_ref = UtilDivers.removeAccents(getPhrase_1().text);
		boolean result = text2_ref.equalsIgnoreCase(text2);
		this.statistique.add(result);
		return result;
	}

	// (name="phrase_0")
	public Phrase getPhrase_0() {

		return phrase_0;
	}

	public void setPhrase_0(Phrase phrase_0_) {
		phrase_0 = phrase_0_;
	}

	// (name="phrase_1")
	public Phrase getPhrase_1() {
		return phrase_1;
	}

	public void setPhrase_1(Phrase phrase_1_) {
		phrase_1 = phrase_1_;
	}



	@Override
	public String toString() {
		return " ---- " + phrase_0 + " ---- " + phrase_1;
	}

	public String getStatistiqueResume() {

		return this.statistique.getResume();
	}

	public StatistiquesUL getStatistique() {
		String uniteLexicaleId = getId();
		statistique.setUniteLexicaleId(uniteLexicaleId);
		return statistique;
	}

	/**
	 * Id est la phrase a traduire. Ce n'est pas forcement une bonne id�e.
	 * 
	 * @return
	 */
	public String getId() {
		return getPhrase_0().getText();
	}

	public void setStatistique(StatistiquesUL statistique) {
		this.statistique = statistique;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((phrase_0 == null) ? 0 : phrase_0.hashCode());
		result = prime * result
				+ ((phrase_1 == null) ? 0 : phrase_1.hashCode());
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
		UniteLexicale other = (UniteLexicale) obj;
		if (phrase_0 == null) {
			if (other.phrase_0 != null)
				return false;
		} else if (!phrase_0.equals(other.phrase_0))
			return false;
		if (phrase_1 == null) {
			if (other.phrase_1 != null)
				return false;
		} else if (!phrase_1.equals(other.phrase_1))
			return false;
		return true;
	}

	public void setPhrase(Phrase phrase, int k) {
		if (k == 0){
			phrase_0 = phrase;
		}else if (k == 1){
			phrase_1 = phrase;
		}
		
	}

	
}
