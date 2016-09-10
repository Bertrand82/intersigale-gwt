package bg.client.inter.sigale.model.statistic.curve;

import java.util.Date;

public class StatistiquesSynthese {

	public StatistiquesSynthese(Date dateDebut, int nbUnite, long unite) {
		pointsCurve = new PointsCurve[nbUnite];
	}

	PointsCurve[] pointsCurve;
}
