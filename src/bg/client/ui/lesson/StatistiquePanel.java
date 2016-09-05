package bg.client.ui.lesson;

import java.util.Date;
import java.util.List;

import bg.client.inter.sigale.model.UniteLexicale;
import bg.client.inter.sigale.model.statistic.StatistiquesItem;
import bg.client.inter.sigale.model.statistic.StatistiquesUL;

import com.google.gwt.canvas.client.Canvas;
import com.google.gwt.canvas.dom.client.Context2d;
import com.google.gwt.canvas.dom.client.CssColor;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

public class StatistiquePanel {

	private int heightCanvas = 120;
	private int widthCanvas = 300;
	private Canvas canvas = Canvas.createIfSupported();
	private Context2d context;
	private Label label;
	private int h_2 = heightCanvas / 2;
	private int w_2 = widthCanvas / 2;
	private int marge_w_g = 5;
	private int w = widthCanvas - 2 * marge_w_g;
	private int h = 20;
	private CssColor colorFond = CssColor.make(250, 250, 250);
	private CssColor colorText = CssColor.make(0, 0, 0);
	private CssColor colorBlack = CssColor.make(0, 0, 0);
	private Date date_0 = new Date();
	private long duree = DUREE_MONTH;
	private String titre = "titreBg";

	public StatistiquePanel() {

		if (canvas == null) {
			label = new Label("Canvas Not Supported");
		} else {
			canvas.setWidth(widthCanvas + "px");
			canvas.setCoordinateSpaceWidth(widthCanvas);

			canvas.setHeight(heightCanvas + "px");
			canvas.setCoordinateSpaceHeight(heightCanvas);

			context = canvas.getContext2d();

			context.setFillStyle(colorFond);
			context.fillRect(0, 0, widthCanvas, heightCanvas);
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
		initIntervalle(CALENDAR_DAY, "Day");
	}

	private static StatistiquePanel instance = new StatistiquePanel();

	public static StatistiquePanel getInstance() {
		return instance;
	}

	public void updateStat(UniteLexicale ul, boolean ok) {
		updateStat(ul);
	}

	UniteLexicale uniteLexicale;
	StatistiquesUL statistique;

	private void updateStat(UniteLexicale ulCourrante) {
		this.uniteLexicale = ulCourrante;
		if (this.uniteLexicale != null) {
			this.statistique = this.uniteLexicale.getStatistique();
		} else {
			statistique = null;
		}
		repaint();
	}

	private void repaint() {
		context.clearRect(0, 0, widthCanvas, heightCanvas);
		if (this.uniteLexicale == null) {
			paintNoUL();
		} else {
			paintStatistique();
		}
	}

	private void paintNoUL() {
		context.setFillStyle(colorText);
		context.fillText("No Statistiques", w_2, 20);
		context.beginPath();
		context.moveTo(2, h_2);
		context.lineTo(widthCanvas-2, h_2);
		context.lineTo(w_2, heightCanvas-2);
		context.lineTo(2, h_2);
		//context.fill();
		context.stroke();
		context.closePath();
	}

	private static final int CALENDAR_DAY = 0;
	private static final int CALENDAR_WEEK = 1;
	private static final int CALENDAR_MONTH = 2;

	private static final long DUREE_HOUR = 60 * 60 * 1000;
	private static final long DUREE_DAY = 24 * DUREE_HOUR;
	private static final long DUREE_WEEK = 7 * DUREE_DAY;
	private static final long DUREE_MONTH = 30 * DUREE_DAY;

	private void initIntervalle(int field, String titre) {
		this.date_0 = new Date();
		if (field == CALENDAR_DAY) {
			duree = DUREE_DAY;
		} else if (field == CALENDAR_WEEK) {
			duree = DUREE_WEEK;
		} else if (field == CALENDAR_MONTH) {
			duree = DUREE_MONTH;
		}
		this.titre = titre;
		repaint();
	}

	private void paintStatistique() {

		context.setFillStyle(colorBlack);
		context.fillText(titre, 20, 20);

		context.beginPath();
		context.moveTo(marge_w_g, h_2);
		context.lineTo(widthCanvas - marge_w_g, h_2);
		List<StatistiquesItem> list = statistique.getListAfterDate(this.date_0);
		int nb_succes = 0;
		int nb_failures = 0;
		for (StatistiquesItem statistiquesItem : list) {
			long deltaTime = statistiquesItem.getDate().getTime() - StatistiquePanel.this.date_0.getTime();
			int timeW = (int) ((deltaTime * w) / duree);
			int hStat;
			if (statistiquesItem.isSucces()) {
				hStat = 0;
				nb_succes++;
			} else {
				hStat = h;
				nb_failures++;
			}
			int x = marge_w_g + timeW;
			context.moveTo(x, h_2);
			context.lineTo(x, hStat + h_2);
		}
		context.stroke();
		//context.closePath();
		
		context.setFillStyle(colorText);
		
		context.fillText("Succes : " + nb_succes + " / " + list.size(), 20, h_2 - 10);
		context.fillText("Failures : " + nb_failures + " / " + list.size(), 20, h_2 + 20);

	}

	public Widget getCanvas() {
		if (canvas == null) {
			return label;
		}
		return canvas;
	}

}
