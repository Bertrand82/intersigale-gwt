package bg.client.inter.sigale.model.statistic.curve;

import java.util.Date;

import com.sun.org.apache.bcel.internal.generic.RETURN;

public class PointsCourbe {
	public Date dateDebut;
	public long duree;
	public int nbTentatives = 0;
	public int nbSucces = 0;
	public int nbEchecs = 0;
	public static final int VALUE_nbTenteatives = 0;
	public static final int VALUE_nbSucces = 1;
	public static final int VALUE_nbEchecs = 2;

	public PointsCourbe(Date dateDebut, long duree, int nbTentatives, int nbSucces, int nbEchecs) {
		super();
		this.dateDebut = dateDebut;
		this.duree = duree;
		this.nbTentatives = nbTentatives;
		this.nbSucces = nbSucces;
		this.nbEchecs = nbEchecs;
	}

	@Override
	public String toString() {
		return "PointsCourbe [duree=" + duree + ", nbTentatives=" + nbTentatives + ", nbSucces=" + nbSucces + ", nbEchecs=" + nbEchecs + "]";
	}

	public int getValue(int iCourbe) {
		switch (iCourbe) {
		case VALUE_nbEchecs:
			return nbEchecs;
		case VALUE_nbTenteatives:
			return nbTentatives;
		case VALUE_nbSucces:
			return nbSucces;
		default:
			return 0;
		}
	}

}
