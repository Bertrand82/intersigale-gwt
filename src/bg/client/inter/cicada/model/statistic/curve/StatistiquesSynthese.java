package bg.client.inter.cicada.model.statistic.curve;

import java.util.Date;

import bg.client.inter.cicada.model.statistic.StatistiquesItem;

public class StatistiquesSynthese {

	public static final long DUREE_MINUTE = 60 * 1000;
	public static final long DUREE_HOUR = 60 * DUREE_MINUTE;
	public static final long DUREE_DAY = 24 * DUREE_HOUR;
	public static final long DUREE_WEEK = 7 * DUREE_DAY;
	public static final long DUREE_MONTH = 30 * DUREE_DAY;

	
	/**
	 * Mois, Jour, heure 
	 */
	public long dureeUnitaire;
	/*
	 * Nb de jours, d'heure, de mois.
	 */
	public int nbUnites=7;
	public PointsCourbe[] pointsCurve;
	public Date date_Debut= new Date();
	public Date date_Fin= new Date();
	public int nbMax =-1;
	public int nbTotalSucces=-1;
	public int nbTotalFailures =-1;
	public int nbTotalTentatives =-1;
	public long dureeTotale = 1;

	public StatistiquesSynthese( int nbUnite, long duree) {
		this.nbUnites=nbUnite;
		this.dureeUnitaire=duree;
		this.date_Fin= new Date();
		this.dureeTotale= nbUnites*dureeUnitaire;
		this.date_Debut= new Date(date_Fin.getTime()-dureeTotale);
		
		pointsCurve = new PointsCourbe[nbUnite];
		for(int i=0; i<nbUnites;i++){
			pointsCurve[i]= new PointsCourbe(date_Debut, duree, 0, 0, 0);
		}
	}

	
	

	public int getNbMax() {
		if(nbMax == -1){
			initNbMax();
		}
		return nbMax;
	}

	private void initNbMax() {
		nbTotalFailures=0;
		nbTotalSucces=0;
		nbTotalTentatives =0;
		for(int i = 0; i<pointsCurve.length;i++){
			PointsCourbe pc = pointsCurve[i];
			int n = pc.nbTentatives;
			if (n > nbMax){
				nbMax=n;
			}
			nbTotalFailures+= pc.nbEchecs;
			nbTotalSucces+= pc.nbSucces;
			nbTotalTentatives += pc.nbTentatives;
		}
	}

	public int getNbTotalSucces() {
		if(nbTotalSucces == -1){
			initNbMax();
		}
		return nbTotalSucces;
	}

	public int getNbTotalFailures() {
		if(nbTotalFailures == -1){
			initNbMax();
		}
		return nbTotalFailures;
	}

	public int getNbTotalTentatives() {
		if(nbTotalTentatives == -1){
			initNbMax();
		}
		return nbTotalTentatives;
	}



	public void process(StatistiquesItem statItem) {
		Date d = statItem.getDate();
		if (d.before(date_Debut)){
			// Trop tot
		}else if (d.after(date_Fin)){
			//trop tard
		} else {
			int i = getIntervalle(d);
			pointsCurve[i].process(statItem);
		}
	}



	private int getIntervalle(Date d) {
		long time = d.getTime();
		long timeDebut = date_Debut.getTime();
		for(int i=0; i<pointsCurve.length;i++){
			timeDebut += dureeUnitaire;
			if (time < timeDebut){
				return i;
			}
		}
		return -1; 
	}
	
	
	public void initTest() {
		for(int i=0;i<nbUnites;i++){
			Date dateDebut = new Date(date_Debut.getTime()+i*dureeUnitaire);
			int nbEchec = (int) (100*Math.random());
			int nbSuuces = (int) (100*Math.random());
			int nbTentatives = nbSuuces+nbEchec;
			PointsCourbe pc = new PointsCourbe(dateDebut,dureeUnitaire,nbTentatives,nbSuuces , nbEchec);
			this.pointsCurve[i]=pc;
		}
		initNbMax();
	}

}
