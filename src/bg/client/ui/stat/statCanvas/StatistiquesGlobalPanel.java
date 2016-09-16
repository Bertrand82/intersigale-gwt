package bg.client.ui.stat.statCanvas;

import bg.client.inter.sigale.model.Lexique;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;

public class StatistiquesGlobalPanel extends Composite {

	private static StatistiquesPanelUiBinder uiBinder = GWT.create(StatistiquesPanelUiBinder.class);

	interface StatistiquesPanelUiBinder extends UiBinder<Widget, StatistiquesGlobalPanel> {
	}

	private static StatistiquesGlobalPanel instance = new StatistiquesGlobalPanel();

	public static StatistiquesGlobalPanel getInstance() {
		return instance;
	}

	final StatistiqueSyntheseCanvas statistiqueCanvas = new StatistiqueSyntheseCanvas();

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

	private StatistiquesGlobalPanel() {
		initWidget(uiBinder.createAndBindUi(this));

		panelCanvas.add(statistiqueCanvas.getPanelCanvas_());
		panelCanvas.setWidth(statistiqueCanvas.getWidth() + "px");
		panelCanvas.setHeight(statistiqueCanvas.getHeight() + "px");

		buttonHour.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				statistiqueCanvas.initIntervalle(StatistiqueSyntheseCanvas.CALENDAR_HOUR);
			}
		});
		buttonDay.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				statistiqueCanvas.initIntervalle(StatistiqueSyntheseCanvas.CALENDAR_DAY);
			}
		});
		buttonWeek.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				statistiqueCanvas.initIntervalle(StatistiqueSyntheseCanvas.CALENDAR_WEEK);
			}
		});
		buttonMonth.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				statistiqueCanvas.initIntervalle(StatistiqueSyntheseCanvas.CALENDAR_MONTH);
			}
		});
		//setButtonVisible(false);
	}

	public void refresh(Lexique lexique) {
		statistiqueCanvas.refresh(lexique);
		boolean visible = (lexique != null);
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
