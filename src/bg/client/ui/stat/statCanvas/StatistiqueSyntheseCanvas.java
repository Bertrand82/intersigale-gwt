package bg.client.ui.stat.statCanvas;

import java.util.Date;

import bg.client.inter.sigale.model.Lexique;
import bg.client.inter.sigale.model.statistic.StatistiquesLexique;
import bg.client.inter.sigale.model.statistic.curve.PointsCourbe;
import bg.client.inter.sigale.model.statistic.curve.StatistiquesSynthese;

import com.google.gwt.canvas.client.Canvas;
import com.google.gwt.canvas.dom.client.Context2d;
import com.google.gwt.canvas.dom.client.CssColor;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

public class StatistiqueSyntheseCanvas {

	private int height = 120;
	private int width = 300;
	private Canvas canvas = Canvas.createIfSupported();
	private Context2d context;
	private Label label;
	private int h_2 = height / 2;
	private int w_2 = width / 2;
	private int marge_w_g = 5;
	private int w = width - 2 * marge_w_g;
	private int h = 20;
	private CssColor colorFond = CssColor.make(250, 250, 250);
	private CssColor colorText = CssColor.make(0, 0, 0);
	private CssColor colorBlack = CssColor.make(0, 0, 0);
	private Date date_0 = new Date();
	private Date date_1 = new Date();
	private long dureeTotale = StatistiquesSynthese.DUREE_MONTH;
	private long dureeUnitaire = StatistiquesSynthese.DUREE_DAY;
	private String titre = "titreBg";

	private Lexique lexique;
	private StatistiquesLexique statistique;

	public StatistiqueSyntheseCanvas() {

		if (canvas == null) {
			label = new Label("Canvas Not Supported");
		} else {

			canvas.setWidth(width + "px");
			canvas.setCoordinateSpaceWidth(width);

			canvas.setHeight(height + "px");
			canvas.setCoordinateSpaceHeight(height);

			context = canvas.getContext2d();

			context.setFillStyle(colorFond);
			context.fillRect(0, 0, width, height);
			context.setFillStyle(colorText);
			context.fillText("intersigale", 100, 20);

			context.beginPath();
			context.moveTo(25, 0);
			context.lineTo(0, 20);
			context.lineTo(25, 40);
			context.lineTo(25, 0);
			context.fill();
			context.closePath();

		}
		initIntervalle(CALENDAR_DAY);
	}



	public void refresh(Lexique lexique) {
		this.lexique = lexique;
		if (this.lexique != null) {
			this.statistique = this.lexique.getStatistiquesLexique();
		} else {
			statistique = null;
		}
		repaint();
	}

	private void repaint() {
		context.clearRect(0, 0, width, height);
		if (this.lexique == null) {
			paintNoLexique();
		} else {
			paintStatistique();
		}
	}

	private void paintNoLexique() {
		context.setFillStyle(colorText);
		context.fillText("No Statistiques", w_2, 20);
		context.beginPath();
		int h = h_2/2;
		context.moveTo(2, h);
		context.lineTo(width - 2, h);
		context.lineTo(w_2, height - 2);
		context.lineTo(2, h);
		// context.fill();
		context.stroke();
		context.closePath();
	}

	public static final int CALENDAR_HOUR = 0;
	public static final int CALENDAR_DAY = 1;
	public static final int CALENDAR_WEEK = 2;
	public static final int CALENDAR_MONTH = 3;


	public void initIntervalle(int field) {
		this.date_1 = new Date();
		if (field == CALENDAR_HOUR) {
			dureeTotale = StatistiquesSynthese.DUREE_HOUR;
			dureeUnitaire = 10 * StatistiquesSynthese.DUREE_MINUTE;
			this.titre = "Hour";
		} else if (field == CALENDAR_DAY) {
			dureeTotale = StatistiquesSynthese.DUREE_DAY;
			dureeUnitaire = StatistiquesSynthese.DUREE_HOUR;
			this.titre = "Day";
		} else if (field == CALENDAR_WEEK) {
			dureeTotale = StatistiquesSynthese.DUREE_WEEK;
			dureeUnitaire = StatistiquesSynthese.DUREE_DAY;
			this.titre = "Week";
		} else if (field == CALENDAR_MONTH) {
			dureeTotale = StatistiquesSynthese.DUREE_MONTH;
			dureeUnitaire = StatistiquesSynthese.DUREE_WEEK;
			this.titre = "Month";
		} else {
			this.titre = "XXX";
		}
		this.date_0 = new Date(this.date_1.getTime() - dureeTotale);

		repaint();
	}

	private void paintStatistique() {

		context.setFillStyle(colorBlack);
		context.fillText(titre, 20, 20);

		context.beginPath();
		context.moveTo(marge_w_g, h_2);
		context.lineTo(width - marge_w_g, h_2);
	 	//List<StatistiquesItem> list = statistique.getListAfterDate(this.date_0);
		StatistiquesSynthese  statisticSynthese = statistique.getStatistiquesSynthese(this.dureeTotale,this.dureeUnitaire);
		PointsCourbe[] pointsCourbeArray  = statisticSynthese.pointsCurve;
		int nbMax = statisticSynthese.getNbMax();
    	long deltaTime = statisticSynthese.dureeTotale;
		int nb_succes = statisticSynthese.getNbTotalSucces();
		int nb_failures = statisticSynthese.getNbTotalFailures();
		int nb_tentatives = statisticSynthese.getNbTotalTentatives();
		for (int i= 0; i<pointsCourbeArray.length;i++) {
			PointsCourbe pc = pointsCourbeArray[i];
			
			int timeW_start = (int) ((i*deltaTime * w) / dureeTotale);
			int timeW_end = (int) (((i+1)*deltaTime * w) / dureeTotale);
			int hStat = (pc.nbTentatives * h)/nbMax;
			 
			int x_start = marge_w_g + timeW_start;
			int x_end = marge_w_g + timeW_end;
			context.moveTo(x_start, hStat);
			context.lineTo(x_end, hStat );
		}
		context.stroke();
		// context.closePath();

		context.setFillStyle(colorText);
		
		context.fillText("Succes : " +(int) ((nb_succes *100)/nb_tentatives)+ " %" , 20, h_2 - 10);
		context.fillText("Failures : " +(int) (( nb_failures *100)/nb_tentatives) + " % ", 20, h_2 + 20);
		context.fillText("nb Max : " +nbMax , 20, h_2 + 30);

	}

	public Widget getPanelCanvas_() {
		if (canvas == null) {
			return label;
		}
		return canvas;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}


	public void removeStat() {
		refresh(null);
	}

}
