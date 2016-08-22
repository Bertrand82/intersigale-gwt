package bg.client.ui.lesson;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.Widget;

public class StatistiquePanelGUI extends Composite {

	private static StatistiquePanelGUIUiBinder uiBinder = GWT.create(StatistiquePanelGUIUiBinder.class);

	interface StatistiquePanelGUIUiBinder extends UiBinder<Widget, StatistiquePanelGUI> {
	}

	public StatistiquePanelGUI() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	private static StatistiquePanelGUI instance = new StatistiquePanelGUI();

	public static StatistiquePanelGUI getInstance() {
		return instance;
	}

	public void updateStat(Object object) {
		// TODO Auto-generated method stub

	}

}
