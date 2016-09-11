package bg.client.inter.sigale.model.statistic.curve;

import java.util.Date;

public class PointsCourbe {
	public Date dateDebut;
	public long duree;
	public int nbTentatives= 0;
	public int nbSucces=0;
	public int nbEchecs=0;
	
	
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
	
	
}
