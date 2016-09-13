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
	private int marge_h_b = 50;
	private CssColor colorFond = CssColor.make(250, 250, 250);
	private CssColor colorText = CssColor.make(0, 0, 0);
	private CssColor colorBlack = CssColor.make(0, 0, 0);
	private CssColor colorRed = CssColor.make(255, 0, 0);
	private CssColor colorGreen = CssColor.make(0, 255, 0);
	private CssColor colorBlue = CssColor.make(0, 0, 255);
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

			canvas.setWidth((width+marge_w_g) + "px");
			canvas.setCoordinateSpaceWidth(width+marge_w_g);

			canvas.setHeight((height+marge_h_b) + "px");
			canvas.setCoordinateSpaceHeight(height+marge_h_b);

			context = canvas.getContext2d();

			context.setFillStyle(colorFond);
			context.fillRect(0, 0, width, height);
			context.setFillStyle(colorText);
			context.fillText("intersigale", 100, 20);

			

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
		context.clearRect(0, 0, width+marge_w_g, height+marge_h_b);
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
		int h1 = h_2/2;
		context.moveTo(2, h1);
		context.lineTo(width - 2, h1);
		context.lineTo(w_2, height - 2);
		context.lineTo(2, h1);
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
		context.setStrokeStyle(colorBlack);
		context.fillText(titre, 20, 20);

		context.beginPath();
		context.moveTo(marge_w_g, height);
		context.lineTo(width - marge_w_g, height);
		context.moveTo(marge_w_g, height);
		context.lineTo(marge_w_g, 0);
		context.stroke();
	 	//List<StatistiquesItem> list = statistique.getListAfterDate(this.date_0);
		StatistiquesSynthese  statisticSynthese = statistique.getStatistiquesSynthese(this.dureeTotale,this.dureeUnitaire);
		PointsCourbe[] pointsCourbeArray  = statisticSynthese.pointsCurve;
		int nbMax = statisticSynthese.getNbMax();
    	long deltaTime = statisticSynthese.dureeUnitaire;
		int nbTotalFailures = statisticSynthese.getNbTotalFailures();
		int nbTotalTentatives = statisticSynthese.getNbTotalTentatives();
		drawSegment(pointsCourbeArray, colorBlue,deltaTime,PointsCourbe.VALUE_nbTenteatives,nbMax,"Total");
		drawSegment(pointsCourbeArray, colorRed,deltaTime,PointsCourbe.VALUE_nbEchecs,nbMax,"Failure");
		
		context.setFillStyle(colorText);
		wLegende =marge_w_g;
		context.fillText("nb Max : " +nbMax+" nbUnites : "+statisticSynthese.nbUnites , 20, height + 30);
		context.fillText("Failures : " +(int) (( nbTotalFailures *100)/nbTotalTentatives) + " % ", 20, height + 20);

	}
	
	int wLegende =0;
	private void drawSegment(PointsCourbe[] pointsCourbeArray, CssColor color, long deltaTime,int iCourbe, int nbMax , String legende) {
		context.setStrokeStyle(color);
		context.setFillStyle(color);
		context.beginPath();
		context.moveTo(marge_w_g, height);
		int deltawidth = (int) ((deltaTime * width) / dureeTotale);
		for (int i= 0; i<pointsCourbeArray.length;i++) {
			PointsCourbe pc = pointsCourbeArray[i];			
			int timeW_start = (int) ((i*deltaTime * width) / dureeTotale);
			int hStat_nbTentatives = height - (pc.getValue(iCourbe) * height)/nbMax;
			context.fillRect( timeW_start, hStat_nbTentatives, deltawidth, height - hStat_nbTentatives);
			context.fill();
		}
		context.moveTo(wLegende, height + 40);
		context.lineTo(wLegende+10,height + 35 );
		context.fillText(""+legende , wLegende+15, height + 40);
		wLegende+=70;
		context.stroke();
	}

	public Widget getPanelCanvas_() {
		if (canvas == null) {
			return label;
		}
		return canvas;
	}

	public int getWidth() {
		return width+marge_w_g;
	}

	public int getHeight() {
		return height+marge_h_b;
	}


	public void removeStat() {
		refresh(null);
	}

}
