package bg.client.ui.lesson;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;

public class StatistiquesPanel extends Composite  {

	private static StatistiquesPanelUiBinder uiBinder = GWT.create(StatistiquesPanelUiBinder.class);

	interface StatistiquesPanelUiBinder extends UiBinder<Widget, StatistiquesPanel> {
	}

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
	
	public StatistiquesPanel() {
		initWidget(uiBinder.createAndBindUi(this));
		final StatistiqueCanvas statistiqueCanvas = StatistiqueCanvas.getInstance2();
		panelCanvas.add(statistiqueCanvas.getPanelCanvas());
		panelCanvas.setWidth(statistiqueCanvas.getWidth()+"px");
		panelCanvas.setHeight(statistiqueCanvas.getHeight()+"px");
		
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
	}

	


}
