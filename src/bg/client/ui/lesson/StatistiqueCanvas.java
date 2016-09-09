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

public class StatistiqueCanvas {

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
	private long duree = DUREE_MONTH;
	private String titre = "titreBg";

	private UniteLexicale uniteLexicale;
	private StatistiquesUL statistique;

	public StatistiqueCanvas() {

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



	public void updateStat(UniteLexicale ul, boolean ok) {
		updateStat(ul);
	}

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
		context.clearRect(0, 0, width, height);
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
		context.lineTo(width - 2, h_2);
		context.lineTo(w_2, height - 2);
		context.lineTo(2, h_2);
		// context.fill();
		context.stroke();
		context.closePath();
	}

	public static final int CALENDAR_HOUR = 0;
	public static final int CALENDAR_DAY = 1;
	public static final int CALENDAR_WEEK = 2;
	public static final int CALENDAR_MONTH = 3;

	private static final long DUREE_HOUR = 60 * 60 * 1000;
	private static final long DUREE_DAY = 24 * DUREE_HOUR;
	private static final long DUREE_WEEK = 7 * DUREE_DAY;
	private static final long DUREE_MONTH = 30 * DUREE_DAY;

	public void initIntervalle(int field) {
		this.date_1 = new Date();
		if (field == CALENDAR_HOUR) {
			duree = DUREE_HOUR;
			this.titre = "Hour";
		} else if (field == CALENDAR_DAY) {
			duree = DUREE_DAY;
			this.titre = "Day";
		} else if (field == CALENDAR_WEEK) {
			duree = DUREE_WEEK;
			this.titre = "Week";
		} else if (field == CALENDAR_MONTH) {
			duree = DUREE_MONTH;
			this.titre = "Month";
		} else {
			this.titre = "XXX";
		}
		this.date_0 = new Date(this.date_1.getTime() - duree);

		repaint();
	}

	private void paintStatistique() {

		context.setFillStyle(colorBlack);
		context.fillText(titre, 20, 20);

		context.beginPath();
		context.moveTo(marge_w_g, h_2);
		context.lineTo(width - marge_w_g, h_2);
		List<StatistiquesItem> list = statistique.getListAfterDate(this.date_0);
		int nb_succes = 0;
		int nb_failures = 0;
		for (StatistiquesItem statistiquesItem : list) {
			long deltaTime = statistiquesItem.getDate().getTime() - StatistiqueCanvas.this.date_0.getTime();
			int timeW = (int) ((deltaTime * w) / duree);
			int hStat;
			if (statistiquesItem.isSucces()) {
				hStat = -h;
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
		// context.closePath();

		context.setFillStyle(colorText);

		context.fillText("Succes : " + nb_succes + " / " + list.size(), 20, h_2 - 10);
		context.fillText("Failures : " + nb_failures + " / " + list.size(), 20, h_2 + 20);

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
		updateStat(null);
	}

}
