package bg.client.ui.lesson.lessonStat;

import bg.client.inter.cicada.model.UniteLexicale;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;

public class StatistiquesPanel extends Composite {

	private static StatistiquesPanelUiBinder uiBinder = GWT.create(StatistiquesPanelUiBinder.class);

	interface StatistiquesPanelUiBinder extends UiBinder<Widget, StatistiquesPanel> {
	}

	private static StatistiquesPanel instance = new StatistiquesPanel();

	public static StatistiquesPanel getInstance() {
		return instance;
	}

	final StatistiqueCanvas statistiqueCanvas = new StatistiqueCanvas();

	@UiField
	SimplePanel panelCanvas;

	@UiField
	Button buttonHour;
	@UiField
	Button buttonDay;
	@UiField
	Button buttonWeek;

	@UiField
	Button buttonMonth;

	private StatistiquesPanel() {
		initWidget(uiBinder.createAndBindUi(this));

		panelCanvas.add(statistiqueCanvas.getPanelCanvas_());
		panelCanvas.setWidth(statistiqueCanvas.getWidth() + "px");
		panelCanvas.setHeight(statistiqueCanvas.getHeight() + "px");

		buttonHour.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				statistiqueCanvas.initIntervalle(StatistiqueCanvas.CALENDAR_HOUR);
			}
		});
		buttonDay.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				statistiqueCanvas.initIntervalle(StatistiqueCanvas.CALENDAR_DAY);
			}
		});
		buttonWeek.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				statistiqueCanvas.initIntervalle(StatistiqueCanvas.CALENDAR_WEEK);
			}
		});
		buttonMonth.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				statistiqueCanvas.initIntervalle(StatistiqueCanvas.CALENDAR_MONTH);
			}
		});
		setButtonVisible(false);
	}

	public void updateStat(UniteLexicale ulCourrante, boolean ok) {
		statistiqueCanvas.updateStat(ulCourrante, ok);
		boolean visible = (ulCourrante != null);
		setButtonVisible(visible);
	}

	private void setButtonVisible(boolean visible) {
		
		this.buttonDay.setVisible(visible);
		this.buttonHour.setVisible(visible);
		this.buttonMonth.setVisible(visible);
		this.buttonWeek.setVisible(visible);
	}

	public void removeStat() {
		statistiqueCanvas.removeStat();
		setButtonVisible(false);
	}


}
